package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePrecondition;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class ScopeEnrichment extends OperationExpression {
	
	private enum ScopeEnrichmentType {
		INPUT,
		AUXILIARY
	};
	
	private OperationExpression anonymousSchemaOpExpression;
	private OperationExpression opExpression;
	private ScopeEnrichmentType type;
	
	private IPreconditions preconditions;
	private IPostconditions postconditions;
	
	private Declarations modifiableInputParameters;
	
	public ScopeEnrichment(OperationExpression anonymousSchemaOpExpression, OperationExpression opExpression){
		super();
		this.modifiableInputParameters = new Declarations();
		
		this.anonymousSchemaOpExpression = anonymousSchemaOpExpression;
		this.opExpression = opExpression;
		
		if (!anonymousSchemaOpExpression.getInputParameters().isEmpty()){
			this.type = ScopeEnrichmentType.INPUT;
		} else if (!anonymousSchemaOpExpression.getAuxiliaryParameters().isEmpty()){
			this.type = ScopeEnrichmentType.AUXILIARY;
		}
	}
	
	@Override
	public void resolveOperationReferences(ParseTreeProperty<ExpressionType> typeTree) {
		this.opExpression.resolveOperationReferences(typeTree);
		this.setParameters();
	}
	
	private void setParameters() {
		if (this.type == ScopeEnrichmentType.INPUT){
			this.addInputParameters(this.anonymousSchemaOpExpression.getInputParameters());
			this.modifiableInputParameters.addAll(this.anonymousSchemaOpExpression.getInputParameters());
		} else if (this.type == ScopeEnrichmentType.AUXILIARY){
			this.addAuxiliaryParameters(this.anonymousSchemaOpExpression.getAuxiliaryParameters());
		}
		this.addInputParameters(this.opExpression.getInputParameters());
		this.addOutputParameters(this.opExpression.getOutputParameters());
	}

	@Override
	public void resolveTemplates(ParseTreeProperty<ST> templateTree) {
		this.anonymousSchemaOpExpression.resolveTemplates(templateTree);
		this.opExpression.resolveTemplates(templateTree);
	}
	
	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree) {
		this.anonymousSchemaOpExpression.createPreAndPostconditions(typeTree);
		this.opExpression.createPreAndPostconditions(typeTree);

		setPreconditions();
		setPostconditions();
		
    	if (hasPreconditions()){
    		preconditionFunction = new PreconditionFunction(this, this.preconditions, this.definingClass);
    	}
	}
	
	private void setPostconditions() {
		PostconditionFactory postconditionFactory = PostconditionFactory.getInstance();
		
		if (this.type == ScopeEnrichmentType.INPUT){
			this.postconditions = this.opExpression.getPostconditions();
		} else if (this.type == ScopeEnrichmentType.AUXILIARY){
			this.postconditions = postconditionFactory.createVarPostconditionForScopeEnrichment(this);
		}
	}

	private void setPreconditions() {
		PreconditionFactory preconditionFactory = PreconditionFactory.getInstance();
		
		List<ST> leftTemplates = this.anonymousSchemaOpExpression.getPreconditions().getTemplates();
		List<ST> rightTemplates = this.opExpression.getPreconditions().getTemplates();
		
		if (this.type == ScopeEnrichmentType.INPUT){
			SimplePreconditions simplePreconditions = new SimplePreconditions();
			simplePreconditions.addAll(leftTemplates.stream().map(p -> new SimplePrecondition(p)).collect(Collectors.toList()));
			simplePreconditions.addAll(rightTemplates.stream().map(p -> new SimplePrecondition(p)).collect(Collectors.toList()));
			this.preconditions = simplePreconditions;
		} else if (this.type == ScopeEnrichmentType.AUXILIARY){
			List<ST> allTemplates = new ArrayList<>();
			allTemplates.addAll(leftTemplates);
			allTemplates.addAll(rightTemplates);
			this.preconditions = preconditionFactory.createExistsPrecondition(this.getAuxiliaryParameters(), allTemplates);
		}
	}

	
	@Override
	public List<IPromotedOperation> getPromotedOperations() {
		return opExpression.getPromotedOperations();
	}

	@Override
	public List<IPromotedOperation> getPromotedOperationsWithOutputParameter(Variable outputParameter, boolean ignoreDecoration) {
		return opExpression.getPromotedOperationsWithOutputParameter(outputParameter, ignoreDecoration);
	}

	@Override
	public boolean isNonDeterministicChoice() {
		return opExpression.isNonDeterministicChoice();
	}

	@Override
	public boolean isCombinable() {
		return false;
	}

	@Override
	public boolean isSequentialComposition() {
		return opExpression.isSequentialComposition();
	}

	@Override
	public IComposablePreconditions getComposablePreconditions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICombinableOperation getCombinableOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IComposablePostconditions getComposablePostconditions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IComposablePostconditions getComposablePostconditionsWithoutPromotion(Declarations sharedOutputVariables) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NonDeterministicPreconditions getNonDeterministicPreconditions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NonDeterministicPostconditions getNonDeterministicPostconditions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SequentialPreconditions getSequentialPreconditions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SequentialPostconditions getSequentialPostconditions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SequentialPostconditions getSequentialPostconditionsWithoutPromotion(Declarations outputVariables) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBoolFunction() {
		return this.opExpression.isBoolFunction();
	}

	@Override
	public boolean isFunction() {
		return this.opExpression.isFunction();
	}

	@Override
	public boolean isChangeOperation() {
		return this.opExpression.isChangeOperation();
	}

	@Override
	public List<String> getDeltalist() {
		return this.opExpression.getDeltalist();
	}

	@Override
	public IPreconditions getPreconditions() {
		return this.preconditions;
	}

	@Override
	public IPostconditions getPostconditions() {
		return this.postconditions;
	}

	@Override
	public Idents getUsedStateVariables() {
		return this.opExpression.getUsedStateVariables();
	}

	@Override
	public Idents getChangedStateVariables() {
		return this.opExpression.getChangedStateVariables();
	}

	public Variable getAuxiliaryVariable() {
		if (type == ScopeEnrichmentType.AUXILIARY && getAuxiliaryParameters().size() == 1)
			return getAuxiliaryParameters().get(0);
		return null;
	}

	public IPostconditions getAuxiliaryVariablePostconditions() {
		return this.anonymousSchemaOpExpression.getPostconditions();
	}
	public IPreconditions getPromotedPreconditions() {
		return this.opExpression.getPreconditions();
	}

	public IPostconditions getPromotedPostconditions() {
		return this.opExpression.getPostconditions();
	}

	@Override
	public Declarations getModifiableInputParameters(){
		return this.modifiableInputParameters;
	}

}
