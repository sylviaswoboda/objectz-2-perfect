package at.ac.tuwien.oz.definitions.operation.predicateprovider;

import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICompositionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;
import at.ac.tuwien.oz.translator.templates.StringTemplateCloner;
import at.ac.tuwien.oz.translator.templates.StringTemplateSubstitutor;

public abstract class OperationPredicateProvider 
	implements PostconditionProvider, PreconditionProvider, PredicateProvider{

	protected StringTemplateSubstitutor substitutor;
	protected StringTemplateCloner cloner;
	protected PerfectPredicateTemplateProvider predicateProvider;

	protected PostconditionFactory postconditionFactory;
	protected PreconditionFactory preconditionFactory;
	
	protected ICompositionFactory compositionFactory;
	
	public OperationPredicateProvider(){
		this.cloner = new StringTemplateCloner();
		this.substitutor = new StringTemplateSubstitutor();
		this.postconditionFactory = PostconditionFactory.getInstance();
		this.preconditionFactory = PreconditionFactory.getInstance();
		this.compositionFactory = PostconditionFactory.getInstance();
		this.predicateProvider = PerfectPredicateTemplateProvider.getInstance();
	}
	
//	protected Axiom getPreconditionOperationCall(SimpleBoolFunction preconditionOperation){
//		return getPreconditionOperationCall(null, preconditionOperation);
//	}

//	protected Axiom getPreconditionOperationCall(ST caller, SimpleBoolFunction preconditionOperation){
//		if (preconditionOperation != null){
//			ST preconditionCallTemplate = predicateProvider.createFunctionCallTemplate(caller, preconditionOperation);
//			return new PreconditionAxiom(preconditionCallTemplate);
//		}
//		return null;
//	}
}
