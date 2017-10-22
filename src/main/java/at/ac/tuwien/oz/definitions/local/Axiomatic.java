package at.ac.tuwien.oz.definitions.local;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;

public class Axiomatic {

	private Declarations declarations;
	private AxiomReferences axiomReferences;
	
	public Axiomatic(){
		this.declarations = new Declarations();
		this.axiomReferences = new AxiomReferences();
	}
	
	public Declarations getDeclarations(){
		return this.declarations;
	}
	public AxiomReferences getAxiomReferences() {
		return this.axiomReferences;
	}

	public Variable resolve(Ident ident) {
		return declarations.get(ident);
	}

	public void addAxiomaticDeclarations(Declarations axiomaticDeclarations) {
		this.declarations.addAll(axiomaticDeclarations);
	}

	public void addAxiomaticReferences(AxiomReferences axiomaticReferences) {
		this.axiomReferences.addAll(axiomaticReferences);
	}
	public AxiomReferences getAxiomReferences(Variable axiomaticVar) {
		AxiomReferences axiomReferences = new AxiomReferences();
		if (axiomaticVar != null && declarations.contains(axiomaticVar)){
			for (AxiomReference axiomReference: this.axiomReferences){
				if (axiomReference.usesVariable(axiomaticVar)){
					axiomReferences.add(axiomReference);
				}
			}
		}
		return axiomReferences;
	}
	
	public void translate(ParseTreeProperty<ST> templateTree) {
		for (AxiomReference axiomReference: axiomReferences){
			axiomReference.buildAxiom(templateTree);
		}
	}
}
