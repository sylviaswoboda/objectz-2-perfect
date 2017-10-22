package at.ac.tuwien.oz.translator.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.local.Abbreviation;
import at.ac.tuwien.oz.definitions.local.Axiomatic;
import at.ac.tuwien.oz.definitions.local.FreeType;
import at.ac.tuwien.oz.definitions.local.GivenType;
import at.ac.tuwien.oz.definitions.ozclass.ClassDescriptor;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;
import at.ac.tuwien.oz.translator.TempVarProvider;

public class PerfectTemplateProvider {
	
	private STGroup other;
	private STGroup expression;
	private STGroup predicates;
	private STGroup operations;
	private STGroup definitions;
	private PerfectTemplateChecker checker;

	private static PerfectTemplateProvider templateProvider;
	
	public static PerfectTemplateProvider getInstance(){
		if (templateProvider == null){
			templateProvider = new PerfectTemplateProvider();
		}
		return templateProvider;
	}
	
	private PerfectTemplateProvider(){
		other = new STGroupFile("src/main/java/at/ac/tuwien/oz/translator/templates/OtherPerfect.stg");
		expression = new STGroupFile("src/main/java/at/ac/tuwien/oz/translator/templates/Expressions.stg");
		predicates = new STGroupFile("src/main/java/at/ac/tuwien/oz/translator/templates/Predicates.stg");
		operations = new STGroupFile("src/main/java/at/ac/tuwien/oz/translator/templates/Operations.stg");
		definitions = new STGroupFile("src/main/java/at/ac/tuwien/oz/translator/templates/Definitions.stg");
		checker = new PerfectTemplateChecker();
	}

	/*
	 * Templates in Expressions.stg
	 */
	
	public ST getCartesian(ST left, ST right){
		ST cartesian = expression.getInstanceOf("cartesian");
		cartesian.add("left", left);
		cartesian.add("right", right);
		return cartesian;
	}

	public ST getNat() {
		return expression.getInstanceOf("nat");
	}

	public ST getPNat() {
		return expression.getInstanceOf("pnat");
	}

	public ST getInt() {
		return expression.getInstanceOf("integer");
	}

	public ST getBool() {
		return expression.getInstanceOf("bool");
	}

	public ST getReal() {
		return expression.getInstanceOf("real");
	}

	public ST getChar() {
		return expression.getInstanceOf("char");
	}

	public ST getNumber(String number) {
		ST num = expression.getInstanceOf("num");
		num.add("number", number);
		return num;
	}

	public ST getId(Ident ident) {
		ST id = expression.getInstanceOf("id");
		id.add("name", ident.getName());
		id.add("decoration", ident.getDecoration());
		return id;
	}
	
	public Ident getIdentFromST(ST idTemplate){
		Ident ident = null;
		
		if (checker.isId(idTemplate)){
			String name = (String)idTemplate.getAttribute("name");
			String decoration = (String)idTemplate.getAttribute("decoration");
			
			if (decoration == null){
				ident = new Ident(name);
			} else {
				ident = new Ident(name, decoration);
			}
		}
		return ident;
	}

	public ST getSuccessor(ST idOrNumber) {
		ST succ = expression.getInstanceOf("successor");
		succ.add("number", idOrNumber);
		return succ;
	}

	public ST getAttributeCall(List<ST> idTemplates) {
		ST attrCall = expression.getInstanceOf("attrCall");
		attrCall.add("ids", idTemplates);
		return attrCall;
	}
	
	public ST getFunctionReference(ST relationName, List<ST> params) {
		ST relationReference = expression.getInstanceOf("functionReference");
		relationReference.add("functionName", relationName);
		relationReference.add("params", params);
		return relationReference;
	}

	public ST getSelf() {
		ST self = expression.getInstanceOf("self");
		return self;
	}

	public ST getSequence(List<ST> elements, ST elementType) {
		String collectionType = "sequence";
		return getCollection(elements, elementType, collectionType);
	}

	public ST getBag(List<ST> elements, ST elementType) {
		String collectionType = "bag";
		return getCollection(elements, elementType, collectionType);
	}
	
	public ST getSet(List<ST> elements, ST elementType) {
		String collectionType = "set";
		return getCollection(elements, elementType, collectionType);
	}

	public ST getCollection(List<ST> elements, ST collectionType) {
		ST emptyCollection = expression.getInstanceOf("collection");
		emptyCollection.add("collectionType", collectionType);
		if (elements != null){
			emptyCollection.add("elems", elements);
		}
		return emptyCollection;
	}

	private ST getCollection(List<ST> elements, ST elementType, String collectionType) {
		ST emptyCollection = expression.getInstanceOf(collectionType);
		emptyCollection.add("type", elementType);
		if (elements != null){
			emptyCollection.add("elems", elements);
		}
		return emptyCollection;
	}

	public ST getTuple(List<ST> elements, ST type){
		ST tuple = expression.getInstanceOf("tuple");
		tuple.add("type", type);
		tuple.add("elems", elements);
		return tuple;
	}
	public ST getTupleDefinition(List<ST> subTypeTemplates){
		ST tuple = null;
		if (subTypeTemplates.size() == 2){
			tuple = expression.getInstanceOf("pairType");
		} else if (subTypeTemplates.size() == 3){
			tuple = expression.getInstanceOf("tripleType");
		} else {
			return null;
		}
		tuple.add("elemTypes", subTypeTemplates);
		return tuple;
	}
	
	public ST getPair(ST leftType, ST rightType, ST leftElem, ST rightElem) {
		ST pair = expression.getInstanceOf("pair");
		pair.add("leftType", leftType);
		pair.add("rightType", rightType);
		pair.add("left", leftElem);
		pair.add("right", rightElem);
		return pair;
	}

