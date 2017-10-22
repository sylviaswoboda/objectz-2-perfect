package at.ac.tuwien.oz.datatypes.preconditions;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Elements;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPrecondition;
import at.ac.tuwien.oz.datatypes.preconditions.interfaces.IPreconditions;

public class SimplePreconditions extends Elements<IPrecondition> implements IPreconditions{

	public SimplePreconditions(){
		this.elements = new ArrayList<IPrecondition>();
	}
	
	public SimplePreconditions(IPrecondition... preconditions) {
		this();
		this.addNew(preconditions);
	}

	@Override
	public List<ST> getTemplates() {
		List<ST> templates= new ArrayList<ST>();
		for(IPrecondition precondition: this.elements){
			templates.add(precondition.getTemplate());
		}
		return templates;
	}

	@Override
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
}
