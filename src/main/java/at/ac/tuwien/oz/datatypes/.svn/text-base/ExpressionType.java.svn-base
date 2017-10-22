package at.ac.tuwien.oz.datatypes;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class ExpressionType {
	
	private static final ExpressionType NAT = new ExpressionType(Type.getNat());
	private static final ExpressionType INT = new ExpressionType(Type.getInt());
	private static final ExpressionType REAL = new ExpressionType(Type.getReal());
	private static final ExpressionType BOOL = new ExpressionType(Type.getBool());
	private static final ExpressionType CHAR = new ExpressionType(Type.getChar());


	private Type declaredType;
	private Type effectiveType;
	
	public ExpressionType(Type declaredType){
		this.declaredType = declaredType;
		this.effectiveType = declaredType;
	}
	
	public ExpressionType(Type declaredType, Type effectiveType) {
		this.declaredType = declaredType;
		this.effectiveType = effectiveType;
	}
	
	public Type getDeclaredType(){
		return this.declaredType;
	}
	
	public Type getEffectiveType(){
		return this.effectiveType;
	}
	
	public static ExpressionType getNat(){
		return NAT;
	}
	
	public static ExpressionType getInt(){
		return INT;
	}
	
	public static ExpressionType getReal(){
		return REAL;
	}
	
	public static ExpressionType getBool(){
		return BOOL;
	}
	
	public static ExpressionType getChar(){
		return CHAR;
	}
	
	public static ExpressionType getTuple(List<ExpressionType> subExpressionTypes) {
		Type declaredType = Type.getExpressionTypeTuple(subExpressionTypes);
		return new ExpressionType(declaredType);
	}
	
	public static ExpressionType getSet(ExpressionType innerExpressionType) {
		Type declaredType = Type.getSet(innerExpressionType);
		return new ExpressionType(declaredType);
	}
	
	public static ExpressionType getBag(ExpressionType innerExpressionType) {
		Type declaredType = Type.getBag(innerExpressionType);
		return new ExpressionType(declaredType);
	}
	
	public static ExpressionType getSequence(ExpressionType innerExpressionType) {
		Type declaredType = Type.getSequence(innerExpressionType);
		return new ExpressionType(declaredType);
	}
	
	public static ExpressionType getFunction(ExpressionType left, ExpressionType right){
		Type declaredType = Type.getFunction(left, right);
		return new ExpressionType(declaredType);
	}

	public static ExpressionType getMaplet(ParseTree ctx, ExpressionType left, ExpressionType right){
		Type declaredType = Type.getMaplet(left, right);
		return new ExpressionType(declaredType);
	}
	
	public static ExpressionType getSetConstruction(ParseTree ctx, ExpressionType innerExpressionType) {
		Type declaredType = Type.getTemplateType(ctx);
		Type effectiveType = Type.getSet(innerExpressionType);
		return new ExpressionType(declaredType, effectiveType);
	}
	
	public static ExpressionType getSequenceConstruction(ParseTree ctx, ExpressionType innerExpressionType) {
		Type declaredType = Type.getTemplateType(ctx);
		Type effectiveType = Type.getSequence(innerExpressionType);
		return new ExpressionType(declaredType, effectiveType);
	}
	
	public static ExpressionType getBagConstruction(ParseTree ctx, ExpressionType innerExpressionType) {
		Type declaredType = Type.getTemplateType(ctx);
		Type effectiveType = Type.getBag(innerExpressionType);
		return new ExpressionType(declaredType, effectiveType);
	}
	
	public static ExpressionType getVariableType(ParseTree ctx, ExpressionType declaredCollectionVariableType) {
		if (!declaredCollectionVariableType.isCollectionType() && !declaredCollectionVariableType.isRelation() && !declaredCollectionVariableType.isFunction()){
			throw new ObjectZToPerfectTranslationException("Invalid type of variable, must be any kind of collection (set, bag or sequence)");
		}
		Type declaredType = Type.getTemplateType(ctx);
		Type effectiveType = declaredCollectionVariableType.getEffectiveType();
		return new ExpressionType(declaredType, effectiveType);
	}

	public static ExpressionType getUserDefinedType(ParseTree ctx, String name){
		Type declaredType = Type.getTemplateType(ctx);
		Type effectiveType = Type.getUserDefinedType(name);
		return new ExpressionType(declaredType, effectiveType);
	}

	public static ExpressionType getRelation(ExpressionType left, ExpressionType right){
		// set of pair of (left, right)
		Type declaredType = Type.getRelation(left, right);
		return new ExpressionType(declaredType);
	}
	public static ExpressionType getRelation(ExpressionType left, ExpressionType middle, ExpressionType right){
		// set of pair of (left, right)
		Type declaredType = Type.getRelation(left, middle, right);
		return new ExpressionType(declaredType);
	}

	public boolean isUserDefinedType() {
		return this.effectiveType.isUserDefinedType();
	}

	public Ident getEffectiveTypeId() {
		return new Ident(this.effectiveType.getName());
	}

	public boolean isCollectionType() {
		return this.effectiveType.isCollection();
	}
	
	public boolean isTemplateType(){
		return this.effectiveType.isCollection() && this.declaredType.isTemplateType();
	}

	/**
	 *  returns the id of the base type if this is a collection type. for example: set of nat -> nat
	 * @return
	 */
	public Ident getBaseTypeId() {
		if (isCollectionType()){
			return this.effectiveType.getSubExpressionType(0).getEffectiveTypeId();
		} else {
			return null;
		}
	}
	public ST getBaseTypeTemplate() {
		if (isCollectionType()){
			return this.effectiveType.getSubExpressionType(0).getBaseTypeTemplate();
		} else {
			return null;
		}
	}



	public boolean isPredefinedType() {
		return this.effectiveType.isPredefinedType();
	}
	public boolean isNumber() {
		return this.effectiveType.isNumber();
	}
	public boolean isNat() {
		return this.effectiveType.isNat();
	}
	public boolean isReal() {
		return this.effectiveType.isReal();
	}
	public boolean isInt() {
		return this.effectiveType.isInt();
	}
	public boolean isFunction() {
		return this.effectiveType.isFunction();
	}
	public boolean isSetOfMaplet() {
		return this.effectiveType.isSetOfMaplet();
	}
	public boolean isRelation() {
		return this.effectiveType.isRelation();
	}
	public boolean isSet() {
		return this.effectiveType.isSet();
	}
	public boolean isSeq() {
		return this.effectiveType.isSeq();
	}
	public boolean isBag() {
		return this.effectiveType.isBag();
	}
	
	public boolean isMaplet() {
		return this.effectiveType.isMaplet();
	}
	
	public boolean isPair() {
		return this.effectiveType.isPair();
	}
	public boolean isTriple() {
		return this.effectiveType.isTriple();
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((declaredType == null) ? 0 : declaredType.hashCode());
		result = prime * result + ((effectiveType == null) ? 0 : effectiveType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpressionType other = (ExpressionType) obj;
		if (declaredType == null) {
			if (other.declaredType != null)
				return false;
		} else if (!declaredType.equals(other.declaredType))
			return false;
		if (effectiveType == null) {
			if (other.effectiveType != null)
				return false;
		} else if (!effectiveType.equals(other.effectiveType))
			return false;
		return true;
	}

	public boolean hasSubExpressionTypes(ExpressionType... subExpressionTypes) {
		for (int i = 0; i < subExpressionTypes.length; i++){
			ExpressionType actualSubExpressionType = this.effectiveType.getSubExpressionType(i);
			ExpressionType expectedSubExpressionType = subExpressionTypes[i];
			if (actualSubExpressionType == null || expectedSubExpressionType == null || !actualSubExpressionType.equals(expectedSubExpressionType)){
				return false;
			}
		}
		return false;
	}

	public ST getTemplate(){
		return this.effectiveType.getTemplate();
	}
	public ST getDeclaredTemplate(){
		return this.declaredType.getTemplate();
	}
	public ExpressionType getSubExpressionType(int i) {
		return this.effectiveType.getSubExpressionType(i);
	}

	public int getSubExpressionSize() {
		if (this.effectiveType == null || this.effectiveType.getSubExpressionTypes() == null){
			return 0;
		}
		return this.effectiveType.getSubExpressionTypes().size();
	}

	public void setDeclaredTypeTemplate(ParseTreeProperty<ST> templateTree) {
		this.declaredType.setTemplate(templateTree);
	}

}
