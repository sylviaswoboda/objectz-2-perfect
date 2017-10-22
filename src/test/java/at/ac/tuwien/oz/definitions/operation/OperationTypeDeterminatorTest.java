package at.ac.tuwien.oz.definitions.operation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;


public class OperationTypeDeterminatorTest {
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void boolFunctionHasOnlyPreconditions(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(false, false, true, false);
		OperationType type = determinator.getOperationType();
		
		Assert.assertEquals(OperationType.BOOL_FUNCTION, type);
	}

	@Test
	public void functionHasOutputParametersAndPostconditions(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(false, true, false, true);
		OperationType type = determinator.getOperationType();
		
		Assert.assertEquals(OperationType.FUNCTION, type);
	}

	@Test
	public void functionMayHavePreconditionsAdditionally(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(false, true, true, true);
		OperationType type = determinator.getOperationType();
		
		Assert.assertEquals(OperationType.FUNCTION, type);
	}

	@Test
	public void changeOperationHasDeltaListAndPostcondition(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(true, false, false, true);
		OperationType type = determinator.getOperationType();
		
		Assert.assertEquals(OperationType.CHANGE_OPERATION, type);
	}

	@Test
	public void changeOperationMayHavePreconditionsAdditionally(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(true, false, true, true);
		OperationType type = determinator.getOperationType();
		
		Assert.assertEquals(OperationType.CHANGE_OPERATION, type);
	}

	@Test
	public void changeOperationMayHaveOutputParametersAdditionally(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(true, true, true, true);
		OperationType type = determinator.getOperationType();
		
		Assert.assertEquals(OperationType.CHANGE_OPERATION, type);
	}

	@Test
	public void havingNoPredicatesIsDeclarationSchema(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(false, false, false, false);
		OperationType type = determinator.getOperationType();
		
		Assert.assertEquals("operations without predicates is delcaration schem", OperationType.DECLARATION_SCHEMA, type);
	}
	
	@Test
	public void outputVariablesIsDeclarationSchema(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(false, true, false, false);
		OperationType type = determinator.getOperationType();
		
		Assert.assertEquals("operations without predicates is delcaration schem", OperationType.DECLARATION_SCHEMA, type);
	}
	
	@Test
	public void deltalistWithoutPostconditionsIsInvalid(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(true, false, false, false);
		OperationType type = determinator.getOperationType();
		
		Assert.assertNull("Operation with deltalist needs postconditions as well", type);
	}


	@Test
	public void postconditionWithoutOutputVariablesOrDeltalistIsInvalid(){
		OperationTypeDeterminator determinator = new OperationTypeDeterminator(false, false, false, true);
		OperationType type = determinator.getOperationType();
		
		Assert.assertNull("Operation with postcondition must have output parameters and/or a deltalist", type);
	}

}
