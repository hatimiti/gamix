package com.github.hatimiti.gamix.base.type;

import java.util.Collections;
import java.util.List;

public abstract class SyncListType<O> extends ArrayListType<O> {

	@Override
	protected List<O> createNewList() {
		return Collections.synchronizedList(super.createNewList());
	}

	@Override
	protected List<O> createList(List<O> orig) {
		return Collections.synchronizedList(super.createList(orig));
	}

}
