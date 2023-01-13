package fer.zemris.moop.model;

import fer.zemris.moop.Util;

public class MOOPProblem1 implements MOOPProblem {

    @Override
    public Vertex generateSolution() {
        return new Vertex(new double[]{
                Util.randD(1, 3),
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
        double x2 = solution.getElement(1);
        solution.setResult(0, x2 + 5 / x1);
        solution.setResult(1, Math.sin(x1 + x2));
    }

    @Override
    public boolean satisfiesConstraint(int index, double value) {
        switch(index) {
            case 0: return value >= 1 && value <= 3;
            case 1: return value >= 0 && value <= 5;
            default: throw new IllegalArgumentException("Index " + index + " is not valid for solution of size 2.");
        }
    }
}
