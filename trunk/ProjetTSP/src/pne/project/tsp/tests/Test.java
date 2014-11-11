package pne.project.tsp.tests;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.managers.GraphManager;
import pne.project.tsp.utils.BoundsGraph;
import pne.project.tsp.utils.FileReader;
import pne.project.tsp.view.GraphView;

public class Test {

	public static void main(String[] args) {
//		BoundsGraph boundsGraph = new BoundsGraph();
		Graph g1 = FileReader.buildGraphFromXml("data/XML/ulysses16.xml");
		
		/**
		 * On pourrait enregistrer le resultat dans un dossier a la meme hauteur que data
		 * pour que ca fonctionne chez tout le monde
		 */
		GraphManager.writeLinearProgram(g1, "C:/Users/Julien/Desktop/results.txt");
//		double[][] nodePositions = FileReader.getPositionsFromTsp(
//				"data/TSP/ulysses16.tsp", boundsGraph);
//
//		GraphView gv = new GraphView(nodePositions, boundsGraph, g1);
//		gv.printGraph();
	}

}
