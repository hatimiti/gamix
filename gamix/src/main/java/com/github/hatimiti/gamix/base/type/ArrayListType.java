package com.github.hatimiti.gamix.base.type;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayListType<O> extends ListType<O> {

	@Override
	List<O> createNewList() {
		return new ArrayList<O>();
	}

	@Override
	List<O> createList(List<O> orig) {
		return new ArrayList<O>(orig);
	}

}
