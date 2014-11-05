package pne.project.tsp.beans;

public class Graph {

	double[][] tabAdja;
	int nbNode;
	
	/**
	 * Initial Constructor
	 * @param tabInt
	 * @param nbNode
	 */
	public Graph(double[][] tabInt, int nbNode){
		this.tabAdja= tabInt;
		this.nbNode = nbNode;
	}
	
	public int getNbNode() {
		return nbNode;
	}
	
	/**
	 * @return Adjascence table of the graph
	 */
	public double[][] getTabAdja() {
		return tabAdja;
	}
}
