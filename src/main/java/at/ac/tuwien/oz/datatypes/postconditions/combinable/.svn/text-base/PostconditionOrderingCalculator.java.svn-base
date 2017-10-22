package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.OrderablePostcondition;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class PostconditionOrderingCalculator<T extends OrderablePostcondition> {
	
	private Map<T, ItemContext<T>> data;
	private List<ItemContext<T>> itemsWithoutPredecessors;
	private List<T> result;
	
	private static class ItemContext<T extends OrderablePostcondition>{
		public T item;
		public int numberOfPredecessors;
		public List<T> successors;
		
		public ItemContext(T item){
			this.item = item;
			this.numberOfPredecessors = 0;
			this.successors = new ArrayList<T>();
		}

		public void addSuccessor(T otherPostcondition) {
			this.successors.add(otherPostcondition);
		}

		public void increasePredecessors() {
			this.numberOfPredecessors++;
		}

		public void decreasePredecessors() {
			this.numberOfPredecessors--;
		}

		@Override
		public String toString() {
			return "ItemContext [item=" + item + ", numberOfPredecessors=" + numberOfPredecessors + ", successors="
					+ successors + "]";
		}
		
	}
	
	
	public PostconditionOrderingCalculator(){
	}

	private void initialize(List<T> postconditions) {
		data = new HashMap<T, PostconditionOrderingCalculator.ItemContext<T>>();
		itemsWithoutPredecessors = new ArrayList<ItemContext<T>>();
		result = new ArrayList<T>();
		
		// initialize itemcontext data structure
		for(T postcondition: postconditions){
			ItemContext<T> context = new ItemContext<T>(postcondition);
			data.put(postcondition, context);
		}
		
		// calculate predecessorCount and successors 
		for(T postcondition: postconditions){
			ItemContext<T> leftContext = data.get(postcondition);
			for (T otherPostcondition: postconditions){
				if (postcondition != otherPostcondition){
					ItemContext<T> rightContext = data.get(otherPostcondition);
					if (before(postcondition.getOrderableOperation(), otherPostcondition.getOrderableOperation())){
						leftContext.addSuccessor(otherPostcondition);
						rightContext.increasePredecessors();
					}
				}
			}
		}
		for(ItemContext<T> context: data.values()){
			if (context.numberOfPredecessors == 0){
				itemsWithoutPredecessors.add(context);
			}
		}
	}
	
	public List<T> order(List<T> postconditions) {
		initialize(postconditions);
		
		T nextItemWithZeroPredecessors = null;
		ItemContext<T> nextItem = null;
		boolean terminateLoop = false;
		
		while(!terminateLoop){
			if (itemsWithoutPredecessors.size() > 0){
				nextItem = itemsWithoutPredecessors.get(0);
				nextItemWithZeroPredecessors = nextItem.item;
				result.add(nextItemWithZeroPredecessors);

				if (nextItem.successors.size() > 0){
					for(T successor: nextItem.successors){
						ItemContext<T> successorItem = data.get(successor);
						successorItem.decreasePredecessors();
						if (successorItem.numberOfPredecessors == 0){
							itemsWithoutPredecessors.add(successorItem);
						}
					}
				}
				itemsWithoutPredecessors.remove(0);
			} else {
				terminateLoop = true;
			}
		}
		
		if (result.size() == data.size()){
			return result;
		} else {
			throw new ObjectZToPerfectTranslationException("Cannot find a suitable ordering");
		}
		//		quicksort(0, data.size()-1);
	}

	private boolean before(IOperation iOperation, IOperation iOperation2){
		Idents changedStateVarsLeft = iOperation.getChangedStateVariables();
		Idents changedStateVarsRight = iOperation2.getChangedStateVariables();
		Idents usedStateVariablesRight = iOperation2.getUsedStateVariables();
		Idents usedStateVariablesLeft = iOperation.getUsedStateVariables();
		
		if (changedStateVarsLeft.hasIntersection(changedStateVarsRight)){
			throw new ObjectZToPerfectTranslationException("Deltalists intersect. Translation not possible.");
		} else {
			if (usedStateVariablesLeft.hasIntersection(changedStateVarsRight) && !usedStateVariablesRight.hasIntersection(changedStateVarsLeft)){
				return true;
			} else if (!usedStateVariablesLeft.hasIntersection(changedStateVarsRight)){
				return false;
			} else {
				throw new ObjectZToPerfectTranslationException("Cannot calculate ordering between operations. Translation not possible.");
			}
		}
	}

}
