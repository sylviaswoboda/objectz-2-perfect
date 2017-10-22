package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class CompositePreconditionDataCollector {

	private ICombinableOperation leftOp;
	private ICombinableOperation rightOp;

	private ICombinablePreconditions leftPreconditions;
	private ICombinablePreconditions rightPreconditions;
	
	private Declarations newSharedOutputVariables;
	private Declarations newCommunicationVariables;
	
	private PreconditionFunctionCalls simplePreconditions;
	private PreconditionFunctionCalls communicatingPreconditions;
	private Map<Variable, List<IPromotedOperation>> allCommunicatingOrSharingOperations;
	
	private Declarations allCommunicationVars;
	private Declarations sharedOutputVars;

	public CompositePreconditionDataCollector(ICombinableOperation leftOp, ICombinableOperation rightOp){
		this.allCommunicationVars = new Declarations();
		this.sharedOutputVars = new Declarations();
		
		this.simplePreconditions = new PreconditionFunctionCalls();
		this.communicatingPreconditions = new PreconditionFunctionCalls();
		this.allCommunicatingOrSharingOperations = new HashMap<Variable, List<IPromotedOperation>>();
		
		this.leftOp = leftOp;
		this.rightOp = rightOp;
		
		this.leftPreconditions = leftOp.getCombinablePreconditions();
		this.rightPreconditions = rightOp.getCombinablePreconditions();
	}
	
	public CompositePreconditionDataCollector conjoin(Declarations newSharedOutputVariables){
		this.newSharedOutputVariables = newSharedOutputVariables;
		this.newCommunicationVariables = new Declarations();
		
		addVariables();
		
		this.addSimplePreconditions(leftPreconditions.getSimplePreconditions());
		this.addSimplePreconditions(rightPreconditions.getSimplePreconditions());
		
		this.addCommunicatingPreconditions(leftPreconditions.getCommunicatingPreconditions());
		this.addCommunicatingPreconditions(rightPreconditions.getCommunicatingPreconditions());
		
		this.addCommunicatingOrSharingOperations(leftPreconditions.getAllCommunicatingOrSharingPromotions());
		this.addCommunicatingOrSharingOperations(rightPreconditions.getAllCommunicatingOrSharingPromotions());
		
		addNewSharingOperations();
		
		return this;
	}
	
	public CompositePreconditionDataCollector compose(Declarations newCommunicationVariables, Declarations newSharedOutputVariables) {
		
		this.newSharedOutputVariables = newSharedOutputVariables;
		this.newCommunicationVariables = newCommunicationVariables;
		
		addVariables();
		
		this.splitPreconditionFunctionCalls(leftPreconditions.getSimplePreconditions(), newCommunicationVariables);
		this.splitPreconditionFunctionCalls(rightPreconditions.getSimplePreconditions(), newCommunicationVariables);
		
		this.addCommunicatingPreconditions(leftPreconditions.getCommunicatingPreconditions());
		this.addCommunicatingPreconditions(rightPreconditions.getCommunicatingPreconditions());
		
		this.addCommunicatingOrSharingOperations(leftPreconditions.getAllCommunicatingOrSharingPromotions());
		this.addCommunicatingOrSharingOperations(rightPreconditions.getAllCommunicatingOrSharingPromotions());

		addNewSharingOperations();
		addNewCommunicatingOperations();

		return this;
	}

	private void splitPreconditionFunctionCalls(PreconditionFunctionCalls preconditions, Declarations communicationVariables) {
		if (preconditions != null){
			for (PreconditionFunctionCall precondition: preconditions.asList()){
				if (precondition.usesCommunicationVariables(communicationVariables)){
					this.communicatingPreconditions.add(precondition);
				} else {
					this.simplePreconditions.add(precondition);
				}
			}
		}
	}
	
	private void addNewSharingOperations() {
		for (Variable var: newSharedOutputVariables.asList()){
			List<IPromotedOperation> promotedOperations = this.allCommunicatingOrSharingOperations.get(var);
			if (promotedOperations == null){
				promotedOperations = new ArrayList<IPromotedOperation>();
				this.allCommunicatingOrSharingOperations.put(var, promotedOperations);
			}
			promotedOperations.addAll(leftOp.getPromotedOperationsWithOutputParameter(var, true));
			promotedOperations.addAll(rightOp.getPromotedOperationsWithOutputParameter(var, true));
		}
	}
	
	private void addNewCommunicatingOperations() {
		for (Variable var: newCommunicationVariables.asList()){
			List<IPromotedOperation> promotedOperations = this.allCommunicatingOrSharingOperations.get(var);
			if (promotedOperations == null){
				promotedOperations = new ArrayList<IPromotedOperation>();
				this.allCommunicatingOrSharingOperations.put(var, promotedOperations);
			}
			promotedOperations.addAll(leftOp.getPromotedOperationsWithOutputParameter(var, true));
			promotedOperations.addAll(rightOp.getPromotedOperationsWithOutputParameter(var, true));
		}
	}

	private void addCommunicatingOrSharingOperations(Map<Variable, List<IPromotedOperation>> allCommunicatingOrSharingPromotions) {
		for (Variable var: allCommunicatingOrSharingPromotions.keySet()){
			List<IPromotedOperation> promotedOperations = this.allCommunicatingOrSharingOperations.get(var);
			if (promotedOperations == null){
				promotedOperations = new ArrayList<IPromotedOperation>();
				this.allCommunicatingOrSharingOperations.put(var, promotedOperations);
			}
			promotedOperations.addAll(allCommunicatingOrSharingPromotions.get(var));
		}
	}

	private void addVariables() {
		this.addCommunicationVars();
		this.addSharedOutputVars();
	}

	private void addCommunicationVars() {
		this.allCommunicationVars.addAll(this.newCommunicationVariables);
		this.allCommunicationVars.addAll(leftPreconditions.getAllCommunicationVariables());
		this.allCommunicationVars.addAll(rightPreconditions.getAllCommunicationVariables());
	}
	
	private void addSharedOutputVars() {
		this.sharedOutputVars.addAll(newSharedOutputVariables);
		this.sharedOutputVars.addAll(leftPreconditions.getAllSharedVariables());
		this.sharedOutputVars.addAll(rightPreconditions.getAllSharedVariables());
	}

	private void addSimplePreconditions(PreconditionFunctionCalls preconditionsFunctionCalls){
		this.simplePreconditions.addAll(preconditionsFunctionCalls);
	}

	private void addCommunicatingPreconditions(PreconditionFunctionCalls preconditionFunctionCalls) {
		this.communicatingPreconditions.addAll(preconditionFunctionCalls);
	}
	public Declarations getSharedOutputVariables() {
		return this.sharedOutputVars;
	}

	public Declarations getCommunicationVariables() {
		return this.allCommunicationVars;
	}

	public PreconditionFunctionCalls getSimplePreconditions() {
		return this.simplePreconditions;
	}
	
	public PreconditionFunctionCalls getCommunicatingPreconditions() {
		return this.communicatingPreconditions;
	}

	public Map<Variable, List<IPromotedOperation>> getAllCommunicatingOrSharingOperations() {
		return this.allCommunicatingOrSharingOperations;
	}
}
