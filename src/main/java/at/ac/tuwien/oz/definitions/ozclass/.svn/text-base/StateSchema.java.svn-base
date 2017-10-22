package at.ac.tuwien.oz.definitions.ozclass;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;

public class StateSchema{

	private Declarations primaryDeclarations;
	private Declarations secondaryDeclarations;
	private AxiomReferences axioms;

	// contains axioms splitted into primary and secondary axioms
	// depending on whether a secondary Variable is used in the definition
	private AxiomReferences primaryAxioms;
	private AxiomReferences secondaryAxioms;
	
	private Map<Ident, Variable> allDeclarations = new HashMap<Ident, Variable>();
	
	public StateSchema(Declarations primaryVariables, Declarations secondaryVariables, AxiomReferences axioms){
		this.primaryDeclarations = primaryVariables;
		this.secondaryDeclarations = secondaryVariables;
		this.axioms = axioms;
		for (Variable primary: primaryVariables){
			allDeclarations.put(primary.getId(), primary);
		}
		for (Variable secondary: secondaryVariables){
			allDeclarations.put(secondary.getId(), secondary);
		}
	}
	
	// needed for mapping of primary variables
	public Declarations getPrimaryDeclarations(){
		return this.primaryDeclarations;
	}
	public Declarations getSecondaryDeclarations() {
		return this.secondaryDeclarations;
	}
	public Variable resolve(Ident id) {
		return allDeclarations.get(id);
	}

	public AxiomReferences getAxiomReferences() {
		return this.axioms;
	}

	public void translate(ParseTreeProperty<ST> templateTree) {
		for (AxiomReference axiomRef: this.axioms){
			axiomRef.buildAxiom(templateTree);
		}
	}

	public AxiomReferences getInvariants() {
		if (primaryAxioms == null){
			splitAxioms();
		}
		return primaryAxioms;
	}

	public AxiomReferences getSecondaryAxiomReferences(Variable secondaryVar) {
		AxiomReferences secondaryVarAxioms = new AxiomReferences();
		if (primaryAxioms == null){
			splitAxioms();
		}
		if (secondaryVar != null && secondaryDeclarations.contains(secondaryVar)){
			for (AxiomReference secondary: this.secondaryAxioms){
				if (secondary.usesVariable(secondaryVar)){
					secondaryVarAxioms.add(secondary);
				}
			}
		}
		return secondaryVarAxioms;
	}

	public AxiomReferences getPrimaryAxiomReferences(Variable primaryVar) {
		AxiomReferences primaryVarAxioms = new AxiomReferences();
		if (primaryAxioms == null){
			splitAxioms();
		}
		if (primaryVar != null && primaryDeclarations.contains(primaryVar)){
			for (AxiomReference primary: this.primaryAxioms){
				if (primary.usesVariable(primaryVar)){
					primaryVarAxioms.add(primary);
				}
			}
		}
		return primaryVarAxioms;
	}

	
	private void splitAxioms() {
		primaryAxioms = new AxiomReferences();
		secondaryAxioms = new AxiomReferences();
		
		for (AxiomReference ax: this.axioms){
			if (ax.usesVariables(this.secondaryDeclarations)){
				secondaryAxioms.add(ax);
			} else {
				primaryAxioms.add(ax);
			}
		}
	}

	public AxiomReferences getPrimaryAxiomReferences() {
		if (primaryAxioms == null){
			splitAxioms();
		}
		return primaryAxioms;
	}
	
//	public List<ST> getPredicatesWithoutSecondaryVariables(){
//		// TODO
//		return new ArrayList<ST>();
//	}
//	public List<ST> getAdditionalInvariantsFromVariableTypeDeclarations(){
//		// TODO
//		return new ArrayList<ST>();
//	}
	
	// needed for mapping of secondary variables
	
	
}

