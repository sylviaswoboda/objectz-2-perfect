package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICommunicatingAndSharingPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;


/**
 * Represents a Postcondition of output promotions without input communication and 
 * sequentially arranged change operation calls.
 * Communication may therefore be only one directional (from first operation to last)
 * 
 * @author sylvias
 *
 */
public class ThenPostcondition extends CombinedPostconditions implements ICommunicatingAndSharingPostcondition{

	private static final int INDEX_NOT_FOUND = -1;
	
	private OutputPromotions simpleOutputPromotions;
	private OutputPromotions communicatingOutputPromotions;
	private Map<String, List<ChangeOperationCall>> postconditionsPerCallerMap;
	
	private Declarations allCommunicationVariables;
	private Declarations visibleCommunicationVariables;
	private Declarations sharedOutputVariables;
	
	private PerfectPredicateTemplateProvider templateProvider;
	
	private ThenPostcondition(){
		this.templateProvider = PerfectPredicateTemplateProvider.getInstance();
		
		this.postconditionsPerCallerMap = new HashMap<String, List<ChangeOperationCall>>();
		this.simpleOutputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		this.communicatingOutputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		
		this.allCommunicationVariables = new Declarations();
		this.visibleCommunicationVariables = new Declarations();
		this.sharedOutputVariables = new Declarations();
	}
	
	public ThenPostcondition(ThenPostcondition thenPostcondition){
		this();
		this.simpleOutputPromotions.addAll(thenPostcondition.getSimplePromotions());
		this.communicatingOutputPromotions.addAll(thenPostcondition.getCommunicatingPromotions());
		this.postconditionsPerCallerMap.putAll(thenPostcondition.getPostconditionPerCallerMap());
		this.allCommunicationVariables.addAll(thenPostcondition.getAllCommunicationVariables());
		this.visibleCommunicationVariables.addAll(thenPostcondition.getVisibleCommunicationVariables());
		this.sharedOutputVariables.addAll(thenPostcondition.getSharedOutputVariables());
	}
	
	public static class Builder{
		private ThenPostcondition thenPostcondition;
		
		public Builder (Map<String, List<ChangeOperationCall>> operationCallMap){
			this.thenPostcondition = new ThenPostcondition();
			this.thenPostcondition.postconditionsPerCallerMap.putAll(operationCallMap);
		}
		public Builder withSimpleOutputPromotions(OutputPromotions simpleOutputPromotions){
			this.thenPostcondition.simpleOutputPromotions.addAll(simpleOutputPromotions);
			return this;
		}
		public Builder withCommunicatingOutputPromotions(OutputPromotions communicatingOutputPromotions){
			this.thenPostcondition.communicatingOutputPromotions.addAll(communicatingOutputPromotions);
			return this;
		}
		public Builder withCommunicatingVariables(Declarations communicationVariables){
			this.thenPostcondition.allCommunicationVariables.addAll(communicationVariables);
			return this;
		}
		public Builder withVisibleCommunicatingVariables(Declarations visibleCommunicationVariables){
			this.thenPostcondition.visibleCommunicationVariables.addAll(visibleCommunicationVariables);
			return this;
		}
		public Builder withSharedOutputVariables(Declarations sharedOutputVariables){
			this.thenPostcondition.sharedOutputVariables.addAll(sharedOutputVariables);
			return this;
		}
		public ThenPostcondition build(){
			for (String caller : this.thenPostcondition.postconditionsPerCallerMap.keySet()) {
				List<ChangeOperationCall> postconditionsPerCaller = this.thenPostcondition.postconditionsPerCallerMap.get(caller);
				PostconditionOrderingCalculator<ChangeOperationCall> orderingCalculator = new PostconditionOrderingCalculator<ChangeOperationCall>();
				postconditionsPerCaller = orderingCalculator.order(postconditionsPerCaller);
				this.thenPostcondition.postconditionsPerCallerMap.put(caller, postconditionsPerCaller);
			}
			
			return this.thenPostcondition;
		}
	}

