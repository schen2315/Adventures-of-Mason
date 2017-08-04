package com.mason.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.mason.main.Main;

public class Player extends Entity {
	
	private Animation player, movingUp, movingDown, movingLeft, movingRight;
	private Animation lastState, stillUp, stillDown, stillLeft, stillRight;
	private int duration[] = {100, 100, 100, 100};
	public static final int[] center = {(Main.resolution[0]/2)-24, (Main.resolution[1]/2)-24};
	//public static final int[] center = {0,0};
	public Animation getPlayer() {
		return player;
	}
	public void setPlayer() {
		player = lastState;
	}
	private float posX, posY;
	
	//We load in a sprite sheet w/ each entire animation in a single column
	public Player(String pathToImage, int px, int py) throws SlickException {
		super(px, py);
		posX =center[0]; posY =center[1];
		Image buckyUp0, buckyUp1, buckyUp2, buckyUp3;
		Image buckyDown0, buckyDown1, buckyDown2, buckyDown3;
		Image buckyLeft0, buckyLeft1, buckyLeft2, buckyLeft3;
		Image buckyRight0, buckyRight1, buckyRight2, buckyRight3;
		
		buckyDown0 = new Image("res/" + pathToImage).getSubImage(0, 0, 48, 48);
		buckyDown1 = new Image("res/" + pathToImage).getSubImage(0, 48, 48, 48);
		buckyDown2 = new Image("res/" + pathToImage).getSubImage(0, 96, 48, 48);
		buckyDown3 = new Image("res/" + pathToImage).getSubImage(0, 144, 48, 48);
		Image[] walkDown = {buckyDown0, buckyDown1, buckyDown2, buckyDown3};
		
		buckyLeft0 = new Image("res/" + pathToImage).getSubImage(48, 0, 48, 48);
		buckyLeft1 = new Image("res/" + pathToImage).getSubImage(48, 48, 48, 48);
		buckyLeft2 = new Image("res/" + pathToImage).getSubImage(48, 96, 48, 48);
		buckyLeft3 = new Image("res/" + pathToImage).getSubImage(48, 144, 48, 48);
		Image[] walkLeft = {buckyLeft0, buckyLeft1, buckyLeft2, buckyLeft3};
		
		buckyUp0 = new Image("res/" + pathToImage).getSubImage(96, 0, 48, 48);
		buckyUp1 = new Image("res/" + pathToImage).getSubImage(96, 48, 48, 48);
		buckyUp2 = new Image("res/" + pathToImage).getSubImage(96, 96, 48, 48);
		buckyUp3 = new Image("res/" + pathToImage).getSubImage(96, 144, 48, 48);
		Image[] walkUp = {buckyUp0, buckyUp1, buckyUp2, buckyUp3};
		
		buckyRight0 = new Image("res/" + pathToImage).getSubImage(144, 0, 48, 48);
		buckyRight1 = new Image("res/" + pathToImage).getSubImage(144, 48, 48, 48);
		buckyRight2 = new Image("res/" + pathToImage).getSubImage(144, 96, 48, 48);
		buckyRight3 = new Image("res/" + pathToImage).getSubImage(144, 144, 48, 48);
		Image[] walkRight = {buckyRight0, buckyRight1, buckyRight2, buckyRight3};
		
		movingDown = new Animation(walkDown, duration, true);
		movingLeft = new Animation(walkLeft, duration, true);
		movingUp = new Animation(walkUp, duration, true);
		movingRight = new Animation(walkRight, duration, true);
		
		stillDown = new Animation(walkDown, duration, false);
		stillLeft = new Animation(walkLeft, duration, false);
		stillUp = new Animation(walkUp, duration, false);
		stillRight = new Animation(walkRight, duration, false);
		
		player = stillDown;
		lastState = stillDown;
	}
	public Animation getLastState() {
		return lastState;
	}
	public void setLastState(Animation lastState) {
		this.lastState = lastState;
	}
	@Override
	public void draw(float offX, float offY) {
		//ignore the offset
		player.draw(center[0], center[1]);
	}
	public float getPosX() {
		return posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPos(float x, float y) {
		posX = x; posY = y;
	}
	public void setMovingUp() {
		player = movingUp;
	}
	public void setMovingDown() {
		player = movingDown;
	}
	public void setMovingLeft() {
		player = movingLeft;
	}
	public void setMovingRight() {
		player = movingRight;
	}
	public void setStillUp() {
		lastState = stillUp;
	}
	public void setStillDown() {
		lastState = stillDown;
	}
	public void setStillLeft() {
		lastState = stillLeft;
	}
	public void setStillRight() {
		lastState = stillRight;
	}
}
