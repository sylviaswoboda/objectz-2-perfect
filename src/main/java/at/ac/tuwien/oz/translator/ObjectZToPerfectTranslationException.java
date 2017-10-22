package at.ac.tuwien.oz.translator;

public class ObjectZToPerfectTranslationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7405697132361775250L;

	public ObjectZToPerfectTranslationException(String errorMsg){
		super(errorMsg);
	}
	
	public ObjectZToPerfectTranslationException(String errorMsg, Throwable exception){
		super(errorMsg, exception);
	}
	
}
