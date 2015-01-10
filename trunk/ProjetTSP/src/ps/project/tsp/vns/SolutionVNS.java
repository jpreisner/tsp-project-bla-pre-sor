package ps.project.tsp.vns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pne.project.tsp.beans.Graph;
import pne.project.tsp.beans.NodeCouple;
import pne.project.tsp.utils.FileReader;

public class SolutionVNS {
	private Graph graph_scenario;
	private ArrayList<Integer> pathChosen;	// ex pour un graphe à 4 noeuds : 1-2-3-4
	private double pathCost;
	
	// penalite utile pour la partie stochastique
	private double[][] penaliteLambda;
	private double[][] penaliteRo;
	
	public SolutionVNS(Graph g, ArrayList<Integer> pathChosen, double pathCost){
		graph_scenario = g;
		this.pathChosen = pathChosen;
		this.pathCost = pathCost;
	}
	
	public SolutionVNS(Graph g, ArrayList<Integer> pathChosen){
		graph_scenario = g;
		this.pathChosen = pathChosen;
		this.pathCost = calculPathCost();
	}
	
	public SolutionVNS(Graph g){
		graph_scenario = g;
		pathChosen = new ArrayList<Integer>();
		pathCost = 0.0;
	}
	
	
	/**
	 * Faire les méthodes pour savoir si circuit hamiltonien
	 * 		- sous méthode sur les aretes entrantes
	 * 		- sous méthode sur les aretes sortantes
	 * 		- sous méthode pour les sous tours
	 */
	
	/**
	 *  Comparator used in glouton algorithm
	 */
	private Comparator<NodeCouple> compareNodeCouple = new Comparator<NodeCouple>() {

		@Override
		public int compare(NodeCouple arg0, NodeCouple arg1) {
			if (arg0.getCostEdge() < arg1.getCostEdge()) {
				return -1;
			} else if (arg0.getCostEdge() > arg1.getCostEdge()) {
				return 1;
			} else {
				return 0;
			}
		}
	};
	
	/**Algorithme de Glouton lancé au tout début du programme
	 * 
	 * @param graph_scenario
	 * @return
	 */
	public ArrayList<Integer> gloutonAlgorithm() {
		ArrayList<NodeCouple> alNodeCouple = new ArrayList<NodeCouple>();
		boolean[] tabInner = new boolean[graph_scenario.getNbNode()];
		boolean[] tabOuter = new boolean[graph_scenario.getNbNode()];
		ArrayList<NodeCouple> tmpSolution = new ArrayList<NodeCouple>();
		
		/* recuperation de toutes les arêtes*/
		for(int i=0;i<graph_scenario.getNbNode();i++){
			for(int j=0;j<graph_scenario.getNbNode();j++){
				if(i!=j && graph_scenario.getTabAdja()[i][j]>0){
					alNodeCouple.add(new NodeCouple(i, j, graph_scenario.getTabAdja()[i][j]));
				}
			}
		}
		
		/* tri des arêtes selon le poids */
		Collections.sort(alNodeCouple, compareNodeCouple);
		
		/* construction de la solution gloutonne*/
		
		/* TODO verifier les sous tours*/
		for(NodeCouple e : alNodeCouple){
			
			/* aucun des 2 déja occupes*/
			if(!tabOuter[e.getN1()] && !tabInner[e.getN2()]){
				
				/* sous tours*/
				if((!tabInner[e.getN1()] || !tabOuter[e.getN2()])|| endOfFillingGloutonTab(tabInner, tabOuter) || !isSubCycle(e,tmpSolution)){
					tmpSolution.add(e);
					tabOuter[e.getN1()] = true;
					tabInner[e.getN2()] = true;
				}
			}			

		}
		
		
		
		/**
		 * Remarque : on souhaite que la solution soit de la forme 1-2-3-4 et non (1,2) (2,3) (3,4) (4,1)
		 */
		ArrayList<NodeCouple> result = sortSolution(tmpSolution);
		System.out.println("result = " + result);

		ArrayList<Integer> solution = new ArrayList<Integer>();
		for(NodeCouple nc : result){
			solution.add(nc.getN1());
		}
		System.out.println("solution : "+solution);
		
		return solution;
	}

	private ArrayList<NodeCouple> sortSolution(ArrayList<NodeCouple> param) {
		ArrayList<NodeCouple> result = new ArrayList<NodeCouple>();
		result.add(param.get(0));
		
		int valueToSearch = 0;
		
		while(result.size() < param.size()){
			for (NodeCouple nodeCouple : param) {
				if(nodeCouple.getN1() == result.get(result.size()-1).getN2()){
					result.add(nodeCouple);
				}
			}
		}
		
		return result;		
	}

