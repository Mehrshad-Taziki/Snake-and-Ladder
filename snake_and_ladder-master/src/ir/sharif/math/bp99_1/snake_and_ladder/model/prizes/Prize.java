package ir.sharif.math.bp99_1.snake_and_ladder.model.prizes;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Prize {
    private Cell cell;
    private final int point;
    private final int chance, diceNumber;
    private boolean pickedUp;
    private boolean destroyed;


    public Prize(Cell cell, int point, int chance, int diceNumber, boolean pickedUp, boolean destroyed) {
        this.cell = cell;
        this.point = point;
        this.chance = chance;
        this.diceNumber = diceNumber;
        this.pickedUp = false;
        this.destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public int getPoint() {
        return point;
    }

    public Cell getCell() {
        return cell;
    }

    public int getChance() {
        return chance;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getDiceNumber() {
        return diceNumber;
    }


    /**
     * apply the prize.
     * you can use method "usePrize" in class "Player" (not necessary, but recommended)
     */
    //done
    public void using(Piece piece) {
        piece.getPlayer().usePrize(this);
    }

}
