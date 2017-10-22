package at.ac.tuwien.oz.definitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;

public class DefinitionTable<T extends IDefinition> {

	private Map<Ident, T> map;
	
	public DefinitionTable(){
		this.map = new HashMap<Ident, T>();
	}
	
	public void addAll(List<T> definitions){
		for (T definition: definitions){
			this.add(definition);
		}
	}
	
	public void add(T definition) {
		this.add(definition.getId(), definition);
	}
	
	public void add(Ident name, T definition){
		this.map.put(name, definition);
	}
	
	public T get(Ident name){
		return this.map.get(name);
	}
	public Idents identifiers(){
		Idents i = new Idents();
		i.addAll(this.map.keySet());
		return i;
	}

}
