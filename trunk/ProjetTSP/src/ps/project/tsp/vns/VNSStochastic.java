package ps.project.tsp.vns;

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


}
