package pne.project.tsp.view;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import pne.project.tsp.beans.Graph;

public class TabView extends JTable {

	public TabView(Graph g) {
		super(new TabModel(g));
		setAutoCreateColumnsFromModel(false);
		setAutoscrolls(false);

		getColumnModel().getColumn(0).setCellRenderer(
				getTableHeader().getDefaultRenderer());
		getColumnModel().getColumn(0).setPreferredWidth(50);
//		setPreferredScrollableViewportSize(getPreferredSize());
		setDefaultRenderer(Object.class, new MatrixCellRenderer());

	}

	public JTable getAdjaMatrix() {
		return this;
	}

}