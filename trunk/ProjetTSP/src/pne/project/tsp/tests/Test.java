package pne.project.tsp.tests;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.controler.MainControler;
import pne.project.tsp.managers.GraphManager;
import pne.project.tsp.utils.FileReader;
import pne.project.tsp.view.MainView;
import ps.project.tsp.vns.SolutionVNS;

public class Test {
	public static void main(String[] args) {
		//System.out.println("TEST");
		int aleas = 100;
		int nbScenario = 3;
		int Kmax = 4;
		double tmax = 100;	// on le d�fini comment?
		Graph g = FileReader.buildGraphFromXml("data/XML/" + "a280" + ".xml");
		GraphManager gm = new GraphManager();
		
		// Attention : k doit �tre inf�rieur � nbNoeud/2
		SolutionVNS sol = gm.resolutionTSP_vns(g, aleas, nbScenario, Kmax, tmax);
		
		System.out.println("sol finale = " + sol.getPathChosen());
		System.out.println("cout = " + sol.getPathCost());
		System.out.println("temps = "+ gm.getResolutionDuration());
		
//		MainControler c = new MainControler(new MainView("Probl�me du voyageur de commerce", 1000, 600));
	}
}