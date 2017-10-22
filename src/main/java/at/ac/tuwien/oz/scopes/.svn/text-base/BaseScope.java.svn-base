package at.ac.tuwien.oz.scopes;
import java.util.LinkedHashMap;
import java.util.Map;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.definitions.IDefinition;


public abstract class BaseScope implements Scope, Comparable<Scope> {
    Scope enclosingScope; // null if global (outermost) scope
    Map<Ident, IDefinition> symbols = new LinkedHashMap<Ident, IDefinition>();

    public IDefinition resolve(String name) {
        IDefinition s = symbols.get(name);
        if ( s!=null ) return s;
        // if not here, check any enclosing scope
        if ( enclosingScope != null ) return enclosingScope.resolve(name);
        return null; // not found
    }

    public void define(IDefinition sym) {
        symbols.put(sym.getId(), sym);
//        sym.setScope(this); // track the scope in each symbol
    }

    public Scope getEnclosingScope() { 
    	return enclosingScope;
    }

    public String toString() { 
    	return getScopeName()+":"+symbols.values().toString(); 
    }

	@Override
	public int compareTo(Scope otherScope) {
		return this.getScopeName().compareTo(otherScope.getScopeName());
	}

}
