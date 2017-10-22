package at.ac.tuwien.oz.definitions.operation.predicateprovider;

import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.definitions.operation.Operation;
import at.ac.tuwien.oz.definitions.ozclass.Operations;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public class OperationOrderingCalculator {
	
	public Operations order(Operation leftOperation, Operation rightOperation) {
		Idents changedStateVarsLeft = leftOperation.getChangedStateVariables();
		Idents changedStateVarsRight = rightOperation.getChangedStateVariables();
		
		Operations ordering = new Operations();
		
		if (changedStateVarsLeft.hasIntersection(changedStateVarsRight)){
			throw new ObjectZToPerfectTranslationException("Cannot calculate ordering between operations. Translation not possible.");
		} else if (!changedStateVarsLeft.hasIntersection(rightOperation.getUsedStateVariables())){
			ordering.add(leftOperation);
			ordering.add(rightOperation);
		} else if (!changedStateVarsRight.hasIntersection(leftOperation.getUsedStateVariables())){
			ordering.add(rightOperation);
			ordering.add(leftOperation);
		} else {
			throw new ObjectZToPerfectTranslationException("Cannot calculate ordering between operations. Translation not possible.");
		}
		return ordering;
	}

}
