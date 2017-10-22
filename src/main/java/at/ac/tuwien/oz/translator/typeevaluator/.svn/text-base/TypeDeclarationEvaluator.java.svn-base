package at.ac.tuwien.oz.translator.typeevaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Type;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.definitions.IDefinition;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.Operation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.parser.OZBaseListener;
import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;
import at.ac.tuwien.oz.parser.OZParser.AdditiveExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.AttributeCallContext;
import at.ac.tuwien.oz.parser.OZParser.BagContext;
import at.ac.tuwien.oz.parser.OZParser.BinaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.CallContext;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;
import at.ac.tuwien.oz.parser.OZParser.CartesianContext;
import at.ac.tuwien.oz.parser.OZParser.ClassDefinitionContext;
import at.ac.tuwien.oz.parser.OZParser.CollectionOperationContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationContext;
import at.ac.tuwien.oz.parser.OZParser.EmptyBagContext;
import at.ac.tuwien.oz.parser.OZParser.EmptySequenceContext;
import at.ac.tuwien.oz.parser.OZParser.EmptySetContext;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.FeatureOrNumberContext;
import at.ac.tuwien.oz.parser.OZParser.FormalClassReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.FunctionReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.GeneralizedOperationContext;
import at.ac.tuwien.oz.parser.OZParser.GenericClassReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.IdContext;
import at.ac.tuwien.oz.parser.OZParser.IdOrNumberContext;
import at.ac.tuwien.oz.parser.OZParser.InfixComparisonOperationContext;
import at.ac.tuwien.oz.parser.OZParser.InfixRelationOpContext;
import at.ac.tuwien.oz.parser.OZParser.InfixRelationOperationContext;
import at.ac.tuwien.oz.parser.OZParser.MinMaxOfExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.MultiplicativeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.NumberContext;
import at.ac.tuwien.oz.parser.OZParser.OperationExpressionDefContext;
import at.ac.tuwien.oz.parser.OZParser.OperationSchemaDefContext;
import at.ac.tuwien.oz.parser.OZParser.ParenthesizedExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PolymorphicExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PostfixExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PowerSetExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PowerSetOpContext;
import at.ac.tuwien.oz.parser.OZParser.PredefinedTypeContext;
import at.ac.tuwien.oz.parser.OZParser.PredefinedTypeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PrefixContext;
import at.ac.tuwien.oz.parser.OZParser.PrefixExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.RangeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaSumContext;
import at.ac.tuwien.oz.parser.OZParser.ScopeEnrichmentContext;
import at.ac.tuwien.oz.parser.OZParser.SelfContext;
import at.ac.tuwien.oz.parser.OZParser.SequenceContext;
import at.ac.tuwien.oz.parser.OZParser.SetAbstractionContext;
import at.ac.tuwien.oz.parser.OZParser.SetContext;
import at.ac.tuwien.oz.parser.OZParser.SetOpContext;
import at.ac.tuwien.oz.parser.OZParser.SuccessorContext;
import at.ac.tuwien.oz.parser.OZParser.TernaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.TupleContext;
import at.ac.tuwien.oz.scopes.LocalScope;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class TypeDeclarationEvaluator extends OZBaseListener{
	
	private Logger logger;
	
	//input
	private ObjectZDefinition definition;
	
	private ParseTreeProperty<Variable> declarationTree = new ParseTreeProperty<Variable>();
	private ParseTreeProperty<LocalScope> localScopeTree = new ParseTreeProperty<LocalScope>();
	
	//output
	private ParseTreeProperty<ExpressionType> expressionTypeTree = new ParseTreeProperty<ExpressionType>();

	//local
	private ObjectZClass currentClass;
	private Operation currentOperation;
	private LocalScope localScope = null;
	
	public TypeDeclarationEvaluator(ObjectZDefinition definition, ParseTreeProperty<Variable> declarationTree, ParseTreeProperty<LocalScope> localScopeTree){
		this.logger = Logger.getLogger(this.getClass().getName());
		this.definition = definition;
		this.declarationTree = declarationTree;
		this.localScopeTree = localScopeTree;
	}
	
	public ParseTreeProperty<ExpressionType> getExpressionTypeTree(){
		return this.expressionTypeTree;
	}

	
	@Override
	public void enterClassDefinition(ClassDefinitionContext ctx) {
		String className = ctx.ID().getText();
		currentClass = definition.resolveClass(new Ident(className));
	}
	
	@Override
	public void exitClassDefinition(ClassDefinitionContext ctx) {
		this.currentClass = null;
	}
	
	@Override
	public void enterOperationExpressionDef(OperationExpressionDefContext ctx) {
		String operationName = ctx.ID().getText();
		currentOperation = currentClass.resolveOperation(new Ident(operationName));
	}
	
	@Override
	public void exitOperationExpressionDef(OperationExpressionDefContext ctx) {
		currentOperation = null;
	}
	
	@Override
	public void enterOperationSchemaDef(OperationSchemaDefContext ctx) {
		String operationName = ctx.ID().getText();
		currentOperation = currentClass.resolveOperation(new Ident(operationName));
	}
	
	@Override
	public void exitOperationSchemaDef(OperationSchemaDefContext ctx) {
		currentOperation = null;
	}
	
	@Override
	public void exitDeclaration(DeclarationContext ctx) {
		for(IdContext idCtx: ctx.declarationNameList().id()){
			Variable var = declarationTree.get(idCtx);
			ExpressionContext exprCtx = ctx.expression();
			
			ExpressionType expressionType = expressionTypeTree.get(exprCtx);
			var.setExpressionType(expressionType);
			expressionTypeTree.put(ctx, expressionType);

		}
	}
	
	@Override
	public void exitPredefinedType(PredefinedTypeContext ctx) {
		ExpressionType newType = null;
		
		if (ctx.op == null){
			throw new ObjectZToPerfectTranslationException("predefined type recognized but is null");
		}
		
		switch(ctx.op.getType()){
			case OZParser.NAT: newType = ExpressionType.getNat(); break;
			case OZParser.PNAT: newType = ExpressionType.getNat(); break;
			case OZParser.INTEGER: newType = ExpressionType.getInt(); break;
			case OZParser.BOOL: newType = ExpressionType.getBool(); break;
			case OZParser.REAL: newType = ExpressionType.getReal(); break;
			case OZParser.CHAR: newType = ExpressionType.getChar(); break;
		}
//		typeTree.put(ctx, newType.getEffectiveType());
		expressionTypeTree.put(ctx, newType);
	}
	
	@Override
	public void exitCall(CallContext ctx) {
//		typeTree.put(ctx, typeTree.get(ctx.featureOrFunctionCall()));
		ExpressionType expressionType = expressionTypeTree.get(ctx.featureOrFunctionCall());
		expressionTypeTree.put(ctx, expressionType);
	}
	
	@Override
	public void exitFeatureOrNumber(FeatureOrNumberContext ctx) {
//		typeTree.put(ctx, typeTree.get(ctx.idOrNumber()));
		expressionTypeTree.put(ctx, expressionTypeTree.get(ctx.idOrNumber()));
	}
	
	@Override
	public void exitAttributeCall(AttributeCallContext ctx) {
		List<IdContext> idList = ctx.id();
//		Type newType = extractTypeFromIdList(idList);
//		typeTree.put(ctx, newType);
		
		ExpressionType newExprType = extractExpressionTypeFromIdList(ctx, idList);
		expressionTypeTree.put(ctx, newExprType);
	}
	
	private ExpressionType extractExpressionTypeFromIdList(ParseTree ctx, List<IdContext> idList) {
		ExpressionType newType = null;
		ExpressionType enclosingType = null;
		
		for (IdContext idCtx: idList){
			Ident id = extractId(idCtx);
			newType = resolveExpressionType(ctx, id, enclosingType);
			enclosingType = newType;
		}
		return newType;
	}
	
	private ExpressionType resolveExpressionType(ParseTree ctx, Ident id, ExpressionType enclosingType) {
		if(enclosingType == null){
			return resolveExpressionType(ctx, id);
		} else if(enclosingType.isUserDefinedType()){
			ObjectZClass theClass = definition.resolveClass(enclosingType.getEffectiveTypeId());
			IDefinition d = theClass.resolve(id);
			ExpressionType newType = d.getExpressionType();
			return newType;
		} else if (enclosingType.isCollectionType()){
			ObjectZClass theClass = definition.resolveClass(enclosingType.getBaseTypeId());
			IDefinition d = theClass.resolve(id);
			ExpressionType newType = d.getExpressionType();
			return newType;
		}
		return null;
	}

	private ExpressionType resolveExpressionType(ParseTree ctx, Ident id) {
		IDefinition typeSymbol = null;
		
		if (localScope != null){
			typeSymbol = localScope.resolve(id);
		}
		
		if (typeSymbol == null && currentOperation != null){
			typeSymbol = currentOperation.resolve(id);
		}
		
		if (typeSymbol == null && currentClass != null){
			typeSymbol = currentClass.resolve(id);
		}
		
		if (typeSymbol == null && definition != null){
			typeSymbol = definition.resolve(id);
		}
		
		if (typeSymbol == null){
			return null;
		} else {
			ExpressionType resultExpression = typeSymbol.getExpressionType();
			if (resultExpression == null){
				return resultExpression;
			}
			if (typeSymbol.isVariable() && (resultExpression.isCollectionType() ||resultExpression.isFunction())){
				return ExpressionType.getVariableType(ctx, resultExpression);
			} else {
				return resultExpression;
			}
		}
	}


	private Ident extractId(IdContext ctx) {
		Ident id;
		if (ctx.DECORATION() != null){
			id = new Ident(ctx.ID().getText(), ctx.DECORATION().getText());
		} else {
			id = new Ident(ctx.ID().getText());
		}
		return id;
	}

	@Override
	public void exitSuccessor(SuccessorContext ctx) {
		ExpressionType newExpressionType = expressionTypeTree.get(ctx.idOrNumber());
		if (newExpressionType.isNumber()){
			expressionTypeTree.put(ctx, newExpressionType);
		} else {
			throw new ObjectZToPerfectTranslationException("Calculating Succesor of non number type value is not allowed");
		}
	}
	
	@Override
	public void exitFunctionReference(FunctionReferenceContext ctx) {
		ExpressionType functionExpressionType = expressionTypeTree.get(ctx.id());
		
		if (functionExpressionType.isFunction()){
			expressionTypeTree.put(ctx, functionExpressionType);
		} else if (functionExpressionType.isRelation()){
			expressionTypeTree.put(ctx, ExpressionType.getBool());
		} else {
			throw new ObjectZToPerfectTranslationException("Cannot find type of function (or their subtypes)");
		}
	}
	
	@Override
	public void exitIdOrNumber(IdOrNumberContext ctx) {
		if (ctx.id() != null){
			ExpressionType expressionType = expressionTypeTree.get(ctx.id());
			expressionTypeTree.put(ctx, expressionType);
		} else if (ctx.number() != null){
			expressionTypeTree.put(ctx, expressionTypeTree.get(ctx.number()));
		}
	}
	
	@Override
	public void exitId(IdContext ctx) {
 		Ident id = extractId(ctx);
 		
		ExpressionType resolvedType = resolveExpressionType(ctx, id);
		expressionTypeTree.put(ctx,  resolvedType);
	}

	@Override
	public void exitNumber(NumberContext ctx) {
		if (ctx.type.getType() == OZLexer.INT){
			expressionTypeTree.put(ctx, ExpressionType.getNat());
		} else if (ctx.type.getType() == OZLexer.FLOAT){
			expressionTypeTree.put(ctx, ExpressionType.getReal());
		}
	}
	
	@Override
	public void exitCartesian(CartesianContext ctx){
		ExpressionType leftExpressionType = expressionTypeTree.get(ctx.left);
		ExpressionType rightExpressionType = expressionTypeTree.get(ctx.right);
		ExpressionType newExpressionType = ExpressionType.getTuple(Arrays.asList(leftExpressionType, rightExpressionType));
		expressionTypeTree.put(ctx, newExpressionType);
	}
	
	@Override
	public void exitPowerSetExpression(PowerSetExpressionContext ctx) {
		PowerSetOpContext powerSetOpCtx = ctx.powerSetOp();
		ExpressionType innerExpressionType = expressionTypeTree.get(ctx.expression());
		
		ExpressionType newExpressionType = null;
		
		int opType = powerSetOpCtx.op.getType();
		
		if (opType == OZLexer.POWER || opType == OZLexer.POWER1 || opType == OZLexer.FINITE || opType == OZLexer.FINITE1){
			newExpressionType = ExpressionType.getSet(innerExpressionType);
		} else if (opType == OZLexer.SEQ || opType == OZLexer.SEQ1 || opType == OZLexer.ISEQ){
			newExpressionType = ExpressionType.getSequence(innerExpressionType);
		} else if (opType == OZLexer.BAG){
			newExpressionType = ExpressionType.getBag(innerExpressionType);
		} else {
			throw new ObjectZToPerfectTranslationException("Unknown power set expression operator");
		}
		
		expressionTypeTree.put(ctx, newExpressionType);
	}
	
	@Override
	public void exitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
		ExpressionType leftExpressionType = expressionTypeTree.get(ctx.left);
		ExpressionType rightExpressionType = expressionTypeTree.get(ctx.right);
		
		calculateTypeOfBinaryAlgebraicExpression(ctx, leftExpressionType, rightExpressionType);
	}
	@Override
	public void exitAdditiveExpression(AdditiveExpressionContext ctx) {
		ExpressionType leftExpressionType = expressionTypeTree.get(ctx.left);
		ExpressionType rightExpressionType = expressionTypeTree.get(ctx.right);
		
		calculateTypeOfBinaryAlgebraicExpression(ctx, leftExpressionType, rightExpressionType);
	}
	
	@Override
	public void exitRangeExpression(RangeExpressionContext ctx) {
		ExpressionType leftExpressionType = expressionTypeTree.get(ctx.left);
		ExpressionType rightExpressionType = expressionTypeTree.get(ctx.right);
		
		if (!leftExpressionType.isNumber() || !rightExpressionType.isNumber()){
			throw new ObjectZToPerfectTranslationException("Invalid type for range operation");
		}
		
		ExpressionType newType = null;
		if (leftExpressionType.isInt() || rightExpressionType.isInt()){
			newType = ExpressionType.getSet(ExpressionType.getInt());
		} else {
			newType = ExpressionType.getSet(ExpressionType.getNat());
		}
		expressionTypeTree.put(ctx, newType);
	}
	
	@Override
	public void exitCollectionOperation(CollectionOperationContext ctx) {
		ExpressionType leftExpressionType = expressionTypeTree.get(ctx.left);
		ExpressionType rightExpressionType = expressionTypeTree.get(ctx.right);
		
		SetOpContext opCtx = ctx.setOp();
		
		int opType = opCtx.op.getType();
		
		if (opType == OZLexer.UNION || opType == OZLexer.DIFFERENCE || opType == OZLexer.INTERSECT){
			if (!(leftExpressionType.isSet() || leftExpressionType.isRelation()) || !(rightExpressionType.isSet() || rightExpressionType.isRelation())){
				logger.log(Level.SEVERE, "Invalid type for set operation at input '" + ctx.getText() +"'");
				throw new ObjectZToPerfectTranslationException("Invalid type for set operation");
			}
			Type effectiveLeftType = leftExpressionType.getEffectiveType();
			Type effectiveRightType = rightExpressionType.getEffectiveType();
			
			ExpressionType subExpressionType = determineSubTypesOfCollection(effectiveLeftType.getSubExpressionTypes(), effectiveRightType.getSubExpressionTypes());
			ExpressionType newType = ExpressionType.getSetConstruction(ctx, subExpressionType);
			expressionTypeTree.put(ctx, newType);
		} else if (opType == OZLexer.CONCATENATE){
			if (!leftExpressionType.isSeq() || !rightExpressionType.isSeq()){
				throw new ObjectZToPerfectTranslationException("Invalid type for seq operation");
			}
			Type effectiveLeftType = leftExpressionType.getEffectiveType();
			Type effectiveRightType = rightExpressionType.getEffectiveType();
			
			ExpressionType subExpressionType = determineSubTypesOfCollection(effectiveLeftType.getSubExpressionTypes(), effectiveRightType.getSubExpressionTypes());
			ExpressionType newType = ExpressionType.getSequenceConstruction(ctx, subExpressionType);
			expressionTypeTree.put(ctx, newType);
		} else if (opType == OZLexer.BAG_UNION || opType == OZLexer.BAG_DIFFERENCE){
			if (!leftExpressionType.isBag() || !rightExpressionType.isBag()){
				throw new ObjectZToPerfectTranslationException("Invalid type for seq operation");
			}
			Type effectiveLeftType = leftExpressionType.getEffectiveType();
			Type effectiveRightType = rightExpressionType.getEffectiveType();
			
			ExpressionType subExpressionType = determineSubTypesOfCollection(effectiveLeftType.getSubExpressionTypes(), effectiveRightType.getSubExpressionTypes());
			ExpressionType newType = ExpressionType.getBagConstruction(ctx, subExpressionType);
			expressionTypeTree.put(ctx, newType);
		} else if (opType == OZLexer.OVERRIDE){
			if (leftExpressionType.isFunction() && rightExpressionType.isFunction()){
				Type effectiveLeftType = leftExpressionType.getEffectiveType();
				Type effectiveRightType = rightExpressionType.getEffectiveType();
				
				List<ExpressionType> leftSubExpressionTypes = effectiveLeftType.getSubExpressionTypes();
				List<ExpressionType> rightSubExpressionTypes = effectiveRightType.getSubExpressionTypes();
				
				if (effectiveLeftType.isSetOfMaplet()){
					leftSubExpressionTypes = effectiveLeftType.getSubExpressionTypes();
				}
				if (effectiveRightType.isSetOfMaplet()){
					rightSubExpressionTypes = effectiveRightType.getSubExpressionType(0).getEffectiveType().getSubExpressionTypes();
				}
				
				List<ExpressionType> subExpressionTypes = determineSubTypesOfBinaryType(leftSubExpressionTypes, rightSubExpressionTypes);
				ExpressionType newType = ExpressionType.getFunction(subExpressionTypes.get(0), subExpressionTypes.get(1));
				expressionTypeTree.put(ctx, newType);
			} else if (leftExpressionType.isRelation() && (rightExpressionType.isRelation() || rightExpressionType.isSetOfMaplet())){
				Type effectiveLeftType = leftExpressionType.getEffectiveType();
				Type effectiveRightType = rightExpressionType.getEffectiveType();

				List<ExpressionType> leftSubExpressionTypes = effectiveLeftType.getSubExpressionType(0).getEffectiveType().getSubExpressionTypes();
				List<ExpressionType> rightSubExpressionTypes = effectiveRightType.getSubExpressionType(0).getEffectiveType().getSubExpressionTypes();
				
				List<ExpressionType> subExpressionTypes = determineSubTypesOfBinaryType(leftSubExpressionTypes, rightSubExpressionTypes);
				ExpressionType newType = ExpressionType.getRelation(subExpressionTypes.get(0), subExpressionTypes.get(1));
				expressionTypeTree.put(ctx, newType);
			} else {
				throw new ObjectZToPerfectTranslationException("Incompatible types for override operation");
			}
		} else if (opType == OZLexer.EXTRACT){
			if (leftExpressionType.isSet() && leftExpressionType.hasSubExpressionTypes(ExpressionType.getNat()) && rightExpressionType.isSeq()){
				ExpressionType newSubType = rightExpressionType.getEffectiveType().getSubExpressionType(0);
				expressionTypeTree.put(ctx, ExpressionType.getSequence(newSubType));
			} else {
				throw new ObjectZToPerfectTranslationException("Invalid type for extract operation");
			}
		} else if (opType == OZLexer.FILTER){
			if (leftExpressionType.isSeq() && rightExpressionType.isSet() 
					&& leftExpressionType.getEffectiveType().getSubExpressionTypes().size() == 1 && rightExpressionType.getEffectiveType().getSubExpressionTypes().size() == 1 
					&& leftExpressionType.getEffectiveType().getSubExpressionTypes().get(0).equals(rightExpressionType.getEffectiveType().getSubExpressionTypes().get(0))){
				ExpressionType newSubType = leftExpressionType.getEffectiveType().getSubExpressionTypes().get(0);
				expressionTypeTree.put(ctx, ExpressionType.getSequence(newSubType));
			} else {
				throw new ObjectZToPerfectTranslationException("Invalid types for filter operation");
			}
		} else if (opType == OZLexer.MULTIPLICITY){
			if (leftExpressionType.isBag() && leftExpressionType.getEffectiveType().getSubExpressionTypes().get(0).equals(rightExpressionType)){
				expressionTypeTree.put(ctx, ExpressionType.getNat());
			} else {
				throw new ObjectZToPerfectTranslationException("Invalid types for bag multiplicity operation");
			}
		} else if (opType == OZLexer.SCALING){
			if (leftExpressionType.isNat() && rightExpressionType.isBag()){
				expressionTypeTree.put(ctx, ExpressionType.getBag(rightExpressionType.getEffectiveType().getSubExpressionTypes().get(0)));
			} else {
				throw new ObjectZToPerfectTranslationException("Invalid types for bag scaling operation");
			}
		} else {
			throw new ObjectZToPerfectTranslationException("Invalid collection operation");
		}
	}
	
	@Override
	public void exitInfixRelationOperation(InfixRelationOperationContext ctx) {
//		Type leftType = typeTree.get(ctx.left);
//		Type rightType = typeTree.get(ctx.right);

		ExpressionType leftType = expressionTypeTree.get(ctx.left);
		ExpressionType rightType = expressionTypeTree.get(ctx.right);
		
		InfixRelationOpContext opCtx = ctx.infixRelationOp();
		
		ExpressionType newType = null;
		int opType = opCtx.op.getType();
		if (opType == OZLexer.PART_FUNC || opType == OZLexer.TOT_FUNC 
				|| opType == OZLexer.PART_INJ || opType == OZLexer.TOT_INJ
				|| opType == OZLexer.PART_SUR || opType == OZLexer.TOT_SUR 
				|| opType == OZLexer.BIJEC || opType == OZLexer.F_PART_FUNC 
				|| opType == OZLexer.F_PART_INJ){
			newType = ExpressionType.getFunction(leftType, rightType);
		} else if (opType == OZLexer.MAPLET){
			newType = ExpressionType.getMaplet(ctx, leftType, rightType);
		} else if (opType == OZLexer.DOM_RESTR || opType == OZLexer.RAN_RESTR
				|| opType == OZLexer.DOM_AR || opType == OZLexer.RAN_AR){
			if (leftType.isFunction() && rightType.isFunction()){
				newType = ExpressionType.getFunction(leftType, rightType);
			} else if (leftType.isRelation() && rightType.isRelation()){
				newType = ExpressionType.getRelation(leftType, rightType);
			} else {
				throw new ObjectZToPerfectTranslationException("Incompatible types for domain/range (anti)restriction operation");
			}
		}
		expressionTypeTree.put(ctx, newType);
	}
	
	@Override
	public void exitInfixComparisonOperation(InfixComparisonOperationContext ctx) {
		expressionTypeTree.put(ctx, ExpressionType.getBool());
	}
	
	@Override
	public void exitTernaryRelation(TernaryRelationContext ctx) {
		ExpressionType leftType   = expressionTypeTree.get(ctx.left);
		ExpressionType middleType = expressionTypeTree.get(ctx.middle);
		ExpressionType rightType  = expressionTypeTree.get(ctx.right);
		expressionTypeTree.put(ctx, ExpressionType.getRelation(leftType, middleType, rightType));
	}
	@Override
	public void exitBinaryRelation(BinaryRelationContext ctx) {
		ExpressionType leftType   = expressionTypeTree.get(ctx.left);
		ExpressionType rightType  = expressionTypeTree.get(ctx.right);
		expressionTypeTree.put(ctx, ExpressionType.getRelation(leftType, rightType));
	}
	
	@Override
	public void exitGeneralizedOperation(GeneralizedOperationContext ctx) {
		ExpressionType innerType = expressionTypeTree.get(ctx.expression());
		
		int opType = ctx.op.getType();
		
		if (opType == OZLexer.UNION || opType == OZLexer.INTERSECT){
			if (innerType.isSet()){
				expressionTypeTree.put(ctx, ExpressionType.getSetConstruction(ctx, innerType.getEffectiveType().getSubExpressionTypes().get(0)));
			} else {
				throw new ObjectZToPerfectTranslationException("Generalized operation only applicable for sets");
			}
		} else if(opType == OZLexer.CONCATENATE) {
			if (innerType.isSeq()){
				expressionTypeTree.put(ctx, ExpressionType.getSequenceConstruction(ctx, innerType.getEffectiveType().getSubExpressionTypes().get(0)));
			}
		} else {
			throw new ObjectZToPerfectTranslationException("impossible generalized operation found");
		}
	}
	
	@Override
	public void exitPrefixExpression(PrefixExpressionContext ctx) {
		ExpressionType innerType = expressionTypeTree.get(ctx.expression());
		
		PrefixContext prefixCtx = ctx.prefix();
		
		int opType = prefixCtx.op.getType();
		
		if (opType == OZLexer.MINUS){
			if (innerType.isNumber()){
				if (innerType.isNat()){
					expressionTypeTree.put(ctx, ExpressionType.getInt());
				} else {
					expressionTypeTree.put(ctx, innerType);
				}
			}
		} else if (opType == OZLexer.COUNT){
			if (innerType.isSet() || innerType.isBag() || innerType.isSeq()){
				expressionTypeTree.put(ctx, ExpressionType.getNat());
			}
		} else if (opType == OZLexer.RAN){
			if (innerType.isRelation() || innerType.isFunction()){
				expressionTypeTree.put(ctx, ExpressionType.getSetConstruction(ctx, innerType.getEffectiveType().getSubExpressionType(1)));
			}
		} else if (opType == OZLexer.DOM){
			if (innerType.isRelation()|| innerType.isFunction()){
				expressionTypeTree.put(ctx, ExpressionType.getSetConstruction(ctx, innerType.getEffectiveType().getSubExpressionType(0)));
			}
		} else if (opType == OZLexer.TAIL || opType == OZLexer.FRONT || opType == OZLexer.REV){
			if (innerType.isSeq()){
				expressionTypeTree.put(ctx, innerType);
			}
		} else if (opType == OZLexer.HEAD || opType == OZLexer.LAST){
			if (innerType.isSeq()){
				expressionTypeTree.put(ctx, innerType.getEffectiveType().getSubExpressionType(0));
			}
		} else if (opType == OZLexer.ITEMS){
			if (innerType.isSeq()){
				expressionTypeTree.put(ctx, ExpressionType.getBagConstruction(ctx, innerType.getEffectiveType().getSubExpressionType(0)));
			}
		}
		if (expressionTypeTree.get(ctx) == null){
			throw new ObjectZToPerfectTranslationException("Could not determine type of node: " + ctx.getText());
		}
	}
	
	@Override
	public void exitPostfixExpression(PostfixExpressionContext ctx) {
		throw new ObjectZToPerfectTranslationException("postfix expressions not yet implemented");
	}
	
	@Override
	public void exitPolymorphicExpression(PolymorphicExpressionContext ctx) {
		List<ExpressionType> genericParameters = new ArrayList<ExpressionType>();
		if (ctx.genActuals() != null){
			for (ExpressionContext exprCtx: ctx.genActuals().expression()){
				genericParameters.add(expressionTypeTree.get(exprCtx));
			}
		}
		
		ExpressionType newType = ExpressionType.getUserDefinedType(ctx, ctx.ID().getText());
		expressionTypeTree.put(ctx, newType);
	}
	
	@Override
	public void exitBag(BagContext ctx) {
		ExpressionType resolvedType = null;
		for (ExpressionContext exprCtx: ctx.expression()){
			ExpressionType newType = expressionTypeTree.get(exprCtx);
			resolvedType = determineNewType(resolvedType, newType);
		}
		expressionTypeTree.put(ctx, ExpressionType.getBagConstruction(ctx, resolvedType));
	}

	@Override
	public void exitSet(SetContext ctx) {
		ExpressionType resolvedType = null;
		for (ExpressionContext exprCtx: ctx.expression()){
			ExpressionType newType = expressionTypeTree.get(exprCtx);
			resolvedType = determineNewType(resolvedType, newType);
		}
		expressionTypeTree.put(ctx, ExpressionType.getSetConstruction(ctx, resolvedType));
	}
	
	@Override
	public void exitSequence(SequenceContext ctx) {
		ExpressionType resolvedType = null;
		for (ExpressionContext exprCtx: ctx.expression()){
			ExpressionType newType = expressionTypeTree.get(exprCtx);
			resolvedType = determineNewType(resolvedType, newType);
		}
		expressionTypeTree.put(ctx, ExpressionType.getSequenceConstruction(ctx, resolvedType));
	}

	@Override
	public void exitEmptySet(EmptySetContext ctx) {
		ExpressionType siblingSubType = null;
		boolean isFunction = false;
		
		if (ctx.getParent() != null){
			for (int i = 0; i< ctx.getParent().getChildCount(); i++){
				ExpressionType newSiblingType = expressionTypeTree.get(ctx.getParent().getChild(i));
				if (newSiblingType != null && (newSiblingType.isSet() || newSiblingType.isRelation())){
					Type effectiveType = newSiblingType.getEffectiveType();
					siblingSubType = determineNewType(siblingSubType, effectiveType.getSubExpressionType(0)); 
				} else if (newSiblingType != null && newSiblingType.isFunction()){
					siblingSubType = newSiblingType;
					isFunction = true;
				}
			}
		}
		if (isFunction){
			expressionTypeTree.put(ctx, siblingSubType);
		} else {
			expressionTypeTree.put(ctx, ExpressionType.getSetConstruction(ctx, siblingSubType));
		}
	}
	@Override
	public void exitEmptyBag(EmptyBagContext ctx) {
		ExpressionType siblingSubType = null;
		
		if (ctx.getParent() != null){
			for (int i = 0; i< ctx.getParent().getChildCount(); i++){
				ExpressionType newSiblingType = expressionTypeTree.get(ctx.getParent().getChild(i));
				if (newSiblingType != null && newSiblingType.isBag()){
					Type effectiveType = newSiblingType.getEffectiveType();
					siblingSubType = determineNewType(siblingSubType, effectiveType.getSubExpressionType(0)); 
				}
			}
		}
		expressionTypeTree.put(ctx, ExpressionType.getBagConstruction(ctx, siblingSubType));
	}
	@Override
	public void exitEmptySequence(EmptySequenceContext ctx) {
		ExpressionType siblingSubType = null;
		
		if (ctx.getParent() != null){
			for (int i = 0; i< ctx.getParent().getChildCount(); i++){
				ExpressionType newSiblingType = expressionTypeTree.get(ctx.getParent().getChild(i));
				if (newSiblingType != null && newSiblingType.isSeq()){
					Type effectiveType = newSiblingType.getEffectiveType();
					siblingSubType = determineNewType(siblingSubType, effectiveType.getSubExpressionType(0)); 
				}
			}
		}
		expressionTypeTree.put(ctx, ExpressionType.getSequenceConstruction(ctx, siblingSubType));
	}
	@Override
	public void exitSelf(SelfContext ctx) {
		expressionTypeTree.put(ctx, currentClass.getExpressionType());
	}
	@Override
	public void exitParenthesizedExpression(ParenthesizedExpressionContext ctx){
		expressionTypeTree.put(ctx, expressionTypeTree.get(ctx.expression()));
	}
	
	@Override
	public void enterSchemaSum(SchemaSumContext ctx) {
		this.localScope = this.localScopeTree.get(ctx);
	}
	
	@Override
	public void exitSchemaSum(SchemaSumContext ctx) {
		ExpressionType featureType = expressionTypeTree.get(ctx.featureOrFunctionCall());
		if (featureType != null && featureType.isNumber()){
			expressionTypeTree.put(ctx, featureType);
			this.localScope = this.localScope.getEnclosingScope();
		} else {
			throw new ObjectZToPerfectTranslationException("schema sum is only applicable for number type features");
		}
	}
	
	@Override
	public void exitMinMaxOfExpression(MinMaxOfExpressionContext ctx) {
		ExpressionType expressionType = expressionTypeTree.get(ctx.expression());
		
		ExpressionType subType = expressionType.getEffectiveType().getSubExpressionType(0);
		
		if (expressionType.isSet() && subType != null &&subType.isNumber()){
			ExpressionType newType = subType;
			expressionTypeTree.put(ctx, newType);
		} else {
			throw new ObjectZToPerfectTranslationException("Type of min max expression cannot be resolved. Expression may only be a set and subtype a number - other types are not supported");
		}
	}
	
	@Override
	public void exitFormalClassReference(FormalClassReferenceContext ctx) {
		ExpressionType classReference = expressionTypeTree.get(ctx.id());
		
		List<ExpressionType> formalParameters = new ArrayList<ExpressionType>();
		if (classReference != null && classReference.isUserDefinedType() && ctx.formalParameters() != null){
			for (TerminalNode idNode: ctx.formalParameters().ID()){
				Ident id = new Ident(idNode.getText());
				ExpressionType formalParamType = resolveExpressionType(ctx, id);
				formalParameters.add(formalParamType);
			}
			ExpressionType newType = ExpressionType.getUserDefinedType(ctx, classReference.getEffectiveTypeId().getName());
			expressionTypeTree.put(ctx, newType);
		} else {
			throw new ObjectZToPerfectTranslationException("Cannot resolve types in formal class reference");
		}
	}
	
	@Override
	public void exitGenericClassReference(GenericClassReferenceContext ctx) {
		ExpressionType classReference = expressionTypeTree.get(ctx.id());
		
		List<ExpressionType> genActuals = new ArrayList<ExpressionType>();
		if(classReference != null && classReference.isUserDefinedType() && ctx.genActuals() != null){
			for (ExpressionContext exprCtx: ctx.genActuals().expression()){
				ExpressionType exprType = expressionTypeTree.get(exprCtx);
				genActuals.add(exprType);
			}
			ExpressionType newType = ExpressionType.getUserDefinedType(ctx, classReference.getEffectiveTypeId().getName());
			expressionTypeTree.put(ctx, newType);
		} else {
			throw new ObjectZToPerfectTranslationException("Cannot resolve types in generic class reference");
		}
	}
	
	@Override
	public void enterSetAbstraction(SetAbstractionContext ctx) {
		this.localScope = localScopeTree.get(ctx);
	}
	
	@Override
	public void exitSetAbstraction(SetAbstractionContext ctx) {
		Declarations localDeclarations = this.localScope.findAll();
		if (localDeclarations.size() != 1){
			throw new ObjectZToPerfectTranslationException("Set abstractions with more than one varialbe declaration are currently not supported");
		} else {
			ExpressionType elemType = localDeclarations.get(0).getExpressionType();
			if (elemType != null){
				if (ctx.expression() == null){
					if (elemType.isNat()){
						// TODO Bounded expression (when elements have a type and are not collections)
						expressionTypeTree.put(ctx, ExpressionType.getSetConstruction(ctx, ExpressionType.getNat()));
					} else if (elemType.isInt()){
						// TODO Bounded expression (when elements have a type and are not collections)
						expressionTypeTree.put(ctx, ExpressionType.getSetConstruction(ctx, ExpressionType.getInt()));
					} else if (elemType.isCollectionType()){
//						typeTree.put(ctx, elemType.copy());
						expressionTypeTree.put(ctx, elemType);
					} else {
						throw new ObjectZToPerfectTranslationException("set abstraction is only supported on int, nat and collection type values when there is no expression");
					}
				} else {
					ExpressionType expressionType = expressionTypeTree.get(ctx.expression());
					if (elemType.isCollectionType() || elemType.isNat() || elemType.isNumber()){
						// TODO Bounded expression (when elements have a type and are not collections)
						expressionTypeTree.put(ctx, ExpressionType.getSet(expressionType));
					} else {
						throw new ObjectZToPerfectTranslationException("set abstraction is only supported on collection type values when there is an expression");
					}
				}
			} else {
				throw new ObjectZToPerfectTranslationException("elem type is missing");
			}
		} 
		this.localScope = this.localScope.getEnclosingScope();
	}
	
	@Override
	public void exitCaller(CallerContext ctx) {
		List<IdContext> idList = ctx.id();
		ExpressionType newType = extractExpressionTypeFromIdList(ctx, idList); //extractTypeFromIdList(idList);
		expressionTypeTree.put(ctx, newType);
	}
	
	@Override
	public void exitTuple(TupleContext ctx) {
		List<ExpressionType> subTypes = new ArrayList<ExpressionType>();
		for (ExpressionContext exprContext: ctx.expression()){
			subTypes.add(expressionTypeTree.get(exprContext));
		}
		expressionTypeTree.put(ctx, ExpressionType.getTuple(subTypes));
	}
	
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
			} else if (subTypeLeft.isMaplet() && subTypeRight.isPair()){
				newSubType = subTypeRight;
			} else if (subTypeLeft.isPair() && subTypeRight.isMaplet()){
				newSubType = subTypeLeft;
			} else {
				throw new ObjectZToPerfectTranslationException("Incompatible subtypes of collection - subtypes not equal");
			}
		}
		return newSubType;
	}
	private ExpressionType determineSubTypesOfCollection(List<ExpressionType> subTypesLeft, List<ExpressionType> subTypesRight) {
		if (subTypesLeft.size() != 1 || subTypesRight.size() != 1){
			throw new ObjectZToPerfectTranslationException("Incompatible subtypes of collection - wrong number of subtypes");
		}
		return determineNewType(subTypesLeft.get(0), subTypesRight.get(0));
	}

	
	private List<ExpressionType> determineSubTypesOfBinaryType(List<ExpressionType> subTypesLeft, List<ExpressionType> subTypesRight) {
		if (subTypesLeft.size() != 2 || subTypesRight.size() != 2){
			throw new ObjectZToPerfectTranslationException("Incompatible subtypes of binary type - wrong number of subtypes");
		}
		
		ExpressionType leftType  =  determineNewType(subTypesLeft.get(0), subTypesRight.get(0));
		ExpressionType rightType =  determineNewType(subTypesLeft.get(1), subTypesRight.get(1));
		return Arrays.asList(leftType, rightType);
	}

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
	
	private boolean isSubTypeOf(ExpressionType subTypeLeft, ExpressionType subTypeRight) {
		if (!subTypeLeft.isUserDefinedType() || !subTypeRight.isUserDefinedType()){
			return false;
		}
		Ident classId = subTypeLeft.getEffectiveTypeId();
		ObjectZClass userDefinedType = definition.resolveClass(classId);
		if (userDefinedType == null){
			throw new ObjectZToPerfectTranslationException("Cannot find user defined Class: " + classId);
		}
		return userDefinedType.isSubTypeOf(subTypeLeft.getEffectiveTypeId());
	}

	private void calculateTypeOfBinaryAlgebraicExpression(ExpressionContext ctx, ExpressionType leftType, ExpressionType rightType) {
		if (leftType == null || rightType == null){
			throw new ObjectZToPerfectTranslationException("left or right type null but needed for type evaluation.");
		} else if (!leftType.isNumber() || !rightType.isNumber()){
			throw new ObjectZToPerfectTranslationException("Invalid type for algebraic operation");
		}
		
		ExpressionType newType = determineTypeOfNumber(leftType, rightType);
		expressionTypeTree.put(ctx, newType);
	}
	
