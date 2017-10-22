package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICompositionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class SequentialComposition extends OperationExpression{

	private IComposableOperation leftOperation;
	private IComposableOperation rightOperation;
	
	private SequentialPostconditions sequentialPostconditions;
	private SequentialPreconditions  sequentialPreconditions;
	
	private Declarations communicationVariablesLeftToRight;
	private Declarations sharedVariables;
	private ICompositionFactory factory;

	public SequentialComposition(IComposableOperation left, IComposableOperation right) {
		super();
		this.factory = PostconditionFactory.getInstance();
		this.leftOperation = left;
		this.rightOperation = right;
	}
	
	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree) {
		if (this.leftOperation.isAnonymousOperation()){
			this.leftOperation.resolveOperationReferences(typeTree);
			this.leftOperation.createPreAndPostconditions(typeTree);
		}
		if (this.rightOperation.isAnonymousOperation()){
			this.rightOperation.resolveOperationReferences(typeTree);
			this.rightOperation.createPreAndPostconditions(typeTree);
		}

		setInputAndOutputParameters();
		
		setPreconditions();
		setPostconditions();

		if (hasPreconditions()){
    		this.preconditionFunction = new PreconditionFunction(this, this.sequentialPreconditions, this.definingClass);
		}
		
	}
	
	@Override
	public boolean isOperationExpression() {
		return true;
	}

	private void setPostconditions() {
		if (isBoolFunction()){
			this.sequentialPostconditions = null;
		} else {
			Context context = Context.SCHEMA;
			if (isFunction()){
				context = Context.FUNCTION;
			}
			this.sequentialPostconditions = factory.sequentiallyCompose(leftOperation, rightOperation, this.getCommunicationVariables(), this.getSharedOutputVariables(), context);
		}
	}

	private void setPreconditions() {
		PreconditionFactory preconditionFactory = PreconditionFactory.getInstance();
		
		this.sequentialPreconditions = preconditionFactory.sequentiallyCompose(leftOperation, rightOperation, getCommunicationVariables());
	}

	private Declarations getCommunicationVariables() {
		return communicationVariablesLeftToRight;
	}

	protected void setInputAndOutputParameters() {
		this.communicationVariablesLeftToRight = new Declarations();
		this.sharedVariables = new Declarations();
		
		for(Variable outputLeft: this.leftOperation.getOutputParameters().asList()){
			if (rightOperation.getInputParameters().containsIgnoreDecoration(outputLeft)){
				communicationVariablesLeftToRight.add(outputLeft.getUndecoratedCopy());
			} else {
				this.addOutputParameter(outputLeft);
			}
		}
		for(Variable outputRight: rightOperation.getOutputParameters().asList()){
			this.addOutputParameter(outputRight);
		}
		
		for(Variable inputLeft: leftOperation.getInputParameters().asList()){
			this.addInputParameter(inputLeft);
		}
		
		for(Variable inputRight: rightOperation.getInputParameters().asList()){
			if (!communicationVariablesLeftToRight.containsIgnoreDecoration(inputRight)){
				this.addInputParameter(inputRight);
			}
		}
		if (isFunction()){
			this.sharedVariables.addAll(leftOperation.getOutputParameters().getSharedDeclarations(rightOperation.getOutputParameters()));
		}
	}
	@Override
	public List<String> getDeltalist() {
		return new ArrayList<String>();
	}

	@Override
	public boolean isBoolFunction() {
		return this.leftOperation.isBoolFunction() && this.rightOperation.isBoolFunction();
	}


	@Override
	public boolean isFunction() {
		return (this.leftOperation.isFunction() && !this.rightOperation.isChangeOperation()) || 
				(this.rightOperation.isFunction() && !this.leftOperation.isChangeOperation());
	}


	@Override
	public boolean isChangeOperation() {
		return this.leftOperation.isChangeOperation() || this.rightOperation.isChangeOperation();
	}


	@Override
	public boolean isNonDeterministicChoice() {
		return false;
	}


	@Override
	public NonDeterministicPostconditions getNonDeterministicPostconditions() {
		return null;
	}

	@Override
	public IPreconditions getPreconditions() {
		return this.sequentialPreconditions;
	}

	@Override
	public IComposablePreconditions getComposablePreconditions() {
		return this.sequentialPreconditions;
	}


	@Override
	public IPostconditions getPostconditions() {
		return getComposablePostconditions();
	}

	@Override
	public IComposablePostconditions getComposablePostconditions() {
		if (this.sequentialPostconditions == null){
			return EmptyPostconditions.empty();
		}
		return this.sequentialPostconditions;
	}

	@Override
	public IComposablePostconditions getComposablePostconditionsWithoutPromotion(Declarations sharedOutputVariables) {
		return this.getComposablePostconditions().getWithoutPromotions(sharedOutputVariables);
	}

	@Override
	public SequentialPostconditions getSequentialPostconditions() {
		if (this.sequentialPostconditions.isSequential()){
			return this.sequentialPostconditions.getSequentialPostconditions();
		}
		return null;
	}

	@Override
	public SequentialPostconditions getSequentialPostconditionsWithoutPromotion(Declarations sharedVariables) {
		if (this.sequentialPostconditions.isSequential()){
			IComposablePostconditions postconditions = this.sequentialPostconditions.getWithoutPromotions(sharedVariables);
			if (postconditions.isSequential()){
				return postconditions.getSequentialPostconditions();
			}
		}
		return null;
	}


	@Override
	public Idents getUsedStateVariables() {
		Idents usedStateVariables = new Idents();
		
		usedStateVariables.addAll(this.leftOperation.getUsedStateVariables());
		usedStateVariables.addAll(this.rightOperation.getUsedStateVariables());
		
		return usedStateVariables;
	}

	@Override
	public Idents getChangedStateVariables() {
		Idents changedStateVariable = new Idents();
		
		changedStateVariable.addAll(this.leftOperation.getChangedStateVariables());
		changedStateVariable.addAll(this.rightOperation.getChangedStateVariables());
		
		return changedStateVariable;
	}

	@Override
	public ICombinableOperation getCombinableOperation() {
		return null;
	}

	@Override
	public boolean isCombinable() {
		return false;
	}

	@Override
	public List<IPromotedOperation> getPromotedOperations() {
		ArrayList<IPromotedOperation> promotedOperations = new ArrayList<IPromotedOperation>();
		promotedOperations.addAll(leftOperation.getPromotedOperations());
		promotedOperations.addAll(rightOperation.getPromotedOperations());
		return promotedOperations;
	}

	@Override
	public List<IPromotedOperation> getPromotedOperationsWithOutputParameter(Variable outputParameter, boolean ignoreDecoration){
		List<IPromotedOperation> promotionsWithParameter = new ArrayList<IPromotedOperation>();
		promotionsWithParameter.addAll(leftOperation.getPromotedOperationsWithOutputParameter(outputParameter, ignoreDecoration));
		promotionsWithParameter.addAll(rightOperation.getPromotedOperationsWithOutputParameter(outputParameter, ignoreDecoration));
		return promotionsWithParameter;
	}
	
	public Declarations getSharedOutputVariables() {
		return this.sharedVariables;
	}

	@Override
	public boolean isSequentialComposition() {
		return true;
	}


	@Override
	public SequentialPreconditions getSequentialPreconditions() {
		return this.sequentialPreconditions;
	}

	@Override
	public NonDeterministicPreconditions getNonDeterministicPreconditions() {
		return null;
	}

	@Override
	public void resolveOperationReferences(ParseTreeProperty<ExpressionType> typeTree) {
		this.leftOperation.resolveOperationReferences(typeTree);
		this.rightOperation.resolveOperationReferences(typeTree);
	}
	@Override
	public void resolveTemplates(ParseTreeProperty<ST> templateTree) {
		this.leftOperation.resolveTemplates(templateTree);
		this.rightOperation.resolveTemplates(templateTree);
	}

}
