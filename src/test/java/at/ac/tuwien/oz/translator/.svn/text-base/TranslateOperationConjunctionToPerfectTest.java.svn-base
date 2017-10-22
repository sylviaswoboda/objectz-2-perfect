package at.ac.tuwien.oz.translator;

import org.junit.Before;
import org.junit.Test;

import at.ac.tuwien.oz.definitions.operation.OperationConjunction;

public class TranslateOperationConjunctionToPerfectTest extends TranslationTestBase{

	private String simpleOperationClassInput;
	private String operationPromotionClassInput;
	
	private String aBoolOp1;
	private String aBoolOp2;
	private String aFunc1;
	private String aFunc3;
	private String aFunc4;
	private String aChangeOp1;
	private String aChangeOp2;
	private String aChangeOp3;
	private String aChangeOp4;
	private String aChangeOp5;
	private String aChangeOp6;
	private String aChangeOp7;
	
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
	
	@Before
	public void setup(){
		expectedClass = OperationConjunction.class;
		aBoolOp1 = "aBoolFunc1{" +
							"inVarOp1?:!N; " +
							"stateVar > inVarOp1?;}";
		aBoolOp2 = "aBoolFunc2{" +
							"inVarOp2?:!N; " +
							"stateVar > inVarOp2?;}";

		aFunc1 = "aFunc1{" +
							"inVarOp1?:!N; " +
							"outVarOp1!:!N; " +
							"inVarOp1? > 0; " +
							"outVarOp1! = balance + inVarOp1?;}";

		
		aFunc3 = "aFunc3{" +
							"outVarOp1!:!N; " +
							"outVarOp2!:!N; " +
							"outVarOp1! = 3 * balance;" +
							"outVarOp2! = 2 * balance;}";
		
		aFunc4 = "aFunc4{" +
							"outVarOp2!:!N; " +
							"outVarOp3!:!N; " +
							"outVarOp2! = balance + 100;" +
							"outVarOp3! = balance + 200;}";

		
		aChangeOp1 = "aChangeOp1{" +
									"delta(stateVar1)" +
									"inVarOp1?:!N; " +
									"outVarOp1!:!Z; " + 
									"inVarOp1? > stateVar1; " +
									"outVarOp1! = stateVar1 + stateVar2; " +
									"stateVar1' = stateVar1 - inVarOp1?;}";

		aChangeOp2 = "aChangeOp2{" +
									"delta(stateVar2)" +
									"inVarOp2?:!N; " +
									"outVarOp2!:!Z; " + 
									"inVarOp2? > stateVar2; " +
									"outVar2! = stateVar2; " +
									"stateVar2' = stateVar2 - inVarOp2?;}";
		
		aChangeOp3 = "aChangeOp3{" +
								"delta(stateVar1)" +
								"inVarOp1?:!N; " +
								"inVarOp2?:!N; " +
								"inVarOp1? > stateVar1; " +
								"stateVar1' = inVarOp1? + inVarOp2?;}";

		aChangeOp4 = "aChangeOp4{" +
								"delta(stateVar2)" +
								"inVarOp2?:!N; " +
								"inVarOp3?:!N; " +
								"inVarOp2? > stateVar1; " +
								"stateVar2' = inVarOp2? + inVarOp3?;}";

		aChangeOp5 = "aChangeOp5{" +
								"delta(stateVar1)" +
								"outVarOp1!:!N; " +
								"outVarOp2!:!N; " +
								"outVarOp1! = stateVar1;" +
								"outVarOp2! = stateVar1*2;" +
								"stateVar1' = stateVar2 * stateVar3;}";

		aChangeOp6 = "aChangeOp6{" +
								"delta(stateVar2)" +
								"outVarOp2!:!N; " +
								"outVarOp3!:!N; " +
								"outVarOp2! = stateVar2;" +
								"outVarOp3! = stateVar2*2;" +
								"stateVar2' = 3 * stateVar3;}";

		aChangeOp7 = "aChangeOp7{" +
								"delta(stateVar3)" +
								"outVarOp3!:!N; " +
								"outVarOp4!:!N; " +
								"outVarOp3! = stateVar3;" +
								"outVarOp4! = stateVar3*3;" +
								"stateVar3' = 3 * stateVar3;}";
		
	}
	
	private String getProgram() {
		return this.simpleOperationClassInput + this.operationPromotionClassInput;
	}



