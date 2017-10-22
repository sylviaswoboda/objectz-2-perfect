package at.ac.tuwien.oz.translator.templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;

public class PerfectOperationTemplateProvider {

	private static PerfectOperationTemplateProvider templateProvider;

	public static PerfectOperationTemplateProvider getInstance() {
		if (templateProvider == null) {
			templateProvider = new PerfectOperationTemplateProvider(PerfectTemplateProvider.getInstance());
		}
		return templateProvider;
	}

	public static void setInstance(PerfectOperationTemplateProvider provider) {
		templateProvider = provider;
	}

	private PerfectTemplateProvider perfect;

	private PerfectOperationTemplateProvider(PerfectTemplateProvider perfect) {
		this.perfect = perfect;
	}

	private ST getBoolFunctionOperation(IOperation operation) {
		String operationName = operation.getName();
		ST inputDeclarations = getParenthesizedDeclarationInFunctionList(operation.getInputParameters().sorted());
		List<ST> preconditions = operation.getPreconditions().getTemplates();

		ST boolFunction = perfect.getBoolFunction(operationName, inputDeclarations, preconditions);
		return boolFunction;
	}

	private ST getFunctionOperation(IOperation operation) {
		ST inputDeclarations = getParenthesizedDeclarationInFunctionList(operation.getInputParameters().sorted());
		ST outputDeclarations = getDeclarationInFunctionList(operation.getOutputParameters().sorted());
		List<ST> postconditions = operation.getPostconditions().getTemplates();

		ST preconditionOp = null;
		ST preconditionOpCall = null;

		if (operation.hasPreconditions()) {
			preconditionOp = getBoolFunctionOperation(operation.getPreconditionFunction());
			preconditionOpCall = operation.getPreconditionFunctionCall().getTemplate();
		}
		ST opaque = null;
		if (operation.isOpaque()){
			opaque = perfect.getOpaque();
		}

		
		ST function = perfect.getFunction(operation.getName(), opaque, inputDeclarations,
				preconditionOpCall, outputDeclarations, postconditions);

		ST functionWithPreconditionOp = perfect.getOperationWithPreconditionOp(function, preconditionOp, null);

		return functionWithPreconditionOp;
	}

	private ST getChangeOperation(IOperation op) {
		ST parameterDeclarationsTemplate = 
				getParenthesizedDeclarationsInSchema(
						op.getInputParameters().sorted(), 
						op.getOutputParameters().sorted(),
						op.getModifiableInputParameters());

		List<ST> postconditionTemplates = op.getPostconditions().getTemplates();

		ST preconditionOpCallTemplate = null;
		ST preconditionOpTemplate = null;

		ST postconditionOpTemplate = null;
		if (op.hasPreconditions()) {
			preconditionOpTemplate = getBoolFunctionOperation(op.getPreconditionFunction());
			preconditionOpCallTemplate = op.getPreconditionFunctionCall().getTemplate();
		}
		
		if (op.includePostconditionFunction()){
			postconditionOpTemplate = getFunctionOperation(op.getPostconditionFunction());
		}

		ST changeOpTemplate;
		ST opaque = null;
		if (op.isOpaque()){
			opaque = perfect.getOpaque();
		}
		
		
		if (op.isOperationExpression()){
			changeOpTemplate = perfect.getPromotedChangeOperation(
					op.getName(), 
					opaque,
					parameterDeclarationsTemplate, 
					preconditionOpCallTemplate, postconditionTemplates);

		} else {
			ST deltalistTemplate = getDeltaList(
					op.getDeltalist(), 
					op.getOutputParameters().getIdentifiers());
			
			changeOpTemplate = perfect.getChangeOperation(
					op.getName(), 
					parameterDeclarationsTemplate, 
					deltalistTemplate,
					preconditionOpCallTemplate, 
					postconditionTemplates);
		}
		
		ST changeOpWithPreconditionOpTemplate = perfect.getOperationWithPreconditionOp(
				changeOpTemplate, 
				preconditionOpTemplate,
				postconditionOpTemplate);

		return changeOpWithPreconditionOpTemplate;
	}

	public ST getOperationTemplate(IOperation op) {
		ST operationTemplate = null;
		
		if (op.isBoolFunction()){
			operationTemplate = getBoolFunctionOperation(op);
		} else if (op.isFunction()){
			operationTemplate = getFunctionOperation(op);
		} else if (op.isChangeOperation()){
			operationTemplate = getChangeOperation(op);
		} 
		return operationTemplate;
	}

	private ST getParenthesizedDeclarationInFunctionList(Declarations parameters) {
		ST declarationList = null;
		if (!parameters.isEmpty()) {
			declarationList = getDeclarationInFunctionList(parameters);
			declarationList = perfect.getParenthesized(declarationList);
		}
		return declarationList;
	}
	
	private ST getParenthesizedDeclarationsInSchema(Declarations inputVariables, Declarations outputVariables, Declarations modifiableInputVariables) {
		Declarations variables = new Declarations();
		variables.addAll(inputVariables);
		variables.addAll(outputVariables);
	
		ST declarationList = null;
		if (!variables.isEmpty()) {
			List<ST> declarations = new ArrayList<ST>();
			for (Variable declaration : variables.asList()) {
				if (declaration.isInputVariable() && modifiableInputVariables.contains(declaration)) {
					declarations.add(perfect.getModifiableDeclarationInOperation(declaration));
				} else if (declaration.isInputVariable()){
					declarations.add(perfect.getDeclarationInOperation(declaration));
				} else if (declaration.isOutputVariable()) {
					declarations.add(perfect.getOutputVariableDeclarationInSchema(declaration));
				}
			}
			declarationList = perfect.getItemList(declarations);
			declarationList = perfect.getParenthesized(declarationList);
		}
		return declarationList;
	}

	private ST getDeclarationInFunctionList(Declarations parameters) {
		ST declarationList;
		List<ST> declarations = new ArrayList<ST>();
		for (Variable declaration : parameters.asList()) {
			declarations.add(perfect.getDeclarationInOperation(declaration));
		}
		declarationList = perfect.getItemList(declarations);
		return declarationList;
	}

	private ST getDeltaList(List<String> changedStateVariables, Idents outputIdents) {
		List<ST> items = new ArrayList<ST>();

		for (String changedStateVar : changedStateVariables) {
			items.add(new ST(changedStateVar));
		}
		for (Ident outputIdent : outputIdents.asList()) {
			items.add(perfect.getId(outputIdent));
		}
		return perfect.getItemList(items);
	}
	
	public ST createVarPostcondition(ST template, Map<Variable, Variable> tempVariables) {
		StringTemplateCloner cloner = new StringTemplateCloner();
		StringTemplateSubstitutor substitutor = new StringTemplateSubstitutor();

		List<ST> tempVarDeclarations = new ArrayList<ST>();
		ST templateClone = cloner.clone(template);

		for (Variable outputVariable : tempVariables.keySet()) {
			ST tempVarDeclaration = perfect.getDeclarationInOperation(tempVariables.get(outputVariable));
			tempVarDeclarations.add(tempVarDeclaration);

			ST oldIdent = perfect.getId(outputVariable.getId());
			ST newIdent = perfect.getId(tempVariables.get(outputVariable).getId());

			substitutor.substituteIdentifier(templateClone, oldIdent, newIdent);
		}

		ST tempVarDeclarationList = perfect.getItemList(tempVarDeclarations);
		return perfect.getPostconditionWithDeclarations(tempVarDeclarationList, templateClone);
	}

}
