package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import at.ac.tuwien.AssertCollection;
import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPromotingPostcondition;

public class CompositePostconditionDataCollectorTest extends CompositePostconditionDataCollectorTestBase {
	
	private ICombinablePostconditions left;
	private ICombinablePostconditions right;
	
	Variable var1;
	Variable var2;
	private Variable var3;
	private Variable var4;
	private Variable newVar1;
	private Variable newVar2;

	private OutputPromotion outputPromo1;
	private OutputPromotion outputPromo2;
	private OutputPromotion outputPromo3;
	private OutputPromotion outputPromo4;
	
	private ChangeOperationCall call1;
	private ChangeOperationCall call2;
	private ChangeOperationCall call3;
	private ChangeOperationCall call4;
	CompositePostconditionDataCollector dataConjoin;
	CompositePostconditionDataCollector dataCompose;
	CompositePostconditionDataCollector dataComposeAssoc;
	
	Declarations newSharedOutputVariables;
	Declarations newCommunicationVariables;
	
	@Before
	public void setup(){
		left  = Mockito.mock(ICombinablePostconditions.class, "left");
		right = Mockito.mock(ICombinablePostconditions.class, "right");
		
		var1 = Mockito.mock(Variable.class, "var1");
		var2 = Mockito.mock(Variable.class, "var2");
		var3 = Mockito.mock(Variable.class, "var3");
		var4 = Mockito.mock(Variable.class, "var4");
		
		newVar1 = Mockito.mock(Variable.class, "newVar1");
		newVar2 = Mockito.mock(Variable.class, "newVar2");
		
		outputPromo1 = Mockito.mock(OutputPromotion.class, "outputPromo1");
		outputPromo2 = Mockito.mock(OutputPromotion.class, "outputPromo2");
		outputPromo3 = Mockito.mock(OutputPromotion.class, "outputPromo3");
		outputPromo4 = Mockito.mock(OutputPromotion.class, "outputPromo4");

		call1 = Mockito.mock(ChangeOperationCall.class, "call1");
		call2 = Mockito.mock(ChangeOperationCall.class, "call2");
		call3 = Mockito.mock(ChangeOperationCall.class, "call3");
		call4 = Mockito.mock(ChangeOperationCall.class, "call4");
		
		newSharedOutputVariables = new Declarations();
		newCommunicationVariables = new Declarations();
	}	
	
	private void combine(){
		conjoin();
		compose();
		assocCompose();
	}
	private void combineReverse(){
		conjoinReverse();
		composeReverse();
		assocComposeReverse();
	}

	private void conjoinAndCompose(){
		conjoin();
		compose();
	}
	
	private void composeAndComposeAssoc(){
		compose();
		assocCompose();
	}

	private void conjoin() {
		dataConjoin = new CompositePostconditionDataCollector(left, right);
		dataConjoin.conjoin(newSharedOutputVariables);
	}
	private void conjoinReverse() {
		dataConjoin = new CompositePostconditionDataCollector(right, left);
		dataConjoin.conjoin(newSharedOutputVariables);
	}


	private void compose(){
		dataCompose = new CompositePostconditionDataCollector(left, right);
		dataCompose.compose(newCommunicationVariables, newSharedOutputVariables, false);
	}
	private void composeReverse(){
		dataCompose = new CompositePostconditionDataCollector(right, left);
		dataCompose.compose(newCommunicationVariables, newSharedOutputVariables, false);
	}

	private void assocCompose(){
		dataComposeAssoc = new CompositePostconditionDataCollector(left, right);
		dataComposeAssoc.compose(newCommunicationVariables, newSharedOutputVariables, true);
	}
	private void assocComposeReverse(){
		dataComposeAssoc = new CompositePostconditionDataCollector(right, left);
		dataComposeAssoc.compose(newCommunicationVariables, newSharedOutputVariables, true);
	}

	@Test
	public void combineShouldCombineSharedOutputOfLeftAndRightSideAndNew() {
		Mockito.when(left.getSharedOutputVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getSharedOutputVariables()).thenReturn(new Declarations(var3, var4));
		newSharedOutputVariables = new Declarations(newVar1, newVar2);
		
		combine();

		verifySharedOutputVariablesAreEqualForConjoinAndCompose(var1, var2, var3, var4, newVar1, newVar2);
	}

