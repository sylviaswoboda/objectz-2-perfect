package at.ac.tuwien.oz.datatypes.axioms;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Idents;

public class PostconditionAxiom extends Axiom{

	public PostconditionAxiom(ST axiom, Idents identifiers) {
		super(axiom, identifiers);
	}

	@Override
	public boolean isPrecondition() {
		return false;
	}

	@Override
	public boolean isPostcondition() {
		return true;
	}

}
