package pne.project.tsp.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

public class MatrixCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JTextField editor = new JTextField();
		if (value != null) {
			editor.setText(value.toString());
		}
		editor.setEditable(false);
		if (row == col) {
			editor.setBackground(Color.GRAY);
		}
		return editor;

	}

}
