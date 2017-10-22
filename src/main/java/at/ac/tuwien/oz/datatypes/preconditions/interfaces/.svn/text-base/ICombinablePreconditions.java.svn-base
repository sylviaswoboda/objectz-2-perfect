package at.ac.tuwien.oz.datatypes.preconditions.interfaces;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.PreconditionFunctionCalls;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public interface ICombinablePreconditions extends IComposablePreconditions{

	PreconditionFunctionCalls getSimplePreconditions();

	PreconditionFunctionCalls getCommunicatingPreconditions();

	Declarations getAllCommunicationVariables();

	Declarations getAllSharedVariables();

	Map<Variable, List<IPromotedOperation>> getAllCommunicatingOrSharingPromotions();
}
