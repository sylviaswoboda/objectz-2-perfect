package at.ac.tuwien.oz.definitions.operation;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.parser.OZParser.CallerContext;
import at.ac.tuwien.oz.parser.OZParser.OperationExpressionDefContext;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;

public abstract class OperationExpression extends Operation implements IComposableOperation{

	public OperationExpression (){
		super(null);
	}
	
	protected ObjectZClass findDefiningClassOfCaller(ParseTreeProperty<ExpressionType> typeTree, CallerContext callerCtx) {
		ObjectZClass definingClass = this.definingClass;
		if (callerCtx != null){
			ExpressionType definingClassType = typeTree.get(callerCtx);
			if (definingClassType != null){
				if (definingClassType.isUserDefinedType()){
					// a: MyClass
					ObjectZDefinition program = this.definingClass.getDefinition();
					definingClass = program.resolveClass(definingClassType.getEffectiveTypeId());
				} else if (definingClassType.isCollectionType()) {
					// cards: !P Creditcard
					// c: cards
//					Type declaredTypeOfVariable = definingClassType.getSubType(0);
//					if (declaredTypeOfVariable.isCollection()){
//						String className = declaredTypeOfVariable.getSubType(0).getName();
//						ObjectZDefinition program = this.definingClass.getDefinition();
//						definingClass = program.resolveClass(new Ident(className));
//					}
					ObjectZDefinition program = this.definingClass.getDefinition();
					definingClass = program.resolveClass(definingClassType.getBaseTypeId());
				}
			}
		}
		if (definingClass == null){
			throw new ObjectZToPerfectTranslationException("Could not find defining class of callerCtx: " + callerCtx);
		}
		return definingClass;
	}

	@Override
	public void setDefiningClass(ObjectZClass currentClass) {
		this.definingClass = currentClass;
	}
	
	@Override
	public boolean isOperationExpression() {
		return true;
	}

	public void setContext(OperationExpressionDefContext ctx) {
		super.ctx = ctx;
	}

	public boolean isAnonymousSchemaOperation() {
		return false;
	}
	
}
