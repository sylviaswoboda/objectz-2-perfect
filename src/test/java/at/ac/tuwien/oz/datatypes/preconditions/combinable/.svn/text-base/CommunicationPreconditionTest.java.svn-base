package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class CommunicationPreconditionTest extends PreconditionTestBase{

	@Mock private CommunicationPreconditionMapping mapping1;
	@Mock private CommunicationPreconditionMapping mapping2;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void buildFromMappingHasOnlyExactlyThisMapping(){
		CommunicationPrecondition communicationPrecondition = new CommunicationPrecondition(mapping1);
		
		Assert.assertEquals(communicationPrecondition.communicationPreconditionMapping, mapping1);
	}
	
	@Test
	public void buildFromMappingHasEmptyPreconditionFunctionCalls(){
		CommunicationPrecondition communicationPrecondition = new CommunicationPrecondition(mapping1);
		
		Assert.assertEquals(0, communicationPrecondition.getPreconditionFunctionCalls().size());
	}
	
	@Test
	public void copyConstructorRefersToSameMapping(){
		CommunicationPrecondition communicationPrecondition = new CommunicationPrecondition(mapping1);
		
		CommunicationPrecondition copy = new CommunicationPrecondition(communicationPrecondition);
		Assert.assertEquals(mapping1, copy.communicationPreconditionMapping);
	}
	
}
