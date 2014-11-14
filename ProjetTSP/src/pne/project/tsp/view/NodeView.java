package pne.project.tsp.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.geom.Ellipse2D;

public class NodeView extends Component{
	
	private Ellipse2D circle;
	private int id;
	private Color colorFill;	// couleur interieur
	private Color colorDraw;	// couleur contour
	private Color colorText;
	
	public NodeView(float x, float y, float d, int number, Color fill, Color draw, Color text){
		this.id = number;
		colorFill = fill;
		colorDraw = draw;
		colorText = text;
		
		circle = new Ellipse2D.Float(x, y, d, d);	// a verifier?
	}

	public Ellipse2D getCircle() {
		return circle;
	}

	public void setCircle(Ellipse2D circle) {
		this.circle = circle;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
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

	public Color getColorText() {
		return colorText;
	}

	public void setColorText(Color colorText) {
		this.colorText = colorText;
	}
}
