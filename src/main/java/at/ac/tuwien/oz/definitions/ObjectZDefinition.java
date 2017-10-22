package at.ac.tuwien.oz.definitions;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.definitions.local.Axiomatic;
import at.ac.tuwien.oz.definitions.local.LocalDefinition;
import at.ac.tuwien.oz.definitions.local.LocalDefinitions;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClasses;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

/**
 * Container representing an object Z program definition 
 * which may contain local definitions (which are in that case globally visible)
 * and classes
 * 
 * @author sylvias
 *
 */
public class ObjectZDefinition extends BaseDefinition implements IScope{

	private DefinitionTable<IDefinition>  symbolTable;
	
	private LocalDefinitions globalDefinitions;
	
	private Axiomatic		 globalAxiomatic;

	private ObjectZClasses   classes;

	private ST template;
	
	public ObjectZDefinition() {
		super("objectZprogram", null);
		this.symbolTable = new DefinitionTable<IDefinition>();
		
		this.globalDefinitions = new LocalDefinitions();
		this.classes = new ObjectZClasses();
		this.globalAxiomatic = new Axiomatic();
	}

	private void define(IDefinition definition){
		this.symbolTable.add(definition.getId(), definition);
	}
	
	@Override
	public IDefinition resolve(Ident id) {
		if (id == null){
			return null;
		}
		return this.symbolTable.get(id);
	}

	/**
	 * @return always null, because ObjectZDefinition never has an enclosing scope.
	 */
	@Override
	public IScope getEnclosingScope() {
		return null;
	}

	public void addGlobalDefinition(LocalDefinition definition){
		this.globalDefinitions.add(definition);
		this.define(definition);
	}
	
	public void addClass(ObjectZClass objectZClass){
		this.classes.add(objectZClass);
		this.define(objectZClass);
	}

	public void addAxiomaticDeclarations(Declarations axiomaticDeclarations) {
		this.globalAxiomatic.addAxiomaticDeclarations(axiomaticDeclarations);
		for (Variable axiomVar: axiomaticDeclarations){
			this.define(axiomVar);
		}
	}

	public void addAxiomaticReferences(AxiomReferences axiomaticReferences) {
		this.globalAxiomatic.addAxiomaticReferences(axiomaticReferences);
	}

	public ObjectZClasses getObjectZClasses() {
		return this.classes;
	}

	public ObjectZClass resolveClass(Ident ident) {
		if (ident == null || symbolTable.get(ident) == null){
			return null;
		}
		
		for(ObjectZClass clazz: this.classes){
			if (ident.equals(clazz.getId())){
				return clazz;
			}
		}
		return null;
	}
	
	public Variable resolveConstant(Ident ident) {
		if (ident == null || symbolTable.get(ident) == null){
			return null;
		}
		return this.globalAxiomatic.resolve(ident);
	}

	public LocalDefinitions getLocalDefinitions() {
		return this.globalDefinitions;
	}

	public void setContext(ParseTree ctx) {
		super.ctx = ctx;
	}
	
	private void translateAxiomatic(ParseTreeProperty<ST> templateTree) {
		this.globalAxiomatic.toString();
	}
	public Axiomatic getGlobalAxiomatic(){
		return globalAxiomatic;
	}

	public void translate(ParseTreeProperty<ST> templateTree){
		translateAxiomatic(templateTree);
		
		this.template = PerfectTemplateProvider.getInstance().getDefinition(this);
		templateTree.put(ctx, this.template);
	}

	@Override
	public ST getTemplate() {
		return this.template;
	}

	public boolean includeStrictNaturals() {
		return true; // TODO implement, set to true, in case the specification uses strict naturals at least one 
	}

}
