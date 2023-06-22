package shaders;

import static org.lwjgl.opengl.GL46.*;

import game.Board;
import jangl.graphics.shaders.FragmentShader;
import jangl.io.Window;

public class BackgroundShader extends FragmentShader {
    public BackgroundShader() {
        super("src/main/resources/shaders/backgroundShader.frag");
    }

    @Override
    public void setUniforms(int programID) {
        int windowSizeLocation = glGetUniformLocation(programID, "windowSize");
        int boardDimensionsLocation = glGetUniformLocation(programID, "boardDimensions");

        glUniform2f(windowSizeLocation, Window.getScreenWidth(), Window.getScreenHeight());
        glUniform2f(boardDimensionsLocation, Board.WIDTH, Board.HEIGHT);
    }
}
