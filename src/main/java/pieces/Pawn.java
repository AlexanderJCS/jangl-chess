package pieces;

import game.Board;
import jangl.graphics.Texture;
import org.lwjgl.opengl.GL46;

public class Pawn extends Piece {
    private boolean moved;

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, new Texture("src/main/resources/pieces/pawn.png", GL46.GL_LINEAR, false), isWhite);

        this.moved = false;
    }

    private boolean validMoveNotCapturing(int newX, int newY, Board board) {
        int yMaxDelta = this.moved ? 1 : 2;
        yMaxDelta *= this.isWhite() ? 1 : -1;
        int yDelta = newY - this.y;

        int lowerX = Math.min(this.x, newX);
        int higherX = Math.max(this.x, newX);

        for (int i = lowerX; i < higherX; i++) {
            Piece piece = board.getPieceAt(this.x, i);
            if (piece != null && piece != this) {
                return false;
            }
        }

        if (this.isWhite() && this.x == newX && yDelta <= yMaxDelta && yDelta > 0) {
            this.moved = true;
            return true;
        } else if (!this.isWhite() && this.x == newX && yDelta >= yMaxDelta && yDelta < 0) {
            this.moved = true;
            return true;
        } else {
            return false;
        }
    }

    private boolean validMoveCapturing(int newX, int newY) {
        int xDelta = Math.abs(this.x - newX);
        int yDelta = (newY - this.y) * (this.isWhite() ? 1 : -1);

        return xDelta == 1 && yDelta == 1;
    }

    @Override
    public boolean isValidMove(int newX, int newY, Board board) {
        if (board.getPieceAt(newX, newY) == null) {
            if (this.validMoveNotCapturing(newX, newY, board)) {
                this.moved = true;
                return true;
            }
        } else {
            if (this.validMoveCapturing(newX, newY)) {
                this.moved = true;
                return true;
            }
        }

        return false;
    }
}
