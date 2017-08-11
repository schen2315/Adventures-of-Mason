package com.mason.entities;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class Entity {
	private Tile[][][] tiles;
	private int dimX, dimY, numLayers;
	private float posX, posY;
	private Rectangle renderBox;
	public ArrayList<Shape> collisionBoxes;
	Entity(int dX, int dY) {
		dimX = dX; dimY = dY;
		posX = 0; posY = 0;
		collisionBoxes = new ArrayList<Shape>();
		tiles = new Tile[1][dimX][dimY];
		//default renderBox:
		this.setRenderBox(new Rectangle(0,0,32,32));
	}
	Entity(String file, int dX, int dY) throws SlickException {
		dimX = dX; dimY = dY;
		collisionBoxes = new ArrayList<Shape>();
		//default renderBox:
		this.setRenderBox(new Rectangle(0,0,32,32));
		//generate tiles from tmx file
		loadTMX(file);
	}
	public Entity(String file, int dX, int dY, int px, int py) throws SlickException {
		dimX = dX; dimY = dY;
		collisionBoxes = new ArrayList<Shape>();
		posX = px; posY = py;
		//default renderBox:
		this.setRenderBox(new Rectangle(0,0,32,32));
		//generate tiles from tmx file
		loadTMX(file);
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
	public void insertTile(Tile t, int x, int y, int layer) {
		if((x >= 0 && x < dimX) && (y >= 0 && y < dimY) && (layer >= 0 && layer < numLayers)) {
			tiles[layer][x][y] = t;
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
		for(int k=0; k<numLayers; k++) {
			for(int i=0; i < dimX; i++) {
				for(int j=0; j < dimY; j++) {
					if(tiles[k][i][j] != null)
						tiles[k][i][j].draw(offX + (i*Tile.width) + posX , offY + (j*Tile.height) + posY);
				}
			}
		}
	}
	public void loadTMX(String file) throws SlickException {
		TiledMap tmx = new TiledMap("res/" + file);
		numLayers = tmx.getLayerCount();
		System.out.println(file + " layers: " + numLayers + " dimX: " + dimX + " dimY: " + dimY);
		tiles = new Tile[numLayers][dimX][dimY];
		for(int k=0; k < numLayers; k++) {
			for(int i=0; i<dimX; i++) {
				for(int j=0; j<dimY; j++) {
					if(tmx.getTileImage(i,j,k) != null)
						tiles[k][i][j] = new Tile(tmx.getTileImage(i,j,k));
				}
			}
		}
	}
}
