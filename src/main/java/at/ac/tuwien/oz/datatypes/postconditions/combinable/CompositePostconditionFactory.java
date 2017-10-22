package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexChangePostconditionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexOutputPromotionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;

public class CompositePostconditionFactory {

	private CompositePostconditionDataCollector collector;
	
	private Map<String, List<ChangeOperationCall>> postconditionPerCallerMap;
	

	public CompositePostconditionFactory(CompositePostconditionDataCollector collector){
		this.collector = collector;
	}
	
	public ICombinablePostconditions createPostcondition(){
		if (hasCommonCallers()){
			return createThenPostcondition();
		} else if (hasCalls() && (hasCommunicationVars() || hasSharedVars() || hasPromotions())){
			return createCommunicationChangePostcondition();
		} else if (hasCalls()){
			return createChangeOperationCalls();
		} else if (hasPromotions() && hasPromotionsWithCommunication()){
			return createOutputPromotionsWithCommunication();
		} else if (hasPromotions()){
			return createOutputPromotions();
		} else {
			return EmptyPostconditions.empty();
		}
	}

	private boolean hasCommonCallers() {
		createPostconditionsPerCallerMap();
		
		for (List<ChangeOperationCall> postcondition : postconditionPerCallerMap.values()) {
			if (postcondition.size() > 1) {
				return true;
			}
		}
		return false;
	}

	private void createPostconditionsPerCallerMap() {
		Map<String, List<ChangeOperationCall>> postconditionPerCallerMap = new HashMap<String, List<ChangeOperationCall>>();
	
		for (ChangeOperationCall postcondition : collector.getAllCalls().asList()) {
			String caller = "self";
			if (postcondition.getCaller() != null){
				caller = postcondition.getCaller().render();
			}
			
			List<ChangeOperationCall> postconditionsOfCaller = postconditionPerCallerMap.get(caller);
	
			if (postconditionsOfCaller != null) {
				postconditionsOfCaller.add(postcondition);
			} else {
				postconditionsOfCaller = new ArrayList<ChangeOperationCall>();
				postconditionsOfCaller.add(postcondition);
				postconditionPerCallerMap.put(caller, postconditionsOfCaller);
			}
		}
		
		this.postconditionPerCallerMap = postconditionPerCallerMap;
	}

	
	private ICombinablePostconditions createOutputPromotions() {
		return new OutputPromotions(collector.getSimplePromotions());
	}

	private ICombinablePostconditions createOutputPromotionsWithCommunication() {
		ComplexOutputPromotionMapping mapping = 
				new ComplexOutputPromotionMapping(collector.getCommunicatingPromotions(), 
				collector.getCommunicationVariables(), collector.getVisibleCommunicationVariables());
		return new ComplexOutputPromotions(collector.getSimplePromotions(), mapping);
	}

	private ICombinablePostconditions createChangeOperationCalls(){
		return new ChangeOperationCalls(collector.getSimpleCalls());
	}

	private ICombinablePostconditions createCommunicationChangePostcondition() {
		ComplexChangePostconditionMapping mapping = 
				new ComplexChangePostconditionMapping(collector.getCommunicatingPromotions(), collector.getCommunicatingCalls(), 
						collector.getCommunicationVariables(), collector.getVisibleCommunicationVariables(), 
						collector.getSharedOutputVariables());
		return new ComplexChangePostcondition(collector.getSimplePromotions(), collector.getSimpleCalls(), mapping);
	}

	private ICombinablePostconditions createThenPostcondition() {
		ThenPostcondition result = new ThenPostcondition.Builder(this.postconditionPerCallerMap)
				.withSimpleOutputPromotions(collector.getSimplePromotions())
				.withCommunicatingOutputPromotions(collector.getCommunicatingPromotions())
				.withCommunicatingVariables(collector.getCommunicationVariables())
				.withVisibleCommunicatingVariables(collector.getVisibleCommunicationVariables())
				.withSharedOutputVariables(collector.getSharedOutputVariables())
				.build();
		result.checkCommunicationOrder();
		return result;
	}

	private boolean hasPromotionsWithCommunication() {
		if (hasVisibleCommunicationVars()){
			return true;
		} else if (!collector.getCommunicatingPromotions().getWithInputParameters(collector.getCommunicationVariables().getInputCopies()).isEmpty()) {
			return true;
		}
		return false;
	}

	
	private boolean hasCalls() {
		return !this.collector.getAllCalls().isEmpty();
	}

	private boolean hasPromotions() {
		if (this.collector.getSimplePromotions().isEmpty() && this.collector.getCommunicatingPromotions().isEmpty()){
			return false;
		}
		return true;
	}

	private boolean hasSharedVars() {
		return !this.collector.getSharedOutputVariables().isEmpty();
	}

	private boolean hasCommunicationVars() {
		return !this.collector.getCommunicationVariables().isEmpty();
	}
	private boolean hasVisibleCommunicationVars() {
		return !this.collector.getVisibleCommunicationVariables().isEmpty();
	}
}
