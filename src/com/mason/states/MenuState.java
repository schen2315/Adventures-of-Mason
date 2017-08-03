package com.mason.states;
import com.mason.main.Main;

import org.lwjgl.input.Mouse;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {
	public Image playNow;
	public Image exitGame;
	private int playNowResolution[] = {202,37};
	private int exitGameResolution[] = {202,37};
	private int playNowPosition[] = {100, 100};
	private int exitGamePosition[] = {100, 200};
	
	public MenuState(int State) {
		
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		playNow = new Image("res/playNow.png");
		exitGame = new Image("res/exitGame.png");
	}
	//draw stuff on screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Welcome to Mason's Adventures!", 100, 50);
		playNow.draw(playNowPosition[0], playNowPosition[1]);
		exitGame.draw(exitGamePosition[0], exitGamePosition[1]);
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		//playNow button -> later we should put make this a function that
		//takes a function as a parameter
		if((posX >= playNowPosition[0] && posX <= playNowPosition[0] + playNowResolution[0])
				&& (posY >= (Main.resolution[1]-playNowPosition[1]-playNowResolution[1]) 
				&& posY <= (Main.resolution[1]-playNowPosition[1]))) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(0);
				System.out.println("Entering game state");
			}
		}
		//exit button
		if((posX >= exitGamePosition[0] && posX <= exitGamePosition[0] + exitGameResolution[0])
				&& (posY >= (Main.resolution[1]-exitGamePosition[1]-exitGameResolution[1]) 
				&& posY <= (Main.resolution[1]-exitGamePosition[1]))) {
			if(Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
	}
	public int getID() {
		return 1;
	}
}

