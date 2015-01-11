package ps.project.tsp.vns;

import java.util.ArrayList;

import pne.project.tsp.beans.NodeCouple;

public abstract class VNSAbstract {
	private ArrayList<SolutionVNS> listSolutions;		
	private int Kmax;
	
	public VNSAbstract(int Kmax){
		listSolutions = new ArrayList<SolutionVNS>();
		this.Kmax = Kmax;
	}

	public SolutionVNS vnsAlgorithm(SolutionVNS solutionInitiale, double tmax) {
		double t;
		SolutionVNS x = solutionInitiale.clone();
		SolutionVNS y;
		int k;
		do {
			k = 2;
			do {
				System.out.println("DANS VNS : k=" + k + " et kmax = " + Kmax);
				System.out.println("-----------------------");
				System.out.println("Pour k=" + k);
				System.out.println("---> x = " + x.getPathChosen());
				System.out.println("---> cout = " + x.getPathCost());
				System.out.println("-----------------------");
				
				// on cherche des solutions differentes (et meilleure si possible) de x
				//System.out.println("lambda = " + x.getPenaliteLambda()[0][1]);
				y = findBetterSolution(x, k);
				
				// Changement de voisinage
				if (y.getPathCost() < x.getPathCost()) {
					x = y;
					k=2;
				} 
				else{
					k++;
				}				
				
			} while (k <= Kmax);

			t = System.currentTimeMillis();
		} while (t <= tmax);
		System.out.println("VNS --> solFinale = " + x.getPathChosen());
		return x;
	}

	/**
	 * Teste toutes les combinaisons s'il le faut, mais s'arrete dès qu'une meilleure solution est trouvée
	 * @param x
	 * @param k
	 * @return
	 */
	protected SolutionVNS findBetterSolution(SolutionVNS x, int k){
		int i;
		int n = x.getPathChosen().size()-1;	// n est la derniere position du noeud dans la solution
		
		/**
		 * Remarque : ce sont des combinaisons des positions des arêtes (et non des arêtes réelles)
		 */
		
		// initialiser un tableau ou tout sera a "false" pour la combinaison
		ArrayList<ArrayList<NodeCouple>> listCombinaison = new ArrayList<ArrayList<NodeCouple>>();
		ArrayList<NodeCouple> combinaison = new ArrayList<NodeCouple>();

		// Initialisation de listCombinaison : liste toutes les k combinaisons possibles
		for(i=0; i<n-1; i++){
			combinaison.clear();
			combinaison.add(new NodeCouple(i, i+1));
			searchCombinaisonRecursive(i, i+2, n, k-1, combinaison, listCombinaison);
		}

		return k_transformation(x, k, listCombinaison);
	}
	
	/**
	 * 
	 * @param i : i du for dans la méthode précédente (il ne changera pas dans la récursivité
	 * @param j	: indice qui nous permet de chercher toutes les combinaisons
	 * @param n : nb de noeud dans la solution x
	 * @param k	: k-opt. Au début il est égal à k, à la fin il sera à 0
	 * @param combinaison	: combinaison qu'on regarde actuellement
	 * @param listCombinaison	: on va ajouter combinaison à la liste à la fin
	 */
	protected void searchCombinaisonRecursive(int i, int j, int n, int k, ArrayList<NodeCouple> combinaison, /*HashMap<ArrayList<NodeCouple>, Boolean>*/ ArrayList<ArrayList<NodeCouple>> listCombinaison){
		int next_j;
		NodeCouple nc;
			
		// Cas ou on peut mettre encore des combinaisons
		if(k>0 && (j<n || (j==n && i!=0))){
			while(k>0 && (j<n || (j==n && i!=0))){
				if(j == n){
					next_j = 0;
				}
				else{
					next_j = j+1;
				}
				nc = new NodeCouple(j, next_j);
				combinaison.add(nc);
				searchCombinaisonRecursive(i, j+2, n, k-1, combinaison, listCombinaison);
				combinaison.remove(nc);
				j++;
				
			}
		}
		
		// Cas ou on ne peut pas : on ajoute la combinaison qui est "terminée"
		else{
			// on ajoute que si on a respecter la condition k-opt (on a k permutation si k=0 ici)
			if(k==0){
				listCombinaison.add((ArrayList<NodeCouple>) combinaison.clone());
			}
		}
	}
	
	
	/** Permet de faire une transformation k-opt
	 * @param x : la solution qui doit subir la transformation
	 * @param k : nb de chemin a interdire
	 * @param listCombinaison : liste de toutes les combinaisons possibles
	 * @return le nouveau chemin issu de la transformation k-opt
	 */
	protected abstract SolutionVNS k_transformation(SolutionVNS x, int k, ArrayList<ArrayList<NodeCouple>> listCombinaison);

	public ArrayList<SolutionVNS> getListSolutions() {
		return listSolutions;
	}
	
	public void setListSolutions(ArrayList<SolutionVNS> listSolutions) {
		this.listSolutions = listSolutions;
	}

	public int getKmax() {
		return Kmax;
	}

	public void setKmax(int kmax) {
		Kmax = kmax;
	}
}
