package at.ac.tuwien.oz.datatypes;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class ExpressionTypeTest {

	@Test
	public void getTemplateOfPair(){
		ExpressionType pair = ExpressionType.getTuple(Arrays.asList(ExpressionType.getNat(), ExpressionType.getNat()));
		
		Assert.assertEquals("pair of (nat, nat)", pair.getTemplate().render());
	}

	@Test
	public void getTemplateOfSetOfPair(){
		ExpressionType pair = ExpressionType.getTuple(Arrays.asList(ExpressionType.getNat(), ExpressionType.getNat()));
		ExpressionType set = ExpressionType.getSet(pair);
		
		Assert.assertEquals("set of pair of (nat, nat)", set.getTemplate().render());
	}

	@Test
	public void getTemplateOfRelation(){
		ExpressionType relation = ExpressionType.getRelation(ExpressionType.getNat(), ExpressionType.getNat());
		
		Assert.assertEquals("set of pair of (nat, nat)", relation.getTemplate().render());
	}

}
