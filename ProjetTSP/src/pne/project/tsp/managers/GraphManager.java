package pne.project.tsp.managers;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.DoubleParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.omg.CORBA.portable.IndirectionException;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.beans.NodeCouple;

public class GraphManager {
	
	/**
	 * Method called to write Linear program with graph in param and write it in file in param.
	 * @param i_graph
	 * @param o_pathFileToExport
	 * @return 
	 */
	public static int[] writeLinearProgram(Graph i_graph,String o_pathModelToExport,String o_pathFileToExport) {
		System.out.println("1 - utiliser la méthode plans-coupants\n2 - utiliser l'autre méthode");
		Scanner s = new Scanner(System.in);
		int choix = s.nextInt();
		int[] tabResult = new int[i_graph.getNbNode()];

		
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
			
			/* Permet de ne renvoyer que des 1 et 0*/
			cplex.setParam(DoubleParam.EpInt, 0.0);

			IloNumVar[] u = cplex.numVarArray(i_graph.getNbNode(), 0, Double.MAX_VALUE);

			setObjectiveFonction(i_graph, cplex, x);
			setConstraintOuterEdge(i_graph, cplex, x);
			setConstraintInnerEdge(i_graph, cplex, x);
			
			long startTime = System.nanoTime();			
			
			if(choix == 1){
				cplex.setOut(null);
				cplex.solve();		
				
				for(int i=0; i<i_graph.getNbNode(); i++){
					tabResult[i] = searchIndiceJ(cplex.getValues(x[i]), i_graph.getNbNode());
				}
				int cpt=0;
				while(addNewSubCycleConstraint(i_graph.getNbNode(), cplex, x, tabResult)){
					cpt++;
					
					// désactive l'affichage de cplex (ralentit les traitements)
					cplex.setOut(null);
					cplex.solve();

					// Enregistrement du résultat dans tabResult
					tabResult = new int[i_graph.getNbNode()];	// pas besoin?
					for(int i=0; i<i_graph.getNbNode(); i++){
						tabResult[i] = searchIndiceJ(cplex.getValues(x[i]), i_graph.getNbNode());
					}			
				}
				long stopTime = System.nanoTime();
				System.out.println(((stopTime - startTime)/1000000000)+" seconds");
				
				System.out.println("cpt=" + cpt);
				System.out.println("valeur chemin optimal : "+cplex.getObjValue());
				cplex.exportModel(o_pathModelToExport);
				cplex.writeSolution(o_pathFileToExport);
			}
			else{
				cplex.setOut(null);
				setConstraintSubCycle(i_graph, cplex, x, u);
				cplex.solve();
				System.out.println("valeur chemin optimal : "+cplex.getObjValue());
				cplex.exportModel(o_pathModelToExport);
				cplex.writeSolution(o_pathFileToExport);
			}
			cplex.end();
		} catch (IloException e) {
			e.printStackTrace();
		}
		return tabResult;
	}
	

	/** add subcycles (if they exists)
	 * 
	 * @param nbNode
	 * @param cplex
	 * @param x
	 * @param tabResult
	 * @return true if it exists subCycles
	 */
	public static boolean addNewSubCycleConstraint(int nbNode,IloCplex cplex,IloNumVar[][] x, int[] tabResult){
		int cpt = 0;	// le nb de noeud dans la recherche d'un sous tours
		int i_saved = 0;
		int i = i_saved;
		int j;
		int indice_j;
		boolean hasSubCycle = false;
		
		// de type <i, j> pour avoir une liste [(i1, j1), (i2, j2), ...]
		//HashMap<Integer, Integer> listVariables = new HashMap<Integer, Integer>();
		ArrayList<NodeCouple> listVariables = new ArrayList<NodeCouple>();
		
		boolean[] nodeVisited = new boolean[nbNode];
		boolean[] nodeVisitedInSubCycle = new boolean[nbNode];
		for(int k=0; k<nbNode; k++){
			nodeVisited[k] = false;
			nodeVisitedInSubCycle[k] = false;
		}
		
		while(cpt<nbNode && /*i_saved<nbNode &&*/ i_saved != -1){
			nodeVisited[i] = true;
			nodeVisitedInSubCycle[i] = true;
			j = tabResult[i];
			
			// si j = -1, ca veut dire que tous les noeuds xij pour j=0,...,n-1 sont = à 0
			if(j == -1){
				/** NORMALEMENT NE DOIT JAMAIS ARRIVE -> GERER L'ERREUR **/
				System.out.println("Pb pour i="+i);
				return true;
			}
			else{
				cpt++;
				//listVariables.put(i, j);
				listVariables.add(new NodeCouple(i, j));
				// dans le cas ou on rencontre un sous-tour (cas ou on revient sur le noeud i_saved)
				if(/*j == i_saved*/ nodeVisitedInSubCycle[j] && cpt<nbNode){
					
					try {
						// ajout de la contrainte
						IloLinearNumExpr expr = cplex.linearNumExpr();
						int pos = getNodeInList(listVariables, j);
						if(pos==-1){
							System.out.println("j="+j+", visite[j]="+nodeVisited[j]);
							System.out.println("k=-1 pour j=" + j + ", listVariables="+listVariables);
						}
						while(pos<cpt){
							expr.addTerm(1.0, x[listVariables.get(pos).getN1()][listVariables.get(pos).getN2()]);
							pos++;
							
						}
						/*for(Integer indice_i : listVariables.keySet()){
							indice_j = listVariables.get(indice_i);	
							expr.addTerm(1.0, x[indice_i][indice_j]);
					
						}*/
						
						cplex.addLe(expr, cpt-1);
						
						// mise a jour des variables
						cpt=0;
						i_saved = nextNode(nodeVisited, nbNode);
						i = i_saved;
						listVariables.clear();
						hasSubCycle = true;
						for(int l=0; l<nbNode; l++){
							nodeVisitedInSubCycle[l] = false;
						}
						
					} catch (IloException e) {
						e.printStackTrace();
					}
				}
				
				else{
					/*
					// sous tour
					if(nodeVisited[j]){
						// j'ajoute a partir de la position ou j'ai rencontré le j
						//listVariables.get(0);
					}
					// dans le cas ou on ne rencontre pas de sous-tour : on continue notre recherche
					else{*/
						i = j;
					//}
				}
			}
		}
		return hasSubCycle;
	}
	
	private static int getNodeInList(ArrayList<NodeCouple> listVariables, int j) {
		int n = listVariables.size();
		for(int i=0; i<n; i++){
			if(listVariables.get(i).getN1() == j){
				return i;
			}
		}
		return -1;	// error
	}


	// On connait l'indice i, on cherche l'indice j tel que resultat[i][j] = 1
	/**
	 * 
	 * @param tabResult
	 * @param nbNode
	 * @return
	 */
	public static int searchIndiceJ(double[] tabResult, int nbNode){
		for(int j=0; j<nbNode; j++){
			if(tabResult[j] != 0){
				return j;
			}
		}
		return -1;	// error
	}

	/**
	 * 
	 * @param nodeVisite
	 * @param nbNode
	 * @return 
	 */
	public static int nextNode(boolean[] nodeVisite, int nbNode){
		for(int i=0; i<nbNode; i++){
			if(!nodeVisite[i]){
				return i;
			}
		}
		return -1;	// tous les noeuds ont été visité
	}
	
	/*
	public static void affiche(int i, double[][]tabResult, int nbNode){
		for(int j=0; j<nbNode; j++){
			System.out.println(tabResult[i][j]);
		}
	}
	*/
	
	/**
	 * 
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
						//System.out.println("(" + graph.getTabAdja()[i][j] + ", " + x[i][j] + ")");
						objectiveFunction.addTerm(graph.getTabAdja()[i][j],	x[i][j]);
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
				IloLinearNumExpr expr = cplex.linearNumExpr();
				for (int j = 0; j < graph.getNbNode(); j++) {
					if (i != j) {
						expr.addTerm(1.0, x[i][j]);	// somme
					}
				}
				cplex.addEq(1.0, expr);	// l'égalité = 1 (d'ou addEq, et le 1.0 car on dit que =1)
				
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
	 * @param u 
	 */
	
	private static void setConstraintSubCycle(Graph graph, IloCplex cplex, IloNumVar[][] x, IloNumVar[] u) {
		try {
			for (int i = 1; i < graph.getNbNode(); i++) {
				for (int j = 1; j < graph.getNbNode(); j++) {
					if (i != j) {
						IloLinearNumExpr expr = cplex.linearNumExpr();
						expr.addTerm(1.0, u[i]);
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
