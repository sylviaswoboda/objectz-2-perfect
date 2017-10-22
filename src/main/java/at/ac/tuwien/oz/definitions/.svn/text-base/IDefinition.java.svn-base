package at.ac.tuwien.oz.definitions;

import org.antlr.v4.runtime.tree.ParseTree;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;

public interface IDefinition {

	public Ident getId();

	public ExpressionType getExpressionType();

	public boolean isVariable();
	
	public ParseTree getContext();
	
	public ST getTemplate();
}
