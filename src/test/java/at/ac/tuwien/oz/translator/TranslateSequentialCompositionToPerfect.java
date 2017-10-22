package at.ac.tuwien.oz.translator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import at.ac.tuwien.oz.definitions.operation.SequentialComposition;

public class TranslateSequentialCompositionToPerfect extends TranslationTestBase {
	
	private String simpleOperationClassInput;
	private String operationPromotionClassInput;
	
	private void buildSimpleOperations(String ... additionalOperations){
		StringBuilder classBuilder = new StringBuilder()
				.append("class SimpleOperations {")
				.append(	"state{")
//				.append(		"stateVar: !N;")
				.append(		"stateVar1: !N;")
				.append( 		"stateVar2: !N;")
				.append( 		"stateVar3: !N;")
				.append( 		"balance: !N;")
				.append( 	"}");
		
		if (additionalOperations != null && additionalOperations.length > 0){
			for (String op: additionalOperations){
				classBuilder.append(op);
			}
		}
		classBuilder.append("}");
		this.simpleOperationClassInput = classBuilder.toString();
	}
	
	private void buildOperationPromotions(String ... additionalOperations) {
		StringBuilder classBuilder = new StringBuilder()
				.append("class OperationPromotions {")
				.append(	"state{")
				.append(		"a: SimpleOperations;")
				.append(		"b: SimpleOperations;")
				.append(		"c: SimpleOperations;")
				.append(	"}");
		
		if (additionalOperations != null && additionalOperations.length > 0){
			for (String op: additionalOperations){
				classBuilder.append(op);
			}
		}
		classBuilder.append("}");
		
		this.operationPromotionClassInput = classBuilder.toString();
	}

