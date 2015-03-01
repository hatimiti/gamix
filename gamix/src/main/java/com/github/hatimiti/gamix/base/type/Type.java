package com.github.hatimiti.gamix.base.type;

/**
 * ドメイン領域における、全ての型の基底クラス．
 *
 * @author hatimiti
 *
 * @param <T> ドメイン型の基となる基本型(Integer, Double, String など)
 */
public abstract class Type<T> {

	public abstract T getVal();

	@Override
	public String toString() {
		return getVal().toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		if (!isIncetanceOf(obj)) {
			return false;
		}
		return this.getVal().equals(((Type<T>) obj).getVal());
	}

	@Override
	public int hashCode() {
		return this.getVal().hashCode();
	}

	/**
	 * instanceofの代替実装．
	 * ジェネリクスでの instanceof が不可のため．
	 * @param obj
	 * @return
	 */
	private boolean isIncetanceOf(final Object obj) {
		if (obj == null) {
			return false;
		}
		try {
			return this.getClass().equals(obj.getClass());
		} catch (Throwable t) {
			return false;
		}
	}

}
