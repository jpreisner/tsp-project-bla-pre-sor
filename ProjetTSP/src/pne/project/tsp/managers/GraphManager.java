package pne.project.tsp.managers;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.DoubleParam;

import java.util.ArrayList;
import java.util.Collections;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.beans.NodeCouple;
import pne.project.tsp.utils.Stats;
import ps.project.tsp.vns.SolutionVNS;
import ps.project.tsp.vns.VNSDeterminist;
import ps.project.tsp.vns.VNSStochastic;

public class GraphManager {
	private double solutionValue;
	private int resolutionDuration;

	public GraphManager() {
	}

	/**
	 * PS
	 */
	
	/**
	 * 
	 * @param g
	 * @param aleas : pourcentage d'aretes deterministes
	 */
	public ArrayList<Integer> resolutionTSP_vns(Graph g, int aleas, int nbScenario, int Kmax, double tmax){
		if(aleas < 0){
			aleas = 0;
		}
		if(aleas > 100){
			aleas = 100;
		}
		
		// Déterministe
		if(aleas == 100){
			// Initialisation de l'attribut qui va nous servir a enregistrer Glouton
			SolutionVNS solutionInitiale = new SolutionVNS(g);	// on passe en parametre le graphe de base
			
			// Enregistrement de de la solution gloutonne
			solutionInitiale.setPathChosen(solutionInitiale.gloutonAlgorithm());
			
			System.out.println("GM --> solInit = " + solutionInitiale.getPathChosen());
			// Résolution avec la méthode VNS du problème deterministe
			VNSDeterminist vnsD = new VNSDeterminist(Kmax);
			vnsD.getListSolutions().add(solutionInitiale);
			return vnsD.vnsAlgorithm(solutionInitiale, tmax).getPathChosen();
		}
		// Stochastique
		else{
			System.out.println("STOCHA");
			double proba_scenario = 1/nbScenario;
			ArrayList<Integer> fusion = new ArrayList<Integer>();
			
			// Initialisation des arêtes déterministes
			initAretesDeterministes(g, aleas);
			
			// Calcul de l'écart type des arêtes stochastiques
			double ecartType = Stats.ecartType(g);
			
			// Solution de reference (glouton)
			SolutionVNS solutionRef = new SolutionVNS(g);	
			solutionRef.setPathChosen(solutionRef.gloutonAlgorithm());
			
			System.out.println("GM --> solInit = " + solutionRef.getPathChosen());
			
			VNSStochastic vnsS = new VNSStochastic(Kmax);
			vnsS.getListSolutions().add(solutionRef);	
			
			// Génération de tous les scénarios + glouton sur chaque scénario
			generationAllScenarios(vnsS, g, nbScenario, ecartType);
			
			// Initialisation des pénalités
			initAllPenalites(vnsS);
			
			SolutionVNS sol_scenario;
			int t=0;
			// Recherche d'une solution
			do{
				System.out.println("Recherche sol + " + t + " nbS = " + nbScenario);
				for(int i=1; i<=nbScenario; i++){
					System.out.println("---------------------------------------------------");
					System.out.println("scenario + " + i);
					System.out.println("---------------------------------------------------");
					
					// Application des pénalités
					if(t!=0){
						vnsS.getSolutionScenario(i).calculPenalite(vnsS.getSolutionRef(), 2);
					}
					
					// Appel de VNS
					sol_scenario = vnsS.vnsAlgorithm(vnsS.getSolutionScenario(i), tmax);
					
					// le nouveau chemin et le nouveau cout sont sauvegardé
					vnsS.setSolutionScenario(i, sol_scenario);
					
					
					
					// Calcul fonction obj (?!)
					// UTILISER LES PROBA !
				}
				
				// Fusionner toutes les solutions
				fusion.clear();
				fusion = fusionSolutionsScenarios(vnsS);
				
				System.out.println("---------------------------> fusion = " + fusion);
				
				// Mise a jour de la solution de reference (?!)
				//vnsS.getListSolutions().get(0).setPathChosen(fusion);
				
				t++;
			} while(!allScenarioOntMemesAretesDeter(vnsS.getSolutionRef(), vnsS.getListSolutions()));
			
		 // }while(!aLesMemesAretesDeter(vnsS.getSolutionRef(), fusion));	// La condition d'arret : si la fusion possede les memes aretes deterministes que la sol de ref
		
		/**
		 * PB ici : je sais pas lequel des 2 while mettre
		 * 	--> est ce que la condition d'arret c'est que la fusion ait les memes aretes det (2eme while)
		 * --> ou est ce que la condition d'arret ce que tous les scenarios ait les memes aretes det (1er while)
		 */
		
			System.out.println("FIN STOCHA");
			System.out.println("Calcul Cout fusion : "+calculCostFusion(vnsS, fusion));
			return fusion;
		}
	}
	
