package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.translator.TempVarProvider;


public class PredicateDependencyGraphTest {

	@Mock private VariableNode variableNode1;
	@Mock private VariableNode variableNode2;
	@Mock private VariableNode variableNode3;
	
	@Mock private PredicateNode predicateNode1;
	@Mock private PredicateNode predicateNode2;
	@Mock private PredicateNode predicateNode3;
	
	private List<VariableNode> variableNodes;
	private List<PredicateNode> predicateNodes;
	private List<Edge> edges;
	private Edge edgep1v1;
	private Edge edgep1v2;
	private Edge edgep1v3;
	private Edge edgep2v1;
	private Edge edgep2v2;
	private Edge edgep2v3;
	private Edge edgep3v1;
	private Edge edgep3v2;
	private Edge edgep3v3;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		TempVarProvider.resetNameCounter();
		
		edgep1v1 = new Edge(predicateNode1, variableNode1);
		edgep1v2 = new Edge(predicateNode1, variableNode2);
		edgep1v3 = new Edge(predicateNode1, variableNode3);
		edgep2v1 = new Edge(predicateNode2, variableNode1);
		edgep2v2 = new Edge(predicateNode2, variableNode2);
		edgep2v3 = new Edge(predicateNode2, variableNode3);
		edgep3v1 = new Edge(predicateNode3, variableNode1);
		edgep3v2 = new Edge(predicateNode3, variableNode2);
		edgep3v3 = new Edge(predicateNode3, variableNode3);

		variableNodes = new ArrayList<VariableNode>();
		predicateNodes = new ArrayList<PredicateNode>();
		edges = new ArrayList<Edge>();
		
		Mockito.when(variableNode1.isVariable()).thenReturn(true);
		Mockito.when(variableNode2.isVariable()).thenReturn(true);
		Mockito.when(variableNode3.isVariable()).thenReturn(true);
		
