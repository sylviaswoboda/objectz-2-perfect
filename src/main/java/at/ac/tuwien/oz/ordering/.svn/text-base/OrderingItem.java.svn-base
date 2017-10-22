package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

public class OrderingItem {

	private VariableNode variableNode;
	private List<PredicateNode> predicateNodes;
	
	public OrderingItem(VariableNode variableNode){
		this.variableNode = variableNode;
		this.predicateNodes = new ArrayList<PredicateNode>();
	}
	
	public void addPredicate(PredicateNode predicateNode){
		this.predicateNodes.add(predicateNode);
	}

	public VariableNode getVariableNode() {
		return variableNode;
	}

	public List<PredicateNode> getPredicateNodes() {
		return predicateNodes;
	}

	public List<ST> getPredicateTemplates(ParseTreeProperty<ST> templateTree) {
		List<ST> predicateTemplates = new ArrayList<ST>();
		for (PredicateNode p: predicateNodes){
			predicateTemplates.add(p.getTemplate(templateTree));
		}
		return predicateTemplates;
	}

}
