package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Player implements Save {
    private final String name;
    private int score;
    private List<Piece> pieces;
    private final Dice dice;
    private Player rival;
    private final int id;
    private int playerNumber;
    private boolean isReady;
    private boolean dicePlayedThisTurn;
    private int moveLeft;
    private Piece selectedPiece;
    int thisTurnDice = -1;
    int previousDice = -1;
    int previousPreviousDice = -1;

    public Player(String name, int score, int id, int playerNumber) {
        this.name = name;
        this.score = score;
        this.id = id;
        this.playerNumber = playerNumber;
        this.dice = new Dice();
        this.pieces = new ArrayList<>();
        this.pieces.add(new Bomber(this, Color.RED));
        this.pieces.add(new Sniper(this, Color.BLUE));
        this.pieces.add(new Healer(this, Color.GREEN));
        this.pieces.add(new Thief(this, Color.YELLOW));
        this.pieces.get(3).setHasPower(true);
        this.moveLeft = 0;
        this.selectedPiece = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dice getDice() {
        return dice;
    }

    public int getScore() {
        return score;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Player getRival() {
        return rival;
    }

    public int getMoveLeft() {
        return moveLeft;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isDicePlayedThisTurn() {
        return dicePlayedThisTurn;
    }

    public void setDicePlayedThisTurn(boolean dicePlayedThisTurn) {
        this.dicePlayedThisTurn = dicePlayedThisTurn;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void setMoveLeft(int moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setRival(Player rival) {
        this.rival = rival;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void applyOnScore(int score) {
        this.score += score;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public void setPreviousDice(int previousDice) {
        this.previousDice = previousDice;
    }

    public void setPreviousPreviousDice(int previousPreviousDice) {
        this.previousPreviousDice = previousPreviousDice;
    }

    public int getPreviousDice() {
        return previousDice;
    }

    public int getPreviousPreviousDice() {
        return previousPreviousDice;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Healer getHealer() {
        for (Piece helper :
                this.pieces) {
            if (helper.getColor() == Color.GREEN) {
                return (Healer) helper;
            }
        }
        return null;
    }

    public Bomber getBomber() {
        for (Piece helper :
                this.pieces) {
            if (helper.getColor() == Color.RED) {
                return (Bomber) helper;
            }
        }
        return null;
    }

    public Sniper getSniper() {
        for (Piece helper :
                this.pieces) {
            if (helper.getColor() == Color.BLUE) {
                return (Sniper) helper;
            }
        }
        return null;
    }

    public Thief getThief() {
        for (Piece helper :
                this.pieces) {
            if (helper.getColor() == Color.YELLOW) {
                return (Thief) helper;
            }
        }
        return null;
    }

    public void setThisTurnDice(int thisTurnDice) {
        this.thisTurnDice = thisTurnDice;
    }

    public int getThisTurnDice() {
        return thisTurnDice;
    }

    /**
     * @param prize according to input prize , apply necessary changes to score and dice chance
     *              <p>
     *              you can use method "addChance" in class "Dice"(not necessary, but recommended)
     */
    public void usePrize(Prize prize) {
        this.score += prize.getPoint();
        this.dice.addChance(prize.getDiceNumber(), prize.getChance());
    }


    /**
     * check if any of player pieces can move to another cell.
     *
     * @return true if at least 1 piece has a move , else return false
     * <p>
     * you can use method "isValidMove" in class "Piece"(not necessary, but recommended)
     */
    public boolean hasMove(Board board, int diceNumber) {
        boolean ans = false;
        for (Piece helper :
                pieces) {
            int pieceX = helper.getCurrentCell().getX();
            int pieceY = helper.getCurrentCell().getY();
            if (board.getCell(pieceX, pieceY - diceNumber) != null) {
                if (helper.isValidMove(board.getCell(pieceX, pieceY - diceNumber), diceNumber)) {
                    ans = true;
                }
            }
            if (board.getCell(pieceX, pieceY + diceNumber) != null) {
                if (helper.isValidMove(board.getCell(pieceX, pieceY + diceNumber), diceNumber)) {
                    ans = true;
                }
            }
            if (board.getCell(pieceX - diceNumber, pieceY) != null) {
                if (helper.isValidMove(board.getCell(pieceX - diceNumber, pieceY), diceNumber)) {
                    ans = true;
                }
            }
            if (board.getCell(pieceX + diceNumber, pieceY) != null) {
                if (helper.isValidMove(board.getCell(pieceX + diceNumber, pieceY), diceNumber)) {
                    ans = true;
                }
            }

        }
        if (!ans){
            System.out.println("No possible move");
        }
        return ans;
    }
    public void save(String path) throws FileNotFoundException {
        File gamePlayer = new File(path);
        PrintStream printStream = new PrintStream(gamePlayer);
        printStream.println("NAME: " + this.getName());
        printStream.println("POINTS: " + this.getScore());
        printStream.println("PLAYERNUMBER: " + this.playerNumber);
        printStream.println("PIECES: ");
        printStream.println("HEALER " + this.getHealer().getCurrentCell().getX() + " " + this.getHealer().getCurrentCell().getY() + " POWER: " + this.getHealer().HasPower() + " ALIVE: " + this.getHealer().isAlive());
        printStream.println("SNIPER " + this.getSniper().getCurrentCell().getX() + " " + this.getSniper().getCurrentCell().getY() + " POWER: " + this.getSniper().HasPower() + " ALIVE: " + this.getSniper().isAlive());
        printStream.println("BOMBER " + this.getBomber().getCurrentCell().getX() + " " + this.getBomber().getCurrentCell().getY() + " POWER: " + this.getBomber().HasPower() + " ALIVE: " + this.getBomber().isAlive());
        printStream.println("THIEF " + this.getThief().getCurrentCell().getX() + " " + this.getThief().getCurrentCell().getY() + " POWER: " + (this.getThief()).isUsedPower() + " ALIVE: " + this.getThief().isAlive());
        if (this.getThief().getPrize() != null) {
            printStream.println("THIEF'S PRIZE " + this.getThief().getPrize().getCell().getX() + " " + this.getThief().getPrize().getCell().getY() + " POINT: " + this.getThief().getPrize().getPoint() + " CHANCE: " + this.getThief().getPrize().getChance() + " DICENUM: " + this.getThief().getPrize().getDiceNumber());
        }
        else {
            printStream.println("THIEF'S PRIZE " + 0 + " " + 0 + " POINT: " + 0 + " CHANCE: " + 0 + " DICENUM: " + 0);
        }
        printStream.println("MoveLeft: " + moveLeft);
        printStream.println("PreviousDice: " + previousDice);
        printStream.println("PreviousPreviousDice: " + previousPreviousDice);
        printStream.println("Dice: " + isDicePlayedThisTurn());
        printStream.println(dice.getChanceOne());
        printStream.println(dice.getChanceTwo());
        printStream.println(dice.getChanceThree());
        printStream.println(dice.getChanceFour());
        printStream.println(dice.getChanceFive());
        printStream.println(dice.getChanceSix());
        printStream.flush();
        printStream.close();

    }


    /**
     * Deselect selectedPiece and make some changes in this class fields.
     */
    // **
    //todo
    public void endTurn() {
        if (this.getSniper().canUsePower()){
            this.getSniper().usePower();
        }
        if (this.getHealer().canUsePower()){
            this.getHealer().usePower();
        }
        if (this.rival.getSniper().canUsePower()){
            this.rival.getSniper().usePower();
        }
        if (this.rival.getHealer().canUsePower()){
            this.getRival().getHealer().usePower();
        }
        this.moveLeft = 0;
        this.setDicePlayedThisTurn(false);
        this.getThief().setUsedPower(false);
        if (this.getSelectedPiece() != null) {
            this.getSelectedPiece().setSelected(false);
        }
        this.setSelectedPiece(null);

    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

