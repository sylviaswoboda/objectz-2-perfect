package at.ac.tuwien.oz.translator.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexChangePostconditionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexOutputPromotionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.NonDeterministicChoiceItem;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.ThenPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.TempVarProvider;

public class PerfectPostconditionTemplateProvider extends PerfectPredicateTemplateProvider{

	private static PerfectPostconditionTemplateProvider postconditionProvider;

	public static PerfectPostconditionTemplateProvider getInstance() {
		if (postconditionProvider == null) {
			postconditionProvider = new PerfectPostconditionTemplateProvider();
		}
		return postconditionProvider;
	}

	public static void setInstance(PerfectPostconditionTemplateProvider provider) {
		postconditionProvider = provider;
	}

	public PerfectPostconditionTemplateProvider(){
		
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
		Variable promotedOutput = outputPromotion.getOutputVariable();
		
		ST promotedOutputST = perfect.getOutputVariableReference(promotedOutput.getId());
		ST promotedFunctionCallST = getOutputCall(outputPromotion);
		ST equality = perfect.getEquals(promotedOutputST, promotedFunctionCallST);
		return equality;
	}
	
	public ST createOutputPromotionInFunction(OutputPromotion outputPromotion, TemporaryVariableMap outerTempVarMap) {
		Variable tempVar;
		ST outputPromotionInFunction;
		if (outerTempVarMap == null){
			tempVar = null;
		} else {
			tempVar = outerTempVarMap.get(outputPromotion.getOutputVariable().getUndecoratedCopy());
		}
		if (tempVar == null){
			// create output promotion normally
			outputPromotionInFunction = createOutputPromotionInFunction(outputPromotion);
		} else {
			// if output promotion promotes one of the outerTempVarMap variables => create template using tempVar as output variable
			outputPromotionInFunction = createTempVarEqualsOutputPromotionInFunction(outputPromotion, tempVar);
		}
		outputPromotionInFunction = substituteInputByTemporaryVariables(outputPromotionInFunction, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
		return outputPromotionInFunction;
	}
	
	/**
	 * Creates an output promotion template for schemas.
	 * 
	 * @param outputPromotion   the output promotion to be promoted
	 * 
	 * @return template of the form outVar! = a.func(inVar).outVar
	 */
	public ST createOutputPromotionInSchema(OutputPromotion outputPromotion) {
		Variable promotedOutput = outputPromotion.getOutputVariable();
		IPromotedOperation promotedOperation = outputPromotion.getPromotedOperation();
		
		ST promotedOutputST = perfect.getWithExclamation(perfect.getId(promotedOutput.getId()));
		ST promotedFunctionCallST = getOutputCall(promotedOutput, promotedOperation);
		return perfect.getEquals(promotedOutputST, promotedFunctionCallST);
	}

	/**
	 * Creates an output promotion template for schemas using the given temporary variables.
	 * 
	 * @param outputPromotion  outputPromotion to be promoted
	 * @param communicationTempVarMap contains all communication variables that are potentially used in the given promoted operation 
	 *                        as input or output parameters.
	 * @param type indicates whether input variables substituted by temporary variables from outerTempVarMap should be primed.
	 *                        
	 * @return template of the form tempVar1! = a.func(tempVar2).outVar where tempVar1 and tempVar2 are two different variables in the 
	 *         communicationTempVarMap, if communicationTempVarMap is empty, no substitutions are done and the output equals the output of 
	 *         {@link #createOutputPromotionInSchema(OutputPromotion)}
	 */
	public ST createOutputPromotionInSchema(OutputPromotion outputPromotion, TemporaryVariableMap communicationTempVarMap,
			InputSubstitutionType type) {
		Variable promotedOutput = outputPromotion.getOutputVariable();
		
		Variable tempVar = null;
		if (communicationTempVarMap != null){
			tempVar = communicationTempVarMap.get(promotedOutput);
		}
		ST promotedOutputST = null;
		if (tempVar != null) {
			promotedOutputST = createTempVarEqualsOutputPromotionInSchema(outputPromotion, tempVar);
		} else {
			promotedOutputST = createOutputPromotionInSchema(outputPromotion);
		}
		return substituteInputByTemporaryVariables(promotedOutputST, communicationTempVarMap, type);
	}
	
	
	/**
	 * Creates a schema call template.
	 * 
	 * @param promotedOperation the operation to be promoted
	 *                        
	 * @return template of the form a!call1(inVar, outVar!)
	 */
	public ST createChangeOperationCall(IPromotedOperation promotedOperation) {
		ST caller = promotedOperation.getCaller();
		String operationName = promotedOperation.getPromotedOperation().getName();
		ST parameterList = getParenthesizedParameterListOfSchemaCall(
				promotedOperation.getInputParameters().sorted(), promotedOperation.getOutputParameters().sorted());
		return perfect.getSchemaCall(caller, operationName, parameterList);
	}
	
	public ST createChangeOperationCall(IPromotedOperation promotedOperation, TemporaryVariableMap outerTempVarMap) {
		ST promotedSchemaCall = createChangeOperationCall(promotedOperation);
		promotedSchemaCall = substituteOutputByTemporaryVariables(promotedSchemaCall, outerTempVarMap);
		promotedSchemaCall = substituteInputByTemporaryVariables(promotedSchemaCall, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
		return promotedSchemaCall;
	}
	
	/**
	 * Creates a schema call template using the given temporary variables.
	 * 
	 * @param promotedOperation the operation to be promoted
	 * @param tempVarMap contains all communication or shared variables that are potentially used in the given promoted operation 
	 *                        as input or output parameters.
	 *                        
	 * @return template of the form tempVar1! = a.func(tempVar2).outVar where tempVar1 and tempVar2 are two different variables in the 
	 *         outerTempVarMap, if tempVarMap is empty, no substitutions are done and the output equals the output of 
	 *         {@link #createOutputPromotionInSchema(OutputPromotion)}
	 */
	public ST createChangeOperationCall(IPromotedOperation promotedOperation, TemporaryVariableMap tempVarMap, 
			Map<Variable, Boolean> sharedOutputSubstitutionMap, InputSubstitutionType inputSubstitutionType) {
		ST promotedSchemaCall = createChangeOperationCall(promotedOperation);
		promotedSchemaCall = substituteOutputByTemporaryVariables(promotedSchemaCall, promotedOperation, tempVarMap, sharedOutputSubstitutionMap);
		promotedSchemaCall = substituteInputByTemporaryVariables(promotedSchemaCall, tempVarMap, inputSubstitutionType);
		return promotedSchemaCall;
	}

	/**
	 * Creates a template for a complex output promotion mapping in the context of a function
	 * 
	 * @param mapping - the mapping to create an exists postcondition template
	 * 
	 * @return an exists postcondition template representing the contents of the mapping.
	 */
	public ST createExistsPostcondition(ComplexOutputPromotionMapping mapping, TemporaryVariableMap outerTempVarMap) {
		List<ST> tempVarDeclarations = new ArrayList<ST>();
		List<ST> predicateList = new ArrayList<ST>();

		TemporaryVariableMap innerTempVarMap = new TemporaryVariableMap();

		List<Variable> communicationVariables = mapping.getAllCommunicationVars().sorted().asList();
		List<Variable> visibleCommunicationVariables = mapping.getVisibleCommunicationVars().asList();
		List<OutputPromotion> communicatingPromotions = mapping.getCommunicationPromotions().getElements();
		List<OutputPromotion> outputPromotions = mapping.getOtherPromotions().getElements();

		fillTemporaryVariablesFor(tempVarDeclarations, innerTempVarMap, communicationVariables);

		// add predicates (using tempVarMap)
		predicateList.addAll(
				createCommunicationOutputPromotionsInFunction(communicatingPromotions, innerTempVarMap, outerTempVarMap));

		predicateList.addAll(
				createOutputPromotionsInFunction(outputPromotions, innerTempVarMap, outerTempVarMap));

		predicateList.addAll(
				createOutputVariablesEqualTempVarsInFunction(visibleCommunicationVariables, innerTempVarMap, outerTempVarMap));

		ST predicates = perfect.getConjunctionFromList(predicateList);

		return perfect.getExists(tempVarDeclarations, predicates);
	}

	/**
	 * Creates a template for sequential function postconditions
	 * 
	 * @param sequentialPostconditions - the sequential postconditions to create an exists postcondition template
	 * 
	 * @return an exists postcondition template representing the contents of the sequentialPostconditions.
	 */
	// TODO Test
	public ST createSatisfyPostcondition(SequentialPostconditions sequentialPostconditions) {
		List<ST> tempVarDeclarations = new ArrayList<ST>();
		List<ST> predicateList = new ArrayList<ST>();
	
		TemporaryVariableMap tempVarMap = new TemporaryVariableMap();
	
		List<Variable> communicationVariables = sequentialPostconditions.getAllCommunicationVariables().sorted().asList();
		
		fillTemporaryVariablesFor(tempVarDeclarations, tempVarMap,	communicationVariables);
		
		for (int i = 0; i < sequentialPostconditions.getPostconditions().size(); i++){
			IComposablePostconditions composablePostcondition = sequentialPostconditions.getPostconditions().get(i);
			Declarations inputCommunicationVariables = new Declarations();
			Declarations outputCommunicationVariables = new Declarations();
			
			if (i-1 >= 0){
				inputCommunicationVariables = sequentialPostconditions.getCommunicationVariables().get(i-1);
			}
			
			if (i < sequentialPostconditions.getCommunicationVariables().size()){
				outputCommunicationVariables = sequentialPostconditions.getCommunicationVariables().get(i);
			}
			
			TemporaryVariableMap itemTempVarMap = new TemporaryVariableMap();
			for (Variable v: inputCommunicationVariables){
				itemTempVarMap.add(v, tempVarMap.get(v)); // copy items to a new tempVarMap only for this postcondition
			}
			for (Variable v: outputCommunicationVariables){
				itemTempVarMap.add(v, tempVarMap.get(v)); // copy items to a new tempVarMap only for this postcondition
			}
			List<ST> composablePostconditionST = composablePostcondition.getSequentialTemplates(itemTempVarMap, Context.FUNCTION);
			
			ST predicates = perfect.getConjunctionFromList(composablePostconditionST);
			predicateList.add(predicates);
		}
		ST predicates = perfect.getConjunctionFromList(predicateList);
	
		if (tempVarDeclarations.isEmpty()){
			return predicates;
		} else {
			return perfect.getExists(tempVarDeclarations, predicates);
		}
	}

	/**
	 * Creates a template from a mapping in the context of an operation composition.
	 * 
	 * 
	 * @param mapping         - the mapping to create an exists postcondition template
	 * @param outerTempVarMap - contains temporary variables to map communication from or to this postcondition.
	 * 							The temporary variables declarations will be declared outside this template.
	 *                          They are only supposed to be used for variable substitution.
	 * 
	 * @return an exists postcondition template representing the contents of the mapping.
	 */
	// TODO test
	public ST createVarPostcondition(ComplexOutputPromotionMapping mapping, TemporaryVariableMap outerTempVarMap) {

		List<ST> tempVarDeclarations = new ArrayList<ST>();
		List<ST> predicateList = new ArrayList<ST>();

		TemporaryVariableMap innerTempVarMap = new TemporaryVariableMap();

		List<Variable> communicationVariables = mapping.getAllCommunicationVars().sorted().asList();
		List<Variable> visibleCommunicationVariables = mapping.getVisibleCommunicationVars().asList();
		List<OutputPromotion> communicatingPromotions = mapping.getCommunicationPromotions().getElements();
		List<OutputPromotion> outputPromotions = mapping.getOtherPromotions().getElements();

		fillTemporaryVariablesFor(tempVarDeclarations, innerTempVarMap, communicationVariables);

		// add predicates (using tempVarMap)
		predicateList.addAll(createCommunicationOutputPromotionsInSchema(communicatingPromotions, innerTempVarMap, outerTempVarMap));

		predicateList.addAll(createOutputPromotionsInSchema(outputPromotions, innerTempVarMap, outerTempVarMap));

		predicateList.addAll(createOutputVariablesEqualTempVarsInSchema(visibleCommunicationVariables, innerTempVarMap));

		ST tempVarDeclarationListST = perfect.getItemList(tempVarDeclarations);
		ST predicates = perfect.getConjunctionFromList(predicateList);

		return perfect.getPostconditionWithDeclarations(tempVarDeclarationListST, predicates);

	}

	public ST createVarPostcondition(ComplexChangePostconditionMapping mapping, TemporaryVariableMap outerCommunicationTempVarMap) {
		List<ST> predicateList = new ArrayList<ST>();
		List<ST> tempVarDeclarations = new ArrayList<ST>();
		
		TemporaryVariableMap innerTempVarMap = new TemporaryVariableMap();
		
		Map<Variable, Boolean> innerOutputVariableSubstituted = new HashMap<Variable, Boolean>();
	
		List<OutputPromotion> communicatingPromotions = mapping.getCommunicationOutputPromotions().getElements();
		List<OutputPromotion> outputPromotions = mapping.getOutputPromotions().getElements();
		List<ChangeOperationCall> communicatingCalls = mapping.getCommunicationChangeOperationCalls().asList();
		List<Variable> visibleCommunicationVariables = mapping.getVisibleCommunicationVariables().asList();
		
		fillTemporaryVariables(tempVarDeclarations, innerTempVarMap, innerOutputVariableSubstituted, mapping);
		
		predicateList.addAll(
				createCommunicationOutputPromotionsInSchema(communicatingPromotions, 
						innerTempVarMap, 
						outerCommunicationTempVarMap));
	
		predicateList.addAll(
				createOutputPromotionsInSchema(outputPromotions, 
						innerTempVarMap, 
						outerCommunicationTempVarMap));
	
		predicateList.addAll(
				createCommunicatingCallsInSchema(communicatingCalls, 
						innerTempVarMap, 
						outerCommunicationTempVarMap,
						innerOutputVariableSubstituted));
	
		predicateList.addAll(
				createOutputVariablesEqualTempVarsInSchema(visibleCommunicationVariables, 
						innerTempVarMap));
	
		ST tempVarDeclarationList = perfect.getItemList(tempVarDeclarations);
		ST predicates = perfect.getConjunctionFromList(predicateList);
		return perfect.getPostconditionWithDeclarations(tempVarDeclarationList, predicates);
	}

	public ST createThenPostcondition(ThenPostcondition thenPostconditions, TemporaryVariableMap outerCommunicationTempVarMap) {
		List<ST> tempVarDeclarations = new ArrayList<ST>();
		
		TemporaryVariableMap sharedAndCommunicationTempVars = new TemporaryVariableMap();

		Map<Variable, Boolean> outputVariableSubstituted = new HashMap<Variable, Boolean>();
		
		List<OutputPromotion> communicatingOutputPromotions = thenPostconditions.getCommunicatingPromotions().getElements();
		List<List<ChangeOperationCall>> postconditionList = thenPostconditions.getPostconditions();
		Map<Variable, Integer> communicationOutputIndices = thenPostconditions.getCommunicationOutputVariableIndices();
		List<Variable> visibleCommunicationVariables = thenPostconditions.getVisibleCommunicationVariables().asList();

		// add temporary variables for communication and sharedOutput
		fillTemporaryVariables(tempVarDeclarations, sharedAndCommunicationTempVars, outputVariableSubstituted, thenPostconditions);
	
		List<ST> operationPromotionsST = createOperationPromotionTemplates(sharedAndCommunicationTempVars, outerCommunicationTempVarMap, communicatingOutputPromotions);
	
		List<ST> thenPostconditionsST = createThenPostconditionTemplates(sharedAndCommunicationTempVars, outerCommunicationTempVarMap,
				outputVariableSubstituted, communicationOutputIndices, postconditionList, operationPromotionsST);
	
		thenPostconditionsST.addAll(
				createOutputVariablesEqualTempVarsInSchema(sharedAndCommunicationTempVars, outerCommunicationTempVarMap,
							visibleCommunicationVariables));
	
		ST predicates = perfect.getThenPostconditions(thenPostconditionsST);
	
		if (sharedAndCommunicationTempVars.size() == 0) {
			return predicates;
		} else {
			ST tempVarDeclarationList = perfect.getItemList(tempVarDeclarations);
	
			ST varPostcondition = perfect.getPostconditionWithDeclarations(tempVarDeclarationList, predicates);
			return varPostcondition;
		}
	}
	
	public ST createThenPostcondition(SequentialPostconditions sequentialPostconditions,
			TemporaryVariableMap outerTempVarMap) {
		List<ST> tempVarDeclarations = new ArrayList<ST>();
		List<ST> thenPostconditionsST = new ArrayList<ST>();
	
		TemporaryVariableMap innerTempVarMap = new TemporaryVariableMap();
		
		List<Variable> communicationVariables = sequentialPostconditions.getAllCommunicationVariables().sorted().asList();
		
		fillTemporaryVariablesFor(tempVarDeclarations, innerTempVarMap, communicationVariables);
	
		for (int i = 0; i < sequentialPostconditions.getPostconditions().size(); i++){
			IComposablePostconditions composablePostcondition = sequentialPostconditions.getPostconditions().get(i);
			Declarations inputCommunicationVariables = new Declarations();
			Declarations outputCommunicationVariables = new Declarations();
			
			if (i-1 >= 0){
				inputCommunicationVariables = sequentialPostconditions.getCommunicationVariables().get(i-1);
			}
			
			if (i < sequentialPostconditions.getCommunicationVariables().size()){
				outputCommunicationVariables = sequentialPostconditions.getCommunicationVariables().get(i);
			}
			
			TemporaryVariableMap itemTempVarMap = new TemporaryVariableMap();
			for (Variable v: inputCommunicationVariables){
				itemTempVarMap.add(v, innerTempVarMap.get(v)); // copy items to a new tempVarMap only for this postcondition
			}
			for (Variable v: outputCommunicationVariables){
				itemTempVarMap.add(v, innerTempVarMap.get(v)); // copy items to a new tempVarMap only for this postcondition
			}
			List<ST> postconditionTemplates = composablePostcondition.getSequentialTemplates(itemTempVarMap, Context.SCHEMA);
			
			thenPostconditionsST.add(perfect.getConjunctionFromList(postconditionTemplates));
		}
		ST thenPostconditions = perfect.getThenPostconditions(thenPostconditionsST);
		
		if (outerTempVarMap != null){
			// substitute outer communication output var references by tempVar
			// a!call(outerCommVar1_out!)
			// replace by
			// a!call(tempVar1_out!)
			thenPostconditions = substituteInputByTemporaryVariables(thenPostconditions, outerTempVarMap, 
					InputSubstitutionType.DO_NOT_PRIME);
			thenPostconditions = substituteOutputByTemporaryVariables(thenPostconditions, outerTempVarMap);
		}
		
		if (innerTempVarMap.size() == 0) {
			return thenPostconditions;
		} else {
			ST tempVarDeclarationList = perfect.getItemList(tempVarDeclarations);
			ST varPostcondition = perfect.getPostconditionWithDeclarations(tempVarDeclarationList, thenPostconditions);
			return varPostcondition;
		}
	}

	private List<ST> createCommunicationOutputPromotionsInFunction(List<OutputPromotion> communicatingPromotions, 
			TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap) {
		List<ST> predicateList = new ArrayList<ST>();
		
		for (OutputPromotion communicationOutputPromotion : communicatingPromotions) {
			Variable outputVariable = communicationOutputPromotion.getOutputVariable();
			Variable tempVar = null;
	
			if (innerTempVarMap.containsKey(outputVariable)) {
				tempVar = innerTempVarMap.get(outputVariable);
			} else if (outerTempVarMap.containsKey(outputVariable)) {
				tempVar = outerTempVarMap.get(outputVariable);
			}
			ST tempVarEquality = createTempVarEqualsOutputPromotionInFunction(communicationOutputPromotion, tempVar);
			ST substitutedCall = substituteInputByTemporaryVariables(tempVarEquality, innerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
			substitutedCall = substituteInputByTemporaryVariables(substitutedCall, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
			predicateList.add(substitutedCall);
		}
		
		return predicateList;
	}

	private List<ST> createOutputPromotionsInFunction(List<OutputPromotion> outputPromotions, 
			TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap) {
		List<ST> predicateList = new ArrayList<ST>();
		for (OutputPromotion outputPromotion : outputPromotions) {
			ST outputPromotionST = outputPromotion.getTemplate(outerTempVarMap, Context.FUNCTION);
			ST substitutedCall = substituteInputByTemporaryVariables(outputPromotionST, innerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
			substitutedCall = substituteInputByTemporaryVariables(substitutedCall, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
			predicateList.add(substitutedCall);
		}
		return predicateList;
	}
	
	/**
	 * Creates a template line for each of the given communicating Promotions.
	 * 
	 * @outerTempVarMap variables that will be substituted when they are used as Input or output in one of the output promotion templates
	 *                  that is the output variable is substituted. The variable Declaration however appears in a surrounding context
	 */
	private List<ST> createCommunicationOutputPromotionsInSchema(List<OutputPromotion> communicatingPromotions,
			TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap) {
		
		List<ST> predicateList = new ArrayList<ST>();
		
		for (OutputPromotion communicationOutputPromotion : communicatingPromotions) {
			Variable outputVariable = communicationOutputPromotion.getOutputVariable();

			Variable tempVar = null;
			if (innerTempVarMap.containsKey(outputVariable)) {
				tempVar = innerTempVarMap.get(outputVariable);
			} else if (outerTempVarMap.containsKey(outputVariable)) {
				tempVar = outerTempVarMap.get(outputVariable);
			}
			ST tempVarEquality = createTempVarEqualsOutputPromotionInSchema(communicationOutputPromotion, tempVar);
			ST substitutedCall = substituteInputByTemporaryVariables(tempVarEquality, innerTempVarMap, InputSubstitutionType.PRIME);
			substitutedCall = substituteInputByTemporaryVariables(substitutedCall, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
			predicateList.add(substitutedCall);
		}
		
		return predicateList;
	}

	private List<ST> createOutputPromotionsInSchema(List<OutputPromotion> outputPromotions,
			TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap) {
		
		List<ST> predicateList = new ArrayList<ST>();
		for (OutputPromotion outputPromotion : outputPromotions) {
			ST outputPromotionST = createOutputPromotionInSchema(outputPromotion);
			outputPromotionST = substituteInputByTemporaryVariables(outputPromotionST, innerTempVarMap, InputSubstitutionType.PRIME);
			outputPromotionST = substituteInputByTemporaryVariables(outputPromotionST, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);

			predicateList.add(outputPromotionST);
		}
		return predicateList;
	}

	private List<ST> createOutputVariablesEqualTempVarsInSchema(List<Variable> visibleCommunicationVariables, 
			TemporaryVariableMap innerTempVarMap) {
		
		List<ST> predicateList = new ArrayList<ST>();
		for (Variable visibleCommunicationVariable : visibleCommunicationVariables) {
			ST tempVarEquality = 
					createOutputVariableEqualsTempVarInSchema(innerTempVarMap, visibleCommunicationVariable, true);
			predicateList.add(tempVarEquality);
		}
		return predicateList;
	}

	private ST createOutputVariableEqualsTempVarInSchema(TemporaryVariableMap tempVarMap, Variable undecoratedVariable,
			boolean primed) {
		Variable outputVariable = undecoratedVariable.getOutputCopy();
		Variable tempVar = tempVarMap.get(undecoratedVariable);

		ST communicationVarST = perfect.getWithExclamation(perfect.getId(outputVariable.getId()));
		Ident tempVarId = tempVar.getId();
		if (primed) {
			tempVarId = tempVarId.getPrimedCopy();
		}
		ST tempVarST = perfect.getId(tempVarId);
		ST tempVarEquality = perfect.getEquals(communicationVarST, tempVarST);
		return tempVarEquality;
	}

	private List<ST> createCommunicatingCallsInSchema(List<ChangeOperationCall> communicatingCalls,
			TemporaryVariableMap innerTempVarMap, TemporaryVariableMap outerTempVarMap,
			Map<Variable, Boolean> innerOutputVariableSubstituted) {
		List<ST> predicateList = new ArrayList<ST>();
		
		for (ChangeOperationCall communicationChangeCall : communicatingCalls) {
			IPromotedOperation promotion = communicationChangeCall.getOrderableOperation();
			ST callTemplate = createChangeOperationCall(promotion, innerTempVarMap, innerOutputVariableSubstituted, InputSubstitutionType.PRIME);
			callTemplate = substituteOutputByTemporaryVariables(callTemplate, outerTempVarMap);

			ST substitutedCall = substituteInputByTemporaryVariables(callTemplate, outerTempVarMap, InputSubstitutionType.DO_NOT_PRIME);
			predicateList.add(substitutedCall);
		}
		
		return predicateList;
	}

	private List<ST> createOperationPromotionTemplates(TemporaryVariableMap sharedAndCommunicationTempVarMap,
			TemporaryVariableMap outerCommunicationTempVarMap,
			List<OutputPromotion> communicatingOutputPromotions) {
		
		List<ST> operationPromotionsST = new ArrayList<ST>();
		
		for (OutputPromotion communicationOutputPromotion : communicatingOutputPromotions) {
			// outerCommVar1_out! = a.func.outerCommVar1_out (has been a normal promotion before and is now a communication variable)
			// replace by
			// tempVar1! = a.func.outerCommVar1_out
			
			// create template with innerSharedAndCommunicationTempVarMap
			ST promotionTemplate = createOutputPromotionInSchema(communicationOutputPromotion, sharedAndCommunicationTempVarMap, InputSubstitutionType.PRIME);
			// substitute outer communication input var references by primed tempVars
			promotionTemplate = substituteInputByTemporaryVariables(promotionTemplate, outerCommunicationTempVarMap, InputSubstitutionType.PRIME);
			// substitute outer communication output var references by tempVar in promotion 
			promotionTemplate = substituteOutputByTemporaryVariables(promotionTemplate, outerCommunicationTempVarMap);
			operationPromotionsST.add(promotionTemplate);
		}
		return operationPromotionsST;
	}

	private List<ST> createThenPostconditionTemplates(TemporaryVariableMap sharedAndCommunicationTempVarMap,
			TemporaryVariableMap outerCommunicationTempVarMap, Map<Variable, Boolean> outputVariableSubstituted,
			Map<Variable, Integer> communicationOutputIndices, List<List<ChangeOperationCall>> postconditionList,
			List<ST> operationPromotionsST) {
		int level = 0;
		List<ST> thenPostconditionsST = new ArrayList<ST>();
		for (List<ChangeOperationCall> postconditions : postconditionList) {
			List<ST> conjunctionST = new ArrayList<ST>();
	
			for (ChangeOperationCall postcondition : postconditions) {
				// create template using innerSharedAndCommunicationTempVarMap
				ST callTemplate = postcondition.getTemplate(sharedAndCommunicationTempVarMap, outputVariableSubstituted, 
									InputSubstitutionType.NONE);
				// substitute outer communication input var references by unprimed tempVars
				callTemplate = substituteInputByTemporaryVariables(callTemplate, outerCommunicationTempVarMap, 
									InputSubstitutionType.DO_NOT_PRIME);
				// substitute outer communication output var references by tempVar
				// a!call(outerCommVar1_out!)
				// replace by
				// a!call(tempVar1_out!)
				callTemplate = substituteOutputByTemporaryVariables(callTemplate, outerCommunicationTempVarMap);
				
				// substitute inner communication input var references by primed OR unprimed tempVars 
				// depending on inner output variable index -> calculate it once
				// and inner input variable index
				// if unequal => use unprimed
				for (Variable sharedOrCommunicationTempVar: sharedAndCommunicationTempVarMap.originalVariables()){
					Variable tempVar = sharedAndCommunicationTempVarMap.get(sharedOrCommunicationTempVar);
					//      if equal => use primed
					Integer communicationOutputIndex = communicationOutputIndices.get(sharedOrCommunicationTempVar.getOutputCopy());
					if (communicationOutputIndex != null){
						if (level == communicationOutputIndex){
							callTemplate = substituteByTemporaryVariables(callTemplate, sharedOrCommunicationTempVar.getInputCopy(), 
												tempVar, InputSubstitutionType.PRIME);
						} else {
							callTemplate = substituteByTemporaryVariables(callTemplate, sharedOrCommunicationTempVar.getInputCopy(), 
												tempVar, InputSubstitutionType.DO_NOT_PRIME);
						}
					}
				}
				conjunctionST.add(callTemplate);
			}
			if (level == 0){
				operationPromotionsST.addAll(conjunctionST);
				ST firstElement = perfect.getConjunctionFromList(operationPromotionsST);
				if (operationPromotionsST.size() > 1){
					thenPostconditionsST.add(perfect.getParenthesized(firstElement));
				} else {
					thenPostconditionsST.add(firstElement);
				}
			} else {
				thenPostconditionsST.add(perfect.getConjunctionFromList(conjunctionST));
			}
			level++;
		}
		return thenPostconditionsST;
	}

	private List<ST> createOutputVariablesEqualTempVarsInSchema(TemporaryVariableMap innerTempVarMap,
			TemporaryVariableMap outerTempVarMap, List<Variable> visibleCommunicationVariables) {
		List<ST> thenPostconditionsST = new ArrayList<ST>();
		for (Variable visibleCommunicationVariable : visibleCommunicationVariables) {
			List<ST> conjunctionST = new ArrayList<ST>();
	
			ST tempVarEquality = createOutputVariableEqualsTempVarInSchema(innerTempVarMap, visibleCommunicationVariable, false);
			tempVarEquality = substituteInputByTemporaryVariables(tempVarEquality, outerTempVarMap, InputSubstitutionType.PRIME);
			conjunctionST.add(tempVarEquality);
	
			thenPostconditionsST.add(perfect.getConjunctionFromList(conjunctionST));
		}
		return thenPostconditionsST;
	}

	public ST createVarPostconditionForScopeEnrichment(Variable auxVar, List<ST> auxVarPredicates, List<ST> postconditions) {
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
		
		List<ST> tempVarDeclarations = new ArrayList<ST>();
		
		Variable temporaryVariable = auxVar.getTempCopy();
		tempVarDeclarations.add(perfect.getDeclarationInOperation(temporaryVariable));
		ST newOuterTempVarTemplate = perfect.getId(temporaryVariable.getId());
		
		List<ST> predicateList = new ArrayList<ST>();
		
		Variable anyTempVar = auxVar.getTempCopy(TempVarProvider.getTempVarName());

		ST originalVarTemplate = perfect.getId(auxVar.getId());
		ST newVarTemplate = perfect.getId(anyTempVar.getId());
		
		ST predicate = perfect.getConjunctionFromList(auxVarPredicates);
		predicate = cloner.clone(predicate);
		predicate = substitutor.substituteIdentifier(predicate, originalVarTemplate, newVarTemplate);
		
		ST declaration = perfect.getDeclarationInOperation(anyTempVar);
		ST any = perfect.getAny(declaration, predicate);
		
		ST tempVarAsOutput = perfect.getIdentInSchema(temporaryVariable.getId());
		ST equals = perfect.getEquals(tempVarAsOutput, any);
		predicateList.add(equals);
		
		ST originalPostconditionConjunction = perfect.getConjunctionFromList(postconditions);
		ST postconditionConjunctionClone = cloner.clone(originalPostconditionConjunction);
		substitutor.substituteIdentifier(postconditionConjunctionClone, originalVarTemplate, newOuterTempVarTemplate);
		predicateList.add(postconditionConjunctionClone);
		
		ST thenPostcondition = perfect.getThenPostconditions(predicateList);
		ST tempVarDeclarationList = perfect.getItemList(tempVarDeclarations);
		return perfect.getPostconditionWithDeclarations(tempVarDeclarationList, thenPostcondition);
	}
	
	public ST createNonDeterministicChoiceItem(NonDeterministicChoiceItem choiceItem, TemporaryVariableMap temporaryVariables) {
		return null;
	}

}
