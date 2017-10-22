package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredicateDependencyGraph {

	private List<VariableNode> variableNodes;
	private List<PredicateNode> predicateNodes;
	private List<Edge> edges;
	
	private Map<Node, List<Node>> neighbourMap;
	
	public PredicateDependencyGraph(List<VariableNode> variables, List<PredicateNode> predicates, List<Edge> edges) {
		this.variableNodes = variables;
		this.predicateNodes = predicates;
		this.edges = edges;
		this.neighbourMap = new HashMap<Node, List<Node>>();
	}
	public List<VariableNode> getVariableNodes(){
		return this.variableNodes;
	}
	public List<PredicateNode> getPredicateNodes(){
		return this.predicateNodes;
	}
	public List<Edge> getEdges(){
		return this.edges;
	}
	public List<Node> getNeighbours(Node startNode){
		if (startNode == null){
			return null;
		}
		
		List<Node> neighbours = null;
		if(neighbourMap.containsKey(startNode)){
			neighbours = neighbourMap.get(startNode);
		} else {
			if (startNode.isPredicate()){
				neighbours = calculateNeighboursOfPredicate(startNode);
			} else {
				neighbours = calculateNeighboursOfVariable(startNode);
			}
			neighbourMap.put(startNode, neighbours);
		}
		return neighbours;
	}
	private List<Node> calculateNeighboursOfVariable(Node variableNode) {
		List<Node> neighbours = new ArrayList<Node>();
		for(Edge edge:edges){
			if (variableNode.equals(edge.getVariable())){
				neighbours.add(edge.getPredicate());
			}
		}
		return neighbours;
	}
	private List<Node> calculateNeighboursOfPredicate(Node predicateNode) {
		List<Node> neighbours = new ArrayList<Node>();
		for(Edge edge:edges){
			if (predicateNode.equals(edge.getPredicate())){
				neighbours.add(edge.getVariable());
			}
		}
		return neighbours;
	}
	public void removeVariableAndPredicates(VariableNode v, List<PredicateNode> predicates) {
		removeNodeFromNeighboursMap(v);
		removeVariableFromEdges(v);
		variableNodes.remove(v);
		if (predicates != null){
			removePredicates(predicates);
		}
	}
	private void removePredicates(List<PredicateNode> predicateNodesToRemove) {
		for (PredicateNode p: predicateNodesToRemove){
			removeNodeFromNeighboursMap(p);
			removePredicateFromEdges(p);
			predicateNodes.remove(p);
		}
	}
	private void removePredicateFromEdges(PredicateNode v) {
		List<Edge> edgesToRemove = new ArrayList<Edge>();
		for (Edge edge:edges){
			if (v == edge.getPredicate()){
				edgesToRemove.add(edge);
			}
		}
		this.edges.removeAll(edgesToRemove);
	}
	private void removeVariableFromEdges(VariableNode v) {
		List<Edge> edgesToRemove = new ArrayList<Edge>();
		for (Edge edge:edges){
			if (v == edge.getVariable()){
				edgesToRemove.add(edge);
			}
		}
		this.edges.removeAll(edgesToRemove);
	}
	private void removeNodeFromNeighboursMap(Node n) {
		this.neighbourMap.remove(n);
		for (List<Node> neighbours: neighbourMap.values()){
			if (neighbours.contains(n)){
				neighbours.remove(n);
			}
		}
	}
	public boolean hasNeighbours(Node predicate) {
		List<Node> neighbours = getNeighbours(predicate);
		if (neighbours.size() > 0){
			return true;
		} else {
			return false;
		}
	}
	public VariableNode getVariableWithSmallestIndex(PredicateDependencyGraph subgraph) {
		for (VariableNode v: this.variableNodes){
			if (subgraph.getVariableNodes().contains(v)){
				return v;
			}
		}
		return null;
	}
	public PredicateDependencyGraph getSubgraphWithPredicates(
			List<PredicateNode> predicatesWithExactlyOneNeighbour) {
		List<VariableNode> variablesOfSubgraph = new ArrayList<VariableNode>();
		List<PredicateNode> predicatesOfSubgraph = new ArrayList<PredicateNode>();
		List<Edge> edgesOfSubgraph = new ArrayList<Edge>();
		
		predicatesOfSubgraph.addAll(predicatesWithExactlyOneNeighbour);
		
		// extract those edges and variable nodes, that are necessary in the subgraph
		for (Edge e: this.edges){
			if (predicatesWithExactlyOneNeighbour.contains(e.getPredicate())){
				VariableNode v = e.getVariable();
				if (!variablesOfSubgraph.contains(v)){
					variablesOfSubgraph.add(v);
				}
				edgesOfSubgraph.add(e);
			}
		}
		
		PredicateDependencyGraph subGraph = new PredicateDependencyGraph(variablesOfSubgraph, predicatesOfSubgraph, edgesOfSubgraph);
		return subGraph;
	}
	public List<PredicateNode> getNeighbouringPredicates(VariableNode selectedVariableNode) {
		List<Node> neighbouringNodes = getNeighbours(selectedVariableNode);
		List<PredicateNode> predicateNodes = new ArrayList<PredicateNode>();
		for (Node n: neighbouringNodes){
			if (n.isPredicate()){
				predicateNodes.add((PredicateNode)n);
			}
		}
		return predicateNodes;
	}
	@Override
	public String toString() {
		return "PredicateDependencyGraph [variableNodes=" + variableNodes + ", predicateNodes=" + predicateNodes
				+ ", edges=" + edges + ", neighbourMap=" + neighbourMap + "]";
	}
	
}
