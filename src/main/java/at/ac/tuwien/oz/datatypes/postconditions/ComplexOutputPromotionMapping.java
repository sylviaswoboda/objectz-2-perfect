package at.ac.tuwien.oz.datatypes.postconditions;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPromotingPostcondition;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class ComplexOutputPromotionMapping implements IPostcondition {
	
	// output promotions using communication variables as input, but output variable is not a communication variable
	private OutputPromotions otherPromotions;
	
	// output promotions with a communication variable as output variable
	private OutputPromotions communicationPromotions;
	
	// all known communication variables within this mapping -> used for temporary variable generation
	private Declarations allCommunicationVars;
	
	// all visible communication variables -> promoted outside.
	private Declarations visibleCommunicationVars;
	
	private PerfectPredicateTemplateProvider templateProvider;
	
	private ComplexOutputPromotionMapping(){
		this.templateProvider = PerfectPredicateTemplateProvider.getInstance();
		
		this.otherPromotions = new OutputPromotions();
		this.communicationPromotions = new OutputPromotions();
		this.allCommunicationVars = new Declarations();
		this.visibleCommunicationVars = new Declarations();
	}
	
	public ComplexOutputPromotionMapping(OutputPromotions allOutputPromotions, Declarations allCommunicationVariables, Declarations visibleCommunicationVariables){
		this();
		
		for(OutputPromotion outputPromotion: allOutputPromotions.getElements()){
			if (outputPromotion.usesOutputParameters(allCommunicationVariables.getOutputCopies())){
				this.communicationPromotions.add(outputPromotion);
			} else {
				this.otherPromotions.add(outputPromotion);
			}
		}
		this.allCommunicationVars.addAll(allCommunicationVariables);
		this.visibleCommunicationVars.addAll(visibleCommunicationVariables);
	}
	
	@Override
	public ST getTemplate() {
		return templateProvider.createExistsPostcondition(this);
	}

	public ST getTemplate(TemporaryVariableMap tempVarMap, Context context) {
		if (context == Context.FUNCTION){
			return templateProvider.createExistsPostcondition(this, tempVarMap);
		} else {
			return templateProvider.createVarPostcondition(this, tempVarMap);
		}
	}

	@Override
	public Idents getIdentifiers() {
		Idents identifiers = new Idents();
		identifiers.addAll(otherPromotions.getUsedIdentifiers());
		return identifiers;
	}
	
	public OutputPromotions getOtherPromotions(){
		return this.otherPromotions;
	}
	public OutputPromotions getCommunicationPromotions(){
		return this.communicationPromotions;
	}
	public Declarations getAllCommunicationVars() {
		return this.allCommunicationVars;
	}
	public Declarations getVisibleCommunicationVars() {
		return this.visibleCommunicationVars;
	}

	public IPromotingPostcondition getOtherPromotion(Variable outputVariable) {
		return this.otherPromotions.getOutputPromotion(outputVariable);
	}

	public IPromotingPostcondition getCommunicationPromotion(Variable outputVariable) {
		return this.communicationPromotions.getOutputPromotion(outputVariable);
	}

	public boolean isEmpty() {
		return this.communicationPromotions.isEmpty() && this.otherPromotions.isEmpty();
	}

}
