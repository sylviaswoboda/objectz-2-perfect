package at.ac.tuwien.oz.ordering;

public class VariableNode implements Node {

	private ISchemaVariable node;
	
	public VariableNode(ISchemaVariable theNode){
		this.node = theNode;
	}
	
	@Override
	public Object getNode() {
		return node;
	}

	public ISchemaVariable getVariable(){
		return node;
	}
	
	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	public boolean isPredicate() {
		return false;
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
		VariableNode other = (VariableNode) obj;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}

//	public boolean hasCollectionType(/*TypeEvaluator typeEvaluator*/) {
////		return typeEvaluator.isCollectionType(this.node.getTypeCtx());
//		return this.node.isCollection();
//	}

	@Override
	public String toString() {
		return "VariableNode [node=" + node + "]";
	}

	public boolean hasPredefinedType() {
		return this.node.hasPredefinedType();
	}

	public boolean hasUserDefinedType() {
		return this.node.hasUserDefinedType();
	}

	
	
}
