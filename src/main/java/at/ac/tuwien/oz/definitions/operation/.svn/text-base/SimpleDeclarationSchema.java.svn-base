package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.SimplePostconditions;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.SimplePreconditions;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public class SimpleDeclarationSchema extends SimpleOperation{

	private SimplePreconditions preconditions;
	private SimplePostconditions postconditions;
	
	public SimpleDeclarationSchema(String name, Declarations originalDeclarations,
			ObjectZClass definingClass, ParseTree operationCtx) {
		super(name, originalDeclarations, AxiomReferences.empty(), definingClass, operationCtx);
		this.preconditions = new SimplePreconditions();
		this.postconditions = new SimplePostconditions();
	}

	@Override
	public boolean isBoolFunction() {
		return false;
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
	public void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree) {
		this.preconditions.addAll(getVariableTypePreconditions().asList());
		this.postconditions.addAll(getVariableTypePostconditions().asList());
	}

	@Override
	public IPreconditions getPreconditions() {
		return this.preconditions;
	}

	@Override
	public IPostconditions getPostconditions() {
		return this.postconditions;
	}

	@Override
	public Idents getChangedStateVariables() {
		return Idents.empty();
	}
}
