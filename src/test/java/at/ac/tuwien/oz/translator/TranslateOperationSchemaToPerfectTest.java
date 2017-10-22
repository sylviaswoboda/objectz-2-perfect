package at.ac.tuwien.oz.translator;

import java.util.Arrays;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.SimpleBoolFunction;
import at.ac.tuwien.oz.definitions.operation.SimpleChangeOperation;
import at.ac.tuwien.oz.definitions.operation.SimpleFunction;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.parser.OZParser.ClassDefinitionContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationListContext;
import at.ac.tuwien.oz.parser.OZParser.PrimaryContext;
import at.ac.tuwien.oz.parser.OZParser.StateContext;
import at.ac.tuwien.oz.translator.symboldefinition.SymbolCollector;
import at.ac.tuwien.oz.translator.typeevaluator.TypeDeclarationEvaluator;


public class TranslateOperationSchemaToPerfectTest extends TranslationTestBase{
	
	private ObjectZDefinition globalDefinition;
	
	private static final String TEST_CLASS_NAME = "TestClass";

	private SymbolCollector symbolCollector;
	private ParseTreeProperty<Declarations> declarationsTree;
	private TypeDeclarationEvaluator typeDeclarationEval;
	
	private static final ExpressionType NAT = ExpressionType.getNat();

	@SuppressWarnings("unchecked")
	private void prepareSurroundingTestClass(Variable... primaryVariables){
		symbolCollector = (SymbolCollector)Whitebox.getInternalState(this.ozTranslator, "symbolCollector");
		typeDeclarationEval = (TypeDeclarationEvaluator)Whitebox.getInternalState(this.ozTranslator, "typeDeclarationEvaluator");
		declarationsTree = (ParseTreeProperty<Declarations>)Whitebox.getInternalState(this.symbolCollector, "declarationsTree");
		
		ClassDefinitionContext ctx = Mockito.mock(ClassDefinitionContext.class);
		TerminalNode idNode = Mockito.mock(TerminalNode.class);
		Mockito.when(ctx.ID()).thenReturn(idNode );
		Mockito.when(idNode.getText()).thenReturn(TEST_CLASS_NAME);
		symbolCollector.enterClassDefinition(ctx);
		typeDeclarationEval.enterClassDefinition(ctx);
		
		StateContext stateCtx = mockStateVariables(primaryVariables);
				
		symbolCollector.exitState(stateCtx);
		this.globalDefinition = symbolCollector.getObjectZDefinition();
	}

	private StateContext mockStateVariables(Variable... primaryVariables) {
		
		StateContext stateCtx = Mockito.mock(StateContext.class);
		PrimaryContext primaryCtx = Mockito.mock(PrimaryContext.class);
		DeclarationListContext declarationListCtx = Mockito.mock(DeclarationListContext.class);

		Mockito.when(stateCtx.primary()).thenReturn(primaryCtx);
		Mockito.when(primaryCtx.declarationList()).thenReturn(declarationListCtx);
		
		declarationsTree.put(declarationListCtx, new Declarations(primaryVariables));
		return stateCtx;
	}

	@Test
	public void boolFunctionOperationsWithInputParameters(){
		buildTranslatorForOperationSchemaDef(
				"isValueSum{"
					+ "value1?:!N; "
					+ "value2?:!N; "
					+ "balance = value1? + value2?;"
					+ "value1? > 0; "
					+ "value2? > 10;"
				+ "}");
		
		String expected = "function isValueSum (value1_in:nat, value2_in:nat): bool\n" + 
					      "    ^= balance = value1_in + value2_in &\n" + "" +
					      "       value1_in > 0 &\n" + 
					      "       value2_in > 10;"; 

		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", ExpressionType.getNat()));
		
		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
				
		Assert.assertEquals(expected, rootTemplate.render());
		
		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("isValueSum"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleBoolFunction.class, operation.getClass());
		Assert.assertEquals(2, operation.getInputParameters().size());
		Assert.assertEquals(3, operation.getPreconditions().size());
	}

