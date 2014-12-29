package ps.project.tsp.vns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


import pne.project.tsp.beans.NodeCouple;

public abstract class VNSAbstract {
	private ArrayList<SolutionVNS> listSolutions;		
	private int Kmax;

	public SolutionVNS vnsAlgorithm(double tmax) {
		double t;
		SolutionVNS x;
		SolutionVNS y;
		int k;
		do {
			k = 1;
			do {
				x = initialSolution(k);
				y = findBetterSolution(x, k);
				changeNeighbourhood(x, y, k);
			} while (k == Kmax);

			t = System.currentTimeMillis();
		} while (t > tmax);
		return x;
	}

	protected abstract SolutionVNS initialSolution(int k);

	protected SolutionVNS findBetterSolution(SolutionVNS x, int k){
		int i, j;
		int n = x.getPathChosen().size();
		/**
		 * Remarque : ce sont des combinaisons des positions des arêtes (et non des arêtes réelles)
		 */
		
		// initialiser un tableau ou tout sera a "false" pour la combinaison
		HashMap<ArrayList<NodeCouple>, Boolean> listCombinaison = new HashMap<ArrayList<NodeCouple>, Boolean>();
		ArrayList<NodeCouple> listNodeCouple = new ArrayList<NodeCouple>();
		
		// Enregistrement de toutes les positions des arêtes possibles
		/**
		 * Pas sur qu'on en ait besoin
		 */
		for(i=0; i<n-1; i++){
			listNodeCouple.add(new NodeCouple(i, i+1));
		}
		listNodeCouple.add(new NodeCouple(n, 0));
		
		// Initialisation de listCombinaison
	
		/**
		 * Trouver un algo pour lister toutes les combinaisons possibles
		 */
				
		return k_transformation(x, k, listCombinaison);
	}

