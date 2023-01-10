package fer.zemris.racani.particle.system;

import com.jogamp.opengl.GL2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

public class Util {

    public static final Random RAND = new Random();

    public static int loadTexture(String path, GL2 gl, boolean wrap) {
        int[] texture = new int[1];
        int width = 256, height = 256;
        byte[] data;
        File file = new File(path);

        try(FileInputStream fis = new FileInputStream(file)) {
            data = new byte[(int)file.length()];
            fis.read(data);
        } catch (IOException e) {
            throw new RuntimeException("File " + path + " not found.");
        }

        gl.glGenTextures(1, texture, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[0]);
        gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

        gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_NEAREST);
        gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, wrap ? GL2.GL_REPEAT : GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, wrap ? GL2.GL_REPEAT : GL2.GL_CLAMP_TO_EDGE);

        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 3, GL2.GL_RGB, width, height,
                0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(data));
        return texture[0];
    }

    public static int randI(int lower, int upper) {
        return RAND.nextInt(upper - lower) + lower;
    }

    public static float randF(float lower, float upper) {
        return RAND.nextFloat()*(upper - lower) + lower;
    }
}
