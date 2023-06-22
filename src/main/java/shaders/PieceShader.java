package shaders;

import static org.lwjgl.opengl.GL46.*;

import jangl.graphics.shaders.FragmentShader;

import java.io.UncheckedIOException;

public class PieceShader extends FragmentShader {
    private final boolean isWhite;

    public PieceShader(boolean isWhite) throws UncheckedIOException {
        super("src/main/resources/shaders/pieceShader.frag");
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    @Override
    public void setUniforms(int programID) {
        int location = glGetUniformLocation(programID, "isWhite");
        glUniform1i(location, isWhite ? 1 : 0);
    }
}
