package fer.zemris.racani.particle.system.model;

public class Vertex {

    private float x;
    private float y;
    private float z;

    public Vertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void translate(float deltaX, float deltaY, float deltaZ) {
        this.x += deltaX;
        this.y += deltaY;
        this.z += deltaZ;
    }

    public void divide(double amount) {
        this.x /= amount;
        this.y /= amount;
        this.z /= amount;
    }

    public double norm() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    public static double multiply(Vertex v1, Vertex v2) {
        return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
    }
}
