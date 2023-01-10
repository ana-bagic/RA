package fer.zemris.racani.particle.system;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import fer.zemris.racani.particle.system.model.Particle;
import fer.zemris.racani.particle.system.model.Source;
import fer.zemris.racani.particle.system.model.Vertex;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class DisplayFrame implements GLEventListener {

    private final String path;
    private final Vertex camera;
    private final Source source;
    private final List<Particle> particles = new LinkedList<>();
    private final Vertex s = new Vertex(0, 0, 1);
    private static final int CF = 1;
    private static final int minTime = 150;
    private static final int maxTime = 152;
    private static final float minVelocity = 0.6f;
    private static final float maxVelocity = 0.61f;

    public DisplayFrame(String path, Vertex camera, Source source) {
        this.path = path;
        this.camera = camera;
        this.source = source;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);
        gl.glEnable(GL2.GL_BLEND);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, Util.loadTexture(path, gl, false));
        gl.glViewport(0, 1000,0, 1000);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        calculateNewState();

        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glScaled(0.02, 0.02, 0);
        gl.glTranslated(camera.getX(), camera.getY(), camera.getZ());

        for(Particle particle: particles) {
            drawParticle(particle, gl);
        }

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {}

    private void calculateNewState() {
        particles.forEach(Particle::decreaseTime);
        particles.removeIf(p -> p.getTime() <= 0);

        if(source.getQuantity() > 0) {
            int n = Util.randI(1, source.getQuantity() + 1);
            for (int i = 0; i < n; i++) {
                Vertex sP = source.getPosition();
                Color sC = source.getColor();

                Vertex position = new Vertex(sP.getX(), sP.getY(), sP.getZ());
                Color color = new Color(sC.getRed(), sC.getGreen(), sC.getBlue(), 100);
                int time = Util.randI(minTime, maxTime);
                Vertex s = new Vertex(Util.randF(-1, 1), Util.randF(-1, 1), Util.randF(-1, 1));
                s.divide(s.norm());
                float v = Util.randF(minVelocity, maxVelocity);

                particles.add(new Particle(position, color, source.getSize(), time, s, v));
            }
        }

        for(Particle particle: particles) {
            Vertex p = particle.getPosition();
            Vertex e = new Vertex(p.getX() - camera.getX(), p.getY() - camera.getY(), p.getZ() - camera.getZ());

            float osX = s.getY()*e.getZ() - s.getZ()*e.getY();
            float osY = s.getZ()*e.getX() - s.getX()*e.getZ();
            float osZ = s.getX()*e.getY() - s.getY()*e.getX();

            double angle = Math.acos(Vertex.multiply(s, e) / (s.norm()*e.norm()));
            particle.setAngle(angle*180 / Math.PI);
            particle.setRotateAxis(new Vertex(osX, osY, osZ));

            particle.move();
            Color c = particle.getColor();
            int red = c.getRed();
            int blue = c.getBlue();
            particle.setColor(new Color(red, c.getGreen(), blue, c.getAlpha()));
        }
    }

    private void drawParticle(Particle particle, GL2 gl) {
        Color c = particle.getColor();
        Vertex p = particle.getPosition();
        Vertex r = particle.getRotateAxis();
        double size = particle.getSize();

        gl.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
        gl.glTranslated(p.getX(), p.getY(), p.getZ());
        gl.glRotated(particle.getAngle(), r.getX(), r.getY(), r.getZ());

        gl.glBegin(GL2.GL_QUADS);
        //gl.glTexCoord2d(0.0,0.0);
        gl.glVertex3d(-size, -size, 0.0);
        //gl.glTexCoord2d(1.0,0.0);
        gl.glVertex3d(-size, size, 0.0);
        //gl.glTexCoord2d(1.0,1.0);
        gl.glVertex3d(size, size, 0.0);
        //gl.glTexCoord2d(0.0,1.0);
        gl.glVertex3d(size, -size, 0.0);
        gl.glEnd();

        gl.glRotated(-particle.getAngle(), r.getX(), r.getY(), r.getZ());
        gl.glTranslated(-p.getX(), -p.getY(), -p.getZ());
    }

}
