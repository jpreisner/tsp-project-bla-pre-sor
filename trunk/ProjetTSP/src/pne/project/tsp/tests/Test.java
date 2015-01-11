package pne.project.tsp.tests;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.managers.GraphManager;
import pne.project.tsp.utils.FileReader;
import ps.project.tsp.vns.SolutionVNS;

public class Test {
	public static void main(String[] args) {
		System.out.println("TEST");
		int aleas = 10;
		int nbScenario = 3;
		int Kmax = 4;
		double tmax = 100;	// on le défini comment?
		Graph g = FileReader.buildGraphFromXml("data/XML/" + "gr17" + ".xml");
		GraphManager gm = new GraphManager();
		
		// Attention : k doit être inférieur à nbNoeud/2
		SolutionVNS sol = gm.resolutionTSP_vns(g, aleas, nbScenario, Kmax, tmax);
		
		System.out.println("sol finale = " + sol.getPathChosen());
		System.out.println("cout = " + sol.getPathCost());
		
		//MainControler c = new MainControler(new MainView("Problème du voyageur de commerce", 1000, 600));
	}
}