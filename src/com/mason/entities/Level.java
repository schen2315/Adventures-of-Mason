package com.mason.entities;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Level {
	private Tile[][] map;
	private int dimX, dimY;
	public ArrayList<Entity> objects;
	public Player player;
	Level(int dX, int dY) {
		dimX = dX; dimY = dY;
		map = new Tile[dimX][dimY];
		objects = new ArrayList<Entity>();
	}
	public Level(String file, int dX, int dY) throws SlickException {
		dimX = dX; dimY = dY;
		map = new Tile[dimX][dimY];
		objects = new ArrayList<Entity>();
		TiledMap tmx = new TiledMap("res/" + file);
		for(int i=0; i<dimX; i++) {
			for(int j=0; j<dimY; j++) {
				if(tmx.getTileImage(i,j,0) != null)
					map[i][j] = new Tile(tmx.getTileImage(i,j,0));
			}
		}
	}
	public void insertTile(Tile t, int x, int y) {
		if((x >= 0 && x < dimX) && (y >= 0 && y < dimY)) {
			map[x][y] = t;
		} else {
			throw new IllegalArgumentException("x and y have to be valid tile dimensions");
		}
	}
	public Tile getTile(int x, int y) {
		if((x >= 0 && x < dimX) && (y >= 0 && y < dimY)) {
			return map[x][y];
		} else {
			throw new IllegalArgumentException("x and y have to be valid tile dimensions");
		}
	}
	public void insertEntity(Entity ent) {
		objects.add(ent);
	}
	public void draw(float offX, float offY) {
		//draw ground
		for(int i=0; i < dimX; i++) {
			for(int j=0; j < dimY; j++) {
				if(map[i][j] != null)
					map[i][j].draw(offX + (i*Tile.width), offY + (j*Tile.height));
			}
		}
		//render objects & player
		//sort objects using render boxes
		Collections.sort(objects, (ent1, ent2) -> {
			float val1 = ((Entity) ent1).getRenderBox().getY() + ((Entity) ent1).getRenderBox().getHeight() + ent1.getPosY();
			float val2 = ((Entity) ent2).getRenderBox().getY() + ((Entity) ent2).getRenderBox().getHeight() + ent2.getPosY();
			//System.out.println("val1: " + val1 + " val2: " + val2);
			if(val1 > val2) return 1;
			else if(val1 == val2) return 0;
			else return -1;
		});
		for(int i=0; i < objects.size(); i++) {
			objects.get(i).draw(offX, offY);
		}
	}
}
