package pne.project.tsp.view;

import javax.swing.table.AbstractTableModel;

import pne.project.tsp.beans.Graph;

public class MatrixModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private Graph graph;

	public MatrixModel(Graph graph) {
		this.graph = graph;
	}

	@Override
	public int getColumnCount() {
		return this.graph.getNbNode();
	}

	@Override
	public int getRowCount() {
		return this.graph.getNbNode();
	}

	@Override
	public Object getValueAt(int i, int j) {
		return graph.getTabAdja()[i][j];
	}
	
	

}
