package fer.zemris.moop.model;

public interface MOOPProblem {

    Vertex generateSolution();
    int getNumberOfObjectives();
    void evaluateSolution(Vertex solution);
    boolean satisfiesConstraint(int index, double value);
}
