package at.ac.tuwien.oz.datatypes;

import java.util.Collection;
import java.util.Map;

import at.ac.tuwien.oz.definitions.IDefinition;

public class SearchableElements<T extends IDefinition> extends Elements<T>{
	
	protected Map<Ident, T> elementMap;
	
	@Override
	public boolean add(T elem) {
		elementMap.put(elem.getId(), elem);
		return super.add(elem);
	}

	@Override
	public boolean addAll(Collection<? extends T> elems) {
		for(T elem: elems){
			elementMap.put(elem.getId(), elem);
		}
		return this.elements.addAll(elems);
	}

	public T resolve(Ident id) {
		return elementMap.get(id);
	}
}
