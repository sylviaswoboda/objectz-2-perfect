package at.ac.tuwien.oz.translator.uat;

import java.io.FileReader;
import java.io.IOException;

public class AcceptanceTestHelper {

	public static String getTextFileContent(String referenceFileName) throws IOException{
		FileReader referenceFile = new FileReader(referenceFileName);
		int readChar = referenceFile.read();
		StringBuffer referenceBuffer = new StringBuffer();
		
		while(readChar != -1){
			referenceBuffer.append((char)readChar);
			readChar = referenceFile.read();
		}
		referenceFile.close();
		return referenceBuffer.toString();
	
	}

//	protected static Map<String, String> doTransformation(String fileName, PerfectStringTemplateGroup templates) throws RecognitionException, IOException{
//	    CharStream input = new ANTLRFileStream(fileName);
//	    OZLexer lexer = new OZLexer(input);
//	    
//	    // use TokenRewriteStream not CommonTokenStream!!
//	    CommonTokenStream tokens = new CommonTokenStream(lexer);
//	    OZParser parser = new OZParser(tokens);
//	    
//	    OZParser.program_return result = parser.program();
//	    CommonTree t = (CommonTree) result.getTree();
//	    CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
//	    nodes.setTokenStream(tokens);
//	
//	    String treeOutput = t.toStringTree();
//	    System.err.println(treeOutput);
//	
//	    OZTree treeParser = OZTree.getInstance(nodes);
//	    treeParser.setTemplateLib(templates);
//	
//	    String transformationOutput = treeParser.program().toString();
//	    
//	    HashMap<String, String> map = new HashMap<String, String>();
//	    map.put(AcceptanceTestHelper.TREE_OUTPUT, treeOutput);
//	    map.put(AcceptanceTestHelper.TRANSFORMATION_OUTPUT, transformationOutput);
//	    return map;
//	}
//
//	static final String TRANSFORMATION_OUTPUT = "transformationOutput";
//	static final String TREE_OUTPUT = "treeOutput";

}
