package ps.project.tsp.vns;

import java.util.ArrayList;

public abstract class VNSAbstract {
	private ArrayList<SolutionVNS> listSolutions;
	private int Kmax;

	public SolutionVNS vnsAlgorithm(double tmax, int kmax) {
		double t;
		SolutionVNS x;
		SolutionVNS y;
		int k;
		do {
			k = 1;
			do {
				x = initialSolution(k);
				y = findBetterSolution(x);
				changeNeighbourhood(x, y, k);
			} while (k == kmax);

			t = System.currentTimeMillis();
		} while (t > tmax);
		return x;
	}

	protected abstract SolutionVNS initialSolution(int k);

	protected abstract SolutionVNS findBetterSolution(SolutionVNS x);

	protected void changeNeighbourhood(SolutionVNS x, SolutionVNS y, int k) {
		if (y.getPathCost() > x.getPathCost()) {
			x = y;
			k = 1;
		} else {
			k++;
		}
	}
}
