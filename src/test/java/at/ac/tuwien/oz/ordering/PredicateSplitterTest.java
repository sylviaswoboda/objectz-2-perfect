package at.ac.tuwien.oz.ordering;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class PredicateSplitterTest{
	
	private PredicateSplitter predicateSplitter;
	private ST a;
	private ST b;
	private ST c;
	private ST zero;
	private ST num1;
	private ST num2;
	
	private static PerfectTemplateProvider perfect;

	@BeforeClass
	public static void setupClass(){
		perfect = PerfectTemplateProvider.getInstance();
	}
	
	@Before
	public void setup(){
		predicateSplitter = new PredicateSplitter();
		
		a = perfect.getId(new Ident("a"));
		zero = perfect.getNumber("0");
		
		b = perfect.getId(new Ident("b"));
		num1 = perfect.getNumber("200");

		c = perfect.getId(new Ident("c"));
		num2 = perfect.getNumber("15");
	}

	@Test
	public void nothingToSplitSimple(){
		ST predicate = perfect.getEquivalence(a, b);
		List<ST> predicateList = predicateSplitter.split(predicate);
		
		Assert.assertEquals(1, predicateList.size());
		Assert.assertEquals(predicate, predicateList.get(0));
	}
	
	@Test
	public void singleConjunction(){
		ST left = perfect.getGreaterThan(a, zero);
		ST right = perfect.getEquals(b, num1);
		
		ST predicate = perfect.getConjunction(left, right);
		List<ST> predicateList = predicateSplitter.split(predicate);
		
		Assert.assertEquals(2, predicateList.size());
		Assert.assertEquals("a > 0", predicateList.get(0).render());
		Assert.assertEquals("b = 200", predicateList.get(1).render());
	}
	
	@Test
	public void doubleConjunction(){
		ST left = perfect.getGreaterThan(a, zero);
		ST middle = perfect.getEquals(b, num1);
		ST right = perfect.getLessThanEquals(c, num2);
		
		ST predicateL = perfect.getConjunction(left, middle);
		ST predicate = perfect.getConjunction(predicateL, right);
		
		List<ST> predicateList = predicateSplitter.split(predicate);
		
		Assert.assertEquals(3, predicateList.size());
		Assert.assertEquals("a > 0", predicateList.get(0).render());
		Assert.assertEquals("b = 200", predicateList.get(1).render());
		Assert.assertEquals("c <= 15", predicateList.get(2).render());
	}
	
	@Test
	public void innerConjunction(){
		ST conjunction = perfect.getConjunction(a, b);
		ST equivalence = perfect.getEquivalence(conjunction, c);

		List<ST> predicateList = predicateSplitter.split(equivalence);
		
		Assert.assertEquals(1, predicateList.size());
		Assert.assertEquals(equivalence, predicateList.get(0));
		
		conjunction = perfect.getConjunction(b, c);
		equivalence = perfect.getEquivalence(a, conjunction);
		
		predicateList = predicateSplitter.split(equivalence);
		
		Assert.assertEquals(1, predicateList.size());
		Assert.assertEquals(equivalence, predicateList.get(0));

	}

}
