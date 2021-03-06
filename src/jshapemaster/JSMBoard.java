package jshapemaster;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Rectangle;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

//import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;
//import java.math.*;


/*
 * We can move the Master, short for ShapeMaster
 * on the board using the cursor keys.
 * 
 * This is the Board class
 */
@SuppressWarnings("serial")
public class JSMBoard extends JPanel implements ActionListener {
	
	private Timer timer;
	private Master master;
	
	private ArrayList<Shape> shapes;
	private boolean ingame;
	static int B_WIDTH;
	static int B_HEIGHT;
	static int spriteSize; // using this to temporarily pass size of sprite throughout the program
	private int counter;
	private int posX;     /* ****** for randomizing ******************* */
	private int posY;     /* ****** for randomizing ******************* */
	private int newDX;     /* ****** for randomizing ******************* */
	private int newDY;     /* ****** for randomizing ******************* */
	private int newShape; /* ******** for randomizing ****************** */
	
	// velocity related constant... from c#...  ****************************************
	//protected readonly double DIAGONAL_FACTOR = 1.0 / Math.Sqrt(2.0); ****************
	static double DIAGONAL_FACTOR = Math.sqrt(2.0);
	
	public JSMBoard() {
		
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		
		ingame = true;
		
		setSize(800, 600);
		spriteSize = 64;
		
		master = new Master();
		
		counter = 0;
		initShapes();
		
		timer = new Timer(5, this);
		timer.start();
		
//		System.out.printf("B_WIDTH IS: %d\n", B_WIDTH);

	}

	
	static int getB_WIDTH() {
//		System.out.printf("B_WIDTH IS: %d\n", B_WIDTH);
		return B_WIDTH;
	}
	
	static int getB_HEIGHT() {
		return B_HEIGHT;
	}
	

	
	public void addNotify() {
		super.addNotify();
		B_WIDTH = getWidth();
		B_HEIGHT = getHeight();
//		counter++;
//		System.out.printf("B_WIDTH IS: %d and counter is; %d\n", B_WIDTH, counter);
		
	}
	