	public ST getTriple(ST leftType, ST middleType, ST rightType, ST left, ST middle, ST right) {
		ST triple = expression.getInstanceOf("triple");
		triple.add("leftType", leftType);
		triple.add("middleType", middleType);
		triple.add("rightType", rightType);
		triple.add("left", left);
		triple.add("middle", middle);
		triple.add("right", right);
		return triple;
	}

	private ST getTemplateWithInnerExpression(String templateName, ST innerExpression) {
		ST template = expression.getInstanceOf(templateName);
		return addInnerTemplate(template, innerExpression);
	}
	
	public ST addInnerTemplate(ST expressionTemplate, ST innerExpression){
		if (innerExpression != null){
			expressionTemplate.add("expr", innerExpression);
		}
		return expressionTemplate;
	}

	private ST getTemplateWithTwoInnerExpressions(String templateName, ST leftExpression, ST rightExpression) {
		ST template = expression.getInstanceOf(templateName);
		return addInnerTemplates(template, leftExpression, rightExpression);
	}

	public ST addInnerTemplates(ST resultTemplate, ST leftExpression, ST rightExpression){
		if (leftExpression != null){
			resultTemplate.add("left", leftExpression);
		}
		if (rightExpression != null){
			resultTemplate.add("right", rightExpression);
		}
		return resultTemplate;
	}

	public ST getMin(ST innerExpression) {
		return getTemplateWithInnerExpression("min", innerExpression);
	}

	public ST getMax(ST innerExpression) {
		return getTemplateWithInnerExpression("max", innerExpression);
	}

	public ST getMinus(ST innerExpression) {
		return getTemplateWithInnerExpression("minus", innerExpression);
	}

	public ST getCount(ST innerExpression) {
		return getTemplateWithInnerExpression("count", innerExpression);
	}

	public ST getHead(ST innerExpression) {
		return getTemplateWithInnerExpression("head", innerExpression);
	}

	public ST getTail(ST innerExpression) {
		return getTemplateWithInnerExpression("tail", innerExpression);
	}

	public ST getLast(ST innerExpression) {
		return getTemplateWithInnerExpression("last", innerExpression);
	}

	public ST getFront(ST innerExpression) {
		return getTemplateWithInnerExpression("front", innerExpression);
	}

	public ST getRev(ST innerExpression) {
		return getTemplateWithInnerExpression("rev", innerExpression);
	}

	public ST getItems(ST innerExpression) {
		return getTemplateWithInnerExpression("items", innerExpression);
	}

	public ST getRan(ST innerExpression) {
		return getTemplateWithInnerExpression("ran", innerExpression);
	}

	public ST getDom(ST innerExpression) {
		return getTemplateWithInnerExpression("dom", innerExpression);
	}

	public ST getGeneralizedUnion(ST innerExpression) {
		return getTemplateWithInnerExpression("generalizedUnion", innerExpression);
	}

	public ST getGeneralizedIntersection(ST innerExpression) {
		return getTemplateWithInnerExpression("generalizedIntersection", innerExpression);
	}

	public ST getDistributedConcatenation(ST innerExpression) {
		return getTemplateWithInnerExpression("distributedConcatenation", innerExpression);
	}

	public ST getBinaryRelation(ST leftExpression, ST rightExpression) {
		ST binaryRelation = expression.getInstanceOf("binaryRelation");
		binaryRelation.add("left", leftExpression);
		binaryRelation.add("right", rightExpression);
		return binaryRelation;
	}

	public ST getTernaryRelation(ST leftExpression, ST middleExpression,
			ST rightExpression) {
		ST ternaryRelation = expression.getInstanceOf("ternaryRelation");
		ternaryRelation.add("left", leftExpression);
		ternaryRelation.add("middle", middleExpression);
		ternaryRelation.add("right", rightExpression);
		return ternaryRelation;
	}

	public ST getMap(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("map", leftExpression, rightExpression);
	}

	public ST getEquals(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("equals", leftExpression, rightExpression);
	}

	public ST getNotEquals(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("nequals", leftExpression, rightExpression);
	}

	public ST getElem(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("elem", leftExpression, rightExpression);
	}

	public ST getNotElem(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("nelem", leftExpression, rightExpression);
	}

	public ST getSubset(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("subset", leftExpression, rightExpression);
	}

	public ST getStrictSubset(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("str_subset", leftExpression, rightExpression);
	}

	public ST getLessThan(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("lt", leftExpression, rightExpression);
	}

	public ST getLessThanEquals(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("lte", leftExpression, rightExpression);
	}

	public ST getGreaterThan(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("gt", leftExpression, rightExpression);
	}

	public ST getGreaterThanEquals(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("gte", leftExpression, rightExpression);
	}

	public ST getPrefix(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("prefix", leftExpression, rightExpression);
	}

	public ST getSuffix(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("suffix", leftExpression, rightExpression);
	}

	public ST getInSequence(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("segment", leftExpression, rightExpression);
	}

	public ST getInBag(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("inbag", leftExpression, rightExpression);
	}

	public ST getSubBag(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("subbag", leftExpression, rightExpression);
	}

	public ST getDomainRestriction(ST leftType, ST rightType, ST relationOrMap, ST restrictingSet, boolean isMap) {
		ST resultTemplate = null;
		if (isMap){
			resultTemplate = expression.getInstanceOf("mapDomainRestriction");
			addTypes(resultTemplate, leftType, rightType);
		} else {
			resultTemplate = expression.getInstanceOf("relationDomainRestriction");
		} 
		addRestriction(resultTemplate, relationOrMap, restrictingSet);
		return resultTemplate;
	}

