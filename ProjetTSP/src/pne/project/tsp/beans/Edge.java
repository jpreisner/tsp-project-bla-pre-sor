package pne.project.tsp.beans;

public class Edge implements Comparable {
	private int node1;
	private int node2;
	private double costEdge;

	public Edge(int node1, int node2, double costEdge) {
		this.node1 = node1;
		this.node2 = node2;
		this.costEdge = costEdge;
	}

	public int getNode1() {
		return node1;
	}

	public int getNode2() {
		return node2;
	}

	public double getCostEdge() {
		return costEdge;
	}

	@Override
	public int compareTo(Object o) {
		Edge e = (Edge) o;
		if (costEdge > e.getCostEdge()) {
			return -1;
		} else if (costEdge < e.getCostEdge()) {
			return 1;
		} else {
			return 0;
		}

	}

}
