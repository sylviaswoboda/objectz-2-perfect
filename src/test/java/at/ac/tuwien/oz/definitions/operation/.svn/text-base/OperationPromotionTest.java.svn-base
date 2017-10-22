package at.ac.tuwien.oz.definitions.operation;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.junit.After;
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
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.SimpleFunctionPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.SimpleSchemaPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePrecondition;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.PreconditionFunctionCalls;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;
import at.ac.tuwien.oz.parser.OZParser.PredicateContext;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class OperationPromotionTest {

	@Mock private IOperation anyOperation;
	
	@Mock private SimplePrecondition simplePrecondition;
	@Mock private SimpleFunctionPostcondition simpleFunctionPostcondition;
	@Mock private SimpleSchemaPostcondition simpleSchemaPostcondition;
	
	@Mock private Variable anyVariable;
	
	@Mock private PreconditionFactory preconditionFactory;
	@Mock private PostconditionFactory postconditionFactory;
	
	@Mock private PreconditionFunction preconditionFunction;
	@Mock private PreconditionFunctionCall preconditionFunctionCall;
	@Mock private ST caller;
	
	@Mock private CallerContext callerCtxMock;
	
	@Mock private ObjectZDefinition program;	
	
	private AxiomReference preconditionReference;
	private Idents preconditionIdentifiers;
	@Mock private PredicateContext preconditionCtx;
	
	private AxiomReference postconditionReference;
	private Idents postconditionIdentifiers;
	@Mock private PredicateContext postconditionCtx;

	@Mock private ObjectZClass simpleOperationClass;
	@Mock private ObjectZClass promotedOperationClass;

	@Mock private ParseTreeProperty<ExpressionType> typeTree;
	@Mock private ParseTree ctx;

	private Variable myInputVar;

	private Variable myOutputVar;

	private Variable myAuxVar;

	private static final Ident SIMPLE_OPERATION_CLASS_NAME = new Ident("A");

	private SimpleFunction myFunc;

	private SimpleBoolFunction myBoolFunc;
	
	@Before
	public void before(){
		MockitoAnnotations.initMocks(this);
		
//		PreconditionFactory.setInstance(preconditionFactory);
		PostconditionFactory.setInstance(postconditionFactory);
		
		preconditionIdentifiers = new Idents();
		Ident stateVar = new Ident("stateVar");
		preconditionIdentifiers.add(stateVar);
		preconditionReference = new AxiomReference(preconditionIdentifiers, preconditionCtx);
		
		postconditionIdentifiers = new Idents();
		postconditionIdentifiers.add(stateVar.getPrimedCopy());
		postconditionReference = new AxiomReference(postconditionIdentifiers, postconditionCtx);

		Mockito.when(simpleOperationClass.getDefinition()).thenReturn(program);
		Mockito.when(program.resolveClass(SIMPLE_OPERATION_CLASS_NAME)).thenReturn(simpleOperationClass);

		Mockito.when(promotedOperationClass.getDefinition()).thenReturn(program);
		
		myInputVar = Variable.createInputVariable("myInput", ExpressionType.getNat());
		myOutputVar = Variable.createOutputVariable("myOutput", ExpressionType.getNat());
		myAuxVar = Variable.createUndecoratedVariable("myAux", ExpressionType.getNat());
	}

	@After
	public void tearDown(){
		PreconditionFactory.setInstance(null);
		PostconditionFactory.setInstance(null);
	}
	
	@Test
	public void promotedFunctionWithPreconditionsHasPreconditionFunctionCallToPromotedFunction(){
		OperationPromotion opPromo = buildFunctionPromotion();
		opPromo.resolveOperationReferences(typeTree);
		
		myFunc.createPreAndPostconditions(typeTree);
		opPromo.createPreAndPostconditions(typeTree);
		
		Assert.assertEquals(1, opPromo.getPreconditions().size());
		Assert.assertTrue(opPromo.hasPreconditions());
		Assert.assertNotNull(opPromo.getPreconditionFunction());
		Assert.assertEquals(PreconditionFunctionCalls.class, opPromo.getPreconditions().getClass());

		PreconditionFunctionCalls calls = (PreconditionFunctionCalls)opPromo.getPreconditions();
		Assert.assertEquals(1, calls.size());
		
		PreconditionFunctionCall call1 = calls.get(0);
		Assert.assertEquals(call1.getCaller(), opPromo.getCaller());
		Assert.assertEquals(myFunc.getPreconditionFunction(), call1.getPreconditionFunction());
	}

	@Test
	public void boolFunctionHasAlwaysEmptyPostconditions(){
		OperationExpression opPromo = buildBoolFunctionPromotion();
		opPromo.resolveOperationReferences(typeTree);
		opPromo.createPreAndPostconditions(typeTree);
		
		IPostconditions postconditions = opPromo.getPostconditions();
		Assert.assertEquals(EmptyPostconditions.class, postconditions.getClass());
	}

	@Test
	public void functionPostconditionIsBuiltAsOutputPromotionsByPostconditionFactory(){
		OperationPromotion opPromo = buildFunctionPromotion();
		opPromo.resolveOperationReferences(typeTree);
		opPromo.createPreAndPostconditions(typeTree);

		Mockito.verify(postconditionFactory, Mockito.times(1)).createOutputPromotions(opPromo, opPromo.getOutputParameters());
	}
	
	@Test(expected=ObjectZToPerfectTranslationException.class)
	public void before_ResolveOperationReferences_isFunction_ThrowsException(){
		OperationExpression funcPromo = buildFunctionPromotion();
		
		funcPromo.isFunction();
	}

	@Test
	public void after_ResolveOperationReferences_isFunction_evaluatesToSameAsPromotedOperation(){
		OperationExpression funcPromo = buildFunctionPromotion();
		funcPromo.resolveOperationReferences(typeTree);
		
		Assert.assertTrue(funcPromo.isFunction());
	}

	@Test
	public void resolveOperationReferences_SetsPromotedOperationOfSameClass(){
		OperationPromotion opPromo = buildBoolFunctionPromotion();
		
		opPromo.resolveOperationReferences(typeTree);
		
		Assert.assertEquals(myBoolFunc, opPromo.getPromotedOperation());
		Assert.assertEquals(new Declarations(myInputVar), opPromo.getInputParameters());
		Assert.assertEquals(Declarations.empty(), opPromo.getOutputParameters());
		Assert.assertEquals(Declarations.empty(), opPromo.getAuxiliaryParameters());
	}

	@Test
	public void resolveOperationReferences_SetsPromotedOperationOfOtherClass(){
		OperationPromotion funcPromo = buildFunctionPromotion();
		
		funcPromo.resolveOperationReferences(typeTree);
		
		Assert.assertEquals(myFunc, funcPromo.getPromotedOperation());
		Assert.assertEquals(new Declarations(myInputVar), funcPromo.getInputParameters());
		Assert.assertEquals(new Declarations(myOutputVar), funcPromo.getOutputParameters());
		Assert.assertEquals(Declarations.empty(), funcPromo.getAuxiliaryParameters());
	}
	
	@Test
	public void resolveOperationReferences_PromotesInputAndOutputVariables(){
		OperationExpression funcPromo = buildFunctionPromotion();
		
		funcPromo.resolveOperationReferences(typeTree);
		
		Assert.assertEquals(new Declarations(myInputVar), funcPromo.getInputParameters());
		Assert.assertEquals(new Declarations(myOutputVar), funcPromo.getOutputParameters());
	}

	@Test
	public void resolveOperationReferences_IgnoresAuxiliaryVariables(){
		OperationExpression funcPromo = buildFunctionPromotion();
		funcPromo.resolveOperationReferences(typeTree);
		Assert.assertEquals(Declarations.empty(), funcPromo.getAuxiliaryParameters());
	}

	private OperationPromotion buildBoolFunctionPromotion() {
		String operationName = "myBoolFunc";
		buildSimpleBoolFunction(operationName);
		OperationPromotion boolFuncPromo = buildOperationPromotion(null, operationName, simpleOperationClass);
		return boolFuncPromo;
	}

	private OperationPromotion buildFunctionPromotion() {
		String operationName = "myFunc";
		buildSimpleFunction(operationName);
		CallerContext callerMock = mockCallerClass(SIMPLE_OPERATION_CLASS_NAME.getName()); 
		OperationPromotion funcPromo = buildOperationPromotion(callerMock, operationName, promotedOperationClass);
		return funcPromo;
	}

	private CallerContext mockCallerClass(String className) {
		CallerContext callerMock = Mockito.mock(CallerContext.class);
		Mockito.when(typeTree.get(callerMock)).thenReturn(ExpressionType.getUserDefinedType(null, className));
		Mockito.when(typeTree.get(callerCtxMock)).thenReturn(ExpressionType.getUserDefinedType(null, SIMPLE_OPERATION_CLASS_NAME.getName()));
		return callerMock;
	}

	private OperationPromotion buildOperationPromotion(CallerContext callerCtx, String operationName, ObjectZClass definingClass) {
		OperationPromotion funcPromo = new OperationPromotion(callerCtx, operationName);
		funcPromo.setDefiningClass(definingClass);
		return funcPromo;
	}

	private SimpleBoolFunction buildSimpleBoolFunction(String operationName) {
		myBoolFunc = new SimpleBoolFunction(
				operationName,
				new Declarations(myInputVar), 
				new AxiomReferences(this.preconditionReference),
				simpleOperationClass,
				ctx);
		Mockito.when(simpleOperationClass.resolveOperation(new Ident(operationName))).thenReturn(myBoolFunc);
		return myBoolFunc;
	}	
	
	private SimpleFunction buildSimpleFunction(String operationName) {
		myFunc = new SimpleFunction(
				operationName,
				new Declarations(myInputVar, myOutputVar, myAuxVar), 
				new AxiomReferences(this.preconditionReference, this.postconditionReference),
				simpleOperationClass,
				ctx);
		Mockito.when(simpleOperationClass.resolveOperation(new Ident(operationName))).thenReturn(myFunc);
		return myFunc;
	}
}