	@Test
	public void combineShouldCombineSharedOutputWithEmptyDeclarationsOnLeftSide() {
		Mockito.when(left.getSharedOutputVariables()).thenReturn(new Declarations());
		Mockito.when(right.getSharedOutputVariables()).thenReturn(new Declarations(var3, var4));
		newSharedOutputVariables = new Declarations(newVar1, newVar2);
		
		combine();
		
		verifySharedOutputVariablesAreEqualForConjoinAndCompose(var3, var4, newVar1, newVar2);
	}

	@Test
	public void combineShouldCombineSharedOutputWithEmptyDeclarationsOnRightSide() {
		Mockito.when(left.getSharedOutputVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getSharedOutputVariables()).thenReturn(new Declarations());
		newSharedOutputVariables = new Declarations(newVar1, newVar2);
		
		combine();

		verifySharedOutputVariablesAreEqualForConjoinAndCompose(var1, var2, newVar1, newVar2);
	}

	@Test
	public void combineShouldCombineSharedOutputWithEmptyDeclarationsInNew() {
		Mockito.when(left.getSharedOutputVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getSharedOutputVariables()).thenReturn(new Declarations(var3, var4));
		newSharedOutputVariables = new Declarations();
		
		combine();
		
		verifySharedOutputVariablesAreEqualForConjoinAndCompose(var1, var2, var3, var4);
	}

	@Test
	public void combineShouldCombineSharedOutputWithAllEmptyDeclarations() {
		Mockito.when(left.getSharedOutputVariables()).thenReturn(new Declarations());
		Mockito.when(right.getSharedOutputVariables()).thenReturn(new Declarations());
		newSharedOutputVariables = new Declarations();
		
		combine();

		verifySharedOutputVariablesAreEqualForConjoinAndCompose();
	}

	@Test
	public void combineShouldCombineSharedOutputWithNullDeclarationsOnLeftSide() {
		Mockito.when(left.getSharedOutputVariables()).thenReturn(null);
		Mockito.when(right.getSharedOutputVariables()).thenReturn(new Declarations());
		newSharedOutputVariables = new Declarations();
		
		combine();

		verifySharedOutputVariablesAreEqualForConjoinAndCompose();
	}

	@Test
	public void combineShouldCombineSharedOutputWithNullDeclarationsOnRightSide() {
		Mockito.when(left.getSharedOutputVariables()).thenReturn(new Declarations());
		Mockito.when(right.getSharedOutputVariables()).thenReturn(null);
		newSharedOutputVariables = new Declarations();
		
		combine();

		verifySharedOutputVariablesAreEqualForConjoinAndCompose();
	}

	@Test
	public void combineShouldCombineSharedOutputWithNullDeclarationsInNew() {
		Mockito.when(left.getSharedOutputVariables()).thenReturn(new Declarations());
		Mockito.when(right.getSharedOutputVariables()).thenReturn(new Declarations());
		newSharedOutputVariables = null;
		
		combine();

		verifySharedOutputVariablesAreEqualForConjoinAndCompose();
	}

	@Test
	public void conjoinShouldCombinesCommunicationVariablesOfLeftAndRightSide(){
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(new Declarations(var3, var4));
		
		conjoin();
		
		verifyConjoinCommunicationVariables(var1, var2, var3, var4);
	}
	
	@Test
	public void conjoinShouldCombineLeftEmptyAndRightNullCommunicationVariables(){
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(new Declarations());
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(null);
		
		conjoin();
		
		verifyConjoinCommunicationVariables();
	}

	@Test
	public void conjoinShouldCombineLeftNullAndRightEmptyCommunicationVariables(){
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(null);
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(new Declarations());
		
		conjoin();
		
		verifyConjoinCommunicationVariables();
	}

	
	@Test
	public void composeShouldCombineCommunicationVariables() {
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(new Declarations(var3, var4));
		newCommunicationVariables = new Declarations(newVar1, newVar2);
		
		combine();

		verifyCommunicationVariablesAreEqualForComposeAndAssociativeCompose(var1, var2, var3, var4, newVar1, newVar2);
	}