	public ST getRangeRestriction(ST leftType, ST rightType, ST relationOrMap, ST restrictingSet, boolean isMap) {
		ST resultTemplate = null;
		if (isMap){
			resultTemplate = expression.getInstanceOf("mapRangeRestriction");
			addTypes(resultTemplate, leftType, rightType);
		} else{
			resultTemplate = expression.getInstanceOf("relationRangeRestriction");
		} 
		addRestriction(resultTemplate, relationOrMap, restrictingSet);
		return resultTemplate;
	}

	public ST getDomainAntiRestriction(ST leftType, ST rightType, ST relationOrMap, ST restrictingSet, boolean isMap) {
		ST resultTemplate = null;
		if (isMap){
			resultTemplate = expression.getInstanceOf("mapDomainAntiRestriction");
			addTypes(resultTemplate, leftType, rightType);
		} else {
			resultTemplate = expression.getInstanceOf("relationDomainAntiRestriction");
		} 
		addRestriction(resultTemplate, relationOrMap, restrictingSet);
		return resultTemplate;
	}

	public ST getRangeAntiRestriction(ST leftType, ST rightType, ST relation, ST restrictingSet, boolean isMap) {
		ST resultTemplate = null;
		if (isMap){
			resultTemplate = expression.getInstanceOf("mapRangeAntiRestriction");
			addTypes(resultTemplate, leftType, rightType);
		} else {
			resultTemplate = expression.getInstanceOf("relationRangeAntiRestriction");
		} 
		addRestriction(resultTemplate, relation, restrictingSet);
		return resultTemplate;
	}

	public void addTypes(ST resultTemplate, ST leftType, ST rightType) {
		resultTemplate.add("leftType", leftType);
		resultTemplate.add("rightType", rightType);
	}
	public void addRestriction(ST resultTemplate, ST relation, ST restrictingSet) {
		resultTemplate.add("relation", relation);
		resultTemplate.add("restrictingSet", restrictingSet);
		resultTemplate.add("varName", TempVarProvider.getTempVarName());
	}

	public ST getExtract(ST theSequence, ST theSet) {
		ST extract = expression.getInstanceOf("extract");
		extract.add("theSequence", theSequence);
		extract.add("theSet", theSet);
		extract.add("tempVar", TempVarProvider.getTempVarName());
		return extract;
	}

	public ST getFiltering(ST theSequence, ST theSet) {
		ST extract = expression.getInstanceOf("filter");
		extract.add("theSequence", theSequence);
		extract.add("theSet", theSet);
		extract.add("tempVar", TempVarProvider.getTempVarName());
		return extract;
	}

	public ST getUnion(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("union", leftExpression, rightExpression);
	}

	public ST getDifference(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("difference", leftExpression, rightExpression);
	}

	public ST getIntersection(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("intersect", leftExpression, rightExpression);
	}

	public ST getConcatenation(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("concat", leftExpression, rightExpression);
	}

	public ST getMultiplicity(ST elem, ST bagExpression) {
		return getTemplateWithTwoInnerExpressions("multiplicity", elem, bagExpression);
	}

	public ST getScaling(ST bag, ST count) {
		ST scaling = expression.getInstanceOf("bagScaling");
		scaling.add("bag", bag);
		scaling.add("count", count);
		return scaling;
	}

	public ST getBagUnion(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("bagUnion", leftExpression, rightExpression);
	}

	public ST getBagDifference(ST leftExpression, ST rightExpression) {
		return getTemplateWithTwoInnerExpressions("bagDifference", leftExpression, rightExpression);
	}
	
	public ST getOverride(ST leftType, ST rightType, ST leftExpression, ST rightExpression, boolean isMap) {
		ST resultTemplate = null;
		if (isMap){
			resultTemplate = expression.getInstanceOf("mapOverride");
			resultTemplate.add("varName", TempVarProvider.getTempVarName());
			addTypes(resultTemplate, leftType, rightType);
		} else {
			resultTemplate = expression.getInstanceOf("relationOverride");
			resultTemplate.add("leftVarName", TempVarProvider.getTempVarName());
			resultTemplate.add("rightVarName", TempVarProvider.getTempVarName());
		} 
		resultTemplate.add("leftRelation", leftExpression);
		resultTemplate.add("rightRelation", rightExpression);
		return resultTemplate;
	}

	public ST getRange(ST left, ST right) {
		return getTemplateWithTwoInnerExpressions("range", left, right);
	}

	public ST getAddition(ST left, ST right) {
		return getTemplateWithTwoInnerExpressions("add", left, right);
	}

	public ST getSubtraction(ST left, ST right) {
		return getTemplateWithTwoInnerExpressions("sub", left, right);
	}

	public ST getMultiplication(ST left, ST right) {
		return getTemplateWithTwoInnerExpressions("mult", left, right);
	}

	public ST getDivision(ST left, ST right) {
		return getTemplateWithTwoInnerExpressions("div", left, right);
	}

	public ST getIntegerDivision(ST left, ST right) {
		return getTemplateWithTwoInnerExpressions("intDiv", left, right);
	}

	public ST getModulo(ST left, ST right) {
		return getTemplateWithTwoInnerExpressions("mod", left, right);
	}

	public ST getSetDefinition(ST innerExpression) {
		ST definition = expression.getInstanceOf("setDefinition");
		definition.add("type", innerExpression);
		return definition;
	}

	public ST getSequenceDefinition(ST innerExpression) {
		ST definition = expression.getInstanceOf("seqDefinition");
		definition.add("type", innerExpression);
		return definition;
	}

	public ST getBagDefinition(ST innerExpression) {
		ST definition = expression.getInstanceOf("bagDefinition");
		definition.add("type", innerExpression);
		return definition;
	}

	public ST getGenericParameter(ST innerExpression) {
		return getTemplateWithInnerExpression("genericParameter", innerExpression);
	}

