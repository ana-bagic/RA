package fer.zemris.moop.model;

import fer.zemris.moop.Util;

public class MOOPProblem2 implements MOOPProblem {

    @Override
    public Vertex generateSolution() {
        return new Vertex(new double[]{
                Util.randD(0.1, 1),
                Util.randD(0, 5)
        }, 2);
    }

    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public void evaluateSolution(Vertex solution) {
        double x1 = solution.getElement(0);
        solution.setResult(0, x1);
        solution.setResult(1, (1 + solution.getElement(1))/x1);
    }

    @Override
    public boolean satisfiesConstraint(int index, double value) {
        switch(index) {
            case 0: return value >= 0.1 && value <= 1;
            case 1: return value >= 0 && value <= 5;
            default: throw new IllegalArgumentException("Index " + index + " is not valid for solution of size 2.");
        }
    }
}
