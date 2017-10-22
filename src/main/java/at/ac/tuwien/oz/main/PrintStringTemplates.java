package at.ac.tuwien.oz.main;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.parser.OZBaseListener;

public class PrintStringTemplates extends OZBaseListener {

	private ParseTreeProperty<ST> treeTemplates;
	
	public PrintStringTemplates(ParseTreeProperty<ST> treeTemplates){
		this.treeTemplates = treeTemplates;
	}

	
	@Override
	public void exitEveryRule(ParserRuleContext ctx) {
		ST template = this.treeTemplates.get(ctx);
		
		System.out.println(ctx.getRuleIndex() + ":" + template.render());
	}

	
	
}
