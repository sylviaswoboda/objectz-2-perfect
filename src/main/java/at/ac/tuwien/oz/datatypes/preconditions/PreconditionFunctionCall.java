package at.ac.tuwien.oz.datatypes.preconditions;

import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class PreconditionFunctionCall implements IPrecondition{
	
	// built by OperationComposer
	private ST caller;
	private PreconditionFunction preconditionFunction;
	
	// added by OperationTranslator
	private ST template;
	
	private PerfectPredicateTemplateProvider templateProvider;

	public PreconditionFunctionCall(ST caller, PreconditionFunction preconditionFunction){
		this.templateProvider = PerfectPredicateTemplateProvider.getInstance();
		this.caller = caller;
		this.preconditionFunction = preconditionFunction;
	}

	@Override
	public ST getTemplate() {
		return this.template = templateProvider.createFunctionCallTemplate(caller, preconditionFunction);
	}

	public List<ST> getTemplate(TemporaryVariableMap tempVarMap) {
		return templateProvider.createFunctionCallTemplate(caller, preconditionFunction, tempVarMap);
	}

	@Override
	public String toString() {
		if (template != null){
			return "PreconditionFunctionCall [template=" + template.render() + "]";
		}
		return "PreconditionFunctionCall [caller=" + caller + ", preconditionFunction=" + preconditionFunction + "]";
	}

	public boolean usesCommunicationVariables(Declarations communicationVariables) {
		Declarations communicationInputVariables = communicationVariables.getInputCopies();
		
		for(Variable commVar: communicationInputVariables.asList()){
			if (preconditionFunction.hasInputVariable(commVar)){
				return true;
			}
		}
		return false;
	}
	
	public ST getCaller(){
		return this.caller;
	}
	
	public PreconditionFunction getPreconditionFunction() {
		return this.preconditionFunction;
	}
}
