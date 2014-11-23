package pne.project.tsp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import pne.project.tsp.beans.Graph;

public class MainView extends JFrame {
	private MainCanvas mc = null;
	private GraphCanvas gc = null;
	private TabView adjaMatrix = null;
	private ButtonsCanvas bc = null;

	public MainView(String titre, int width, int height) {
		new JFrame(titre);
		setSize(width, height);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		menuPrincipal(width, height);

	}

	public void menuPrincipal(int width, int height) {
		// getContentPane().removeAll();
		mc = new MainCanvas(width, height);
		getContentPane().add(mc);
		// getContentPane().repaint();
		// add(mc);
		setVisible(true);
	}

	public void graphView(int width, int height, Graph g, boolean isResolved,
			ArrayList<NodeView> listNode, double[][] tabResult) {
		getContentPane().removeAll();

		JPanel mainPanel = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		// topPanel
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		gc = new GraphCanvas(isResolved, listNode, tabResult);
		topPanel.add(gc);

		topPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		topPanel.add(separator);
		topPanel.add(Box.createRigidArea(new Dimension(30, 0)));

		/* display Right Matrix*/
		JPanel panelTab = new JPanel();
		panelTab.setLayout(new BoxLayout(panelTab, BoxLayout.Y_AXIS));
		adjaMatrix = new TabView(g);

		panelTab.add(adjaMatrix.getTableHeader(), BorderLayout.WEST);
		panelTab.add(adjaMatrix, BorderLayout.LINE_END);

		JScrollPane sc = new JScrollPane(panelTab,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		topPanel.add(sc);

		// bottomPanel
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bc = new ButtonsCanvas(width, height);
		bottomPanel.add(bc);

		// mainPanel
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);

		getContentPane().add(mainPanel);
		setVisible(true);
	}

	public MainCanvas getMainCanvas() {
		return mc;
	}

	public GraphCanvas getGraphCanvas() {
		return gc;
	}

	public TabView getAdjaMatrix() {
		return adjaMatrix;
	}

	public ButtonsCanvas getButtonCanvas() {
		return bc;
	}

	public static void main(String[] args) {
		MainView view = new MainView("View", 800, 600);
	}

}
