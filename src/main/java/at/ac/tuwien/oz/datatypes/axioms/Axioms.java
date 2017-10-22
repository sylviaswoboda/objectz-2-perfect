package at.ac.tuwien.oz.datatypes.axioms;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Elements;
import at.ac.tuwien.oz.translator.templates.StringTemplateSubstitutor;

public class Axioms extends Elements<Axiom>{

	public Axioms() {
		this.elements = new ArrayList<Axiom>();
	}
	public Axioms(List<Axiom> predicates) {
		this();
		this.elements.addAll(predicates);
	}
	
	public Axioms(Axiom...predicates){
		this();
		this.addNew(predicates);
	}
	
	public void addAll(Axioms axioms){
		if (axioms != null){
			elements.addAll(axioms.elements);
		}
	}
	
	public boolean hasPostconditions(){
		for (Axiom predicate: elements){
			if (predicate.isPostcondition()){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPreconditions(){
		for (Axiom predicate: elements){
			if (predicate.isPrecondition()){
				return true;
			}
		}
		return false;
	}
	
	public Axioms getPreconditions(){
		Axioms preconditions = new Axioms();
		for (Axiom predicate: elements){
			if (predicate.isPrecondition()){
				preconditions.add(predicate);
			}
		}
		return preconditions;
	}
	public Axioms getPostconditions(){
		Axioms postconditions = new Axioms();
		for (Axiom predicate: elements){
			if (predicate.isPostcondition()){
				postconditions.add(predicate);
			}
		}
		return postconditions;
	}
	
	public List<ST> getAxiomTemplates(){
		List<ST> templates= new ArrayList<ST>();
		for(Axiom ax: this.elements){
			templates.add(ax.getPredicate());
		}
		return templates;
	}
	
	public void substitute(ST oldIdentifier, ST newIdentifier) {
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
		
		for (Axiom elem: elements){
			substitutor.substituteIdentifier(elem.getPredicate(), oldIdentifier, newIdentifier);
		}
		
	}
}
