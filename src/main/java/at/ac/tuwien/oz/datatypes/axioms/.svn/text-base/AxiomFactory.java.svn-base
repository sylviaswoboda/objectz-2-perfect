package at.ac.tuwien.oz.datatypes.axioms;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;

public class AxiomFactory {

	private static AxiomFactory axiomFactory;
	
	public static AxiomFactory getInstance(){
		if (axiomFactory == null){
			axiomFactory = new AxiomFactory();
		}
		return axiomFactory;
	}
	
	public Axiom createPredicateWithIdentifiers(ST axiom, Idents identifiers){
		if (identifiers == null){
			return null;
		}
		if (isPrecondition(identifiers)){
			return new PreconditionAxiom(axiom, identifiers);
		} else {
			return new PostconditionAxiom(axiom, identifiers);
		}
	}
	
    /**
     * Decides whether an axiom is a precondition axiom, i.e. it does not
     * contain output variables or primed variables.
     * 
     * @return 'false' if one of the contained identifiers is a primed or output
     * 			identifier, otherwise 'true'
     */
	private static boolean isPrecondition(Idents identifiers) {
		for (Ident i : identifiers.asList()){
			if (i.isOutputIdent()){
				return false;
			}
			if (i.isPrimedIdent()){
				return false;
			}
		}
		return true;
	}
}
