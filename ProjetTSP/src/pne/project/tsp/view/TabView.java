package pne.project.tsp.view;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import pne.project.tsp.beans.Graph;

public class TabView {

	private JTable adjaMatrix;
	private JScrollPane sc;
	
	public TabView(Graph g){
		// Construire la JTable en fonction du graphe
	}

	public JTable getAdjaMatrix() {
		return adjaMatrix;
	}

	public void setAdjaMatrix(JTable adjaMatrix) {
		this.adjaMatrix = adjaMatrix;
	}

	public JScrollPane getSc() {
		return sc;
	}

	public void setSc(JScrollPane sc) {
		this.sc = sc;
	}
	
}