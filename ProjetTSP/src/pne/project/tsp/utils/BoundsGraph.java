package pne.project.tsp.utils;

public class BoundsGraph {

	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	
	public BoundsGraph() {
		this.xMin = 0;
		this.xMax = 0;
		this.yMin = 0;
		this.yMax = 0;
	}
	
	public int getxMin() {
		return xMin;
	}
	public void setxMin(int xMin) {
		this.xMin = xMin;
	}
	public int getxMax() {
		return xMax;
	}
	public void setxMax(int xMax) {
		this.xMax = xMax;
	}
	public int getyMin() {
		return yMin;
	}
	public void setyMin(int yMin) {
		this.yMin = yMin;
	}
	public int getyMax() {
		return yMax;
	}
	public void setyMax(int yMax) {
		this.yMax = yMax;
	}
	
	@Override
	public String toString() {
		return "xMax : "+xMax+", xMin : "+xMin+", yMax : "+yMax+", yMin : "+yMin;
	}
}
