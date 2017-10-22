package at.ac.tuwien.oz.datatypes.postconditions;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;

public class SimplePostcondition implements IPostcondition{

	private ST postcondition;
	private Idents usedIdentifiers;
	
	public SimplePostcondition(ST postcondition, Idents usedIdentifiers){
		this.postcondition = postcondition;
		this.usedIdentifiers = new Idents();
		if (usedIdentifiers != null){
			this.usedIdentifiers.addAll(usedIdentifiers);
		}
	}

	@Override
	public ST getTemplate() {
		return postcondition;
	}

	@Override
	public Idents getIdentifiers() {
		return this.usedIdentifiers;
	}
}
