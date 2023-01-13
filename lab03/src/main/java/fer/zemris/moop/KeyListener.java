package fer.zemris.moop;

import fer.zemris.moop.algorithm.Configuration;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        Configuration C = Util.CONFIG;
        double translateX = 1/C.getScaleX() * 0.1;
        double translateY = 1/C.getScaleY() * 0.1;

        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                C.setTranslationY(C.getTranslationY() - translateY);
                break;
            }
            case KeyEvent.VK_LEFT: {
                C.setTranslationX(C.getTranslationX() + translateX);
                break;
            }
            case KeyEvent.VK_DOWN: {
                C.setTranslationY(C.getTranslationY() + translateY);
                break;
            }
            case KeyEvent.VK_RIGHT: {
                C.setTranslationX(C.getTranslationX() - translateX);
                break;
            }
            case KeyEvent.VK_W: {
                C.setScaleY(C.getScaleY() + 0.01);
                break;
            }
            case KeyEvent.VK_A: {
                C.setScaleX(C.getScaleX() + 0.01);
                break;
            }
            case KeyEvent.VK_S: {
                if(C.getScaleY() > 0.01) {
                    C.setScaleY(C.getScaleY() - 0.01);
                }
                break;
            }
            case KeyEvent.VK_D: {
                if(C.getScaleX() > 0.01) {
                    C.setScaleX(C.getScaleX() - 0.01);
                }
                break;
            }
            case KeyEvent.VK_P: {
                C.setPlay(!C.isPlay());
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
