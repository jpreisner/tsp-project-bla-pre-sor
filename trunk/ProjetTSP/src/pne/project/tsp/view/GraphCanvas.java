package pne.project.tsp.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GraphCanvas extends Component {
	private boolean isResolved;
	private ArrayList<NodeView> listNode;
	private int[] tabResult;

	public GraphCanvas(boolean isResolved, ArrayList<NodeView> listNode, int[] tabResult) {
		this.isResolved = isResolved;
		this.listNode = listNode;
		this.tabResult = tabResult;
	}

	@Override
	public void paint(Graphics graphics) {

		Graphics2D g = (Graphics2D) graphics;

		// Dessiner tous les elements

		// NodeView a = new NodeView(10, 10, 50, 10, Color.blue, Color.blue,
		// Color.blue);
		// g.setColor(Color.BLACK);
		// g.draw(a .getCircle());
		// g.draw(new Rectangle(0, 0, 490, 450));

		// Dessin des aretes
		if (isResolved) {
			System.out.println("Dans GraphCanvas-paint(), on rentre dans isResolved");
			for (int i = 0; i < listNode.size() - 1; i++) {

				g.setColor(Color.GRAY); // couleur des aretes

				// pb : drawLine prend que des int, alors qu'on a des double
				g.drawLine((int) listNode.get(i).getCircle().getCenterX(), (int) listNode.get(i).getCircle()
						.getCenterY(), (int) listNode.get(tabResult[i]).getCircle().getCenterX(),
						(int) listNode.get(tabResult[i]).getCircle().getCenterY());

			}
		} else {
			for (NodeView node : listNode) {
				for (NodeView nodeJ : listNode) {
					g.setColor(Color.GRAY); // couleur des aretes
					if (!(node.equals(nodeJ))) {
						// pb : drawLine prend que des int, alors qu'on a des
						// double
						g.drawLine((int) node.getCircle().getCenterX(), (int) node.getCircle().getCenterY(),
								(int) nodeJ.getCircle().getCenterX(), (int) nodeJ.getCircle().getCenterY());
					}
				}
			}
		}

		// Dessin des noeuds present dans listNode
		for (NodeView node : listNode) {
			// contour
			g.setColor(node.getColorDraw());
			g.draw(node.getCircle());

			// remplissage
			g.setColor(node.getColorFill());
			g.fill(node.getCircle());

			// numéro du noeud
			g.setColor(node.getColorText());
			g.setStroke(new BasicStroke(1));

			int dec;
			if(node.getID()<10){
				dec = 2;
			}
			else if(node.getID()<100){
				dec = 5;
			}
			else if(node.getID()<1000){
				dec = 3;
			}
			else{
				dec = 4;
			}
			g.drawString(Integer.toString(node.getID()), (float) node.getCircle().getCenterX()- dec, (float) node
					.getCircle().getCenterY() + 3);
		}
	}

	public boolean getIsResolved() {
		return isResolved;
	}

	public void setIsResolved(boolean isResolved) {
		this.isResolved = isResolved;
	}

	public ArrayList<NodeView> getListNode() {
		return listNode;
	}

	public void setListNode(ArrayList<NodeView> listNode) {
		this.listNode = listNode;
	}

	public int[] getTabResult() {
		return tabResult;
	}

	public void setTabResult(int[] tabResult) {
		this.tabResult = tabResult;
	}

}
