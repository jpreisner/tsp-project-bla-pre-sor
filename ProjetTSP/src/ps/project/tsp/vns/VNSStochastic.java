package ps.project.tsp.vns;

import java.util.ArrayList;

import pne.project.tsp.beans.NodeCouple;

public class VNSStochastic extends VNSAbstract {

	public VNSStochastic(int Kmax) {
		super(Kmax);
	}
	
	public SolutionVNS getSolutionRef(){
		return getListSolutions().get(0);
	}
	
	public SolutionVNS getSolutionScenario(int k){
		return getListSolutions().get(k);
	}

	public void setSolutionScenario(int i, SolutionVNS sol_scenario) {
		// TODO Auto-generated method stub
		getSolutionScenario(i).setSolution(sol_scenario);
	}
	
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
			s.setPenaliteLambda(x.getPenaliteLambda().clone());
			s.setPenaliteRo(x.getPenaliteRo().clone());
			
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

			s.setPathCost(s.calculPathCostWithPenalty(getSolutionRef()));
			
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


}
