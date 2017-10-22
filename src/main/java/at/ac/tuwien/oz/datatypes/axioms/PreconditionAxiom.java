package at.ac.tuwien.oz.datatypes.axioms;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Idents;

public class PreconditionAxiom extends Axiom{

	public PreconditionAxiom(ST axiom, Idents identifiers) {
		super(axiom, identifiers);
	}

	@Override
	public boolean isPrecondition() {
		return true;
	}

	@Override
	public boolean isPostcondition() {
		return false;
	}

}
