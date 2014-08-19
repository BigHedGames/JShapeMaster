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
	static int spriteSize; // using this to temporarily pass size of sprites throughout the program
	private int counter;
	private int posX;     /* ****** for randomizing ******************* */
	private int posY;     /* ****** for randomizing ******************* */
	private int newDX;     /* ****** for randomizing ******************* */
	private int newDY;     /* ****** for randomizing ******************* */
	private int newShape; /* ******** for randomizing ****************** */
	
	
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

				//g2d.drawImage(master.getImage(), master.getX(), master.getY(), this);
				//g2d.drawImage(master.getImage(), master.getX(), master.getY(), 64, 64, this);
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
				//g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
				//g2d.drawImage(a.getImage(), a.getX(), a.getY(), spriteSize, spriteSize, this);
				g2d.drawImage(a.getImage(), 
						a.getX(), a.getY(), a.getX()+(spriteSize+1), a.getY()+(spriteSize+1), 
						(a.myAnimation*128), a.getMyShape(), (a.myAnimation*128)+127, (a.getMyShape())+127, this);
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
							(a.getMyEyezDirection()*128), (a.getMyEyez()), (a.getMyEyezDirection()*128)+127, (a.getMyEyez())+127, this);
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
		
		// if we destroy all alien ships, the game is finished
		if (shapes.size()==0) {
			ingame = false;
		}
		
/*		ArrayList<Missile> ms = craft.getMissiles();
		
		for (int i = 0; i < ms.size(); i++) {
			Missile m = (Missile) ms.get(i);
			if (m.isVisible())
				m.move();
			else ms.remove(i);
		}
*/		
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
	
}
