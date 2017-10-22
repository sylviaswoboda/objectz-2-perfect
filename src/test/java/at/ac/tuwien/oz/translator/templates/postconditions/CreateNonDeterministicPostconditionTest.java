package at.ac.tuwien.oz.translator.templates.postconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.NonDeterministicChoiceItem;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ChangeOperationCalls;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.PreconditionFunctionCalls;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions.PreconditionItem;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.NonDeterministicChoiceTemplateProvider;
import at.ac.tuwien.oz.translator.templates.preconditions.PreconditionTestMockProvider;

public class CreateNonDeterministicPostconditionTest {
	
	private NonDeterministicChoiceTemplateProvider provider = new NonDeterministicChoiceTemplateProvider();
	
	private PreconditionTestMockProvider preconditionMockProvider = new PreconditionTestMockProvider();
	private PostconditionTestMockProvider postconditionMockProvider = new PostconditionTestMockProvider();

	private NonDeterministicPostconditions nonDeterministicPostconditions;

	private List<NonDeterministicChoiceItem> postconditionItems;
	
	private ExpressionType type = ExpressionType.getNat();
	private Variable commVar1In = Variable.createInputVariable("commVar1", type);
	private Variable commVar1Out = Variable.createOutputVariable("commVar1", type);
	private Variable outVar1Out = Variable.createOutputVariable("outVar1", type);
	
	private void withNonDeterministicChoiceItem(NonDeterministicChoiceItem item){
		this.postconditionItems.add(item);
	}

	private NonDeterministicChoiceItem buildNonDeterministicChoiceFunctionItem(
			IComposablePreconditions composablePrecondition, 
			List<IPromotedOperation> promotingOperations,
			IComposablePostconditions composablePostcondition,
			Context context) {
		PreconditionItem preconditionItem = new PreconditionItem(composablePrecondition, promotingOperations);
		NonDeterministicChoiceItem item1 = new NonDeterministicChoiceItem(preconditionItem, composablePostcondition, context);
		return item1;
	}

	@Before
	public void before(){
		TempVarProvider.resetNameCounter();
		nonDeterministicPostconditions = Mockito.mock(NonDeterministicPostconditions.class);
		postconditionItems = new ArrayList<NonDeterministicChoiceItem>();
		Mockito.when(nonDeterministicPostconditions.getElements()).thenReturn(postconditionItems);
	}
	
	@Test
	public void testSimpleFunctionChoice() {
		// a.func1 [] b.func2
		
		IPromotedOperation aFunc1 = 
				postconditionMockProvider.buildFunctionPromotionMock("a", "func1", Declarations.empty(), outVar1Out);
		PreconditionFunctionCall aFunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", Declarations.empty());
		OutputPromotion outputPromotion1 = new OutputPromotion(outVar1Out, aFunc1, OutputPromotion.OutputPromotionContext.FUNCTION);

		NonDeterministicChoiceItem item1Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(aFunc1Prec),
				Arrays.asList(aFunc1),
				new OutputPromotions(outputPromotion1),
				Context.FUNCTION);
		
