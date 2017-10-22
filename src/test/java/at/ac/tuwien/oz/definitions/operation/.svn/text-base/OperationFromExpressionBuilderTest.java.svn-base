package at.ac.tuwien.oz.definitions.operation;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;


public class OperationFromExpressionBuilderTest {
	
	private OperationBuilder operationBuilder;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		operationBuilder = new OperationBuilder();
	}

	@Test
	public void getAnonymousOperationPromotion(){
		CallerContext callerCtx = Mockito.mock(CallerContext.class);
		String calledOperationName = "name";
		
		IPromotedOperation operationPromotion = operationBuilder.getAnonymousOperationPromotion(callerCtx, calledOperationName);

		Assert.assertEquals(callerCtx, operationPromotion.getCallerCtx());
		Assert.assertEquals("anonymous", operationPromotion.getId().getName());
	}

}
