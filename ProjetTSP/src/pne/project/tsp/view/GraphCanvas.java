package pne.project.tsp.view;

import java.awt.Canvas;
import java.awt.Graphics;
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
	public void paint(Graphics g) {
		// Dessiner tous les elements
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
