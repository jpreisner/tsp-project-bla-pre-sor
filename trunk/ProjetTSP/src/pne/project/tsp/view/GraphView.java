package pne.project.tsp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.utils.BoundsGraph;

public class GraphView {

	private JFrame frame;
	private TabView tv;
	// private GraphCanvas gc;
	private static final int frameHeight = 600;
	private static final int frameWidth = 850;
	private static double[][] tmp;
	private static BoundsGraph boundsGraph;
	private static Graph graph;

	public GraphView(double[][] nodePositions, BoundsGraph boundsGraph, Graph graph) {
		GraphView.tmp = nodePositions;
		GraphView.boundsGraph = boundsGraph;
		GraphView.graph = graph;
	}

	public void printGraph() {
		final JFrame frame = new JFrame("TSP");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the frame
		frame.setSize(new Dimension(frameWidth, frameHeight));
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		final Panel panel = new Panel();
		panel.setSize(frameWidth, frameHeight);
		panel.setVisible(true);
		frame.setContentPane(panel);
		// frame.pack();
		frame.setVisible(true);
		frame.repaint();

	}

	public static class Panel extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(final Graphics g) {
			double ratioHeight = (boundsGraph.getyMax() - boundsGraph.getyMin())  / (double) frameHeight;
			double ratiowidth = (boundsGraph.getxMax() - boundsGraph.getxMin())	/ (double) frameWidth;
		
			// EDGES PRINTING
			g.setColor(Color.gray);
			for (int i = 0; i < graph.getNbNode(); i++) {
				for (int j = 0; j < graph.getNbNode(); j++) {
					if (i != j && graph.getTabAdja()[i][j] != 0) {
						g.drawLine((int)( tmp[j][0] * ratiowidth), (int)( tmp[j][1] * ratioHeight),
								   (int)( tmp[i][0] * ratiowidth), (int)( tmp[i][1]	* ratioHeight));
						// System.out.println(tmp[j][0] + " " +
						// tmp[j][1]+", "+tmp[i][0] + " " + tmp[i][1]);
					}
				}
			}
			
			// NODE PRINTING
			g.setColor(Color.RED);
			for (int j = 0; j < tmp.length; j++) {
				g.fillRect((int)( tmp[j][0] * ratiowidth), (int)( tmp[j][1] * ratioHeight), 5, 5);
				 System.out.println(tmp[j][0] + " " + tmp[j][1]);
			}
		}
	}
}
