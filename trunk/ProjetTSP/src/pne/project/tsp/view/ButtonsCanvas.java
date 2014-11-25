package pne.project.tsp.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class ButtonsCanvas extends Component {
	private Rectangle solve_or_startMenu;
	private Rectangle quit;
	private boolean isResolved;
	private Image fondEcran;

	public ButtonsCanvas(int w, int h, boolean isResolved) {
		ImageIcon image = new ImageIcon("Images/Main.jpg");
		fondEcran = image.getImage();
		int x = 45;
		int y = 40;
		int largeur = (w / 2) - 100;
		int hauteur = (h / 8) - 5;

		// rect = (x, y, largeur, hauteur)
		solve_or_startMenu = new Rectangle(x, y, largeur, hauteur);
		quit = new Rectangle(w - x - largeur, y, largeur, hauteur);
		this.isResolved = isResolved;
	}

	public Rectangle getSolve_or_startMenu() {
		return solve_or_startMenu;
	}

	public Rectangle getQuit() {
		return quit;
	}

	public boolean getIsResolved() {
		return isResolved;
	}

	public void setIsResolved(boolean b) {
		isResolved = b;
	}

	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;

		g.drawImage(fondEcran, 0, 0, getWidth(), getHeight(), this);
		
		g.setColor(new Color (220, 220, 220));
		g.fill(solve_or_startMenu);
		g.fill(quit);

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(4));
		g.draw(solve_or_startMenu);
		g.draw(quit);

		String msg;
		int val;
		if (isResolved) {
			// dessiner le bouton startMenu
			msg = "Recommencer";
			val = 60;
		} else {
			// dessiner le bouton solve
			msg = "Résoudre";
			val = 15;
		}

		g.setColor(Color.BLACK);
		g.setFont(new Font("Dialog", Font.BOLD, 35));
		g.drawString(msg, solve_or_startMenu.x + solve_or_startMenu.width / 3 - val, solve_or_startMenu.y
				+ solve_or_startMenu.height / 2 + 10);

		// dessiner le bouton quit
		g.drawString("Quitter", quit.x + quit.width / 3 + 10, quit.y + quit.height / 2 + 10);
	}

}
