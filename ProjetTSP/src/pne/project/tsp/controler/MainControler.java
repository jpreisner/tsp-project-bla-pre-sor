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
import pne.project.tsp.view.GraphView;
import pne.project.tsp.view.MainView;
import pne.project.tsp.view.NodeView;

public class MainControler {

	private MainView mv;

	public MainControler(MainView mv) {
		this.mv = mv;
		mv.getMainCanvas().addMouseListener(menuPrincipal);
	}
	
	public MouseListener menuPrincipal = new MouseListener() {
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Point p = new Point(arg0.getX(), arg0.getY());
			
			if(mv.getMainCanvas().getChargerPVC().contains(p)){
				// appeler methode chargerPVC
//				System.out.println("Gignac souhaite charger un PVC");
				//mv.dispose();
				Choix_fichier jf = new Choix_fichier("data/XML");
				File fileSelected = jf.selectFile();
				System.out.println("Le fichier selected est = " + fileSelected.getName());
				
				
				Graph g1 = FileReader.buildGraphFromXml("data/XML/"+fileSelected.getName());
				//double[][] tabResult = GraphManager.writeLinearProgram(g1, "tests/lpex1.lp", "tests/results.txt");
				
				String filename = fileSelected.getName().substring(0, (int) (fileSelected.getName().length()-4));
				System.out.println(filename);
				int w = mv.getWidth();
				int h = mv.getHeight();
				
				BoundsGraph bg = new BoundsGraph();
				ArrayList<NodeView> listNode = new ArrayList<NodeView>();
				int i;
				int d = 18;
				Color fill = Color.RED;
				Color draw = Color.RED;
				Color text = Color.BLACK;
				
				double ratioX = (bg.getxMax()-bg.getxMin())/mv.getWidth();
				double ratioY = (bg.getyMax()-bg.getyMin())/mv.getHeight();
				
				double[][] nodePositions = FileReader.getPositionsFromTsp("data/TSP/"+filename+".tsp", bg);
				
				for (i = 0; i < nodePositions.length; i++) {
					listNode.add(new NodeView((nodePositions[i][0]-bg.getxMin()) / (ratioX*1.1), 
							(nodePositions[i][1]-bg.getyMin())/(ratioY*1.1),
							d, i, fill, draw, text));
				}
				double[][] tabResult = null;
				mv.graphView(w, h, g1, false, listNode, tabResult);
				
				/*BoundsGraph bg = new BoundsGraph();
				double[][] nodePositions = FileReader.getPositionsFromTsp("data/TSP/"+filename+".tsp", bg);
				//GraphView view = new GraphView("Affichage Graph", 800, 600, tmp, bg,true, tabResult);
				
				
				
				// A enlever : c'est juste pour tester
				
				*/
				
			}
			else if(mv.getMainCanvas().getQuitter().contains(p)){
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
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	};

	public MainView getMv() {
		return mv;
	}

	public void setMv(MainView mv) {
		this.mv = mv;
	}
	
	public static void main (String[] args){
		MainControler c = new MainControler(new MainView("View", 800, 600));
		//c.getMv().setVisible(true);
	}
}
