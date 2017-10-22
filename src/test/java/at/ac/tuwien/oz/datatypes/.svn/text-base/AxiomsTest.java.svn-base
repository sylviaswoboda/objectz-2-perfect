package at.ac.tuwien.oz.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.axioms.Axioms;


public class AxiomsTest {

	private Axioms predicates;
	
	@Mock private Axiom precondition1;
	@Mock private Axiom precondition2;
	@Mock private Axiom postcondition1;
	@Mock private Axiom postcondition2;
	
	@Mock private ST precondition1ST;
	@Mock private ST precondition2ST;
	@Mock private ST postcondition1ST;
	@Mock private ST postcondition2ST;
	

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		predicates = new Axioms();
		
		Mockito.when(precondition1.isPrecondition()).thenReturn(true);
		Mockito.when(precondition2.isPrecondition()).thenReturn(true);
		
		Mockito.when(postcondition1.isPostcondition()).thenReturn(true);
		Mockito.when(postcondition2.isPostcondition()).thenReturn(true);
		
		Mockito.when(postcondition1.getPredicate()).thenReturn(postcondition1ST);
		Mockito.when(postcondition2.getPredicate()).thenReturn(postcondition2ST);
		Mockito.when(precondition1.getPredicate()).thenReturn(precondition1ST);
		Mockito.when(precondition2.getPredicate()).thenReturn(precondition2ST);
	}

	@Test
	public void allPredicatesArePreconditions(){
		predicates.add(precondition1);
		predicates.add(precondition2);
		
		Axioms preconditions = predicates.getPreconditions();
		
		Assert.assertEquals(2, preconditions.size());
		Assert.assertEquals(precondition1, preconditions.get(0));
		Assert.assertEquals(precondition2, preconditions.get(1));
		Assert.assertTrue(preconditions.hasPreconditions());
		Assert.assertFalse(preconditions.hasPostconditions());
	}
	
	@Test
	public void noPredicatesArePreconditions(){
		predicates.add(postcondition1);
		predicates.add(postcondition2);
		
		Axioms preconditions = predicates.getPreconditions();
		
		Assert.assertEquals(0, preconditions.size());
		Assert.assertFalse(preconditions.hasPreconditions());
		Assert.assertFalse(preconditions.hasPostconditions());
	}
	
	@Test
	public void somePredicatesArePreconditions(){
		predicates.add(postcondition1);
		predicates.add(precondition2);
		predicates.add(precondition1);
		predicates.add(postcondition2);
		
		Axioms preconditions = predicates.getPreconditions();
		
		Assert.assertEquals(2, preconditions.size());
		Assert.assertEquals(precondition2, preconditions.get(0));
		Assert.assertEquals(precondition1, preconditions.get(1));
		
		Assert.assertTrue(preconditions.hasPreconditions());
	}

	@Test
	public void allPredicatesArePostconditions(){
		predicates.add(postcondition1);
		predicates.add(postcondition2);
		
		Axioms postconditions = predicates.getPostconditions();
		
		Assert.assertEquals(2, postconditions.size());
		Assert.assertEquals(postcondition1, postconditions.get(0));
		Assert.assertEquals(postcondition2, postconditions.get(1));
		Assert.assertFalse(postconditions.hasPreconditions());
		Assert.assertTrue(postconditions.hasPostconditions());
	}
	
	@Test
	public void noPredicatesArePostconditions(){
		predicates.add(precondition1);
		predicates.add(precondition2);
		
		Axioms postconditions = predicates.getPostconditions();
		
		Assert.assertEquals(0, postconditions.size());
		Assert.assertFalse(postconditions.hasPreconditions());
		Assert.assertFalse(postconditions.hasPostconditions());
	}
	
	@Test
	public void somePredicatesArePostconditions(){
		predicates.add(postcondition1);
		predicates.add(precondition2);
		predicates.add(precondition1);
		predicates.add(postcondition2);
		
		Axioms postconditions = predicates.getPostconditions();
		
		Assert.assertEquals(2, postconditions.size());
		Assert.assertEquals(postcondition1, postconditions.get(0));
		Assert.assertEquals(postcondition2, postconditions.get(1));
		
		Assert.assertTrue(postconditions.hasPostconditions());
	}

	@Test
	public void axiomTemplatesReturnsAllInnerTemplates(){
		Axioms predicates = new Axioms();
		predicates.add(precondition1);
		predicates.add(precondition2);
		predicates.add(postcondition1);
		predicates.add(postcondition2);
		
		List<ST> templates = predicates.getAxiomTemplates();
		
		Assert.assertEquals(4, templates.size());
		Assert.assertEquals(precondition1ST, templates.get(0));
		Assert.assertEquals(precondition2ST, templates.get(1));
		Assert.assertEquals(postcondition1ST, templates.get(2));
		Assert.assertEquals(postcondition2ST, templates.get(3));
	}
	
	@Test
	public void constructAxiomsFromList(){
		List<Axiom> predicates = new ArrayList<Axiom>();
		predicates.add(precondition1);
		predicates.add(postcondition1);
		
		Axioms axioms = new Axioms(predicates);
		
		Assert.assertEquals(2, axioms.size());
		Assert.assertEquals(precondition1, axioms.get(0));
		Assert.assertEquals(postcondition1, axioms.get(1));
	}
	
	@Test
	public void addAxiomsFromAxioms(){
		Axioms predicates = new Axioms();
		predicates.add(precondition1);
		predicates.add(postcondition1);
		
		Axioms axioms = new Axioms();
		axioms.addAll(predicates);
		axioms.addAll(predicates);
		
		Assert.assertEquals(4, axioms.size());
		Assert.assertEquals(precondition1, axioms.get(0));
		Assert.assertEquals(postcondition1, axioms.get(1));
		Assert.assertEquals(precondition1, axioms.get(2));
		Assert.assertEquals(postcondition1, axioms.get(3));
	}

}
