package at.ac.tuwien;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Test;

public class AssertCollection {


	public static <T> void assertHasElements(Collection<T> actualCollection, T... expectedElements) {
		Assert.assertEquals(expectedElements.length, actualCollection.size());
		Assert.assertThat(actualCollection, IsCollectionContaining.hasItems(expectedElements));
	}
	
	public static <T> void assertHasElements(Collection<T> actualCollection, Collection<T> expectedElements) {
		Assert.assertEquals(expectedElements.size(), actualCollection.size());
		
		for(T expectedElement: expectedElements){
			Assert.assertTrue(actualCollection.contains(expectedElement));
		}
	}
	
	public static void assertStringsMatchWithPlaceholders(String actual, String expectedTemplate, String...placeholders){
		String patternFromTemplate = expectedTemplate.replaceAll("\\.", "\\\\."); // escape "." 
		patternFromTemplate = patternFromTemplate.replace("(", "\\(");
		patternFromTemplate = patternFromTemplate.replace(")", "\\)");
		patternFromTemplate = patternFromTemplate.replace("^", "\\^");

		for (String placeholder: placeholders){
			patternFromTemplate = patternFromTemplate.replace(placeholder, "(\\p{Alnum}+)"); // capturing group s
		}
		Pattern p = Pattern.compile(patternFromTemplate);
		Matcher m = p.matcher(actual);
		
		Assert.assertTrue(m.matches());
		
		List<String> replacements = new ArrayList<String>();
		
		for (int i = 1; i <= m.groupCount(); i++){
			String replacement = m.group(i);
			if (!replacements.contains(replacement)){
				replacements.add(replacement);
			}
		}
		String expectedWithReplacements = expectedTemplate; 
		int counter = 0; 
		for (String placeholder:placeholders){
			expectedWithReplacements = expectedWithReplacements.replace(placeholder, replacements.get(counter));
			counter++;
		}
		
		Assert.assertEquals(actual, expectedWithReplacements);
		
	}

	
	@Test
	public void testAssertMatchWithPlaceholdersAdvanced(){
		String pattern = "function parallelComp_prec \\(inVar1_in:nat, inVar2_in:nat\\): bool     "
				+ "\\^= \\(exists (\\p{Alnum}+):nat, (\\p{Alnum}+):nat, (\\p{Alnum}+):nat :- "
				+ "\\((\\p{Alnum}+) = b\\.aFuncCommInAndOutput2\\((\\p{Alnum}+), inVar2_in\\)\\.commVar1_out & ";
		String actual  = "function parallelComp_prec (inVar1_in:nat, inVar2_in:nat): bool     "
				+ "^= (exists tempVar3:nat, tempVar4:nat, tempVar5:nat :- "
				+ "(tempVar3 = b.aFuncCommInAndOutput2(tempVar4, inVar2_in).commVar1_out & ";
		
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(actual);
		
		Assert.assertTrue(m.matches());

	}
	
	@Test
	public void testAssertMatchWithPlaceholders(){
		String actualResult = "(exists tempVar1, tempVar2 :- " +
								"tempVar1 = a.func(tempVar2, inVar_in).outVar_out & " +
								"tempVar2 = b.func(tempVar1).outVar2_out)";
		String expectedTemplate = "(exists TEMP1, TEMP2 :- " +
								"TEMP1 = a.func(TEMP2, inVar_in).outVar_out & " +
								"TEMP2 = b.func(TEMP1).outVar2_out)";
		
		assertStringsMatchWithPlaceholders(actualResult, expectedTemplate, "TEMP1", "TEMP2");
		
	}
	
	@Test
	public void testMatches(){
		Pattern p = Pattern.compile("exists (\\p{Alnum}+) :-");
		
		Matcher m1 = p.matcher("exists tempVar1 :-");
		Assert.assertTrue(m1.matches());
		
		Assert.assertEquals("tempVar1", m1.group(1));
	}
}
