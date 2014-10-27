package pne.project.tsp.beans;

public class Graph {

	double[][] tabAdja;
	int nbNode;
	
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
