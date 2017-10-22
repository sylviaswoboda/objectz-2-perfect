package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Elements;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion.OutputPromotionContext;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;

public class OutputPromotions extends CombinedPostconditions {
	
	private static final OutputPromotions EMPTY_PROMOTIONS = new OutputPromotions();
	
	public static final OutputPromotions empty(){
		return EMPTY_PROMOTIONS;
	}

	private Elements<OutputPromotion> elements;
	private OutputPromotionContext context;
	

	public OutputPromotions(OutputPromotionContext context){
		this.elements = new Elements<OutputPromotion>();
		this.context = context;
	}
	
	public OutputPromotions(){
		this(OutputPromotionContext.FUNCTION);
	}

	public OutputPromotions(OutputPromotion...outputPromotions){
		this();
		if (outputPromotions!= null){
			this.elements.addNew(outputPromotions);
		}
	}
	
	public OutputPromotions(OutputPromotions outputPromotions) {
		this();
		this.addAll(outputPromotions);
	}
	
	public OutputPromotions(OutputPromotions outputPromotions, OutputPromotionContext context) {
		this(context);
		this.addAll(outputPromotions);
	}
	
	public List<OutputPromotion> getElements(){
		return this.elements.asList();
	}
	
	public void addAll(OutputPromotions outputPromotions) {
		if (outputPromotions != null){
			for (OutputPromotion promo: outputPromotions.getElements()){
				if (!this.elements.contains(promo)){
					this.elements.add(promo);
				}
			}
			this.changeContext(context);
		}
	}
	public boolean add(OutputPromotion outputPromotion){
		boolean result = this.elements.add(outputPromotion);
		this.changeContext(context);
		return result;
	}
	
	public void changeContext(OutputPromotionContext context){
		this.context = context;
		for(OutputPromotion outputPromotion: this.getElements()){
			outputPromotion.changeContext(context);
		}
	}
	
	public OutputPromotionContext getContext(){
		return this.context;
	}

	/**
	 * Searches for outputPromotions not using any of the given parameters.
	 * These outputPromotions are then removed and returned as output.
	 * 
	 * @param undecoratedParameters
	 * @return all outputPromotions that do neither use the given parameters (or one of them) as input or output parameter
	 */
	public OutputPromotions removeWithoutParameters(Declarations undecoratedParameters) {
		OutputPromotions outputPromotions = new OutputPromotions(this.context);
		OutputPromotions outputPromotionsToRemove = new OutputPromotions(this.context);
		
		
		Declarations outputParameters = undecoratedParameters.getOutputCopies();
		Declarations inputParameters = undecoratedParameters.getInputCopies();
		
		for (OutputPromotion p: this.elements.asList()){
			if (!p.usesOutputParameters(outputParameters) && !p.usesInputParameters(inputParameters)){
				outputPromotions.add(p);
				outputPromotionsToRemove.add(p);
			}
		}
		this.elements.removeAll(outputPromotionsToRemove.getElements());
		return outputPromotions;
	}

	public OutputPromotions removeWithOutputParameters(Declarations outputVariables) {
		OutputPromotions outputPromotions = new OutputPromotions(this.context);
		OutputPromotions outputPromotionsToRemove = new OutputPromotions(this.context);
		
		for (OutputPromotion p: this.getElements()){
			if (p.usesOutputParameters(outputVariables)){
				outputPromotions.add(p);
				outputPromotionsToRemove.add(p);
			}
		}
		this.elements.removeAll(outputPromotionsToRemove.getElements());
		return outputPromotions;
	}

	
	public OutputPromotions getWithInputParameters(Declarations inputParameters) {
		OutputPromotions outputPromotions = new OutputPromotions(this.context);
		
		for (OutputPromotion p: this.getElements()){
			if (p.usesInputParameters(inputParameters)){
				outputPromotions.add(p);
			}
		}
		return outputPromotions;
	}

	public OutputPromotions getWithOutputParameters(Declarations outputParameters) {
		OutputPromotions outputPromotions = new OutputPromotions(this.context);
		
		for (OutputPromotion p: this.getElements()){
			if (p.usesOutputParameters(outputParameters)){
				outputPromotions.add(p);
			}
		}
		return outputPromotions;
	}
	
	private OutputPromotions getWithOutputParameter(Variable outputVariable) {
		OutputPromotions result = new OutputPromotions(context);
		OutputPromotion element = this.getOutputPromotion(outputVariable);
		if (element != null){
			result.add(element);
		}
		return result;
	}

	public OutputPromotion getOutputPromotion(Variable outputVariable) {
		for (OutputPromotion outputPromotion: this.getElements()){
			if (outputPromotion.usesOutputParameter(outputVariable)){
				return outputPromotion;
			}
		}
		return null;
	}

	public OutputPromotions removeCommunicationVariable(Variable communicationVariable) {
		OutputPromotions outputPromotions = new OutputPromotions(this.context);

		OutputPromotions promotionsToRemove = new OutputPromotions(this.context);
		
		for (OutputPromotion p: this.getElements()){
			if (p.usesOutputParameter(communicationVariable.getOutputCopy())){
				outputPromotions.add(p);
				promotionsToRemove.add(p);
			}
		}
		
		this.elements.removeAll(promotionsToRemove.getElements());
		return outputPromotions;
	}

	public OutputPromotions getWithoutOutputParameters(Declarations outputParameters) {
		OutputPromotions outputPromotions = new OutputPromotions(this.context);
		
		for (OutputPromotion p: this.getElements()){
			if (!p.usesOutputParameters(outputParameters)){
				outputPromotions.add(p);
			}
		}
		return outputPromotions;
	}

