package at.ac.tuwien.oz.datatypes.postconditions.interfaces;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ChangeOperationCalls;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;

/**
 * to be implemented by postconditions that can be combined that is conjunction, (associative) parallel composition
 * 
 * @author sylvias
 *
 */
public interface ICombinablePostconditions extends IComposablePostconditions{

	Declarations getSharedOutputVariables();
	Declarations getVisibleCommunicationVariables();
	Declarations getAllCommunicationVariables();
	
	OutputPromotions getSimplePromotions();
	OutputPromotions getCommunicatingPromotions();
	
	ChangeOperationCalls getSimpleCalls();
	ChangeOperationCalls getCommunicatingCalls();
	ChangeOperationCalls getUncategorizedCalls();

	boolean isChangePostcondition();
}
