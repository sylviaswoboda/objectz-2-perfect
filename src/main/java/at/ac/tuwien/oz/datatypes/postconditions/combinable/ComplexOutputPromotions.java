package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ComplexOutputPromotionMapping;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPromotingPostcondition;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;

public class ComplexOutputPromotions extends CombinedPostconditions{

	private OutputPromotions simpleOutputPromotions;
	private ComplexOutputPromotionMapping mapping;
	
	private ComplexOutputPromotions(){
	}
	
	public ComplexOutputPromotions(OutputPromotions simpleOutputPromotions, ComplexOutputPromotionMapping mapping){
		this();
		this.simpleOutputPromotions = simpleOutputPromotions;
		this.mapping = mapping;
	}
	
	public ComplexOutputPromotions(ComplexOutputPromotions oldPostcondition) {
		this(oldPostcondition.getSimplePromotions(), oldPostcondition.getMapping());
	}

	public ComplexOutputPromotionMapping getMapping() {
		return mapping;
	}
	
	@Override
	public List<ST> getTemplates() {
		List<ST> templates= new ArrayList<ST>();
		templates.addAll(simpleOutputPromotions.getTemplates());
		templates.add(mapping.getTemplate());
		return templates;
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap, Context context) {
		List<ST> templates= new ArrayList<ST>();
		templates.addAll(simpleOutputPromotions.getSequentialTemplates(tempVarMap, context));
		templates.add(mapping.getTemplate(tempVarMap, context));
		return templates;
	}

	@Override
	public Idents getUsedIdentifiers() {
		Idents identifiers = new Idents();
		identifiers.addAll(simpleOutputPromotions.getUsedIdentifiers());
		identifiers.addAll(mapping.getIdentifiers());
		return identifiers;
	}

	public IPromotingPostcondition getSimpleOutputPromotion(Variable outputVariable) {
		return this.simpleOutputPromotions.getOutputPromotion(outputVariable);
	}

	public IPromotingPostcondition getOtherPromotion(Variable outputParameter) {
		return this.mapping.getOtherPromotion(outputParameter);
	}
	@Override
	public OutputPromotions getSimplePromotions() {
		return simpleOutputPromotions;
	}

	@Override
	public OutputPromotions getCommunicatingPromotions() {
		OutputPromotions communicatingOutputPromotions = new OutputPromotions();
		if (this.mapping != null){
			communicatingOutputPromotions.addAll(mapping.getOtherPromotions());
			communicatingOutputPromotions.addAll(mapping.getCommunicationPromotions());
		}
		return communicatingOutputPromotions;
	}

	public Declarations getAllCommunicationVariables() {
		return this.mapping.getAllCommunicationVars();
	}

	public Declarations getVisibleCommunicationVariables(){
		return this.mapping.getVisibleCommunicationVars();
	}

	@Override
	public Declarations getSharedOutputVariables() {
		return Declarations.empty();
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
	public boolean isEmpty() {
		return this.simpleOutputPromotions.isEmpty() && (mapping == null || mapping.isEmpty());
	}
}