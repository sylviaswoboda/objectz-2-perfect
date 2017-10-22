package at.ac.tuwien.oz.translator;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;

import at.ac.tuwien.AssertCollection;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.definitions.IDefinition;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.Operation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.main.OZTranslator;
import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;
import at.ac.tuwien.oz.translator.uat.AcceptanceTestHelper;

public class TranslationTestBase {

	protected TypeEvaluator typeEvaluator;
	
	protected OZTranslator ozTranslator;
	private ParseTree tree;
	private ObjectZDefinition definition;
	private ObjectZClass operationPromotions;
	protected String input;
	protected String expected;
	protected String className;
	protected String operationName;
	
	protected Class<?> expectedClass;

	public TranslationTestBase() {
		super();
	}

	@Before
	public void before(){
		TempVarProvider.resetNameCounter();
		typeEvaluator = Mockito.mock(TypeEvaluator.class);
	}
	
	protected OZParser getParser(String input) {
		CharStream stream = CharStreams.fromString(input);
		return getParser(stream);
	}

	private OZParser getParser(CharStream stream) {
		OZLexer lexer = new OZLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		OZParser parser = new OZParser(tokens);
		return parser;
	}

	protected void buildTranslatorForOperationSchemaDef(String input) {
		OZParser parser = getParser(input);
		this.tree = parser.operationSchemaDef();
		this.ozTranslator = new OZTranslator(tree);
	}
	
	protected void buildTranslatorForProgram(String input) {
		OZParser parser = getParser(input);
		this.tree = parser.program();
		this.ozTranslator = new OZTranslator(tree);
	}
	
	protected void buildTranslatorForProgramFromFile(String fileName) throws IOException {
		CharStream charStream = CharStreams.fromFileName(fileName);
		OZParser parser = getParser(charStream);
		this.tree = parser.program();
		this.ozTranslator = new OZTranslator(tree);
	}
	
	protected void verifyOperationTranslation(String operationName, String inClass, String givenInput, String expectedOperationOutput) {
		Operation operation = verifyOperationTranslation(operationName, inClass, givenInput);
		Assert.assertEquals(expectedOperationOutput, operation.getTemplate().render());
	}

	protected void verifyOperationTranslationWithPlaceholders(
			String operationName, String inClass, String givenInput, 
			String expectedOperationOutput, String ... placeholders) {
		Operation operation = verifyOperationTranslation(operationName, inClass, givenInput);
//		Assert.assertEquals(expectedOperationOutput, operation.getTemplate().render());
		AssertCollection.assertStringsMatchWithPlaceholders(operation.getTemplate().render(), expectedOperationOutput, placeholders);

	}

	private Operation verifyOperationTranslation(String operationName, String inClass, String givenInput) {
		buildTranslatorForProgram(givenInput);
		this.ozTranslator.translate();
		
		this.definition = this.ozTranslator.getDefinition();
		this.operationPromotions = this.definition.resolveClass(new Ident(inClass));
		
		Operation operation = operationPromotions.resolveOperation(new Ident(operationName));
		
		Assert.assertNotNull(operation);
		Assert.assertEquals(expectedClass, operation.getClass());
		return operation;
	}
	
	protected void verifyClassTranslationWithFiles(String className, String inputFileName, String expectedOutputFileName) throws IOException {
		ObjectZClass clazz = verifyClassTranslation(className, inputFileName);
		String expectedFileContent = AcceptanceTestHelper.getTextFileContent(expectedOutputFileName);
		expectedFileContent = expectedFileContent.replaceAll("\r\n", "\n");
		String actualOutput = clazz.getTemplate().render();
		Assert.assertEquals(expectedFileContent, actualOutput);
	}

	private ObjectZClass verifyClassTranslation(String className, String inputFileName) throws IOException {
		buildTranslatorForProgramFromFile(inputFileName);
		this.ozTranslator.translate();
		
		this.definition = this.ozTranslator.getDefinition();
		ObjectZClass clazz = this.definition.resolveClass(new Ident(className));
		
		Assert.assertNotNull(clazz);
		return clazz;
	}

	private IDefinition verifyDefinitionTranslation(String definitionName, String inputFileName) throws IOException {
		buildTranslatorForProgramFromFile(inputFileName);
		this.ozTranslator.translate();
		
		this.definition = this.ozTranslator.getDefinition();
		IDefinition localDefinition = this.definition.resolve(new Ident(definitionName));
		
		Assert.assertNotNull(localDefinition);
		return localDefinition;
	}
	
	protected void verifyDefinitionTranslationWithFiles(String definitionName, String inputFileName, String expectedOutputFileName) throws IOException {
		IDefinition definition = verifyDefinitionTranslation(definitionName, inputFileName);
		String expectedFileContent = AcceptanceTestHelper.getTextFileContent(expectedOutputFileName);
		expectedFileContent = expectedFileContent.replaceAll("\r\n", "\n");
		String actualOutput = definition.getTemplate().render();
		Assert.assertEquals(expectedFileContent, actualOutput);
	}
}