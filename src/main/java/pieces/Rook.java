package pieces;

import game.Board;
import jangl.graphics.Texture;
import org.lwjgl.opengl.GL46;

public class Rook extends Piece {
    public Rook(int x, int y, boolean isWhite) {
        super(x, y, new Texture("src/main/resources/pieces/rook.png", GL46.GL_LINEAR, true), isWhite);
    }

    public static boolean isValidMove(int currentX, int currentY, int newX, int newY, Board board, Piece thisPiece) {
        if (newX != currentX && newY != currentY) {
            return false;
        }

        int lowerX = Math.min(currentX, newX);
        int higherX = Math.max(currentX, newX);

        int lowerY = Math.min(currentY, newY);
        int higherY = Math.max(currentY, newY);

        for (int i = lowerX; i < higherX; i++) {
            Piece piece = board.getPieceAt(currentX, i);
            if (piece != null && piece.isWhite() == thisPiece.isWhite() && piece != thisPiece) {
                return false;
            }
        }

        for (int i = lowerY; i < higherY; i++) {
            Piece piece = board.getPieceAt(currentX, i);
            if (piece != null && piece.isWhite() == thisPiece.isWhite() && piece != thisPiece) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        return isValidMove(this.x, this.y, newX, newY, board, this);
    }
}
