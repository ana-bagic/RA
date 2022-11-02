package fer.zemris.racani.trajectory.tracking;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import fer.zemris.racani.trajectory.tracking.model.Object;
import fer.zemris.racani.trajectory.tracking.model.Vertex;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class TrajectoryTracking {

    private final Object object;
    private final List<Vertex> curve;
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
        List<Vertex> someTangent = new LinkedList<>();

        for(int i = 0; i < segmentsNr; i++) {
            Vertex v0 = curve.get(i);
            Vertex v1 = curve.get(i + 1);
            Vertex v2 = curve.get(i + 2);
            Vertex v3 = curve.get(i + 3);

            for(int j = 0; j < T_VALUES; j++) {
                double t = j*T_INCREMENT;

                float tb0 = (float) ((-Math.pow(t, 3) + 3*Math.pow(t, 2) - 3*t + 1) / 6);
                float tb1 = (float) ((3*Math.pow(t, 3) - 6*Math.pow(t, 2) + 4) / 6);
                float tb2 = (float) ((-3*Math.pow(t, 3) + 3*Math.pow(t, 2) + 3*t + 1) / 6);
                float tb3 = (float) (Math.pow(t, 3) / 6);

                Vertex v = multiplyVertex(v0, v1, v2, v3, tb0, tb1, tb2, tb3);
                bSpline.add(v);

                float tbd0 = (float) ((-Math.pow(t, 2) + 2*t - 1) / 2);
                float tbd1 = (float) ((3*Math.pow(t, 2) - 4*t) / 2);
                float tbd2 = (float) ((-3*Math.pow(t, 2) + 2*t + 1) / 2);
                float tbd3 = (float) (Math.pow(t, 2) / 2);

                Vertex tv = multiplyVertex(v0, v1, v2, v3, tbd0, tbd1, tbd2, tbd3);
                tv.divide(2);
                tv.translate(v);
                tangent.add(tv);

                if(j % 50 == 0) {
                    someTangent.add(v);
                    someTangent.add(tv);
                }
            }
        }

        display(bSpline, tangent, someTangent);
    }

    private void display(List<Vertex> bSpline, List<Vertex> tangent, List<Vertex> someTangent) {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas glcanvas = new GLCanvas(capabilities);

        DisplayFrame displayFrame = new DisplayFrame(object, bSpline, tangent, someTangent);
        glcanvas.addGLEventListener(displayFrame);
        glcanvas.setSize(800, 800);

        JFrame frame = new JFrame ("Trajectory tracking");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        FPSAnimator animator = new FPSAnimator(glcanvas, 60);
        animator.start();
    }

    private Vertex multiplyVertex(Vertex v0, Vertex v1, Vertex v2, Vertex v3, float a0, float a1, float a2, float a3) {
        float x = v0.getX()*a0 + v1.getX()*a1 + v2.getX()*a2 + v3.getX()*a3;
        float y = v0.getY()*a0 + v1.getY()*a1 + v2.getY()*a2 + v3.getY()*a3;
        float z = v0.getZ()*a0 + v1.getZ()*a1 + v2.getZ()*a2 + v3.getZ()*a3;
        return new Vertex(x, y, z);
    }

}
