package fer.zemris.racani.trajectory.tracking;

import fer.zemris.racani.trajectory.tracking.model.Polygon;
import fer.zemris.racani.trajectory.tracking.model.Vertex;
import fer.zemris.racani.trajectory.tracking.model.Object;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String objectFile = "examples/aircraft747.obj";
        String curveFile = "examples/bSpline.txt";
        List<Vertex> vertices = new LinkedList<>();
        List<Polygon> polygons = new LinkedList<>();
        List<Vertex> curve = new LinkedList<>();
        Vertex origin = new Vertex(0, 0, 0);

        try(Scanner sc = new Scanner(new File(objectFile))) {
            while(sc.hasNext()) {
                String line = sc.nextLine().trim();
                if(line.startsWith("#") || line.equals("")) continue;

                String[] elements = line.split(" ");
                if(line.startsWith("v")) {
                    Vertex vertex = Util.loadVertex(elements, true);
                    origin.translate(vertex);
                    vertices.add(vertex);
                } else if(line.startsWith("f")) {
                    polygons.add(Util.loadPolygon(elements, vertices));
                } else {
                    throw new RuntimeException("File " + objectFile + " is not formatted right. " +
                            "Line " + line + " is not valid.");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + objectFile + " not found.");
        }

        origin.divide(vertices.size());

        try(Scanner sc = new Scanner(new File(curveFile))) {
            while(sc.hasNext()) {
                String line = sc.nextLine().trim();
                if(line.startsWith("#") || line.equals("")) continue;

                if(line.startsWith("v")) {
                    curve.add(Util.loadVertex(line.split(" "), false));
                } else {
                    throw new RuntimeException("File " + curveFile + " is not formatted right. " +
                            "Line " + line + " is not valid.");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + curveFile + " not found.");
        }

        Object object = new Object(polygons, origin);
        TrajectoryTracking tracking = new TrajectoryTracking(object, curve);
        tracking.animate();
    }
}