	@Test
	public void composeShouldCombineLeftNullCommunicationVariables() {
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(null);
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(new Declarations(var3, var4));
		newCommunicationVariables = new Declarations(newVar1, newVar2);

		combine();
		
		verifyCommunicationVariablesAreEqualForComposeAndAssociativeCompose(var3, var4, newVar1, newVar2);
	}
	
	@Test
	public void composeShouldCombineRightNullCommunicationVariables() {
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(null);
		newCommunicationVariables = new Declarations(newVar1, newVar2);

		combine();

		verifyCommunicationVariablesAreEqualForComposeAndAssociativeCompose(var1, var2, newVar1, newVar2);
	}
	
	@Test
	public void composeShouldCombineNewNullCommunicationVariables() {
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(new Declarations(var3, var4));
		newCommunicationVariables = null;

		combine();
		
		verifyCommunicationVariablesAreEqualForComposeAndAssociativeCompose(var1, var2, var3, var4);
	}

	@Test
	public void composeShouldCombineAllNullCommunicationVariables() {
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(null);
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(null);
		newCommunicationVariables = null;
		
		combine();

		verifyCommunicationVariablesAreEqualForComposeAndAssociativeCompose();
	}

	@Test
	public void composeShouldCombineAllNewCommunicationVariables() {
		Mockito.when(left.getAllCommunicationVariables()).thenReturn(new Declarations());
		Mockito.when(right.getAllCommunicationVariables()).thenReturn(new Declarations());
		newCommunicationVariables = new Declarations();

		combine();
		
		verifyCommunicationVariablesAreEqualForComposeAndAssociativeCompose();
	}

	@Test
	public void assocComposeShouldMakeNewCommunicationVariablesVisible(){
		Mockito.when(left.getVisibleCommunicationVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getVisibleCommunicationVariables()).thenReturn(new Declarations(var3, var4));
		newCommunicationVariables = new Declarations(newVar1, newVar2);
		
		assocCompose();
		
		verifyAssociativeComposeVisibleCommunicationVariables(var1, var2, var3, var4, newVar1, newVar2);
	}

	@Test
	public void conjoinAndComposeShouldNotMakeNewCommunicationVariablesVisible() {
		Mockito.when(left.getVisibleCommunicationVariables()).thenReturn(new Declarations(var1, var2));
		Mockito.when(right.getVisibleCommunicationVariables()).thenReturn(new Declarations(var3, var4));
		newCommunicationVariables = new Declarations(newVar1, newVar2);
		
		conjoinAndCompose();

		verifyVisibleCommunicationVariablesAreEqualForConjoinAndCombine(var1, var2, var3, var4);
	}


	@Test
	public void combiningSimplePromotionsShouldYieldOnlySimplePromotions() {
		withSimplePromotions(left, outputPromo1, outputPromo2);
		withSimplePromotions(right, outputPromo3, outputPromo4);

		combine();
		
		AssertCollection.assertHasElements(dataConjoin.getSimplePromotions().getElements(), outputPromo1, outputPromo2, outputPromo3, outputPromo4);
		AssertCollection.assertHasElements(dataConjoin.getCommunicatingPromotions().getElements());
		
		AssertCollection.assertHasElements(dataCompose.getSimplePromotions().getElements(), outputPromo1, outputPromo2, outputPromo3, outputPromo4);
		AssertCollection.assertHasElements(dataCompose.getCommunicatingPromotions().getElements());

		AssertCollection.assertHasElements(dataComposeAssoc.getSimplePromotions().getElements(), outputPromo1, outputPromo2, outputPromo3, outputPromo4);
		AssertCollection.assertHasElements(dataComposeAssoc.getCommunicatingPromotions().getElements());
	}

	@Test
	public void combiningSimplePromotionsWithSharedOutputRemovesSharingPromotionOfRightSide() {
		withSharingOutputParameters(outputPromo1, outputPromo3);
		withSimplePromotions(left, outputPromo1, outputPromo2);
		withSimplePromotions(right, outputPromo3, outputPromo4);

		combine();
		
		verifySimplePromotionsAreEqualForAllCombineOperations(outputPromo1, outputPromo2, outputPromo4);
		verifyCommunicatingPromotionsAreEqualForAllCombineOperations();
	}

