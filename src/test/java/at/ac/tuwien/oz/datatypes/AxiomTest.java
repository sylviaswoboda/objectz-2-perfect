package at.ac.tuwien.oz.datatypes;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.axioms.AxiomFactory;


public class AxiomTest {

	@Mock private ST templateMock;
	@Mock private Ident inputIdent;
	@Mock private Ident outputIdent;
	@Mock private Ident primedIdent;
	@Mock private Ident undecoratedIdent;
	
	private Idents identifiers;
	private AxiomFactory axiomFactory;
	
	@Before
	public void setup(){
		axiomFactory = AxiomFactory.getInstance();
		MockitoAnnotations.initMocks(this);
		identifiers = new Idents();
		Mockito.when(inputIdent.isInputIdent()).thenReturn(true);
		Mockito.when(inputIdent.isOutputIdent()).thenReturn(false);
		Mockito.when(inputIdent.isPrimedIdent()).thenReturn(false);

		Mockito.when(outputIdent.isInputIdent()).thenReturn(false);
		Mockito.when(outputIdent.isOutputIdent()).thenReturn(true);
		Mockito.when(outputIdent.isPrimedIdent()).thenReturn(false);

		Mockito.when(primedIdent.isInputIdent()).thenReturn(false);
		Mockito.when(primedIdent.isOutputIdent()).thenReturn(false);
		Mockito.when(primedIdent.isPrimedIdent()).thenReturn(true);
		
		Mockito.when(undecoratedIdent.isInputIdent()).thenReturn(false);
		Mockito.when(undecoratedIdent.isOutputIdent()).thenReturn(false);
		Mockito.when(undecoratedIdent.isPrimedIdent()).thenReturn(false);

	}
	
	@Test
	public void onlyInputIdentYieldsPrecondition(){
		identifiers.add(inputIdent);
		
		Axiom ax = axiomFactory.createPredicateWithIdentifiers(templateMock, identifiers);
		
		Assert.assertTrue(ax.isPrecondition());
		Assert.assertFalse(ax.isPostcondition());
	}
	
	@Test
	public void onlyUndecoratedIdentYieldsPrecondition(){
		identifiers.add(undecoratedIdent);
		
		Axiom ax = axiomFactory.createPredicateWithIdentifiers(templateMock, identifiers);
		
		Assert.assertTrue(ax.isPrecondition());
		Assert.assertFalse(ax.isPostcondition());
	}

	@Test
	public void inputAndUndecoratedIdentsYieldsPrecondition(){
		identifiers.add(inputIdent);
		identifiers.add(undecoratedIdent);
		
		Axiom ax = axiomFactory.createPredicateWithIdentifiers(templateMock, identifiers);
		
		Assert.assertTrue(ax.isPrecondition());
		Assert.assertFalse(ax.isPostcondition());
	}

	@Test
	public void onlyOutputIdentYieldsPostcondition(){
		identifiers.add(outputIdent);
		
		Axiom ax = axiomFactory.createPredicateWithIdentifiers(templateMock, identifiers);
		
		Assert.assertFalse(ax.isPrecondition());
		Assert.assertTrue(ax.isPostcondition());
	}

	@Test
	public void onlyPrimedIdentYieldsPostcondition(){
		identifiers.add(primedIdent);
		
		Axiom ax = axiomFactory.createPredicateWithIdentifiers(templateMock, identifiers);
		
		Assert.assertFalse(ax.isPrecondition());
		Assert.assertTrue(ax.isPostcondition());
	}

	@Test
	public void allIdentTypesYieldsPostcondition(){
		identifiers.add(inputIdent);
		identifiers.add(outputIdent);
		identifiers.add(primedIdent);
		identifiers.add(undecoratedIdent);
		
		Axiom ax = axiomFactory.createPredicateWithIdentifiers(templateMock, identifiers);
		
		Assert.assertFalse(ax.isPrecondition());
		Assert.assertTrue(ax.isPostcondition());
	}
}
