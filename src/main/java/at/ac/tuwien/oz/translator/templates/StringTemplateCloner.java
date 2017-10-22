package at.ac.tuwien.oz.translator.templates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.stringtemplate.v4.ST;

public class StringTemplateCloner {

	public ST clone(ST original){
		if (original == null){
			return null;
		}
		ST clone = new ST(original);
		
		if (original.getAttributes() != null){
			for (String attributeName: original.getAttributes().keySet()){
				Object attribute = original.getAttribute(attributeName);
				if (attribute instanceof ST){
					ST attributeST = (ST) attribute;
					clone.remove(attributeName);
					clone.add(attributeName, clone(attributeST));
				} else if (attribute instanceof Collection<?>){
					List<ST> newListOfST = new ArrayList<ST>();
					boolean useNewList = false;

					for (Object element: (Collection<?>)attribute){
						if (element instanceof ST){
							useNewList = true;
							newListOfST.add(clone((ST)element));
						}
					}

					if (useNewList){
						clone.remove(attributeName);
						clone.add(attributeName, newListOfST);
					}
				}
			}
		}

		return clone;
	}
	
}
