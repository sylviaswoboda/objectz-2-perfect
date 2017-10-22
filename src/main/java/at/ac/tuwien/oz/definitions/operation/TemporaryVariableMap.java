package at.ac.tuwien.oz.definitions.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Variable;

public class TemporaryVariableMap {

	private Map<Variable, Entry> temporaryVariableMap;
	
	public TemporaryVariableMap(){
		this.temporaryVariableMap = new HashMap<Variable, Entry>();
	}
	
	/**
	 * 
	 * @param originalVariable
	 * @return the newly associated temporary variable
	 */
	public Variable add(Variable originalVariable){
		Variable undecoratedCopy = originalVariable.getUndecoratedCopy();

		if (this.temporaryVariableMap.containsKey(undecoratedCopy)){
			// nothing more to add
			return null;
		}
		
		Variable temporaryVariable = Variable.createTempVarCopy(originalVariable);
		Entry e = new Entry(temporaryVariable);
		this.temporaryVariableMap.put(undecoratedCopy, e);
		return temporaryVariable;
	}
	
	public Variable add(Variable originalVariable, Variable temporaryVariable){
		Variable undecoratedCopy = originalVariable.getUndecoratedCopy();
		Entry e = new Entry(temporaryVariable);
		this.temporaryVariableMap.put(undecoratedCopy, e);
		return temporaryVariable;
	}
	
	public List<Variable> add(Variable originalVariable, int times) {
		Variable undecoratedCopy = originalVariable.getUndecoratedCopy();
		List<Variable> newTemporaryVariables = new ArrayList<Variable>();
		for (int i = 0; i < times; i++){
			Variable temporaryVariable = Variable.createTempVarCopy(originalVariable);
			newTemporaryVariables.add(temporaryVariable);
		}
		Entry e = new Entry(newTemporaryVariables);
		this.temporaryVariableMap.put(undecoratedCopy, e);
		return newTemporaryVariables;
	}
	
	/**
	 * Gets the temporary variable for the given key (base name)
	 * 
	 * @param key
	 * @return null - if no the key is not in the map
	 * 		   mappedValue - otherwise
	 */
	public Variable get(Variable key){
		Variable undecoratedKey = key.getUndecoratedCopy();
		Entry e = this.temporaryVariableMap.get(undecoratedKey);
		if (e != null){
			return e.get();
		}
		return null;
	}
	public Variable use(Variable key){
		Variable undecoratedKey = key.getUndecoratedCopy();
		Entry e = this.temporaryVariableMap.get(undecoratedKey);
		if (e != null){
			return e.use();
		}
		return null;
	}

	
	public boolean containsKey(Variable key){
		Variable undecoratedKey = key.getUndecoratedCopy();
		return temporaryVariableMap.containsKey(undecoratedKey);
	}
	
	public Set<Variable> originalVariables(){
		return this.temporaryVariableMap.keySet();
	}

	public Declarations mappedTemporaryVariables(){
		Declarations temporaryVariables = new Declarations();
		for(Entry entry: this.temporaryVariableMap.values()){
			for(Variable tempVar: entry.temporaryVariables){
				temporaryVariables.add(tempVar);
			}
		}
		return temporaryVariables.sorted();
	}

	public void addAll(TemporaryVariableMap otherMap) {
		this.temporaryVariableMap.putAll(otherMap.temporaryVariableMap);
	}

	public int size() {
		return temporaryVariableMap.size();
	}

	
	private class Entry{
		private List<Variable> temporaryVariables;
		private int index;
		
		Entry(Variable v){
			temporaryVariables = new ArrayList<Variable>();
			temporaryVariables.add(v);
			index = 0;
		}
		Entry(List<Variable> vars){
			temporaryVariables = new ArrayList<Variable>();
			temporaryVariables.addAll(vars);
			index = 0;
		}

		Variable get(){
			Variable result = temporaryVariables.get(0);
			return result;
		}
		Variable use(){
			Variable result = temporaryVariables.get(index);
			index = (index + 1) % temporaryVariables.size();
			return result;
		}
	}
}