	@Test
	public void combiningSimpleAndCommunicatingPromotionsShouldCombineSingleAndCommunicatingPromotions() {
		withSimplePromotions(left, outputPromo1);
		withCommunicatingPromotions(left, outputPromo2);
		
		withSimplePromotions(right, outputPromo3);
		withCommunicatingPromotions(right, outputPromo4);

		combine();
		
		verifySimplePromotionsAreEqualForAllCombineOperations(outputPromo1, outputPromo3);
		verifyCommunicatingPromotionsAreEqualForAllCombineOperations(outputPromo2, outputPromo4);
	}
	
	@Test
	public void combiningCommunicatingPromotionsWithSharedOutputShouldRemoveSharingOutputPromotionFromRightSide() {
		withSharingOutputParameters(outputPromo1, outputPromo2);
		withCommunicatingPromotions(left, outputPromo1);
		withCommunicatingPromotions(right, outputPromo2);

		combine();
		
		verifySimplePromotionsAreEqualForAllCombineOperations();
		verifyCommunicatingPromotionsAreEqualForAllCombineOperations(outputPromo1);
	}

	@Test
	public void combiningSimpleAndCommunicatingPromotionsWithSharedOutputShouldRemoveSharingOutputPromotionFromRightSide() {
		withSharingOutputParameters(outputPromo1, outputPromo2);
		withSimplePromotions(left, outputPromo1);
		withCommunicatingPromotions(right, outputPromo2);

		combine();
		
		verifySimplePromotionsAreEqualForAllCombineOperations(outputPromo1);
		verifyCommunicatingPromotionsAreEqualForAllCombineOperations();
	}

	@Test
	public void combiningCommunicatingAndSimplePromotionsWithSharedOutputShouldRemoveSharingOutputPromotionFromRightSide() {
		withSharingOutputParameters(outputPromo1, outputPromo2);
		withCommunicatingPromotions(left, outputPromo1);
		withSimplePromotions(right, outputPromo2);

		combine();
		
		verifySimplePromotionsAreEqualForAllCombineOperations();
		verifyCommunicatingPromotionsAreEqualForAllCombineOperations(outputPromo1);
	}

	@Test
	public void combineShouldCombineSimpleCalls() {
		withSimpleCalls(left, call1, call2);
		withSimpleCalls(right, call3, call4);

		combine();
		
		verifySimpleCallsAreEqualForAllCombineOperations(call1, call2, call3, call4);
		verifyCommunicatingCallsAreEqualForAllCombineOperations();
		verifyAllCallsAreEqualForAllCombineOperations(call1, call2, call3, call4);
	}

	@Test
	public void combineShouldCombineCommunicatingCalls() {
		withCommunicatingCalls(left, call1);
		withCommunicatingCalls(right, call2);
	
		combine();
		
		verifySimpleCallsAreEqualForAllCombineOperations();
		verifyCommunicatingCallsAreEqualForAllCombineOperations(call1, call2);
		verifyAllCallsAreEqualForAllCombineOperations(call1, call2);

	}

	@Test
	public void combineWithSharedOutputShoudMoveSharingCallsToCommunicatingCalls() {
		withSharingOutputParameters(call1, call3);
		withSimpleCalls(left, call1, call2);
		withSimpleCalls(right, call3, call4);

		combine();
		
		verifySimpleCallsAreEqualForAllCombineOperations(call2, call4);
		verifyCommunicatingCallsAreEqualForAllCombineOperations(call1, call3);
		verifyAllCallsAreEqualForAllCombineOperations(call1, call2, call3, call4);
	}

	@Test
	public void combineWithSharedOutputShouldSharingCommunicatingCalls() {
		withSharingOutputParameters(call2, call4);
		withSimpleCalls(left, call1);
		withCommunicatingCalls(left, call2);
		withSimpleCalls(right, call3);
		withCommunicatingCalls(right, call4);

		combine();
		
		verifySimpleCallsAreEqualForAllCombineOperations(call1, call3);
		verifyCommunicatingCallsAreEqualForAllCombineOperations(call2, call4);
		verifyAllCallsAreEqualForAllCombineOperations(call1, call2, call3, call4);
	}