	public ST getGenericParameters(List<ST> innerExpressions){
		ST formalParameters = expression.getInstanceOf("genericParameters");
		formalParameters.add("exprs", innerExpressions);
		return formalParameters;
	}

	public ST getGenericClassReference(ST className, ST genericParameters) {
		ST genericClassReference = expression.getInstanceOf("genericClassReference");
		genericClassReference.add("className", className);
		genericClassReference.add("genericParameters", genericParameters);
		return genericClassReference;
	}

	public ST getPolymorphicExpression(ST className, ST genericParameters) {
		ST polymorphicExpression = expression.getInstanceOf("polymorphicExpression");
		polymorphicExpression.add("className", className);
		polymorphicExpression.add("genericParameters", genericParameters);
		return polymorphicExpression;
	}
	
	/*
	 * Templates in Predicates.stg
	 */

	public ST getTrue() {
		return predicates.getInstanceOf("trueT");
	}

	public ST getFalse() {
		return predicates.getInstanceOf("falseT");
	}

	public ST getBinaryRelationReference(ST type, ST relation, ST left, ST right) {
		ST binaryRelationReference = predicates.getInstanceOf("binaryRelationReference");
		binaryRelationReference.add("type", type);
		binaryRelationReference.add("relation", relation);
		binaryRelationReference.add("left", left);
		binaryRelationReference.add("right", right);
		return binaryRelationReference;
	}

	public ST getTernaryRelationReference(ST type, ST relation, ST left, ST middle, ST right) {
		ST ternaryRelationReference = predicates.getInstanceOf("ternaryRelationReference");
		ternaryRelationReference.add("type", type);
		ternaryRelationReference.add("relation", relation);
		ternaryRelationReference.add("left", left);
		ternaryRelationReference.add("middle", middle);
		ternaryRelationReference.add("right", right);
		return ternaryRelationReference;
	}

	public ST getInitCall(ST theObject) {
		ST initcall = predicates.getInstanceOf("initcall");
		initcall.add("object", theObject);
		return initcall;
	}

	public ST getNegation(ST innerPredicate) {
		ST negation = predicates.getInstanceOf("negation");
		negation.add("axiom", innerPredicate);
		return negation;
	}

	public ST getConjunction(ST left, ST right) {
		return getTemplateWithTwoInnerPredicates(left, right, "conjunction");
	}

	public ST getDisjunction(ST left, ST right) {
		return getTemplateWithTwoInnerPredicates(left, right, "disjunction");
	}

	public ST getImplication(ST left, ST right) {
		return getTemplateWithTwoInnerPredicates(left, right, "implication");
	}
	
	public ST getEquivalence(ST left, ST right) {
		return getTemplateWithTwoInnerPredicates(left, right, "equivalence");
	}
	
	private ST getTemplateWithTwoInnerPredicates(ST left, ST right, String templateName) {
		ST predicate = predicates.getInstanceOf(templateName);
		predicate.add("left", left);
		predicate.add("right", right);
		return predicate;
	}

	public ST getForall(List<ST> declarations, ST predicate) {
		ST forall = predicates.getInstanceOf("forall");
		forall.add("declarations", declarations);
		forall.add("predicate", predicate);
		return forall;
	}
	
	public ST getExists(List<ST> declarations, ST predicate) {
		ST exists = predicates.getInstanceOf("exists");
		exists.add("declarations", declarations);
		exists.add("predicate", predicate);
		return exists;
	}
	public ST getExistsOne(ST declaration, ST predicate) {
		ST existsOne = predicates.getInstanceOf("existsOne");
		existsOne.add("declaration", declaration);
		existsOne.add("predicate", predicate);
		return existsOne;
	}
	public ST getAny(ST declaration, ST predicate) {
		ST exists = predicates.getInstanceOf("any");
		exists.add("declaration", declaration);
		exists.add("predicate", predicate);
		return exists;
	}

	/*
	 * Templates in Other.stg
	 */
	
	public ST getParenthesized(ST innerExpr) {
		ST parentheses = other.getInstanceOf("parentheses");
		parentheses.add("expr", innerExpr);
		return parentheses;
	}
	
	public ST getBoundVariableDeclarationOfType(ST declarationNameList,
			ST typeExpression) {
		ST boundDeclaration = other.getInstanceOf("boundDeclType");
		boundDeclaration.add("names", declarationNameList);
		boundDeclaration.add("typeExpression", typeExpression);
		return boundDeclaration;
	}

	public ST getBoundVariableDeclarationOfCollection(ST declarationNameList,
			ST typeExpression) {
		ST boundDeclaration = other.getInstanceOf("boundDeclCollection");
		boundDeclaration.add("names", declarationNameList);
		boundDeclaration.add("collExpression", typeExpression);
		return boundDeclaration;
	}

	public ST getItemList(List<ST> items) {
		ST declarationNames = other.getInstanceOf("itemList");
		declarationNames.add("items", items);
		return declarationNames;
	}

	public ST getThoseClause(List<ST> vars, List<ST> predicates) {
		ST thoseClause = other.getInstanceOf("those");
		thoseClause.add("vars", vars);
		thoseClause.add("predicates", predicates);
		return thoseClause;
	}

	public ST getConjunctionFromList(List<ST> predicateList) {
		ST conjunction = other.getInstanceOf("conjunctionFromList");
		conjunction.add("predicateList", predicateList);
		return conjunction;
	}

	public ST getConjunctionFromListWithWS(List<ST> predicateList) {
		ST conjunction = other.getInstanceOf("conjunctionFromListWithWS");
		conjunction.add("predicateList", predicateList);
		return conjunction;
	}
	
	public ST getDisjunctionFromList(List<ST> predicateList) {
		ST conjunction = other.getInstanceOf("disjunctionFromList");
		conjunction.add("predicateList", predicateList);
		return conjunction;
	}
	
