package com.github.hatimiti.gamix.app.util;

import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;


public class FontFactory {

	private static FontFactory instance;
	protected UnicodeFont font;

	private FontFactory() {
		loadUnicodeFont();
	}

	public Font getFont() {
		return font;
	}

	@SuppressWarnings("unchecked")
	private void loadUnicodeFont() {
		if (this.font != null) {
			return;
		}
		try {
			this.font = new UnicodeFont("fontFiles/migmix-1p-regular.ttf", 20, false, false);
			this.font.addAsciiGlyphs();
			this.font.addGlyphs(0x3000, 0x30ff); // Hiragana + katakanab + fullwidth punctuations
			this.font.addGlyphs(0x4e00, 0x9fc0); // Kanji
			this.font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			this.font.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static FontFactory createInstance() {
		if (instance == null) {
			instance = new FontFactory();
		}
		return instance;
	}

}
