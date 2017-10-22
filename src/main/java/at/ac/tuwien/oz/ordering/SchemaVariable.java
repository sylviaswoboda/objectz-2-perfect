package at.ac.tuwien.oz.ordering;

import org.stringtemplate.v4.ST;

import at.ac.tuwien.oz.parser.OZParser.ExpressionContext;
import at.ac.tuwien.oz.parser.OZParser.IdContext;

public class SchemaVariable {

	private IdContext idCtx;
	private ExpressionContext typeCtx;
	private ST id;
	private ST type;
	
	public SchemaVariable(IdContext idCtx, ExpressionContext typeCtx, ST id, ST type) {
		super();
		this.idCtx = idCtx;
		this.typeCtx = typeCtx;
		this.id = id;
		this.type = type;
	}

	public ST getId() {
		return id;
	}

	public void setId(ST id) {
		this.id = id;
	}

	public ST getType() {
		return type;
	}

	public void setType(ST type) {
		this.type = type;
	}

	public ExpressionContext getTypeCtx() {
		return typeCtx;
		
	}

	@Override
	public String toString() {
		return "SchemaVariable [id=" + id.render() + ", type=" + type.render() + "]";
	}
	
	

}