	@Test
	public void combineWithSharedOutputShoudMoveSimpleRightSharingCallsToCommunicatingCalls() {
		withSharingOutputParameters(call2, call3);
		withSimpleCalls(left, call1);
		withCommunicatingCalls(left, call2);
		withSimpleCalls(right, call3);
		withCommunicatingCalls(right, call4);
		
		combine();
		
		verifySimpleCallsAreEqualForAllCombineOperations(call1);
		verifyCommunicatingCallsAreEqualForAllCombineOperations(call2, call3, call4);
		verifyAllCallsAreEqualForAllCombineOperations(call1, call2, call3, call4);
	}

	@Test
	public void combineWithSharedOutputShoudMoveSimpleLeftSharingCallsToCommunicatingCalls() {
		withSharingOutputParameters(call1, call4);
		withSimpleCalls(left, call1);
		withCommunicatingCalls(left, call2);
		withSimpleCalls(right, call3);
		withCommunicatingCalls(right, call4);
		
		combine();

		verifySimpleCallsAreEqualForAllCombineOperations(call3);
		verifyCommunicatingCallsAreEqualForAllCombineOperations(call1, call2, call4);
		verifyAllCallsAreEqualForAllCombineOperations(call1, call2, call3, call4);
	}

	@Test
	public void combineShouldRemoveSharingPromotionOnAnySide() {
		withSharingOutputParameters(call1, outputPromo3);
		
		withSimpleCalls(left, call1);
		withCommunicatingCalls(left, call2);
		withSimplePromotions(left, outputPromo1);
		withCommunicatingPromotions(left, outputPromo2);

		withSimplePromotions(right, outputPromo3);
		withCommunicatingPromotions(right, outputPromo4);

		combine();
		
		verifySimplePromotionsAreEqualForAllCombineOperations(outputPromo1);
		verifyCommunicatingPromotionsAreEqualForAllCombineOperations(outputPromo2, outputPromo4);
		
		verifySimpleCallsAreEqualForAllCombineOperations(call1);
		verifyCommunicatingCallsAreEqualForAllCombineOperations(call2);
		verifyAllCallsAreEqualForAllCombineOperations(call1, call2);

		combineReverse();
		
		AssertCollection.assertHasElements(dataConjoin.getSimplePromotions().getElements(), outputPromo1);
		AssertCollection.assertHasElements(dataConjoin.getCommunicatingPromotions().getElements(), outputPromo2, outputPromo4);
		
		AssertCollection.assertHasElements(dataConjoin.getSimpleCalls().asList(), call1);
		AssertCollection.assertHasElements(dataConjoin.getCommunicatingCalls().asList(), call2);
		AssertCollection.assertHasElements(dataConjoin.getAllCalls().asList(), call1, call2);
		verifySimplePromotionsAreEqualForAllCombineOperations(outputPromo1);
		verifyCommunicatingPromotionsAreEqualForAllCombineOperations(outputPromo2, outputPromo4);
		
		verifySimpleCallsAreEqualForAllCombineOperations(call1);
		verifyCommunicatingCallsAreEqualForAllCombineOperations(call2);
		verifyAllCallsAreEqualForAllCombineOperations(call1, call2);
	}
	
	@Test
	public void composeWithCommunicationShouldMoveSimpleCallsToCommunicating() {
		withCommunicatingOutputParameters(call1, call3);
		
		withSimpleCalls(left, call1, call2);
		withSimpleCalls(right, call3, call4);

		composeAndComposeAssoc();
		
		verifySimpleCallsAreEqualForAllComposeOperations(call2, call4);
		verifyCommunicatingCallsAreEqualForAllComposeOperations(call1, call3);
		verifyAllCallsAreEqualForAllComposeOperations(call1, call2, call3, call4);
	}

