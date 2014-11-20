package pne.project.tsp.controler;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import pne.project.tsp.view.MainView;

public class MainControler {

	private MainView mv;

	public MainControler(MainView mv) {
		this.mv = mv;
		mv.getMainCanvas().addMouseListener(menuPrincipal);
	}
	
	public MouseListener menuPrincipal = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub		
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub		
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Point p = new Point(arg0.getX(), arg0.getY());
			
			if(mv.getMainCanvas().getChargerPVC().contains(p)){
				// appeler methode chargerPVC
				System.out.println("Gignac souhaite charger un PVC");
				mv.dispose();
			}
			else if(mv.getMainCanvas().getQuitter().contains(p)){
				mv.dispose();
			}
			
		}
	};

	public MainView getMv() {
		return mv;
	}

	public void setMv(MainView mv) {
		this.mv = mv;
	}
	
	public static void main (String[] args){
		MainControler c = new MainControler(new MainView("View", 800, 600));
		//c.getMv().setVisible(true);
	}
}
