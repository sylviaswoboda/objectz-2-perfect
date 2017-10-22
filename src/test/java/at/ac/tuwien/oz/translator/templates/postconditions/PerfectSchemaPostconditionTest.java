package at.ac.tuwien.oz.translator.templates.postconditions;

import java.util.HashMap;
import java.util.Map;

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
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.InputSubstitutionType;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class PerfectSchemaPostconditionTest {

	private PerfectPredicateTemplateProvider predicateTemplateProvider;
	
	private ST caller;
	private Declarations inputParameters;
	private Declarations outputParameters;
	
	private ExpressionType type; 
	
	@Mock private IOperation func;
	@Mock private IOperation changeOp;
	@Mock private OperationPromotion operationPromotion;
	@Mock private OperationPromotion anonymousOpPromo;
	
	@Mock private Variable inputVariable1;
	@Mock private Variable inputVariable2;

	@Mock private Variable outputVariable1;
	@Mock private Variable outputVariable2;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		predicateTemplateProvider = PerfectPredicateTemplateProvider.getInstance();
		caller = new ST("someCaller");
		inputParameters = new Declarations();		
		outputParameters = new Declarations();
		
		type = ExpressionType.getNat();
		
		TempVarProvider.resetNameCounter();
		
		Mockito.when(func.getName()).thenReturn("func");
		Mockito.when(func.getPostconditionFunctionName()).thenReturn("func");
		Mockito.when(changeOp.getName()).thenReturn("changeOp");
		Mockito.when(operationPromotion.getName()).thenReturn("opPromo");
		
		inputVariable1 = Variable.createInputVariable("inputVar1", type);
		inputVariable2 = Variable.createInputVariable("inputVar2", type);
		outputVariable1 = Variable.createOutputVariable("outputVar1", type);
		outputVariable2 = Variable.createOutputVariable("outputVar2", type);
		
		Mockito.when(anonymousOpPromo.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(anonymousOpPromo.getOutputParameters()).thenReturn(outputParameters);
	}

	@Test
	public void testCreateOutputPromotionInSchema_WithInputParameters() {
		inputParameters.add(inputVariable1);
		
		Mockito.when(operationPromotion.getPromotedOperation()).thenReturn(func);
		Mockito.when(operationPromotion.getInputParameters()).thenReturn(inputParameters);
		
		OutputPromotion outputPromotion = Mockito.mock(OutputPromotion.class);
		Mockito.when(outputPromotion.getOutputVariable()).thenReturn(outputVariable1);
		Mockito.when(outputPromotion.getPromotedOperation()).thenReturn(operationPromotion);
		
		ST outputPromotionST = predicateTemplateProvider.createOutputPromotionInSchema(outputPromotion);
		Assert.assertEquals("outputVar1_out! = func(inputVar1_in).outputVar1_out", outputPromotionST.render());
		
		Mockito.when(outputPromotion.getOutputVariable()).thenReturn(outputVariable2);
		
		outputPromotionST = predicateTemplateProvider.createOutputPromotionInSchema(outputPromotion);
		Assert.assertEquals("outputVar2_out! = func(inputVar1_in).outputVar2_out", outputPromotionST.render());
	}

	@Test
	public void testCreateOutputPromotionInSchema_WithInputParametersAndCaller() {
		inputParameters.add(inputVariable1);
		inputParameters.add(inputVariable2);
		
		Mockito.when(operationPromotion.getPromotedOperation()).thenReturn(func);
		Mockito.when(operationPromotion.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(operationPromotion.getCaller()).thenReturn(caller);
		
		OutputPromotion outputPromotion = Mockito.mock(OutputPromotion.class);
		Mockito.when(outputPromotion.getOutputVariable()).thenReturn(outputVariable1);
		Mockito.when(outputPromotion.getPromotedOperation()).thenReturn(operationPromotion);
		
		ST outputPromotionST = predicateTemplateProvider.createOutputPromotionInSchema(outputPromotion);
		Assert.assertEquals("outputVar1_out! = someCaller.func(inputVar1_in, inputVar2_in).outputVar1_out", outputPromotionST.render());
	}

	@Test
	public void testCreateOutputPromotionInSchema_WithInputParametersAndCallerAndTempVar() {
		inputParameters.add(inputVariable1);
		inputParameters.add(inputVariable2);
		
		Variable tempVar = Variable.createUndecoratedVariable("tempVar1", type);
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(outputVariable1, tempVar);
		
		Mockito.when(operationPromotion.getPromotedOperation()).thenReturn(func);
		Mockito.when(operationPromotion.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(operationPromotion.getCaller()).thenReturn(caller);
		
		OutputPromotion outputPromotion = Mockito.mock(OutputPromotion.class);
		Mockito.when(outputPromotion.getOutputVariable()).thenReturn(outputVariable1);
		Mockito.when(outputPromotion.getPromotedOperation()).thenReturn(operationPromotion);
		
		ST outputPromotionST = predicateTemplateProvider.createOutputPromotionInSchema(outputPromotion, tempVarMap, InputSubstitutionType.DO_NOT_PRIME);
		Assert.assertEquals("tempVar1! = someCaller.func(inputVar1_in, inputVar2_in).outputVar1_out", outputPromotionST.render());
	}
	
	@Test
	public void testCreateChangeOperationCall_WithInputAndOutputParameters() {
		inputParameters.add(inputVariable1);
		outputParameters.add(outputVariable1);
		
		Mockito.when(anonymousOpPromo.getCaller()).thenReturn(null);
		Mockito.when(anonymousOpPromo.getPromotedOperation()).thenReturn(changeOp);

		ST promotedSchemaCall = predicateTemplateProvider.createChangeOperationCall(anonymousOpPromo);
		Assert.assertEquals("!changeOp(inputVar1_in, outputVar1_out!)", promotedSchemaCall.render());
	}

	@Test
	public void testCreateChangeOperationCall_WithInputAndOutputParametersWithCaller() {
		inputParameters.add(inputVariable1);
		outputParameters.add(outputVariable1);
		
		Mockito.when(anonymousOpPromo.getCaller()).thenReturn(caller);
		Mockito.when(anonymousOpPromo.getPromotedOperation()).thenReturn(changeOp);
		
		ST promotedSchemaCall = predicateTemplateProvider.createChangeOperationCall(anonymousOpPromo);
		Assert.assertEquals("someCaller!changeOp(inputVar1_in, outputVar1_out!)", promotedSchemaCall.render());
	}

	@Test
	public void testCreateChangeOperationCall_WithInputParametersOnly() {
		inputParameters.add(inputVariable1);
		
		Mockito.when(anonymousOpPromo.getCaller()).thenReturn(null);
		Mockito.when(anonymousOpPromo.getPromotedOperation()).thenReturn(changeOp);
		
		ST promotedSchemaCall = predicateTemplateProvider.createChangeOperationCall(anonymousOpPromo);
		Assert.assertEquals("!changeOp(inputVar1_in)", promotedSchemaCall.render());
	}
	
	@Test
	public void testCreateChangeOperationCall_WithOutputParametersOnly() {
		outputParameters.add(outputVariable1);
		
		Mockito.when(anonymousOpPromo.getCaller()).thenReturn(null);
		Mockito.when(anonymousOpPromo.getPromotedOperation()).thenReturn(changeOp);
		
		ST promotedSchemaCall = predicateTemplateProvider.createChangeOperationCall(anonymousOpPromo);
		Assert.assertEquals("!changeOp(outputVar1_out!)", promotedSchemaCall.render());
	}

	@Test
	public void testCreateChangeOperationCall_WithoutParameters() {
		Mockito.when(anonymousOpPromo.getCaller()).thenReturn(null);
		Mockito.when(anonymousOpPromo.getPromotedOperation()).thenReturn(changeOp);
		
		ST promotedSchemaCall = predicateTemplateProvider.createChangeOperationCall(anonymousOpPromo);
		Assert.assertEquals("!changeOp", promotedSchemaCall.render());
	}
	
	@Test
	public void testCreateChangeOperationCall_WithTemporaryVariables_doSubstitute() {
		inputParameters.add(inputVariable1);
		outputParameters.add(outputVariable1);
		
		Mockito.when(anonymousOpPromo.getCaller()).thenReturn(null);
		Mockito.when(anonymousOpPromo.getPromotedOperation()).thenReturn(changeOp);

		Map<Variable, Boolean> substituteSharedOutputMap = new HashMap<Variable, Boolean>();
		substituteSharedOutputMap.put(outputVariable1, Boolean.TRUE);
		
		Variable tempVar = Variable.createUndecoratedVariable("tempVar", type);
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(outputVariable1, tempVar);
		
		ST promotedSchemaCall = predicateTemplateProvider.createChangeOperationCall(anonymousOpPromo, tempVarMap, substituteSharedOutputMap, InputSubstitutionType.NONE);
		Assert.assertEquals("!changeOp(inputVar1_in, tempVar!)", promotedSchemaCall.render());
	}

	@Test
	public void testCreateChangeOperationCall_WithTemporaryVariables_doNotSubstitute() {
		inputParameters.add(inputVariable1);
		outputParameters.add(outputVariable1);
		
		Mockito.when(anonymousOpPromo.getCaller()).thenReturn(null);
		Mockito.when(anonymousOpPromo.getPromotedOperation()).thenReturn(changeOp);

		Map<Variable, Boolean> substituteSharedOutputMap = new HashMap<Variable, Boolean>();
		substituteSharedOutputMap.put(outputVariable1, Boolean.FALSE);
		
		Variable tempVar = Variable.createUndecoratedVariable("tempVar", type);
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(outputVariable1, tempVar);
		
		ST promotedSchemaCall = predicateTemplateProvider.createChangeOperationCall(anonymousOpPromo, tempVarMap, substituteSharedOutputMap, InputSubstitutionType.NONE);
		Assert.assertEquals("!changeOp(inputVar1_in, outputVar1_out!)", promotedSchemaCall.render());
	}

	@Test
	public void testCreateChangeOperationCall_WithTemporaryVariables_notInTempVarMap() {
		inputParameters.add(inputVariable1);
		outputParameters.add(outputVariable1);
		
		Mockito.when(anonymousOpPromo.getCaller()).thenReturn(null);
		Mockito.when(anonymousOpPromo.getPromotedOperation()).thenReturn(changeOp);

		Map<Variable, Boolean> substituteSharedOutputMap = new HashMap<Variable, Boolean>();
		substituteSharedOutputMap.put(outputVariable1, Boolean.TRUE);
		
		ST promotedSchemaCall = predicateTemplateProvider.createChangeOperationCall(anonymousOpPromo, new TemporaryVariableMap(), substituteSharedOutputMap, InputSubstitutionType.NONE);
		Assert.assertEquals("!changeOp(inputVar1_in, outputVar1_out!)", promotedSchemaCall.render());
	}
}
