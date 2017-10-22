package at.ac.tuwien.oz.datatypes.postconditions;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Elements;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostcondition;
import at.ac.tuwien.oz.datatypes.postconditions.interfaces.IPostconditions;

public class SimplePostconditions extends Elements<IPostcondition> implements IPostconditions{

	public SimplePostconditions(){
		this.elements = new ArrayList<IPostcondition>();
	}
	
	@Override
	public List<ST> getTemplates() {
		List<ST> templates= new ArrayList<ST>();
		for(IPostcondition postcondition: this.elements){
			templates.add(postcondition.getTemplate());
		}
		return templates;
	}

	@Override
	public Idents getUsedIdentifiers() {
		Idents identifiers = new Idents();
		for(IPostcondition postcondition: this.elements){
			identifiers.addAll(postcondition.getIdentifiers());
		}
		return identifiers;
	}

}
