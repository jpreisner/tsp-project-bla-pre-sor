package pne.project.tsp.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.geom.Ellipse2D;

public class NodeView extends Component{
	
	private Ellipse2D circle;
	private int number;
	private Color colorFill;	// couleur interieur
	private Color colorDraw;	// couleur contour
	
	public NodeView(double x, double y, double d, int number, Color fill, Color draw){
		this.number = number;
		colorFill = fill;
		colorDraw = draw;
		circle = new Ellipse2D.Double(x, y, d, d);	// a verifier?
	}

	public Ellipse2D getCircle() {
		return circle;
	}

	public void setCircle(Ellipse2D circle) {
		this.circle = circle;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Color getColorFill() {
		return colorFill;
	}

	public void setColorFill(Color colorFill) {
		this.colorFill = colorFill;
	}

	public Color getColorDraw() {
		return colorDraw;
	}

	public void setColorDraw(Color colorDraw) {
		this.colorDraw = colorDraw;
	}
}
