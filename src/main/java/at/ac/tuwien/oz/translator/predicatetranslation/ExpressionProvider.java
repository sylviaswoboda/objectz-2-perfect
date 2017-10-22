package at.ac.tuwien.oz.translator.predicatetranslation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.parser.OZParser;
import at.ac.tuwien.oz.parser.OZParser.AdditiveExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.AttributeCallContext;
import at.ac.tuwien.oz.parser.OZParser.BagContext;
import at.ac.tuwien.oz.parser.OZParser.BinaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.CallContext;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;
import at.ac.tuwien.oz.parser.OZParser.CartesianContext;
import at.ac.tuwien.oz.parser.OZParser.CollectionOperationContext;
import at.ac.tuwien.oz.parser.OZParser.EmptyBagContext;
import at.ac.tuwien.oz.parser.OZParser.EmptySequenceContext;
import at.ac.tuwien.oz.parser.OZParser.EmptySetContext;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.FeatureOrNumberContext;
import at.ac.tuwien.oz.parser.OZParser.FormalClassReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.FormalParametersContext;
import at.ac.tuwien.oz.parser.OZParser.FunctionReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.GenActualsContext;
import at.ac.tuwien.oz.parser.OZParser.GeneralizedOperationContext;
import at.ac.tuwien.oz.parser.OZParser.GenericClassReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.IdContext;
import at.ac.tuwien.oz.parser.OZParser.IdOrNumberContext;
import at.ac.tuwien.oz.parser.OZParser.InfixComparisonOperationContext;
import at.ac.tuwien.oz.parser.OZParser.InfixRelationOperationContext;
import at.ac.tuwien.oz.parser.OZParser.MinMaxOfExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.MultiplicativeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.NumberContext;
import at.ac.tuwien.oz.parser.OZParser.ParenthesizedExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PolymorphicExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PowerSetExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PredefinedTypeContext;
import at.ac.tuwien.oz.parser.OZParser.PredefinedTypeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PrefixExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.RangeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaSumContext;
import at.ac.tuwien.oz.parser.OZParser.SelfContext;
import at.ac.tuwien.oz.parser.OZParser.SequenceContext;
import at.ac.tuwien.oz.parser.OZParser.SetAbstractionContext;
import at.ac.tuwien.oz.parser.OZParser.SetContext;
import at.ac.tuwien.oz.parser.OZParser.SuccessorContext;
import at.ac.tuwien.oz.parser.OZParser.TernaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.TupleContext;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;
import at.ac.tuwien.oz.translator.TypeEvaluator;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class ExpressionProvider {

	private ParseTreeProperty<ST> templateTree;
	private TypeEvaluator typeEvaluator;
	
	private PerfectTemplateProvider perfect;

	public ExpressionProvider(ParseTreeProperty<ST> templateTree, 
			TypeEvaluator typeEvaluator){
		this.templateTree = templateTree;
		this.typeEvaluator = typeEvaluator;
		this.perfect = PerfectTemplateProvider.getInstance();
	}
		
	public ST getPredefinedTypeST(PredefinedTypeContext ctx) {
		ST template = null;
				
		switch(ctx.op.getType()){
			case OZParser.NAT:
				template = perfect.getNat();
				break;
			case OZParser.PNAT:
				template = perfect.getPNat();
				break;
			case OZParser.INTEGER:
				template = perfect.getInt();
				break;
			case OZParser.BOOL:
				template = perfect.getBool();
				break;
			case OZParser.REAL:
				template = perfect.getReal();
				break;
			case OZParser.CHAR:
				template = perfect.getChar();
				break;
		}
		return template;
	}

	public ST getNumberST(NumberContext ctx) {
		return perfect.getNumber(ctx.type.getText());
	}

	public ST getId(IdContext ctx) {
		ST id = null;
		String originalDecoration = null;
		
		if (ctx.getChildCount() == 2){
			originalDecoration = ctx.DECORATION().getText();
		}
		Ident i = new Ident(ctx.ID().getText(), originalDecoration);
		id = perfect.getId(i);
		return id;
	}

	public ST getIdOrNumberST(IdOrNumberContext ctx) {
		// only propagate stringtemplate one level up
		ParserRuleContext idOrNumberNode = ctx.getChild(ParserRuleContext.class, 0);
		return templateTree.get(idOrNumberNode);
	}

	public ST getFeatureOrNumberST(FeatureOrNumberContext ctx) {
		return templateTree.get(ctx.idOrNumber());
	}
	
	public ST getSuccessorST(SuccessorContext ctx) {
		ST idOrNumber = templateTree.get(ctx.idOrNumber());
		ST successor = perfect.getSuccessor(idOrNumber);
		return successor;
	}

	public ST getAttributeCallST(AttributeCallContext ctx) {
		List<ST> idTemplates = new ArrayList<ST>();
		
		for (int i = 0; i < ctx.getChildCount(); i++){
			idTemplates.add(templateTree.get(ctx.id(i)));
		}

		ST attributeCall = perfect.getAttributeCall(idTemplates);
		return attributeCall;
	}

	public ST getFunctionReferenceST(FunctionReferenceContext ctx) {
		List<ST> params = new ArrayList<ST>();
		
		ExpressionType relationType = typeEvaluator.getType(ctx.id()); // could be a function or a relation
		ST relationName = templateTree.get(ctx.id());
		
		ST relationReference = null;
		
		if (relationType.isFunction()){
			for (int i = 0; i < ctx.getChildCount(); i++){
				params.add(templateTree.get(ctx.featureOrFunctionCall(i)));
			}

			relationReference = perfect.getFunctionReference(relationName, params);
		} else if (relationType.isRelation()){
			if (relationType.getSubExpressionSize() == 1){
				ExpressionType relationTuple = relationType.getSubExpressionType(0);
				ST relationTypeST = relationTuple.getTemplate();
				
				if (relationTuple.isPair()){
					ST left = templateTree.get(ctx.featureOrFunctionCall(0));
					ST right = templateTree.get(ctx.featureOrFunctionCall(1));
					relationReference = perfect.getBinaryRelationReference(relationTypeST, relationName, left, right);
				} else if (relationTuple.isTriple()){
					ST left = templateTree.get(ctx.featureOrFunctionCall(0));
					ST middle = templateTree.get(ctx.featureOrFunctionCall(1));
					ST right = templateTree.get(ctx.featureOrFunctionCall(2));
					relationReference = perfect.getTernaryRelationReference(relationTypeST, relationName, left, middle, right);
				}
			} else {
				throw new ObjectZToPerfectTranslationException("Invalid sub expression size of relation");
			}
			
		}
		return relationReference;
	}

	public ST getSelfST(SelfContext ctx){
		return perfect.getSelf();
	}

	private ST getTypeTemplate(ExpressionType t){
		if (t != null){
			return t.getTemplate();
		}
		return null;
		
	}
	
	public ST getEmptySequenceST(EmptySequenceContext ctx){
		ExpressionType elemType = typeEvaluator.getType(ctx);
		return perfect.getCollection(new ArrayList<>(), getTypeTemplate(elemType));
	}
	
	public ST getEmptyBagST(EmptyBagContext ctx){
		ExpressionType elemType = typeEvaluator.getType(ctx);
		return perfect.getCollection(new ArrayList<>(), getTypeTemplate(elemType));
	}

	public ST getEmptySetST(EmptySetContext ctx){
		ExpressionType elemType = typeEvaluator.getType(ctx);
		return perfect.getCollection(new ArrayList<>(), getTypeTemplate(elemType));
	}

	public ST getSequenceST(SequenceContext ctx){
		// [a, b, c] => seq of nat {a, b, c}
		List<ST>elements = getTemplatesOfSubexpressions(ctx.expression());
		ExpressionType elemType = typeEvaluator.getType(ctx);
		return perfect.getCollection(elements, getTypeTemplate(elemType));
	}

	public ST getBagST(BagContext ctx){
		List<ST>elements = getTemplatesOfSubexpressions(ctx.expression());
		ExpressionType elemType = typeEvaluator.getType(ctx);
		ST bag = perfect.getCollection(elements, getTypeTemplate(elemType));
		return bag;
	}

	public ST getSetST(SetContext ctx){
		List<ST>elements = getTemplatesOfSubexpressions(ctx.expression());
		ExpressionType elemType = typeEvaluator.getType(ctx);
		ST set = perfect.getCollection(elements, getTypeTemplate(elemType));
		return set;
	}

	private List<ST> getTemplatesOfSubexpressions(List<ExpressionContext> ctx) {
		List<ST>elements = new ArrayList<ST>();
		for(ExpressionContext exprCtx: ctx){
			if (exprCtx != null){
				elements.add(templateTree.get(exprCtx));
			}
		}
		return elements;
	}
	
	public ST getTupleST(TupleContext ctx){
		List<ST> elements = getTemplatesOfSubexpressions(ctx.expression());

		int tupleSize = elements.size();
		
		if (tupleSize < 2 || tupleSize > 3){
			throw new ObjectZToPerfectTranslationException("Mapping of tuples only available for pairs and triples");
		}
		
		ST tuple = null;
		
		ExpressionType tupleType = typeEvaluator.getType(ctx);
		
		ST typeTemplate = getTypeTemplate(tupleType);
		tuple = perfect.getTuple(elements, typeTemplate);
		
//		if (tupleSize == 2){
//			Type leftType = tupleType.getSubType(0);
//			Type rightType = tupleType.getSubType(1);
//			tuple = perfect.getPair(getTypeTemplate(leftType), getTypeTemplate(rightType), elements.get(0), elements.get(1));
//		} else if (tupleSize == 3){
//			Type leftType = tupleType.getSubType(0);
//			Type middleType = tupleType.getSubType(1);
//			Type rightType = tupleType.getSubType(2);
//			tuple = perfect.getTriple(getTypeTemplate(leftType), getTypeTemplate(middleType), getTypeTemplate(rightType), 
//					elements.get(0), elements.get(1), elements.get(2));
//		}
		return tuple;
		
	}
	
	public ST getParenthesizedExpressionST(ParenthesizedExpressionContext ctx){
		ST innerExpr = templateTree.get(ctx.expression());
		ST parenthesized = perfect.getParenthesized(innerExpr);
		
		return parenthesized;
	}
	
	public ST getCallST(CallContext ctx){
		// only propagate stringtemplate one level up
		return templateTree.get(ctx.featureOrFunctionCall());
	}

	public ST getMinMaxOfExpressionST(MinMaxOfExpressionContext ctx) {
		ST innerExpression = templateTree.get(ctx.e);
		ST minMaxOfExpression = null;
		
		switch(ctx.op.getType()){
			case OZParser.MIN:
				minMaxOfExpression = perfect.getMin(innerExpression);
				break;
			case OZParser.MAX:
				minMaxOfExpression = perfect.getMax(innerExpression);
				break;
		}

		return minMaxOfExpression;
	}

	public ST getPredefinedTypeExpressionST(PredefinedTypeExpressionContext ctx) {
		// only propagate stringtemplate one level up
		return templateTree.get(ctx.p);
	}
	
	public ST getPrefixExpressionST(PrefixExpressionContext ctx) {
		ST prefixedExpression = templateTree.get(ctx.expression());
		ST prefixTemplate = null;

		switch(ctx.prefix().op.getType()){
			case OZParser.MINUS:
				prefixTemplate = perfect.getMinus(prefixedExpression);
				break;
			case OZParser.COUNT:
				prefixTemplate = perfect.getCount(prefixedExpression);
				break;
			case OZParser.RAN:
				prefixTemplate = perfect.getRan(prefixedExpression);
				break;
			case OZParser.DOM:
				prefixTemplate = perfect.getDom(prefixedExpression);
				break;
			case OZParser.TAIL:
				prefixTemplate = perfect.getTail(prefixedExpression);
				break;
			case OZParser.HEAD:
				prefixTemplate = perfect.getHead(prefixedExpression);
				break;
			case OZParser.REV:
				prefixTemplate = perfect.getRev(prefixedExpression);
				break;
			case OZParser.LAST:
				prefixTemplate = perfect.getLast(prefixedExpression);
				break;
			case OZParser.FRONT:
				prefixTemplate = perfect.getFront(prefixedExpression);
				break;
			case OZParser.ITEMS:
				prefixTemplate = perfect.getItems(prefixedExpression);
				break;
		}
		return prefixTemplate;
	}
	
	public ST getGeneralizedOperationST(GeneralizedOperationContext ctx){
		ST innerExpression = templateTree.get(ctx.expression());
		ST expression = null;
		
		switch(ctx.op.getType()){
			case OZParser.UNION:
				expression = perfect.getGeneralizedUnion(innerExpression);
				break;

			case OZParser.INTERSECT:
				expression = perfect.getGeneralizedIntersection(innerExpression);
				break;

			case OZParser.CONCATENATE:
				expression = perfect.getDistributedConcatenation(innerExpression);
				break;
		}
		return expression;
	}
	
	public ST getBinaryRelationST(BinaryRelationContext ctx){
		ST leftExpression = templateTree.get(ctx.left);
		ST rightExpression = templateTree.get(ctx.right);
		ST binaryRelation = perfect.getBinaryRelation(leftExpression, rightExpression);
		return binaryRelation;
	}
	
	public ST getTernaryRelationST(TernaryRelationContext ctx){
		ST leftExpression = templateTree.get(ctx.left);
		ST middleExpression = templateTree.get(ctx.middle);
		ST rightExpression = templateTree.get(ctx.right);
		ST ternaryRelation = perfect.getTernaryRelation(leftExpression, middleExpression, rightExpression);
		return ternaryRelation;
	}
	
	public ST getInfixRelationOperationST(InfixRelationOperationContext ctx){
		ExpressionContext leftCtx = ctx.left;
		ExpressionContext rightCtx = ctx.right;
		ST leftExpression = templateTree.get(leftCtx);
		ST rightExpression = templateTree.get(rightCtx);

		int opType = ctx.infixRelationOp().op.getType();

		if (opType == OZParser.DOM_RESTR || opType == OZParser.RAN_RESTR || opType == OZParser.DOM_AR || opType == OZParser.RAN_AR){
			ExpressionType type = typeEvaluator.getType(ctx);
			ExpressionType subTypeLeft = null;
			ExpressionType subTypeRight = null;
			if (type.isRelation() || type.isFunction()){
				subTypeLeft = type.getSubExpressionType(0);
				subTypeRight = type.getSubExpressionType(1);
			}
			
			if (opType == OZParser.DOM_RESTR){
				ST restrictingSet = leftExpression;
				ST mapOrRelation = rightExpression;
				if (typeEvaluator.isMap(ctx)){
					return perfect.getDomainRestriction(getTypeTemplate(subTypeLeft), getTypeTemplate(subTypeRight), mapOrRelation, restrictingSet, true);
				} else if (typeEvaluator.isRelation(ctx)){
					return perfect.getDomainRestriction(null, null, mapOrRelation, restrictingSet, false);
				} else {
					throw new ObjectZToPerfectTranslationException("Domain restriction only allowed for maps/functions or relations ");
				}
			} else if (opType == OZParser.RAN_RESTR){
				ST restrictingSet = rightExpression;
				ST mapOrRelation = leftExpression;
				if (typeEvaluator.isMap(ctx)){
					return perfect.getRangeRestriction(getTypeTemplate(subTypeLeft), getTypeTemplate(subTypeRight), mapOrRelation, restrictingSet, true);
				} else if (typeEvaluator.isRelation(ctx)){
					return perfect.getRangeRestriction(null, null, mapOrRelation, restrictingSet, false);
				} else {
					throw new ObjectZToPerfectTranslationException("Domain restriction only allowed for maps/functions or relations ");
				}
			} else if (opType == OZParser.DOM_AR){
				ST restrictingSet = leftExpression;
				ST map = rightExpression;
				if (typeEvaluator.isMap(ctx)){
					return perfect.getDomainAntiRestriction(getTypeTemplate(subTypeLeft), getTypeTemplate(subTypeRight), map, restrictingSet, true);
				} else if (typeEvaluator.isRelation(ctx)){
					return perfect.getDomainAntiRestriction(null, null, map, restrictingSet, false);
				} else {
					throw new ObjectZToPerfectTranslationException("Domain restriction only allowed for maps/functions or relations ");
				}
			} else if (opType == OZParser.RAN_AR){
				ST restrictingSet = rightExpression;
				ST map = leftExpression;
				if (typeEvaluator.isMap(ctx)){
					return perfect.getRangeAntiRestriction(getTypeTemplate(subTypeLeft), getTypeTemplate(subTypeRight), map, restrictingSet, true);
				} else if (typeEvaluator.isRelation(ctx)){
					return perfect.getRangeAntiRestriction(null, null, map, restrictingSet, false);
				} else {
					throw new ObjectZToPerfectTranslationException("Domain restriction only allowed for maps/functions or relations ");
				}
			}
		} else if (opType == OZParser.MAPLET){
			ExpressionType type = typeEvaluator.getType(ctx);
			if (type.isMaplet()){
				ExpressionType subTypeLeft = type.getSubExpressionType(0);
				ExpressionType subTypeRight = type.getSubExpressionType(1);
			
				return perfect.getPair(subTypeLeft.getTemplate(), subTypeRight.getTemplate(), leftExpression, rightExpression);
			} else {
				throw new ObjectZToPerfectTranslationException("Maplet operation can only translated for expressions of type Maplet.");
			}
		} else {
			ST map = perfect.getMap(leftExpression, rightExpression);
			return map;
		}
		
		return null;
		
		
	}
	
	public ST getInfixComparisonOperationST(InfixComparisonOperationContext ctx){
		ExpressionContext leftCtx = ctx.left;
		ExpressionContext rightCtx = ctx.right;
		ST leftExpression = templateTree.get(leftCtx);
		ST rightExpression = templateTree.get(rightCtx);

		ST resultExpression = null;
		
		switch(ctx.infixComparisonOp().op.getType()){
			case OZParser.EQUALS:
				resultExpression = perfect.getEquals(leftExpression, rightExpression);
				break;

			case OZParser.NEQUALS:
				resultExpression = perfect.getNotEquals(leftExpression, rightExpression);
				break;

			case OZParser.ELEM:
				resultExpression = perfect.getElem(leftExpression, rightExpression);
				break;

			case OZParser.NELEM:
				resultExpression = perfect.getNotElem(leftExpression, rightExpression);
				break;

			case OZParser.SUBSET:
				resultExpression = perfect.getSubset(leftExpression, rightExpression);
				break;

			case OZParser.STR_SUBSET:
				resultExpression = perfect.getStrictSubset(leftExpression, rightExpression);
				break;

			case OZParser.LT:
				resultExpression = perfect.getLessThan(leftExpression, rightExpression);
				break;

			case OZParser.LTE:
				resultExpression = perfect.getLessThanEquals(leftExpression, rightExpression);
				break;

			case OZParser.GT:
				resultExpression = perfect.getGreaterThan(leftExpression, rightExpression);
				break;

			case OZParser.GTE:
				resultExpression = perfect.getGreaterThanEquals(leftExpression, rightExpression);
				break;

			case OZParser.PREFIX:
				resultExpression = perfect.getPrefix(leftExpression, rightExpression);
				break;

			case OZParser.SUFFIX:
				resultExpression = perfect.getSuffix(leftExpression, rightExpression);
				break;

			case OZParser.IN_SEQ:
				resultExpression = perfect.getInSequence(leftExpression, rightExpression);
				break;

			case OZParser.IN_BAG:
				resultExpression = perfect.getInBag(leftExpression, rightExpression);
				break;

			case OZParser.SUBBAG:
				resultExpression = perfect.getSubBag(leftExpression, rightExpression);
				break;
				
		}
		
		
		return resultExpression;
	}
	
	public ST getCollectionOperationST(CollectionOperationContext ctx){
		ExpressionContext leftCtx = ctx.left;
		ExpressionContext rightCtx = ctx.right;
		ST leftExpression = templateTree.get(leftCtx);
		ST rightExpression = templateTree.get(rightCtx);
		
		ST resultExpression = null;
		
		switch(ctx.setOp().op.getType()){
			case OZParser.UNION:
				resultExpression = perfect.getUnion(leftExpression, rightExpression);
				break;
			case OZParser.DIFFERENCE:
				resultExpression = perfect.getDifference(leftExpression, rightExpression);
				break;
			case OZParser.INTERSECT:
				resultExpression = perfect.getIntersection(leftExpression, rightExpression);
				break;
			case OZParser.CONCATENATE:
				resultExpression = perfect.getConcatenation(leftExpression, rightExpression);
				break;
			case OZParser.OVERRIDE:
				if (typeEvaluator.isMap(ctx)){
					ExpressionType type = typeEvaluator.getType(ctx);
					ExpressionType subTypeLeft = type.getSubExpressionType(0);
					ExpressionType subTypeRight = type.getSubExpressionType(1);
					resultExpression = perfect.getOverride(subTypeLeft.getTemplate(), subTypeRight.getTemplate(), leftExpression, rightExpression, true);
				} else if (typeEvaluator.isRelation(ctx)){
					resultExpression = perfect.getOverride(null, null, leftExpression, rightExpression, false);
				} else {
					throw new ObjectZToPerfectTranslationException("Override is only possible on relation and function/maplet operands");
				}
				break;
			case OZParser.EXTRACT:
				resultExpression = perfect.getExtract(rightExpression, leftExpression);
				break;
			case OZParser.FILTER:
				resultExpression = perfect.getFiltering(leftExpression, rightExpression);
				break;
			case OZParser.MULTIPLICITY:
				resultExpression = perfect.getMultiplicity(leftExpression, rightExpression);
				break;
			case OZParser.SCALING:
				resultExpression = perfect.getScaling(rightExpression, leftExpression);
				break;
			case OZParser.BAG_UNION:
				resultExpression = perfect.getBagUnion(leftExpression, rightExpression);
				break;
			case OZParser.BAG_DIFFERENCE:
				resultExpression = perfect.getBagDifference(leftExpression, rightExpression);
				break;
		}
		return resultExpression;
	}
	
	public ST getRangeExpressionST(RangeExpressionContext ctx){
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);
		ST range = perfect.getRange(left, right);
		return range;
	}
	
	public ST getAdditiveExpressionST(AdditiveExpressionContext ctx){
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);

		ST additive = null;
		
		switch(ctx.op.getType()){
			case OZParser.PLUS:
				additive = perfect.getAddition(left, right);
				break;
			case OZParser.MINUS:
				additive = perfect.getSubtraction(left, right);
				break;
		}
		return additive;
	}

	public ST getMultiplicativeExpressionST(MultiplicativeExpressionContext ctx){
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);
		
		ST multiplicative = null;
		
		switch(ctx.op.getType()){
			case OZParser.MULT:
				multiplicative = perfect.getMultiplication(left, right);
				break;
			case OZParser.DIV:
				multiplicative = perfect.getDivision(left, right);
				break;
			case OZParser.INT_DIV:
				multiplicative = perfect.getIntegerDivision(left, right);
				break;
			case OZParser.MOD:
				multiplicative = perfect.getModulo(left, right);
				break;
		}
		return multiplicative;
	}
	
	public ST getPowerSetExpressionST(PowerSetExpressionContext ctx){
		ST innerExpression = templateTree.get(ctx.expression());
		
		ST powerSetExpression = null;
		
		switch(ctx.powerSetOp().op.getType()){
			case OZParser.POWER:
				powerSetExpression = perfect.getSetDefinition(innerExpression);
				break;
			case OZParser.POWER1:
				powerSetExpression = perfect.getSetDefinition(innerExpression);
				break;
			case OZParser.FINITE:
				powerSetExpression = perfect.getSetDefinition(innerExpression);
				break;
			case OZParser.FINITE1:
				powerSetExpression = perfect.getSetDefinition(innerExpression);
				break;
			case OZParser.SEQ:
				powerSetExpression = perfect.getSequenceDefinition(innerExpression);
				break;
			case OZParser.SEQ1:
				powerSetExpression = perfect.getSequenceDefinition(innerExpression);
				break;
			case OZParser.ISEQ:
				powerSetExpression = perfect.getSequenceDefinition(innerExpression);
				break;
			case OZParser.BAG:
				powerSetExpression = perfect.getBagDefinition(innerExpression);
				break;
		}
		return powerSetExpression;
	}
	
	public ST getCartesianST(CartesianContext ctx) {
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);
		ST cartesian = perfect.getCartesian(left, right);
		
		return cartesian;
	}
	
	public ST getGenericClassReferenceST(GenericClassReferenceContext ctx){
		ST id = templateTree.get(ctx.id());
		ST genActuals = templateTree.get(ctx.genActuals());
		ST genericClassReference = perfect.getGenericClassReference(id, genActuals);
		return genericClassReference;
	}

	public ST getFormalClassReferenceST(FormalClassReferenceContext ctx){
		ST id = templateTree.get(ctx.id());
		ST formalParameters = templateTree.get(ctx.formalParameters());
		ST genericClassReference = perfect.getGenericClassReference(id, formalParameters);
		return genericClassReference;
	}
	
	public ST getPolymorphicExpressionST(PolymorphicExpressionContext ctx){
		ST className = perfect.getId(new Ident(ctx.ID().getText()));
		GenActualsContext genActualsCtx = ctx.genActuals();
		ST genericParameters = null;
		
		if (genActualsCtx != null){
			genericParameters = templateTree.get(genActualsCtx);
		}

		ST polymorphicExpression = perfect.getPolymorphicExpression(className, genericParameters);
		return polymorphicExpression;
	}
	
	public ST getSetAbstractionST(SetAbstractionContext ctx){
		// TODO implement and improve part in thesis
		return null;
	}
	
	public ST getSchemaSumST(SchemaSumContext ctx){
		// TODO implement and improve part in thesis
		return null;
	}
	
	public ST getGenActualsST(GenActualsContext ctx){
		List<ST> exprs = getTemplatesOfSubexpressions(ctx.expression());
		return getGenericParameters(ctx, exprs);
	}
	
	public ST getFormalParametersST(FormalParametersContext ctx){
		List<ST> ids = new ArrayList<ST>();
		
		for (TerminalNode id: ctx.ID()){
			ids.add(perfect.getId(new Ident(id.getText())));
		}
		return getGenericParameters(ctx, ids);
	}

	private ST getGenericParameters(ParserRuleContext ctx, List<ST> exprs) {
		ST genericParameters = null;
		
		if (exprs.size() == 1){
			genericParameters = perfect.getGenericParameter(exprs.get(0));
		} else {
			genericParameters = perfect.getGenericParameters(exprs);
		}
		return genericParameters;
	}

	public ST getCallerST(CallerContext ctx) {
		List<ST> ids = new ArrayList<ST>();
		
		for(IdContext id: ctx.id()){
			ids.add(templateTree.get(id));
		}
		return perfect.getAttributeCall(ids);
	}

}
