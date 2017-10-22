package at.ac.tuwien.oz.translator.typeevaluator;

import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.local.FreeType;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.definitions.ozclass.StateSchema;
import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;
import at.ac.tuwien.oz.parser.OZParser.DeclarationContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationListContext;
import at.ac.tuwien.oz.parser.OZParser.IdContext;
import at.ac.tuwien.oz.parser.OZParser.ProgramContext;
import at.ac.tuwien.oz.scopes.LocalScope;
import at.ac.tuwien.oz.translator.TranslationTestBase;
import at.ac.tuwien.oz.translator.symboldefinition.IdentifierCollector;
import at.ac.tuwien.oz.translator.symboldefinition.SymbolCollector;

public class TypeDeclarationEvaluatorTest extends TranslationTestBase{

	@Mock 
	private ParseTreeProperty<Variable> declarationTree;
	@Mock
	private ParseTreeProperty<LocalScope> localScopeTree;
	@Mock
	private ObjectZDefinition program;
	
	@Mock
	private ParseTree irrelevantCtx;
	
	private ParseTreeWalker walker;
	private TypeDeclarationEvaluator eval;
	private ParseTreeProperty<ExpressionType> typeTree;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		walker = new ParseTreeWalker();
		eval = new TypeDeclarationEvaluator(program, declarationTree, localScopeTree);
		typeTree = eval.getExpressionTypeTree();
	}
	
	@Test
	public void typeOfIdNodeIsTypeOfDeclaredVariable() {
		CharStream input = CharStreams.fromString("value1?");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTree parseTree = parser.id();
		declare(new Ident("value1", "?"), ExpressionType.getNat());

		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(eval, parseTree);
		
		ParseTreeProperty<ExpressionType> typeTree = eval.getExpressionTypeTree();
		Assert.assertNotNull(typeTree);
		
		ExpressionType calculatedType = typeTree.get(parseTree);
		Assert.assertEquals(ExpressionType.getNat(), calculatedType);
	}
	
	@Test
	public void typeOfIntegerNumberIsInt(){
		CharStream input = CharStreams.fromString("123");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree parseTree = parser.number();

		walker.walk(eval, parseTree);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(parseTree);
		Assert.assertEquals(ExpressionType.getNat(), calculatedType);
	}

	@Test
	public void typeOfFloatNumberIsReal(){
		CharStream input = CharStreams.fromString("123.34");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree parseTree = parser.number();

		walker.walk(eval, parseTree);

		ExpressionType calculatedType = eval.getExpressionTypeTree().get(parseTree);
		Assert.assertEquals(ExpressionType.getReal(), calculatedType);
	}
	
	@Test
	public void typeOfDeclarationIsTypeOfDeclaredVariablePrimitiveType(){
		String declarationInput = "a: !N";
		
		ParseTree programCtx = evaluateTypesOfDeclaration(declarationInput, null, null);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(programCtx);
		Assert.assertEquals(ExpressionType.getNat(), calculatedType);
	}
	
	@Test
	public void evaluatedTypeIsSavedInDeclaredVariable(){
		String declarationInput = "a, b: !N";
		
		OZParser parser = getParser(declarationInput);
		DeclarationContext parseTree = parser.declaration();
		
		Variable a = Mockito.mock(Variable.class);
		Variable b = Mockito.mock(Variable.class);
		IdContext aId = parseTree.declarationNameList().id(0);
		IdContext bId = parseTree.declarationNameList().id(1);
		Mockito.when(declarationTree.get(aId)).thenReturn(a);
		Mockito.when(declarationTree.get(bId)).thenReturn(b);
		
		this.walker.walk(eval, parseTree);
		
		Mockito.verify(a).setExpressionType(ExpressionType.getNat());
		Mockito.verify(b).setExpressionType(ExpressionType.getNat());
		Assert.assertEquals(ExpressionType.getNat(), typeTree.get(parseTree));
	}
	@Test
	public void evaluatedTypeIsSavedInDeclaredVariablesOfList(){
		String declarationInput = "a: !N; b: !Z;";
		
		OZParser parser = getParser(declarationInput);
		DeclarationListContext parseTree = parser.declarationList();
		
		Variable a = Mockito.mock(Variable.class);
		Variable b = Mockito.mock(Variable.class);
		IdContext aId = parseTree.declaration(0).declarationNameList().id(0);
		IdContext bId = parseTree.declaration(1).declarationNameList().id(0);
		Mockito.when(declarationTree.get(aId)).thenReturn(a);
		Mockito.when(declarationTree.get(bId)).thenReturn(b);
		
		this.walker.walk(eval, parseTree);
		
		Mockito.verify(a).setExpressionType(ExpressionType.getNat());
		Mockito.verify(b).setExpressionType(ExpressionType.getInt());
		Assert.assertEquals(ExpressionType.getNat(), typeTree.get(parseTree.declaration(0)));
		Assert.assertEquals(ExpressionType.getInt(), typeTree.get(parseTree.declaration(1)));
	}

	@Test
	public void typeOfDeclarationIsTypeOfDeclaredVariableCollectionType(){
		String declarationInput = "a: !P !N";
		ParseTree programCtx = evaluateTypesOfDeclaration(declarationInput, null, null);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(programCtx);
		Assert.assertEquals(ExpressionType.getSet(ExpressionType.getNat()), calculatedType);
	}
	
	@Test
	public void typeOfIdIsUserDefinedType() {
		CharStream input = CharStreams.fromString("MyClass");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTree parseTree = parser.id();
		ExpressionType myClass = ExpressionType.getUserDefinedType(parseTree, "MyClass");
		declare(myClass.getEffectiveTypeId(), myClass);

		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(eval, parseTree);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(parseTree);
		Assert.assertEquals(myClass, calculatedType);
	}

	@Test
	public void typeOfDeclarationIsTypeOfDeclaredVariableUserDefinedType(){
		ExpressionType myClassType = ExpressionType.getUserDefinedType(null, "MyClass");
		ObjectZClass myClass = Mockito.mock(ObjectZClass.class);
		Mockito.when(myClass.getId()).thenReturn(new Ident("MyClass"));
		Mockito.when(myClass.getExpressionType()).thenReturn(myClassType);
		
		String declarationInput = "a: MyClass";
		ParseTree programCtx = evaluateTypesOfDeclaration(declarationInput, Arrays.asList(myClass), null);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(programCtx);
		Assert.assertEquals(myClassType, calculatedType);
	}

	@Test
	public void typeOfDeclarationIsVariableTypeOfDeclaredCollectionTypeVariable(){
		ExpressionType sequenceOfNat = ExpressionType.getSequence(ExpressionType.getNat());
		Variable aCollection = Mockito.mock(Variable.class);
		Mockito.when(aCollection.getId()).thenReturn(new Ident("aCollection"));
		Mockito.when(aCollection.getExpressionType()).thenReturn(sequenceOfNat);
		Mockito.when(aCollection.isVariable()).thenReturn(true);
		
		String declarationInput = "a: aCollection";
		ParseTree programCtx = evaluateTypesOfDeclaration(declarationInput, null, Arrays.asList(aCollection));
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(programCtx);
		ExpressionType expectedType = ExpressionType.getVariableType(irrelevantCtx, sequenceOfNat);
		
		Assert.assertEquals(expectedType, calculatedType);
		Assert.assertEquals(expectedType.getEffectiveType(), sequenceOfNat.getEffectiveType());
		Assert.assertTrue(calculatedType.getDeclaredType().isTemplateType());
	}

	@Test
	public void typeOfAttributeCalls(){
		// myCar: Car -> Class
		// seat: Seat -> Class
		// color: Color -> FreeType

		String input = "myCar.seat.color";
		
		CharStream inputStream = CharStreams.fromString(input);
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(inputStream)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree programCtx = parser.featureOrFunctionCall();
		
		IdentifierCollector identCollector = new IdentifierCollector();
		ParseTreeProperty<Idents> usedIdentifiers = new ParseTreeProperty<Idents>();
		usedIdentifiers = identCollector.getUsedIdentifierTree();
		SymbolCollector symbolCollector = new SymbolCollector(usedIdentifiers);
		
		ProgramContext ctx = Mockito.mock(ProgramContext.class);
		symbolCollector.enterProgram(ctx);
		
		walker.walk(identCollector, programCtx);
		walker.walk(symbolCollector, programCtx);
		
		program = symbolCollector.getObjectZDefinition();
		declarationTree = symbolCollector.getDeclarationTree();
		localScopeTree = symbolCollector.getLocalScopeTree();
		eval = new TypeDeclarationEvaluator(program, declarationTree, localScopeTree);
		
		FreeType colorFT = new FreeType("Color", Arrays.asList("brown", "green", "yellow"), program, null);
		ObjectZClass seatClass = new ObjectZClass("Seat", program, null);
		ObjectZClass carClass = new ObjectZClass("Car", program, null);
		
		Variable colorVar = Mockito.mock(Variable.class);
		Mockito.when(colorVar.getId()).thenReturn(new Ident("color"));
		Mockito.when(colorVar.getExpressionType()).thenReturn(colorFT.getExpressionType());
		Mockito.when(colorVar.isVariable()).thenReturn(true);
		StateSchema seatClassState = new StateSchema(new Declarations(colorVar), Declarations.empty(), AxiomReferences.empty());
		seatClass.addStateSchema(seatClassState);
		
		Variable seatVar = Mockito.mock(Variable.class);
		Mockito.when(seatVar.getId()).thenReturn(new Ident("seat"));
		Mockito.when(seatVar.getExpressionType()).thenReturn(seatClass.getExpressionType());
		Mockito.when(seatVar.isVariable()).thenReturn(true);
		StateSchema carClassState = new StateSchema(new Declarations(seatVar), Declarations.empty(), AxiomReferences.empty());
		carClass.addStateSchema(carClassState);
		
		Variable carVar = Mockito.mock(Variable.class);
		Mockito.when(carVar.getId()).thenReturn(new Ident("myCar"));
		Mockito.when(carVar.getExpressionType()).thenReturn(carClass.getExpressionType());
		Mockito.when(carVar.isVariable()).thenReturn(true);
		
		program.addGlobalDefinition(colorFT);
		program.addClass(seatClass);
		program.addClass(carClass);
		program.addAxiomaticDeclarations(new Declarations(carVar));
		
		walker.walk(eval, programCtx);

		ExpressionType calculatedType = eval.getExpressionTypeTree().get(programCtx);
		Assert.assertEquals(colorFT.getExpressionType(), calculatedType);
	}

	@Test
	public void emptySet_ImpossibleToDetermine(){
		CharStream input = CharStreams.fromString("{}");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree parseTree = parser.expression();

		walker.walk(eval, parseTree);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(parseTree);
		Assert.assertEquals(ExpressionType.getSetConstruction(null, null), calculatedType);
	}
	@Test
	public void emptySet_typedSibling(){
		CharStream input = CharStreams.fromString("{1,2,3} ++ {}");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree parseTree = parser.expression();

		walker.walk(eval, parseTree);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(parseTree);
		Assert.assertEquals(ExpressionType.getSetConstruction(null, ExpressionType.getNat()), calculatedType);
	}

	@Test
	public void emptySet_typedSiblingAfter(){
		CharStream input = CharStreams.fromString("{} ++ {1,2,3}");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree parseTree = parser.expression();

		walker.walk(eval, parseTree);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(parseTree);
		Assert.assertEquals(ExpressionType.getSetConstruction(null, ExpressionType.getNat()), calculatedType);
	}

	@Test
	public void emptySet_typedSiblingSameType(){
		CharStream input = CharStreams.fromString("a = {}");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree parseTree = parser.expression();
		declare(new Ident("a"), ExpressionType.getSet(ExpressionType.getNat()));

		walker.walk(eval, parseTree);
		
		ExpressionType calculatedType = eval.getExpressionTypeTree().get(parseTree);
		Assert.assertEquals(ExpressionType.getBool(), calculatedType);
		
		Assert.assertEquals(3, parseTree.getChildCount());
		
		ExpressionType aCtxType = eval.getExpressionTypeTree().get(parseTree.getChild(0));
		ExpressionType emptySetCtxType = eval.getExpressionTypeTree().get(parseTree.getChild(2));
		
		Assert.assertEquals(ExpressionType.getSet(ExpressionType.getNat()).getEffectiveType(), aCtxType.getEffectiveType());
		Assert.assertEquals(ExpressionType.getSet(ExpressionType.getNat()), aCtxType);
		Assert.assertEquals(ExpressionType.getSet(ExpressionType.getNat()).getEffectiveType(), emptySetCtxType.getEffectiveType());
		Assert.assertEquals(ExpressionType.getSetConstruction(irrelevantCtx, ExpressionType.getNat()), emptySetCtxType);
	}

	@Test
	public void typeOfAttributeCallsWithVariableType(){
		// ourCars: !P Car -> Class
		// myCar: ourCars -> Class
		// seat: Seat -> Class
		// color: Color -> FreeType

		String input = "myCar.seat.color";
		
		CharStream inputStream = CharStreams.fromString(input);
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(inputStream)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree programCtx = parser.featureOrFunctionCall();
		
		IdentifierCollector identCollector = new IdentifierCollector();
		ParseTreeProperty<Idents> usedIdentifiers = new ParseTreeProperty<Idents>();
		usedIdentifiers = identCollector.getUsedIdentifierTree();
		SymbolCollector symbolCollector = new SymbolCollector(usedIdentifiers);
		
		ProgramContext ctx = Mockito.mock(ProgramContext.class);
		symbolCollector.enterProgram(ctx);
		
		walker.walk(identCollector, programCtx);
		walker.walk(symbolCollector, programCtx);
		
		program = symbolCollector.getObjectZDefinition();
		declarationTree = symbolCollector.getDeclarationTree();
		localScopeTree = symbolCollector.getLocalScopeTree();
		eval = new TypeDeclarationEvaluator(program, declarationTree, localScopeTree);
		
		FreeType colorFT = new FreeType("Color", Arrays.asList("brown", "green", "yellow"), program, null);
		ObjectZClass seatClass = new ObjectZClass("Seat", program, null);
		ObjectZClass carClass = new ObjectZClass("Car", program, null);
		
		Variable colorVar = Mockito.mock(Variable.class);
		Mockito.when(colorVar.getId()).thenReturn(new Ident("color"));
		Mockito.when(colorVar.getExpressionType()).thenReturn(colorFT.getExpressionType());
		Mockito.when(colorVar.isVariable()).thenReturn(true);
		StateSchema seatClassState = new StateSchema(new Declarations(colorVar), Declarations.empty(), AxiomReferences.empty());
		seatClass.addStateSchema(seatClassState);
		
		Variable seatVar = Mockito.mock(Variable.class);
		Mockito.when(seatVar.getId()).thenReturn(new Ident("seat"));
		Mockito.when(seatVar.getExpressionType()).thenReturn(seatClass.getExpressionType());
		Mockito.when(seatVar.isVariable()).thenReturn(true);
		StateSchema carClassState = new StateSchema(new Declarations(seatVar), Declarations.empty(), AxiomReferences.empty());
		carClass.addStateSchema(carClassState);
		
		Variable ourCarVar = Mockito.mock(Variable.class);
		Mockito.when(ourCarVar.getId()).thenReturn(new Ident("ourCars"));
		Mockito.when(ourCarVar.getExpressionType()).thenReturn(ExpressionType.getSet(carClass.getExpressionType()));
		Mockito.when(ourCarVar.isVariable()).thenReturn(true);

		Variable myCarVar = Mockito.mock(Variable.class);
		Mockito.when(myCarVar.getId()).thenReturn(new Ident("myCar"));
		Mockito.when(myCarVar.getExpressionType()).thenReturn(ExpressionType.getVariableType(irrelevantCtx, ExpressionType.getSet(carClass.getExpressionType())));
		Mockito.when(myCarVar.isVariable()).thenReturn(true);
		
		program.addGlobalDefinition(colorFT);
		program.addClass(seatClass);
		program.addClass(carClass);
		program.addAxiomaticDeclarations(new Declarations(ourCarVar, myCarVar));
		
		walker.walk(eval, programCtx);

		ExpressionType calculatedType = eval.getExpressionTypeTree().get(programCtx);
		Assert.assertEquals(colorFT.getExpressionType(), calculatedType);
	}

	
	private ParseTree evaluateTypesOfDeclaration(String declarationInput, List<ObjectZClass> definitions, List<Variable> variables) {
		CharStream input = CharStreams.fromString(declarationInput);
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree programCtx = parser.declaration();
		
		IdentifierCollector identCollector = new IdentifierCollector();
		ParseTreeProperty<Idents> usedIdentifiers = new ParseTreeProperty<Idents>();
		usedIdentifiers = identCollector.getUsedIdentifierTree();
		SymbolCollector symbolCollector = new SymbolCollector(usedIdentifiers);
		
		ProgramContext ctx = Mockito.mock(ProgramContext.class);
		symbolCollector.enterProgram(ctx);
		
		walker.walk(identCollector, programCtx);
		walker.walk(symbolCollector, programCtx);
		
		program = symbolCollector.getObjectZDefinition();
		declarationTree = symbolCollector.getDeclarationTree();
		localScopeTree = symbolCollector.getLocalScopeTree();
		eval = new TypeDeclarationEvaluator(program, declarationTree, localScopeTree);
		
		if (definitions != null){
			for (ObjectZClass definition: definitions){
				declare(definition);
			}
		}
		if (variables != null){
			declare(variables);
		}
		
		walker.walk(eval, programCtx);
		return programCtx;
	}
	
	private void declare(Ident ident, ExpressionType expectedType) {
		Variable value1Var = Mockito.mock(Variable.class);
		Mockito.when(value1Var.getId()).thenReturn(ident);
		Mockito.when(value1Var.getExpressionType()).thenReturn(expectedType);
		Mockito.when(program.resolve(ident)).thenReturn(value1Var);
	}
	private void declare(List<Variable> v) {
		program.addAxiomaticDeclarations(new Declarations(v));
	}
	private void declare(ObjectZClass clazz) {
		program.addClass(clazz);
	}
}
