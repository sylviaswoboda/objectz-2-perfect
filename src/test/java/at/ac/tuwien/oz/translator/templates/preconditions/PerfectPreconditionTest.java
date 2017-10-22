package at.ac.tuwien.oz.translator.templates.preconditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.CommunicationPreconditionMapping;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.PreconditionFunctionCalls;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.PerfectPreconditionTemplateProvider;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class PerfectPreconditionTest {

	private PerfectPredicateTemplateProvider predicateTemplateProvider;
	
	private static final ExpressionType NAT = ExpressionType.getNat();

	private PreconditionFunctionCalls communicatingPreconditions;
	private Declarations communicationVariables;
	private Declarations sharedVariables;
	private Map<Variable, List<IPromotedOperation>> allCommunicatingOrSharingOperations;

	@Mock private ParseTreeProperty<ST> templateTree; 

	
	public PerfectPreconditionTest() {
	}

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		predicateTemplateProvider = PerfectPreconditionTemplateProvider.getInstance();

		communicatingPreconditions = new PreconditionFunctionCalls();
		communicationVariables = new Declarations();
		sharedVariables = new Declarations();
		allCommunicatingOrSharingOperations = new HashMap<Variable, List<IPromotedOperation>>();
		
		TempVarProvider.resetNameCounter();
	}

	@Test
	public void testCreatePreconditionWithCommunication_NoSharedOutput(){
		Variable commVar1 = Variable.createUndecoratedVariable("commVar1", NAT);

		withPreconditionFunctionCall(null, "func2", new Declarations(commVar1.getInputCopy()));
		withCommunicationVariables(commVar1);
		
		IPromotedOperation func1 = getPromotedOperationMock(null, "func1", new Declarations(), new Declarations(commVar1.getOutputCopy()));
		withPromotingOperations(commVar1, func1);
		
		CommunicationPreconditionMapping communicationPreconditionMapping = 
				new CommunicationPreconditionMapping(
						communicatingPreconditions,
						sharedVariables, 
						communicationVariables, 
						allCommunicatingOrSharingOperations);
		
		ST communicationPrecondition = predicateTemplateProvider.createExistsPrecondition(communicationPreconditionMapping);
		
		String expectedResult = "(exists tempVar1:nat :- " +
									"(tempVar1 = func1.commVar1_out & " +
									"func2_prec(tempVar1)))";
		
		Assert.assertEquals(expectedResult, communicationPrecondition.render());
	}

	@Test
	public void testCreatePreconditionWithCommunication_WithSharedOutput(){
		Variable sharedOutputVariable = Variable.createOutputVariable("sharedOutputVar", NAT);
		Variable commVar1 = Variable.createUndecoratedVariable("commVar1", NAT);
		
		withPreconditionFunctionCall(null, "func2", new Declarations(commVar1.getInputCopy()));
		withCommunicationVariables(commVar1);
		withSharedVariables(sharedOutputVariable);
		
		IPromotedOperation func1 = getPromotedOperationMock(null, "func1", new Declarations(), new Declarations(commVar1.getOutputCopy(), sharedOutputVariable));
		IPromotedOperation func2 = getPromotedOperationMock(null, "func2", new Declarations(commVar1.getInputCopy()), new Declarations(sharedOutputVariable));
		withPromotingOperations(commVar1, func1);
		withPromotingOperations(sharedOutputVariable, func1, func2);
		
		CommunicationPreconditionMapping communicationPreconditionMapping = 
				new CommunicationPreconditionMapping(
						communicatingPreconditions,
						sharedVariables, 
						communicationVariables, 
						allCommunicatingOrSharingOperations);
		
		ST communicationPrecondition = predicateTemplateProvider.createExistsPrecondition(communicationPreconditionMapping);
		
		String expectedResult = "(exists tempVar1:nat, tempVar2:nat :- " +
									"(tempVar1 = func1.commVar1_out & " +
									 "tempVar2 = func1.sharedOutputVar_out & " +
									 "tempVar2 = func2(tempVar1).sharedOutputVar_out & " +
									 "func2_prec(tempVar1)))";
		
		Assert.assertEquals(expectedResult, communicationPrecondition.render());
	}

	private void withCommunicationVariables(Variable... commVars) {
		this.communicationVariables.addNew(commVars);
	}
	
	private void withSharedVariables(Variable... sharedVars) {
		this.sharedVariables.addNew(sharedVars);
	}

	private void withPromotingOperations(Variable variable, IPromotedOperation... promotedOperations){
		allCommunicatingOrSharingOperations.put(variable, Arrays.asList(promotedOperations));
	}

	private IPromotedOperation getPromotedOperationMock(ST caller, String name, Declarations inputVariables,
			Declarations outputVariables) {
		IOperation promotedOperation = Mockito.mock(IOperation.class);
		Mockito.when(promotedOperation.getPostconditionFunctionName()).thenReturn(name);

		IPromotedOperation promotedOperationMock = Mockito.mock(IPromotedOperation.class);
		Mockito.when(promotedOperationMock.getCaller()).thenReturn(caller);
		Mockito.when(promotedOperationMock.getInputParameters()).thenReturn(inputVariables);
		Mockito.when(promotedOperationMock.getOutputParameters()).thenReturn(inputVariables);
		Mockito.when(promotedOperationMock.getPromotedOperation()).thenReturn(promotedOperation);
		
		return promotedOperationMock;
	}

	private void withPreconditionFunctionCall(ST caller, String name, Declarations inputVariables) {
		Declarations inputParameters = inputVariables.getInputCopies();
	
		PreconditionFunction preconditionFunctionMock = Mockito.mock(PreconditionFunction.class);
		Mockito.when(preconditionFunctionMock.getName()).thenReturn(name + "_prec");
		Mockito.when(preconditionFunctionMock.getInputParameters()).thenReturn(inputParameters);
		
		for(Variable v: inputParameters.asList()){
			Mockito.when(preconditionFunctionMock.hasInputVariable(v)).thenReturn(true);
		}
		
		PreconditionFunctionCall preconditionFunctionCall = new PreconditionFunctionCall(caller, preconditionFunctionMock);
		this.communicatingPreconditions.add(preconditionFunctionCall);
	}
}
