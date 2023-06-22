package pieces;

import game.Board;
import jangl.graphics.Texture;
import org.lwjgl.opengl.GL46;

public class King extends Piece {
    public King(int x, int y, boolean isWhite) {
        super(x, y, new Texture("src/main/resources/pieces/king.png", GL46.GL_LINEAR, true), isWhite);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        int deltaX = Math.abs(newX - this.x);
        int deltaY = Math.abs(newY - this.y);

        return deltaX <= 1 && deltaY <= 1;
    }
}
