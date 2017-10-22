package at.ac.tuwien.oz.translator.predicatetranslation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.ordering.ISchemaPredicate;
import at.ac.tuwien.oz.ordering.ISchemaVariable;
import at.ac.tuwien.oz.ordering.OrderingCalculator;
import at.ac.tuwien.oz.ordering.OrderingItem;
import at.ac.tuwien.oz.ordering.PredicateDependencyGraph;
import at.ac.tuwien.oz.ordering.PredicateDependencyGraphBuilder;
import at.ac.tuwien.oz.parser.OZParser.AtomContext;
import at.ac.tuwien.oz.parser.OZParser.BooleanExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.ConjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.DeclarationContext;
import at.ac.tuwien.oz.parser.OZParser.DisjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.EquivalenceContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsOneContext;
import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.FalseContext;
import at.ac.tuwien.oz.parser.OZParser.ForallContext;
import at.ac.tuwien.oz.parser.OZParser.IdContext;
import at.ac.tuwien.oz.parser.OZParser.ImplicationContext;
import at.ac.tuwien.oz.parser.OZParser.InitcallContext;
import at.ac.tuwien.oz.parser.OZParser.NegationContext;
import at.ac.tuwien.oz.parser.OZParser.ParenthesizedPredicateContext;
import at.ac.tuwien.oz.parser.OZParser.PredicateContext;
import at.ac.tuwien.oz.parser.OZParser.ReferenceBinaryRelationContext;
import at.ac.tuwien.oz.parser.OZParser.SchemaTextContext;
import at.ac.tuwien.oz.parser.OZParser.SimpleContext;
import at.ac.tuwien.oz.parser.OZParser.TrueContext;
import at.ac.tuwien.oz.parser.OZParser.UnderlinedIdContext;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;
import at.ac.tuwien.oz.translator.TempVarProvider;
import at.ac.tuwien.oz.translator.TypeEvaluator;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;
import at.ac.tuwien.oz.translator.templates.StringTemplateCloner;
import at.ac.tuwien.oz.translator.templates.StringTemplateSubstitutor;

public class PredicateProvider {
	
	private ParseTreeProperty<ST> templateTree;
	private ParseTreeProperty<Variable> declarationTree;
	private ParseTreeProperty<List<ISchemaPredicate>> schemaPredicateTree;
	private TypeEvaluator typeEvaluator;
	private PerfectTemplateProvider perfect;
	
