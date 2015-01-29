package pne.project.tsp.utils;

import java.awt.Rectangle;
import java.io.FileOutputStream;
import java.util.ArrayList;

import de.progra.charting.ChartEncoder;
import de.progra.charting.DefaultChart;
import de.progra.charting.model.DefaultChartDataModel;
import de.progra.charting.render.LineChartRenderer;

public class LineChart {

	public static void main(String[] args) {
		ArrayList<Double> lambda = new ArrayList<Double>();
		ArrayList<Double> ro = new ArrayList<Double>();
		int n = 100;
		for (int i = 0; i < n; i++) {
			lambda.add((double) (i*2));
			ro.add((double) i);
		}

		makeFirstChart(lambda, ro, n);
	}

	public static void makeFirstChart(ArrayList<Double> lambdas, ArrayList<Double> ro, int nbIterations) {

		double[][] model = new double[2][nbIterations];//{ { 0, 100, 20000 }, { 0, 1000, 20000 } }; // Create data array
		

		for(int i=0;i<nbIterations;i++){
			model[0][i] = lambdas.get(i);
			model[1][i] = ro.get(i);
		}

		// Create x-axis values
		double[] columns = new double[nbIterations]; 
		for (int i = 0; i < nbIterations; i++) {
			columns[i] = i;
		}
		
		String[] rows = { "Lambda","Ro" }; // Create data set title

		String title = "Diagramme des pénalités"; // Create diagram title

		int width = 800; // Image size
		int height = 500;

		// Create data model
		DefaultChartDataModel data = new DefaultChartDataModel(model, columns, rows);

		// Create chart with default coordinate system
		DefaultChart c = new DefaultChart(data, title, DefaultChart.LINEAR_X_LINEAR_Y,"Nombre d'iterations","Valeur de la pénalité");

		// Add a line chart renderer
		c.addChartRenderer(new LineChartRenderer(c.getCoordSystem(), data), 1);

		// Set the chart size
		c.setBounds(new Rectangle(0, 0, width, height));

		// Export the chart as a PNG image
		try {
			ChartEncoder.createEncodedImage(new FileOutputStream("Diagramme Penalites.png"), c,
					"png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}