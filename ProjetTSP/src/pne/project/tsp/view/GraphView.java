package pne.project.tsp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.utils.BoundsGraph;

public class GraphView {

	// private GraphCanvas gc;
	private static final int frameHeight = 600;
	private static final int frameWidth = 850;
	private static int[][] tmp;
	private static BoundsGraph boundsGraph;
	private static Graph graph;

	public GraphView(int[][] nodePositions, BoundsGraph boundsGraph, Graph graph) {
		GraphView.tmp = nodePositions;
		GraphView.boundsGraph = boundsGraph;
		GraphView.graph = graph;
	}

	public void buildCardLayout() {
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

			double ratioHeight = boundsGraph.getyMax() / (double) frameHeight;
			double ratiowidth = (double) boundsGraph.getxMax()
					/ (double) frameWidth;

			/* EDGES PRINTING */
			g.setColor(Color.gray);
			for (int i = 0; i < graph.getNbNode(); i++) {
				for (int j = 0; j < graph.getNbNode(); j++) {
					if (i != j && graph.getTabAdja()[i][j] != 0) {
						g.drawLine(tmp[j][0] / (int) ratiowidth, tmp[j][1]
								/ (int) ratioHeight, tmp[i][0]
								/ (int) ratiowidth, tmp[i][1]
								/ (int) ratioHeight);
						// System.out.println(tmp[j][0] + " " +
						// tmp[j][1]+", "+tmp[i][0] + " " + tmp[i][1]);

					}
				}
			}
			
			/* NODE PRINTING */
			g.setColor(Color.RED);
			for (int j = 0; j < tmp.length; j++) {
				g.fillRect(tmp[j][0] / (int) ratiowidth, tmp[j][1]
						/ (int) ratioHeight, 5, 5);
				// System.out.println(tmp[j][0] + " " + tmp[j][1]);
			}

		}

	}
}