	public void initShapes() {
		shapes = new ArrayList<Shape>();
		
//		for (int i=0; i<pos.length; i++) {
/*		for (int i=0; i<2; i++) {
			shapes.add(new Shape(pos[i][0], pos[i][1]));
		} */
		Random r = new Random();       /* ****** for randomizing ******************* */
		for (int i=0; i<3; i++){
			counter++; // new shape coming out
			posX = r.nextInt(800-spriteSize);        /* ****** for randomizing ******************* */
			posY = r.nextInt(600-spriteSize);        /* ****** for randomizing ******************* */
			newDX = r.nextInt(3);
			newDY = r.nextInt(3);
			newShape = r.nextInt(4);
			shapes.add(new Shape(posX, posY, newDX, newDY, newShape, 0, 0, 0, 0, 0));
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if (ingame) {
			
			Graphics2D g2d = (Graphics2D)g;
			
			if (master.isVisible())
				/*
				 * In the paint() method, we draw the Master Shape.  We get the
				 * image and the coordinates from the sprite class
				 */

				g2d.drawImage(master.getImage(), 
						master.getX(), master.getY(), master.getX()+(spriteSize+1), master.getY()+(spriteSize+1), 
						(Master.masterAnimation*128), (Master.masterAnimationShape*128), (Master.masterAnimation*128)+127, (Master.masterAnimationShape*128)+127, this);

				g2d.drawImage(master.imageEyes, 
						master.getX(), master.getY(), master.getX()+(spriteSize+1), master.getY()+(spriteSize+1), 
						(Master.masterEyezAnimation*128), (0), (Master.masterEyezAnimation*128)+127, (0)+127, this);

				g2d.drawImage(master.imageMouth, 
						master.getX(), master.getY(), master.getX()+(spriteSize+1), master.getY()+(spriteSize+1), 
						(Master.masterMouthAnimation*128), (0), (Master.masterMouthAnimation*128)+127, (0)+127, this);

		
			/*
			 * Let's draw the shapes!
			 */
			for (int i = 0; i < shapes.size(); i++) {
			Shape a = (Shape)shapes.get(i);
			if (a.isVisible())
				g2d.drawImage(a.getImage(), 
						a.getX(), a.getY(), a.getX()+(spriteSize+1), a.getY()+(spriteSize+1), 
						(a.myAnimation*128), a.getMyShapeMasterIndexed(), (a.myAnimation*128)+127, (a.getMyShapeMasterIndexed())+127, this);
				g2d.drawImage(a.getImageMouth(), 
						a.getX(), a.getY(), a.getX()+(spriteSize+1), a.getY()+(spriteSize+1), 
						(a.myMouthAnimation*128), (a.getMyMouth()), (a.myMouthAnimation*128)+127, (a.getMyMouth())+127, this);
				
				if (a.getShape() == master.getMasterAnimationShape()) {
					g2d.drawImage(a.getImageEyes(), 
							a.getX(), a.getY(), a.getX()+(spriteSize+1), a.getY()+(spriteSize+1), 
							(a.myEyezAnimation*128), (a.getMyEyez()), (a.myEyezAnimation*128)+127, (a.getMyEyez())+127, this);
				} else {
					g2d.drawImage(a.getImageEyes(), 
							a.getX(), a.getY(), a.getX()+(spriteSize+1), a.getY()+(spriteSize+1), 
							(whereIsHe(a.getX(), a.getY(), master.getX(), master.getY())*128), (a.getMyEyez()), 
							(whereIsHe(a.getX(), a.getY(), master.getX(), master.getY())*128)+127, (a.getMyEyez())+127, this);
				}
		}
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("Shapes left: " + shapes.size(), 5, 15);
		
	} else {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);
		
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
				B_HEIGHT / 2);
		
		if (shapes.size()==0) {
			g.setColor(Color.red);
			String msg2 = "YOU WON!!";
			g.drawString(msg2, (B_WIDTH - metr.stringWidth(msg)) / 2,
					(B_HEIGHT / 2)+20);
		}
		

	}
	
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	
	/*
	 * The actionPerformed() method is called every 5ms.
	 * We move the sprite and repaint the board.
	 * We also parse all missiles from the array list.  depending on the 
	 * visible flag, we move the missile or remove it from the container.
	 */
	public void actionPerformed(ActionEvent e) {
		
		// if there are no more shapes, the game is finished
		if (shapes.size()==0) {
			ingame = false;
		}
		
		
		/*
		 * The paint() method draws all shapes from the shapes ArrayList.
		 * They are drawn only if they have not been previously destroyed.
		 * this is checked by the isVisible() method.
		 */
//		for (int i = 0; i < shapes.size(); i++) {
		for (int i = 0; i < counter; i++) {
			Shape a = (Shape) shapes.get(i);
			if (a.isVisible())
				a.move();
			else shapes.remove(i);
		}
		
		master.move();
		checkCollisions();
		repaint();
	}
	
	public void checkCollisions() {
		
		Rectangle r3 = master.getBounds();
		/*
		for (int j = 0; j<shapes.size(); j++) {
			Shape a = (Shape) shapes.get(j);
			Rectangle r2 = a.getBounds();
			
			if (r3.intersects(r2)) {
				master.setVisible(false);
				a.setVisible(false);
				ingame = false;
			}
		}
		*/
	}
	
	private class TAdapter extends KeyAdapter {
		
		public void keyReleased(KeyEvent e) {
			master.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e) {
			master.keyPressed(e);
		}
	}

	/*
	 * This is a little routine that gives a single int locator for position of the destX & destY
	 * from the sourceX & sourceY... typically from the shape to the master character...
	 * in a format like:
	 *                          0
	 *                        7   1
	 *                      6   S   2
	 *                        5   3
	 *                          4
	 *  where S is the source position (X & Y) and the number represents the integer returned for the approx destination
	 *  or dest location (X & Y)                        
	 */
	public int whereIsHe(int sourceX, int sourceY, int destX, int destY) {
		
//		if (sourceX == destX) // he is above or below us...
		if (Math.abs(sourceX-destX) < 4) // he is approx above or below us...
		{
			if (sourceY >= destY) // he is above us
			{
				return 0;
			} else                // he must be below us then...
			{
				return 4;   
			}
		}
		
//		if (sourceY == destY) // he is to the right or left of us
		if (Math.abs(sourceY-destY) < 4)  // he is approx to the right or left of us
		{
			if (sourceX <= destX) // he is to the right of us
			{
				return 2;
			} else                // he is to the left of us
			{
				return 6;
			}
		}
		if (sourceX < destX) // he is to the right
		{
			if (sourceY > destY) // he is above
				{
				return 1;
				} else   // he must be below
				{
				return 3;
				}
		}
		if (sourceX > destX) // he is to the left
		{
			if (sourceY > destY) // he is above
				{
				return 7;
				} else   // he must be below
				{
				return 5;
				}
		}
		return 0;
	}
}
