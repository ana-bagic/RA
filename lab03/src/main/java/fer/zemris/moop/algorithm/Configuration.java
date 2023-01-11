package fer.zemris.moop.algorithm;

import fer.zemris.moop.model.MOOPProblem;
import fer.zemris.moop.model.Vertex;

import java.util.List;
import java.util.function.Function;

public class Configuration {

    private MOOPProblem problem;
    private int populationSize;
    private double step;
    private double size;
    private Function<List<Vertex>, Vertex> selection;
    private Function<List<Vertex>, List<Vertex>> crossover;
    private Function<Vertex, Vertex> mutation;
    private double translationX;
    private double translationY;
    private double scaleX;
    private double scaleY;

    public MOOPProblem getProblem() {
        return problem;
    }
    public int getPopulationSize() {
        return populationSize;
    }
    public double getStep() {
        return step;
    }
    public double getSize() {
        return size;
    }
    public Function<List<Vertex>, Vertex> getSelection() {
        return selection;
    }
    public Function<List<Vertex>, List<Vertex>> getCrossover() {
        return crossover;
    }
    public Function<Vertex, Vertex> getMutation() {
        return mutation;
    }
    public double getTranslationX() {
        return translationX;
    }
    public double getTranslationY() {
        return translationY;
    }
    public double getScaleX() {
        return scaleX;
    }
    public double getScaleY() {
        return scaleY;
    }

    public void setProblem(MOOPProblem problem) {
        this.problem = problem;
    }
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }
    public void setStep(double step) {
        this.step = step;
    }
    public void setSize(double size) {
        this.size = size;
    }
    public void setSelection(Function<List<Vertex>, Vertex> selection) {
        this.selection = selection;
    }
    public void setCrossover(Function<List<Vertex>, List<Vertex>> crossover) {
        this.crossover = crossover;
    }
    public void setMutation(Function<Vertex, Vertex> mutation) {
        this.mutation = mutation;
    }
    public void setTranslationX(double translationX) {
        this.translationX = translationX;
    }
    public void setTranslationY(double translationY) {
        this.translationY = translationY;
    }
    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }
    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }
}
