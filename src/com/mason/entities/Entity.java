package com.mason.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class Entity {
	private Tile[][][] tiles;
	private int dimX, dimY, numLayers;
	private float posX, posY;
	private float scale = 1;
	private Rectangle renderBox;

	public ArrayList<Shape> collisionBoxes;

	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}

	Entity(int dX, int dY) {
		dimX = dX; dimY = dY;
		posX = 0; posY = 0;
		collisionBoxes = new ArrayList<Shape>();
		tiles = new Tile[1][dimX][dimY];
		//default renderBox:
		this.setRenderBox(new Rectangle(0,0,32,32));
	}
	public Entity(String file, int dX, int dY) throws SlickException {
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
						tiles[k][i][j].draw(offX + (i*Tile.width*scale) + posX , offY + (j*Tile.height*scale) + posY, scale);
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
	public void loadCollisionFile(String fileName) throws IOException {
		String line;
		String[] params;
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                params = line.split(" ");
                for(int i=0; i < params.length; i++) params[i] = params[i].replaceAll("\\s+", "");
                float p[];
                if(params[0].equals("Rectangle")) {
                	p = new float[4];
                	for(int i=0; i < 4; i++) {
                		p[i] = Float.parseFloat(params[i+1]);
                	}
                	System.out.println("adding rectangle");
                	collisionBoxes.add(new Rectangle(p[0], p[1], p[2], p[3]));
                } else if(params[0].equals("Circle")) {
                	
                } else if(params[0].equals("Line")) {
                	System.out.println("adding line");
                	p = new float[4];
                	for(int i=0; i < 4; i++) {
                		p[i] = Float.parseFloat(params[i+1]);
                	}
                	collisionBoxes.add(new Line(p[0], p[1], p[2], p[3]));
                }
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
	}
}
