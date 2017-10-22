package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.combinable.EmptyPostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFactory;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public class PreconditionFunction extends Operation {

	private IOperation parentOperation;
	
	private IPreconditions preconditions;
	
	public PreconditionFunction(IOperation parentOperation, IPreconditions preconditions, ObjectZClass definingClass){
		super(parentOperation.getPreconditionFunctionName(), definingClass, null);	// a precondition function belongs and is output by the parent operation
		this.addInputParameters(parentOperation.getInputParameters());
		this.parentOperation = parentOperation;
		this.preconditions = preconditions;
	}
	
	@Override
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree) {
		// is already done in constructor
	}
	
	public PreconditionFunctionCall getCall(){
		PreconditionFactory preconditionFactory = PreconditionFactory.getInstance();
		return preconditionFactory.createPreconditionFunctionCall(null, this);
	}

	public void resetName() {
		this.name = parentOperation.getPreconditionFunctionName();
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
	public IPreconditions getPreconditions() {
		return this.preconditions;
	}
	@Override
	public IPostconditions getPostconditions() {
		return EmptyPostconditions.empty();
	}
	@Override
	public Idents getChangedStateVariables() {
		return Idents.empty();
	}

	@Override
	public Idents getUsedStateVariables() {
		return Idents.empty();
	}
	
	public IOperation getParent(){
		return this.parentOperation;
	}

	@Override
	public boolean isStateVariable(Ident usedVariable) {
		return this.definingClass.isStateVariable(usedVariable);
	}
}
