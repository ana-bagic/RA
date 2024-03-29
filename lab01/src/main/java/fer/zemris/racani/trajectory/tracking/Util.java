package fer.zemris.racani.trajectory.tracking;

import fer.zemris.racani.trajectory.tracking.model.Polygon;
import fer.zemris.racani.trajectory.tracking.model.Vertex;

import java.util.List;

public class Util {

    private static final double increaseObject = 0.1;

    public static Vertex loadVertex(String[] elements, boolean increase) {
        float x = Float.parseFloat(elements[1]);
        float y = Float.parseFloat(elements[2]);
        float z = Float.parseFloat(elements[3]);

        if(increase) {
            x *= increaseObject;
            y *= increaseObject;
            z *= increaseObject;
        }

        return new Vertex(x, y, z);
    }

    public static Polygon loadPolygon(String[] elements, List<Vertex> vertices) {
        int v1 = Integer.parseInt(elements[1]);
        int v2 = Integer.parseInt(elements[2]);
        int v3 = Integer.parseInt(elements[3]);
        return new Polygon(vertices.get(v1 - 1), vertices.get(v2 - 1), vertices.get(v3 - 1));
    }

}
