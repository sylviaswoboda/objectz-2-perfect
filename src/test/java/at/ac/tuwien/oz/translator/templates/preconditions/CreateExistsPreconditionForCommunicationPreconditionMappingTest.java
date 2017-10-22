package at.ac.tuwien.oz.translator.templates.preconditions;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.CommunicationPreconditionMapping;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.PreconditionFunctionCalls;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class CreateExistsPreconditionForCommunicationPreconditionMappingTest {

	private PerfectPredicateTemplateProvider templateProvider = PerfectPredicateTemplateProvider.getInstance();
	private PreconditionTestMockProvider preconditionMockProvider = new PreconditionTestMockProvider();

	private Variable inVar;
	
	private Variable inCommVar;
	private Variable inCommVarIn;
	
	private Variable outerCommVar;
	private Variable outerCommVarIn;
	private Variable outerCommVarOut;
	
	private Variable sharedOutputVariable;
	
	private ExpressionType type;
	
	private CommunicationPreconditionMapping communicationPreconditionMapping;

	@Before
	public void setup(){
		TempVarProvider.resetNameCounter();
		type = ExpressionType.getNat();
		
		inVar = Variable.createInputVariable("inVar", type);
		
		inCommVar = Variable.createUndecoratedVariable("inCommVar", type);
		inCommVarIn = Variable.createInputVariable("inCommVar", type);

		outerCommVar = Variable.createUndecoratedVariable("outerCommVar", type);
		outerCommVarIn = Variable.createInputVariable("outerCommVar", type);
		outerCommVarOut = Variable.createOutputVariable("outerCommVar", type);

		sharedOutputVariable = Variable.createOutputVariable("sharedOutput", type);
		
		buildMappingMock();
	}
	
	private void buildMappingMock(){
		communicationPreconditionMapping = Mockito.mock(CommunicationPreconditionMapping.class);
		
		Mockito.when(communicationPreconditionMapping.getAllCommunicationVariables()).thenReturn(Declarations.empty());
		Mockito.when(communicationPreconditionMapping.getAllSharedVariables()).thenReturn(Declarations.empty());
		Mockito.when(communicationPreconditionMapping.getPromotingOperations(Mockito.any(Variable.class))).thenReturn(null);
	}
	
	private void withCommunicatingPromotion(String caller, String postconditionFunctionName, Declarations inputVariables, Variable communicationVariable){
		IPromotedOperation promotedFunction = buildPromotedOperationMock(caller, postconditionFunctionName, inputVariables);
		Mockito.when(communicationPreconditionMapping.getPromotingOperations(communicationVariable)).thenReturn(Arrays.asList(promotedFunction));
	}
	
	private void withPreconditionFunctionCalls(PreconditionFunctionCall... preconditionFunctionCallList) {
		PreconditionFunctionCalls preconditionFunctionCalls = new PreconditionFunctionCalls();
		
		for(PreconditionFunctionCall call: preconditionFunctionCallList){
			preconditionFunctionCalls.add(call);
		}
	
		Mockito.when(communicationPreconditionMapping.getPreconditionsWithCommunication()).thenReturn(preconditionFunctionCalls);
	}

	@Test
	public void communicationVariableIsReplacedByTempVar() {
		withCommunicatingPromotion("a", "func", new Declarations(inVar), inCommVar);
		Mockito.when(communicationPreconditionMapping.getAllCommunicationVariables()).thenReturn(new Declarations(inCommVar));
		
		PreconditionFunctionCall changeOperationPrecondition = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp_prec", new Declarations(inCommVarIn, inVar));
		withPreconditionFunctionCalls(changeOperationPrecondition);
		
		ST resultTemplate = templateProvider.createExistsPrecondition(communicationPreconditionMapping);
		
		String expectedResult = "(exists tempVar1:nat :- "
								+ "(tempVar1 = a.func(inVar_in).inCommVar_out & "
								+  "b.changeOp_prec(tempVar1, inVar_in)))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void sharedVariablesAreReplacedByTempVar() {
		IPromotedOperation sharingFunction = buildPromotedOperationMock("a", "func", new Declarations(inVar));
		IPromotedOperation sharingChangeOperation = buildPromotedOperationMock("b", "changeOp_post", new Declarations(inVar));
		Mockito.when(communicationPreconditionMapping.getPromotingOperations(sharedOutputVariable)).thenReturn(Arrays.asList(sharingFunction, sharingChangeOperation));
		
		Mockito.when(communicationPreconditionMapping.getAllSharedVariables()).thenReturn(new Declarations(sharedOutputVariable));

		
		PreconditionFunctionCall changeOperationPrecondition = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp_prec", new Declarations(inVar));
		withPreconditionFunctionCalls(changeOperationPrecondition);
		
		ST resultTemplate = templateProvider.createExistsPrecondition(communicationPreconditionMapping);
		
		String expectedResult = "(exists tempVar1:nat :- "
								+ "(tempVar1 = a.func(inVar_in).sharedOutput_out & "
								+  "tempVar1 = b.changeOp_post(inVar_in).sharedOutput_out & "
								+  "b.changeOp_prec(inVar_in)))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void communicationAndSharedVariablesAreReplacedByTempVar() {
		IPromotedOperation sharingFunction = buildPromotedOperationMock("a", "func", new Declarations(inVar));
		IPromotedOperation sharingChangeOperation = buildPromotedOperationMock("b", "changeOp_post", new Declarations(inCommVarIn, inVar));
		Mockito.when(communicationPreconditionMapping.getPromotingOperations(sharedOutputVariable)).thenReturn(Arrays.asList(sharingFunction, sharingChangeOperation));
		
		Mockito.when(communicationPreconditionMapping.getAllSharedVariables()).thenReturn(new Declarations(sharedOutputVariable));

		withCommunicatingPromotion("a", "func", new Declarations(inVar), inCommVar);
		Mockito.when(communicationPreconditionMapping.getAllCommunicationVariables()).thenReturn(new Declarations(inCommVar));
		
		PreconditionFunctionCall functionPrecondition = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func_prec", new Declarations(inVar));
		PreconditionFunctionCall changeOperationPrecondition = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp_prec", new Declarations(inCommVarIn, inVar));
		withPreconditionFunctionCalls(functionPrecondition, changeOperationPrecondition);
		
		ST resultTemplate = templateProvider.createExistsPrecondition(communicationPreconditionMapping);
		
		String expectedResult = "(exists tempVar1:nat, tempVar2:nat :- "
								+ "(tempVar1 = a.func(inVar_in).inCommVar_out & "
								+  "tempVar2 = a.func(inVar_in).sharedOutput_out & "
								+  "tempVar2 = b.changeOp_post(tempVar1, inVar_in).sharedOutput_out & "
								+  "a.func_prec(inVar_in) & "
								+  "b.changeOp_prec(tempVar1, inVar_in)))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	
	@Test
	public void outerCommunicationInputVariableIsAlsoReplacedByTempVarEqualityButNoTempVarDeclaration() {
		withCommunicatingPromotion("a", "func", new Declarations(outerCommVarIn), inCommVar);
		Mockito.when(communicationPreconditionMapping.getAllCommunicationVariables()).thenReturn(new Declarations(inCommVar));
		
		PreconditionFunctionCall changeOperationPrecondition = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp_prec", new Declarations(inCommVarIn, outerCommVarIn));
		withPreconditionFunctionCalls(changeOperationPrecondition);
		
		TemporaryVariableMap outerTempVarMap = new TemporaryVariableMap();
		outerTempVarMap.add(outerCommVar);
		
		ST resultTemplate = templateProvider.createExistsPrecondition(communicationPreconditionMapping, outerTempVarMap);
		
		String expectedResult = "(exists tempVar2:nat :- "
								+ "(tempVar2 = a.func(tempVar1).inCommVar_out & "
								+  "b.changeOp_prec(tempVar2, tempVar1)))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void outerCommunicationOutputVariableAlsoProducesTempVarEqualityButNoTempVarDeclaration() {
		IPromotedOperation sharingFunction = buildPromotedOperationMock("a", "func", new Declarations(inVar));
		IPromotedOperation sharingChangeOperation = buildPromotedOperationMock("b", "changeOp_post", new Declarations(inVar));
		Mockito.when(communicationPreconditionMapping.getPromotingOperations(sharedOutputVariable)).thenReturn(Arrays.asList(sharingFunction, sharingChangeOperation));
		Mockito.when(communicationPreconditionMapping.getPromotingOperations(outerCommVarOut)).thenReturn(Arrays.asList(sharingFunction));
		
		Mockito.when(communicationPreconditionMapping.getAllSharedVariables()).thenReturn(new Declarations(sharedOutputVariable));

		PreconditionFunctionCall functionPrecondition = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func_prec", new Declarations(inVar));
		PreconditionFunctionCall changeOperationPrecondition = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp_prec", new Declarations(inVar));
		withPreconditionFunctionCalls(functionPrecondition, changeOperationPrecondition);
		
		TemporaryVariableMap outerTempVarMap = new TemporaryVariableMap();
		outerTempVarMap.add(outerCommVar);
		
		ST resultTemplate = templateProvider.createExistsPrecondition(communicationPreconditionMapping, outerTempVarMap);
		
		String expectedResult = "(exists tempVar2:nat :- "
								+ "(tempVar1 = a.func(inVar_in).outerCommVar_out & "
								+  "tempVar2 = a.func(inVar_in).sharedOutput_out & "
								+  "tempVar2 = b.changeOp_post(inVar_in).sharedOutput_out & "
								+  "a.func_prec(inVar_in) & "
								+  "b.changeOp_prec(inVar_in)))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}
	
	private IPromotedOperation buildPromotedOperationMock(String caller, String postconditionFunctionName, Declarations inputVariables) {
		IPromotedOperation promotedOperationMock = Mockito.mock(IPromotedOperation.class);
		IOperation operationMock = Mockito.mock(IOperation.class);
		
		ST callerTemplate = PerfectTemplateProvider.getInstance().getId(new Ident(caller));
		
		Mockito.when(operationMock.getPostconditionFunctionName()).thenReturn(postconditionFunctionName);
		
		Mockito.when(promotedOperationMock.getCaller()).thenReturn(callerTemplate);
		Mockito.when(promotedOperationMock.getInputParameters()).thenReturn(inputVariables);
		Mockito.when(promotedOperationMock.getPromotedOperation()).thenReturn(operationMock);
		
		return promotedOperationMock;
	}

}
