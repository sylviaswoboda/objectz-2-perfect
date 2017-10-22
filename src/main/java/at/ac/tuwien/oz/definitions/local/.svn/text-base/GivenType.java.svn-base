package at.ac.tuwien.oz.definitions.local;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.definitions.IScope;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;


public class GivenType extends LocalDefinition{

	private ST template;
	
	public GivenType(String givenTypeName, IScope enclosingScope, ParseTree ctx){
		super(givenTypeName, enclosingScope, ctx);
	}
	
	public void translate(ParseTreeProperty<ST> templateTree) {
		this.template = PerfectTemplateProvider.getInstance().getGivenTypeDefinition(this);
		templateTree.put(ctx, this.template);
	}
	
	public ST getTemplate(){
		return this.template;
	}
}
