package at.ac.tuwien.oz.datatypes.axioms;

import java.util.ArrayList;

import at.ac.tuwien.oz.datatypes.Elements;
import at.ac.tuwien.oz.datatypes.Idents;

public class AxiomReferences extends Elements<AxiomReference> {
	
	private static final AxiomReferences EMPTY_REFERENCES = new AxiomReferences();

	public AxiomReferences(){
		this.elements = new ArrayList<AxiomReference>();
	}

	public AxiomReferences(AxiomReference... axiomReferences){
		this();
		for (AxiomReference axiomReference: axiomReferences){
			this.elements.add(axiomReference);
		}

	}

	public void addAll(AxiomReferences axiomReferences) {
		this.elements.addAll(axiomReferences.elements);
	}

	public boolean hasPostconditions(){
		for (AxiomReference predicate: elements){
			if (predicate.isPostcondition()){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPreconditions(){
		for (AxiomReference predicate: elements){
			if (predicate.isPrecondition()){
				return true;
			}
		}
		return false;
	}

	public AxiomReferences getPreconditionReferences() {
		AxiomReferences preconditions = new AxiomReferences();
		
		for (AxiomReference predicate: elements){
			if (predicate.isPrecondition()){
				preconditions.add(predicate);
			}
		}
		return preconditions;
	}
	public AxiomReferences getPostconditionReferences() {
		AxiomReferences postconditions = new AxiomReferences();
		
		for (AxiomReference predicate: elements){
			if (predicate.isPostcondition()){
				postconditions.add(predicate);
			}
		}
		return postconditions;
	}
	
	public Axioms getOriginalPreconditions() {
		Axioms preconditions = new Axioms();
		
		for (AxiomReference predicate: elements){
			if (predicate.isPrecondition()){
				preconditions.add(predicate.getAxiom());
			}
		}
		return preconditions;
	}
	public Axioms getOriginalPostconditions() {
		Axioms postconditions = new Axioms();
		
		for (AxiomReference predicate: elements){
			if (predicate.isPostcondition()){
				postconditions.add(predicate.getAxiom());
			}
		}
		return postconditions;
	}

	public Idents getUsedIdentifiers() {
		Idents usedIdentifiers = new Idents();
		
		for (AxiomReference axiomReference: elements){
			usedIdentifiers.addAll(axiomReference.getUsedIdentifiers());
		}
		return usedIdentifiers;
	}

	public static AxiomReferences empty() {
		return EMPTY_REFERENCES;
	}
}
