package fer.zemris.racani.trajectory.tracking;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import fer.zemris.racani.trajectory.tracking.model.Polygon;
import fer.zemris.racani.trajectory.tracking.model.Vertex;
import fer.zemris.racani.trajectory.tracking.model.Object;

import java.util.List;

public class DisplayFrame implements GLEventListener {

    private final Object object;
    private final List<Vertex> bSpline;
    private final List<Vertex> tangent;
    private final List<Vertex> someTangent;
    private int t = 0;
    private final Vertex s = new Vertex(0, 0, 1);

    public DisplayFrame(Object object, List<Vertex> bSpline, List<Vertex> tangent, List<Vertex> someTangent) {
        this.object = object;
        this.bSpline = bSpline;
        this.tangent = tangent;
        this.someTangent = someTangent;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glColor3d(1, 1, 1);
        gl.glScaled(0.15, 0.06, 0);
        gl.glTranslated(-4, -17, -5);
        gl.glRotated(-30,1, 0, 0);

        gl.glBegin (GL2.GL_LINE_STRIP);
        for(Vertex v: bSpline) {
            gl.glVertex3f(v.getX(), v.getY(), v.getZ());
        }
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(0, 1, 1);
        for(Vertex v: someTangent) {
            gl.glVertex3f(v.getX(), v.getY(), v.getZ());
        }
        gl.glEnd();

        Vertex spline = bSpline.get(t);
        gl.glTranslated(spline.getX(), spline.getY(), spline.getZ());

        Vertex e = tangent.get(t).copy();
        e.translate(-spline.getX(), -spline.getY(), -spline.getZ());
        Vertex origin = object.getOrigin();
        float osx = s.getY()*e.getZ() - s.getZ()*e.getY();
        float osy = s.getZ()*e.getX() - s.getX()*e.getZ();
        float osz = s.getX()*e.getY() - s.getY()*e.getX();

        double angle = Math.acos(Vertex.multiply(s, e) / (s.norm()*e.norm()));
        angle = angle*180 / Math.PI;
        gl.glRotated(angle, osx, osy, osz);
        gl.glTranslated(-origin.getX(), -origin.getY(), -origin.getZ());

        gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(1, 1, 1);
        for(Polygon p: object.getPolygons()) {
            Vertex v1 = p.getV1();
            Vertex v2 = p.getV2();
            Vertex v3 = p.getV3();

            gl.glVertex3f(v1.getX(), v1.getY(), v1.getZ());
            gl.glVertex3f(v2.getX(), v2.getY(), v2.getZ());

            gl.glVertex3f(v2.getX(), v2.getY(), v2.getZ());
            gl.glVertex3f(v3.getX(), v3.getY(), v3.getZ());

            gl.glVertex3f(v3.getX(), v3.getY(), v3.getZ());
            gl.glVertex3f(v1.getX(), v1.getY(), v1.getZ());
        }

        gl.glColor3d(1, 0, 0);
        gl.glVertex3f(origin.getX(), origin.getY(), origin.getZ());
        gl.glVertex3f(origin.getX() + 2.5f, origin.getY(), origin.getZ());

        gl.glColor3f(0, 1, 0);
        gl.glVertex3f(origin.getX(), origin.getY(), origin.getZ());
        gl.glVertex3f(origin.getX(), origin.getY() + 2.5f, origin.getZ());

        gl.glColor3f(0, 0, 1);
        gl.glVertex3f(origin.getX(), origin.getY(), origin.getZ());
        gl.glVertex3f(origin.getX(), origin.getY(), origin.getZ() + 2.5f);

        gl.glColor3f(0, 0, 0);
        gl.glEnd();
        gl.glFlush();

        t++;

        if(t == bSpline.size()) {
            t = 0;
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

}
