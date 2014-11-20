package pne.project.tsp.view;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class MainCanvas extends Canvas{
	private Image fondEcran;
	private Rectangle chargerPVC;
	private Rectangle quitter;
	
	public MainCanvas(int w, int h){
		ImageIcon image = new ImageIcon("Images/Main.jpg");
		fondEcran = image.getImage();
		// rect = (x, y, largeur, hauteur)
		chargerPVC = new Rectangle(w/4, 150, w/2, 100);
		quitter = new Rectangle(w/4, 350, w/2, 100);
		//System.out.println(getWidth() + " " + getHeight());
	}
	
	public void paint(Graphics graphics){
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(fondEcran, 0, 0, this);
		
		// remplissage des boutons
		g.setColor(Color.WHITE);
		g.fill(chargerPVC);
		g.fill(quitter);
		
		// contours des boutons
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(4));
		g.draw(chargerPVC);
		g.draw(quitter);
		
		// ecriture des boutons
		g.setColor(Color.BLACK);
		g.setFont(new Font("Dialog", Font.BOLD, 35));
		g.drawString("Charger un PVC", (int) (chargerPVC.getX()+chargerPVC.getWidth()/6), (int) (chargerPVC.getY() + chargerPVC.getHeight()/2 + 10));
		g.drawString("Quitter", (int) (quitter.getX()+quitter.getWidth()/3), (int) (quitter.getY()+quitter.getHeight()/2 + 10));
		
	}

	public Image getFondEcran() {
		return fondEcran;
	}

	public Rectangle getChargerPVC() {
		return chargerPVC;
	}

	public Rectangle getQuitter() {
		return quitter;
	}

}
