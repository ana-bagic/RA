package fer.zemris.racani.particle.system;

import fer.zemris.racani.particle.system.model.Source;
import fer.zemris.racani.particle.system.model.Vertex;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ParticleKeyListener implements KeyListener {

    private final Vertex camera;
    private final Source source;

    public ParticleKeyListener(Vertex camera, Source source) {
        this.camera = camera;
        this.source = source;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key " + e.getKeyChar());
        switch(e.getKeyChar()) {
            case 'w': {
                source.getPosition().translate(0, 1,  0);
                break;
            }
            case 'a': {
                source.getPosition().translate(-1, 0, 0);
                break;
            }
            case 's': {
                source.getPosition().translate(0, -1, 0);
                break;
            }
            case 'd': {
                source.getPosition().translate(1, 0, 0);
                break;
            }
            case 'q': {
                source.decreaseQuantity();
                break;
            }
            case 'e': {
                source.increaseQuantity();
                break;
            }
            case 'n': {
                source.decreaseSize();
                break;
            }
            case 'm': {
                source.increaseSize();
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
