package at.ac.tuwien.oz.datatypes.preconditions;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.translator.templates.StringTemplateCloner;

public class SimplePrecondition implements IPrecondition{

	private ST template;
	
	public SimplePrecondition(ST originalPredicate) {
		StringTemplateCloner cloner = new StringTemplateCloner();
		this.template = cloner.clone(originalPredicate);
	}

	@Override
	public ST getTemplate() {
		return template;
	}
}
