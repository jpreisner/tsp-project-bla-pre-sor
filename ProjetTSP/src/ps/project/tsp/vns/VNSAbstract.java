package ps.project.tsp.vns;

import java.util.ArrayList;
import java.util.HashMap;


import pne.project.tsp.beans.NodeCouple;

public class VNSAbstract {
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
				System.out.println("-----------------------");
				System.out.println("Pour k=" + k);
				System.out.println("---> x = " + x.getPathChosen());
				System.out.println("---> cout = " + x.getPathCost());
				System.out.println("-----------------------");
				
				// on cherche des solutions differentes (et meilleure si possible) de x
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
	protected SolutionVNS k_transformation(SolutionVNS x, int k, ArrayList<ArrayList<NodeCouple>> listCombinaison){
	
		// liste des arêtes interdites (contient les positions des arêtes dans la solution x, et non les arêtes
		// en elles-mêmes
		ArrayList<NodeCouple> combinaison = new ArrayList<NodeCouple>();

		int n = x.getPathChosen().size();	// taille du graphe
		int i, j;
		boolean nodePositionPresent[] = new boolean[n];		// permet d'éviter d'avoir des arêtes déjà mises
		
		/**
		 * On repète tant qu'on a pas trouver une meilleure solution que x ou alors qu'on ait tester 
		 * toutes les combinaisons
		 */
	
		do{
			// Initialisation de nodePositionPresent
			for(i=0; i<n; i++){
				nodePositionPresent[i] = false;
			}
			
			/**
			 * Tirer au hasard une combinaison parmi toutes les combinaisons
			 */
			int nbCombinaison = listCombinaison.size();
			j = (int) (Math.random() * nbCombinaison);
			combinaison = (ArrayList<NodeCouple>) listCombinaison.get(j).clone();
			listCombinaison.remove(j);
		
			SolutionVNS s = new SolutionVNS(x.getGraph_scenario());
			
			/**
			 * Construction de la nouvelle solution
			 */
	
			// on avance de la 1ere jusqu'à la 1ère arête interdite dans la solution x
			i = 0;
			while(i<combinaison.get(0).getN1()){
				s.getPathChosen().add(x.getPathChosen().get(i));
				i++;
			}
			
			i=0;
			j=k-1;
			int pos_val1, pos_val2;
			int changement;	// permet de savoir s'il faut incrémenter ou décrémenter
			while(i<=(k/2)+1){
				// Dans le cas où on arrive à la fin et que k est impair
				if(i == j && k%2 != 0){
					s.getPathChosen().add(x.getPathChosen().get(combinaison.get(i).getN1()));
					
					// Enregistrement de la position Ai
					pos_val1 = combinaison.get(i).getN1();
					
					// Enregistrement de la position Bk
					pos_val2 = combinaison.get(k-1).getN2();
					
					// Ajout de Bk inclu jusqu'à la fin de la solution x qu'il reste
					if(pos_val2 != 0){
						for(i=pos_val2; i<x.getPathChosen().size(); i++){
							s.getPathChosen().add(x.getPathChosen().get(i));
						}
					}
					//System.out.println("s = " + s.getPathChosen());					
					break;
				}
				
				// ajout de Ai
				s.getPathChosen().add(x.getPathChosen().get(combinaison.get(i).getN1()));
				
				// Enregistrement des positions de Aj et Bj-1 car il faut enregistrer toutes les valeurs
				// comprises dedans
				pos_val1 = combinaison.get(j).getN1();
				pos_val2 = combinaison.get(j-1).getN2();
				
				// test pour savoir s'il faut incrémenter ou décrémenter pour aller de Aj à Bj-1
				if(pos_val1>pos_val2){
					changement = -1;
				}
				else{
					changement = 1;
				}
				
				// ajout de Aj inclu jusqu'à Bj-1 exclu
				while(pos_val1!=pos_val2){
					s.getPathChosen().add(x.getPathChosen().get(pos_val1));
					pos_val1+=changement;
				}

				// ajout de Bj-1
				s.getPathChosen().add(x.getPathChosen().get(pos_val2));

				// Enregistrement de Bi
				pos_val1 = combinaison.get(i).getN2();
				
				// Dans le cas ou on arrive à la fin et que k est pair
				if (i == (k / 2) - 1 && k % 2 == 0) {
					// Enregistrement de Bk
					pos_val2 = combinaison.get(k-1).getN2();
				
					// Ajout de Bk inclu jusqu'à la fin de la solution x qu'il reste
					if(pos_val2 != 0){
						for (i = pos_val2; i < x.getPathChosen().size(); i++) {
							s.getPathChosen().add(x.getPathChosen().get(i));
						}
					}
					break;
				}
				
				// Rajouter de Bi-> jusqu'à Ai+1 qui va être l'étape suivante

				// Enregistrement de Ai+1
				pos_val2 = combinaison.get(i+1).getN1();
				
				// Ajout de Bi inclu jusqu'à Ai+1 exclu
				// Remarque : on sait que pos_val1 < pos_val2
				while (pos_val1 != pos_val2) {
					s.getPathChosen().add(x.getPathChosen().get(pos_val1));
					pos_val1++;
				}

				// on passe à "l'étape suivante"
				i++;
				j--;
			}
			
			/**
			 * Calcul du cout total de la nouvelle solution
			 */
			s.setPathCost(s.calculPathCost());
			
			/**
			 * Si la solution s recherchée est meilleure que la solution initiale x, on renvoie s
			 */
			if(s.getPathCost()<x.getPathCost()){
				System.out.println("Changement ! pour k="+k + " et cout="+s.getPathCost());
				System.out.println("------combinaison choisie = " + combinaison);
				return s;
			}
			
		} while(listCombinaison.size()>0); // la condition d'arret : qd tt a été testé
		
		/**
		 * Si aucune meilleure solution n'a été trouvé, on renvoie x
		 */
		return x;
	}

	public ArrayList<SolutionVNS> getListSolutions() {
		return listSolutions;
	}

	public int getKmax() {
		return Kmax;
	}

	public void setKmax(int kmax) {
		Kmax = kmax;
	}

	public void setListSolutions(ArrayList<SolutionVNS> listSolutions) {
		this.listSolutions = listSolutions;
	}
}
