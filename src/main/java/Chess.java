import game.Board;
import jangl.JANGL;
import jangl.io.Window;
import jangl.io.mouse.Mouse;

public class Chess {
    private final Board board;

    public Chess() {
        this.board = new Board();
        this.board.parseFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    public void update() {
        this.board.update(Mouse.getEvents());
    }

    public void draw() {
        Window.clear();
        this.board.draw();
    }

    public void run() {
        while (Window.shouldRun()) {
            this.draw();
            this.update();

            JANGL.update();
        }
    }

    public static void main(String[] args) {
        JANGL.init(900, 900);
        Window.setVsync(true);

        new Chess().run();

        Window.close();
    }
}