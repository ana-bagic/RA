package fer.zemris.racani.trajectory.tracking;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import fer.zemris.racani.trajectory.tracking.model.Object;
import fer.zemris.racani.trajectory.tracking.model.Vertex;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class TrajectoryTracking {

    private Object object;
    private List<Vertex> curve;
    private static final int T_VALUES = 100;
    private static final double T_INCREMENT = 1.0/T_VALUES;

    public TrajectoryTracking(Object object, List<Vertex> curve) {
        this.object = object;
        this.curve = curve;
    }

    public void animate() {
        int segmentsNr = curve.size() - 3;
        List<Vertex> bSpline = new LinkedList<>();
        List<Vertex> tangent = new LinkedList<>();

        for(int i = 0; i < segmentsNr; i++) {
            Vertex v0 = curve.get(i);
            Vertex v1 = curve.get(i + 1);
            Vertex v2 = curve.get(i + 2);
            Vertex v3 = curve.get(i + 3);

            for(double t = 0; t < 1; t += T_INCREMENT) {
                double tb0 = (-Math.pow(t, 3) + 3*Math.pow(t, 2) - 3*t + 1) / 6;
                double tb1 = (3*Math.pow(t, 3) - 6*Math.pow(t, 2) + 4) / 6;
                double tb2 = (-3*Math.pow(t, 3) + 3*Math.pow(t, 2) + 3*t + 1) / 6;
                double tb3 = Math.pow(t, 3) / 6;

                bSpline.add(multiplyVertex(v0, v1, v2, v3, tb0, tb1, tb2, tb3));

                double tbd0 = (-Math.pow(t, 2) + 2*t - 1) / 2;
                double tbd1 = (3*Math.pow(t, 2) - 4*t) / 2;
                double tbd2 = (-3*Math.pow(t, 2) + 2*t + 1) / 2;
                double tbd3 = Math.pow(t, 2) / 2;

                tangent.add(multiplyVertex(v0, v1, v2, v3, tbd0, tbd1, tbd2, tbd3));
            }
        }

        display(bSpline, tangent);
    }

    private void display(List<Vertex> bSpline, List<Vertex> tangent) {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas glcanvas = new GLCanvas(capabilities);

        DisplayFrame displayFrame = new DisplayFrame();
        glcanvas.addGLEventListener(displayFrame);
        glcanvas.setSize(400, 400);

        JFrame frame = new JFrame ("Trajectory tracking");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }

    private Vertex multiplyVertex(Vertex v0, Vertex v1, Vertex v2, Vertex v3, double a0, double a1, double a2, double a3) {
        double x = v0.getX()*a0 + v1.getX()*a1 + v2.getX()*a2 + v3.getX()*a3;
        double y = v0.getY()*a0 + v1.getY()*a1 + v2.getY()*a2 + v3.getY()*a3;
        double z = v0.getZ()*a0 + v1.getZ()*a1 + v2.getZ()*a2 + v3.getZ()*a3;
        return new Vertex(x, y, z);
    }

}
