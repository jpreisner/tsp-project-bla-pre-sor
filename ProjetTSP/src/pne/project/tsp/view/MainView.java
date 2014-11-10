package pne.project.tsp.view;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainView {

	private JFrame jf;
	
	// Ces 3 boutons --> MainCanvas	???
	private JButton createTSP;
	private JButton readTSP;
	private JButton quit;
	
	//private MainCanvas mainCanvas;		???
	
	public MainView(String titre, int width, int height){
		jf = new JFrame(titre);
		jf.setSize(width, height);
		
		//mainCanvas = new MainCanvas();
		//jf.add(mainCanvas); 
		
		//jf.setVisible(true);
	}
	
}
