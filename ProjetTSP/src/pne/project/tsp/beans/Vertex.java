package pne.project.tsp.beans;

import java.util.List;

public class Vertex {
	
	private int name;
	private List<Edge> listEdge;

	public Vertex(int name, List<Edge> listEdge) {
		super();
		this.name = name;
		this.listEdge = listEdge;
	}

}
