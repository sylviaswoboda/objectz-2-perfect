package at.ac.tuwien.oz.datatypes.postconditions.combinable;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.ChangeOperationCall;
import at.ac.tuwien.oz.datatypes.postconditions.OutputPromotion;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.ICombinablePostconditions;

public class CompositePostconditionDataCollector {

	private ICombinablePostconditions left;
	private ICombinablePostconditions right;
	private Declarations newSharedOutputVariables;
	private Declarations newCommunicationVariables;
	private boolean isAssociative;
	
	private OutputPromotions simplePromotions;
	private OutputPromotions communicatingPromotions;
	
	private ChangeOperationCalls simpleCalls;
	private ChangeOperationCalls communicatingCalls;
	private ChangeOperationCalls allCalls;
	
	private Declarations allCommunicationVars;
	private Declarations visibleCommunicationVars;
	private Declarations sharedOutputVars;
	
	public CompositePostconditionDataCollector(ICombinablePostconditions postcondition){
		init();
		
		this.left = postcondition;
		this.right = EmptyPostconditions.empty();
	}

	public CompositePostconditionDataCollector(ICombinablePostconditions left, ICombinablePostconditions right){
		init();
		
		if (!left.isChangePostcondition() && right.isChangePostcondition()){
			this.left = right;
			this.right = left;
		} else {
			this.left = left;
			this.right = right;
		}

	}

	private void init() {
		this.simplePromotions = new OutputPromotions();
		this.communicatingPromotions = new OutputPromotions();
		
		this.simpleCalls = new ChangeOperationCalls();
		this.communicatingCalls = new ChangeOperationCalls();
		this.allCalls = new ChangeOperationCalls();
		
		this.allCommunicationVars = new Declarations();
		this.visibleCommunicationVars = new Declarations();
		this.sharedOutputVars = new Declarations();
		
		this.newSharedOutputVariables = new Declarations();
		this.newCommunicationVariables = new Declarations();

	}
	
	public CompositePostconditionDataCollector conjoin(Declarations newSharedOutputVariables){
		this.newSharedOutputVariables = newSharedOutputVariables;
		
		this.addVariables();
		
		this.addSimplePromotions(left.getSimplePromotions());
		this.addCommunicatingPromotions(left.getCommunicatingPromotions());
		
		this.addUniqueSimplePromotions(right.getSimplePromotions(), this.newSharedOutputVariables);
		this.addUniqueCommunicatingPromotions(right.getCommunicatingPromotions(), this.newSharedOutputVariables);
		
		if (left.isChangePostcondition() && right.isChangePostcondition()){
			this.splitChangeOperationCalls(left.getSimpleCalls());
		} else {
			this.addSimpleCalls(left.getSimpleCalls());
		}
		this.addCommunicatingCalls(left.getCommunicatingCalls());
		this.addUncategorizedCalls(left.getUncategorizedCalls());
		
		this.splitChangeOperationCalls(right.getSimpleCalls());
		this.addCommunicatingCalls(right.getCommunicatingCalls());
		this.addUncategorizedCalls(right.getUncategorizedCalls());
		
		return this;
	}

	public CompositePostconditionDataCollector compose(Declarations newCommunicationVariables, Declarations newSharedOutputVariables,
			boolean isAssociative) {
		
		this.newSharedOutputVariables = newSharedOutputVariables;
		this.newCommunicationVariables = newCommunicationVariables;
		this.isAssociative = isAssociative;
		
		addVariables();
		
		this.splitOutputPromotions(left.getSimplePromotions(), this.newCommunicationVariables, Declarations.empty());
		this.addCommunicatingPromotions(left.getCommunicatingPromotions());
		
		this.splitOutputPromotions(right.getSimplePromotions(), this.newCommunicationVariables, this.newSharedOutputVariables);
		this.addUniqueCommunicatingPromotions(right.getCommunicatingPromotions(), this.newSharedOutputVariables);
		
		if (left.isChangePostcondition() && right.isChangePostcondition() || 
				newCommunicationVariables != null && !newCommunicationVariables.isEmpty()){
			this.splitChangeOperationCalls(left.getSimpleCalls());
		} else {
			this.addSimpleCalls(left.getSimpleCalls());
		}
		
		this.addCommunicatingCalls(left.getCommunicatingCalls());
		this.addUncategorizedCalls(left.getUncategorizedCalls());
		
		this.splitChangeOperationCalls(right.getSimpleCalls());
		this.addCommunicatingCalls(right.getCommunicatingCalls());
		this.addUncategorizedCalls(right.getUncategorizedCalls());
		
		return this;
	}
	
	public CompositePostconditionDataCollector eliminateOutputVariable(Declarations eliminationVariables){
		this.addVariables();
		this.eliminateVariables(eliminationVariables);
		
		this.addUniqueSimplePromotions(left.getSimplePromotions(), eliminationVariables);
		this.addUniqueSimplePromotions(right.getSimplePromotions(), eliminationVariables);

		this.addUniqueCommunicatingPromotions(left.getCommunicatingPromotions(), eliminationVariables);
		this.addUniqueCommunicatingPromotions(right.getCommunicatingPromotions(), eliminationVariables);
		
		this.addSimpleCalls(left.getSimpleCalls());
		this.addSimpleCalls(right.getSimpleCalls());
		
		this.addCommunicatingCalls(left.getCommunicatingCalls());
		this.addCommunicatingCalls(right.getCommunicatingCalls());
		
		this.addUncategorizedCalls(left.getUncategorizedCalls());
		this.addUncategorizedCalls(right.getUncategorizedCalls());

		return this;
	}