	@Override
	public List<ST> getTemplates() {
		List<ST> templates= new ArrayList<ST>();
		for(IPostcondition postcondition: this.getElements()){
			templates.add(postcondition.getTemplate());
		}
		return templates;
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap outerTempVarMap, Context context) {
		List<ST> templates= new ArrayList<ST>();
		for(OutputPromotion postcondition: this.getElements()){
			templates.add(postcondition.getTemplate(outerTempVarMap, context));
		}
		return templates;
	}

	@Override
	public Idents getUsedIdentifiers() {
		Idents identifiers = new Idents();
		for(OutputPromotion postcondition: this.getElements()){
			identifiers.addAll(postcondition.getIdentifiers());
		}
		return identifiers;
	}
	
	public OutputPromotions getWithSharedOutputParameters(OutputPromotions outputPromotions, Declarations sharedOutputVariables) {
		OutputPromotions sharedOutputPromotions = new OutputPromotions(OutputPromotionContext.SCHEMA);
		for (Variable sharedVariable: sharedOutputVariables.asList()){
			for (OutputPromotion outputPromotion: this.getElements()){
				if (outputPromotion.usesOutputParameter(sharedVariable)){
					for(OutputPromotion otherPromotion: outputPromotions.getElements()){
						if (otherPromotion.usesOutputParameter(sharedVariable)){
							sharedOutputPromotions.add(outputPromotion);
						}
					}
				}
			}
		}
		return sharedOutputPromotions;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("OutputPromotions[");
		
		for(ST template: getTemplates()){
			sb.append(template.render());
			sb.append(",\n");
		}
		
		sb.append("]");
		return sb.toString();
	}

	public Declarations getPromotedOutputVariables() {
		Declarations promotedOutputVariables = new Declarations();
		for(OutputPromotion elem: this.getElements()){
			promotedOutputVariables.add(elem.getOutputVariable());
		}
		return promotedOutputVariables;
	}

	public void removeSharedOutputDuplicates(Declarations sharedOutputVariables) {
		for (Variable sharedOutput: sharedOutputVariables.asList()){
			OutputPromotions sharedPromotions = this.getWithOutputParameter(sharedOutput);
			if (sharedPromotions.size() > 1){
				this.removeAll(sharedPromotions);
				this.add(sharedPromotions.get(0));
			}
		}
	}

	public OutputPromotion get(int i) {
		return this.elements.get(i);
	}

	public void remove(OutputPromotion outputPromotionToRemove) {
		this.elements.remove(outputPromotionToRemove);
	}

	public void removeAll(OutputPromotions outputPromotionsToRemove) {
		this.elements.removeAll(outputPromotionsToRemove.getElements());
	}

	public int size() {
		return this.elements.size();
	}

	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

//	private ICombinablePostconditions compose(ICompositionFactory compositionFactory, OutputPromotions outputPromotions, ICombinablePostconditions other, 
//			Declarations communicationParameters, Declarations sharedParameters) {
//		if (other instanceof EmptyPostconditions){
//			return compositionFactory.compose(this, (EmptyPostconditions)other);
//		} else if (other instanceof OutputPromotions){
//			return compositionFactory.compose(this, (OutputPromotions)other, communicationParameters, sharedParameters);
//		} else if (other instanceof ChangeOperationCalls){
//			return compositionFactory.compose((ChangeOperationCalls)other, this, communicationParameters, sharedParameters);
//		} else if (other instanceof ComplexOutputPromotions){
//			return compositionFactory.compose((ComplexOutputPromotions)other, this, communicationParameters, sharedParameters);
//		} else if (other instanceof ComplexChangePostcondition){
//			return compositionFactory.compose((ComplexChangePostcondition)other, this, communicationParameters, sharedParameters);
//		} else if (other instanceof ThenPostcondition){
//			return compositionFactory.compose((ThenPostcondition)other, this, communicationParameters, sharedParameters);
//		}
//		throw new ObjectZToPerfectTranslationException("Cannot compose OutputPromotions with other postconditions: " + other);
//	}
//
//	@Override
//	public ICombinablePostconditions createConjunction(ICombinablePostconditions other, Declarations sharedParameters) {
//		return compose(conjunctionFactory, this, other, Declarations.empty(), sharedParameters);
//	}
//
//	@Override
//	public ICombinablePostconditions createParallelComposition(ICombinablePostconditions other, Declarations communicationParameters, Declarations sharedParameters) {
//		return compose(parallelFactory, this, other, communicationParameters, sharedParameters);
//	}
//
//	@Override
//	public ICombinablePostconditions createAssociativeParallelComposition(ICombinablePostconditions other, 
//			Declarations communicationParameters, Declarations sharedParameters) {
//		return compose(assocParallelFactory, this, other, communicationParameters, sharedParameters);
//	}

	@Override
	public Declarations getSharedOutputVariables() {
		return Declarations.empty();
	}

	@Override
	public Declarations getVisibleCommunicationVariables() {
		return Declarations.empty();
	}

	@Override
	public Declarations getAllCommunicationVariables() {
		return Declarations.empty();
	}

	@Override
	public OutputPromotions getSimplePromotions() {
		return this;
	}

	@Override
	public OutputPromotions getCommunicatingPromotions() {
		return OutputPromotions.empty();
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
	public ChangeOperationCalls getUncategorizedCalls() {
		return ChangeOperationCalls.empty();
	}

	@Override
	public boolean isChangePostcondition() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutputPromotions other = (OutputPromotions) obj;
		if (context != other.context)
			return false;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

}