	public ST getChoice(ST precondition, ST postcondition){
		ST choice = other.getInstanceOf("choice");
		choice.add("precondition", precondition);
		choice.add("postcondition", postcondition);
		return choice;
	}
	
	/*
	 * Templates in Operations.stg
	 */

	public ST getBoolFunction(String operationName, ST inputDeclarations,
			List<ST> preconditions) {
		ST boolFunction = operations.getInstanceOf("boolFunction");
		boolFunction.add("operationName", operationName);
		boolFunction.add("inputDeclarations", inputDeclarations);
		boolFunction.add("preconditions", preconditions);
		return boolFunction;
	}

	public ST getFunction(String operationName, ST opaque,
			ST inputDeclarations, ST preconditionOpCall, ST outputDeclarations, List<ST> postconditions) {
		ST function = operations.getInstanceOf("function");
		function.add("operationName", operationName);
		function.add("opaque", opaque);
		function.add("inputDeclarations", inputDeclarations);
		function.add("outputDeclarations", outputDeclarations);
		function.add("preconditionOpCall", preconditionOpCall);
		function.add("postconditions", postconditions);
		return function;
	}
	
	public ST getChangeOperation(String operationName, ST inputAndOutputDeclarations,
			ST deltalist, ST preconditionOpCall, List<ST> postconditions) {
		ST changeOp = operations.getInstanceOf("changeOperation");
		changeOp.add("operationName", operationName);
		changeOp.add("inputAndOutputDeclarations", inputAndOutputDeclarations);
		changeOp.add("preconditionOpCall", preconditionOpCall);
		changeOp.add("deltalist", deltalist);
		changeOp.add("postconditions", postconditions);
		return changeOp;
	}

	public ST getOperationWithPreconditionOp(ST operation, ST preconditionOperation, ST postconditionOperation) {
		ST operationWithPreconditionOp = operations.getInstanceOf("operationWithPreconditionOp");
		operationWithPreconditionOp.add("postconditionOp", postconditionOperation);
		operationWithPreconditionOp.add("preconditionOp", preconditionOperation);
		operationWithPreconditionOp.add("operation", operation);
		return operationWithPreconditionOp;
	}
	
	public ST getPreconditionOpCall(ST caller, String preconditionOpName, ST inputParameterList) {
		ST preconditionOpCall;
		preconditionOpCall = operations.getInstanceOf("preconditionOpCall");
		preconditionOpCall.add("caller", caller);
		preconditionOpCall.add("operationName", preconditionOpName);
		preconditionOpCall.add("inputParameters", inputParameterList);
		return preconditionOpCall;
	}

	public ST getOutputVariableFunctionCall(ST caller, String operationName, ST inputParameterList, ST outputVariable) {
		ST outputVariableFunctionCall;
		outputVariableFunctionCall = operations.getInstanceOf("outputVariableFunctionCall");
		outputVariableFunctionCall.add("caller", caller);
		outputVariableFunctionCall.add("operationName", operationName);
		outputVariableFunctionCall.add("inputParameters", inputParameterList);
		outputVariableFunctionCall.add("outputVariable", outputVariable);
		return outputVariableFunctionCall;
	}

	public ST getWithExclamation(ST ident) {
		ST withExclamation = operations.getInstanceOf("withExclamation");
		withExclamation.add("id", ident);
		return withExclamation;
	}

	public ST getSchemaCall(ST caller, String operationName, ST parameterList) {
		ST schemaCall = operations.getInstanceOf("schemaCall");
		schemaCall.add("caller", caller);
		schemaCall.add("operationName", operationName);
		schemaCall.add("parameterList", parameterList);
		return schemaCall;
	}

	public ST getPromotedChangeOperation(String operationName, ST opaque, 
			ST inputAndOutputDeclarations, ST preconditionOpCall, List<ST> postconditions) {
		ST promotedChangeOperation = operations.getInstanceOf("promotedChangeOperation");
		promotedChangeOperation.add("operationName", operationName);
		promotedChangeOperation.add("opaque", opaque);
		promotedChangeOperation.add("inputAndOutputDeclarations", inputAndOutputDeclarations);
		promotedChangeOperation.add("preconditionOpCall", preconditionOpCall);
		promotedChangeOperation.add("postconditions", postconditions);
		return promotedChangeOperation;
	}

	public ST getCaller(ST caller) {
		ST callerTemplate = operations.getInstanceOf("caller");
		callerTemplate.add("caller", caller);
		return callerTemplate;
	}


	public ST getOutputVariableReference(Ident ident) {
		ST outputVariableReference = operations.getInstanceOf("outputVariableReference");
		ST id = getId(ident);
		outputVariableReference.add("id", id);
		return outputVariableReference;
	}

	public ST getDeclarationInOperation(Variable v){
		ST declaration = operations.getInstanceOf("declarationInOperation");
		declaration.add("id", getId(v.getId()));
		declaration.add("type", v.getTypeTemplate());
		return declaration;
	}
	public ST getModifiableDeclarationInOperation(Variable v){
		ST declaration = operations.getInstanceOf("declarationInOperation");
		declaration.add("id", getIdentInSchema(v.getId()));
		declaration.add("type", v.getTypeTemplate());
		return declaration;
	}

	public ST getOutputVariableDeclarationInSchema(Variable v){
		ST declaration = operations.getInstanceOf("outVarDeclarationInSchema");
		declaration.add("id", getId(v.getId()));
		declaration.add("type", v.getTypeTemplate());
		return declaration;
	}
	
	public ST getIdentInSchema(Ident ident){
		ST idTemplate = operations.getInstanceOf("identInSchema");
		idTemplate.add("expr", getId(ident));
		return idTemplate;
	}

