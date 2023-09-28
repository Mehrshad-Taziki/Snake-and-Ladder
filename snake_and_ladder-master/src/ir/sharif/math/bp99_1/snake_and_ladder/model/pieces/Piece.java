package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

import java.util.List;

public abstract class Piece {
    protected Cell currentCell;
    protected final Color color;
    protected final Player player;
    protected boolean isSelected;
    protected boolean hasPower = false;
    boolean alive = true;


    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
        this.hasPower = false;
    }


    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public abstract void usePower();
    public abstract boolean canUsePower();

    public boolean isAlive() {
        return alive;
    }

    public boolean HasPower() {
        return hasPower;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setHasPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid of not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */
    //todo wall
    public boolean isValidMove(Cell destination, int diceNumber) {
        if (this.alive) {
            if (destination.getX() == this.currentCell.getX()) {
                if (Math.abs(destination.getY() - this.currentCell.getY()) == diceNumber) {
                    if (this.NoWall(this.getCurrentCell(), destination)) {
                        if (destination.canEnter(this)) {
                            return true;
                        }
                    }
                }
            }
            if (destination.getY() == this.currentCell.getY()) {
                if (Math.abs(destination.getX() - this.currentCell.getX()) == diceNumber) {
                    if (this.NoWall(this.getCurrentCell(), destination)) {
                        if (destination.canEnter(this)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean NoWall(Cell firstCell, Cell secondCell) {
        if (firstCell == secondCell) {
            return true;
        }
        if (firstCell.getX() == secondCell.getX()) {
            if (firstCell.getY() < secondCell.getY()) {
                for (Cell cell :
                        firstCell.getAdjacentOpenCells()) {
                    if (cell.getY() == firstCell.getY() + 1) {
                        return NoWall(cell, secondCell);
                    }
                }
                return false;
            }
            else {
                for (Cell cell :
                        firstCell.getAdjacentOpenCells()) {
                    if (cell.getY() == firstCell.getY() - 1) {
                        return NoWall(cell, secondCell);
                    }
                }
                return false;
            }
        }
        if (firstCell.getY() == secondCell.getY()) {
            if (firstCell.getX() < secondCell.getX()) {
                for (Cell cell :
                        firstCell.getAdjacentOpenCells()) {
                    if (cell.getX() == firstCell.getX() + 1) {
                        return NoWall(cell, secondCell);
                    }
                }
                return false;
            }
            else {
                for (Cell cell :
                        firstCell.getAdjacentOpenCells()) {
                    if (cell.getX() == firstCell.getX() - 1) {
                        return NoWall(cell, secondCell);
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination) {
        this.currentCell.setPiece(null);
        destination.setPiece(this);
        this.currentCell = destination;
        if (destination.hasPrize() && !destination.getPrize().isPickedUp() && !destination.getPrize().isDestroyed()) {
            this.player.usePrize(destination.getPrize());
        }
        if (this.getColor() == destination.getColor()) {
            this.player.applyOnScore(4);
        }
        if (destination.getTransmitter() != null) {
            destination.getTransmitter().transmit(this);
        }
    }
    public void kill() {
        if (this.getColor() != Color.GREEN) {
            this.alive = false;
            this.hasPower = false;
            if (this.color == Color.RED) {
                System.out.print(this.getPlayer().getName());
                System.out.println("'s Bomber Got Killed");
            }
            if (this.color == Color.YELLOW) {
                System.out.print(this.getPlayer().getName());
                System.out.println("'s Thief Got Killed");
            }
            if (this.color == Color.BLUE) {
                System.out.print(this.getPlayer().getName());
                System.out.println("'s Sniper Got Killed");
            }
        }
    }
}
