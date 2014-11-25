package pne.project.tsp.view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.utils.BoundsGraph;


public class GraphView {
	private JFrame frame;
	private GraphCanvas canvas;
	private TabView adjaMatrix;

	/**
	 * Constructor
	 * 
	 * @param titre
	 * @param width
	 * @param height
	 * @param bg
	 */

	// ce que je dois faire: passer en parametre le graphe pour dessiner le
	// graphe
	public GraphView(String titre, int width, int height, double[][] nodePositions, BoundsGraph bg, boolean isResolved,
			double[][] tabResult) {
		frame = new JFrame(titre);
		frame.setSize(width, height);

		ArrayList<NodeView> listNode = new ArrayList<NodeView>();
		int i;
		int d = 18;
		Color fill = Color.RED;
		Color draw = Color.RED;
		Color text = Color.BLACK;

		double ratioX = (bg.getxMax() - bg.getxMin()) / width;
		double ratioY = (bg.getyMax() - bg.getyMin()) / height;

		for (i = 0; i < nodePositions.length; i++) {
			listNode.add(new NodeView((nodePositions[i][0] - bg.getxMin()) / (ratioX * 1.1), (nodePositions[i][1] - bg
					.getyMin()) / (ratioY * 1.1), d, i, fill, draw, text));
		}
		canvas.setBackground(Color.WHITE);
		frame.add(canvas);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}