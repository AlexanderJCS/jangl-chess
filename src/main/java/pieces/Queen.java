package pieces;

import game.Board;
import jangl.graphics.Texture;
import org.lwjgl.opengl.GL46;

public class Queen extends Piece {
    public Queen(int x, int y, boolean isWhite) {
        super(x, y, new Texture("src/main/resources/pieces/queen.png", GL46.GL_LINEAR, false), isWhite);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        return Rook.isValidMove(this.x, this.y, newX, newY, board, this) ||
                Bishop.isValidMove(this.x, this.y, newX, newY, board, this);
    }
}
