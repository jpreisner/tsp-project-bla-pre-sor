package pne.project.tsp.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import pne.project.tsp.beans.Graph;

public class TabView extends JTable {

	public TabView(Graph g, boolean isResolved, final int[] tabResult) {
		super(new TabModel(g));
		setAutoCreateColumnsFromModel(true);
		setAutoscrolls(false);

		getColumnModel().getColumn(0).setCellRenderer(getTableHeader().getDefaultRenderer());
		setPreferredScrollableViewportSize(getPreferredSize());

		/*
		 * setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 * 
		 * for (int j=1; j < getColumnCount(); j+=1) {
		 * getColumnModel().getColumn(j).setPreferredWidth(500); }
		 */
		if(!isResolved){
			setDefaultRenderer(Object.class, new MatrixCellRenderer());
		}else{
			setDefaultRenderer(Object.class, new TableCellRenderer() {
				
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
						int row, int col) {
					JTextField editor = new JTextField();
					if (value != null) {
						editor.setText(value.toString());
					}
					editor.setEditable(false);
					if (row + 1 == col) {
						editor.setBackground(Color.GRAY);
					}
					if (tabResult[row]==col) {
						editor.setBackground(Color.RED);
					}
					return editor;
				}
			});
		}

	}

	public JTable getAdjaMatrix() {
		return this;
	}

}