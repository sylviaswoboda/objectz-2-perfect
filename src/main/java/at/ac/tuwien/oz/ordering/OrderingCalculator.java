package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.List;


public class OrderingCalculator {

	private PredicateDependencyGraph graph;
	private List<OrderingItem> orderingItems;
	
	public OrderingCalculator(PredicateDependencyGraph graph){
		this.graph = graph;
		this.orderingItems = new ArrayList<OrderingItem>();
	}
	
	public List<OrderingItem> calculateOrdering(){
		// step 1
		addNonCollectionVariables();
		
		// step 2
		if(predicateWithoutNeighboursExists()){
			throw new RuntimeException("Predicate without neighbouring variable nodes found");
		}
		
		// step 3
		addVariablesWithoutNeighbours();
		
		boolean addedPredicateWithExactlyOneNeighbour = false;
		boolean addedPredicateWithMoreThanOneNeighbour = false;
		
		do {
			do{
				// step 4
				addedPredicateWithExactlyOneNeighbour = addPredicateWithExactlyOneNeighbour();
			} while(addedPredicateWithExactlyOneNeighbour);

			addedPredicateWithMoreThanOneNeighbour = addPredicateWithMoreThanOneNeighbour();
		} while(addedPredicateWithMoreThanOneNeighbour && !graph.getVariableNodes().isEmpty());
		
		if (!graph.getPredicateNodes().isEmpty()){
			throw new RuntimeException("There are remaining predicate nodes. Ordering is not possible");
		}
		
		return orderingItems;
	}

	private VariableNode getVariableWithMinimalNeighbours(
			PredicateDependencyGraph subGraph) {
		VariableNode selectedVariableNode = null;
		int minimumNeighbourSize = subGraph.getPredicateNodes().size()+1;
		for (VariableNode varNode: subGraph.getVariableNodes()){
			int currentNeighbourSize = subGraph.getNeighbours(varNode).size();
			
			if (currentNeighbourSize < minimumNeighbourSize){
				minimumNeighbourSize = currentNeighbourSize;
				selectedVariableNode = varNode;
			}
		}
		return selectedVariableNode;
	}

	private boolean addPredicateWithMoreThanOneNeighbour() {
		List<PredicateNode> predicatesWithMoreThanOneNeighbours = findPredicatesWithMoreThanOneNeighbour();
		
		if (predicatesWithMoreThanOneNeighbours.isEmpty()){
			return false;
		}
		
		PredicateDependencyGraph subGraph = graph.getSubgraphWithPredicates(predicatesWithMoreThanOneNeighbours);
		VariableNode selectedVariableNode = getVariableWithMinimalNeighbours(subGraph);
		
		List<PredicateNode> predicateNodesToAdd = new ArrayList<PredicateNode>();
		for (PredicateNode p: subGraph.getNeighbouringPredicates(selectedVariableNode)){
			int numberOfNeighbours = subGraph.getNeighbours(p).size();
			if  (numberOfNeighbours == 1){
				predicateNodesToAdd.add(p);
			}
		}
		
		if (selectedVariableNode != null){
			addVariableToOrdering(selectedVariableNode, predicateNodesToAdd);
			graph.removeVariableAndPredicates(selectedVariableNode, predicateNodesToAdd);
			return true;
		}
		return false;
	}

	private boolean addPredicateWithExactlyOneNeighbour() {
		VariableNode selectedVariableNode = null;
		List<PredicateNode> predicateNodesToAdd = new ArrayList<PredicateNode>();
	
		List<PredicateNode> predicatesWithExactlyOneNeighbour = findPredicatesWithExactlyOneNeighbour();
		
		if (predicatesWithExactlyOneNeighbour.size() == 1){
			PredicateNode selectedPredicateNode = predicatesWithExactlyOneNeighbour.get(0);
			selectedVariableNode = getFirstVariableNeighbour(selectedPredicateNode);
			
			predicateNodesToAdd.add(selectedPredicateNode);
		} else if (predicatesWithExactlyOneNeighbour.size() > 1){
			PredicateDependencyGraph subgraph = graph.getSubgraphWithPredicates(predicatesWithExactlyOneNeighbour);
			
			selectedVariableNode = graph.getVariableWithSmallestIndex(subgraph);
			predicateNodesToAdd = subgraph.getNeighbouringPredicates(selectedVariableNode);
		}
		
		if (selectedVariableNode != null){
			addVariableToOrdering(selectedVariableNode, predicateNodesToAdd);
			graph.removeVariableAndPredicates(selectedVariableNode, predicateNodesToAdd);
			return true;
		}
		return false;
	}

