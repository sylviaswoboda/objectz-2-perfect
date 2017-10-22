package at.ac.tuwien.oz.ordering;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;

public interface ISchemaVariable {

	Ident getId();
	ExpressionType getExpressionType();
	boolean hasPredefinedType();
	boolean hasUserDefinedType();
	boolean hasCollectionType();

}
