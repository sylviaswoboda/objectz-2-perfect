package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.CompositePostconditionDataCollector;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.CompositePostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public abstract class CombinedOperation extends OperationExpression implements ICombinableOperation{
	
	protected ICombinableOperation leftOperation;
	protected ICombinableOperation rightOperation;
	
	protected ICombinablePostconditions combinablePostconditions;
	protected ICombinablePreconditions combinablePreconditions;
	
	public CombinedOperation(ICombinableOperation left, ICombinableOperation right){
		super();
		
		this.leftOperation = left;
		this.rightOperation = right;

	}
	
	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree) {
		if (this.getLeftOperation().isAnonymousOperation()){
			this.getLeftOperation().resolveOperationReferences(typeTree);
			this.getLeftOperation().createPreAndPostconditions(typeTree);
		}
		if (this.getRightOperation().isAnonymousOperation()){
			this.getRightOperation().resolveOperationReferences(typeTree);
			this.getRightOperation().createPreAndPostconditions(typeTree);
		}
		
		setInputAndOutputParameters();
		
		setPreconditions();
		setPostconditions();
		
		if (hasPreconditions()){
			this.preconditionFunction = new PreconditionFunction(this, this.getCombinablePreconditions(), this.definingClass);
		}
	}
	
	protected abstract void setInputAndOutputParameters();
	protected abstract void setPostconditions();
	protected abstract void setPreconditions();

	@Override
	public List<String> getDeltalist(){
		return new ArrayList<String>();
	}
	
	@Override
	public Idents getUsedStateVariables() {
		Idents usedStateVariables = new Idents();
		usedStateVariables.addAll(leftOperation.getUsedStateVariables());
		usedStateVariables.addAll(rightOperation.getUsedStateVariables());
		return usedStateVariables;
	}

	@Override
	public Idents getChangedStateVariables() {
		Idents changedStateVariables = new Idents();
		changedStateVariables.addAll(leftOperation.getChangedStateVariables());
		changedStateVariables.addAll(rightOperation.getChangedStateVariables());
		return changedStateVariables;
	}

	@Override
	public ICombinablePreconditions getCombinablePreconditions() {
		return this.combinablePreconditions;
	}
	
	@Override
	public IComposablePreconditions getComposablePreconditions() {
		return this.getCombinablePreconditions();
	}

	@Override
	public IPreconditions getPreconditions() {
		return this.getCombinablePreconditions();
	}

	@Override
	public ICombinablePostconditions getCombinablePostconditions() {
		return this.combinablePostconditions;
	}

	@Override
	public IComposablePostconditions getComposablePostconditions() {
		return this.getCombinablePostconditions();
	}
	
	@Override
	public IPostconditions getPostconditions() {
		return this.getCombinablePostconditions();
	}
	
	@Override
	public IComposablePostconditions getComposablePostconditionsWithoutPromotion(Declarations sharedOutputVariables) {
		CompositePostconditionDataCollector collector = new CompositePostconditionDataCollector(this.getCombinablePostconditions());
		collector.eliminateOutputVariable(sharedOutputVariables);
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		return factory.createPostcondition();
	}

	@Override
	public Declarations getSharedOutputVariables() {
		return leftOperation.getOutputParameters().getSharedDeclarations(rightOperation.getOutputParameters());
	}

	@Override
	public boolean isCombinable() {
		return true;
	}

	@Override
	public ICombinableOperation getCombinableOperation() {
		return this;
	}

	public ICombinableOperation getLeftOperation() {
		return leftOperation;
	}

	public ICombinableOperation getRightOperation() {
		return rightOperation;
	}

	@Override
	public boolean isStateVariable(Ident usedVariable) {
		if (leftOperation.isStateVariable(usedVariable)){
			return true;
		} else if (rightOperation.isStateVariable(usedVariable)){
			return true;
		}
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
	
	@Override
	public boolean isNonDeterministicChoice() {
		return false;
	}
	@Override
	public NonDeterministicPreconditions getNonDeterministicPreconditions() {
		return null;
	}

	@Override
	public NonDeterministicPostconditions getNonDeterministicPostconditions() {
		return null;
	}

	@Override
	public boolean isSequentialComposition() {
		return false;
	}
	@Override
	public SequentialPreconditions getSequentialPreconditions() {
		return null;
	}
	@Override
	public SequentialPostconditions getSequentialPostconditions() {
		return null;
	}
	@Override
	public SequentialPostconditions getSequentialPostconditionsWithoutPromotion(Declarations sharedVariables) {
		return null;
	}
	@Override
	public boolean isOperationExpression(){
		return true;
	}

	@Override
	public void resolveOperationReferences(ParseTreeProperty<ExpressionType> typeTree) {
		this.leftOperation.resolveOperationReferences(typeTree);
		this.rightOperation.resolveOperationReferences(typeTree);
		this.setInputAndOutputParameters();
	}

	@Override
	public void resolveTemplates(ParseTreeProperty<ST> templateTree) {
		this.leftOperation.resolveTemplates(templateTree);
		this.rightOperation.resolveTemplates(templateTree);
	}
	


}
