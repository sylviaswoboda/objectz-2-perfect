package at.ac.tuwien.oz.datatypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class Type {
	
	private static final Type NAT = new Type(Category.PREDEFINED, "nat");
	private static final Type INT = new Type(Category.PREDEFINED, "int");
	private static final Type REAL = new Type(Category.PREDEFINED, "real");
	private static final Type BOOL = new Type(Category.PREDEFINED, "bool");
	private static final Type CHAR = new Type(Category.PREDEFINED, "char");

	public enum Category{
		TUPLE,
		SET,
		SEQ,
		BAG,
		FUNCTION,
		MAPLET,
		RELATION,
		TERNARY_RELATION,
		PREDEFINED,
		USERDEFINED,
		TEMPLATE
	};
	
	private Category category;
	private String name;
	private List<ExpressionType> subExpressionTypes;
	
	private ParseTree exprCtx;
	private ST template;
	
	private Type(Category category, String name) {
		this.category = category;
		this.name = name;
	}
	
	public static Type getUserDefinedType(String name){
		return new Type(Category.USERDEFINED, name);
	}
	
	public static Type getTemplateType(ParseTree ctx) {
		Type templateType = new Type(Category.TEMPLATE, "template");
		templateType.exprCtx = ctx;
		return templateType;
	}
	
	protected static Type getExpressionTypeTuple(List<ExpressionType> subTypes){
		Type tupleType = new Type(Category.TUPLE, "tuple of");
		tupleType.subExpressionTypes = subTypes;
		return tupleType;
	}
	
	protected static Type getSet(ExpressionType subType){
		Type setType = new Type(Category.SET, "set of");
		setType.subExpressionTypes = Arrays.asList(subType);
		return setType;
	}

	protected static Type getSequence(ExpressionType subType){
		Type seqType = new Type(Category.SEQ, "seq of");
		seqType.subExpressionTypes = Arrays.asList(subType);
		return seqType;
	}
	protected static Type getBag(ExpressionType subType){
		Type newType = new Type(Category.BAG, "bag of");
		newType.subExpressionTypes = Arrays.asList(subType);
		return newType;
	}
	protected static Type getFunction(ExpressionType left, ExpressionType right){
		Type newType = new Type(Category.FUNCTION, "function of");
		newType.subExpressionTypes = Arrays.asList(left, right);
		return newType;
	}
	protected static Type getMaplet(ExpressionType left, ExpressionType right){
		Type newType = new Type(Category.MAPLET, "maplet of");
		newType.subExpressionTypes = Arrays.asList(left, right);
		return newType;
	}
	public static Type getRelation(ExpressionType left, ExpressionType right){
		// set of pair of (left, right)
		Type newType = new Type(Category.RELATION, "relation of");
		ExpressionType innerPairType = ExpressionType.getTuple(Arrays.asList(left, right));
		newType.subExpressionTypes = Arrays.asList(innerPairType);
		return newType;
	}
	protected static Type getRelation(ExpressionType left, ExpressionType middle, ExpressionType right){
		// set of triple of (left, middle, right)
		Type newType = new Type(Category.TERNARY_RELATION, "ternary relation of");
		ExpressionType innerPairType = ExpressionType.getTuple(Arrays.asList(left, middle, right));
		newType.subExpressionTypes = Arrays.asList(innerPairType);
		return newType;
	}

	public static Type getNat(){
		return NAT;
	}
	
	protected static Type getInt(){
		return INT;
	}
	
	public static Type getReal(){
		return REAL;
	}
	
	protected static Type getBool(){
		return BOOL;
	}
	
	protected static Type getChar(){
		return CHAR;
	}

	public boolean isMap() {
		if (this.category == Category.MAPLET || this.category == Category.FUNCTION){
			return true;
		}
		return false;
	}
	public boolean isMaplet() {
		if (this.category == Category.MAPLET){
			return true;
		}
		return false;
	}

	public boolean isRelation() {
		if (this.category == Category.RELATION){
			return true;
		}
		return false;
	}

	public boolean isFunction() {
		if (this.category == Category.FUNCTION){
			return true;
		}
		if (isSetOfMaplet()){
			return true;
		}
		return false;
	}
	
	public boolean isSetOfMaplet(){
		if (this.category == Category.SET && this.getSubExpressionTypes().size() == 1 && 
				(this.getSubExpressionType(0).isMaplet() || this.getSubExpressionType(0).isPair())){
			return true;
		}
		return false;
	}

	public boolean isUserDefinedType() {
		if (this.category == Category.USERDEFINED){
			return true;
		}
		return false;
	}

	public boolean isTemplateType() {
		if (this.category == Category.TEMPLATE){
			return true;
		}
		return false;
	}

	public boolean isPredefinedType() {
		if (this.category == Category.PREDEFINED){
			return true;
		}
		return false;
	}

	public boolean isCollection() {
		return this.isBag() || this.isSeq() || this.isSet();
	}

	public boolean isSet() {
		if (this.category == Category.SET ){
			return true;
		}
		return false;
	}
	
	public boolean isSeq() {
		if (this.category == Category.SEQ ){
			return true;
		}
		return false;
	}
	public boolean isBag() {
		if (this.category == Category.BAG ){
			return true;
		}
		return false;
	}
	
	public boolean isNumber() {
		if (this.isNat() || this.isInt() || isReal()){
			return true;
		}
		return false;
	}
	
	public boolean isNat() {
		return this.equals(NAT);
	}
	public boolean isInt() {
		return this.equals(INT);
	}
	public boolean isReal() {
		return this.equals(REAL);
	}
	public String getName(){
		return this.name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		if (subExpressionTypes != null){
			sb.append(subExpressionTypes);
		}
		sb.append("(").append(category).append(")");
		return sb.toString();
	}

	public Category getCategory() {
		return this.category;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subExpressionTypes == null) ? 0 : subExpressionTypes.hashCode());
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
		Type other = (Type) obj;
		if (category != other.category)
			return false;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subExpressionTypes == null) {
			if (other.subExpressionTypes != null)
				return false;
		} else if (!subExpressionTypes.equals(other.subExpressionTypes))
			return false;
		return true;
	}

	public ST getTemplate() {
		if (this.template == null){
			setTemplate();
		}
		return this.template;
	}

	public void setTemplate(ParseTreeProperty<ST> templateTree){
		if (exprCtx != null){
			this.template = templateTree.get(exprCtx);
		}
	}
	
	private ST setTemplate() {
		PerfectTemplateProvider perfect = PerfectTemplateProvider.getInstance();
		if (this.isNat()){
			this.template = perfect.getNat();
		} else if (this.isInt()){
			this.template = perfect.getInt();
		} else if (this.isReal()){
			this.template = perfect.getReal();
		} else if (this.equals(BOOL)){
			this.template = perfect.getBool();
		} else if (this.equals(CHAR)){
			this.template = perfect.getChar();
		} else if (this.isUserDefinedType()){
			ST className = perfect.getId(new Ident(this.name));
			this.template = className;
		} else {
//			if (this.isUserDefinedType()){
//				ST className = perfect.getId(new Ident(this.name));
//				this.template = className;
//				if (this.subTypes == null || this.subTypes.isEmpty()){
//				} else { 
//					ST genericParameters;
//					if (this.subTypes.size() == 1){
//						genericParameters = perfect.getGenericParameter(subtypes.get(0).getTemplate());
//					} else {
//						genericParameters = perfect.getGenericParameters(getSubTypeTemplates());
//					}
//					this.template = perfect.getGenericClassReference(className, genericParameters);
//					throw new ObjectZToPerfectTranslationException("generic parameters not supported");
//				}
			if (this.isSet() || this.isSeq() || this.isBag() || this.isRelation()){
				ST subTemplate = null;
				if (this.subExpressionTypes != null && this.subExpressionTypes.get(0) != null){
					subTemplate = this.subExpressionTypes.get(0).getTemplate();
				}
				if (this.isSeq()){
					this.template = perfect.getSequenceDefinition(subTemplate);
				} else if (this.isSet() || this.isRelation()){
					this.template = perfect.getSetDefinition(subTemplate);
				} else if (this.isBag()){
					this.template = perfect.getBagDefinition(subTemplate);
				}
			} else if (this.isTuple() || this.isMaplet()){
				this.template = perfect.getTupleDefinition(getSubTypeTemplates());
			} else if (this.isFunction()){
				this.template = perfect.getMap(this.subExpressionTypes.get(0).getTemplate(), this.subExpressionTypes.get(1).getTemplate());
			}
		}
		return this.template;
	}

	private List<ST> getSubTypeTemplates() {
		return this.subExpressionTypes.stream().map(elem -> elem.getTemplate()).collect(Collectors.toList());
	}

	private boolean isTuple() {
		return Category.TUPLE.equals(this.category);
	}

	public boolean isPair() {
		return isTuple() && this.getSubExpressionTypes().size() == 2;
	}

	public boolean isTriple() {
		return isTuple() && this.getSubExpressionTypes().size() == 3;
	}

//	public boolean isSetVariable() {
//		return this.isTemplateType() && this.getSubType(0).isSet();
//	}
//
	public ExpressionType getSubExpressionType(int index) {
		if (index >= 0 && index < this.subExpressionTypes.size()){
			return this.subExpressionTypes.get(index);
		}
		return null;

	}
	public List<ExpressionType> getSubExpressionTypes() {
		return this.subExpressionTypes;
	}

}
