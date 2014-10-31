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
	
	public double[][] getTabAdja() {
		return tabAdja;
	}
}
