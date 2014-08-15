package jshapemaster;

import javax.swing.JFrame;

/*
 * This is the Shape Master MAIN class
 * 
 */
@SuppressWarnings("serial")
public class JShapeMaster extends JFrame {
	
	public JShapeMaster() {
		add(new JSMBoard());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Shape Master");
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new JShapeMaster();
		
	}
}
