package pne.project.tsp.view;

import javax.swing.table.AbstractTableModel;

import pne.project.tsp.beans.Graph;

public class TabModel extends AbstractTableModel {

	private Graph g;

	public TabModel(Graph g) {
		this.g = g;
	}

	@Override
	public int getColumnCount() {
		return g.getNbNode() + 1;
	}

	@Override
	public String getColumnName(int index) {
		if (index > 0) {
			return Integer.toString(index - 1);
		} else {
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return g.getNbNode();
	}

	@Override
	public Object getValueAt(int i, int j) {
		if (j < 1) {
			/* Row Names */
			return i;
		} else {
			return g.getTabAdja()[i][j - 1];
		}
	}

}
