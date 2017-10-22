package at.ac.tuwien.oz.datatypes.preconditions.interfaces;

import java.util.List;

import org.stringtemplate.v4.ST;

public interface IPreconditions{
	List<ST> getTemplates();
	boolean isEmpty();
	int size();
}
