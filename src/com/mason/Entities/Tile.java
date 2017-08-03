package com.mason.Entities;

import org.newdawn.slick.Image;


public class Tile {
	private Image tile;
	private int width;
	private int height;
	Tile(Image t, int w, int h) {
		tile = t;
		width = w;
		height = h;
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
	public void draw(float offX, float offY) {
		tile.draw(offX, offY);
	}
}
