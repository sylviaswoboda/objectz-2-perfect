package at.ac.tuwien.oz.translator;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;

public class TranslateClassTest extends TranslationTestBase{

	@Before
	public void setup(){
	}

	@Test
	public void testCreditCard() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/01_CreditCard.oz";
		String referenceFileName = "src/main/resources/reffiles/01_CreditCard.pd";
		
		verifyClassTranslationWithFiles("CreditCard", testFileName, referenceFileName);
	}

	@Test
	public void testBinaryRelation() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/02_BinaryRelationTest.oz";
		String referenceFileName = "src/main/resources/reffiles/02_BinaryRelationTest.pd";
		
		verifyClassTranslationWithFiles("BinaryRelationTest", testFileName, referenceFileName);
	}
	
	@Test
	public void testDatabaseSimple() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/03_DataBaseSimple.oz";
		String referenceFileName = "src/main/resources/reffiles/03_DataBaseSimple.pd";
		
		verifyClassTranslationWithFiles("DataBase", testFileName, referenceFileName);
	}

	@Test
	public void testCreditCards() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/04_CreditCards.oz";
		String referenceFileName1 = "src/main/resources/reffiles/04_CreditCards_CreditCard.pd";
		String referenceFileName2 = "src/main/resources/reffiles/04_CreditCards_CreditCards.pd";
		
		verifyClassTranslationWithFiles("CreditCard", testFileName, referenceFileName1);
		verifyClassTranslationWithFiles("CreditCards", testFileName, referenceFileName2);
	}
	@Test
	public void testScopeEnrichment() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/05_ScopeEnrichment.oz";
		String referenceFileName1 = "src/main/resources/reffiles/05_ScopeEnrichment_CreditCards.pd";
		
		verifyClassTranslationWithFiles("CreditCards", testFileName, referenceFileName1);
	}

	@Test
	public void testTwoCards() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/07_TwoCards.oz";
//		String referenceFileName1 = "src/main/resources/reffiles/07_TwoCards_CreditCard.pd";
		String referenceFileName2 = "src/main/resources/reffiles/07_TwoCards_TwoCards.pd";
		
//		verifyClassTranslationWithFiles("CreditCards", testFileName, referenceFileName1);
		verifyClassTranslationWithFiles("TwoCards", testFileName, referenceFileName2);
	}
	
	@Test
	public void testThesisExample() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/Thesis_Book.oz";
		String referenceFileName1 = "src/main/resources/reffiles/Thesis_Book_String.pd";
		String referenceFileName2 = "src/main/resources/reffiles/Thesis_Book_Book.pd";
		String referenceFileName3 = "src/main/resources/reffiles/Thesis_Book_SmallLibrary.pd";
		verifyDefinitionTranslationWithFiles("String", testFileName, referenceFileName1);
		verifyDefinitionTranslationWithFiles("Book", testFileName, referenceFileName2);
		verifyDefinitionTranslationWithFiles("SmallLibrary", testFileName, referenceFileName3);
		
	}
	
	@Test
	public void testThesisExampleModified() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/Thesis_Book_mod.oz";
		String referenceFileName1 = "src/main/resources/reffiles/Thesis_Book_String.pd";
		String referenceFileName2 = "src/main/resources/reffiles/Thesis_Book_Book_mod.pd";
		String referenceFileName3 = "src/main/resources/reffiles/Thesis_Book_SmallLibrary_mod.pd";
		verifyDefinitionTranslationWithFiles("String", testFileName, referenceFileName1);
		verifyDefinitionTranslationWithFiles("Book", testFileName, referenceFileName2);
		verifyDefinitionTranslationWithFiles("SmallLibrary", testFileName, referenceFileName3);
		
	}

	@Test
	public void testRecension() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/09_OverrideFunction.oz";
		String referenceFileName1 = "src/main/resources/reffiles/09_OverrideFunction.pd";
		verifyDefinitionTranslationWithFiles("OverrideFunction", testFileName, referenceFileName1);
		
	}
	@Test
	public void testKeySystem() throws RecognitionException, IOException{
		String testFileName = "src/main/resources/testfiles/10_KeySystem.oz";
		String referenceFileName1 = "src/main/resources/reffiles/10_KeySystem_Key.pd";
		String referenceFileName2 = "src/main/resources/reffiles/10_KeySystem_Room.pd";
		String referenceFileName3 = "src/main/resources/reffiles/10_KeySystem_KeySystem.pd";
		verifyDefinitionTranslationWithFiles("Room", testFileName, referenceFileName2);
		verifyDefinitionTranslationWithFiles("Key", testFileName, referenceFileName1);
		// TODO this does not yet work -> future work
//		verifyDefinitionTranslationWithFiles("KeySystem", testFileName, referenceFileName3);
		
	}

}
