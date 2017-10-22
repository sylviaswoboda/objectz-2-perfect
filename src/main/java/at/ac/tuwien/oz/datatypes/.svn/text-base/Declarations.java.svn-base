package at.ac.tuwien.oz.datatypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Declarations extends Elements<Variable>{

	private static final Declarations EMPTY_DECLARATIONS = new Declarations();
	
	public static final Declarations empty(){
		return EMPTY_DECLARATIONS;
	}
	
	public Declarations(){
		this.elements = new ArrayList<Variable>();
	}
	public Declarations(Collection<Variable> variables){
		this.elements = new ArrayList<Variable>();
		this.addAll(variables);
	}
	
	public Declarations(Variable elem){
		this();
		this.add(elem);
	}

	public Declarations(Variable... elems){
		this();
		this.addNew(elems);
	}
	
	public void addAll(Declarations declarations) {
		if (declarations != null){
			for(Variable d: declarations.asList()){
				if (!this.elements.contains(d)){
					this.elements.add(d);
				}
			}
		}
	}

	public boolean hasOutputVariables() {
		for (Variable v: this.elements){
			if (v.isOutputVariable()){
				return true;
			}
		}
		return false;
	}

	public Declarations getAuxiliaryVariables() {
		Declarations auxiliaryParameters = new Declarations();
		for (Variable d: this.elements){
			if (!d.isOutputVariable() && !d.isInputVariable()){
				auxiliaryParameters.add(d);
			}
		}
		Collections.sort(auxiliaryParameters.asList());
		
		return auxiliaryParameters;
	}
	
	public Declarations getOutputVariables() {
		Declarations outputParameters = new Declarations();
		for (Variable d: this.elements){
			if (d.isOutputVariable()){
				outputParameters.add(d);
			}
		}
		Collections.sort(outputParameters.asList());
		return outputParameters;
	}

	public Declarations getInputVariables() {
		Declarations inputParameters = new Declarations();
		for (Variable d: this.elements){
			if (d.isInputVariable()){
				inputParameters.add(d);
			}
		}
		Collections.sort(inputParameters.asList());
		return inputParameters;
	}

	/**
	 * 
	 * @return Declarations that are common in this object and in parameter as a new Declaration object
	 */
	public Declarations getSharedDeclarations(Declarations other){
		Declarations sharedVariables = new Declarations();
		
		sharedVariables.addAll(this);
		sharedVariables.retainAll(other.asList());
		
		return sharedVariables;
	}

	public Declarations getNotSharedDeclarations(Declarations other) {
		Declarations notSharedVariables = new Declarations();
		
		notSharedVariables.addAll(this);
		notSharedVariables.removeAll(other.asList());
		
		return notSharedVariables;
	}

	public Declarations sorted(){
		Collections.sort(this.asList());
		return this;
	}
	
	public boolean contains(Variable searchVariable){
		return this.elements.contains(searchVariable);
	}

	public boolean containsIgnoreDecoration(Variable variable) {
		for (Variable v: this.elements){
			if (v.hasSameBaseName(variable)){
				return true;
			}
		}
		return false;
	}

	public Declarations getOutputCopies() {
		Declarations copy = new Declarations();
		
		for (Variable elem: this.elements){
			copy.add(elem.getOutputCopy());
		}
		return copy;
	}
	
	public Declarations getInputCopies() {
		Declarations copy = new Declarations();
		
		for (Variable elem: this.elements){
			copy.add(elem.getInputCopy());
		}
		return copy;
	}
	
	public boolean removeAll(Declarations other){
		return this.removeAll(other.asList());
	}

	public void clear() {
		this.elements.clear();
	}

	@Override
	public String toString() {
		return "Declarations [elements=" + elements + "]";
	}

	public Idents getIdentifiers() {
		Idents identifiers = new Idents();
		for (Variable v: this.elements){
			identifiers.add(v.getId());
		}
		return identifiers;
	}

	public Variable get(Ident ident) {
		if (ident == null){
			return null;
		}
		for (Variable v: this.elements){
			if (ident.equals(v.getId())){
				return v;
			}
		}
		return null;
	}

}
