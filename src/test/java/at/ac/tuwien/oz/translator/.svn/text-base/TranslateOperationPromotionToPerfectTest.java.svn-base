package at.ac.tuwien.oz.translator;

import org.junit.Before;
import org.junit.Test;

import at.ac.tuwien.oz.definitions.operation.OperationPromotion;


public class TranslateOperationPromotionToPerfectTest extends TranslationTestBase{
	
	@Before
	public void setup(){
		this.expectedClass = OperationPromotion.class;
	}
	
	@Test
	public void promoteBoolFunctionOperationOfSameClass(){
		input = 
				"class OperationPromotions {"
				+ 	"state{"
				+ 		"stateVar: !N;"
				+ 	"}"
				+ 	"aBoolFunc{"
				+ 		"inVar1?:!N; "
				+ 		"stateVar > inVar1?;"
				+ 	"}"
				+ 	"promotedBoolFunc ^= aBoolFunc;"
				+ "}";
		
		expected = 
				"function promotedBoolFunc (inVar1_in:nat): bool\n" + 
						"    ^= aBoolFunc(inVar1_in);";
		
		className = "OperationPromotions";
		operationName = "promotedBoolFunc";
		
		verifyOperationTranslation(operationName, className, input, expected);
	}

	@Test
	public void promoteFunctionOperationOfSameClass(){
		input = 
				"class OperationPromotions {"
				+ 	"state{"
				+ 		"balance: !N; "
				+ 	"}"
				+ 	"fundsAvail{"
				+ 		"limit?:!N; "
				+ 		"funds1!:!N; "
				+ 		"funds2!:!N; "
				+ 		"limit? > 0; "
				+ 		"funds1! = balance + limit?; "
				+ 		"funds2! = 2 * balance + limit?;"
				+ 	"}"
				+ 	"getAvailableFunds ^= fundsAvail;"
				+ "}";
		expected = 
				"function getAvailableFunds_prec (limit_in:nat): bool\n" +
				"    ^= fundsAvail_prec(limit_in);\n" +
				"function getAvailableFunds (limit_in:nat)funds1_out:nat, funds2_out:nat\n" + 
				"    pre getAvailableFunds_prec(limit_in)\n" +
				"    satisfy result.funds1_out = fundsAvail(limit_in).funds1_out &\n" +
				"            result.funds2_out = fundsAvail(limit_in).funds2_out;"; 
		
		className = "OperationPromotions";
		operationName = "getAvailableFunds";
		
		verifyOperationTranslation(operationName, className, input, expected);
	}
	
	@Test
	public void promoteFunctionOperationWithImplicitPreconditionOnly(){
		input = 
				"class OperationPromotions {"
				+ 	"state{"
				+ 		"balance: !N; "
				+ 	"}"
				+ 	"fundsAvail{"
				+ 		"limit?:!N; "
				+ 		"funds1!:!N; "
				+ 		"funds2!:!N; "
				+ 		"funds1! = balance + limit?; "
				+ 		"funds2! = 2 * balance + limit?;"
				+ 	"}"
				+ 	"getAvailableFunds ^= fundsAvail;"
				+ "}";
		
		expected = 
				"function getAvailableFunds_prec (limit_in:nat): bool\n" +
				"    ^= fundsAvail_prec(limit_in);\n" +
				"function getAvailableFunds (limit_in:nat)funds1_out:nat, funds2_out:nat\n" + 
				"    pre getAvailableFunds_prec(limit_in)\n" +
				"    satisfy result.funds1_out = fundsAvail(limit_in).funds1_out &\n" +
				"            result.funds2_out = fundsAvail(limit_in).funds2_out;"; 
		
		className = "OperationPromotions";
		operationName = "getAvailableFunds";
		
		verifyOperationTranslation(operationName, className, input, expected);
		
	}

	@Test
	public void promoteChangeOperationOfSameClass(){
		input = "class OperationPromotions {"
				+ 	"state{"
				+ 		"stateVar1: !N;"
				+ 		"stateVar2: !N;"
				+ 	"}"
				+ 	"aChangeOp{" 
				+		"delta(stateVar1, stateVar2)" 
				+		"inVar1?:!N; " 
				+		"inVar2?:!N; " 
				+		"outVar1!:!Z; " 
				+		"outVar2!:!Z; " 
				+		"inVar1? > inVar2?; " 
				+		"outVar1! = stateVar1 + stateVar2;" 
				+		"outVar2! = stateVar2 - stateVar2;" 
				+		"stateVar1' = stateVar1 - inVar1?;" 
				+		"stateVar2' = stateVar2 + inVar2?;"
				+ 	"}"
				+ 	"opPromo ^= aChangeOp;"
				+ "}";
		
		expected = 
				"function opPromo_prec (inVar1_in:nat, inVar2_in:nat): bool\n" + 
				"    ^= aChangeOp_prec(inVar1_in, inVar2_in);\n" +
				"schema !opPromo (inVar1_in:nat, inVar2_in:nat, outVar1_out!:out int, outVar2_out!:out int)\n" +
				"    pre opPromo_prec(inVar1_in, inVar2_in)\n" +
				"    post !aChangeOp(inVar1_in, inVar2_in, outVar1_out!, outVar2_out!);";
		
		className = "OperationPromotions";
		operationName = "opPromo";
		
		verifyOperationTranslation(operationName, className, input, expected);
	}
	