	public ST getPostconditionWithDeclarations(ST declarations, ST predicates) {
		ST postconditionWithDeclarations = operations.getInstanceOf("postconditionWithDeclarations");
		postconditionWithDeclarations.add("declarations", declarations);
		postconditionWithDeclarations.add("predicates", predicates);
		return postconditionWithDeclarations;
	}

	public ST getThenPostconditions(List<ST> expressions) {
		ST thenPostconditions = operations.getInstanceOf("thenPostconditions");
		thenPostconditions.add("exprs", expressions);
		return thenPostconditions;
	}

	/*
	 * Templates in Definitions.stg
	 */
	public ST getGivenTypeDefinition(GivenType givenType){
		ST givenTypeDefinition = definitions.getInstanceOf("givenTypeDefinition");
		givenTypeDefinition.add("name", givenType.getName());
		return givenTypeDefinition;
	}
	
	public ST getFreeTypeDefinition(FreeType freeType){
		List<ST> valueList = freeType.getValues().stream().map(v -> getId(new Ident(v))).collect(Collectors.toList());
		ST freeTypeDefinition = definitions.getInstanceOf("freeTypeDefinition");
		freeTypeDefinition.add("name", freeType.getName());
		freeTypeDefinition.add("values", getItemList(valueList));
		return freeTypeDefinition;
	}
	
	public ST getAbbreviationDefinition(Abbreviation abbreviation){
		ST abbreviationTemplate;
		
		if (abbreviation.isCollection()){
			abbreviationTemplate = definitions.getInstanceOf("abbrDefinitionCollection");
		} else {
			abbreviationTemplate = definitions.getInstanceOf("abbrDefinition");
		}
		abbreviationTemplate.add("name", abbreviation.getName());
		
		if (abbreviation.hasFormalParameters()){
			List<ST> formalParamterList = abbreviation.getFormalParameters().stream().map(v -> getId(v)).collect(Collectors.toList());
			ST formalParameterTemplate = getItemList(formalParamterList);
			abbreviationTemplate.add("formalParameters", formalParameterTemplate);
		}
		abbreviationTemplate.add("definition", abbreviation.getDefinition());
		return abbreviationTemplate;
	}

	public ST getGlobalAxiomatic(Axiomatic axiomatic){
		if (axiomatic.getDeclarations().isEmpty()){
			return null;
		}
		ST globalAxiomatic = definitions.getInstanceOf("globalAxiomatic");
		
		List<ST> declarations = new ArrayList<>();
		declarations.addAll(axiomatic.getDeclarations().stream().map(v -> getDeclarationInClass(v)).collect(Collectors.toList()));
		globalAxiomatic.add("declarations", declarations);
		
		List<ST> invariants = new ArrayList<>();
		invariants.addAll(axiomatic.getAxiomReferences().stream().map(i -> getInvariant(i)).collect(Collectors.toList()));
		globalAxiomatic.add("invariants", invariants);
		
		
		List<ST> buildDeclarations = new ArrayList<>();
		for (Variable var: axiomatic.getDeclarations()){
			buildDeclarations.add(getBuildDeclaration(var));
		}
		List<ST> buildPreconditions = new ArrayList<>();
		for (AxiomReference predicateRef: axiomatic.getAxiomReferences()){
			buildPreconditions.add(predicateRef.getAxiom().getPredicate());
		}
		ST constructorST = getConstructor(buildDeclarations, buildPreconditions, null);
		
		globalAxiomatic.add("constructor", constructorST);
		
		
		List<ST> visibleGetter = new ArrayList<>();
		visibleGetter.addAll(axiomatic.getDeclarations().stream().map(v -> getGetter(v.getId().getName())).collect(Collectors.toList()));
		globalAxiomatic.add("visibleGetter", visibleGetter);
		return globalAxiomatic;
	}
	
	public ST getDefinition(ObjectZDefinition objectZDefinition) {
		ST ozDefinition = definitions.getInstanceOf("objectZDefinition");
		List<ST> globalDefTemplates = objectZDefinition.getLocalDefinitions().asList().stream().map(d -> d.getTemplate()).collect(Collectors.toList());
		ozDefinition.add("globalDefinitions", globalDefTemplates);
		ozDefinition.add("globalAxiomatic", getGlobalAxiomatic(objectZDefinition.getGlobalAxiomatic()));
		List<ST> classTemplates = objectZDefinition.getObjectZClasses().asList().stream().map(c -> c.getTemplate()).collect(Collectors.toList());
		ozDefinition.add("classes", classTemplates);
		ozDefinition.add("includeStrictNaturals", objectZDefinition.includeStrictNaturals());
		return ozDefinition;
	}

	public ST getInherits(List<ST> classTemplates){
		ST inherits = definitions.getInstanceOf("inherits");
		inherits.add("classes", classTemplates);
		return inherits;
	}
	
	public ST getClassDef(ObjectZClass objectZClass) {
		ST classDef = definitions.getInstanceOf("classDef");
		classDef.add("name", objectZClass.getName());
		
		addLocalDefinitions(classDef, objectZClass);
		addFormalParameters(classDef, objectZClass);
		addInheritedClasses(classDef, objectZClass);
		addDeclarations(classDef, objectZClass);
		addInvariants(classDef, objectZClass);
		addInitState(classDef, objectZClass);
		addGettersAndSetters(classDef, objectZClass);
		addSecondary(classDef, objectZClass);
		addOperations(classDef, objectZClass);
		addConstructor(classDef, objectZClass);
		return classDef;
	}

