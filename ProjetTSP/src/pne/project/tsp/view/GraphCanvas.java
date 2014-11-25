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

		// Dessin des aretes
		if (isResolved) {
			for (int i = 0; i < listNode.size() - 1; i++) {
				g.setColor(Color.GRAY); // couleur des aretes
				g.drawLine((int) listNode.get(i).getCircle().getCenterX(), (int) listNode.get(i).getCircle()
						.getCenterY(), (int) listNode.get(tabResult[i]).getCircle().getCenterX(),
						(int) listNode.get(tabResult[i]).getCircle().getCenterY());
			}
		} else {
			for (NodeView node : listNode) {
				for (NodeView nodeJ : listNode) {
					g.setColor(Color.GRAY); // couleur des aretes
					if (!(node.equals(nodeJ))) {
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
				dec = 6;
			}
			else if(node.getID()<1000){
				dec = (int) (node.getCircle().getWidth()-8);
			}
			else{
				dec = (int) (node.getCircle().getWidth()-4);
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
