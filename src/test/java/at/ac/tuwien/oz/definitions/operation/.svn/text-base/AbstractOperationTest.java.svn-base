package at.ac.tuwien.oz.definitions.operation;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;


public class AbstractOperationTest {
	
	protected static final String IRRELEVANT_NAME = "irrelevantName";
	
	@Mock private Variable inputVar1;
	@Mock private Variable inputVar2;
	@Mock private Variable inputVar3;
	
	@Mock private Variable outputVar1;
	@Mock private Variable outputVar2;
	
	@Mock private Variable primedVar1;
	
	@Mock private Variable auxiliaryVar1;
	@Mock private Variable auxiliaryVar2;
	
	@Mock private Ident outputIdent1;
	@Mock private Ident outputIdent2;
	
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(outputVar1.getId()).thenReturn(outputIdent1);
		Mockito.when(outputVar2.getId()).thenReturn(outputIdent2);
		
		Mockito.when(inputVar1.isInputVariable()).thenReturn(true);
		Mockito.when(inputVar2.isInputVariable()).thenReturn(true);
		Mockito.when(inputVar3.isInputVariable()).thenReturn(true);

		Mockito.when(outputVar1.isOutputVariable()).thenReturn(true);
		Mockito.when(outputVar2.isOutputVariable()).thenReturn(true);
		Mockito.when(primedVar1.isPrimdVariable()).thenReturn(true);
		
	}

}