		IPromotedOperation bFunc2 = 
				postconditionMockProvider.buildFunctionPromotionMock("b", "func2", Declarations.empty(), outVar1Out);
		PreconditionFunctionCall bFunc2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "func2_prec", Declarations.empty());
				
		OutputPromotion outputPromotion2 = new OutputPromotion(outVar1Out, bFunc2, OutputPromotion.OutputPromotionContext.FUNCTION);

		NonDeterministicChoiceItem item2Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(bFunc2Prec),
				Arrays.asList(bFunc2),
				new OutputPromotions(outputPromotion2),
				Context.FUNCTION);
		
		withNonDeterministicChoiceItem(item1Alt);
		withNonDeterministicChoiceItem(item2Alt);
		
		ST resultTemplate = provider.createNonDeterministicPostcondition(nonDeterministicPostconditions, null, Context.FUNCTION);
		
		String expectedResult = 
					"([a.func1_prec]: result.outVar1_out = a.func1.outVar1_out, "
				+    "[b.func2_prec]: result.outVar1_out = b.func2.outVar1_out)";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());

	}

	@Test
	public void testFunctionWithInputCommunication() {
		// a.func1 [] b.func2
		
		IPromotedOperation aFunc1 = 
				postconditionMockProvider.buildFunctionPromotionMock("a", "func1", new Declarations(commVar1In), outVar1Out);
		PreconditionFunctionCall aFunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", new Declarations(commVar1In));
		OutputPromotion outputPromotion1 = new OutputPromotion(outVar1Out, aFunc1, OutputPromotion.OutputPromotionContext.FUNCTION);

		NonDeterministicChoiceItem item1Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(aFunc1Prec),
				Arrays.asList(aFunc1),
				new OutputPromotions(outputPromotion1),
				Context.FUNCTION);
		
		IPromotedOperation bFunc2 = 
				postconditionMockProvider.buildFunctionPromotionMock("b", "func2", new Declarations(commVar1In), outVar1Out);
		PreconditionFunctionCall bFunc2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "func2_prec", new Declarations(commVar1In));
				
		OutputPromotion outputPromotion2 = new OutputPromotion(outVar1Out, bFunc2, OutputPromotion.OutputPromotionContext.FUNCTION);

		NonDeterministicChoiceItem item2Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(bFunc2Prec),
				Arrays.asList(bFunc2),
				new OutputPromotions(outputPromotion2),
				Context.FUNCTION);
		
		withNonDeterministicChoiceItem(item1Alt);
		withNonDeterministicChoiceItem(item2Alt);
		
		TemporaryVariableMap temporaryVariables = new TemporaryVariableMap();
		temporaryVariables.add(commVar1In);
		
		ST resultTemplate = provider.createNonDeterministicPostcondition(nonDeterministicPostconditions, temporaryVariables, Context.FUNCTION);
		
		String expectedResult = 
					"([a.func1_prec(tempVar1)]: result.outVar1_out = a.func1(tempVar1).outVar1_out, "
				+    "[b.func2_prec(tempVar1)]: result.outVar1_out = b.func2(tempVar1).outVar1_out)";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());

	}

	@Test
	public void testFunctionWithOutputCommunication() {
		IPromotedOperation aFunc1 = 
				postconditionMockProvider.buildFunctionPromotionMock("a", "func1", Declarations.empty(), commVar1Out);
		PreconditionFunctionCall aFunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", Declarations.empty());
		OutputPromotion outputPromotion1 = new OutputPromotion(commVar1Out, aFunc1, OutputPromotion.OutputPromotionContext.FUNCTION);
		NonDeterministicChoiceItem item1 = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(aFunc1Prec),
				Arrays.asList(aFunc1),
				new OutputPromotions(outputPromotion1),
				Context.FUNCTION);
		
		IPromotedOperation bFunc2 = 
				postconditionMockProvider.buildFunctionPromotionMock("b", "func2", Declarations.empty(), commVar1Out);
		PreconditionFunctionCall bFunc2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "func2_prec", Declarations.empty());
		OutputPromotion outputPromotion2 = new OutputPromotion(commVar1Out, bFunc2, OutputPromotion.OutputPromotionContext.FUNCTION);
		NonDeterministicChoiceItem item2 = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(bFunc2Prec),
				Arrays.asList(bFunc2),
				new OutputPromotions(outputPromotion2),
				Context.FUNCTION);
		
		withNonDeterministicChoiceItem(item1);
		withNonDeterministicChoiceItem(item2);
		
		TemporaryVariableMap temporaryVariables = new TemporaryVariableMap();
		temporaryVariables.add(commVar1Out);
		
		ST resultTemplate = provider.createNonDeterministicPostcondition(nonDeterministicPostconditions, temporaryVariables, Context.FUNCTION);
		
		String expectedResult = 
					"([a.func1_prec & tempVar1 = a.func1.commVar1_out]: tempVar1 = a.func1.commVar1_out, "
				+    "[b.func2_prec & tempVar1 = b.func2.commVar1_out]: tempVar1 = b.func2.commVar1_out)";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());

	}
	
	@Test
	public void testSimpleSchemaChoice() {
		// a.changeOp1 [] b.changeOp2
		
		IPromotedOperation aChangeOp1 = 
				postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", Declarations.empty(), new Declarations(outVar1Out));
		PreconditionFunctionCall aChangeOp1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "changeOp1_prec", Declarations.empty());
		ChangeOperationCall changeOpCall1 = new ChangeOperationCall(aChangeOp1);

		NonDeterministicChoiceItem item1Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(aChangeOp1Prec),
				Arrays.asList(aChangeOp1),
				new ChangeOperationCalls(changeOpCall1),
				Context.SCHEMA);
		
		IPromotedOperation bChangeOp2 = 
				postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp2", Declarations.empty(), new Declarations(outVar1Out));
		PreconditionFunctionCall bChangeOp2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp2_prec", Declarations.empty());
				
		ChangeOperationCall changeOpCall2 = new ChangeOperationCall(bChangeOp2);

		NonDeterministicChoiceItem item2Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(bChangeOp2Prec),
				Arrays.asList(bChangeOp2),
				new ChangeOperationCalls(changeOpCall2),
				Context.SCHEMA);
		
		withNonDeterministicChoiceItem(item1Alt);
		withNonDeterministicChoiceItem(item2Alt);
		
		ST resultTemplate = provider.createNonDeterministicPostcondition(nonDeterministicPostconditions, null, Context.SCHEMA);
		
		String expectedResult = 
					"([a.changeOp1_prec]: a!changeOp1(outVar1_out!), "
				+    "[b.changeOp2_prec]: b!changeOp2(outVar1_out!))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void testSchemaWithInputCommunication() {
		// a.changeOp1 [] b.changeOp2
		
		IPromotedOperation aChangeOp1 = 
				postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(commVar1In), new Declarations(outVar1Out));
		PreconditionFunctionCall aChangeOp1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "changeOp1_prec", new Declarations(commVar1In));
		ChangeOperationCall changeOpCall1 = new ChangeOperationCall(aChangeOp1);

		NonDeterministicChoiceItem item1Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(aChangeOp1Prec),
				Arrays.asList(aChangeOp1),
				new ChangeOperationCalls(changeOpCall1),
				Context.SCHEMA);
		
		IPromotedOperation bChangeOp2 = 
				postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp2", new Declarations(commVar1In), new Declarations(outVar1Out));
		PreconditionFunctionCall bChangeOp2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp2_prec", new Declarations(commVar1In));
				
		ChangeOperationCall changeOpCall2 = new ChangeOperationCall(bChangeOp2);

		NonDeterministicChoiceItem item2Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(bChangeOp2Prec),
				Arrays.asList(bChangeOp2),
				new ChangeOperationCalls(changeOpCall2),
				Context.SCHEMA);
		
		withNonDeterministicChoiceItem(item1Alt);
		withNonDeterministicChoiceItem(item2Alt);
		
		TemporaryVariableMap temporaryVariables = new TemporaryVariableMap();
		temporaryVariables.add(commVar1In);
		
		ST resultTemplate = provider.createNonDeterministicPostcondition(nonDeterministicPostconditions, temporaryVariables, Context.SCHEMA);
		
		String expectedResult = 
					"([a.changeOp1_prec(tempVar1)]: a!changeOp1(tempVar1, outVar1_out!), "
				+    "[b.changeOp2_prec(tempVar1)]: b!changeOp2(tempVar1, outVar1_out!))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void testSchemaWithOutputCommunication() {
		// a.changeOp1 [] b.changeOp2
		
		IPromotedOperation aChangeOp1 = 
				postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", Declarations.empty(), new Declarations(commVar1Out));
		PreconditionFunctionCall aChangeOp1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "changeOp1_prec", Declarations.empty());
		ChangeOperationCall changeOpCall1 = new ChangeOperationCall(aChangeOp1);

		NonDeterministicChoiceItem item1Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(aChangeOp1Prec),
				Arrays.asList(aChangeOp1),
				new ChangeOperationCalls(changeOpCall1),
				Context.SCHEMA);
		
		IPromotedOperation bChangeOp2 = 
				postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp2", Declarations.empty(), new Declarations(commVar1Out));
		PreconditionFunctionCall bChangeOp2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp2_prec", Declarations.empty());
				
		ChangeOperationCall changeOpCall2 = new ChangeOperationCall(bChangeOp2);

		NonDeterministicChoiceItem item2Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(bChangeOp2Prec),
				Arrays.asList(bChangeOp2),
				new ChangeOperationCalls(changeOpCall2),
				Context.SCHEMA);
		
		withNonDeterministicChoiceItem(item1Alt);
		withNonDeterministicChoiceItem(item2Alt);
		
		TemporaryVariableMap temporaryVariables = new TemporaryVariableMap();
		temporaryVariables.add(commVar1In);
		
		ST resultTemplate = provider.createNonDeterministicPostcondition(nonDeterministicPostconditions, temporaryVariables, Context.SCHEMA);
		
		String expectedResult = 
					"([a.changeOp1_prec & tempVar1 = a.changeOp1.commVar1_out]: a!changeOp1(tempVar1!), "
				+    "[b.changeOp2_prec & tempVar1 = b.changeOp2.commVar1_out]: b!changeOp2(tempVar1!))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void testFunctionAndChangeOpChoice() {
		// a.func1 [] b.changeOp2
		
		IPromotedOperation aFunc1 = 
				postconditionMockProvider.buildFunctionPromotionMock("a", "func1", Declarations.empty(), outVar1Out);
		PreconditionFunctionCall aFunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", Declarations.empty());
		OutputPromotion outputPromotion1 = new OutputPromotion(outVar1Out, aFunc1, OutputPromotion.OutputPromotionContext.FUNCTION);

		NonDeterministicChoiceItem item1Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(aFunc1Prec),
				Arrays.asList(aFunc1),
				new OutputPromotions(outputPromotion1),
				Context.SCHEMA);
		
		IPromotedOperation bChangeOp2 = 
				postconditionMockProvider.buildFunctionPromotionMock("b", "changeOp2", Declarations.empty(), outVar1Out);
		PreconditionFunctionCall bChangeOp2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "changeOp2_prec", Declarations.empty());
				
		ChangeOperationCall changeOpCall2 = new ChangeOperationCall(bChangeOp2);

		NonDeterministicChoiceItem item2Alt = buildNonDeterministicChoiceFunctionItem(
				new PreconditionFunctionCalls(bChangeOp2Prec),
				Arrays.asList(bChangeOp2),
				new ChangeOperationCalls(changeOpCall2),
				Context.SCHEMA);
		
		withNonDeterministicChoiceItem(item1Alt);
		withNonDeterministicChoiceItem(item2Alt);
		
		ST resultTemplate = provider.createNonDeterministicPostcondition(nonDeterministicPostconditions, null, Context.SCHEMA);
		
		String expectedResult = 
					"([a.func1_prec]: outVar1_out! = a.func1.outVar1_out, "
				+    "[b.changeOp2_prec]: b!changeOp2(outVar1_out!))";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());

	}

}
