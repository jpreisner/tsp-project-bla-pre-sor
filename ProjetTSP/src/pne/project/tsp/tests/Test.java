package pne.project.tsp.tests;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.managers.GraphManager;
import pne.project.tsp.utils.FileReader;
import pne.project.tsp.view.TabView;

public class Test {

	/* TSP main*/
	public static void main(String[] args) {
		Graph g1 = FileReader.
				buildGraphFromXml("data/XML/gr48.xml");
		GraphManager.writeLinearProgram(g1,"tests/lpex1.lp", "tests/results.txt");
	}
	
	/* JTable main*/
//	public static void main(String[] args) {
//		JFrame jf = new JFrame();
//		Graph g1 = FileReader.buildGraphFromXml("data/XML/ulysses16.xml");
//
//		JScrollPane sc = new JScrollPane(new TabView(g1), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//			    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//		jf.add(sc);
//		jf.add(new TabView(g1));
//		jf.setVisible(true);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.pack();
//		jf.setLocationRelativeTo(null);
	
//	}

}
