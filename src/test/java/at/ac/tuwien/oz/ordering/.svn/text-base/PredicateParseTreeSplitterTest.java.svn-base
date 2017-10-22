package at.ac.tuwien.oz.ordering;

import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;
import at.ac.tuwien.oz.parser.OZParser.PredicateContext;

public class PredicateParseTreeSplitterTest{
	
	private PredicateParseTreeSplitter predicateSplitter;
	

	@BeforeClass
	public static void setupClass(){
	}
	
	@Before
	public void setup(){
		predicateSplitter = new PredicateParseTreeSplitter();
	}
	private OZParser getParser(String input) {
		CharStream stream = CharStreams.fromString(input);
		OZLexer lexer = new OZLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		OZParser parser = new OZParser(tokens);
		return parser;
	}


	@Test
	public void nothingToSplitSimple(){
		String input = "a <=> b";
		OZParser parser = getParser(input);
		PredicateContext predicateParseTree = parser.predicate();
		
		predicateSplitter.split(predicateParseTree);
		
		List<ParseTree> predicateList = predicateSplitter.split(predicateParseTree);
		
		Assert.assertEquals(1, predicateList.size());
	}
	
	@Test
	public void singleConjunction(){
		String input = "a < 0 and b = num1";
		
		OZParser parser = getParser(input);
		PredicateContext predicateParseTree = parser.predicate();
		
		List<ParseTree> predicateList = predicateSplitter.split(predicateParseTree);
		
		Assert.assertEquals(2, predicateList.size());
		
		Assert.assertEquals("a<0", predicateList.get(0).getText());
		Assert.assertEquals("b=num1", predicateList.get(1).getText());
	}
	
	@Test
	public void doubleConjunction(){
		String input = "a > 0 and b = 200 and c <= 15";
		
		OZParser parser = getParser(input);
		PredicateContext predicateParseTree = parser.predicate();
		
		List<ParseTree> predicateList = predicateSplitter.split(predicateParseTree);
		
		Assert.assertEquals(3, predicateList.size());
		Assert.assertEquals("a>0", predicateList.get(0).getText());
		Assert.assertEquals("b=200", predicateList.get(1).getText());
		Assert.assertEquals("c<=15", predicateList.get(2).getText());
	}
	
	@Test
	public void innerConjunction(){
		String input = "a and b <=> c";
		
		OZParser parser = getParser(input);
		PredicateContext predicateParseTree = parser.predicate();
		
		List<ParseTree> predicateList = predicateSplitter.split(predicateParseTree);
		
		Assert.assertEquals(1, predicateList.size());
		Assert.assertEquals("aandb<=>c", predicateList.get(0).getText());

	}

}
