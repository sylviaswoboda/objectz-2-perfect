package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.SimpleFunctionPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.SimplePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.SimpleSchemaPostcondition;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.translator.templates.StringTemplateCloner;

public class SimpleChangeOperation extends SimpleOperation {

	private List<String> deltalist;
	private Idents changedStateVariables;
	
	private SimplePreconditions  preconditions;
	private SimplePostconditions postconditions;
	
	public SimpleChangeOperation(String operationName, List<String> deltalist, Declarations originalDeclarations, 
			AxiomReferences originalPredicates, ObjectZClass objectZClass, 
			ParseTree operationCtx){
		super(operationName, originalDeclarations, originalPredicates, objectZClass, operationCtx);
		
		this.deltalist = new ArrayList<String>();
		this.changedStateVariables = new Idents();
		
		if (deltalist != null){
			this.deltalist.addAll(deltalist);
			
			for (String changedVar: this.deltalist){
				changedStateVariables.add(new Ident(changedVar));
			}
		}
	}

	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree){
		PreconditionFactory preconditionFactory = PreconditionFactory.getInstance();
		PostconditionFactory postconditionFactory = PostconditionFactory.getInstance();
		
		preconditions = new SimplePreconditions();
		postconditions = new SimplePostconditions();

		List<ST> implicitPreconditions = new ArrayList<ST>();

		for(Axiom originalPrecondition: super.getOriginalPreconditions().asList()){
			IPrecondition simplePrecondition = preconditionFactory.createSimplePrecondition(originalPrecondition);
			preconditions.add(simplePrecondition);
		}
		
		for (Axiom originalPredicate : super.getOriginalPostconditions().asList()) {
			SimpleSchemaPostcondition schemaPostcondition = postconditionFactory.createSchemaPredicate(originalPredicate, super.getOutputParameters());
			postconditions.add(schemaPostcondition);
			
			// add existential quantification for all output variables combined with the used postcondition predicates
			StringTemplateCloner cloner = new StringTemplateCloner();
			ST postconditionClone = cloner.clone(originalPredicate.getPredicate());
			
			implicitPreconditions.add(postconditionClone);

		}
		this.preconditions.addAll(getVariableTypePreconditions().asList());

		
		Declarations deltaVariables = new Declarations();
		
		for (String name: deltalist){
			Variable deltaVariable = this.definingClass.resolveVariable(new Ident(name));
			if (deltaVariable != null){
				deltaVariables.add(deltaVariable);
			}
		}
		
		preconditions.add(preconditionFactory.createImplicitSchemaPreconditions(deltaVariables, this.getOutputParameters(), implicitPreconditions));
		
		if (hasPreconditions()){
			preconditionFunction = new PreconditionFunction(this, preconditions, this.definingClass);
		}
	}
	
	@Override
	public boolean isBoolFunction() {
		return false;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

	@Override
	public boolean isChangeOperation() {
		return true;
	}

	@Override
	public List<String> getDeltalist() {
		return this.deltalist;
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
		return this.changedStateVariables;
	}
	
	@Override
	public Operation getPostconditionFunction(){
		Declarations newDeclarations = new Declarations();
		newDeclarations.addAll(this.getInputParameters());
		newDeclarations.addAll(this.getOutputParameters());
		
		SimplePostconditions simplePostconditions = new SimplePostconditions();
		for (Axiom originalPredicate: super.getOriginalPostconditions().asList()){
			boolean include = true;
			for (Ident usedIdentifier: originalPredicate.getIdentifiers()){
				if (usedIdentifier.isPrimedIdent()){
					include = false;
				}
			}
			if (include){
				SimpleFunctionPostcondition functionPostcondition = PostconditionFactory.getInstance()
						.createOutputVariablePredicate(originalPredicate, this.getOutputParameters());
				simplePostconditions.add(functionPostcondition);
			}
			
		}
		
		return SimpleFunction.createPostconditionFunction(getPostconditionFunctionName(), newDeclarations, simplePostconditions, definingClass);
	}
}