	private void addConstructor(ST classDef, ObjectZClass objectZClass) {
		List<ST> declarations = new ArrayList<>();
		for (Variable var: objectZClass.getPrimaryDeclarations()){
			declarations.add(getBuildDeclaration(var));
		}
		for (Variable var: objectZClass.getAxiomaticDeclarations()){
			declarations.add(getBuildDeclaration(var));
		}
		List<ST> inherits = new ArrayList<>();
		Map<ClassDescriptor, Declarations> inheritedFields = objectZClass.getInheritedFields();
		for (ClassDescriptor classDes: inheritedFields.keySet()){
			List<ST> constructorCallParams = inheritedFields.get(classDes).getInputCopies().getIdentifiers()
					.stream()
					.map(i -> PerfectTemplateProvider.getInstance().getId(i))
					.collect(Collectors.toList());
			inherits.add(getConstructorCall(classDes.getTemplate(), constructorCallParams));
			for (Variable inheritedMember: inheritedFields.get(classDes)){
				declarations.add(getDeclarationInOperation(inheritedMember.getInputCopy()));
			}
		}
		
		List<ST> preconditions = new ArrayList<>();
		for (AxiomReference predicateRef: objectZClass.getStateAxiomReferences()){
			preconditions.add(predicateRef.getAxiom().getPredicate());
		}
		for (AxiomReference predicateRef: objectZClass.getAxiomaticReferences()){
			preconditions.add(predicateRef.getAxiom().getPredicate());
		}
		for (AxiomReference predicateRef: objectZClass.getInitialAxiomReferences()){
			preconditions.add(predicateRef.getAxiom().getPredicate());
		}
		
		
		ST constructorST = getConstructor(declarations, preconditions, inherits);
		classDef.add("constructor", constructorST);
	}

	private ST getConstructorCall(ST classDescriptor, List<ST> params) {
		ST constructorCallST = definitions.getInstanceOf("constructorCall");
		constructorCallST.add("classDescriptor", classDescriptor);
		constructorCallST.add("params", params);
		return constructorCallST;
	}

	private ST getConstructor(List<ST> declarations, List<ST> preconditions, List<ST> inherits) {
		ST constructorST = definitions.getInstanceOf("constructor");
		constructorST.add("declarations", declarations);
		constructorST.add("preconditions", preconditions);
		constructorST.add("inherits", inherits);
		return constructorST;
	}

	private ST getBuildDeclaration(Variable var) {
		ST buildDecl = definitions.getInstanceOf("buildDecl");
		buildDecl.add("name", var.getId().getName());
		buildDecl.add("type", var.getTypeTemplate());
		return buildDecl;
	}

	private void addOperations(ST classDef, ObjectZClass objectZClass) {
		List<ST> visibleOperationsST = new ArrayList<>();
		List<ST> invisibleOperationsST = new ArrayList<>();
		
		visibleOperationsST.addAll(objectZClass.getSimpleOperations().stream()
				.filter(op -> objectZClass.isVisible(op.getId()))
				.map(op -> op.getTemplate())
				.collect(Collectors.toList()));
		visibleOperationsST.addAll(objectZClass.getOperationExpressions().stream()
				.filter(op -> objectZClass.isVisible(op.getId()))
				.map(op -> op.getTemplate())
				.collect(Collectors.toList()));

		invisibleOperationsST.addAll(objectZClass.getSimpleOperations().stream()
				.filter(op -> !objectZClass.isVisible(op.getId()))
				.map(op -> op.getTemplate())
				.collect(Collectors.toList()));
		invisibleOperationsST.addAll(objectZClass.getOperationExpressions().stream()
				.filter(op -> !objectZClass.isVisible(op.getId()))
				.map(op -> op.getTemplate())
				.collect(Collectors.toList()));

		classDef.add("visibleOperations", visibleOperationsST);
		classDef.add("invisibleOperations", invisibleOperationsST);
		
	}

	private void addLocalDefinitions(ST classDef, ObjectZClass objectZClass) {
		List<ST> localDefinitions = objectZClass.getLocalDefinitions().stream().map(l -> l.getTemplate()).collect(Collectors.toList());
		classDef.add("localDefinitions", localDefinitions);
	}

	private void addSecondary(ST classDef, ObjectZClass objectZClass) {
		List<ST> invisibleSecondary = new ArrayList<>();
		List<ST> visibleSecondary = new ArrayList<>();
		for (Variable secondary: objectZClass.getSecondaryDeclarations()){
			AxiomReferences secondaryAxiomReferences = objectZClass.getStateAxiomReferences(secondary);
			ST secondaryST = getFunctionDeclaration(secondary, 
					secondaryAxiomReferences.stream()
					.map(a -> a.getAxiom().getPredicate())
					.collect(Collectors.toList()));
			ST secondaryVarST = getId(secondary.getId());
			ST result = getId(new Ident("result"));
			secondaryST = new StringTemplateSubstitutor().substituteIdentifier(secondaryST, secondaryVarST, result);
			if (objectZClass.isVisible(secondary.getId())){
				visibleSecondary.add(secondaryST);
			} else {
				invisibleSecondary.add(secondaryST);
			}
		}
		classDef.add("invisibleSecondary", invisibleSecondary);
		classDef.add("visibleSecondary", visibleSecondary);
	}

	private void addGettersAndSetters(ST classDef, ObjectZClass objectZClass) {
		List<ST> invisibleGetter = new ArrayList<>();
		List<ST> invisibleSetter = new ArrayList<>();
		
		List<ST> visibleGetter = new ArrayList<>();
		List<ST> visibleSetter = new ArrayList<>();
		
		for (Variable primary: objectZClass.getPrimaryDeclarations()){
			ST getterST = getGetter(primary.getId().getName());
			AxiomReferences stateAxiomReferences = objectZClass.getStateAxiomReferences(primary);
			ST setterST = getSetter(primary, stateAxiomReferences.stream().map(a -> a.getAxiom().getPredicate()).collect(Collectors.toList()));
			if (objectZClass.isVisible(primary.getId())){
				visibleGetter.add(getterST);
				visibleSetter.add(setterST);
			} else {
				invisibleGetter.add(getterST);
				invisibleSetter.add(setterST);
			}
		}
		for (Variable axiomVar: objectZClass.getAxiomaticDeclarations()){
			ST getterST = getGetter(axiomVar.getId().getName());
			if (objectZClass.isVisible(axiomVar.getId())){
				visibleGetter.add(getterST);
			} else {
				invisibleGetter.add(getterST);
			}
		}
		
		classDef.add("invisibleGetter", invisibleGetter);
		classDef.add("invisibleSetter", invisibleSetter);
		classDef.add("visibleGetter", visibleGetter);
		classDef.add("visibleSetter", visibleSetter);
	}

