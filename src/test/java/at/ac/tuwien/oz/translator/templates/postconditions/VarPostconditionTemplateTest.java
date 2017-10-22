package at.ac.tuwien.oz.translator.templates.postconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexChangePostconditionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ChangeOperationCalls;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ThenPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.PerfectPostconditionTemplateProvider;

public class VarPostconditionTemplateTest {

	private PerfectPostconditionTemplateProvider templateProvider;
	private PostconditionTestMockProvider postconditionMockProvider;
	
	@Mock private ComplexChangePostconditionMapping mapping;
	@Mock private ThenPostcondition thenPostcondition;
	private ExpressionType type;
	
	private Variable commVar;
	private Variable commVarOut;
	private Variable commVarIn;
	private Variable inVar;
	private Variable outVar;

	private OutputPromotions outputPromotions;
	private OutputPromotions communicationOutputPromotions;
	private ChangeOperationCalls communicationChangeOperationCalls;
	private Declarations allCommunicationVariables;
	private Declarations visibleCommunicationVariables;
	private Declarations sharedOutputVariables;
	
	private List<List<ChangeOperationCall>> thenPostconditions;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		TempVarProvider.resetNameCounter();
		this.templateProvider = PerfectPostconditionTemplateProvider.getInstance();
		this.postconditionMockProvider = new PostconditionTestMockProvider();
		this.type = ExpressionType.getNat();
		this.commVar = Variable.createUndecoratedVariable("commVar", type);
		this.commVarOut = Variable.createOutputVariable("commVar", type);
		this.commVarIn = Variable.createInputVariable("commVar", type);
		this.inVar = Variable.createInputVariable("inVar", type);
		this.outVar = Variable.createOutputVariable("outVar", type);
		
		this.outputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		this.communicationOutputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		this.communicationChangeOperationCalls = new ChangeOperationCalls();
		this.allCommunicationVariables = new Declarations();
		this.visibleCommunicationVariables = new Declarations();
		this.sharedOutputVariables = new Declarations();
		
		this.thenPostconditions = new ArrayList<List<ChangeOperationCall>>();
		
		Mockito.when(mapping.getCommunicationOutputPromotions()).thenReturn(communicationOutputPromotions);
		Mockito.when(mapping.getOutputPromotions()).thenReturn(outputPromotions);
		Mockito.when(mapping.getCommunicationChangeOperationCalls()).thenReturn(communicationChangeOperationCalls);
		Mockito.when(mapping.getAllCommunicationVariables()).thenReturn(allCommunicationVariables);
		Mockito.when(mapping.getVisibleCommunicationVariables()).thenReturn(visibleCommunicationVariables);
		Mockito.when(mapping.getSharedOutputVariables()).thenReturn(sharedOutputVariables);
		
