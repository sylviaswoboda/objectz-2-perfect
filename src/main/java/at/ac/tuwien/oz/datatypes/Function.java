package at.ac.tuwien.oz.datatypes;

import java.util.List;

import org.stringtemplate.v4.ST;

public class Function {

	private Ident id;
	private ST type;
	private List<ST> predicates;
	
	public Function(Ident id, ST type, List<ST> predicates){
		this.id = id;
		this.type = type;
		this.predicates = predicates;
	}
	
	public Ident getName(){
		return id;
	}
	
	public ST getType(){
		return type;
	}
	
	public List<ST> gePredicates(){
		return predicates;
	}
}