	@Override
	public List<ST> getTemplates() {
		List<ST> templates = new ArrayList<ST>();
		templates.addAll(simpleOutputPromotions.getTemplates());
		templates.add(this.templateProvider.createThenPostcondition(this));
		return templates;
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap, Context context) {
		List<ST> templates = new ArrayList<ST>();
		templates.addAll(simpleOutputPromotions.getSequentialTemplates(tempVarMap, context));
		templates.add(this.templateProvider.createThenPostcondition(this, tempVarMap));
		return templates;
	}

	@Override
	public Idents getUsedIdentifiers() {
		Idents usedIdentifiers = new Idents();
		usedIdentifiers.addAll(this.simpleOutputPromotions.getUsedIdentifiers());
		usedIdentifiers.addAll(this.communicatingOutputPromotions.getUsedIdentifiers());
		for (List<ChangeOperationCall> postconditionsPerCaller: postconditionsPerCallerMap.values()){
			for(ChangeOperationCall postcondition: postconditionsPerCaller){
				usedIdentifiers.addAll(postcondition.getIdentifiers());
			}
		}
		return null;
	}

	public List<List<ChangeOperationCall>> getPostconditions() {
		List<List<ChangeOperationCall>> postconditions = new ArrayList<List<ChangeOperationCall>>();
		for (List<ChangeOperationCall> postconditionsPerCaller: postconditionsPerCallerMap.values()){
			for(int i = 0; i < postconditionsPerCaller.size(); i++){
				ChangeOperationCall postconditionPerCaller = postconditionsPerCaller.get(i);
				List<ChangeOperationCall> postconditionsPerPosition = null;
				if (i < postconditions.size()){
					postconditionsPerPosition = postconditions.get(i);
				}
				if (postconditionsPerPosition == null){
					postconditionsPerPosition = new ArrayList<ChangeOperationCall>();
					postconditions.add(postconditionsPerPosition);
				}
				postconditionsPerPosition.add(postconditionPerCaller);
			}
		}
		return postconditions;
	}

	public int getNumberOfSharingPromotions(Variable sharedOutputVariable) {
		int numberOfSharingPromotions = 0;
		
		for (OutputPromotion outputPromotion: simpleOutputPromotions.getElements()){
			if (outputPromotion.usesOutputParameter(sharedOutputVariable)){
				numberOfSharingPromotions++;
			}
		}
		for (ChangeOperationCall changeOperationCall: getChangeOperationCalls()){
			if (changeOperationCall.usesOutputParameter(sharedOutputVariable)){
				numberOfSharingPromotions++;
			}
		}

		return numberOfSharingPromotions;
	}

	private List<ChangeOperationCall> getChangeOperationCalls() {
		return getUncategorizedCalls().asList();
	}
	
	public Map<String, List<ChangeOperationCall>> getPostconditionPerCallerMap() {
		return this.postconditionsPerCallerMap;
	}

	public void checkCommunicationOrder() {
		
		for (Variable commVar: allCommunicationVariables.asList()){
			int inVarIndex = getIndexOfVariable(commVar.getInputCopy());
			int outVarIndex = getIndexOfVariable(commVar.getOutputCopy());
			
			if (inVarIndex == INDEX_NOT_FOUND || outVarIndex == INDEX_NOT_FOUND){
				throw new ObjectZToPerfectTranslationException("Cannot find communication variable: " + commVar);
			}
			
			if (inVarIndex < outVarIndex){
				throw new ObjectZToPerfectTranslationException("Mapping not possible because of communication variable order.");
			}
		}
		
	}

