package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Elements;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class PreconditionFunctionCalls extends Elements<PreconditionFunctionCall> implements ICombinablePreconditions {

	private static final PreconditionFunctionCalls EMPTY_FUNCTION_CALLS = new PreconditionFunctionCalls();

	public static PreconditionFunctionCalls empty() {
		return EMPTY_FUNCTION_CALLS;
	}

	public PreconditionFunctionCalls(){
		this.elements = new ArrayList<PreconditionFunctionCall>();
	}
	
	public PreconditionFunctionCalls(PreconditionFunctionCalls preconditionFunctionCalls) {
		this();
		this.elements.addAll(preconditionFunctionCalls.elements);
	}
	public PreconditionFunctionCalls(PreconditionFunctionCall... preconditionFunctionCalls) {
		this();
		this.addNew(preconditionFunctionCalls);
	}

	
	@Override
	public List<ST> getTemplates() {
		List<ST> templates= new ArrayList<ST>();
		for(IPrecondition precondition: this.elements){
			templates.add(precondition.getTemplate());
		}
		return templates;
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap) {
		List<ST> templates= new ArrayList<ST>();
		for(PreconditionFunctionCall precondition: this.elements){
			templates.addAll(precondition.getTemplate(tempVarMap));
		}
		return templates;
	}

	public boolean addAll(PreconditionFunctionCalls newElements){
		if (newElements != null){
			return this.addAll(newElements.asList());
		}
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	@Override
	public PreconditionFunctionCalls getSimplePreconditions() {
		return this;
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
