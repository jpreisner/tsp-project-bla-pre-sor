package pne.project.tsp.beans;

public class Graph {

	private double[][] tabAdja;
	private int nbNode;
	private boolean[][] tabStoch;

	/**
	 * PNE Constructor
	 * @param tabInt
	 * @param nbNode
	 */
	public Graph(double[][] tabInt, int nbNode) {
		this.tabAdja = tabInt;
		this.nbNode = nbNode;
	}

	/**
	 * PS Constructor
	 * @param tabInt
	 * @param nbNode
	 * @param tabStoch
	 */
	public Graph(double[][] tabInt, int nbNode, boolean[][] tabStoch) {
		this.tabAdja = tabInt;
		this.nbNode = nbNode;
		this.tabStoch = tabStoch;
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
	
	public boolean[][] getTabStoch() {
		return tabStoch;
	}
}
