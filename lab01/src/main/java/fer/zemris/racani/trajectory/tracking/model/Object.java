package fer.zemris.racani.trajectory.tracking.model;

import java.util.List;

public class Object {

    private final List<Polygon> polygons;
    private final Vertex origin;

    public Object(List<Polygon> polygons, Vertex origin) {
        this.polygons = polygons;
        this.origin = origin;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public Vertex getOrigin() {
        return origin;
    }
}
