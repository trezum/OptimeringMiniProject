package HillClimb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleHillClimbing {


	private final Problem curProblem;

	public SimpleHillClimbing(final Problem problem) {
		this.curProblem = problem;
	}

	public ArrayList<Double> findOptima(int iterations, double stepSize, int neighbours) {


		ArrayList<Double> bestGlobalPoint = this.getRandomPoint();
		double bestGlobal = this.curProblem.Eval(bestGlobalPoint);

		for (int i = 0; i < iterations; i++) {
			boolean shouldContinue;

			// Select a random value as a starting point aka 'best solution'
			ArrayList<Double> bestPoint = this.getRandomPoint();
			double bestSolution = this.curProblem.Eval(bestPoint);

			do {
				// Select a random neighbour
				ArrayList<Double> newPoint = this.getBestNeighbourPoint(bestPoint, stepSize, neighbours);
				double newSolution = this.curProblem.Eval(newPoint);
				// If a new solution's value is greater than current, best solution
				if (bestSolution < newSolution) {
					// Change the best solution
					bestSolution = newSolution;
					bestPoint = newPoint;
					// And continue searching
					shouldContinue = true;
				} else {
					// Otherwise stop
					shouldContinue = false;
				}

			} while (shouldContinue);

			if (bestGlobal < bestSolution )
			{
				bestGlobal = bestSolution;
				bestGlobalPoint = bestPoint;
				//System.out.println(bestGlobal);
			}
		}
		return bestGlobalPoint;
	}

	private ArrayList<Double> getBestNeighbourPoint(ArrayList<Double> point, double stepSize, int neighbours){
		ArrayList<Double> bestNeighbourPoint = getNeighbourPoint(point, stepSize);
		double bestNeighbour = curProblem.Eval(bestNeighbourPoint);
		int betterNeighbourCounter = 0;
		for (int i = 0; i < neighbours-1; i++) {
			ArrayList<Double> newNeighbour = getNeighbourPoint(point, stepSize);
			double newNeighborEvaluation = curProblem.Eval(newNeighbour);
			if (newNeighborEvaluation > bestNeighbour){
				betterNeighbourCounter++;
				bestNeighbourPoint = newNeighbour;
				bestNeighbour = newNeighborEvaluation;
			}
		}
		//System.out.println("Better neighbors found:" + betterNeighbourCounter);
		return bestNeighbourPoint;
	}

	private ArrayList<Double> getNeighbourPoint(ArrayList<Double> point, double stepSize){
		ArrayList<Double> neighbourPoint = new ArrayList<Double>();

		for (int i = 0; i < curProblem.getDimensions(); i++) {
			double newParam;
			if (Math.random() > 0.5){
				newParam = point.get(i) - Math.random() * stepSize;
				if (newParam < curProblem.getMinValues().get(i)) {
					newParam = curProblem.getMinValues().get(i);
				}
			}
			else
			{
				newParam = point.get(i) + Math.random() * stepSize;
				if (newParam > curProblem.getMaxValues().get(i)) {
					newParam = curProblem.getMaxValues().get(i);
				}
			}
			neighbourPoint.add(newParam);
		}
		return neighbourPoint;
	}

	private ArrayList<Double> getRandomPoint() {
		ArrayList<Double> initPoint = new ArrayList<>();
		for (int dim = 0; dim < curProblem.getDimensions(); dim++) {
			initPoint.add(curProblem.getMinValues().get(dim) + Math.random() * (curProblem.getMaxValues().get(dim) - curProblem.getMinValues().get(dim)));
		}
		return initPoint;
	}

	private static void iterateAllParams(){
		ArrayList<Problem> problems = new ArrayList<Problem>();
		//problems.add(new P1());
		//problems.add(new P2());
		problems.add(new RevAckley());

		int[] iterations = new int[3];
		iterations[0] = 10;
		iterations[1] = 100;
		iterations[2] = 200;

		int[] neighbours = new int[3];
		neighbours[0] = 10;
		neighbours[1] = 100;
		neighbours[2] = 200;

		double[] stepSizes = new double[3];
		stepSizes[0] = 0.1;
		stepSizes[1] = 0.01;
		stepSizes[2] = 0.001;

		for (Problem p : problems)
		{
			SimpleHillClimbing test = new SimpleHillClimbing(p);
			System.out.println("---------------------Next Problem---------------------");
			for (int i = 0; i < iterations.length; i++) {
				for (int n = 0; n < neighbours.length; n++) {
					for (int s = 0; s < stepSizes.length; s++)
					{
						var point = test.findOptima(iterations[i],stepSizes[s],neighbours[n]);
						System.out.println("Iterations:" + iterations[i] + "\tStepsize:" + stepSizes[s]+ "\tNeighbours:" + neighbours[n]+
								"\t"+ p.EvalCallCount+ "\t" + String.format("%.14f", p.Eval(point))+ "\t" + point);
						p.ResetEvalCallCount();
					}
				}
			}
		}
	}
	public static void runOne(){
		Problem p = new RevAckley();
		SimpleHillClimbing test = new SimpleHillClimbing(p);
		var point = test.findOptima(100000,0.1,100);
		System.out.println(point);
		System.out.println(p.Eval(point));
		System.out.println(p.EvalCallCount);
}
	public static void evalBest(){
		var p1Top = new ArrayList<Double>();
		p1Top.add(0.0);
		p1Top.add(0.0);
		System.out.println("P1 top eval: " + new P1().Eval(p1Top));

		var p2Top = new ArrayList<Double>();
		p2Top.add(1.6973307015218115);
		p2Top.add(1.6973307015218115);
		System.out.println("P2 top eval: " + new P2().Eval(p2Top));

		var revAckleyTop = new ArrayList<Double>();
		revAckleyTop.add(0.0);
		revAckleyTop.add(0.0);
		System.out.println("RevAckley top eval: " + new RevAckley().Eval(revAckleyTop));


	}
	public static void main(String[] args) {
		//runOne();
		iterateAllParams();
		evalBest();
	}
}