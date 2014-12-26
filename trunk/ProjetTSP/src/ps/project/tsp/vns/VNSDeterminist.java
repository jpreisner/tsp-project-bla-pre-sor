package ps.project.tsp.vns;

public class VNSDeterminist extends VNSAbstract {

	@Override
	protected SolutionVNS initialSolution(int k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SolutionVNS findBetterSolution(SolutionVNS x, int k) {
		return k_transformation(x, k);
	}
	


}
