package at.ac.tuwien.oz.definitions.operation.interfaces;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;
import at.ac.tuwien.oz.datatypes.preconditions.PreconditionFunctionCall;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;
import at.ac.tuwien.oz.definitions.IDefinition;
import at.ac.tuwien.oz.definitions.operation.Operation;
import at.ac.tuwien.oz.definitions.operation.PreconditionFunction;

public interface IOperation extends IDefinition{
	
	String getName();
	void rename(String operationName);

	boolean isBoolFunction();
	boolean isFunction();
	boolean isChangeOperation();

	Declarations getInputParameters();
	Declarations getAuxiliaryParameters();
	Declarations getOutputParameters();
	Declarations getModifiableInputParameters();
	
	List<String> getDeltalist();
	
	AxiomReferences getPreconditionAxiomReferences();
	AxiomReferences getPostconditionAxiomReferences();
	
	void createPreAndPostconditions(ParseTreeProperty<ExpressionType> typeTree);
	
	IPreconditions getPreconditions();
	boolean hasPreconditions();
	PreconditionFunctionCall getPreconditionFunctionCall();
	PreconditionFunction     getPreconditionFunction();
	
	Operation getPostconditionFunction();
	
	IPostconditions getPostconditions();

	Idents getUsedStateVariables();
	
	String getPreconditionFunctionName();
	String getPostconditionFunctionName();

	Idents getChangedStateVariables();
	boolean isStateVariable(Ident usedVariable);
	
	boolean hasInputVariable(Variable variable);
	boolean hasInputVariables(Declarations inputParameters);
	boolean hasOutputVariable(Variable outputVariable);
	boolean hasOutputVariable(Variable outputParameter, boolean ignoreDecoration);
	
	boolean isSimpleOperation();
	boolean isOperationExpression();
	
	ST getTemplate();
	void setTemplate(ST template);
	
	boolean isAnonymousOperation();
	
	boolean includePostconditionFunction();
	void setIncludePostconditionFunction(boolean include);
	boolean isOpaque();
	void setOpaque(boolean opaque);

}
