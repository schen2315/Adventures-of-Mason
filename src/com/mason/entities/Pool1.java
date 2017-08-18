package com.mason.entities;

import java.io.IOException;

import org.newdawn.slick.SlickException;

public class Pool1 extends Entity {
	public Pool1(int px, int py) throws SlickException, IOException {
		super("Pool.tmx", 10, 10, px, py);
		this.loadCollisionFile("res/pool.tmx.txt");
	}
}