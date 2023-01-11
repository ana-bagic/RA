package fer.zemris.moop.operators;

import fer.zemris.moop.Util;
import fer.zemris.moop.model.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class KTournamentSelection implements Function<List<Vertex>, Vertex> {

    private final int k;

    public KTournamentSelection(int k) {
        this.k = k;
    }

    @Override
    public Vertex apply(List<Vertex> population) {
        Set<Integer> indexes = Util.generateNDifferentIntsInRange(k, 0, population.size());
        List<Vertex> tournament = new ArrayList<>();

        for(Integer index: indexes) {
            tournament.add(population.get(index));
        }

        tournament.sort(Comparator.naturalOrder());
        return tournament.get(0);
    }
}
