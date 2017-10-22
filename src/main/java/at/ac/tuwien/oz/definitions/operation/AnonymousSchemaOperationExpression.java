package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.EmptyPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class AnonymousSchemaOperationExpression extends OperationExpression {

	private SimpleOperation simpleOperation;
	
	public AnonymousSchemaOperationExpression(SimpleOperation simpleOperation) {
		this.simpleOperation = simpleOperation;
		this.addInputParameters(simpleOperation.getInputParameters());
		this.addAuxiliaryParameters(simpleOperation.getAuxiliaryParameters());
		this.addOutputParameters(simpleOperation.getOutputParameters());
	}
	
	public boolean isAnonymousSchemaOperation() {
		return true;
	}

	@Override
	public List<IPromotedOperation> getPromotedOperations() {
		return new ArrayList<IPromotedOperation>();
	}

	@Override
	public List<IPromotedOperation> getPromotedOperationsWithOutputParameter(Variable outputParameter,
			boolean ignoreDecoration) {
		return new ArrayList<IPromotedOperation>();
	}

	@Override
	public boolean isNonDeterministicChoice() {
		return false;
	}

	@Override
	public boolean isCombinable() {
		return false;
	}

	@Override
	public boolean isSequentialComposition() {
		return false;
	}

	@Override
	public IComposablePreconditions getComposablePreconditions() {
		return EmptyPreconditions.empty();
	}

	@Override
	public ICombinableOperation getCombinableOperation() {
		return null;
	}

	@Override
	public IComposablePostconditions getComposablePostconditions() {
		return EmptyPostconditions.empty();
	}

	@Override
	public IComposablePostconditions getComposablePostconditionsWithoutPromotion(Declarations sharedOutputVariables) {
		return EmptyPostconditions.empty();
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
	public void resolveOperationReferences(ParseTreeProperty<ExpressionType> typeTree) {
		// nothing to do here
	}

	@Override
	public void resolveTemplates(ParseTreeProperty<ST> templateTree) {
		// nothing to do here
	}

	@Override
	public boolean isBoolFunction() {
		return this.simpleOperation.isBoolFunction();
	}

	@Override
	public boolean isFunction() {
		return this.simpleOperation.isFunction();
	}

	@Override
	public boolean isChangeOperation() {
		return this.simpleOperation.isChangeOperation();
	}

	@Override
	public List<String> getDeltalist() {
		return this.simpleOperation.getDeltalist();
	}

	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree) {
		this.simpleOperation.createPreAndPostconditions(typeTree);
	}

	@Override
	public IPreconditions getPreconditions() {
		return this.simpleOperation.getPreconditions();
	}

	@Override
	public IPostconditions getPostconditions() {
		return this.simpleOperation.getPostconditions();
	}

	@Override
	public Idents getUsedStateVariables() {
		return this.simpleOperation.getUsedStateVariables();
	}

	@Override
	public Idents getChangedStateVariables() {
		return this.simpleOperation.getChangedStateVariables();
	}
}
