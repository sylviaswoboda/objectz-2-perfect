package at.ac.tuwien.oz.translator.templates.preconditions;

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
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.PreconditionFunctionCalls;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions.PreconditionItem;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.NonDeterministicChoiceTemplateProvider;
import at.ac.tuwien.oz.translator.templates.postconditions.PostconditionTestMockProvider;

public class CreateNonDeterministicPreconditionTest {

	private NonDeterministicChoiceTemplateProvider provider = new NonDeterministicChoiceTemplateProvider();
	
	private PreconditionTestMockProvider preconditionMockProvider = new PreconditionTestMockProvider();
	private PostconditionTestMockProvider postconditionMockProvider = new PostconditionTestMockProvider();

	private NonDeterministicPreconditions nonDeterministicPreconditions;

	private List<PreconditionItem> preconditionItems;
	
	private static final ExpressionType NAT = ExpressionType.getNat();
	
	@Before
	public void before(){
		TempVarProvider.resetNameCounter();
		nonDeterministicPreconditions = Mockito.mock(NonDeterministicPreconditions.class);
		preconditionItems = new ArrayList<PreconditionItem>();
		Mockito.when(nonDeterministicPreconditions.getPreconditions()).thenReturn(preconditionItems);
	}
	
	private void withPreconditionItem(PreconditionItem precondition){
		this.preconditionItems.add(precondition);
	}
	
	@Test
	public void combiningTwoSimpleComposablePreconditions(){
		// a.func [] b.func
		// =>
		// a.func_prec | b.func_prec 
		
		PreconditionFunctionCall afunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", Declarations.empty());
		PreconditionFunctionCall bfunc2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "func2_prec", Declarations.empty());
		
		IPromotedOperation afunc1 = postconditionMockProvider.buildFunctionPromotionMock("a", "func1", Declarations.empty(), Variable.createOutputVariable("outVar1", NAT));
		IPromotedOperation bfunc2 = postconditionMockProvider.buildFunctionPromotionMock("b", "func2", Declarations.empty(), Variable.createOutputVariable("outVar2", NAT));
		
		PreconditionItem item1 = new PreconditionItem(new PreconditionFunctionCalls(afunc1Prec), Arrays.asList(afunc1));
		PreconditionItem item2 = new PreconditionItem(new PreconditionFunctionCalls(bfunc2Prec), Arrays.asList(bfunc2));
		
		withPreconditionItem(item1);
		withPreconditionItem(item2);
		
		ST resultTemplate = provider.createNonDeterministicPrecondition(nonDeterministicPreconditions, null);
		
