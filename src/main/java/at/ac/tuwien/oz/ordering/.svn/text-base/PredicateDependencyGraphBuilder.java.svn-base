package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;

public class PredicateDependencyGraphBuilder {
	
//	private List<SchemaVariable> variables;
//	private List<ST> predicateList;
//	private PerfectTemplateChecker templateChecker;
	
	private List<ISchemaVariable> newVariables;
	private List<ISchemaPredicate> newPredicates;

//	public PredicateDependencyGraphBuilder (List<SchemaVariable> variables,
//			List<ST> predicateList) {
//		this.variables = variables;
//		this.predicateList = predicateList;
//		this.templateChecker = new PerfectTemplateChecker();
//	}
//	
	public PredicateDependencyGraphBuilder(List<ISchemaVariable> variables, List<ISchemaPredicate> predicates){
		this.newVariables = variables;
		this.newPredicates = predicates;
	}


	public PredicateDependencyGraph buildGraph() {
		List<VariableNode> variableNodes;
		List<PredicateNode> predicateNodes = new ArrayList<PredicateNode>();
		List<Edge> edges = new ArrayList<Edge>();
		
		variableNodes = wrapVariablesInNode();
		
//		for (ST predicate: predicateList){
		for (ISchemaPredicate predicate: newPredicates){
//			List<ST> identifiersInPredicate = findIdentifiersInTemplate(predicate);
			Idents usedIdentifiers = predicate.getUsedIdentifiers();
			PredicateNode predicateNode = new PredicateNode(predicate);
			predicateNodes.add(predicateNode);
			for(VariableNode variableNode: variableNodes){
//				ST variableIdent = variableNode.getVariable().getId();
				Ident variableIdent = variableNode.getVariable().getId();
//				if (isIdentifierInList(variableIdent, identifiersInPredicate)){
//					Edge e = new Edge(predicateNode, variableNode);
//					edges.add(e);
//				}
				if (usedIdentifiers.contains(variableIdent)){
					Edge e = new Edge(predicateNode, variableNode);
					edges.add(e);
				}
			}
		}
		return new PredicateDependencyGraph(variableNodes, predicateNodes, edges);
	}


//	private boolean isIdentifierInList(ST variableIdent, List<ST> identifiers) {
//		for (ST ident: identifiers){
//			if (templateChecker.hasSameNameAndDecoration(ident, variableIdent)){
//				return true;
//			} 
//		}
//		return false;
//	}


	private List<VariableNode> wrapVariablesInNode() {
		List<VariableNode> variableNodes = new ArrayList<VariableNode>();
		for (ISchemaVariable v: newVariables){
			variableNodes.add(new VariableNode(v));
		}
		return variableNodes;
	}


//	private List<ST> findIdentifiersInTemplate(ST template) {
//		List<ST> identifiers = new ArrayList<ST>();
//		
//		if (templateChecker.isId(template)){
//			identifiers.add(template);
//		} else {
//			for (Object o: template.getAttributes().values()){
//				if (o instanceof ST){
//					identifiers.addAll(findIdentifiersInTemplate((ST)o));
//				}
//				if (o instanceof Collection<?>){
//					for (Object element: (Collection<?>)o){
//						if (element instanceof ST){
//							identifiers.addAll(findIdentifiersInTemplate((ST)element));
//						}
//					}
//
//				}
//			}
//		}
//		
//		return identifiers;
//	}


}
