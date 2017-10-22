package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import at.ac.tuwien.oz.parser.OZParser.ConjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.DisjunctionContext;
import at.ac.tuwien.oz.parser.OZParser.EquivalenceContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsContext;
import at.ac.tuwien.oz.parser.OZParser.ExistsOneContext;
import at.ac.tuwien.oz.parser.OZParser.ForallContext;
import at.ac.tuwien.oz.parser.OZParser.ImplicationContext;
import at.ac.tuwien.oz.parser.OZParser.SimplePredicateContext;

public class PredicateParseTreeSplitter {
	
	/** 
	 * Splits a StringTemplate into two predicates, if the current template is a conunction.
	 * 
	 * @param predicate the StringTemplate to be split.
	 * 
	 * @return a list containing all subpredicates in original order.
	 */
	public List<ParseTree> split(ParseTree predicateTree) {
		List<ParseTree> resultingPredicateParseTrees = new ArrayList<ParseTree>();
		
		if(isConjunction(predicateTree)){
			ParseTree left = predicateTree.getChild(0);
			ParseTree right = predicateTree.getChild(2);
			resultingPredicateParseTrees.addAll(split(left));
			resultingPredicateParseTrees.addAll(split(right));
		} else if (isStopper(predicateTree)){
			resultingPredicateParseTrees.add(predicateTree);
		} else {
			for (int i = 0; i < predicateTree.getChildCount(); i++){
				ParseTree child = predicateTree.getChild(i);
				if (isSimplePredicate(child)){
					resultingPredicateParseTrees.addAll(split(predicateTree.getChild(i)));
				} else {
					resultingPredicateParseTrees.add(child);
				}
			}
		}
		return resultingPredicateParseTrees;
	}

	private boolean isSimplePredicate(ParseTree predicateTree) {
		return predicateTree instanceof SimplePredicateContext;
	}

	private boolean isConjunction(ParseTree predicateTree) {
		return predicateTree instanceof ConjunctionContext;
	}
	private boolean isStopper(ParseTree predicateTree){
		if (predicateTree instanceof EquivalenceContext || predicateTree instanceof DisjunctionContext 
				|| predicateTree instanceof ImplicationContext || predicateTree instanceof ForallContext
				|| predicateTree instanceof ExistsContext || predicateTree instanceof ExistsOneContext 
				|| predicateTree instanceof TerminalNode){
			return true;
		}
		return false;
	}


}
