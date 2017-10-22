package at.ac.tuwien.oz.datatypes.axioms;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Idents;


/** 
 * Wrapper for a pre- or postcondition StringTemplate predicate
 * 
 * @author sylvias
 *
 */
public abstract class Axiom{

    // holds all attributes used in this axiom/predicate
    // it includes all sub-axioms like in
    // ((a and b) or c) <=> (a => c)
    // you have the following axioms
    // 1.   (a and b)
    // 2.   ((a and b) or c)     with or as the conjunctive
    // 3.   (a => c)
    // 4.   ((a and b) or c) <=> (a => c)   with <=> as the conjunctive
    // each sub axiom has a set of variables
    // 1.   {a, b}
    // 2.   {c} ++ {a, b} = {a,b,c}     from 1
    // 3.   {a,c}
    // 4.   {a,b,c} ++ {a,c} = {a,b,c}  from 2,3
    private Idents identifiers;
    private ST axiom;


    protected Axiom(ST axiom, Idents identifiers){
    	this.identifiers = new Idents();
        this.axiom = axiom;
        this.identifiers.addAll(identifiers);
    }
    
    public ST getPredicate(){
    	return this.axiom;
    }
    
    public Idents getIdentifiers(){
        return this.identifiers;
    }

    public abstract boolean isPrecondition();
    public abstract boolean isPostcondition();
    
	@Override
	public String toString() {
		return "Axiom [axiom=" + axiom.render() + "]";
	}
    
    
}