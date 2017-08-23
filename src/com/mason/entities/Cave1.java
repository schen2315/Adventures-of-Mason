package com.mason.entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

public class Cave1 extends Entity {
	public Cave1(int px, int py) throws SlickException {
		super("cave.tmx", 8, 5, px, py);
		collisionBoxes.add(new Line(20, 0, 20, 40));
		collisionBoxes.add(new Line(20, 40, 20, 100));
		collisionBoxes.add(new Line(20, 140, 170, 140));
		collisionBoxes.add(new Line(20, 140, 90, 75));
		collisionBoxes.add(new Line(90, 75, 105, 75));
		collisionBoxes.add(new Line(105, 75, 170, 140));
		
		collisionBoxes.add(new Line(20, 40, 85, 40));
		collisionBoxes.add(new Line(175, 100, 110, 40));
		collisionBoxes.add(new Line(175, 100, 175, 75));
		collisionBoxes.add(new Line(175, 100, 175, 75));
		collisionBoxes.add(new Line(230, 75, 175, 75));
		
		collisionBoxes.add(new Line(250, 75, 260, 75));
		
		collisionBoxes.add(new Line(170, 40, 110, 40));
		collisionBoxes.add(new Line(170, 40, 185, 15));
		collisionBoxes.add(new Line(260, 10, 185, 15));
		collisionBoxes.add(new Line(260, 10, 260, 0));
		collisionBoxes.add(new Line(260, 0, 20, 0));
		collisionBoxes.add(new Line(260, 10, 260, 70));
		
		collisionBoxes.add(new Line(20, 100, 85, 40));
		setRenderBox(new Rectangle(0, 0, 260, 1));
	}
}