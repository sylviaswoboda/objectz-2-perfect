package at.ac.tuwien.oz.definitions.operation;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.definitions.IScope;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;

public class OperationBuilder {
	
	public OperationBuilder() {
	}

	/**
	 * Operation from same class is called.
	 * Called operation is already resolved from scope
	 * 
	 * @param calledOperation
	 * @return newly created anonymous (has no name and is not added to scope) operation promotion.
	 */
	public IPromotedOperation getAnonymousOperationPromotion(CallerContext callerCtx, String calledOperationName) {
		return new OperationPromotion(callerCtx, calledOperationName);
	}

	@Deprecated
	public IComposableOperation getOperationExpression(String operationName, IComposableOperation promotedOperation, IScope currentScope) {
		promotedOperation.rename(operationName);
		return promotedOperation;
	}

	public SimpleOperation buildOperation(List<String> deltalist, Declarations declarations, 
			AxiomReferences predicates, ObjectZClass definingClass, ParseTree operationCtx) {
		String operationName = Operation.createAnonymousName();
		return buildOperation(operationName, deltalist, declarations, predicates, definingClass, operationCtx);
	}

	public SimpleOperation buildOperation(String operationName, List<String> deltalist, 
			Declarations declarations, AxiomReferences predicates, ObjectZClass definingClass, ParseTree operationCtx) {
		
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(
				!deltalist.isEmpty(), declarations.hasOutputVariables(), predicates.hasPreconditions(), 
				predicates.hasPostconditions());
		
		SimpleOperation op = null;
		
		if (determinator.getOperationType() == null){
			throw new RuntimeException("Found impossible Operation at operation: " + operationName);
		}
		
		switch(determinator.getOperationType()){
			case BOOL_FUNCTION:
				op = new SimpleBoolFunction(operationName, declarations, predicates, definingClass, operationCtx);
				break;
			case FUNCTION:
				op = new SimpleFunction(operationName, declarations, predicates, definingClass, operationCtx);
				break;
			case CHANGE_OPERATION:
				op = new SimpleChangeOperation(operationName, deltalist, declarations, predicates, definingClass, operationCtx);
				break;
			case DECLARATION_SCHEMA:
				op = new SimpleDeclarationSchema(operationName, declarations, definingClass, operationCtx);
		}
		return op;
	}

}
