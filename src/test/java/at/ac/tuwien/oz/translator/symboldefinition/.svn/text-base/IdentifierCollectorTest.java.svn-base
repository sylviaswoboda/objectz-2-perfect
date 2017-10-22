package at.ac.tuwien.oz.translator.symboldefinition;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import at.ac.tuwien.oz.datatypes.Ident;
import at.ac.tuwien.oz.datatypes.Idents;
import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;

public class IdentifierCollectorTest{

	private IdentifierCollector identifierCollector;
	private ParseTreeWalker walker;
	private ParseTreeProperty<Idents> usedIdentifierTree;
	
	protected OZParser getParser(CharStream stream) {
		OZLexer lexer = new OZLexer(stream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		OZParser parser = new OZParser(tokens);
		return parser;
	}
	
	@Before
	public void setup(){
		this.usedIdentifierTree = new ParseTreeProperty<Idents>();
		this.identifierCollector = new IdentifierCollector();
		this.usedIdentifierTree = this.identifierCollector.getUsedIdentifierTree();
		this.walker = new ParseTreeWalker();
	}

	
	
	@Test
	public void testPredicate() throws RecognitionException, IOException{
		String expressionText = "limit in {1000, 2000, 5000};";
		
		CharStream testFileStream = CharStreams.fromString(expressionText);
		OZParser parser = getParser(testFileStream);
		
		ParseTree tree = parser.predicate();	
		walker.walk(identifierCollector, tree);
		
		Idents usedIdentifiers = usedIdentifierTree.get(tree);
		
		Assert.assertNotNull(usedIdentifiers);
		Assert.assertEquals(1, usedIdentifiers.size());
		Assert.assertEquals(new Ident("limit"), usedIdentifiers.get(0));
	}

	@Test
	public void findIdentifiersInExistsPredicate() {
		String expressionText = "exists x:limit @ x > balance";
		
		CharStream testFileStream = CharStreams.fromString(expressionText);
		OZParser parser = getParser(testFileStream);
		
		ParseTree tree = parser.predicate();	
		walker.walk(identifierCollector, tree);
		
		Idents usedIdentifiers = usedIdentifierTree.get(tree);
		
		Assert.assertNotNull(usedIdentifiers);
		Assert.assertEquals(3, usedIdentifiers.size());
		Assert.assertEquals(new Ident("x"), usedIdentifiers.get(0));
		Assert.assertEquals(new Ident("limit"), usedIdentifiers.get(1));
		Assert.assertEquals(new Ident("balance"), usedIdentifiers.get(2));
	}
	
	@Test
	public void findIdentifiersWithInputSuffix() {
		String expressionText = "balance = value1? + value2?";
		
		CharStream testFileStream = CharStreams.fromString(expressionText);
		OZParser parser = getParser(testFileStream);
		
		ParseTree tree = parser.predicate();	
		walker.walk(identifierCollector, tree);
		
		Idents usedIdentifiers = usedIdentifierTree.get(tree);
		
		Assert.assertNotNull(usedIdentifiers);
		Assert.assertEquals(3, usedIdentifiers.size());
		Assert.assertEquals(new Ident("balance"), usedIdentifiers.get(0));
		Assert.assertEquals(new Ident("value1", "?"), usedIdentifiers.get(1));
		Assert.assertEquals(new Ident("value2", "?"), usedIdentifiers.get(2));
	}

	@Test
	public void findIdentifiersWithOutputSuffix() {
		String expressionText = "balanceVal! = balance";
		
		CharStream testFileStream = CharStreams.fromString(expressionText);
		OZParser parser = getParser(testFileStream);
		
		ParseTree tree = parser.predicate();	
		walker.walk(identifierCollector, tree);
		
		Idents usedIdentifiers = usedIdentifierTree.get(tree);
		
		Assert.assertNotNull(usedIdentifiers);
		Assert.assertEquals(2, usedIdentifiers.size());
		Assert.assertEquals(new Ident("balanceVal", "!"), usedIdentifiers.get(0));
		Assert.assertEquals(new Ident("balance"), usedIdentifiers.get(1));
	}
	@Test
	public void findIdentifiersWithPrime() {
		String expressionText = "balance' = balance - amount?";
		
		CharStream testFileStream = CharStreams.fromString(expressionText);
		OZParser parser = getParser(testFileStream);
		
		ParseTree tree = parser.predicate();	
		walker.walk(identifierCollector, tree);
		
		Idents usedIdentifiers = usedIdentifierTree.get(tree);
		
		Assert.assertNotNull(usedIdentifiers);
		Assert.assertEquals(3, usedIdentifiers.size());
		Assert.assertEquals(new Ident("balance", "'"), usedIdentifiers.get(0));
		Assert.assertEquals(new Ident("balance"), usedIdentifiers.get(1));
		Assert.assertEquals(new Ident("amount", "?"), usedIdentifiers.get(2));
	}
		
}