	/**
	 * @param e
	 * @param result
	 * @return true if the param list<NodeCouple> contains a sub-cycle with the node e
	 */
	private boolean isSubCycle(NodeCouple e, ArrayList<NodeCouple> result) {
		/*Partir de e.getN2()
		 * et faire un tour jusqu'a retrouver e.getN1()
		 */
		int valueToFind = e.getN1();
		int nodeTmp = e.getN2();
		NodeCouple nodeCoupleTmp = null;
		
		/* trouver le premier */
		for (NodeCouple nodeCouple : result) 
		{
			if (nodeCouple.getN1()==nodeTmp)
			{
				if(nodeCouple.getN2()==valueToFind){
					return true;
				}
				nodeCoupleTmp = nodeCouple;
			}
		}	
		
		/* faire tout le tour.*/
		for (int i = 0; i < result.size(); i++) 
		{
			for (NodeCouple nodeCouple : result) 
			{

				if (nodeCouple.getN1()==nodeCoupleTmp.getN2())
				{
					if(nodeCouple.getN2()==valueToFind){
						return true;
					}
					nodeCoupleTmp = nodeCouple;
				}
			}			
		}

		return false;
	}

	/**
	 * @param tabInner
	 * @param tabOuter
	 * @return true if only one element is to add to the two tabs
	 */
	private boolean endOfFillingGloutonTab(boolean[] tabInner, boolean[] tabOuter) {
		int a=0,b=0;
		for (int i = 0; i < tabOuter.length; i++) {
			if(!tabInner[i]){
				a++;
			}
		}
		
		for (int i = 0; i < tabOuter.length; i++) {
			if(!tabOuter[i]){
				b++;
			}
		}
		return (a==1 && b==1);
	}

	public double getPathCost() {
		return pathCost;
	}
	
	public double calculPathCost(){
		double cost = 0.0;
		int n = pathChosen.size();
		for(int i=0; i<n-1; i++){
			cost+=graph_scenario.getTabAdja()[pathChosen.get(i)][pathChosen.get(i+1)];
		}
		cost+=graph_scenario.getTabAdja()[pathChosen.get(n-1)][pathChosen.get(0)];
		return cost;
	}

	public void initPenalite(Graph graphInitial){
		int n = graph_scenario.getNbNode();
		penaliteLambda = new double[n][n];
		penaliteRo = new double[n][n];
		int i,j;
		double coutMax = graph_scenario.getCoutMax();
		
		/**
		 * !! Les penalites sappliquent a toutes les aretes deterministes
		 * 		--> c peut etre que pour les aretes deterministes de la solution
		 */
		
		// initialisation de lambda
		for(i=0; i<n; i++){
			for(j=0; j<n; j++){
				// on veut (i,j) deterministe
				if(i!=j && !graph_scenario.getTabStoch()[i][j]){
					penaliteLambda[i][j] = 2*coutMax;
				}
				else{
					penaliteLambda[i][j] = -1;	// par defaut
				}
			}
		}
		
		// initialisation de ro
		for(i=0; i<n; i++){
			for(j=0; j<n; j++){
				// on veut (i,j) deterministe
				if(i!=j && !graph_scenario.getTabStoch()[i][j]){
					penaliteRo[i][j] = graphInitial.getTabAdja()[i][j]/2;
				}
				else{
					penaliteRo[i][j] = -1;	// par defaut
				}
			}
		}
	}

	public void appliquePenalite(SolutionVNS solReference, int beta){
		int i,j;
		int n = graph_scenario.getNbNode();
		
		/**
		 * !! Les penalites sappliquent a toutes les aretes deterministes
		 * 		--> c peut etre que pour les aretes deterministes de la solution
		 */
		
		// itération sur lambda
		for(i=0; i<n; i++){
			for(j=0; j<n; j++){
				// on veut (i,j) deterministe
				if(i!=j && !graph_scenario.getTabStoch()[i][j]){
					penaliteLambda[i][j] = penaliteLambda[i][j] + penaliteRo[i][j]*(areteDansSolution(pathChosen, i, j) - areteDansSolution(solReference.getPathChosen(), i, j));
				}
			}
		}
		
		// itération sur ro
		for(i=0; i<n; i++){
			for(j=0; j<n; j++){
				// on veut (i,j) deterministe
				if(i!=j && !graph_scenario.getTabStoch()[i][j]){
					penaliteRo[i][j] *= beta;
				}
			}
		}
	}
	
	public ArrayList<Integer> getPathChosen() {
		return pathChosen;
	}

	public Graph getGraph_scenario() {
		return graph_scenario;
	}
	
	public void setPathCost(double cost){
		pathCost = cost;
	}
	
	public void setPathChosen(ArrayList<Integer> pathChosen){
		this.pathChosen = pathChosen;
		this.pathCost = calculPathCost();
	}
	
	public SolutionVNS clone(){
		return new SolutionVNS(graph_scenario, pathChosen, pathCost);
	}
	
	public int areteDansSolution(ArrayList<Integer> sol, int i, int j){
		for(int a=0; a<sol.size()-1; a++){
			if(sol.get(a) == i){
				if(sol.get(a+1) == j)	return 1;
				return 0;
			}
		}
		if(sol.get(sol.size()-1) == i && sol.get(0) == j){
			return 1;
		}
		return 0;
	}
	
	public static void main(String[] args){
		Graph g = FileReader.buildGraphFromXml("data/XML/gr17.xml");
		SolutionVNS sol = new SolutionVNS(g);
		sol.setPathChosen(sol.gloutonAlgorithm());
		System.out.println(sol.getPathChosen() + "  cout = " + sol.getPathCost());
	}
}
