package ps.project.tsp.vns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.beans.NodeCouple;

public class SolutionVNS {
	private Graph graph_scenario;
	private ArrayList<Integer> pathChosen;	// ex pour un graphe à 4 noeuds : 1-2-3-4
	private double pathCost;
	
	public SolutionVNS(Graph g, ArrayList<Integer> pathChosen, double pathCost){
		graph_scenario = g;
		this.pathChosen = pathChosen;
		this.pathCost = pathCost;
	}
	
	public SolutionVNS(Graph g, ArrayList<Integer> pathChosen){
		graph_scenario = g;
		this.pathChosen = pathChosen;
		this.pathCost = calculPathCost();
	}
	
	public SolutionVNS(Graph g){
		graph_scenario = g;
		pathChosen = new ArrayList<Integer>();
		pathCost = 0.0;
	}
	
	/**
	 *  Comparator used in glouton algorithm
	 */
	private Comparator<NodeCouple> compareNodeCouple = new Comparator<NodeCouple>() {

		@Override
		public int compare(NodeCouple arg0, NodeCouple arg1) {
			if (arg0.getCostEdge() < arg1.getCostEdge()) {
				return -1;
			} else if (arg0.getCostEdge() > arg1.getCostEdge()) {
				return 1;
			} else {
				return 0;
			}
		}
	};
	
	/**Algorithme de Glouton lancé au tout début du programme
	 * 
	 * @param g
	 * @return
	 */
	public ArrayList<Integer> gloutonAlgorithm(Graph g) {
		ArrayList<NodeCouple> alNodeCouple = new ArrayList<NodeCouple>();
		boolean[] tabInnerEdge = new boolean[g.getNbNode()];
		boolean[] tabOuterEdge = new boolean[g.getNbNode()];
		ArrayList<NodeCouple> result = new ArrayList<NodeCouple>();
		/* recuperation de toutes les arêtes*/
		for(int i=0;i<g.getNbNode();i++){
			for(int j=0;j<g.getNbNode();j++){
				if(i!=j && g.getTabAdja()[i][j]>0){
					alNodeCouple.add(new NodeCouple(i, j, g.getTabAdja()[i][j]));
				}
			}
		}
		
		/* tri des arêtes */
		Collections.sort(alNodeCouple, compareNodeCouple );
		
		/* construction de la solution gloutonne*/
		/* TODO verifier les sous tours*/
		for(NodeCouple e : alNodeCouple){
			if(!tabInnerEdge[e.getN1()] && !tabOuterEdge[e.getN2()]){
				tabInnerEdge[e.getN1()] = true;
				tabOuterEdge[e.getN2()] = true;
				result.add(e);
			}
		}	
		
		/**
		 * Remarque : on souhaite que la solution soit de la forme 1-2-3-4 et non (1,2) (2,3) (3,4) (4,1)
		 */
		ArrayList<Integer> solution = new ArrayList<Integer>();
		for(NodeCouple nc : result){
			solution.add(nc.getN1());
		}
		return solution;
	}

	public double getPathCost() {
		return pathCost;
	}

	
	
	public double calculPathCost(){
		double cost = 0.0;
		int n = pathChosen.size();
		for(int i=0; i<n-1; i++){
			cost+=graph_scenario.getTabAdja()[pathChosen.get(i)][pathChosen.get(i+1)];
		}
		cost+=graph_scenario.getTabAdja()[pathChosen.get(n)][pathChosen.get(0)];
		return cost;
	}

	public Graph defineScenario(Graph g) {
		return null;
	}

	public void definePenalty(Graph g) {

	}

	public ArrayList<Integer> getPathChosen() {
		return pathChosen;
	}

	public Graph getGraph_scenario() {
		return graph_scenario;
	}
	
	public void setPathCost(double cost){
		pathCost = cost;
	}
	
	public void setPathChosen(ArrayList<Integer> pathChosen){
		this.pathChosen = pathChosen;
	}
	
	public SolutionVNS clone(){
		return new SolutionVNS(graph_scenario, pathChosen, pathCost);
	}
}
