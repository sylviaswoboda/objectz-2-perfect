package at.ac.tuwien.oz.main;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.ordering.ISchemaPredicate;
import at.ac.tuwien.oz.scopes.LocalScope;
import at.ac.tuwien.oz.translator.localdefinitiontranslation.DefinitionTranslator;
import at.ac.tuwien.oz.translator.operationtranslation.OperationComposer;
import at.ac.tuwien.oz.translator.operationtranslation.OperationTranslator;
import at.ac.tuwien.oz.translator.predicatetranslation.PredicateTranslator;
import at.ac.tuwien.oz.translator.symboldefinition.IdentifierCollector;
import at.ac.tuwien.oz.translator.symboldefinition.SymbolCollector;
import at.ac.tuwien.oz.translator.typeevaluator.EmptyCollectionEvaluator;
import at.ac.tuwien.oz.translator.typeevaluator.TypeDeclarationEvaluator;

public class OZTranslator{
	private ParseTree tree;
	
	private ParseTreeProperty<Idents> usedIdentifierTree;
	
	private ParseTreeProperty<Variable> declarationTree;
	private ParseTreeProperty<List<ISchemaPredicate>> schemaPredicateTree;
	private ParseTreeProperty<LocalScope> localScopeTree;
	
	private ParseTreeProperty<ExpressionType> expressionTypeTree;
	
	private ParseTreeProperty<ST> templateTree;
	
	private ObjectZDefinition objectZDefinition;
	
	private IdentifierCollector identifierCollector;
	private SymbolCollector symbolCollector;
	private TypeDeclarationEvaluator typeDeclarationEvaluator;
	private PredicateTranslator predicateTranslator;
	private EmptyCollectionEvaluator emptyCollectionEvaluator;
	private OperationComposer operationComposer;
	private OperationTranslator operationTranslator;
	private DefinitionTranslator definitionTranslator;

	public OZTranslator(ParseTree tree){
		this.tree = tree;
		
		buildIdentifierCollector();
		buildSymbolCollector();
		buildTypeDeclarationEvaluator();
		emptyCollectionEvaluator = new EmptyCollectionEvaluator(objectZDefinition, expressionTypeTree);
		
		buildPredicateTranslator();
		operationComposer = new OperationComposer(objectZDefinition, templateTree, expressionTypeTree);
		
		operationTranslator = new OperationTranslator(objectZDefinition, templateTree);
		definitionTranslator = new DefinitionTranslator(objectZDefinition, templateTree);
	}

	private void buildPredicateTranslator() {
		predicateTranslator = new PredicateTranslator(expressionTypeTree, declarationTree, schemaPredicateTree);
		templateTree = predicateTranslator.getTemplateTree();
	}

	private void buildTypeDeclarationEvaluator() {
		typeDeclarationEvaluator = new TypeDeclarationEvaluator(objectZDefinition, declarationTree, localScopeTree);
		expressionTypeTree = typeDeclarationEvaluator.getExpressionTypeTree();
	}

	private void buildSymbolCollector() {
		symbolCollector = new SymbolCollector(usedIdentifierTree);
		objectZDefinition = symbolCollector.getObjectZDefinition();
		declarationTree = symbolCollector.getDeclarationTree();
		localScopeTree = symbolCollector.getLocalScopeTree();
		schemaPredicateTree = symbolCollector.getSchemaPredicateTree();
	}

	private void buildIdentifierCollector() {
		identifierCollector = new IdentifierCollector();
		usedIdentifierTree = identifierCollector.getUsedIdentifierTree();
	}
	
	public void translate(){
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(identifierCollector, tree);
		walker.walk(symbolCollector, tree);
		walker.walk(typeDeclarationEvaluator, tree);
		walker.walk(emptyCollectionEvaluator, tree);
		walker.walk(predicateTranslator, tree);

		operationComposer.compose();
		operationTranslator.translate();
		definitionTranslator.translate();
	}

	public ST getRootTemplate() {
		return this.templateTree.get(tree);
	}
	public ObjectZDefinition getDefinition(){
		return this.symbolCollector.getObjectZDefinition();
	}
}
