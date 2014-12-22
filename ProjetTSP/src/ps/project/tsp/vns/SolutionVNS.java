package ps.project.tsp.vns;

import java.util.ArrayList;
import java.util.Collections;

import pne.project.tsp.beans.Edge;
import pne.project.tsp.beans.Graph;

public class SolutionVNS {
	private ArrayList<Integer> pathChosen;
	private double pathCost;

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

	public Graph defineScenario(Graph g) {
		return null;
	}

	public void definePenalty(Graph g) {

	}
}
