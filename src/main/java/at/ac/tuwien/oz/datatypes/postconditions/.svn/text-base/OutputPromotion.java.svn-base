package at.ac.tuwien.oz.datatypes.postconditions;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPromotingPostcondition;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;
import at.ac.tuwien.oz.translator.templates.InputSubstitutionType;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class OutputPromotion implements IPostcondition, IPromotingPostcondition {

	public enum OutputPromotionContext{
		FUNCTION,
		SCHEMA;
	}
	
	private Variable outputVariable;
	private IPromotedOperation promotedOperation;

	private PerfectPredicateTemplateProvider templateProvider;
	private OutputPromotionContext context;
	
	public OutputPromotion(Variable outputVar, IPromotedOperation aFunc1, OutputPromotionContext context) {
		this.templateProvider = PerfectPredicateTemplateProvider.getInstance();
		this.outputVariable = outputVar;
		this.promotedOperation = aFunc1;
		this.context = context;
	}
	
	public void changeContext(OutputPromotionContext context){
		if (this.context != context){
			this.context = context;
		}
	}
	
	public Variable getOutputVariable() {
		return outputVariable;
	}

	@Override
	public IPromotedOperation getPromotedOperation() {
		return promotedOperation;
	}
	@Override
	public boolean usesOutputParameters(Declarations outputParameters) {
		if (outputParameters.contains(outputVariable)){
			return true;
		}
		return false;
	}
	public boolean usesOutputParameter(Variable outputVariable) {
		if (this.outputVariable.equals(outputVariable)){
			return true;
		}
		return false;
	}
	public boolean usesInputParameters(Declarations inputVariables) {
		if (promotedOperation.hasInputVariables(inputVariables)){
			return true;
		}
		return false;
	}

	public boolean usesInputParameter(Variable variable) {
		if (promotedOperation.hasInputVariable(variable)){
			return true;
		}
		return false;
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
	public ST getTemplate() {
		if (this.context == OutputPromotionContext.FUNCTION){
			return templateProvider.createOutputPromotionInFunction(this);
		} else if (this.context == OutputPromotionContext.SCHEMA){
			return templateProvider.createOutputPromotionInSchema(this);
		}
		throw new ObjectZToPerfectTranslationException("Context of OutputPromotion not set: " + this);
	}
	
	public ST getTemplate(TemporaryVariableMap outerTempVarMap, Context context){
		if (context == Context.FUNCTION){
			return this.templateProvider.createOutputPromotionInFunction(this, outerTempVarMap);
		} else {
			return this.templateProvider.createOutputPromotionInSchema(this, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
		}
	}

	@Override
	public Idents getIdentifiers() {
		return promotedOperation.getPromotedOperation().getUsedStateVariables();
	}

	@Override
	public String toString() {
		return "OutputPromotion [outputVariable=" + outputVariable + ", promotedOperation=" + promotedOperation + "]";
	}
}
