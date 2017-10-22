package at.ac.tuwien.oz.definitions.operation;

public class OperationTypeDeterminator {

	private boolean hasDeltaVariables;
	private boolean hasOutputVariables;
	private boolean hasPreconditions;
	private boolean hasPostconditions;
	
	
	private OperationTypeDeterminator(){
	}
	
	public OperationTypeDeterminator(boolean hasDeltaVariables, boolean hasOutputVariables, boolean hasPreconditions, boolean hasPostconditions){
		this();
		this.hasDeltaVariables  = hasDeltaVariables;
		this.hasOutputVariables = hasOutputVariables;
		this.hasPreconditions   = hasPreconditions;
		this.hasPostconditions  = hasPostconditions;
	}

	
	public OperationType getOperationType(){
		if (isBoolFunctionOperation()){
			return OperationType.BOOL_FUNCTION;
		} else if (isFunctionOperation()){
			return OperationType.FUNCTION;
		} else if (isChangeOperation()){
			return OperationType.CHANGE_OPERATION;
		} else if (isDeclarationSchema()){
			return OperationType.DECLARATION_SCHEMA;
		}
		return null;
	}
	
	private  boolean isChangeOperation() {
		return hasDeltaVariables && hasPostconditions;
	}

	private boolean isFunctionOperation() {
		return !hasDeltaVariables && hasOutputVariables && hasPostconditions;
	}

	private boolean isBoolFunctionOperation() {
		return !hasDeltaVariables && !hasOutputVariables && !hasPostconditions && hasPreconditions;
	}
	
	private boolean isDeclarationSchema(){
		return !hasPreconditions && ! hasPostconditions && !hasDeltaVariables;
	}
}
