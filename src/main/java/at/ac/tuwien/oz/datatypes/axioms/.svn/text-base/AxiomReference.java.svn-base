package at.ac.tuwien.oz.datatypes.axioms;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.ordering.ISchemaPredicate;

/**
 * Container for the original predicate
 *  * its PredicateContext
 *  * all the identifiers used by it
 *  * the predicate type (Precondition/Postcondition
 *  * 
 * @author sylvias
 *
 */
public class AxiomReference implements ISchemaPredicate{
	
	public enum AxiomReferenceType{
		PRECONDITION,
		POSTCONDITION
	}

	// initially set by constructor from input
	private Idents identifiers;
	private ParseTree predicateCtx;
	// calculated in constructor depending on identifiers
	private AxiomReferenceType type;
	// added by OperationComposer (in operations) and DefinitionTranslator in local definitions and state 
	private Axiom axiom;
	
	public AxiomReference(Idents identifiers, ParseTree ctx){
		this.identifiers = new Idents();
		this.predicateCtx = ctx;
		
		if (identifiers != null){
			this.identifiers.addAll(identifiers);
		}
		setType();
		
		this.axiom = null;
	}
	
	public void buildAxiom(ParseTreeProperty<ST>templateTree){
		ST predicateTemplate = templateTree.get(predicateCtx);
		if (isPrecondition()){
			axiom = new PreconditionAxiom(predicateTemplate, identifiers);
		} else if (isPostcondition()){
			axiom = new PostconditionAxiom(predicateTemplate, identifiers);
		}
	}

	private void setType() {
		for (Ident i : this.identifiers.asList()){
			if (i.isOutputIdent()){
				this.type = AxiomReferenceType.POSTCONDITION;
				return;
			}
			if (i.isPrimedIdent()){
				this.type = AxiomReferenceType.POSTCONDITION;
				return;
			}
		}
		this.type = AxiomReferenceType.PRECONDITION;
	}

	public boolean isPostcondition() {
		return AxiomReferenceType.POSTCONDITION.equals(this.type);
	}

	public boolean isPrecondition() {
		return AxiomReferenceType.PRECONDITION.equals(this.type);
	}

	public Idents getUsedIdentifiers(){
		return this.identifiers;
	}
	
	public Axiom getAxiom() {
		return this.axiom;
	}
	
	public void addAxiom(Axiom axiom){
		this.axiom = axiom;
	}

	public boolean usesVariables(Declarations secondaryDeclarations) {
		for (Ident id : secondaryDeclarations.getIdentifiers()){
			if (getUsedIdentifiers().contains(id)){
				return true;
			}
		}
		return false;
	}

	public boolean usesVariable(Variable secondaryVar) {
		return getUsedIdentifiers().contains(secondaryVar.getId());
	}

	public ST getTemplate(ParseTreeProperty<ST> templateTree){
		buildAxiom(templateTree);
		return this.axiom.getPredicate();
	}
}