	private int getIndexOfVariable(Variable variable) {
		for (OutputPromotion promotion: this.communicatingOutputPromotions.getElements()){
			if (promotion.usesInputParameter(variable) || promotion.usesOutputParameter(variable)){
				return 0;
			}
		}
	
		List<List<ChangeOperationCall>> changingPostconditions = getPostconditions();
		
		for (int i = 0; i < changingPostconditions.size(); i++){
			for (ChangeOperationCall changingPostcondition: changingPostconditions.get(i)){
				if (changingPostcondition.usesInputParameters(variable)){
					return i;
				}
				if (changingPostcondition.usesOutputParameter(variable)){
					return i;
				}
			}
		}
		return INDEX_NOT_FOUND;
	}

//	private ICombinablePostconditions compose(ICompositionFactory compositionFactory, ICombinablePostconditions other,
//			Declarations communicationParameters, Declarations sharedOutputParameters) {
//		if (other instanceof EmptyPostconditions){
//			return compositionFactory.compose(this, (EmptyPostconditions)other);
//		} else if (other instanceof OutputPromotions){
//			return compositionFactory.compose(this, (OutputPromotions)other, communicationParameters, sharedOutputParameters);
//		} else if (other instanceof ComplexOutputPromotions){
//			return compositionFactory.compose(this, (ComplexOutputPromotions)other, communicationParameters, sharedOutputParameters);
//		} else if (other instanceof ChangeOperationCalls){
//			return compositionFactory.compose(this, (ChangeOperationCalls)other, communicationParameters, sharedOutputParameters);
//		} else if (other instanceof ComplexChangePostcondition){
//			return compositionFactory.compose(this, (ComplexChangePostcondition) other, communicationParameters, sharedOutputParameters);
//		} else if (other instanceof ThenPostcondition){
//			return compositionFactory.compose(this, (ThenPostcondition)other, communicationParameters, sharedOutputParameters);
//		}
//		throw new ObjectZToPerfectTranslationException("Not yet possible to parallely compose " + this.getClass() + " with " + other.getClass() + ".");
//	}
//
//	@Override
//	public ICombinablePostconditions createConjunction(ICombinablePostconditions other, Declarations sharedParameters) {
//		return compose(conjunctionFactory, other, Declarations.empty(), sharedParameters);
//	}
//
//	@Override
//	public ICombinablePostconditions createParallelComposition(ICombinablePostconditions other,
//			Declarations communicationParameters, Declarations sharedParameters) {
//		return compose(parallelFactory, other, communicationParameters, sharedParameters);
//	}
//
//	@Override
//	public ICombinablePostconditions createAssociativeParallelComposition(ICombinablePostconditions other,
//			Declarations communicationParameters, Declarations sharedParameters) {
//		return compose(assocParallelFactory, other, communicationParameters, sharedParameters);
//	}

	public Declarations getAllCommunicationVariables() {
		return this.allCommunicationVariables;
	}

	public Declarations getVisibleCommunicationVariables() {
		return this.visibleCommunicationVariables;
	}

	public Declarations getSharedOutputVariables() {
		return this.sharedOutputVariables;
	}

	public OutputPromotions getSimplePromotions() {
		return this.simpleOutputPromotions;
	}

	public OutputPromotions getCommunicatingPromotions() {
		return this.communicatingOutputPromotions;
	}

	@Override
	public ChangeOperationCalls getSimpleCalls() {
		return ChangeOperationCalls.empty();
	}

	@Override
	public ChangeOperationCalls getCommunicatingCalls() {
		return ChangeOperationCalls.empty();
	}

	@Override
	public ChangeOperationCalls getUncategorizedCalls(){
		ChangeOperationCalls allChangeOperationCalls = new ChangeOperationCalls();
		for (List<ChangeOperationCall> callsPerCaller: postconditionsPerCallerMap.values()){
			allChangeOperationCalls.addAll(callsPerCaller);
		}
		return allChangeOperationCalls;
	}

	@Override
	public boolean isChangePostcondition() {
		return false;
	}

	public Map<Variable, Integer> getCommunicationOutputVariableIndices() {
		Map<Variable, Integer> commVariableIndexMap = new HashMap<Variable, Integer>();
		
		for (Variable commVar: allCommunicationVariables.asList()){
			int index = getIndexOfVariable(commVar.getOutputCopy());
			commVariableIndexMap.put(commVar.getOutputCopy(), index);
		}
		return commVariableIndexMap;
	}

	@Override
	public boolean isEmpty() {
		return this.communicatingOutputPromotions.isEmpty() && this.postconditionsPerCallerMap.isEmpty() &&
				this.simpleOutputPromotions.isEmpty();
	}

}
