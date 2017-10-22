package at.ac.tuwien.oz.translator.templates;

import java.util.List;

import org.stringtemplate.v4.ST;

public class PerfectTemplateChecker {

	public boolean isId(ST originalTemplate) {
		if ("/id".equals(originalTemplate.getName())){
			return true;
		}
		return false;
	}

	public boolean hasSameNameAndDecoration(ST template, ST identifier) {
		Object templateName = template.getAttribute("name");
		Object templateDecoration = template.getAttribute("decoration");
	
		Object identifierName = identifier.getAttribute("name");
		Object identifierDecoration = identifier.getAttribute("decoration");
		
		boolean namesEqual = false;
		boolean decorationsEqual = false;
		
		if (templateName == identifierName ||
				templateName != null && templateName.equals(identifierName)){
			namesEqual = true;
		}
		if (templateDecoration == identifierDecoration ||
				templateDecoration != null && templateDecoration.equals(identifierDecoration)){
			decorationsEqual = true;
		}
		
		if (namesEqual && decorationsEqual){
			return true;
		}
		
		return false;
	}

	public boolean isInputId(ST id) {
		Object decoration = id.getAttribute("decoration");
		if (decoration == null){
			return false;
		}
		if ("_in".equals(decoration.toString())){
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<ST> getIdsOfAttributeAccessor(ST caller) {
		if ("/attrCall".equals(caller.getName())){
			return (List<ST>)caller.getAttribute("ids");
		}
		return null;
	}

	
}
