package at.ac.tuwien.oz.datatypes.preconditions.composable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class NonDeterministicPreconditions implements IComposablePreconditions {

	public static class PreconditionItem{
		private Map<Variable, List<IPromotedOperation>> allPromotingOperations;
		private IComposablePreconditions composablePrecondition;
		
		private PreconditionItem(){
			this.allPromotingOperations = new HashMap<Variable, List<IPromotedOperation>>();
		}
		
		public PreconditionItem(IComposablePreconditions precondition, List<IPromotedOperation> promotingOperations){
			this();
			this.composablePrecondition = precondition;
			for (IPromotedOperation promotingOperation: promotingOperations){
				for (Variable outputVariable: promotingOperation.getOutputParameters()){
					List<IPromotedOperation> promotingOperationsOfVariable = allPromotingOperations.get(outputVariable);
					if (promotingOperationsOfVariable == null){
						promotingOperationsOfVariable = new ArrayList<IPromotedOperation>();
						allPromotingOperations.put(outputVariable, promotingOperationsOfVariable);
					}
					promotingOperationsOfVariable.add(promotingOperation);
				}
			}
		}
		
		public List<IPromotedOperation> getPromotingOperations(Variable outputVariable){
			List<IPromotedOperation> promotingOperationsOfVariable = allPromotingOperations.get(outputVariable);
			if (promotingOperationsOfVariable == null){
				return new ArrayList<IPromotedOperation>();
			} else {
				return promotingOperationsOfVariable;
			}
		}
		
		public IComposablePreconditions getComposablePrecondition(){
			return this.composablePrecondition;
		}
		
	}
	
	private List<PreconditionItem> preconditionItems;

	public NonDeterministicPreconditions(List<PreconditionItem> preconditions) {
		this.preconditionItems = preconditions;
	}

	@Override
	public List<ST> getTemplates() {
		return Arrays.asList(PerfectPredicateTemplateProvider.getInstance().createNonDeterministicPrecondition(this));
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap) {
		return Arrays.asList(PerfectPredicateTemplateProvider.getInstance().createNonDeterministicPrecondition(this, tempVarMap));
	}
	@Override
	public boolean isEmpty() {
		return preconditionItems.isEmpty();
	}

	@Override
	public int size() {
		return preconditionItems.size();
	}

	public List<PreconditionItem> getPreconditions() {
		return this.preconditionItems;
	}
}
