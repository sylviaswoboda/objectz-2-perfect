package at.ac.tuwien.oz.definitions.local;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.definitions.IScope;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class FreeType extends LocalDefinition {
	
	private List<String> values;
	private ST template;
	
	public FreeType(String freeTypeName, List<String> values, IScope enclosingScope, ParseTree ctx){
		super(freeTypeName, enclosingScope, ctx);
		this.values = values;
	}
	
	public List<String> getValues(){
		return values;
	}
	
	public void translate(ParseTreeProperty<ST> templateTree) {
		this.template = PerfectTemplateProvider.getInstance().getFreeTypeDefinition(this);
		templateTree.put(ctx, template);
	}

	public ST getTemplate(){
		return this.template;
	}
}
