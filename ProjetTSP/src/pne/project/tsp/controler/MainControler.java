package pne.project.tsp.controler;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.managers.GraphManager;
import pne.project.tsp.utils.BoundsGraph;
import pne.project.tsp.utils.FileReader;
import pne.project.tsp.view.Choix_fichier;
import pne.project.tsp.view.MainView;
import pne.project.tsp.view.NodeView;

public class MainControler {

	private MainView mv;
	private boolean solved;
	private Graph g;

	public MainControler(MainView mv) {
		this.mv = mv;
		init();
	}
	
	/**
	 * Listener pour le menu principal
	 */
	public MouseListener menuPrincipal = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Point p = new Point(arg0.getX(), arg0.getY());
			System.out.println("clicked");
			if (mv.getMainCanvas().getChargerPVC().contains(p)) {
				// recupération du xml
				String filename = getXML();
				if(filename != null){
					ArrayList<NodeView> listNode = affichageGraphe(filename);
					g = FileReader.buildGraphFromXml("data/XML/" + filename + ".xml");
					// construction de la vue initiale
					mv.graphView(mv.getWidth(), mv.getHeight(), g, solved, listNode, null, 0,0);
					mv.getButtonCanvas().addMouseListener(graphButtons);	
				}
			}

			else if (mv.getMainCanvas().getQuitter().contains(p)) {
				mv.dispose();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			Point p = new Point(arg0.getX(), arg0.getY());
			if (mv.getMainCanvas().getChargerPVC().contains(p)) {
				mv.getMainCanvas().setColorChargerPVC(new Color(200, 200, 200));
				mv.repaint();
			}
			else if(mv.getMainCanvas().getQuitter().contains(p)) {
				mv.getMainCanvas().setColorQuitter(new Color(200, 200, 200));
				mv.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			Point p = new Point(arg0.getX(), arg0.getY());
			if (mv.getMainCanvas().getChargerPVC().contains(p)) {
				mv.getMainCanvas().setColorChargerPVC(new Color(220, 220, 220));
				mv.repaint();
			}
			else if(mv.getMainCanvas().getQuitter().contains(p)) {
				mv.getMainCanvas().setColorQuitter(new Color(220, 220, 220));
				mv.repaint();
			}
		}
	};
	

	/**
	 * Listener pour la partie "graphe"
	 */
	public MouseListener graphButtons = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Point p = new Point(arg0.getX(), arg0.getY());

			if (mv.getButtonCanvas().getQuit().contains(p)) {
				mv.getMainCanvas().setColorQuitter(new Color(200, 200, 200));
				mv.repaint();
				mv.dispose();
			} else if (mv.getButtonCanvas().getSolve_or_startMenu().contains(p)) {
				mv.getContentPane().removeAll();

				// cas ou le graphe n'est pas resolu
				if (!solved) {
					solved = true;
					// lancer la résolution
					GraphManager gm = new GraphManager();
					int[] tabResult = gm.writeLinearProgram(g, "tests/lpex1.lp", "tests/results.txt");
					// passer le resultat dans la fonction d'affichage
					mv.getGraphCanvas().setTabResult(tabResult);
					mv.getGraphCanvas().setIsResolved(true);
					mv.getGraphCanvas().repaint();
					mv.getButtonCanvas().setIsResolved(true);
					mv.graphView(mv.getWidth(), mv.getHeight(), g, solved, mv.getGraphCanvas().getListNode(),
							tabResult, gm.getSolutionValue(), gm.getResolutionDuration());
					System.out.println("terminé");
					mv.getButtonCanvas().addMouseListener(graphButtons);

				}
				// cas ou le graphe a ete resolu
				else {
					mv.getContentPane().removeAll();
					mv.menuPrincipal(mv.getWidth(), mv.getHeight());
					init();
				}

			}

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Point p = new Point(arg0.getX(), arg0.getY());
			if (mv.getButtonCanvas().getSolve_or_startMenu().contains(p)) {
				mv.getButtonCanvas().setColorSolve_startMenu(new Color(200, 200, 200));
				mv.repaint();
			}
			else if(mv.getButtonCanvas().getQuit().contains(p)) {
				mv.getButtonCanvas().setColorQuit(new Color(200, 200, 200));
				mv.repaint();
			}
			

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			Point p = new Point(arg0.getX(), arg0.getY());
			if (mv.getButtonCanvas().getSolve_or_startMenu().contains(p)) {
				mv.getButtonCanvas().setColorSolve_startMenu(new Color(220, 220, 220));
				mv.repaint();
			}
			else if(mv.getButtonCanvas().getQuit().contains(p)) {
				mv.getButtonCanvas().setColorQuit(new Color(220, 220, 220));
				mv.repaint();
			}

		}

	};

	
	
	/**
	 *  Methodes auxiliaires
	 */
	
	public void init() {
		mv.getMainCanvas().addMouseListener(menuPrincipal);
		solved = false;
		g = null;
	}

	public void addListenerButtons() {
		mv.getButtonCanvas().addMouseListener(graphButtons);
	}

	private String getXML() {
		Choix_fichier jf = new Choix_fichier("data/XML");
		File fileSelected = jf.selectFile();
		if(fileSelected == null)	return null;
		return fileSelected.getName().substring(0, (int) (fileSelected.getName().length() - 4));
	}

	private ArrayList<NodeView> affichageGraphe(String filename) {

		// TODO Auto-generated method stub
		/* Affichage du graphe */
		int w = mv.getWidth();
		int h = mv.getHeight();

		BoundsGraph bg = new BoundsGraph();
		ArrayList<NodeView> listNode = new ArrayList<NodeView>();
		int i;
		int d = 18;
		Color fill = Color.RED;
		Color draw = Color.RED;
		Color text = Color.BLACK;

		/* initialisation des bornes du graphe */
		double[][] nodePositions = FileReader.getPositionsFromTsp("data/TSP/" + filename + ".tsp", bg);

		double ratioX = (bg.getxMax() - bg.getxMin()) / (double) (w / 2);
		double ratioY = (bg.getyMax() - bg.getyMin()) / (double) (h / 1.5);

		/* affectation des positions aux noeuds */
		for (i = 0; i < nodePositions.length; i++) {
			listNode.add(new NodeView((nodePositions[i][0] - bg.getxMin()) / (ratioX * 1.2), (nodePositions[i][1] - bg
					.getyMin()) / (ratioY * 1.2), d, i, fill, draw, text));

		}
		return listNode;

	}

	public MainView getMv() {
		return mv;
	}

	public void setMv(MainView mv) {
		this.mv = mv;
	}
}
