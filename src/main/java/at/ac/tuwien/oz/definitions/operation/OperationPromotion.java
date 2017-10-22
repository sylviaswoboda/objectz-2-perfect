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
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ChangeOperationCalls;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.EmptyPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.PreconditionFunctionCalls;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class OperationPromotion extends OperationExpression implements IPromotedOperation {
	
	// will be set initially
	private CallerContext callerCtx;
	private String calledOperationName;
	
	// added by operationComposer
	private IOperation promotedOperation;
	private ICombinablePostconditions combinablePostconditions;
	private ICombinablePreconditions combinablePreconditions;
	
	// added by operationTranslator
	private ST callerST;
	

	public OperationPromotion(CallerContext callerCtx, String calledOperationName){
		super();
		this.callerCtx = callerCtx;
		this.calledOperationName = calledOperationName;
	}
	
	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree) {
		
		setPreconditions();
		setPostconditions();
		
    	if (hasPreconditions()){
    		preconditionFunction = new PreconditionFunction(this, this.combinablePreconditions, this.definingClass);
    	}
	}

	private void setPostconditions() {
		PostconditionFactory postconditionFactory = PostconditionFactory.getInstance();
		
		if (isBoolFunction()){
			this.combinablePostconditions = EmptyPostconditions.empty();
		} else if (isFunction()){
			this.combinablePostconditions = postconditionFactory.createOutputPromotions(this, this.getOutputParameters());
		} else if (isChangeOperation()){
			ChangeOperationCalls postconditions = new ChangeOperationCalls();
			ChangeOperationCall promotedSchemaCall = new ChangeOperationCall(this);
			postconditions.add(promotedSchemaCall);
			this.combinablePostconditions = postconditions;
		}
	}

	private void setPreconditions() {
		PreconditionFactory preconditionFactory = PreconditionFactory.getInstance();
		
		if (promotedOperation.hasPreconditions()){
			PreconditionFunctionCalls preconditionFunctionCalls = new PreconditionFunctionCalls();
			PreconditionFunctionCall promotedPreconditionFunctionCall = 
					preconditionFactory.createPreconditionFunctionCall(this.callerST, promotedOperation.getPreconditionFunction());
			preconditionFunctionCalls.add(promotedPreconditionFunctionCall);
			this.combinablePreconditions = preconditionFunctionCalls;
		} else {
			this.combinablePreconditions = new EmptyPreconditions();
		}
	}

	@Override
	public boolean isBoolFunction() {
		if (promotedOperation == null){
			throw new ObjectZToPerfectTranslationException("Function undefined when promotedOperation is unknown");
		}
		return promotedOperation.isBoolFunction();
	}
	
	@Override
	public boolean isFunction() {
		if (promotedOperation == null){
			throw new ObjectZToPerfectTranslationException("Function undefined when promotedOperation is unknown");
		}
		return promotedOperation.isFunction();
	}
	
	@Override
	public boolean isChangeOperation() {
		if (promotedOperation == null){
			throw new ObjectZToPerfectTranslationException("Function undefined when promotedOperation is unknown");
		}
		return promotedOperation.isChangeOperation();
	}
	
	
	@Override
	public String getPostconditionFunctionName(){
		return getName();
	}

	
	@Override
	public List<IPromotedOperation> getPromotedOperations() {
		List<IPromotedOperation> promotedOperations = new ArrayList<IPromotedOperation>();
		promotedOperations.add(this);
		return promotedOperations;
	}

	@Override
	public boolean isStateVariable(Ident usedVariable) {
		return promotedOperation.isStateVariable(usedVariable);
	}

	@Override
	public ICombinablePreconditions getCombinablePreconditions() {
		return combinablePreconditions;
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
		return combinablePostconditions;
	}

	@Override
	public IComposablePostconditions getComposablePostconditions() {
		return this.combinablePostconditions;
	}

	@Override
	public IPostconditions getPostconditions() {
		return this.combinablePostconditions;
	}

	@Override
	public NonDeterministicPostconditions getNonDeterministicPostconditions() {
		return null;
	}

	@Override
	public boolean isNonDeterministicChoice() {
		return false;
	}

	@Override
	public boolean isCombinable() {
		return true;
	}

	@Override
	public String toString() {
		return "OperationPromotion [promotedOperation=" + promotedOperation + ", caller=" + callerST + "]";
	}

	@Override
	public List<String> getDeltalist() {
		return new ArrayList<String>();
	}

	@Override
	public Idents getUsedStateVariables() {
		return promotedOperation.getUsedStateVariables();
	}

	@Override
	public Idents getChangedStateVariables() {
		return promotedOperation.getChangedStateVariables();
	}

	@Override
	public ICombinableOperation getCombinableOperation() {
		return this;
	}

	@Override
	public List<IPromotedOperation> getPromotedOperationsWithOutputParameter(Variable outputParameter, boolean ignoreDecoration){
		List<IPromotedOperation> promotionsWithParameter = new ArrayList<IPromotedOperation>();
		
		for (IPromotedOperation opPromo: getPromotedOperations()){
			if (opPromo.hasOutputVariable(outputParameter, ignoreDecoration)){
				promotionsWithParameter.add(opPromo);
			}
		}
		return promotionsWithParameter;
	}
	
	@Override
	public Declarations getSharedOutputVariables() {
		return Declarations.empty();
	}

	@Override
	public IComposablePostconditions getComposablePostconditionsWithoutPromotion(Declarations outputVariables) {
		return this.combinablePostconditions.getWithoutPromotions(outputVariables);
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
	public NonDeterministicPreconditions getNonDeterministicPreconditions() {
		return null;
	}


	@Override
	public void setDefiningClass(ObjectZClass currentClass) {
		this.definingClass = currentClass;
	}


	@Override
	public void resolveOperationReferences(ParseTreeProperty<ExpressionType> typeTree) {
		ObjectZClass definingClass = findDefiningClassOfCaller(typeTree, this.callerCtx);
		this.promotedOperation = definingClass.resolveOperation(new Ident(this.calledOperationName));
		this.setInputAndOutputParameters();
	}

	private void setInputAndOutputParameters() {
		this.addInputParameters(this.promotedOperation.getInputParameters());
		this.addOutputParameters(this.promotedOperation.getOutputParameters());
	}

	@Override
	public CallerContext getCallerCtx() {
		return this.callerCtx;
	}

	/////////////////////////////////////////////
	@Override
	public ST getCaller() {
		return callerST;
	}
	
	@Override
	public IOperation getPromotedOperation(){
		return this.promotedOperation;
	}
	/////////////////////////////////////////////

	@Override
	public void resolveTemplates(ParseTreeProperty<ST> templateTree) {
		this.callerST = templateTree.get(callerCtx);
//		this.combinablePreconditions.resolveTemplates(templateTree);
	}
}
