package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class EmptyPreconditions implements ICombinablePreconditions {

	private static final EmptyPreconditions EMPTY_PRECONDITIONS = new EmptyPreconditions();

	public static final EmptyPreconditions empty(){
		return EMPTY_PRECONDITIONS;
	}

	@Override
	public List<ST> getTemplates() {
		return Arrays.asList();
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public int size(){
		return 0;
	}
	
	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap) {
		return getTemplates();
	}

	@Override
	public PreconditionFunctionCalls getSimplePreconditions() {
		return PreconditionFunctionCalls.empty();
	}

	@Override
	public PreconditionFunctionCalls getCommunicatingPreconditions() {
		return PreconditionFunctionCalls.empty();
	}

	@Override
	public Declarations getAllCommunicationVariables() {
		return Declarations.empty();
	}

	@Override
	public Declarations getAllSharedVariables() {
		return Declarations.empty();
	}

	@Override
	public Map<Variable, List<IPromotedOperation>> getAllCommunicatingOrSharingPromotions() {
		return new HashMap<Variable, List<IPromotedOperation>>();
	}
}