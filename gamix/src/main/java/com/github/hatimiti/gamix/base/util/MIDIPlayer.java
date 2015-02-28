package com.github.hatimiti.gamix.base.util;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MIDIPlayer {

	/** シングルトンオブジェクト */
	private static MIDIPlayer instance;
	/** 曲情報を記憶するシーケンス */
	private Sequence sequence;
	/** シーケンス(曲)情報を元に、演奏するシーケンサ */
	private Sequencer sequencer;

	public MIDIPlayer() {
		/* 初めてプレイヤーが作成されたときは何も流れていない */
		try {
			/* シーケンサの初期化 */
			sequencer = MidiSystem.getSequencer();
			/* 再生ループ回数の設定(無限ループ) */
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			/* シーケンサを開く */
			sequencer.open();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定された曲番号の音楽を再生する.
	 * @param musicNumber 再生したい曲の曲番号.
	 */
	public void play(File file) {

		stop();

		try {
			/* 曲ファイルからシーケンス情報を取得 */
			sequence = MidiSystem.getSequence(file);
			/* シーケンサにシーケンス(曲)をセットする */
			sequencer.setSequence(sequence);
			/* シーケンサを再生する */
			sequencer.start();

		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 現在再生している曲を停止する
	 */
	public void stop() {
		if (sequencer.isRunning()) {
			sequencer.stop();
		}
	}

	public static MIDIPlayer createInstance() {
		if (instance == null) {
			instance = new MIDIPlayer();
		}
		return instance;
	}

}