	public PredicateProvider(ParseTreeProperty<ST> treeTemplates,
			ParseTreeProperty<Variable> declarationTree, 
			ParseTreeProperty<List<ISchemaPredicate>> schemaPredicateTree,
			TypeEvaluator typeEvaluator) {
		super();
		this.templateTree = treeTemplates;
		this.declarationTree = declarationTree;
		this.typeEvaluator = typeEvaluator;
		this.schemaPredicateTree = schemaPredicateTree;
		this.perfect = PerfectTemplateProvider.getInstance();
	}
	public ST getTrueST(TrueContext ctx) {
		return perfect.getTrue();
	}
	public ST getFalseST(FalseContext ctx) {
		return perfect.getFalse();
	}
	public ST getBooleanExpressionST(BooleanExpressionContext ctx) {
		return templateTree.get(ctx.expression());
	}
	public ST getReferenceBinaryRelationST(ReferenceBinaryRelationContext ctx) {
		ExpressionType type = typeEvaluator.getType(ctx);
		ST typeTemplate = (type != null ? type.getTemplate() : null);
		ST relation = templateTree.get(ctx.underlinedId());
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);
		return perfect.getBinaryRelationReference(typeTemplate, relation, left, right);
	}
	public ST getInitcallST(InitcallContext ctx) {
		ST theObject = templateTree.get(ctx.id());
		return perfect.getInitCall(theObject);
	}
	public ST getNegationST(NegationContext ctx) {
		ST innerPredicate = templateTree.get(ctx.predicateAtom());
		return perfect.getNegation(innerPredicate);
	}
	public ST getUnderLinedIdST(UnderlinedIdContext ctx) {
		return templateTree.get(ctx.id());
	}
	public ST getAtomST(AtomContext ctx) {
		return templateTree.get(ctx.predicateAtom());
	}
	public ST getConjunctionST(ConjunctionContext ctx) {
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);
		return perfect.getConjunction(left, right);
	}
	public ST getDisjunctionST(DisjunctionContext ctx) {
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);
		return perfect.getDisjunction(left, right);
	}
	public ST getImplicationST(ImplicationContext ctx) {
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);
		return perfect.getImplication(left, right);
	}
	public ST getEquivalence(EquivalenceContext ctx) {
		ST left = templateTree.get(ctx.left);
		ST right = templateTree.get(ctx.right);
		return perfect.getEquivalence(left, right);
	}
	public ST getSimpleST(SimpleContext ctx) {
		return templateTree.get(ctx.simplePredicate());
	}
	public ST getForallST(ForallContext ctx) {
		SchemaTextContext schemaCtx = ctx.schemaText();
		List<ST> declarations = null;
		
		ST predicate = templateTree.get(ctx.predicate());
		
		if (schemaCtx.predicate() == null){
			declarations = getDeclarationsOfSchemaTextWithoutPredicates(schemaCtx);
		} else {
			declarations = getDeclarationsOfSchemaTextWithPredicates(schemaCtx);
		}
		
		return perfect.getForall(declarations, predicate);
	}
	public ST getExistsST(ExistsContext ctx){
		SchemaTextContext schemaCtx = ctx.schemaText();
		List<ST> declarations = null;
		
		ST predicate = templateTree.get(ctx.predicate());
		
		if (schemaCtx.predicate() == null){
			declarations = getDeclarationsOfSchemaTextWithoutPredicates(schemaCtx);
		} else {
			declarations = getDeclarationsOfSchemaTextWithPredicates(schemaCtx);
		}
		
		return perfect.getExists(declarations, predicate);
	}
	public ST getPredicateSt(PredicateContext ctx){
		return templateTree.get(ctx);
	}
	
	public ST getExistsOneST(ExistsOneContext ctx) {
		SchemaTextContext schemaCtx = ctx.schemaText();
		ST predicate = templateTree.get(ctx.predicate());
		
		if (schemaCtx.predicate() != null){
			throw new ObjectZToPerfectTranslationException("Unique Quantification for schema texts with predicates is not implemented");
		}
		List<ISchemaVariable> declaredVariables = collectDeclaredVariables(schemaCtx);
		
		if (declaredVariables.size() > 1){
			throw new ObjectZToPerfectTranslationException("Unique Quantification for schema texts with more than one declaration is not implemented");
		}
		ISchemaVariable declaredVariable = declaredVariables.get(0);

		// if declaration is not collection type
		if (!declaredVariable.hasCollectionType()){
			throw new ObjectZToPerfectTranslationException("Unique Quantification is only implemented for collection type variables");
		}
		
		// create declaration for one variable
		// get template of predicate
		// create universal quantification for declared variable
		//		create those-clause (type of bound variable declaration)
		//		tempVar
		//		create declaration of tempVar and type
		// 		create equality of tempVar and declaredVar.
		//		create forall template
		
		ST tempVarInThose = perfect.getId(new Ident(TempVarProvider.getTempVarName()));
		ST declarationInThoseClause = perfect.getBoundVariableDeclarationOfCollection(tempVarInThose, declaredVariable.getExpressionType().getDeclaredTemplate());
		ST predicateInThose = new StringTemplateCloner().clone(predicate);
		ST idST = perfect.getId(declaredVariable.getId());
		predicateInThose = new StringTemplateSubstitutor().substituteIdentifier(predicateInThose, idST, tempVarInThose);
		ST thoseClause = perfect.getThoseClause(Arrays.asList(declarationInThoseClause), Arrays.asList(predicateInThose));
		
		ST tempVarInForall = perfect.getId(new Ident(TempVarProvider.getTempVarName()));
		ST declarationInForall = perfect.getBoundVariableDeclarationOfCollection(tempVarInForall, thoseClause);
		ST forallPredicate = perfect.getEquals(tempVarInForall, idST);
		ST forall = perfect.getForall(Arrays.asList(declarationInForall), forallPredicate);
		
		ST declarationInExists = perfect.getBoundVariableDeclarationOfCollection(idST, declaredVariable.getExpressionType().getDeclaredTemplate());
		ST conjunctionInExists = perfect.getConjunction(predicate, forall);
		
		ST existsOne = perfect.getExists(Arrays.asList(declarationInExists), conjunctionInExists);
		
		return existsOne;
	}
	public ST getParenthesizedPredicateST(ParenthesizedPredicateContext ctx) {
		ST innerPredicate = templateTree.get(ctx.predicate());
		ST parenthesized = perfect.getParenthesized(innerPredicate);
		return parenthesized;
	}

	private List<ST> getDeclarationsOfSchemaTextWithoutPredicates(SchemaTextContext schemaCtx) {
		List<ST> declarations = new ArrayList<ST>();
		
		for (DeclarationContext d: schemaCtx.schemaDeclarationList().declaration()){
			ExpressionContext expressionCtx = d.expression();
			
			ST declarationNameList = templateTree.get(d.declarationNameList());
			Variable firstDeclaredVar = declarationTree.get(d.declarationNameList().id(0));
			ExpressionType declarationType = firstDeclaredVar.getExpressionType();
			ST typeExpression = templateTree.get(expressionCtx);
			
			if(declarationType.isPredefinedType() || declarationType.isUserDefinedType()){
				declarations.add(perfect.getBoundVariableDeclarationOfType(declarationNameList, declarationType.getTemplate()));
			} else if (declarationType.isTemplateType()){
				declarations.add(perfect.getBoundVariableDeclarationOfCollection(declarationNameList, declarationType.getDeclaredTemplate()));
			} else {
				throw new ObjectZToPerfectTranslationException("Type of expression cannot be resolved: " + typeExpression.render());
			}
		}
		
		return declarations;
	}
	
	private List<ST> getDeclarationsOfSchemaTextWithPredicates(SchemaTextContext schemaCtx) {
		List<ST> declarationTemplates = new ArrayList<ST>();
		
		List<ISchemaVariable> declaredVariables = collectDeclaredVariables(schemaCtx);
		
		if (declaredVariables.size() == 1){
			ST schemaPredicate = templateTree.get(schemaCtx.predicate());
			getDeclarationWithPredicatesForOneVariable(declarationTemplates, declaredVariables.get(0), schemaPredicate);
		} else {
			List<ISchemaPredicate> schemaPredicates = schemaPredicateTree.get(schemaCtx.predicate());
			getDeclarationsWithPredicateForMultipleVariables(declarationTemplates, declaredVariables, schemaPredicates);
		}
		
		return declarationTemplates;
	}
	
	private void getDeclarationsWithPredicateForMultipleVariables(List<ST> declarationTemplates, List<ISchemaVariable> variables, List<ISchemaPredicate> schemaPredicates) {
		ISchemaVariable currentVariable;
		ST declarationTemplate;

		// build graph from schemaText context
		PredicateDependencyGraph graph = buildGraph(variables, schemaPredicates);
		List<OrderingItem> orderedDeclarations = calculateOrdering(graph);
		
		for (OrderingItem currentDeclaration: orderedDeclarations){
			currentVariable = currentDeclaration.getVariableNode().getVariable();
			
			if (currentDeclaration.getPredicateNodes().isEmpty()){
				declarationTemplate = getDeclarationTemplateForVariableWithoutPredicate(currentVariable);
			} else {
				declarationTemplate = getDeclarationTemplateForVariableWithPredicates(currentVariable, currentDeclaration);
			}
			
			if (declarationTemplate != null){
				declarationTemplates.add(declarationTemplate);
			}
		}
	}
	private ST getDeclarationTemplateForVariableWithPredicates(ISchemaVariable currentVariable, OrderingItem schemaTextDeclaration) {
		ST predicatesTemplate = perfect.getConjunctionFromList(schemaTextDeclaration.getPredicateTemplates(this.templateTree));
		
		ST innerCollectionType = currentVariable.getExpressionType().getDeclaredTemplate();
		ST idST = perfect.getId(currentVariable.getId());
		ST tempVar = perfect.getId(new Ident(TempVarProvider.getTempVarName()));
		ST tempVarDeclarationTemplate = perfect.getBoundVariableDeclarationOfCollection(tempVar, innerCollectionType);
		predicatesTemplate = new StringTemplateSubstitutor().substituteIdentifier(predicatesTemplate, idST, tempVar);

		ST collectionType = perfect.getThoseClause(Arrays.asList(tempVarDeclarationTemplate), Arrays.asList(predicatesTemplate));
		ST declaration = perfect.getBoundVariableDeclarationOfCollection(idST, collectionType);
		return declaration;
	}
	
	private void getDeclarationWithPredicatesForOneVariable(List<ST> declarationTemplates, ISchemaVariable variable, ST predicateTemplate) {
		if (variable.hasCollectionType()){
//			ST innerCollectionType = variable.getType();
			ST innerCollectionType = variable.getExpressionType().getDeclaredTemplate();
			ST idST = perfect.getId(variable.getId());
			ST tempVar = perfect.getId(new Ident(TempVarProvider.getTempVarName()));
			ST tempVarDeclarationTemplate = perfect.getBoundVariableDeclarationOfCollection(tempVar, innerCollectionType);

			predicateTemplate = new StringTemplateSubstitutor().substituteIdentifier(predicateTemplate, idST, tempVar);
			
			ST collectionType = perfect.getThoseClause(Arrays.asList(tempVarDeclarationTemplate), Arrays.asList(predicateTemplate));
			ST declaration = perfect.getBoundVariableDeclarationOfCollection(idST, collectionType);
			declarationTemplates.add(declaration);
		} else if (variable.hasPredefinedType() || variable.hasUserDefinedType()){
			throw new ObjectZToPerfectTranslationException("Schema with one variable of user or predefined type with predicate not supported");
		}
	}
	private ST getDeclarationTemplateForVariableWithoutPredicate(ISchemaVariable currentVariable) {
		ST declarationTemplate = null;
		
//		ExpressionContext currentTypeCtx = currentVariable.getTypeCtx();
//		ST currentType = currentVariable.getType();
		ST currentId = perfect.getId(currentVariable.getId());
		
		if (currentVariable.hasPredefinedType() || currentVariable.hasUserDefinedType()){
			declarationTemplate = perfect.getBoundVariableDeclarationOfType(currentId, currentVariable.getExpressionType().getTemplate());
		} else if (currentVariable.hasCollectionType()){
			declarationTemplate = perfect.getBoundVariableDeclarationOfCollection(currentId, currentVariable.getExpressionType().getDeclaredTemplate());
		} else {
			throw new ObjectZToPerfectTranslationException("Type of variable cannot be resolved: " + currentVariable);
		}
		return declarationTemplate;
	}
	private List<OrderingItem> calculateOrdering(PredicateDependencyGraph graph) {
		OrderingCalculator calculator = new OrderingCalculator(graph);
		List<OrderingItem> orderedDeclarations = calculator.calculateOrdering();
		return orderedDeclarations;
	}
	private PredicateDependencyGraph buildGraph(List<ISchemaVariable> variables, List<ISchemaPredicate> schemaPredicates) {
//		PredicateSplitter splitter = new PredicateSplitter();
//		List<ISchemaPredicate> predicateList = splitter.split(schemaPredicate);
		PredicateDependencyGraphBuilder builder = new PredicateDependencyGraphBuilder(variables, schemaPredicates);
		PredicateDependencyGraph graph = builder.buildGraph();
		return graph;
	}
	
	private List<ISchemaVariable> collectDeclaredVariables(SchemaTextContext schemaCtx) {
		List<ISchemaVariable> variables = new ArrayList<ISchemaVariable>();
		for (DeclarationContext d: schemaCtx.schemaDeclarationList().declaration()){
//			ExpressionContext typeCtx = d.expression();
			for(IdContext idCtx : d.declarationNameList().id()){
				Variable v = declarationTree.get(idCtx);;
				variables.add(v);
//				ST id = templateTree.get(idCtx);
//				ST originalType = templateTree.get(typeCtx);
//				SchemaVariable v = new SchemaVariable(idCtx, typeCtx, id, originalType);
//				variables.add(v);
			}
		}
		return variables;
	}
		
}
