package com.github.hatimiti.gamix.base.util;

import java.util.Collection;
import java.util.Optional;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _Util {

	/**
	 * Get the logging object.
	 * @return Get the logging object of current stack trace.
	 */
	public static Logger getLogger() {
		return LoggerFactory.getLogger(new Throwable().getStackTrace()[1].getClassName());
	}
	
	public static <T> boolean contains(Collection<T> col, T t) {
		if (col == null || t == null) {
			return false;
		}
		Optional<T> any = col.parallelStream().findAny();
		return any.isPresent();
	}

	public static boolean isNullOrEmpty(Object value) {
		
		if (value == null) {
			return true;
		}
		if (value instanceof Collection) {
			return ((Collection<?>) value).isEmpty();
		}
		return value.toString().isEmpty();
	}
	
	/**
	 * seasar 管理下のオブジェクトを取得する．
	 * @return 指定したオブジェクト
	 */
	@SuppressWarnings("unchecked")
	public static <D> D getComponent(Class<D> clazz) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		return (D) container.getComponent(clazz);
	}

}
