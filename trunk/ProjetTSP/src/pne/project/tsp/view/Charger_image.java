package pne.project.tsp.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Charger_image extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics image){
	    try {
	      Image img = ImageIO.read(new File("Menu.jpg"));
	      image.drawImage(img, 0, 0, this);
	      //Pour une image de fond
	      //g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	    } catch (IOException e) {
	    		e.printStackTrace();
	    } 
	}
}
