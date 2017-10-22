package at.ac.tuwien.oz.datatypes.postconditions;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ChangeOperationCalls;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICommunicatingAndSharingPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;


/**
 * Wrapper for a postcondition of a change operation with communication
 * 
 * @author sylvias
 *
 */
public class ComplexChangePostconditionMapping implements ICommunicatingAndSharingPostcondition{
	
	private OutputPromotions outputPromotions;
	private OutputPromotions communicationOutputPromotions;
	
	private ChangeOperationCalls communicationChangeOperationCalls;
	
	private Declarations allCommunicationVariables;
	private Declarations visibleCommunicationVariables;
	private Declarations sharedOutputVariables;

	private PerfectPredicateTemplateProvider templateProvider;
	
	private ComplexChangePostconditionMapping(){
		this.templateProvider = PerfectPredicateTemplateProvider.getInstance();
		
		this.outputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		this.communicationOutputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		
		this.communicationChangeOperationCalls = new ChangeOperationCalls();
		
		this.allCommunicationVariables = new Declarations();
		this.visibleCommunicationVariables = new Declarations();
		this.sharedOutputVariables = new Declarations();
	}
	
	/**
	 * constructor for change operation calls with shared output
	 * @param changeOperationCalls - involved change operation calls
	 * @param sharedOutputVariables - output variables shared by change operation calls given in other parameter
	 */
	public ComplexChangePostconditionMapping(ChangeOperationCalls changeOperationCalls, Declarations sharedOutputVariables) {
		this();
		this.sharedOutputVariables.addAll(sharedOutputVariables);
		this.communicationChangeOperationCalls.addAll(changeOperationCalls);
	}


	/**
	 * constructor for postconditions involving communication
	 * 
	 * @param outputPromotions - may provide communication output variables or use communication input variables, could be empty
	 * @param changeOperationCalls - may have communication input or output variables or shared output parameters, must not be empty
	 * @param allCommunicationVariables - variables used for communication (given undecorated)
	 * @param visibleCommunicationVariables - subset of allCommunicationVariables that are visible outside the postcondition (given undecorated)
	 * @param sharedOutputVariables
	 */
	public ComplexChangePostconditionMapping(OutputPromotions outputPromotions, ChangeOperationCalls changeOperationCalls, 
			Declarations allCommunicationVariables, Declarations visibleCommunicationVariables, Declarations sharedOutputVariables) {
		this();
		
		for(OutputPromotion outputPromotion: outputPromotions.getElements()){
			if (outputPromotion.usesOutputParameters(allCommunicationVariables.getOutputCopies())){
				this.communicationOutputPromotions.add(outputPromotion);
			} else {
				this.outputPromotions.add(outputPromotion);
			}
		}

		this.communicationChangeOperationCalls.addAll(changeOperationCalls);
		
		this.allCommunicationVariables.addAll(allCommunicationVariables);
		this.visibleCommunicationVariables.addAll(visibleCommunicationVariables);
		this.sharedOutputVariables.addAll(sharedOutputVariables);
	}

	
	public ST getTemplate(){
		return this.templateProvider.createVarPostcondition(this);
	}

	public ST getTemplate(TemporaryVariableMap tempVarMap, Context context) {
		return this.templateProvider.createVarPostcondition(this, tempVarMap);
	}

	public OutputPromotions getCommunicationOutputPromotions() {
		return this.communicationOutputPromotions;
	}

	public OutputPromotions getOutputPromotions() {
		return this.outputPromotions;
	}

	public ChangeOperationCalls getCommunicationChangeOperationCalls() {
		return this.communicationChangeOperationCalls;
	}

	public Declarations getAllCommunicationVariables() {
		return this.allCommunicationVariables;
	}
	public Declarations getVisibleCommunicationVariables(){
		return this.visibleCommunicationVariables;
	}

	public Declarations getSharedOutputVariables() {
		return this.sharedOutputVariables;
	}

	public int getNumberOfSharingPromotions(Variable sharedOutputVariable) {
		int numberOfSharingPromotions = 0;
		
		for (OutputPromotion outputPromotion: outputPromotions.getElements()){
			if (outputPromotion.usesOutputParameter(sharedOutputVariable)){
				numberOfSharingPromotions++;
			}
		}
		for (ChangeOperationCall changeOperationCall: communicationChangeOperationCalls.asList()){
			if (changeOperationCall.usesOutputParameter(sharedOutputVariable)){
				numberOfSharingPromotions++;
			}
		}

		return numberOfSharingPromotions;
	}

	public boolean isEmpty() {
		if (this.outputPromotions.isEmpty() && this.communicationOutputPromotions.isEmpty() && this.communicationChangeOperationCalls.isEmpty()){
			return true;
		}
		return false;
	}
}
