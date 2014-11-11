package pne.project.tsp.view;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JButton;

public class GraphCanvas extends Canvas{

	private boolean isCreateMode;
	private JButton solve;
	private JButton end;
	private JButton quit;
	private ArrayList <NodeView> listNode;
	
	public GraphCanvas(boolean isCreateMode, ArrayList <NodeView> listNode) {
		this.isCreateMode = isCreateMode;
		this.listNode = listNode;
		
		// Créer les 3 boutons
		
	}
	
	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D)graphics ;
		
		// Dessiner tous les elements
		
		// Dessin des noeuds present dans listNode
		for(NodeView node : listNode){
			// contour
			g.setColor(node.getColorDraw());
			g.draw(node.getCircle());
			
			// remplissage
			g.setColor(node.getColorFill());
			g.fill(node.getCircle());
			
			// numéro du noeud
			g.setColor(node.getColorText());
			// pb : drawString prend que des float, alors qu'on a des double
			g.drawString(Integer.toString(node.getNumber()), (float) node.getCircle().getCenterX(), (float) node.getCircle().getCenterY());
	
			// Dessin des aretes
			for(NodeView nodeJ : listNode){
				g.setColor(Color.GRAY);	// couleur des aretes
				if(!(node.equals(nodeJ))){
					// pb : drawLine prend que des int, alors qu'on a des double
					g.drawLine((int) node.getCircle().getCenterX(), (int) node.getCircle().getCenterY(), (int) nodeJ.getCircle().getCenterX(), (int) nodeJ.getCircle().getCenterY());
				}
			}
		}
	}

	public boolean getIsCreateMode() {
		return isCreateMode;
	}

	public void setIsCreateMode(boolean isCreateMode) {
		this.isCreateMode = isCreateMode;
	}

	public JButton getSolve() {
		return solve;
	}

	public void setSolve(JButton solve) {
		this.solve = solve;
	}

	public JButton getEnd() {
		return end;
	}

	public void setEnd(JButton end) {
		this.end = end;
	}

	public JButton getQuit() {
		return quit;
	}

	public void setQuit(JButton quit) {
		this.quit = quit;
	}

	public ArrayList<NodeView> getListNode() {
		return listNode;
	}

	public void setListNode(ArrayList<NodeView> listNode) {
		this.listNode = listNode;
	}
	
}
