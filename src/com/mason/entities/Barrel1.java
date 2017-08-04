package com.mason.entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Barrel1 extends Entity {
	public Barrel1(int px, int py) throws SlickException {
		super("barrel.tmx", 2, 2, px, py);
		
		collisionBoxes.add(new Rectangle(24, 24, 8, 8));
		collisionBoxes.add(new Rectangle(32, 24, 16, 8));
		collisionBoxes.add(new Rectangle(32, 32, 10, 30));
		collisionBoxes.add(new Rectangle(0, 32, 32, 10));
		collisionBoxes.add(new Rectangle(16, 32 + 10, 16, 16));
		setRenderBox(new Rectangle(0, 32, 32, 10));
	}
}
