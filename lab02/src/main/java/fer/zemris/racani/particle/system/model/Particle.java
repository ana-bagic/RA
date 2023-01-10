package fer.zemris.racani.particle.system.model;

import java.awt.*;

public class Particle {

    private final Vertex position;
    private Color color;
    private final double size;
    private int time;
    private final Vertex s;
    private final float v;

    private Vertex rotateAxis;
    private double angle;

    public Particle(Vertex position, Color color, double size, int time, Vertex s, float v) {
        this.position = position;
        this.color = color;
        this.size = size;
        this.time = time;
        this.s = s;
        this.v = v;
    }

    public Vertex getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public double getSize() {
        return size;
    }

    public int getTime() {
        return time;
    }

    public Vertex getRotateAxis() {
        return rotateAxis;
    }

    public double getAngle() {
        return angle;
    }

    public void move() {
        position.translate(v*s.getX(), v*s.getY(), v*s.getZ());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void decreaseTime() {
        time--;
    }

    public void setRotateAxis(Vertex rotateAxis) {
        this.rotateAxis = rotateAxis;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

}
