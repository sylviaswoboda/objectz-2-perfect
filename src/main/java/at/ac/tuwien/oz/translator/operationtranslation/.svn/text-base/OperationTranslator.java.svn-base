package at.ac.tuwien.oz.translator.operationtranslation;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.definitions.operation.interfaces.IOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.translator.templates.PerfectOperationTemplateProvider;

public class OperationTranslator {

	private PerfectOperationTemplateProvider perfectOperations;
	private ObjectZDefinition definition;
	private ParseTreeProperty<ST> templateTree;
	
	public OperationTranslator(ObjectZDefinition program, ParseTreeProperty<ST> templateTree) {
		this.definition = program;
		this.templateTree = templateTree;
		this.perfectOperations = PerfectOperationTemplateProvider.getInstance();
	}

	public void translate(){
		for (ObjectZClass clazz: definition.getObjectZClasses()){
			for (IOperation op: clazz.getSimpleOperations()){
				ST operationTemplate = perfectOperations.getOperationTemplate(op);
				op.setTemplate(operationTemplate);
				templateTree.put(op.getContext(), operationTemplate);
			}
			for (IComposableOperation op: clazz.getOperationExpressions()){
				ST operationTemplate = perfectOperations.getOperationTemplate(op);
				op.setTemplate(operationTemplate);
				templateTree.put(op.getContext(), operationTemplate);
			}
		}
	}
}