	@Test
	public void composeWithCommunicationShouldMoveSimplePromotionsToCommunicating() {
		withCommunicatingOutputParameters(outputPromo1, outputPromo3);
		
		withSimplePromotions(left, outputPromo1, outputPromo2);
		withSimplePromotions(right, outputPromo3, outputPromo4);
		
		composeAndComposeAssoc();
		
		verifySimplePromotionsAreEqualForAllComposeOperations(outputPromo2, outputPromo4);
		verifyCommunicatingPromotionsAreEqualForAllComposeOperations(outputPromo1, outputPromo3);
	}

	protected void verifySharedOutputVariablesAreEqualForConjoinAndCompose(Variable... expectedSharedOutputs) {
		AssertCollection.assertHasElements(dataConjoin.getSharedOutputVariables().asList(), expectedSharedOutputs);
		AssertCollection.assertHasElements(dataCompose.getSharedOutputVariables().asList(), expectedSharedOutputs);
		AssertCollection.assertHasElements(dataComposeAssoc.getSharedOutputVariables().asList(), expectedSharedOutputs);
	}

	protected void verifyConjoinCommunicationVariables(Variable... expectedCommunicationVariables) {
		AssertCollection.assertHasElements(dataConjoin.getCommunicationVariables().asList(), expectedCommunicationVariables);
	}

	protected void verifyCommunicationVariablesAreEqualForComposeAndAssociativeCompose(Variable... expectedCommunicationVariables) {
		AssertCollection.assertHasElements(dataComposeAssoc.getCommunicationVariables().asList(), expectedCommunicationVariables);
		AssertCollection.assertHasElements(dataCompose.getCommunicationVariables().asList(), expectedCommunicationVariables);
	}

	protected void verifyAssociativeComposeVisibleCommunicationVariables(Variable... expectedVisibleCommunicationVariables) {
		AssertCollection.assertHasElements(dataComposeAssoc.getVisibleCommunicationVariables().asList(), expectedVisibleCommunicationVariables);
	}

	protected void verifyVisibleCommunicationVariablesAreEqualForConjoinAndCombine(Variable... expectedVisibleCommunicationVariables) {
		AssertCollection.assertHasElements(dataCompose.getVisibleCommunicationVariables().asList(), expectedVisibleCommunicationVariables);
		AssertCollection.assertHasElements(dataConjoin.getVisibleCommunicationVariables().asList(), expectedVisibleCommunicationVariables);
	}

	protected void verifySimplePromotionsAreEqualForAllCombineOperations(OutputPromotion... outputPromotions) {
		AssertCollection.assertHasElements(dataConjoin.getSimplePromotions().getElements(), outputPromotions);
		verifySimplePromotionsAreEqualForAllComposeOperations(outputPromotions);
	}

	protected void verifySimplePromotionsAreEqualForAllComposeOperations(OutputPromotion... outputPromotions) {
		AssertCollection.assertHasElements(dataCompose.getSimplePromotions().getElements(), outputPromotions);
		AssertCollection.assertHasElements(dataComposeAssoc.getSimplePromotions().getElements(), outputPromotions);
	}

	protected void verifyCommunicatingPromotionsAreEqualForAllCombineOperations(OutputPromotion... outputPromotions) {
		AssertCollection.assertHasElements(dataConjoin.getCommunicatingPromotions().getElements(), outputPromotions);
		verifyCommunicatingPromotionsAreEqualForAllComposeOperations(outputPromotions);
	}

	protected void verifyCommunicatingPromotionsAreEqualForAllComposeOperations(OutputPromotion... outputPromotions) {
		AssertCollection.assertHasElements(dataCompose.getCommunicatingPromotions().getElements(), outputPromotions);
		AssertCollection.assertHasElements(dataComposeAssoc.getCommunicatingPromotions().getElements(), outputPromotions);
	}

	protected void verifySimpleCallsAreEqualForAllCombineOperations(ChangeOperationCall... simpleCalls) {
		AssertCollection.assertHasElements(dataConjoin.getSimpleCalls().asList(), simpleCalls);
		verifySimpleCallsAreEqualForAllComposeOperations(simpleCalls);
	}

	protected void verifySimpleCallsAreEqualForAllComposeOperations(ChangeOperationCall... simpleCalls) {
		AssertCollection.assertHasElements(dataCompose.getSimpleCalls().asList(), simpleCalls);
		AssertCollection.assertHasElements(dataComposeAssoc.getSimpleCalls().asList(), simpleCalls);
	}

