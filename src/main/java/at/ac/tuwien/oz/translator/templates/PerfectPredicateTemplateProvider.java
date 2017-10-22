package at.ac.tuwien.oz.translator.templates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexChangePostconditionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexOutputPromotionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.NonDeterministicChoiceItem;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ThenPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICommunicatingAndSharingPostcondition;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.CommunicationPreconditionMapping;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class PerfectPredicateTemplateProvider {

	private static PerfectPredicateTemplateProvider predicateProvider;

	public static PerfectPredicateTemplateProvider getInstance() {
		if (predicateProvider == null) {
			predicateProvider = new PerfectPredicateTemplateProvider();
		}
		return predicateProvider;
	}

	public static void setInstance(PerfectPredicateTemplateProvider provider) {
		predicateProvider = provider;
	}

	protected PerfectTemplateProvider              perfect;

	protected PerfectPredicateTemplateProvider() {
		this.perfect = PerfectTemplateProvider.getInstance();
	}

	//
	//
	//
	//
	
	public ST createImplicitFunctionPrecondition(Declarations outputParameters, List<ST> implicitPreconditions) {
		return PerfectPreconditionTemplateProvider.getInstance().createImplicitFunctionPrecondition(outputParameters, implicitPreconditions);
	}

	public ST createImplicitSchemaPreconditions(Declarations deltaVariables, Declarations outputParameters, List<ST> implicitPreconditions) {
		return PerfectPreconditionTemplateProvider.getInstance()
				.createImplicitSchemaPreconditions(deltaVariables, outputParameters, implicitPreconditions);
	}

	public ST createFunctionCallTemplate(ST caller, PreconditionFunction operationToCall) {
		List<ST> resultingTemplates = PerfectPreconditionTemplateProvider.getInstance().createFunctionCallTemplate(caller, operationToCall, null);
		if (resultingTemplates.size() == 1){
			return resultingTemplates.get(0);
		} else {
			return perfect.getConjunctionFromList(resultingTemplates);
		}
	}

	public List<ST> createFunctionCallTemplate(ST caller, PreconditionFunction operationToCall, TemporaryVariableMap tempVarMap) {
		return PerfectPreconditionTemplateProvider.getInstance().createFunctionCallTemplate(caller, operationToCall, tempVarMap);
	}

	public ST createSharedOutputInFunction(List<IPromotedOperation> opPromos, Variable outputVariable,
			Variable tempVariable) {
		return PerfectPreconditionTemplateProvider.getInstance().createSharedOutputInFunction(opPromos, outputVariable, tempVariable);
	}

	public ST createExistsPrecondition(CommunicationPreconditionMapping communicationPreconditionMapping) {
		return PerfectPreconditionTemplateProvider.getInstance().createExistsPrecondition(communicationPreconditionMapping, null);
	}

	public ST createExistsPrecondition(CommunicationPreconditionMapping communicationPreconditionMapping, TemporaryVariableMap tempVarMap) {
		return PerfectPreconditionTemplateProvider.getInstance().createExistsPrecondition(communicationPreconditionMapping, tempVarMap);
	}
	
	public ST createExistsPrecondition(SequentialPreconditions sequentialPreconditions) {
		return PerfectPreconditionTemplateProvider.getInstance().createExistsPrecondition(sequentialPreconditions, null);
	}

	public ST createExistsPrecondition(SequentialPreconditions sequentialPreconditions, TemporaryVariableMap outerTempVarMap) {
		return PerfectPreconditionTemplateProvider.getInstance().createExistsPrecondition(sequentialPreconditions, outerTempVarMap);
	}

	public ST createNonDeterministicPrecondition(NonDeterministicPreconditions nonDeterministicPreconditions) {
		return PerfectPreconditionTemplateProvider.getInstance().createNonDeterministicPrecondition(nonDeterministicPreconditions, null);
	}

	public ST createNonDeterministicPrecondition(NonDeterministicPreconditions nonDeterministicPreconditions, TemporaryVariableMap outerTempVarMap) {
		return PerfectPreconditionTemplateProvider.getInstance().createNonDeterministicPrecondition(nonDeterministicPreconditions, outerTempVarMap);
	}
	
	public ST createExistsPreconditionForScopeEnrichment(Declarations decoratedVariables, List<ST> preconditions){
		return  PerfectPreconditionTemplateProvider.getInstance().createExistsPreconditionForScopeEnrichment(decoratedVariables, preconditions);
	}


	


	/**
	 * Creates an output promotion template for functions.
	 * 
	 * @param promotedOutput  the output variable to be promoted
	 * @param promotedOperation the operation to be promoted
	 * 
	 * @return result.outVar = a.func(inVar).outVar
	 */
	public ST createOutputPromotionInFunction(OutputPromotion outputPromotion) {
		return PerfectPostconditionTemplateProvider.getInstance().createOutputPromotionInFunction(outputPromotion);
	}

	public ST createOutputPromotionInFunction(OutputPromotion outputPromotion, TemporaryVariableMap outerTempVarMap) {
		return PerfectPostconditionTemplateProvider.getInstance().createOutputPromotionInFunction(outputPromotion, outerTempVarMap);
	}

	public ST createOutputPromotionInSchema(OutputPromotion outputPromotion) {
		return PerfectPostconditionTemplateProvider.getInstance().createOutputPromotionInSchema(outputPromotion);
	}

	public ST createOutputPromotionInSchema(OutputPromotion outputPromotion, TemporaryVariableMap communicationTempVarMap, InputSubstitutionType type) {
		return PerfectPostconditionTemplateProvider.getInstance().createOutputPromotionInSchema(outputPromotion, communicationTempVarMap, type);
	}

	public ST createChangeOperationCall(IPromotedOperation promotedOperation) {
		return PerfectPostconditionTemplateProvider.getInstance().createChangeOperationCall(promotedOperation);
	}
	public ST createChangeOperationCall(IPromotedOperation promotedOperation, TemporaryVariableMap outerTempVarMap) {
		return PerfectPostconditionTemplateProvider.getInstance().createChangeOperationCall(promotedOperation, outerTempVarMap);
	}
	
	public ST createChangeOperationCall(IPromotedOperation promotedOperation, TemporaryVariableMap tempVarMap, 
			Map<Variable, Boolean> sharedOutputSubstitutionMap, InputSubstitutionType inputSubstitutionType) {
		return PerfectPostconditionTemplateProvider.getInstance().createChangeOperationCall(promotedOperation, tempVarMap, sharedOutputSubstitutionMap, inputSubstitutionType);
	}

	public ST createExistsPostcondition(ComplexOutputPromotionMapping mapping) {
		return PerfectPostconditionTemplateProvider.getInstance().createExistsPostcondition(mapping, null);
	}

	public ST createExistsPostcondition(ComplexOutputPromotionMapping mapping, TemporaryVariableMap outerTempVarMap) {
		return PerfectPostconditionTemplateProvider.getInstance().createExistsPostcondition(mapping, outerTempVarMap);
	}

	public ST createSatisfyPostcondition(SequentialPostconditions sequentialPostconditions) {
		return PerfectPostconditionTemplateProvider.getInstance().createSatisfyPostcondition(sequentialPostconditions);
	}

	public ST createVarPostcondition(ComplexOutputPromotionMapping mapping, TemporaryVariableMap outerTempVarMap) {
		return PerfectPostconditionTemplateProvider.getInstance().createVarPostcondition(mapping, outerTempVarMap);
	}
		
	public ST createVarPostcondition(ComplexChangePostconditionMapping mapping) {
		return PerfectPostconditionTemplateProvider.getInstance().createVarPostcondition(mapping, null);
	}

	public ST createVarPostcondition(ComplexChangePostconditionMapping mapping, TemporaryVariableMap outerTempVarMap) {
		return PerfectPostconditionTemplateProvider.getInstance().createVarPostcondition(mapping, outerTempVarMap);
	}
	
	public ST createVarPostconditionForScopeEnrichment(Variable auxVar, List<ST> auxVarPredicates, List<ST> postconditions) {
		return PerfectPostconditionTemplateProvider.getInstance().createVarPostconditionForScopeEnrichment(auxVar, auxVarPredicates, postconditions);
	}

	public ST createThenPostcondition(ThenPostcondition thenPostconditions, TemporaryVariableMap outerCommunicationTempVarMap) {
		return PerfectPostconditionTemplateProvider.getInstance().createThenPostcondition(thenPostconditions, outerCommunicationTempVarMap);
	}
	
	public ST createThenPostcondition(ThenPostcondition thenPostconditions) {
		return PerfectPostconditionTemplateProvider.getInstance().createThenPostcondition(thenPostconditions, null);
	}

	public ST createThenPostcondition(SequentialPostconditions sequentialPostconditions,
			TemporaryVariableMap outerTempVarMap) {
		return PerfectPostconditionTemplateProvider.getInstance().createThenPostcondition(sequentialPostconditions, outerTempVarMap);
	}

	public ST createThenPostcondition(SequentialPostconditions sequentialPostconditions) {
		return PerfectPostconditionTemplateProvider.getInstance().createThenPostcondition(sequentialPostconditions, null);
	}

	public ST createNonDeterministicChoiceItem(NonDeterministicChoiceItem choiceItem) {
		return PerfectPostconditionTemplateProvider.getInstance().createNonDeterministicChoiceItem(choiceItem, null);
	}
	
	public ST createNonDeterministicChoiceItem(NonDeterministicChoiceItem choiceItem, TemporaryVariableMap temporaryVariables) {
		return PerfectPostconditionTemplateProvider.getInstance().createNonDeterministicChoiceItem(choiceItem, temporaryVariables);
	}

	//
	//
	//
	//
	
	protected void fillTemporaryVariables(List<ST> tempVarDeclarations,
			TemporaryVariableMap tempVarMap, Map<Variable, Boolean> outputVariableSubstituted,
			ICommunicatingAndSharingPostcondition postcondition) {
		fillTemporaryVariablesFor(tempVarDeclarations, 
				tempVarMap, postcondition.getAllCommunicationVariables().sorted().asList());

		fillTemporaryVariablesForSharing(tempVarDeclarations, tempVarMap, outputVariableSubstituted, postcondition);
	}

	protected void fillTemporaryVariablesFor(List<ST> tempVarDeclarations,
			TemporaryVariableMap tempVarMap, List<Variable> variables) {
		for (Variable variable : variables) {
			Variable temporaryVariable = tempVarMap.add(variable);
			if (temporaryVariable != null){
				tempVarDeclarations.add(perfect.getDeclarationInOperation(temporaryVariable));
			}
		}
	}

	protected ST substituteByTemporaryVariables(ST template, Variable originalVariable, Variable tempVar, InputSubstitutionType type) {
		if (originalVariable == null || tempVar == null || type == null || type == InputSubstitutionType.NONE) {
			return template;
		}
	
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
	
		ST templateClone = cloner.clone(template);
	
		ST oldIdent = perfect.getId(originalVariable.getId());
		Ident tempVarId = tempVar.getId();
		if (type == InputSubstitutionType.PRIME) {
			tempVarId = tempVarId.getPrimedCopy();
		}
	
		ST newIdent = perfect.getId(tempVarId);
		substitutor.substituteIdentifier(templateClone, oldIdent, newIdent);
		return templateClone;
	}

	protected ST substituteInputByTemporaryVariables(ST template, TemporaryVariableMap tempVarMap, InputSubstitutionType type) {
		if (tempVarMap == null || type == null || type == InputSubstitutionType.NONE) {
			return template;
		}

		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();

		ST templateClone = cloner.clone(template);

		for (Variable originalVariable : tempVarMap.originalVariables()) {
			ST oldIdent = perfect.getId(originalVariable.getInputCopy().getId());
			Ident tempVar = tempVarMap.get(originalVariable).getId();
			if (type == InputSubstitutionType.PRIME) {
				tempVar = tempVar.getPrimedCopy();
			}

			ST newIdent = perfect.getId(tempVar);
			substitutor.substituteIdentifier(templateClone, oldIdent, newIdent);
		}
		return templateClone;
	}

	protected ST substituteInputByTemporaryVariableMaps(ST template, List<TemporaryVariableMap> tempVarMaps) {
		ST result = template;
		for(TemporaryVariableMap tempVarMap: tempVarMaps){
			result = substituteInputByTemporaryVariables(result, tempVarMap, InputSubstitutionType.DO_NOT_PRIME);
		}
		return result;
	}

	protected ST substituteOutputByTemporaryVariables(ST template, TemporaryVariableMap tempVarMap) {
		if (tempVarMap == null) {
			return template;
		}
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();

		ST templateClone = cloner.clone(template);

		for (Variable originalVariable : tempVarMap.originalVariables()) {
			ST oldIdent = perfect.getId(originalVariable.getOutputCopy().getId());
			Ident tempVar = tempVarMap.get(originalVariable).getId();
			ST newIdent = perfect.getId(tempVar);

			substitutor.substituteIdentifier(templateClone, oldIdent, newIdent);
		}

		return templateClone;
	}
	
	protected ST substituteOutputByTemporaryVariables(ST template, IPromotedOperation promotedOperation,
			TemporaryVariableMap tempVarMap, Map<Variable, Boolean> sharedOutputSubstitutionMap) {
		
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();

		ST templateClone = cloner.clone(template);

		for (Variable undecoratedVariable : tempVarMap.originalVariables()) {
			Variable outputVariable = undecoratedVariable.getOutputCopy();
			
			Boolean isSubstituteSharedOutput = sharedOutputSubstitutionMap.get(outputVariable);
			
			if (promotedOperation.getOutputParameters().contains(outputVariable)){
				if (isSubstituteSharedOutput != null && isSubstituteSharedOutput == false){
					sharedOutputSubstitutionMap.put(outputVariable, true);
				} else {
					ST oldIdent = perfect.getId(outputVariable.getId());
					ST newIdent = perfect.getId(tempVarMap.use(outputVariable).getId());
					
					substitutor.substituteIdentifier(templateClone, oldIdent, newIdent);
				}
			}
		}
		return templateClone;
	}

	//
	//
	//
	//
	
	protected ST createTempVarEqualsOutputPromotionInFunction(Variable outputVariable, IPromotedOperation operation, Variable tempVar) {
		ST outputVariableCall = getOutputCall(outputVariable, operation);
		return createTempVarEqualsTemplateInFunction(tempVar, outputVariableCall);
	}
	protected ST createTempVarEqualsOutputPromotionInFunction(Variable outputVariable, Variable tempVar, 
					ST caller, String operationName, Declarations inputParameters) {
		ST outputVariableCall = getOutputCall(outputVariable, caller, operationName, inputParameters);
		return createTempVarEqualsTemplateInFunction(tempVar, outputVariableCall);
	}
	protected ST createTempVarEqualsOutputPromotionInFunction(OutputPromotion outputPromotion, Variable tempVar) {
		ST outputVariableCall = getOutputCall(outputPromotion);
		return createTempVarEqualsTemplateInFunction(tempVar, outputVariableCall);
	}
	protected ST createTempVarEqualsOutputPromotionInSchema(OutputPromotion outputPromotion, Variable tempVar) {
		ST outputVariableCall = getOutputCall(outputPromotion);
		return createTempVarEqualsOutputPromotionInSchema(tempVar, outputVariableCall);
	}
	
	/**
	 * Yields a template of the form a.func(inputVariable).outputVariable.out
	 * 
	 */
	protected ST getOutputCall(Variable outputDeclaration, IPromotedOperation opPromo) {
		ST outVarST = perfect.getId(outputDeclaration.getId());
	
		ST promotedFunctionInputParameters = getParenthesizedParameterListOfFunctionCall(opPromo.getInputParameters().sorted());
	
		ST callerTemplate = null;
		if (opPromo.getCaller() != null) {
			callerTemplate = perfect.getCaller(opPromo.getCaller());
		}
		ST promotedFunctionCallST = perfect.getOutputVariableFunctionCall(callerTemplate,
				opPromo.getPromotedOperation().getPostconditionFunctionName(), promotedFunctionInputParameters,
				outVarST);
		return promotedFunctionCallST;
	}
	
	protected ST getOutputCall(Variable outputDeclaration, ST caller, String operationName, Declarations inputParameters) {
		ST outVarST = perfect.getId(outputDeclaration.getId());
	
		ST promotedFunctionInputParameters = getParenthesizedParameterListOfFunctionCall(inputParameters.sorted());
	
		ST callerTemplate = null;
		if (caller != null) {
			callerTemplate = perfect.getCaller(caller);
		}
		ST promotedFunctionCallST = perfect.getOutputVariableFunctionCall(callerTemplate, operationName, 
				promotedFunctionInputParameters, outVarST);
		return promotedFunctionCallST;
	}

	protected ST getOutputCall(OutputPromotion outputPromotion) {
		Variable outputDeclaration = outputPromotion.getOutputVariable();
		IPromotedOperation opPromo = outputPromotion.getPromotedOperation();
		
		ST outVarST = perfect.getId(outputDeclaration.getId());
	
		ST promotedFunctionInputParameters = getParenthesizedParameterListOfFunctionCall(opPromo.getInputParameters().sorted());
	
		ST callerTemplate = null;
		if (opPromo.getCaller() != null) {
			callerTemplate = perfect.getCaller(opPromo.getCaller());
		}
		ST promotedFunctionCallST = perfect.getOutputVariableFunctionCall(callerTemplate,
				opPromo.getPromotedOperation().getPostconditionFunctionName(), promotedFunctionInputParameters,
				outVarST);
		return promotedFunctionCallST;
	}
	
	protected List<ST> createOutputVariablesEqualTempVarsInFunction(List<Variable> visibleCommunicationVariables, 
			TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap) {
		List<ST> predicateList = new ArrayList<ST>();
		for (Variable visibleCommunicationVariable : visibleCommunicationVariables) {
			ST tempVarEquality = 
					createOutputVariableEqualsTempVarInFunction(innerTempVarMap, outerTempVarMap, visibleCommunicationVariable);
			predicateList.add(tempVarEquality);
		}
		return predicateList;
	}

	protected ST getParenthesizedParameterListOfSchemaCall(Declarations inputParameters, Declarations outputParameters) {
		ST declarationList = null;
		if (!inputParameters.isEmpty() || !outputParameters.isEmpty()) {
			declarationList = getParameterListInSchema(inputParameters, outputParameters);
			declarationList = perfect.getParenthesized(declarationList);
		}
		return declarationList;
	}

	protected ST getParenthesizedParameterListOfFunctionCall(Declarations inputParameters) {
		ST declarationList = null;
		if (!inputParameters.isEmpty()) {
			declarationList = getParameterList(inputParameters);
			declarationList = perfect.getParenthesized(declarationList);
		}
		return declarationList;
	}

	private ST getParameterList(Declarations declarations) {
		ST parameterList;
		List<ST> parameters = new ArrayList<ST>();
		for (Variable declaration : declarations.asList()) {
			parameters.add(perfect.getId(declaration.getId()));
		}
		parameterList = perfect.getItemList(parameters);
		return parameterList;
	}

	private ST getParameterListInSchema(Declarations inputDeclarations, Declarations outputDeclarations) {
		ST parameterList;
		List<ST> parameters = new ArrayList<ST>();
		for (Variable declaration : inputDeclarations.asList()) {
			parameters.add(perfect.getId(declaration.getId()));
		}
		for (Variable declaration : outputDeclarations.asList()) {
			ST parameter = null;
			ST ident = perfect.getId(declaration.getId());
			parameter = perfect.getWithExclamation(ident);
			parameters.add(parameter);
		}
	
		parameterList = perfect.getItemList(parameters);
		return parameterList;
	}

	private void fillTemporaryVariablesForSharing(List<ST> tempVarDeclarations,
			TemporaryVariableMap tempVarMap, Map<Variable, Boolean> outputVariableSubstituted,
			ICommunicatingAndSharingPostcondition postcondition) {
		
		for (Variable sharedOutputVariable : postcondition.getSharedOutputVariables().sorted().asList()) {
			outputVariableSubstituted.put(sharedOutputVariable, false);
			int numberOfSharingPromotions = postcondition.getNumberOfSharingPromotions(sharedOutputVariable);
			int numberOfNecessaryTempVars = numberOfSharingPromotions - 1;
			if (tempVarMap.containsKey(sharedOutputVariable)) {
				numberOfNecessaryTempVars--;
			}
			if (numberOfNecessaryTempVars > 0) {
				List<Variable> temporaryVariables = tempVarMap.add(sharedOutputVariable, numberOfNecessaryTempVars);
				for (Variable v : temporaryVariables) {
					tempVarDeclarations.add(perfect.getDeclarationInOperation(v));
				}
			}
		}
	}

	private ST createTempVarEqualsTemplateInFunction(Variable tempVar, ST template){
		ST tempVarST = perfect.getId(tempVar.getId());
		ST tempVarEquality = perfect.getEquals(tempVarST, template);
		return tempVarEquality;
	}

	private ST createTempVarEqualsOutputPromotionInSchema(Variable tempVar, ST template){
		ST tempVarST = perfect.getWithExclamation(perfect.getId(tempVar.getId()));
		ST tempVarEquality = perfect.getEquals(tempVarST, template);
		return tempVarEquality;
	}

	private ST createOutputVariableEqualsTempVarInFunction(TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap,
			Variable undecoratedVariable) {
		Variable outputVariable = undecoratedVariable.getOutputCopy();
		ST resultOutvarST = perfect.getOutputVariableReference(outputVariable.getId());
	
		Variable tempVar = null;
		if (innerTempVarMap.containsKey(undecoratedVariable)){
			tempVar = innerTempVarMap.get(undecoratedVariable);
		} else if (outerTempVarMap.containsKey(undecoratedVariable)){
			tempVar = outerTempVarMap.get(undecoratedVariable);
		}
		ST tempVarST = perfect.getId(tempVar.getId());
		ST tempVarEquality = perfect.getEquals(resultOutvarST, tempVarST);
		return tempVarEquality;
	}


	protected List<ST> createPreconditionTemplates(IComposablePreconditions preconditionsWithCommunication, TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap) {
		List<ST> predicates = new ArrayList<ST>();
		for (ST callTemplate : preconditionsWithCommunication.getTemplates()) {
			callTemplate = substituteInputByTemporaryVariableMaps(callTemplate, Arrays.asList(innerTempVarMap, outerTempVarMap));
			predicates.add(callTemplate);
		}
		return predicates;
	
	}
	
	//
	//
	//
	//

}
