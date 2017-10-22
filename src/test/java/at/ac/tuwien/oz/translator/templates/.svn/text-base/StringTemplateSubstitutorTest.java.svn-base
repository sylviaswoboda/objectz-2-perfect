package at.ac.tuwien.oz.translator.templates;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Ident;

public class StringTemplateSubstitutorTest {

	private StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
	private PerfectTemplateProvider perfect = PerfectTemplateProvider.getInstance();
	
	@Test
	public void substituteInIdent(){
		ST leftId = perfect.getId(new Ident("left"));
		ST rightId = perfect.getId(new Ident("right"));
		
		ST add = perfect.getAddition(leftId, rightId);
		
		ST newLeftId = perfect.getId(new Ident("left", "!"));
		
		substitutor.substituteIdentifier(add, leftId, newLeftId);
		
		Assert.assertEquals("left_out + right", add.render());
	}
	
	@Test
	public void substituteInCollection(){
		ST item1 = perfect.getId(new Ident("item1"));
		ST item2 = perfect.getId(new Ident("item2"));
		ST item3 = perfect.getId(new Ident("item3"));
		ST item4 = perfect.getId(new Ident("item4"));
		
		ST list = perfect.getItemList(Arrays.asList(item1, item2, item3, item4, item1));
		
		ST newItem = perfect.getId(new Ident("newItem"));
		
		substitutor.substituteIdentifier(list, item1, newItem);
		
		Assert.assertEquals("newItem, item2, item3, item4, newItem", list.render());
	}
	
	@Test
	public void substituteOutputIdent() {
		ST outVar1 = perfect.getId(new Ident("outVar1", "!"));
		ST stateVar1 = perfect.getId(new Ident("stateVar1"));
		ST stateVar2 = perfect.getId(new Ident("stateVar2"));
		
		ST add = perfect.getAddition(stateVar1, stateVar2);
		ST equals = perfect.getEquals(outVar1, add);
		
		Ident outputIdent = new Ident("outVar1", "!");
		substitutor.substituteOutputIdent(equals, outputIdent);
		
		Assert.assertEquals("result.outVar1_out = stateVar1 + stateVar2", equals.render());
	}

	@Test
	public void substituteBooleanOutputIdent() {
		ST booleanOutVar1 = perfect.getId(new Ident("booleanOutVar1", "!"));
		ST trueST = perfect.getTrue();
		
		ST equivalence = perfect.getEquivalence(booleanOutVar1, trueST);
		
		Ident outputIdent = new Ident("booleanOutVar1", "!");
		substitutor.substituteOutputIdent(equivalence, outputIdent);
		
		Assert.assertEquals("result.booleanOutVar1_out <==> true", equivalence.render());
	}

	@Test
	public void substituteOutputIdentInCollection() {
		Ident outputIdent1 = new Ident("outputIdent1", "!");
		Ident outputIdent2 = new Ident("outputIdent2", "!");
		Ident outputIdent3 = new Ident("outputIdent3", "!");	// this is a set
		
		ST left = perfect.getId(outputIdent3);
		ST right = perfect.getSet(Arrays.asList(perfect.getId(outputIdent1), perfect.getId(outputIdent2)), perfect.getNat());
		ST equals = perfect.getEquals(left, right);
		
		substitutor.substituteOutputIdent(equals, outputIdent1);
		substitutor.substituteOutputIdent(equals, outputIdent2);
		substitutor.substituteOutputIdent(equals, outputIdent3);
		
		Assert.assertEquals("result.outputIdent3_out = set of nat {result.outputIdent1_out, result.outputIdent2_out}", equals.render());
	}

}