	protected void verifyCommunicatingCallsAreEqualForAllCombineOperations(ChangeOperationCall... communicatingCalls) {
		AssertCollection.assertHasElements(dataConjoin.getCommunicatingCalls().asList(), communicatingCalls);
		verifyCommunicatingCallsAreEqualForAllComposeOperations(communicatingCalls);
	}

	protected void verifyCommunicatingCallsAreEqualForAllComposeOperations(ChangeOperationCall... communicatingCalls) {
		AssertCollection.assertHasElements(dataCompose.getCommunicatingCalls().asList(), communicatingCalls);
		AssertCollection.assertHasElements(dataComposeAssoc.getCommunicatingCalls().asList(), communicatingCalls);
	}

	protected void verifyAllCallsAreEqualForAllCombineOperations(ChangeOperationCall... calls) {
		AssertCollection.assertHasElements(dataConjoin.getAllCalls().asList(), calls);
		verifyAllCallsAreEqualForAllComposeOperations(calls);
	}

	protected void verifyAllCallsAreEqualForAllComposeOperations(ChangeOperationCall... calls) {
		AssertCollection.assertHasElements(dataCompose.getAllCalls().asList(), calls);
		AssertCollection.assertHasElements(dataComposeAssoc.getAllCalls().asList(), calls);
	}

	protected void withCommunicatingPromotions(ICombinablePostconditions postcondition, OutputPromotion... outputPromos) {
		Mockito.when(postcondition.getCommunicatingPromotions()).thenReturn(new OutputPromotions(outputPromos));
	}

	protected void withSimplePromotions(ICombinablePostconditions postcondition, OutputPromotion... outputPromos) {
		Mockito.when(postcondition.getSimplePromotions()).thenReturn(new OutputPromotions(outputPromos));
	}

	protected void withSimpleCalls(ICombinablePostconditions postcondition, ChangeOperationCall... simpleCalls) {
		Mockito.when(postcondition.getSimpleCalls()).thenReturn(new ChangeOperationCalls(simpleCalls));
		if (simpleCalls != null && simpleCalls.length > 0){
			Mockito.when(postcondition.isChangePostcondition()).thenReturn(true);
		}
	}

	protected void withCommunicatingCalls(ICombinablePostconditions postcondition, ChangeOperationCall... communicatingCalls) {
		Mockito.when(postcondition.getCommunicatingCalls()).thenReturn(new ChangeOperationCalls(communicatingCalls));
		if (communicatingCalls != null && communicatingCalls.length > 0){
			Mockito.when(postcondition.isChangePostcondition()).thenReturn(true);
		}
	}

	protected void withSharingOutputParameters(IPromotingPostcondition left, IPromotingPostcondition right) {
		newSharedOutputVariables = new Declarations(var1);
		withOutputParameters(left);
		withOutputParameters(right);
	}

	protected void withCommunicatingOutputParameters(IPromotingPostcondition from, IPromotingPostcondition to) {
		newCommunicationVariables = new Declarations(var2);
		Mockito.when(from.usesCommunicationVariables(newCommunicationVariables)).thenReturn(true);
		Mockito.when(to.usesCommunicationVariables(newCommunicationVariables)).thenReturn(true);
	}

	private void withOutputParameters(IPromotingPostcondition postcondition) {
		Mockito.when(postcondition.usesOutputParameters(newSharedOutputVariables)).thenReturn(true);
	}
	
//	@Test
//	public void createThenPostcondition(){
//		CompositePostconditionDataCollector data = Mockito.mock(CompositePostconditionDataCollector.class);
//		
//		Mockito.when(data.getSimplePromotions().isEmpty()).thenReturn(true);
//		Mockito.when(data.getCommunicatingPromotions().isEmpty()).thenReturn(true);
//		Mockito.when(data.getSimpleCalls().isEmpty()).thenReturn(true);
//		Mockito.when(data.getCommunicatingCalls().isEmpty()).thenReturn(true);
//		
//		ICombinablePostconditions result = data.createPostcondition();
//
//		
//	}
}
