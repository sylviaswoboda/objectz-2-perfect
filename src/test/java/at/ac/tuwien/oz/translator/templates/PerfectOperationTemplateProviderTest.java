package at.ac.tuwien.oz.translator.templates;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ChangeOperationCalls;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePrecondition;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.translator.TempVarProvider;

public class PerfectOperationTemplateProviderTest {

	private PerfectOperationTemplateProvider operationTemplateProvider;
	
	private ST caller;
	private Declarations inputParameters;
	private Declarations outputParameters;
	
	@Mock private IOperation boolFunc;
	@Mock private OperationPromotion operationPromotion;
	
	@Mock private Variable inputVariable1;
	@Mock private Variable inputVariable2;
	
	@Mock private Ident inputIdent1;
	@Mock private Ident inputIdent2;

	@Mock private Variable outputVariable1;

	@Mock private Ident outputIdent1;

	@Mock private IOperation promotedOperation;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		operationTemplateProvider = PerfectOperationTemplateProvider.getInstance();
		caller = new ST("someCaller");
		inputParameters = new Declarations();		
		outputParameters = new Declarations();
		
		TempVarProvider.resetNameCounter();
		
		Mockito.when(boolFunc.getName()).thenReturn("boolFunc");
		Mockito.when(operationPromotion.getName()).thenReturn("opPromo");
		
		Mockito.when(inputVariable1.isInputVariable()).thenReturn(true);
		Mockito.when(inputVariable2.isInputVariable()).thenReturn(true);
		Mockito.when(inputVariable1.getId()).thenReturn(inputIdent1);
		Mockito.when(inputVariable2.getId()).thenReturn(inputIdent2);
		Mockito.when(inputIdent1.getName()).thenReturn("inputVar1");
		Mockito.when(inputIdent1.getDecoration()).thenReturn("_in");
		Mockito.when(inputIdent2.getName()).thenReturn("inputVar2");
		Mockito.when(inputIdent2.getDecoration()).thenReturn("_in");
		
		Mockito.when(outputVariable1.isOutputVariable()).thenReturn(true);
		Mockito.when(outputVariable1.getId()).thenReturn(outputIdent1);
		Mockito.when(outputIdent1.getName()).thenReturn("outputVar1");
		Mockito.when(outputIdent1.getDecoration()).thenReturn("_out");
		
		ST nat = new ST("nat");
		Mockito.when(inputVariable1.getTypeTemplate()).thenReturn(nat);
		Mockito.when(inputVariable2.getTypeTemplate()).thenReturn(nat);
		Mockito.when(outputVariable1.getTypeTemplate()).thenReturn(nat);
		
		Mockito.when(inputVariable1.compareTo(inputVariable2)).thenReturn(-1);
		Mockito.when(inputVariable2.compareTo(inputVariable1)).thenReturn(1);
	}
	@Test
	public void testGetOperationPromotion_boolFunction(){
		inputParameters.add(inputVariable1);
		Mockito.when(operationPromotion.isBoolFunction()).thenReturn(true);
		Mockito.when(operationPromotion.getInputParameters()).thenReturn(inputParameters);
		
		ST axiom = new ST("boolFunc(inputVar1_in)");
		SimplePreconditions preconditions = new SimplePreconditions();
		SimplePrecondition precondition = new SimplePrecondition(axiom);
		preconditions.add(precondition);
		Mockito.when(operationPromotion.getPreconditions()).thenReturn(preconditions);
		
		
		ST operationPromotionST = operationTemplateProvider.getOperationTemplate(operationPromotion);
		
		String expectedResult = "function opPromo (inputVar1_in:nat): bool\n" +
								"    ^= boolFunc(inputVar1_in);";
		
		Assert.assertEquals(expectedResult, operationPromotionST.render());
	}

	@Test
	public void testGetOperationPromotion_function(){
		inputParameters.add(inputVariable1);
		outputParameters.add(outputVariable1);
		
		Mockito.when(operationPromotion.isFunction()).thenReturn(true);
		Mockito.when(operationPromotion.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(operationPromotion.getOutputParameters()).thenReturn(outputParameters);
		Mockito.when(operationPromotion.getPromotedOperation()).thenReturn(promotedOperation);
		Mockito.when(promotedOperation.getPostconditionFunctionName()).thenReturn("func");
		
		OutputPromotion postcondition = new OutputPromotion(outputVariable1, operationPromotion, OutputPromotionContext.FUNCTION);
		OutputPromotions postconditions = new OutputPromotions(OutputPromotionContext.FUNCTION);
		postconditions.add(postcondition);
		Mockito.when(operationPromotion.getPostconditions()).thenReturn(postconditions);
		
		
		ST operationPromotionST = operationTemplateProvider.getOperationTemplate(operationPromotion);
		
		String expectedResult = "function opPromo (inputVar1_in:nat)outputVar1_out:nat\n" +
								"    satisfy result.outputVar1_out = func(inputVar1_in).outputVar1_out;";
		
		Assert.assertEquals(expectedResult, operationPromotionST.render());
	}

	@Test
	public void testGetOperationPromotion_changeOperation(){
		inputParameters.add(inputVariable1);
		outputParameters.add(outputVariable1);
		
		Mockito.when(operationPromotion.isChangeOperation()).thenReturn(true);
		Mockito.when(operationPromotion.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(operationPromotion.getOutputParameters()).thenReturn(outputParameters);
		Mockito.when(operationPromotion.getModifiableInputParameters()).thenReturn(Declarations.empty());
		Mockito.when(operationPromotion.getPromotedOperation()).thenReturn(promotedOperation);
		Mockito.when(operationPromotion.isOperationExpression()).thenReturn(true);
		Mockito.when(operationPromotion.getCaller()).thenReturn(caller);
		
		Mockito.when(promotedOperation.getName()).thenReturn("changeOp");
		Mockito.when(promotedOperation.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(promotedOperation.getOutputParameters()).thenReturn(outputParameters);
		
		ChangeOperationCall postcondition = new ChangeOperationCall(operationPromotion);
		ChangeOperationCalls postconditions = new ChangeOperationCalls();
		postconditions.add(postcondition);
		Mockito.when(operationPromotion.getPostconditions()).thenReturn(postconditions);
		
		ST operationPromotionST = operationTemplateProvider.getOperationTemplate(operationPromotion);
		
		String expectedResult = "schema !opPromo (inputVar1_in:nat, outputVar1_out!:out nat)\n" +
								"    post someCaller!changeOp(inputVar1_in, outputVar1_out!);";
		
		Assert.assertEquals(expectedResult, operationPromotionST.render());
	}
}
