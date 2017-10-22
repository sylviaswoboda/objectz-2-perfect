package at.ac.tuwien.oz.datatypes;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;


public class DeclarationsTest {
	
	private Declarations declarations;

	@Mock private Variable inputVar1;
	@Mock private Variable inputVar2;
	@Mock private Variable inputVar3;
	
	@Mock private Variable outputVar1;
	@Mock private Variable outputVar2;
	
	@Mock private Ident outputIdent1;
	@Mock private Ident outputIdent2;
	
	@Mock private Variable primedVar1;
	
	@Mock private Variable auxiliaryVar1;
	@Mock private Variable auxiliaryVar2;
	
	@Mock private Variable undecoratedVar1;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		declarations = new Declarations();
		
		Mockito.when(inputVar1.isInputVariable()).thenReturn(true);
		Mockito.when(inputVar2.isInputVariable()).thenReturn(true);
		Mockito.when(inputVar3.isInputVariable()).thenReturn(true);

		Mockito.when(outputVar1.isOutputVariable()).thenReturn(true);
		Mockito.when(outputVar1.getId()).thenReturn(outputIdent1);

		Mockito.when(outputVar2.isOutputVariable()).thenReturn(true);
		Mockito.when(outputVar2.getId()).thenReturn(outputIdent2);

		Mockito.when(primedVar1.isPrimdVariable()).thenReturn(true);
		
		Mockito.when(inputVar1.compareTo(inputVar2)).thenReturn(-1);
		Mockito.when(inputVar2.compareTo(inputVar1)).thenReturn(1);
		
		Mockito.when(inputVar1.compareTo(inputVar3)).thenReturn(-1);
		Mockito.when(inputVar3.compareTo(inputVar1)).thenReturn(1);
		
		Mockito.when(inputVar2.compareTo(inputVar3)).thenReturn(-1);
		Mockito.when(inputVar3.compareTo(inputVar2)).thenReturn(1);
	}

	
	@Test
	public void inputParametersDoesNotContainUndecoratedVars(){
		declarations.add(inputVar1);
		declarations.add(undecoratedVar1);
		
		Declarations inputParameters = declarations.getInputVariables();
		
		Assert.assertEquals(1, inputParameters.size());
		Assert.assertEquals(inputVar1, inputParameters.get(0));
	}
	
	@Test
	public void inputParametersContainsAllInputVars(){
		declarations.add(inputVar3);
		declarations.add(inputVar1);
		declarations.add(inputVar2);
		
		Declarations inputParameters = declarations.getInputVariables();
		
		Assert.assertEquals(3, inputParameters.size());
		Assert.assertEquals(inputVar1, inputParameters.get(0));
		Assert.assertEquals(inputVar2, inputParameters.get(1));
		Assert.assertEquals(inputVar3, inputParameters.get(2));
	}

	@Test
	public void inputParametersDoesNotContainOutputVars(){
		declarations.add(outputVar1);
		declarations.add(inputVar1);
		
		Declarations inputParameters = declarations.getInputVariables();
		
		Assert.assertEquals(1, inputParameters.size());
		Assert.assertEquals(inputVar1, inputParameters.get(0));
	}
	
	@Test
	public void inputParametersDoesNotContainPrimedVars(){
		declarations.add(inputVar2);
		declarations.add(primedVar1);
		declarations.add(inputVar1);
		
		Declarations inputParameters = declarations.getInputVariables();
		
		Assert.assertEquals(2, inputParameters.size());
		Assert.assertEquals(inputVar1, inputParameters.get(0));
		Assert.assertEquals(inputVar2, inputParameters.get(1));
	}
	
	@Test
	public void auxiliaryParametersAreNeitherInputNorOutputVariables() {
		declarations.add(inputVar1);
		declarations.add(auxiliaryVar1);
		declarations.add(outputVar1);
		
		Declarations auxiliaryParameters = declarations.getAuxiliaryVariables();
		
		Assert.assertEquals(1, auxiliaryParameters.size());
		Assert.assertEquals(auxiliaryVar1, auxiliaryParameters.get(0));
	}

	@Test
	public void auxiliaryParametersAreSorted() {
		declarations.add(inputVar2);
		declarations.add(auxiliaryVar2);
		declarations.add(outputVar2);
		declarations.add(inputVar1);
		declarations.add(auxiliaryVar1);
		declarations.add(outputVar1);
		
		Declarations auxiliaryParameters = declarations.getAuxiliaryVariables();
		
		Assert.assertEquals(2, auxiliaryParameters.size());
		Assert.assertThat(auxiliaryParameters.asList(), IsCollectionContaining.hasItems(auxiliaryVar1, auxiliaryVar2));
	}
	
	@Test
	public void outputParametersAreSorted(){
		declarations.add(inputVar2);
		declarations.add(auxiliaryVar2);
		declarations.add(outputVar2);
		declarations.add(inputVar1);
		declarations.add(auxiliaryVar1);
		declarations.add(outputVar1);
		
		Declarations outputParameters = declarations.getOutputVariables();
		
		Assert.assertEquals(2, outputParameters.size());
		Assert.assertThat(outputParameters.asList(), IsCollectionContaining.hasItems(outputVar1, outputVar2));
	}
	
	@Test
	public void containsIgnoreDecoration(){
		Ident input = new Ident("input", "?");
		Ident output = new Ident("output", "!");
		Ident undecorated = new Ident("undecorated");
		ExpressionContext exprCtx = Mockito.mock(ExpressionContext.class);
		
		Variable v1 = new Variable(input, exprCtx);
		Variable v2 = new Variable(output, exprCtx);
		Variable v3 = new Variable(undecorated, exprCtx);
		declarations.add(v1);
		declarations.add(v2);
		declarations.add(v3);
		
		
		Variable undecoratedCopy = v1.getUndecoratedCopy();
		Assert.assertTrue(declarations.containsIgnoreDecoration(undecoratedCopy));
		
		undecoratedCopy = v2.getUndecoratedCopy();
		Assert.assertTrue(declarations.containsIgnoreDecoration(undecoratedCopy));
	
		undecoratedCopy = v3.getUndecoratedCopy();
		Assert.assertTrue(declarations.containsIgnoreDecoration(undecoratedCopy));

		Variable somethingElse = new Variable(new Ident("somethingElse"), exprCtx);
		Assert.assertFalse(declarations.containsIgnoreDecoration(somethingElse));
	}
	
	@Test
	public void identifiersReturnsAllIdentifiers(){
		declarations.add(outputVar2);
		declarations.add(outputVar1);
		
		Idents identifiers = declarations.getIdentifiers();
		
		Assert.assertEquals(2, identifiers.size());
		Assert.assertThat(identifiers.asList(), IsCollectionContaining.hasItems(outputIdent1, outputIdent2));
	}


	

}