		Mockito.when(predicateNode1.isPredicate()).thenReturn(true);
		Mockito.when(predicateNode2.isPredicate()).thenReturn(true);
		Mockito.when(predicateNode3.isPredicate()).thenReturn(true);
	}
	
	@Test
	public void neighboursOneVariableNoPredicate(){
		variableNodes.add(variableNode1);
		
		PredicateDependencyGraph graph = new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
		
		List<Node> neighbours = graph.getNeighbours(variableNode1);
		Assert.assertEquals(0, neighbours.size());
	}

	@Test
	public void neighboursTwoVariablesOneEdge(){
		variableNodes.add(variableNode1);
		variableNodes.add(variableNode2);
		
		edges.add(edgep1v1);

		PredicateDependencyGraph graph = new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
		
		List<Node> neighbours = graph.getNeighbours(variableNode1);
		Assert.assertEquals(1, neighbours.size());
		Assert.assertEquals(predicateNode1, neighbours.get(0));
		
		neighbours = graph.getNeighbours(variableNode2);
		Assert.assertEquals(0, neighbours.size());
		
		neighbours = graph.getNeighbours(predicateNode1);
		Assert.assertEquals(1, neighbours.size());
		Assert.assertEquals(variableNode1, neighbours.get(0));
	}
	
	@Test
	public void neighboursThreeVariablesTwoEdges(){
		variableNodes.add(variableNode1);
		variableNodes.add(variableNode2);
		variableNodes.add(variableNode3);
		edges.add(edgep1v1);
		edges.add(edgep2v2);
		edges.add(edgep2v3);

		PredicateDependencyGraph graph = new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
		
		List<Node> neighbours = graph.getNeighbours(variableNode1);
		Assert.assertEquals(1, neighbours.size());
		Assert.assertEquals(predicateNode1, neighbours.get(0));
		
		neighbours = graph.getNeighbours(variableNode2);
		Assert.assertEquals(1, neighbours.size());
		Assert.assertEquals(predicateNode2, neighbours.get(0));

		neighbours = graph.getNeighbours(variableNode3);
		Assert.assertEquals(1, neighbours.size());
		Assert.assertEquals(predicateNode2, neighbours.get(0));

		neighbours = graph.getNeighbours(predicateNode1);
		Assert.assertEquals(1, neighbours.size());
		Assert.assertEquals(variableNode1, neighbours.get(0));

		neighbours = graph.getNeighbours(predicateNode2);
		Assert.assertEquals(2, neighbours.size());
		Assert.assertEquals(variableNode2, neighbours.get(0));
		Assert.assertEquals(variableNode3, neighbours.get(1));
	}
	
	@Test
	public void removeVariableAndPredicatesOneVariable(){
		variableNodes.add(variableNode1);
		
		PredicateDependencyGraph graph = new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
		Assert.assertFalse(graph.hasNeighbours(variableNode1));
		
		graph.removeVariableAndPredicates(variableNode1, null);
		
		Assert.assertEquals(0, graph.getVariableNodes().size());
		Assert.assertEquals(0, graph.getEdges().size());
		Assert.assertEquals(0, graph.getNeighbours(variableNode1).size());
	}
	
	@Test
	public void removeVariableAndPredicatesTwoVariables(){
		variableNodes.add(variableNode1);
		variableNodes.add(variableNode2);
		predicateNodes.add(predicateNode1);
		edges.add(edgep1v1);
		
		PredicateDependencyGraph graph = new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
		Assert.assertTrue(graph.hasNeighbours(variableNode1));
		Assert.assertFalse(graph.hasNeighbours(variableNode2));
		Assert.assertTrue(graph.hasNeighbours(predicateNode1));
		
		graph.removeVariableAndPredicates(variableNode2, null);
		
		Assert.assertEquals(1, graph.getVariableNodes().size());
		Assert.assertEquals(1, graph.getEdges().size());
		
		Assert.assertEquals(1, graph.getPredicateNodes().size());
		Assert.assertEquals(1, graph.getNeighbours(predicateNode1).size());
		Assert.assertEquals(0, graph.getNeighbours(variableNode2).size());
		
		graph.removeVariableAndPredicates(variableNode1, null);
		
		Assert.assertEquals(0, graph.getVariableNodes().size());
		Assert.assertEquals(0, graph.getEdges().size());
		Assert.assertEquals(0, graph.getNeighbours(predicateNode1).size());
		Assert.assertEquals(0, graph.getNeighbours(variableNode1).size());
		
		Assert.assertEquals(1, graph.getPredicateNodes().size());
		Assert.assertEquals(0, graph.getNeighbours(variableNode2).size());
	}

	@Test
	public void removeVariableAndPredicatesTwoVariablesOnePredicateEach(){
		variableNodes.add(variableNode1);
		variableNodes.add(variableNode2);
		predicateNodes.add(predicateNode1);
		predicateNodes.add(predicateNode2);
		
		edges.add(edgep1v1);
		edges.add(edgep2v2);
		
		PredicateDependencyGraph graph = new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
		
		Assert.assertEquals(1, graph.getNeighbours(variableNode1).size());
		Assert.assertEquals(1, graph.getNeighbours(variableNode2).size());
		Assert.assertEquals(1, graph.getNeighbours(predicateNode1).size());
		Assert.assertEquals(1, graph.getNeighbours(predicateNode2).size());
		
		graph.removeVariableAndPredicates(variableNode1, Arrays.asList(predicateNode1));
		
		Assert.assertEquals(0, graph.getNeighbours(variableNode1).size());
		Assert.assertEquals(0, graph.getNeighbours(predicateNode1).size());
		Assert.assertEquals(1, graph.getVariableNodes().size());
		Assert.assertEquals(variableNode2, graph.getVariableNodes().get(0));
		Assert.assertEquals(1, graph.getPredicateNodes().size());
		Assert.assertEquals(predicateNode2, graph.getPredicateNodes().get(0));
		Assert.assertEquals(1, graph.getEdges().size());
		Assert.assertTrue(graph.getEdges().contains(edgep2v2));
		
		Assert.assertEquals(1, graph.getNeighbours(variableNode2).size());
		Assert.assertEquals(1, graph.getNeighbours(predicateNode2).size());

	}

	@Test
	public void removeVariableAndPredicatesOneVariableWithTwoPredicates(){
		variableNodes.add(variableNode1);
		variableNodes.add(variableNode2);
		predicateNodes.add(predicateNode1);
		predicateNodes.add(predicateNode2);
		predicateNodes.add(predicateNode3);
		
		edges.add(edgep1v1);
		edges.add(edgep2v1);
		edges.add(edgep3v2);
		
		PredicateDependencyGraph graph = new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
		
		Assert.assertTrue(graph.hasNeighbours(variableNode1));
		Assert.assertTrue(graph.hasNeighbours(variableNode2));
		Assert.assertTrue(graph.hasNeighbours(predicateNode1));
		Assert.assertTrue(graph.hasNeighbours(predicateNode2));
		Assert.assertTrue(graph.hasNeighbours(predicateNode3));
		
		graph.removeVariableAndPredicates(variableNode1, Arrays.asList(predicateNode1, predicateNode2));
		
		Assert.assertEquals(1, graph.getVariableNodes().size());
		Assert.assertEquals(1, graph.getPredicateNodes().size());
		Assert.assertEquals(1, graph.getEdges().size());
		Assert.assertEquals(edgep3v2, graph.getEdges().get(0));
	}
	
	@Test
	public void getSubGraphWithOnePredicate(){
		PredicateDependencyGraph graph = getFullGraph();

		PredicateDependencyGraph subGraph = graph.getSubgraphWithPredicates(Arrays.asList(predicateNode1));
		
		Assert.assertEquals(1, subGraph.getPredicateNodes().size());
		Assert.assertEquals(3, subGraph.getVariableNodes().size());
		Assert.assertEquals(3, subGraph.getEdges().size());
		
		Assert.assertTrue(subGraph.getPredicateNodes().contains(predicateNode1));
		
		Assert.assertTrue(subGraph.getVariableNodes().contains(variableNode1));
		Assert.assertTrue(subGraph.getVariableNodes().contains(variableNode2));
		Assert.assertTrue(subGraph.getVariableNodes().contains(variableNode3));
		
		Assert.assertTrue(subGraph.getEdges().contains(edgep1v1));
		Assert.assertTrue(subGraph.getEdges().contains(edgep1v2));
		Assert.assertTrue(subGraph.getEdges().contains(edgep1v3));
	}
	
	@Test
	public void getSubGraphWithTwoPredicates(){
		PredicateDependencyGraph graph = getFullGraph();
		
		PredicateDependencyGraph subGraph = graph.getSubgraphWithPredicates(Arrays.asList(predicateNode1, predicateNode3));

		Assert.assertEquals(2, subGraph.getPredicateNodes().size());
		Assert.assertEquals(3, subGraph.getVariableNodes().size());
		Assert.assertEquals(6, subGraph.getEdges().size());
		
		Assert.assertTrue(subGraph.getPredicateNodes().contains(predicateNode1));
		Assert.assertTrue(subGraph.getPredicateNodes().contains(predicateNode3));
		
		Assert.assertTrue(subGraph.getVariableNodes().contains(variableNode1));
		Assert.assertTrue(subGraph.getVariableNodes().contains(variableNode2));
		Assert.assertTrue(subGraph.getVariableNodes().contains(variableNode3));
		
		Assert.assertTrue(subGraph.getEdges().contains(edgep1v1));
		Assert.assertTrue(subGraph.getEdges().contains(edgep1v2));
		Assert.assertTrue(subGraph.getEdges().contains(edgep1v3));
		Assert.assertTrue(subGraph.getEdges().contains(edgep3v1));
		Assert.assertTrue(subGraph.getEdges().contains(edgep3v2));
		Assert.assertTrue(subGraph.getEdges().contains(edgep3v3));
	}

	private PredicateDependencyGraph getFullGraph() {
		variableNodes.add(variableNode1);
		variableNodes.add(variableNode2);
		variableNodes.add(variableNode3);
		predicateNodes.add(predicateNode1);
		predicateNodes.add(predicateNode2);
		predicateNodes.add(predicateNode3);
		edges.add(edgep1v1);
		edges.add(edgep1v2);
		edges.add(edgep1v3);
		edges.add(edgep2v1);
		edges.add(edgep2v2);
		edges.add(edgep2v3);
		edges.add(edgep3v1);
		edges.add(edgep3v2);
		edges.add(edgep3v3);
		
		PredicateDependencyGraph graph =  new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
		return graph;
	}
	
	@Test
	public void getNeighbouringPredicates(){
		variableNodes.add(variableNode1);
		variableNodes.add(variableNode2);
		variableNodes.add(variableNode3);
		
		predicateNodes.add(predicateNode1);
		predicateNodes.add(predicateNode2);
		predicateNodes.add(predicateNode3);
		
		edges.add(edgep1v1);
		
		edges.add(edgep1v2);
		edges.add(edgep3v2);

		edges.add(edgep1v3);
		edges.add(edgep2v3);
		edges.add(edgep3v3);

		PredicateDependencyGraph graph =  new PredicateDependencyGraph(variableNodes, predicateNodes, edges);

		List<PredicateNode>predicateNeighbours = graph.getNeighbouringPredicates(variableNode1);
		Assert.assertEquals(1, predicateNeighbours.size());
		Assert.assertTrue(predicateNeighbours.contains(predicateNode1));

		predicateNeighbours = graph.getNeighbouringPredicates(variableNode2);
		Assert.assertEquals(2, predicateNeighbours.size());
		Assert.assertTrue(predicateNeighbours.contains(predicateNode1));
		Assert.assertTrue(predicateNeighbours.contains(predicateNode3));

		predicateNeighbours = graph.getNeighbouringPredicates(variableNode3);
		Assert.assertEquals(3, predicateNeighbours.size());
		Assert.assertTrue(predicateNeighbours.contains(predicateNode1));
		Assert.assertTrue(predicateNeighbours.contains(predicateNode2));
		Assert.assertTrue(predicateNeighbours.contains(predicateNode3));

	}
}
