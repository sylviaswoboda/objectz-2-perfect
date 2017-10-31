package at.ac.tuwien.oz.datatypes.postconditions.interfaces;

import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;

/**
 * to be implemented by classes that represent postconditions that can be combined with other
 * postconditions to compose a new postcondition.
 * (conjunction, parallel composition, non deterministic choice, sequential composition a.s.o)
 * 
 * @author sylvias
 *
 */
public interface IComposablePostconditions extends IPostconditions {

	/**
	 * 
	 * @param outerTempVarMap - contains the communication variables that are being replaced by inputs or outputs
	 * @param context		  - the context where a composable postcondition is used (function or schema)
	 * 
	 * @return A template of the composable postcondition that replaces all input and output variable occurrences of 
	 *         variables in the outerTempVarMap by the corresponding tempVar.
	 */
	List<ST> getSequentialTemplates(TemporaryVariableMap outerTempVarMap, Context context);

	IComposablePostconditions getWithoutPromotions(Declarations sharedOutputVariables);

	boolean isSequential();

	SequentialPostconditions getSequentialPostconditions();

	boolean isEmpty();
	
	
}
