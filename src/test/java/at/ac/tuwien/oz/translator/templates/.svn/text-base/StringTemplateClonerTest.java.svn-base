package at.ac.tuwien.oz.translator.templates;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Ident;

public class StringTemplateClonerTest {

	private PerfectTemplateProvider perfect;
	private StringTemplateCloner cloner;
	
	@Before
	public void setup(){
		perfect = PerfectTemplateProvider.getInstance();
		cloner = new StringTemplateCloner();
	}
	
	@Test
	public void simpleTemplate(){
		ST id = perfect.getId(new Ident("testName"));
		ST idClone = cloner.clone(id);
		
		Assert.assertEquals(id.render(), idClone.render());
		Assert.assertFalse(idClone == id);
		Assert.assertEquals("testName", idClone.getAttribute("name"));
		Assert.assertEquals("", idClone.getAttribute("decoration"));
	}
	
	@Test
	public void conjunction(){
		ST left = perfect.getId(new Ident("testName1"));
		ST right = perfect.getId(new Ident("testName2", "?"));
		
		ST conjunction = perfect.getConjunction(left, right);
		ST conjunctionClone = cloner.clone(conjunction);
		
		Assert.assertEquals(conjunction.render(), conjunctionClone.render());
		Assert.assertFalse(conjunctionClone == conjunction);
		Assert.assertFalse(left == conjunctionClone.getAttribute("left"));
		Assert.assertFalse(right == conjunctionClone.getAttribute("right"));
	}
	
	@Test
	public void setOfST(){
		ST elem1 = perfect.getId(new Ident("t1", ""));
		ST elem2 = perfect.getId(new Ident("t2", "?"));
		ST elem3 = perfect.getId(new Ident("t3", "!"));
		
		List<ST> elements = Arrays.asList(elem1, elem2, elem3);
		
		ST set = perfect.getSet(elements, perfect.getNat());
		
		ST setClone = cloner.clone(set);
		
		Assert.assertEquals(set.render(), setClone.render());
		Assert.assertFalse(setClone == set);
		@SuppressWarnings("unchecked")
		List<ST> elementClones = (List<ST>) setClone.getAttribute("elems");
		Assert.assertEquals(3, elementClones.size());
		Assert.assertFalse(elem1 == elementClones.get(0));
		Assert.assertFalse(elem2 == elementClones.get(1));
		Assert.assertFalse(elem3 == elementClones.get(2));
		Assert.assertEquals(elem1.render(), elementClones.get(0).render());
		Assert.assertEquals(elem2.render(), elementClones.get(1).render());
		Assert.assertEquals(elem3.render(), elementClones.get(2).render());
	}

}
