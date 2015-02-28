package com.github.hatimiti.gamix.app.util;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ImageFactory {

	private static ImageFactory instance;

	private Map<Integer, Image> characterImageMap;
	private Map<Integer, Image> weaponImageMap;
	private Map<Integer, Image> effectImageMap;

	private ImageFactory() {
		loadImage();
	}

	public Image getCharacterImage(int i) {
		return this.characterImageMap.get(i);
	}

	public Image getWeaponImage(int i) {
		return this.weaponImageMap.get(i);
	}

	public Image getEffectImage(int i) {
		return this.effectImageMap.get(i);
	}

	private void loadImage() {
		try {
			this.characterImageMap = new HashMap<Integer, Image>() {{
				put( 33, getCharacterImage("chara033.png"));
				put(101, getCharacterImage("chara101.png"));
				put(111, getCharacterImage("chara111.png"));
			}};
			this.weaponImageMap = new HashMap<Integer, Image>() {{
				put(  1, getWeaponImage("sword001.png"));
			}};
			this.effectImageMap = new HashMap<Integer, Image>() {{
				put(  1, getEffectImage("ef_slash001.png"));
				put(  2, getEffectImage("dead.png"));
			}};
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private Image getCharacterImage(String fileName) throws SlickException {
		return new Image("imageFiles/character/" + fileName);
	}

	private Image getWeaponImage(String fileName) throws SlickException {
		return new Image("imageFiles/weapon/" + fileName);
	}

	private Image getEffectImage(String fileName) throws SlickException {
		return new Image("imageFiles/effect/" + fileName);
	}

	public static ImageFactory getInstance() {
		if (instance == null) {
			instance = new ImageFactory();
		}
		return instance;
	}

}
