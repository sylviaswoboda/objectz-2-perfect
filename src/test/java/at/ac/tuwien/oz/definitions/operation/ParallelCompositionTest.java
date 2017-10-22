package at.ac.tuwien.oz.definitions.operation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;


public class ParallelCompositionTest {

	@Mock private ICombinableOperation left;
	@Mock private ICombinableOperation right;
	
	@Mock private Variable inputLeft1;
	@Mock private Variable inputRight1;
	@Mock private Variable inputCommVar1;
	
	@Mock private Variable outputLeft1;
	@Mock private Variable outputRight1;
	@Mock private Variable outputCommVar1;

	@Mock private Variable undecoratedCommVarCopy;
	
	@Mock private PreconditionFactory preconditionFactoryMock;
	@Mock private PostconditionFactory compositionFactoryMock;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		PreconditionFactory.setInstance(preconditionFactoryMock);
		PostconditionFactory.setInstance(compositionFactoryMock);
		
		Mockito.when(inputCommVar1.hasSameBaseName(outputCommVar1)).thenReturn(true);
		Mockito.when(outputCommVar1.hasSameBaseName(inputCommVar1)).thenReturn(true);
		
		Mockito.when(outputCommVar1.getUndecoratedCopy()).thenReturn(undecoratedCommVarCopy);
		Mockito.when(undecoratedCommVarCopy.hasSameBaseName(inputCommVar1)).thenReturn(true);
		Mockito.when(undecoratedCommVarCopy.hasSameBaseName(outputCommVar1)).thenReturn(true);
		
		Declarations inputLeft = new Declarations();
		Declarations outputLeft = new Declarations();
		Declarations inputRight = new Declarations();
		Declarations outputRight = new Declarations();
		
		inputLeft.add(inputLeft1);
		inputLeft.add(inputCommVar1);
		
		inputRight.add(inputRight1);
		
		outputLeft.add(outputLeft1);
		
		outputRight.add(outputRight1);
		outputRight.add(outputCommVar1);
		
		Mockito.when(left.getInputParameters()).thenReturn(inputLeft);
		Mockito.when(left.getOutputParameters()).thenReturn(outputLeft);
		Mockito.when(right.getInputParameters()).thenReturn(inputRight);
		Mockito.when(right.getOutputParameters()).thenReturn(outputRight);
		
	}
	
	@After
	public void tearDown(){
		PreconditionFactory.setInstance(null);
		PostconditionFactory.setInstance(null);
	}

	
	@Test
	public void extractCommunicationVariables(){
		ParallelComposition composition = new ParallelComposition(left, right, false);
		composition.createPreAndPostconditions(null);
		
		Declarations inputResult = composition.getInputParameters();
		Declarations outputResult = composition.getOutputParameters();
		Declarations communicationResult = composition.getCommunicationVariables();
		
		Assert.assertEquals(2, inputResult.size());
		Assert.assertEquals(2, outputResult.size());
		Assert.assertEquals(1, communicationResult.size());
		
		Assert.assertTrue(inputResult.contains(inputLeft1));
		Assert.assertTrue(inputResult.contains(inputRight1));
		
		Assert.assertTrue(outputResult.contains(outputLeft1));
		Assert.assertTrue(outputResult.contains(outputRight1));
		
		Assert.assertTrue(communicationResult.contains(undecoratedCommVarCopy));
		
	}
}
