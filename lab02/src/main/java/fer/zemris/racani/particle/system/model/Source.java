package fer.zemris.racani.particle.system.model;

import java.awt.*;

public class Source {

    private final Vertex position;
    private final Color color;
    private double size;
    private int quantity;

    public Source(Vertex position, Color color, double size, int quantity) {
        this.position = position;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantity() {
        if(quantity > 0) {
            quantity--;
        }
    }

    public void increaseQuantity() {
        if(quantity < 70) {
            quantity++;
        }
    }

    public void decreaseSize() {
        size *= 0.98;
    }

    public void increaseSize() {
        size *= 1.02;
    }



}
