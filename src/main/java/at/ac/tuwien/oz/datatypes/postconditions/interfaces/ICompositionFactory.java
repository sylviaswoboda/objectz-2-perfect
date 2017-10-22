package at.ac.tuwien.oz.datatypes.postconditions.interfaces;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;

public interface ICompositionFactory {

	ICombinablePostconditions compose(ICombinablePostconditions left, ICombinablePostconditions right, Declarations communicationVariables, Declarations sharedVariables, boolean isAssociative);
	ICombinablePostconditions conjoin(ICombinablePostconditions left, ICombinablePostconditions right, Declarations sharedVariables);
	IComposablePostconditions nondeterministicallyChoose(IComposableOperation leftOp, IComposableOperation rightOp, Context context);

	SequentialPostconditions sequentiallyCompose(IComposableOperation left, IComposableOperation right, Declarations communicationVariables, Declarations sharedOutputVariables, Context context);
	
}