	@Test
	public void boolFunctionAndBoolFunctionDifferentObjects(){
		String input = "conjOp ^= a.aBoolFunc1 && b.aBoolFunc2;";
		
		buildSimpleOperations(aBoolOp1, aBoolOp2);
		buildOperationPromotions(input);

		expected = 
			"function conjOp (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aBoolFunc1(inVarOp1_in) &\n" +
			"       b.aBoolFunc2(inVarOp2_in);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}


	@Test
	public void boolFunctionAndBoolFunctionSameClass(){
		String input = "conjOp ^= aBoolFunc1 && aBoolFunc2;";
		
		buildSimpleOperations(aBoolOp1, aBoolOp2, input);
		buildOperationPromotions();
			
		expected = 
			"function conjOp (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= aBoolFunc1(inVarOp1_in) &\n" +
			"       aBoolFunc2(inVarOp2_in);";
		
		className = "SimpleOperations";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void functionAndBoolFunctionDifferentObjects(){
		String input = "conjOp ^= a.aFunc1 && b.aBoolFunc2;";
			
		buildSimpleOperations(aFunc1, aBoolOp2);
		buildOperationPromotions(input);

		expected = 
			"function conjOp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVarOp1_in) &\n" +
			"       b.aBoolFunc2(inVarOp2_in);\n" +
			"function conjOp (inVarOp1_in:nat, inVarOp2_in:nat)outVarOp1_out:nat\n" +
			"    pre conjOp_prec(inVarOp1_in, inVarOp2_in)\n" +
			"    satisfy result.outVarOp1_out = a.aFunc1(inVarOp1_in).outVarOp1_out;";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);

	}

	@Test
	public void boolFunctionAndFunctionDifferentObjects(){
		String input = "conjOp ^= a.aBoolFunc2 && b.aFunc1;";

		buildSimpleOperations(aFunc1, aBoolOp2);
		buildOperationPromotions(input);

		expected = 
			"function conjOp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aBoolFunc2(inVarOp2_in) &\n" +
			"       b.aFunc1_prec(inVarOp1_in);\n" +
			"function conjOp (inVarOp1_in:nat, inVarOp2_in:nat)outVarOp1_out:nat\n" +
			"    pre conjOp_prec(inVarOp1_in, inVarOp2_in)\n" +
			"    satisfy result.outVarOp1_out = b.aFunc1(inVarOp1_in).outVarOp1_out;";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);

	}

	@Test
	public void boolFunctionAndChangeOpDifferentObjects(){
		String input = "conjOp ^= a.aBoolFunc1 && b.aChangeOp2;";
		
		buildSimpleOperations(aBoolOp1, aChangeOp2);
		buildOperationPromotions(input);
			
		expected = 
			"function conjOp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aBoolFunc1(inVarOp1_in) &\n" +
			"       b.aChangeOp2_prec(inVarOp2_in);\n" +
			"schema !conjOp (inVarOp1_in:nat, inVarOp2_in:nat, outVarOp2_out!:out int)\n" +
			"    pre conjOp_prec(inVarOp1_in, inVarOp2_in)\n" +
			"    post b!aChangeOp2(inVarOp2_in, outVarOp2_out!);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);

	}

