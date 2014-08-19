package jshapemaster;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;

/*
 * We can move the Master character
 * on the board using the cursor keys.
 * 
 * This class represents a ShapeMASTER.  in this class we keep the image of the
 * sprite and the coordinates of the sprite.  The keyPressed() and keyReleased()
 * methods control whether the sprite is moving or is in standstill
 * 
 * Same file is used for the second example... where we add firing missiles.
 * So we added ArrayLists, and the file changed a bit.
 * 
 * ********************currentframe=spritesheet.getSubimage(x, y, w, h); 
 * Make your sprite sheet knowing the size and number of each sequence.
 * Grab a buffered image of your sheet and use something like this: 
 * currentframe=spritesheet.getSubimage(x, y, w, h); 
 * 
 */
public class Master {
	
	private String masterShapePix = "CirSqStTr.png";
	private String masterMouthPix = "CHAR_MOUTHZ.png";
	private String masterEyesPix = "CHAR_EYEZZ.png";
	static int masterAnimation;
	static int masterAnimationShape;
	static int masterMouthAnimation;
	static int masterEyezAnimation;
	final int CIRCLE = 0;
	final int SQUARE  = 1;
	final int STAR = 2;
	final int TRIANGLE = 3;
	
	
	
	private int dx;
	private int dy;
	private int x;
	private int y;
	
	private int width;
	private int height;
	private boolean visible;
	
	private Image image;
	public Image imageEyes;
	public Image imageMouth;
	
	public Master() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(masterShapePix));
		image = ii.getImage();
		ImageIcon ie = new ImageIcon(this.getClass().getResource(masterEyesPix));
		imageEyes = ie.getImage();
		ImageIcon im = new ImageIcon(this.getClass().getResource(masterMouthPix));
		imageMouth = im.getImage();
		//width = image.getWidth(null);
		width = JSMBoard.spriteSize;
		//height = image.getHeight(null);
		height = JSMBoard.spriteSize;
		visible = true;
		x = 40;
		y = 60;
		masterAnimation = 0;
		masterAnimationShape = 0;
	}
	
	/*
	 * The move() method changes the coordinates of the sprite.  These x,y
	 * values are used in the paint() method to draw the image of the sprite
	 */
	public void move() {
		x += dx;
		y += dy;
		
		if (x<1) {
			x = 1;
		} 
		else if (x >(JSMBoard.getB_WIDTH()- width)) {
//			x = (JSMBoard.getB_WIDTH()- width);
			x -= dx;
		}
		
		
		if (y<1) {
			y = 1;
		}
		else if (y >= (JSMBoard.getB_HEIGHT()- height)) {
//			y = (JSMBoard.getB_HEIGHT()- height);
			y -= dy;
		}

	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public int getMasterAnimationShape() {
		return masterAnimationShape;
		}
	
	public Image getImage() {
		return image;
	}
	
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	/*
	 * the getBounds() method returns the bounds of the spacecraft image.
	 * We need them in collision detection.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
			if (masterAnimation == 3) {
				masterAnimation = 0;
				} else {
				masterAnimation ++;}
			masterMouthAnimation = 1;
			if ((masterEyezAnimation == 0)||(masterEyezAnimation == 10)) {
				masterEyezAnimation = 5;
			} else {
				masterEyezAnimation = 10;
			}
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
			if (masterAnimation == 3) {
				masterAnimation = 0;
				} else {
				masterAnimation ++;}
			masterMouthAnimation = 1;
			if (masterEyezAnimation == 0) {
				masterEyezAnimation = 2;
			} else {
				masterEyezAnimation = 0;
			}
		}
		
		if (key == KeyEvent.VK_UP) {
			dy = -1;
			if (masterAnimation == 3) {
				masterAnimation = 0;
				} else {
				masterAnimation ++;}
			masterMouthAnimation = 1;
			if ((masterEyezAnimation == 0)||(masterEyezAnimation == 9)) {
				masterEyezAnimation = 7;
			} else {
				masterEyezAnimation = 9;
			}
		}
		
		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
			if (masterAnimation == 3) {
				masterAnimation = 0;
				} else {
				masterAnimation ++;}
			masterMouthAnimation = 1;
			if ((masterEyezAnimation == 0)||(masterEyezAnimation == 9)) {
				masterEyezAnimation = 8;
			} else {
				masterEyezAnimation = 9;
			}
		}
		
		if (key == KeyEvent.VK_A) {
			masterAnimationShape=CIRCLE;
		}
		if (key == KeyEvent.VK_S) {
			masterAnimationShape=SQUARE;
		}
		if (key == KeyEvent.VK_D) {
			masterAnimationShape=STAR;
		}
		if (key == KeyEvent.VK_F) {
			masterAnimationShape=TRIANGLE;
		}
	}

	/*
	 * When we release the cursor key we set the appropriate dx or dy 
	 * variable to zero.  The spacecraft will stop moving.
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
			masterAnimation = 0;
			masterMouthAnimation = 0;
			masterEyezAnimation = 0;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
			masterAnimation = 0;
			masterMouthAnimation = 0;
			masterEyezAnimation = 0;
		}
		
		if (key == KeyEvent.VK_UP) {
			dy = 0;
			masterAnimation = 0;
			masterMouthAnimation = 0;
			masterEyezAnimation = 0;
		}
		
		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
			masterAnimation = 0;
			masterMouthAnimation = 0;
			masterEyezAnimation = 0;
		}
	}
}
