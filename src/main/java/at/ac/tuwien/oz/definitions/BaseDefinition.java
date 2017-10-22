package at.ac.tuwien.oz.definitions;

import org.antlr.v4.runtime.tree.ParseTree;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;

public abstract class BaseDefinition implements IDefinition{

	protected String name;
	protected ParseTree ctx;
	
	public BaseDefinition(ParseTree ctx){
		this.name = "anonymous";
	}
	
	public BaseDefinition(String name, ParseTree ctx){
		this.name = name;
		this.ctx = ctx;
	}
	public Ident getId() {
		return new Ident(this.name);
	}
	
	public boolean isVariable(){
		return false;
	}
	
	public ExpressionType getExpressionType(){
		return null;
	}
	
	public String getName(){
		return this.name;
	}
	public ParseTree getContext(){
		return this.ctx;
	}
}