	@Test
	public void functionAndChangeOpDifferentObjects(){
		String input = "conjOp ^= a.aFunc1 && b.aChangeOp2;";
		
		buildSimpleOperations(aFunc1, aChangeOp2);
		buildOperationPromotions(input);
			
		expected = 
			"function conjOp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aFunc1_prec(inVarOp1_in) &\n" +
			"       b.aChangeOp2_prec(inVarOp2_in);\n" +
			"schema !conjOp (inVarOp1_in:nat, inVarOp2_in:nat, outVarOp1_out!:out nat, outVarOp2_out!:out int)\n" +
			"    pre conjOp_prec(inVarOp1_in, inVarOp2_in)\n" +
			"    post outVarOp1_out! = a.aFunc1(inVarOp1_in).outVarOp1_out &\n" +
			"         b!aChangeOp2(inVarOp2_in, outVarOp2_out!);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void changeOpAndFunctionDifferentObjects(){
		String input = "conjOp ^= a.aChangeOp2 && b.aFunc1;";
		
		buildSimpleOperations(aFunc1, aChangeOp2);
		buildOperationPromotions(input);
			
		expected = 
			"function conjOp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aChangeOp2_prec(inVarOp2_in) &\n" +
			"       b.aFunc1_prec(inVarOp1_in);\n" +
			"schema !conjOp (inVarOp1_in:nat, inVarOp2_in:nat, outVarOp1_out!:out nat, outVarOp2_out!:out int)\n" +
			"    pre conjOp_prec(inVarOp1_in, inVarOp2_in)\n" +
			"    post outVarOp1_out! = b.aFunc1(inVarOp1_in).outVarOp1_out &\n" +
			"         a!aChangeOp2(inVarOp2_in, outVarOp2_out!);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void changeOpAndChangeOpDifferentObjects(){
		String input = "conjOp ^= a.aChangeOp1 && b.aChangeOp2;";
			
		buildSimpleOperations(aChangeOp1, aChangeOp2);
		buildOperationPromotions(input);
			
		expected = 
			"function conjOp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aChangeOp1_prec(inVarOp1_in) &\n" +
			"       b.aChangeOp2_prec(inVarOp2_in);\n" +
			"schema !conjOp (inVarOp1_in:nat, inVarOp2_in:nat, outVarOp1_out!:out int, outVarOp2_out!:out int)\n" +
			"    pre conjOp_prec(inVarOp1_in, inVarOp2_in)\n" +
			"    post a!aChangeOp1(inVarOp1_in, outVarOp1_out!) &\n" +
			"         b!aChangeOp2(inVarOp2_in, outVarOp2_out!);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void changeOpAndChangeOpSharedInput(){
		String input = "conjOp ^= a.aChangeOp3 && b.aChangeOp4;";

		buildSimpleOperations(aChangeOp3, aChangeOp4);
		buildOperationPromotions(input);
		
		expected = 
			"function conjOp_prec (inVarOp1_in:nat, inVarOp2_in:nat, inVarOp3_in:nat): bool\n" +
			"    ^= a.aChangeOp3_prec(inVarOp1_in, inVarOp2_in) &\n" +
			"       b.aChangeOp4_prec(inVarOp2_in, inVarOp3_in);\n" +
			"schema !conjOp (inVarOp1_in:nat, inVarOp2_in:nat, inVarOp3_in:nat)\n" +
			"    pre conjOp_prec(inVarOp1_in, inVarOp2_in, inVarOp3_in)\n" +
			"    post a!aChangeOp3(inVarOp1_in, inVarOp2_in) &\n" +
			"         b!aChangeOp4(inVarOp2_in, inVarOp3_in);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void twoFunctionsWithSharedOutputDifferentObjects(){
		String input = "conjOp ^= a.aFunc3 && b.aFunc4;";
		
		buildSimpleOperations(aFunc3, aFunc4);
		buildOperationPromotions(input);

		expected = 
			"function conjOp_prec : bool\n" +
			"    ^= a.aFunc3_prec &\n" + 
			"       b.aFunc4_prec &\n" +
			"       (exists tempVar1:nat :- (tempVar1 = a.aFunc3.outVarOp2_out & tempVar1 = b.aFunc4.outVarOp2_out));\n" +
			"function conjOp outVarOp1_out:nat, outVarOp2_out:nat, outVarOp3_out:nat\n" +
			"    pre conjOp_prec\n" +
			"    satisfy result.outVarOp1_out = a.aFunc3.outVarOp1_out &\n" +
			"            result.outVarOp2_out = a.aFunc3.outVarOp2_out &\n" +
			"            result.outVarOp3_out = b.aFunc4.outVarOp3_out;" ;
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void functionAndChangeOpWithSharedOutputDifferentObjects(){
		String input = "conjOp ^= a.aFunc3 && b.aChangeOp6;";
		
		buildSimpleOperations(aFunc3, aChangeOp6);
		buildOperationPromotions(input);

		// outVarOp2 function output promotion is not shown combined postcondition.
		// we don't need a tempVar then
		expected = 
			"function conjOp_prec : bool\n" +
			"    ^= a.aFunc3_prec &\n" +
			"       b.aChangeOp6_prec &\n" + 
			"       (exists tempVar1:nat :- (tempVar1 = a.aFunc3.outVarOp2_out & tempVar1 = b.aChangeOp6_post.outVarOp2_out));\n" +
			"schema !conjOp (outVarOp1_out!:out nat, outVarOp2_out!:out nat, outVarOp3_out!:out nat)\n" +
			"    pre conjOp_prec\n" +
			"    post outVarOp1_out! = a.aFunc3.outVarOp1_out &\n" +
			"         b!aChangeOp6(outVarOp2_out!, outVarOp3_out!);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	
	@Test
	public void changeOpAndFunctionWithSharedOutputDifferentObjects(){
		String input = "conjOp ^= a.aChangeOp6 && b.aFunc3;";
		
		buildSimpleOperations(aFunc3, aChangeOp6);
		buildOperationPromotions(input);

		// outVarOp2 function output promotion is not shown combined postcondition.
		// we don't need a tempVar then
		expected = 
			"function conjOp_prec : bool\n" +
			"    ^= a.aChangeOp6_prec &\n" + 
			"       b.aFunc3_prec &\n" + 
			"       (exists tempVar1:nat :- (tempVar1 = a.aChangeOp6_post.outVarOp2_out & tempVar1 = b.aFunc3.outVarOp2_out));\n" +
			"schema !conjOp (outVarOp1_out!:out nat, outVarOp2_out!:out nat, outVarOp3_out!:out nat)\n" +
			"    pre conjOp_prec\n" +
			"    post outVarOp1_out! = b.aFunc3.outVarOp1_out &\n" +
			"         a!aChangeOp6(outVarOp2_out!, outVarOp3_out!);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}
	
	@Test
	public void twoChangeOpsWithSharedOutputDifferentObjects(){
		String input = "conjOp ^= a.aChangeOp5 && b.aChangeOp6;";
		
		buildSimpleOperations(aChangeOp5, aChangeOp6);
		buildOperationPromotions(input);

		expected = 
			"function conjOp_prec : bool\n" +
			"    ^= a.aChangeOp5_prec &\n" + 
			"       b.aChangeOp6_prec &\n" +
			"       (exists TEMP1:nat :- (TEMP1 = a.aChangeOp5_post.outVarOp2_out & TEMP1 = b.aChangeOp6_post.outVarOp2_out));\n" +
			"schema !conjOp (outVarOp1_out!:out nat, outVarOp2_out!:out nat, outVarOp3_out!:out nat)\n" +
			"    pre conjOp_prec\n" +
			"    post (var TEMP2:nat; (a!aChangeOp5(outVarOp1_out!, outVarOp2_out!) & b!aChangeOp6(TEMP2!, outVarOp3_out!)));";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}
	
	@Test
	public void changeOpAndChangeOpSameObject(){
		String input = "conjOp ^= a.aChangeOp1 && a.aChangeOp2;";

		buildSimpleOperations(aChangeOp1, aChangeOp2);
		buildOperationPromotions(input);

		expected = 
			"function conjOp_prec (inVarOp1_in:nat, inVarOp2_in:nat): bool\n" +
			"    ^= a.aChangeOp1_prec(inVarOp1_in) &\n" +
			"       a.aChangeOp2_prec(inVarOp2_in);\n" +
			"schema !conjOp (inVarOp1_in:nat, inVarOp2_in:nat, outVarOp1_out!:out int, outVarOp2_out!:out int)\n" +
			"    pre conjOp_prec(inVarOp1_in, inVarOp2_in)\n" +
			"    post a!aChangeOp1(inVarOp1_in, outVarOp1_out!) then\n" +
			"         a!aChangeOp2(inVarOp2_in, outVarOp2_out!);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	@Test
	public void changeOpAndFunctionWithSharedOutputSameObject(){
		String input = "conjOp ^= a.aChangeOp6 && a.aFunc3;";
		
		buildSimpleOperations(aChangeOp6, aFunc3);
		buildOperationPromotions(input);

		// outVarOp2 function output promotion is not shown combined postcondition.
		// we don't need a tempVar then
		expected = 
			"function conjOp_prec : bool\n" +
			"    ^= a.aChangeOp6_prec &\n" + 
			"       a.aFunc3_prec &\n" +
			"       (exists tempVar1:nat :- (tempVar1 = a.aChangeOp6_post.outVarOp2_out & tempVar1 = a.aFunc3.outVarOp2_out));\n" +
			"schema !conjOp (outVarOp1_out!:out nat, outVarOp2_out!:out nat, outVarOp3_out!:out nat)\n" +
			"    pre conjOp_prec\n" +
			"    post outVarOp1_out! = a.aFunc3.outVarOp1_out &\n" +
			"         a!aChangeOp6(outVarOp2_out!, outVarOp3_out!);";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslation(operationName, className, getProgram(), expected);
	}

	
	@Test
	public void twoChangeOpsWithSharedOutputSameObject(){
		String input = "conjOp ^= a.aChangeOp5 && a.aChangeOp6;";
		
		buildSimpleOperations(aChangeOp5, aChangeOp6);
		buildOperationPromotions(input);

		expected = 
			"function conjOp_prec : bool\n" +
			"    ^= a.aChangeOp5_prec &\n"+
			"       a.aChangeOp6_prec &\n"+ 
			"       (exists TEMP1:nat :- (TEMP1 = a.aChangeOp5_post.outVarOp2_out & TEMP1 = a.aChangeOp6_post.outVarOp2_out));\n" +
			"schema !conjOp (outVarOp1_out!:out nat, outVarOp2_out!:out nat, outVarOp3_out!:out nat)\n" +
			"    pre conjOp_prec\n" +
			"    post (var TEMP2:nat; (a!aChangeOp5(outVarOp1_out!, outVarOp2_out!) then\n" +
			"         a!aChangeOp6(TEMP2!, outVarOp3_out!)));";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2");
	}

	@Test
	public void threeChangeOpsWithSharedOutputDifferentObjects(){
		String input = "conjOp ^= a.aChangeOp5 && b.aChangeOp6 && c.aChangeOp7;";
		
		buildSimpleOperations(aChangeOp5, aChangeOp6, aChangeOp7);
		buildOperationPromotions(input);

		expected = 
			"function conjOp_prec : bool\n" +
			"    ^= a.aChangeOp5_prec &\n" + 
			"       b.aChangeOp6_prec &\n" +
			"       c.aChangeOp7_prec &\n" + 
			"       (exists TEMP1:nat, TEMP2:nat :- "
					+ "(TEMP1 = a.aChangeOp5_post.outVarOp2_out &"
					+ " TEMP1 = b.aChangeOp6_post.outVarOp2_out &"
					+ " TEMP2 = b.aChangeOp6_post.outVarOp3_out &"
					+ " TEMP2 = c.aChangeOp7_post.outVarOp3_out));\n" +
			"schema !conjOp (outVarOp1_out!:out nat, outVarOp2_out!:out nat, outVarOp3_out!:out nat, outVarOp4_out!:out nat)\n" +
			"    pre conjOp_prec\n" +
			"    post (var TEMP3:nat, TEMP4:nat; (a!aChangeOp5(outVarOp1_out!, outVarOp2_out!) & " +
					"b!aChangeOp6(TEMP3!, outVarOp3_out!) & " +
					"c!aChangeOp7(TEMP4!, outVarOp4_out!)));";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2", "TEMP3", "TEMP4");
	}

	@Test
	public void threeChangeOpsWithSharedOutputSameObjects(){
		String input = "conjOp ^= a.aChangeOp5 && a.aChangeOp6 && a.aChangeOp7;";
		
		buildSimpleOperations(aChangeOp5, aChangeOp6, aChangeOp7);
		buildOperationPromotions(input);

		expected = 
			"function conjOp_prec : bool\n" +
			"    ^= a.aChangeOp5_prec &\n"+ 
			"       a.aChangeOp6_prec &\n" + 
			"       a.aChangeOp7_prec &\n" + 
			"       (exists TEMP1:nat, TEMP2:nat :- "
						+ "(TEMP1 = a.aChangeOp5_post.outVarOp2_out & "
						+  "TEMP1 = a.aChangeOp6_post.outVarOp2_out & "
						+  "TEMP2 = a.aChangeOp6_post.outVarOp3_out & "
						+  "TEMP2 = a.aChangeOp7_post.outVarOp3_out));\n" +
			"schema !conjOp (outVarOp1_out!:out nat, outVarOp2_out!:out nat, outVarOp3_out!:out nat, outVarOp4_out!:out nat)\n" +
			"    pre conjOp_prec\n" +
			"    post (var TEMP3:nat, TEMP4:nat; (a!aChangeOp5(outVarOp1_out!, outVarOp2_out!) then\n" +
			"         a!aChangeOp6(TEMP3!, outVarOp3_out!) then\n" +
			"         a!aChangeOp7(TEMP4!, outVarOp4_out!)));";
		
		className = "OperationPromotions";
		operationName = "conjOp";
		
		verifyOperationTranslationWithPlaceholders(operationName, className, getProgram(), expected, "TEMP1", "TEMP2", "TEMP3", "TEMP4");
	}

	
}
