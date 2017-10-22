package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.axioms.Axiom;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

/**
 * Representation of an operation that has no output parameters and
 * does not change state variables.
 * 
 * @author sylvias
 *
 */
public class SimpleBoolFunction extends SimpleOperation{
	
	private SimplePreconditions preconditions;
	private EmptyPostconditions postconditions;

	public SimpleBoolFunction(String name, Declarations declarations, AxiomReferences originalPredicates, ObjectZClass definingClass, ParseTree operationCtx){
		super(name, declarations, originalPredicates, definingClass, operationCtx);
	}
	
	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree){
		PreconditionFactory preconditionFactory = PreconditionFactory.getInstance();
		this.preconditions = new SimplePreconditions();
		
		if (super.getOriginalPreconditions() != null){
			for (Axiom originalPredicate: super.getOriginalPreconditions().asList()){
				IPrecondition simplePrecondition = preconditionFactory.createSimplePrecondition(originalPredicate);
				preconditions.add(simplePrecondition);
			}
		}
		this.preconditions.addAll(getVariableTypePreconditions().asList());
		this.preconditionFunction = new PreconditionFunction(this, this.preconditions, this.definingClass);
		
		this.postconditions = EmptyPostconditions.empty();
	}
	
	@Override
	public boolean isBoolFunction() {
		return true;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

	@Override
	public boolean isChangeOperation() {
		return false;
	}


	@Override
	public List<String> getDeltalist() {
		return new ArrayList<String>();
	}

	@Override
	public SimplePreconditions getPreconditions() {
		return this.preconditions;
	}

	@Override
	public EmptyPostconditions getPostconditions() {
		return this.postconditions;
	}

	@Override
	public Idents getUsedStateVariables() {
		return Idents.empty();
	}

	@Override
	public Idents getChangedStateVariables() {
		return Idents.empty();
	}
	
}