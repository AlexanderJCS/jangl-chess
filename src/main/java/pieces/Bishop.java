package pieces;

import game.Board;
import jangl.graphics.Texture;
import org.lwjgl.opengl.GL46;

public class Bishop extends Piece {
    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, new Texture("src/main/resources/pieces/bishop.png", GL46.GL_LINEAR, false), isWhite);
    }

    public static boolean isValidMove(int currentX, int currentY, int newX, int newY, Board board, Piece thisPiece) {
        int deltaX = Math.abs(newX - currentX);
        int deltaY = Math.abs(newY - currentY);

        if (deltaX != deltaY) {
            return false;
        }

        int xAdd = newX > currentX ? 1 : -1;
        int yAdd = newY > currentY ? 1 : -1;

        int testX = currentX;
        int testY = currentY;

        for (int i = 0; i < deltaX; i++) {
            Piece piece = board.getPieceAt(testX, testY);
            if (piece != null && piece != thisPiece) {
                return false;
            }

            testX += xAdd;
            testY += yAdd;
        }

        return true;
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        return isValidMove(this.x, this.y, newX, newY, board, this);
    }
}
