package at.ac.tuwien.oz.translator;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;

public class TypeEvaluator {
	
	public TypeEvaluator(ParseTreeProperty<ExpressionType> typeTree){
		this.register(typeTree);
	}

	private List<ParseTreeProperty<ExpressionType>> typeTrees = new ArrayList<ParseTreeProperty<ExpressionType>>();
	
	public boolean isMap(ExpressionContext ctx){
		for(ParseTreeProperty<ExpressionType> typeTree: typeTrees){
			ExpressionType t = typeTree.get(ctx);
			if (t != null){
				return t.isFunction();
			}
		}
		return false;
	}
	
	public boolean isRelation(ExpressionContext ctx){
		for(ParseTreeProperty<ExpressionType> typeTree: typeTrees){
			ExpressionType t = typeTree.get(ctx);
			if (t != null){
				return t.isRelation();
			}
		}
		return false;
	}

	public boolean isPreOrUserDefinedType(ExpressionContext ctx) {
		for(ParseTreeProperty<ExpressionType> typeTree: typeTrees){
			ExpressionType t = typeTree.get(ctx);
			if (t != null){
				return t.isPredefinedType() || t.isUserDefinedType();
			}
		}
		return false;
	}

	public boolean isCollectionType(ParseTree cTypeExprCtx) {
		for(ParseTreeProperty<ExpressionType> typeTree: typeTrees){
			ExpressionType t = typeTree.get(cTypeExprCtx);
			if (t != null){
				return t.isCollectionType();
				
			}
		}
		return false;
	}

	public void register(ParseTreeProperty<ExpressionType> typeTree) {
		this.typeTrees.add(typeTree);
	}

	public ExpressionType getType(ParseTree ctx) {
		for(ParseTreeProperty<ExpressionType> typeTree: typeTrees){
			ExpressionType t = typeTree.get(ctx);
			return t;
		}
		return null;
	}

}