	private double calculCostFusion(VNSStochastic vnsS, ArrayList<Integer> listFusion){
		int nbScenarios = vnsS.getListSolutions().size()-1;
		double result = 0;
		ArrayList<SolutionVNS> listSolutions = vnsS.getListSolutions();
		/* tous les elements de la liste fusionnee*/
		for (int i = 0; i < listFusion.size()-1; i++) {
			double sumCostEdge = 0;
			for (int j = 1; j < listSolutions.size(); j++) {
				sumCostEdge+= listSolutions.get(j).getGraph_scenario().getTabAdja()[listFusion.get(i)][listFusion.get(i+1)];
			}
			System.out.println("cout moyen de l'arête : "+listFusion.get(i)+","+listFusion.get(i+1)+" : "+(sumCostEdge/nbScenarios));
			result += (sumCostEdge/nbScenarios);
		}
		
		/* dernier element pour faire le cycle*/
		double sumCostEdge2 = 0;
		for (int j = 1; j < listSolutions.size(); j++) {
			sumCostEdge2+= listSolutions.get(j).getGraph_scenario().getTabAdja()[listFusion.get(listFusion.size()-1)][listFusion.get(0)];
		}
		result += (sumCostEdge2/nbScenarios);
		
		return result;		
	}
	
	private void initAllPenalites(VNSStochastic vnsS) {
		for(int i=1; i<vnsS.getListSolutions().size(); i++){
			vnsS.getSolutionScenario(i).initPenalite(vnsS.getSolutionRef().getGraph_scenario());
		}
	}

	/**
	 * @param vnsS
	 * @return
	 */
	private ArrayList<Integer> fusionSolutionsScenarios(VNSStochastic vnsS) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int nbNode = vnsS.getListSolutions().get(0).getGraph_scenario().getNbNode();
		SolutionVNS solVns;
		for(int i=0;i<nbNode;i++){
			
			/* on s'occupe de l'element i*/
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for(int j=1; j<vnsS.getListSolutions().size(); j++){
				solVns = vnsS.getSolutionScenario(j);	
				tmp.add(solVns.getPathChosen().get(i));
			}
			result.add(getMaxNode(tmp,result));
		}
		
