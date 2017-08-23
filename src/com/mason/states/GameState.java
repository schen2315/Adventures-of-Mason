package com.mason.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.mason.entities.Barrel1;
import com.mason.entities.Cave1;
import com.mason.entities.Entity;
import com.mason.entities.Level;
import com.mason.entities.Player;

import com.mason.player.Inventory;

import com.mason.entities.Tower1;
import com.mason.main.Main;


import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
/*
 * Handles rendering the player & map on screen
 * Also handles render order
 * and collision management
 * */
import org.newdawn.slick.geom.Shape;

public class GameState extends BasicGameState {
	

	private Level worldMap;
	private Player player;
	private Barrel1 barrel;
	private Entity tower;
	private Entity cave;
	private float step = .2f;
	boolean quit = false;
	boolean showInv = false;
	float buckyPositionX;
	float buckyPositionY;

	public GameState(int State) {
		super();
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		buckyPositionX = 0;
		buckyPositionY = 0;
		player = new Player("george.png", 296, 136);
		barrel = new Barrel1(9*32, 11*32);
		tower = new Tower1(6*32, 0*32);
		
		cave = new Cave1(12*32, 0*32);
		worldMap = new Level("firstMap.tmx", 20, 20);
		worldMap.player = player;
		
		worldMap.insertEntity(player);
		worldMap.insertEntity(barrel);
		worldMap.insertEntity(tower);
		worldMap.insertEntity(cave);
		
		player.collisionBoxes.add(new Rectangle(player.center[0] + 12, player.center[1] + 28, 24, 12));
		player.setRenderBox(new Rectangle(12, 28, 24, 12));
	}
	//draw stuff on screen
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		worldMap.draw(buckyPositionX, buckyPositionY);
		//drawCollisionBoxes(g);
		//drawRenderBoxes(g);

		String invString = Inventory.inventory.toString();
		if (showInv == true){
			g.drawString(invString, 50, 20);
		}
		g.drawString( "Bucky's X: " + buckyPositionX + "\nBucky's Y: " + buckyPositionY, 200, 20);

		g.drawString("Bucky's X: " + buckyPositionX + "\nBucky's Y: " + buckyPositionY, 200, 20);
		g.drawString("Mouse X: " + Mouse.getX() + " Mouse Y: " + (Main.resolution[1] - Mouse.getY()), 200, 60);

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
			buckyPositionY += delta * step;
			if(isCollision()) {
				buckyPositionY -= delta * step;
			}
			player.setPos(player.getPosX(), player.center[1] - buckyPositionY);
		}
		//down
		if(input.isKeyDown(Input.KEY_S)) {
			player.setMovingDown();
			player.setStillDown();
			buckyPositionY -= delta * step;
			if(isCollision()) {
				buckyPositionY += delta * step;
			}
			player.setPos(player.getPosX(), player.center[1] - buckyPositionY);
 		}
		//left
		if(input.isKeyDown(Input.KEY_A)) {
			player.setMovingLeft();
			player.setStillLeft();
			buckyPositionX += delta * step;
			if(isCollision()) {
				buckyPositionX -= delta * step;
			}
			player.setPos(player.center[0] - buckyPositionX, player.getPosY());
		}
		//right
		if(input.isKeyDown(Input.KEY_D)) {
			player.setMovingRight();
			player.setStillRight();
			buckyPositionX -= delta * step;
			if(isCollision()) {
				buckyPositionX += delta * step;
			}
			player.setPos(player.center[0] - buckyPositionX, player.getPosY());
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
		
		// Inventory
		if (input.isKeyPressed(Input.KEY_I)){
			showInv = true;
			}
		
		//Pickup Items
		if (input.isKeyPressed(Input.KEY_P)){
			Inventory.itemOne();
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
		Shape r;
		Entity ent;
		for(int i=0; i < worldMap.objects.size(); i++) {
			ent = worldMap.objects.get(i);
			if(ent != player) {
				for(int j=0; j < worldMap.objects.get(i).collisionBoxes.size(); j++) {
					r = worldMap.objects.get(i).collisionBoxes.get(j);
					float rposX, rposY;
					if(r instanceof Line) {
						r.setLocation(buckyPositionX + ent.getPosX(), buckyPositionY + ent.getPosY());
						if(player.collisionBoxes.get(0).intersects(r)) {
							r.setLocation((buckyPositionX + ent.getPosX())*-1, (buckyPositionY + ent.getPosY())*-1);
							return true;
						}
						r.setLocation((buckyPositionX + ent.getPosX())*-1, (buckyPositionY + ent.getPosY())*-1);
					} else {
						rposX = r.getX();
						rposY = r.getY();
						r.setLocation(buckyPositionX + r.getX() + ent.getPosX(), buckyPositionY + r.getY() + ent.getPosY());
						if(player.collisionBoxes.get(0).intersects(r)) {
							r.setLocation(rposX, rposY);
							return true;
						}
						r.setLocation(rposX, rposY);
					}
				}
			}
		}
		
		return false;
	}
	public void drawCollisionBoxes(Graphics g) {
		//player
		g.draw(player.collisionBoxes.get(0));
		Shape r;
		Entity ent;
		float rposX, rposY;
		for(int i=0; i < worldMap.objects.size(); i++) {
			ent = worldMap.objects.get(i);
			if(ent != player) {
				for(int j=0; j < worldMap.objects.get(i).collisionBoxes.size(); j++) {
					r = ent.collisionBoxes.get(j);
					if(r instanceof Line) {
						r.setLocation(buckyPositionX + ent.getPosX(), buckyPositionY + ent.getPosY());
						g.draw(r);
						r.setLocation((buckyPositionX + ent.getPosX())*-1, (buckyPositionY + ent.getPosY())*-1);
					} else {
						rposX = r.getX();
						rposY = r.getY();
						r.setLocation(buckyPositionX + r.getX() + ent.getPosX(), buckyPositionY + r.getY() + ent.getPosY());
						g.draw(r);
						r.setLocation(rposX, rposY);
					}
				}
			}
		}
	}
	public void drawRenderBoxes(Graphics g) {
		//player
		Shape r = player.getRenderBox();
		Entity ent;
		float rposX, rposY;
		rposX = r.getX();
		rposY = r.getY();
		r.setLocation(buckyPositionX + r.getX() + player.getPosX(), buckyPositionY + r.getY() + player.getPosY());
		g.draw(r);
		r.setLocation(rposX, rposY);
//		g.draw(new Rectangle(buckyPositionX + r.getX() + player.getPosX(), buckyPositionY + r.getY() + player.getPosY(), 
//				r.getWidth(), r.getHeight()));
		for(int i=0; i < worldMap.objects.size(); i++) {
			ent = worldMap.objects.get(i);
			r = ent.getRenderBox();
			if(ent != player) {
					//g.draw(new Rectangle(buckyPositionX + r.getX() + ent.getPosX(), buckyPositionY + r.getY() + ent.getPosY(), 
					//		r.getWidth(), r.getHeight()));
					rposX = r.getX();
					rposY = r.getY();
					r.setLocation(buckyPositionX + r.getX() + ent.getPosX(), buckyPositionY + r.getY() + ent.getPosY());
					g.draw(r);
					r.setLocation(rposX, rposY);
			}
		}
	}
}
