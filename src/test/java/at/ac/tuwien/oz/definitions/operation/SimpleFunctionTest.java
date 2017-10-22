package at.ac.tuwien.oz.definitions.operation;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.SimplePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;

public class SimpleFunctionTest {

	private Declarations declarations;
	private AxiomReferences predicates;
	
	@Mock private Variable auxiliaryVariable;
	@Mock private Variable inputVariable;
	@Mock private Variable outputVariable;
	
	@Mock private AxiomReference precondition;
	@Mock private AxiomReference postcondition;
	@Mock private Axiom preconditionAxiom;
	@Mock private Axiom postconditionAxiom;
	
	@Mock private IPrecondition preconditionFromFactory;

	@Mock private PreconditionFactory preconditionFactoryMock;
	@Mock private PostconditionFactory postconditionFactoryMock;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		declarations = new Declarations();
		predicates = new AxiomReferences();

		declarations.add(inputVariable);
		declarations.add(outputVariable);
		declarations.add(auxiliaryVariable);
		Mockito.when(inputVariable.isInputVariable()).thenReturn(true);
		Mockito.when(outputVariable.isOutputVariable()).thenReturn(true);
		
		predicates.add(precondition);
		predicates.add(postcondition);
		Mockito.when(precondition.isPrecondition()).thenReturn(true);
		Mockito.when(postcondition.isPostcondition()).thenReturn(true);
		
		Mockito.when(precondition.getAxiom()).thenReturn(preconditionAxiom);
		Mockito.when(postcondition.getAxiom()).thenReturn(postconditionAxiom);
		
		PreconditionFactory.setInstance(preconditionFactoryMock);
		PostconditionFactory.setInstance(postconditionFactoryMock);
	}
	
	@AfterClass
	public static void tearDown(){
		PreconditionFactory.setInstance(null);
		PostconditionFactory.setInstance(null);
	}
	
	@Test
	public void operationIsAlwaysFunction(){
		IOperation operation = new SimpleFunction(AbstractOperationTest.IRRELEVANT_NAME, null, null, null, null);
		
		Assert.assertTrue(operation.isFunction());
		Assert.assertFalse(operation.isBoolFunction());
		Assert.assertFalse(operation.isChangeOperation());
	}
	
	@Test
	public void deltalistIsAlwaysEmptyList(){
		IOperation operation = new SimpleFunction(AbstractOperationTest.IRRELEVANT_NAME, null, null, null, null);
		
		List<String> deltalist = operation.getDeltalist();
		
		Assert.assertTrue(deltalist.isEmpty());
	}
	
	@Test
	public void auxiliaryParametersAreTakenFromGivenDeclarations(){
		IOperation operation = new SimpleFunction(AbstractOperationTest.IRRELEVANT_NAME, declarations, null, null, null);
		
		Declarations auxiliaryParameters = operation.getAuxiliaryParameters();
		
		Assert.assertFalse(auxiliaryParameters.isEmpty());
		Assert.assertEquals(1, auxiliaryParameters.size());
		Assert.assertEquals(auxiliaryVariable, auxiliaryParameters.get(0));
	}

	@Test
	public void inputParametersAreTakenFromGivenDeclarations(){
		IOperation operation = new SimpleFunction(AbstractOperationTest.IRRELEVANT_NAME, declarations, null, null, null);
		
		Declarations inputParameters = operation.getInputParameters();
		
		Assert.assertFalse(inputParameters.isEmpty());
		Assert.assertEquals(1, inputParameters.size());
		Assert.assertEquals(inputVariable, inputParameters.get(0));
	}

	@Test
	public void outputParametersAreTakenFromGivenDeclarations(){
		IOperation operation = new SimpleFunction(AbstractOperationTest.IRRELEVANT_NAME, declarations, null, null, null);
		
		Declarations outputParameters = operation.getOutputParameters();
		
		Assert.assertFalse(outputParameters.isEmpty());
		Assert.assertEquals(1, outputParameters.size());
		Assert.assertEquals(outputVariable, outputParameters.get(0));
	}

	@Test
	public void preconditionsAreTransformedByPreconditionFactory(){
		IOperation operation = new SimpleFunction(AbstractOperationTest.IRRELEVANT_NAME, null, predicates, null, null);
		operation.createPreAndPostconditions(null);
		
		Mockito.verify(preconditionFactoryMock, Mockito.times(1)).createSimplePrecondition(preconditionAxiom);
		
		IPreconditions preconditions = operation.getPreconditions();
		Assert.assertNotNull(preconditions);
		Assert.assertEquals(SimplePreconditions.class, preconditions.getClass());
	}
	
	@Test 
	public void postconditionsAreCalculatedByAxiomProvider(){
		IOperation operation = new SimpleFunction(AbstractOperationTest.IRRELEVANT_NAME, null, predicates, null, null);
		operation.createPreAndPostconditions(null);
		
		Mockito.verify(postconditionFactoryMock, Mockito.times(1)).createOutputVariablePredicate(postconditionAxiom, operation.getOutputParameters());
		
		IPostconditions postconditions = operation.getPostconditions();
		Assert.assertNotNull(postconditions);
		Assert.assertEquals(SimplePostconditions.class, postconditions.getClass());
	}
	
	@Test
	public void functionWithPreconditionsHasPreconditionOperationAndCall(){
		Mockito.when(preconditionFactoryMock.createSimplePrecondition(preconditionAxiom)).thenReturn(preconditionFromFactory);
		
		IOperation operation = new SimpleFunction(AbstractOperationTest.IRRELEVANT_NAME, null, predicates, null, null);
		operation.createPreAndPostconditions(null);
		
		PreconditionFunction preconditionOperation = operation.getPreconditionFunction();
		Assert.assertNotNull(preconditionOperation);
	}
}
