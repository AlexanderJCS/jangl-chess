package game;

import jangl.coords.Coords;
import jangl.coords.NDCoords;
import jangl.coords.PixelCoords;
import jangl.graphics.shaders.ShaderProgram;
import jangl.io.mouse.Mouse;
import jangl.io.mouse.MouseEvent;
import jangl.shapes.Rect;
import jangl.sound.Sound;
import org.lwjgl.glfw.GLFW;
import pieces.*;
import shaders.BackgroundShader;

import java.util.ArrayList;
import java.util.List;


public class Board {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private final Sound moveSound;
    private final Rect background;
    private final ShaderProgram backgroundShader;
    private final List<Piece> pieces;
    private Piece selectedPiece;
    private boolean isWhiteTurn;

    public Board() {
        this.background = new Rect(new NDCoords(-1, 1), 2, 2);
        this.backgroundShader = new ShaderProgram(new BackgroundShader());

        this.isWhiteTurn = true;

        this.pieces = new ArrayList<>();
        this.moveSound = new Sound("src/main/resources/sounds/move.ogg");
    }

    public void parseFEN(String fen) {
        int xPos = 0;
        int yPos = 0;

        for (char ch : fen.toCharArray()) {
            if (Character.isDigit(ch)) {
                xPos += Integer.parseInt(String.valueOf(ch));
            } else if (ch == '/') {
                yPos++;
                xPos = -1;  // set to -1 since it will be set to 0 by the end of the iteration
            }

            // Pieces
            char lower = Character.toLowerCase(ch);

            if (lower == 'r') {
                this.pieces.add(new Rook(xPos, yPos, Character.isLowerCase(ch)));
            } if (lower == 'n') {
                this.pieces.add(new Knight(xPos, yPos, Character.isLowerCase(ch)));
            } if (lower == 'b') {
                this.pieces.add(new Bishop(xPos, yPos, Character.isLowerCase(ch)));
            } if (lower == 'q') {
                this.pieces.add(new Queen(xPos, yPos, Character.isLowerCase(ch)));
            } if (lower == 'k') {
                this.pieces.add(new King(xPos, yPos, Character.isLowerCase(ch)));
            } if (lower == 'p') {
                this.pieces.add(new Pawn(xPos, yPos, Character.isLowerCase(ch)));
            }

            xPos++;
        }
    }

    public List<Piece> getPieces() {
        return this.pieces;
    }

    public Piece getPieceAt(Coords coords) {
        return this.getPieceAt((int) coords.x, (int) coords.y);
    }

    public Piece getPieceAt(int x, int y) {
        for (Piece piece : pieces) {
            if ((int) piece.getCoords().x == x && (int) piece.getCoords().y == y) {
                return piece;
            }
        }

        return null;
    }

    private void selectPieceAt(Coords coords) {
        Piece piece = this.getPieceAt(coords);

        if (piece != null && this.isWhiteTurn == piece.isWhite()) {
            this.selectedPiece = piece;
        }
    }

    /**
     * Requires that this.selectedPiece is not null
     * @param coords The grid coords to move to
     */
    private void placeSelectedPieceAt(Coords coords) {
        if (!this.selectedPiece.isValidMove((int) coords.x, (int) coords.y, this)) {
            return;
        }

        if (coords.x >= WIDTH || coords.x < 0 || coords.y >= HEIGHT || coords.y < 0) {
            return;
        }

        Piece pieceToGoTo = this.getPieceAt(coords);

        // Avoid taking your own piece
        if (pieceToGoTo != null && pieceToGoTo.isWhite() == this.selectedPiece.isWhite()) {
            return;
        } if (pieceToGoTo != null) {
            pieceToGoTo.close();
            this.pieces.remove(pieceToGoTo);
        }

        this.selectedPiece.setCoords((int) coords.x, (int) coords.y);
        this.moveSound.play();
        this.isWhiteTurn = !this.isWhiteTurn;
    }

    public void update(List<MouseEvent> mouseEvents) {
        for (MouseEvent mouseEvent : mouseEvents) {
            Coords mouseCoords = NDCoordsToGridCoords(Mouse.getMousePos());

            if (mouseEvent.action == GLFW.GLFW_PRESS) {
                this.selectPieceAt(mouseCoords);
            } else if (mouseEvent.action == GLFW.GLFW_RELEASE) {
                if (this.selectedPiece == null) {
                    return;
                }

                this.placeSelectedPieceAt(mouseCoords);

                // Reset the selected piece position, used in case if it's an illegal move
                Coords pieceCoords = this.selectedPiece.getCoords();
                this.selectedPiece.setCoords((int) pieceCoords.x, (int) pieceCoords.y);
                this.selectedPiece = null;
            }
        }

        if (this.selectedPiece != null) {
            this.selectedPiece.getRect().setCenter(Mouse.getMousePos());
        }
    }

    public void draw() {
        this.background.draw(this.backgroundShader);

        for (Piece piece : this.pieces) {
            if (piece != null && piece != this.selectedPiece) {
                piece.draw();
            }
        }

        if (this.selectedPiece != null) {
            this.selectedPiece.draw();  // Draw the selected piece above all other pieces
        }
    }

    public static NDCoords gridCoordsToNDC(int x, int y) {
        return new NDCoords(
                x / (float) WIDTH * 2 - 1, (y + 1) / (float) HEIGHT * 2 - 1
        );
    }

    public static Coords NDCoordsToGridCoords(NDCoords coords) {
        return new PixelCoords(
                (float) ((coords.x + 1) * 0.5 * WIDTH),
                (float) ((coords.y + 1) * 0.5 * HEIGHT)
        );
    }
}
