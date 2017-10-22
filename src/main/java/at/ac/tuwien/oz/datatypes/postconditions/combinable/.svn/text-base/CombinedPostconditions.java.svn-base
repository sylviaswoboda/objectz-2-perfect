package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;

public abstract class CombinedPostconditions implements ICombinablePostconditions {

	@Override
	public ICombinablePostconditions getWithoutPromotions(Declarations outputVariables) {
		CompositePostconditionDataCollector collector = new CompositePostconditionDataCollector(this);
		collector.eliminateOutputVariable(outputVariables);
		CompositePostconditionFactory factory = new CompositePostconditionFactory(collector);
		return factory.createPostcondition();
	}
	
	@Override
	public boolean isSequential() {
		return false;
	}
	@Override
	public SequentialPostconditions getSequentialPostconditions() {
		return null;
	}

}
