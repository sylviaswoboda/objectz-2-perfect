package at.ac.tuwien.oz.definitions.operation;

import org.antlr.v4.runtime.tree.ParseTree;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.BaseDefinition;
import at.ac.tuwien.oz.definitions.DefinitionTable;
import at.ac.tuwien.oz.definitions.IDefinition;
import at.ac.tuwien.oz.definitions.IScope;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public abstract class Operation extends BaseDefinition implements IOperation, IScope {

	private static int counter = 0;
	
	private Declarations inputParameters;
	private Declarations auxiliaryParameters;
	private Declarations outputParameters;
	
	private DefinitionTable<Variable> symbolTable;
	
	protected PreconditionFunction preconditionFunction;
	
	protected ObjectZClass definingClass;
	private ST template;

	private boolean includePostconditionFunction;
	private boolean opaque;
	
	public Operation(String name, ObjectZClass definingClass, ParseTree operationCtx) {
		super(name, operationCtx);
		this.definingClass = definingClass;
		initFields();
		includePostconditionFunction = false;
		opaque = false;
	}
	public Operation(ParseTree operationCtx){
		super(operationCtx);
		initFields();
	}

	private void initFields() {
		this.symbolTable = new DefinitionTable<>();
		this.inputParameters = new Declarations();
		this.auxiliaryParameters = new Declarations();
		this.outputParameters = new Declarations();
	}
	public String getName(){
		return this.name;
	}
	
	@Override
	public Declarations getInputParameters() {
		return this.inputParameters;
	}

	@Override
	public Declarations getAuxiliaryParameters() {
		return this.auxiliaryParameters;
	}

	@Override
	public Declarations getOutputParameters() {
		return this.outputParameters;
	}
	@Override
	public Declarations getModifiableInputParameters() {
		return Declarations.empty();
	}

	@Override
	public AxiomReferences getPreconditionAxiomReferences(){
		return AxiomReferences.empty();
	}
	@Override
	public AxiomReferences getPostconditionAxiomReferences(){
		return AxiomReferences.empty();
	}
	
	protected void addInputParameters(Declarations inputVariables) {
		if (inputVariables != null){
			this.symbolTable.addAll(inputVariables.asList());
			this.inputParameters.addAll(inputVariables);
		}
	}
	protected void addInputParameter(Variable inputVariable) {
		if (inputVariable != null){
			this.symbolTable.add(inputVariable);
			this.inputParameters.add(inputVariable);
		}
	}
	protected void addOutputParameters(Declarations outputVariables) {
		if (outputVariables != null){
			this.outputParameters.addAll(outputVariables);
			// TODO symbol table??
		}
	}
	protected void addOutputParameter(Variable outputVariable) {
		if (outputVariable != null){
			this.outputParameters.add(outputVariable);
		}
	}
	protected void addAuxiliaryParameters(Declarations auxiliaryVariables) {
		this.auxiliaryParameters.addAll(auxiliaryVariables);
	}

	@Override
	public boolean hasPreconditions() {
		IPreconditions preconditions = getPreconditions();
		if (preconditions == null)
			return false;
		return !preconditions.isEmpty();
	}
	
	@Override
	public PreconditionFunction getPreconditionFunction() {
		return preconditionFunction;
	}
	
	@Override
	public PreconditionFunctionCall getPreconditionFunctionCall() {
		if (preconditionFunction != null){
			return preconditionFunction.getCall();
		}
		return null;
	}

	public String getPreconditionFunctionName() {
		if (isBoolFunction()){
			return this.getName();
		} else {
			return this.getName() + "_prec";
		}
	}
	@Override
	public String getPostconditionFunctionName() {
		this.setIncludePostconditionFunction(true);
		return this.name + "_post";
	}

	
	/**
	 * provides the name for a previously unknown operation 
	 * could also be used to update the name and propagate the change correctly
	 * 
	 * @param operationName
	 */
	public void rename(String operationName) {
		this.name = operationName;
		resetPreconditionFunctionName();
	}

	private void resetPreconditionFunctionName() {
		if (preconditionFunction != null){
			preconditionFunction.resetName();
		}
	}
	
	@Override
	public boolean isStateVariable(Ident usedVariable) {
		return this.definingClass.isStateVariable(usedVariable);
	}
	
	@Override
	public boolean hasInputVariables(Declarations inputVariables){
		for(Variable inputVar: inputVariables.asList()){
			if (inputParameters.contains(inputVar)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasInputVariable(Variable variable) {
		return inputParameters.contains(variable);
	}

	@Override
	public boolean hasOutputVariable(Variable outputVariable){
		return hasOutputVariable(outputVariable, false);
	}

	@Override
	public boolean hasOutputVariable(Variable outputVariable, boolean ignoreDecoration) {
		if (ignoreDecoration){
			return outputParameters.containsIgnoreDecoration(outputVariable);
		} else {
			return outputParameters.contains(outputVariable);
		}
	}
	public static String createAnonymousName() {
		counter++;
		return "anonymous_" + counter;
	}
	
	@Override
	public boolean isAnonymousOperation() {
		return this.name == null || this.name.startsWith("anonymous");
	}


	
	@Override
	public boolean isSimpleOperation(){
		return false;
	}
	@Override
	public boolean isOperationExpression(){
		return false;
	}

	public void setTemplate(ST template){
		this.template=template;
	}
	
	@Override
	public ST getTemplate() {
		return template;
	}
	
	public ObjectZClass getDefiningClass(){
		return definingClass;
		
	}
	public IDefinition resolve(Ident id) {
		return symbolTable.get(id);
	}
	
    public IScope getEnclosingScope(){
    	return this.definingClass;
    }
    @Override
	public boolean includePostconditionFunction(){
    	return this.includePostconditionFunction;
    }
    @Override
	public void setIncludePostconditionFunction(boolean include){
    	this.includePostconditionFunction = include;
    }
    @Override
	public boolean isOpaque(){
    	return this.opaque;
    }
    @Override
	public void setOpaque(boolean opaque){
    	this.opaque = opaque;
    }
	@Override
	public Operation getPostconditionFunction(){
		return null;
	}


}
