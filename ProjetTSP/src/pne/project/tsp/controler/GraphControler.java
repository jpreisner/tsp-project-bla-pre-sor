package pne.project.tsp.controler;

import pne.project.tsp.view.GraphView;

public class GraphControler {

	private GraphView gv;

	public GraphControler(GraphView gv) {
		this.gv = gv;
	}

	public GraphView getGv() {
		return gv;
	}

	public void setGv(GraphView gv) {
		this.gv = gv;
	}

}
