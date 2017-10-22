package at.ac.tuwien.oz.translator;


public class TempVarProvider {

    private static final String VAR_NAME_BASE = "tempVar";
    private static int varNameCount = 0;

    public static void resetNameCounter(){
    	varNameCount = 0;
    }
    
    public static String getTempVarName(){
    	varNameCount++;
    	return VAR_NAME_BASE + varNameCount;
    }
    
}
