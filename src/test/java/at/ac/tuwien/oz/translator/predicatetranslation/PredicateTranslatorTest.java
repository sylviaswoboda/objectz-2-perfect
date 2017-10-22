package at.ac.tuwien.oz.translator.predicatetranslation;


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
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.ordering.ISchemaPredicate;
import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PredicateContext;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.TypeEvaluator;


public class PredicateTranslatorTest {
	
	@Mock private ParseTreeProperty<ExpressionType> typeTree;
	@Mock private ParseTreeProperty<List<ISchemaPredicate>> schemaPredicateTree;
	@Mock private ParseTreeProperty<Variable> declarationTree;	
	@Mock private ExpressionType mapMock;
	@Mock private ExpressionType relationMock;
	@Mock private ExpressionType typeMock;
	@Mock private TypeEvaluator typeEval;
	
	private ParseTreeWalker walker;
	private PredicateTranslator predicateTranslator;
	private ParseTreeProperty<ST> templateTree;
	private ParseTree irrelevantCtx;
	
	private ST getNumber(String input) {
		OZParser parser = getParser(input);
		ParseTree ctx = parser.number();
		walker.walk(predicateTranslator, ctx);
		ST template = templateTree.get(ctx);
		return template;
	}
	private ST getPredefinedType(String input) {
		OZParser parser = getParser(input);
		ParseTree ctx = parser.predefinedType();
		walker.walk(predicateTranslator, ctx);
		ST template = templateTree.get(ctx);
		return template;
	}
	private ST getExpression(String input) {
		OZParser parser = getParser(input);
		ParseTree ctx = parser.expression();
		walker.walk(predicateTranslator, ctx);
		ST template = templateTree.get(ctx);
		return template;
	}
	private ST getPredicate(String input) {
		OZParser parser = getParser(input);
		ParseTree ctx = parser.predicate();
		walker.walk(predicateTranslator, ctx);
		ST template = templateTree.get(ctx);
		return template;
	}

	private OZParser getParser(String input) {
		CharStream stream = CharStreams.fromString(input);
		OZLexer lexer = new OZLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		OZParser parser = new OZParser(tokens);
		return parser;
	}
	
	@Before
	public void setup(){
		TempVarProvider.resetNameCounter();
		MockitoAnnotations.initMocks(this);
		walker = new ParseTreeWalker();
		predicateTranslator = new PredicateTranslator(typeTree, declarationTree, schemaPredicateTree);
		templateTree = predicateTranslator.getTemplateTree();
		
		Mockito.when(mapMock.isFunction()).thenReturn(true);
		Mockito.when(relationMock.isRelation()).thenReturn(false);

	}
	
	@Test
	public void predefinedTypeNat(){
		ST rootTemplate = getPredefinedType("!N");
		Assert.assertEquals("nat", rootTemplate.render());
	
		rootTemplate = getExpression("!N");
		Assert.assertEquals("nat", rootTemplate.render());
	}
	@Test
	public void predefinedTypeBool(){
		ST rootTemplate = getPredefinedType("!B");
		Assert.assertEquals("bool", rootTemplate.render());
	
		rootTemplate = getExpression("!B");
		Assert.assertEquals("bool", rootTemplate.render());
	}

	@Test
	public void predefinedTypePositiveNat(){
		ST rootTemplate = getPredefinedType("!N1");
		Assert.assertEquals("pNat", rootTemplate.render());
	}
	@Test
	public void predefinedTypeInteger(){
		ST rootTemplate = getPredefinedType("!Z");
		Assert.assertEquals("int", rootTemplate.render());
	}
	@Test
	public void predefinedTypeReal(){
		ST rootTemplate = getPredefinedType("!R");
		Assert.assertEquals("real", rootTemplate.render());
	}
	@Test
	public void predefinedTypeChar(){
		ST rootTemplate = getPredefinedType("!C");
		Assert.assertEquals("char", rootTemplate.render());
	}
	@Test
	public void numberInteger(){
		ST rootTemplate = getNumber("123");
		Assert.assertEquals("123", rootTemplate.render());
		
		rootTemplate = getNumber("0");
		Assert.assertEquals("0", rootTemplate.render());
	}
	@Test
	public void numberFloat(){
		ST template = getNumber("123.45");
		Assert.assertEquals("123.45", template.render());
		
		ST rootTemplate = getNumber("12.3");
		Assert.assertEquals("12.3", rootTemplate.render());
		
		rootTemplate = getNumber("0.0");
		Assert.assertEquals("0.0", rootTemplate.render());
	}

	@Test
	public void ids(){
		ST idTemplate = getExpression("inputVar?");
		Assert.assertEquals("inputVar_in", idTemplate.render());

		idTemplate = getExpression("outputVar!");
		Assert.assertEquals("outputVar_out", idTemplate.render());

		idTemplate = getExpression("primedVar'");
		Assert.assertEquals("primedVar'", idTemplate.render());
	}
	
	@Test
	public void idOrNumber(){
		ST idOrNumber = getExpression("anIdentifier?");
		Assert.assertEquals("anIdentifier_in", idOrNumber.render());
		
		idOrNumber = getExpression("123.345");
		Assert.assertEquals("123.345", idOrNumber.render());
		
		idOrNumber = getExpression("anIdentifier?");
		Assert.assertEquals("anIdentifier_in", idOrNumber.render());
	}

	@Test
	public void attributeCall(){
		ST attributeCall = getExpression("thisClass.aField");
		Assert.assertEquals("thisClass.aField", attributeCall.render());
		
		attributeCall = getExpression("thisClass.aField.aSubField");
		Assert.assertEquals("thisClass.aField.aSubField", attributeCall.render());
	}
	
	@Test
	public void successor(){
		ST successor = getExpression("succ (someIdentifier)");
		Assert.assertEquals(">someIdentifier", successor.render());
		
		successor = getExpression("succ (20)");
		Assert.assertEquals(">20", successor.render());
	}
	
	@Test
	public void functionReferenceWithFunction(){
		OZParser parser;
		ParseTree ctx;
		ST functionReference;

		parser = getParser("even(10)");
		ctx = parser.expression();
		ExpressionType functionDomainType = ExpressionType.getNat();
		ExpressionType functionRangeType = ExpressionType.getBool();
		Mockito.when(typeTree.get(ctx)).thenReturn(functionRangeType);
		Mockito.when(typeTree.get(ctx.getChild(0))).thenReturn(functionRangeType);
		Mockito.when(typeTree.get(ctx.getChild(0).getChild(0))).thenReturn(ExpressionType.getFunction(functionDomainType, functionRangeType));
		walker.walk(predicateTranslator, ctx);
		functionReference = templateTree.get(ctx);
		Assert.assertEquals("even[10]", functionReference.render());
	}

	@Test
	public void functionReferenceWithRelation(){
		OZParser parser;
		ParseTree ctx;
		ST relationReference;

		//	OVERRIDE
		parser = getParser("greaterThan(10, Constants.fifteen)");
		ctx = parser.expression();
		ExpressionType relationDomainType = ExpressionType.getNat();
		ExpressionType relationRangeType = ExpressionType.getNat();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getBool());
		Mockito.when(typeTree.get(ctx.getChild(0))).thenReturn(ExpressionType.getBool());
		Mockito.when(typeTree.get(ctx.getChild(0).getChild(0))).thenReturn(ExpressionType.getRelation(relationDomainType, relationRangeType));
		walker.walk(predicateTranslator, ctx);
		
