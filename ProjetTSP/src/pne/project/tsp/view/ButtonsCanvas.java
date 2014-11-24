package pne.project.tsp.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ButtonsCanvas extends Component {
	private Rectangle solve_or_startMenu;
	private Rectangle quit;
	private boolean isResolved;
	
	public ButtonsCanvas(int w, int h){
		int var = 10;
		// rect = (x, y, largeur, hauteur)
		solve_or_startMenu = new Rectangle(var, var+h/6, (w/2)-var, (h/6)-var);
		quit = new Rectangle(var+(w/2), var+h/6, (w/2)-var, (h/6)-var);
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
		g.setBackground(Color.BLUE);
		
		g.setColor(Color.BLACK);
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
		g.drawString(msg, solve_or_startMenu.x, solve_or_startMenu.y);
		
		// dessiner le bouton quit
		g.drawString("Quitter", quit.x, quit.y);
	}
	

}
