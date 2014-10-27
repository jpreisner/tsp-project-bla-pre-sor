package pne.project.tsp.beans;

import java.util.List;

public class Vertex {
	
	public int name;
	public List<Edge> listEdge;

	public Vertex(int name, List<Edge> listEdge) {
		super();
		this.name = name;
		this.listEdge = listEdge;
	}

}
