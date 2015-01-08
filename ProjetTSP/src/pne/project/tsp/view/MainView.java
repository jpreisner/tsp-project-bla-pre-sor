package pne.project.tsp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;

import pne.project.tsp.beans.Graph;

public class MainView extends JFrame {
	private MainCanvas mc = null;
	private GraphCanvas gc = null;
	private TabView adjaMatrix = null;
	private ButtonsCanvas bc = null;
	private ButtonGroup group;
	private JRadioButton branch_and_band=new JRadioButton("Branch and bound");
	private JRadioButton vns=new JRadioButton("VNS");
	private Box box =Box.createVerticalBox();
	private JLabel methode=new JLabel("methode de resolution");
	private JTextField pourcentage=new JTextField("0",1);
	private JSlider pou_aret_deter=new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
	private JPanel panel1 =new JPanel();

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
		if(isResolved){
			bottomPanel.setLayout(new BorderLayout());
			bottomPanel.add(bc, BorderLayout.CENTER);
			
		}else{
			group=new ButtonGroup();
			group.add(branch_and_band);
			group.add(vns);
			panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
			panel1.add(methode);
			panel1.add(branch_and_band);
			panel1.add(vns);
			panel1.add(pou_aret_deter);
			panel1.add(pourcentage);
			panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			bottomPanel.setLayout(new BorderLayout());
			bottomPanel.add(bc, BorderLayout.CENTER);
			bottomPanel.add(panel1,BorderLayout.WEST);
			
			
			
			
		}
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

	public ButtonGroup getGroup() {
		return group;
	}

	public void setGroup(ButtonGroup group) {
		this.group = group;
	}

	public JRadioButton getBranch_and_band() {
		return branch_and_band;
	}

	public void setBranch_and_band(JRadioButton branch_and_band) {
		this.branch_and_band = branch_and_band;
	}

	public JRadioButton getVns() {
		return vns;
	}

	public void setVns(JRadioButton vns) {
		this.vns = vns;
	}

	public JLabel getMethode() {
		return methode;
	}

	public void setMethode(JLabel methode) {
		this.methode = methode;
	}

	public JTextField getPourcentage() {
		return pourcentage;
	}

	public void setPourcentage(JTextField pourcentage) {
		this.pourcentage = pourcentage;
	}

	public JSlider getPou_aret_deter() {
		return pou_aret_deter;
	}

	public void setPou_aret_deter(JSlider pou_aret_deter) {
		this.pou_aret_deter = pou_aret_deter;
	}

	public JPanel getPanel1() {
		return panel1;
	}

	public void setPanel1(JPanel panel1) {
		this.panel1 = panel1;
	}
	
}
