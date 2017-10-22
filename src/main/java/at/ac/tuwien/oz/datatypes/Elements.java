package at.ac.tuwien.oz.datatypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Elements<T> implements Iterable<T>{

	protected List<T> elements;
	
	public Elements(){
		this.elements = new ArrayList<T>();
	}

	public boolean add(T elem) {
		if (elem != null){
			if (!elements.contains(elem)){
				return elements.add(elem);
			}
		}
		
		return false;
	}

	public boolean addAll(Collection<? extends T> elems) {
		return this.elements.addAll(elems);
	}
	
	public boolean addAll(Elements<? extends T> elems) {
		return this.elements.addAll(elems.elements);
	}
	
	public void addNew(T... elems) {
		if (elems != null){
			for (T elem: elems){
				this.add(elem);
			}
		}
	}

	
	public T get(int index) {
		return elements.get(index);
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public int size() {
		return elements.size();
	}
	
	public List<T> asList(){
		return elements;
	}
	
	protected boolean retainAll(Collection<? extends T> elems){
		return this.elements.retainAll(elems);
	}
	
	public boolean removeAll(Collection<? extends T> elems){
		return this.elements.removeAll(elems);
	}
	public boolean removeAll(Elements<? extends T> elems){
		return this.elements.removeAll(elems.asList());
	}

	public boolean contains(T elem) {
		return this.elements.contains(elem);
	}

	public void remove(T elem) {
		this.elements.remove(elem);
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
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
		@SuppressWarnings("unchecked")
		Elements<T> other = (Elements<T>) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return elements.iterator();
	}
	
	public Stream<T> stream() {
		return elements.stream();
	}
	
	

}
