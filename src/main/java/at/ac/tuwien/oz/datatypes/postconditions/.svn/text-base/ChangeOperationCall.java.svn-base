package at.ac.tuwien.oz.datatypes.postconditions;

import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPromotingPostcondition;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.templates.InputSubstitutionType;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class ChangeOperationCall implements IPostcondition, IPromotingPostcondition, OrderablePostcondition{

	private IPromotedOperation promotedOperation;
	
	private PerfectPredicateTemplateProvider templateProvider;

	public ChangeOperationCall(IPromotedOperation opPromo) {
		this.templateProvider = PerfectPredicateTemplateProvider.getInstance();
		this.promotedOperation = opPromo;
	}

	@Override
	public ST getTemplate() {
		return templateProvider.createChangeOperationCall(promotedOperation);
	}
	
	public ST getTemplate(TemporaryVariableMap sharedAndCommTempVarMap, Map<Variable, Boolean> substituteSharedOutputVariable, 
			InputSubstitutionType type){
		return templateProvider.createChangeOperationCall(this.getPromotedOperation(), sharedAndCommTempVarMap, 
				substituteSharedOutputVariable, type);
	}
	public ST getTemplate(TemporaryVariableMap sharedAndCommTempVarMap){
		return templateProvider.createChangeOperationCall(this.getPromotedOperation(), sharedAndCommTempVarMap);
	}

	public IPromotedOperation getOrderableOperation() {
		return promotedOperation;
	}

	public IPromotedOperation getPromotedOperation(){
		return promotedOperation;
	}

	public Declarations getUsedVariables(Declarations sharedOutputVariables) {
		Declarations usedVariables = new Declarations();
		usedVariables.addAll(promotedOperation.getOutputParameters());
		return usedVariables.getSharedDeclarations(sharedOutputVariables);
	}

	public boolean usesInputParameters(Variable variable) {
		return promotedOperation.hasInputVariable(variable);
	}

	public boolean usesInputParameters(Declarations inputParameters) {
		return promotedOperation.hasInputVariables(inputParameters);
	}

	public boolean usesOutputParameter(Variable outputVariable) {
		return promotedOperation.hasOutputVariable(outputVariable);
	}

	public boolean usesOutputParameters(Declarations outputParameters) {
		for(Variable outputParameter: outputParameters.asList()){
			if (promotedOperation.hasOutputVariable(outputParameter)){
				return true;
			}
		}
		return false;
	}

	public ST getCaller() {
		return promotedOperation.getCaller();
	}
	@Override
	public Idents getIdentifiers() {
		return promotedOperation.getPromotedOperation().getUsedStateVariables();
	}

	public boolean usesCommunicationVariables(Declarations communicationVariables) {
		Declarations communicationInputVariables = communicationVariables.getInputCopies();
		Declarations communicationOutputVariables = communicationVariables.getOutputCopies();
		
		if (this.usesInputParameters(communicationInputVariables)){
			return true;
		}
		if (this.usesOutputParameters(communicationOutputVariables)){
			return true;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return "ChangeOperationCall [promotedOperation=" + promotedOperation + "]";
	}
}
