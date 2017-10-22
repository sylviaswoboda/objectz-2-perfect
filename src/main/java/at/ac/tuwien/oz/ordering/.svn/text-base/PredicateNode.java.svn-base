package at.ac.tuwien.oz.ordering;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

public class PredicateNode implements Node {

	private ISchemaPredicate node;
	
	public PredicateNode(ISchemaPredicate node){
		this.node = node;
	}
	
	@Override
	public ISchemaPredicate getNode() {
		return node;
	}

	public ST getTemplate(ParseTreeProperty<ST>templateTree){
		return node.getTemplate(templateTree);
	}

	@Override
	public boolean isVariable() {
		return false;
	}

	@Override
	public boolean isPredicate() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PredicateNode other = (PredicateNode) obj;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}
}
