package at.ac.tuwien.oz.definitions.operation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;

public class SimpleBoolFunctionTest {
	
	@Mock private Axiom preconditionAxiom;
	@Mock private Axiom postconditionAxiom;

	@Mock private AxiomReference precondition;
	@Mock private AxiomReference postcondition;
	
	@Mock private PreconditionFactory preconditionFactoryMock;
	
	private AxiomReferences predicates;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		Mockito.when(precondition.isPrecondition()).thenReturn(true);
		Mockito.when(postcondition.isPostcondition()).thenReturn(true);
		
		Mockito.when(precondition.getAxiom()).thenReturn(preconditionAxiom);
		Mockito.when(postcondition.getAxiom()).thenReturn(postconditionAxiom);
		
		predicates = new AxiomReferences();
		predicates.add(precondition);
		predicates.add(postcondition);
		
		PreconditionFactory.setInstance(preconditionFactoryMock);

	}
	
	@After
	public void tearDown(){
		PreconditionFactory.setInstance(null);
	}
	
	@Test
	public void operationIsAlwaysBoolFunction(){
		IOperation operation = new SimpleBoolFunction(AbstractOperationTest.IRRELEVANT_NAME, null, (AxiomReferences)null, null, null);
		
		Assert.assertTrue(operation.isBoolFunction());
		Assert.assertFalse(operation.isFunction());
		Assert.assertFalse(operation.isChangeOperation());
	}
	
	@Test
	public void outputParametersAreAlwaysEmptyList(){
		IOperation operation = new SimpleBoolFunction(AbstractOperationTest.IRRELEVANT_NAME, null, (AxiomReferences)null, null, null);
		
		Declarations outputParameters = operation.getOutputParameters();
		
		Assert.assertTrue(outputParameters.isEmpty());
	}

	@Test
	public void deltalistIsAlwaysEmptyList(){
		IOperation operation = new SimpleBoolFunction(AbstractOperationTest.IRRELEVANT_NAME, null, (AxiomReferences)null, null, null);
		
		List<String> deltalist = operation.getDeltalist();
		
		Assert.assertTrue(deltalist.isEmpty());
	}

	@Test
	public void auxiliaryParametersAreAlwaysEmptyList(){
		IOperation operation = new SimpleBoolFunction(AbstractOperationTest.IRRELEVANT_NAME, null, (AxiomReferences)null, null, null);
		
		Declarations auxiliaryParameters = operation.getAuxiliaryParameters();
		
		Assert.assertTrue(auxiliaryParameters.isEmpty());
	}

	@Test
	public void postconditionsAreEmptyPostconditions(){
		IOperation operation = new SimpleBoolFunction(AbstractOperationTest.IRRELEVANT_NAME, null, predicates, null, null);
		operation.createPreAndPostconditions(null);
		
		IPostconditions postconditions = operation.getPostconditions();
		Assert.assertNotNull(postconditions);
		assertEquals(EmptyPostconditions.class, postconditions.getClass());
	}

	@Test
	public void preconditionsAreSimplePreconditions(){
		IOperation operation = new SimpleBoolFunction(AbstractOperationTest.IRRELEVANT_NAME, null, predicates, null, null);
		operation.createPreAndPostconditions(null);
		
		IPreconditions preconditions = operation.getPreconditions();
		Assert.assertNotNull(preconditions);
		assertEquals(SimplePreconditions.class, preconditions.getClass());
		
		Mockito.verify(preconditionFactoryMock, Mockito.times(1)).createSimplePrecondition(preconditionAxiom);
	}
	
	@Test
	public void boolFunctionHasPreconditionFunctionWithSameName(){
		IOperation operation = new SimpleBoolFunction(AbstractOperationTest.IRRELEVANT_NAME, null, predicates, null, null);
		operation.createPreAndPostconditions(null);
		
		PreconditionFunction preconditionFunction = operation.getPreconditionFunction();
		
		Assert.assertNotNull(preconditionFunction);
		assertEquals(AbstractOperationTest.IRRELEVANT_NAME, preconditionFunction.getName());
	}
	
	@Test
	public void boolFunctionDelegatesCreationOfPreconditionsToFactory(){
		IOperation operation = new SimpleBoolFunction(AbstractOperationTest.IRRELEVANT_NAME, null, predicates, null, null);
		operation.createPreAndPostconditions(null);
		
		Mockito.verify(preconditionFactoryMock, Mockito.times(1)).createSimplePrecondition(preconditionAxiom);
	}
}
