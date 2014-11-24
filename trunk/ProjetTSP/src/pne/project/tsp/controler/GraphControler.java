package pne.project.tsp.controler;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.view.GraphView;

public class GraphControler {

	private GraphView gv;
	private Graph graph;

	public GraphControler(GraphView gv, boolean isCreateMode, Graph graph) {
		this.gv = gv;
		this.graph = graph;
		// Faire passer le boolean dans GraphView
	}

	/**
	 * Ajouter une méthode qui permet de savoir si : - on clique sur Terminer -
	 * on clique sur Résoudre - on clique sur Quitter (ActionListeners)
	 */

	public GraphView getGv() {
		return gv;
	}

	public void setGv(GraphView gv) {
		this.gv = gv;
	}

	public Graph getGraph() {
		return graph;
	}

}
