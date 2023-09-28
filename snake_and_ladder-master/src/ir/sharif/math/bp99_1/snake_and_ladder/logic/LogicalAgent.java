package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Thief;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private final GameState gameState;
    private boolean started = false;

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent() throws IOException {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
    }


    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState() throws IOException {
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1, false);
        Player player2;
        Board board;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2, false);
        } while (player1.equals(player2));
        String name = "P1" + player1.getName() + "P2" + player2.getName();
        if (getGameFile(name) != null) {
            started = true;
        }
        String path;
        if (started) {
            path = getGameFile(name).getPath() + "\\" + "board.txt";
        } else {
            path = "resources/ir/sharif/math/bp99_1/snake_and_ladder/1.board";
        }
        File boardFile = new File(path);
        board = modelLoader.loadBord(boardFile);
        player1.setRival(player2);
        player2.setRival(player1);
        GameState gameState = new GameState(board, player1, player2);
        if (started) {
            gameState.load(getGameFile(name), 1);
            gameState.load(getGameFile(name), 2);
//            gameState.loadTurns(getGameFile(name));
        }
        return gameState;
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */
    //todo add shode ast list staring piece
    public void readyPlayer(int playerNumber) throws IOException {
        String name = "P1" + gameState.getPlayer1().getName() + "P2" + gameState.getPlayer2().getName();
        if (playerNumber == 1) {
            gameState.getPlayer1().setReady(!gameState.getPlayer1().isReady());
        } else if (playerNumber == 2) {
            gameState.getPlayer2().setReady(!gameState.getPlayer2().isReady());
        }
        // dont touch this line
        if (gameState.getPlayer1().isReady() && gameState.getPlayer2().isReady()) {
            gameState.startGame();
            if (!started) {
                for (Map.Entry<Cell, Integer> helper :
                        gameState.getBoard().getStartingCells().entrySet()) {
                    if (helper.getValue() == 1) {
                        int X = helper.getKey().getX();
                        int Y = helper.getKey().getY();
                        Color color = gameState.getBoard().getCell(X, Y).getColor();
                        for (Piece piece :
                                gameState.getPlayer1().getPieces()) {
                            if (piece.getColor() == color) {
                                piece.setCurrentCell(gameState.getBoard().getCell(X, Y));
                                gameState.getBoard().getCell(X, Y).setPiece(piece);
                            }
                        }
                    }
                    if (helper.getValue() == 2) {
                        int X = helper.getKey().getX();
                        int Y = helper.getKey().getY();
                        Color color = gameState.getBoard().getCell(X, Y).getColor();
                        for (Piece piece :
                                gameState.getPlayer2().getPieces()) {
                            if (piece.getColor() == color) {
                                piece.setCurrentCell(gameState.getBoard().getCell(X, Y));
                                gameState.getBoard().getCell(X, Y).setPiece(piece);
                            }
                        }
                    }
                }
            }
            if (started) {
                gameState.loadTurns(getGameFile(name));
            }
        }
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    //check
    public void selectCell(int x, int y) throws IOException {
        if (gameState.isStarted()) {
            if (gameState.getCurrentPlayer().isDicePlayedThisTurn()) {
                if (gameState.getCurrentPlayer().getSelectedPiece() != null) {
                    if (gameState.getBoard().getCell(x, y).getPiece() != null) {
                        if (gameState.getCurrentPlayer().getSelectedPiece().getColor() == Color.YELLOW) {
                            this.thiefSelected(x, y);
                        } else {
                            if (gameState.getCurrentPlayer().getSelectedPiece() == gameState.getBoard().getCell(x, y).getPiece()) {
                                if (gameState.getCurrentPlayer().getSelectedPiece().getColor() == Color.RED) {
                                    if (gameState.getCurrentPlayer().getSelectedPiece().HasPower()) {
                                        gameState.getCurrentPlayer().getBomber().usePower();
                                        if (!gameState.getCurrentPlayer().hasMove(gameState.getBoard(), gameState.getCurrentPlayer().getMoveLeft())) {
                                            gameState.getCurrentPlayer().applyOnScore(-3);
                                            gameState.nextTurn();
                                        }
                                    } else {
                                        gameState.getCurrentPlayer().getSelectedPiece().setSelected(false);
                                        gameState.getCurrentPlayer().setSelectedPiece(null);
                                    }
                                } else {
                                    gameState.getCurrentPlayer().getSelectedPiece().setSelected(false);
                                    gameState.getCurrentPlayer().setSelectedPiece(null);
                                }
                            } else {
                                if (gameState.getBoard().getCell(x, y).getPiece().getPlayer() == gameState.getCurrentPlayer()) {
                                    if (gameState.getBoard().getCell(x, y).getPiece().isAlive()) {
                                        gameState.getCurrentPlayer().getSelectedPiece().setSelected(false);
                                        gameState.getCurrentPlayer().setSelectedPiece(null);
                                        gameState.getCurrentPlayer().setSelectedPiece(gameState.getBoard().getCell(x, y).getPiece());
                                        gameState.getBoard().getCell(x, y).getPiece().setSelected(true);
                                    }
                                }
                            }
                        }
                    } else {
                        if (gameState.getCurrentPlayer().getSelectedPiece().isValidMove(gameState.getBoard().getCell(x, y), gameState.getCurrentPlayer().getMoveLeft())) {
                            gameState.getCurrentPlayer().getSelectedPiece().moveTo(gameState.getBoard().getCell(x, y));
                            gameState.nextTurn();
                        }
                    }
                } else {
                    if (gameState.getBoard().getCell(x, y).getPiece() != null) {
                        if (gameState.getBoard().getCell(x, y).getPiece().getPlayer() == gameState.getCurrentPlayer()) {
                            if (gameState.getBoard().getCell(x, y).getPiece().isAlive()) {
                                gameState.getCurrentPlayer().setSelectedPiece(gameState.getBoard().getCell(x, y).getPiece());
                                gameState.getBoard().getCell(x, y).getPiece().setSelected(true);
                            }
                        }
                    }
                }
                gameState.save();
            }
            // dont touch this line
            graphicalAgent.update(gameState);
            checkForEndGame();
        }
    }


    public void thiefSelected(int x, int y) throws IOException {
        if (gameState.getBoard().getCell(x, y).getPiece() != null) {
            if (gameState.getCurrentPlayer().getSelectedPiece() == gameState.getBoard().getCell(x, y).getPiece()) {
                if (!((Thief) gameState.getCurrentPlayer().getSelectedPiece()).isUsedPower()) {
                    if (((Thief) gameState.getCurrentPlayer().getSelectedPiece()).canUsePower()) {
                        ((Thief) gameState.getCurrentPlayer().getSelectedPiece()).usePower();
                        if (!gameState.getCurrentPlayer().hasMove(gameState.getBoard(), gameState.getCurrentPlayer().getMoveLeft())) {
                            gameState.getCurrentPlayer().applyOnScore(-3);
                            gameState.nextTurn();
                        }
                    } else {
                        gameState.getCurrentPlayer().getSelectedPiece().setSelected(false);
                        gameState.getCurrentPlayer().setSelectedPiece(null);
                    }
                } else {
                    gameState.getCurrentPlayer().getSelectedPiece().setSelected(false);
                    gameState.getCurrentPlayer().setSelectedPiece(null);
                }
            } else {
                gameState.getCurrentPlayer().getSelectedPiece().setSelected(false);
                gameState.getCurrentPlayer().setSelectedPiece(null);
            }
        } else {
            if (gameState.getCurrentPlayer().getSelectedPiece().isValidMove(gameState.getBoard().getCell(x, y), gameState.getCurrentPlayer().getMoveLeft())) {
                if (!((Thief) gameState.getCurrentPlayer().getSelectedPiece()).isUsedPower()) {
                    gameState.getCurrentPlayer().getSelectedPiece().moveTo(gameState.getBoard().getCell(x, y));
                    gameState.nextTurn();
                }
            }
        }
        gameState.save();
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame() throws IOException {
        if (gameState.getTurn() > 40) {
            int winner = 1;
            if (gameState.getPlayer1().getScore() > gameState.getPlayer2().getScore()) {
                winner = 1;
            } else if (gameState.getPlayer1().getScore() < gameState.getPlayer2().getScore()) {
                winner = 2;
            } else {
                winner = 3;
            }
            // dont touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            String name = "P1" + gameState.getPlayer1().getName() + "P2" + gameState.getPlayer2().getName();
            String help = "resources/ir/sharif/math/bp99_1/snake_and_ladder/savedGames" + "\\" + name;
            String path = help + "\\" + "board.txt";
            Files.delete(Paths.get(path));
            path = help + "\\" + "Player1.txt";
            Files.delete(Paths.get(path));
            path = help + "\\" + "Player2.txt";
            Files.delete(Paths.get(path));
            path = help + "\\" + "turn.txt";
            Files.delete(Paths.get(path));
            Files.delete(Paths.get(help));
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();

        }
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public void rollDice(int playerNumber) throws IOException {
        if (gameState.isStarted()) {
            if (gameState.getCurrentPlayer() == gameState.getPlayer(playerNumber)) {
                if (!gameState.getPlayer(playerNumber).isDicePlayedThisTurn()) {
                    gameState.getPlayer(playerNumber).setPreviousPreviousDice(gameState.getPlayer(playerNumber).getPreviousDice());
                    gameState.getPlayer(playerNumber).setPreviousDice(gameState.getPlayer(playerNumber).getThisTurnDice());
                    gameState.getPlayer(playerNumber).setMoveLeft(gameState.getPlayer(playerNumber).getDice().roll());
                    gameState.getPlayer(playerNumber).setThisTurnDice(gameState.getPlayer(playerNumber).getMoveLeft());
                    if (gameState.getPlayer(playerNumber).getMoveLeft() == 6) {
                        gameState.getPlayer(playerNumber).applyOnScore(4);
                    }
                    if (gameState.getPlayer(playerNumber).getMoveLeft() == 1) {
                        gameState.getPlayer(playerNumber).getHealer().setHasPower(true);
                    }
                    if (gameState.getPlayer(playerNumber).getMoveLeft() == 5) {
                        if (gameState.getPlayer(playerNumber).getSniper().isAlive()) {
                            gameState.getPlayer(playerNumber).getSniper().setHasPower(true);
                        }
                    }
                    if (gameState.getPlayer(playerNumber).getMoveLeft() == 3) {
                        if (gameState.getPlayer(playerNumber).getBomber().isAlive()) {
                            gameState.getPlayer(playerNumber).getBomber().setHasPower(true);
                        }
                    }
                    if (gameState.getPlayer(playerNumber).getMoveLeft() == 2) {
                        gameState.getPlayer(playerNumber).getDice().resetDice();
                    }
                    if (gameState.getPlayer(playerNumber).getPreviousDice() == gameState.getPlayer(playerNumber).getThisTurnDice()) {
                        if (gameState.getPlayer(playerNumber).getPreviousPreviousDice() != gameState.getPlayer(playerNumber).getMoveLeft()) {
                            gameState.getPlayer(playerNumber).getDice().addChance(gameState.getPlayer(playerNumber).getPreviousDice(), 1);
                        }
                    }
                    gameState.getPlayer(playerNumber).setDicePlayedThisTurn(true);
                    if (!gameState.getPlayer(playerNumber).hasMove(gameState.getBoard(), gameState.getPlayer(playerNumber).getMoveLeft())) {
                        gameState.getPlayer(playerNumber).applyOnScore(-3);
                        gameState.nextTurn();
                    }
                }
            }
            gameState.save();
        }
        // dont touch this line
        graphicalAgent.update(gameState);
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber) {
        if (playerNumber == 1) {
            return gameState.getPlayer1().getDice().getDetails();
        } else if (playerNumber == 2) {
            return gameState.getPlayer2().getDice().getDetails();
        }
        return null;
    }

    private File getGameFile(String name) throws IOException {
        File savedGames = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/savedGames");
        File[] Gameslist = savedGames.listFiles();
        assert Gameslist != null;
        for (File Game : Gameslist) {
            if (Game.getName().equals(name)) {
                return Game;
            }
        }
        return null;
    }
}
