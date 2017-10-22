package at.ac.tuwien.oz.translator.templates;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.NonDeterministicChoiceItem;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class NonDeterministicChoiceTemplateProvider {
	
	private PerfectTemplateProvider			 perfect		   = PerfectTemplateProvider.getInstance();
	private PerfectPredicateTemplateProvider predicateProvider = PerfectPredicateTemplateProvider.getInstance();

	public ST createNonDeterministicPrecondition(NonDeterministicPreconditions preconditions, TemporaryVariableMap outerTempVarMap) {
		
		TemporaryVariableMap innerTempVarMap = new TemporaryVariableMap();
		
		List<ST> preconditionList = new ArrayList<ST>();
		
		for (NonDeterministicPreconditions.PreconditionItem precondition: preconditions.getPreconditions()){
			ST conjunction = createNonDeterministicPreconditionItem(precondition, outerTempVarMap, innerTempVarMap);
			preconditionList.add(conjunction);
		}
		
		return perfect.getDisjunctionFromList(preconditionList);
	}

	public ST createNonDeterministicPreconditionItem(NonDeterministicPreconditions.PreconditionItem precondition, TemporaryVariableMap outerTempVarMap, TemporaryVariableMap innerTempVarMap) {
		List<ST> composablePreconditions = 
				predicateProvider.createPreconditionTemplates(precondition.getComposablePrecondition(), innerTempVarMap, outerTempVarMap);
		
		if (outerTempVarMap != null){
			for (Variable undecoratedVariable: outerTempVarMap.originalVariables()){
				Variable outputVariable = undecoratedVariable.getOutputCopy();
				List<IPromotedOperation> promotedOperations = precondition.getPromotingOperations(outputVariable);
				if (promotedOperations != null && promotedOperations.size() >= 1){
					composablePreconditions.add(
							predicateProvider.createTempVarEqualsOutputPromotionInFunction(outputVariable, promotedOperations.get(0), outerTempVarMap.get(undecoratedVariable)));
				}
			}
		}
		ST conjunction = perfect.getConjunctionFromList(composablePreconditions);
		return conjunction;
	}

	public ST createNonDeterministicPostcondition(NonDeterministicPostconditions nonDeterministicPostconditions, TemporaryVariableMap temporaryVariables, Context context) {
		List<ST> nonDeterministicItems = new ArrayList<ST>();
		for (NonDeterministicChoiceItem item: nonDeterministicPostconditions.getElements()){
			nonDeterministicItems.add(createNonDeterministicChoiceItem(item, temporaryVariables, context));
		}
		ST disjunctionST = perfect.getItemList(nonDeterministicItems);
		ST parenthesizedST = perfect.getParenthesized(disjunctionST);
		return parenthesizedST;
	}

	public ST createNonDeterministicChoiceItem(NonDeterministicChoiceItem choiceItem, TemporaryVariableMap outerTempVarMap, Context context) {
		ST preconditionST = createNonDeterministicPreconditionItem(choiceItem.getPrecondition(), outerTempVarMap, new TemporaryVariableMap());
		ST postconditionST = perfect.getConjunctionFromList(choiceItem.getPostcondition().getSequentialTemplates(outerTempVarMap, context));

		ST choice = perfect.getChoice(preconditionST, postconditionST);
		return choice;

	}

}
