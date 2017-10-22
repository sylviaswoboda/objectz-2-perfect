package at.ac.tuwien.oz.translator.symboldefinition;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.parser.OZBaseListener;
import at.ac.tuwien.oz.parser.OZParser.AdditiveExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.AtomContext;
import at.ac.tuwien.oz.parser.OZParser.AttributeCallContext;
import at.ac.tuwien.oz.parser.OZParser.BagContext;
import at.ac.tuwien.oz.parser.OZParser.BinaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.BooleanExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.CallContext;
import at.ac.tuwien.oz.parser.OZParser.CartesianContext;
import at.ac.tuwien.oz.parser.OZParser.CollectionOperationContext;
import at.ac.tuwien.oz.parser.OZParser.ConjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationNameListContext;
import at.ac.tuwien.oz.parser.OZParser.DisjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.EquivalenceContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsOneContext;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
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
import at.ac.tuwien.oz.parser.OZParser.ParenthesizedExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.ParenthesizedPredicateContext;
import at.ac.tuwien.oz.parser.OZParser.PolymorphicExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PostfixExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PowerSetExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PredicateContext;
import at.ac.tuwien.oz.parser.OZParser.PredicatesContext;
import at.ac.tuwien.oz.parser.OZParser.PrefixExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.RangeExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.ReferenceBinaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaDeclarationListContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaSumContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaTextContext;
import at.ac.tuwien.oz.parser.OZParser.SequenceContext;
import at.ac.tuwien.oz.parser.OZParser.SetAbstractionContext;
import at.ac.tuwien.oz.parser.OZParser.SetContext;
import at.ac.tuwien.oz.parser.OZParser.SimpleContext;
import at.ac.tuwien.oz.parser.OZParser.SimplePredicateContext;
import at.ac.tuwien.oz.parser.OZParser.SuccessorContext;
import at.ac.tuwien.oz.parser.OZParser.TernaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.TupleContext;
import at.ac.tuwien.oz.parser.OZParser.UnderlinedIdContext;

public class IdentifierCollector extends OZBaseListener{
	
	private ParseTreeProperty<Idents> usedIdentifierTree;

	public IdentifierCollector(){
		this.usedIdentifierTree = new ParseTreeProperty<>();
	}
	private void saveUsedIdentifiers(ParserRuleContext ctx, Idents usedIdentifiers){
		this.usedIdentifierTree.put(ctx, usedIdentifiers);
	}
	private Idents getUsedIdentifiers(ParserRuleContext ctx){
		if (ctx == null){
			return new Idents();
		}
		
		Idents idents = this.usedIdentifierTree.get(ctx);
		if (idents == null){
			idents = new Idents();
		}
		return idents;
	}
	private void propagateUsedIdentifiersUp(ParserRuleContext child, ParserRuleContext parent){
		saveUsedIdentifiers(parent, getUsedIdentifiers(child));
	}

