package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import at.ac.tuwien.oz.datatypes.preconditions.interfaces.ICombinablePreconditions;

public class CompositePreconditionFactory {

	private CompositePreconditionDataCollector collector;
	
	public CompositePreconditionFactory(CompositePreconditionDataCollector collector){
		this.collector = collector;
	}
	
	public ICombinablePreconditions createPrecondition(){
		if (hasCommunicationVars() || hasSharedVars()){
			return createComplexPrecondition();
		} else if (hasSimplePreconditionCalls()){
			return createPreconditionFunctionCalls();
		} else {
			return EmptyPreconditions.empty();
		}
	}

	private ICombinablePreconditions createPreconditionFunctionCalls() {
		return new PreconditionFunctionCalls(collector.getSimplePreconditions());
	}

	private ICombinablePreconditions createComplexPrecondition() {
		CommunicationPreconditionMapping mapping = 
				new CommunicationPreconditionMapping(collector.getCommunicatingPreconditions(), 
				collector.getSharedOutputVariables(), collector.getCommunicationVariables(),
				collector.getAllCommunicatingOrSharingOperations());
		return new CommunicationPrecondition(collector.getSimplePreconditions(), mapping);
	}

	private boolean hasSimplePreconditionCalls() {
		if (this.collector.getSimplePreconditions().isEmpty()){
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
}
