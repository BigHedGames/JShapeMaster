package jshapemaster;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/*
 * this is the Shape class
 * 
 * 
 */
public class Shape {
	
	private String shapePix = "RedBlueShapes.png";
	private String shapeEyez = "MAD_EYEZ.png";
	private String shapeMouth = "MAD_MOUTHZ.png";
	
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean visible;
	private Image image;
	private Image imageEyes, imageMouth;
	private int dx, dy;
	private int myShape;
	static int myAnimation;
	private int skipIt;
	//private int wait;     /* ****** for randomizing ******************* */

	
	public Shape(int x, int y, int dx, int dy, int myShape, int myAnimation) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(shapePix));
		image = ii.getImage();

		ImageIcon ie = new ImageIcon(this.getClass().getResource(shapeEyez));
		imageEyes = ie.getImage();

		ImageIcon iMouth = new ImageIcon(this.getClass().getResource(shapeMouth));
		imageMouth = iMouth.getImage();

		//width = image.getWidth(null);
		width = JSMBoard.spriteSize;
		//height = image.getHeight(null);
		height = JSMBoard.spriteSize;
		visible = true;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.myShape = myShape;
		this.myAnimation = myAnimation;
//		dx = 1;
//		dy = 1;
		skipIt = 0;

	}
	
/* 
 * 	We don't want to move the shapes every time this is called (every 5 ms), so we use skipIt to 
 *  skip the routine every so often...
 */
	public void move() {
		
		skipIt++;
		if (skipIt == 6) {
		skipIt = 0; 
		
		x += dx;
		y += dy;
		
		if ((x<0)||(x>(800-width))) {
			x -= dx;
			dx = (-dx);}

		if ((y<0)||(y>(600-height))) {
			y -= dy;
			dy = (-dy);}
		
		if (myAnimation == 3) {
		myAnimation = 0;
		} else {
			myAnimation++;
		}
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getShape() {
		return myShape;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Image getImage() {
		return image;
	}

	public Image getImageEyes(){
		return imageEyes;
	}

	public Image getImageMouth(){
		return imageMouth;
	}
	

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}


	