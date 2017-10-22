package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.OrderablePostcondition;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;


public class OperationOrderingCalculatorTest {

	@Mock private ChangeOperationCall postcondition1;
	@Mock private ChangeOperationCall postcondition2;
	@Mock private ChangeOperationCall postcondition3;
	@Mock private ChangeOperationCall postcondition4;

	@Mock private OperationPromotion operation1;
	@Mock private OperationPromotion operation2;
	@Mock private OperationPromotion operation3;
	@Mock private OperationPromotion operation4;
	
	private Ident a1;
	private Ident a2;
	private Ident a;
	private Ident b;
	private Ident c;
	private Ident d;
	private Ident e;
	
	private Idents changedStateVarsOp1;
	private Idents changedStateVarsOp2;
	private Idents changedStateVarsOp3;
	private Idents changedStateVarsOp4;
	
	private Idents usedStateVarsOp1;
	private Idents usedStateVarsOp2;
	private Idents usedStateVarsOp3;
	private Idents usedStateVarsOp4;
	
	private List<ChangeOperationCall> calls;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		Mockito.when(postcondition1.getOrderableOperation()).thenReturn(operation1);
		Mockito.when(postcondition2.getOrderableOperation()).thenReturn(operation2);
		Mockito.when(postcondition3.getOrderableOperation()).thenReturn(operation3);
		Mockito.when(postcondition4.getOrderableOperation()).thenReturn(operation4);
		
		a1 = new Ident("a");
		a2 = new Ident("a");
		a = new Ident("a");
		b = new Ident("b");
		c = new Ident("c");
		d = new Ident("d");
		e = new Ident("e");
		
		changedStateVarsOp1 = new Idents();
		changedStateVarsOp2 = new Idents();
		changedStateVarsOp3 = new Idents();
		changedStateVarsOp4 = new Idents();
		
		usedStateVarsOp1 = new Idents();
		usedStateVarsOp2 = new Idents();
		usedStateVarsOp3 = new Idents();
		usedStateVarsOp4 = new Idents();
		
		Mockito.when(operation1.getChangedStateVariables()).thenReturn(changedStateVarsOp1);
		Mockito.when(operation2.getChangedStateVariables()).thenReturn(changedStateVarsOp2);
		Mockito.when(operation3.getChangedStateVariables()).thenReturn(changedStateVarsOp3);
		Mockito.when(operation4.getChangedStateVariables()).thenReturn(changedStateVarsOp4);
		
		Mockito.when(operation1.getUsedStateVariables()).thenReturn(usedStateVarsOp1);
		Mockito.when(operation2.getUsedStateVariables()).thenReturn(usedStateVarsOp2);
		Mockito.when(operation3.getUsedStateVariables()).thenReturn(usedStateVarsOp3);
		Mockito.when(operation4.getUsedStateVariables()).thenReturn(usedStateVarsOp4);
		
		calls = new ArrayList<ChangeOperationCall>();
	}
	
	@Test
	public void changedStateVarsIntersect(){
		changedStateVarsOp1.add(a1);
		changedStateVarsOp1.add(b);
		
		changedStateVarsOp2.add(a2);
		changedStateVarsOp2.add(c);
		
		calls.add(postcondition1);
		calls.add(postcondition2);
		
		PostconditionOrderingCalculator<ChangeOperationCall> calculator = new PostconditionOrderingCalculator<ChangeOperationCall>();
		try{
			calculator.order(calls);
			Assert.fail("Deltalists should be detected as intersecting");
		} catch(ObjectZToPerfectTranslationException ex){
			Assert.assertEquals("Deltalists intersect. Translation not possible.", ex.getMessage());
		}
	}

	
	@Test
	public void circularDependency(){
		changedStateVarsOp1.add(b);
		changedStateVarsOp2.add(c);
		
		usedStateVarsOp1.add(c);
		usedStateVarsOp2.add(b);
		
		calls.add(postcondition1);
		calls.add(postcondition2);

		PostconditionOrderingCalculator<ChangeOperationCall> calculator = new PostconditionOrderingCalculator<ChangeOperationCall>();
		try{
			calculator.order(calls);
			Assert.fail("Circular dependency no ordering possible.");
		} catch(ObjectZToPerfectTranslationException ex){
			Assert.assertEquals("Cannot calculate ordering between operations. Translation not possible.", ex.getMessage());
		}
	}
	
	@Test
	public void switchGivenOrdering(){
		changedStateVarsOp1.add(b);
		changedStateVarsOp2.add(c);
		
		usedStateVarsOp2.add(b);
		
		calls.add(postcondition1);
		calls.add(postcondition2);
		
		PostconditionOrderingCalculator<ChangeOperationCall> calculator = new PostconditionOrderingCalculator<ChangeOperationCall>();
		
		List<? extends OrderablePostcondition>	order = calculator.order(calls);
		
		Assert.assertEquals(2, order.size());
		Assert.assertEquals(postcondition2, order.get(0));
		Assert.assertEquals(postcondition1, order.get(1));
	}
	
	@Test
	public void givenOrderIsKept(){
		changedStateVarsOp1.add(b);
		changedStateVarsOp2.add(c);
		
		usedStateVarsOp1.add(c);
		
		calls.add(postcondition1);
		calls.add(postcondition2);
		
		PostconditionOrderingCalculator<ChangeOperationCall> calculator = new PostconditionOrderingCalculator<ChangeOperationCall>();
		
		List<? extends OrderablePostcondition>	order = calculator.order(calls);
		
		Assert.assertEquals(2, order.size());
		Assert.assertEquals(postcondition1, order.get(0));
		Assert.assertEquals(postcondition2, order.get(1));
	}

	@Test
	public void orderSeveralPostconditions(){
		changedStateVarsOp1.add(a);
		changedStateVarsOp2.add(b);
		changedStateVarsOp3.add(c);
		changedStateVarsOp4.add(d);
		
		usedStateVarsOp1.add(a);
		usedStateVarsOp1.add(b);
		usedStateVarsOp2.add(b);
		usedStateVarsOp2.add(c);
		usedStateVarsOp2.add(d);
		usedStateVarsOp3.add(c);
		usedStateVarsOp3.add(e);
		usedStateVarsOp4.add(d);
		usedStateVarsOp4.add(e);
		
		calls.add(postcondition4);
		calls.add(postcondition3);
		calls.add(postcondition2);
		calls.add(postcondition1);
		
		PostconditionOrderingCalculator<ChangeOperationCall> calculator = new PostconditionOrderingCalculator<ChangeOperationCall>();
		
		List<? extends OrderablePostcondition>	order = calculator.order(calls);
		
		Assert.assertEquals(4, order.size());
		Assert.assertEquals(postcondition1, order.get(0));
		Assert.assertEquals(postcondition2, order.get(1));
		Assert.assertEquals(postcondition4, order.get(2));
		Assert.assertEquals(postcondition3, order.get(3));
	}

}
