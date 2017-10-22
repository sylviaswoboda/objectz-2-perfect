package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.AssertCollection;
import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class CompositePostconditionFactoryTest {

	
	private CompositePostconditionDataCollector collector;
	
	private OutputPromotion outputPromo1;
	private OutputPromotion outputPromo2;
	private OutputPromotion outputPromo3;
	
	private ChangeOperationCall call1;
	private ChangeOperationCall call2;
	private ChangeOperationCall call3;
	private Variable var1;
	private Variable var2;
	private Variable var3;
	private Variable var1In;
	private Variable var1Out;
	private Variable var2In;
	private Variable var2Out;
	private Variable var3In;
	private Variable var3Out;

	
	
	@Before
	public void setup(){
		collector = Mockito.mock(CompositePostconditionDataCollector.class);
		outputPromo1 = Mockito.mock(OutputPromotion.class);
		outputPromo2 = Mockito.mock(OutputPromotion.class);
		outputPromo3 = Mockito.mock(OutputPromotion.class);
		call1 = Mockito.mock(ChangeOperationCall.class);
		call2 = Mockito.mock(ChangeOperationCall.class);
		call3 = Mockito.mock(ChangeOperationCall.class);
		var1 = Mockito.mock(Variable.class, "var1");
		var2 = Mockito.mock(Variable.class, "var2");
		var3 = Mockito.mock(Variable.class, "var3");
		var1In = Mockito.mock(Variable.class, "var1In");
		var2In = Mockito.mock(Variable.class, "var2In");
		var3In = Mockito.mock(Variable.class, "var3In");
		var1Out = Mockito.mock(Variable.class, "var1Out");
		var2Out = Mockito.mock(Variable.class, "var2Out");
		var3Out = Mockito.mock(Variable.class, "var3Out");

		Mockito.when(call1.getCaller()).thenReturn(new ST("c1"));
		Mockito.when(call2.getCaller()).thenReturn(new ST("c2"));
		Mockito.when(call3.getCaller()).thenReturn(new ST("c3"));
		
		Mockito.when(collector.getSimpleCalls()).thenReturn(ChangeOperationCalls.empty());
		Mockito.when(collector.getCommunicatingCalls()).thenReturn(ChangeOperationCalls.empty());
		Mockito.when(collector.getAllCalls()).thenReturn(ChangeOperationCalls.empty());
		Mockito.when(collector.getSimplePromotions()).thenReturn(OutputPromotions.empty());
		Mockito.when(collector.getCommunicatingPromotions()).thenReturn(OutputPromotions.empty());
		
		Mockito.when(collector.getSharedOutputVariables()).thenReturn(Declarations.empty());
		Mockito.when(collector.getCommunicationVariables()).thenReturn(Declarations.empty());
		Mockito.when(collector.getVisibleCommunicationVariables()).thenReturn(Declarations.empty());
		
		Mockito.when(var1.getInputCopy()).thenReturn(var1In);
		Mockito.when(var2.getInputCopy()).thenReturn(var2In);
		Mockito.when(var3.getInputCopy()).thenReturn(var3In);
		Mockito.when(var1.getOutputCopy()).thenReturn(var1Out);
		Mockito.when(var2.getOutputCopy()).thenReturn(var2Out);
		Mockito.when(var3.getOutputCopy()).thenReturn(var3Out);

	}
	
	@Test
	public void testCreateEmptyPostconditions() {
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		IComposablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(EmptyPostconditions.class, postcondition.getClass());
		
	}

	@Test
	public void testCreateOutputPromotions() {
		Mockito.when(collector.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromo1));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(OutputPromotions.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements(), outputPromo1);
		
	}

	@Test
	public void testCreateOutputPromotions_WithSharedOutput() {
		Mockito.when(collector.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromo1));
		Mockito.when(collector.getSharedOutputVariables()).thenReturn(new Declarations(var1));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(OutputPromotions.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements(), outputPromo1);
		AssertCollection.assertHasElements(postcondition.getSharedOutputVariables().asList());
		
	}

	@Test
	public void testCreateComplexOutputPromotions_WithCommunication() {
		Mockito.when(collector.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromo1));
		Mockito.when(collector.getCommunicatingPromotions()).thenReturn(new OutputPromotions(outputPromo2, outputPromo3));
		
		Mockito.when(collector.getCommunicationVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(collector.getVisibleCommunicationVariables()).thenReturn(new Declarations(var2));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(ComplexOutputPromotions.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements(), outputPromo1);
		AssertCollection.assertHasElements(postcondition.getCommunicatingPromotions().getElements(), outputPromo2, outputPromo3);
		AssertCollection.assertHasElements(postcondition.getSharedOutputVariables().asList());
		AssertCollection.assertHasElements(postcondition.getAllCommunicationVariables().asList(), var1, var2);
		AssertCollection.assertHasElements(postcondition.getVisibleCommunicationVariables().asList(), var2);
	}

	@Test
	public void testCreateComplexOutputPromotions_WithInvisibleCommunication() {
		Mockito.when(collector.getCommunicatingPromotions()).thenReturn(new OutputPromotions(outputPromo1, outputPromo2));
		
		Mockito.when(outputPromo1.usesInputParameters(new Declarations(var1In))).thenReturn(true);
		
		Mockito.when(collector.getCommunicationVariables()).thenReturn(new Declarations(var1));
		Mockito.when(collector.getVisibleCommunicationVariables()).thenReturn(new Declarations());
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(ComplexOutputPromotions.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements());
		AssertCollection.assertHasElements(postcondition.getCommunicatingPromotions().getElements(), outputPromo1, outputPromo2);
		AssertCollection.assertHasElements(postcondition.getSharedOutputVariables().asList());
		AssertCollection.assertHasElements(postcondition.getAllCommunicationVariables().asList(), var1);
		AssertCollection.assertHasElements(postcondition.getVisibleCommunicationVariables().asList());
		
	}

	
	@Test
	public void testCreateChangeOperationCalls() {
		Mockito.when(collector.getSimpleCalls()).thenReturn(new ChangeOperationCalls(call1));
		Mockito.when(collector.getAllCalls()).thenReturn(new ChangeOperationCalls(call1));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(ChangeOperationCalls.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimpleCalls().asList(), call1);
	}
	
	@Test
	public void testCreateComplexChangePostcondition_WithSharedOutput() {
		Mockito.when(collector.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromo1));
		Mockito.when(collector.getCommunicatingPromotions()).thenReturn(new OutputPromotions(outputPromo2));
		
		Mockito.when(collector.getSimpleCalls()).thenReturn(new ChangeOperationCalls(call1));
		Mockito.when(collector.getCommunicatingCalls()).thenReturn(new ChangeOperationCalls(call2, call3));
		Mockito.when(collector.getAllCalls()).thenReturn(new ChangeOperationCalls(call1, call2, call3));
		Mockito.when(collector.getSharedOutputVariables()).thenReturn(new Declarations(var1));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(ComplexChangePostcondition.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimpleCalls().asList(), call1);
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements(), outputPromo1);
		
		AssertCollection.assertHasElements(postcondition.getCommunicatingCalls().asList(), call2, call3);
		AssertCollection.assertHasElements(postcondition.getCommunicatingPromotions().getElements(), outputPromo2);
		
		AssertCollection.assertHasElements(postcondition.getUncategorizedCalls().asList());

		AssertCollection.assertHasElements(postcondition.getSharedOutputVariables().asList(), var1);
		AssertCollection.assertHasElements(postcondition.getAllCommunicationVariables().asList());
		AssertCollection.assertHasElements(postcondition.getVisibleCommunicationVariables().asList());
	}

	@Test
	public void testCreateComplexChangePostcondition_WithCommunication() {
		Mockito.when(collector.getCommunicatingPromotions()).thenReturn(new OutputPromotions(outputPromo2));
		
		Mockito.when(collector.getCommunicatingCalls()).thenReturn(new ChangeOperationCalls(call2, call3));
		Mockito.when(collector.getAllCalls()).thenReturn(new ChangeOperationCalls(call2, call3));
		
		Mockito.when(collector.getCommunicationVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(collector.getVisibleCommunicationVariables()).thenReturn(new Declarations(var1));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(ComplexChangePostcondition.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimpleCalls().asList());
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements());
		
		AssertCollection.assertHasElements(postcondition.getCommunicatingCalls().asList(), call2, call3);
		AssertCollection.assertHasElements(postcondition.getCommunicatingPromotions().getElements(), outputPromo2);
		
		AssertCollection.assertHasElements(postcondition.getUncategorizedCalls().asList());

		AssertCollection.assertHasElements(postcondition.getSharedOutputVariables().asList());
		AssertCollection.assertHasElements(postcondition.getAllCommunicationVariables().asList(), var1, var2);
		AssertCollection.assertHasElements(postcondition.getVisibleCommunicationVariables().asList(), var1);
	}

	@Test
	public void testCreateComplexChangePostcondition_SimpleCallsAndPromotionsOnly() {
		Mockito.when(collector.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromo1));
		Mockito.when(collector.getSimpleCalls()).thenReturn(new ChangeOperationCalls(call1));
		Mockito.when(collector.getAllCalls()).thenReturn(new ChangeOperationCalls(call1));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(ComplexChangePostcondition.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimpleCalls().asList(), call1);
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements(), outputPromo1);
		
		AssertCollection.assertHasElements(postcondition.getCommunicatingCalls().asList());
		AssertCollection.assertHasElements(postcondition.getCommunicatingPromotions().getElements());
		
		AssertCollection.assertHasElements(postcondition.getUncategorizedCalls().asList());

		AssertCollection.assertHasElements(postcondition.getSharedOutputVariables().asList());
		AssertCollection.assertHasElements(postcondition.getAllCommunicationVariables().asList());
		AssertCollection.assertHasElements(postcondition.getVisibleCommunicationVariables().asList());
	}

	@Test
	public void testCreateThenPostcondition_OnlyCommonCaller() {
		ST callerMock = Mockito.mock(ST.class);
		
		Mockito.when(call1.getCaller()).thenReturn(callerMock);
		Mockito.when(call2.getCaller()).thenReturn(callerMock);
	
		IPromotedOperation operation1 = Mockito.mock(IPromotedOperation.class);
		IPromotedOperation operation2 = Mockito.mock(IPromotedOperation.class);
		Mockito.when(call1.getOrderableOperation()).thenReturn(operation1);
		Mockito.when(call2.getOrderableOperation()).thenReturn(operation2);
		
		Mockito.when(operation1.getUsedStateVariables()).thenReturn(new Idents());
		Mockito.when(operation1.getChangedStateVariables()).thenReturn(new Idents());
		Mockito.when(operation2.getUsedStateVariables()).thenReturn(new Idents());
		Mockito.when(operation2.getChangedStateVariables()).thenReturn(new Idents());
		
		Mockito.when(outputPromo2.usesOutputParameter(Mockito.any(Variable.class))).thenReturn(true);
		Mockito.when(call3.usesInputParameters(Mockito.any(Variable.class))).thenReturn(true);
		
		Mockito.when(collector.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromo1));
		Mockito.when(collector.getCommunicatingPromotions()).thenReturn(new OutputPromotions(outputPromo2));
		
		Mockito.when(collector.getSimpleCalls()).thenReturn(new ChangeOperationCalls(call1, call2));
		Mockito.when(collector.getCommunicatingCalls()).thenReturn(new ChangeOperationCalls(call3));
		Mockito.when(collector.getAllCalls()).thenReturn(new ChangeOperationCalls(call1, call2, call3));
		Mockito.when(collector.getSharedOutputVariables()).thenReturn(new Declarations(var1));
		Mockito.when(collector.getCommunicationVariables()).thenReturn(new Declarations(var2, var3));
		Mockito.when(collector.getVisibleCommunicationVariables()).thenReturn(new Declarations(var3));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(ThenPostcondition.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimpleCalls().asList());
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements(), outputPromo1);
		
		AssertCollection.assertHasElements(postcondition.getCommunicatingCalls().asList());
		AssertCollection.assertHasElements(postcondition.getCommunicatingPromotions().getElements(), outputPromo2);
		
		AssertCollection.assertHasElements(postcondition.getUncategorizedCalls().asList(), call1, call2, call3);

		AssertCollection.assertHasElements(postcondition.getSharedOutputVariables().asList(), var1);
		AssertCollection.assertHasElements(postcondition.getAllCommunicationVariables().asList(), var2, var3);
		AssertCollection.assertHasElements(postcondition.getVisibleCommunicationVariables().asList(), var3);
	}

	@Test
	public void testCreateThenPostcondition_CommunicationOrderNotPossible() {
		ST callerMock = Mockito.mock(ST.class);
		
		Mockito.when(call1.getCaller()).thenReturn(callerMock);
		Mockito.when(call2.getCaller()).thenReturn(callerMock);
	
		IPromotedOperation operation1 = Mockito.mock(IPromotedOperation.class);
		IPromotedOperation operation2 = Mockito.mock(IPromotedOperation.class);
		Mockito.when(call1.getOrderableOperation()).thenReturn(operation1);
		Mockito.when(call2.getOrderableOperation()).thenReturn(operation2);
		
		Mockito.when(operation1.getUsedStateVariables()).thenReturn(new Idents(new Ident("a")));
		Mockito.when(operation1.getChangedStateVariables()).thenReturn(new Idents());
		Mockito.when(operation2.getUsedStateVariables()).thenReturn(new Idents());
		Mockito.when(operation2.getChangedStateVariables()).thenReturn(new Idents(new Ident("a")));
		
		Mockito.when(outputPromo2.usesInputParameter(var2In)).thenReturn(true);
		Mockito.when(outputPromo2.usesInputParameter(var3In)).thenReturn(true);
		Mockito.when(call2.usesOutputParameter(var2Out)).thenReturn(true);
		Mockito.when(call2.usesOutputParameter(var3Out)).thenReturn(true);
		
		Mockito.when(collector.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromo1));
		Mockito.when(collector.getCommunicatingPromotions()).thenReturn(new OutputPromotions(outputPromo2));
		
		Mockito.when(collector.getSimpleCalls()).thenReturn(new ChangeOperationCalls(call1));
		Mockito.when(collector.getCommunicatingCalls()).thenReturn(new ChangeOperationCalls(call2));
		Mockito.when(collector.getAllCalls()).thenReturn(new ChangeOperationCalls(call1, call2));
		Mockito.when(collector.getSharedOutputVariables()).thenReturn(new Declarations(var1));
		Mockito.when(collector.getCommunicationVariables()).thenReturn(new Declarations(var2, var3));
		Mockito.when(collector.getVisibleCommunicationVariables()).thenReturn(new Declarations(var3));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		try{
			factory.createPostcondition();
			Assert.fail("Expecting exception");
		}catch(ObjectZToPerfectTranslationException ex){
			Assert.assertEquals("Mapping not possible because of communication variable order.", ex.getMessage());
		}
	}
	
	@Test
	public void testCreateThenPostcondition_SelfIsCommonCaller() {
		Mockito.when(call1.getCaller()).thenReturn(null);
		Mockito.when(call2.getCaller()).thenReturn(null);
	
		IPromotedOperation operation1 = Mockito.mock(IPromotedOperation.class);
		IPromotedOperation operation2 = Mockito.mock(IPromotedOperation.class);
		Mockito.when(call1.getOrderableOperation()).thenReturn(operation1);
		Mockito.when(call2.getOrderableOperation()).thenReturn(operation2);
		
		Mockito.when(operation1.getUsedStateVariables()).thenReturn(new Idents());
		Mockito.when(operation1.getChangedStateVariables()).thenReturn(new Idents());
		Mockito.when(operation2.getUsedStateVariables()).thenReturn(new Idents());
		Mockito.when(operation2.getChangedStateVariables()).thenReturn(new Idents());
		
		Mockito.when(collector.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromo1));
		
		Mockito.when(collector.getSimpleCalls()).thenReturn(new ChangeOperationCalls(call1, call2));
		Mockito.when(collector.getAllCalls()).thenReturn(new ChangeOperationCalls(call1, call2));
		
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		
		ICombinablePostconditions postcondition = factory.createPostcondition();
		
		Assert.assertEquals(ThenPostcondition.class, postcondition.getClass());
		AssertCollection.assertHasElements(postcondition.getSimpleCalls().asList());
		AssertCollection.assertHasElements(postcondition.getSimplePromotions().getElements(), outputPromo1);
		
		AssertCollection.assertHasElements(postcondition.getCommunicatingCalls().asList());
		AssertCollection.assertHasElements(postcondition.getCommunicatingPromotions().getElements());
		
		AssertCollection.assertHasElements(postcondition.getUncategorizedCalls().asList(), call1, call2);

		AssertCollection.assertHasElements(postcondition.getSharedOutputVariables().asList());
		AssertCollection.assertHasElements(postcondition.getAllCommunicationVariables().asList());
		AssertCollection.assertHasElements(postcondition.getVisibleCommunicationVariables().asList());
	}

}
