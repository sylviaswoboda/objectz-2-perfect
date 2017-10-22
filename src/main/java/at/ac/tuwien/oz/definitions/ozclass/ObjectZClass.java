package at.ac.tuwien.oz.definitions.ozclass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Declarations;
import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.datatypes.Variable;
import at.ac.tuwien.oz.datatypes.axioms.AxiomReferences;
import at.ac.tuwien.oz.definitions.BaseDefinition;
import at.ac.tuwien.oz.definitions.DefinitionTable;
import at.ac.tuwien.oz.definitions.IDefinition;
import at.ac.tuwien.oz.definitions.IScope;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.local.Axiomatic;
import at.ac.tuwien.oz.definitions.local.LocalDefinition;
import at.ac.tuwien.oz.definitions.local.LocalDefinitions;
import at.ac.tuwien.oz.definitions.operation.Operation;
import at.ac.tuwien.oz.definitions.operation.OperationExpression;
import at.ac.tuwien.oz.definitions.operation.interfaces.IComposableOperation;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;


public class ObjectZClass extends BaseDefinition implements IScope{

	private ObjectZDefinition enclosingScope;
	
	private DefinitionTable<IDefinition> classMembers;
	
	private List<Ident> formalParameters;
	private List<Ident> visibleFeatures;
	private List<ClassDescriptor> inheritedClasses;
	
	private LocalDefinitions localDefinitions;
	
	private Axiomatic localAxiomatic;
	
	private InitialState     initialState;
	private StateSchema		 stateSchema;
	
	private Operations operations;
	
	private boolean allFeaturesVisible;
	
	private ST classTemplate;

	public ObjectZClass(String name, ObjectZDefinition globalScope, ParseTree ctx){
		super(name, ctx);
		this.enclosingScope = globalScope;
		this.classMembers = new DefinitionTable<IDefinition>();
		this.formalParameters = new ArrayList<Ident>();
		this.visibleFeatures = new ArrayList<Ident>();
		this.inheritedClasses = new ArrayList<ClassDescriptor>();
		this.localDefinitions = new LocalDefinitions();
		this.operations = new Operations();
		this.localAxiomatic = new Axiomatic();
	}
	
	@Override
	public IScope getEnclosingScope(){
		return this.enclosingScope;
	}

	private void define(IDefinition definition) {
		this.classMembers.add(definition.getId(), definition);
	}
	@Override
	public IDefinition resolve(Ident id) {
		if (id == null){
			return null;
		}
		return this.classMembers.get(id);
	}

	public void addFormalParameter(Ident i){
		this.formalParameters.add(i);
	}
	
	public void addVisibleFeature(Ident i){
		this.visibleFeatures.add(i);
	}
	
	public void addInheritedClass(ClassDescriptor descriptor){
		this.inheritedClasses.add(descriptor);
	}
	
	public void addLocalDefinition(LocalDefinition localDefinition){
		this.define(localDefinition);
		this.localDefinitions.add(localDefinition);
	}
	
	public void addStateSchema(StateSchema stateSchema) {
		this.stateSchema = stateSchema;
		for (Variable primary: stateSchema.getPrimaryDeclarations()){
			this.define(primary);
		}
		for (Variable secondary: stateSchema.getSecondaryDeclarations()){
			this.define(secondary);
		}
	}
	
	public void addInitialState(InitialState init){
		this.initialState = init;
	}
	
	public void addOperation(Operation op){
		this.define(op);
		this.operations.add(op);
	}
	
	public Operation resolveOperation(Ident id) {
		Operation operation = operations.resolve(id);
		return operation;
	}

	public Variable resolveVariable(Ident id){
		return this.stateSchema.resolve(id);
	}
	
	public Variable resolveConstant(Ident id){
		return this.localAxiomatic.resolve(id);
	}

	public boolean isStateVariable(Ident usedId) {
		if (usedId == null || this.classMembers.get(usedId) == null){
			return false;
		}
		for(Variable primary: stateSchema.getPrimaryDeclarations()){
			if (usedId.equals(primary.getId())){
				return true;
			}
		}
		for (Variable secondary: stateSchema.getSecondaryDeclarations()){
			if (usedId.equals(secondary.getId())){
				return true;
			}
		}
		// TODO look into super classes
		return false;
	}

	public void setAllFeaturesVisible() {
		this.allFeaturesVisible = true;
	}

	public List<Ident> getVisibleFeatures() {
		if (allFeaturesVisible && visibleFeatures.isEmpty()){
			calculateVisibleFeatures();
		}
		return visibleFeatures;
	}
	
	public boolean isVisible(Ident id){
		return this.getVisibleFeatures().contains(id);
	}
	
	private void calculateVisibleFeatures() {
		if (initialState != null){
			visibleFeatures.add(new Ident("INIT"));
		}
		// add all class members;
		for (Ident i: this.classMembers.identifiers()){
			visibleFeatures.add(i);
		}
	}

