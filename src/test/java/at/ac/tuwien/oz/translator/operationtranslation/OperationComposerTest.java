package at.ac.tuwien.oz.translator.operationtranslation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.Operation;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.definitions.operation.SimpleBoolFunction;
import at.ac.tuwien.oz.definitions.operation.SimpleChangeOperation;
import at.ac.tuwien.oz.definitions.operation.SimpleFunction;
import at.ac.tuwien.oz.definitions.operation.SimpleOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClasses;
import at.ac.tuwien.oz.definitions.ozclass.Operations;
import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;
import at.ac.tuwien.oz.parser.OZParser.PredicateContext;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class OperationComposerTest {

	@Mock private ParseTreeProperty<ST> templateTree;
	@Mock private ObjectZDefinition program;
	@Mock private ObjectZClass aClass;
	
	private ObjectZClasses classes;
	private Operations simpleOperations;
	private List<IComposableOperation> operationExpressions;
	
	private AxiomReference preconditionReference;
	private AxiomReference postconditionReference;

	private Idents preconditionIdentifiers;
	private Idents postconditionIdentifiers;
	
	@Mock private PredicateContext preconditionCtx;
	@Mock private PredicateContext postconditionCtx;
	
	@Mock private ST preconditionTemplate;
	@Mock private ST postconditionTemplate;
	
	private PerfectTemplateProvider perfect;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		perfect = PerfectTemplateProvider.getInstance();
		
		preconditionIdentifiers = new Idents();
		preconditionIdentifiers.add(new Ident("stateVar"));
		preconditionIdentifiers.add(new Ident("inputVar", "?"));
		preconditionReference = new AxiomReference(preconditionIdentifiers, preconditionCtx);
		
		postconditionIdentifiers = new Idents();
		postconditionIdentifiers.add(new Ident("primedStateVar", "'"));
		postconditionIdentifiers.add(new Ident("outputVar", "?"));
		postconditionReference = new AxiomReference(postconditionIdentifiers, postconditionCtx);
		
		classes = new ObjectZClasses();
		classes.add(aClass);
	
		simpleOperations = new Operations();
		operationExpressions = new ArrayList<IComposableOperation>();
		
		Mockito.when(program.getObjectZClasses()).thenReturn(classes);
		Mockito.when(aClass.getSimpleOperations()).thenReturn(simpleOperations);
		Mockito.when(aClass.getOperationExpressions()).thenReturn(operationExpressions);
		
		Mockito.when(templateTree.get(preconditionCtx)).thenReturn(preconditionTemplate);
		Mockito.when(templateTree.get(postconditionCtx)).thenReturn(postconditionTemplate);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void preAndPostconditionsAreCreatedForSimpleOperations(){
		AxiomReferences preconditionRefs = new AxiomReferences();
		preconditionRefs.add(preconditionReference);
		AxiomReferences postconditionRefs = new AxiomReferences();
		postconditionRefs.add(postconditionReference);
		
		SimpleOperation op = buildSimpleOperation(preconditionRefs, postconditionRefs);
		
		OperationComposer composer  = new OperationComposer(program, templateTree, null);
		composer.compose();
		
		Mockito.verify(op, Mockito.times(1)).createPreAndPostconditions(Mockito.any(ParseTreeProperty.class));
		
		Axiom preconditionAxiom = preconditionReference.getAxiom();
		Assert.assertTrue(preconditionAxiom.isPrecondition());
		Assert.assertFalse(preconditionAxiom.isPostcondition());
		Assert.assertEquals(preconditionIdentifiers, preconditionAxiom.getIdentifiers());
		Assert.assertEquals(preconditionTemplate, preconditionAxiom.getPredicate());
		
		Axiom postconditionAxiom = postconditionReference.getAxiom();
		Assert.assertFalse(postconditionAxiom.isPrecondition());
		Assert.assertTrue(postconditionAxiom.isPostcondition());
		Assert.assertEquals(postconditionIdentifiers, postconditionAxiom.getIdentifiers());
		Assert.assertEquals(postconditionTemplate, postconditionAxiom.getPredicate());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void preAndPostconditionsAreCreatedForOperationExpressions(){
		Operation op1 = buildOperationExpression();
		Operation op2 = buildOperationExpression();
		
		OperationComposer composer  = new OperationComposer(program, templateTree, null);
		composer.compose();
		
		Mockito.verify(op1, Mockito.times(1)).createPreAndPostconditions(Mockito.any(ParseTreeProperty.class));
		Mockito.verify(op2, Mockito.times(1)).createPreAndPostconditions(Mockito.any(ParseTreeProperty.class));
	}
	
	
	@Test
	public void simpleBoolFunctionHasCloneOfPredicateTreeTemplateAsPrecondition(){
		String predicate1 = "property1 < 100";
		Ident property1 = new Ident("property1");
		OZParser parser = getParser(predicate1);
		
		PredicateContext predicateCtx = parser.predicate();
		ST predicate = perfect.getLessThan(perfect.getId(property1), perfect.getNumber("100"));
		Mockito.when(templateTree.get(predicateCtx)).thenReturn(predicate);
		
		SimpleBoolFunction isSmallAmount = new SimpleBoolFunction(
				"isSmallAmount", 
				Declarations.empty(), 
				new AxiomReferences(new AxiomReference(new Idents(property1), predicateCtx)), 
				aClass,
				null);
		
		this.simpleOperations.add(isSmallAmount);
		
		OperationComposer composer = new OperationComposer(program, templateTree, null);
		composer.compose();
		
		Assert.assertEquals(1, isSmallAmount.getPreconditions().size());
		IPrecondition simplePrecondition1 = isSmallAmount.getPreconditions().get(0);
		Assert.assertNotEquals(predicate, simplePrecondition1.getTemplate());
		Assert.assertEquals(predicate.render(), simplePrecondition1.getTemplate().render());
		Assert.assertEquals("property1 < 100", simplePrecondition1.getTemplate().render());
	}

	@Test
	public void simpleFunctionHasCloneOfPredicateTreeTemplateAsPreconditionResultPostconditionAndImplicitPrecondition(){
		String predicate1 = "property1 < 100";
		Ident property1 = new Ident("property1");

		String predicate2 = "outVar1! = 100";
		Variable outVar1 = Variable.createOutputVariable("outVar1", ExpressionType.getNat());
		
		PredicateContext predicate1Ctx = getParser(predicate1).predicate();
		PredicateContext predicate2Ctx = getParser(predicate2).predicate();
		
		ST predicate1ST = perfect.getLessThan(perfect.getId(property1), perfect.getNumber("100"));
		ST predicate2ST = perfect.getEquals(perfect.getId(outVar1.getId()), perfect.getNumber("100"));
		
		Mockito.when(templateTree.get(predicate1Ctx)).thenReturn(predicate1ST);
		Mockito.when(templateTree.get(predicate2Ctx)).thenReturn(predicate2ST);
		
		SimpleFunction aFunction = new SimpleFunction(
				"aFunction", 
				new Declarations(outVar1), 
				new AxiomReferences(new AxiomReference(new Idents(property1), predicate1Ctx), new AxiomReference(new Idents(outVar1.getId()), predicate2Ctx)), 
				aClass, 
				null);
		
		this.simpleOperations.add(aFunction);
		
		OperationComposer composer = new OperationComposer(program, templateTree, null);
		composer.compose();
		
		Assert.assertEquals(2, aFunction.getPreconditions().size());
		
		IPrecondition simplePrecondition1 = aFunction.getPreconditions().get(0);
		Assert.assertNotEquals("lt", simplePrecondition1.getTemplate().getName());
		Assert.assertEquals(predicate1ST.render(), simplePrecondition1.getTemplate().render());

		IPrecondition simplePrecondition2 = aFunction.getPreconditions().get(1);
		Assert.assertNotEquals("exists", simplePrecondition2.getTemplate().getName());
		Assert.assertEquals("(exists outVar1_temp:nat :- (outVar1_temp = 100))", simplePrecondition2.getTemplate().render());

		Assert.assertEquals(1, aFunction.getPostconditions().size());
		IPostcondition simplePostcondition1 = aFunction.getPostconditions().get(0);
		Assert.assertNotEquals("equals", simplePostcondition1.getTemplate().getName());
		Assert.assertEquals("result.outVar1_out = 100", simplePostcondition1.getTemplate().render());

	}

	@Test
	public void simpleSchemaHasCloneOfPredicateTreeTemplateAsPreconditionResultPostconditionAndImplicitPrecondition(){
		String predicate1 = "property1 < 100";
		Variable property1 = Variable.createUndecoratedVariable("property1", ExpressionType.getNat());

		String predicate2 = "property1' = property1 * 2";
		
		PredicateContext predicate1Ctx = getParser(predicate1).predicate();
		PredicateContext predicate2Ctx = getParser(predicate2).predicate();
		
		ST predicate1ST = perfect.getLessThan(perfect.getId(property1.getId()), perfect.getNumber("100"));
		ST rightSideST = perfect.getMultiplication(perfect.getId(property1.getId()), perfect.getNumber("2"));
		ST predicate2ST = perfect.getEquals(perfect.getId(property1.getPrimedCopy().getId()), rightSideST);
		
		Mockito.when(templateTree.get(predicate1Ctx)).thenReturn(predicate1ST);
		Mockito.when(templateTree.get(predicate2Ctx)).thenReturn(predicate2ST);
		
		Mockito.when(aClass.resolveVariable(property1.getId())).thenReturn(property1);
		
		SimpleChangeOperation aSchema = new SimpleChangeOperation(
				"aSchema", 
				Arrays.asList("property1"),
				Declarations.empty(), 
				new AxiomReferences(
						new AxiomReference(new Idents(property1.getId()), predicate1Ctx), 
						new AxiomReference(new Idents(property1.getId(), property1.getPrimedCopy().getId()), predicate2Ctx)), 
				aClass,
				null);
		
		this.simpleOperations.add(aSchema);
		
		OperationComposer composer = new OperationComposer(program, templateTree, null);
		composer.compose();
		
		Assert.assertEquals(2, aSchema.getPreconditions().size());
		
		IPrecondition simplePrecondition1 = aSchema.getPreconditions().get(0);
		Assert.assertNotEquals("lt", simplePrecondition1.getTemplate().getName());
		Assert.assertEquals(predicate1ST.render(), simplePrecondition1.getTemplate().render());

		IPrecondition simplePrecondition2 = aSchema.getPreconditions().get(1);
		Assert.assertNotEquals("exists", simplePrecondition2.getTemplate().getName());
		Assert.assertEquals("(exists property1_temp:nat :- (property1_temp = property1 * 2))", simplePrecondition2.getTemplate().render());

		Assert.assertEquals(1, aSchema.getPostconditions().size());
		IPostcondition simplePostcondition1 = aSchema.getPostconditions().get(0);
		Assert.assertNotEquals("equals", simplePostcondition1.getTemplate().getName());
		Assert.assertEquals(predicate2ST.render(), simplePostcondition1.getTemplate().render());
		Assert.assertEquals("property1' = property1 * 2", simplePostcondition1.getTemplate().render());
	}	
	
	private OZParser getParser(String boolFunction) {
		CharStream stream = CharStreams.fromString(boolFunction);
		OZLexer lexer = new OZLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		OZParser parser = new OZParser(tokens);
		return parser;
	}

	
	private Operation buildOperationExpression() {
		OperationPromotion op = Mockito.mock(OperationPromotion.class);
		this.operationExpressions.add(op);
		return op;
	}

	private SimpleOperation buildSimpleOperation(AxiomReferences preconditionRefs, AxiomReferences postconditionRefs) {
		SimpleOperation op = Mockito.mock(SimpleOperation.class);
		Mockito.when(op.getPreconditionAxiomReferences()).thenReturn(preconditionRefs);
		Mockito.when(op.getPostconditionAxiomReferences()).thenReturn(postconditionRefs);
		this.simpleOperations.add(op);
		return op;
	}


}
