package pne.project.tsp.view;

import java.awt.Color;

import javax.swing.JFrame;

public class MainView extends JFrame{
	private MainCanvas mc;
	
	public MainView(String titre, int width, int height){
		new JFrame(titre);
		setSize(width, height);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		mc = new MainCanvas(800, 600);
		menuPrincipal();
		
	}
	
	public void menuPrincipal(){
		//removeAll();
		add(mc);
		setVisible(true);
	}
	
	public MainCanvas getMainCanvas(){
		return mc;
	}
	
	
	public static void main (String[] args){
		MainView view = new MainView("View", 800, 600);
	}
	
}
