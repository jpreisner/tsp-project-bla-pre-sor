package pne.project.tsp.tests;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.managers.GraphManager;
import pne.project.tsp.utils.XmlReader;

public class Test {

	public static void main(String[] args) {
		Graph g1 = XmlReader.buildGraphFromXml("att48.xml");
		GraphManager.writeLinearProgram(g1,"D:/results.txt");
	}
}
