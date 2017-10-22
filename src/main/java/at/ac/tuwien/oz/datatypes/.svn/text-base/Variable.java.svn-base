package at.ac.tuwien.oz.datatypes;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.definitions.IDefinition;
import at.ac.tuwien.oz.ordering.ISchemaVariable;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class Variable implements IDefinition, Comparable<Variable>, ISchemaVariable{

	private Ident id;
	private ExpressionContext typeCtx;
	
	private ExpressionType expressionType;

	public static Variable createInputVariable(String name, ExpressionType type){
		return new Variable(new Ident(name, "?"), type);
	}

	public static Variable createOutputVariable(String name, ExpressionType type){
		return new Variable(new Ident(name, "!"), type);
	}

	public static Variable createUndecoratedVariable(String name, ExpressionType type){
		return new Variable(new Ident(name), type);
	}

	public static Variable createTempVarCopy(Variable original){
		return new Variable(new Ident(TempVarProvider.getTempVarName()), original.getExpressionType());
	}
	
	public Variable(Ident id, ExpressionContext typeCtx){
		super();
		this.id = id;
		this.typeCtx = typeCtx;
	}
	
	private Variable(Ident id, ExpressionType type) {
		super();
		this.id = id;
		this.expressionType = type;
	}
	
//	private Variable(Ident id, Type type, ST typeExpressionTemplate){
//		this(id, type);
//		this.typeExpressionTemplate = typeExpressionTemplate;
//	}

	public Ident getId() {
		return id;
	}

	public void setExpressionType(ExpressionType expressionType){
		this.expressionType = expressionType;
	}
	public void updateDeclaredExpressionTypeTemplate(ParseTreeProperty<ST> templateTree) {
		this.expressionType.setDeclaredTypeTemplate(templateTree);
	}
	public ExpressionType getExpressionType(){
		return this.expressionType;
	}
	
	public ExpressionContext getTypeCtx() {
		return typeCtx;
	}

	public boolean isInputVariable() {
		return id.isInputIdent();
	}

	public boolean isOutputVariable() {
		return id.isOutputIdent();
	}

	public boolean isPrimdVariable() {
		return id.isPrimedIdent();
	}

	@Override
	public int compareTo(Variable o) {
		return id.compareTo(o.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((expressionType == null) ? 0 : expressionType.hashCode());
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
		Variable other = (Variable) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (expressionType == null) {
			if (other.expressionType != null)
				return false;
			else
				return true;
		} else if (other.expressionType == null){
			return false;
		} else {
			return expressionType.equals(other.expressionType);
		}
	}

	public boolean hasSameBaseName(Variable other) {
		return id.hasSameBaseName(other.getId());
	}

	public Variable getUndecoratedCopy() {
		Ident undecoratedIdent = id.getUndecoratedCopy();
		return new Variable(undecoratedIdent, expressionType);
	}
	public Variable getPrimedCopy() {
		Ident primedIdent = id.getPrimedCopy();
		return new Variable(primedIdent, expressionType);
	}

	public Variable getInputCopy() {
		Ident inputIdent = id.getInputCopy();
		return new Variable(inputIdent, expressionType);
	}

	public Variable getOutputCopy() {
		Ident inputIdent = id.getOutputCopy();
		return new Variable(inputIdent, expressionType);
	}

	public Variable getTempCopy() {
		Ident ident = id.getTempCopy();
		return new Variable(ident, expressionType);
	}
	
	public Variable getTempCopy(String tempVarName) {
		Ident ident = new Ident(tempVarName);
		return new Variable(ident, expressionType);
	}

	@Override
	public String toString() {
		return "Variable [" + id.getName() + id.getDecoration() + ":" + expressionType + "]";
	}

	@Override
	public boolean isVariable() {
		return true;
	}

	public ST getTypeTemplate(){
		if (this.expressionType.isCollectionType() && this.expressionType.getDeclaredType().isTemplateType()){
			return this.expressionType.getSubExpressionType(0).getTemplate();
		} else {
			return this.expressionType.getTemplate();
		}
	}
	
	public ST getDeclaredTypeTemplate(){
		return this.expressionType.getDeclaredTemplate();
	}

	@Override
	public ParseTree getContext() {
		return null;
	}

	@Override
	public ST getTemplate() {
		return PerfectTemplateProvider.getInstance().getDeclarationInOperation(this);
	}

	@Override
	public boolean hasPredefinedType() {
		return this.expressionType.isPredefinedType();
	}

	@Override
	public boolean hasUserDefinedType() {
		return this.expressionType.isUserDefinedType();
	}

	@Override
	public boolean hasCollectionType() {
		return this.expressionType.isCollectionType() || this.expressionType.isRelation();
	}

}
