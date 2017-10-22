package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Elements;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.NonDeterministicChoiceItem;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.translator.templates.NonDeterministicChoiceTemplateProvider;

public class NonDeterministicPostconditions implements IComposablePostconditions {

	private NonDeterministicChoiceTemplateProvider templateProvider;
	private Elements<NonDeterministicChoiceItem> elements;
	private Context context;
	
	public NonDeterministicPostconditions(Context context){
		this.context = context;
		this.elements = new Elements<NonDeterministicChoiceItem>();
		this.templateProvider = new NonDeterministicChoiceTemplateProvider();
	}
	
	public List<NonDeterministicChoiceItem> getElements(){
		return this.elements.asList();
	}
	
	public boolean add(NonDeterministicChoiceItem elem){
		return this.elements.add(elem);
	}

	public void addAll(NonDeterministicPostconditions nonDeterministicPostconditions) {
		this.elements.addAll(nonDeterministicPostconditions.getElements());
	}

	@Override
	public List<ST> getTemplates() {
		List<ST> templates = new ArrayList<ST>();
			templates.add(templateProvider.createNonDeterministicPostcondition(this, null, context));
		return templates;
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap, Context context) {
		List<ST> templates = new ArrayList<ST>();
		templates.add(templateProvider.createNonDeterministicPostcondition(this, tempVarMap, context));
		return templates;
	}

	@Override
	public Idents getUsedIdentifiers() {
		Idents usedIdentifiers = new Idents();
		for (NonDeterministicChoiceItem elem: elements){
			usedIdentifiers.addAll(elem.getUsedIdentifiers());
		}
		return usedIdentifiers;
	}

	@Override
	public IComposablePostconditions getWithoutPromotions(Declarations sharedOutputVariables) {
		Elements<NonDeterministicChoiceItem> newElements = new Elements<NonDeterministicChoiceItem>();
		
		for (NonDeterministicChoiceItem elem: elements){
			NonDeterministicChoiceItem updatedItem = elem.getWithoutPromotions(sharedOutputVariables);
			if (!updatedItem.isEmpty()){
				newElements.add(updatedItem);
			}
		}
		if(newElements.isEmpty()){
			return EmptyPostconditions.empty();
		} else {
			NonDeterministicPostconditions newPostcondition = new NonDeterministicPostconditions(this.context);
			newPostcondition.elements.addAll(newElements.asList());
			return newPostcondition;
		}
	}

	@Override
	public boolean isSequential() {
		return false;
	}

	@Override
	public SequentialPostconditions getSequentialPostconditions() {
		return null;
	}


	public List<NonDeterministicChoiceItem> getNonDeterministicPostconditionItems() {
		return this.elements.asList();
	}


	@Override
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
	
}
