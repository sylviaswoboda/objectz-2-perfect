package at.ac.tuwien.oz.translator;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Type;
import at.ac.tuwien.oz.datatypes.axioms.Axioms;
import at.ac.tuwien.oz.definitions.IScope;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.parser.OZBaseListener;
import at.ac.tuwien.oz.translator.operationtranslation.OperationTranslator;
import at.ac.tuwien.oz.translator.predicatetranslation.ExpressionProvider;
import at.ac.tuwien.oz.translator.predicatetranslation.PredicateProvider;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

@Deprecated
public class TranslateToPerfectTemplates extends OZBaseListener{
	private ParseTreeProperty<ST> treeTemplates;
	private ParseTreeProperty<Type> treeExpressionTypes;
	private ParseTreeProperty<List<Ident>> treeDeclarationNames;
	private ParseTreeProperty<Declarations> treeDeclarations;
	private ParseTreeProperty<Axioms> treePredicates;
	private ParseTreeProperty<Idents> usedIdentifiers;
	private ParseTreeProperty<IComposableOperation> anonymousOperations;
	
	private TypeEvaluator		typeEvaluator;
	private PerfectTemplateProvider perfect;

	private ExpressionProvider expressionTranslator;
	private PredicateProvider predicateTranslator;
	
	private OperationTranslator operationTranslator;
	
	private ObjectZDefinition objectZDefinition;
	private ObjectZClass	  currentClass;
	private IScope			  currentScope;
	private IOperation        currentOperation;
	
	public TranslateToPerfectTemplates(ParseTreeProperty<ST> treeTemplates,
			PerfectTemplateProvider perfect, ParseTreeProperty<Idents> usedIdentifiers,
			TypeEvaluator typeEvaluator) {
		super();
		this.treeTemplates = treeTemplates;
		this.usedIdentifiers = usedIdentifiers;
		this.typeEvaluator = typeEvaluator;
		
		this.perfect = PerfectTemplateProvider.getInstance();
		
		this.treePredicates = new ParseTreeProperty<Axioms>();
		this.treeDeclarations = new ParseTreeProperty<Declarations>();
		this.treeDeclarationNames = new ParseTreeProperty<List<Ident>>();
		this.treeExpressionTypes = new ParseTreeProperty<Type>();
		
//		this.typeEvaluator.register(treeExpressionTypes);
		
		this.expressionTranslator = new ExpressionProvider(treeTemplates, typeEvaluator);
//		this.predicateTranslator = new PredicateProvider(treeTemplates, null, typeEvaluator);
//		this.operationTranslator = new OperationTranslator(perfect);
	}
	
	
}
