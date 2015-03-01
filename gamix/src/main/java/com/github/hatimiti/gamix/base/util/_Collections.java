package com.github.hatimiti.gamix.base.util;

import java.util.Collection;
import java.util.Optional;

public class _Collections {

	public static <T> boolean contains(Collection<T> col, T t) {
		if (col == null || t == null) {
			return false;
		}
		Optional<T> any = col.parallelStream().findAny();
		return any.isPresent();
	}
	
}
