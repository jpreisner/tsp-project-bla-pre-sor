package pne.project.tsp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		super(titre);
		setSize(width, height);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);

		menuPrincipal(width, height);

	}

	public void menuPrincipal(int width, int height) {
		mc = new MainCanvas(width, height);
		getContentPane().add(mc);
		setVisible(true);
	}

	public void graphView(int width, int height, Graph g, boolean isResolved, ArrayList<NodeView> listNode,
			int[] tabResult, double objectiveValue, int resolutionDuration) {
		getContentPane().removeAll();
		JPanel mainPanel = new JPanel();
		JPanel topPanel = new JPanel(); 
		JPanel bottomPanel = new JPanel();
		JPanel panelTab = new JPanel();
		JPanel panelGraph = new JPanel();
		JPanel panelRight = new JPanel();
		int widthPanelTab = 60*g.getNbNode();
		int heightPanelTab = 16 * g.getNbNode() + 20;
		bottomPanel.setBackground(new Color(0, 255, 255));
		
		topPanel.setPreferredSize(new Dimension(width, height - 150));
		panelGraph.setPreferredSize(new Dimension(widthPanelTab, heightPanelTab));
		bottomPanel.setPreferredSize(new Dimension(width, 150));

		// topPanel
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		gc = new GraphCanvas(isResolved, listNode, tabResult);
		panelGraph.setLayout(new BorderLayout());
		panelGraph.add(gc, BorderLayout.CENTER);

		topPanel.add(panelGraph);
		topPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		topPanel.add(separator);
		topPanel.add(Box.createRigidArea(new Dimension(30, 0)));

		/* display Right Matrix */
		panelTab.setLayout(new BoxLayout(panelTab, BoxLayout.Y_AXIS));
		adjaMatrix = new TabView(g,isResolved,tabResult);

		panelTab.add(adjaMatrix.getTableHeader(), BorderLayout.WEST);
		panelTab.add(adjaMatrix, BorderLayout.LINE_END);
		panelTab.setPreferredSize(new Dimension(widthPanelTab, heightPanelTab));

		JScrollPane sc = new JScrollPane(panelTab, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sc.setWheelScrollingEnabled(true);

		topPanel.add(sc);		

		// bottomPanel
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bc = new ButtonsCanvas(width, height, isResolved);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(bc, BorderLayout.CENTER);

		// mainPanel
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);

		getContentPane().add(mainPanel);
		setVisible(true);
		if (isResolved) {
			JOptionPane.showMessageDialog(this, "Coût de la solution : " + objectiveValue + ".\n"
					+ "Duree de la résolution : "+resolutionDuration+" secondes.\n"
							, "Solution",
					JOptionPane.INFORMATION_MESSAGE);
		}
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
}
