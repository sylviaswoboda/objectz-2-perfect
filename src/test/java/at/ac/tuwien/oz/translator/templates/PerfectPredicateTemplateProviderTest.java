package at.ac.tuwien.oz.translator.templates;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;
import at.ac.tuwien.oz.translator.TempVarProvider;

public class PerfectPredicateTemplateProviderTest {
	
	private PerfectPredicateTemplateProvider templateProvider;

	
	
	private ST caller;
	private Declarations inputParameters;
	
	@Mock private PreconditionFunction boolFunc;
	@Mock private Variable inputVariable1;
	@Mock private Variable inputVariable2;

	@Mock private Ident inputIdent1;
	@Mock private Ident inputIdent2;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		templateProvider = PerfectPredicateTemplateProvider.getInstance();
		caller = new ST("someCaller");
		inputParameters = new Declarations();		
		TempVarProvider.resetNameCounter();
		
		Mockito.when(boolFunc.getName()).thenReturn("boolFunc");
		Mockito.when(inputVariable1.isInputVariable()).thenReturn(true);
		Mockito.when(inputVariable2.isInputVariable()).thenReturn(true);
		Mockito.when(inputVariable1.getId()).thenReturn(inputIdent1);
		Mockito.when(inputVariable2.getId()).thenReturn(inputIdent2);
		Mockito.when(inputIdent1.getName()).thenReturn("inputVar1");
		Mockito.when(inputIdent1.getDecoration()).thenReturn("_in");
		Mockito.when(inputIdent2.getName()).thenReturn("inputVar2");
		Mockito.when(inputIdent2.getDecoration()).thenReturn("_in");
	}

	
	@Test
	public void testCreatePreconditionCallTemplate_WithCallerAndWithoutInputParameters() {
		
		Mockito.when(boolFunc.getInputParameters()).thenReturn(inputParameters);
		
		ST template = templateProvider.createFunctionCallTemplate(caller, boolFunc);
		
		Assert.assertEquals("someCaller.boolFunc", template.render());
	}

	@Test
	public void testCreatePreconditionCallTemplate_WithoutCallerAndWithoutInputParameters() {
		Mockito.when(boolFunc.getInputParameters()).thenReturn(inputParameters);
		
		ST template = templateProvider.createFunctionCallTemplate(null, boolFunc);
		
		Assert.assertEquals("boolFunc", template.render());
	}

	@Test
	public void testCreatePreconditionCallTemplate_WithOneInputParameter() {
		inputParameters.add(inputVariable1);
		
		Mockito.when(boolFunc.getInputParameters()).thenReturn(inputParameters);
		
		ST template = templateProvider.createFunctionCallTemplate(caller, boolFunc);
		
		Assert.assertEquals("someCaller.boolFunc(inputVar1_in)", template.render());
	}

	@Test
	public void testCreatePreconditionCallTemplate_WithMultipleInputParameters() {
		inputParameters.add(inputVariable1);
		inputParameters.add(inputVariable2);
		
		Mockito.when(boolFunc.getInputParameters()).thenReturn(inputParameters);
		
		ST template = templateProvider.createFunctionCallTemplate(caller, boolFunc);
		
		Assert.assertEquals("someCaller.boolFunc(inputVar1_in, inputVar2_in)", template.render());
	}
}
