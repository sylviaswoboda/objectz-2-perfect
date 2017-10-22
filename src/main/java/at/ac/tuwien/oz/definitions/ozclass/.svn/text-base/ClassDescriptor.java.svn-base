package at.ac.tuwien.oz.definitions.ozclass;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.parser.OZParser.GenActualsContext;
import at.ac.tuwien.oz.parser.OZParser.RenamingContext;
import at.ac.tuwien.oz.translator.ObjectZToPerfectTranslationException;
import at.ac.tuwien.oz.translator.templates.PerfectTemplateProvider;

public class ClassDescriptor {

	private ST template;
	
	private Ident className;
	private GenActualsContext genActualsCtx;
	private RenamingContext renamingCtx;
	
	public ClassDescriptor(Ident className, GenActualsContext genActualsCtx, RenamingContext renamingCtx) {
		this.className = className;
		this.genActualsCtx = genActualsCtx;
		this.renamingCtx = renamingCtx;
		
		if (this.renamingCtx != null){
			throw new ObjectZToPerfectTranslationException("renaming in class descriptors is not yet supported");
		}
	}
	
	public Ident getId(){
		return this.className;
	}

	public void translate(ParseTreeProperty<ST> templateTree) {
		ST genActuals = templateTree.get(genActualsCtx);
		ST name = PerfectTemplateProvider.getInstance().getId(className);
		this.template = PerfectTemplateProvider.getInstance().getGenericClassReference(name, genActuals);
	}
	
	public ST getTemplate(){
		return this.template;
	}
}
