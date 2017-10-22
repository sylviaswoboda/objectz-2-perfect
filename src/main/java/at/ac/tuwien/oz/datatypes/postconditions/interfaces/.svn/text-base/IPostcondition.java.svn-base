package at.ac.tuwien.oz.datatypes.postconditions.interfaces;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Idents;

/**
 * to be implemented by classes, that represent a single postcondition entity.
 */
public interface IPostcondition {

	ST getTemplate();
	Idents getIdentifiers();
	
	public enum Context {
		FUNCTION,
		SCHEMA
	}
}
