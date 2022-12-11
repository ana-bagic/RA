package fer.zemris.racani.particle.system.model;

public class Vertex {

    private double x;
    private double y;
    private double z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void translate(double deltaX, double deltaY, double deltaZ) {
        this.x += deltaX;
        this.y += deltaY;
        this.z += deltaZ;
    }

    public void translate(Vertex vertex) {
        this.x += vertex.x;
        this.y += vertex.y;
        this.z += vertex.z;
    }

    public void divide(double amount) {
        this.x /= amount;
        this.y /= amount;
        this.z /= amount;
    }

}
