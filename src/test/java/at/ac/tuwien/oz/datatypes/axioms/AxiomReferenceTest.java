package at.ac.tuwien.oz.datatypes.axioms;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.parser.OZParser;
import at.ac.tuwien.oz.parser.OZParser.PredicateContext;
import at.ac.tuwien.oz.translator.TranslationTestBase;

public class AxiomReferenceTest extends TranslationTestBase{
	
	@Mock private ParseTreeProperty<Idents> usedIdentifiers;
	@Mock private Ident inVar;
	@Mock private Ident outVar;
	@Mock private Ident undecVar;
	@Mock private Ident primedVar;
	
	private Idents identifiers;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		identifiers = new Idents();
		Mockito.when(inVar.isInputIdent()).thenReturn(true);
		Mockito.when(outVar.isOutputIdent()).thenReturn(true);
		Mockito.when(primedVar.isPrimedIdent()).thenReturn(true);
	}
	
	@Test
	public void constructAPrecondition() {
		String input = "inputVar? < 200";
		identifiers.add(inVar);
		OZParser parser = getParser(input);
		PredicateContext predicateCtx = parser.predicate();
		AxiomReference reference = new AxiomReference(identifiers, predicateCtx);
		
		Assert.assertTrue(reference.isPrecondition());
		
	}

	@Test
	public void usingOutputParametersConstructsAPostcondition() {
		String input = "outputVar! < 200";
		identifiers.add(outVar);
		OZParser parser = getParser(input);
		PredicateContext predicateCtx = parser.predicate();
		AxiomReference reference = new AxiomReference(identifiers, predicateCtx);
		
		Assert.assertTrue(reference.isPostcondition());
	}
	
	@Test
	public void usingPrimedParametersConstructsAPostcondition() {
		String input = "primed' = inputVar? + undecorated";
		identifiers.add(primedVar);
		identifiers.add(inVar);
		identifiers.add(undecVar);
		OZParser parser = getParser(input);
		PredicateContext predicateCtx = parser.predicate();
		AxiomReference reference = new AxiomReference(identifiers, predicateCtx);
		
		Assert.assertTrue(reference.isPostcondition());
		
	}
	
}
