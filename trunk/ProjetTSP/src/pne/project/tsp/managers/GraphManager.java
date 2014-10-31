package pne.project.tsp.managers;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import pne.project.tsp.beans.Graph;

public class GraphManager {

	/**
	 * Method called to write Linear program with graph in param and write it in file in param.
	 * @param i_graph
	 * @param o_pathFileToExport
	 */
	public static void writeLinearProgram(Graph i_graph,String o_pathFileToExport) {
		IloCplex cplex;
		try {
			cplex = new IloCplex();

			IloNumVar[][] x = new IloNumVar[i_graph.getNbNode()][];
			for (int i = 0; i < i_graph.getNbNode(); i++) {
				x[i] = cplex.boolVarArray(i_graph.getNbNode());
			}

			setObjectiveFonction(i_graph, cplex, x);
			setConstraint1(i_graph, cplex, x);
			setConstraint2(i_graph, cplex, x);
			setConstraint3(i_graph, cplex,x);
			
			cplex.solve();
			cplex.writeSolution(o_pathFileToExport);
			
			cplex.end();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write objective function
	 * @param graph
	 * @param cplex
	 * @param x
	 */
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

	/**
	 * Write the first constraint : each node have only one edge going out
	 * @param graph
	 * @param cplex
	 * @param x
	 */
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

	/**
	 * Write the second constraint : each node have only one edge going in
	 * @param graph
	 * @param cplex
	 * @param x
	 */
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

	/**
	 * Write the third constraint : the path chozen does'nt contains sub-cycle in it.
	 * @param graph
	 * @param cplex
	 * @param x
	 */
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
