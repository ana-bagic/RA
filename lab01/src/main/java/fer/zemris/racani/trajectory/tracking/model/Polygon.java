package fer.zemris.racani.trajectory.tracking.model;

public class Polygon {

    private Vertex v1;
    private Vertex v2;
    private Vertex v3;

    public Polygon(Vertex v1, Vertex v2, Vertex v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public Vertex getV3() {
        return v3;
    }
}
