package pne.project.tsp.beans;

public class Graph {

	private double[][] tabAdja;
	private int nbNode;
	private boolean[][] tabStoch;
	private int percentageDeterministEdges;

	/**
	 * PNE Constructor
	 * 
	 * @param tabInt
	 * @param nbNode
	 */
	public Graph(double[][] tabInt, int nbNode) {
		this.tabAdja = tabInt;
		this.nbNode = nbNode;
	}

	/**
	 * PS Constructor
	 * 
	 * @param tabInt
	 * @param nbNode
	 * @param tabStoch
	 */
	public Graph(double[][] tabInt, int nbNode, int percentageDeterministEdges) {
		this.tabAdja = tabInt;
		this.nbNode = nbNode;
		this.percentageDeterministEdges = percentageDeterministEdges;
	}

	//  met toutes les val de tabStoch a bool
	public void initTabStoch(boolean bool){
		for(int i=0; i<nbNode; i++){
			for(int j=0; j<nbNode; j++){
				tabStoch[i][j] = bool;
			}
		}
	}

	public int getNbNode() {
		return nbNode;
	}

	public double[][] getTabAdja() {
		return tabAdja;
	}

	public boolean[][] getTabStoch() {
		return tabStoch;
	}

	/**
	 * @param i
	 * @param j
	 * @return true if the edge (i,j) is stochastic
	 */
	public boolean isEdgeStochastic(int i, int j) {
		return tabStoch[i][j];
	}

	/**
	 * @return true is all edges are determinist, else return false if only one
	 *         edge is stochastic
	 */
	public boolean isGraphDeterminist() {
		for (int i = 0; i < nbNode; i++) {
			for (int j = 0; j < nbNode; j++) {
				if (tabStoch[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * this method define which edges are determinist in the graph
	 */
	public void defineDeterministEdges() {
		int numberEdgesDeterminist = (int) Math.ceil((double) nbNode / (double) percentageDeterministEdges);
		initTabStoch();
		for (int z = 0; z < numberEdgesDeterminist; z++) {
			int i = (int) (Math.random() * nbNode);
			int j = (int) (Math.random() * nbNode);
			if (!tabStoch[i][j]) {
				z--;
			} else {
				tabStoch[i][j] = false;
			}
		}
	}

	/**
	 * Init all tabStoch values to true
	 */
	private void initTabStoch() {
		for (int i = 0; i < nbNode; i++) {
			for (int j = 0; j < nbNode; j++) {
				tabStoch[i][j] = true;
			}
		}
	}
}