	private void addVariablesWithoutNeighbours() {
		List<VariableNode> variablesWithoutNeighbours = findVariablesWithoutNeighbours();
		addVariablesToOrdering(variablesWithoutNeighbours);
		removeVariablesFromGraph(variablesWithoutNeighbours);
	}

	private void addNonCollectionVariables() {
		List<VariableNode> preOrUserdefinedTypeVariables = findVariablesWithPreOrUserdefinedType();
		addVariablesToOrdering(preOrUserdefinedTypeVariables);
		removeVariablesFromGraph(preOrUserdefinedTypeVariables);
	}

	private List<PredicateNode> findPredicatesWithMoreThanOneNeighbour() {
		List<PredicateNode> predicatesWithMoreThanOneNeighbour = new ArrayList<PredicateNode>();
		
		for (PredicateNode p: this.graph.getPredicateNodes()){
			List<Node> neighbours = graph.getNeighbours(p);
			if (neighbours.size() > 1){
				predicatesWithMoreThanOneNeighbour.add(p);
			}
		}
		return predicatesWithMoreThanOneNeighbour;
	}

	private List<PredicateNode> findPredicatesWithExactlyOneNeighbour() {
		List<PredicateNode> predicatesWithExactlyOneNeighbour = new ArrayList<PredicateNode>();
		
		for (PredicateNode p: this.graph.getPredicateNodes()){
			List<Node> neighbours = graph.getNeighbours(p);
			if (neighbours.size() == 1){
				predicatesWithExactlyOneNeighbour.add(p);
			}
		}
		return predicatesWithExactlyOneNeighbour;
	}

	private List<VariableNode> findVariablesWithoutNeighbours() {
		List<VariableNode> variablesWithoutNeighbours = new ArrayList<VariableNode>();
		
		for (VariableNode v: graph.getVariableNodes()){
			if (!graph.hasNeighbours(v)){
				variablesWithoutNeighbours.add(v);
			}
		}
		return variablesWithoutNeighbours;
	}

	private List<VariableNode> findVariablesWithPreOrUserdefinedType() {
		List<VariableNode> preOrUserdefinedTypeVariables = new ArrayList<VariableNode>();
		
		for (VariableNode v: graph.getVariableNodes()){
//			if (!v.hasCollectionType(typeEvaluator)){
//				preOrUserdefinedTypeVariables.add(v);
//			}
			if (v.hasPredefinedType()){
				preOrUserdefinedTypeVariables.add(v);
			}
			if (v.hasUserDefinedType()){
				preOrUserdefinedTypeVariables.add(v);
			}
		}
		return preOrUserdefinedTypeVariables;
	}

	private VariableNode getFirstVariableNeighbour(PredicateNode p) {
		List<Node> variableNodes = graph.getNeighbours(p);
		return (VariableNode)variableNodes.get(0);
	}

	private boolean predicateWithoutNeighboursExists() {
		for(PredicateNode predicate: graph.getPredicateNodes()){
			if (!graph.hasNeighbours(predicate)){
				return true;
			}
		}
		return false;
	}

	private void removeVariablesFromGraph(List<VariableNode> variablesToRemove) {
		for(VariableNode v: variablesToRemove){
			graph.removeVariableAndPredicates(v, null);
		}
	}

	private void addVariablesToOrdering(List<VariableNode> variablesToAdd) {
		for (VariableNode v: variablesToAdd){
			addVariableToOrdering(v, new ArrayList<PredicateNode>());
		}
	}

	private void addVariableToOrdering(VariableNode v, List<PredicateNode> predicatesToAdd) {
		OrderingItem item = new OrderingItem(v);
		for (PredicateNode p: predicatesToAdd){
			item.addPredicate(p);
		}
		this.orderingItems.add(item);
	}
}
