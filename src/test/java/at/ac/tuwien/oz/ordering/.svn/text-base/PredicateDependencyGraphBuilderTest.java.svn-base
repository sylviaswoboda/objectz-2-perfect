package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;

public class PredicateDependencyGraphBuilderTest{
	private PredicateDependencyGraphBuilder graphBuilder;
	
	private Ident a;
	private Ident b;
	private Ident c;
	
	private ExpressionType anyType = ExpressionType.getNat();
	private List<ISchemaPredicate> predicateList;
	private List<ISchemaVariable> variables;
	private PredicateDependencyGraph graph;

	private ISchemaVariable varA;

	private ISchemaVariable varB;

	private ISchemaVariable varC;
	
	@Before
	public void setup(){
		a = new Ident("a");
		b = new Ident("b");
		c = new Ident("c");
	
		varA = Variable.createUndecoratedVariable("a", anyType);
		varB = Variable.createUndecoratedVariable("b", anyType);
		varC = Variable.createUndecoratedVariable("c", anyType);

		predicateList = new ArrayList<ISchemaPredicate>();
		variables = new ArrayList<ISchemaVariable>();
	}
	private void buildGraph() {
		graphBuilder = new PredicateDependencyGraphBuilder(variables, predicateList);
		graph = graphBuilder.buildGraph();
	}
	
	@Test
	public void oneVariableNoPredicate(){
		variables.add(varA);
		
		buildGraph();
		
		List<VariableNode> nodes = graph.getVariableNodes();
		Assert.assertEquals(1, nodes.size());
		Assert.assertEquals(varA, nodes.get(0).getNode());
		Assert.assertEquals(0, graph.getPredicateNodes().size());
		Assert.assertEquals(0, graph.getEdges().size());
	}

	@Test
	public void twoVariablesOnePredicate(){
		AxiomReference pred1 = new AxiomReference(new Idents(a), null);
		predicateList.add(pred1);
		
		variables.add(varA);
		variables.add(varB);
		
		buildGraph();
		
		List<VariableNode> nodes = graph.getVariableNodes();
		Assert.assertEquals(2, nodes.size());
		Assert.assertEquals(varA, nodes.get(0).getNode());
		Assert.assertEquals(varB, nodes.get(1).getNode());
		
		List<PredicateNode> predicates = graph.getPredicateNodes();
		Assert.assertEquals(1, predicates.size());
		Assert.assertEquals(pred1, predicates.get(0).getNode());
		
		List<Edge> edges = graph.getEdges();
		Edge edge1 = new Edge(new PredicateNode(pred1), new VariableNode(varA));
		Assert.assertEquals(1, edges.size());
		Assert.assertTrue(edges.contains(edge1));
		
	}
	
	@Test
	public void threeVariablesTwoPredicates(){
		AxiomReference pred1 = new AxiomReference(new Idents(a), null);
		AxiomReference pred2 = new AxiomReference(new Idents(b, c), null);
		predicateList.add(pred1);
		predicateList.add(pred2);
		
		variables.add(varA);
		variables.add(varB);
		variables.add(varC);
		
		buildGraph();
		
		List<VariableNode> nodes = graph.getVariableNodes();
		Assert.assertEquals(3, nodes.size());
		Assert.assertEquals(varA, nodes.get(0).getNode());
		Assert.assertEquals(varB, nodes.get(1).getNode());
		Assert.assertEquals(varC, nodes.get(2).getNode());
		
		List<PredicateNode> predicateNodes = graph.getPredicateNodes();
		Assert.assertEquals(2, predicateNodes.size());
		Assert.assertEquals(pred1, predicateNodes.get(0).getNode());
		Assert.assertEquals(pred2, predicateNodes.get(1).getNode());
		
		List<Edge> edges = graph.getEdges();
		Edge edgeForComparison1 = new Edge(new PredicateNode(pred1), new VariableNode(varA));
		Edge edgeForComparison2 = new Edge(new PredicateNode(pred2), new VariableNode(varB));
		Edge edgeForComparison3 = new Edge(new PredicateNode(pred2), new VariableNode(varC));
		Assert.assertEquals(3, edges.size());
		Assert.assertTrue(edges.contains(edgeForComparison1));
		Assert.assertTrue(edges.contains(edgeForComparison2));
		Assert.assertTrue(edges.contains(edgeForComparison3));
		
	}

	@Test
	public void otherVariablesInPredicates(){
		AxiomReference pred1 = new AxiomReference(new Idents(a, c), null);
		AxiomReference pred2 = new AxiomReference(new Idents(a, b, c), null);
		predicateList.add(pred1);
		predicateList.add(pred2);
		
		variables.add(varA);
		variables.add(varB);
		variables.add(varC);
		
		buildGraph();
		
		List<VariableNode> nodes = graph.getVariableNodes();
		Assert.assertEquals(3, nodes.size());
		Assert.assertEquals(varA, nodes.get(0).getNode());
		Assert.assertEquals(varB, nodes.get(1).getNode());
		Assert.assertEquals(varC, nodes.get(2).getNode());
		
		List<PredicateNode> predicateNodes = graph.getPredicateNodes();
		Assert.assertEquals(2, predicateNodes.size());
		Assert.assertEquals(pred1, predicateNodes.get(0).getNode());
		Assert.assertEquals(pred2, predicateNodes.get(1).getNode());

		List<Edge> edges = graph.getEdges();
		Edge edgeForComparison1A = new Edge(new PredicateNode(pred1), new VariableNode(varA));
		Edge edgeForComparison1C = new Edge(new PredicateNode(pred1), new VariableNode(varC));
		Edge edgeForComparison2A = new Edge(new PredicateNode(pred2), new VariableNode(varA));
		Edge edgeForComparison2B = new Edge(new PredicateNode(pred2), new VariableNode(varB));
		Edge edgeForComparison2C = new Edge(new PredicateNode(pred2), new VariableNode(varC));
		Assert.assertEquals(5, edges.size());
		Assert.assertTrue(edges.contains(edgeForComparison1A));
		Assert.assertTrue(edges.contains(edgeForComparison1C));
		Assert.assertTrue(edges.contains(edgeForComparison2A));
		Assert.assertTrue(edges.contains(edgeForComparison2B));
		Assert.assertTrue(edges.contains(edgeForComparison2C));
	}
	
}
