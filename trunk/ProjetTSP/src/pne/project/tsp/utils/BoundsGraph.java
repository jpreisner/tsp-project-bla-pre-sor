package pne.project.tsp.utils;

public class BoundsGraph {

	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	
	public BoundsGraph() {
		this.xMin = 1000;
		this.xMax = 0;
		this.yMin = 1000;
		this.yMax = 0;
	}
	
	public double getxMin() {
		return xMin;
	}
	public void setxMin(double xMin) {
		this.xMin = xMin;
	}
	public double getxMax() {
		return xMax;
	}
	public void setxMax(double xMax) {
		this.xMax = xMax;
	}
	public double getyMin() {
		return yMin;
	}
	public void setyMin(double yMin) {
		this.yMin = yMin;
	}
	public double getyMax() {
		return yMax;
	}
	public void setyMax(double yMax) {
		this.yMax = yMax;
	}
	
	@Override
	public String toString() {
		return "xMax : "+xMax+", xMin : "+xMin+", yMax : "+yMax+", yMin : "+yMin;
	}
}
