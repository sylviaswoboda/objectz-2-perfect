package at.ac.tuwien.oz.translator.templates.postconditions;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexOutputPromotionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ComplexOutputPromotions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class CreateSatisfyPostconditionForSequentialCompositionTest {

	private static PerfectPredicateTemplateProvider templateProvider;
	private static PostconditionTestMockProvider postconditionMockProvider;
	
	private static Variable outVar1;
	private static Variable outVar2;
	private static Variable outVar3;
	
	private static Variable commVar;
	private static Variable commVarIn;
	private static Variable commVarOut;

	private static Variable outerCommVar;
	private static Variable outerCommVarIn;
	private static Variable outerCommVarOut;

	
	private static ExpressionType type;
	
	@BeforeClass
	public static void setupClass(){
		templateProvider = PerfectPredicateTemplateProvider.getInstance();
		postconditionMockProvider = new PostconditionTestMockProvider();
		type = ExpressionType.getNat();
		
		outVar1 = Variable.createOutputVariable("outVar1", type);
		outVar2 = Variable.createOutputVariable("outVar2", type);
		outVar3 = Variable.createOutputVariable("outVar3", type);

		commVar = Variable.createUndecoratedVariable("commVar", type);
		commVarIn = Variable.createInputVariable("commVar", type);
		commVarOut = Variable.createOutputVariable("commVar", type);
		
		outerCommVar = Variable.createUndecoratedVariable("outerCommVar", type);
		outerCommVarIn = Variable.createInputVariable("outerCommVar", type);
		outerCommVarOut = Variable.createOutputVariable("outerCommVar", type);

	}

	@Before
	public void setup(){
		TempVarProvider.resetNameCounter();
	}
	@Test
	public void noCommunicationVariablesProduceSimpleOutputPromotionConjunction(){
		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		OutputPromotion outputPromoMock1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func1", Declarations.empty(), outVar1);
		OutputPromotion outputPromoMock2 = postconditionMockProvider.buildOutputPromotionMockInSchema("b", "func2", Declarations.empty(), outVar2);
		IComposablePostconditions functions1 = new OutputPromotions(outputPromoMock1);
		IComposablePostconditions functions2 = new OutputPromotions(outputPromoMock2);

		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(Arrays.asList(functions1, functions2));
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(Declarations.empty());
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(Declarations.empty()));
		
		ST result = templateProvider.createSatisfyPostcondition(sequentialPostconditions);
		
		String expectedResult = "result.outVar1_out = a.func1.outVar1_out & "
							  + "result.outVar2_out = b.func2.outVar2_out";
		
		Assert.assertEquals(expectedResult, result.render());
	}

	@Test
	public void simpleAndComplexOutputPromotionsHaveExistPostconditionAsConjunction(){
		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		
		
		OutputPromotion outputPromoMock1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func1", Declarations.empty(), outVar1);
		OutputPromotion inPromotion = postconditionMockProvider.buildOutputPromotionMockInSchema("b", "func2", new Declarations(commVarIn), outVar2);
		OutputPromotion outPromotion = postconditionMockProvider.buildOutputPromotionMockInSchema("c", "func3", Declarations.empty(), commVarOut);

		ComplexOutputPromotionMapping mapping = new ComplexOutputPromotionMapping(new OutputPromotions(inPromotion, outPromotion), 
				new Declarations(commVar), new Declarations(commVar));
		
		IComposablePostconditions left = new OutputPromotions(outputPromoMock1);
		IComposablePostconditions right = new ComplexOutputPromotions(OutputPromotions.empty(), mapping);

		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(Arrays.asList(left, right));
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(Declarations.empty());
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(Declarations.empty()));
		
		ST result = templateProvider.createSatisfyPostcondition(sequentialPostconditions);
		
		String expectedResult = "result.outVar1_out = a.func1.outVar1_out & "
							  + "(exists tempVar1:nat :- "
							      + "(tempVar1 = c.func3.commVar_out & "
							      +  "result.outVar2_out = b.func2(tempVar1).outVar2_out & "
							      +  "result.commVar_out = tempVar1))";
		
		Assert.assertEquals(expectedResult, result.render());
	}

	@Test
	public void doesNotSurroundSingleComposablePostconditionsWithParentheses(){
		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		OutputPromotion outputPromoMock1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func1", Declarations.empty(), outVar1);
		OutputPromotion outputPromoMock2 = postconditionMockProvider.buildOutputPromotionMockInSchema("b", "func2", Declarations.empty(), outVar2);
		OutputPromotion outputPromoMock3 = postconditionMockProvider.buildOutputPromotionMockInSchema("c", "func3", Declarations.empty(), outVar3);
		IComposablePostconditions functions1 = new OutputPromotions(outputPromoMock1);
		IComposablePostconditions functions2 = new OutputPromotions(outputPromoMock2, outputPromoMock3);

		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(Arrays.asList(functions1, functions2));
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(Declarations.empty());
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(Declarations.empty()));
		
		ST result = templateProvider.createSatisfyPostcondition(sequentialPostconditions);
		
		String expectedResult = "result.outVar1_out = a.func1.outVar1_out & "
							  + "result.outVar2_out = b.func2.outVar2_out & "
							  + "result.outVar3_out = c.func3.outVar3_out";
		
		Assert.assertEquals(expectedResult, result.render());
	}

	@Test
	public void communicationVariablesProduceExistsPostcondition(){
		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		OutputPromotion outputPromoMock1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func1", Declarations.empty(), outerCommVarOut);
		OutputPromotion outputPromoMock2 = postconditionMockProvider.buildOutputPromotionMockInSchema("b", "func2", new Declarations(outerCommVarIn), outVar2);
		IComposablePostconditions functions1 = new OutputPromotions(outputPromoMock1);
		IComposablePostconditions functions2 = new OutputPromotions(outputPromoMock2);
	
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(Arrays.asList(functions1, functions2));
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations(outerCommVar));
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(new Declarations(outerCommVar)));

		ST result = templateProvider.createSatisfyPostcondition(sequentialPostconditions);
		
		String expectedResult = "(exists tempVar1:nat :- "
				                  + "(tempVar1 = a.func1.outerCommVar_out & "
				                  +  "result.outVar2_out = b.func2(tempVar1).outVar2_out))";
		
		Assert.assertEquals(expectedResult, result.render());
	}

	@Test
	public void communicationVariableProduceAdditionalExistsPostcondition(){
		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		
		OutputPromotion outputPromoMock1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func1", Declarations.empty(), outerCommVarOut);
		
		OutputPromotion inPromotion = postconditionMockProvider.buildOutputPromotionMockInSchema("b", "func2", new Declarations(commVarIn), outVar2);
		OutputPromotion outPromotion = postconditionMockProvider.buildOutputPromotionMockInSchema("c", "func3", new Declarations(outerCommVarIn), commVarOut);
	
		ComplexOutputPromotionMapping mapping = new ComplexOutputPromotionMapping(new OutputPromotions(inPromotion, outPromotion), 
				new Declarations(commVar), new Declarations(commVar));
		
		IComposablePostconditions left = new OutputPromotions(outputPromoMock1);
		IComposablePostconditions right = new ComplexOutputPromotions(OutputPromotions.empty(), mapping);
	
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(Arrays.asList(left, right));
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations(outerCommVar));
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(new Declarations(outerCommVar)));

		ST result = templateProvider.createSatisfyPostcondition(sequentialPostconditions);
		
		String expectedResult = "(exists tempVar1:nat :- "
				                + "(tempVar1 = a.func1.outerCommVar_out & "
							    +  "(exists tempVar2:nat :- "
							        + "(tempVar2 = c.func3(tempVar1).commVar_out & "
							        +  "result.outVar2_out = b.func2(tempVar2).outVar2_out & "
							        +  "result.commVar_out = tempVar2))))";
		
		Assert.assertEquals(expectedResult, result.render());
	}

	@Test
	public void communicationVariableComplexToSimpleProduceAdditionalExistsPostcondition(){
		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		
		OutputPromotion inPromotion = postconditionMockProvider.buildOutputPromotionMockInSchema("b", "func2", new Declarations(commVarIn), outerCommVarOut);
		OutputPromotion outPromotion = postconditionMockProvider.buildOutputPromotionMockInSchema("c", "func3", Declarations.empty(), commVarOut);
		
		OutputPromotion outputPromoMock1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func1", new Declarations(outerCommVarIn), outVar1);
		
		ComplexOutputPromotionMapping mapping = new ComplexOutputPromotionMapping(new OutputPromotions(inPromotion, outPromotion), 
				new Declarations(commVar), new Declarations(commVar));
		
		IComposablePostconditions left = new ComplexOutputPromotions(OutputPromotions.empty(), mapping);
		IComposablePostconditions right = new OutputPromotions(outputPromoMock1);
	
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(Arrays.asList(left, right));
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations(outerCommVar));
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(new Declarations(outerCommVar)));
		
		ST result = templateProvider.createSatisfyPostcondition(sequentialPostconditions);
		
		String expectedResult = "(exists tempVar1:nat :- "
				                +  "((exists tempVar2:nat :- "
							         + "(tempVar2 = c.func3.commVar_out & "
							         +  "tempVar1 = b.func2(tempVar2).outerCommVar_out & "
							         +  "result.commVar_out = tempVar2)) & "
							      + "result.outVar1_out = a.func1(tempVar1).outVar1_out))";
		
		Assert.assertEquals(expectedResult, result.render());
	}

}
