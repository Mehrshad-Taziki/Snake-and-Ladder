package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class GameState {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private final File savedGames;
    private int turn;

    public GameState(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.savedGames = new File("resources/ir/sharif/math/bp99_1/snake_and_ladder/savedGames");
        turn = 0;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer(int i) {
        if (i == 1) return player1;
        else if (i == 2) return player2;
        else return null;
    }

    public boolean isStarted() {
        return turn != 0;
    }

    public int getTurn() {
        return turn;
    }

    //done

    /**
     * return null if game is not started.
     * else return a player who's turn is now.
     */
    public Player getCurrentPlayer() {
        if (isStarted()) {
            if (this.turn % 2 == 0) {
                return this.player2;
            } else {
                return this.player1;
            }
        }

        return null;
    }

    private File getGameFile(String name) throws IOException {
        File[] GamesList = savedGames.listFiles();
        assert GamesList != null;
        for (File Game : GamesList) {
            if (Game.getName().equals(name)) {
                return Game;
            }
        }
        return null;
    }

    public void save() throws IOException {
        String name = "P1" + this.getPlayer1().getName() + "P2" + this.getPlayer2().getName();
        if (getGameFile(name) == null) {
            String path = savedGames.getPath() + "\\" + name;
            File newGameFile = new File(path);
            newGameFile.mkdir();
            File gameBoard = new File(path + "\\" + "board.txt");
            gameBoard.createNewFile();
            File firstPlayer = new File(path + "\\" + "Player1.txt");
            firstPlayer.createNewFile();
            File secondPlayer = new File(path + "\\" + "Player2.txt");
            secondPlayer.createNewFile();
            File turnsPlayed = new File(path + "\\" + "turn.txt");
            turnsPlayed.createNewFile();
            PrintStream printStream = new PrintStream(turnsPlayed);
            printStream.println(this.turn + " TURNS PLAYED");
            printStream.flush();
            printStream.close();
        }
        String path = savedGames.getPath() + "\\" + "P1" + this.getPlayer1().getName() + "P2" + this.getPlayer2().getName() + "\\" + "board.txt";
        this.getBoard().save(path);
        path = savedGames.getPath() + "\\" + "P1" + this.getPlayer1().getName() + "P2" + this.getPlayer2().getName() + "\\" + "Player1.txt";
        this.getPlayer1().save(path);
        path = savedGames.getPath() + "\\" + "P1" + this.getPlayer1().getName() + "P2" + this.getPlayer2().getName() + "\\" + "Player2.txt";
        this.getPlayer2().save(path);
        path = savedGames.getPath() + "\\" + "p1" + this.getPlayer1().getName() + "P2" + this.getPlayer2().getName() + "\\" + "turn.txt";
        File turnsPlayed = new File(path);
        PrintStream printStream = new PrintStream(turnsPlayed);
        printStream.println(this.turn + " TURNS PLAYED");
        printStream.flush();
        printStream.close();
    }
    public void loadTurns(File saved) throws FileNotFoundException {
        String path = saved.getPath() + "\\" + "turn.txt";
        File turnsPlayed = new File(path);
        Scanner scanner = new Scanner(turnsPlayed);
        this.turn = scanner.nextInt();
        scanner.close();

    }
    public void load(File saved, int playerNum) throws FileNotFoundException {
        String path = saved.getPath() + "\\" + "Player" + playerNum + ".txt";
        File gamePlayer = new File(path);
        Scanner scanner = new Scanner(gamePlayer);
        scanner.next();
        scanner.next();
        scanner.next();
        int score = scanner.nextInt();
        getPlayer(playerNum).setScore(score);
        scanner.next();
        scanner.next();
        scanner.next();
        scanner.next();
        int X = scanner.nextInt();
        int Y = scanner.nextInt();
        getPlayer(playerNum).getHealer().setCurrentCell(board.getCell(X, Y));
        board.getCell(X, Y).setPiece(getPlayer(playerNum).getHealer());
        scanner.next();
        boolean power = scanner.nextBoolean();
        getPlayer(playerNum).getHealer().setHasPower(power);
        scanner.next();
        boolean alive = scanner.nextBoolean();
        getPlayer(playerNum).getHealer().setAlive(alive);
        scanner.next();
        X = scanner.nextInt();
        Y = scanner.nextInt();
        getPlayer(playerNum).getSniper().setCurrentCell(board.getCell(X, Y));
        board.getCell(X, Y).setPiece(getPlayer(playerNum).getSniper());
        scanner.next();
        power = scanner.nextBoolean();
        getPlayer(playerNum).getSniper().setHasPower(power);
        scanner.next();
        alive = scanner.nextBoolean();
        getPlayer(playerNum).getSniper().setAlive(alive);
        scanner.next();
        X = scanner.nextInt();
        Y = scanner.nextInt();
        getPlayer(playerNum).getBomber().setCurrentCell(board.getCell(X, Y));
        board.getCell(X, Y).setPiece(getPlayer(playerNum).getBomber());
        scanner.next();
        power = scanner.nextBoolean();
        getPlayer(playerNum).getBomber().setHasPower(power);
        scanner.next();
        alive = scanner.nextBoolean();
        getPlayer(playerNum).getBomber().setAlive(alive);
        scanner.next();
        X = scanner.nextInt();
        Y = scanner.nextInt();
        getPlayer(playerNum).getThief().setCurrentCell(board.getCell(X, Y));
        board.getCell(X, Y).setPiece(getPlayer(playerNum).getThief());
        scanner.next();
        power = scanner.nextBoolean();
        getPlayer(playerNum).getThief().setUsedPower(power);
        scanner.next();
        alive = scanner.nextBoolean();
        getPlayer(playerNum).getThief().setAlive(alive);
        scanner.next();
        scanner.next();
        X = scanner.nextInt();
        Y = scanner.nextInt();
        scanner.next();
        int point = scanner.nextInt();
        scanner.next();
        int chance = scanner.nextInt();
        scanner.next();
        int diceNum = scanner.nextInt();
        if (X > 0) {
            Prize prize = new Prize(board.getCell(X, Y), point, chance, diceNum, true, false);
            board.getPrizes().add(prize);
            getPlayer(playerNum).getThief().setPrize(board.getPrizes().get(board.getPrizes().size()-1));
        }
        else {
            getPlayer(playerNum).getThief().setPrize(null);
        }
        scanner.next();
        getPlayer(playerNum).setMoveLeft(scanner.nextInt());
        scanner.next();
        getPlayer(playerNum).setPreviousDice(scanner.nextInt());
        scanner.next();
        getPlayer(playerNum).setPreviousPreviousDice(scanner.nextInt());
        scanner.next();
        getPlayer(playerNum).setDicePlayedThisTurn(scanner.nextBoolean());
        getPlayer(playerNum).getDice().setChanceOne(scanner.nextInt());
        getPlayer(playerNum).getDice().setChanceTwo(scanner.nextInt());
        getPlayer(playerNum).getDice().setChanceThree(scanner.nextInt());
        getPlayer(playerNum).getDice().setChanceFour(scanner.nextInt());
        getPlayer(playerNum).getDice().setChanceFive(scanner.nextInt());
        getPlayer(playerNum).getDice().setChanceSix(scanner.nextInt());

        scanner.close();
    }
    //todo

    /**
     * finish current player's turn and update some fields of this class;
     * you can use method "endTurn" in class "Player" (not necessary, but Recommanded)
     */
    public void nextTurn() throws IOException {
        this.getCurrentPlayer().endTurn();
        this.turn++;
        this.save();
    }

    public void startGame() {
        this.turn++;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "board=" + board +
                ", playerOne=" + player1 +
                ", playerTwo=" + player2 +
                ", turn=" + turn +
                '}';
    }
}
