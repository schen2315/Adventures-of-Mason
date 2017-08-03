package com.mason.states;

import com.mason.Entities.Entity;
import com.mason.Entities.Level;
import com.mason.Entities.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
/*
 * Handles rendering the player & map on screen
 * Also handles render order
 * and collision management
 * */

public class GameState extends BasicGameState {
	

	private Level worldMap;
	private Player player;
	private Entity barrel;
	private Entity tower;
	boolean quit = false;
	float buckyPositionX;
	float buckyPositionY;

	public GameState(int State) {
		super();
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		buckyPositionX = 0;
		buckyPositionY = 0;
		player = new Player("george.png", 296, 136);
		barrel = new Entity("barrel.tmx", 2, 2, 9*32, 11*32);
		//tower = new Entity("tower.tmx", 3, 11, 6*32, 0*32);
		worldMap = new Level("firstMap.tmx", 20, 20);
		worldMap.player = player;
		
		worldMap.insertEntity(player);
		worldMap.insertEntity(barrel);
		//worldMap.insertEntity(tower);
		
		player.collisionBoxes.add(new Rectangle(296 + 12, 136 + 28, 24, 12));
		player.setRenderBox(new Rectangle(12, 28, 24, 12));
		player.setPos(buckyPositionX + 296, buckyPositionY + 136);
		
		barrel.collisionBoxes.add(new Rectangle(24, 24, 8, 8));
		barrel.collisionBoxes.add(new Rectangle(32, 24, 16, 8));
		barrel.collisionBoxes.add(new Rectangle(32, 32, 10, 30));
		barrel.collisionBoxes.add(new Rectangle(0, 32, 32, 10));
		barrel.collisionBoxes.add(new Rectangle(16, 32 + 10, 16, 16));
		barrel.setRenderBox(new Rectangle(0, 32, 32, 10));
		
		for(int i=0; i < worldMap.objects.size(); i++) {
			System.out.println(worldMap.objects.get(i).getRenderBox().getY());
		}
	}
	//draw stuff on screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		worldMap.draw(buckyPositionX, buckyPositionY);
		//drawCollisionBoxes(g);
		g.drawString( "Bucky's X: " + buckyPositionX + "\nBucky's Y: " + buckyPositionY, 400, 20);
		if(quit == true) {
			g.drawString( "Resume (R)", 250, 100);
			g.drawString( "Main Menu (M)", 250, 150);
			g.drawString( "Quit Game (Q)", 250, 200);
			if(quit == false) {
				g.clear();
			}
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		player.setPlayer();
		//up
		if(input.isKeyDown(Input.KEY_W)) {
			player.setMovingUp();
			player.setStillUp();
			buckyPositionY += delta * .2f;
			if(isCollision()) {
				buckyPositionY -= delta * .2f;
			}
			player.setPos(player.getPosX(), 136 - buckyPositionY);
		}
		//down
		if(input.isKeyDown(Input.KEY_S)) {
			player.setMovingDown();
			player.setStillDown();
			buckyPositionY -= delta * .2f;
			if(isCollision()) {
				buckyPositionY += delta * .2f;
			}
			player.setPos(player.getPosX(), 136 - buckyPositionY);
 		}
		//left
		if(input.isKeyDown(Input.KEY_A)) {
			player.setMovingLeft();
			player.setStillLeft();
			buckyPositionX += delta * .2f;
			if(isCollision()) {
				buckyPositionX -= delta * .2f;
			}
			player.setPos(296 - buckyPositionX, player.getPosY());
		}
		//right
		if(input.isKeyDown(Input.KEY_D)) {
			player.setMovingRight();
			player.setStillRight();
			buckyPositionX -= delta * .2f;
			if(isCollision()) {
				buckyPositionX += delta * .2f;
			}
			player.setPos(296 - buckyPositionX, player.getPosY());
		}
		
		//escape
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			quit = true;
		}
		//when menu is up
		if(quit == true) {
			if(input.isKeyDown(Input.KEY_R)){
				quit = false;
			}
			if(input.isKeyDown(Input.KEY_M)){
				quit = false;
				sbg.enterState(1); 
			}
			if(input.isKeyDown(Input.KEY_Q)){
				System.exit(0);
			}
		}
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		System.out.println("Entering Play State!");
	}
	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) {
		System.out.println("Leaving Play State!");
	}
	public int getID() {
		return 0;
	}
	public boolean isCollision() {
		Rectangle r;
		Entity ent;
		for(int i=0; i < worldMap.objects.size(); i++) {
			ent = worldMap.objects.get(i);
			if(ent != player) {
				for(int j=0; j < worldMap.objects.get(i).collisionBoxes.size(); j++) {
					r = worldMap.objects.get(i).collisionBoxes.get(j);
					if(player.collisionBoxes.get(0).intersects(
							new Rectangle(buckyPositionX + r.getX() + ent.getPosX(), buckyPositionY + r.getY() + ent.getPosY(), 
									r.getWidth(), r.getHeight()))) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	public void drawCollisionBoxes(Graphics g) {
		//player
		g.drawRect(296 + 12, 136 + 28, 24, 12);	
		Rectangle r;
		Entity ent;
		for(int i=0; i < worldMap.objects.size(); i++) {
			ent = worldMap.objects.get(i);
			if(ent != player) {
				for(int j=0; j < worldMap.objects.get(i).collisionBoxes.size(); j++) {
					r = worldMap.objects.get(i).collisionBoxes.get(j);
					g.draw(new Rectangle(buckyPositionX + r.getX() + ent.getPosX(), buckyPositionY + r.getY() + ent.getPosY(), 
							r.getWidth(), r.getHeight()));
				}
			}
		}
	}
}
