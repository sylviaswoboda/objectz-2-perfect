package at.ac.tuwien.oz.datatypes;



public class Ident implements Comparable<Ident> {

	private static final String OZ_OUT_VAR_SUFFIX = "!";
	private static final String OZ_IN_VAR_SUFFIX = "?";
	private static final String PERFECT_IN_VAR_SUFFIX 	= "_in";
	private static final String PERFECT_OUT_VAR_SUFFIX 	= "_out";
	private static final String PERFECT_TEMP_VAR_SUFFIX = "_temp";
	private static final String PRIME 			= "'";

	/** name of the identifier, never null */
	private String name;
	
	/** decoration, one of _in, _out or ', never null.*/
	private String decoration;
	
	private Ident(){
	}

	public Ident(String name){
		this();
		this.name = name;
		this.decoration = "";
	}
	
	public Ident(String name, String decoration){
		this(name);
		if (OZ_IN_VAR_SUFFIX.equals(decoration)){
			this.decoration = PERFECT_IN_VAR_SUFFIX;
		} else if (OZ_OUT_VAR_SUFFIX.equals(decoration)){
			this.decoration = PERFECT_OUT_VAR_SUFFIX;
		} else if (isDecorationPrime(decoration) || isDecorationInPerfectStyle(decoration) ){
			this.decoration = decoration;
		}
	}

	private boolean isDecorationInPerfectStyle(String decoration) {
		return PERFECT_IN_VAR_SUFFIX.equals(decoration) || PERFECT_OUT_VAR_SUFFIX.equals(decoration) || PERFECT_TEMP_VAR_SUFFIX.equals(decoration);
	}

	private boolean isDecorationPrime(String decoration) {
		return PRIME.equals(decoration);
	}
	
	public String getName(){
		return this.name;
	}
	public String getDecoration(){
		return this.decoration;
	}
	
	/**
	 * @return 'true' for identifiers originally suffixed with '?', i.e. input variable 
	 * 			identifiers, 'false' otherwise
	 */
	public boolean isInputIdent(){
		if (PERFECT_IN_VAR_SUFFIX.equals(this.decoration)){
			return true;
		}
		return false;
	}
	
	/**
	 * @return 'true' for identifiers originally suffixed with '!', i.e. output variable 
	 * 			identifiers, 'false' otherwise
	 */
	public boolean isOutputIdent(){
		if(PERFECT_OUT_VAR_SUFFIX.equals(this.decoration)){
			return true;
		}
		return false;
	}
	/**
	 * @return 'true' for identifiers originally suffixed with a prime ', i.e. primed 
	 * 			variable identifiers, 'false' otherwise
	 */
	public boolean isPrimedIdent(){
		if(PRIME.equals(this.decoration)){
			return true;
		} 
		return false;
	}

	public Ident getPrimedCopy() {
		return new Ident(this.name + this.decoration, PRIME);
	}
	public Ident getUndecoratedCopy() {
		return new Ident(this.name);
	}
	public Ident getInputCopy() {
		return new Ident(this.name, PERFECT_IN_VAR_SUFFIX);
	}

	public Ident getOutputCopy() {
		return new Ident(this.name, PERFECT_OUT_VAR_SUFFIX);
	}

	public Ident getTempCopy() {
		return new Ident(this.name, PERFECT_TEMP_VAR_SUFFIX);
	}

	public int compareTo(Ident i){
		int comp = this.name.compareTo(i.name);
		if (comp != 0)
			return comp;
		else
			// names are the same
			return this.decoration.compareTo(i.decoration);
	}
	


	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder();
		if (name != null){
			sb.append(name);
		}
		if (decoration != null){
			sb.append(decoration);
		}
		return sb.toString().hashCode();
		
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((decoration == null) ? 0 : decoration.hashCode());
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ident other = (Ident) obj;
		if (decoration == null) {
			if (other.decoration != null)
				return false;
		} else if (!decoration.equals(other.decoration))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ident [name=" + name + ", decoration=" + decoration + "]";
	}

	public boolean hasSameBaseName(Ident id) {
		return this.name.equals(id.name);
	}

	
	
	
//	
//	/**
//	 * Two Ident objects are considered to have the same basename,
//	 * if their name values are the same
//	 *
//	 * @param i Ident object to which the current object shall be compared
//	 */
//	public boolean hasSameBaseName(Ident i){
//		if (i == null)
//			return false;
//		
//		return this.name.equals(i.name);
//	}
//	

	
//	public Ident getUnprimedIdent(){
//		Ident unprimed = new Ident(this.name);
//		return unprimed;
//	}
//	public Ident getPrimeRemovedIdent(){
//		if (PRIME.equals(this.decoration)){
//			return new Ident(this.name);
//		}
//		return this;
//	}
	
//	public boolean isUsingPrefix(){
//		return this.withPrefix;
//	}
//	public boolean isUsingPrime(){
//		return this.withPrime;
//	}
//	public void prefixResult() {
//		if (this.isOutputIdent()){
//			this.withPrefix = true;
//		}
//	}
//	public void prime() {
//		if (this.isOutputIdent()){
//			this.withPrime = true;
//		}
//	}
//	@Override
//	public String toString() {
//		return "Ident [name=" + name + ", decoration=" + decoration + "]";
//	}
//	
//	public Ident getCopy(){
//		Ident returnIdent;
//		returnIdent = new Ident(this.name, this.originalDecoration, this.templateLib);
//		return returnIdent;
//	}
}