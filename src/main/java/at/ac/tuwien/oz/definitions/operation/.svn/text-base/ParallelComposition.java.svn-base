package at.ac.tuwien.oz.definitions.operation;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICompositionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;

public class ParallelComposition extends CombinedOperation{
	private Declarations communicationVariablesLeftToRight;
	private Declarations communicationVariablesRightToLeft;

	private boolean isAssociative;
	
	public ParallelComposition(ICombinableOperation leftOperation, ICombinableOperation rightOperation, boolean associative) {
		super(leftOperation, rightOperation);
		this.isAssociative = associative;
	}
	
	@Override
	protected void setInputAndOutputParameters() {
		communicationVariablesLeftToRight = new Declarations();
		communicationVariablesRightToLeft = new Declarations();
		
		if (isAssociative()){
			for(Variable outputLeft: leftOperation.getOutputParameters().asList()){
				if (rightOperation.getInputParameters().containsIgnoreDecoration(outputLeft)){
					communicationVariablesLeftToRight.add(outputLeft.getUndecoratedCopy());
				}
				this.addOutputParameter(outputLeft);
			}
			for(Variable outputRight: rightOperation.getOutputParameters().asList()){
				if (leftOperation.getInputParameters().containsIgnoreDecoration(outputRight)){
					Variable undecoratedCopy = outputRight.getUndecoratedCopy();
					communicationVariablesRightToLeft.add(undecoratedCopy);
				} 
				this.addOutputParameter(outputRight);
			}
		} else {
			for(Variable outputLeft: leftOperation.getOutputParameters().asList()){
				if (rightOperation.getInputParameters().containsIgnoreDecoration(outputLeft)){
					communicationVariablesLeftToRight.add(outputLeft.getUndecoratedCopy());
				} else {
					this.addOutputParameter(outputLeft);
				}
			}
			for(Variable outputRight: rightOperation.getOutputParameters().asList()){
				if (leftOperation.getInputParameters().containsIgnoreDecoration(outputRight)){
					Variable undecoratedCopy = outputRight.getUndecoratedCopy();
					communicationVariablesRightToLeft.add(undecoratedCopy);
				} else {
					this.addOutputParameter(outputRight);
				}
			}
		}	
		
		for(Variable inputLeft: leftOperation.getInputParameters().asList()){
			if (!communicationVariablesRightToLeft.containsIgnoreDecoration(inputLeft)){
				this.addInputParameter(inputLeft);
			}
		}

		for(Variable inputRight: rightOperation.getInputParameters().asList()){
			if (!communicationVariablesLeftToRight.containsIgnoreDecoration(inputRight)){
				this.addInputParameter(inputRight);
			}
		}
	}
	
	private boolean isAssociative() {
		return this.isAssociative;
	}

	@Override
	public boolean isBoolFunction() {
		return leftOperation.isBoolFunction() && rightOperation.isBoolFunction() ||
			   leftOperation.isFunction() && !rightOperation.isChangeOperation() && getOutputParameters().isEmpty() ||
		   	   !leftOperation.isChangeOperation() && rightOperation.isFunction() && getOutputParameters().isEmpty();
	}

	@Override
	public boolean isFunction() {
		return leftOperation.isFunction() && !rightOperation.isChangeOperation() && !getOutputParameters().isEmpty() ||
			   !leftOperation.isChangeOperation() && rightOperation.isFunction() && !getOutputParameters().isEmpty();
	}

	@Override
	public boolean isChangeOperation() {
		return leftOperation.isChangeOperation() || rightOperation.isChangeOperation();
	}

	/**
	 * communication variables (without decoration)
	 * 
	 * @return
	 */
	public Declarations getCommunicationVariables() {
		Declarations allCommunicationVariables = new Declarations();
		allCommunicationVariables.addAll(communicationVariablesLeftToRight);
		allCommunicationVariables.addAll(communicationVariablesRightToLeft);
		return allCommunicationVariables;
	}

	public Declarations getCommunicationVariablesLeftToRight() {
		return this.communicationVariablesLeftToRight;
	}


	public Declarations getCommunicationVariablesRightToLeft() {
		return this.communicationVariablesRightToLeft;
	}

	@Override
	protected void setPostconditions() {
		Declarations sharedOutputVariables = getSharedOutputVariables();
		Declarations communicationVariables = getCommunicationVariables();
		ICompositionFactory factory = PostconditionFactory.getInstance();
		
		this.combinablePostconditions = factory.compose(leftOperation.getCombinablePostconditions(), 
				rightOperation.getCombinablePostconditions(), communicationVariables, sharedOutputVariables, isAssociative);
	}


	@Override
	protected void setPreconditions() {
		PreconditionFactory preconditionFactory = PreconditionFactory.getInstance();
		
		this.combinablePreconditions = preconditionFactory.compose(leftOperation, rightOperation, getCommunicationVariables(), getSharedOutputVariables());
//		if (getCommunicationVariables().isEmpty()){
////			this.combinablePreconditions = preconditionFactory.getPreconditionConjunction(leftOperation, rightOperation, getSharedOutputVariables());
//		} else {
//			this.com
//			this.combinablePreconditions = preconditionFactory.getPreconditionWithCommunication(leftOperation, rightOperation, 
//					getCommunicationVariablesLeftToRight(), getCommunicationVariablesRightToLeft(), getSharedOutputVariables());
//		}
	}

	
}
