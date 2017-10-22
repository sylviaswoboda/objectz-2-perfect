package at.ac.tuwien.oz.scopes;

import java.util.Map;
import java.util.TreeMap;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;

public class LocalScope {

	private LocalScope enclosingLocalScope;
	private Map<Ident, Variable> declarationMap;
	
	public LocalScope(){
		this.enclosingLocalScope = null;
		this.declarationMap = new TreeMap<Ident, Variable>();
	}

	public LocalScope(LocalScope parent){
		this();
		this.enclosingLocalScope = parent;
	}

	public void define(Variable v){
		this.declarationMap.put(v.getId(), v);
	}
	
	public Variable resolve(Ident id){
		return this.declarationMap.get(id);
	}
	public LocalScope getEnclosingScope(){
		return this.enclosingLocalScope;
	}

	public Declarations findAll() {
		return new Declarations(declarationMap.values());
	}
}
