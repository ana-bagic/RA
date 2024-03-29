package fer.zemris.moop.algorithm;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import fer.zemris.moop.Util;
import fer.zemris.moop.model.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class NSGAII implements GLEventListener {

    private List<Vertex> P;
    private List<List<Vertex>> fronts;
    private int iteration;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        P = initialPopulation();
        fronts = nonDominatedSort(P);
        fronts.forEach(this::crowdingDistance);
        iteration = 0;

        Util.CONFIG.setTranslationX(0);
        Util.CONFIG.setTranslationY(-5);
        Util.CONFIG.setScaleX(0.05);
        Util.CONFIG.setScaleY(0.05);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        if(iteration % (60*Util.CONFIG.getStep()) == 0 && Util.CONFIG.isPlay()) {
            optimizeStep();
            System.out.println("Iteration: " + iteration);
        }

        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glColor3f(0, 0, 0);

        gl.glScaled(Util.CONFIG.getScaleX(), Util.CONFIG.getScaleY(), 0);
        gl.glTranslated(Util.CONFIG.getTranslationX(),  Util.CONFIG.getTranslationY(), 0);

        drawCoords(gl);
        drawPoints(gl);

        gl.glFlush();
        iteration++;
    }

    private void drawCoords(GL2 gl) {
        double inverseX = 1/Util.CONFIG.getScaleX();
        double inverseY = 1/Util.CONFIG.getScaleY();
        double arrowX = inverseX * 0.03;
        double arrowY = inverseY * 0.03;

        double xLeft = -inverseX - Util.CONFIG.getTranslationX();
        double xRight = inverseX - Util.CONFIG.getTranslationX();
        double yUp = inverseY - Util.CONFIG.getTranslationY();
        double yDown = -inverseY - Util.CONFIG.getTranslationY();

        // AXIS
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3d(xLeft, 0, 0);
        gl.glVertex3d(xRight, 0, 0);
        gl.glVertex3d(0, yUp, 0);
        gl.glVertex3d(0, yDown, 0);
        gl.glEnd();

        // arrows on axis
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3d(xLeft, 0, 0);
        gl.glVertex3d(xLeft + arrowX, arrowY, 0);
        gl.glVertex3d(xLeft + arrowX, -arrowY, 0);
        gl.glVertex3d(xRight, 0, 0);
        gl.glVertex3d(xRight - arrowX, arrowY, 0);
        gl.glVertex3d(xRight - arrowX, -arrowY, 0);
        gl.glVertex3d(0, yUp, 0);
        gl.glVertex3d(arrowX, yUp - arrowY, 0);
        gl.glVertex3d(-arrowX, yUp - arrowY, 0);
        gl.glVertex3d(0, yDown, 0);
        gl.glVertex3d(arrowX, yDown + arrowY, 0);
        gl.glVertex3d(-arrowX, yDown + arrowY, 0);
        gl.glEnd();

        // LINES
        double diffX = xRight - xLeft;
        double stepX = diffX / 9;
        double realStepX = Util.getRealStep(stepX);
        double offsetY = inverseY * 0.01;
        double diffY = yUp - yDown;
        double stepY = diffY / 9;
        double realStepY = Util.getRealStep(stepY);
        double offsetX = inverseX * 0.01;

        gl.glBegin(GL2.GL_LINES);
        for(double x = realStepX; x < xRight; x += realStepX) {
            gl.glVertex3d(x, -offsetY, 0);
            gl.glVertex3d(x, offsetY, 0);
        }
        for(double x = -realStepX; x > xLeft; x -= realStepX) {
            gl.glVertex3d(x, -offsetY, 0);
            gl.glVertex3d(x, offsetY, 0);
        }
        for(double y = realStepY; y < yUp; y += realStepY) {
            gl.glVertex3d(-offsetX, y, 0);
            gl.glVertex3d(offsetX, y, 0);
        }
        for(double y = -realStepY; y > yDown; y -= realStepY) {
            gl.glVertex3d(-offsetX, y, 0);
            gl.glVertex3d(offsetX, y, 0);
        }
        gl.glEnd();

        // LABELS
        GLUT glut = new GLUT();
        double scaleX = inverseX * 0.00025;
        double scaleY = inverseY * 0.0004;
        double offsetLabelX = inverseX * 0.04;
        double offsetLabelY = inverseY * 0.065;

        gl.glPushMatrix();
        gl.glTranslated(-offsetLabelX, -offsetLabelY, 0);
        gl.glScaled(scaleX, scaleY, 0);
        glut.glutStrokeString(GLUT.STROKE_ROMAN, "0");
        gl.glPopMatrix();

        offsetLabelX = inverseX * 0.08;
        for(double x = realStepX; x < xRight; x += realStepX) {
            gl.glPushMatrix();
            gl.glTranslated(x, -offsetLabelY, 0);
            gl.glScaled(scaleX, scaleY, 0);
            glut.glutStrokeString(GLUT.STROKE_ROMAN, Util.doubleAsString(x));
            gl.glPopMatrix();
        }
        for(double x = -realStepX; x > xLeft; x -= realStepX) {
            gl.glPushMatrix();
            gl.glTranslated(x, -offsetLabelY, 0);
            gl.glScaled(scaleX, scaleY, 0);
            glut.glutStrokeString(GLUT.STROKE_ROMAN, Util.doubleAsString(x));
            gl.glPopMatrix();
        }
        for(double y = realStepY; y < yUp; y += realStepY) {
            gl.glPushMatrix();
            gl.glTranslated(-offsetLabelX, y, 0);
            gl.glScaled(scaleX, scaleY, 0);
            glut.glutStrokeString(GLUT.STROKE_ROMAN, Util.doubleAsString(y));
            gl.glPopMatrix();
        }
        for(double y = -realStepY; y > yDown; y -= realStepY) {
            gl.glPushMatrix();
            gl.glTranslated(-offsetLabelX, y, 0);
            gl.glScaled(scaleX, scaleY, 0);
            glut.glutStrokeString(GLUT.STROKE_ROMAN, Util.doubleAsString(y));
            gl.glPopMatrix();
        }
    }

    private void optimizeStep() {
        List<Vertex> Q = mate(P);
        Q.addAll(P);

        fronts = nonDominatedSort(Q);
        List<Vertex> C = new ArrayList<>();
        int f = 0;

        while(C.size() + fronts.get(f).size() <= Util.CONFIG.getPopulationSize()) {
            crowdingDistance(fronts.get(f));
            C.addAll(fronts.get(f));
            f++;
        }

        List<Vertex> front = fronts.get(f);
        crowdingDistance(front);
        front.sort(Comparator.naturalOrder());
        C.addAll(front.stream().limit(Util.CONFIG.getPopulationSize() - C.size()).collect(Collectors.toList()));

        P = C;
    }

    private List<Vertex> initialPopulation() {
        List<Vertex> population = new ArrayList<>();

        for(int i = 0; i < Util.CONFIG.getPopulationSize(); i++) {
            population.add(Util.CONFIG.getProblem().generateSolution());
        }

        return population;
    }

    private List<Vertex> mate(List<Vertex> population) {
        List<Vertex> newPopulation = new ArrayList<>();

        while(newPopulation.size() < Util.CONFIG.getPopulationSize()) {
            List<Vertex> children;
            do {
                Vertex parent1 = Util.CONFIG.getSelection().apply(population);
                Vertex parent2 = Util.CONFIG.getSelection().apply(population);

                children = Util.CONFIG.getCrossover().apply(List.of(parent1, parent2));
            } while(children == null);

            for(Vertex child: children) {
                newPopulation.add(Util.CONFIG.getMutation().apply(child));
            }
        }

        return newPopulation;
    }

    private List<List<Vertex>> nonDominatedSort(List<Vertex> population) {
        population.forEach(s -> Util.CONFIG.getProblem().evaluateSolution(s));
        population.forEach(Vertex::reset);

        List<List<Vertex>> fronts = new LinkedList<>();
        List<Vertex> front = new LinkedList<>();
        int currentFront = 0;

        for(int n = 0; n < population.size(); n++) {
            Vertex p = population.get(n);
            for(int s = n + 1; s < population.size(); s++) {
                Vertex q = population.get(s);

                if(dominates(p, q)) {
                    q.increaseDominatedBy();
                    p.setDominates(q);
                }
                if(dominates(q, p)) {
                    p.increaseDominatedBy();
                    q.setDominates(p);
                }
            }

            if(p.getDominatedBy() == 0) {
                front.add(p);
                p.setFront(currentFront);
            }
        }

        while(!front.isEmpty()) {
            currentFront++;
            List<Vertex> H = new LinkedList<>();

            for(Vertex p: front) {
                for(Vertex q: p.getDominates()) {
                    q.decreaseDominatedBy();
                    if(q.getDominatedBy() == 0) {
                        H.add(q);
                        q.setFront(currentFront);
                    }
                }
            }

            fronts.add(front);
            front = H;
        }

        return fronts;
    }

    private boolean dominates(Vertex p, Vertex q) {
        boolean dominates = false;

        for(int i = 0; i < Util.CONFIG.getProblem().getNumberOfObjectives(); i++) {
            if(p.getResult(i) > q.getResult(i)) {
                return false;
            } else if(p.getResult(i) < q.getResult(i)) {
                dominates = true;
            }
        }

        return dominates;
    }

    private void crowdingDistance(List<Vertex> front) {
        int size = front.size();

        for(int m = 0; m < Util.CONFIG.getProblem().getNumberOfObjectives(); m++) {
            int finalM = m;
            front.sort(Comparator.comparingDouble(s -> s.getResult(finalM)));

            front.get(0).increaseDistance(Double.MAX_VALUE);
            front.get(size - 1).increaseDistance(Double.MAX_VALUE);

            for(int i = 1; i < size - 1; i++) {
                front.get(i).increaseDistance(front.get(i + 1).getResult(m) - front.get(i - 1).getResult(m));
            }
        }
    }

    private void drawPoints(GL2 gl) {
        for(Vertex vertex: P) {
            double x = vertex.getResult(0);
            double y = vertex.getResult(1);
            double sizeX = 1/Util.CONFIG.getScaleX() * 0.008;
            double sizeY = 1/Util.CONFIG.getScaleY() * 0.01;

            setColor(gl, vertex.getFront());
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(x - sizeX, y - sizeY, 0);
            gl.glVertex3d(x - sizeX, y + sizeY, 0);
            gl.glVertex3d(x + sizeX, y + sizeY, 0);
            gl.glVertex3d(x + sizeX, y - sizeY, 0);
            gl.glEnd();
        }
    }

    private void setColor(GL2 gl, int front) {
        switch(front) {
            case 0: {
                gl.glColor3f(1, 0, 0);
                break;
            }
            case 1: {
                gl.glColor3f(1, 0.6f, 0);
                break;
            }
            case 2: {
                gl.glColor3f(1, 1, 0);
                break;
            }
            case 3: {
                gl.glColor3f(0, 0.5f, 0);
                break;
            }
            case 4: {
                gl.glColor3f(0, 0, 1);
                break;
            }
            default: {
                gl.glColor3f(1, 0, 1);
            }
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}
    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {}
}
