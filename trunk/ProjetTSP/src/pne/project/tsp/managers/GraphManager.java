package pne.project.tsp.managers;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import pne.project.tsp.beans.Graph;

public class GraphManager {

	public static void writeLinearProgram(Graph graph,String pathFileToExport) {
		IloCplex cplex;
		try {
			cplex = new IloCplex();

			IloNumVar[][] x = new IloNumVar[graph.getNbNode()][];
			for (int i = 0; i < graph.getNbNode(); i++) {
				x[i] = cplex.boolVarArray(graph.getNbNode());
			}

			setObjectiveFonction(graph, cplex, x);
			setConstraint1(graph, cplex, x);
			setConstraint2(graph, cplex, x);
			setConstraint3(graph, cplex,x);
			
			cplex.solve();
			cplex.writeSolution(pathFileToExport);
			
			cplex.end();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}

	private static void setObjectiveFonction(Graph graph, IloCplex cplex, IloNumVar[][] x) {
		IloLinearNumExpr objectiveFunction;
		try {
			objectiveFunction = cplex.linearNumExpr();

			for (int i = 0; i < graph.getNbNode(); i++) {
				for (int j = 0; j < graph.getNbNode(); j++) {
					if (i != j) {
						objectiveFunction.addTerm(graph.getTabAdja()[i][j],	x[i][j]);
					}
				}
			}

			cplex.addMinimize(objectiveFunction);
		} catch (IloException e) {
			e.printStackTrace();
		}
	}

	private static void setConstraint1(Graph graph, IloCplex cplex, IloNumVar[][] x) {
		try {
			for (int i = 0; i < graph.getNbNode(); i++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (int j = 0; j < graph.getNbNode(); j++) {
					if (i != j) {
						expr.addTerm(1.0, x[i][j]);
					}
				}
				cplex.addEq(1.0, expr);
			}
		} catch (IloException e) {
			e.printStackTrace();
		}
	}

	private static void setConstraint2(Graph graph, IloCplex cplex, IloNumVar[][] x) {
		try {
			for (int j = 0; j < graph.getNbNode(); j++) {
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (int i = 0; i < graph.getNbNode(); i++) {
					if (i != j) {
						expr.addTerm(1.0, x[i][j]);
					}
				}
				cplex.addEq(1.0, expr);
			}
		} catch (IloException e) {
			e.printStackTrace();
		}	
	}

	private static void setConstraint3(Graph graph, IloCplex cplex, IloNumVar[][] x) {
		try {
			IloNumVar[] u = cplex.numVarArray(graph.getNbNode(), 0, Double.MAX_VALUE);

			for (int i = 0; i < graph.getNbNode(); i++) {
				for (int j = 0; j < graph.getNbNode(); j++) {
					if (i != j) {
						IloLinearNumExpr expr = cplex.linearNumExpr();
						expr.addTerm(1.0, u[i]);
						expr.addTerm(-1.0, u[j]);
						expr.addTerm(1.0, x[i][j]);
						cplex.addLe(expr, graph.getNbNode()-2);
					}
				}
			}
		} catch (IloException e) {
			e.printStackTrace();
		}	
	}
}
