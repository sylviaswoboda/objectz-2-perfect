package at.ac.tuwien.oz.translator.predicatetranslation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.ordering.ISchemaPredicate;
import at.ac.tuwien.oz.parser.OZBaseListener;
import at.ac.tuwien.oz.parser.OZParser.AbbreviationDefinitionContext;
import at.ac.tuwien.oz.parser.OZParser.AdditiveExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.AtomContext;
import at.ac.tuwien.oz.parser.OZParser.AttributeCallContext;
import at.ac.tuwien.oz.parser.OZParser.BagContext;
import at.ac.tuwien.oz.parser.OZParser.BinaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.BooleanExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.CallContext;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;
import at.ac.tuwien.oz.parser.OZParser.CartesianContext;
import at.ac.tuwien.oz.parser.OZParser.CollectionOperationContext;
import at.ac.tuwien.oz.parser.OZParser.ConjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationNameListContext;
import at.ac.tuwien.oz.parser.OZParser.DisjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.EmptyBagContext;
import at.ac.tuwien.oz.parser.OZParser.EmptySequenceContext;
import at.ac.tuwien.oz.parser.OZParser.EmptySetContext;
import at.ac.tuwien.oz.parser.OZParser.EquivalenceContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsOneContext;
import at.ac.tuwien.oz.parser.OZParser.FalseContext;
import at.ac.tuwien.oz.parser.OZParser.FeatureOrNumberContext;
import at.ac.tuwien.oz.parser.OZParser.ForallContext;
import at.ac.tuwien.oz.parser.OZParser.FormalClassReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.FormalParametersContext;
import at.ac.tuwien.oz.parser.OZParser.FunctionReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.GenActualsContext;
import at.ac.tuwien.oz.parser.OZParser.GeneralizedOperationContext;
import at.ac.tuwien.oz.parser.OZParser.GenericClassReferenceContext;
import at.ac.tuwien.oz.parser.OZParser.IdContext;
import at.ac.tuwien.oz.parser.OZParser.IdOrNumberContext;
import at.ac.tuwien.oz.parser.OZParser.ImplicationContext;
import at.ac.tuwien.oz.parser.OZParser.InfixComparisonOperationContext;
import at.ac.tuwien.oz.parser.OZParser.InfixRelationOperationContext;
import at.ac.tuwien.oz.parser.OZParser.InitcallContext;
import at.ac.tuwien.oz.parser.OZParser.MinMaxOfExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.MultiplicativeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.NegationContext;
import at.ac.tuwien.oz.parser.OZParser.NumberContext;
import at.ac.tuwien.oz.parser.OZParser.ParenthesizedExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.ParenthesizedPredicateContext;
import at.ac.tuwien.oz.parser.OZParser.PolymorphicExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PowerSetExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PredefinedTypeContext;
import at.ac.tuwien.oz.parser.OZParser.PredefinedTypeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PrefixExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.RangeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.ReferenceBinaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaSumContext;
import at.ac.tuwien.oz.parser.OZParser.SelfContext;
import at.ac.tuwien.oz.parser.OZParser.SequenceContext;
import at.ac.tuwien.oz.parser.OZParser.SetAbstractionContext;
import at.ac.tuwien.oz.parser.OZParser.SetContext;
import at.ac.tuwien.oz.parser.OZParser.SimpleContext;
import at.ac.tuwien.oz.parser.OZParser.SuccessorContext;
import at.ac.tuwien.oz.parser.OZParser.TernaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.TrueContext;
import at.ac.tuwien.oz.parser.OZParser.TupleContext;
import at.ac.tuwien.oz.parser.OZParser.UnderlinedIdContext;
import at.ac.tuwien.oz.translator.TypeEvaluator;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class PredicateTranslator extends OZBaseListener {
	
	// input
	private ParseTreeProperty<Variable> declarationTree;
	
	// output
	private ParseTreeProperty<ST> templateTree;
	
	// provides templates for predicates and expressions
	private PredicateProvider predicateTranslator;
	private ExpressionProvider expressionTranslator;
	
	private boolean isInAbbreviation;

	
	public PredicateTranslator(ParseTreeProperty<ExpressionType> typeTree, 
			ParseTreeProperty<Variable> declarationTree, 
			ParseTreeProperty<List<ISchemaPredicate>> schemaPredicateTree) {
		super();
		this.templateTree = new ParseTreeProperty<ST>();
		this.declarationTree = declarationTree;
		this.isInAbbreviation = false;
		TypeEvaluator typeEvaluator = new TypeEvaluator(typeTree);
		this.predicateTranslator = new PredicateProvider(templateTree, declarationTree, schemaPredicateTree, typeEvaluator);
		this.expressionTranslator = new ExpressionProvider(templateTree, typeEvaluator);
	}
	
	public ParseTreeProperty<ST> getTemplateTree(){
		return this.templateTree;
	}
	
	@Override
	public void exitDeclarationNameList(DeclarationNameListContext ctx) {
		List<ST> idTemplates = new ArrayList<>();
		for (IdContext idCtx: ctx.id()){
			ST idTemplate = templateTree.get(idCtx);
			idTemplates.add(idTemplate);
		}
		saveST(ctx, PerfectTemplateProvider.getInstance().getItemList(idTemplates));
	}
	
	@Override
	public void exitDeclaration(DeclarationContext ctx) {
		for(IdContext idCtx: ctx.declarationNameList().id()){
			// set x
			Variable var = declarationTree.get(idCtx);
			var.updateDeclaredExpressionTypeTemplate(templateTree);
//			var.setTypeExpressionTemplate(templateTree);
		}
	}
	
	/*
	 * Delegates to TranslatePredicateToPerfect
	 */
	
	@Override
	public void enterAbbreviationDefinition(AbbreviationDefinitionContext ctx) {
		this.isInAbbreviation = true;
	}
	@Override
	public void exitAbbreviationDefinition(AbbreviationDefinitionContext ctx) {
		this.isInAbbreviation = false;
	}
	
	@Override
	public void exitExists(ExistsContext ctx) {
		saveST(ctx, predicateTranslator.getExistsST(ctx));
	}
	@Override
	public void exitExistsOne(ExistsOneContext ctx) {
		saveST(ctx, predicateTranslator.getExistsOneST(ctx));
	}
	@Override
	public void exitParenthesizedPredicate(ParenthesizedPredicateContext ctx) {
		saveST(ctx, predicateTranslator.getParenthesizedPredicateST(ctx));
	}
	@Override
	public void exitForall(ForallContext ctx) {
		saveST(ctx, predicateTranslator.getForallST(ctx));
	}
	@Override
	public void exitSimple(SimpleContext ctx) {
		saveST(ctx, predicateTranslator.getSimpleST(ctx));
	}
	@Override
	public void exitEquivalence(EquivalenceContext ctx) {
		saveST(ctx, predicateTranslator.getEquivalence(ctx));
	}
	@Override
	public void exitImplication(ImplicationContext ctx) {
		saveST(ctx, predicateTranslator.getImplicationST(ctx));
	}
	@Override
	public void exitDisjunction(DisjunctionContext ctx) {
		saveST(ctx, predicateTranslator.getDisjunctionST(ctx));
	}
	@Override
	public void exitConjunction(ConjunctionContext ctx) {
		saveST(ctx, predicateTranslator.getConjunctionST(ctx));
	}
	@Override
	public void exitAtom(AtomContext ctx) {
		saveST(ctx, predicateTranslator.getAtomST(ctx));
	}
	@Override
	public void exitNegation(NegationContext ctx) {
		saveST(ctx, predicateTranslator.getNegationST(ctx));
	}
	@Override
	public void exitInitcall(InitcallContext ctx) {
		saveST(ctx, predicateTranslator.getInitcallST(ctx));
	}
	@Override
	public void exitReferenceBinaryRelation(ReferenceBinaryRelationContext ctx) {
		saveST(ctx, predicateTranslator.getReferenceBinaryRelationST(ctx));
	}
	@Override
	public void exitBooleanExpression(BooleanExpressionContext ctx) {
		saveST(ctx, predicateTranslator.getBooleanExpressionST(ctx));
	}
	@Override
	public void exitFalse(FalseContext ctx) {
		saveST(ctx, predicateTranslator.getFalseST(ctx));
	}
	@Override
	public void exitTrue(TrueContext ctx) {
		saveST(ctx, predicateTranslator.getTrueST(ctx));
	}
	@Override
	public void exitUnderlinedId(UnderlinedIdContext ctx) {
		saveST(ctx, predicateTranslator.getUnderLinedIdST(ctx));
	}
	
	/*
	 * Delegates to TranslateExpressionToPerfect 
	 */
	
	@Override
	public void exitCaller(CallerContext ctx){
		saveST(ctx, expressionTranslator.getCallerST(ctx));
	}
	@Override
	public void exitCall(CallContext ctx) {
		saveST(ctx, expressionTranslator.getCallST(ctx));
	}
	@Override
	public void exitInfixComparisonOperation(InfixComparisonOperationContext ctx) {
		saveST(ctx, expressionTranslator.getInfixComparisonOperationST(ctx));
	}
	@Override
	public void exitTernaryRelation(TernaryRelationContext ctx) {
		saveST(ctx, expressionTranslator.getTernaryRelationST(ctx));
	}
	@Override
	public void exitTuple(TupleContext ctx) {
		saveST(ctx, expressionTranslator.getTupleST(ctx));
	}
	@Override
	public void exitSet(SetContext ctx) {
		saveST(ctx, expressionTranslator.getSetST(ctx));
	}
	@Override
	public void exitSelf(SelfContext ctx) {
		saveST(ctx, expressionTranslator.getSelfST(ctx));		
	}
	@Override
	public void exitBinaryRelation(BinaryRelationContext ctx) {
		saveST(ctx, expressionTranslator.getBinaryRelationST(ctx));
	}
	@Override
	public void exitGeneralizedOperation(GeneralizedOperationContext ctx) {
		saveST(ctx, expressionTranslator.getGeneralizedOperationST(ctx));
	}
	@Override
	public void exitPolymorphicExpression(PolymorphicExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getPolymorphicExpressionST(ctx));
	}
	@Override
	public void exitCartesian(CartesianContext ctx) {
		saveST(ctx, expressionTranslator.getCartesianST(ctx));
	}
	@Override
	public void exitRangeExpression(RangeExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getRangeExpressionST(ctx));
	}
	@Override
	public void exitEmptyBag(EmptyBagContext ctx) {
		saveST(ctx, expressionTranslator.getEmptyBagST(ctx));
	}
	@Override
	public void exitInfixRelationOperation(InfixRelationOperationContext ctx) {
		saveST(ctx, expressionTranslator.getInfixRelationOperationST(ctx));
	}
	@Override
	public void exitEmptySequence(EmptySequenceContext ctx) {
		saveST(ctx, expressionTranslator.getEmptySequenceST(ctx));
	}
	@Override
	public void exitPrefixExpression(PrefixExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getPrefixExpressionST(ctx));
	}
	@Override
	public void exitPowerSetExpression(PowerSetExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getPowerSetExpressionST(ctx));
	}
	@Override
	public void exitBag(BagContext ctx) {
		saveST(ctx, expressionTranslator.getBagST(ctx));
	}
	@Override
	public void exitAdditiveExpression(AdditiveExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getAdditiveExpressionST(ctx));
	}
	@Override
	public void exitCollectionOperation(CollectionOperationContext ctx) {
		saveST(ctx, expressionTranslator.getCollectionOperationST(ctx));
	}
	@Override
	public void exitParenthesizedExpression(ParenthesizedExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getParenthesizedExpressionST(ctx));
	}
	@Override
	public void exitMinMaxOfExpression(MinMaxOfExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getMinMaxOfExpressionST(ctx));
	}
	@Override
	public void exitSequence(SequenceContext ctx) {
		saveST(ctx, expressionTranslator.getSequenceST(ctx));
	}
	@Override
	public void exitGenericClassReference(GenericClassReferenceContext ctx) {
		saveST(ctx, expressionTranslator.getGenericClassReferenceST(ctx));
	}
	@Override
	public void exitFormalClassReference(FormalClassReferenceContext ctx) {
		saveST(ctx, expressionTranslator.getFormalClassReferenceST(ctx));
	}
	@Override
	public void exitEmptySet(EmptySetContext ctx) {
		saveST(ctx, expressionTranslator.getEmptySetST(ctx));
	}
	@Override
	public void exitPredefinedTypeExpression(PredefinedTypeExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getPredefinedTypeExpressionST(ctx));
	}
	@Override
	public void exitMultiplicativeExpression(MultiplicativeExpressionContext ctx) {
		saveST(ctx, expressionTranslator.getMultiplicativeExpressionST(ctx));
	}
	@Override
	public void exitGenActuals(GenActualsContext ctx) {
		saveST(ctx, expressionTranslator.getGenActualsST(ctx));
	}
	@Override
	public void exitFormalParameters(FormalParametersContext ctx) {
		saveST(ctx, expressionTranslator.getFormalParametersST(ctx));
	}
	@Override
	public void exitAttributeCall(AttributeCallContext ctx) {
		saveST(ctx, expressionTranslator.getAttributeCallST(ctx));
	}
	@Override
	public void exitSuccessor(SuccessorContext ctx) {
		saveST(ctx, expressionTranslator.getSuccessorST(ctx));
	}
	@Override
	public void exitFunctionReference(FunctionReferenceContext ctx) {
		saveST(ctx, expressionTranslator.getFunctionReferenceST(ctx));		
	}
	@Override
	public void exitFeatureOrNumber(FeatureOrNumberContext ctx) {
		saveST(ctx, expressionTranslator.getFeatureOrNumberST(ctx));
	}
	@Override
	public void exitIdOrNumber(IdOrNumberContext ctx) {
		saveST(ctx, expressionTranslator.getIdOrNumberST(ctx));
	}
	@Override
	public void exitId(IdContext ctx) {
		saveST(ctx, expressionTranslator.getId(ctx));
	}
	@Override
	public void exitNumber(NumberContext ctx) {
		saveST(ctx, expressionTranslator.getNumberST(ctx));
	}
	@Override
	public void exitPredefinedType(PredefinedTypeContext ctx) {
		saveST(ctx, expressionTranslator.getPredefinedTypeST(ctx));
	}
	
	@Override
	public void exitSetAbstraction(SetAbstractionContext ctx) {
		// TODO Auto-generated method stub
	}
	@Override
	public void exitSchemaSum(SchemaSumContext ctx) {
		// TODO Auto-generated method stub
	}
	
	private void saveST(ParserRuleContext ctx, ST template) {
		templateTree.put(ctx, template);
	}

}
