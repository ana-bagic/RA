package fer.zemris.moop;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import fer.zemris.moop.algorithm.NSGAII;

import javax.swing.*;

public class MOOP {

    public static void main(String[] args) {
        String configPath = "examples/config.txt";
        Util.loadConfiguration(configPath);

        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.addGLEventListener(new NSGAII());
        glcanvas.addKeyListener(new KeyListener());
        glcanvas.setSize(800, 800);

        JFrame frame = new JFrame("Particle System");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        FPSAnimator animator = new FPSAnimator(glcanvas, 60);
        animator.start();
    }

}
