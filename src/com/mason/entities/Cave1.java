package com.mason.entities;

import org.newdawn.slick.SlickException;

public class Cave1 extends Entity {
	public Cave1(int px, int py) throws SlickException {
		super("cave.tmx", 8, 5, px, py);
	}
}