	@Before
	public void setup(){
		expectedClass = SequentialComposition.class;
	}

//	@Test
//	public void boolFunctionAndBoolFunctionDifferentObjects(){
//		String aBoolOp1 = "aBoolFunc1{" +
//			"inVarOp1?:!N; " +
//			"stateVar > inVarOp1?;}";
//
//		String aBoolOp2 = "aBoolFunc2{" +
//			"inVarOp2?:!N; " +
//			"stateVar > inVarOp2?;}";
//
//		getOperationSchemaDef(aBoolOp1);
//		getOperationSchemaDef(aBoolOp2);
//		
//		String input = "parallelComp ^= a.aBoolFunc1 || b.aBoolFunc2;";
//			
//		String expectedOutput = 
//			"function parallelComp (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
//			"    ^= a.aBoolFunc1(inVarOp1_in) &\n" +
//			"       b.aBoolFunc2(inVarOp2_in);";
//		
//		
//		translator.currentScope = parallelCompositions;
//		ST rootTemplate = getOperationExpressionDef(input);
//		
//		Assert.assertEquals(expectedOutput, rootTemplate.render());
//		
//		IOperation operation = parallelCompositions.resolveOperation("parallelComp");
//		Assert.assertNotNull(operation);
//	}
//
//	@Test
//	public void boolFunctionAndFunctionDifferentObjects(){
//		String aFunc1 = "aFunc1{" +
//			"inVarOp1?:!N; " +
//			"outVarOp1!:!N; " +
//			"inVarOp1? > 0; " +
//			"outVarOp1! = balance + inVarOp1?;}";
//
//		String aBoolOp2 = "aBoolFunc2{" +
//			"inVarOp2?:!N; " +
//			"stateVar > inVarOp2?;}";
//		
//		getOperationSchemaDef(aFunc1);
//		getOperationSchemaDef(aBoolOp2);
//
//		String input = "parallelComp ^= a.aFunc1 || b.aBoolFunc2;";
//			
//		String expectedOutput = 
//			"function parallelComp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
//			"    ^= a.aFunc1_prec(inVarOp1_in) &\n" +
//			"       b.aBoolFunc2(inVarOp2_in);\n" +
//			"function parallelComp (inVarOp1_in:nat, inVarOp2_in:nat)outVarOp1_out:nat\n" +
//			"    pre parallelComp_prec(inVarOp1_in, inVarOp2_in)\n" +
//			"    satisfy result.outVarOp1_out = a.aFunc1(inVarOp1_in).outVarOp1_out;";
//		
//		translator.currentScope = parallelCompositions;
//		ST rootTemplate = getOperationExpressionDef(input);
//		
//		Assert.assertEquals(expectedOutput, rootTemplate.render());
//		
//		IOperation operation = parallelCompositions.resolveOperation("parallelComp");
//		Assert.assertNotNull(operation);
//	}
//
//	@Test
//	public void boolFunctionAndFunctionWithCommunicationVariablesAndDegenerateToBoolFunc(){
//		String aBoolComm1 = "aBoolComm1{" +
//			"commVar1?:!N; " +
//			"stateVar > commVar1?;}";
//
//		String aFuncComm1 = "aFuncComm1{" +
//			"inVarOp1?:!N; " +
//			"commVar1!:!N; " +
//			"inVarOp1? > 0; " +
//			"commVar1! = balance + inVarOp1?;}";
//
//		getOperationSchemaDef(aBoolComm1);
//		getOperationSchemaDef(aFuncComm1);
//		
//		String input = "parallelComp ^= a.aBoolComm1 || b.aFuncComm1;";
//			
//		String expectedOutput = 
//			"function parallelComp (inVarOp1_in:nat): bool\n" +
//			"    ^= b.aFuncComm1_prec(inVarOp1_in) &\n" +
//			"       (exists tempVar1:nat :- (tempVar1 = b.aFuncComm1(inVarOp1_in).commVar1_out & a.aBoolComm1(tempVar1)));";
//		
//		translator.currentScope = parallelCompositions;
//		ST rootTemplate = getOperationExpressionDef(input);
//		
//		Assert.assertEquals(expectedOutput, rootTemplate.render());
//		
//		IOperation operation = parallelCompositions.resolveOperation("parallelComp");
//		Assert.assertNotNull(operation);
//	}
//	
//	@Test
//	public void functionAndBoolFunctionWithCommunicationVariablesAndDegenerateToBoolFunc(){
//		String aBoolComm1 = "aBoolComm1{" +
//			"commVar1?:!N; " +
//			"stateVar > commVar1?;}";
//
//		String aFuncComm1 = "aFuncComm1{" +
//			"inVarOp1?:!N; " +
//			"commVar1!:!N; " +
//			"inVarOp1? > 0; " +
//			"commVar1! = balance + inVarOp1?;}";
//
//		getOperationSchemaDef(aBoolComm1);
//		getOperationSchemaDef(aFuncComm1);
//		
//		String input = "parallelComp ^= b.aFuncComm1 || a.aBoolComm1 ;";
//			
//		String expectedOutput = 
//			"function parallelComp (inVarOp1_in:nat): bool\n" +
//			"    ^= b.aFuncComm1_prec(inVarOp1_in) &\n" +
//			"       (exists tempVar1:nat :- (tempVar1 = b.aFuncComm1(inVarOp1_in).commVar1_out & a.aBoolComm1(tempVar1)));";
//		
//		translator.currentScope = parallelCompositions;
//		ST rootTemplate = getOperationExpressionDef(input);
//		
//		Assert.assertEquals(expectedOutput, rootTemplate.render());
//		
//		IOperation operation = parallelCompositions.resolveOperation("parallelComp");
//		Assert.assertNotNull(operation);
//	}
	
	private String getProgram() {
		return this.simpleOperationClassInput + this.operationPromotionClassInput;
	}
	