	public Declarations getPrimaryDeclarations() {
		if (this.stateSchema == null){
			return Declarations.empty();
		}
		return this.stateSchema.getPrimaryDeclarations();
	}
	public Declarations getSecondaryDeclarations() {
		if (this.stateSchema == null){
			return Declarations.empty();
		}
		return this.stateSchema.getSecondaryDeclarations();
	}
	public Declarations getAxiomaticDeclarations() {
		return this.localAxiomatic.getDeclarations();
	}

	public void addAxiomaticDeclarations(Declarations axiomaticDeclarations) {
		this.localAxiomatic.addAxiomaticDeclarations(axiomaticDeclarations);
		for (Variable axiomVar: axiomaticDeclarations){
			this.define(axiomVar);
		}
	}

	public AxiomReferences getAxiomaticReferences() {
		return this.localAxiomatic.getAxiomReferences();
	}

	public AxiomReferences getAxiomaticReferences(Variable axiomVar) {
		return this.localAxiomatic.getAxiomReferences(axiomVar);
	}

	public void addAxiomaticReferences(AxiomReferences axiomaticReferences) {
		this.localAxiomatic.addAxiomaticReferences(axiomaticReferences);
		
	}

	public AxiomReferences getInitialAxiomReferences() {
		if (initialState == null){
			return new AxiomReferences();
		}
		return this.initialState.getAxiomReferences();
	}
	public AxiomReferences getStateAxiomReferences() {
		return this.stateSchema.getPrimaryAxiomReferences();
	}
	
	public AxiomReferences getStateAxiomReferences(Variable stateVar) {
		AxiomReferences primaryReferences = this.stateSchema.getPrimaryAxiomReferences(stateVar);
		if (primaryReferences.isEmpty()){
			return this.stateSchema.getSecondaryAxiomReferences(stateVar);
		} else {
			return primaryReferences;
		}
		
	}

	public Operations getOperations() {
		return this.operations;
	}

	public Operations getSimpleOperations() {
		Operations simpleOperations = new Operations();
		
		Collection<Operation> simpleOperationCollection = 
				this.operations.asList().stream()
					.filter(op -> op.isSimpleOperation())
					.collect(Collectors.toList());
		
		simpleOperations.addAll(simpleOperationCollection);
		return simpleOperations;
	}

	public List<IComposableOperation> getOperationExpressions() {
		List<IComposableOperation> operationExpressions = new ArrayList<IComposableOperation>();
		
		Collection<IComposableOperation> operationExpressionCollection = 
				this.operations.asList().stream()
					.filter(op -> op.isOperationExpression())
					.map(op -> (OperationExpression)op)
					.collect(Collectors.toList());
		
		operationExpressions.addAll(operationExpressionCollection);
		return operationExpressions;
	}
	

	public boolean isSubTypeOf(Ident id) {
		Idents inheritedClassIds = new Idents();
		for (ClassDescriptor des: this.inheritedClasses){
			inheritedClassIds.add(des.getId());
			if (des.getId().equals(id)){
				return true;
			}
		}
		for (Ident inheritedClassId: inheritedClassIds){
			ObjectZClass superClass = enclosingScope.resolveClass(inheritedClassId);
			if (superClass.isSubTypeOf(id)){
				return true;
			}
		}
		return false;
	}

	@Override
	public ExpressionType getExpressionType() {
		// TODO think about it if we should include formal parameters here or even generic ones?? -> leave it for now
		return ExpressionType.getUserDefinedType(ctx, this.name);
	}
	
	public ObjectZDefinition getDefinition(){
		return this.enclosingScope;
	}

	public ST getTemplate(){
		return this.classTemplate;
	}

	public void translate(ParseTreeProperty<ST> templateTree) {
		for (LocalDefinition localDef: this.localDefinitions){
			localDef.translate(templateTree);
		}
		this.localAxiomatic.translate(templateTree);
		if (this.initialState != null){
			this.initialState.translate(templateTree);
		}
		if (this.stateSchema != null){
			this.stateSchema.translate(templateTree);
		}
		this.inheritedClasses.stream().forEach(c -> c.translate(templateTree));
		
		this.classTemplate = PerfectTemplateProvider.getInstance().getClassDef(this);
	}

	public List<Ident> getFormalParameters() {
		return this.formalParameters;
	}
	public List<ClassDescriptor> getInheritedClasses(){
		return this.inheritedClasses;
	}
	public Map<ClassDescriptor, Declarations> getInheritedFields(){
		
		Map<ClassDescriptor, Declarations> inheritedFields = new HashMap<>();

		if (inheritedClasses == null || inheritedClasses.isEmpty()){
			return inheritedFields;
		}
		
		for (ClassDescriptor inheritedClass: inheritedClasses){
			ObjectZClass superClass = this.enclosingScope.resolveClass(inheritedClass.getId());
			Declarations inheritedMembers = new Declarations();
			inheritedMembers.addAll(superClass.getPrimaryDeclarations());
			inheritedMembers.addAll(superClass.getAxiomaticDeclarations());
			inheritedFields.put(inheritedClass, inheritedMembers);
		}
		return inheritedFields;
	}
	public LocalDefinitions getLocalDefinitions() {
		return this.localDefinitions;
	}

	public AxiomReferences getInvariants() {
		return this.stateSchema.getInvariants();
	}

}
