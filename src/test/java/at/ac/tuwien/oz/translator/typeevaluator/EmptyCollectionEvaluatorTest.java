package at.ac.tuwien.oz.translator.typeevaluator;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import at.ac.tuwien.oz.datatypes.ExpressionType;
import at.ac.tuwien.oz.definitions.ObjectZDefinition;
import at.ac.tuwien.oz.parser.OZLexer;
import at.ac.tuwien.oz.parser.OZParser;

public class EmptyCollectionEvaluatorTest {

	@Mock 
	private ParseTreeProperty<ExpressionType> typeTree;
	@Mock
	private ObjectZDefinition program;
	
	private EmptyCollectionEvaluator eval;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		eval = new EmptyCollectionEvaluator(program, typeTree);
	}
	
	@Test
	public void emptySet_revisited(){
		CharStream input = CharStreams.fromString("{} ++ {1,2,3}");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree parseTree = parser.expression();
		
		ParseTree emptySetCtx = parseTree.getChild(0);
		ParseTree setCtx = parseTree.getChild(2);
		
		Mockito.when(typeTree.get(emptySetCtx)).thenReturn(ExpressionType.getSetConstruction(emptySetCtx, null));
		Mockito.when(typeTree.get(setCtx)).thenReturn(ExpressionType.getSetConstruction(setCtx, ExpressionType.getNat()));

		walker.walk(eval, parseTree);
		
		Mockito.verify(typeTree).put(emptySetCtx, ExpressionType.getSetConstruction(emptySetCtx, ExpressionType.getNat()));
		Mockito.verify(typeTree, Mockito.never()).put(setCtx, ExpressionType.getSetConstruction(setCtx, ExpressionType.getNat()));
	}
	@Test
	public void emptySet_revisitedInComparison(){
		CharStream input = CharStreams.fromString("a = {} ++ {1,2,3}");
		OZParser parser = new OZParser(new CommonTokenStream(new OZLexer(input)));
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTree parseTree = parser.expression();
		
		ParseTree aCtx = parseTree.getChild(0);
		
		ParseTree rightSide = parseTree.getChild(2);
		ParseTree emptySetCtx = rightSide.getChild(0);
		ParseTree setCtx = rightSide.getChild(2);
		
		Mockito.when(typeTree.get(aCtx)).thenReturn(ExpressionType.getVariableType(aCtx, ExpressionType.getSet(ExpressionType.getInt()))); //a
		
		Mockito.when(typeTree.get(emptySetCtx)).thenReturn(ExpressionType.getSetConstruction(emptySetCtx, null));
		Mockito.when(typeTree.get(setCtx)).thenReturn(ExpressionType.getSetConstruction(setCtx, ExpressionType.getInt()));

		walker.walk(eval, parseTree);
		
		Mockito.verify(typeTree).put(emptySetCtx, ExpressionType.getSetConstruction(emptySetCtx, ExpressionType.getInt()));
		Mockito.verify(typeTree, Mockito.never()).put(Mockito.eq(aCtx), Mockito.any(ExpressionType.class));
		Mockito.verify(typeTree, Mockito.never()).put(Mockito.eq(setCtx), Mockito.any(ExpressionType.class));
		Mockito.verify(typeTree, Mockito.never()).put(Mockito.eq(parseTree), Mockito.any(ExpressionType.class));
	}

}
