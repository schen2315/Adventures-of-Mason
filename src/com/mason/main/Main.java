package com.mason.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.mason.states.CreditState;
import com.mason.states.GameState;
import com.mason.states.MenuState;
import com.mason.states.PauseState;
import com.mason.states.SplashScreen;


public class Main extends StateBasedGame {
	
	
	public static final String gameName = "The Adventures of Mason  ";
	
	// Game state identifiers
    public static final int PAUSE 		 = 0;
    public static final int MAINMENU     = 1;
    public static final int GAME         = 2;
    public static final int CREDITS		 = 3;
    public static final int SPLASH		 = 4;

    // Application Properties
    public static final int WIDTH   = 640;
    public static final int HEIGHT  = 480;
    public static final int FPS     = 60;
    public static final double VERSION = 1.0;


	public Main(String gameName) {
		super(gameName);
		this.addState(new PauseState(PAUSE));
		this.addState(new MenuState(MAINMENU));
		this.addState(new GameState(GAME));
		this.addState(new CreditState(CREDITS));
		this.addState(new SplashScreen(SPLASH));
		
	}

	
	public static void main(String[] args) {
		  try {
	            AppGameContainer app = new AppGameContainer(new Main(gameName + VERSION));
	            app.setDisplayMode(WIDTH, HEIGHT, false);
	            app.setTargetFrameRate(FPS);
	            app.setShowFPS(true);
	            app.start();
	            
	        } catch(SlickException e) {
	            e.printStackTrace();
	        }
	}


	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		
		this.getState(PAUSE).init(gc, this);
		this.getState(GAME).init(gc, this);
		this.getState(MAINMENU).init(gc, this);
		this.getState(CREDITS).init(gc, this);
		this.getState(SPLASH).init(gc, this);
		this.enterState(MAINMENU);
	}


}