	@Test
	public void promoteBoolFunctionOperationOfAnotherClass(){
		input = "class SimpleDeclarations {"
				+ 	"state{"
				+ 		"stateVar: !N;"
				+ 	"}"
				+ 	"aBoolFunc{"
				+		"inVar1?:!N; " 
				+ 		"stateVar > inVar1?;"
				+ 	"}"
				+ "}"
				+ "class OperationPromotions {"
				+ 	"state{"
				+ 		"a: SimpleDeclarations;"
				+ 	"}"
				+ 	"promotedBoolFunc ^= a.aBoolFunc;"
				+ "}";
		
		expected = 
				"function promotedBoolFunc (inVar1_in:nat): bool\n" + 
				"    ^= a.aBoolFunc(inVar1_in);";
		
		className = "OperationPromotions";
		operationName = "promotedBoolFunc";
		
		verifyOperationTranslation(operationName, className, input, expected);

	}
	
	@Test
	public void promoteFunctionOperationOfAnotherClass(){
		input = "class SimpleDeclarations {"
				+ 	"state{"
				+ 		"balance: !N;"
				+ 	"}"
				+ 	"fundsAvail{"
				+ 		"limit?:!N; " 
				+		"funds1!:!N; " 
				+		"funds2!:!N; " 
				+		"limit? > 0; " 
				+		"funds1! = balance + limit?; "
				+ 		"funds2! = 2 * balance + limit?;"
				+ 	"}"
				+ "}"
				+ "class OperationPromotions {"
				+ 	"state{"
				+ 		"a: SimpleDeclarations;"
				+ 	"}"
				+ 	"getAvailableFunds ^= a.fundsAvail;"
				+ "}";
		
		expected = 
				"function getAvailableFunds_prec (limit_in:nat): bool\n" +
				"    ^= a.fundsAvail_prec(limit_in);\n" +
				"function getAvailableFunds (limit_in:nat)funds1_out:nat, funds2_out:nat\n" + 
				"    pre getAvailableFunds_prec(limit_in)\n" +
				"    satisfy result.funds1_out = a.fundsAvail(limit_in).funds1_out &\n" +
				"            result.funds2_out = a.fundsAvail(limit_in).funds2_out;";
		
		className = "OperationPromotions";
		operationName = "getAvailableFunds";
		
		verifyOperationTranslation(operationName, className, input, expected);
	}

	@Test
	public void promoteChangeOperationOfAnotherClass(){
		input = "class SimpleDeclarations {"
				+ 	"state{"
				+ 		"stateVar1: !N;"
				+ 		"stateVar2: !N;"
				+ 	"}"
				+ 	"aChangeOp{" 
				+		"delta(stateVar1, stateVar2)" 
				+		"inVar1?:!N; " 
				+		"inVar2?:!N; " 
				+		"outVar1!:!Z; " 
				+		"outVar2!:!Z; " 
				+		"inVar1? > inVar2?; " 
				+		"outVar1! = stateVar1 + stateVar2; " 
				+		"outVar2! = stateVar2 - stateVar2;" 
				+		"stateVar1' = stateVar1 - inVar1?;" 
				+		"stateVar2' = stateVar2 + inVar2?;"
				+ 	"}"
				+ "}"
				+ "class OperationPromotions {"
				+ 	"state{"
				+ 		"a: SimpleDeclarations;"
				+ 	"}"
				+ 	"opPromo ^= a.aChangeOp;"
				+ "}";
		
		expected = 
				"function opPromo_prec (inVar1_in:nat, inVar2_in:nat): bool\n" + 
				"    ^= a.aChangeOp_prec(inVar1_in, inVar2_in);\n" +
				"schema !opPromo (inVar1_in:nat, inVar2_in:nat, outVar1_out!:out int, outVar2_out!:out int)\n" +
				"    pre opPromo_prec(inVar1_in, inVar2_in)\n" +
				"    post a!aChangeOp(inVar1_in, inVar2_in, outVar1_out!, outVar2_out!);";
		
		className = "OperationPromotions";
		operationName = "opPromo";
		
		verifyOperationTranslation(operationName, className, input, expected);
	}
}
