package pne.project.tsp.view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.utils.BoundsGraph;
import pne.project.tsp.utils.FileReader;

/**
 * 
 * Faire le truc d'IG pour les dessins
 *
 */

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
	public GraphView(String titre, int width, int height,
			double[][] nodePositions, BoundsGraph bg, boolean isResolved, double[][] tabResult) {
		frame = new JFrame(titre);
		frame.setSize(width, height);

		// A enlever : c'est juste pour tester
		ArrayList<NodeView> listNode = new ArrayList<NodeView>();
		int i;
		int d = 18;
		Color fill = Color.RED;
		Color draw = Color.RED;
		Color text = Color.BLACK;
		
		double ratioX = (bg.getxMax()-bg.getxMin())/width;
		double ratioY = (bg.getyMax()-bg.getyMin())/height;
		
		for (i = 0; i < nodePositions.length; i++) {
			listNode.add(new NodeView((nodePositions[i][0]-bg.getxMin()) / (ratioX*1.1), 
					(nodePositions[i][1]-bg.getyMin())/(ratioY*1.1),
					d, i, fill, draw, text));
		}
		// fin de ce qu'il faut enlever

		// a mettre pour de vrai :
		// listNode = displayGraph(graph);

//		canvas = new GraphCanvas(isResolved, listNode, tabResult);
		canvas.setBackground(Color.WHITE);
		frame.add(canvas);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public ArrayList<NodeView> displayGraph(Graph graph) {
		ArrayList<NodeView> listNode = new ArrayList<NodeView>();

		// a changer
		int d = 50;
		Color fill = Color.RED;
		Color draw = Color.BLACK;
		Color text = Color.WHITE;

		// Placer les noeuds --> trouver la formule ou librairie
		// JGraph :
		// http://www.zdnet.fr/actualites/dessiner-facilement-des-graphes-en-java-avec-jgraph-39115715.htm
		// http://cyberzoide.developpez.com/graphviz/
		// http://graphstream-project.org/

		return listNode;
	}

}

/**
 * A revoir -> normalement c'est dans canvas qu'on dessine
 * 
 * 
 * private JFrame frame; private TabView tv; // private GraphCanvas gc; private
 * static final int frameHeight = 600; private static final int frameWidth =
 * 850; private static double[][] tmp; private static BoundsGraph boundsGraph;
 * private static Graph graph;
 * 
 * public GraphView(double[][] nodePositions, BoundsGraph boundsGraph, Graph
 * graph) { GraphView.tmp = nodePositions; GraphView.boundsGraph = boundsGraph;
 * GraphView.graph = graph; }
 * 
 * public void printGraph() { final JFrame frame = new JFrame("TSP");
 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * 
 * // Display the frame frame.setSize(new Dimension(frameWidth, frameHeight));
 * frame.setResizable(true); frame.setVisible(true);
 * frame.setLocationRelativeTo(null);
 * 
 * final Panel panel = new Panel(); panel.setSize(frameWidth, frameHeight);
 * panel.setVisible(true); frame.setContentPane(panel); // frame.pack();
 * frame.setVisible(true); frame.repaint();
 * 
 * }
 * 
 * public static class Panel extends JPanel { private static final long
 * serialVersionUID = 1L;
 * 
 * @Override public void paintComponent(final Graphics g) { double ratioHeight =
 *           (boundsGraph.getyMax() - boundsGraph.getyMin()) / (double)
 *           frameHeight; double ratiowidth = (boundsGraph.getxMax() -
 *           boundsGraph.getxMin()) / (double) frameWidth;
 * 
 *           // EDGES PRINTING g.setColor(Color.gray); for (int i = 0; i <
 *           graph.getNbNode(); i++) { for (int j = 0; j < graph.getNbNode();
 *           j++) { if (i != j && graph.getTabAdja()[i][j] != 0) {
 *           g.drawLine((int)( tmp[j][0] * ratiowidth), (int)( tmp[j][1] *
 *           ratioHeight), (int)( tmp[i][0] * ratiowidth), (int)( tmp[i][1] *
 *           ratioHeight)); // System.out.println(tmp[j][0] + " " + //
 *           tmp[j][1]+", "+tmp[i][0] + " " + tmp[i][1]); } } }
 * 
 *           // NODE PRINTING g.setColor(Color.RED); for (int j = 0; j <
 *           tmp.length; j++) { g.fillRect((int)( tmp[j][0] * ratiowidth),
 *           (int)( tmp[j][1] * ratioHeight), 5, 5);
 *           System.out.println(tmp[j][0] + " " + tmp[j][1]); } } }
 */
