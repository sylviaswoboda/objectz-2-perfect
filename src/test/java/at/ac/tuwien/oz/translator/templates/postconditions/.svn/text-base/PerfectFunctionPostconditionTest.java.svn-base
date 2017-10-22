package at.ac.tuwien.oz.translator.templates.postconditions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexOutputPromotionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class PerfectFunctionPostconditionTest {

	private PerfectPredicateTemplateProvider predicateTemplateProvider;
	
	private ST caller;
	private Declarations inputParameters;
	
	private ExpressionType type; 
	
	@Mock private IOperation func;
	@Mock private OperationPromotion operationPromotion;
	
	@Mock private Variable inputVariable1;
	@Mock private Variable inputVariable2;

	@Mock private Variable outputVariable1;
	@Mock private Variable outputVariable2;

	private Variable commVar1;
	private Variable commVar1In;
	private Variable commVar1Out;

	private Variable commVar2;
	private Variable commVar2In;
	private Variable commVar2Out;

//	private Variable commVar2;
	private Variable commVar3In;
//	private Variable commVar2Out;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		predicateTemplateProvider = PerfectPredicateTemplateProvider.getInstance();
		caller = new ST("someCaller");
		inputParameters = new Declarations();
		
		type = ExpressionType.getNat();
		
		TempVarProvider.resetNameCounter();
		
		Mockito.when(func.getName()).thenReturn("func");
		Mockito.when(func.getPostconditionFunctionName()).thenReturn("func");
		Mockito.when(operationPromotion.getName()).thenReturn("opPromo");
		
		inputVariable1 = Variable.createInputVariable("inputVar1", type);
		inputVariable2 = Variable.createInputVariable("inputVar2", type);
		outputVariable1 = Variable.createOutputVariable("outputVar1", type);
		outputVariable2 = Variable.createOutputVariable("outputVar2", type);
		
		commVar1 = Variable.createUndecoratedVariable("commVar1", type);
		commVar1In = Variable.createInputVariable("commVar1", type);
		commVar1Out = Variable.createOutputVariable("commVar1", type);

		commVar2 = Variable.createUndecoratedVariable("commVar2", type);
		commVar2In = Variable.createInputVariable("commVar2", type);
		commVar2Out = Variable.createOutputVariable("commVar2", type);

		commVar3In = Variable.createInputVariable("commVar3", type);
	}

	@Test
	public void testCreateOutputPromotion_WithInputParameters() {
		inputParameters.add(inputVariable1);
		
		Mockito.when(operationPromotion.getPromotedOperation()).thenReturn(func);
		Mockito.when(operationPromotion.getInputParameters()).thenReturn(inputParameters);
		
		OutputPromotion outputPromotion = Mockito.mock(OutputPromotion.class);
		Mockito.when(outputPromotion.getOutputVariable()).thenReturn(outputVariable1);
		Mockito.when(outputPromotion.getPromotedOperation()).thenReturn(operationPromotion);
		
		ST outputPromotionST = predicateTemplateProvider.createOutputPromotionInFunction(outputPromotion);
		Assert.assertEquals("result.outputVar1_out = func(inputVar1_in).outputVar1_out", outputPromotionST.render());
		
		Mockito.when(outputPromotion.getOutputVariable()).thenReturn(outputVariable2);
		
		outputPromotionST = predicateTemplateProvider.createOutputPromotionInFunction(outputPromotion);
		Assert.assertEquals("result.outputVar2_out = func(inputVar1_in).outputVar2_out", outputPromotionST.render());
	}

	@Test
	public void testCreateOutputPromotion_WithInputParametersAndCaller() {
		inputParameters.add(inputVariable1);
		inputParameters.add(inputVariable2);
		
		Mockito.when(operationPromotion.getPromotedOperation()).thenReturn(func);
		Mockito.when(operationPromotion.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(operationPromotion.getCaller()).thenReturn(caller);
		
		OutputPromotion outputPromotion = Mockito.mock(OutputPromotion.class);
		Mockito.when(outputPromotion.getOutputVariable()).thenReturn(outputVariable1);
		Mockito.when(outputPromotion.getPromotedOperation()).thenReturn(operationPromotion);

		ST outputPromotionST = predicateTemplateProvider.createOutputPromotionInFunction(outputPromotion);
		Assert.assertEquals("result.outputVar1_out = someCaller.func(inputVar1_in, inputVar2_in).outputVar1_out", outputPromotionST.render());
	}
	
	@Test
	public void testCreateOutputPromotionsWithCommunication() {
		OperationPromotion aFunc1 = getOperationPromotionMock("a", "func1", new Declarations(), new Declarations(commVar1Out));
		OperationPromotion bFunc2 = getOperationPromotionMock("b", "func2", new Declarations(commVar1In), new Declarations(outputVariable1));
		
		OutputPromotion outputPromotion1 = new OutputPromotion(outputVariable1, bFunc2, OutputPromotionContext.FUNCTION);
		OutputPromotions outputPromotions = new OutputPromotions(OutputPromotionContext.FUNCTION);
		outputPromotions.add(outputPromotion1);
		
		OutputPromotion commOutputPromotion = new OutputPromotion(commVar1Out, aFunc1, OutputPromotionContext.FUNCTION);
		OutputPromotions communicationOutputPromotions = new OutputPromotions();
		communicationOutputPromotions.add(commOutputPromotion);
		
		ComplexOutputPromotionMapping communicatingOutputPromotions = Mockito.mock(ComplexOutputPromotionMapping.class);
		Mockito.when(communicatingOutputPromotions.getOtherPromotions()).thenReturn(outputPromotions);
		Mockito.when(communicatingOutputPromotions.getCommunicationPromotions()).thenReturn(communicationOutputPromotions);
		Mockito.when(communicatingOutputPromotions.getAllCommunicationVars()).thenReturn(new Declarations(commVar1));
		Mockito.when(communicatingOutputPromotions.getVisibleCommunicationVars()).thenReturn(Declarations.empty());
		
		ST outputPromotionsST = predicateTemplateProvider.createExistsPostcondition(communicatingOutputPromotions );
		Assert.assertEquals("(exists tempVar1:nat :- (tempVar1 = a.func1.commVar1_out & result.outputVar1_out = b.func2(tempVar1).outputVar1_out))", outputPromotionsST.render());
		
	}

	@Test
	public void testCreateOutputPromotionsWithCommunication_twoInputs() {
		OperationPromotion aFunc1 = getOperationPromotionMock("a", "func1", new Declarations(), new Declarations(commVar1Out));
		OperationPromotion bFunc2 = getOperationPromotionMock("b", "func2", new Declarations(commVar1In), new Declarations(commVar2Out, outputVariable1));
		OperationPromotion cFunc3 = getOperationPromotionMock("c", "func3", new Declarations(commVar1In, commVar2In), new Declarations(outputVariable2));
		
		OutputPromotion outputPromotion1 = new OutputPromotion(outputVariable1, bFunc2, OutputPromotionContext.FUNCTION);
		OutputPromotion outputPromotion2 = new OutputPromotion(outputVariable2, cFunc3, OutputPromotionContext.FUNCTION);
		
		OutputPromotion commOutputPromotion1 = new OutputPromotion(commVar1Out, aFunc1, OutputPromotionContext.FUNCTION);
		OutputPromotion commOutputPromotion2 = new OutputPromotion(commVar2Out, bFunc2, OutputPromotionContext.FUNCTION);
		
		OutputPromotions outputPromotions = new OutputPromotions(OutputPromotionContext.FUNCTION);
		outputPromotions.add(outputPromotion1);
		outputPromotions.add(outputPromotion2);
		
		OutputPromotions communicationOutputPromotions = new OutputPromotions();
		communicationOutputPromotions.add(commOutputPromotion1);
		communicationOutputPromotions.add(commOutputPromotion2);
		
		ComplexOutputPromotionMapping communicatingOutputPromotions = Mockito.mock(ComplexOutputPromotionMapping.class);
		Mockito.when(communicatingOutputPromotions.getOtherPromotions()).thenReturn(outputPromotions);
		Mockito.when(communicatingOutputPromotions.getCommunicationPromotions()).thenReturn(communicationOutputPromotions);
		Mockito.when(communicatingOutputPromotions.getAllCommunicationVars()).thenReturn(new Declarations(commVar1, commVar2));
		Mockito.when(communicatingOutputPromotions.getVisibleCommunicationVars()).thenReturn(Declarations.empty());
		
		ST outputPromotionsST = predicateTemplateProvider.createExistsPostcondition(communicatingOutputPromotions );
		Assert.assertEquals("(exists tempVar1:nat, tempVar2:nat :- (tempVar1 = a.func1.commVar1_out & tempVar2 = b.func2(tempVar1).commVar2_out & result.outputVar1_out = b.func2(tempVar1).outputVar1_out & " +
				"result.outputVar2_out = c.func3(tempVar1, tempVar2).outputVar2_out))", outputPromotionsST.render());
	}

	@Test
	public void testCreateOutputPromotionsWithAssocCommunication() {
		OperationPromotion aFunc1 = getOperationPromotionMock("a", "func1", new Declarations(), new Declarations(commVar1Out));
		OperationPromotion bFunc2 = getOperationPromotionMock("b", "func2", new Declarations(commVar1In), new Declarations(outputVariable1));
		
		OutputPromotion outputPromotion1 = new OutputPromotion(outputVariable1, bFunc2, OutputPromotionContext.FUNCTION);
		OutputPromotion commOutputPromotion = new OutputPromotion(commVar1Out, aFunc1, OutputPromotionContext.FUNCTION);
		
		OutputPromotions communicationOutputPromotions = new OutputPromotions();
		OutputPromotions outputPromotions = new OutputPromotions(OutputPromotionContext.FUNCTION);
		
		outputPromotions.add(outputPromotion1);
		
		communicationOutputPromotions.add(commOutputPromotion);
		
		ComplexOutputPromotionMapping communicatingOutputPromotions = Mockito.mock(ComplexOutputPromotionMapping.class);
		Mockito.when(communicatingOutputPromotions.getOtherPromotions()).thenReturn(outputPromotions);
		Mockito.when(communicatingOutputPromotions.getCommunicationPromotions()).thenReturn(communicationOutputPromotions);
		Mockito.when(communicatingOutputPromotions.getAllCommunicationVars()).thenReturn(new Declarations(commVar1));
		Mockito.when(communicatingOutputPromotions.getVisibleCommunicationVars()).thenReturn(new Declarations(commVar1));
		
		ST outputPromotionsST = predicateTemplateProvider.createExistsPostcondition(communicatingOutputPromotions );
		Assert.assertEquals("(exists tempVar1:nat :- (tempVar1 = a.func1.commVar1_out & "
				+ "result.outputVar1_out = b.func2(tempVar1).outputVar1_out & "
				+ "result.commVar1_out = tempVar1))", outputPromotionsST.render());
	}

	
	@Test
	public void testCreateOutputPromotionsWithAssocCommunication_twoInputs() {
		OperationPromotion aFunc1 = getOperationPromotionMock("a", "func1", new Declarations(), new Declarations(commVar1Out));
		OperationPromotion bFunc2 = getOperationPromotionMock("b", "func2", new Declarations(commVar1In), new Declarations(commVar2Out, outputVariable1));
		OperationPromotion cFunc3 = getOperationPromotionMock("c", "func3", new Declarations(commVar1In, commVar2In), new Declarations(outputVariable2));
		
		OutputPromotion outputPromotion1 = new OutputPromotion(outputVariable1, bFunc2, OutputPromotionContext.FUNCTION);
		OutputPromotion outputPromotion2 = new OutputPromotion(outputVariable2, cFunc3, OutputPromotionContext.FUNCTION);
		
		OutputPromotion commOutputPromotion1 = new OutputPromotion(commVar1Out, aFunc1, OutputPromotionContext.FUNCTION);
		OutputPromotion commOutputPromotion2 = new OutputPromotion(commVar2Out, bFunc2, OutputPromotionContext.FUNCTION);
		
		OutputPromotions outputPromotions = new OutputPromotions(OutputPromotionContext.FUNCTION);
		outputPromotions.add(outputPromotion1);
		outputPromotions.add(outputPromotion2);
		
		OutputPromotions communicationOutputPromotions = new OutputPromotions();
		communicationOutputPromotions.add(commOutputPromotion1);
		communicationOutputPromotions.add(commOutputPromotion2);
		
		ComplexOutputPromotionMapping communicatingOutputPromotions = Mockito.mock(ComplexOutputPromotionMapping.class);
		Mockito.when(communicatingOutputPromotions.getOtherPromotions()).thenReturn(outputPromotions);
		Mockito.when(communicatingOutputPromotions.getCommunicationPromotions()).thenReturn(communicationOutputPromotions);
		Mockito.when(communicatingOutputPromotions.getAllCommunicationVars()).thenReturn(new Declarations(commVar1, commVar2));
		Mockito.when(communicatingOutputPromotions.getVisibleCommunicationVars()).thenReturn(new Declarations(commVar1, commVar2));
		
		ST outputPromotionsST = predicateTemplateProvider.createExistsPostcondition(communicatingOutputPromotions );
		Assert.assertEquals("(exists tempVar1:nat, tempVar2:nat :- (tempVar1 = a.func1.commVar1_out & "
				+ "tempVar2 = b.func2(tempVar1).commVar2_out & "
				+ "result.outputVar1_out = b.func2(tempVar1).outputVar1_out & "
				+ "result.outputVar2_out = c.func3(tempVar1, tempVar2).outputVar2_out & "
				+ "result.commVar1_out = tempVar1 & "
				+ "result.commVar2_out = tempVar2))", outputPromotionsST.render());
	}
	
	
	@Test
	public void withOuterTempVarMap() {
		OperationPromotion aFunc1 = getOperationPromotionMock("a", "func1", new Declarations(), new Declarations(commVar1Out));
		OperationPromotion bFunc2 = getOperationPromotionMock("b", "func2", new Declarations(commVar1In, commVar3In), new Declarations(outputVariable1, commVar2Out));
		
		OutputPromotion outputPromotion1 = new OutputPromotion(outputVariable1, bFunc2, OutputPromotionContext.FUNCTION);
		OutputPromotion outerCommVarPromotion = new OutputPromotion(commVar2Out, bFunc2, OutputPromotionContext.FUNCTION);
		OutputPromotions outputPromotions = new OutputPromotions(OutputPromotionContext.FUNCTION);
		outputPromotions.add(outputPromotion1);
		outputPromotions.add(outerCommVarPromotion);
		
		OutputPromotion commOutputPromotion = new OutputPromotion(commVar1Out, aFunc1, OutputPromotionContext.FUNCTION);
		OutputPromotions communicationOutputPromotions = new OutputPromotions();
		communicationOutputPromotions.add(commOutputPromotion);
		
		
		ComplexOutputPromotionMapping communicatingOutputPromotions = Mockito.mock(ComplexOutputPromotionMapping.class);
		Mockito.when(communicatingOutputPromotions.getOtherPromotions()).thenReturn(outputPromotions);
		Mockito.when(communicatingOutputPromotions.getCommunicationPromotions()).thenReturn(communicationOutputPromotions);
		Mockito.when(communicatingOutputPromotions.getAllCommunicationVars()).thenReturn(new Declarations(commVar1));
		Mockito.when(communicatingOutputPromotions.getVisibleCommunicationVars()).thenReturn(Declarations.empty());
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(commVar2Out);
		tempVarMap.add(commVar3In);
		
		ST outputPromotionsST = predicateTemplateProvider.createExistsPostcondition(communicatingOutputPromotions, tempVarMap );
		Assert.assertEquals("(exists tempVar3:nat :- "
				+ "(tempVar3 = a.func1.commVar1_out &"
				+ " result.outputVar1_out = b.func2(tempVar3, tempVar2).outputVar1_out &"
				+ " tempVar1 = b.func2(tempVar3, tempVar2).commVar2_out))", outputPromotionsST.render());
		
	}

	private OperationPromotion getOperationPromotionMock(String caller, String name, Declarations inputParameters, Declarations outputParamters) {
		OperationPromotion opPromo = Mockito.mock(OperationPromotion.class);
		IOperation promotedOp = Mockito.mock(IOperation.class);
		Mockito.when(opPromo.getPromotedOperation()).thenReturn(promotedOp);
		Mockito.when(opPromo.getCaller()).thenReturn(new ST(caller));
		Mockito.when(opPromo.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(opPromo.getOutputParameters()).thenReturn(outputParamters);
		Mockito.when(promotedOp.getName()).thenReturn(name);
		Mockito.when(promotedOp.getPostconditionFunctionName()).thenReturn(name);
		return opPromo;
	}


}