	@Test
	public void combineTwoFunctionsSequentiallyWithoutCommonNameOrCommunicatingEqualsConjunction(){
		String aFunc1 = "aFunc1{" +
			"inVar1?:!N; " +
			"outVar1!:!N; " +
			"outVar1! = balance + inVar1?;" + 
			"}";
	
		String aFunc2 = "aFunc2{" +
			"inVar2?:!N; " +
			"outVar2!:!N; " +
			"outVar2! = balance + inVar2?;}";
	
		String input = "sequentialComp ^= a.aFunc1 0/9 b.aFunc2 ;";
		
		buildSimpleOperations(aFunc1, aFunc2);
		buildOperationPromotions(input);
		
		expected = 
			"function sequentialComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVar1_in) &" +
			      " b.aFunc2_prec(inVar2_in);\n" +
			"function sequentialComp (inVar1_in:nat, inVar2_in:nat)outVar1_out:nat, outVar2_out:nat\n" +
			"    pre sequentialComp_prec(inVar1_in, inVar2_in)\n" +
			"    satisfy result.outVar1_out = a.aFunc1(inVar1_in).outVar1_out &" + 
			           " result.outVar2_out = b.aFunc2(inVar2_in).outVar2_out;";
		
		className = "OperationPromotions";
		operationName = "sequentialComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void combineTwoFunctionsSequentiallyWithCommonNamedOutputPromotionKeepsOnlyRightHandSidePromotion(){
		String aFunc1 = "aFunc1{" +
			"inVar1?:!N; " +
			"outVar1!:!N; " +
			"outVar1! = balance + inVar1?;" + 
			"}";
	
		String aFunc2 = "aFunc2{" +
			"inVar2?:!N; " +
			"outVar1!:!N; " +
			"outVar1! = balance + inVar2?;}";
	
		String input = "sequentialComp ^= a.aFunc1 0/9 b.aFunc2 ;";
		
		buildSimpleOperations(aFunc1, aFunc2);
		buildOperationPromotions(input);
		
		expected = 
			"function sequentialComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVar1_in) &" +
			      " b.aFunc2_prec(inVar2_in);\n" +
			"function sequentialComp (inVar1_in:nat, inVar2_in:nat)outVar1_out:nat\n" +
			"    pre sequentialComp_prec(inVar1_in, inVar2_in)\n" +
			"    satisfy result.outVar1_out = b.aFunc2(inVar2_in).outVar1_out;";
		
		className = "OperationPromotions";
		operationName = "sequentialComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	@Test
	public void combineTwoFunctionsSequentiallyWithOneDirectionalCommunicationIsEqualToParallelComposition(){
		String function1 = "function1{" +
			"inVar1?:!N; " +
			"comm1!:!N; " +
			"outVar2!:!N; " +
			"inVar1? > 200; " +
			"comm1! = 3 * inVar1?; " + 
			"outVar2! = inVar1? - 20;}";

		String function2 = "function2{" +
			"comm1?:!N; " +
			"outVar1!:!N; " +
			"comm1? > 50; " +
			"outVar1! = 2 * comm1?;}";

		String input = "seqComp ^= a.function1 0/9 b.function2 ;";
		
		buildSimpleOperations(function1, function2);
		buildOperationPromotions(input);
		
		expected = 
			"function seqComp_prec (inVar1_in:nat): bool\n" +
			"    ^= (exists tempVar2:nat :- " +
						"(tempVar2 = a.function1(inVar1_in).comm1_out & " + 
			             "a.function1_prec(inVar1_in) & " + 
		                 "b.function2_prec(tempVar2)));\n" +
			"function seqComp (inVar1_in:nat)outVar1_out:nat, outVar2_out:nat\n" +
		    "    pre seqComp_prec(inVar1_in)\n" +
			"    satisfy (exists tempVar1:nat :- "
			                  + "("
			                  +  "tempVar1 = a.function1(inVar1_in).comm1_out & "
			                  +  "result.outVar2_out = a.function1(inVar1_in).outVar2_out & "
			                  +  "result.outVar1_out = b.function2(tempVar1).outVar1_out"
			                  + "));";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	@Test
	public void combineTwoFunctionsSequentiallyAllowsOnlyForOneDirectionalCommunication(){
		String aFuncComm1InComm2Out = "aFuncComm1InComm2Out{" +
				"comm1?:!N; " +
				"comm2!:!N; " +
				"comm2! = 2 * comm1?;}";
		
		String aFuncComm2InComm1OutAddInput = "aFuncComm2InComm1OutAddInput{" +
			"input1?:!N; " +
			"comm2?:!N; " +
			"comm1!:!N; " +
			"input1? > 20; " +
			"comm1! = 3 * comm2?;}";

		String input = "seqComp ^= a.aFuncComm1InComm2Out 0/9 b.aFuncComm2InComm1OutAddInput ;";

		buildSimpleOperations(aFuncComm1InComm2Out, aFuncComm2InComm1OutAddInput);
		buildOperationPromotions(input);
		
		expected = 
			"function seqComp_prec (comm1_in:nat, input1_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat :- " +
							"(TEMP1 = a.aFuncComm1InComm2Out(comm1_in).comm2_out & " + 
						     "a.aFuncComm1InComm2Out_prec(comm1_in) & " + 
					         "b.aFuncComm2InComm1OutAddInput_prec(TEMP1, input1_in)));\n" +	
			"function seqComp (comm1_in:nat, input1_in:nat)comm1_out:nat\n" +
			"    pre seqComp_prec(comm1_in, input1_in)\n" +
			"    satisfy (exists TEMP2:nat :- " +
			        	   "(TEMP2 = a.aFuncComm1InComm2Out(comm1_in).comm2_out & " +
			        	   "result.comm1_out = b.aFuncComm2InComm1OutAddInput(TEMP2, input1_in).comm1_out));";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test
	public void combineTwoFunctionsSequentiallyWithCommunicationVariablesAndSharedOutputKeepsOnlySecondSharedOutput(){
		String aFunc1 = "func1{" +
			"inVar1?:!N; " +
			"commVar1!:!N; " +
			"outVar1!:!N; " +
			"inVar1? > 0; " +
			"commVar1! = balance + inVar1?;" +
			"outVar1! = balance * 2;}";

		String aFunc2 = "func2{" +
			"inVar2?:!N; " +
			"commVar1?:!N; " +
			"outVar1!:!N; " +
			"inVar2? > 0; " +
			"commVar1? > 0;" +
			"outVar1! = 2 * balance;}";

		String input = "seqComp ^= a.func1 0/9 b.func2 ;";

		buildSimpleOperations(aFunc1, aFunc2);
		buildOperationPromotions(input);
		
		expected = 
			"function seqComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat :- " +
			                "(TEMP1 = a.func1(inVar1_in).commVar1_out & " +
			                 "a.func1_prec(inVar1_in) & " +
			                 "b.func2_prec(TEMP1, inVar2_in)));\n" +
			"function seqComp (inVar1_in:nat, inVar2_in:nat)outVar1_out:nat\n" +
			"    pre seqComp_prec(inVar1_in, inVar2_in)\n" +
			"    satisfy (exists TEMP2:nat :- " +
						 "(TEMP2 = a.func1(inVar1_in).commVar1_out & " + 
			              "result.outVar1_out = b.func2(TEMP2, inVar2_in).outVar1_out));";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}
	
	@Test
	public void combineThreeFunctionsSequentiallyWithoutCommonNameOrCommunicatingEqualsConjunction(){
		
		String aFunc1 = "aFunc1{" +
			"inVar1?:!N; " +
			"outVar1!:!N; " +
			"outVar1! = balance + inVar1?;" + 
			"}";
	
		String aFunc2 = "aFunc2{" +
			"inVar2?:!N; " +
			"outVar2!:!N; " +
			"outVar2! = balance + inVar2?;}";

		String aFunc3 = "aFunc3{" +
			"inVar3?:!N; " +
			"outVar3!:!N; " +
			"outVar3! = balance + inVar3?;}";

		String input = "sequentialComp ^= a.aFunc1 0/9 b.aFunc2 0/9 c.aFunc3 ;";

		buildSimpleOperations(aFunc1, aFunc2, aFunc3);
		buildOperationPromotions(input);
		
		expected = 
			"function sequentialComp_prec (inVar1_in:nat, inVar2_in:nat, inVar3_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVar1_in) &" +
			      " b.aFunc2_prec(inVar2_in) &" +
			      " c.aFunc3_prec(inVar3_in);\n" +
			"function sequentialComp (inVar1_in:nat, inVar2_in:nat, inVar3_in:nat)outVar1_out:nat, outVar2_out:nat, outVar3_out:nat\n" +
			"    pre sequentialComp_prec(inVar1_in, inVar2_in, inVar3_in)\n" +
			"    satisfy result.outVar1_out = a.aFunc1(inVar1_in).outVar1_out &" + 
			           " result.outVar2_out = b.aFunc2(inVar2_in).outVar2_out &" + 
			           " result.outVar3_out = c.aFunc3(inVar3_in).outVar3_out;";
		
		className = "OperationPromotions";
		operationName = "sequentialComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void combineThreeFunctionsSequentiallyWithCommunication(){
		
		String aFunc1 = "aFunc1{" +
			"inVar1?:!N; " +
			"commVar1!:!N; " +
			"commVar1! = balance + inVar1?;" + 
			"}";
	
		String aFunc2 = "aFunc2{" +
			"commVar1?:!N; " +
			"commVar2!:!N; " +
			"commVar2! = balance + commVar1?;}";

		String aFunc3 = "aFunc3{" +
			"commVar2?:!N; " +
			"outVar3!:!N; " +
			"outVar3! = balance + commVar2?;}";

		String input = "sequentialComp ^= a.aFunc1 0/9 b.aFunc2 0/9 c.aFunc3 ;";

		buildSimpleOperations(aFunc1, aFunc2, aFunc3);
		buildOperationPromotions(input);
		
		expected = 
			"function sequentialComp_prec (inVar1_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat, TEMP2:nat :- "
					+ "(TEMP1 = a.aFunc1(inVar1_in).commVar1_out &"
					+ " a.aFunc1_prec(inVar1_in) &" 
					+ " TEMP2 = b.aFunc2(TEMP1).commVar2_out &"
					+ " b.aFunc2_prec(TEMP1) &" 
					+ " c.aFunc3_prec(TEMP2)));\n" 
					+
			"function sequentialComp (inVar1_in:nat)outVar3_out:nat\n" +
			"    pre sequentialComp_prec(inVar1_in)\n" +
			"    satisfy (exists TEMP3:nat, TEMP4:nat :- "
			+ 				"(TEMP3 = a.aFunc1(inVar1_in).commVar1_out &" 
			+ 				" TEMP4 = b.aFunc2(TEMP3).commVar2_out &" 
			+		        " result.outVar3_out = c.aFunc3(TEMP4).outVar3_out));";
		
		className = "OperationPromotions";
		operationName = "sequentialComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2", "TEMP3", "TEMP4");
	}

	@Test
	public void combineThreeFunctionsSequentiallyWithSharedOutput(){
		
		String aFunc1 = "aFunc1{" +
			"inVar1?:!N; " +
			"outVar3!:!N; " +
			"outVar3! = balance + inVar1?;" + 
			"}";
	
		String aFunc2 = "aFunc2{" +
			"inVar2?:!N; " +
			"outVar3!:!N; " +
			"outVar3! = balance + inVar2?;}";

		String aFunc3 = "aFunc3{" +
			"inVar3?:!N; " +
			"outVar3!:!N; " +
			"outVar3! = balance + inVar3?;}";

		String input = "sequentialComp ^= a.aFunc1 0/9 b.aFunc2 0/9 c.aFunc3 ;";
			
		buildSimpleOperations(aFunc1, aFunc2, aFunc3);
		buildOperationPromotions(input);
		
		expected = 
			"function sequentialComp_prec (inVar1_in:nat, inVar2_in:nat, inVar3_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVar1_in) &"
				+ " b.aFunc2_prec(inVar2_in) &" 
				+ " c.aFunc3_prec(inVar3_in);\n" 
				+
			"function sequentialComp (inVar1_in:nat, inVar2_in:nat, inVar3_in:nat)outVar3_out:nat\n" +
			"    pre sequentialComp_prec(inVar1_in, inVar2_in, inVar3_in)\n" +
			"    satisfy result.outVar3_out = c.aFunc3(inVar3_in).outVar3_out;";
		
		className = "OperationPromotions";
		operationName = "sequentialComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	
	@Test
	public void combineThreeFunctionsSequentiallyWithCommunicationAndSameButNotSharingNameWithCommunicationVariable(){
		String aFunc1 = "aFunc1{" +
			"inVar1?:!N; " +
			"commVar1!:!N; " +
			"commVar1! = balance + inVar1?;" + 
			"}";
	
		String aFunc2 = "aFunc2{" +
			"commVar1?:!N; " +
			"outVar2!:!N; " +
			"outVar2! = balance + commVar1?;}";

		String aFunc3 = "aFunc3{" +
			"inVar2?:!N; " +
			"commVar1!:!N; " +
			"commVar1! = balance + inVar2?;}";

		String input = "sequentialComp ^= a.aFunc1 0/9 b.aFunc2 0/9 c.aFunc3 ;";
		
		buildSimpleOperations(aFunc1, aFunc2, aFunc3);
		buildOperationPromotions(input);
		
		expected = 
			"function sequentialComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat :- "
					+ "(TEMP1 = a.aFunc1(inVar1_in).commVar1_out &"
					+ " a.aFunc1_prec(inVar1_in) &" 
					+ " b.aFunc2_prec(TEMP1) &" 
					+ " c.aFunc3_prec(inVar2_in)));\n" 
					+
			"function sequentialComp (inVar1_in:nat, inVar2_in:nat)commVar1_out:nat, outVar2_out:nat\n" +
			"    pre sequentialComp_prec(inVar1_in, inVar2_in)\n" +
			"    satisfy (exists TEMP2:nat :- "
			+ 				"(TEMP2 = a.aFunc1(inVar1_in).commVar1_out &" 
			+ 				" result.outVar2_out = b.aFunc2(TEMP2).outVar2_out &" 
			+		        " result.commVar1_out = c.aFunc3(inVar2_in).commVar1_out));";
		
		className = "OperationPromotions";
		operationName = "sequentialComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test
	public void combineFunctionAndSchemaSequentially(){
		String func = "func{" +
				"commVar1!:!N; " +
				"outVar!:!N; " +
				"commVar1! = 3 * balance;" + 
				"outVar! = 2 * balance;}";
		
		String changeOp = "changeOp{" +
				"delta(stateVar2)" +
				"commVar2?:!N; " +
				"commVar2? > 0;" +
				"stateVar2' = stateVar2 + commVar2?}";
		
		String input = "seqComp ^= a.func 0/9 b.changeOp ;";
		
		buildSimpleOperations(func, changeOp);
		buildOperationPromotions(input);
		
		expected = 
			"function seqComp_prec (commVar2_in:nat): bool\n" +
			"    ^= a.func_prec & " +
			       "b.changeOp_prec(commVar2_in);\n" +
			"schema !seqComp (commVar2_in:nat, commVar1_out!:out nat, outVar_out!:out nat)\n" +
			"    pre seqComp_prec(commVar2_in)\n" +
			"    post commVar1_out! = a.func.commVar1_out & "
			       + "outVar_out! = a.func.outVar_out then\n" +
			"         b!changeOp(commVar2_in);";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);

	}
	
	@Test
	public void combineFunctionAndSchemaSequentiallyWithCommunication(){
		String func = "func{" +
			"commVar!:!N; " +
			"outVar!:!N; " +
			"commVar! = 3 * balance;" +
			"outVar! = 2 * balance;}";
		
		String changeOp = "changeOp{" +
			"delta(stateVar2)" +
			"commVar?:!N; " +
			"commVar? > 0;" +
			"stateVar2' = stateVar2 + commVar?}";

		String input = "seqComp ^= a.func 0/9 b.changeOp ;";
		
		buildSimpleOperations(func, changeOp);
		buildOperationPromotions(input);
				
		expected = 
			"function seqComp_prec : bool\n" +
			"    ^= (exists TEMP1:nat :- " +
			                "(TEMP1 = a.func.commVar_out & " +
			                 "a.func_prec & " +
			                 "b.changeOp_prec(TEMP1)));\n" +
			"schema !seqComp (outVar_out!:out nat)\n" +
			"    pre seqComp_prec\n" +
			"    post (var TEMP2:nat; " +
			          "(TEMP2! = a.func.commVar_out & "
			        + "outVar_out! = a.func.outVar_out then\n" +
			"         b!changeOp(TEMP2)));";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test
	public void combineFunctionAndSchemaSequentiallyWithCommonName(){
		String func = "func{" +
			"outVar1!:!N; " +
			"outVar1! = 2 * balance;}";
		
		String changeOp = "changeOp{" +
			"delta(stateVar2)" +
			"outVar1!:!N; " +
			"outVar1! = 3 * balance; " +
			"stateVar2' = stateVar2 * 2}";

		String input = "seqComp ^= a.func 0/9 b.changeOp ;";
		
		buildSimpleOperations(func, changeOp);
		buildOperationPromotions(input);
				
		expected = 
			"function seqComp_prec : bool\n" +
			"    ^= a.func_prec & " +
			       "b.changeOp_prec;\n" +
			"schema !seqComp (outVar1_out!:out nat)\n" +
			"    pre seqComp_prec\n" +
			"    post outVar1_out! = a.func.outVar1_out then\n" +
			"         b!changeOp(outVar1_out!);";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	
	@Test
	public void combineSchemaAndFunctionWithSequentiallyCommunicationVariable(){
		String changeOp = "changeOp{" +
			"delta(stateVar2)" +
			"commVar!:!N; " +
			"commVar! = 0;" +
			"stateVar2' = stateVar2}";

		String func = "func{" +
				"commVar?:!N; " +
				"outVar!:!N; " +
				"outVar! = 2 * balance + commVar?;}";
		
		String input = "seqComp ^= b.changeOp 0/9 a.func ;";
		
		buildSimpleOperations(func, changeOp);
		buildOperationPromotions(input);
		
		expected = 
			"function seqComp_prec : bool\n" +
			"    ^= (exists TEMP1:nat :- (TEMP1 = b.changeOp_post.commVar_out & "
									    + "b.changeOp_prec & " +
			       						  "a.func_prec(TEMP1)));\n" +
			"schema !seqComp (outVar_out!:out nat)\n" +
			"    pre seqComp_prec\n" +
			"    post (var TEMP2:nat; (b!changeOp(TEMP2!) then\n" +
			"         outVar_out! = a.func(TEMP2).outVar_out));";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test
	public void combineSchemaAndFunctionWithSequentiallyCommunicationVariableAndSameCaller(){
		String changeOp = "changeOp{" +
			"delta(stateVar2)" +
			"commVar!:!N; " +
			"commVar! = 0;" +
			"stateVar2' = stateVar2}";

		String func = "func{" +
				"commVar?:!N; " +
				"outVar!:!N; " +
				"outVar! = 2 * balance + commVar?;}";
		
		String input = "seqComp ^= a.changeOp 0/9 a.func ;";
		
		buildSimpleOperations(func, changeOp);
		buildOperationPromotions(input);
		
		expected = 
			"function seqComp_prec : bool\n" +
			"    ^= (exists TEMP1:nat :- (TEMP1 = a.changeOp_post.commVar_out & "
									    + "a.changeOp_prec & " +
			       						  "a.func_prec(TEMP1)));\n" +
			"schema !seqComp (outVar_out!:out nat)\n" +
			"    pre seqComp_prec\n" +
			"    post (var TEMP2:nat; (a!changeOp(TEMP2!) then\n" +
			"         outVar_out! = a.func(TEMP2).outVar_out));";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}
	
	@Test
	public void combineTwoSchemasSequentiallyWithCommunication(){
		String changeOp1 = "changeOp1{" +
				"delta(balance)" +
				"inVar1?:!N; " +
				"commVar!:!N; " +
				"inVar1? > 0; " +
				"commVar! = balance + inVar1?;" +
				"balance' = balance + 1}";
		
		String changeOp2 = "changeOp2{" +
			"delta(stateVar2)" +
			"commVar?:!N; " +
			"commVar? > 0;" +
			"stateVar2' = stateVar2 + commVar?}";

		String input = "seqComp ^= a.changeOp1 0/9 b.changeOp2 ;";
			
		buildSimpleOperations(changeOp1, changeOp2);
		buildOperationPromotions(input);
		
		expected = 
			"function seqComp_prec (inVar1_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat :- " +
			                "(TEMP1 = a.changeOp1_post(inVar1_in).commVar_out & " +
			                 "a.changeOp1_prec(inVar1_in) & " +
			                 "b.changeOp2_prec(TEMP1)));\n" +
			"schema !seqComp (inVar1_in:nat)\n" +
			"    pre seqComp_prec(inVar1_in)\n" +
			"    post (var TEMP2:nat; " +
			            "(a!changeOp1(inVar1_in, TEMP2!) then\n" +
			"         b!changeOp2(TEMP2)));";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test @Ignore
	public void combineTwoSchemasSequentiallySameCaller(){
		String changeOp1 = "changeOp1{" +
				"delta(balance)" +
				"balance' = balance + 1}";
		
		String changeOp2 = "changeOp2{" +
			"delta(stateVar2)" +
			"balance = 0;" +
			"stateVar2' = stateVar2 * 2}";

		String input = "seqComp ^= a.changeOp1 0/9 a.changeOp2 ;";
			
		buildSimpleOperations(changeOp1, changeOp2);
		buildOperationPromotions(input);
		
		expected = 
			"function seqComp_prec : bool\n" +
			"    ^= a.changeOp1_prec & " +
				   "(exists TEMP1:SimpleOperations :- " +
				     "TEMP1 = a.changeOp1_constr &" +
				     "TEMP1.changeOp2_prec);\n" +
			"schema !seqComp \n" +
			"    pre seqComp_prec\n" +
			"    post a!changeOp1 then\n" +
			"         a!changeOp2;";
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected);
	}

	
	@Test
	public void threeChangeOpsWithOneDirectionalCommunication(){
		String changeOp1 = "changeOp1{" +
			"delta(stateVar1)" +
			"inVar1?:!N; " +
			"commVar1!:!N; " +
			"inVar1? > 0; " +
			"commVar1! = stateVar1 + inVar1?;" +
			"stateVar1' = stateVar1 + 1;"
			+ "}";

		String changeOp2 = "changeOp2{" +
			"delta(stateVar2)" +
			"commVar1?:!N; " +
			"commVar2!:!N; " +
			"commVar1? > 0;" +
			"commVar2! = stateVar2 + 2 * commVar1?; " +
			"stateVar2' = stateVar2 + commVar1?;"
			+ "}";

		String changeOp3 = "changeOp3{" +
			"delta(stateVar3)" +
			"commVar1!:!N; " +
			"commVar2?:!N; " +
			"commVar2? > 0;" +
			"stateVar3' = stateVar3 + commVar2?" +
			"commVar1! = stateVar1 + inVar1?;" +
			"}";

		String input = "seqComp ^= a.changeOp1 0/9 b.changeOp2 0/9 a.changeOp3;";
		
		buildSimpleOperations(changeOp1, changeOp2, changeOp3);
		buildOperationPromotions(input);

		expected = 
			"function seqComp_prec (inVar1_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat, TEMP2:nat :- " +
			                "(TEMP1 = a.changeOp1_post(inVar1_in).commVar1_out & " +
			                 "a.changeOp1_prec(inVar1_in) & " +
			                 "TEMP2 = b.changeOp2_post(TEMP1).commVar2_out & " +
			                 "b.changeOp2_prec(TEMP1) & " +
			                 "a.changeOp3_prec(TEMP2)));\n" +
			"schema !seqComp (inVar1_in:nat, commVar1_out!:out nat)\n" +
			"    pre seqComp_prec(inVar1_in)\n" +
			"    post (var TEMP3:nat, TEMP4:nat; " +
			        "(a!changeOp1(inVar1_in, TEMP3!) then\n" +
			"         b!changeOp2(TEMP3, TEMP4!) then\n" +
			"         a!changeOp3(TEMP4, commVar1_out!)));";
		
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2", "TEMP3", "TEMP4");
	}

	@Test
	public void parallelCompositionCombinedWithSchemaSequentiallyWithCommunication(){
		String func1 = "func1{" +
			"inVar1?:!N; " +
			"commVar1!:!N; " +
			"commVar2!:!N; " +
			"inVar1? > 0; " +
			"commVar1! = stateVar1 + inVar1?;" +
			"commVar2! = 100 + stateVar1 + inVar1?; }";

		String changeOp2 = "changeOp2{" +
			"delta(stateVar2)" +
			"commVar1?:!N; " +
			"commVar1? > 0;" +
			"stateVar2' = stateVar2 + commVar1?}";

		String changeOp3 = "changeOp3{" +
			"delta(stateVar3)" +
			"commVar2?:!N; " +
			"commVar2? > 0;" +
			"stateVar3' = stateVar3 + commVar2?}";

		String input = "seqComp ^= a.func1 || b.changeOp2 0/9 a.changeOp3;";
		
		buildSimpleOperations(func1, changeOp2, changeOp3);
		buildOperationPromotions(input);

		expected = 
			"function seqComp_prec (inVar1_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat :- " +
						"(TEMP1 = a.func1(inVar1_in).commVar2_out &" +
						" a.func1_prec(inVar1_in) &" +
			            " (exists TEMP2:nat :- " 
						+     "(TEMP2 = a.func1(inVar1_in).commVar1_out &"
			            +	  " b.changeOp2_prec(TEMP2))) &" +
			            " a.changeOp3_prec(TEMP1)));\n" +
			"schema !seqComp (inVar1_in:nat)\n" +
			"    pre seqComp_prec(inVar1_in)\n" +
			"    post (var TEMP3:nat; "
					+ "(TEMP3! = a.func1(inVar1_in).commVar2_out &" 
					+ " (var TEMP4:nat; "
					+ 		"(TEMP4! = a.func1(inVar1_in).commVar1_out &"
			        + 		" b!changeOp2(TEMP4'))) then\n" +
			"         a!changeOp3(TEMP3)));";
		
		
		className = "OperationPromotions";
		operationName = "seqComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2", "TEMP3", "TEMP4");
	}

}

