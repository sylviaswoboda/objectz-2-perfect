package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.SimpleFunctionPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.SimplePostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.translator.templates.StringTemplateCloner;

public class SimpleFunction extends SimpleOperation{

	private SimplePreconditions  preconditions;
	private SimplePostconditions postconditions;
	
	public SimpleFunction(String operationName, Declarations declarations, AxiomReferences predicates, ObjectZClass definingClass, ParseTree operationCtx) {
		super(operationName, declarations, predicates, definingClass, operationCtx);
	}

	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree){
		PreconditionFactory  preconditionFactory  = PreconditionFactory.getInstance();
		PostconditionFactory  postconditionFactory  = PostconditionFactory.getInstance();
		
		preconditions = new SimplePreconditions();
		postconditions = new SimplePostconditions();
		
		for(Axiom originalPrecondition: super.getOriginalPreconditions().asList()){
			IPrecondition simplePrecondition = preconditionFactory.createSimplePrecondition(originalPrecondition);
			preconditions.add(simplePrecondition);
		}

		List<ST> implicitPreconditions = new ArrayList<ST>();
		
		for (Axiom originalPredicate: super.getOriginalPostconditions().asList()){
			SimpleFunctionPostcondition functionPostcondition = 
				postconditionFactory.createOutputVariablePredicate(originalPredicate, super.getOutputParameters());
			postconditions.add(functionPostcondition);
			
			// add existential quantification for all output variables combined with the used postcondition predicates
			StringTemplateCloner cloner = new StringTemplateCloner();
			ST postconditionClone = cloner.clone(originalPredicate.getPredicate());
			
			implicitPreconditions.add(postconditionClone);
		}
		
		this.preconditions.addAll(getVariableTypePreconditions().asList());
		
		preconditions.add(preconditionFactory.createImplicitFunctionPrecondition(this.getOutputParameters(), implicitPreconditions));
		
    	if (hasPreconditions()){
    		preconditionFunction = new PreconditionFunction(this, this.preconditions, this.definingClass);
    	}

	}
	
	@Override
	public boolean isBoolFunction() {
		return false;
	}

	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public boolean isChangeOperation() {
		return false;
	}

	@Override
	public String getPostconditionFunctionName(){
		return getName();
	}

	@Override
	public List<String> getDeltalist() {
		return new ArrayList<String>();
	}

	@Override
	public SimplePreconditions getPreconditions() {
		return this.preconditions;
	}

	@Override
	public SimplePostconditions getPostconditions() {
		return this.postconditions;
	}

	@Override
	public Idents getChangedStateVariables() {
		return Idents.empty();
	}

	public static SimpleFunction createPostconditionFunction(String postconditionFunctionName, Declarations newDeclarations,
			SimplePostconditions postconditions2, ObjectZClass definingClass) {
		SimpleFunction postconditionFunction = new SimpleFunction(postconditionFunctionName, newDeclarations, AxiomReferences.empty(), definingClass, null);
		postconditionFunction.postconditions = postconditions2;
		return postconditionFunction;
	}

}