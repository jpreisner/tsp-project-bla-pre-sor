package pne.project.tsp.view;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * 
 * Faire le truc d'IG pour les dessins
 *
 */

public class GraphView {
	private JFrame frame;
	private GraphCanvas canvas;
	
	/**
	 * Constructor
	 * @param titre
	 * @param width
	 * @param height
	 */
	
	// ce que je dois faire: passer en parametre le graphe pour dessiner le graphe
	public GraphView(String titre, int width, int height){
		frame = new JFrame(titre);
		frame.setSize(width, height);
		
		// A enlever : c'est juste pour tester
		ArrayList <NodeView> listNode = new ArrayList<NodeView>();
		int i;
		int x=10;
		int y=10;
		int d=50;
		Color fill = Color.RED;
		Color draw = Color.BLACK;
		Color text = Color.WHITE;
		for(i=0; i<5; i++){
			listNode.add(new NodeView((x*i+d*i)*((i+1)%2), (y*i+d*i)*(i%2), d, i, fill, draw, text));	// j'ai fais au hasard
		}
		// fin de ce qu'il faut enlever
		
		canvas = new GraphCanvas(false, listNode);
		canvas.setBackground(Color.WHITE) ;
		frame.add(canvas) ;
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		GraphView view = new GraphView("Affichage grille", 800, 600);
	}
	
}


	
	
	
	
	
	/**
	 *  A revoir -> normalement c'est dans canvas qu'on dessine
	 

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
	
	*/
