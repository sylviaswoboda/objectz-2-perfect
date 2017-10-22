package at.ac.tuwien.oz.datatypes.postconditions;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.translator.templates.StringTemplateCloner;
import at.ac.tuwien.oz.translator.templates.StringTemplateSubstitutor;

public class SimpleSchemaPostcondition implements IPostcondition{

	private Idents identifiers;
	private ST template;
	
	private SimpleSchemaPostcondition(Idents identifiers){
		this.identifiers = new Idents();
		this.identifiers.addAll(identifiers);
	}
	
	public SimpleSchemaPostcondition(Idents identifiers, ST originalPredicate, Idents outputVariableIdents) {
		this(identifiers);
		
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
		
		ST clonedPostcondition = cloner.clone(originalPredicate);
		clonedPostcondition = substitutor.primeOutputVariables(clonedPostcondition, outputVariableIdents);
		
		this.template = clonedPostcondition;
	}

	@Override
	public ST getTemplate() {
		return template;
	}

	@Override
	public Idents getIdentifiers() {
		return this.identifiers;
	}

}
