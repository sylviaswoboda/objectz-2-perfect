package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public class SimpleChangeOperationTest {

	private String stateVar1;
	private String stateVar2;
	private String stateVar3;
	
	private List<String> deltalist;
	@Mock private ObjectZClass enclosingClass;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		deltalist = new ArrayList<String>();
		deltalist.add(stateVar1);
		deltalist.add(stateVar2);
		deltalist.add(stateVar3);
	}
	
	@Test
	public void operationTypeIsAlwaysChangeOperation() {
		IOperation operation = new SimpleChangeOperation(AbstractOperationTest.IRRELEVANT_NAME, null, null, null, null, null);

		Assert.assertTrue(operation.isChangeOperation());
		Assert.assertFalse(operation.isFunction());
		Assert.assertFalse(operation.isBoolFunction());
	}

	@Test
	public void deltalistIsAlwaysProvidedAsParameter() {
		
		IOperation operation = new SimpleChangeOperation(AbstractOperationTest.IRRELEVANT_NAME, deltalist, null, null, enclosingClass, null);
		
		List<String> deltaListActual = operation.getDeltalist();
		Assert.assertEquals(3, deltaListActual.size());
		Assert.assertEquals(stateVar1, deltaListActual.get(0));
		Assert.assertEquals(stateVar2, deltaListActual.get(1));
		Assert.assertEquals(stateVar3, deltaListActual.get(2));
	}

}
