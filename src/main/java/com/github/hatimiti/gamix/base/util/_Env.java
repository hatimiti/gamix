package com.github.hatimiti.gamix.base.util;

import org.seasar.framework.env.Env;

/**
 * env.txt の状態による制御を行う。
 * ut/it/product
 * @author hatimiti
 */
public final class _Env {

	/*
	 * private コンストラクタ
	 */
	private _Env() { }

	/**
	 * 単体テスト環境で動作しているかどうかをチェックする。
	 * @return 単体テスト環境で動作している場合は true
	 */
	public static boolean isUt() {
		return Env.UT.equals(Env.getValue());
	}

	/**
	 * 結合テスト環境で動作しているかどうかをチェックする。
	 * @return 結合テスト環境で動作している場合は true
	 */
	public static boolean isCt() {
		return Env.CT.equals(Env.getValue());
	}

	/**
	 * 統合テスト環境で動作しているかどうかをチェックする。
	 * @return 統合テスト環境で動作している場合は true
	 */
	public static boolean isIt() {
		return Env.IT.equals(Env.getValue());
	}

	/**
	 * 本番環境で動作しているかどうかをチェックする。
	 * @return 本番環境で動作している場合は true
	 */
	public static boolean isProduct() {
		return Env.PRODUCT.equals(Env.getValue());
	}

	/**
	 * 現在の env.txt の値を取得する。
	 */
	public static String getValue() {
		return Env.getValue();
	}

}
