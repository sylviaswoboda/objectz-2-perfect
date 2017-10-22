package at.ac.tuwien.oz.translator.templates.preconditions;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class PreconditionTestMockProvider {
	
	@Mock ParseTreeProperty<ST> templateTree;

	public PreconditionFunctionCall buildPreconditionFunctionCallMock(String caller, String preconditionFunctionName, Declarations inputVariables) {
		MockitoAnnotations.initMocks(this);
		
		PreconditionFunction preconditionFunction = Mockito.mock(PreconditionFunction.class);
		
		ST callerTemplate = PerfectTemplateProvider.getInstance().getId(new Ident(caller));
		
		Mockito.when(preconditionFunction.getName()).thenReturn(preconditionFunctionName);
		Mockito.when(preconditionFunction.getInputParameters()).thenReturn(inputVariables);
		
		PreconditionFunctionCall preconditionCall = new PreconditionFunctionCall(callerTemplate, preconditionFunction);
		return preconditionCall;
	}
	
}
