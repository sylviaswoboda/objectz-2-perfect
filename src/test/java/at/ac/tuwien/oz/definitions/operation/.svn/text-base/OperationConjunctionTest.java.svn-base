package at.ac.tuwien.oz.definitions.operation;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.EmptyPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;


public class OperationConjunctionTest {

	@Mock private ICombinableOperation opPromo1;
	@Mock private ICombinableOperation opPromo2;
	
	@Mock private Declarations outputParameters1;
	@Mock private Declarations outputParameters2;

	@Mock private ICombinablePostconditions postconditions1;
	@Mock private ICombinablePostconditions postconditions2;

	@Mock private ICombinablePreconditions preconditions1;
	@Mock private ICombinablePreconditions preconditions2;
	@Mock private ICombinablePreconditions combinedPreconditions;
	
	@Mock private PostconditionFactory postconditionFactory;
	@Mock private PreconditionFactory  preconditionFactory;
	@Mock private ParseTreeProperty<ExpressionType> typeTree;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		PreconditionFactory.setInstance(preconditionFactory);
		PostconditionFactory.setInstance(postconditionFactory);
		Mockito.when(opPromo1.getOutputParameters()).thenReturn(outputParameters1);
		Mockito.when(opPromo2.getOutputParameters()).thenReturn(outputParameters2);
		Mockito.when(opPromo1.getCombinablePreconditions()).thenReturn(preconditions1);
		Mockito.when(opPromo2.getCombinablePreconditions()).thenReturn(preconditions2);
		Mockito.when(opPromo1.getCombinablePostconditions()).thenReturn(postconditions1);
		Mockito.when(opPromo2.getCombinablePostconditions()).thenReturn(postconditions2);
		
		Mockito.when(outputParameters1.getSharedDeclarations(outputParameters2)).thenReturn(Declarations.empty());
		Mockito.when(outputParameters2.getSharedDeclarations(outputParameters1)).thenReturn(Declarations.empty());
		
		Mockito.when(preconditionFactory.conjoin(opPromo1, opPromo2, Declarations.empty())).thenReturn(EmptyPreconditions.empty());
		Mockito.when(postconditionFactory.conjoin(postconditions1, postconditions2, Declarations.empty())).thenReturn(EmptyPostconditions.empty());
		
	}
	
	@After
	public void tearDown(){
		PreconditionFactory.setInstance(null);
		PostconditionFactory.setInstance(null);
	}

	@Test
	public void preconditionsAreConjoinedByFactory(){
		OperationConjunction conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		conjunction.createPreAndPostconditions(typeTree);
		
		Mockito.verify(preconditionFactory).conjoin(opPromo1, opPromo2, Declarations.empty());
	}
	
	@Test
	public void postconditionsAreConjoinedByFactory(){
		OperationConjunction conjunction = new OperationConjunction(opPromo1, opPromo2);
		conjunction.createPreAndPostconditions(typeTree);
		
		Mockito.verify(postconditionFactory).conjoin(postconditions1, postconditions2, Declarations.empty());
	}

	@Test
	public void twoBoolFunctionsYieldsBoolFunction(){
		Mockito.when(opPromo1.isBoolFunction()).thenReturn(true);
		Mockito.when(opPromo2.isBoolFunction()).thenReturn(true);
		
		ICombinableOperation conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		Assert.assertTrue(conjunction.isBoolFunction());
		Assert.assertFalse(conjunction.isFunction());
		Assert.assertFalse(conjunction.isChangeOperation());
	}
	
	@Test
	public void twoFunctionsYieldsFunction(){
		Mockito.when(opPromo1.isFunction()).thenReturn(true);
		Mockito.when(opPromo2.isFunction()).thenReturn(true);
		
		ICombinableOperation conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		Assert.assertTrue(conjunction.isFunction());
		Assert.assertFalse(conjunction.isBoolFunction());
		Assert.assertFalse(conjunction.isChangeOperation());
	}

	@Test
	public void boolFunctionAndFunctionsYieldsFunction(){
		Mockito.when(opPromo1.isBoolFunction()).thenReturn(true);
		Mockito.when(opPromo2.isFunction()).thenReturn(true);
		
		ICombinableOperation conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		Assert.assertTrue(conjunction.isFunction());
		Assert.assertFalse(conjunction.isBoolFunction());
		Assert.assertFalse(conjunction.isChangeOperation());
	}

	@Test
	public void functionAndBoolFunctionsYieldsFunction(){
		Mockito.when(opPromo1.isFunction()).thenReturn(true);
		Mockito.when(opPromo2.isBoolFunction()).thenReturn(true);
		
		ICombinableOperation conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		Assert.assertTrue(conjunction.isFunction());
		Assert.assertFalse(conjunction.isBoolFunction());
		Assert.assertFalse(conjunction.isChangeOperation());
	}

	@Test
	public void changeOpAndBoolFunctionsYieldsChangeOp(){
		Mockito.when(opPromo1.isChangeOperation()).thenReturn(true);
		Mockito.when(opPromo2.isBoolFunction()).thenReturn(true);
		
		ICombinableOperation conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		Assert.assertTrue(conjunction.isChangeOperation());
		Assert.assertFalse(conjunction.isFunction());
		Assert.assertFalse(conjunction.isBoolFunction());
	}

	@Test
	public void changeOpAndFunctionsYieldsChangeOp(){
		Mockito.when(opPromo1.isChangeOperation()).thenReturn(true);
		Mockito.when(opPromo2.isFunction()).thenReturn(true);
		
		ICombinableOperation conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		Assert.assertTrue(conjunction.isChangeOperation());
		Assert.assertFalse(conjunction.isFunction());
		Assert.assertFalse(conjunction.isBoolFunction());
	}

	@Test
	public void twoChangeOpsYieldsChangeOp(){
		Mockito.when(opPromo1.isChangeOperation()).thenReturn(true);
		Mockito.when(opPromo2.isChangeOperation()).thenReturn(true);
		
		ICombinableOperation conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		Assert.assertTrue(conjunction.isChangeOperation());
		Assert.assertFalse(conjunction.isFunction());
		Assert.assertFalse(conjunction.isBoolFunction());
	}

	@Test
	public void boolFunctionAndChangeOpYieldsChangeOp(){
		Mockito.when(opPromo1.isBoolFunction()).thenReturn(true);
		Mockito.when(opPromo2.isChangeOperation()).thenReturn(true);
		
		ICombinableOperation conjunction = new OperationConjunction(opPromo1, opPromo2);
		
		Assert.assertTrue(conjunction.isChangeOperation());
		Assert.assertFalse(conjunction.isFunction());
		Assert.assertFalse(conjunction.isBoolFunction());
	}
	
	@Test
	public void deltalistIsEmpty(){
		CombinedOperation conjunction = new OperationConjunction(opPromo1, opPromo2);

		Assert.assertEquals(0, conjunction.getDeltalist().size());
	}

}
