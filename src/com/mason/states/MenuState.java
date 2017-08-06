package com.mason.states;



import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState{
	
	Image playNow;
	Image exitGame;
	
	public static final int ID = 1;

	public MenuState(int State){
		
		
	}
	public void enter(GameContainer gc, StateBasedGame sbg){
		System.out.println("Entering MenuState");
		
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg){
		System.out.println("leaving MenuState");
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		playNow = new Image("res/button1.png");
		exitGame = new Image("res/right_sprite_one.png");
		enter(gc, sbg);
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Welcome to The Adventures Of Mason", 150, 50);
		playNow.draw(100, 100);
		exitGame.draw(100, 200);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int DELTA) throws SlickException {
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		//playNow button
		if((posX > 100 && posX < 311) && (posY > 209 && posY < 260)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(2);
			}
		}
		//exit button
		if((posX > 100 && posX < 311) && (posY > 109 && posY < 160)) {
			if(Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
	}

	@Override
	public int getID() {
		
		return ID;
	}

}
