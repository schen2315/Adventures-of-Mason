package com.mason.entities;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class Entity {
	private Tile[][] tiles;
	private int dimX, dimY;
	private float posX, posY;
	private Rectangle renderBox;
	public ArrayList<Rectangle> collisionBoxes;
	Entity(int dX, int dY) {
		dimX = dX; dimY = dY;
		posX = 0; posY = 0;
		collisionBoxes = new ArrayList<Rectangle>();
		tiles = new Tile[dimX][dimY];
	}
	Entity(String file, int dX, int dY) throws SlickException {
		dimX = dX; dimY = dY;
		tiles = new Tile[dimX][dimY];
		collisionBoxes = new ArrayList<Rectangle>();
		//generate tiles from tmx file
		TiledMap tmx = new TiledMap("res/" + file);
		for(int i=0; i<dimX; i++) {
			for(int j=0; j<dimY; j++) {
				if(tmx.getTileImage(i,j,0) != null)
					tiles[i][j] = new Tile(tmx.getTileImage(i,j,0));
			}
		}
	}
	public Entity(String file, int dX, int dY, int px, int py) throws SlickException {
		dimX = dX; dimY = dY;
		tiles = new Tile[dimX][dimY];
		collisionBoxes = new ArrayList<Rectangle>();
		posX = px; posY = py;
		//generate tiles from tmx file
		TiledMap tmx = new TiledMap("res/" + file);
		for(int i=0; i<dimX; i++) {
			for(int j=0; j<dimY; j++) {
				if(tmx.getTileImage(i,j,0) != null)
					tiles[i][j] = new Tile(tmx.getTileImage(i,j,0));
			}
		}
	}
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public void insertTile(Tile t, int x, int y) {
		if((x >= 0 && x < dimX) && (y >= 0 && y < dimY)) {
			tiles[x][y] = t;
		} else {
			throw new IllegalArgumentException("x and y have to be valid tile dimensions");
		}
	}
	public int getDimX() {
		return dimX;
	}
	public int getDimY() {
		return dimY;
	}
	public Rectangle getRenderBox() {
		return renderBox;
	}
	public void setRenderBox(Rectangle r) {
		renderBox = r;
	}
	public void draw(float offX, float offY) {
		for(int i=0; i < dimX; i++) {
			for(int j=0; j < dimY; j++) {
				if(tiles[i][j] != null)
					tiles[i][j].draw(offX + (i*Tile.width) + posX , offY + (j*Tile.height) + posY);
			}
		}
	}
}
