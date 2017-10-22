package at.ac.tuwien.oz.datatypes.postconditions;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition.Context;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions.PreconditionItem;

public class NonDeterministicChoiceItem {

	private PreconditionItem precondition;
	private IComposablePostconditions postcondition;
	private Context context;
	
	public NonDeterministicChoiceItem(PreconditionItem precondition, IComposablePostconditions postconditions, Context context){
		this.precondition = precondition;
		this.postcondition = postconditions;
		this.context = context;
	}

	public IComposablePostconditions getPostcondition(){
		return this.postcondition;
	}
	public PreconditionItem getPrecondition(){
		return this.precondition;
	}

	public Context getContext() {
		return this.context;
	}

	public NonDeterministicChoiceItem getWithoutPromotions(Declarations sharedOutputVariables) {
		IComposablePostconditions newPostcondition = postcondition.getWithoutPromotions(sharedOutputVariables);
		return new NonDeterministicChoiceItem(precondition, newPostcondition, context);
	}

	public boolean isEmpty() {
		return postcondition.isEmpty();
	}

	public Idents getUsedIdentifiers() {
		return postcondition.getUsedIdentifiers();
	}
	
}
