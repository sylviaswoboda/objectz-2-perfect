package at.ac.tuwien.oz.translator.uat;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import at.ac.tuwien.oz.datatypes.Ident;

public class HashMapTest {

	
	
	@Test
	public void test() {
		HashMap<Ident, String> map = new HashMap<>();
		String p = "myTestValue";
		Ident i = new Ident("return");
		Ident i2 = new Ident("return");
		map.put(i, p);
		
		Assert.assertEquals(p, map.get(i));
		Assert.assertEquals(p, map.get(i2));
	}

}
