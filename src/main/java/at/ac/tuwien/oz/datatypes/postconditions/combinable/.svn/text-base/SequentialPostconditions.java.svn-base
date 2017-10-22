package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;
import at.ac.tuwien.oz.translator.templates.PerfectPredicateTemplateProvider;

public class SequentialPostconditions implements IComposablePostconditions {

	// a.op1 ; b.op2 ; c.op3
	
	// consists of a sequence of combinable postconditions (simple or complex output promotions or change postconditions)
	private List<IComposablePostconditions> postconditions;

	// communication from first to second, second to third postcondition a.s.o.
	private List<Declarations> communicationVariables;
	
	private PerfectPredicateTemplateProvider templateProvider;

	private Context context;
	
	
	public SequentialPostconditions(List<IComposablePostconditions> sequentialPostconditions, 
			List<Declarations> newCommunicationVariables, Context context) {
		super();
		
		this.templateProvider = PerfectPredicateTemplateProvider.getInstance();
		
		this.communicationVariables = new ArrayList<Declarations>();
		this.postconditions = new ArrayList<IComposablePostconditions>();
		
		if (sequentialPostconditions != null){
			this.postconditions = sequentialPostconditions;
		}
		
		if (newCommunicationVariables != null){
			this.communicationVariables = newCommunicationVariables;
		}	
		
		this.context = context;
	}
	
	public List<IComposablePostconditions> getPostconditions(){
		return this.postconditions;
	}

	public List<Declarations> getCommunicationVariables() {
		return this.communicationVariables;
	}

	@Override
	public List<ST> getTemplates() {
		List<ST> templates= new ArrayList<ST>();
		if (context == Context.FUNCTION){
			templates.add(this.templateProvider.createSatisfyPostcondition(this));
		} else {
			templates.add(this.templateProvider.createThenPostcondition(this));
		}
		return templates;
	}

	@Override
	public List<ST> getSequentialTemplates(TemporaryVariableMap tempVarMap, Context context) {
		List<ST> templates= new ArrayList<ST>();
		
		if (context == Context.FUNCTION){
			throw new ObjectZToPerfectTranslationException("Function is not expected here");
		}
		templates.add(this.templateProvider.createThenPostcondition(this, tempVarMap));
		return templates;
	}

	@Override
	public Idents getUsedIdentifiers() {
		Idents usedIdentifiers = new Idents();
		for(IComposablePostconditions postcondition: this.postconditions){
			usedIdentifiers.addAll(postcondition.getUsedIdentifiers());
		}
		return usedIdentifiers;
	}

	public Declarations getAllCommunicationVariables() {
		Declarations allCommunicationVariables = new Declarations();
		
		for (Declarations partCommunicationVariable: communicationVariables){
			allCommunicationVariables.addAll(partCommunicationVariable);
		}
		return allCommunicationVariables;
	}

	@Override
	public IComposablePostconditions getWithoutPromotions(Declarations sharedOutputVariables) {
		List<IComposablePostconditions> postconditionsWithoutPromotions = new ArrayList<IComposablePostconditions>();
		for(IComposablePostconditions singlePostcondition: postconditions){
			IComposablePostconditions postconditionWithoutPromotion = singlePostcondition.getWithoutPromotions(sharedOutputVariables);
			if (!postconditionWithoutPromotion.isEmpty()){
				postconditionsWithoutPromotions.add(postconditionWithoutPromotion);
			}
		}
		if (postconditionsWithoutPromotions.isEmpty()){
			return EmptyPostconditions.empty();
		} else {
			return new SequentialPostconditions(postconditionsWithoutPromotions, this.communicationVariables, this.context);
		}
	}

	@Override
	public boolean isSequential() {
		return true;
	}

	@Override
	public SequentialPostconditions getSequentialPostconditions() {
		return this;
	}

	@Override
	public boolean isEmpty() {
		return this.postconditions.isEmpty();
	}

}
