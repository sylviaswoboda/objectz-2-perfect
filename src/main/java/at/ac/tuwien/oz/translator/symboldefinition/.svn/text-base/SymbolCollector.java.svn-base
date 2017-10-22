package at.ac.tuwien.oz.translator.symboldefinition;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.definitions.IScope;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.local.Abbreviation;
import at.ac.tuwien.oz.definitions.local.FreeType;
import at.ac.tuwien.oz.definitions.local.GivenType;
import at.ac.tuwien.oz.definitions.operation.AnonymousSchemaOperationExpression;
import at.ac.tuwien.oz.definitions.operation.CombinedOperation;
import at.ac.tuwien.oz.definitions.operation.NonDeterministicChoice;
import at.ac.tuwien.oz.definitions.operation.OperationBuilder;
import at.ac.tuwien.oz.definitions.operation.OperationConjunction;
import at.ac.tuwien.oz.definitions.operation.OperationExpression;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.definitions.operation.ParallelComposition;
import at.ac.tuwien.oz.definitions.operation.ScopeEnrichment;
import at.ac.tuwien.oz.definitions.operation.SequentialComposition;
import at.ac.tuwien.oz.definitions.operation.SimpleOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.definitions.ozclass.ClassDescriptor;
import at.ac.tuwien.oz.definitions.ozclass.InitialState;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.definitions.ozclass.StateSchema;
import at.ac.tuwien.oz.ordering.ISchemaPredicate;
import at.ac.tuwien.oz.ordering.PredicateParseTreeSplitter;
import at.ac.tuwien.oz.parser.OZBaseListener;
import at.ac.tuwien.oz.parser.OZParser.AbbreviationDefinitionContext;
import at.ac.tuwien.oz.parser.OZParser.AnonymousOperationSchemaDefContext;
import at.ac.tuwien.oz.parser.OZParser.AssociativeParallelCompositionContext;
import at.ac.tuwien.oz.parser.OZParser.AxiomaticDefinitionContext;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;
import at.ac.tuwien.oz.parser.OZParser.ClassDefinitionContext;
import at.ac.tuwien.oz.parser.OZParser.ClassDesContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationListContext;
import at.ac.tuwien.oz.parser.OZParser.DistributedConjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.DistributedNonDeterminisiticChoiceContext;
import at.ac.tuwien.oz.parser.OZParser.DistributedSequentialCompositionContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsOneContext;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.FeatureContext;
import at.ac.tuwien.oz.parser.OZParser.ForallContext;
import at.ac.tuwien.oz.parser.OZParser.FreeTypeDefinitionContext;
import at.ac.tuwien.oz.parser.OZParser.GenActualsContext;
import at.ac.tuwien.oz.parser.OZParser.GivenTypeDefinitionContext;
import at.ac.tuwien.oz.parser.OZParser.IdContext;
import at.ac.tuwien.oz.parser.OZParser.InitialStateContext;
import at.ac.tuwien.oz.parser.OZParser.NonDeterministicChoiceContext;
import at.ac.tuwien.oz.parser.OZParser.OperationConjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.OperationExpressionAtomContext;
import at.ac.tuwien.oz.parser.OZParser.OperationExpressionDefContext;
import at.ac.tuwien.oz.parser.OZParser.OperationPromotionContext;
import at.ac.tuwien.oz.parser.OZParser.OperationSchemaDefContext;
import at.ac.tuwien.oz.parser.OZParser.ParallelCompositionContext;
import at.ac.tuwien.oz.parser.OZParser.ParenthesizedOperationExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.PredicateContext;
import at.ac.tuwien.oz.parser.OZParser.PredicateListContext;
import at.ac.tuwien.oz.parser.OZParser.PredicatesContext;
import at.ac.tuwien.oz.parser.OZParser.ProgramContext;
import at.ac.tuwien.oz.parser.OZParser.RenamingContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaDeclarationListContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaSumContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaTextContext;
import at.ac.tuwien.oz.parser.OZParser.ScopeEnrichmentContext;
import at.ac.tuwien.oz.parser.OZParser.SequentialCompositionContext;
import at.ac.tuwien.oz.parser.OZParser.SetAbstractionContext;
import at.ac.tuwien.oz.parser.OZParser.SimpleOperationExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.StateContext;
import at.ac.tuwien.oz.scopes.LocalScope;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class SymbolCollector extends OZBaseListener{

	protected ObjectZDefinition globalScope;
	protected ObjectZClass 	  	currentClass;
	protected LocalScope		currentLocalScope;
	protected IScope 			currentScope; // define symbols in this scope;
	
	
	private ParseTreeProperty<Idents> usedIdentifierTree;
	
	private ParseTreeProperty<OperationExpression> anonymousOperations;

	private ParseTreeProperty<Variable> declarationTree;
	private ParseTreeProperty<Declarations> declarationsTree;
//	private ParseTreeProperty<AxiomReference> axiomReferenceTree;
	private ParseTreeProperty<List<ISchemaPredicate>> schemaPredicateTree;
	
	private ParseTreeProperty<LocalScope> localScopeTree;

	
	public SymbolCollector(ParseTreeProperty<Idents> usedIdentifierTree){
		this.usedIdentifierTree = usedIdentifierTree;
//		this.axiomReferenceTree = new ParseTreeProperty<AxiomReference>();
		this.schemaPredicateTree = new ParseTreeProperty<List<ISchemaPredicate>>();
		this.anonymousOperations = new ParseTreeProperty<OperationExpression>();
		this.declarationTree = new ParseTreeProperty<Variable>();
		this.declarationsTree = new ParseTreeProperty<Declarations>();
		this.localScopeTree = new ParseTreeProperty<LocalScope>();
		this.globalScope = new ObjectZDefinition();
	}
	
	public ObjectZDefinition getObjectZDefinition(){
		return this.globalScope;
	}
	
	public ParseTreeProperty<Variable> getDeclarationTree() {
		return this.declarationTree;
	}

	public ParseTreeProperty<LocalScope> getLocalScopeTree() {
		return this.localScopeTree;
	}

	/* Handle entering and exiting the different scopes correctly */
	
	@Override
	public void enterProgram(ProgramContext ctx) {
		this.currentScope = globalScope;
		this.globalScope.setContext(ctx);
	}

	@Override
	public void exitProgram(ProgramContext ctx) {
		currentScope = null;
	}

	@Override
	public void enterClassDefinition(ClassDefinitionContext ctx) {
		String className = ctx.ID().getText();
		ObjectZClass objectZClass = new ObjectZClass(className, globalScope, ctx);
		globalScope.addClass(objectZClass);
		
		currentClass = objectZClass;
		currentScope = objectZClass;
	}

	@Override
	public void exitClassDefinition(ClassDefinitionContext ctx) {
		if (ctx.formalParameters() != null){
			for(TerminalNode idNode: ctx.formalParameters().ID()){
				Ident formalParameter = new Ident(idNode.getText());
				currentClass.addFormalParameter(formalParameter);
			}
		}
		if (ctx.visibilityList() == null){
			currentClass.setAllFeaturesVisible();
		} else {
			if (ctx.visibilityList().feature() != null){
				for (FeatureContext featureCtx: ctx.visibilityList().feature()){
					if (featureCtx.ID() != null){
						Ident visibleFeature = new Ident(featureCtx.ID().getText());
						currentClass.addVisibleFeature(visibleFeature);
					} else if (featureCtx.INIT() != null){
						Ident visibleFeature = new Ident(featureCtx.INIT().getText());
						currentClass.addVisibleFeature(visibleFeature);
					} 
				}
			}
		}
		
		if (ctx.inheritedClassList() != null){
			if (ctx.inheritedClassList().classDes() != null){
				for (ClassDesContext classDesCtx: ctx.inheritedClassList().classDes()){
					Ident className = new Ident(classDesCtx.ID().getText());
					GenActualsContext genActualsCtx = classDesCtx.genActuals();
					RenamingContext   renamingCtx = classDesCtx.renaming();
					
					ClassDescriptor classDescriptor = new ClassDescriptor(className, genActualsCtx, renamingCtx);
					currentClass.addInheritedClass(classDescriptor);
				}
			}
		}

		currentClass = null;
		currentScope = globalScope;
	}
	
	@Override
	public void exitAbbreviationDefinition(AbbreviationDefinitionContext ctx) {
		String abbreviationName = ctx.ID().getText();
		Abbreviation abbreviation = new Abbreviation(abbreviationName, currentScope, ctx);
		if (ctx.formalParameters() != null){
			for (TerminalNode formalParameter: ctx.formalParameters().ID()){
				abbreviation.addFormalParameter(new Ident(formalParameter.getText()));
			}
		}
		abbreviation.addExpressionCtx(ctx.expression());
		if (currentClass != null){
			currentClass.addLocalDefinition(abbreviation);
		} else {
			globalScope.addGlobalDefinition(abbreviation);
		}
	}

	@Override
	public void exitGivenTypeDefinition(GivenTypeDefinitionContext ctx) {
		for (TerminalNode givenType: ctx.ID()){
			String givenTypeName = givenType.getText();
			GivenType givenTypeDef = new GivenType(givenTypeName, currentScope, ctx);
			if (currentClass != null){
				currentClass.addLocalDefinition(givenTypeDef);
			} else {
				globalScope.addGlobalDefinition(givenTypeDef);
			}
		}
	}
	
	@Override
	public void exitFreeTypeDefinition(FreeTypeDefinitionContext ctx) {
		
		String freeTypeName = ctx.ID(0).getText();
		List<String> values = new ArrayList<String>();
		
		for(int i = 1; i < ctx.ID().size(); i++){
			values.add(ctx.ID(i).getText());
		}
		
		FreeType freeTypeDef = new FreeType(freeTypeName, values, currentScope, ctx);
		if (currentClass != null){
			currentClass.addLocalDefinition(freeTypeDef);
		} else {
			globalScope.addGlobalDefinition(freeTypeDef);
		}
	}

	@Override
	public void exitAxiomaticDefinition(AxiomaticDefinitionContext ctx) {
		Declarations axiomaticDeclarations = extractDeclarations(ctx.declarationList());
		AxiomReferences axiomReferences = extractPredicates(ctx.predicateList());
		
		if (currentClass != null){
			currentClass.addAxiomaticDeclarations(axiomaticDeclarations);
			currentClass.addAxiomaticReferences(axiomReferences);
		} else {
			globalScope.addAxiomaticDeclarations(axiomaticDeclarations);
			globalScope.addAxiomaticReferences(axiomReferences);
		}
	}

	@Override
	public void exitState(StateContext ctx) {
		Declarations primary = new Declarations();
		Declarations secondary = new Declarations();
		AxiomReferences axiomReferences = new AxiomReferences();
		
		if (ctx.primary() != null){
			primary.addAll(declarationsTree.get(ctx.primary().declarationList()));
		}
		
		if (ctx.secondary() != null){
			secondary.addAll(declarationsTree.get(ctx.secondary().declarationList()));
		}
		
		if (ctx.predicateList() != null){
			axiomReferences.addAll(extractPredicates(ctx.predicateList()));
		}
		
		StateSchema stateSchema = new StateSchema(primary, secondary, axiomReferences);
		currentClass.addStateSchema(stateSchema);
	}
	
	@Override
	public void exitDeclarationList(DeclarationListContext ctx) {
		Declarations declarations = new Declarations();
		for (DeclarationContext declCtx: ctx.declaration()){
			declarations.addAll(declarationsTree.get(declCtx));
		}
		declarationsTree.put(ctx, declarations);
	}
	
	@Override
	public void exitDeclaration(DeclarationContext ctx) {
		Declarations declarations = new Declarations();
		ExpressionContext variableTypeCtx = ctx.expression();
		for(IdContext idCtx: ctx.declarationNameList().id()){
			String name = idCtx.ID().getText();
			String decoration = null;
			if (idCtx.DECORATION() != null){
				decoration = idCtx.DECORATION().getText();
			}
			Ident ident = new Ident(name, decoration);
			Variable varDef = new Variable(ident, variableTypeCtx);
			declarations.add(varDef);
			declarationTree.put(idCtx, varDef);
		}
		declarationsTree.put(ctx, declarations);
	}

	@Override
	public void exitInitialState(InitialStateContext ctx){
		AxiomReferences axiomReferences = extractPredicates(ctx.predicateList());
		InitialState initState = new InitialState(axiomReferences);
		currentClass.addInitialState(initState);
	}

	@Override
	public void exitOperationSchemaDef(OperationSchemaDefContext ctx) {
		extractOperationFromSchema(ctx);
	}

	
	@Override
	public void exitOperationExpressionDef(OperationExpressionDefContext ctx) {
		String operationName = ctx.operationName.getText();
		OperationExpression anonymousOp = getAnonymousOperation(ctx.operationExpression());
		anonymousOp.rename(operationName);
		anonymousOp.setDefiningClass(currentClass);
		anonymousOp.setContext(ctx);
		currentClass.addOperation(anonymousOp);
	}

	@Override
	public void exitSimpleOperationExpression(SimpleOperationExpressionContext ctx) {
		saveAnonymousOperation(ctx, getAnonymousOperation(ctx.opExpression()));
	}

	@Override
	public void exitOperationExpressionAtom(OperationExpressionAtomContext ctx) {
		saveAnonymousOperation(ctx, getAnonymousOperation(ctx.opExprAtom()));
	}

	@Override
	public void exitOperationConjunction(OperationConjunctionContext ctx){
		IComposableOperation leftOp = getAnonymousOperation(ctx.left);
		IComposableOperation rightOp = getAnonymousOperation(ctx.right);
		
		if (leftOp.isCombinable() && rightOp.isCombinable()){
			ICombinableOperation leftCombineOp = leftOp.getCombinableOperation();
			ICombinableOperation rightCombineOp = rightOp.getCombinableOperation();
			
			CombinedOperation conjunctionOp = new OperationConjunction(leftCombineOp, rightCombineOp);
			conjunctionOp.setDefiningClass(currentClass);
			saveAnonymousOperation(ctx, conjunctionOp);
		} else {
			throw new ObjectZToPerfectTranslationException("It is not possible to conjoin " + leftOp + " and " + rightOp + ". At least one operation is not combinable.");
		}
	}

	@Override
	public void exitAssociativeParallelComposition(AssociativeParallelCompositionContext ctx) {
		IComposableOperation leftOp = getAnonymousOperation(ctx.left);
		IComposableOperation rightOp = getAnonymousOperation(ctx.right);
		
		if (leftOp.isCombinable() && rightOp.isCombinable()){
			ICombinableOperation leftCombineOp = leftOp.getCombinableOperation();
			ICombinableOperation rightCombineOp = rightOp.getCombinableOperation();
			
			CombinedOperation parallelComp = new ParallelComposition(leftCombineOp, rightCombineOp, true);
			parallelComp.setDefiningClass(currentClass);
			saveAnonymousOperation(ctx, parallelComp);
		} else {
			throw new ObjectZToPerfectTranslationException("It is not possible to compose " + leftOp + " and " + rightOp + ". At least one operation is not combinable.");
		}
	}

	@Override
	public void exitParallelComposition(ParallelCompositionContext ctx) {
		IComposableOperation leftOp = getAnonymousOperation(ctx.left);
		IComposableOperation rightOp = getAnonymousOperation(ctx.right);
		
		if (leftOp.isCombinable() && rightOp.isCombinable()){
			ICombinableOperation leftCombineOp = leftOp.getCombinableOperation();
			ICombinableOperation rightCombineOp = rightOp.getCombinableOperation();
			
			CombinedOperation parallelComp = new ParallelComposition(leftCombineOp, rightCombineOp, false);
			parallelComp.setDefiningClass(currentClass);
			saveAnonymousOperation(ctx, parallelComp);
		} else {
			throw new ObjectZToPerfectTranslationException("It is not possible to compose " + leftOp + " and " + rightOp + ". At least one operation is not combinable.");
		}
	}

	@Override
	public void exitNonDeterministicChoice(NonDeterministicChoiceContext ctx) {
		OperationExpression leftOp = getAnonymousOperation(ctx.left);
		OperationExpression rightOp = getAnonymousOperation(ctx.right);
		
		OperationExpression nonDeterministicChoice = new NonDeterministicChoice(leftOp, rightOp);
		nonDeterministicChoice.setDefiningClass(currentClass);

		saveAnonymousOperation(ctx, nonDeterministicChoice);
	}

	@Override
	public void exitSequentialComposition(SequentialCompositionContext ctx) {
		OperationExpression leftOp = getAnonymousOperation(ctx.left);
		OperationExpression rightOp = getAnonymousOperation(ctx.right);
		
		OperationExpression sequentialComp = new SequentialComposition(leftOp, rightOp);
		sequentialComp.setDefiningClass(currentClass);
		
		saveAnonymousOperation(ctx, sequentialComp);
	}
	@Override
	public void enterScopeEnrichment(ScopeEnrichmentContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}

	@Override
	public void exitScopeEnrichment(ScopeEnrichmentContext ctx) {
		OperationExpression scopeEnrichmentSchema = getAnonymousOperation(ctx.left);
		OperationExpression opExpression = getAnonymousOperation(ctx.right);
		
		if (!scopeEnrichmentSchema.isAnonymousSchemaOperation()){
			throw new ObjectZToPerfectTranslationException("Scope Enrichment is only supported for anonymous schema operations on left side");
		}
		if (!scopeEnrichmentSchema.getOutputParameters().isEmpty()){
			throw new ObjectZToPerfectTranslationException("Output parameters on left side of scope enrichment are not supported");
		}
		if (scopeEnrichmentSchema.getInputParameters().size() > 0 && scopeEnrichmentSchema.getAuxiliaryParameters().size() > 0){
			throw new ObjectZToPerfectTranslationException("Only input or auxiliary parameters are supported on left side of scope enrichment");
		}
		if (scopeEnrichmentSchema.getAuxiliaryParameters().size() > 1){
			throw new ObjectZToPerfectTranslationException("More than one auxiliary parameter is not supported on left side of scope enrichment");
		}
		if (scopeEnrichmentSchema.getDeltalist().size() > 0){
			throw new ObjectZToPerfectTranslationException("Changed state variables are not supported on left side of scope enrichment");
		}
		ScopeEnrichment scopeEnrichment = new ScopeEnrichment((AnonymousSchemaOperationExpression)scopeEnrichmentSchema, opExpression);
		
		saveAnonymousOperation(ctx, scopeEnrichment);
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();

	}

	@Override
	public void exitAnonymousOperationSchemaDef(AnonymousOperationSchemaDefContext ctx) {
		SimpleOperation anonymousOperation = extractAnonymousOperationFromSchema(ctx);
		AnonymousSchemaOperationExpression opExpression = new AnonymousSchemaOperationExpression(anonymousOperation);
		saveAnonymousOperation(ctx, opExpression);
		
		for (Variable v: anonymousOperation.getInputParameters()){
			this.currentLocalScope.define(v);
		}
		for (Variable v: anonymousOperation.getAuxiliaryParameters()){
			this.currentLocalScope.define(v);
		}
		for (Variable v: anonymousOperation.getOutputParameters()){
			this.currentLocalScope.define(v);
		}

	}
	
	@Override
	public void exitOperationPromotion(OperationPromotionContext ctx) {
		CallerContext callerCtx = ctx.caller();
		
		String calledOperationName = ctx.opName.ID().getText();
		OperationExpression opPromo = new OperationPromotion(callerCtx, calledOperationName);
		opPromo.setDefiningClass(currentClass);
		saveAnonymousOperation(ctx, opPromo);
	}
	
	@Override
	public void exitParenthesizedOperationExpression(ParenthesizedOperationExpressionContext ctx) {
		saveAnonymousOperation(ctx, getAnonymousOperation(ctx.operationExpression()));
	}
	
	private SimpleOperation extractOperationFromSchema(OperationSchemaDefContext ctx) {
		String operationName = ctx.ID().getText();
		
		List<String> deltalist = new ArrayList<String>();
		Declarations declarations = new Declarations();
		AxiomReferences predicates = new AxiomReferences();
		
		if (ctx.deltalist() != null){
			for (TerminalNode i: ctx.deltalist().ID()){
				deltalist.add(i.getText());
			}
		}
		if (ctx.declarationList() != null){
			declarations.addAll(declarationsTree.get(ctx.declarationList()));
		}
		if (ctx.predicateList() != null){
			predicates.addAll(extractPredicates(ctx.predicateList()));
		}
		
		SimpleOperation op = new OperationBuilder().buildOperation(operationName, deltalist, declarations, predicates, currentClass, ctx);
		
		if (op != null){
			currentClass.addOperation(op);
		}
		
		return op;
	}

	private SimpleOperation extractAnonymousOperationFromSchema(AnonymousOperationSchemaDefContext ctx) {
		
		List<String> deltalist = new ArrayList<String>();
		Declarations declarations = new Declarations();
		AxiomReferences predicates = new AxiomReferences();
		
		if (ctx.ID() != null){
			for (TerminalNode i: ctx.ID()){
				deltalist.add(i.getText());
			}
		}
		if (ctx.declarationList() != null){
			declarations.addAll(declarationsTree.get(ctx.declarationList()));
		}
		if (ctx.predicateList() != null){
			predicates.addAll(extractPredicates(ctx.predicateList()));
		}
		
		SimpleOperation op = new OperationBuilder().buildOperation(deltalist, declarations, predicates, currentClass, ctx);
		
		return op;
	}

	private void saveAnonymousOperation(ParserRuleContext ctx, OperationExpression op){
		anonymousOperations.put(ctx, op);
	}

	private OperationExpression getAnonymousOperation(ParserRuleContext ctx){
		return anonymousOperations.get(ctx);
	}

	private AxiomReferences extractPredicates(PredicateListContext predicateList) {
		AxiomReferences axiomReferences = new AxiomReferences();
		if (predicateList != null && predicateList instanceof PredicatesContext){
			PredicatesContext predicates = (PredicatesContext)predicateList;
			
			for (PredicateContext predicateCtx: predicates.predicate()){
				extractSchemaPredicates(predicateCtx);
				Idents usedIdentifiers = usedIdentifierTree.get(predicateCtx);
				AxiomReference axiomReference = new AxiomReference(usedIdentifiers, predicateCtx);
				axiomReferences.add(axiomReference);
//				axiomReferenceTree.put(predicateCtx, axiomReference);
			}
		}
		return axiomReferences;
	}

	private void extractSchemaPredicates(PredicateContext predicateCtx) {
		PredicateParseTreeSplitter splitter = new PredicateParseTreeSplitter();
		List<ParseTree> simplePredicates = splitter.split(predicateCtx);
		
		List<ISchemaPredicate> schemaPredicates = simplePredicates.stream()
				.map(subParseTree -> new AxiomReference(usedIdentifierTree.get(subParseTree), subParseTree))
				.collect(Collectors.toList());
		schemaPredicateTree.put(predicateCtx, schemaPredicates);
	}

	@Override
	public void exitSchemaDeclarationList(SchemaDeclarationListContext ctx) {
		Declarations declarations = new Declarations();
		for(DeclarationContext declCtx: ctx.declaration()){
			declarations.addAll(declarationsTree.get(declCtx));
		}
		declarationsTree.put(ctx, declarations);
	}

	private Declarations extractDeclarations(DeclarationListContext declarationList) {
		Declarations declarations = new Declarations();
		if (declarationList != null){
			List<DeclarationContext> declarationCtxs = declarationList.declaration();
			extractDeclarations(declarations, declarationCtxs);
		}
		return declarations;
	}

	private void extractDeclarations(Declarations declarations, List<DeclarationContext> declarationCtxs) {
		for (DeclarationContext declarationCtx: declarationCtxs){
			ExpressionContext variableTypeCtx = declarationCtx.expression();
			for(IdContext idCtx: declarationCtx.declarationNameList().id()){
				String name = idCtx.ID().getText();
				String decoration = null;
				if (idCtx.DECORATION() != null){
					decoration = idCtx.DECORATION().getText();
				}
				Ident ident = new Ident(name, decoration);
				Variable varDef = new Variable(ident, variableTypeCtx);
				declarations.add(varDef);
				declarationTree.put(idCtx, varDef);
			}
		}
	}

	@Override
	public void enterSetAbstraction(SetAbstractionContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}
	@Override
	public void enterSchemaSum(SchemaSumContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}
	@Override
	public void enterDistributedSequentialComposition(DistributedSequentialCompositionContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}
	@Override
	public void enterDistributedNonDeterminisiticChoice(DistributedNonDeterminisiticChoiceContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}
	@Override
	public void enterDistributedConjunction(DistributedConjunctionContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}
	@Override
	public void enterForall(ForallContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}
	@Override
	public void enterExists(ExistsContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}
	@Override
	public void enterExistsOne(ExistsOneContext ctx) {
		this.currentLocalScope = new LocalScope(this.currentLocalScope);
		this.localScopeTree.put(ctx, this.currentLocalScope);
	}
	
	@Override
	public void exitSetAbstraction(SetAbstractionContext ctx) {
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();
	}
	@Override
	public void exitSchemaSum(SchemaSumContext ctx) {
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();
	}
	@Override
	public void exitDistributedSequentialComposition(DistributedSequentialCompositionContext ctx) {
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();
	}
	@Override
	public void exitDistributedNonDeterminisiticChoice(DistributedNonDeterminisiticChoiceContext ctx) {
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();
	}
	@Override
	public void exitDistributedConjunction(DistributedConjunctionContext ctx) {
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();
	}
	@Override
	public void exitForall(ForallContext ctx) {
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();
	}
	@Override
	public void exitExists(ExistsContext ctx) {
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();
	}
	@Override
	public void exitExistsOne(ExistsOneContext ctx) {
		this.currentLocalScope = this.currentLocalScope.getEnclosingScope();
	}
	
	@Override
	public void exitSchemaText(SchemaTextContext ctx) {
		Declarations declarations = declarationsTree.get(ctx.schemaDeclarationList());
		for (Variable v: declarations){
			this.currentLocalScope.define(v);
		}
	}

	public ParseTreeProperty<List<ISchemaPredicate>> getSchemaPredicateTree() {
		return schemaPredicateTree;
	}

}
