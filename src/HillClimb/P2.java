package HillClimb;

import java.util.ArrayList;

public class P2 extends Problem {

	public P2() {
		super();

		ArrayList<Double> maxValues = new ArrayList<>();
		maxValues.add(2.0);
		maxValues.add(2.0);
		this.setMaxValues(maxValues);

		ArrayList<Double> minValues = new ArrayList<>();
		minValues.add(-2.0);
		minValues.add(-2.0);
		this.setMinValues(minValues);
	}

	@Override
	public double Eval(ArrayList<Double> paramVals) {
		this.EvalCallCount++;
		return Math.pow(Math.E, -(Math.pow(paramVals.get(0), 2.0) + Math.pow(paramVals.get(1), 2.0))) + 2
				* Math.pow(Math.E, -(Math.pow(paramVals.get(0) - 1.7, 2.0) + Math.pow(paramVals.get(1) - 1.7, 2.0)));
	}

	@Override
	public int getDimensions() {
		return 2;
	}

}
