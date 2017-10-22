package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.translator.TypeEvaluator;


public class OrderingCalculatorTest {

	private List<VariableNode> variables;
	private List<PredicateNode> predicates;
	private List<Edge> edges;
	
	
	@Mock private VariableNode varNodeMock1;
	@Mock private VariableNode varNodeMock2;
	@Mock private VariableNode varNodeMock3;
	@Mock private VariableNode varNodeMock4;
	@Mock private VariableNode varNodeMock5;
	@Mock private VariableNode varNodeMock6;

	@Mock private PredicateNode predNodeMock1;
	@Mock private PredicateNode predNodeMock2;
	@Mock private PredicateNode predNodeMock3;
	@Mock private PredicateNode predNodeMock4;
	@Mock private PredicateNode predNodeMock5;
	@Mock private PredicateNode predNodeMock6;
	
	@Mock private TypeEvaluator typeEvaluatorMock;

	@Before
	public void initialize(){
		MockitoAnnotations.initMocks(this);
		
		this.variables = new ArrayList<VariableNode>();
		this.predicates = new ArrayList<PredicateNode>();
		this.edges = new ArrayList<Edge>();
		
		Mockito.when(varNodeMock1.isVariable()).thenReturn(true);
		Mockito.when(varNodeMock2.isVariable()).thenReturn(true);
		Mockito.when(varNodeMock3.isVariable()).thenReturn(true);
		Mockito.when(varNodeMock4.isVariable()).thenReturn(true);
		Mockito.when(varNodeMock5.isVariable()).thenReturn(true);
		Mockito.when(varNodeMock6.isVariable()).thenReturn(true);

		Mockito.when(predNodeMock1.isPredicate()).thenReturn(true);
		Mockito.when(predNodeMock2.isPredicate()).thenReturn(true);
		Mockito.when(predNodeMock3.isPredicate()).thenReturn(true);
		Mockito.when(predNodeMock4.isPredicate()).thenReturn(true);
		Mockito.when(predNodeMock5.isPredicate()).thenReturn(true);
		Mockito.when(predNodeMock6.isPredicate()).thenReturn(true);

	}

	@Test
	public void orderCollectionAndNonCollectionVariables(){
		Mockito.when(varNodeMock2.hasPredefinedType()).thenReturn(true);
		
		this.variables.add(varNodeMock1);	// this is a collection type var
		this.variables.add(varNodeMock2);	// this is a non-collection type var
		this.predicates.add(predNodeMock1); // predicate is a constraint on varNodeMock1
		Edge edge1 = new Edge(predNodeMock1, varNodeMock1);
		this.edges.add(edge1);
		
		PredicateDependencyGraph graph = new PredicateDependencyGraph(variables, predicates, edges);
		OrderingCalculator calculator = new OrderingCalculator(graph);
		List<OrderingItem> orderedItems = calculator.calculateOrdering();
		
		Assert.assertEquals(2, orderedItems.size());
		Assert.assertEquals(varNodeMock2, orderedItems.get(0).getVariableNode());
		Assert.assertEquals(varNodeMock1, orderedItems.get(1).getVariableNode());
		List<PredicateNode> predicatesOfVar1 = orderedItems.get(1).getPredicateNodes();
		Assert.assertEquals(1, predicatesOfVar1.size());
		Assert.assertEquals(predNodeMock1, predicatesOfVar1.get(0));
	}

	@Test
	public void order2VariablesWithNeighbourAndWithout(){
		this.variables.add(varNodeMock1);
		this.variables.add(varNodeMock2);
		this.predicates.add(predNodeMock1);
		
		Edge edge1 = new Edge(predNodeMock1, varNodeMock1);
		this.edges.add(edge1);
		
		PredicateDependencyGraph graph = new PredicateDependencyGraph(variables, predicates, edges);
		OrderingCalculator calculator = new OrderingCalculator(graph);
		List<OrderingItem> orderedItems = calculator.calculateOrdering();
		
		Assert.assertEquals(2, orderedItems.size());
		OrderingItem orderingItem1 = orderedItems.get(0);
		OrderingItem orderingItem2 = orderedItems.get(1);
		
		// verify first element
		Assert.assertEquals(varNodeMock2, orderingItem1.getVariableNode());
		Assert.assertTrue(orderingItem1.getPredicateNodes().isEmpty());
		
		// verify first element
		Assert.assertEquals(varNodeMock1, orderingItem2.getVariableNode());
		List<PredicateNode> predicatesOfVar = orderingItem2.getPredicateNodes();
		Assert.assertEquals(1, predicatesOfVar.size());
		Assert.assertEquals(predNodeMock1, predicatesOfVar.get(0));
	}

