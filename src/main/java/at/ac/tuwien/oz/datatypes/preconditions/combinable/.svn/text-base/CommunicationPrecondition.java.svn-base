package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class CommunicationPrecondition implements ICombinablePreconditions{

	private PreconditionFunctionCalls preconditionFunctionCalls;
	protected CommunicationPreconditionMapping communicationPreconditionMapping;
	
	protected PerfectPredicateTemplateProvider templateProvider;

	protected CommunicationPrecondition(){
		preconditionFunctionCalls = new PreconditionFunctionCalls();
		
		templateProvider = PerfectPredicateTemplateProvider.getInstance();
	}
	
	public CommunicationPrecondition (CommunicationPreconditionMapping communicationMapping){
		this();
		this.communicationPreconditionMapping = communicationMapping;
	}
	
	public CommunicationPrecondition(PreconditionFunctionCalls simplePreconditions, CommunicationPreconditionMapping mapping){
		this();
		this.preconditionFunctionCalls = simplePreconditions;
		this.communicationPreconditionMapping = mapping;
	}
	
	public CommunicationPrecondition(CommunicationPrecondition communicationPrecondition) {
		this();
		this.communicationPreconditionMapping = communicationPrecondition.communicationPreconditionMapping;
	}
	
	public void addPreconditionFunctionCalls(PreconditionFunctionCalls functionCalls){
		this.preconditionFunctionCalls.addAll(functionCalls);
	}
	
	public PreconditionFunctionCalls getPreconditionFunctionCalls() {
		return this.preconditionFunctionCalls;
	}
	
	@Override
	public List<ST> getTemplates() {
		List<ST> templates = new ArrayList<ST>();
		templates.addAll(preconditionFunctionCalls.getTemplates());
		templates.add(communicationPreconditionMapping.getTemplate());
		return templates;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return preconditionFunctionCalls.size() + this.communicationPreconditionMapping.size();
	}
	
	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap) {
		List<ST> templates = new ArrayList<ST>();
		templates.addAll(preconditionFunctionCalls.getSequentialTemplates(tempVarMap));
		templates.add(communicationPreconditionMapping.getSequentialTemplate(tempVarMap));
		return templates;
	}

	@Override
	public PreconditionFunctionCalls getSimplePreconditions() {
		return this.preconditionFunctionCalls;
	}

	@Override
	public PreconditionFunctionCalls getCommunicatingPreconditions() {
		return this.communicationPreconditionMapping.getPreconditionsWithCommunication();
	}

	@Override
	public Declarations getAllCommunicationVariables() {
		return this.communicationPreconditionMapping.getAllCommunicationVariables();
	}

	@Override
	public Declarations getAllSharedVariables() {
		return this.communicationPreconditionMapping.getAllSharedVariables();
	}

	@Override
	public Map<Variable, List<IPromotedOperation>> getAllCommunicatingOrSharingPromotions() {
		return this.communicationPreconditionMapping.getAllCommunicatingAndSharingOperationPromotions();
	}
}
