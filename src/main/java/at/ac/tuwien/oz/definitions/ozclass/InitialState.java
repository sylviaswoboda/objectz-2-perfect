package at.ac.tuwien.oz.definitions.ozclass;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;

public class InitialState{

	private AxiomReferences initialStatePredicates;
	
	private boolean visible;
	
	public InitialState(AxiomReferences initialPredicates){
		this.initialStatePredicates = initialPredicates;
	}

	public boolean isVisible(){
		return this.visible;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public AxiomReferences getAxiomReferences(){
		return this.initialStatePredicates;
	}

	public void translate(ParseTreeProperty<ST> templateTree) {
		for (AxiomReference axiomRef: this.initialStatePredicates){
			axiomRef.buildAxiom(templateTree);
		}
	}
}
