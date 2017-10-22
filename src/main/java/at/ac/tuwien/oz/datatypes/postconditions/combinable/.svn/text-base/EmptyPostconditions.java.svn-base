package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.Arrays;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;

public class EmptyPostconditions extends CombinedPostconditions {
	
	private static final EmptyPostconditions EMPTY_POSTCONDITIONS = new EmptyPostconditions();
	
	public static final EmptyPostconditions empty(){
		return EMPTY_POSTCONDITIONS;
	}
	
	private EmptyPostconditions(){
	}

	@Override
	public List<ST> getTemplates() {
		return Arrays.asList();
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap, Context context) {
		return getTemplates();
	}

	@Override
	public Idents getUsedIdentifiers() {
		return new Idents();
	}

	@Override
	public Declarations getSharedOutputVariables() {
		return Declarations.empty();
	}

	@Override
	public Declarations getVisibleCommunicationVariables() {
		return Declarations.empty();
	}

	@Override
	public Declarations getAllCommunicationVariables() {
		return Declarations.empty();
	}

	@Override
	public OutputPromotions getSimplePromotions() {
		return OutputPromotions.empty();
	}

	@Override
	public OutputPromotions getCommunicatingPromotions() {
		return OutputPromotions.empty();
	}

	@Override
	public ChangeOperationCalls getSimpleCalls() {
		return ChangeOperationCalls.empty();
	}

	@Override
	public ChangeOperationCalls getCommunicatingCalls() {
		return ChangeOperationCalls.empty();
	}

	@Override
	public ChangeOperationCalls getUncategorizedCalls() {
		return ChangeOperationCalls.empty();
	}

	@Override
	public boolean isChangePostcondition() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

}