	private void eliminateVariables(Declarations eliminationVariables) {
		for (Variable v: eliminationVariables){
			this.visibleCommunicationVars.remove(v);
			this.visibleCommunicationVars.remove(v.getUndecoratedCopy());
			this.sharedOutputVars.remove(v);
		}
	}

	private void addVariables() {
		this.addCommunicationVars();
		this.addVisibleCommunicationVars();
		this.addSharedOutputVars();
	}

	private void addCommunicationVars() {
		this.allCommunicationVars.addAll(this.newCommunicationVariables);
		this.allCommunicationVars.addAll(left.getAllCommunicationVariables());
		this.allCommunicationVars.addAll(right.getAllCommunicationVariables());

		
	}
	private void addVisibleCommunicationVars() {
		if (isAssociative){
			this.visibleCommunicationVars.addAll(newCommunicationVariables);
		}
		this.visibleCommunicationVars.addAll(left.getVisibleCommunicationVariables());
		this.visibleCommunicationVars.addAll(right.getVisibleCommunicationVariables());
	}
	
	private void addSharedOutputVars() {
		this.sharedOutputVars.addAll(newSharedOutputVariables);
		this.sharedOutputVars.addAll(left.getSharedOutputVariables());
		this.sharedOutputVars.addAll(right.getSharedOutputVariables());
	}

	private void addSimplePromotions(OutputPromotions outputPromotions){
		this.simplePromotions.addAll(outputPromotions);
	}

	private void addUniqueSimplePromotions(OutputPromotions outputPromotions, Declarations sharedOutputVariables){
		if (outputPromotions != null){
			for (OutputPromotion outputPromotion: outputPromotions.getElements()){
				if (!outputPromotion.usesOutputParameters(sharedOutputVariables)){
					this.simplePromotions.add(outputPromotion);
				}
			}
		}
	}
	
	/**
	 * Used to decide whether outputPromotion remains a simple OutputPromotion or uses communication variables (other OutputPromotion) or 
	 * will no longer be used because of shared output)
	 * 
	 * @param outputPromotions			 - the Output Promotions to categorize
	 * @param communicationVariables	 - communication variables in the current scope
	 * @param sharedOutputVariables		 - shared output variables in the current scope
	 */
	private void splitOutputPromotions(OutputPromotions outputPromotions, Declarations communicationVariables, Declarations sharedOutputVariables) {
		if (outputPromotions != null){
			for (OutputPromotion outputPromotion: outputPromotions.getElements()){
				if (!outputPromotion.usesOutputParameters(sharedOutputVariables)){
					if (outputPromotion.usesCommunicationVariables(communicationVariables)){
						this.communicatingPromotions.add(outputPromotion);
					} else {
						this.simplePromotions.add(outputPromotion);
					}
				}
			}
		}
	}

	private void addCommunicatingPromotions(OutputPromotions communicationOutputPromotions) {
		this.communicatingPromotions.addAll(communicationOutputPromotions);
	}

	private void addCommunicatingCalls(ChangeOperationCalls communicationChangeOperationCalls) {
		this.communicatingCalls.addAll(communicationChangeOperationCalls);
		this.allCalls.addAll(communicationChangeOperationCalls);
	}

	/**
	 * Used to decide whether outputPromotion will be used any longer or not based on shared output.
	 * 
	 * @param outputPromotions			 - the Output Promotions to categorize
	 * @param sharedOutputVariables		 - shared output variables in the current scope
	 * @param newOutputPromotions	 - input/output parameter: container to put in future other output promotions
	 */
	private void addUniqueCommunicatingPromotions(OutputPromotions oldOutputPromotions, Declarations sharedOutputVariables) {
		if (oldOutputPromotions != null){
			for (OutputPromotion outputPromotion : oldOutputPromotions.getElements()) {
				if (!outputPromotion.usesOutputParameters(sharedOutputVariables)) {
					this.communicatingPromotions.add(outputPromotion);
				}
			}
		}
	}

	private void addSimpleCalls(ChangeOperationCalls changeOperationCalls){
		this.simpleCalls.addAll(changeOperationCalls);
		this.allCalls.addAll(changeOperationCalls);
	}
	
	private void splitChangeOperationCalls(ChangeOperationCalls changeOperationCalls) {
		if (changeOperationCalls != null){
			for (ChangeOperationCall changeOperationCall: changeOperationCalls.asList()){
				if (changeOperationCall.usesCommunicationVariables(this.newCommunicationVariables) ||
						changeOperationCall.usesOutputParameters(this.sharedOutputVars)){
					this.communicatingCalls.add(changeOperationCall);
					this.allCalls.add(changeOperationCall);
				} else {
					this.simpleCalls.add(changeOperationCall);
					this.allCalls.add(changeOperationCall);
				}
			}
		}
	}

	private void addUncategorizedCalls(ChangeOperationCalls uncategorizedCalls) {
		this.allCalls.addAll(uncategorizedCalls);
	}

	public Declarations getSharedOutputVariables() {
		return this.sharedOutputVars;
	}

	public Declarations getCommunicationVariables() {
		return this.allCommunicationVars;
	}

	public Declarations getVisibleCommunicationVariables() {
		return this.visibleCommunicationVars;
	}

	public OutputPromotions getSimplePromotions() {
		return this.simplePromotions;
	}
	
	public OutputPromotions getCommunicatingPromotions() {
		return this.communicatingPromotions;
	}
	
	public ChangeOperationCalls getSimpleCalls(){
		return this.simpleCalls;
	}

	public ChangeOperationCalls getCommunicatingCalls(){
		return this.communicatingCalls;
	}
	
	public ChangeOperationCalls getAllCalls(){
		return this.allCalls;
	}

}
