package at.ac.tuwien.oz.definitions.operation;

import org.antlr.v4.runtime.tree.ParseTree;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.axioms.Axioms;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.SimplePostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public abstract class SimpleOperation extends Operation {

	private Declarations originalDeclarations;
	private AxiomReferences preconditionReferences;
	private AxiomReferences postconditionReferences;
	
	private Idents usedStateVariables;

	public SimpleOperation(String name, Declarations originalDeclarations, AxiomReferences originalPredicates, ObjectZClass definingClass, ParseTree operationCtx) {
		super(name, definingClass, operationCtx);
		
		this.originalDeclarations = new Declarations();
		this.preconditionReferences = new AxiomReferences();
		this.postconditionReferences = new AxiomReferences();
		this.usedStateVariables = new Idents();
		
		
		if (originalDeclarations != null){
			this.originalDeclarations.addAll(originalDeclarations);
			
			addInputParameters(originalDeclarations.getInputVariables());
			addAuxiliaryParameters(originalDeclarations.getAuxiliaryVariables());
			addOutputParameters(originalDeclarations.getOutputVariables());
		}

		if (originalPredicates != null){
			this.preconditionReferences.addAll(originalPredicates.getPreconditionReferences());
			this.postconditionReferences.addAll(originalPredicates.getPostconditionReferences());
		}
		
		setUsedStateVariables();
	}

	@Override
	public Idents getUsedStateVariables() {
		return this.usedStateVariables;
	}

	@Override
	public AxiomReferences getPreconditionAxiomReferences(){
		return this.preconditionReferences;
	}
	
	@Override
	public AxiomReferences getPostconditionAxiomReferences(){
		return this.postconditionReferences;
	}
	
	@Override
	public boolean isSimpleOperation(){
		return true;
	}
	
	protected Axioms getOriginalPreconditions() {
		return this.preconditionReferences.getOriginalPreconditions();
	}
	protected Axioms getOriginalPostconditions() {
		return this.postconditionReferences.getOriginalPostconditions();
	}

	protected void setUsedStateVariables() {
		for (Ident usedIdent: postconditionReferences.getUsedIdentifiers()){
			if (!usedIdent.isInputIdent() && !usedIdent.isOutputIdent() && isStateVariable(usedIdent)){
				usedStateVariables.add(usedIdent);
			}
		}
	}
	
	protected SimplePreconditions getVariableTypePreconditions(){
		PreconditionFactory preconditionFactory = PreconditionFactory.getInstance();
		SimplePreconditions preconditions = new SimplePreconditions();
		for (Variable v: getInputParameters()){
			if (v.getExpressionType().isTemplateType()){
				preconditions.add(preconditionFactory.createVariableTypePrecondition(v));
			}
		}
		return preconditions;

	}
	protected SimplePostconditions getVariableTypePostconditions(){
		PostconditionFactory postconditionFactory = PostconditionFactory.getInstance();
		SimplePostconditions postconditions = new SimplePostconditions();
		for (Variable v: getAuxiliaryParameters()){
			if (v.getExpressionType().isTemplateType()){
				postconditions.add(postconditionFactory.createVariableTypePostcondition(v, Idents.empty()));
			}
		}
		for (Variable v: getOutputParameters()){
			if (v.getExpressionType().isTemplateType()){
				postconditions.add(postconditionFactory.createVariableTypePostcondition(v, Idents.empty()));
			}
		}
		return postconditions;

	}

}
