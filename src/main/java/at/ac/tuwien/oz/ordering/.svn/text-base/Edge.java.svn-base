package at.ac.tuwien.oz.ordering;

public class Edge{
	private PredicateNode predicate;
	private VariableNode variable;
	
	public Edge(PredicateNode p, VariableNode v){
		this.predicate = p;
		this.variable = v;
	}
	public PredicateNode getPredicate(){
		return this.predicate;
	}
	public VariableNode getVariable(){
		return this.variable;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result
				+ ((variable == null) ? 0 : variable.hashCode());
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
		Edge other = (Edge) obj;
		if (predicate == null) {
			if (other.predicate != null)
				return false;
		} else if (!predicate.equals(other.predicate))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}
}