		relationReference = templateTree.get(ctx);
		Assert.assertEquals("pair of (nat, nat) {10, Constants.fifteen} in greaterThan", relationReference.render());
	}

	@Test
	public void self(){
		ST self = getExpression("self");
		Assert.assertEquals("self", self.render());
	}
	
	@Test
	public void emptySequence(){
		ST expression = getExpression("[]");
		Assert.assertEquals(" {}", expression.render());
	}

	@Test
	public void emptyBag(){
		ST expression = getExpression("|[]|");
		Assert.assertEquals(" {}", expression.render());
	}

	@Test
	public void emptySetWithType(){
		OZParser parser = getParser("{}");
		ParseTree ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getSet(ExpressionType.getNat()));
		
		walker.walk(predicateTranslator, ctx);
		ST expression = templateTree.get(ctx);

		Assert.assertEquals("set of nat {}", expression.render());
	}

	@Test
	public void setWithoutType(){
		ST expression = getExpression("{a, b, 200}");
		Assert.assertEquals(" {a, b, 200}", expression.render());
	}

	@Test
	public void setWithType(){
		OZParser parser = getParser("{a, b, 200}");
		ParseTree ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getSet(ExpressionType.getNat()));
		
		walker.walk(predicateTranslator, ctx);
		ST expression = templateTree.get(ctx);

		Assert.assertEquals("set of nat {a, b, 200}", expression.render());
	}

	
	@Test
	public void sequenceWithType(){
		OZParser parser = getParser("[a, b, 200]");
		ParseTree ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getSequence(ExpressionType.getInt()));
		
		walker.walk(predicateTranslator, ctx);
		ST expression = templateTree.get(ctx);

		Assert.assertEquals("seq of int {a, b, 200}", expression.render());
	}

	@Test
	public void bag(){
		OZParser parser = getParser("|[a.x, 200.1, 37]|");
		ParseTree ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getBag(ExpressionType.getReal()));
		
		walker.walk(predicateTranslator, ctx);
		ST expression = templateTree.get(ctx);

		Assert.assertEquals("bag of real {a.x, 200.1, 37}", expression.render());
	}

	@Test
	public void triple(){
		OZParser parser = getParser("(x, 300, z.t)");
		
		ParseTree ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getTuple(Arrays.asList(ExpressionType.getNat(), ExpressionType.getNat(), ExpressionType.getNat())));
		
		walker.walk(predicateTranslator, ctx);
		ST expression = templateTree.get(ctx);

		Assert.assertEquals("triple of (nat, nat, nat) {x, 300, z.t}", expression.render());
		
	}
	@Test
	public void pair(){
		OZParser parser = getParser("(2.3, 30.7)");
		
		ParseTree ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getTuple(Arrays.asList(ExpressionType.getReal(), ExpressionType.getReal())));
		
		walker.walk(predicateTranslator, ctx);
		ST expression = templateTree.get(ctx);

		Assert.assertEquals("pair of (real, real) {2.3, 30.7}", expression.render());
	}
	
	@Test
	public void parenthesizedExpression(){
		ST expression = getExpression("(succ (100))");
		Assert.assertEquals("(>100)", expression.render());
		
		expression = getExpression("({3,5,8})");
		Assert.assertEquals("( {3, 5, 8})", expression.render());
	}
	
	@Test
	public void minMax(){
		ST expression = getMinMaxExpressionWithType("min {100, 200, 300}", ExpressionType.getSet(ExpressionType.getNat()));
		Assert.assertEquals("set of nat {100, 200, 300}.min", expression.render());

		
		expression = getMinMaxExpressionWithType("max {a, 200.34, t.x}", ExpressionType.getSet(ExpressionType.getReal()));
		Assert.assertEquals("set of real {a, 200.34, t.x}.max", expression.render());

		expression = getMinMaxExpressionWithType("min aBigBigCollection", ExpressionType.getBag(ExpressionType.getNat()));
		Assert.assertEquals("aBigBigCollection.min", expression.render());

		expression = getMinMaxExpressionWithType("max t.anotherSet", ExpressionType.getSet(ExpressionType.getNat()));
		Assert.assertEquals("t.anotherSet.max", expression.render());
	}
	
	private ST getMinMaxExpressionWithType(String input, ExpressionType childType){
		OZParser parser = getParser(input);
		ParseTree ctx = parser.expression();
		Mockito.when(typeTree.get(ctx.getChild(1))).thenReturn(childType);
		
		walker.walk(predicateTranslator, ctx);
		return templateTree.get(ctx);
		
	}
	
	@Test
	public void prefixExpression(){
		ST expression = getExpression("-aNumber");
		Assert.assertEquals("-aNumber", expression.render());
		
		expression = getExpression("#aCollection");
		Assert.assertEquals("#aCollection", expression.render());
		
		expression = getExpression("ran aSequence");
		Assert.assertEquals("aSequence.ran", expression.render());
		
		expression = getExpression("dom aSequence");
		Assert.assertEquals("aSequence.dom", expression.render());
		
		expression = getExpression("tail aSequence");
		Assert.assertEquals("aSequence.tail", expression.render());

		expression = getExpression("head aSequence");
		Assert.assertEquals("aSequence.head", expression.render());

		expression = getExpression("rev aSequence");
		Assert.assertEquals("aSequence.rev", expression.render());
		
		expression = getExpression("last aSequence");
		Assert.assertEquals("aSequence.last", expression.render());

		expression = getExpression("front aSequence");
		Assert.assertEquals("aSequence.front", expression.render());

		expression = getExpression("items aSequence");
		Assert.assertEquals("aSequence.ranb", expression.render());
	}
	
	@Test
	public void generalizedUnion(){
		ST expression = getExpression("++(collectionsForUnion)");
		Assert.assertEquals("([(collectionsForUnion).empty]: {}, []: ++over(collectionsForUnion))", expression.render());
	}
	
	@Test
	public void generalizedIntersection(){
		ST expression = getExpression("**(collectionsForIntersection)");
		Assert.assertEquals("([(collectionsForIntersection).empty]: {}, []: **over(collectionsForIntersection))", expression.render());
	}

	@Test
	public void distributedConcatenation(){
		ST expression = getExpression("+^+(sequencesForConcatenation)");
		Assert.assertEquals("flatten(sequencesForConcatenation)", expression.render());
	}
	
	@Test
	public void binaryRelation(){
		ST expression = getExpression("ClassA <--> !N");
		Assert.assertEquals("set of pair of (ClassA, nat)", expression.render());
	}
	
	@Test
	public void ternaryRelation(){
		ST expression = getExpression("ClassB <--> !Z <--> !B");
		Assert.assertEquals("set of triple of (ClassB, int, bool)", expression.render());
	}
	
	@Test
	public void infixRelationOperations(){
		//	PART_FUNC	:	'-|->';		// partial function
		ST expression = getExpression("ClassC -|-> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());
		
		//	TOT_FUNC	:	'--->';		// total function
		expression = getExpression("ClassC ---> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());

		//	PART_INJ	:	'>-|->';	// partial injection
		expression = getExpression("ClassC >-|-> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());

		//	TOT_INJ		:	'>--->';	// total injection
		expression = getExpression("ClassC >---> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());

		//	PART_SUR	:	'-|->>';	// partial surjection
		expression = getExpression("ClassC -|->> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());

		//	TOT_SUR		:	'--->>';	// total surjection
		expression = getExpression("ClassC --->> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());

		//	BIJEC		:	'>-->>';	// bijection
		expression = getExpression("ClassC >-->> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());

		//	F_PART_FUNC	:	'-||->';	// finite partial function
		expression = getExpression("ClassC -||-> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());

		//	F_PART_INJ	:	'>-||->';	// finite partial injection
		expression = getExpression("ClassC >-||-> AnotherType");
		Assert.assertEquals("map of (ClassC -> AnotherType)", expression.render());
	}
	
	@Test
	public void infixRelationOperationMaplet(){
		OZParser parser;
		ParseTree ctx;
		ST expression;

		parser = getParser("cObject |-> aObject");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getMaplet(irrelevantCtx,
				ExpressionType.getUserDefinedType(irrelevantCtx, "ClassC"), ExpressionType.getUserDefinedType(irrelevantCtx, "AnotherType")));
		walker.walk(predicateTranslator, ctx);
		expression = templateTree.get(ctx);

		//	MAPLET		:	'|->';		// maplet
		Assert.assertEquals("pair of (ClassC, AnotherType) {cObject, aObject}", expression.render());
		
	}
	
	@Test
	public void infixComparisonOperations(){
		//	EQUALS		// equals
		ST expression = getExpression("identifier1 = identifier2");
		Assert.assertEquals("identifier1 = identifier2", expression.render());
		
		//	NEQUALS		// equals not
		expression = getExpression("identifier1 ~= identifier2");
		Assert.assertEquals("identifier1 ~= identifier2", expression.render());
		
		//	ELEM		// is element in
		expression = getExpression("item in aCollection");
		Assert.assertEquals("item in aCollection", expression.render());

		//	NELEM		// is not element in
		expression = getExpression("item ~in aCollection");
		Assert.assertEquals("item ~in aCollection", expression.render());
		
		//	SUBSET		// is subset of
		expression = getExpression("miniCollection <<= aCollection");
		Assert.assertEquals("miniCollection <<= aCollection", expression.render());

		//	STR_SUBSET	// is a strict subset
		expression = getExpression("miniCollection << aCollection");
		Assert.assertEquals("miniCollection << aCollection", expression.render());
		
		//	LT			// less than
		expression = getExpression("aNumber < anotherNumber");
		Assert.assertEquals("aNumber < anotherNumber", expression.render());
		
		//	LTE			// less than or equal
		expression = getExpression("aNumber <= anotherNumber");
		Assert.assertEquals("aNumber <= anotherNumber", expression.render());
		
		//	GT			// greater than
		expression = getExpression("aNumber > anotherNumber");
		Assert.assertEquals("aNumber > anotherNumber", expression.render());
		
		//	GTE			// greater than or equal
		expression = getExpression("aNumber >= anotherNumber");
		Assert.assertEquals("aNumber >= anotherNumber", expression.render());
		
		//	PREFIX		// prefix relation a is prefix of sequence b
		expression = getExpression("sequence1 prefix sequence2");
		Assert.assertEquals("sequence2.begins(sequence1)", expression.render());

		//	SUFFIX		// suffix relation a is suffix of sequence b
		expression = getExpression("sequence1 suffix sequence2");
		Assert.assertEquals("sequence2.ends(sequence1)", expression.render());
		
		//	IN_SEQ		// segment relation a is part of sequence b
		expression = getExpression("sequence1 inseq sequence2");
		Assert.assertEquals("sequence1 <<= sequence2", expression.render());
		
		//	IN_BAG		// bag membership
		expression = getExpression("bag1 inbag bag2");
		Assert.assertEquals("bag1 in bag2", expression.render());

		//	SUBBAG		// sub-bag relation
		expression = getExpression("bag1 subbag bag2");
		Assert.assertEquals("bag1 <<= bag2", expression.render());
	}
	
	@Test
	public void setOperations(){
		//	UNION
		ST expression = getExpression("oneCollection ++ anotherCollection");
		Assert.assertEquals("oneCollection ++ anotherCollection", expression.render());

		//	DIFFERENCE
		expression = getExpression("oneCollection \\ anotherCollection");
		Assert.assertEquals("oneCollection -- anotherCollection", expression.render());
		
		//	INTERSECT
		expression = getExpression("oneCollection ** anotherCollection");
		Assert.assertEquals("oneCollection ** anotherCollection", expression.render());
		
		// 	CONCATENATE
		expression = getExpression("oneSequence +^+ anotherSequence");
		Assert.assertEquals("oneSequence ++ anotherSequence", expression.render());
		
		//	EXTRACT
		expression = getExpression("aSet extract theSequence");
		Assert.assertEquals("(for those tempVar1::aSet.permndec :- tempVar1 < #theSequence yield theSequence[tempVar1-1])", expression.render());
		
		//	FILTER
		expression = getExpression("theSequence filter aSet");
		Assert.assertEquals("(those tempVar2::theSequence :- tempVar2 in aSet)", expression.render());
		
		//	MULTIPLICITY 		// multiplicity in a bag
		expression = getExpression("anElement (#) aBag");
		Assert.assertEquals("anElement # aBag", expression.render());

		//	SCALING				// bag scaling
		expression = getExpression("nCount (><) anElement");
		Assert.assertEquals("anElement.rep(nCount)", expression.render());

		//	BAG_UNION
		expression = getExpression("aBag |+| anotherBag");
		Assert.assertEquals("aBag ++ anotherBag", expression.render());
		
		//	BAG_DIFFERENCE
		expression = getExpression("aBag |-| anotherBag");
		Assert.assertEquals("aBag -- anotherBag", expression.render());

	}
	
	@Test
	public void restrictionMap(){
		// collection: !P !N;
		// aMap: !N -|->  !Z; 
		// collection <| aMap
		// ===>
		// map of (nat -> int) {those tempVar1::aMap.pairs :- tempVar1.x in collection}
		
		//	DOM_RESTR	// domain restriction
		OZParser parser = getParser("collection <| aMap");
		ExpressionContext ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getFunction(ExpressionType.getNat(), ExpressionType.getInt())); 	// collection <| aMap
		walker.walk(predicateTranslator, ctx);
		ST template = templateTree.get(ctx);
		Assert.assertEquals("map of (nat -> int) {those tempVar1::aMap.pairs :- tempVar1.x in collection}", template.render());
		
//		//	RAN_RESTR	// range restriction
		parser = getParser("aMap |> collection");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getFunction(ExpressionType.getReal(), ExpressionType.getInt())); 	// collection <| aMap
		walker.walk(predicateTranslator, ctx);
		template = templateTree.get(ctx);
		Assert.assertEquals("map of (real -> int) {those tempVar2::aMap.pairs :- tempVar2.y in collection}", template.render());
//		
//		//	DOM_AR		// domain anti-restriction
		parser = getParser("collection <|| aMap");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getFunction(ExpressionType.getReal(), ExpressionType.getInt())); 	// collection <| aMap
		walker.walk(predicateTranslator, ctx);
		template = templateTree.get(ctx);
		Assert.assertEquals("map of (real -> int) {those tempVar3::aMap.pairs :- tempVar3.x ~in collection}", template.render());
//		
//		//	RAN_AR		// range anti-restriction
		parser = getParser("aMap ||> collection");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getFunction(ExpressionType.getBool(), ExpressionType.getReal())); 	// collection <| aMap
		walker.walk(predicateTranslator, ctx);
		template = templateTree.get(ctx);
		Assert.assertEquals("map of (bool -> real) {those tempVar4::aMap.pairs :- tempVar4.y ~in collection}", template.render());
	}
	
	@Test
	public void restrictionRelation(){
		// collection1: !P !N
		// aRelation: !N <-> !R
		OZParser parser;
		ParseTree ctx;
		ST expression;
		
		//	DOM_RESTR	// domain restriction
		parser = getParser("collection <| aRelation");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getRelation(ExpressionType.getInt(), ExpressionType.getReal()));
		walker.walk(predicateTranslator, ctx);
		expression = templateTree.get(ctx);
		
		Assert.assertEquals("(those tempVar1::aRelation :- tempVar1.x in collection)", expression.render());
		
		//	RAN_RESTR	// range restriction
		parser = getParser("aRelation |> collection");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getRelation(ExpressionType.getInt(), ExpressionType.getReal()));
		walker.walk(predicateTranslator, ctx);
		expression = templateTree.get(ctx);
		Assert.assertEquals("(those tempVar2::aRelation :- tempVar2.y in collection)", expression.render());
		
		//	DOM_AR		// domain anti-restriction
		parser = getParser("collection <|| aRelation");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getRelation(ExpressionType.getInt(), ExpressionType.getReal()));
		walker.walk(predicateTranslator, ctx);
		expression = templateTree.get(ctx);
		Assert.assertEquals("(those tempVar3::aRelation :- tempVar3.x ~in collection)", expression.render());
		
		//	RAN_AR		// range anti-restriction
		parser = getParser("aRelation ||> collection");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getRelation(ExpressionType.getInt(), ExpressionType.getReal()));
		walker.walk(predicateTranslator, ctx);
		expression = templateTree.get(ctx);
		Assert.assertEquals("(those tempVar4::aRelation :- tempVar4.y ~in collection)", expression.render());
	}
	
	@Test
	public void overrideMap(){
		OZParser parser;
		ParseTree ctx;
		ST expression;

		//	OVERRIDE
		parser = getParser("oneFunction >O< anotherFunction");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getFunction(ExpressionType.getInt(), ExpressionType.getReal()));
		walker.walk(predicateTranslator, ctx);
		expression = templateTree.get(ctx);

		Assert.assertEquals("map of (int -> real) {for those tempVar1::oneFunction.dom :- tempVar1 ~in anotherFunction.dom yield pair of (int, real){tempVar1, oneFunction[tempVar1]}} ++ anotherFunction", expression.render());
		
	}
	
	@Test
	public void overrideRelation(){
		OZParser parser;
		ParseTree ctx;
		ST expression;

		//	OVERRIDE
		parser = getParser("oneRelation >O< anotherRelation");
		ctx = parser.expression();
		Mockito.when(typeTree.get(ctx)).thenReturn(ExpressionType.getRelation(ExpressionType.getInt(), ExpressionType.getReal()));
		walker.walk(predicateTranslator, ctx);
		expression = templateTree.get(ctx);
		Assert.assertEquals("(those tempVar1::oneRelation :- (exists tempVar2::anotherRelation :- tempVar1.x = tempVar2.x)) ++ anotherRelation", expression.render());
		
	}
	
	@Test
	public void range(){
		ST expression = getExpression("a..b");
		Assert.assertEquals("([a <= b]: (a..b).ran, []: set of int{})", expression.render());

		expression = getExpression("c1 .. d2");
		Assert.assertEquals("([c1 <= d2]: (c1..d2).ran, []: set of int{})", expression.render());
		
		expression = getExpression("1..3");
		Assert.assertEquals("([1 <= 3]: (1..3).ran, []: set of int{})", expression.render());
		
		expression = getExpression("12 .. 34");
		Assert.assertEquals("([12 <= 34]: (12..34).ran, []: set of int{})", expression.render());
	}
	
	@Test
	public void additiveExpression(){
		ST expression = getExpression("a + b.x");
		Assert.assertEquals("a + b.x", expression.render());
		
		expression = getExpression("1.2 + 3");
		Assert.assertEquals("1.2 + 3", expression.render());
		
		expression = getExpression("15 - (3 + 2)");
		Assert.assertEquals("15 - (3 + 2)", expression.render());
		
		expression = getExpression("20.2342 - 22 - 4.09");
		Assert.assertEquals("20.2342 - 22 - 4.09", expression.render());
	}
	
	@Test
	public void multiplicativeExpression(){
		ST expression = getExpression("a * b.x");
		Assert.assertEquals("a * b.x", expression.render());
		
		expression = getExpression("1.2 / 3");
		Assert.assertEquals("1.2 / 3", expression.render());
		
		expression = getExpression("15 div 3 div 5");
		Assert.assertEquals("15 / 3 / 5", expression.render());
		
		expression = getExpression("62 mod 10");
		Assert.assertEquals("62 % 10", expression.render());
	}

	@Test
	public void powerSetExpression(){
		ST expression = getExpression("!P !N");
		Assert.assertEquals("set of nat", expression.render());
		
		expression = getExpression("!P1 !Z");
		Assert.assertEquals("set of int", expression.render());
		
		expression = getExpression("!F someType");
		Assert.assertEquals("set of someType", expression.render());
		
		expression = getExpression("!F1 anotherType");
		Assert.assertEquals("set of anotherType", expression.render());
	
		expression = getExpression("seq anotherType");
		Assert.assertEquals("seq of anotherType", expression.render());
		
		expression = getExpression("seq1 aClass");
		Assert.assertEquals("seq of aClass", expression.render());

		expression = getExpression("iseq anotherClass");
		Assert.assertEquals("seq of anotherClass", expression.render());

		expression = getExpression("bag anotherClass");
		Assert.assertEquals("bag of anotherClass", expression.render());
	}
	
	@Test
	public void cartesian(){
		ST expression = getExpression("TypeA >< TypeB");
		Assert.assertEquals("pair of (TypeA, TypeB)", expression.render());
		
	}
	
	@Test
	public void formalClassReference(){
		ST genericClassReference = getExpression("List[T]");
		Assert.assertEquals("List of T", genericClassReference.render());
		
		genericClassReference = getExpression("Hashmap[T, U]");
		Assert.assertEquals("Hashmap of (T, U)", genericClassReference.render());
	}
	
	@Test
	public void genericClassReference(){
		ST genericClassReference = getExpression("List[!N]");
		Assert.assertEquals("List of nat", genericClassReference.render());
		
		genericClassReference = getExpression("Hashmap[!N, List[!Z]]");
		Assert.assertEquals("Hashmap of (nat, List of int)", genericClassReference.render());
	}
	
	@Test
	public void polymorphicExpression(){
		ST expression = getExpression("|v List");
		Assert.assertEquals("from List", expression.render());
		
		expression = getExpression("|v List[!N, !Z]");
		Assert.assertEquals("from List of (nat, int)", expression.render());
	}
	
	@Test
	public void genericActualParameters(){
		OZParser parser = getParser("[!N, aType, something.different]");
		ParseTree parseTree = parser.genActuals();
		walker.walk(predicateTranslator, parseTree);
		
		ST genActuals = templateTree.get(parseTree);
		Assert.assertEquals("of (nat, aType, something.different)", genActuals.render());
	}
	
	@Test
	public void formalParameters(){
		ST formalParameters = getExpression("MyClass [T, S, V]");
		Assert.assertEquals("MyClass of (T, S, V)", formalParameters.render());
		
		formalParameters = getExpression("YourClass [T]");
		Assert.assertEquals("YourClass of T", formalParameters.render());
	}
	
	/*
	 * Tests for predicates
	 */
	
	@Test
	public void trueTemplate(){
		ST trueTemplate = getPredicate("true");
		Assert.assertEquals("true", trueTemplate.render());

		trueTemplate = getPredicate("TRUE");
		Assert.assertEquals("true", trueTemplate.render());
	}
	
	@Test
	public void falseTemplate(){
		ST trueTemplate = getPredicate("false");
		Assert.assertEquals("false", trueTemplate.render());

		trueTemplate = getPredicate("FALSE");
		Assert.assertEquals("false", trueTemplate.render());
	}
	
	@Test
	public void booleanExpression(){
		ST booleanExpression = getPredicate("a = 200");
		Assert.assertEquals("a = 200", booleanExpression.render());
		
		booleanExpression = getPredicate("#aSet < 4");
		Assert.assertEquals("#aSet < 4", booleanExpression.render());
	}
	
	@Test
	public void initcall(){
		ST initCall = getPredicate("anObject.INIT");
		Assert.assertEquals("anObject.INIT", initCall.render());
	}
	
	@Test
	public void negation(){
		OZParser parser = getParser("~ (key?, room?) in access");
		
		PredicateContext predicateCtx = parser.predicate();
		ParseTree tupleCtx = predicateCtx.getChild(0).getChild(0).getChild(1).getChild(0).getChild(0);
		Mockito.when(typeTree.get(tupleCtx)).thenReturn(ExpressionType.getTuple(
				Arrays.asList(ExpressionType.getUserDefinedType(irrelevantCtx, "Key"), ExpressionType.getUserDefinedType(irrelevantCtx, "Room"))));

		walker.walk(predicateTranslator, predicateCtx);
		
		ST negation = templateTree.get(predicateCtx);
		
		Assert.assertEquals("~ pair of (Key, Room) {key_in, room_in} in access", negation.render());
	}
	
	@Test
	public void atom(){
		OZParser parser = getParser("~ (key? _access_ room?)");
		
		PredicateContext predicateCtx = parser.predicate();
		ParseTree binaryRelationCtx = predicateCtx.getChild(0).getChild(0).getChild(1).getChild(1).getChild(0).getChild(0);
		Mockito.when(typeTree.get(binaryRelationCtx)).thenReturn(ExpressionType.getTuple(
				Arrays.asList(ExpressionType.getUserDefinedType(irrelevantCtx, "Key"), ExpressionType.getUserDefinedType(irrelevantCtx, "Room"))));
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST atom = templateTree.get(predicateCtx);

		Assert.assertEquals("~ (pair of (Key, Room) {key_in, room_in} in access)", atom.render());
		
		atom = getPredicate("~isEmpty");
		Assert.assertEquals("~ isEmpty", atom.render());
		
		atom = getPredicate("a = b");
		Assert.assertEquals("a = b", atom.render());
	}

	
	@Test
	public void binaryRelationReferenceUnderlined(){
		OZParser parser = getParser("30 _isBigger_ 20");
		
		PredicateContext predicateCtx = parser.predicate();
		ParseTree binaryRelationCtx = predicateCtx.getChild(0).getChild(0);
		Mockito.when(typeTree.get(binaryRelationCtx)).thenReturn(ExpressionType.getTuple(
				Arrays.asList(ExpressionType.getNat(), ExpressionType.getNat())));
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST binaryRelation = templateTree.get(predicateCtx);
		Assert.assertEquals("pair of (nat, nat) {30, 20} in isBigger", binaryRelation.render());;
	}
	
	@Test
	public void binaryRelationReferenceAsPair(){
		OZParser parser = getParser("(30, 20) in isBigger");
		
		PredicateContext predicateCtx = parser.predicate();
		ParseTree tupleCtx = predicateCtx.getChild(0).getChild(0).getChild(0).getChild(0);
		Mockito.when(typeTree.get(tupleCtx)).thenReturn(ExpressionType.getTuple(Arrays.asList(ExpressionType.getNat(), ExpressionType.getNat())));
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST binaryRelation = templateTree.get(predicateCtx);
		Assert.assertEquals("pair of (nat, nat) {30, 20} in isBigger", binaryRelation.render());;
	}

	
	@Test
	public void conjunction(){
		ST conjunction = getPredicate("a and b");
		Assert.assertEquals("a & b", conjunction.render());
		
		conjunction = getPredicate("c and TRUE");
		Assert.assertEquals("c & true", conjunction.render());
		
		conjunction = getPredicate("~e and f");
		Assert.assertEquals("~ e & f", conjunction.render());
	}

	@Test
	public void disjunction(){
		ST disjunction = getPredicate("a or b");
		Assert.assertEquals("a | b", disjunction.render());
		
		disjunction = getPredicate("c or TRUE");
		Assert.assertEquals("c | true", disjunction.render());
		
		disjunction = getPredicate("~e or f");
		Assert.assertEquals("~ e | f", disjunction.render());
	}

	@Test
	public void implication(){
		ST implication = getPredicate("a => b");
		Assert.assertEquals("a ==> b", implication.render());
		
		implication = getPredicate("c => TRUE");
		Assert.assertEquals("c ==> true", implication.render());
		
		implication = getPredicate("~e => f");
		Assert.assertEquals("~ e ==> f", implication.render());
	}

	@Test
	public void equivalence(){
		ST equivalence = getPredicate("a <=> b");
		Assert.assertEquals("a <==> b", equivalence.render());
		
		equivalence = getPredicate("c <=> TRUE");
		Assert.assertEquals("c <==> true", equivalence.render());
		
		equivalence = getPredicate("~e <=> f");
		Assert.assertEquals("~ e <==> f", equivalence.render());
	}
	
	@Test
	public void simple(){
		ST simple = getPredicate("a <=> b");
		Assert.assertEquals("a <==> b", simple.render());
		
		simple = getPredicate("aBooleanVar");
		Assert.assertEquals("aBooleanVar", simple.render());
		
		simple = getPredicate("#someSet = 0");
		Assert.assertEquals("#someSet = 0", simple.render());
	}

	@Test
	public void parenthesizedPredicate(){
		ST parenthesizd = getPredicate("(a and b or c)");
		Assert.assertEquals("(a & b | c)", parenthesizd.render());
		
		parenthesizd = getPredicate("((a => b) and d)");
		Assert.assertEquals("((a ==> b) & d)", parenthesizd.render());
 	}

	
	@Test
	public void forallCollectionWithoutPredicateOneVariable(){
		OZParser parser = getParser("forall c:{2,4,6,20,30,40} @ c mod 2 = 0");
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cTypeExprCtx = schemaDeclarationListCtx.getChild(0).getChild(2);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		
		ExpressionType cVarType = ExpressionType.getSetConstruction(cTypeExprCtx, ExpressionType.getNat());
		Mockito.when(typeTree.get(cTypeExprCtx)).thenReturn(cVarType);
		Variable cVar = Variable.createUndecoratedVariable("c", cVarType);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);
		Assert.assertEquals("(forall c::set of nat {2, 4, 6, 20, 30, 40} :- (c % 2 = 0))", forall.render());
	}

	@Test
	public void forallCollectionWithoutPredicateTwoVariables(){
		OZParser parser = getParser("forall c:{-2,4,6}; d:{20,30,40} @ c < d");
		
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cTypeExpr1Ctx = schemaDeclarationListCtx.getChild(0).getChild(2);
		ParseTree cTypeExpr2Ctx = schemaDeclarationListCtx.getChild(2).getChild(2);

		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		ParseTree dIdCtx = schemaDeclarationListCtx.getChild(2).getChild(0).getChild(0);

		ExpressionType cVarType = ExpressionType.getSetConstruction(cTypeExpr1Ctx, ExpressionType.getInt());
		Variable cVar = Variable.createUndecoratedVariable("c", cVarType);
		ExpressionType dVarType = ExpressionType.getSetConstruction(cTypeExpr2Ctx, ExpressionType.getNat());
		Variable dVar = Variable.createUndecoratedVariable("d", dVarType);
		
		// simulating what the type evaluator does 
		Mockito.when(typeTree.get(cTypeExpr1Ctx)).thenReturn(cVarType);
		Mockito.when(typeTree.get(cTypeExpr2Ctx)).thenReturn(dVarType);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		Mockito.when(declarationTree.get(dIdCtx)).thenReturn(dVar);
		// end of type evaluator simulation

		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);

		Assert.assertEquals("(forall c::set of int {-2, 4, 6}, d::set of nat {20, 30, 40} :- (c < d))", forall.render());
	}
	
	@Test
	public void forallCollectionWithoutPredicateTwoVariablesInOneDeclaration(){
		OZParser parser = getParser("forall c, d:{2,4,6.3} @ c < d");
		
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cTypeExpr1Ctx = schemaDeclarationListCtx.getChild(0).getChild(2);

		ExpressionType varType = ExpressionType.getSetConstruction(cTypeExpr1Ctx, ExpressionType.getReal());
		Mockito.when(typeTree.get(cTypeExpr1Ctx)).thenReturn(varType);
		
		Variable cVar = Variable.createUndecoratedVariable("c", varType);
		Variable dVar = Variable.createUndecoratedVariable("d", varType);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		ParseTree dIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(2);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		Mockito.when(declarationTree.get(dIdCtx)).thenReturn(dVar);
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);

		Assert.assertEquals("(forall c, d::set of real {2, 4, 6.3} :- (c < d))", forall.render());
	}
	
	@Test
	public void thesisExample(){
		StringBuilder input = new StringBuilder();
		input.append("forall v1:col1; v2:col2; v3:col3; v4:col4; v5:type5; v6:col6");
		input.append(" | p1(v1,v2,v3) and p2(v1,v2,v3) and p3(v1,v2,v3) and ");
		input.append("   p4(v2,v4) and p5(v3) and p6(v6) ");
		input.append(" @ p(v1,v2,v3,v4,v5)");
		
		OZParser parser = getParser(input.toString());
		
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree v1TypeExprCtx = schemaDeclarationListCtx.getChild(0).getChild(2);
		ParseTree v2TypeExprCtx = schemaDeclarationListCtx.getChild(2).getChild(2);
		ParseTree v3TypeExprCtx = schemaDeclarationListCtx.getChild(4).getChild(2);
		ParseTree v4TypeExprCtx = schemaDeclarationListCtx.getChild(6).getChild(2);
		ParseTree v5TypeExprCtx = schemaDeclarationListCtx.getChild(8).getChild(2);
		ParseTree v6TypeExprCtx = schemaDeclarationListCtx.getChild(10).getChild(2);
		
		ExpressionType setOfNat = ExpressionType.getSet(ExpressionType.getNat());
		
		ExpressionType v1VarType = ExpressionType.getVariableType(v1TypeExprCtx, setOfNat);
		ExpressionType v2VarType = ExpressionType.getVariableType(v2TypeExprCtx, setOfNat);
		ExpressionType v3VarType = ExpressionType.getVariableType(v3TypeExprCtx, setOfNat);
		ExpressionType v4VarType = ExpressionType.getVariableType(v4TypeExprCtx, setOfNat);
		ExpressionType v5VarType = ExpressionType.getUserDefinedType(v5TypeExprCtx, "type5");
		ExpressionType v6VarType = ExpressionType.getVariableType(v6TypeExprCtx, setOfNat);
		
		Mockito.when(typeTree.get(v1TypeExprCtx)).thenReturn(v1VarType);
		Mockito.when(typeTree.get(v2TypeExprCtx)).thenReturn(v2VarType);
		Mockito.when(typeTree.get(v3TypeExprCtx)).thenReturn(v3VarType);
		Mockito.when(typeTree.get(v4TypeExprCtx)).thenReturn(v4VarType);
		Mockito.when(typeTree.get(v5TypeExprCtx)).thenReturn(v5VarType);
		Mockito.when(typeTree.get(v6TypeExprCtx)).thenReturn(v6VarType);
		
		Variable v1 = Variable.createUndecoratedVariable("v1", v1VarType);
		Variable v2 = Variable.createUndecoratedVariable("v2", v2VarType);
		Variable v3 = Variable.createUndecoratedVariable("v3", v3VarType);
		Variable v4 = Variable.createUndecoratedVariable("v4", v4VarType);
		Variable v5 = Variable.createUndecoratedVariable("v5", v5VarType);
		Variable v6 = Variable.createUndecoratedVariable("v6", v6VarType);
		
		ParseTree v1IdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		ParseTree v2IdCtx = schemaDeclarationListCtx.getChild(2).getChild(0).getChild(0);
		ParseTree v3IdCtx = schemaDeclarationListCtx.getChild(4).getChild(0).getChild(0);
		ParseTree v4IdCtx = schemaDeclarationListCtx.getChild(6).getChild(0).getChild(0);
		ParseTree v5IdCtx = schemaDeclarationListCtx.getChild(8).getChild(0).getChild(0);
		ParseTree v6IdCtx = schemaDeclarationListCtx.getChild(10).getChild(0).getChild(0);
		
		Mockito.when(declarationTree.get(v1IdCtx)).thenReturn(v1);
		Mockito.when(declarationTree.get(v2IdCtx)).thenReturn(v2);
		Mockito.when(declarationTree.get(v3IdCtx)).thenReturn(v3);
		Mockito.when(declarationTree.get(v4IdCtx)).thenReturn(v4);
		Mockito.when(declarationTree.get(v5IdCtx)).thenReturn(v5);
		Mockito.when(declarationTree.get(v6IdCtx)).thenReturn(v6);

		ParseTree schemaPredicateCtx = schemaCtx.getChild(2);
		ParseTree forallPredicateCtx = predicateCtx.getChild(3);
		ParseTree pCtx = forallPredicateCtx.getChild(0).getChild(0).getChild(0).getChild(0);

		ExpressionType nat = ExpressionType.getNat();
		ExpressionType bool = ExpressionType.getBool();
		ExpressionType pType = ExpressionType.getFunction(ExpressionType.getTuple(Arrays.asList(nat, nat, nat, nat, nat)), ExpressionType.getBool());
		Mockito.when(typeTree.get(pCtx.getChild(0))).thenReturn(pType);
		
		ExpressionType oneParmeterFunction = ExpressionType.getFunction(nat, bool);
		ExpressionType twoParmeterFunction = ExpressionType.getFunction(ExpressionType.getTuple(Arrays.asList(nat, nat)), bool);
		ExpressionType threeParmeterFunction = ExpressionType.getFunction(ExpressionType.getTuple(Arrays.asList(nat, nat, nat)), bool);
		
		ParseTree p1Ctx = schemaPredicateCtx.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(0);
		ParseTree p2Ctx = schemaPredicateCtx.getChild(0).getChild(0).getChild(0).getChild(0).getChild(0).getChild(2).getChild(0).getChild(0).getChild(0);
		ParseTree p3Ctx = schemaPredicateCtx.getChild(0).getChild(0).getChild(0).getChild(0).getChild(2).getChild(0).getChild(0).getChild(0);
		ParseTree p4Ctx = schemaPredicateCtx.getChild(0).getChild(0).getChild(0).getChild(2).getChild(0).getChild(0).getChild(0);
		ParseTree p5Ctx = schemaPredicateCtx.getChild(0).getChild(0).getChild(2).getChild(0).getChild(0).getChild(0);
		ParseTree p6Ctx = schemaPredicateCtx.getChild(0).getChild(2).getChild(0).getChild(0).getChild(0);
		
		Ident v1Ident = new Ident("v1");
		Ident v2Ident = new Ident("v2");
		Ident v3Ident = new Ident("v3");
		Ident v4Ident = new Ident("v4");
		Ident v6Ident = new Ident("v6");
		AxiomReference r1 = new AxiomReference(new Idents(v1Ident, v2Ident, v3Ident), p1Ctx);
		AxiomReference r2 = new AxiomReference(new Idents(v1Ident, v2Ident, v3Ident), p2Ctx);
		AxiomReference r3 = new AxiomReference(new Idents(v1Ident, v2Ident, v3Ident), p3Ctx);
		AxiomReference r4 = new AxiomReference(new Idents(v2Ident, v4Ident), p4Ctx);
		AxiomReference r5 = new AxiomReference(new Idents(v3Ident), p5Ctx);
		AxiomReference r6 = new AxiomReference(new Idents(v6Ident), p6Ctx);
		
		Mockito.when(schemaPredicateTree.get(schemaPredicateCtx)).thenReturn(Arrays.asList(r1, r2, r3, r4, r5, r6));
		
		Mockito.when(typeTree.get(p1Ctx.getChild(0))).thenReturn(threeParmeterFunction);
		Mockito.when(typeTree.get(p2Ctx.getChild(0))).thenReturn(threeParmeterFunction);
		Mockito.when(typeTree.get(p3Ctx.getChild(0))).thenReturn(threeParmeterFunction);
		Mockito.when(typeTree.get(p4Ctx.getChild(0))).thenReturn(twoParmeterFunction);
		Mockito.when(typeTree.get(p5Ctx.getChild(0))).thenReturn(oneParmeterFunction);
		Mockito.when(typeTree.get(p6Ctx.getChild(0))).thenReturn(oneParmeterFunction);
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);
		
		StringBuilder expectedResult = new StringBuilder();
		expectedResult.append("(forall v5:type5, ");
		expectedResult.append(       "v3::(those tempVar1::col3 :- (p5[tempVar1])), ");
		expectedResult.append(       "v6::(those tempVar2::col6 :- (p6[tempVar2])), ");
		expectedResult.append(       "v4::col4, ");
		expectedResult.append(       "v2::(those tempVar3::col2 :- (p4[tempVar3, v4])), ");
		expectedResult.append(       "v1::(those tempVar4::col1 :- (p1[tempVar4, v2, v3] & p2[tempVar4, v2, v3] & p3[tempVar4, v2, v3]))");
		expectedResult.append(" :- (p[v1, v2, v3, v4, v5]))");
		Assert.assertEquals(expectedResult.toString(), forall.render());
	}
	
	
	@Test
	public void forallTypeWithoutPredicateOneVariable(){
		OZParser parser = getParser("forall c:!N @ c mod 2 = 0");
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cTypeExpr1Ctx = schemaDeclarationListCtx.getChild(0).getChild(2);

		ExpressionType varType = ExpressionType.getNat();
		Mockito.when(typeTree.get(cTypeExpr1Ctx)).thenReturn(varType);
		
		Variable cVar = Variable.createUndecoratedVariable("c", varType);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);

		Assert.assertEquals("(forall c:nat :- (c % 2 = 0))", forall.render());
	}
	@Test
	public void forallTypeWithoutPredicateTwoVariables(){
		OZParser parser = getParser("forall c:!N; d:!Z @ c < d");
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cTypeExprCtx = schemaDeclarationListCtx.getChild(0).getChild(2);
		ParseTree dTypeExprCtx = schemaDeclarationListCtx.getChild(2).getChild(2);

		ExpressionType cVarType = ExpressionType.getNat();
		ExpressionType dVarType = ExpressionType.getInt();
		Mockito.when(typeTree.get(cTypeExprCtx)).thenReturn(cVarType);
		Mockito.when(typeTree.get(dTypeExprCtx)).thenReturn(cVarType);
		
		Variable cVar = Variable.createUndecoratedVariable("c", cVarType);
		Variable dVar = Variable.createUndecoratedVariable("d", dVarType);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		ParseTree dIdCtx = schemaDeclarationListCtx.getChild(2).getChild(0).getChild(0);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		Mockito.when(declarationTree.get(dIdCtx)).thenReturn(dVar);

		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);

		Assert.assertEquals("(forall c:nat, d:int :- (c < d))", forall.render());
	}

	@Test
	public void forallCollectionWithPredicate1Variable1Predicate(){
		OZParser parser = getParser("forall c:{2,20,200} | c < 100 @ c mod 2 = 0");
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cTypeExprCtx = schemaDeclarationListCtx.getChild(0).getChild(2);

		ExpressionType cVarType = ExpressionType.getSetConstruction(cTypeExprCtx, ExpressionType.getNat());
		Mockito.when(typeTree.get(cTypeExprCtx)).thenReturn(cVarType);
		
		Variable cVar = Variable.createUndecoratedVariable("c", cVarType);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);

		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);
		Assert.assertEquals("(forall c::(those tempVar1::set of nat {2, 20, 200} :- (tempVar1 < 100)) :- (c % 2 = 0))", forall.render());
	}
	
	@Test
	public void forallCollectionWithPredicate2VariablesInDeclarationList(){
		OZParser parser = getParser("forall c, d:{2,20,200} | c ~= d @ c < 200*d");
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cdTypeExprCtx = schemaDeclarationListCtx.getChild(0).getChild(2);

		ExpressionType cdVarType = ExpressionType.getSetConstruction(cdTypeExprCtx, ExpressionType.getNat());
		Mockito.when(typeTree.get(cdTypeExprCtx)).thenReturn(cdVarType);
		
		Variable cVar = Variable.createUndecoratedVariable("c", cdVarType);
		Variable dVar = Variable.createUndecoratedVariable("d", cdVarType);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		ParseTree dIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(2);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		Mockito.when(declarationTree.get(dIdCtx)).thenReturn(dVar);

		ParseTree schemaPredicateCtx = schemaCtx.getChild(2);
		Ident cIdent = new Ident("c");
		Ident dIdent = new Ident("d");
		AxiomReference r = new AxiomReference(new Idents(cIdent, dIdent), schemaPredicateCtx);
		Mockito.when(schemaPredicateTree.get(schemaPredicateCtx)).thenReturn(Arrays.asList(r));
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);
		Assert.assertEquals("(forall c::set of nat {2, 20, 200}, d::(those tempVar1::set of nat {2, 20, 200} :- (c ~= tempVar1)) :- (c < 200 * d))", forall.render());
	}
	@Test
	public void forallCollectionWithPredicate2VariablesSeparateDeclarations(){
		OZParser parser = getParser("forall c:{2,20,200}; d:{2,20,200} | c ~= d @ c < 200*d");
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cTypeExprCtx = schemaDeclarationListCtx.getChild(0).getChild(2);
		ParseTree dTypeExprCtx = schemaDeclarationListCtx.getChild(2).getChild(2);

		ExpressionType cVarType = ExpressionType.getSetConstruction(cTypeExprCtx, ExpressionType.getNat());
		ExpressionType dVarType = ExpressionType.getSetConstruction(dTypeExprCtx, ExpressionType.getNat());
		Mockito.when(typeTree.get(cTypeExprCtx)).thenReturn(cVarType);
		Mockito.when(typeTree.get(dTypeExprCtx)).thenReturn(dVarType);
		
		Variable cVar = Variable.createUndecoratedVariable("c", cVarType);
		Variable dVar = Variable.createUndecoratedVariable("d", dVarType);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		ParseTree dIdCtx = schemaDeclarationListCtx.getChild(2).getChild(0).getChild(0);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		Mockito.when(declarationTree.get(dIdCtx)).thenReturn(dVar);

		ParseTree schemaPredicateCtx = schemaCtx.getChild(2);
		Ident cIdent = new Ident("c");
		Ident dIdent = new Ident("d");
		AxiomReference r = new AxiomReference(new Idents(cIdent, dIdent), schemaPredicateCtx);
		Mockito.when(schemaPredicateTree.get(schemaPredicateCtx)).thenReturn(Arrays.asList(r));

		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);
		Assert.assertEquals("(forall c::set of nat {2, 20, 200}, d::(those tempVar1::set of nat {2, 20, 200} :- (c ~= tempVar1)) :- (c < 200 * d))", forall.render());
	}
	
	@Test
	public void forallCollectionWithPredicate3Variables2Predicates(){
		OZParser parser = getParser("forall c:cSet; d:dSet; e:ePairs | c ~= d and c > 10 @ (c,d) in e");
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree forallPredicateCtx = predicateCtx.getChild(3);
		ParseTree cTypeExprCtx = schemaDeclarationListCtx.getChild(0).getChild(2);
		ParseTree dTypeExprCtx = schemaDeclarationListCtx.getChild(2).getChild(2);
		ParseTree eTypeExprCtx = schemaDeclarationListCtx.getChild(4).getChild(2);
		ParseTree tupleCtx = forallPredicateCtx.getChild(0).getChild(0).getChild(0).getChild(0);

		ExpressionType cVarType = ExpressionType.getVariableType(cTypeExprCtx, ExpressionType.getBag(ExpressionType.getNat()));
		ExpressionType dVarType = ExpressionType.getVariableType(dTypeExprCtx, ExpressionType.getBag(ExpressionType.getNat()));
		ExpressionType eVarType = ExpressionType.getVariableType(eTypeExprCtx, ExpressionType.getRelation(ExpressionType.getNat(), ExpressionType.getNat()));
		Mockito.when(typeTree.get(cTypeExprCtx)).thenReturn(cVarType);
		Mockito.when(typeTree.get(dTypeExprCtx)).thenReturn(dVarType);
		Mockito.when(typeTree.get(eTypeExprCtx)).thenReturn(eVarType);
		
		Variable cVar = Variable.createUndecoratedVariable("c", cVarType);
		Variable dVar = Variable.createUndecoratedVariable("d", dVarType);
		Variable eVar = Variable.createUndecoratedVariable("e", eVarType);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		ParseTree dIdCtx = schemaDeclarationListCtx.getChild(2).getChild(0).getChild(0);
		ParseTree eIdCtx = schemaDeclarationListCtx.getChild(4).getChild(0).getChild(0);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		Mockito.when(declarationTree.get(dIdCtx)).thenReturn(dVar);
		Mockito.when(declarationTree.get(eIdCtx)).thenReturn(eVar);

		ParseTree schemaPredicateCtx = schemaCtx.getChild(2);;
		ParseTree schemaPredicate1Ctx = schemaCtx.getChild(2).getChild(0).getChild(0);
		ParseTree schemaPredicate2Ctx = schemaCtx.getChild(2).getChild(0).getChild(2);
		Ident cIdent = new Ident("c");
		Ident dIdent = new Ident("d");
		AxiomReference r1 = new AxiomReference(new Idents(cIdent, dIdent), schemaPredicate1Ctx);
		AxiomReference r2 = new AxiomReference(new Idents(cIdent), schemaPredicate2Ctx);
		Mockito.when(schemaPredicateTree.get(schemaPredicateCtx)).thenReturn(Arrays.asList(r1, r2));

		Mockito.when(typeTree.get(tupleCtx)).thenReturn(ExpressionType.getTuple(Arrays.asList(ExpressionType.getNat(), ExpressionType.getNat())));

		walker.walk(predicateTranslator, predicateCtx);
		
		ST forall = templateTree.get(predicateCtx);
		Assert.assertEquals("(forall e::ePairs, c::(those tempVar1::cSet :- (tempVar1 > 10)), d::(those tempVar2::dSet :- (c ~= tempVar2)) :- (pair of (nat, nat) {c, d} in e))", forall.render());
	}

	@Test
	public void existsOne(){
		OZParser parser = getParser("exists1 c:{2,4,6} @ c mod 2 = 0");
		PredicateContext predicateCtx = parser.predicate();
		ParseTree schemaCtx = predicateCtx.getChild(1);
		ParseTree schemaDeclarationListCtx = schemaCtx.getChild(0);
		ParseTree cTypeExprCtx = schemaDeclarationListCtx.getChild(0).getChild(2);

		ExpressionType cVarType = ExpressionType.getSetConstruction(cTypeExprCtx, ExpressionType.getNat());
		Mockito.when(typeTree.get(cTypeExprCtx)).thenReturn(cVarType);
		
		Variable cVar = Variable.createUndecoratedVariable("c", cVarType);
		ParseTree cIdCtx = schemaDeclarationListCtx.getChild(0).getChild(0).getChild(0);
		Mockito.when(declarationTree.get(cIdCtx)).thenReturn(cVar);
		
		walker.walk(predicateTranslator, predicateCtx);
		
		ST existsOne = templateTree.get(predicateCtx);
		
		StringBuilder expectedResult = new StringBuilder();
		expectedResult.append("(exists c::set of nat {2, 4, 6} :- (c % 2 = 0 & ");
		expectedResult.append(       "(forall tempVar2::(those tempVar1::set of nat {2, 4, 6} :- (tempVar1 % 2 = 0)) :- (tempVar2 = c))))");
		
		Assert.assertEquals(expectedResult.toString(), existsOne.render());
	}



}
