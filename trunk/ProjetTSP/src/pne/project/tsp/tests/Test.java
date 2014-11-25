package pne.project.tsp.tests;

import pne.project.tsp.controler.MainControler;
import pne.project.tsp.view.MainView;

public class Test {
	public static void main(String[] args) {
		
		MainControler c = new MainControler(new MainView("Problème du voyageur de commerce", 1000, 600));

		
		/*

		String filename = "gr17";
		Graph g1 = FileReader.buildGraphFromXml("data/XML/" + filename + ".xml");
		GraphManager gm = new GraphManager();
		int[] tabResult = gm.writeLinearProgram(g1, "tests/lpex1.lp", "tests/results.txt");
		int cpt = 0;
		int n = tabResult.length;
		int i = 0;

		while (cpt < n) {
			cpt++;
			System.out.print("(" + i + ", " + tabResult[i] + ") - ");
			i = tabResult[i];
		}
		
		*/
	}
}