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
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
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
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class NonDeterministicChoice extends OperationExpression {

	private IComposableOperation left;
	private IComposableOperation right;
	
	private NonDeterministicPreconditions preconditions;
	private NonDeterministicPostconditions postconditions;
	
	public NonDeterministicChoice(IComposableOperation left, IComposableOperation right) {
		super();
		this.left = left;
		this.right = right;
		this.setOpaque(true);
	}
	
	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType>typeTree) {
		if (this.left.isAnonymousOperation()){
			this.left.resolveOperationReferences(typeTree);
			this.left.createPreAndPostconditions(typeTree);
		}
		if (this.right.isAnonymousOperation()){
			this.right.resolveOperationReferences(typeTree);
			this.right.createPreAndPostconditions(typeTree);
		}
		this.setInputAndOutputParameters();

		setPreconditions();
		setPostconditions();

		if (hasPreconditions()){
    		this.preconditionFunction = new PreconditionFunction(this, this.getPreconditions(), this.definingClass);
		}
	}
	
	@Override
	public boolean isOperationExpression() {
		return true;
	}
	
	@Override
	public void resolveOperationReferences(ParseTreeProperty<ExpressionType> typeTree) {
		this.left.resolveOperationReferences(typeTree);
		this.right.resolveOperationReferences(typeTree);
		
	}
	protected void setPreconditions(){
		PreconditionFactory factory = PreconditionFactory.getInstance();
		this.preconditions = factory.nondeterministicallyChoose(left, right);
	}
	 
	protected void setPostconditions(){
		PostconditionFactory factory = PostconditionFactory.getInstance();
		if (isFunction()){
			this.postconditions = factory.nondeterministicallyChoose(left, right, Context.FUNCTION);
		} else if (isChangeOperation()){
			this.postconditions = factory.nondeterministicallyChoose(left, right, Context.SCHEMA);
		} else {
			throw new ObjectZToPerfectTranslationException("Non Deterministically Choice combination of two bool functions does not result in a NonDeterminstic Choice");
		}
	}

	protected void setInputAndOutputParameters() {
		if (left.getInputParameters().size() != right.getInputParameters().size()){
			throw new ObjectZToPerfectTranslationException("Number of input parameters do not match");
		}
		for (Variable inputVar: right.getInputParameters()){
			if (!left.getInputParameters().contains(inputVar)){
				throw new ObjectZToPerfectTranslationException("Input variable " + inputVar + " is missing in left operation");
			}
		}

		if (left.getOutputParameters().size() != right.getOutputParameters().size()){
			throw new ObjectZToPerfectTranslationException("Number of output parameters do not match");
		}
		for (Variable outputVar: right.getOutputParameters()){
			if (!left.getOutputParameters().contains(outputVar)){
				throw new ObjectZToPerfectTranslationException("Output variable " + outputVar + " is missing in left operation");
			}
		}

		this.addInputParameters(left.getInputParameters());
		this.addOutputParameters(left.getOutputParameters());
	}

	
	@Override
	public IComposablePreconditions getComposablePreconditions() {
		return this.getNonDeterministicPreconditions();
	}

	@Override
	public boolean isChangeOperation() {
		return left.isChangeOperation() || right.isChangeOperation();
	}

	@Override
	public boolean isFunction() {
		return left.isFunction() && !right.isChangeOperation() ||
				right.isFunction() && !left.isChangeOperation();
	}

	@Override
	public boolean isBoolFunction() {
		return left.isBoolFunction() && right.isBoolFunction();
	}

	@Override
	public List<String> getDeltalist() {
		return new ArrayList<String>();
	}

	@Override
	public Idents getUsedStateVariables() {
		Idents usedStateVariables = new Idents();
		usedStateVariables.addAll(left.getUsedStateVariables());
		usedStateVariables.addAll(right.getUsedStateVariables());
		return usedStateVariables;
	}

	@Override
	public Idents getChangedStateVariables() {
		Idents changedStateVariables = new Idents();
		changedStateVariables.addAll(left.getChangedStateVariables());
		changedStateVariables.addAll(right.getChangedStateVariables());
		return changedStateVariables;
	}

	@Override
	public List<IPromotedOperation> getPromotedOperations() {
		ArrayList<IPromotedOperation> promotedOperations = new ArrayList<IPromotedOperation>();
		promotedOperations.addAll(left.getPromotedOperations());
		promotedOperations.addAll(right.getPromotedOperations());
		return promotedOperations;
	}

	@Override
	public List<IPromotedOperation> getPromotedOperationsWithOutputParameter(Variable outputParameter,
			boolean ignoreDecoration) {
		List<IPromotedOperation> promotionsWithParameter = new ArrayList<IPromotedOperation>();
		promotionsWithParameter.addAll(left.getPromotedOperationsWithOutputParameter(outputParameter, ignoreDecoration));
		promotionsWithParameter.addAll(right.getPromotedOperationsWithOutputParameter(outputParameter, ignoreDecoration));
		return promotionsWithParameter;
	}

	@Override
	public IPreconditions getPreconditions() {
		return this.getNonDeterministicPreconditions();
	}

	@Override
	public IPostconditions getPostconditions() {
		return this.getNonDeterministicPostconditions();
	}

	@Override
	public boolean isCombinable() {
		return false;
	}

	@Override
	public ICombinableOperation getCombinableOperation() {
		return null;
	}

	@Override
	public IComposablePostconditions getComposablePostconditions() {
		return this.getNonDeterministicPostconditions();
	}

	@Override
	public IComposablePostconditions getComposablePostconditionsWithoutPromotion(Declarations sharedOutputVariables) {
		return null;
	}

	@Override
	public boolean isNonDeterministicChoice() {
		return true;
	}

	@Override
	public NonDeterministicPreconditions getNonDeterministicPreconditions() {
		return this.preconditions;
	}
	public NonDeterministicPostconditions getNonDeterministicPostconditions() {
		return this.postconditions;
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
	public SequentialPostconditions getSequentialPostconditionsWithoutPromotion(Declarations outputVariables) {
		return null;
	}
	@Override
	public void resolveTemplates(ParseTreeProperty<ST> templateTree) {
		this.left.resolveTemplates(templateTree);
		this.right.resolveTemplates(templateTree);
	}
	
}
