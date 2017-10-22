package at.ac.tuwien.oz.datatypes.postconditions;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.translator.templates.StringTemplateCloner;
import at.ac.tuwien.oz.translator.templates.StringTemplateSubstitutor;

public class SimpleFunctionPostcondition implements IPostcondition{

	private Idents identifiers;
	private ST template;
	
	private SimpleFunctionPostcondition(Idents identifiers){
		this.identifiers = new Idents();
		this.identifiers.addAll(identifiers);
	}
	
	public SimpleFunctionPostcondition(Idents identifiers, ST originalPredicate, Declarations outputVariables) {
		this(identifiers);
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
		
		ST templateClone = cloner.clone(originalPredicate);
		for (Variable outputVariable: outputVariables.asList()){
			substitutor.substituteOutputIdent(templateClone, outputVariable.getId());
		}
		this.template = templateClone;
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
