package com.mason.entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

public class Cave1 extends Entity {
	public Cave1(int px, int py) throws SlickException {
		super("cave.tmx", 8, 5, px, py);
		//collisionBoxes.add(new Line(600, 0, 200, 600));
		collisionBoxes.add(new Line(20, 0, 20, 40));
		System.out.println(collisionBoxes.get(0).getX());
		System.out.println(collisionBoxes.get(0).getY());
	}
}