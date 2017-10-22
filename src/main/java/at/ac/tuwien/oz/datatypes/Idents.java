package at.ac.tuwien.oz.datatypes;

import java.util.ArrayList;

public class Idents extends Elements<Ident>{

	private static final Idents EMPTY_IDENTS = new Idents();
	
	public static Idents empty(){
		return EMPTY_IDENTS;
	}
	
	public Idents() {
		this.elements = new ArrayList<Ident>();
	}
	public Idents(Ident ident){
		this();
		this.elements.add(ident);
	}
	public Idents(Ident... idents){
		this();
		for (Ident ident: idents){
			this.elements.add(ident);
		}
	}

	public void addAll(Idents idents) {
		if (idents != null){
			this.elements.addAll(idents.elements);
		}
	}
	public void addNew(Idents idents) {
		if (idents != null){
			for (Ident i: idents){
				this.add(i);
			}
		}
	}

	public boolean hasIntersection(Idents usedStateVariables) {
		Idents copy = new Idents();
		copy.addAll(this.asList());
		
		copy.retainAll(usedStateVariables.asList());
		
		return !copy.isEmpty();
	}
	@Override
	public String toString() {
		return "Idents [elements=" + elements + "]";
	}
	
	
}
