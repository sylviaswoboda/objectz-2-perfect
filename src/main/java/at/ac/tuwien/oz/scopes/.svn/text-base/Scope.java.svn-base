package at.ac.tuwien.oz.scopes;

import at.ac.tuwien.oz.definitions.IDefinition;


public interface Scope {

    public String getScopeName();

    /** Where to look next for symbols */
    public Scope getEnclosingScope();

    /** Define a symbol in the current scope */
    public void define(IDefinition sym);

    /** Look up name in this scope or in enclosing scope if not here */
    public IDefinition resolve(String name);

	
}
