package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.AssertCollection;
import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class CompositePreconditionDataCollectorTest {

	@Mock private ICombinableOperation leftOp;
	@Mock private ICombinableOperation rightOp;
	@Mock private ICombinablePreconditions leftPreconditions;
	@Mock private ICombinablePreconditions rightPreconditions;
	
	private Declarations sharedVariables;
	private Declarations communicationVariables;
	
	private CompositePreconditionDataCollector collector;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		Mockito.when(leftOp.getCombinablePreconditions()).thenReturn(leftPreconditions);
		Mockito.when(rightOp.getCombinablePreconditions()).thenReturn(rightPreconditions);
		initCombinablePreconditionsMock(leftPreconditions);
		initCombinablePreconditionsMock(rightPreconditions);
		
		sharedVariables = new Declarations();
		communicationVariables = new Declarations();
		collector = new CompositePreconditionDataCollector(leftOp, rightOp);
	}

	@After
	public void tearDown(){
		PerfectPredicateTemplateProvider.setInstance(null);
	}

	private void initCombinablePreconditionsMock(ICombinablePreconditions preconditions) {
		Mockito.when(preconditions.getAllCommunicationVariables()).thenReturn(Declarations.empty());
		Mockito.when(preconditions.getAllSharedVariables()).thenReturn(Declarations.empty());
		
		Mockito.when(preconditions.getSimplePreconditions()).thenReturn(PreconditionFunctionCalls.empty());
		Mockito.when(preconditions.getCommunicatingPreconditions()).thenReturn(PreconditionFunctionCalls.empty());
		Mockito.when(preconditions.getAllCommunicatingOrSharingPromotions()).thenReturn(new HashMap<Variable, List<IPromotedOperation>>());
	}
	
	private void withSimplePreconditions(ICombinablePreconditions preconditions, PreconditionFunctionCall... calls) {
		Mockito.when(preconditions.getSimplePreconditions()).thenReturn(new PreconditionFunctionCalls(calls));
	}
	private void withCommunicatingPreconditions(ICombinablePreconditions preconditions, PreconditionFunctionCall... calls) {
		Mockito.when(preconditions.getCommunicatingPreconditions()).thenReturn(new PreconditionFunctionCalls(calls));
	}
	private void withCommunicationVariables(ICombinablePreconditions preconditions, Variable... vars) {
		Mockito.when(preconditions.getAllCommunicationVariables()).thenReturn(new Declarations(vars));
	}
	private void withSharedVariables(ICombinablePreconditions preconditions, Variable... vars) {
		Mockito.when(preconditions.getAllSharedVariables()).thenReturn(new Declarations(vars));
	}
	private void withCommunicatingOrSharingPromotions(ICombinablePreconditions preconditions, Variable var, List<IPromotedOperation> opPromos) {
		Map<Variable, List<IPromotedOperation>> existingOpPromos = preconditions.getAllCommunicatingOrSharingPromotions();
		existingOpPromos.put(var, opPromos);
	}

	private PreconditionFunctionCall getPreconditionFunctionCallMock() {
		return Mockito.mock(PreconditionFunctionCall.class);
	}
	private PreconditionFunctionCall getPreconditionFunctionCallMock(Declarations inputVariables) {
		ST caller = Mockito.mock(ST.class);
		PreconditionFunction preconditionFunction = Mockito.mock(PreconditionFunction.class);
		
		PreconditionFunctionCall preconditionCall = new PreconditionFunctionCall(caller, preconditionFunction);
		
		for(Variable inputVar: inputVariables.asList()){
			Mockito.when(preconditionFunction.hasInputVariable(inputVar)).thenReturn(true);
		}
		return preconditionCall;
	}

	private IPromotedOperation getPromotedOperationMock() {
		return Mockito.mock(IPromotedOperation.class);
	}

	private Variable getVariableMock() {
		return Mockito.mock(Variable.class);
	}

	@Test
	public void conjoinCombinesSimplePreconditions(){
		PreconditionFunctionCall preconditionCall1 = getPreconditionFunctionCallMock();
		PreconditionFunctionCall preconditionCall2 = getPreconditionFunctionCallMock();
		
		withSimplePreconditions(leftPreconditions, preconditionCall1);
		withSimplePreconditions(rightPreconditions, preconditionCall2);
		
		collector.conjoin(sharedVariables);
		
		Assert.assertEquals(2, collector.getSimplePreconditions().size());
		AssertCollection.assertHasElements(collector.getSimplePreconditions().asList(), preconditionCall1, preconditionCall2);
	}

	@Test
	public void conjoinCombinesCommunicatingPreconditions(){
		PreconditionFunctionCall preconditionCall1 = getPreconditionFunctionCallMock();
		PreconditionFunctionCall preconditionCall2 = getPreconditionFunctionCallMock();
		
		withCommunicatingPreconditions(leftPreconditions, preconditionCall1);
		withCommunicatingPreconditions(rightPreconditions, preconditionCall2);
		
		collector.conjoin(sharedVariables);
		
		Assert.assertEquals(2, collector.getCommunicatingPreconditions().size());
		AssertCollection.assertHasElements(collector.getCommunicatingPreconditions().asList(), preconditionCall1, preconditionCall2);
	}
	
	@Test
	public void conjoinCombinesCommunicationVariables(){
		Variable communicationVariable1 = getVariableMock();
		Variable communicationVariable2 = getVariableMock();
		
		withCommunicationVariables(leftPreconditions, communicationVariable1);
		withCommunicationVariables(rightPreconditions, communicationVariable2);
		
		collector.conjoin(sharedVariables);
		
		Assert.assertEquals(2, collector.getCommunicationVariables().size());
		AssertCollection.assertHasElements(collector.getCommunicationVariables().asList(), communicationVariable1, communicationVariable2);
	}

	@Test
	public void conjoinCombinesLeftRightAndNewSharedVariables(){
		Variable var1 = getVariableMock();
		Variable var2 = getVariableMock();
		Variable var3 = getVariableMock();
		
		sharedVariables.add(var3);
		
		withSharedVariables(leftPreconditions, var1);
		withSharedVariables(rightPreconditions, var2);
		
		collector.conjoin(sharedVariables);
		
		Assert.assertEquals(3, collector.getSharedOutputVariables().size());
		AssertCollection.assertHasElements(collector.getSharedOutputVariables().asList(), var1, var2, var3);
	}
	
	@Test
	public void conjoinCombinesCommunicatingOrSharingPromotions(){
		Variable var1 = getVariableMock();
		IPromotedOperation opPromo1 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(leftPreconditions, var1, Arrays.asList(opPromo1));
		
		Variable var2 = getVariableMock();
		IPromotedOperation opPromo2 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(rightPreconditions, var2, Arrays.asList(opPromo2));
		
		collector.conjoin(sharedVariables);
		
		Assert.assertEquals(2, collector.getAllCommunicatingOrSharingOperations().size());
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().keySet(), var1, var2);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(var1), opPromo1);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(var2), opPromo2);
	}
	
	@Test
	public void composeCombinesCommunicatingOrSharingPromotionsAndAddsNewCommunicatingPromotions(){
		Variable var1 = getVariableMock();
		IPromotedOperation opPromo1 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(leftPreconditions, var1, Arrays.asList(opPromo1));
		
		Variable var2 = getVariableMock();
		IPromotedOperation opPromo2 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(rightPreconditions, var2, Arrays.asList(opPromo2));
		
		Variable commVar1 = getVariableMock();
		IPromotedOperation commPromo1 = getPromotedOperationMock();
		Variable commVar2 = getVariableMock();
		IPromotedOperation commPromo2 = getPromotedOperationMock();
		communicationVariables.addNew(commVar1, commVar2);
		
		Mockito.when(leftOp.getPromotedOperationsWithOutputParameter(commVar1, true)).thenReturn(Arrays.asList(commPromo1));
		Mockito.when(rightOp.getPromotedOperationsWithOutputParameter(commVar2, true)).thenReturn(Arrays.asList(commPromo2));
		
		collector.compose(communicationVariables, sharedVariables);
		
		Assert.assertEquals(4, collector.getAllCommunicatingOrSharingOperations().size());
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().keySet(), var1, var2, commVar1, commVar2);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(var1), opPromo1);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(var2), opPromo2);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(commVar1), commPromo1);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(commVar2), commPromo2);
	}

	@Test
	public void composeCombinesCommunicatingOrSharingPromotionsAndAddsNewCommunicatingPromotionsVariableOverlap(){
		Variable var1 = getVariableMock();
		IPromotedOperation opPromo1 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(leftPreconditions, var1, Arrays.asList(opPromo1));
		
		Variable var2 = getVariableMock();
		IPromotedOperation opPromo2 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(rightPreconditions, var2, Arrays.asList(opPromo2));
		
		IPromotedOperation commPromo1 = getPromotedOperationMock();
		IPromotedOperation commPromo2 = getPromotedOperationMock();
		communicationVariables.addNew(var1, var2);
		
		Mockito.when(leftOp.getPromotedOperationsWithOutputParameter(var1, true)).thenReturn(Arrays.asList(commPromo1));
		Mockito.when(rightOp.getPromotedOperationsWithOutputParameter(var2, true)).thenReturn(Arrays.asList(commPromo2));
		
		collector.compose(communicationVariables, sharedVariables);
		
		Assert.assertEquals(2, collector.getAllCommunicatingOrSharingOperations().size());
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().keySet(), var1, var2);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(var1), opPromo1, commPromo1);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(var2), opPromo2, commPromo2);
	}

	@Test
	public void composeCombinesCommunicatingOrSharingPromotionsAndAddsNewSharingPromotions(){
		Variable var1 = getVariableMock();
		IPromotedOperation opPromo1 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(leftPreconditions, var1, Arrays.asList(opPromo1));
		
		Variable var2 = getVariableMock();
		IPromotedOperation opPromo2 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(rightPreconditions, var2, Arrays.asList(opPromo2));
		
		Variable sharedVar1 = getVariableMock();
		IPromotedOperation commPromo1 = getPromotedOperationMock();
		IPromotedOperation commPromo2 = getPromotedOperationMock();
		sharedVariables.addNew(sharedVar1);
		
		Mockito.when(leftOp.getPromotedOperationsWithOutputParameter(sharedVar1, true)).thenReturn(Arrays.asList(commPromo1));
		Mockito.when(rightOp.getPromotedOperationsWithOutputParameter(sharedVar1, true)).thenReturn(Arrays.asList(commPromo2));
		
		collector.compose(communicationVariables, sharedVariables);
		
		Assert.assertEquals(3, collector.getAllCommunicatingOrSharingOperations().size());
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().keySet(), var1, var2, sharedVar1);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(var1), opPromo1);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(var2), opPromo2);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(sharedVar1), commPromo1, commPromo2);
	}

	@Test
	public void composeCombinesCommunicatingOrSharingPromotionsAndAddsNewSharingPromotionsVariableOverlap(){
		Variable sharedVar1 = getVariableMock();
		
		IPromotedOperation opPromo1 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(leftPreconditions, sharedVar1, Arrays.asList(opPromo1));
		
		IPromotedOperation opPromo2 = getPromotedOperationMock();
		withCommunicatingOrSharingPromotions(rightPreconditions, sharedVar1, Arrays.asList(opPromo2));
		
		IPromotedOperation sharedPromo1 = getPromotedOperationMock();
		IPromotedOperation sharedPromo2 = getPromotedOperationMock();
		sharedVariables.addNew(sharedVar1);
		
		Mockito.when(leftOp.getPromotedOperationsWithOutputParameter(sharedVar1, true)).thenReturn(Arrays.asList(sharedPromo1));
		Mockito.when(rightOp.getPromotedOperationsWithOutputParameter(sharedVar1, true)).thenReturn(Arrays.asList(sharedPromo2));
		
		collector.compose(communicationVariables, sharedVariables);
		
		Assert.assertEquals(1, collector.getAllCommunicatingOrSharingOperations().size());
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().keySet(), sharedVar1);
		AssertCollection.assertHasElements(collector.getAllCommunicatingOrSharingOperations().get(sharedVar1), sharedPromo1, sharedPromo2, opPromo1, opPromo2);
	}
	
	@Test
	public void composeSplitsSimpleCommunicationPreconditions(){
		ExpressionType type = Mockito.mock(ExpressionType.class);
		
		Variable commVar1 = Variable.createUndecoratedVariable("commVar1", type);
		Variable commVar2 = Variable.createUndecoratedVariable("commVar2", type);
		Variable commVar1In = Variable.createInputVariable("commVar1", type);
		Variable commVar2In = Variable.createInputVariable("commVar2", type);
		Variable inVar = Variable.createInputVariable("inVar", type);

		PreconditionFunctionCall commVar1Input = getPreconditionFunctionCallMock(new Declarations(commVar1In, inVar));
		PreconditionFunctionCall commVar2Input = getPreconditionFunctionCallMock(new Declarations(commVar2In, inVar));
		
		PreconditionFunctionCall noCommVarInput1 = getPreconditionFunctionCallMock(new Declarations(inVar));
		PreconditionFunctionCall noCommVarInput2 = getPreconditionFunctionCallMock(new Declarations(inVar));
		
		withSimplePreconditions(leftPreconditions, commVar1Input, noCommVarInput1);
		withSimplePreconditions(rightPreconditions, noCommVarInput2, commVar2Input);
		
		communicationVariables.addNew(commVar1, commVar2);
		
		collector.compose(communicationVariables, sharedVariables);
		
		Assert.assertEquals(2, collector.getSimplePreconditions().size());
		Assert.assertEquals(2, collector.getCommunicatingPreconditions().size());
		AssertCollection.assertHasElements(collector.getSimplePreconditions().asList(), noCommVarInput1, noCommVarInput2);
		AssertCollection.assertHasElements(collector.getCommunicatingPreconditions().asList(), commVar1Input, commVar2Input);
	}

}
