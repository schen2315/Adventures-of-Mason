package com.mason.states;

import java.awt.Cursor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.mason.entities.Barrel1;
import com.mason.entities.Cave1;
import com.mason.entities.Entity;
import com.mason.entities.Tower1;
import com.mason.main.Main;

public class CollisionEditor extends BasicGameState implements MouseListener, KeyListener {

	public static int state;
	private Entity ent;
	private static Rectangle palette;
	private static int[] paletteSize = {50, 50, 50, 200};
	private Rectangle paletteIconRect;
	private Circle paletteIconCircle;
	private Line paletteIconLine;
	private Boolean isActive = false;
	private Boolean isDrag = false;
	private Boolean isMovingShape = false;
	private float[] startPosition = {0.0f, 0.0f};
	private Shape selected;
	private Shape activeShape = null;
	private LinkedList<Shape> savedShapes;
	private int zoom = 1;
	private static Line axes[];	//index {0,1} is {x,y} axes
	private Boolean[] keyCodes;
	private Circle pointerArea;
	
	private float totalZoom;
	private Boolean onExported = false;
	private float entPosX;
	private float entPosY;
	
	private String resName;
	public CollisionEditor(int state) throws SlickException {
		this.state = state;
	}
	public CollisionEditor(int state, String resName) {
		this.state = state;
		this.resName = resName;
	}
	public void export() throws FileNotFoundException {
		float x,y, x1, y1, w, h, r;
		applyTransform(-1*totalZoom);
		try (PrintWriter out = new PrintWriter("res/" + resName + ".txt")) {
			for(int i=0; i < savedShapes.size(); i++) {
				if(savedShapes.get(i) instanceof Rectangle) {
					System.out.println("Printing to file ...");
					x = savedShapes.get(i).getX();
					y = savedShapes.get(i).getY();
					w = savedShapes.get(i).getWidth();
					h = savedShapes.get(i).getHeight();
					x = x - entPosX;
					y = y - entPosY;
					System.out.println("Rectangle " + x + " " + y + " " + w + " " + h);
					out.println("Rectangle " + x + " " + y + " " + w + " " + h);
				} else if(savedShapes.get(i) instanceof Circle) {
					
				} else if(savedShapes.get(i) instanceof Line) {
					x = ((Line)savedShapes.get(i)).getX1();
					y = ((Line)savedShapes.get(i)).getY1();
					x1 = ((Line)savedShapes.get(i)).getX2();
					y1 = ((Line)savedShapes.get(i)).getY2();
					
					x = x - entPosX;
					y = y - entPosY;
					x1 = x1 - entPosX;
					y1 = y1 - entPosY;
					out.println("Line " + x + " " + y + " " + x1 + " " + y1);
				}
			}
		}
		onExported = true;
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub
		//this.ent = new Barrel1(0, 0);
		//this.ent = new Cave1(0,0);
		this.ent = new Entity(resName, 10, 10);
		this.entPosX = Main.resolution[0]/2;
		this.entPosY = Main.resolution[1]/2;
		palette = new Rectangle(paletteSize[0], paletteSize[1], paletteSize[2], paletteSize[3]);
		axes = new Line[2];
		axes[0] = new Line(0,(Main.resolution[1]/2), Main.resolution[0], (Main.resolution[1]/2));
		axes[1] = new Line((Main.resolution[0]/2), 0, Main.resolution[0]/2, Main.resolution[1]);
		
		paletteIconRect = new Rectangle(50+12, 50+12, 25, 25);
		paletteIconLine = new Line(50+12, 100+12, 50+12+25, 100+12+25);
		selected = paletteIconRect;
		
		pointerArea = new Circle(Mouse.getX(), Main.resolution[1]-Mouse.getY(), 5);
		savedShapes = new LinkedList<Shape>();
		keyCodes = new Boolean[100];
		for(int i=0; i < 100; i++) {
			keyCodes[i] = false;
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.setColor(Color.gray);
		g.draw(axes[0]);
		g.draw(axes[1]);
		g.draw(palette);
		g.setColor(Color.white);
		g.draw(paletteIconRect);
		g.draw(paletteIconLine);
		
		ent.draw(entPosX, entPosY);
		for(int i = 0; i < savedShapes.size(); i++) {
			g.draw(savedShapes.get(i));
		}
		if(activeShape != null) g.draw(activeShape);
		printSelected(g);
		//g.draw(pointerArea);
	}
	public void mouseWheelMoved(int change) {
		
		float zoom = ((float)change)/1000;
		//scale and translate the Entity
		applyTransform(zoom);
	}
	@Override
	public void mousePressed(int button, int x, int y) {
		//check if the mouse pointer is on top of any of the savedShapes
		//how to set mouse cursor?
		if(button == 0) {
			//check hover over a paletteIcon area:
			if(x >=62 && x <= 87 && y >= 62 && y <= 87) {
				selected = paletteIconRect;
			}
			if(x >=62 && x <= 87 && y >= (62+50) && y <= (87+50)) {
				selected = paletteIconLine;
			}
			pointerArea.setLocation(x,y);
			//check through shapes in the savedShapes list
			//starting from the tail down
			for(int i = savedShapes.size()-1; i >= 0; i--) {
				if(savedShapes.get(i) instanceof Rectangle) {
					//change size
					if(pointerArea.intersects(savedShapes.get(i))) {
						//if one is found that intersects the mouse pointer move to tail of list
						System.out.println("About to change shape size");
					} 
					//move shape around
					else if(savedShapes.get(i).contains(x,y)) {
						System.out.println("About to move shape around");
						
					}
				} else if(savedShapes.get(i) instanceof Circle) {
					
				} else if(savedShapes.get(i) instanceof Line) {
					
				}
			}
			isActive = true;
			startPosition[0] = x;
			startPosition[1] = y;
		} else if(button == 1) {
			isDrag = true;
		}
		System.out.println("button: " + button);
		System.out.println("x: " + startPosition[0]);
		System.out.println("y: " + startPosition[1]);
	}
	public void mouseDragged(int oldx, int oldy, int x, int y) {
		if(isActive) {
			//System.out.println("x: " + x);
			//System.out.println("y: " + y);
			if(selected == paletteIconRect) 
				activeShape = new Rectangle(startPosition[0], startPosition[1], x - startPosition[0], y-startPosition[1]);
			else if(selected == paletteIconLine)
				activeShape = new Line(startPosition[0], startPosition[1], x, y);
		} else if(isDrag) {
			//translate everything on screen:
			applyTransform(x-oldx, y-oldy);
		}
	}
	public void mouseReleased(int button, int x, int y) {
		if(button == 0 && isActive) {
			isActive = false;
			//add new shape to list
			if(selected == paletteIconRect)
				savedShapes.add(new Rectangle(startPosition[0], startPosition[1], x - startPosition[0], y-startPosition[1]));
			else if(selected == paletteIconLine)
				savedShapes.add(new Line(startPosition[0], startPosition[1], x , y));
			activeShape = null;
		} else if(button == 1 && isDrag) {
			isDrag = false;
		}
	}
	public void mouseMoved(int oldx, int oldy, int x, int y) {
		pointerArea.setLocation(x-5, y-5);
		
	};
	public void keyPressed(int key, char c) {
		System.out.println(key);
		keyCodes[key] = true;
		//press ctrl-z
		if(keyCodes[29] && keyCodes[44]) {
			savedShapes.removeLast();
		}
		//1 -> Rectangle
		if(keyCodes[2]) {
			selected = paletteIconRect;
		}
		//2 -> Circle
		if(keyCodes[3]) {
			
		}
		//3 -> Line
		if(keyCodes[4]) {
			selected = paletteIconLine;
		}
		//Enter -> export to file
		if(keyCodes[28]) {
			try {
				export();
				System.out.println("exported");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void keyReleased(int key, char c) {
		System.out.println(key);
		keyCodes[key] = false;
	}
	public void printSelected(Graphics g) {
		String toPrint = "";
		if(selected == paletteIconRect) {
			toPrint = "Rectangle";
		} else if(selected == paletteIconCircle) {
			toPrint = "Circle";
		} else if(selected == paletteIconLine) {
			toPrint = "Line";
		} 
		g.drawString("Selected " + toPrint, Main.resolution[0] - 300, 100);
		g.drawString("Press Enter to export to .txt file", Main.resolution[0] - 400, 150);
		if(onExported) {
			//onExported = false;
			g.drawString("Successfully exported to res/" + resName + ".txt", Main.resolution[0] - 400, 200);
		}
	}
	public void applyTransform(float zoom) {
		//scaling
		float newzoom = zoom/ent.getScale();
		float x,y,w,h,dx,dy;
		//only apply scaling if the new scale is greater than 1
		if(ent.getScale() + zoom > 0) {
			totalZoom += zoom;
			ent.setScale(ent.getScale()+zoom);
			for(int i=0; i < savedShapes.size(); i++) {
				if(savedShapes.get(i) instanceof Rectangle) {
					x = savedShapes.get(i).getX();
					y = savedShapes.get(i).getY();
					w = savedShapes.get(i).getWidth();
					h = savedShapes.get(i).getHeight();
					dx = x - entPosX;
					dy = y - entPosY;
					savedShapes.set(i, new Rectangle(entPosX + dx*(1+newzoom), entPosY + dy*(1+newzoom), w + w*newzoom, h + h*newzoom));
				} else if(savedShapes.get(i) instanceof Circle) {
					
				} else if(savedShapes.get(i) instanceof Line) {
					x = ((Line)savedShapes.get(i)).getX1();
					y = ((Line)savedShapes.get(i)).getY1();
					dx = x - entPosX;
					dy = y - entPosY;
					x = ((Line)savedShapes.get(i)).getX2() - ((Line)savedShapes.get(i)).getX1();
					y = ((Line)savedShapes.get(i)).getY2() - ((Line)savedShapes.get(i)).getY1();
					x = x*(1+newzoom);
					y = y*(1+newzoom);
					savedShapes.set(i, new Line(entPosX + dx*(1+newzoom), entPosY + dy*(1+newzoom), entPosX + dx*(1+newzoom) + x, entPosY + dy*(1+newzoom) + y));
				}
			}
		}
	}
	public void applyTransform(float x, float y) {
		//translation
		float oldx,oldy,oldx1, oldy1, w,h;
		entPosX += x;
		entPosY += y;
		
		oldx = axes[0].getX();
		oldy = axes[0].getY();
		axes[0] = new Line(0, oldy + y, Main.resolution[0], oldy + y);
		oldx = axes[1].getX();
		oldy = axes[1].getY();
		axes[1] = new Line(oldx + x, 0, oldx + x, Main.resolution[1]);
		for(int i=0; i < savedShapes.size(); i++) {
			if(savedShapes.get(i) instanceof Rectangle) {
				oldx = savedShapes.get(i).getX();
				oldy = savedShapes.get(i).getY();
				w = savedShapes.get(i).getWidth();
				h = savedShapes.get(i).getHeight();
				savedShapes.set(i, new Rectangle(oldx + x, oldy + y, w, h));
			} else if(savedShapes.get(i) instanceof Circle) {
				
			} else if(savedShapes.get(i) instanceof Line) {
				oldx = ((Line)savedShapes.get(i)).getX1();
				oldy = ((Line)savedShapes.get(i)).getY1();
				oldx1 = ((Line)savedShapes.get(i)).getX2();
				oldy1 = ((Line)savedShapes.get(i)).getY2();
				System.out.println("x: " + x + ", y: " + y);
				savedShapes.set(i, new Line(oldx+x, oldy+y, oldx1+x, oldy1+y));
			}
		}
	}
	public void applyTransformCase(float zoom) {
		
	}
	public void applyTransformCase(float x, float y) {
		
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return state;
	}

}
