package fer.zemris.racani.trajectory.tracking;

import fer.zemris.racani.trajectory.tracking.model.Polygon;
import fer.zemris.racani.trajectory.tracking.model.Vertex;

import java.util.List;

public class Util {

    public static Vertex loadVertex(String[] elements) {
        double x = Double.parseDouble(elements[1]);
        double y = Double.parseDouble(elements[2]);
        double z = Double.parseDouble(elements[3]);
        return new Vertex(x, y, z);
    }

    public static Polygon loadPolygon(String[] elements, List<Vertex> vertices) {
        int v1 = Integer.parseInt(elements[1]);
        int v2 = Integer.parseInt(elements[2]);
        int v3 = Integer.parseInt(elements[3]);
        return new Polygon(vertices.get(v1 - 1), vertices.get(v2 - 1), vertices.get(v3 - 1));
    }

}
