package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

/** 
 * Containerclass for all communicating and sharing preconditions and the necessary promoted operations to refer to for 
 * additional preconditions due to sharing or communication
 * 
 * @author sylvias
 *
 */
public class CommunicationPreconditionMapping{
	private PreconditionFunctionCalls preconditionsWithCommunication; 
	
	private Declarations allCommunicationVariables;
	private Declarations allSharedVariables;
	private Map<Variable, List<IPromotedOperation>> allCommunicatingOrSharingOperations;
	
	private CommunicationPreconditionMapping(){
		this.allCommunicationVariables = new Declarations();
		this.allSharedVariables = new Declarations();
		this.allCommunicatingOrSharingOperations = new HashMap<Variable, List<IPromotedOperation>>();
		
		this.preconditionsWithCommunication = new PreconditionFunctionCalls();
	}
	
	/**
	 * @param communicatingPreconditions
	 * @param sharedVariables
	 * @param communicationVariables
	 * @param allCommunicatingOrSharingOperations
	 */
	public CommunicationPreconditionMapping(PreconditionFunctionCalls communicatingPreconditions, 
			Declarations sharedVariables,
			Declarations communicationVariables,
			Map<Variable, List<IPromotedOperation>> allCommunicatingOrSharingOperations){
		this();
		this.allCommunicationVariables.addAll(communicationVariables);
		this.allSharedVariables.addAll(sharedVariables);
		this.allCommunicatingOrSharingOperations.putAll(allCommunicatingOrSharingOperations);
		this.preconditionsWithCommunication = communicatingPreconditions;
	}
	
	/**
	 * Returns all sharing operation Promotions for the given output variable
	 * 
	 * @param sharedOutputVariable - to look for, has to have output decoration
	 * 
	 * @return a list of all matching shared output promotions, may be empty
	 */
	public List<IPromotedOperation> getPromotingOperations(Variable sharedOutputOrCommunicationVariable) {
			if (allCommunicatingOrSharingOperations.containsKey(sharedOutputOrCommunicationVariable)){	
				return allCommunicatingOrSharingOperations.get(sharedOutputOrCommunicationVariable);
			}
			return new ArrayList<IPromotedOperation>();
		}

	public Declarations getAllCommunicationVariables() {
		return this.allCommunicationVariables;
	}
	public Declarations getAllSharedVariables(){
		return this.allSharedVariables;
	}

	protected Map<Variable, List<IPromotedOperation>> getAllCommunicatingAndSharingOperationPromotions() {
		return this.allCommunicatingOrSharingOperations;
	}

	public PreconditionFunctionCalls getPreconditionsWithCommunication() {
		return preconditionsWithCommunication;
	}

	public int size() {
		return 1;
	}

	public ST getTemplate() {
		return PerfectPredicateTemplateProvider.getInstance().createExistsPrecondition(this);
	}

	public ST getSequentialTemplate(TemporaryVariableMap tempVarMap) {
		return PerfectPredicateTemplateProvider.getInstance().createExistsPrecondition(this, tempVarMap);
	}
}
