package com.mason.entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class Tower1 extends Entity {
	public Tower1(int px, int py) throws SlickException {
		super("tower.tmx", 3, 11, px, py);
		collisionBoxes.add(new Circle(46, 310, 45));
		setRenderBox(new Rectangle(0, 267, 85, 85));
	}
}
