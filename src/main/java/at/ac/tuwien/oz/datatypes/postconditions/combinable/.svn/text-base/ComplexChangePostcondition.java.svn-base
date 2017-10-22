package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexChangePostconditionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;



public class ComplexChangePostcondition extends CombinedPostconditions{

	private OutputPromotions simpleOutputPromotions;
	private ChangeOperationCalls simpleChangeOperationCalls;
	
	private ComplexChangePostconditionMapping mapping;
	
	
	
	/**
	 * Constructor for non communicating postconditions involving output promotions and change operations
	 * the original operations share output variables between an output promotion and a change operation.
	 * 
	 * @param simpleOutputPromotions
	 * @param simpleChangeOperationCalls
	 */
	public ComplexChangePostcondition(OutputPromotions simpleOutputPromotions,
			ChangeOperationCalls simpleChangeOperationCalls) {
		this.simpleOutputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		this.simpleChangeOperationCalls = new ChangeOperationCalls();
		this.mapping = null;
		
		this.simpleOutputPromotions.addAll(simpleOutputPromotions);
		this.simpleChangeOperationCalls.addAll(simpleChangeOperationCalls);
	}
	/**
	 * Constructor for shared output between change operation calls and/or communication
	 * 
	 * @param simpleOutputPromotions
	 * @param simpleChangeOperationCalls
	 * @param mapping
	 */
	public ComplexChangePostcondition(OutputPromotions simpleOutputPromotions,
			ChangeOperationCalls simpleChangeOperationCalls, 
			ComplexChangePostconditionMapping mapping) {
		this(simpleOutputPromotions, simpleChangeOperationCalls);
		this.mapping = mapping;
	}
	/**
	 * Copy constructor
	 * @param original
	 */
	public ComplexChangePostcondition(ComplexChangePostcondition original){
		this(original.simpleOutputPromotions, original.simpleChangeOperationCalls, original.mapping);
	}
	
	
	@Override
	public List<ST> getTemplates() {
		List<ST> templates = new ArrayList<ST>();
		templates.addAll(simpleOutputPromotions.getTemplates());
		templates.addAll(simpleChangeOperationCalls.getTemplates());
		if (mapping != null && !mapping.isEmpty()){
			templates.add(mapping.getTemplate());
		}
		return templates;
	}
	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap, Context context) {
		List<ST> templates = new ArrayList<ST>();
		templates.addAll(simpleOutputPromotions.getSequentialTemplates(tempVarMap, context));
		templates.addAll(simpleChangeOperationCalls.getSequentialTemplates(tempVarMap, context));
		if (mapping != null && !mapping.isEmpty()){
			templates.add(mapping.getTemplate(tempVarMap, context));
		}
		return templates;
	}
	
	@Override
	public Idents getUsedIdentifiers() {
		Idents identifiers = new Idents();
		for(OutputPromotion postcondition: this.simpleOutputPromotions.getElements()){
			identifiers.addAll(postcondition.getIdentifiers());
		}
		for(ChangeOperationCall postcondition: this.simpleChangeOperationCalls.asList()){
			identifiers.addAll(postcondition.getIdentifiers());
		}
		for(OutputPromotion postcondition: this.getMapping().getOutputPromotions().getElements()){
			identifiers.addAll(postcondition.getIdentifiers());
		}
		for(OutputPromotion postcondition: this.getMapping().getCommunicationOutputPromotions().getElements()){
			identifiers.addAll(postcondition.getIdentifiers());
		}
		for(ChangeOperationCall postcondition: this.getMapping().getCommunicationChangeOperationCalls().asList()){
			identifiers.addAll(postcondition.getIdentifiers());
		}
		return identifiers;
	}
	public OutputPromotions getAllOutputPromotions() {
		OutputPromotions allOutputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		allOutputPromotions.addAll(this.simpleOutputPromotions);
		if (mapping != null){
			allOutputPromotions.addAll(mapping.getOutputPromotions());
			allOutputPromotions.addAll(mapping.getCommunicationOutputPromotions());
		}
		return allOutputPromotions;
	}

	public ChangeOperationCalls getAllChangeOperationCalls() {
		ChangeOperationCalls allChangeOperationCalls = new ChangeOperationCalls();
		allChangeOperationCalls.addAll(this.simpleChangeOperationCalls);
		if (mapping != null){
			allChangeOperationCalls.addAll(mapping.getCommunicationChangeOperationCalls());
		}
		return allChangeOperationCalls;
	}

	public ComplexChangePostconditionMapping getMapping() {
		return this.mapping;
	}

//	private ICombinablePostconditions compose(ICompositionFactory compositionFactory, ICombinablePostconditions other, 
//			Declarations communicationParameters, Declarations sharedParameters) {
//		if (other instanceof EmptyPostconditions){
//			return compositionFactory.compose(this, (EmptyPostconditions)other);
//		} else if (other instanceof OutputPromotions){
//			return compositionFactory.compose(this, (OutputPromotions)other, communicationParameters, sharedParameters);
//		} else if (other instanceof ChangeOperationCalls){
//			return compositionFactory.compose(this, (ChangeOperationCalls)other, communicationParameters, sharedParameters);
//		} else if (other instanceof ComplexOutputPromotions){
//			return compositionFactory.compose(this, (ComplexOutputPromotions)other, communicationParameters, sharedParameters);
//		} else if (other instanceof ComplexChangePostcondition){
//			return compositionFactory.compose(this, (ComplexChangePostcondition)other, communicationParameters, sharedParameters);
//		} else if (other instanceof ThenPostcondition){
//			return compositionFactory.compose((ThenPostcondition)other, this, communicationParameters, sharedParameters);
//		}
//		throw new ObjectZToPerfectTranslationException("Cannot combine CommunicationAndSharedOutputPostcondition with other postconditions: " + other);
//	}

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
		if (mapping != null){
			return mapping.getAllCommunicationVariables();
		}
		return Declarations.empty();
	}

	public Declarations getVisibleCommunicationVariables(){
		if (mapping != null){
			return mapping.getVisibleCommunicationVariables();
		}
		
		return Declarations.empty();
	}

	public Declarations getSharedOutputVariables() {
		if (mapping != null){
			return mapping.getSharedOutputVariables();
		}
		return Declarations.empty();
	}

	public OutputPromotions getSimplePromotions() {
		return this.simpleOutputPromotions;
	}

	public OutputPromotions getCommunicatingPromotions() {
		OutputPromotions communicationOutputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		if (mapping != null){
			communicationOutputPromotions.addAll(mapping.getOutputPromotions());
			communicationOutputPromotions.addAll(mapping.getCommunicationOutputPromotions());
		}
		return communicationOutputPromotions;
	}

	public ChangeOperationCalls getSimpleCalls() {
		return this.simpleChangeOperationCalls;
	}

	public ChangeOperationCalls getCommunicatingCalls() {
		if (mapping != null){
			return mapping.getCommunicationChangeOperationCalls();
		}
		return ChangeOperationCalls.empty();
	}

	@Override
	public ChangeOperationCalls getUncategorizedCalls() {
		return ChangeOperationCalls.empty();
	}

	@Override
	public boolean isChangePostcondition() {
		return true;
	}
	@Override
	public boolean isEmpty() {
		return simpleChangeOperationCalls.isEmpty() && simpleOutputPromotions.isEmpty() && 
				(mapping == null || mapping.isEmpty());
	}
}
