package ps.project.tsp.vns;

import java.util.ArrayList;
import java.util.Collections;

import pne.project.tsp.beans.Edge;
import pne.project.tsp.beans.Graph;

public class SolutionVNS {
	private Graph graph_scenario;
	private ArrayList<Integer> pathChosen;	// pour un graphe à 4 noeuds : 1-2-3-4
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

	public double getPathCost() {
		return pathCost;
	}

	public ArrayList<Edge> gloutonAlgorithm(Graph g) {
		ArrayList<Edge> allEdge = new ArrayList<Edge>();
		boolean[] tabInnerEdge = new boolean[g.getNbNode()];
		boolean[] tabOuterEdge = new boolean[g.getNbNode()];
		ArrayList<Edge> result = new ArrayList<Edge>();
		/* recuperation de toutes les arêtes*/
		for(int i=0;i<g.getNbNode();i++){
			for(int j=0;j<g.getNbNode();j++){
				if(i!=j){
					allEdge.add(new Edge(i, j, g.getTabAdja()[i][j]));
				}
			}
		}
		
		/* tri des arêtes */
		Collections.sort(allEdge);
		
		/* construction de la solution gloutonne*/
		/* TODO verifier les sous tours*/
		for(Edge e : allEdge){
			if(!tabInnerEdge[e.getNode1()] && !tabOuterEdge[e.getNode2()]){
				tabInnerEdge[e.getNode1()] = true;
				tabOuterEdge[e.getNode2()] = true;
				result.add(e);
			}
		}	
		
		return result;
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
}
