package at.ac.tuwien.oz.definitions.ozclass;

import java.util.ArrayList;
import java.util.HashMap;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.SearchableElements;


/**
 * Container for multiple Object Z classes
 * 
 * @author sylvias
 *
 */
public class ObjectZClasses extends SearchableElements<ObjectZClass> {

	public ObjectZClasses(){
		this.elements = new ArrayList<ObjectZClass>();
		this.elementMap = new HashMap<Ident, ObjectZClass>();
	}
}
