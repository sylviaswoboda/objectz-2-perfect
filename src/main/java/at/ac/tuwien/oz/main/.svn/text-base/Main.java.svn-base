package at.ac.tuwien.oz.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		
		String inputFile = null;
		if (args.length > 0){
			inputFile = args[0];
		}
		InputStream is = System.in;
		
		if (inputFile != null){
			
			is = new FileInputStream(inputFile);
		}
		
		CharStream input = CharStreams.fromStream(is);
		OZLexer lexer = new OZLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		OZParser parser = new OZParser(tokens);
		ParseTree tree = parser.program();
		
		OZTranslator translator = new OZTranslator(tree);
		translator.translate();
		
		
		String output = translator.getRootTemplate().render();
		
		String outputFile = null;
		if (args.length > 1){
			outputFile = args[1];
		}
		if (outputFile != null){
			FileOutputStream fos = new FileOutputStream(outputFile);
			fos.write(output.getBytes());
			fos.flush();
			fos.close();
		} else {
			System.out.println(output);
		}
	}
	
}