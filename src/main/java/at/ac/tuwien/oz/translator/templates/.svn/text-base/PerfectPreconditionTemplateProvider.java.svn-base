package at.ac.tuwien.oz.translator.templates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.CommunicationPreconditionMapping;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions.PreconditionItem;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class PerfectPreconditionTemplateProvider extends PerfectPredicateTemplateProvider{
	
	private static PerfectPredicateTemplateProvider preconditionProvider;

	public static PerfectPredicateTemplateProvider getInstance() {
		if (preconditionProvider == null) {
			preconditionProvider = new PerfectPreconditionTemplateProvider();
		}
		return preconditionProvider;
	}

	public static void setInstance(PerfectPredicateTemplateProvider provider) {
		preconditionProvider = provider;
	}

	public PerfectPreconditionTemplateProvider(){
		super();
	}

	public ST createSharedOutputInFunction(List<IPromotedOperation> opPromos, Variable outputVariable,
			Variable tempVariable) {

		ST tempVarDeclaration = perfect.getDeclarationInOperation(tempVariable);

		List<ST> functionEqualities = new ArrayList<ST>();

		for (IPromotedOperation opPromo : opPromos) {
			ST functionEquality = createTempVarEqualsOutputPromotionInFunction(outputVariable, opPromo, tempVariable);
			functionEqualities.add(functionEquality);
		}

		ST equalityConjunction = perfect.getConjunctionFromList(functionEqualities);
		ST existsClause = perfect.getExists(Arrays.asList(tempVarDeclaration), equalityConjunction);

		return existsClause;
	}
	
	public ST createExistsPrecondition(CommunicationPreconditionMapping communicationPreconditionMapping, TemporaryVariableMap outerTempVarMap) {
		List<ST> tempVarDeclarations = new ArrayList<ST>();
		List<ST> predicateList = new ArrayList<ST>();
	
		List<Variable> communicationVariables = communicationPreconditionMapping.getAllCommunicationVariables().sorted().asList();
		List<Variable> sharedVariables = communicationPreconditionMapping.getAllSharedVariables().sorted().asList();
		
		TemporaryVariableMap innerTempVarMap = new TemporaryVariableMap();
		
		fillTemporaryVariablesFor(tempVarDeclarations, innerTempVarMap, communicationVariables);
		fillTemporaryVariablesFor(tempVarDeclarations, innerTempVarMap, sharedVariables);
		
		predicateList.addAll(
				createCommunicationOutputTempVarEqualities(communicationVariables, communicationPreconditionMapping, innerTempVarMap, outerTempVarMap));

		predicateList.addAll(
				createSharedOutputTempVarEqualities(sharedVariables, communicationPreconditionMapping, innerTempVarMap, outerTempVarMap));

		predicateList.addAll(
				createPreconditionTemplates(communicationPreconditionMapping.getPreconditionsWithCommunication(), innerTempVarMap, outerTempVarMap));
		
		ST equalityConjunction = perfect.getConjunctionFromList(predicateList);
		return perfect.getExists(tempVarDeclarations, equalityConjunction);

	}
	
	private List<ST> createCommunicationOutputTempVarEqualities(
			List<Variable> innerCommunicationVariables, CommunicationPreconditionMapping mapping, 
			TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap) {
		
		List<ST> predicateList = new ArrayList<ST>();
		
		for (Variable communicationVariable : innerCommunicationVariables) {
			Variable tempVar = null;
			List<IPromotedOperation> promotedOperations = mapping.getPromotingOperations(communicationVariable);
			if (innerTempVarMap.containsKey(communicationVariable)) {
				tempVar = innerTempVarMap.get(communicationVariable);
			} else if (outerTempVarMap.containsKey(communicationVariable)) {
				tempVar = outerTempVarMap.get(communicationVariable);
			}
			if (tempVar != null && promotedOperations != null){
				Variable outputVariable = communicationVariable.getOutputCopy();
				for (IPromotedOperation promotedOperation: promotedOperations){
					ST tempVarEquality = createTempVarEqualsOutputPromotionInFunction(outputVariable, promotedOperation, tempVar);
					tempVarEquality = substituteInputByTemporaryVariableMaps(tempVarEquality, Arrays.asList(innerTempVarMap, outerTempVarMap));
					predicateList.add(tempVarEquality);
				}
			}
		}

		if (outerTempVarMap != null){
			for (Variable communicationVariable : outerTempVarMap.originalVariables()) {
				Variable tempVar = null;
				Variable outputVariable = communicationVariable.getOutputCopy();
				List<IPromotedOperation> promotedOperations = mapping.getPromotingOperations(outputVariable);
				if (innerTempVarMap.containsKey(communicationVariable)) {
					tempVar = innerTempVarMap.get(communicationVariable);
				} else if (outerTempVarMap.containsKey(communicationVariable)) {
					tempVar = outerTempVarMap.get(communicationVariable);
				}
				if (tempVar != null && promotedOperations != null){
					for (IPromotedOperation promotedOperation: promotedOperations){
						ST tempVarEquality = createTempVarEqualsOutputPromotionInFunction(outputVariable, promotedOperation, tempVar);
						tempVarEquality = substituteInputByTemporaryVariableMaps(tempVarEquality, Arrays.asList(innerTempVarMap, outerTempVarMap));
						predicateList.add(tempVarEquality);
					}
				}
			}
		}
		
		return predicateList;
	}

	private List<ST> createSharedOutputTempVarEqualities(
			List<Variable> sharedVariables, CommunicationPreconditionMapping mapping, 
			TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap) {
		
		List<ST> predicateList = new ArrayList<ST>();
		
		for (Variable sharedVariable : sharedVariables) {
			Variable outputVariable = sharedVariable;
			Variable tempVar = null;
			List<IPromotedOperation> promotedOperations = mapping.getPromotingOperations(outputVariable);
			
			if (innerTempVarMap.containsKey(outputVariable)) {
				tempVar = innerTempVarMap.get(outputVariable);
			} else if (outerTempVarMap.containsKey(outputVariable)) {
				tempVar = outerTempVarMap.get(outputVariable);
			}
			if (tempVar != null && promotedOperations != null){
				for(IPromotedOperation promotedOperation: promotedOperations){
					ST tempVarEquality = createTempVarEqualsOutputPromotionInFunction(outputVariable, promotedOperation, tempVar);
					tempVarEquality = substituteInputByTemporaryVariableMaps(tempVarEquality, Arrays.asList(innerTempVarMap, outerTempVarMap));
					predicateList.add(tempVarEquality);
				}
			}
		}
		
		return predicateList;
	}

	
	public ST createExistsPrecondition(SequentialPreconditions sequentialPreconditions, TemporaryVariableMap outerTempVarMap) {
		
		List<ST> tempVarDeclarations = new ArrayList<ST>();
		List<ST> predicateList = new ArrayList<ST>();
	
		TemporaryVariableMap innerTempVarMap = new TemporaryVariableMap();
		
		for (int i = 0; i< sequentialPreconditions.getPreconditions().size(); i++){
			TemporaryVariableMap localTempVarMap = new TemporaryVariableMap();
			
			List<Variable> communicationVariables = new ArrayList<Variable>();
			if (i-1 >= 0){
				communicationVariables.addAll(sequentialPreconditions.getCommunicationVariables().get(i-1).asList());
			}
			if (i < sequentialPreconditions.getCommunicationVariables().size()){
				communicationVariables.addAll(sequentialPreconditions.getCommunicationVariables().get(i).asList());
			}
			fillTemporaryVariablesFor(tempVarDeclarations, innerTempVarMap, communicationVariables);
			for (Variable v: communicationVariables){
				localTempVarMap.add(v, innerTempVarMap.get(v));
			}
				
			IComposablePreconditions composablePrecondition = sequentialPreconditions.getPreconditions().get(i);
			List<ST> composablePreconditionST = composablePrecondition.getSequentialTemplates(localTempVarMap);

			ST predicates = perfect.getConjunctionFromList(composablePreconditionST);
			predicateList.add(predicates);

		}
		
		
		ST predicates = perfect.getConjunctionFromList(predicateList);
		
		
		if (outerTempVarMap != null){
			predicates = substituteInputByTemporaryVariables(predicates, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
			predicates = substituteOutputByTemporaryVariables(predicates, outerTempVarMap);
		}
			
		if (tempVarDeclarations.isEmpty()){
			return predicates;
		} else {
			return perfect.getExists(tempVarDeclarations, predicates);
		}

	}
	
	public List<ST> createFunctionCallTemplate(ST caller, PreconditionFunction operationToCall, TemporaryVariableMap outerTempVarMap) {
		List<ST> resultingTemplates = new ArrayList<ST>();
		ST preconditionOpCall;
		String preconditionOpName = operationToCall.getName();
		ST inputParameterList = getParenthesizedParameterListOfFunctionCall(operationToCall.getInputParameters()
				.sorted());
		
		
		if (outerTempVarMap != null){
			for(Variable outputVariable: outerTempVarMap.originalVariables()){
				Variable originalOutputVariable = outputVariable.getOutputCopy();
				IOperation parent = operationToCall.getParent();
				if (parent.hasOutputVariable(originalOutputVariable)){
					Variable tempVar = outerTempVarMap.get(outputVariable);
					ST tempVarEquality = createTempVarEqualsOutputPromotionInFunction(originalOutputVariable, tempVar, 
							caller, parent.getPostconditionFunctionName(), operationToCall.getInputParameters());
					tempVarEquality = substituteInputByTemporaryVariables(tempVarEquality, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
					resultingTemplates.add(tempVarEquality);
				}
			}
			if (inputParameterList != null){
				inputParameterList = substituteInputByTemporaryVariables(inputParameterList, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
			}
		}
		
		ST callerTemplate = null;
		if (caller != null) {
			callerTemplate = perfect.getCaller(caller);
		}
		preconditionOpCall = perfect.getPreconditionOpCall(callerTemplate, preconditionOpName, inputParameterList);
		resultingTemplates.add(preconditionOpCall);

		return resultingTemplates;
	}


	public ST createImplicitFunctionPrecondition(Declarations outputParameters, List<ST> implicitPreconditions) {
		List<ST> declarations = new ArrayList<ST>();
		
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
		ST predicate = perfect.getConjunctionFromList(implicitPreconditions);
		ST clonedTemplate = cloner.clone(predicate);

		for (Variable outputParameter: outputParameters.asList()){
			Variable tempVar = outputParameter.getTempCopy();
			declarations.add(perfect.getDeclarationInOperation(tempVar));
			
			ST oldIdentifier = perfect.getId(outputParameter.getId());
			ST newIdentifier = perfect.getId(tempVar.getId());
			substitutor.substituteIdentifier(clonedTemplate, oldIdentifier, newIdentifier);
		}

		ST existsPrecondition = perfect.getExists(declarations, clonedTemplate);
		return existsPrecondition;
	}
	
	public ST createImplicitSchemaPreconditions(Declarations deltaVariables, Declarations outputParameters, List<ST> implicitPreconditions) {
		List<ST> declarations = new ArrayList<ST>();
		
		StringTemplateCloner cloner = new StringTemplateCloner();
		ST predicate = perfect.getConjunctionFromList(implicitPreconditions);
		ST clonedTemplate = cloner.clone(predicate);
		
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
		
		for (Variable deltaVar: deltaVariables.asList()){
			Variable primedDeltaVar = deltaVar.getPrimedCopy();
			Variable tempDeltaVar = deltaVar.getTempCopy();
			declarations.add(perfect.getDeclarationInOperation(tempDeltaVar));
			
			ST oldIdentifier = PerfectTemplateProvider.getInstance().getId(primedDeltaVar.getId());
			ST newIdentifier = PerfectTemplateProvider.getInstance().getId(tempDeltaVar.getId());
			substitutor.substituteIdentifier(clonedTemplate, oldIdentifier, newIdentifier);
		}
		
		for (Variable outputVar: outputParameters.asList()){
			declarations.add(perfect.getDeclarationInOperation(outputVar));
		}
		
		ST existsPrecondition = perfect.getExists(declarations, clonedTemplate);
		return existsPrecondition;
	}

	public ST createExistsPreconditionForScopeEnrichment(Declarations decoratedVariables, List<ST> preconditions){
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
		
		List<ST> declarations = new ArrayList<ST>();
		
		ST predicate = perfect.getConjunctionFromList(preconditions);
		ST clonedTemplate = cloner.clone(predicate);
		
		for (Variable decoratedVariable: decoratedVariables.asList()){
			Variable tempVar = decoratedVariable.getTempCopy();
			ST idTemplate = perfect.getId(tempVar.getId());
			ST typeTemplate = tempVar.getDeclaredTypeTemplate();
			declarations.add(perfect.getBoundVariableDeclarationOfCollection(idTemplate, typeTemplate));
			
			ST oldIdentifier = PerfectTemplateProvider.getInstance().getId(decoratedVariable.getId());
			ST newIdentifier = PerfectTemplateProvider.getInstance().getId(tempVar.getId());
			substitutor.substituteIdentifier(clonedTemplate, oldIdentifier, newIdentifier);
		}
		
		ST existsPrecondition = perfect.getExists(declarations, clonedTemplate);	
		return existsPrecondition;
	}
	
	public ST createNonDeterministicPrecondition(NonDeterministicPreconditions nonDeterministicPreconditions, TemporaryVariableMap outerTempVarMap) {
		List<PreconditionItem> preconditionItems = nonDeterministicPreconditions.getPreconditions();
		List<ST> preconditionItemsForDisjunction = new ArrayList<>();
		for (PreconditionItem p: preconditionItems){
			ST composablePreconditionTemplate = null;
			
			if (outerTempVarMap != null){
				composablePreconditionTemplate = perfect.getConjunctionFromList(p.getComposablePrecondition().getSequentialTemplates(outerTempVarMap));
			} else {
				composablePreconditionTemplate = perfect.getConjunctionFromList(p.getComposablePrecondition().getTemplates());
			}
			preconditionItemsForDisjunction.add(composablePreconditionTemplate);
		}
		ST nonDeterministicChoicePrecondition = perfect.getDisjunctionFromList(preconditionItemsForDisjunction);
		return nonDeterministicChoicePrecondition;
	}
}
