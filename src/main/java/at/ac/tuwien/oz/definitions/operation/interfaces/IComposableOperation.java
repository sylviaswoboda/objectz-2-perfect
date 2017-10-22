package at.ac.tuwien.oz.definitions.operation.interfaces;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.NonDeterministicPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.SequentialPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IComposablePostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.NonDeterministicPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.composable.SequentialPreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IComposablePreconditions;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public interface IComposableOperation extends IOperation{

	List<IPromotedOperation> getPromotedOperations();
	List<IPromotedOperation> getPromotedOperationsWithOutputParameter(Variable outputParameter, boolean ignoreDecoration);
	
	boolean isNonDeterministicChoice();
	boolean isCombinable();
	boolean isSequentialComposition();

	IComposablePreconditions getComposablePreconditions();
	ICombinableOperation getCombinableOperation();
	
	IComposablePostconditions getComposablePostconditions();
	IComposablePostconditions getComposablePostconditionsWithoutPromotion(Declarations sharedOutputVariables);

	NonDeterministicPreconditions getNonDeterministicPreconditions();
	/**
	 * @return the non deterministic postconditions of this operation 
	 * 		   or null, if not applicable (that is {@link #isNonDeterministicChoice()} is false)
	 */
	NonDeterministicPostconditions getNonDeterministicPostconditions();
	
	SequentialPreconditions getSequentialPreconditions();
	/**
	 * 
	 * @return  the sequential postconditions of this operation 
	 * 			or null, if not applicable (that is {@link #isSequentialComposition()} is false)
	 */
	SequentialPostconditions getSequentialPostconditions();
	
	/**
	 * Selects only those parts of the postcondition that are not promotions of the provided outputVariables.
	 * 
	 * @param outputVariables the output variables that should not be promoted in the resulting postcondition
	 * @return the sequential postconditions of this operation without promotion of the given outputVariables or null if not applicable.
	 */
	SequentialPostconditions getSequentialPostconditionsWithoutPromotion(Declarations outputVariables);
	
	void setDefiningClass(ObjectZClass currentClass);
	void resolveOperationReferences(ParseTreeProperty<ExpressionType>typeTree);
	void resolveTemplates(ParseTreeProperty<ST> templateTree);
}
