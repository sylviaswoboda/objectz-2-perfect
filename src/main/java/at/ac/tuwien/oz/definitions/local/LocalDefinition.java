package at.ac.tuwien.oz.definitions.local;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.definitions.BaseDefinition;
import at.ac.tuwien.oz.definitions.IDefinition;
import at.ac.tuwien.oz.definitions.IScope;

/**
 * Puts all Local definitions (Abbreviation, Axiomatic, FreeType and GivenType) into one type hierarchy.
 * 
 * @author sylvias
 *
 */
public abstract class LocalDefinition extends BaseDefinition implements IDefinition{

	public LocalDefinition(String name, IScope enclosingScope, ParseTree ctx) {
		super(name, ctx);
	}

	@Override
	public ExpressionType getExpressionType() {
		return ExpressionType.getUserDefinedType(ctx, this.getId().getName());
	}

	public abstract void translate(ParseTreeProperty<ST> templateTree);

}
