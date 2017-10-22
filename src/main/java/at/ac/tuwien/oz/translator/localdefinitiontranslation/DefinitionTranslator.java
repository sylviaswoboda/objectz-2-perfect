package at.ac.tuwien.oz.translator.localdefinitiontranslation;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.definitions.local.LocalDefinition;
import at.ac.tuwien.oz.definitions.ozclass.ObjectZClass;

public class DefinitionTranslator {
	
	private ObjectZDefinition program;
	private ParseTreeProperty<ST> templateTree;
	
	public DefinitionTranslator(ObjectZDefinition program, ParseTreeProperty<ST> templateTree){
		this.program = program;
		this.templateTree = templateTree;
	}
	
	public void translate(){
		// translate global definitions
		for (LocalDefinition globalDef: program.getLocalDefinitions()){
			globalDef.translate(templateTree);
		}
		for (ObjectZClass clazz: program.getObjectZClasses()){
			clazz.translate(templateTree);
		}
		program.translate(templateTree);
		
	}

}