		return result;
	}

	private Integer getMaxNode(ArrayList<Integer> tmp, ArrayList<Integer> result) {
		int[][] tabOccurence = new int[tmp.size()][2]; 		
		int indiceMax;
		int valueMax;
		
		/* on ecrit les frequences */
		for (int i=0;i<tmp.size();i++) {
			tabOccurence[i][0] = tmp.get(i);
			tabOccurence[i][1] = Collections.frequency(tmp, tmp.get(i));
		}
		
		/* on prends la meilleure valeur sauf si elle a déja été choisie */
		indiceMax = 0;
		valueMax = 0;
		/* on lit la valeur max */
		for(int j=0;j<tabOccurence.length;j++){
			if(tabOccurence[j][1]>valueMax && !result.contains(tabOccurence[j][0])){
				indiceMax = j;
				valueMax = tabOccurence[j][1];				
			}
		}
			
		
		return tabOccurence[indiceMax][0];
	}

	public boolean allScenarioOntMemesAretesDeter(SolutionVNS solReference, ArrayList<SolutionVNS> listSolScenario){
		SolutionVNS scenario;
		for(int i=1; i<listSolScenario.size(); i++){
			scenario = listSolScenario.get(i);
			if(!aLesMemesAretesDeter(solReference, scenario.getPathChosen())){
				return false;
			}
		}
		return true;
	}
	public boolean aLesMemesAretesDeter(SolutionVNS solReference, ArrayList<Integer> solFusion){
		ArrayList<NodeCouple> listSolRef = listAretesDeter(solReference.getPathChosen(), solReference.getGraph_scenario());
		ArrayList<NodeCouple> listSolFus = listAretesDeter(solFusion, solReference.getGraph_scenario());
		
		Collections.sort(listSolRef);
		Collections.sort(listSolFus);
		
		return listSolRef.equals(listSolFus);
	}
	
	public ArrayList<NodeCouple> listAretesDeter(ArrayList<Integer> solution, Graph g){
		ArrayList<NodeCouple> list = new ArrayList<NodeCouple>();
		int n1, n2;
		for(int i=0; i<solution.size()-1; i++){
			n1 = solution.get(i);
			n2 = solution.get(i+1);
			if(!g.getTabStoch()[n1][n2]){
				list.add(new NodeCouple(n1, n2));
			}
		}

		n1 = solution.get(solution.size()-1);
		n2 = solution.get(0);
		if(!g.getTabStoch()[n1][n2]){
			list.add(new NodeCouple(n1, n2));
		}
		return list;
	}
	
	/**
	 * Génère tous les scenarios, et détermine une solution initiale de chaque scenario (par glouton)
	 * @param vnsS
	 * @param g
	 * @param nbScenario
	 * @param ecartType
	 */
	public void generationAllScenarios(VNSStochastic vnsS, Graph g,	int nbScenario, double ecartType) {
		// Génération des K scénarios
		SolutionVNS sol;
		Graph graph_scenario;
		for(int i=0; i<nbScenario; i++){
			
			// génération d'un scénario
			graph_scenario = genereScenario(g, ecartType);
			
			// initialisation d'un attribut solutionVNS qui contient le graphe du scenario i
			sol = new SolutionVNS(graph_scenario);
			
			// initialisation de la solution a l'aide de glouton appliqué au graphe du scenario i
			sol.setPathChosen(sol.gloutonAlgorithm());
			
			// ajout dans VNS du scenario i
			vnsS.getListSolutions().add(sol);
		}
	}

	/** Permet de générer un graphe correspondant a un scenario
	 * 
	 * @param g
	 * @param ecartType
	 * @return le graphe généré
	 */
	public Graph genereScenario(Graph g, double ecartType){
		double cij;
		//Graph graph_scenario = (Graph) g.clone();
		int nbNode = g.getNbNode();
		double tabGraph[][] = new double[nbNode][nbNode];
		
		for(int i=0; i<nbNode; i++){
			for(int j=0; j<nbNode; j++){
				// Dans le cas ou (i,j) est stochastique = on lui attribut une valeur
				if(i!=j && g.isEdgeStochastic(i, j)){
					cij = g.getTabAdja()[i][j];
					tabGraph[i][j] = Stats.getRandValueBetween(cij-3*ecartType, cij+3*ecartType);
				}
				else{
					tabGraph[i][j] = g.getTabAdja()[i][j];
				}
			}
		}
		return new Graph(tabGraph, nbNode, g.getTabStoch().clone(), g.getPercentageDeterminist());
	}
	
	/**
	 * Détermine de façon aléatoire les arêtes déterministes du graphe
	 * @param g
	 * @param aleas : pourcentage des arêtes déterministes (compris entre 0 et 100
	 */
	private void initAretesDeterministes(Graph g, int aleas) {
		int nbNodeDeterminist = g.getNbNode()*aleas/100;
		int edgeRandom;
		ArrayList<NodeCouple> listEdge = allEdge(g);
		
		// on met toutes les aretes en stochastique pour initialiser les aretes deterministes
		g.initTabStoch(true);
		
		for(int i=0; i<nbNodeDeterminist; i++){
			edgeRandom = (int) (Math.random()*listEdge.size());
			g.getTabStoch()[listEdge.get(edgeRandom).getN1()][listEdge.get(edgeRandom).getN2()] = false;
			listEdge.remove(edgeRandom);
		}
	}
	
	/** Méthode utile pour le tirage au sort des arêtes déterministes
	 *
	 * @param g
	 * @return une liste de toutes les arêtes du graphe
	 */
	public ArrayList<NodeCouple> allEdge(Graph g){
		ArrayList<NodeCouple> listEdge = new ArrayList<NodeCouple>();
		for(int i=0; i<g.getNbNode(); i++){
			for(int j=0; j<g.getNbNode(); j++){
				if(i!=j && g.getTabAdja()[i][j] > 0){
					listEdge.add(new NodeCouple(i, j));
				}
			}
		}
		return listEdge;
	}

	
	
	// il faut résoudre le graphe chargé (appel de VNS deterministe)
	
	// si le X% = 0 ==> fin
	
	// sinon
		// il faut tirer au sort les arêtes deterministes
	
		// génération des K scenarios
	
			// calcul ecart type et tirage au sort des valeurs
	
			// VNS stochastique + pénalité
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * PNE
	 */
	
	/**
	 * Method called to write Linear program with graph in param and write it in
	 * file in param.
	 * 
	 * @param i_graph
	 * @param o_pathFileToExport
	 * @param objectiveValue
	 * @return
	 */
	public int[] writeLinearProgram(Graph i_graph, String o_pathModelToExport,
			String o_pathFileToExport) {
		int[] tabResult = new int[i_graph.getNbNode()];
		IloCplex cplex;
		try {
			cplex = new IloCplex();
			IloNumVar[][] x = new IloNumVar[i_graph.getNbNode()][];

			/* Variables Initialisation */
			String[][] varName = new String[i_graph.getNbNode()][i_graph
					.getNbNode()];
			initVarNameTab(i_graph.getNbNode(), varName);

			for (int i = 0; i < i_graph.getNbNode(); i++) {
				x[i] = cplex.boolVarArray(i_graph.getNbNode(), varName[i]);
			}

			/* Permet de ne renvoyer que des 1 et 0 */
			cplex.setParam(DoubleParam.EpInt, 0.0);
			if(i_graph.getNbNode()>=100){
//				cplex.setParam(IloCplex.IntParam.TimeLimit, 0.4);
				cplex.setParam(IloCplex.DoubleParam.TreLim, 0.4);
				cplex.setParam(IloCplex.IntParam.IntSolLim, 1);
//				cplex.setParam(IloCplex.IntParam.Threads, 1);
			}

			setObjectiveFonction(i_graph, cplex, x);
			setConstraintOuterEdge(i_graph, cplex, x);
			setConstraintInnerEdge(i_graph, cplex, x);

			long startTime = System.nanoTime();

			cplex.setOut(null);
			cplex.solve();

			for (int i = 0; i < i_graph.getNbNode(); i++) {
				tabResult[i] = searchIndiceJ(cplex.getValues(x[i]), i_graph.getNbNode());
			}
			while (addNewSubCycleConstraint(i_graph.getNbNode(), cplex, x, tabResult)) {
				cplex.setOut(null);
				cplex.solve();
				// Enregistrement du résultat dans tabResult
				tabResult = new int[i_graph.getNbNode()]; // pas besoin?
				for (int i = 0; i < i_graph.getNbNode(); i++) {
					tabResult[i] = searchIndiceJ(cplex.getValues(x[i]),
							i_graph.getNbNode());
				}
			}
			long stopTime = System.nanoTime();
			//System.out.println(((stopTime - startTime) / 1000000000)+ " seconds");
			this.resolutionDuration = (int) ((stopTime - startTime) / 1000000000);
			//System.out.println("valeur chemin optimal : " + cplex.getObjValue());
			solutionValue = cplex.getObjValue();
			cplex.exportModel(o_pathModelToExport);
			cplex.writeSolution(o_pathFileToExport);
			cplex.end();
		} catch (IloException e) {
			e.printStackTrace();
		}
		return tabResult;
	}

	
	
	/**
	 * 
	 * @param nbNode
	 * @param varName
	 */
	private static void initVarNameTab(int nbNode, String[][] varName) {
		for (int i = 0; i < nbNode; i++) {
			for (int j = 0; j < nbNode; j++) {
				varName[i][j] = "x" + i + ";" + j;
			}
		}
	}

	/**
	 * Write objective function
	 * 
	 * @param graph
	 * @param cplex
	 * @param x
	 */
	private static void setObjectiveFonction(Graph graph, IloCplex cplex,
			IloNumVar[][] x) {
		IloLinearNumExpr objectiveFunction;
		try {
			objectiveFunction = cplex.linearNumExpr();

			for (int i = 0; i < graph.getNbNode(); i++) {
				for (int j = 0; j < graph.getNbNode(); j++) {
					objectiveFunction.addTerm(graph.getTabAdja()[i][j], x[i][j]);
				}
			}
			cplex.addMinimize(objectiveFunction);
		} catch (IloException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write the first constraint : each node have only one edge going out
	 * 
	 * @param graph
	 * @param cplex
	 * @param x
	 */
	private static void setConstraintOuterEdge(Graph graph, IloCplex cplex,
			IloNumVar[][] x) {
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
	 * 
	 * @param graph
	 * @param cplex
	 * @param x
	 */
	private static void setConstraintInnerEdge(Graph graph, IloCplex cplex,
			IloNumVar[][] x) {
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
	 * add subcycles (if they exists)
	 * 
	 * @param nbNode
	 * @param cplex
	 * @param x
	 * @param tabResult
	 * @return true if it exists subCycles
	 */
	public static boolean addNewSubCycleConstraint(int nbNode, IloCplex cplex,
			IloNumVar[][] x, int[] tabResult) {
		int cpt = 0; // le nb de noeud dans la recherche d'un sous tours
		int i_saved = 0;
		int i = i_saved;
		int j;
		boolean hasSubCycle = false;

		ArrayList<NodeCouple> listVariables = new ArrayList<NodeCouple>();

		boolean[] nodeVisited = new boolean[nbNode];
		boolean[] nodeVisitedInSubCycle = new boolean[nbNode];
		for (int k = 0; k < nbNode; k++) {
			nodeVisited[k] = false;
			nodeVisitedInSubCycle[k] = false;
		}

		while (cpt < nbNode && i_saved != -1) {
			nodeVisited[i] = true;
			nodeVisitedInSubCycle[i] = true;
			j = tabResult[i];

			// si j = -1, ca veut dire que tous les noeuds xij pour j=0,...,n-1
			// sont = à 0
			if (j == -1) {
				return true;
			} else {
				cpt++;
				listVariables.add(new NodeCouple(i, j));
				// dans le cas ou on rencontre un sous-tour (cas ou on revient
				// sur le noeud i_saved)
				if (nodeVisitedInSubCycle[j] && cpt < nbNode) {
					try {
						// ajout de la contrainte
						IloLinearNumExpr expr = cplex.linearNumExpr();
						int pos = getNodeInList(listVariables, j);
						while (pos < cpt) {
							expr.addTerm(1.0, x[listVariables.get(pos).getN1()][listVariables.get(pos).getN2()]);
							pos++;
						}

						cplex.addLe(expr, cpt - 1);

						// mise a jour des variables
						cpt = 0;
						i_saved = nextNode(nodeVisited, nbNode);
						i = i_saved;
						listVariables.clear();
						hasSubCycle = true;
						for (int l = 0; l < nbNode; l++) {
							nodeVisitedInSubCycle[l] = false;
						}

					} catch (IloException e) {
						e.printStackTrace();
					}
				}

				else {
					i = j;
				}
			}
		}
		return hasSubCycle;
	}

	private static int getNodeInList(ArrayList<NodeCouple> listVariables, int j) {
		int n = listVariables.size();
		for (int i = 0; i < n; i++) {
			if (listVariables.get(i).getN1() == j) {
				return i;
			}
		}
		return -1; // error
	}

	/**
	 * On connait l'indice i, on cherche l'indice j tel que resultat[i][j] = 1
	 * @param tabResult
	 * @param nbNode
	 * @return
	 */
	public static int searchIndiceJ(double[] tabResult, int nbNode) {
		for (int j = 0; j < nbNode; j++) {
			if (tabResult[j] != 0) {
				return j;
			}
		}
		return -1; // error
	}

	/**
	 * 
	 * @param nodeVisite
	 * @param nbNode
	 * @return
	 */
	public static int nextNode(boolean[] nodeVisite, int nbNode) {
		for (int i = 0; i < nbNode; i++) {
			if (!nodeVisite[i]) {
				return i;
			}
		}
		return -1; // tous les noeuds ont été visité
	}


	public double getSolutionValue() {
		return solutionValue;
	}

	public int getResolutionDuration() {
		return resolutionDuration;
	}

}
