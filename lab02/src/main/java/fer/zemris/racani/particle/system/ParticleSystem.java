package fer.zemris.racani.particle.system;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import fer.zemris.racani.particle.system.model.Source;
import fer.zemris.racani.particle.system.model.Vertex;

import javax.swing.*;
import java.awt.*;

public class ParticleSystem {

    public static void main(String[] args) {
        String path = "examples/cestica.bmp";

        Vertex sourcePosition = new Vertex(0, 0, 0);
        Color sourceColor = new Color(255, 0, 0);
        Source source = new Source(sourcePosition, sourceColor, 0.4, 10);
        Vertex camera = new Vertex(0, 0, 50);

        start(path, source, camera);
    }

    public static void start(String path, Source source, Vertex camera) {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.addGLEventListener(new DisplayFrame(path, camera, source));
        glcanvas.addKeyListener(new ParticleKeyListener(camera, source));
        glcanvas.setSize(800, 800);

        JFrame frame = new JFrame("Particle System");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        FPSAnimator animator = new FPSAnimator(glcanvas, 60);
        animator.start();
    }
}
