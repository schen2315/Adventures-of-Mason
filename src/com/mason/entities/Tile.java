package com.mason.entities;

import org.newdawn.slick.Image;


public class Tile {
	private Image tile;
	static final int height = 32;
	static final int width = 32;
	Tile(Image t) {
		tile = t;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Image getImage() {
		return tile;
	}
	public void draw(float offX, float offY, float scale) {
		tile.draw(offX, offY, scale);
	}
}
