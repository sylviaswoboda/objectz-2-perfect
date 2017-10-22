package at.ac.tuwien.oz.ordering;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;

public class PredicateSplitter {

	/** 
	 * Splits a StringTemplate into two predicates, if the current template is a conunction.
	 * 
	 * @param predicate the StringTemplate to be split.
	 * 
	 * @return a list containing all subpredicates in original order.
	 */
	public List<ST> split(ST predicate) {
		List<ST> resultingPredicates = new ArrayList<ST>();
		
		if(isConjunction(predicate)){
			Object leftO = predicate.getAttribute("left");
			Object rightO = predicate.getAttribute("right");
			ST leftST = null;
			ST rightST = null;
			if (leftO instanceof ST && rightO instanceof ST){
				leftST = (ST)leftO;
				rightST = (ST)rightO;
				resultingPredicates.addAll(split(leftST));
				resultingPredicates.addAll(split(rightST));
			} else {
				throw new RuntimeException("Unexpected object type of attributes of a conjunction.");
			}
		} else {
			resultingPredicates.add(predicate);
		}
		return resultingPredicates;
	}

	private boolean isConjunction(ST predicate) {
		if ("/conjunction".equals(predicate.getName())){
			return true;
		} else {
			return false;
		}
	}

}
