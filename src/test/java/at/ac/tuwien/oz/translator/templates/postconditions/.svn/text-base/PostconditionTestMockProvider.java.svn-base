package at.ac.tuwien.oz.translator.templates.postconditions;

import org.mockito.Mockito;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class PostconditionTestMockProvider {

	private PerfectTemplateProvider perfect;
	
	public PostconditionTestMockProvider(){
		this.perfect = PerfectTemplateProvider.getInstance();
	}
	
	public ChangeOperationCall buildChangeOperationCall(String caller, String name, Declarations inputParameters, Declarations outputParameters) {
		OperationPromotion changeOp = buildSchemaPromotionMock(caller, name, inputParameters, outputParameters);
		ChangeOperationCall opCall = new ChangeOperationCall(changeOp);
		return opCall;
	}

	public OutputPromotion buildOutputPromotionMockInSchema(String caller, String name, Declarations inputParameters, Variable outputParameter) {
		OperationPromotion opPromo = buildFunctionPromotionMock(caller, name, inputParameters, outputParameter);
		OutputPromotion outputPromotion = new OutputPromotion(outputParameter, opPromo, OutputPromotionContext.SCHEMA);
		return outputPromotion;
	}
	
	public OperationPromotion buildFunctionPromotionMock(String caller, String name, Declarations inputParameters, Variable outputParameter) {
		Declarations outputParameters = new Declarations(outputParameter);
		OperationPromotion theMock =  buildPromotionMock(caller, name, inputParameters, outputParameters);
		Mockito.when(theMock.isFunction()).thenReturn(true);
		return theMock;
	}
	
	public OperationPromotion buildPromotionMock(String caller, String name, Declarations inputParameters, Declarations outputParameters) {
		IOperation operationMock = Mockito.mock(IOperation.class);
		Mockito.when(operationMock.getName()).thenReturn(name);
		Mockito.when(operationMock.getPostconditionFunctionName()).thenReturn(name);
		Mockito.when(operationMock.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(operationMock.getOutputParameters()).thenReturn(outputParameters);
		
		OperationPromotion operationPromotionMock = Mockito.mock(OperationPromotion.class);
		Mockito.when(operationPromotionMock.getInputParameters()).thenReturn(inputParameters);
		Mockito.when(operationPromotionMock.getOutputParameters()).thenReturn(outputParameters);
		Mockito.when(operationPromotionMock.getPromotedOperation()).thenReturn(operationMock);
		Mockito.when(operationPromotionMock.getCaller()).thenReturn(perfect.getId(new Ident(caller)));
		return operationPromotionMock;
	}
	public OperationPromotion buildSchemaPromotionMock(String caller, String name, Declarations inputParameters, Declarations outputParameters) {
		OperationPromotion theMock =  buildPromotionMock(caller, name, inputParameters, outputParameters);
		Mockito.when(theMock.isChangeOperation()).thenReturn(true);
		return theMock;
	}


}
