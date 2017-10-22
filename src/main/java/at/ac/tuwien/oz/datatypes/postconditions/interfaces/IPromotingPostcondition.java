package at.ac.tuwien.oz.datatypes.postconditions.interfaces;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public interface IPromotingPostcondition {

	boolean usesOutputParameters(Declarations outputParameters);
	boolean usesCommunicationVariables(Declarations communicationVariables);

	IPromotedOperation getPromotedOperation();
	
	

}