	private void propagateUsedIdentifiersOfChildExpressions(List<ExpressionContext> children, 
			ParserRuleContext ctx) {
		Idents identifiers = new Idents();
		
		for (ExpressionContext childExpression: children){
			identifiers.addAll(getUsedIdentifiers(childExpression));
		}
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	private void propagateUsedIdentifiersOfChildPredicates(List<SimplePredicateContext> children, 
			ParserRuleContext ctx) {
		Idents identifiers = new Idents();
		
		for (SimplePredicateContext childExpression: children){
			identifiers.addAll(getUsedIdentifiers(childExpression));
		}
		
		saveUsedIdentifiers(ctx, identifiers);
	}

	@Override
	public void exitId(IdContext ctx) {
		String originalDecoration = null;
		if (ctx.getChildCount() == 2){
			originalDecoration = ctx.DECORATION().getText();
		}
		Ident i = new Ident(ctx.ID().getText(), originalDecoration);
		
		saveUsedIdentifiers(ctx, new Idents(i));
	}
	
	@Override
	public void exitUnderlinedId(UnderlinedIdContext ctx){
		propagateUsedIdentifiersUp(ctx.id(), ctx);
	}
	
	@Override
	public void exitIdOrNumber(IdOrNumberContext ctx) {
		ParserRuleContext idOrNumberNode = ctx.getChild(ParserRuleContext.class, 0);
		propagateUsedIdentifiersUp(idOrNumberNode, ctx);
	}

	@Override
	public void exitFeatureOrNumber(FeatureOrNumberContext ctx) {
		propagateUsedIdentifiersUp(ctx.idOrNumber(), ctx);
	}

	@Override
	public void exitFunctionReference(FunctionReferenceContext ctx) {
		Idents identifiers = new Idents();
		identifiers.addAll(getUsedIdentifiers(ctx.id()));
		
		for (int i = 0; i < ctx.getChildCount(); i++){
			identifiers.addNew(getUsedIdentifiers(ctx.featureOrFunctionCall(i)));
		}
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitSuccessor(SuccessorContext ctx) {
		propagateUsedIdentifiersUp(ctx.idOrNumber(), ctx);
	}

	@Override
	public void exitAttributeCall(AttributeCallContext ctx) {
		Idents identifiers = new Idents();
		
		for (int i = 0; i < ctx.getChildCount(); i++){
			identifiers.addAll(getUsedIdentifiers(ctx.id(i)));
		}

		saveUsedIdentifiers(ctx, identifiers);
	}

	@Override
	public void exitFormalParameters(FormalParametersContext ctx){
		Idents identifiers = new Idents();

		for (TerminalNode id: ctx.ID()){
			identifiers.add(new Ident(id.getText()));
		}
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitSetAbstraction(SetAbstractionContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.schemaText()));
		identifiers.addNew(getUsedIdentifiers(ctx.expression()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitGenericClassReference(GenericClassReferenceContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.id()));
		identifiers.addNew(getUsedIdentifiers(ctx.genActuals()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}

	@Override
	public void exitFormalClassReference(FormalClassReferenceContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.id()));
		identifiers.addNew(getUsedIdentifiers(ctx.formalParameters()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitSchemaSum(SchemaSumContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.schemaText()));
		identifiers.addNew(getUsedIdentifiers(ctx.featureOrFunctionCall()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}

	@Override
	public void exitPolymorphicExpression(PolymorphicExpressionContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.add(new Ident(ctx.ID().getText()));
		identifiers.addNew(getUsedIdentifiers(ctx.genActuals()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}

	@Override
	public void exitReferenceBinaryRelation(ReferenceBinaryRelationContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.expression(0)));
		identifiers.addNew(getUsedIdentifiers(ctx.underlinedId()));
		identifiers.addNew(getUsedIdentifiers(ctx.expression(1)));
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitExistsOne(ExistsOneContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.schemaText()));
		identifiers.addNew(getUsedIdentifiers(ctx.predicate()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}

	@Override
	public void exitExists(ExistsContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.schemaText()));
		identifiers.addNew(getUsedIdentifiers(ctx.predicate()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitForall(ForallContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.schemaText()));
		identifiers.addNew(getUsedIdentifiers(ctx.predicate()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitPredicates(PredicatesContext ctx){
		Idents identifiers = new Idents();
		
		for (PredicateContext child: ctx.predicate()){
			identifiers.addNew(getUsedIdentifiers(child));
		}
		
		saveUsedIdentifiers(ctx, identifiers);
	}

	@Override
	public void exitSchemaText(SchemaTextContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.schemaDeclarationList()));
		identifiers.addNew(getUsedIdentifiers(ctx.predicate()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitSchemaDeclarationList(SchemaDeclarationListContext ctx){
		Idents identifiers = new Idents();
		
		for (DeclarationContext child: ctx.declaration()){
			identifiers.addNew(getUsedIdentifiers(child));
		}
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	@Override
	public void exitDeclaration(DeclarationContext ctx){
		Idents identifiers = new Idents();
		
		identifiers.addAll(getUsedIdentifiers(ctx.declarationNameList()));
		identifiers.addNew(getUsedIdentifiers(ctx.expression()));
		
		saveUsedIdentifiers(ctx, identifiers);
	}

	@Override
	public void exitDeclarationNameList(DeclarationNameListContext ctx){
		Idents identifiers = new Idents();
		
		for (IdContext child: ctx.id()){
			identifiers.addNew(getUsedIdentifiers(child));
		}
		
		saveUsedIdentifiers(ctx, identifiers);
	}
	
	/* PropagateUsedIdentifiersUp */

	@Override
	public void exitParenthesizedExpression(ParenthesizedExpressionContext ctx){
		propagateUsedIdentifiersUp(ctx.expression(), ctx);
	}
	
	@Override
	public void exitCall(CallContext ctx){
		propagateUsedIdentifiersUp(ctx.featureOrFunctionCall(), ctx);
	}
	
	@Override
	public void exitMinMaxOfExpression(MinMaxOfExpressionContext ctx){
		propagateUsedIdentifiersUp(ctx.expression(), ctx);
	}
	
	@Override
	public void exitPostfixExpression(PostfixExpressionContext ctx){
		propagateUsedIdentifiersUp(ctx.expression(), ctx);
	}
	
	@Override
	public void exitPrefixExpression(PrefixExpressionContext ctx){
		propagateUsedIdentifiersUp(ctx.expression(), ctx);
	}
	
	@Override
	public void exitGeneralizedOperation(GeneralizedOperationContext ctx){
		propagateUsedIdentifiersUp(ctx.expression(), ctx);
	}

	@Override
	public void exitPowerSetExpression(PowerSetExpressionContext ctx){
		propagateUsedIdentifiersUp(ctx.expression(), ctx);
	}
	
	@Override
	public void exitParenthesizedPredicate(ParenthesizedPredicateContext ctx){
		propagateUsedIdentifiersUp(ctx.predicate(), ctx);
	}
	
	@Override
	public void exitBooleanExpression(BooleanExpressionContext ctx){
		propagateUsedIdentifiersUp(ctx.expression(), ctx);
	}
	
	@Override
	public void exitInitcall(InitcallContext ctx){
		propagateUsedIdentifiersUp(ctx.id(), ctx);
	}
	
	@Override
	public void exitNegation(NegationContext ctx){
		propagateUsedIdentifiersUp(ctx.predicateAtom(), ctx);
	}
	
	@Override
	public void exitAtom(AtomContext ctx){
		propagateUsedIdentifiersUp(ctx.predicateAtom(), ctx);
	}
	
	@Override
	public void exitSimple(SimpleContext ctx){
		propagateUsedIdentifiersUp(ctx.simplePredicate(), ctx);
	}

	/* PropagateUsedIdentifiersOfChildPredicates */

	@Override
	public void exitEquivalence(EquivalenceContext ctx){
		propagateUsedIdentifiersOfChildPredicates(ctx.simplePredicate(), ctx);
	}
	
	@Override
	public void exitImplication(ImplicationContext ctx){
		propagateUsedIdentifiersOfChildPredicates(ctx.simplePredicate(), ctx);
	}
	
	@Override
	public void exitDisjunction(DisjunctionContext ctx){
		propagateUsedIdentifiersOfChildPredicates(ctx.simplePredicate(), ctx);
	}
	
	@Override
	public void exitConjunction(ConjunctionContext ctx){
		propagateUsedIdentifiersOfChildPredicates(ctx.simplePredicate(), ctx);
	}
	

	/* PropagateUsedIdentifiersOfChildExpressions */

	@Override
	public void exitGenActuals(GenActualsContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}

	@Override
	public void exitSequence(SequenceContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}
	
	@Override
	public void exitBag(BagContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}

	@Override
	public void exitSet(SetContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}
	
	@Override
	public void exitTuple(TupleContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}
	
	@Override
	public void exitBinaryRelation(BinaryRelationContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}

	@Override
	public void exitTernaryRelation(TernaryRelationContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}
	
	@Override
	public void exitInfixRelationOperation(InfixRelationOperationContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}
	
	@Override
	public void exitInfixComparisonOperation(InfixComparisonOperationContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}

	@Override
	public void exitCollectionOperation(CollectionOperationContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}

	@Override
	public void exitRangeExpression(RangeExpressionContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}

	@Override
	public void exitAdditiveExpression(AdditiveExpressionContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}

	@Override
	public void exitMultiplicativeExpression(MultiplicativeExpressionContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}
	
	@Override
	public void exitCartesian(CartesianContext ctx){
		propagateUsedIdentifiersOfChildExpressions(ctx.expression(), ctx);
	}
	public ParseTreeProperty<Idents> getUsedIdentifierTree() {
		return this.usedIdentifierTree;
	}

}
