package at.ac.tuwien.oz.datatypes.postconditions;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.AssertCollection;
import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class PostconditionFactoryTest {

	protected static PostconditionFactory compositionFactory;
	protected static ExpressionType type;

	@BeforeClass
	public static void setupClass() {
		compositionFactory = PostconditionFactory.getInstance();
		type = Mockito.mock(ExpressionType.class);

	}

	protected Declarations sharedVariables;
	protected Declarations communicationVariables;
	protected Variable commVar1In;
	protected Variable commVar1Out;
	protected Variable commVar1;
	protected Variable commVar2In;
	protected Variable commVar2Out;
	protected Variable commVar2;
	protected Variable commVar3In;
	protected Variable commVar3Out;
	protected Variable commVar3;
	protected Variable commVar4;
	protected Variable commVar4In;
	protected Variable commVar4Out;
	protected Variable outVar;
	protected Variable outVar1;
	protected Variable outVar2;
	protected Variable outVar3;
	protected Variable outVar4;
	protected Variable outVar5;
	protected Variable sharedOutput;
	
	protected EmptyPostconditions emptyPostconditions;

	private ICombinablePostconditions left = Mockito.mock(ICombinablePostconditions.class);
	private ICombinablePostconditions right = Mockito.mock(ICombinablePostconditions.class);
	
	private Declarations newSharedOutputVariables = Mockito.mock(Declarations.class);
	private Declarations newCommunicationVariables = Mockito.mock(Declarations.class);
	
	@Test
	public void compose(){
		ICombinablePostconditions result = compositionFactory.compose(left, right, newCommunicationVariables, newSharedOutputVariables, false);
		
		verifyResultIsEmpty(result);
	}

	@Test
	public void composeAssociative(){
		ICombinablePostconditions result = compositionFactory.compose(left, right, newCommunicationVariables, newSharedOutputVariables, true);
		
		verifyResultIsEmpty(result);
	}

	@Test
	public void conjoin(){
		ICombinablePostconditions result = compositionFactory.conjoin(left, right, newSharedOutputVariables);
		
		verifyResultIsEmpty(result);
	}

	private void verifyResultIsEmpty(ICombinablePostconditions result) {
		AssertCollection.assertHasElements(result.getSharedOutputVariables().asList());
		AssertCollection.assertHasElements(result.getAllCommunicationVariables().asList());
		AssertCollection.assertHasElements(result.getVisibleCommunicationVariables().asList());
		
		AssertCollection.assertHasElements(result.getSimplePromotions().getElements());
		AssertCollection.assertHasElements(result.getCommunicatingPromotions().getElements());
		
		AssertCollection.assertHasElements(result.getSimpleCalls().asList());
		AssertCollection.assertHasElements(result.getCommunicatingCalls().asList());
		AssertCollection.assertHasElements(result.getUncategorizedCalls().asList());
	}

	
	protected OutputPromotion getOutputPromotion(Variable outputVariable, List<Variable> inputVariables) {
		OperationPromotion operationPromotionMock = Mockito.mock(OperationPromotion.class);
		
		Mockito.doReturn(true).when(operationPromotionMock)
				.hasInputVariables(Matchers.argThat(new DeclarationMatcher(inputVariables)));
		for (Variable inputVariable: inputVariables){
			Mockito.when(operationPromotionMock.hasInputVariable(inputVariable)).thenReturn(true);
		}

		OutputPromotion realOutputPromotion = new OutputPromotion(outputVariable, operationPromotionMock,
				OutputPromotionContext.FUNCTION);
		return realOutputPromotion;
	}
	
	protected OutputPromotion getOutputPromotion(String caller, Variable outputVariable, List<Variable> inputVariables) {
		OperationPromotion operationPromotion = Mockito.mock(OperationPromotion.class);

		ST callerTemplate = PerfectTemplateProvider.getInstance().getId(new Ident(caller));
		Mockito.when(operationPromotion.getCaller()).thenReturn(callerTemplate);

		Mockito.doReturn(true).when(operationPromotion)
				.hasInputVariables(Matchers.argThat(new DeclarationMatcher(inputVariables)));
		
		for (Variable inputVariable: inputVariables){
			Mockito.when(operationPromotion.hasInputVariable(inputVariable)).thenReturn(true);
		}

		OutputPromotion realOutputPromotion = new OutputPromotion(outputVariable, operationPromotion,
				OutputPromotionContext.FUNCTION);
		return realOutputPromotion;
	}

	protected ChangeOperationCall getChangeOperationCall(List<Variable> outputVariables, List<Variable> inputVariables) {
		OperationPromotion operationPromotion = Mockito.mock(OperationPromotion.class);

		Mockito.doReturn(true).when(operationPromotion)
			.hasInputVariables(Matchers.argThat(new DeclarationMatcher(inputVariables)));
		
		for (Variable outputVariable: outputVariables){
			Mockito.when(operationPromotion.hasOutputVariable(outputVariable)).thenReturn(true);
			Mockito.when(operationPromotion.hasOutputVariable(Mockito.eq(outputVariable), Mockito.anyBoolean())).thenReturn(true);
		}
		
		ChangeOperationCall changeOperationCall = new ChangeOperationCall(operationPromotion);
		
		return changeOperationCall;
	}

	protected ChangeOperationCall getChangeOperationCall(String caller, List<Variable> outputVariables, List<Variable> inputVariables) {
		OperationPromotion operationPromotion = Mockito.mock(OperationPromotion.class);
		ST callerTemplate = PerfectTemplateProvider.getInstance().getId(new Ident(caller));
		Mockito.when(operationPromotion.getCaller()).thenReturn(callerTemplate);

		Mockito.doReturn(true).when(operationPromotion)
			.hasInputVariables(Matchers.argThat(new DeclarationMatcher(inputVariables)));
		
		for (Variable outputVariable: outputVariables){
			Mockito.when(operationPromotion.hasOutputVariable(outputVariable)).thenReturn(true);
			Mockito.when(operationPromotion.hasOutputVariable(Mockito.eq(outputVariable), Mockito.anyBoolean())).thenReturn(true);
		}
		
		ChangeOperationCall changeOperationCall = new ChangeOperationCall(operationPromotion);
		
		return changeOperationCall;
	}

	protected ChangeOperationCall getChangeOperationCall(String caller, List<Variable> outputVariables, List<Variable> inputVariables, List<Ident> changedStateVars, List<Ident> usedStateVars) {
		return getChangeOperationCall(caller, null, outputVariables, inputVariables, changedStateVars,
				usedStateVars);
	}

	protected ChangeOperationCall getChangeOperationCall(String caller, String name, List<Variable> outputVariables, List<Variable> inputVariables, List<Ident> changedStateVars, List<Ident> usedStateVars) {
		OperationPromotion operationPromotion = Mockito.mock(OperationPromotion.class);
		ST callerTemplate = PerfectTemplateProvider.getInstance().getId(new Ident(caller));
		Mockito.when(operationPromotion.getCaller()).thenReturn(callerTemplate);
		Mockito.when(operationPromotion.toString()).thenReturn(caller + "." + name);

		Mockito.doReturn(true).when(operationPromotion)
			.hasInputVariables(Matchers.argThat(new DeclarationMatcher(inputVariables)));
		
		for (Variable inputVar: inputVariables){
			Mockito.when(operationPromotion.hasInputVariable(inputVar)).thenReturn(true);
		}
		for (Variable outputVariable: outputVariables){
			Mockito.when(operationPromotion.hasOutputVariable(outputVariable)).thenReturn(true);
			Mockito.when(operationPromotion.hasOutputVariable(Mockito.eq(outputVariable), Mockito.anyBoolean())).thenReturn(true);
		}

		
		Idents changedStateVarIdents = new Idents();
		changedStateVarIdents.addAll(changedStateVars);
		
		Idents usedStateVarIdents = new Idents();
		usedStateVarIdents.addAll(usedStateVars);
		
		Mockito.doReturn(changedStateVarIdents).when(operationPromotion).getChangedStateVariables();
		Mockito.doReturn(usedStateVarIdents).when(operationPromotion).getUsedStateVariables();
		
		
		
		ChangeOperationCall changeOperationCall = new ChangeOperationCall(operationPromotion);
		
		return changeOperationCall;
	}
	
	protected void initVariables() {
		this.sharedVariables = new Declarations();
		this.communicationVariables = new Declarations();

		this.commVar1In = Variable.createInputVariable("commVar1", type);
		this.commVar1Out = Variable.createOutputVariable("commVar1", type);
		this.commVar1 = Variable.createUndecoratedVariable("commVar1", type);

		this.commVar2In = Variable.createInputVariable("commVar2", type);
		this.commVar2Out = Variable.createOutputVariable("commVar2", type);
		this.commVar2 = Variable.createUndecoratedVariable("commVar2", type);

		this.commVar3In = Variable.createInputVariable("commVar3", type);
		this.commVar3Out = Variable.createOutputVariable("commVar3", type);
		this.commVar3 = Variable.createUndecoratedVariable("commVar3", type);

		this.commVar4In = Variable.createInputVariable("commVar4", type);
		this.commVar4Out = Variable.createOutputVariable("commVar4", type);
		this.commVar4 = Variable.createUndecoratedVariable("commVar4", type);

		this.sharedOutput = Variable.createOutputVariable("sharedOutput", type);

		this.outVar = Variable.createOutputVariable("outVar", type);
		this.outVar1 = Variable.createOutputVariable("outVar1", type);
		this.outVar2 = Variable.createOutputVariable("outVar2", type);
		this.outVar3 = Variable.createOutputVariable("outVar3", type);
		this.outVar4 = Variable.createOutputVariable("outVar4", type);
		this.outVar5 = Variable.createOutputVariable("outVar5", type);

		this.emptyPostconditions = EmptyPostconditions.empty();
	}
	
}

class DeclarationMatcher extends ArgumentMatcher<Declarations> {
	List<Variable> referenceItems;

	DeclarationMatcher(List<Variable> referenceItems) {
		this.referenceItems = referenceItems;
	}

	@Override
	public boolean matches(Object argument) {
		if (referenceItems.size() == 0) {
			return false;
		}
		if (argument instanceof Declarations) {
			Declarations declarations = (Declarations) argument;
			// if (declarations.size() != referenceItems.size()){
			// return false;
			// }
			for (Variable v : referenceItems) {
				if (!declarations.contains(v)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
