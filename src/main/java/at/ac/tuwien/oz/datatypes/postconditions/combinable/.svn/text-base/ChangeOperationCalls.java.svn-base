package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Elements;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;

public class ChangeOperationCalls extends CombinedPostconditions {

	private static final ChangeOperationCalls EMPTY_CALLS = new ChangeOperationCalls();
	
	public static final ChangeOperationCalls empty(){
		return EMPTY_CALLS;
	}

	private Elements<ChangeOperationCall> elements;
	
	public ChangeOperationCalls() {
		this.elements = new Elements<ChangeOperationCall>();
		
	}
	public ChangeOperationCalls(ChangeOperationCall...calls){
		this();
		this.elements.addNew(calls);
	}
	public ChangeOperationCalls(ChangeOperationCalls changeOperationCalls) {
		this();
		this.addAll(changeOperationCalls);
	}
	
	public List<ChangeOperationCall> asList(){
		return this.elements.asList();
	}
	
	public boolean add(ChangeOperationCall elem) {
		return this.elements.add(elem);
	}
	
	public boolean addAll(ChangeOperationCalls changeOperationCalls) {
		if (changeOperationCalls != null){
			return this.elements.addAll(changeOperationCalls.asList());
		}
		return false;
	}
	public boolean addAll(List<ChangeOperationCall> changeOperationCalls) {
		if (changeOperationCalls != null){
			return this.elements.addAll(changeOperationCalls);
		}
		return false;
	}

	public boolean isEmpty(){
		return this.elements.isEmpty();
	}
	
	@Override
	public List<ST> getTemplates() {
		List<ST> templates= new ArrayList<ST>();
		for(IPostcondition postcondition: this.elements){
			templates.add(postcondition.getTemplate());
		}
		return templates;
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap outerTempVarMap, Context context) {
		List<ST> templates= new ArrayList<ST>();
		for(ChangeOperationCall postcondition: this.elements){
			templates.add(postcondition.getTemplate(outerTempVarMap));
		}
		return templates;
	}
	@Override
	public Idents getUsedIdentifiers() {
		Idents identifiers = new Idents();
		for(ChangeOperationCall postcondition: this.elements){
			identifiers.addAll(postcondition.getIdentifiers());
		}
		return identifiers;
	}
	public ChangeOperationCalls getWithoutSharedOutputParameters(Declarations sharedOutputParameters) {
		ChangeOperationCalls operationCalls = new ChangeOperationCalls();
		
		for (ChangeOperationCall call: this.elements){
			if (!call.usesOutputParameters(sharedOutputParameters)){
				operationCalls.add(call);
			}
		}
		return operationCalls;
	}
	
	public ChangeOperationCalls getWithOutputParameters(Declarations outputVariables) {
		ChangeOperationCalls changeOperationCalls = new ChangeOperationCalls();
		
		for (ChangeOperationCall call: this.elements){
			if (call.usesOutputParameters(outputVariables)){
				changeOperationCalls.add(call);
			}
		}
		return changeOperationCalls;
	}
	public ChangeOperationCall getOperationCallWithOutputParameter(Variable outputVariable) {
		for (ChangeOperationCall call: this.elements){
			if (call.usesOutputParameter(outputVariable)){
				return call;
			}
		}
		return null;
	}

	/**
	 * Returns all change operation calls, that uses the given parameters neither as input nor as output parameter.
	 * 
	 * @param undecoratedParameters - only the base name of the parameters is used.
	 * @return
	 */
	public ChangeOperationCalls getWithoutParameters(Declarations undecoratedParameters) {
		ChangeOperationCalls changeOperationCalls = new ChangeOperationCalls();
		
		Declarations outputParameters = undecoratedParameters.getOutputCopies();
		Declarations inputParameters = undecoratedParameters.getInputCopies();
		
		for (ChangeOperationCall p: this.elements){
			if (!p.usesOutputParameters(outputParameters) && !p.usesInputParameters(inputParameters)){
				changeOperationCalls.add(p);
			}
		}
		return changeOperationCalls;
	}
	

	public void removeAll(ChangeOperationCalls changeOperationCallsToRemove) {
		this.elements.removeAll(changeOperationCallsToRemove.asList());
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
	public Declarations getSharedOutputVariables() {
		return Declarations.empty();
	}
	@Override
	public OutputPromotions getSimplePromotions() {
		return OutputPromotions.empty();
	}
	@Override
	public OutputPromotions getCommunicatingPromotions() {
		return OutputPromotions.empty();
	}
	@Override
	public ChangeOperationCalls getSimpleCalls() {
		return this;
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
		return true;
	}
}
