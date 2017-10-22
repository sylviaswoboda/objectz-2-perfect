package at.ac.tuwien.oz.datatypes.preconditions.combinable;

import java.util.List;
import java.util.Set;

import org.junit.Assert;

import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.definitions.operation.TemporaryVariableMap;
import at.ac.tuwien.oz.definitions.operation.interfaces.IPromotedOperation;

public class PreconditionTestBase {
	protected void assertTemporaryVariableMapContains(TemporaryVariableMap resultTempVarMap, List<Variable> expectedElements) {
		Assert.assertEquals(expectedElements.size(), resultTempVarMap.size());
		
		for(Variable expectedElement: expectedElements){
			Assert.assertTrue(resultTempVarMap.containsKey(expectedElement));
		}
	}

	protected void assertSharedOutputIsEmpty(CommunicationPreconditionMapping result) {
		Assert.assertTrue(result.getAllSharedVariables().isEmpty());
	}

	protected void assertOperationPromotionsContains(List<IPromotedOperation> resultingOperationPromotions, List<IPromotedOperation> expectedOperationPromotions) {
		Assert.assertEquals(expectedOperationPromotions.size(), resultingOperationPromotions.size());
		
		for(IPromotedOperation expectedOperationPromotion: expectedOperationPromotions){
			Assert.assertTrue(resultingOperationPromotions.contains(expectedOperationPromotion));
		}
	}

	protected void assertSetContains(Set<Variable> resultingSet, List<Variable> expectedElements) {
		Assert.assertEquals(expectedElements.size(), resultingSet.size());
		
		for(Variable expectedElement: expectedElements){
			Assert.assertTrue(resultingSet.contains(expectedElement));
		}
	}

}
