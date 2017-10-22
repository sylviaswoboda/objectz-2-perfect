package at.ac.tuwien.oz.definitions.local;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.definitions.IScope;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
import at.ac.tuwien.oz.translator.TypeEvaluator;

public class Abbreviation extends LocalDefinition {
	
	private ExpressionContext expressionCtx;
	private List<Ident> formalParameters;
	
	private ST definition;
	private ST template;
	
	
	public Abbreviation(String abbreviationName, IScope enclosingScope, ParseTree ctx){
		super(abbreviationName, enclosingScope, ctx);
		this.formalParameters = new ArrayList<Ident>();
	}
	
	public ExpressionContext getExpressionCtx(){
		return this.expressionCtx;
	}

	public List<Ident> getFormalParameters(){
		return this.formalParameters;
	}
	
	public void addFormalParameter(Ident formalParameter) {
		this.formalParameters.add(formalParameter);
	}

	public void addExpressionCtx(ExpressionContext expressionCtx) {
		this.expressionCtx = expressionCtx;
	}

	public boolean isCollection(TypeEvaluator evaluator) {
		evaluator.isPreOrUserDefinedType(expressionCtx);
		evaluator.isCollectionType(expressionCtx);
		return false;
	}

	public boolean isCollection() {
		// TODO Auto-generated method stub
		return false;
	}

	public ST getDefinition() {
		return this.definition;
	}

	public boolean hasFormalParameters() {
		if (this.formalParameters == null || this.formalParameters.isEmpty()){
			return false;
		}
		return true;
	}
	
	public void translate(ParseTreeProperty<ST> templateTree) {
		// TODO Auto-generated method stub
		this.template = null;
		templateTree.put(ctx, template);
	}
	
	public ST getTemplate(){
		return this.template;
	}

}
