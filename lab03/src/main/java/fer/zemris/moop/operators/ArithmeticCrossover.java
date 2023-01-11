package fer.zemris.moop.operators;

import fer.zemris.moop.Util;
import fer.zemris.moop.model.Vertex;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ArithmeticCrossover implements Function<List<Vertex>, List<Vertex>> {

    @Override
    public List<Vertex> apply(List<Vertex> parents) {
        if(parents.size() != 2) {
            throw new IllegalArgumentException("There can only be 2 parents, not " + parents.size());
        }

        Vertex p1 = parents.get(0);
        Vertex p2 = parents.get(1);
        double[] elements = new double[p1.size()];

        for(int i = 0; i < elements.length; i++) {
            elements[i] = (p1.getElement(i) + p2.getElement(i))/2;
            if(!Util.CONFIG.getProblem().satisfiesConstraint(i, elements[i])) {
                return null;
            }
        }

        return Collections.singletonList(new Vertex(elements, p1.resultSize()));
    }
}
