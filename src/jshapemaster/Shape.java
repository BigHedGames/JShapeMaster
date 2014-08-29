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
	private String shapeEyez = "Shapez_Eyez.png";
	private String shapeMouthz = "Shapez_Mouthz.png";
	
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean visible;
	private Image image;
	private Image imageEyes, imageMouth;
	private int dx, dy;
	private int myShape, myEyez, myMouth;
	static int myAnimation, myEyezAnimation, myMouthAnimation;
	private int skipIt;
	private int shapeAnimationDelay; // for delaying the animation of the shapes

	//private int wait;     /* ****** for randomizing ******************* */

	
	public Shape(int x, int y, int dx, int dy, int myShape, int myAnimation, int myEyez, int myEyezAnimation, int myMouth, int myMouthAnimation) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(shapePix));
		image = ii.getImage();

		ImageIcon ie = new ImageIcon(this.getClass().getResource(shapeEyez));
		imageEyes = ie.getImage();

		ImageIcon iMouth = new ImageIcon(this.getClass().getResource(shapeMouthz));
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
		this.myEyez = myEyez;
		this.myEyezAnimation = myEyezAnimation;
		this.myMouth = myMouth;
		this.myMouthAnimation = myMouthAnimation;
		
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
		if (true) {
		skipIt = 0; 
		
		x += dx;
		y += dy;
		
		if ((x<0)||(x>(800-width))) {
			x -= dx;
			dx = (-dx);}

		if ((y<0)||(y>(600-height))) {
			y -= dy;
			dy = (-dy);}
		
		shapeAnimationDelay++;
		if (shapeAnimationDelay == 5) {
			shapeAnimationDelay = 0;

			if (myAnimation == 3) {
				myAnimation = 0;
				} else {
					myAnimation++;
				}
			if (myMouthAnimation == 3) {
				myMouthAnimation = 0;
				} else {
					myMouthAnimation++;
				}	
			if (myEyezAnimation == 7) {
				myEyezAnimation = 0;
				} else {
					myEyezAnimation++;
				}

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

	public int getMyShapeMasterIndexed() {
		if (myShape == Master.masterAnimationShape ){
			return ((myShape*256)+128);
		} else {
			return (myShape*256);
		}
	}
	
	public int getMyEyez() {
		if (myShape == Master.masterAnimationShape ){
			myEyez = 128;
			return myEyez;
		} else {
			myEyez = 0;
			return myEyez;
		}
	}
	
	public int getMyMouth() {
		if (myShape == Master.masterAnimationShape ){
			myMouth = 128;
			return myMouth;
		} else {
			myMouth = 0;
			return myMouth;
		}
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


	