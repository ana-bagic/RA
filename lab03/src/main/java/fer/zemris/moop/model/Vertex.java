package fer.zemris.moop.model;

import fer.zemris.moop.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Vertex implements Comparable<Vertex> {

    private final double[] elements;
    private final double[] result;
    private int dominatedBy;
    private final Set<Vertex> dominates = new HashSet<>();
    private double distance = 0;
    private int front;

    public Vertex(double[] elements, int results) {
        this.elements = elements;
        this.result = new double[results];
    }

    public static Vertex newRandom(int elements, double bound, int results) {
        double[] numbers = new double[elements];
        for(int i = 0; i < elements; i++) {
            numbers[i] = Util.randD(-bound, bound);
        }
        return new Vertex(numbers, results);
    }

    public int size() {
        return elements.length;
    }
    public int resultSize() {
        return result.length;
    }

    public Vertex copy() {
        return new Vertex(Arrays.copyOf(elements, elements.length), result.length);
    }
    public void reset() {
        dominatedBy = 0;
        dominates.clear();
        distance = 0;
        front = 0;
    }

    public double getElement(int index) {
        return elements[index];
    }
    public double getResult(int index) {
        return result[index];
    }
    public int getDominatedBy() {
        return dominatedBy;
    }
    public Set<Vertex> getDominates() {
        return dominates;
    }
    public int getFront() {
        return front;
    }

    public void setElement(int index, double value) {
        elements[index] = value;
    }
    public void setResult(int index, double value) {
        result[index] = value;
    }
    public void increaseDominatedBy() {
        dominatedBy++;
    }
    public void decreaseDominatedBy() {
        dominatedBy--;
    }
    public void setDominates(Vertex solution) {
        dominates.add(solution);
    }
    public void increaseDistance(double value) {
        distance += value;
    }
    public void setFront(int front) {
        this.front = front;
    }

    @Override
    public int compareTo(Vertex o) {
        if(front != o.front) {
            return front - o.front;
        }
        return Double.compare(o.distance, distance);
    }
}
