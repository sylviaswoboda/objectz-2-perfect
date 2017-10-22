package at.ac.tuwien.oz.translator.templates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;

// TODO Testcase
public class StringTemplateSubstitutor {
	
	private PerfectTemplateChecker templateChecker;
	private PerfectTemplateProvider perfect;
	
	public StringTemplateSubstitutor(){
		this.templateChecker = new PerfectTemplateChecker();
		this.perfect = PerfectTemplateProvider.getInstance();
	}
	
	public ST substituteIdentifier(ST originalTemplate, ST oldIdentifier, ST newIdentifier){
		if (templateChecker.isId(originalTemplate)){
			// found an id template - check if values are equals to oldIdentifier
			// then remove old values and replace by values of newIdentifier.
			if (templateChecker.hasSameNameAndDecoration(originalTemplate, oldIdentifier)){
				originalTemplate.remove("name");
				originalTemplate.remove("decoration");
				originalTemplate.add("name", newIdentifier.getAttribute("name"));
				originalTemplate.add("decoration", newIdentifier.getAttribute("decoration"));
			}
		} else {
			// found something different
			// try substitution on all attributes where the contained object is a StringTemplate
			if (originalTemplate.getAttributes() != null){
				for (Object o: originalTemplate.getAttributes().values()){
					if (o instanceof ST){
						substituteIdentifier((ST)o, oldIdentifier, newIdentifier);
					} else if (o instanceof Collection<?>){
						for (Object element: (Collection<?>)o){
							if (element instanceof ST){
								substituteIdentifier((ST)element, oldIdentifier, newIdentifier);
							}
						}
					}
				}
			}
		}
		return originalTemplate;
	}

	public void substituteOutputIdent(ST predicate, Ident outputIdent) {
		ST parentTemplate = predicate;
		
		if (parentTemplate.getAttributes() == null){
			return;
		}
		for (String attributeName: parentTemplate.getAttributes().keySet()){
			Object attribute = parentTemplate.getAttribute(attributeName);
			if (attribute instanceof ST){
				substituteOutputIdentInTemplate(outputIdent, parentTemplate, attributeName, (ST)attribute);
			} else if (attribute instanceof List<?>){
				List<ST> attributeCollectionWithSubstitutions = new ArrayList<ST>();
				for (Object element: (List<?>)attribute){
					if (element instanceof ST){
						substituteOutputIdentInCollection(outputIdent, attributeCollectionWithSubstitutions, (ST)element);
					}
				}
				parentTemplate.remove(attributeName);
				parentTemplate.add(attributeName, attributeCollectionWithSubstitutions);
			}
		}
	}

	private void substituteOutputIdentInCollection(Ident outputIdent, List<ST> anAttributeCollection, ST potentialIdentTemplate) {
		ST oldOutputVariableReference = perfect.getId(outputIdent);
		ST newOutputVariableReference = perfect.getOutputVariableReference(outputIdent);

		if (templateChecker.isId(potentialIdentTemplate)){
			if (templateChecker.hasSameNameAndDecoration(potentialIdentTemplate, oldOutputVariableReference)){
				anAttributeCollection.add(newOutputVariableReference);
			} else {
				anAttributeCollection.add(potentialIdentTemplate);
			}
		} else {
			substituteOutputIdent(potentialIdentTemplate, outputIdent);
			anAttributeCollection.add(potentialIdentTemplate);
		}
	}

	private void substituteOutputIdentInTemplate(Ident outputIdent,
			ST parentTemplate, String attributeName, ST potentialIdentTemplate) {
		
		ST oldOutputVariableReference = perfect.getId(outputIdent);
		ST newOutputVariableReference = perfect.getOutputVariableReference(outputIdent);

		if (templateChecker.isId(potentialIdentTemplate)){
			if (templateChecker.hasSameNameAndDecoration(potentialIdentTemplate, oldOutputVariableReference)){
				parentTemplate.remove(attributeName);
				parentTemplate.add(attributeName, newOutputVariableReference);
			}
		} else {
			substituteOutputIdent(potentialIdentTemplate, outputIdent);
		}
	}

	public ST primeOutputVariables(ST postcondition, List<Ident> outputVariableIdents) {
		for(Ident outputIdent: outputVariableIdents){
			Ident newIdent = outputIdent.getPrimedCopy();
			postcondition = substituteIdentifier(postcondition, perfect.getId(outputIdent), perfect.getId(newIdent));
		}
		return postcondition;
	}
	
	public ST primeOutputVariables(ST postcondition, Idents outputVariableIdents) {
		return primeOutputVariables(postcondition, outputVariableIdents.asList());
	}

}
