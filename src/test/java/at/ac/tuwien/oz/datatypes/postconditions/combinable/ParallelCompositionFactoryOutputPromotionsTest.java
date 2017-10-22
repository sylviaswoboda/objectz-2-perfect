package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexOutputPromotionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.PostconditionFactoryTest;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;

public class ParallelCompositionFactoryOutputPromotionsTest extends PostconditionFactoryTest{

	private OutputPromotions outputPromotions;
	private OutputPromotions outputPromotionsLeft;
	private OutputPromotions outputPromotionsRight;
	@Mock private OutputPromotion outputPromotion1;
	@Mock private OutputPromotion outputPromotion2;
	
	@Mock private IOperation anyOperation;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		this.initVariables();
		
		this.outputPromotions = new OutputPromotions();
		this.outputPromotionsLeft = new OutputPromotions();
		this.outputPromotionsRight = new OutputPromotions();
	}

	
	
	// begin
	// compose OutputPromotions and EmptyPostconditions
	
	@Test
	public void composeOutputPromotionAndEmptyPostconditionReturnsOutputPromotions() {
		outputPromotions.add(outputPromotion1);
		ICombinablePostconditions result = compositionFactory.compose(outputPromotions, emptyPostconditions, communicationVariables, sharedVariables, false);
		
		Assert.assertEquals(OutputPromotions.class, result.getClass());
	}
	
	@Test
	public void composeOutputPromotionAndEmptyPostconditionHasFunctionContext() {
		outputPromotions.add(outputPromotion1);
		ICombinablePostconditions result = compositionFactory.compose(outputPromotions, emptyPostconditions, communicationVariables, sharedVariables, false);
		
		OutputPromotions resultingOutputPromotions = (OutputPromotions)result;
		Assert.assertEquals(OutputPromotionContext.FUNCTION, resultingOutputPromotions.getContext());
		
		resultingOutputPromotions.changeContext(OutputPromotionContext.SCHEMA);
		Assert.assertEquals(OutputPromotionContext.SCHEMA, resultingOutputPromotions.getContext());
	}
	
	@Test
	public void composeOutputPromotionAndEmptyPostconditionKeepsContainingOutputPromotions() {
		this.outputPromotions.add(outputPromotion1);
		this.outputPromotions.add(outputPromotion2);
		
		ICombinablePostconditions result = compositionFactory.compose(outputPromotions, emptyPostconditions, communicationVariables, sharedVariables, false);
		OutputPromotions resultingOutputPromotions = (OutputPromotions)result;
		Assert.assertEquals(2, resultingOutputPromotions.size());
		Assert.assertThat(resultingOutputPromotions.getElements(), IsCollectionContaining.hasItems(outputPromotion1, outputPromotion2));
	}

	// compose OutputPromotions and EmptyPostconditions
	// end
	
	// begin
	// compose OutputPromotions and OutputPromotions

	@Test
	public void composeTwoOutputPromotionsWithSharedOutputReturnsOutputPromotions(){
		OutputPromotion outputPromotionSpy1 = getOutputPromotion(sharedOutput, new ArrayList<Variable>());
		OutputPromotion outputPromotionSpy2 = getOutputPromotion(sharedOutput, new ArrayList<Variable>());
		
		this.outputPromotionsLeft.add(outputPromotionSpy1);
		this.outputPromotionsRight.add(outputPromotionSpy2);
		
		this.sharedVariables.add(sharedOutput);
		
		ICombinablePostconditions result = compositionFactory.compose(outputPromotionsLeft, outputPromotionsRight, communicationVariables, sharedVariables, false);
		Assert.assertEquals(OutputPromotions.class, result.getClass());
		
		OutputPromotions outputPromotionsResult = (OutputPromotions)result;
		
		Assert.assertEquals(1, outputPromotionsResult.size());
		Assert.assertThat(outputPromotionsResult.getElements(), IsCollectionContaining.hasItems(outputPromotionSpy1));

	}
	
	@Test
	public void composeTwoOutputPromotionsWithCommunicationReturnsOutputPromotionsWithCommunication(){
		OutputPromotion outputPromotionSpy1 = getOutputPromotion(commVar1Out, new ArrayList<Variable>());
		OutputPromotion outputPromotionSpy2 = getOutputPromotion(outVar, Arrays.asList(commVar1In));
		
		this.outputPromotionsLeft.add(outputPromotionSpy1);
		this.outputPromotionsRight.add(outputPromotionSpy2);
		
		this.communicationVariables.add(commVar1);
		
		ICombinablePostconditions result = compositionFactory.compose(outputPromotionsLeft, outputPromotionsRight, communicationVariables, sharedVariables, false);
		Assert.assertEquals(ComplexOutputPromotions.class, result.getClass());
		
		ComplexOutputPromotions outputPromotionsResult = (ComplexOutputPromotions)result;
		
		OutputPromotions simplePromotions = outputPromotionsResult.getSimplePromotions();
		Assert.assertEquals(0, simplePromotions.size());
		
		ComplexOutputPromotionMapping communication = outputPromotionsResult.getMapping();
		Assert.assertEquals(1, communication.getOtherPromotions().size());
		Assert.assertThat(communication.getOtherPromotions().getElements(), IsCollectionContaining.hasItems(outputPromotionSpy2));
		Assert.assertEquals(1, communication.getCommunicationPromotions().size());
		Assert.assertThat(communication.getCommunicationPromotions().getElements(), IsCollectionContaining.hasItems(outputPromotionSpy1));
	}

	@Test
	public void composeTwoOutputPromotionsWithCommunicationAndSharedOutputDegeneratesToOutputPromotions(){
		OutputPromotion outputPromotionSpy11 = getOutputPromotion(commVar1Out, new ArrayList<Variable>());
		OutputPromotion outputPromotionSpy12 = getOutputPromotion(sharedOutput, new ArrayList<Variable>());
		OutputPromotion outputPromotionSpy2 = getOutputPromotion(sharedOutput, Arrays.asList(commVar1In));
		
		this.outputPromotionsLeft.add(outputPromotionSpy11);
		this.outputPromotionsLeft.add(outputPromotionSpy12);
		this.outputPromotionsRight.add(outputPromotionSpy2);
		
		this.sharedVariables.add(sharedOutput);
		this.communicationVariables.add(commVar1);
		
		ICombinablePostconditions result = compositionFactory.compose(outputPromotionsLeft, outputPromotionsRight, communicationVariables, sharedVariables, false);
		Assert.assertEquals(OutputPromotions.class, result.getClass());
		
		OutputPromotions outputPromotionsResult = (OutputPromotions)result;
		
		Assert.assertEquals(1, outputPromotionsResult.size());
		Assert.assertThat(outputPromotionsResult.getElements(), IsCollectionContaining.hasItems(outputPromotionSpy12));
	}

	@Test
	public void composeTwoOutputPromotionsWithCommunicationAndSharedOutputReturnsOutputPromotionsWithCommunication(){
		OutputPromotion outputPromotionSpy11 = getOutputPromotion(commVar1Out, new ArrayList<Variable>());
		OutputPromotion outputPromotionSpy12 = getOutputPromotion(sharedOutput, new ArrayList<Variable>());
		OutputPromotion outputPromotionSpy21 = getOutputPromotion(outVar, Arrays.asList(commVar1In));
		OutputPromotion outputPromotionSpy22 = getOutputPromotion(sharedOutput, Arrays.asList(commVar1In));
		
		this.outputPromotionsLeft.add(outputPromotionSpy11);
		this.outputPromotionsLeft.add(outputPromotionSpy12);
		this.outputPromotionsRight.add(outputPromotionSpy21);
		this.outputPromotionsRight.add(outputPromotionSpy22);
		
		this.sharedVariables.add(sharedOutput);
		this.communicationVariables.add(commVar1);
		
		ICombinablePostconditions result = compositionFactory.compose(outputPromotionsLeft, outputPromotionsRight, communicationVariables, sharedVariables, false);
		Assert.assertEquals(ComplexOutputPromotions.class, result.getClass());
		
		ComplexOutputPromotions outputPromotionsResult = (ComplexOutputPromotions)result;
		
		OutputPromotions simplePromotions = outputPromotionsResult.getSimplePromotions();
		Assert.assertEquals(1, simplePromotions.size());
		Assert.assertThat(simplePromotions.getElements(), IsCollectionContaining.hasItems(outputPromotionSpy12));
		
		ComplexOutputPromotionMapping communication = outputPromotionsResult.getMapping();
		Assert.assertEquals(1, communication.getOtherPromotions().size());
		Assert.assertThat(communication.getOtherPromotions().getElements(), IsCollectionContaining.hasItems(outputPromotionSpy21));
		Assert.assertEquals(1, communication.getCommunicationPromotions().size());
		Assert.assertThat(communication.getCommunicationPromotions().getElements(), IsCollectionContaining.hasItems(outputPromotionSpy11));
	}
	
}
