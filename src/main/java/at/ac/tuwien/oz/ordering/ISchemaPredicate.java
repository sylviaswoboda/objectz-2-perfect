package at.ac.tuwien.oz.ordering;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Idents;

public interface ISchemaPredicate {

	Idents getUsedIdentifiers();

	ST getTemplate(ParseTreeProperty<ST> templateTree);

}
