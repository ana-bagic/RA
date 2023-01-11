package fer.zemris.moop.operators;

import fer.zemris.moop.Util;
import fer.zemris.moop.model.Vertex;

import java.util.function.Function;

public class GaussMutation implements Function<Vertex, Vertex> {

    private final double p;

    public GaussMutation(double p) {
        this.p = p;
    }

    @Override
    public Vertex apply(Vertex vertex) {
        Vertex copy = vertex.copy();

        for(int i = 0; i < copy.size(); i++) {
            if(Util.RAND.nextDouble() <= p) {
                double prevValue = copy.getElement(i);
                double nextValue;
                do {
                    nextValue = prevValue + Util.RAND.nextGaussian();
                } while(!Util.CONFIG.getProblem().satisfiesConstraint(i, nextValue));

                copy.setElement(i, nextValue);
            }
        }

        return copy;
    }
}
