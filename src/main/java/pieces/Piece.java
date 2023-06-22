package pieces;

import shaders.PieceShader;
import game.Board;

import jangl.coords.Coords;
import jangl.coords.NDCoords;
import jangl.graphics.Image;
import jangl.graphics.Texture;
import jangl.graphics.shaders.ShaderProgram;
import jangl.graphics.shaders.premade.TextureShaderVert;
import jangl.shapes.Rect;

public abstract class Piece implements AutoCloseable {
    private final Image image;
    protected int x, y;
    private final boolean isWhite;
    private final ShaderProgram shaderProgram;

    public Piece(int x, int y, Texture texture, boolean isWhite) {
        this.x = x;
        this.y = y;

        texture.useDefaultShader(false);
        this.image = new Image(
                new Rect(Board.gridCoordsToNDC(x, y), 2f / Board.WIDTH, 2f / Board.HEIGHT),
                texture
        );

        this.shaderProgram = new ShaderProgram(
                new TextureShaderVert(false), new PieceShader(isWhite)
        );

        this.isWhite = isWhite;
    }

    public Coords getCoords() {
        return new NDCoords(this.x, this.y);
    }

    public boolean isWhite() {
        return this.isWhite;
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;

        this.getRect().setCenter(
                new NDCoords(
                        x / (float) Board.WIDTH * 2 - 1 + 1f / Board.WIDTH,
                        (y + 1) / (float) Board.HEIGHT * 2 - 1 - 1f / Board.HEIGHT
                )
        );
    }

    public Rect getRect() {
        return this.image.rect();
    }

    public void draw() {
        this.shaderProgram.bind();
        this.image.draw();
        ShaderProgram.unbind();
    }

    public abstract boolean isValidMove(int newX, int newY, Board board);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void close() {
        this.image.rect().close();
        this.image.texture().close();
    }
}