		String expectedResult = 
					"a.func1_prec | "
				+   "b.func2_prec";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}
	
	@Test
	public void combiningComposablePreconditionsWithConjunction(){
		// a.func1 [] (b.func2 && c.func3)
		// =>
		// a.func1_prec | b.func2_prec & c.func3_prec
		
		PreconditionFunctionCall afunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", Declarations.empty());
		PreconditionFunctionCall bfunc2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "func2_prec", Declarations.empty());
		PreconditionFunctionCall cfunc3Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("c", "func3_prec", Declarations.empty());
		
		IPromotedOperation afunc1 = postconditionMockProvider.buildFunctionPromotionMock("a", "func1", Declarations.empty(), Variable.createOutputVariable("outVar1", NAT));
		IPromotedOperation bfunc2 = postconditionMockProvider.buildFunctionPromotionMock("b", "func2", Declarations.empty(), Variable.createOutputVariable("outVar2", NAT));
		IPromotedOperation cfunc3 = postconditionMockProvider.buildFunctionPromotionMock("c", "func3", Declarations.empty(), Variable.createOutputVariable("outVar3", NAT));
		
		PreconditionItem item1 = new PreconditionItem(new PreconditionFunctionCalls(afunc1Prec), Arrays.asList(afunc1));
		PreconditionItem item2 = new PreconditionItem(new PreconditionFunctionCalls(bfunc2Prec, cfunc3Prec), Arrays.asList(bfunc2, cfunc3));
		
		withPreconditionItem(item1);
		withPreconditionItem(item2);
		
		ST resultTemplate = provider.createNonDeterministicPrecondition(nonDeterministicPreconditions, null);
		
		String expectedResult = 
					"a.func1_prec | "
				+   "b.func2_prec & "
				+   "c.func3_prec";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}
	
	@Test
	public void combiningThreeComposablePreconditions(){
		// a.func1 [] (b.func2 && c.func3)
		// =>
		// a.func1_prec | b.func2_prec & c.func3_prec
		
		PreconditionFunctionCall afunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", Declarations.empty());
		PreconditionFunctionCall bfunc2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "func2_prec", Declarations.empty());
		PreconditionFunctionCall cfunc3Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("c", "func3_prec", Declarations.empty());
		PreconditionFunctionCall dfunc4Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("d", "func4_prec", Declarations.empty());
		
		IPromotedOperation afunc1 = postconditionMockProvider.buildFunctionPromotionMock("a", "func1", Declarations.empty(), Variable.createOutputVariable("outVar1", NAT));
		IPromotedOperation bfunc2 = postconditionMockProvider.buildFunctionPromotionMock("b", "func2", Declarations.empty(), Variable.createOutputVariable("outVar2", NAT));
		IPromotedOperation cfunc3 = postconditionMockProvider.buildFunctionPromotionMock("c", "func3", Declarations.empty(), Variable.createOutputVariable("outVar3", NAT));
		IPromotedOperation dfunc4 = postconditionMockProvider.buildFunctionPromotionMock("d", "func4", Declarations.empty(), Variable.createOutputVariable("outVar4", NAT));
		
		PreconditionItem item1 = new PreconditionItem(new PreconditionFunctionCalls(afunc1Prec), Arrays.asList(afunc1));
		PreconditionItem item2 = new PreconditionItem(new PreconditionFunctionCalls(bfunc2Prec, cfunc3Prec), Arrays.asList(bfunc2, cfunc3));
		PreconditionItem item3 = new PreconditionItem(new PreconditionFunctionCalls(dfunc4Prec), Arrays.asList(dfunc4));
		
		withPreconditionItem(item1);
		withPreconditionItem(item2);
		withPreconditionItem(item3);
		
		ST resultTemplate = provider.createNonDeterministicPrecondition(nonDeterministicPreconditions, null);
		
		String expectedResult = 
					"a.func1_prec | "
				+   "b.func2_prec & "
				+   "c.func3_prec | "
				+   "d.func4_prec";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}


	@Test
	public void functionsWithCommunicationInputParameters(){
		Variable commVarIn = Variable.createInputVariable("commVar", NAT);
		Variable commVarOut = Variable.createInputVariable("commVar", NAT);
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(commVarOut);
		// a.func1(commVarIn) [] b.func2(commVarIn)
		// =>
		// a.func_prec(tempVar1) | b.func_prec(tempVar1) 
		
		PreconditionFunctionCall afunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", new Declarations(commVarIn));
		PreconditionFunctionCall bfunc2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "func2_prec", new Declarations(commVarIn));
		PreconditionFunctionCall cfunc3Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("c", "func3_prec", Declarations.empty());

		IPromotedOperation afunc1 = postconditionMockProvider.buildFunctionPromotionMock("a", "func1", new Declarations(commVarIn), Variable.createOutputVariable("outVar1", NAT));
		IPromotedOperation bfunc2 = postconditionMockProvider.buildFunctionPromotionMock("b", "func2", new Declarations(commVarIn), Variable.createOutputVariable("outVar2", NAT));
		IPromotedOperation cfunc3 = postconditionMockProvider.buildFunctionPromotionMock("c", "func3", Declarations.empty(), Variable.createOutputVariable("outVar3", NAT));
		
		PreconditionItem item1 = new PreconditionItem(new PreconditionFunctionCalls(afunc1Prec), Arrays.asList(afunc1));
		PreconditionItem item2 = new PreconditionItem(new PreconditionFunctionCalls(bfunc2Prec, cfunc3Prec), Arrays.asList(bfunc2, cfunc3));
		
		withPreconditionItem(item1);
		withPreconditionItem(item2);
		
		ST resultTemplate = provider.createNonDeterministicPrecondition(nonDeterministicPreconditions, tempVarMap);
		
		String expectedResult = 
					"a.func1_prec(tempVar1) | "
				+   "b.func2_prec(tempVar1) & "
				+   "c.func3_prec";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void schemasWithCommunicationInputParameters(){
		Variable commVarIn = Variable.createInputVariable("commVar", NAT);
		Variable commVarOut = Variable.createInputVariable("commVar", NAT);
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(commVarOut);
		// a.func1(commVarIn) [] b.func2(commVarIn)
		// =>
		// a.func_prec(tempVar1) | b.func_prec(tempVar1) 
		
		PreconditionFunctionCall aOp1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "op1_prec", new Declarations(commVarIn));
		PreconditionFunctionCall bOp2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "op2_prec", new Declarations(commVarIn));
		PreconditionFunctionCall cOp3Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("c", "op3_prec", Declarations.empty());

		IPromotedOperation aOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "op1", new Declarations(commVarIn), new Declarations(Variable.createOutputVariable("outVar1", NAT)));
		IPromotedOperation bOp2 = postconditionMockProvider.buildSchemaPromotionMock("b", "op2", new Declarations(commVarIn), new Declarations(Variable.createOutputVariable("outVar2", NAT)));
		IPromotedOperation cOp3 = postconditionMockProvider.buildSchemaPromotionMock("c", "op3", Declarations.empty(), new Declarations(Variable.createOutputVariable("outVar3", NAT)));
		
		PreconditionItem item1 = new PreconditionItem(new PreconditionFunctionCalls(aOp1Prec), Arrays.asList(aOp1));
		PreconditionItem item2 = new PreconditionItem(new PreconditionFunctionCalls(bOp2Prec, cOp3Prec), Arrays.asList(bOp2, cOp3));
		
		withPreconditionItem(item1);
		withPreconditionItem(item2);
		
		ST resultTemplate = provider.createNonDeterministicPrecondition(nonDeterministicPreconditions, tempVarMap);
		
		String expectedResult = 
					"a.op1_prec(tempVar1) | "
				+   "b.op2_prec(tempVar1) & "
				+   "c.op3_prec";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void functionsWithCommunicationOutputParameters(){
		Variable commVarOut = Variable.createOutputVariable("commVar", NAT);
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(commVarOut);
		// a.func1 && b.func2 [] c.func3
		// func1: commVarOut
		// func2: outVarOut
		// func3: commVarOut outVarOut
		
		// =>
		// a.func_prec(tempVar1) | b.func_prec(tempVar1) 
		
		PreconditionFunctionCall afunc1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "func1_prec", Declarations.empty());
		PreconditionFunctionCall bfunc2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "func2_prec", Declarations.empty());
		PreconditionFunctionCall cfunc3Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("c", "func3_prec", Declarations.empty());

		IPromotedOperation afunc1 = postconditionMockProvider.buildFunctionPromotionMock("a", "func1", Declarations.empty(), commVarOut);
		IPromotedOperation bfunc2 = postconditionMockProvider.buildFunctionPromotionMock("b", "func2", Declarations.empty(), Variable.createOutputVariable("outVar2", NAT));
		IPromotedOperation cfunc3 = postconditionMockProvider.buildFunctionPromotionMock("c", "func3", Declarations.empty(), commVarOut);
		
		PreconditionItem item1 = new PreconditionItem(new PreconditionFunctionCalls(afunc1Prec, bfunc2Prec), Arrays.asList(afunc1, bfunc2));
		PreconditionItem item2 = new PreconditionItem(new PreconditionFunctionCalls(cfunc3Prec), Arrays.asList(cfunc3));
		
		withPreconditionItem(item1);
		withPreconditionItem(item2);
		
		ST resultTemplate = provider.createNonDeterministicPrecondition(nonDeterministicPreconditions, tempVarMap);
		
		String expectedResult = 
					"a.func1_prec & "
				+   "b.func2_prec & "
				+   "tempVar1 = a.func1.commVar_out | "
				+   "c.func3_prec & "
				+   "tempVar1 = c.func3.commVar_out";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void schemasWithCommunicationOutputParameters(){
		Variable commVarOut = Variable.createOutputVariable("commVar", NAT);
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(commVarOut);
		// a.func1 && b.func2 [] c.func3
		// func1: commVarOut
		// func2: outVarOut
		// func3: commVarOut outVarOut
		
		// =>
		// a.func_prec(tempVar1) | b.func_prec(tempVar1) 
		
		PreconditionFunctionCall aOp1Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("a", "op1_prec", Declarations.empty());
		PreconditionFunctionCall bOp2Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("b", "op2_prec", Declarations.empty());
		PreconditionFunctionCall cOp3Prec = 
				preconditionMockProvider.buildPreconditionFunctionCallMock("c", "op3_prec", Declarations.empty());

		IPromotedOperation aOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "op1", Declarations.empty(), new Declarations(commVarOut));
		IPromotedOperation bOp2 = postconditionMockProvider.buildSchemaPromotionMock("b", "op2", Declarations.empty(), new Declarations(Variable.createOutputVariable("outVar2", NAT)));
		IPromotedOperation cOp3 = postconditionMockProvider.buildSchemaPromotionMock("c", "op3", Declarations.empty(), new Declarations(commVarOut));
		
		PreconditionItem item1 = new PreconditionItem(new PreconditionFunctionCalls(aOp1Prec, bOp2Prec), Arrays.asList(aOp1, bOp2));
		PreconditionItem item2 = new PreconditionItem(new PreconditionFunctionCalls(cOp3Prec), Arrays.asList(cOp3));
		
		withPreconditionItem(item1);
		withPreconditionItem(item2);
		
		ST resultTemplate = provider.createNonDeterministicPrecondition(nonDeterministicPreconditions, tempVarMap);
		
		String expectedResult = 
					"a.op1_prec & "
				+   "b.op2_prec & "
				+   "tempVar1 = a.op1.commVar_out | "
				+   "c.op3_prec & "
				+   "tempVar1 = c.op3.commVar_out";
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

}