	protected void changeNeighbourhood(SolutionVNS x, SolutionVNS y, int k) {
		if (y.getPathCost() > x.getPathCost()) {
			x = y;
			k = 1;
		} else {
			k++;
		}
	}
	
	
	/** Ce qui n'est pas (encore) géré : cas où la nouvelle solution contient une arête qui n'existe pas
	 *  /!\ je pense que cela ne devrait pas arriver car nos graphes sont tous complets, donc toutes les arêtes
	 *  du tab adja devrait exister
	 *  --> mais pour être plus rigoureux faut p'tete gérer ce cas
	 * 
	 * @param x
	 * @param k
	 * @return
	 */
	protected SolutionVNS k_transformation(SolutionVNS x, int k, 
			HashMap<ArrayList<NodeCouple>, Boolean> listCombinaison){
	
		// liste des arêtes interdites (contient les positions des arêtes dans la solution x, et non les arêtes
		// en elles-mêmes
		ArrayList<NodeCouple> listEdge = new ArrayList<NodeCouple>();

		int n = x.getPathChosen().size();	// taille du graphe
		int i, j;
		NodeCouple nc;
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
			 * Tirage au sort des arêtes à interdire
			 * Remarque : on repète l'opération jusqu'à obtenir une combinaison qui n'a pas été testé
			 */
			
			do{
				for(i=0;i<k;i++){
					j = (int) (Math.random()*n);
					nc = new NodeCouple(j, j+1);
					while(nodePositionPresent[j-1] || nodePositionPresent[j] || nodePositionPresent[j+1]){
						j = (int) (Math.random()*n);
						nc = new NodeCouple(j, j+1);
					}
					nodePositionPresent[j] = true;
					nodePositionPresent[j+1] = true;
					listEdge.add(nc);
				}
				Collections.sort(listEdge);	// liste des arêtes interdites, trié selon leur position(!) 
											// dans la solution x & correspond à 1 combinaison
			} while(isTested(listEdge, listCombinaison));	// Condition d'arrêt : si isTested = false
			
			//int noeud_depart = x.getPathChosen().get(0);
			SolutionVNS s = new SolutionVNS(x.getGraph_scenario());
			
			/**
			 * Construction de la nouvelle solution
			 */
			
			// on avance de la 1ere jusqu'à la 1ère arête interdite dans la solution x
			i = 0;
			while(i<listEdge.get(0).getN1()){
				s.getPathChosen().add(x.getPathChosen().get(i));
				i++;
			}
			
			i=0;
			j=k-1;
			int pos_val1, pos_val2;
			int changement;	// permet de savoir s'il faut incrémenter ou décrémenter
			while(i<k){
				// Dans le cas où on arrive à la fin et que k est impair
				if(i == j && k%2 != 0){
					// Enregistrement de la position Ai
					pos_val1 = listEdge.get(i).getN1();
					
					// Enregistrement de la position Bk
					pos_val2 = listEdge.get(k).getN2();
					
					// Test pour savoir s'il faut incrémenter ou décrémenter pour faire Ai -> Bk
					if(pos_val1>pos_val2){
						changement = 1;
					}
					else{
						changement = -1;
					}
					
					// Ajout de Ai inclu jusqu'à Bk exclu
					while(pos_val1!=pos_val2){
						s.getPathChosen().add(x.getPathChosen().get(pos_val1));
						pos_val1+=changement;
					}
				
					// Ajout de Bk inclu jusqu'à la fin de la solution x qu'il reste
					for(i=pos_val2; i<x.getPathChosen().size(); i++){
						s.getPathChosen().add(x.getPathChosen().get(pos_val2));
					}
					break;
				}
				
				// ajout de Ai
				s.getPathChosen().add(x.getPathChosen().get(listEdge.get(i).getN1()));
				
				// Enregistrement des positions de Aj et Bj-1 car il faut enregistrer toutes les valeurs
				// comprises dedans
				pos_val1 = listEdge.get(j).getN1();
				pos_val2 = listEdge.get(j-1).getN2();
				
				// test pour savoir s'il faut incrémenter ou décrémenter pour aller de Aj à Bj-1
				if(pos_val1>pos_val2){
					changement = 1;
				}
				else{
					changement = -1;
				}
				
				// ajout de Aj inclu jusqu'à Bj-1 exclu
				while(pos_val1!=pos_val2){
					s.getPathChosen().add(x.getPathChosen().get(pos_val1));
					pos_val1+=changement;
				}
				
				// ajout de Bj-1
				s.getPathChosen().add(pos_val2);
				
				// Enregistrement de Bi
				pos_val1 = listEdge.get(i).getN2();

				// Dans le cas ou on arrive à la fin et que k est pair
				if (i == (k / 2) - 1 && k % 2 == 0) {
					// Enregistrement de Bk
					pos_val2 = listEdge.get(k).getN2();

					// Test pour savoir s'il faut incrémenter ou décrémenter pour
					// faire Bi -> Bk
					if (pos_val1 > pos_val2) {
						changement = 1;
					} else {
						changement = -1;
					}

					// Ajout de Bi inclu jusqu'à Bk exclu
					while (pos_val1 != pos_val2) {
						s.getPathChosen().add(x.getPathChosen().get(pos_val1));
						pos_val1 += changement;
					}

					// Ajout de Bk inclu jusqu'à la fin de la solution x qu'il reste
					for (i = pos_val2; i < x.getPathChosen().size(); i++) {
						s.getPathChosen().add(x.getPathChosen().get(pos_val2));
					}
					break;
				}
				
				// Rajouter de Bi-> jusqu'à Ai+1 qui va être l'étape suivante

				// Enregistrement de Ai+1
				pos_val2 = listEdge.get(i+1).getN1();
				
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
			
			return s;
			
		} while(allTested(listCombinaison)); // la condition d'arret : qd allTested = true
		
		/**
		 * Si aucune meilleure solution n'a été trouvé, on renvoie x
		 */
		return x;	// Je comprends pas pourquoi il met une erreur, ça doit être à cause du do while
		/* Unreachable code : code inateignable, car le return Ligne 250 dit qu'on executera jamais ce qu'il y a après*/
	}

	public boolean isTested(ArrayList<NodeCouple> combinaison, HashMap<ArrayList<NodeCouple>, Boolean> listCombinaison){
		return listCombinaison.get(combinaison);
	}
	
	public boolean allTested(HashMap<ArrayList<NodeCouple>, Boolean> listCombinaison){
		for(ArrayList<NodeCouple> combinaison : listCombinaison.keySet()){
			if(!listCombinaison.get(combinaison)) return false;
		}
		return true;
	}
}
