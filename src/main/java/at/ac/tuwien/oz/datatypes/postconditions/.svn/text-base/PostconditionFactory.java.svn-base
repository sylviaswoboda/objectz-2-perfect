package at.ac.tuwien.oz.datatypes.postconditions;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.CompositePostconditionDataCollector;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.CompositePostconditionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.OutputPromotions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICompositionFactory;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions.PreconditionItem;
import at.ac.tuwien.oz.definitions.operation.OperationPromotion;
import at.ac.tuwien.oz.definitions.operation.ScopeEnrichment;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class PostconditionFactory implements ICompositionFactory {

	private static PostconditionFactory postconditionFactory;
	
	public static void setInstance(PostconditionFactory factory){
		postconditionFactory = factory;
	}
	
	public static PostconditionFactory getInstance(){
		if (postconditionFactory == null){
			postconditionFactory = new PostconditionFactory();
		}
		return postconditionFactory;
	}
	
	public SimpleFunctionPostcondition createOutputVariablePredicate(Axiom postcondition, Declarations outputParameters) {
		return new SimpleFunctionPostcondition(postcondition.getIdentifiers(), postcondition.getPredicate(), outputParameters);
	}
	public SimpleSchemaPostcondition createSchemaPredicate(Axiom originalPredicate, Declarations outputParameters) {
		return 	new SimpleSchemaPostcondition(originalPredicate.getIdentifiers(), originalPredicate.getPredicate(), outputParameters.getIdentifiers());
	}

	public IPostcondition createPrimedOutputVariablePostcondition(Axiom postcondition, Idents outputVariableIdents) {
		return new SimpleSchemaPostcondition(postcondition.getIdentifiers(), postcondition.getPredicate(), outputVariableIdents);
	}
	
	public ICombinablePostconditions createOutputPromotions(OperationPromotion opPromo, Declarations outputParameters) {
		OutputPromotions outputPromotions = new OutputPromotions(OutputPromotionContext.FUNCTION);
		for (Variable outputVar: outputParameters.asList()){
			OutputPromotion outputPromotion = new OutputPromotion(outputVar, opPromo, OutputPromotionContext.FUNCTION);
			outputPromotions.add(outputPromotion);
		}
		return outputPromotions;
	}
	public IPostcondition createChangeOperationCall(OperationPromotion opPromo){
		return new ChangeOperationCall(opPromo);
	}

	public ICombinablePostconditions compose(ICombinablePostconditions left, ICombinablePostconditions right,
			Declarations communicationVariables, Declarations sharedOutputVariables, boolean isAssociative){
		CompositePostconditionDataCollector data = new CompositePostconditionDataCollector(left, right);
		data.compose(communicationVariables, sharedOutputVariables, isAssociative);
		return new CompositePostconditionFactory(data).createPostcondition();
	}
	
	public ICombinablePostconditions conjoin(ICombinablePostconditions originalLeft, ICombinablePostconditions originalRight,
			Declarations sharedOutputVariables){
		CompositePostconditionDataCollector data = new CompositePostconditionDataCollector(originalLeft, originalRight);
		data.conjoin(sharedOutputVariables);
		return new CompositePostconditionFactory(data).createPostcondition();
	}
	
	public NonDeterministicPostconditions nondeterministicallyChoose(IComposableOperation left, IComposableOperation right, Context context){
		NonDeterministicPostconditions newPostconditions = new NonDeterministicPostconditions(context);
		
		if (left.isNonDeterministicChoice()){
			newPostconditions.addAll(left.getNonDeterministicPostconditions());
		} else {
			PreconditionItem preconditionItem = new PreconditionItem(left.getComposablePreconditions(), left.getPromotedOperations());
			NonDeterministicChoiceItem choiceItemLeft = new NonDeterministicChoiceItem(preconditionItem, left.getComposablePostconditions(), context);
			newPostconditions.add(choiceItemLeft);
		}
		
		if (right.isNonDeterministicChoice()){
			newPostconditions.addAll(right.getNonDeterministicPostconditions());
		} else {
			PreconditionItem preconditionItem = new PreconditionItem(right.getComposablePreconditions(), right.getPromotedOperations());
			NonDeterministicChoiceItem choiceItemRight = new NonDeterministicChoiceItem(preconditionItem, right.getComposablePostconditions(), context);
			newPostconditions.add(choiceItemRight);
		}
		
		return newPostconditions;
	}

	
	public SequentialPostconditions sequentiallyCompose(IComposableOperation left, IComposableOperation right, Declarations communicationVariables, Declarations sharedVariables, Context context){
		List<IComposablePostconditions> newPostconditions = new ArrayList<IComposablePostconditions>();
		List<Declarations> newCommunicationVariables = new ArrayList<Declarations>();
		
		
		if (left.isSequentialComposition()){
			integrateSequentialPostcondition(left, sharedVariables, newCommunicationVariables, newPostconditions);
		} else {
			// shared variables are always only eliminated on the left side
			if (Context.FUNCTION.equals(context)){
				newPostconditions.add(left.getComposablePostconditionsWithoutPromotion(sharedVariables));
			} else {
				newPostconditions.add(left.getComposablePostconditions());
			}
		}
		if (right.isSequentialComposition()){
			integrateSequentialPostcondition(right, sharedVariables, newCommunicationVariables, newPostconditions);
		} else {
			newPostconditions.add(right.getComposablePostconditions());
		}
		newCommunicationVariables.add(communicationVariables);
		
		
		return new SequentialPostconditions(newPostconditions, newCommunicationVariables, context);
		
	}

	private void integrateSequentialPostcondition(IComposableOperation operation,
			Declarations sharedVariables, List<Declarations> newCommunicationVariables,
			List<IComposablePostconditions> newPostconditions) {
		
		SequentialPostconditions seqPostconditions;
		if (sharedVariables == null || sharedVariables.isEmpty()){
			seqPostconditions = operation.getSequentialPostconditions();
		} else {
			seqPostconditions = operation.getSequentialPostconditionsWithoutPromotion(sharedVariables);
		}
		if (seqPostconditions != null &&  !seqPostconditions.isEmpty()){
			newPostconditions.addAll(seqPostconditions.getPostconditions());
			newCommunicationVariables.addAll(seqPostconditions.getCommunicationVariables());
		}
	}
	
	public IPostcondition createVariableTypePostcondition(Variable v, Idents usedIdentifiers) {
		PerfectTemplateProvider perfect = PerfectTemplateProvider.getInstance();
		ST varST = perfect.getId(v.getId());
		ST varTypeST = v.getDeclaredTypeTemplate();
		ST inST = perfect.getElem(varST, varTypeST);
		return new SimplePostcondition(inST, usedIdentifiers);
	}

	
	public IPostconditions createVarPostconditionForScopeEnrichment(ScopeEnrichment scopeEnrichment) {
		SimplePostconditions simple = new SimplePostconditions();
		Variable auxiliaryVariable = scopeEnrichment.getAuxiliaryVariable();
		List<ST> auxiliaryVariablePredicates = scopeEnrichment.getAuxiliaryVariablePostconditions().getTemplates();
		auxiliaryVariablePredicates.addAll(scopeEnrichment.getPromotedPreconditions().getTemplates());
		List<ST> postconditions = scopeEnrichment.getPromotedPostconditions().getTemplates();
		Idents usedIdentifiers = scopeEnrichment.getPromotedPostconditions().getUsedIdentifiers();
		
		ST postcondition = PerfectPredicateTemplateProvider.getInstance()
				.createVarPostconditionForScopeEnrichment(auxiliaryVariable, auxiliaryVariablePredicates, postconditions);
		SimplePostcondition newPostcondition = new SimplePostcondition(postcondition, usedIdentifiers);
		simple.add(newPostcondition);
		return simple;
	}



}
