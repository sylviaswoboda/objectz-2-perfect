package at.ac.tuwien.oz.translator.operationtranslation;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReference;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public class OperationComposer {
	
	private ObjectZDefinition program;
	private ParseTreeProperty<ST> templateTree;
	private ParseTreeProperty<ExpressionType> typeTree;
	
	public OperationComposer(ObjectZDefinition program, ParseTreeProperty<ST> templateTree, ParseTreeProperty<ExpressionType> typeTree){
		this.program = program;
		this.templateTree = templateTree;
		this.typeTree = typeTree;
	}
	
	public void compose(){
		for (ObjectZClass clazz: program.getObjectZClasses()){
			resolveReferencesOfOperationExpressions(clazz);
		}
		for (ObjectZClass clazz: program.getObjectZClasses()){
			composeOperationsOfClass(clazz);
		}
	}

	private void composeOperationsOfClass(ObjectZClass definingClass) {
		composeSimpleOperations(definingClass);
		composeOperationExpressions(definingClass);
	}

	private void resolveReferencesOfOperationExpressions(ObjectZClass definingClass) {
		for (IComposableOperation op: definingClass.getOperationExpressions()){
			op.resolveOperationReferences(this.typeTree);
		}
	}

	private void composeOperationExpressions(ObjectZClass definingClass) {
		for (IComposableOperation op: definingClass.getOperationExpressions()){
			op.resolveTemplates(templateTree);
			op.createPreAndPostconditions(typeTree);
		}
	}

	private void composeSimpleOperations(ObjectZClass definingClass) {
		for (IOperation op: definingClass.getSimpleOperations()){
			AxiomReferences axiomReferences = op.getPreconditionAxiomReferences();
			if (axiomReferences != null && !axiomReferences.isEmpty()){
				for (AxiomReference axiomReference: axiomReferences){
					axiomReference.buildAxiom(templateTree);
				}
			}
			axiomReferences = op.getPostconditionAxiomReferences();
			if (axiomReferences != null && !axiomReferences.isEmpty()){
				for (AxiomReference axiomReference: axiomReferences){
					axiomReference.buildAxiom(templateTree);
				}
			}

			op.createPreAndPostconditions(typeTree);
		}
	}

}
