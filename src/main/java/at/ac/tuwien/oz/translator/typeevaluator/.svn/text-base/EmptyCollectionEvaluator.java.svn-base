package at.ac.tuwien.oz.translator.typeevaluator;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.parser.OZBaseListener;
import at.ac.tuwien.oz.parser.OZParser.EmptyBagContext;
import at.ac.tuwien.oz.parser.OZParser.EmptySequenceContext;
import at.ac.tuwien.oz.parser.OZParser.EmptySetContext;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class EmptyCollectionEvaluator extends OZBaseListener{
	
	// input
	private ObjectZDefinition definition;
	
	//input and output
	private ParseTreeProperty<ExpressionType> typeTree;
	
	public EmptyCollectionEvaluator(ObjectZDefinition definition, ParseTreeProperty<ExpressionType> typeTree){
		this.definition = definition;
		this.typeTree = typeTree;
	}
	
	public ParseTreeProperty<ExpressionType> getTypeTree(){
		return this.typeTree;
	}
	
	public interface TypeInterface {
		boolean filter(ExpressionType t);
		ExpressionType createType(ParseTree ctx, ExpressionType subType);
	}
	
	
	@Override
	public void exitEmptySet(EmptySetContext ctx) {
		ExpressionType newType = determineSiblingType(ctx, new TypeInterface() {
			@Override
			public boolean filter(ExpressionType t) { return t.isSet();}
			@Override
			public ExpressionType createType(ParseTree ctx, ExpressionType subType) { return ExpressionType.getSetConstruction(ctx, subType);	}
		});
		
		typeTree.put(ctx, newType);
	}
	@Override
	public void exitEmptyBag(EmptyBagContext ctx) {
		ExpressionType newType = determineSiblingType(ctx, new TypeInterface() {
			@Override
			public boolean filter(ExpressionType t) { return t.isBag();}
			@Override
			public ExpressionType createType(ParseTree ctx, ExpressionType subType) { return ExpressionType.getBagConstruction(ctx, subType);	}
		});
		typeTree.put(ctx, newType);
	}
	@Override
	public void exitEmptySequence(EmptySequenceContext ctx) {
		ExpressionType newType = determineSiblingType(ctx, new TypeInterface() {
			@Override
			public boolean filter(ExpressionType t) { return t.isSeq();}
			@Override
			public ExpressionType createType(ParseTree ctx, ExpressionType subType) { return ExpressionType.getSequenceConstruction(ctx, subType);}
		});
		typeTree.put(ctx, newType);
	}
	private ExpressionType determineSiblingType(ParseTree ctx, TypeInterface typeInterface) {
		ExpressionType siblingSubType = null;
		
		if (ctx.getParent() != null){
			for (int i = 0; i< ctx.getParent().getChildCount(); i++){
				ExpressionType newSiblingType = typeTree.get(ctx.getParent().getChild(i));
				if (newSiblingType != null && typeInterface.filter(newSiblingType)){
					siblingSubType = determineNewType(siblingSubType, newSiblingType.getEffectiveType().getSubExpressionType(0)); 
				}
			}
		}
		return typeInterface.createType(ctx, siblingSubType);
	}

	// keep
	private ExpressionType determineNewType(ExpressionType subTypeLeft, ExpressionType subTypeRight) {
		ExpressionType newSubType = null;
		
		if (subTypeLeft == null){
			newSubType = subTypeRight;
		} else if (subTypeLeft != null && subTypeRight == null){
			newSubType = subTypeLeft;
		} else if (subTypeLeft.isNumber() && subTypeRight.isNumber()){
			newSubType = determineTypeOfNumber(subTypeLeft, subTypeRight);
		} else {
			if (subTypeLeft.equals(subTypeRight)){
				newSubType = subTypeLeft;
			} else if (isSubTypeOf(subTypeLeft, subTypeRight)){
				newSubType = subTypeRight;
			} else if (isSubTypeOf(subTypeRight, subTypeLeft)){
				newSubType = subTypeLeft;
			} else {
				throw new ObjectZToPerfectTranslationException("Incompatible subtypes of collection - subtypes not equal");
			}
		}
		return newSubType;
	}
	//keep
	private ExpressionType determineTypeOfNumber(ExpressionType subTypeLeft, ExpressionType subTypeRight) {
		ExpressionType newSubType;
		if (subTypeLeft.isReal() || subTypeRight.isReal()){
			newSubType = ExpressionType.getReal();
		} else if (subTypeLeft.isInt() || subTypeRight.isInt()){
			newSubType = ExpressionType.getInt();
		} else {
			newSubType = ExpressionType.getNat();
		}
		return newSubType;
	}
	//keep
	private boolean isSubTypeOf(ExpressionType subTypeLeft, ExpressionType subTypeRight) {
		if (!subTypeLeft.isUserDefinedType() || !subTypeRight.isUserDefinedType()){
			return false;
		}
		Ident classId = subTypeLeft.getEffectiveTypeId();
		ObjectZClass userDefinedType = definition.resolveClass(classId);
		if (userDefinedType == null){
			throw new ObjectZToPerfectTranslationException("Cannot find user defined Class: " + classId.getName());
		}
		return userDefinedType.isSubTypeOf(subTypeLeft.getEffectiveTypeId());
	}

}