	@Test
	public void boolFunctionOperationsWithoutInputParameters(){
		buildTranslatorForOperationSchemaDef(
				"isValueSum{"
					+ "balance = value1 + value2; "
					+ "value1 > 0; value2 > 10;"
				+ "}");
		
		String expected = "function isValueSum : bool\n" + 
					      "    ^= balance = value1 + value2 &\n" +
					      "       value1 > 0 &\n" +
					      "       value2 > 10;"; 
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT),
				Variable.createUndecoratedVariable("value1", NAT),
				Variable.createUndecoratedVariable("value2", NAT));

		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		
		Assert.assertEquals(expected, rootTemplate.render());
		
		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("isValueSum"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleBoolFunction.class, operation.getClass());
		Assert.assertEquals(0, operation.getInputParameters().size());
		Assert.assertEquals(3, operation.getPreconditions().size());
	}

	@Test
	public void boolFunctionOperationsSinglePredicate(){
		buildTranslatorForOperationSchemaDef(
				"isValueSum{"
					+ "balance = value1 + value2;"
				+ "}");
		
		String expected = "function isValueSum : bool\n" + 
					      "    ^= balance = value1 + value2;"; 
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT),
				Variable.createUndecoratedVariable("value1", NAT),
				Variable.createUndecoratedVariable("value2", NAT));

		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		
		Assert.assertEquals(expected, rootTemplate.render());
		
		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("isValueSum"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleBoolFunction.class, operation.getClass());
		Assert.assertEquals(0, operation.getInputParameters().size());
		Assert.assertEquals(1, operation.getPreconditions().size());
	}
	
	@Test
	public void functionOperationWithoutInputParameters(){
		buildTranslatorForOperationSchemaDef(
				"fundsAvail{"
				+ "funds!:!N; "
				+ "funds! = balance + limit;}");
		
		String expected = "function fundsAvail_prec : bool\n"
						+ "    ^= (exists funds_temp:nat :- "
										+ "(funds_temp = balance + limit));\n"
						+ "function fundsAvail funds_out:nat\n"
						+ "    pre fundsAvail_prec\n"
						+ "    satisfy result.funds_out = balance + limit;"; 
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT),
				Variable.createUndecoratedVariable("limit", NAT));

		
		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("fundsAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleFunction.class, operation.getClass());
		Assert.assertEquals(0, operation.getInputParameters().size());
		Assert.assertEquals(1, operation.getOutputParameters().size());
		Assert.assertEquals(1, operation.getPreconditions().size());
	}
	
	@Test
	public void functionOperationWithMultipleOutputParameters(){
		buildTranslatorForOperationSchemaDef(
				"allFundsAvail{"
				+ 	"fundsAccount1!:!N; "
				+ 	"fundsAccount2!:!N; " 
				+	"fundsAccount1! = balance1 + limit1; "
				+   "fundsAccount2! = balance2 + limit2;}");
		
		String expected = "function allFundsAvail_prec : bool\n"
				        + "    ^= (exists fundsAccount1_temp:nat, fundsAccount2_temp:nat :- "
				                    + "(fundsAccount1_temp = balance1 + limit1 & "
				                    +  "fundsAccount2_temp = balance2 + limit2));\n"
						+ "function allFundsAvail fundsAccount1_out:nat, fundsAccount2_out:nat\n"
						+ "    pre allFundsAvail_prec\n"
					    + "    satisfy result.fundsAccount1_out = balance1 + limit1 &\n" + 
					      "            result.fundsAccount2_out = balance2 + limit2;"; 
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance1", NAT),
				Variable.createUndecoratedVariable("balance2", NAT),
				Variable.createUndecoratedVariable("limit1", NAT),
				Variable.createUndecoratedVariable("limit2", NAT));

		
		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("allFundsAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleFunction.class, operation.getClass());
		Assert.assertEquals(0, operation.getInputParameters().size());
		Assert.assertEquals(2, operation.getOutputParameters().size());
		Assert.assertEquals(1, operation.getPreconditions().size());

	}

	@Test
	public void functionOperationWithInputParameters(){
		buildTranslatorForOperationSchemaDef(
				"fundsAvail{"
				+ "limit?:!N; "
				+ "funds!:!N; "
				+ "funds! = balance + limit?;}");
		
		String expected = "function fundsAvail_prec (limit_in:nat): bool\n"
						+ "    ^= (exists funds_temp:nat :- "
						+ 				"(funds_temp = balance + limit_in));\n"
						+ "function fundsAvail (limit_in:nat)funds_out:nat\n"
						+ "    pre fundsAvail_prec(limit_in)\n" + 
					      "    satisfy result.funds_out = balance + limit_in;"; 
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT));

		
		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("fundsAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleFunction.class, operation.getClass());
		Assert.assertEquals(1, operation.getInputParameters().size());
		Assert.assertEquals(1, operation.getOutputParameters().size());
		Assert.assertEquals(1, operation.getPreconditions().size());

	}

	@Test
	public void functionOperationWithExplicitPrecondition(){

		buildTranslatorForOperationSchemaDef(
				"fundsAvail{"
				+ "limit?:!N; "
				+ "funds!:!N; "
				+ "limit? > 0; "
				+ "funds! = balance + limit?;}");
		
		String expected = "function fundsAvail_prec (limit_in:nat): bool\n" +
						  "    ^= limit_in > 0 &\n"
						+ "       (exists funds_temp:nat :- "
						+ 			"(funds_temp = balance + limit_in));\n" +
						  "function fundsAvail (limit_in:nat)funds_out:nat\n" + 
						  "    pre fundsAvail_prec(limit_in)\n" +
					      "    satisfy result.funds_out = balance + limit_in;"; 
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT));

		
		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("fundsAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleFunction.class, operation.getClass());
		Assert.assertEquals(1, operation.getInputParameters().size());
		Assert.assertEquals(1, operation.getOutputParameters().size());
		Assert.assertEquals(2, operation.getPreconditions().size());
	}
	
	@Test
	public void functionOperationWithSeveralInputParametersAndPrecondition(){
		buildTranslatorForOperationSchemaDef(
				"fundsAvail{"
				+ "limit1?:!N; "
				+ "limit2?:!N; "
				+ "funds!:!N; "
				+ "limit1? > 100; "
				+ "funds! = balance + limit1? + limit2?;}");
		
		String expected = "function fundsAvail_prec (limit1_in:nat, limit2_in:nat): bool\n" +
						  "    ^= limit1_in > 100 &\n"
						 +"       (exists funds_temp:nat :- "
						 +              "(funds_temp = balance + limit1_in + limit2_in));\n" +
						  "function fundsAvail (limit1_in:nat, limit2_in:nat)funds_out:nat\n" + 
						  "    pre fundsAvail_prec(limit1_in, limit2_in)\n" +
					      "    satisfy result.funds_out = balance + limit1_in + limit2_in;"; 
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT));

		
		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("fundsAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleFunction.class, operation.getClass());
		Assert.assertEquals(2, operation.getInputParameters().size());
		Assert.assertEquals(1, operation.getOutputParameters().size());
		Assert.assertEquals(2, operation.getPreconditions().size());
	}

	@Test
	public void changeOperationWithImplicitPrecondition(){
		buildTranslatorForOperationSchemaDef(
				"withdrawAvail{"
				+ "delta(balance) "
				+ "amount!:!N; "
				+ "amount! = balance + limit; "
				+ "balance' = -limit;}");
		
		String expected = 
				  "function withdrawAvail_prec : bool\n"
				+ "    ^= (exists balance_temp:nat, amount_out:nat :- "
							+ "(amount_out = balance + limit &"
							+ " balance_temp = -limit));\n"
				+ "schema !withdrawAvail (amount_out!:out nat)\n" +
						  "    pre withdrawAvail_prec\n" +
					      "    post change  balance, amount_out\n" +
					      "         satisfy amount_out' = balance + limit &\n" +
					      "                 balance' = -limit;";

		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT),
				Variable.createUndecoratedVariable("limit", NAT));

		
		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("withdrawAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleChangeOperation.class, operation.getClass());
		Assert.assertEquals(0, operation.getInputParameters().size());
		Assert.assertEquals(1, operation.getOutputParameters().size());
		Assert.assertEquals(1, operation.getPreconditions().size());
		Assert.assertEquals(Arrays.asList("balance"), operation.getDeltalist());

	}

	@Test
	public void changeOperationWithImplicitAndExplicitPreconditions(){
		buildTranslatorForOperationSchemaDef(
				"withdrawAvail{delta(balance) "
				+ "limit?:!N; "
				+ "amount!:!N; "
				+ "limit? > 500; "
				+ "amount! = balance + limit?; "
				+ "balance' = -limit?;}");
		
		String expected = "function withdrawAvail_prec (limit_in:nat): bool\n"+
						  "    ^= limit_in > 500 &\n"
				        + "       (exists balance_temp:nat, amount_out:nat :- "
				        			+ "(amount_out = balance + limit_in &"
				        			+ " balance_temp = -limit_in));\n" +
						  "schema !withdrawAvail (limit_in:nat, amount_out!:out nat)\n" + 
						  "    pre withdrawAvail_prec(limit_in)\n" +
					      "    post change  balance, amount_out\n" +
					      "         satisfy amount_out' = balance + limit_in &\n" +
					      "                 balance' = -limit_in;";
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT));

		
		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("withdrawAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleChangeOperation.class, operation.getClass());
		Assert.assertEquals(1, operation.getInputParameters().size());
		Assert.assertEquals(1, operation.getOutputParameters().size());
		Assert.assertEquals(1, operation.getDeltalist().size());
		Assert.assertEquals(Arrays.asList("balance"), operation.getDeltalist());
		Assert.assertEquals(2, operation.getPreconditions().size());
	}

	@Test
	public void changeOperationWithInputParameters(){
		buildTranslatorForOperationSchemaDef(
				"withdrawAvail{"
				+ "delta(balance) "
				+ "limit?:!N; "
				+ "amount!:!N; "
				+ "amount! = balance + limit?; "
				+ "balance' = -limit?;}");
		
		String expected = "function withdrawAvail_prec (limit_in:nat): bool\n"
						+ "    ^= (exists balance_temp:nat, amount_out:nat :- "
						+          "(amount_out = balance + limit_in &"
						+          " balance_temp = -limit_in));\n"
				        + "schema !withdrawAvail (limit_in:nat, amount_out!:out nat)\n"
				        + "    pre withdrawAvail_prec(limit_in)\n" + 
					      "    post change  balance, amount_out\n" +
					      "         satisfy amount_out' = balance + limit_in &\n" +
					      "                 balance' = -limit_in;";
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT));

		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("withdrawAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleChangeOperation.class, operation.getClass());
		Assert.assertEquals(1, operation.getInputParameters().size());
		Assert.assertEquals(1, operation.getOutputParameters().size());
		Assert.assertEquals(1, operation.getDeltalist().size());
		Assert.assertEquals(Arrays.asList("balance"), operation.getDeltalist());
		Assert.assertEquals(1, operation.getPreconditions().size());
	}

	@Test
	public void changeOperationWithoutParameters(){
		buildTranslatorForOperationSchemaDef(
				"withdrawAvail{"
				+ "delta(balance) "
				+ "balance' = -limit;}");
		
		String expected = "function withdrawAvail_prec : bool\n" +
						  "    ^= (exists balance_temp:nat :- "
						  + 		"(balance_temp = -limit));\n" +
						  "schema !withdrawAvail \n" + 
						  "    pre withdrawAvail_prec\n" + 
					      "    post change  balance\n" +
					      "         satisfy balance' = -limit;";
		
		prepareSurroundingTestClass(Variable.createUndecoratedVariable("balance", NAT),
				Variable.createUndecoratedVariable("limit", NAT));

		this.ozTranslator.translate();
		ST rootTemplate = this.ozTranslator.getRootTemplate();
		Assert.assertEquals(expected, rootTemplate.render());

		ObjectZClass testClass = globalDefinition.resolveClass(new Ident(TEST_CLASS_NAME));
		IOperation operation = testClass.resolveOperation(new Ident("withdrawAvail"));
		Assert.assertNotNull(operation);
		Assert.assertEquals(SimpleChangeOperation.class, operation.getClass());
		Assert.assertEquals(0, operation.getInputParameters().size());
		Assert.assertEquals(0, operation.getOutputParameters().size());
		Assert.assertEquals(1, operation.getDeltalist().size());
		Assert.assertEquals(Arrays.asList("balance"), operation.getDeltalist());
		Assert.assertEquals(1, operation.getPreconditions().size());
	}

}
