package fer.zemris.racani.trajectory.tracking.model;

import java.util.List;

public class Object {

    private List<Vertex> vertices;
    private List<Polygon> polygons;

    public Object(List<Vertex> vertices, List<Polygon> polygons) {
        this.vertices = vertices;
        this.polygons = polygons;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }
}