		Mockito.when(thenPostcondition.getAllCommunicationVariables()).thenReturn(allCommunicationVariables);
		Mockito.when(thenPostcondition.getVisibleCommunicationVariables()).thenReturn(visibleCommunicationVariables);
		Mockito.when(thenPostcondition.getSharedOutputVariables()).thenReturn(sharedOutputVariables);
		Mockito.when(thenPostcondition.getPostconditions()).thenReturn(thenPostconditions);
		Mockito.when(thenPostcondition.getCommunicatingPromotions()).thenReturn(communicationOutputPromotions);
	}
	
	@Test
	public void outputPromotionCommunicatesToChangeOperationCall(){
		// a.func(inVar).commVar_out
		// b!changeOp(commVar_in, inVar)
		OperationPromotion func = postconditionMockProvider.buildPromotionMock(
									"a", "func", new Declarations(inVar), new Declarations(commVarOut));
		OutputPromotion p = new OutputPromotion(commVarOut, func, OutputPromotionContext.SCHEMA);
		
		OperationPromotion changeOp = postconditionMockProvider.buildSchemaPromotionMock(
										"b", "changeOp", new Declarations(commVarIn, inVar), new Declarations(outVar));
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		
		allCommunicationVariables.add(commVar);
		communicationOutputPromotions.add(p);
		communicationChangeOperationCalls.add(opCall);
		
		String expectedResult = "(var tempVar1:nat; (tempVar1! = a.func(inVar_in).commVar_out & b!changeOp(tempVar1', inVar_in, outVar_out!)))";
		ST resultTemplate = templateProvider.createVarPostcondition(mapping);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void changeOperationCallCommunicatesToOutputPromotion(){
		// a.func(commVar_in, inVar).outVar_out
		// b!changeOp(inVar, commVar_out!)
		OperationPromotion func = postconditionMockProvider.buildPromotionMock(
										"a", "func", new Declarations(inVar, commVarIn), new Declarations(outVar));
		OutputPromotion p1 = new OutputPromotion(outVar, func, OutputPromotionContext.SCHEMA);
		
		OperationPromotion changeOp = postconditionMockProvider.buildSchemaPromotionMock(
										"b", "changeOp", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		
		allCommunicationVariables.add(commVar);
		outputPromotions.add(p1);
		communicationChangeOperationCalls.add(opCall);
		
		String expectedResult = "(var tempVar1:nat; (outVar_out! = a.func(tempVar1', inVar_in).outVar_out & b!changeOp(inVar_in, tempVar1!)))";
		ST resultTemplate = templateProvider.createVarPostcondition(mapping);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void outputPromotionCommunicatesAssociativeToChangeOperationCall(){
		// a.func(inVar).commVar_out
		// b!changeOp(commVar_in, inVar)
		OperationPromotion func = postconditionMockProvider.buildPromotionMock("a", "func", new Declarations(inVar), new Declarations(commVarOut));
		OutputPromotion p = new OutputPromotion(commVarOut, func, OutputPromotionContext.SCHEMA);
		
		OperationPromotion changeOp = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp", new Declarations(commVarIn, inVar), new Declarations(outVar));
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		
		allCommunicationVariables.add(commVar);
		visibleCommunicationVariables.add(commVar);
		
		communicationOutputPromotions.add(p);
		communicationChangeOperationCalls.add(opCall);
		
		String expectedResult = "(var tempVar1:nat; (tempVar1! = a.func(inVar_in).commVar_out & "
				+ "b!changeOp(tempVar1', inVar_in, outVar_out!) & "
				+ "commVar_out! = tempVar1'))";
		ST resultTemplate = templateProvider.createVarPostcondition(mapping);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void changeOperationCallCommunicatesAssociativeToOutputPromotion(){
		// a.func(commVar_in, inVar).outVar_out
		// b!changeOp(inVar, commVar_out!)
		OperationPromotion func = postconditionMockProvider.buildPromotionMock("a", "func", new Declarations(inVar, commVarIn), new Declarations(outVar));
		OutputPromotion p1 = new OutputPromotion(outVar, func, OutputPromotionContext.SCHEMA);
		
		OperationPromotion changeOp = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		
		allCommunicationVariables.add(commVar);
		visibleCommunicationVariables.add(commVar);
		
		outputPromotions.add(p1);
		communicationChangeOperationCalls.add(opCall);
		
		String expectedResult = "(var tempVar1:nat; (outVar_out! = a.func(tempVar1', inVar_in).outVar_out & "
				+ "b!changeOp(inVar_in, tempVar1!) & "
				+ "commVar_out! = tempVar1'))";
		ST resultTemplate = templateProvider.createVarPostcondition(mapping);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	
	@Test
	public void changeOperationCallCommunicatesToChangeOperation(){
		// a!changeOp1(commVar_in, inVar, outVar_out!)
		// b!changeOp2(inVar, commVar_out!)
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar, commVarIn), new Declarations(outVar));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp2", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);
		
		allCommunicationVariables.add(commVar);
		communicationChangeOperationCalls.add(opCall1);
		communicationChangeOperationCalls.add(opCall2);
		
		String expectedResult = "(var tempVar1:nat; (a!changeOp1(tempVar1', inVar_in, outVar_out!) & b!changeOp2(inVar_in, tempVar1!)))";
		ST resultTemplate = templateProvider.createVarPostcondition(mapping);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	
	@Test
	public void twoChangeOperationsShareOutput(){
		// a!changeOp1(inVar, outVar_out!)
		// b!changeOp2(inVar, outVar_out!)
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp2", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);
		
		Mockito.when(mapping.getNumberOfSharingPromotions(outVar)).thenReturn(2);
		
		sharedOutputVariables.add(outVar);
		communicationChangeOperationCalls.add(opCall1);
		communicationChangeOperationCalls.add(opCall2);
		
		String expectedResult = "(var tempVar1:nat; (a!changeOp1(inVar_in, outVar_out!) & b!changeOp2(inVar_in, tempVar1!)))";
		ST resultTemplate = templateProvider.createVarPostcondition(mapping);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void threeChangeOperationsShareOutput(){
		// a!changeOp1(inVar, outVar_out!)
		// b!changeOp2(inVar, outVar_out!)
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp2", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);

		OperationPromotion changeOp3 = postconditionMockProvider.buildSchemaPromotionMock("c", "changeOp3", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall3 = new ChangeOperationCall(changeOp3);

		Mockito.when(mapping.getNumberOfSharingPromotions(outVar)).thenReturn(3);
		
		sharedOutputVariables.add(outVar);
		communicationChangeOperationCalls.add(opCall1);
		communicationChangeOperationCalls.add(opCall2);
		communicationChangeOperationCalls.add(opCall3);
		
		String expectedResult = "(var tempVar1:nat, tempVar2:nat; (a!changeOp1(inVar_in, outVar_out!) & b!changeOp2(inVar_in, tempVar1!) & c!changeOp3(inVar_in, tempVar2!)))";
		ST resultTemplate = templateProvider.createVarPostcondition(mapping);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}
	
	@Test
	public void sharedOutputCommunicatesToOperationPromotion(){
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp2", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);
		
		OperationPromotion func = postconditionMockProvider.buildPromotionMock("c", "func", new Declarations(inVar, commVarIn), new Declarations(outVar));
		OutputPromotion opPromo1 = new OutputPromotion(outVar, func, OutputPromotionContext.SCHEMA);
		
		Mockito.when(mapping.getNumberOfSharingPromotions(commVarOut)).thenReturn(2);
		
		allCommunicationVariables.add(commVar);
		sharedOutputVariables.add(commVarOut);
		
		outputPromotions.add(opPromo1);
		communicationChangeOperationCalls.add(opCall1);
		communicationChangeOperationCalls.add(opCall2);

		String expectedResult = "(var tempVar1:nat; (outVar_out! = c.func(tempVar1', inVar_in).outVar_out & a!changeOp1(inVar_in, commVar_out!) & b!changeOp2(inVar_in, tempVar1!)))";
		ST resultTemplate = templateProvider.createVarPostcondition(mapping);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void commonCallerYieldsThenPostcondition(){
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp2", new Declarations(commVarIn), new Declarations(outVar));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);
		
		allCommunicationVariables.add(commVar);
		thenPostconditions.add(Arrays.asList(opCall1));
		thenPostconditions.add(Arrays.asList(opCall2));
		
		Map<Variable, Integer> outputIndicesMap = new TreeMap<Variable, Integer>();
		outputIndicesMap.put(commVarOut, 0);
		Mockito.when(thenPostcondition.getCommunicationOutputVariableIndices()).thenReturn(outputIndicesMap);
		
		String expectedResult = "(var tempVar1:nat; (a!changeOp1(inVar_in, tempVar1!) then\n" +
				                "         a!changeOp2(tempVar1, outVar_out!)))";
		ST resultTemplate = templateProvider.createThenPostcondition(thenPostcondition);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void communicationInputOnSameLevelIsPrimed(){
		Variable outVar2 = Variable.createOutputVariable("outVar2", type);
		
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp2", new Declarations(commVarIn), new Declarations(outVar));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);

		OperationPromotion changeOpB = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp2", new Declarations(commVarIn), new Declarations(outVar2));
		ChangeOperationCall opCallB = new ChangeOperationCall(changeOpB);

		
		allCommunicationVariables.add(commVar);
		thenPostconditions.add(Arrays.asList(opCall1, opCallB));
		thenPostconditions.add(Arrays.asList(opCall2));
		
		Map<Variable, Integer> outputIndicesMap = new TreeMap<Variable, Integer>();
		outputIndicesMap.put(commVarOut, 0);
		Mockito.when(thenPostcondition.getCommunicationOutputVariableIndices()).thenReturn(outputIndicesMap);
		
		String expectedResult = "(var tempVar1:nat; ((a!changeOp1(inVar_in, tempVar1!) & b!changeOp2(tempVar1', outVar2_out!)) then\n" +
				                "         a!changeOp2(tempVar1, outVar_out!)))";
		ST resultTemplate = templateProvider.createThenPostcondition(thenPostcondition);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	
	@Test
	public void firstCallCommunicatesToOutputPromotion(){
		Variable outVar2 = Variable.createOutputVariable("outVar2", type);
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp2", new Declarations(commVarIn), new Declarations(outVar));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);

		OutputPromotion outputPromo = postconditionMockProvider.buildOutputPromotionMockInSchema("b", "func", new Declarations(commVarIn), outVar2);

		
		allCommunicationVariables.add(commVar);
		communicationOutputPromotions.add(outputPromo);
		thenPostconditions.add(Arrays.asList(opCall1));
		thenPostconditions.add(Arrays.asList(opCall2));
		
		Map<Variable, Integer> outputIndicesMap = new TreeMap<Variable, Integer>();
		outputIndicesMap.put(commVarOut, 0);
		Mockito.when(thenPostcondition.getCommunicationOutputVariableIndices()).thenReturn(outputIndicesMap);
		
		String expectedResult = "(var tempVar1:nat; ((outVar2_out! = b.func(tempVar1').outVar2_out & "
				                                   + "a!changeOp1(inVar_in, tempVar1!)) then\n" +
				                            "         a!changeOp2(tempVar1, outVar_out!)))";
		ST resultTemplate = templateProvider.createThenPostcondition(thenPostcondition);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void visibleCommunicationVariablesAreInLastThen(){
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp2", new Declarations(commVarIn), new Declarations(outVar));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);
		
		allCommunicationVariables.add(commVar);
		visibleCommunicationVariables.add(commVar);
		thenPostconditions.add(Arrays.asList(opCall1));
		thenPostconditions.add(Arrays.asList(opCall2));
		
		Map<Variable, Integer> outputIndicesMap = new TreeMap<Variable, Integer>();
		outputIndicesMap.put(commVarOut, 0);
		Mockito.when(thenPostcondition.getCommunicationOutputVariableIndices()).thenReturn(outputIndicesMap);
		
		String expectedResult = "(var tempVar1:nat; (a!changeOp1(inVar_in, tempVar1!) then\n"
								+ "         a!changeOp2(tempVar1, outVar_out!) then\n"
								+ "         commVar_out! = tempVar1))";
		ST resultTemplate = templateProvider.createThenPostcondition(thenPostcondition);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}
	
	@Test
	public void operationPromotionsAreFirstInThenPostcondition(){
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(commVarIn), new Declarations());
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp2", new Declarations(commVarIn), new Declarations());
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);
		
		OperationPromotion func = postconditionMockProvider.buildPromotionMock("a", "func", new Declarations(inVar), new Declarations(commVarOut));
		OutputPromotion opPromo1 = new OutputPromotion(commVarOut, func, OutputPromotionContext.SCHEMA);
		
		allCommunicationVariables.add(commVar);
		thenPostconditions.add(Arrays.asList(opCall1));
		thenPostconditions.add(Arrays.asList(opCall2));
		communicationOutputPromotions.add(opPromo1);
		
		Map<Variable, Integer> outputIndicesMap = new TreeMap<Variable, Integer>();
		outputIndicesMap.put(commVarOut, 0);
		Mockito.when(thenPostcondition.getCommunicationOutputVariableIndices()).thenReturn(outputIndicesMap);
		
		String expectedResult = "(var tempVar1:nat; ((tempVar1! = a.func(inVar_in).commVar_out & a!changeOp1(tempVar1')) then\n" +
				                "         a!changeOp2(tempVar1)))";
		ST resultTemplate = templateProvider.createThenPostcondition(thenPostcondition);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}
	
	@Test
	public void sharedVariablesAreSubstitutedButOnceInThenPostcondition(){
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp2", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);
		
		sharedOutputVariables.add(outVar);
		thenPostconditions.add(Arrays.asList(opCall1));
		thenPostconditions.add(Arrays.asList(opCall2));
		
		Mockito.when(thenPostcondition.getNumberOfSharingPromotions(outVar)).thenReturn(2);

		String expectedResult = "(var tempVar1:nat; (a!changeOp1(inVar_in, outVar_out!) then\n" +
        						"         a!changeOp2(inVar_in, tempVar1!)))";
		ST resultTemplate = templateProvider.createThenPostcondition(thenPostcondition);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());

	}
	
	@Test
	public void shouldWorkWithoutSharedOrCommonOutputVariables(){
		OperationPromotion changeOp1 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp1", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall1 = new ChangeOperationCall(changeOp1);
		
		OperationPromotion changeOp2 = postconditionMockProvider.buildSchemaPromotionMock("a", "changeOp2", new Declarations(inVar), new Declarations());
		ChangeOperationCall opCall2 = new ChangeOperationCall(changeOp2);
		
		thenPostconditions.add(Arrays.asList(opCall1));
		thenPostconditions.add(Arrays.asList(opCall2));
		
		Mockito.when(thenPostcondition.getNumberOfSharingPromotions(outVar)).thenReturn(2);

		String expectedResult = "a!changeOp1(inVar_in, outVar_out!) then\n" +
        						"         a!changeOp2(inVar_in)";
		ST resultTemplate = templateProvider.createThenPostcondition(thenPostcondition);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void sequentialCompositionCombinesSimpleCalls(){
		ChangeOperationCall opCall1 = postconditionMockProvider.buildChangeOperationCall("a", "changeOp1", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall2 = postconditionMockProvider.buildChangeOperationCall("b", "changeOp2", new Declarations(inVar), new Declarations());
		
		List<IComposablePostconditions> postconditions = new ArrayList<IComposablePostconditions>();
		postconditions.add(new ChangeOperationCalls(opCall1));
		postconditions.add(new ChangeOperationCalls(opCall2));

		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(postconditions);
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations());
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(Declarations.empty()));
		
		String expectedResult =          "a!changeOp1(inVar_in, outVar_out!) then\n" +
        						"         b!changeOp2(inVar_in)";
		ST resultTemplate = templateProvider.createThenPostcondition(sequentialPostconditions);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void sequentialCompositionCombinesSimpleCallsWithOuterTempVars(){
		ChangeOperationCall opCall1 = postconditionMockProvider.buildChangeOperationCall("a", "changeOp1", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall2 = postconditionMockProvider.buildChangeOperationCall("b", "changeOp2", new Declarations(inVar), new Declarations());
		
		List<IComposablePostconditions> postconditions = new ArrayList<IComposablePostconditions>();
		postconditions.add(new ChangeOperationCalls(opCall1));
		postconditions.add(new ChangeOperationCalls(opCall2));

		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(postconditions);
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations());
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(Declarations.empty()));

		TemporaryVariableMap outerTempVarMap = new TemporaryVariableMap();
		outerTempVarMap.add(inVar);
		
		String expectedResult =          "a!changeOp1(tempVar1, outVar_out!) then\n" +
        						"         b!changeOp2(tempVar1)";
		ST resultTemplate = templateProvider.createThenPostcondition(sequentialPostconditions, outerTempVarMap);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void sequentialCompositionCombinesSimpleCallAndSimplePromotion(){
		OutputPromotion outputPromo1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func1", new Declarations(inVar), outVar);
		ChangeOperationCall opCall2  = postconditionMockProvider.buildChangeOperationCall("b", "changeOp2", new Declarations(inVar), new Declarations());
		
		List<IComposablePostconditions> postconditions = new ArrayList<IComposablePostconditions>();
		postconditions.add(new OutputPromotions(outputPromo1));
		postconditions.add(new ChangeOperationCalls(opCall2));
	
		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(postconditions);
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations());
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(Declarations.empty()));
		
		String expectedResult =  "outVar_out! = a.func1(inVar_in).outVar_out then\n" +
	    			    "         b!changeOp2(inVar_in)";
		ST resultTemplate = templateProvider.createThenPostcondition(sequentialPostconditions);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void sequentialCompositionCombinesSimpleCallsWithCommunication(){
		ChangeOperationCall opCall1 = postconditionMockProvider.buildChangeOperationCall("a", "changeOp1", new Declarations(inVar), new Declarations(commVarOut));
		ChangeOperationCall opCall2 = postconditionMockProvider.buildChangeOperationCall("b", "changeOp2", new Declarations(commVarIn), new Declarations());
		
		List<IComposablePostconditions> postconditions = new ArrayList<IComposablePostconditions>();
		postconditions.add(new ChangeOperationCalls(opCall1));
		postconditions.add(new ChangeOperationCalls(opCall2));

		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(postconditions);
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations(commVar));
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(new Declarations(commVar)));
		
		String expectedResult =  "(var tempVar1:nat; " +
									"(a!changeOp1(inVar_in, tempVar1!) then\n" +
        					"         b!changeOp2(tempVar1)))";
		ST resultTemplate = templateProvider.createThenPostcondition(sequentialPostconditions);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void sequentialCompositionCombinesSimpleCallsAndSimplePromotionsWithCommunication(){
		OutputPromotion outputPromo1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func1", new Declarations(inVar), commVarOut);
		ChangeOperationCall opCall2  = postconditionMockProvider.buildChangeOperationCall("b", "changeOp2", new Declarations(commVarIn), new Declarations());
		
		List<IComposablePostconditions> postconditions = new ArrayList<IComposablePostconditions>();
		postconditions.add(new OutputPromotions(outputPromo1));
		postconditions.add(new ChangeOperationCalls(opCall2));

		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(postconditions);
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations(commVar));
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(new Declarations(commVar)));
		
		String expectedResult =  "(var tempVar1:nat; " +
									"(tempVar1! = a.func1(inVar_in).commVar_out then\n" +
        					"         b!changeOp2(tempVar1)))";
		ST resultTemplate = templateProvider.createThenPostcondition(sequentialPostconditions);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}
	
	@Test
	public void sequentialCompositionCombinesSimpleCallsWithSharedOutput(){
		ChangeOperationCall opCall1 = postconditionMockProvider.buildChangeOperationCall("a", "changeOp1", new Declarations(inVar), new Declarations(outVar));
		ChangeOperationCall opCall2 = postconditionMockProvider.buildChangeOperationCall("b", "changeOp2", new Declarations(inVar), new Declarations(outVar));
		
		List<IComposablePostconditions> postconditions = new ArrayList<IComposablePostconditions>();
		postconditions.add(new ChangeOperationCalls(opCall1));
		postconditions.add(new ChangeOperationCalls(opCall2));

		SequentialPostconditions sequentialPostconditions = Mockito.mock(SequentialPostconditions.class);
		Mockito.when(sequentialPostconditions.getPostconditions()).thenReturn(postconditions);
		Mockito.when(sequentialPostconditions.getAllCommunicationVariables()).thenReturn(new Declarations());
		Mockito.when(sequentialPostconditions.getCommunicationVariables()).thenReturn(Arrays.asList(Declarations.empty()));

		
		String expectedResult =          "a!changeOp1(inVar_in, outVar_out!) then\n" +
        						"         b!changeOp2(inVar_in, outVar_out!)";
		ST resultTemplate = templateProvider.createThenPostcondition(sequentialPostconditions);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}
	
	@Test
	public void communicationOutputVariableIsSubstitutedInOutputPromotion(){
		String outerCommVarName = "outerCommVar";
		Variable outerCommVarOut = Variable.createOutputVariable(outerCommVarName, type);
		Variable outerCommVar    = Variable.createUndecoratedVariable(outerCommVarName, type);
		
		OutputPromotion promo1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func", new Declarations(inVar), commVarOut);
		OutputPromotion promo2 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func2", Declarations.empty(), outerCommVarOut);
		
		OperationPromotion changeOp = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp", new Declarations(commVarIn, inVar), new Declarations(outVar));
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		
		allCommunicationVariables.add(commVar);
		communicationOutputPromotions.add(promo1);
		communicationOutputPromotions.add(promo2);
		communicationChangeOperationCalls.add(opCall);
		
		String expectedResult = "(var tempVar2:nat; "
				                + "(tempVar2! = a.func(inVar_in).commVar_out & "
				                +  "tempVar1! = a.func2.outerCommVar_out & "
				                +  "b!changeOp(tempVar2', inVar_in, outVar_out!)))";
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(outerCommVar);
		
		ST resultTemplate = templateProvider.createVarPostcondition(mapping, tempVarMap);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void communicationInputVariableIsSubstitutedInCommOutputPromotion(){
		String outerCommVarName = "outerCommVar";
		Variable outerCommVarIn = Variable.createInputVariable(outerCommVarName, type);
		Variable outerCommVar   = Variable.createUndecoratedVariable(outerCommVarName, type);
		
		OutputPromotion promo1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func", new Declarations(outerCommVarIn), commVarOut);
		
		OperationPromotion changeOp = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp", new Declarations(commVarIn, inVar), new Declarations());
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		
		allCommunicationVariables.add(commVar);
		communicationOutputPromotions.add(promo1);
		communicationChangeOperationCalls.add(opCall);
		
		String expectedResult = "(var tempVar2:nat; "
				                + "(tempVar2! = a.func(tempVar1).commVar_out & "
				                +  "b!changeOp(tempVar2', inVar_in)))";
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(outerCommVar);
		
		ST resultTemplate = templateProvider.createVarPostcondition(mapping, tempVarMap);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void communicationInputVariableIsSubstitutedInOutputPromotion(){
		String outerCommVarName = "outerCommVar";
		Variable outerCommVarIn = Variable.createInputVariable(outerCommVarName, type);
		Variable outerCommVar   = Variable.createUndecoratedVariable(outerCommVarName, type);
		
		OutputPromotion promo1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func", new Declarations(outerCommVarIn), commVarOut);
		OutputPromotion promo2 = postconditionMockProvider.buildOutputPromotionMockInSchema("b", "func2", new Declarations(outerCommVarIn), outVar);
		
		OperationPromotion changeOp = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp", new Declarations(commVarIn, inVar), new Declarations());
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		
		allCommunicationVariables.add(commVar);
		communicationOutputPromotions.add(promo1);
		outputPromotions.add(promo2);
		communicationChangeOperationCalls.add(opCall);
		
		String expectedResult = "(var tempVar2:nat; "
				                + "(tempVar2! = a.func(tempVar1).commVar_out & "
				                +  "outVar_out! = b.func2(tempVar1).outVar_out & "
				                +  "b!changeOp(tempVar2', inVar_in)))";
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(outerCommVar);
		
		ST resultTemplate = templateProvider.createVarPostcondition(mapping, tempVarMap);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	@Test
	public void communicationInputVariableIsSubstitutedInChangeCall(){
		String outerCommVarName = "outerCommVar";
		Variable outerCommVarIn = Variable.createInputVariable(outerCommVarName, type);
		Variable outerCommVar   = Variable.createUndecoratedVariable(outerCommVarName, type);
		
		OutputPromotion promo1 = postconditionMockProvider.buildOutputPromotionMockInSchema("a", "func", new Declarations(outerCommVarIn), commVarOut);
		
		OperationPromotion changeOp = postconditionMockProvider.buildSchemaPromotionMock("b", "changeOp", new Declarations(commVarIn, outerCommVarIn), new Declarations());
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		
		allCommunicationVariables.add(commVar);
		communicationOutputPromotions.add(promo1);
		communicationChangeOperationCalls.add(opCall);
		
		String expectedResult = "(var tempVar2:nat; "
				                + "(tempVar2! = a.func(tempVar1).commVar_out & "
				                +  "b!changeOp(tempVar2', tempVar1)))";
		
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
		tempVarMap.add(outerCommVar);
		
		ST resultTemplate = templateProvider.createVarPostcondition(mapping, tempVarMap);
		
		Assert.assertEquals(expectedResult, resultTemplate.render());
	}

	
}
