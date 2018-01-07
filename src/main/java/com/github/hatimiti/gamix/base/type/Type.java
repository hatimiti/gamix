package com.github.hatimiti.gamix.base.type;

/**
 * ドメイン領域における、全ての型の基底クラス．
 *
 * @author hatimiti
 *
 * @param <T> ドメイン型の基となる基本型(Integer, Double, String など)
 */
public interface Type<T> {

	public T getVal();

}
