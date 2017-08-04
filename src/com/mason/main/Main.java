package com.mason.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.mason.states.MenuState;
import com.mason.states.GameState;

public class Main extends StateBasedGame {
	//public static int resolution[] = {640, 360};
	public static int resolution[] = {800, 600};
	public static final int menu = 1;
	public static final int game = 1;
	public static AppGameContainer gc;
	public static final String gamename = "Adventures of Mason";
	public Main(String gamename) {
		super(gamename);
		this.addState(new MenuState(1));
		this.addState(new GameState(0));
	}
	public static void main(String[] args) {
		try {
			gc = new AppGameContainer(new Main(gamename));
			gc.setDisplayMode(resolution[0], resolution[1], false);
			gc.start();
		} catch(SlickException e){
			e.printStackTrace();
		}
	}
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(game).init(gc, this);
		this.enterState(menu);
	}
}
