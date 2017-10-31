package at.ac.tuwien.oz.definitions.operation;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICompositionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;

public class OperationConjunction extends CombinedOperation{

	public OperationConjunction(ICombinableOperation leftOperation, ICombinableOperation rightOperation) {
		super(leftOperation, rightOperation);
	}

	protected void setInputAndOutputParameters() {
		this.addInputParameters(leftOperation.getInputParameters());
		this.addInputParameters(rightOperation.getInputParameters());
		this.addOutputParameters(leftOperation.getOutputParameters());
		this.addOutputParameters(rightOperation.getOutputParameters());
	}

	@Override
	public boolean isBoolFunction() {
		return leftOperation.isBoolFunction() && rightOperation.isBoolFunction();
	}

	@Override
	public boolean isFunction() {
		return leftOperation.isFunction() && !rightOperation.isChangeOperation() ||
			!leftOperation.isChangeOperation() && rightOperation.isFunction();
	}

	@Override
	public boolean isChangeOperation() {
		return leftOperation.isChangeOperation() || rightOperation.isChangeOperation();
	}

	@Override
	protected void setPostconditions() {
		Declarations sharedOutputVariables = getSharedOutputVariables();
		ICompositionFactory factory = PostconditionFactory.getInstance();
		this.combinablePostconditions = factory.conjoin(leftOperation.getCombinablePostconditions(), 
				rightOperation.getCombinablePostconditions(), sharedOutputVariables);
	}

	@Override
	protected void setPreconditions() {
		PreconditionFactory factory = PreconditionFactory.getInstance();
		this.combinablePreconditions = factory.conjoin(leftOperation, rightOperation, getSharedOutputVariables());
	}
}
