package at.ac.tuwien.oz.datatypes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;


public class VariableTest {

	private Ident id1;
	private Ident id2;
	
	@Mock private ExpressionContext typeExpr1;
	@Mock private ExpressionContext typeExpr2;
	
	@Mock private ExpressionType type1;
	@Mock private ExpressionType type2;
	
	@Before
	public void setup(){
		id1 = new Ident("someName");
		id2 = new Ident("someName");
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void twoVariablesWithSameIdAndTypeShouldBeEqual(){
		Variable var1 = new Variable(id1, typeExpr1);
		var1.setExpressionType(type1);
		Variable var2 = new Variable(id1, typeExpr1);
		var2.setExpressionType(type1);
		
		Assert.assertTrue(var1.equals(var2));
		Assert.assertTrue(var2.equals(var1));
	}

	@Test
	public void twoVariablesWithEqualIdAndSameTypeShouldBeEqual(){
		Variable var1 = new Variable(id1, typeExpr1);
		var1.setExpressionType(type1);
		Variable var2 = new Variable(id2, typeExpr1);
		var2.setExpressionType(type1);
		
		Assert.assertTrue(var1.equals(var2));
		Assert.assertTrue(var2.equals(var1));
	}

	@Test
	public void variableWithNotNullAndNullTypeShouldNotBeEqual(){
		Variable var1 = new Variable(id1, typeExpr1);
		var1.setExpressionType(type1);
		Variable var2 = new Variable(id1, null);
		
		Assert.assertFalse(var1.equals(var2));
		Assert.assertFalse(var2.equals(var1));
	}

	@Test
	public void variableWithEqualIdAndNullTypeShouldBeEqual(){
		Variable var1 = new Variable(id1, typeExpr1);
		Variable var2 = new Variable(id1, typeExpr1);
		
		Assert.assertTrue(var1.equals(var2));
		Assert.assertTrue(var2.equals(var1));
	}

}
