package pne.project.tsp.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ButtonsCanvas extends Component {
	private Rectangle solve_or_startMenu;
	private Rectangle quit;
	private boolean isResolved;
	
	public ButtonsCanvas(int w, int h){
		int x = 45;
		int y = 40;
		int largeur = (w/2)-100;
		int hauteur = (h/8)-5;
		
		// rect = (x, y, largeur, hauteur)
		solve_or_startMenu = new Rectangle(x, y, largeur, hauteur);
		quit = new Rectangle(w-x-largeur, y, largeur, hauteur);
		isResolved = false;
	}
	
	public boolean getIsResolved(){
		return isResolved;
	}
	
	public void setIsResolved(boolean b){
		isResolved = b;
	}
	
	public void paint(Graphics graphics){
		Graphics2D g = (Graphics2D) graphics;
		
		g.setColor(Color.WHITE);
		g.fill(solve_or_startMenu);
		g.fill(quit);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(4));
		g.draw(solve_or_startMenu);
		g.draw(quit);
		
		String msg;
		if(isResolved){
			// dessiner le bouton startMenu
			msg = "Recommencer";
		}
		else{
			// dessiner le bouton solve
			msg = "Résoudre";
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Dialog", Font.BOLD, 35));
		g.drawString(msg, solve_or_startMenu.x+solve_or_startMenu.width/3-15, solve_or_startMenu.y+solve_or_startMenu.height/2+10);
		
		// dessiner le bouton quit
		g.drawString("Quitter", quit.x+quit.width/3+10, quit.y+quit.height/2+10);
	}
	

}
