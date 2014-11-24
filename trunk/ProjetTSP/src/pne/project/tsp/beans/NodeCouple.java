package pne.project.tsp.beans;

public class NodeCouple {
	private int n1;
	private int n2;

	public NodeCouple(int n1, int n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	public int getN1() {
		return n1;
	}

	public void setN1(int n1) {
		this.n1 = n1;
	}

	public int getN2() {
		return n2;
	}

	public void setN2(int n2) {
		this.n2 = n2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + n1;
		result = prime * result + n2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeCouple other = (NodeCouple) obj;
		if (n1 != other.n1)
			return false;
		if (n2 != other.n2)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NodeCouple [" + n1 + ", " + n2 + "]";
	}

}
