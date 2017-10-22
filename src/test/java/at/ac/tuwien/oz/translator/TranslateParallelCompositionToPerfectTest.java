package at.ac.tuwien.oz.translator;

import org.junit.Before;
import org.junit.Test;

import at.ac.tuwien.oz.definitions.operation.ParallelComposition;

public class TranslateParallelCompositionToPerfectTest extends TranslationTestBase {

	private String simpleOperationClassInput;
	private String operationPromotionClassInput;
	
	@Before
	public void setup(){
		expectedClass = ParallelComposition.class;
	}

	private void buildSimpleOperations(String ... additionalOperations){
		StringBuilder classBuilder = new StringBuilder()
				.append("class SimpleOperations {")
				.append(	"state{")
				.append(		"stateVar: !N;")
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
	
	private String getProgram() {
		return this.simpleOperationClassInput + this.operationPromotionClassInput;
	}

	@Test
	public void boolFunctionAndBoolFunctionDifferentObjects(){
		String aBoolOp1 = "aBoolFunc1{" +
			"inVarOp1?:!N; " +
			"stateVar > inVarOp1?;}";

		String aBoolOp2 = "aBoolFunc2{" +
			"inVarOp2?:!N; " +
			"stateVar > inVarOp2?;}";

		String input = "parallelComp ^= a.aBoolFunc1 || b.aBoolFunc2;";

		buildSimpleOperations(aBoolOp1, aBoolOp2);
		buildOperationPromotions(input);
		
		expected = 
			"function parallelComp (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aBoolFunc1(inVarOp1_in) &\n" +
			"       b.aBoolFunc2(inVarOp2_in);";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void boolFunctionAndFunctionDifferentObjects(){
		String aFunc1 = "aFunc1{" +
			"inVarOp1?:!N; " +
			"outVarOp1!:!N; " +
			"inVarOp1? > 0; " +
			"outVarOp1! = balance + inVarOp1?;}";

		String aBoolOp2 = "aBoolFunc2{" +
			"inVarOp2?:!N; " +
			"stateVar > inVarOp2?;}";
		
		String input = "parallelComp ^= a.aFunc1 || b.aBoolFunc2;";
		
		buildSimpleOperations(aFunc1, aBoolOp2);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVarOp1_in) &\n" +
			"       b.aBoolFunc2(inVarOp2_in);\n" +
			"function parallelComp (inVarOp1_in:nat, inVarOp2_in:nat)outVarOp1_out:nat\n" +
			"    pre parallelComp_prec(inVarOp1_in, inVarOp2_in)\n" +
			"    satisfy result.outVarOp1_out = a.aFunc1(inVarOp1_in).outVarOp1_out;";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void boolFunctionAndFunctionWithCommunicationVariablesAndDegenerateToBoolFunc(){
		String aBoolComm1 = "aBoolComm1{" +
			"commVar1?:!N; " +
			"stateVar > commVar1?;}";

		String aFuncComm1 = "aFuncComm1{" +
			"inVarOp1?:!N; " +
			"commVar1!:!N; " +
			"inVarOp1? > 0; " +
			"commVar1! = balance + inVarOp1?;}";

		String input = "parallelComp ^= a.aBoolComm1 || b.aFuncComm1;";

		buildSimpleOperations(aBoolComm1, aFuncComm1);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp (inVarOp1_in:nat): bool\n" +
			"    ^= b.aFuncComm1_prec(inVarOp1_in) &\n" +
			"       (exists tempVar1:nat :- (tempVar1 = b.aFuncComm1(inVarOp1_in).commVar1_out & a.aBoolComm1(tempVar1)));";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	
	@Test
	public void functionAndBoolFunctionWithCommunicationVariablesAndDegenerateToBoolFunc(){
		String aBoolComm1 = "aBoolComm1{" +
			"commVar1?:!N; " +
			"stateVar > commVar1?;}";

		String aFuncComm1 = "aFuncComm1{" +
			"inVarOp1?:!N; " +
			"commVar1!:!N; " +
			"inVarOp1? > 0; " +
			"commVar1! = balance + inVarOp1?;}";

		String input = "parallelComp ^= b.aFuncComm1 || a.aBoolComm1 ;";
		
		buildSimpleOperations(aBoolComm1, aFuncComm1);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp (inVarOp1_in:nat): bool\n" +
			"    ^= b.aFuncComm1_prec(inVarOp1_in) &\n" +
			"       (exists tempVar1:nat :- (tempVar1 = b.aFuncComm1(inVarOp1_in).commVar1_out & a.aBoolComm1(tempVar1)));";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	
	@Test
	public void functionAndFunctionWithCommunicationVariablesAndDegenerateToBoolFunc(){
		String aFuncComm2InComm1Out = "aFuncComm2InComm1Out{" +
			"comm2?:!N; " +
			"comm1!:!N; " +
			"comm1! = 3 * comm2?;}";

		String aFuncComm1InComm2Out = "aFuncComm1InComm2Out{" +
			"comm1?:!N; " +
			"comm2!:!N; " +
			"comm2! = 2 * comm1?;}";

		String input = "parallelComp ^= a.aFuncComm1InComm2Out || b.aFuncComm2InComm1Out ;";
			
		buildSimpleOperations(aFuncComm2InComm1Out, aFuncComm1InComm2Out);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp : bool\n" +
			"    ^= (exists TEMP1:nat, TEMP2:nat :- "
					+ "(TEMP1 = b.aFuncComm2InComm1Out(TEMP2).comm1_out &"
					+ " TEMP2 = a.aFuncComm1InComm2Out(TEMP1).comm2_out &"
					+ " a.aFuncComm1InComm2Out_prec(TEMP1) &"
					+ " b.aFuncComm2InComm1Out_prec(TEMP2)));";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}
	
	@Test
	public void functionAndFunctionWithCommunicationVariablesAndAdditionalInputAndDegenerateToBoolFunc(){
		String aFuncComm2InComm1OutAddInput = "aFuncComm2InComm1OutAddInput{" +
			"input1?:!N; " +
			"comm2?:!N; " +
			"comm1!:!N; " +
			"input1? > 20; " +
			"comm1! = 3 * comm2?;}";

		String aFuncComm1InComm2Out = "aFuncComm1InComm2Out{" +
			"comm1?:!N; " +
			"comm2!:!N; " +
			"comm2! = 2 * comm1?;}";

		String input = "parallelComp ^= a.aFuncComm1InComm2Out || b.aFuncComm2InComm1OutAddInput ;";
		
		buildSimpleOperations(aFuncComm2InComm1OutAddInput, aFuncComm1InComm2Out);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp (input1_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat, TEMP2:nat :- " +
			        	"(TEMP1 = b.aFuncComm2InComm1OutAddInput(TEMP2, input1_in).comm1_out & " +
			        	 "TEMP2 = a.aFuncComm1InComm2Out(TEMP1).comm2_out & " +
			        	 "a.aFuncComm1InComm2Out_prec(TEMP1) & " +
			        	 "b.aFuncComm2InComm1OutAddInput_prec(TEMP2, input1_in)));";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test
	public void functionAndFunctionWithoutCommunicationVariablesIsEqualToConjunction(){
		String aFunc1 = "aFunc1{" +
			"inVar1?:!N; " +
			"outVar1!:!N; " +
			"inVar1? > 0; " +
			"outVar1! = balance + inVar1?;}";

		String aFunc2 = "aFunc2{" +
			"inVar2?:!N; " +
			"outVar2!:!N; " +
			"inVar2? > 0; " +
			"outVar2! = balance + inVar2?;}";

		String input = "parallelComp ^= a.aFunc1 || b.aFunc2 ;";
		
		buildSimpleOperations(aFunc1, aFunc2);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVar1_in) &\n" +
			"       b.aFunc2_prec(inVar2_in);\n" +
			"function parallelComp (inVar1_in:nat, inVar2_in:nat)outVar1_out:nat, outVar2_out:nat\n" +
			"    pre parallelComp_prec(inVar1_in, inVar2_in)\n" +
			"    satisfy result.outVar1_out = a.aFunc1(inVar1_in).outVar1_out &\n" +
			"            result.outVar2_out = b.aFunc2(inVar2_in).outVar2_out;";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void functionAndFunctionWithSharedOutput(){
		String aFunc1 = "aFunc1{" +
			"inVar1?:!N; " +
			"outVar1!:!N; " +
			"inVar1? > 0; " +
			"outVar1! = balance + inVar1?;}";

		String aFunc2 = "aFunc2{" +
			"inVar2?:!N; " +
			"outVar1!:!N; " +
			"inVar2? > 0; " +
			"outVar1! = balance + inVar2?;}";

		String input = "parallelComp ^= a.aFunc1 || b.aFunc2 ;";
		
		buildSimpleOperations(aFunc1, aFunc2);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVar1_in) &\n" +
			"       b.aFunc2_prec(inVar2_in) &\n" +
			"       (exists tempVar1:nat :- " +
			            "(tempVar1 = a.aFunc1(inVar1_in).outVar1_out & " +
			             "tempVar1 = b.aFunc2(inVar2_in).outVar1_out));\n" +
			"function parallelComp (inVar1_in:nat, inVar2_in:nat)outVar1_out:nat\n" +
			"    pre parallelComp_prec(inVar1_in, inVar2_in)\n" +
			"    satisfy result.outVar1_out = a.aFunc1(inVar1_in).outVar1_out;";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	
	@Test
	public void functionAndFunctionWithCommunicationVariablesYieldFunction(){
		String aFuncComm1 = "aFuncComm1{" +
			"inVar1?:!N; " +
			"commVar1!:!N; " +
			"inVar1? > 0; " +
			"commVar1! = balance + inVar1?;}";

		String aFuncCommInAndOutput = "aFuncCommInAndOutput{" +
			"commVar1?:!N; " +
			"outVar1!:!N; " +
			"commVar1? > balance;" +
			"outVar1! = 2 * commVar1?; }";

		String input = "parallelComp ^= a.aFuncCommInAndOutput || b.aFuncComm1 ;";
		
		buildSimpleOperations(aFuncComm1, aFuncCommInAndOutput);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp_prec (inVar1_in:nat): bool\n" +
			"    ^= b.aFuncComm1_prec(inVar1_in) &\n" +
			"       (exists TEMP1:nat :- " +
			                "(TEMP1 = b.aFuncComm1(inVar1_in).commVar1_out & " +
			                "a.aFuncCommInAndOutput_prec(TEMP1)));\n" +
			"function parallelComp (inVar1_in:nat)outVar1_out:nat\n" +
			"    pre parallelComp_prec(inVar1_in)\n" +
			"    satisfy (exists TEMP2:nat :- " +
			                "(TEMP2 = b.aFuncComm1(inVar1_in).commVar1_out & " +
			                 "result.outVar1_out = a.aFuncCommInAndOutput(TEMP2).outVar1_out));";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test
	public void functionAndFunctionWithCommunicationVariablesAndSharedOutputYieldFunction(){
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

		String input = "parallelComp ^= a.func1 || b.func2 ;";

		buildSimpleOperations(aFunc1, aFunc2);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= a.func1_prec(inVar1_in) &\n" +
			"       (exists TEMP1:nat, TEMP2:nat :- " +
			                "(TEMP1 = a.func1(inVar1_in).commVar1_out & " +
			                 "TEMP2 = a.func1(inVar1_in).outVar1_out & " +
			                 "TEMP2 = b.func2(TEMP1, inVar2_in).outVar1_out & " +
			                 "b.func2_prec(TEMP1, inVar2_in)));\n" +
			"function parallelComp (inVar1_in:nat, inVar2_in:nat)outVar1_out:nat\n" +
			"    pre parallelComp_prec(inVar1_in, inVar2_in)\n" +
			"    satisfy result.outVar1_out = a.func1(inVar1_in).outVar1_out;";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	
	@Test
	public void twoFunctionsWithCommunicationVariablesYieldFunction(){
		String aFuncComm1 = "aFuncCommInAndOutput1{" +
			"commVar1?:!N; " +
			"inVar1?:!N; " +
			"commVar2!:!N; " +
			"outVar1!:!N; " +
			"inVar1? > 0; " +
			"commVar1? >= 2 * balance;" +
			"commVar2! = balance + inVar1?;" +
			"outVar1! = 2 * balance;}";

		String aFuncCommInAndOutput = "aFuncCommInAndOutput2{" +
			"commVar2?:!N; " +
			"inVar2?:!N; " +
			"commVar1!:!N; " +
			"outVar2!:!N; " +
			"inVar2? > 0; " +
			"commVar1! = balance + commVar2?;" +
			"outVar2! = 2 * commVar2? + inVar2?; }";

		String input = "parallelComp ^= a.aFuncCommInAndOutput1 || b.aFuncCommInAndOutput2 ;";
		
		buildSimpleOperations(aFuncComm1, aFuncCommInAndOutput);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= (exists TEMP1:nat, TEMP2:nat :- " +
			                "(TEMP1 = b.aFuncCommInAndOutput2(TEMP2, inVar2_in).commVar1_out & " +
			                 "TEMP2 = a.aFuncCommInAndOutput1(TEMP1, inVar1_in).commVar2_out & " +
			                 "a.aFuncCommInAndOutput1_prec(TEMP1, inVar1_in) & " +
			                 "b.aFuncCommInAndOutput2_prec(TEMP2, inVar2_in)));\n" +
			"function parallelComp (inVar1_in:nat, inVar2_in:nat)outVar1_out:nat, outVar2_out:nat\n" +
			"    pre parallelComp_prec(inVar1_in, inVar2_in)\n" +
	    	"    satisfy (exists TEMP3:nat, TEMP4:nat :- "
	    	              + "(TEMP4 = a.aFuncCommInAndOutput1(TEMP3, inVar1_in).commVar2_out & "
	    	              +  "TEMP3 = b.aFuncCommInAndOutput2(TEMP4, inVar2_in).commVar1_out & "
	    	              +  "result.outVar1_out = a.aFuncCommInAndOutput1(TEMP3, inVar1_in).outVar1_out & "
	    	              +  "result.outVar2_out = b.aFuncCommInAndOutput2(TEMP4, inVar2_in).outVar2_out));";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2", "TEMP3", "TEMP4");
	}

	@Test
	public void twoFunctionsWithCommunicationVariablesAndCommonOutputYieldFunction(){
		String aFuncComm1 = "aFuncCommInAndOutput1{" +
			"commVar1?:!N; " +
			"inVar1?:!N; " +
			"commVar2!:!N; " +
			"outVar1!:!N; " +
			"inVar1? > 0; " +
			"commVar1? >= 2 * balance;" +
			"commVar2! = balance + inVar1?;" +
			"outVar1! = 2 * balance;}";

		String aFuncCommInAndOutput = "aFuncCommInAndOutput2{" +
			"commVar2?:!N; " +
			"inVar2?:!N; " +
			"commVar1!:!N; " +
			"outVar1!:!N; " +
			"inVar2? > 0; " +
			"commVar1! = balance + commVar2?;" +
			"outVar1! = 2 * commVar2? + inVar2?; }";

		String input = "parallelComp ^= a.aFuncCommInAndOutput1 || b.aFuncCommInAndOutput2 ;";
		
		buildSimpleOperations(aFuncComm1, aFuncCommInAndOutput);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp_prec (inVar1_in:nat, inVar2_in:nat): bool\n" +
			"    ^= (exists TEMPVAR1:nat, TEMPVAR2:nat, TEMPVAR3:nat :- " +
			                "(TEMPVAR1 = b.aFuncCommInAndOutput2(TEMPVAR2, inVar2_in).commVar1_out & " +
			                 "TEMPVAR2 = a.aFuncCommInAndOutput1(TEMPVAR1, inVar1_in).commVar2_out & " +
			                 "TEMPVAR3 = a.aFuncCommInAndOutput1(TEMPVAR1, inVar1_in).outVar1_out & " +
			                 "TEMPVAR3 = b.aFuncCommInAndOutput2(TEMPVAR2, inVar2_in).outVar1_out & " +
			                 "a.aFuncCommInAndOutput1_prec(TEMPVAR1, inVar1_in) & " +
			                 "b.aFuncCommInAndOutput2_prec(TEMPVAR2, inVar2_in)));\n" +
			"function parallelComp (inVar1_in:nat, inVar2_in:nat)outVar1_out:nat\n" +
			"    pre parallelComp_prec(inVar1_in, inVar2_in)\n" +
	    	"    satisfy (exists TEMPVAR4:nat, TEMPVAR5:nat :- "
	    	              + "(TEMPVAR5 = a.aFuncCommInAndOutput1(TEMPVAR4, inVar1_in).commVar2_out & "
	    	              +  "TEMPVAR4 = b.aFuncCommInAndOutput2(TEMPVAR5, inVar2_in).commVar1_out & "
	    	              +  "result.outVar1_out = a.aFuncCommInAndOutput1(TEMPVAR4, inVar1_in).outVar1_out));";

		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMPVAR1", "TEMPVAR2", "TEMPVAR3", "TEMPVAR4", "TEMPVAR5");
	}
	
	@Test
	public void communicationFromFunctionToChangeOp(){
		String func = "func{" +
			"commVar!:!N; " +
			"outVar!:!N; " +
			"commVar! = 3 * balance;" +
			"outVar1! = 2 * balance;}";
		
		String changeOp = "changeOp{" +
			"delta(stateVar2)" +
			"commVar?:!N; " +
			"commVar? > 0;" +
			"stateVar2' = stateVar2 + commVar?}";

		String input = "parallelComp ^= a.func || b.changeOp ;";
		
		buildSimpleOperations(func, changeOp);
		buildOperationPromotions(input);

		expected = 
			"function parallelComp_prec : bool\n" +
			"    ^= a.func_prec &\n"
	      + "       (exists TEMP1:nat :- " +
			                "(TEMP1 = a.func.commVar_out & " +
			                 "b.changeOp_prec(TEMP1)));\n" +
			"schema !parallelComp (outVar_out!:out nat)\n" +
			"    pre parallelComp_prec\n" +
			"    post outVar_out! = a.func.outVar_out &\n" +
			"         (var TEMP2:nat; " +
			            "(TEMP2! = a.func.commVar_out & " +
			             "b!changeOp(TEMP2')));";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}
	
	@Test
	public void twoChangeOpsWithOneDirectionalCommunication(){
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

		String input = "parallelComp ^= a.changeOp1 || b.changeOp2 ;";
		
		buildSimpleOperations(changeOp1, changeOp2);
		buildOperationPromotions(input);
			
		expected = 
			"function parallelComp_prec (inVar1_in:nat): bool\n" +
			"    ^= a.changeOp1_prec(inVar1_in) &\n" +
			"       (exists TEMP1:nat :- " +
			                "(TEMP1 = a.changeOp1_post(inVar1_in).commVar_out & " +
			                 "b.changeOp2_prec(TEMP1)));\n" +
			"schema !parallelComp (inVar1_in:nat)\n" +
			"    pre parallelComp_prec(inVar1_in)\n" +
			"    post (var TEMP2:nat; " +
			            "(a!changeOp1(inVar1_in, TEMP2!) &" +
			            " b!changeOp2(TEMP2')));";
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test
	public void threeChangeOpsWithOneDirectionalCommunication(){
		String changeOp1 = "changeOp1{" +
			"delta(stateVar1)" +
			"inVar1?:!N; " +
			"commVar1!:!N; " +
			"inVar1? > 0; " +
			"commVar1! = stateVar1 + inVar1?;" +
			"stateVar1' = stateVar1 + 1}";

		String changeOp2 = "changeOp2{" +
			"delta(stateVar2)" +
			"commVar1?:!N; " +
			"commVar2!:!N; " +
			"commVar1? > 0;" +
			"commVar2! = stateVar2 + 2 * commVar1?; " +
			"stateVar2' = stateVar2 + commVar1?}";

		String changeOp3 = "changeOp3{" +
			"delta(stateVar3)" +
			"commVar2?:!N; " +
			"commVar2? > 0;" +
			"stateVar3' = stateVar3 + commVar2?}";

		String input = "parallelComp ^= a.changeOp1 || b.changeOp2 || c.changeOp3;";
		
		buildSimpleOperations(changeOp1, changeOp2, changeOp3);
		buildOperationPromotions(input);
			
		expected = 
			"function parallelComp_prec (inVar1_in:nat): bool\n" +
			"    ^= a.changeOp1_prec(inVar1_in) &\n" +
			"       (exists TEMP1:nat, TEMP2:nat :- " +
			                "(TEMP1 = a.changeOp1_post(inVar1_in).commVar1_out & " +
			                 "TEMP2 = b.changeOp2_post(TEMP1).commVar2_out & " +
			                 "c.changeOp3_prec(TEMP2) & " +
			                 "b.changeOp2_prec(TEMP1)));\n" +
			"schema !parallelComp (inVar1_in:nat)\n" +
			"    pre parallelComp_prec(inVar1_in)\n" +
			"    post (var TEMP3:nat, TEMP4:nat; " +
			            "(a!changeOp1(inVar1_in, TEMP3!) &" +
			            " b!changeOp2(TEMP3', TEMP4!) &" +
			            " c!changeOp3(TEMP4')));";
		
		
		className = "OperationPromotions";
		operationName = "parallelComp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2", "TEMP3", "TEMP4");
	}

}

