package pne.project.tsp.utils;

import pne.project.tsp.beans.Graph;

public class Stats {

	/**
	 * @param g
	 * @param power
	 * @return esperance of stochastic edges of the graph
	 */
	private double esperance(Graph g, int power) {
		double result = 0;
		int nbValuesStoch = 0;
		for (int i = 0; i < g.getNbNode(); i++) {
			for (int j = 0; j < g.getNbNode(); j++) {
				if (g.getTabStoch()[i][j]) {
					result += Math.pow(g.getTabAdja()[i][j], power);
					nbValuesStoch++;
				}
			}
		}
		return result / nbValuesStoch;
	}

	/**
	 * @param g
	 * @return ecart type of stochastic edges of the graph
	 */
	public double ecartType(Graph g) {
		return Math.sqrt((esperance(g, 2) - Math.pow(esperance(g, 1), 2)));
	}

	/**
	 * @param min
	 * @param max
	 * @return a random value between min and max
	 */
	public double getRandValueBetween(double min, double max) {
		if (min < 0) {
			min = 0;
		}
		return min + (Math.random() * (max - min));
	}
}
