package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.AssertCollection;
import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;

public class CompositePostconditionDataEliminatorTest {

	private CompositePostconditionDataCollector dataEliminator;
	private Declarations eliminationVariables;
	
	private Variable outputVar1;
	private Variable outputVar2;
	private Variable commVar1;
	private Variable commVar2;
	
	
	@Mock private ICombinablePostconditions postconditions;
	
	private OutputPromotion opPromoVar1;
	private OutputPromotion opPromoVar2;
	private OutputPromotion opPromoCommVar1;
	
	@Mock private ExpressionType type;
	@Mock private OperationPromotion op1;
	@Mock private OperationPromotion op2;
	@Mock private OperationPromotion op3;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		this.eliminationVariables = new Declarations();
		this.outputVar1 = Variable.createOutputVariable("outputVar1", type);
		this.outputVar2 = Variable.createOutputVariable("outputVar2", type);
		this.commVar1 = Variable.createUndecoratedVariable("commVar1", type);
		this.commVar2 = Variable.createUndecoratedVariable("commVar2", type);
		
		this.opPromoVar1 = new OutputPromotion(outputVar1, op1, OutputPromotionContext.FUNCTION);
		this.opPromoVar2 = new OutputPromotion(outputVar2, op2, OutputPromotionContext.FUNCTION);
		this.opPromoCommVar1 = new OutputPromotion(commVar1, op3, OutputPromotionContext.FUNCTION);
	}
	
	// (a.func1 && b.func2) 0/9 c.func3
	// (result.outVar1 = a.func1.outVar1 &
	//  result.outVar2 = b.func2.outVar2) then
	//  result.outVar1 = c.func3.outVar1
	@Test
	public void eliminateRemovesVariableInSharedVariables() {
		Mockito.when(postconditions.getSharedOutputVariables()).thenReturn(new Declarations(outputVar1, outputVar2));
		
		this.eliminationVariables.add(outputVar1);
		this.dataEliminator = new CompositePostconditionDataCollector(postconditions);
		this.dataEliminator.eliminateOutputVariable(eliminationVariables);
		
		AssertCollection.assertHasElements(this.dataEliminator.getSharedOutputVariables().asList(), outputVar2);
	}
	@Test
	public void eliminateRemovesVariableInSimplePromotions() {
		Mockito.when(postconditions.getSimplePromotions()).thenReturn(new OutputPromotions(opPromoVar1, opPromoVar2));
		
		this.eliminationVariables.add(outputVar1);
		this.dataEliminator = new CompositePostconditionDataCollector(postconditions);
		this.dataEliminator.eliminateOutputVariable(eliminationVariables);
		
		AssertCollection.assertHasElements(this.dataEliminator.getSimplePromotions().getElements(), opPromoVar2);
	}

	// a.func1 || b.func2 || c.func3
	//  result.outVar1  = a.func1.outVar1 &
	//  result.commVar1 = a.func1.commVar1 &
	//  result.outVar2  = b.func2(commVar1).outVar2 
	//  result.commVar2 = b.func2(commVar1).commVar2
	//  result.outVar3  = c.func3(commVar2).outVar3
	@Test
	public void eliminateDoesNotRemoveVariableInAllCommunicationVariables() {
		Mockito.when(postconditions.getAllCommunicationVariables()).thenReturn(new Declarations(commVar1, commVar2));
		
		this.eliminationVariables.addNew(commVar1, commVar2.getOutputCopy());
		this.dataEliminator = new CompositePostconditionDataCollector(postconditions);
		this.dataEliminator.eliminateOutputVariable(eliminationVariables);
		
		AssertCollection.assertHasElements(this.dataEliminator.getCommunicationVariables().asList(), commVar1, commVar2);
	}

	@Test
	public void eliminateRemovesVariableInVisibleCommunicationVariables() {
		Mockito.when(postconditions.getVisibleCommunicationVariables()).thenReturn(new Declarations(commVar1, commVar2));
		
		this.eliminationVariables.addNew(commVar2);
		this.dataEliminator = new CompositePostconditionDataCollector(postconditions);
		this.dataEliminator.eliminateOutputVariable(eliminationVariables);
		
		AssertCollection.assertHasElements(this.dataEliminator.getVisibleCommunicationVariables().asList(), commVar1);
	}

	@Test
	public void eliminateDoesNotRemovesVariableInCommunicatingPromotions() {
		Mockito.when(postconditions.getCommunicatingPromotions()).thenReturn(new OutputPromotions(opPromoCommVar1, opPromoVar1));
		
		this.eliminationVariables.add(outputVar1);
		this.dataEliminator = new CompositePostconditionDataCollector(postconditions);
		this.dataEliminator.eliminateOutputVariable(eliminationVariables);
		
		AssertCollection.assertHasElements(this.dataEliminator.getCommunicatingPromotions().getElements(), opPromoCommVar1);
	}

}
