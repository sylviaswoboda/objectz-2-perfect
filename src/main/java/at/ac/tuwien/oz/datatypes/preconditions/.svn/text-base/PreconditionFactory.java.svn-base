package at.ac.tuwien.oz.datatypes.preconditions;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.CompositePreconditionDataCollector;
import at.ac.tuwien.oz.datatypes.preconditions.combinable.CompositePreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions.PreconditionItem;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;
import at.ac.tuwien.oz.definitions.operation.interfaces.ICombinableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.translator.templates.PerfectPreconditionTemplateProvider;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class PreconditionFactory {

	private static PreconditionFactory preconditionFactory;

	public static void setInstance(PreconditionFactory factory){
		preconditionFactory = factory;
	}

	public static PreconditionFactory getInstance(){
		if (preconditionFactory == null){
			preconditionFactory = new PreconditionFactory();
		}
		return preconditionFactory;
	}
	
	public IPrecondition createSimplePrecondition(Axiom precondition) {
		return new SimplePrecondition(precondition.getPredicate());
	}
	
	public IPrecondition createVariableTypePrecondition(Variable v) {
		PerfectTemplateProvider perfect = PerfectTemplateProvider.getInstance();
		ST varST = perfect.getId(v.getId());
		ST varTypeST = v.getDeclaredTypeTemplate();
		ST inST = perfect.getElem(varST, varTypeST);
		return new SimplePrecondition(inST);
	}

	public IPrecondition createImplicitFunctionPrecondition(Declarations outputParameters, List<ST> implicitPreconditions) {
		PerfectPredicateTemplateProvider perfect = PerfectPreconditionTemplateProvider.getInstance();
		ST implicitPrecondition = perfect.createImplicitFunctionPrecondition(outputParameters, implicitPreconditions);
		return new SimplePrecondition(implicitPrecondition);
	}
	
	public IPrecondition createImplicitSchemaPreconditions(Declarations deltaVariables, Declarations outputParameters,
			List<ST> implicitPreconditions) {
		PerfectPredicateTemplateProvider perfect = PerfectPreconditionTemplateProvider.getInstance();
		ST implicitPrecondition = perfect.createImplicitSchemaPreconditions(deltaVariables, outputParameters, implicitPreconditions);
		return new SimplePrecondition(implicitPrecondition);
	}

	public PreconditionFunctionCall createPreconditionFunctionCall(ST caller, PreconditionFunction preconditionFunction){
		if (preconditionFunction != null){
			return new PreconditionFunctionCall(caller, preconditionFunction);
		}
		return null;
	}
	
	public ICombinablePreconditions compose(ICombinableOperation left, ICombinableOperation right, 
				Declarations communicationVariables, Declarations sharedVariables){
		CompositePreconditionDataCollector data = new CompositePreconditionDataCollector(left, right);
		data.compose(communicationVariables, sharedVariables);
		return new CompositePreconditionFactory(data).createPrecondition();
	}
	
	public ICombinablePreconditions conjoin(ICombinableOperation left, ICombinableOperation right, 
				Declarations sharedVariables){
		CompositePreconditionDataCollector data = new CompositePreconditionDataCollector(left, right);
		data.conjoin(sharedVariables);
		return new CompositePreconditionFactory(data).createPrecondition();
	}
	
	public SequentialPreconditions sequentiallyCompose(IComposableOperation left, IComposableOperation right, Declarations communicationVariables){
		
		List<IComposablePreconditions> newPreconditions = new ArrayList<IComposablePreconditions>();
		List<Declarations> newCommunicationVariables = new ArrayList<Declarations>();
		
		if (left.isSequentialComposition()){
			integrateSequentialPrecondition(left, newCommunicationVariables, newPreconditions);
		} else {
			newPreconditions.add(left.getComposablePreconditions());
		}
		newCommunicationVariables.add(communicationVariables);
		
		if (right.isSequentialComposition()){
			integrateSequentialPrecondition(right, newCommunicationVariables, newPreconditions);
		} else {
			newPreconditions.add(right.getComposablePreconditions());
		}
		
		return new SequentialPreconditions(newPreconditions, newCommunicationVariables);
	}
	
	private void integrateSequentialPrecondition(IComposableOperation sequentialOperation,
			List<Declarations> newCommunicationVariables, List<IComposablePreconditions> newPreconditions) {
		
		SequentialPreconditions seqPreconditions = sequentialOperation.getSequentialPreconditions();
		newPreconditions.addAll(seqPreconditions.getPreconditions());
		
		newCommunicationVariables.addAll(seqPreconditions.getCommunicationVariables());
	}
	
	public NonDeterministicPreconditions nondeterministicallyChoose(IComposableOperation left, IComposableOperation right){
		List<PreconditionItem> newPreconditionItems = new ArrayList<PreconditionItem>();
		
		if (left.isNonDeterministicChoice()){
			integrateNonDeterministicPrecondition(left, newPreconditionItems);
		} else {
			PreconditionItem newItem = new PreconditionItem(left.getComposablePreconditions(), left.getPromotedOperations());
			newPreconditionItems.add(newItem);
		}
		
		if (right.isNonDeterministicChoice()){
			integrateNonDeterministicPrecondition(right, newPreconditionItems);
		} else {
			PreconditionItem newItem = new PreconditionItem(right.getComposablePreconditions(), right.getPromotedOperations());
			newPreconditionItems.add(newItem);
		}
		
		return new NonDeterministicPreconditions(newPreconditionItems);
	}

	private void integrateNonDeterministicPrecondition(IComposableOperation operation, List<PreconditionItem> newPreconditionItems) {
		
		NonDeterministicPreconditions nonDeterministicPreconditions = operation.getNonDeterministicPreconditions();
		newPreconditionItems.addAll(nonDeterministicPreconditions.getPreconditions());
		
	}

	public IPreconditions createExistsPrecondition(Declarations auxiliaryParameters, List<ST> preconditions) {
		SimplePreconditions simple = new SimplePreconditions();
		simple.add(new SimplePrecondition(PerfectPredicateTemplateProvider.getInstance().createExistsPreconditionForScopeEnrichment(auxiliaryParameters, preconditions)));
		return simple;
	}

}
