package fer.zemris.racani.particle.system;

import com.jogamp.opengl.GL2;
import fer.zemris.racani.particle.system.model.Polygon;
import fer.zemris.racani.particle.system.model.Vertex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Util {

    public static Object loadTexture(String path) {
        GLuint texture;
        int width = 256, height = 256;
        byte[] data;
        File file = new File(path);

        try(FileInputStream fis = new FileInputStream(file)) { // rb
            data = new byte[(int)file.length()];
            fis.read(data);
        } catch (IOException e) {
            throw new RuntimeException("File " + path + " not found.");
        }

        glGenTextures( 1, &texture );
        glBindTexture( GL_TEXTURE_2D, texture );
        glTexEnvf( GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE );

        glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST );
        glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR );
        glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap ? GL_REPEAT : GL_CLAMP );
        glTexParameterf( GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap ? GL_REPEAT : GL_CLAMP );

        gluBuild2DMipmaps( GL_TEXTURE_2D, 3, width, height, GL_RGB, GL_UNSIGNED_BYTE, data );
        return texture;
    }
}