	private void addInitState(ST classDef, ObjectZClass objectZClass) {
		List<ST> initAxioms = new ArrayList<>();
		boolean redefine = false;
		if (objectZClass.getInheritedClasses().size() > 0){
			// TODO check that there really is an init function
			redefine = true;
		}
		// TODO add initAxioms of inherited classes
		initAxioms.addAll(objectZClass.getInitialAxiomReferences().stream().map(i -> i.getAxiom().getPredicate()).collect(Collectors.toList()));
		ST initState = null;
		if (!initAxioms.isEmpty()){
			initState = getInitState(redefine, initAxioms);
			classDef.add("initState", initState);
			if (objectZClass.isVisible(new Ident("INIT"))){
				classDef.add("initVisible", true);
			}
		}
	}

	private void addInvariants(ST classDef, ObjectZClass objectZClass) {
		List<ST> invariants = new ArrayList<>();
		invariants.addAll(objectZClass.getInvariants().stream().map(i -> getInvariant(i)).collect(Collectors.toList()));
		invariants.addAll(objectZClass.getAxiomaticReferences().stream().map(i -> getInvariant(i)).collect(Collectors.toList()));
		classDef.add("invariants", invariants);
	}

	private void addDeclarations(ST classDef, ObjectZClass objectZClass) {
		List<ST> declarations = new ArrayList<>();
		declarations.addAll(objectZClass.getPrimaryDeclarations().stream().map(v -> getDeclarationInClass(v)).collect(Collectors.toList()));
		declarations.addAll(objectZClass.getAxiomaticDeclarations().stream().map(v -> getDeclarationInClass(v)).collect(Collectors.toList()));
		classDef.add("declarations", declarations);
	}

	private void addInheritedClasses(ST classDef, ObjectZClass objectZClass) {
		if (!objectZClass.getInheritedClasses().isEmpty()){
			throw new ObjectZToPerfectTranslationException("Inheritance currently supported");
		}
//		List<ST> inheritedClasses = objectZClass.getInheritedClasses().stream().map(c -> c.getTemplate()).collect(Collectors.toList());
//		if (inheritedClasses.size() == 1){
//			ST inheritsST = getInherits(inheritedClasses);
//			classDef.add("inheritedClasses", inheritsST);
//		} else if (inheritedClasses.size() > 1){
//			throw new ObjectZToPerfectTranslationException("Multi inheritance is not supported by Perfect");
//		}
	}

	private void addFormalParameters(ST classDef, ObjectZClass objectZClass) {
		List<ST> formalParameters = objectZClass.getFormalParameters().stream().map(p -> getId(p)).collect(Collectors.toList());
		ST formalParameterST = null;
		if (formalParameters.size() == 1){
			formalParameterST = this.getGenericParameter(formalParameters.get(0));
		} else if (formalParameters.size() > 1){
			formalParameterST = this.getGenericParameters(formalParameters);
		}
		classDef.add("formalParams", formalParameterST);
	}
	private ST getFunctionDeclaration(Variable secondary, List<ST> postconditions) {
		ST functionDeclaration = definitions.getInstanceOf("functionDecl");
		functionDeclaration.add("name", secondary.getId().getName());
		functionDeclaration.add("type", secondary.getTypeTemplate());
		functionDeclaration.add("postconditions", postconditions);
		return functionDeclaration;
	}

	private ST getSetter(Variable v, List<ST> preconditions){
		ST setter = definitions.getInstanceOf("setter");
		setter.add("name", v.getId().getName());
		setter.add("type", v.getTypeTemplate());
		List<ST> inputPreconditions = new ArrayList<>();
		if (preconditions != null){
			StringTemplateCloner cloner = new StringTemplateCloner();
			StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();
			for (ST precondition: preconditions){
				ST clone = cloner.clone(precondition);
				substitutor.substituteIdentifier(clone, getId(v.getId()), getId(v.getId().getInputCopy()));
				inputPreconditions.add(clone);
			}
			
		}
		setter.add("preconditions", inputPreconditions);
		return setter;
	}
	
	private ST getGetter(String name) {
		ST getter = definitions.getInstanceOf("getter");
		getter.add("name", name);
		return getter;
	}

	private ST getInitState(boolean redefine, List<ST> axiomReferences){
		ST initState = definitions.getInstanceOf("initState");
		if (redefine){
			initState.add("redefine", "redefine ");
		} else {
			initState.add("redefine", "");
		}
		initState.add("axioms", axiomReferences);
		return initState;
	}
	
	private ST getDeclarationInClass(Variable v){
		ST vardec = definitions.getInstanceOf("vardec");
		vardec.add("id", getId(v.getId()));
		vardec.add("type", v.getTypeTemplate());
		return vardec;
	}
	
	private ST getInvariant(AxiomReference axiomReference){
		ST invariant = definitions.getInstanceOf("invariant");
		invariant.add("predicate", axiomReference.getAxiom().getPredicate());
		return invariant;
	}

	public ST getOpaque() {
		return operations.getInstanceOf("opaque");
	}
}
