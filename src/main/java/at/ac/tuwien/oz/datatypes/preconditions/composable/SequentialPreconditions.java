package at.ac.tuwien.oz.datatypes.preconditions.composable;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class SequentialPreconditions implements IComposablePreconditions{

	private List<IComposablePreconditions> preconditions;

	// communication from first to second, second to third postcondition a.s.o.
	private List<Declarations> communicationVariables;
	
	private PerfectPredicateTemplateProvider templateProvider;
	
	private SequentialPreconditions(){
		this.templateProvider = PerfectPredicateTemplateProvider.getInstance();
	}
	
	public SequentialPreconditions(List<IComposablePreconditions> preconditions, List<Declarations> communicationVariables) {
		this();
		this.preconditions = preconditions;
		this.communicationVariables = communicationVariables;
	}

	@Override
	public List<ST> getTemplates() {
		List<ST> templates = new ArrayList<ST>();
		templates.add(templateProvider.createExistsPrecondition(this));
		return templates;
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap) {
		List<ST> templates = new ArrayList<ST>();
		templates.add(templateProvider.createExistsPrecondition(this, tempVarMap));
		return templates;
	}

	@Override
	public boolean isEmpty() {
		return preconditions.isEmpty();
	}

	@Override
	public int size() {
		return preconditions.size();
	}

	public Declarations getAllCommunicationVariables() {
		Declarations allCommunicationVariables = new Declarations();
		
		for (Declarations partCommunicationVariable: communicationVariables){
			allCommunicationVariables.addAll(partCommunicationVariable);
		}
		return allCommunicationVariables;
	}

	public List<Declarations> getCommunicationVariables() {
		return this.communicationVariables;
	}

	public List<IComposablePreconditions> getPreconditions() {
		return this.preconditions;
	}

}
