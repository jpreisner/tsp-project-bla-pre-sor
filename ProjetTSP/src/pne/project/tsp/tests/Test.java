package pne.project.tsp.tests;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.managers.GraphManager;
import pne.project.tsp.utils.BoundsGraph;
import pne.project.tsp.utils.FileReader;
import pne.project.tsp.view.GraphView;
import pne.project.tsp.view.TabView;

public class Test {
	public static void main(String[] args) {

		String filename = "gr17";
		Graph g1 = FileReader
				.buildGraphFromXml("data/XML/" + filename + ".xml");
		int[] tabResult = GraphManager.writeLinearProgram(g1, "tests/lpex1.lp",
				"tests/results.txt");
		int cpt = 0;
		int n = tabResult.length;
		int i = 0;

		while (cpt < n) {
			cpt++;
			System.out.print("(" + i + ", " + tabResult[i] + ") - ");
			i = tabResult[i];
		}
	}
}
	/* TSP main */
	// public static void main(String[] args) {
	// Graph g1 = FileReader.
	// buildGraphFromXml("data/XML/gr17.xml");
	//
	// GraphManager.writeLinearProgram(g1,"tests/lpex1.lp",
	// "tests/results.txt");
	// }

	/* JTable main */
	// public static void main(String[] args) {
	// JFrame jf = new JFrame();
	// Graph g1 = FileReader.buildGraphFromXml("data/XML/att48.xml");
	//
	// JPanel panel = new JPanel();
	// panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	// TabView tab = new TabView(g1);
	//
	// panel.add(tab.getTableHeader(), BorderLayout.WEST);
	// panel.add(tab , BorderLayout.LINE_END);
	//
	// JScrollPane sc = new JScrollPane(panel,
	// JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	// JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	//
	// jf.add(sc);
	// jf.setVisible(true);
	// jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// jf.pack();
	// jf.setBounds(500, 500, 500, 500);
	// jf.setLocationRelativeTo(null);
	//
	// }

	/* display graph main */
//	public static void main(String[] args) {
//
//		String filename = "kroB100";
//		Graph g1 = FileReader
//				.buildGraphFromXml("data/XML/" + filename + ".xml");
//		int[] tabResult = GraphManager.writeLinearProgram(g1, "tests/lpex1.lp",
//				"tests/results.txt");
//		int cpt = 0;
//		int n = tabResult.length;
//		int i = 0;
//
//		while (cpt < n) {
//			cpt++;
//			System.out.print("(" + i + ", " + tabResult[i] + ") - ");
//			i = tabResult[i];
//		}

		// BoundsGraph bg = new BoundsGraph();
		// double[][] tmp =
		// FileReader.getPositionsFromTsp("data/TSP/"+filename+".tsp", bg);
		// GraphView view = new GraphView("Affichage Graph", 800, 600, tmp,
		// bg,true, tabResult);
//	}

