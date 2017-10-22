package at.ac.tuwien.oz.scopes;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public class OperationScope extends BaseScope{
	
	private ObjectZClass enclosingClass;
	private String operationName;
	
	public OperationScope(ObjectZClass enclosingClass, String operationName) {
		this.enclosingClass = enclosingClass;
		this.operationName = operationName;
	}

	public String getScopeName() {
		return this.operationName;
	}

	public boolean isStateVariable(Ident usedVariable) {
		return this.enclosingClass.isStateVariable(usedVariable);
	}
	
}
