package at.ac.tuwien.oz.main;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.parser.OZBaseListener;
import at.ac.tuwien.oz.parser.OZParser.ClassDefContext;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class TranslateOZToPerfect extends OZBaseListener {
	
	private ParseTreeProperty<ST> templates = new ParseTreeProperty<ST>();
	private PerfectTemplateProvider perfect;
	
	public TranslateOZToPerfect(PerfectTemplateProvider perfect){
		this.perfect = perfect;
	}
	
	@Override
	public void exitClassDef(ClassDefContext ctx){
		ST classDef = templates.get(ctx);
		System.out.println(classDef.render());
	}
	


}
