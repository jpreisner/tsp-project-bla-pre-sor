package pne.project.tsp.view;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import pne.project.tsp.beans.Graph;

public class TabView {

	private JTable adjaMatrix;
	
	public TabView(JFrame jf, Graph i_graph) {
		adjaMatrix = new JTable(new MatrixModel(i_graph));
		adjaMatrix.setDefaultRenderer(Object.class, new MatrixCellRenderer());
		for(int i=0;i<i_graph.getNbNode();i++){
			adjaMatrix.getColumnModel().getColumn(i).setPreferredWidth(50);
		}
		JScrollPane sc = new JScrollPane(adjaMatrix, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		jf.add(sc);
		jf.setVisible(true);
		jf.setBounds(new Rectangle(new Dimension(850, 500)));
		jf.setLocationRelativeTo(null);
		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public TabView(Graph g){
		// Construire la JTable en fonction du graphe
	}

	public JTable getAdjaMatrix() {
		return adjaMatrix;
	}

	public void setAdjaMatrix(JTable adjaMatrix) {
		this.adjaMatrix = adjaMatrix;
	}
	
}