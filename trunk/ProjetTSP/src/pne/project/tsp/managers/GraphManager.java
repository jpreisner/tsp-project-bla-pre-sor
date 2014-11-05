package pne.project.tsp.managers;

import java.util.ArrayList;

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
			
			/* Variables Initialisation*/
			String[][] varName = new String[i_graph.getNbNode()][i_graph.getNbNode()];
			initVarNameTab(i_graph.getNbNode(),varName);

			for (int i = 0; i < i_graph.getNbNode(); i++) {
				x[i] = cplex.boolVarArray(i_graph.getNbNode(),varName[i]);
			}
			IloNumVar[] u = cplex.numVarArray(i_graph.getNbNode(), 0, Double.MAX_VALUE);

			setObjectiveFonction(i_graph, cplex, x);
			setConstraintOuterEdge(i_graph, cplex, x);
			setConstraintInnerEdge(i_graph, cplex, x);
			setConstraintSubCycle(i_graph, cplex,x,u);
			
			cplex.exportModel("lpex1.lp");
			
			cplex.solve();
			cplex.writeSolution(o_pathFileToExport);
			
			cplex.end();
		} catch (IloException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method called to initialize all edge variable names
	 * @param nbNode
	 * @param varName
	 */
	private static void initVarNameTab(int nbNode, String[][] varName) {
		for(int i=0;i<nbNode;i++){
			for(int j=0;j<nbNode;j++){
				varName[i][j]="x"+i+";"+j;
			}
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
	private static void setConstraintOuterEdge(Graph graph, IloCplex cplex, IloNumVar[][] x) {
		try {
			for (int i = 0; i < graph.getNbNode(); i++) {
				IloLinearNumExpr constraint = cplex.linearNumExpr();
				for (int j = 0; j < graph.getNbNode(); j++) {
					if (i != j) {
						constraint.addTerm(1.0, x[i][j]);
					}
				}
				cplex.addEq(1.0, constraint);
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
	private static void setConstraintInnerEdge(Graph graph, IloCplex cplex, IloNumVar[][] x) {
		try {
			for (int j = 0; j < graph.getNbNode(); j++) {
				IloLinearNumExpr constraint = cplex.linearNumExpr();
				for (int i = 0; i < graph.getNbNode(); i++) {
					if (i != j) {
						constraint.addTerm(1.0, x[i][j]);
//						System.out.println(x[i][j].getName().substring(1).split(";")[0]);
					}
				}
				cplex.addEq(1.0, constraint);
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
	 * @param u 
	 */
	private static void setConstraintSubCycle(Graph graph, IloCplex cplex, IloNumVar[][] x, IloNumVar[] u) {
		try {

			for (int i = 1; i < graph.getNbNode(); i++) {
				for (int j = 1; j < graph.getNbNode(); j++) {
					if (i != j) {
						IloLinearNumExpr expr = cplex.linearNumExpr();
						ArrayList<IloNumVar> test = new ArrayList<IloNumVar>();
						expr.addTerm(-1.0, u[j]);
						expr.addTerm(graph.getNbNode()-1, x[i][j]);
						cplex.addLe(expr, graph.getNbNode()-2);
					}
				}
			}
		} catch (IloException e) {
			e.printStackTrace();
		}	
	}

}
