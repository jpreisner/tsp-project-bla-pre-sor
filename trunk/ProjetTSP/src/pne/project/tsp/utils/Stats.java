package pne.project.tsp.utils;

import pne.project.tsp.beans.Graph;

public class Stats {

	private double esperance(Graph g, int power) {
		double result = 0;
		int nbValuesStoch =0;
		for(int i=0;i<g.getNbNode();i++){
			for(int j=0;j<g.getNbNode();j++){
				if(g.getTabStoch()[i][j]){
					result+=Math.pow(g.getTabAdja()[i][j],power);
					nbValuesStoch++;
				}
			}
		}
		return result/nbValuesStoch;
	}
	
	public double ecartType(Graph g){
		return Math.sqrt((esperance(g, 2)-Math.pow(esperance(g, 1),2)));
	}
}
