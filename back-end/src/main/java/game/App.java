package game;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import game.interfaces.Direction;

public class App extends NanoHTTPD {

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public App() throws IOException {
        super(8081);

        GameState.state = GameStates.ONGOING;
        this.game = new Game();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        switch (uri) {
            case "/newgame" -> {
                Board.resetInstance();
                this.game = new Game();
                GameState.state = GameStates.ONGOING;
            }
            case "/undo" -> this.game = this.game.undo();
            case "/move" -> {
                if(GameState.state != GameStates.ONGOING){
                    break;
                }
                String dir = params.get("dir");
                Direction direction = Direction.valueOf(dir.toUpperCase());
                this.game = this.game.move(direction);
            }
        }
        
        GameState gameplay = GameState.forGame(this.game);
        return newFixedLengthResponse(gameplay.toString());
        
    }
}