//	private Type determineSubTypesOfCollection(List<Type> subTypesLeft, List<Type> subTypesRight) {
//		if (subTypesLeft.size() != 1 || subTypesRight.size() != 1){
//			throw new ObjectZToPerfectTranslationException("Incompatible subtypes of collection - wrong number of subtypes");
//		}
//		return determineNewType(subTypesLeft.get(0), subTypesRight.get(0));
//	}
//
//	private List<Type> determineSubTypesOfBinaryType(List<Type> subTypesLeft, List<Type> subTypesRight) {
//		if (subTypesLeft.size() != 2 || subTypesRight.size() != 2){
//			throw new ObjectZToPerfectTranslationException("Incompatible subtypes of binary type - wrong number of subtypes");
//		}
//		
//		Type leftType  =  determineNewType(subTypesLeft.get(0), subTypesRight.get(0));
//		Type rightType =  determineNewType(subTypesLeft.get(1), subTypesRight.get(1));
//		return Arrays.asList(leftType, rightType);
//	}
//
//	private Type determineTypeOfNumber(Type subTypeLeft, Type subTypeRight) {
//		Type newSubType;
//		if (subTypeLeft.isReal() || subTypeRight.isReal()){
//			newSubType = Type.getReal();
//		} else if (subTypeLeft.isInt() || subTypeRight.isInt()){
//			newSubType = Type.getInt();
//		} else {
//			newSubType = Type.getNat();
//		}
//		return newSubType;
//	}
//	private boolean isSubTypeOf(Type subTypeLeft, Type subTypeRight) {
//		if (!subTypeLeft.isUserDefinedType() || !subTypeRight.isUserDefinedType()){
//			return false;
//		}
//		ObjectZClass userDefinedType = definition.resolveClass(new Ident(subTypeLeft.getName()));
//		if (userDefinedType == null){
//			throw new ObjectZToPerfectTranslationException("Cannot find user defined Class: " + subTypeLeft.getName());
//		}
//		return userDefinedType.isSubTypeOf(new Ident(subTypeRight.getName()));
//	}	
//	private void calculateTypeOfBinaryAlgebraicExpression(ExpressionContext ctx, Type leftType, Type rightType) {
//		if (leftType == null || rightType == null){
//			throw new ObjectZToPerfectTranslationException("left or right type null but needed for type evaluation.");
//		} else if (!leftType.isNumber() || !rightType.isNumber()){
//			throw new ObjectZToPerfectTranslationException("Invalid type for algebraic operation");
//		}
//		
//		Type newType = determineTypeOfNumber(leftType, rightType);
//		typeTree.put(ctx, newType);
//	}
	
	@Override
	public void enterScopeEnrichment(ScopeEnrichmentContext ctx) {
		this.localScope = this.localScopeTree.get(ctx);
	}
	
	@Override
	public void exitScopeEnrichment(ScopeEnrichmentContext ctx) {
		this.localScope = this.localScope.getEnclosingScope();
	}
	
	
	@Override
	public void exitPredefinedTypeExpression(PredefinedTypeExpressionContext ctx) {
		expressionTypeTree.put(ctx, expressionTypeTree.get(ctx.p));
	}
	
}
