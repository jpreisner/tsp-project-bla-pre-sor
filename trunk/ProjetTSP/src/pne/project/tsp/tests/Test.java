package pne.project.tsp.tests;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.controler.MainControler;
import pne.project.tsp.managers.GraphManager;
import pne.project.tsp.utils.FileReader;
import pne.project.tsp.view.MainView;


public class Test {
	public static void main(String[] args) {
		System.out.println("TEST");
		int aleas = 90;
		int nbScenario = 1;
		int Kmax = 4;
		Graph g = FileReader.buildGraphFromXml("data/XML/" + "gr17" + ".xml");
		GraphManager gm = new GraphManager();
		int k = 5;
		// Attention : k doit être inférieur à nbNoeud/2
		gm.resolutionTSP_vns(g, aleas, nbScenario, 5);
		
		//MainControler c = new MainControler(new MainView("Problème du voyageur de commerce", 1000, 600));

		// ctrl shift c
//		System.out.println("test");
//		String filename = "test6";
//		Graph g1 = FileReader.buildGraphFromXml("data/XML/" + filename + ".xml");
//		GraphManager gm = new GraphManager();
//		int[] tabResult = gm.writeLinearProgram(g1, "tests/lpex1.lp", "tests/results.txt");
//		int cpt = 0;
//		int n = tabResult.length;
//		int i = 0;
//
//		while (cpt < n) {
//			cpt++;
//			System.out.print("(" + i + ", " + tabResult[i] + ") - ");
//			i = tabResult[i];
//		}
//		
		
	}
}