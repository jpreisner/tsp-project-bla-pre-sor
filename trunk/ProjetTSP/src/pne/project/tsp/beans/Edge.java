package pne.project.tsp.beans;

public class Edge {

	private int cost;
	private Vertex vStart;
	private Vertex vFinal;
	
	public Edge(Vertex vStart, Vertex vFinal, int cost){
		this.vStart = vStart;
		this.vFinal = vFinal;
		this.cost = cost;
	}
}