	@Test
	public void order2CollectionVariablesWith3Predicates(){
		this.variables.add(varNodeMock1);	
		this.variables.add(varNodeMock2);	

		this.predicates.add(predNodeMock1); 
		this.predicates.add(predNodeMock2); 
		this.predicates.add(predNodeMock3); 
		
		Edge edge1 = new Edge(predNodeMock1, varNodeMock1);
		Edge edge2 = new Edge(predNodeMock2, varNodeMock1);
		Edge edge3 = new Edge(predNodeMock3, varNodeMock2);
		this.edges.add(edge1);
		this.edges.add(edge2);
		this.edges.add(edge3);
		
		PredicateDependencyGraph graph = new PredicateDependencyGraph(variables, predicates, edges);
		OrderingCalculator calculator = new OrderingCalculator(graph);
		List<OrderingItem> orderedItems = calculator.calculateOrdering();
		
		Assert.assertEquals(2, orderedItems.size());
		
		OrderingItem orderingItem1 = orderedItems.get(0);
		OrderingItem orderingItem2 = orderedItems.get(1);
		
		// verify first element
		Assert.assertEquals(varNodeMock1, orderingItem1.getVariableNode());
		List<PredicateNode> predicatesOfVar = orderingItem1.getPredicateNodes();
		Assert.assertEquals(2, predicatesOfVar.size());
		Assert.assertEquals(predNodeMock1, predicatesOfVar.get(0));
		Assert.assertEquals(predNodeMock2, predicatesOfVar.get(1));
		
		// verify second element
		Assert.assertEquals(varNodeMock2, orderingItem2.getVariableNode());
		predicatesOfVar = orderingItem2.getPredicateNodes();
		Assert.assertEquals(1, predicatesOfVar.size());
		Assert.assertEquals(predNodeMock3, predicatesOfVar.get(0));
		
	}

	
	@Test
	public void exampleFromThesis(){
		Mockito.when(varNodeMock5.hasUserDefinedType()).thenReturn(true);
		
		this.variables.add(varNodeMock1);
		this.variables.add(varNodeMock2);
		this.variables.add(varNodeMock3);
		this.variables.add(varNodeMock4);
		this.variables.add(varNodeMock5);
		this.variables.add(varNodeMock6);
		
		this.predicates.add(predNodeMock1);
		this.predicates.add(predNodeMock2);
		this.predicates.add(predNodeMock3);
		this.predicates.add(predNodeMock4);
		this.predicates.add(predNodeMock5);
		this.predicates.add(predNodeMock6);
		
		Edge edge1 = new Edge(predNodeMock1, varNodeMock1);
		Edge edge2 = new Edge(predNodeMock1, varNodeMock2);
		Edge edge3 = new Edge(predNodeMock1, varNodeMock3);
		Edge edge4 = new Edge(predNodeMock2, varNodeMock1);
		Edge edge5 = new Edge(predNodeMock2, varNodeMock2);
		Edge edge6 = new Edge(predNodeMock2, varNodeMock3);
		Edge edge7 = new Edge(predNodeMock3, varNodeMock1);
		Edge edge8 = new Edge(predNodeMock3, varNodeMock2);
		Edge edge9 = new Edge(predNodeMock3, varNodeMock3);
		Edge edge10 = new Edge(predNodeMock4, varNodeMock2);
		Edge edge11 = new Edge(predNodeMock4, varNodeMock4);
		Edge edge12 = new Edge(predNodeMock5, varNodeMock3);
		Edge edge13 = new Edge(predNodeMock6, varNodeMock6);
		
		this.edges.add(edge1);
		this.edges.add(edge2);
		this.edges.add(edge3);
		this.edges.add(edge4);
		this.edges.add(edge5);
		this.edges.add(edge6);
		this.edges.add(edge7);
		this.edges.add(edge8);
		this.edges.add(edge9);
		this.edges.add(edge10);
		this.edges.add(edge11);
		this.edges.add(edge12);
		this.edges.add(edge13);

		PredicateDependencyGraph graph = new PredicateDependencyGraph(variables, predicates, edges);
		OrderingCalculator calculator = new OrderingCalculator(graph);
		List<OrderingItem> orderedItems = calculator.calculateOrdering();
		
		Assert.assertEquals(6, orderedItems.size());
		Assert.assertEquals(varNodeMock5, orderedItems.get(0).getVariableNode());
		Assert.assertEquals(varNodeMock3, orderedItems.get(1).getVariableNode());
		Assert.assertEquals(varNodeMock6, orderedItems.get(2).getVariableNode());
		Assert.assertEquals(varNodeMock4, orderedItems.get(3).getVariableNode());
		Assert.assertEquals(varNodeMock2, orderedItems.get(4).getVariableNode());
		Assert.assertEquals(varNodeMock1, orderedItems.get(5).getVariableNode());
		
		Assert.assertEquals(0, orderedItems.get(0).getPredicateNodes().size());
		Assert.assertEquals(1, orderedItems.get(1).getPredicateNodes().size());
		Assert.assertEquals(1, orderedItems.get(2).getPredicateNodes().size());
		Assert.assertEquals(0, orderedItems.get(3).getPredicateNodes().size());
		Assert.assertEquals(1, orderedItems.get(4).getPredicateNodes().size());
		Assert.assertEquals(3, orderedItems.get(5).getPredicateNodes().size());

		List<PredicateNode> predicatesInResult = orderedItems.get(1).getPredicateNodes();
		Assert.assertEquals(predNodeMock5, predicatesInResult.get(0));

		predicatesInResult = orderedItems.get(2).getPredicateNodes();
		Assert.assertEquals(predNodeMock6, predicatesInResult.get(0));

		predicatesInResult = orderedItems.get(4).getPredicateNodes();
		Assert.assertEquals(predNodeMock4, predicatesInResult.get(0));

		predicatesInResult = orderedItems.get(5).getPredicateNodes();
		Assert.assertEquals(predNodeMock1, predicatesInResult.get(0));
		Assert.assertEquals(predNodeMock2, predicatesInResult.get(1));
		Assert.assertEquals(predNodeMock3, predicatesInResult.get(2));
	}
	
}
