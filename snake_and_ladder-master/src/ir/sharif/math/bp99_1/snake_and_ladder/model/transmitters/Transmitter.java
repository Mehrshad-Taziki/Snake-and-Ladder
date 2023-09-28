package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Transmitter {
    protected final Cell firstCell, lastCell;

    public Transmitter(Cell firstCell, Cell lastCell) {
        this.firstCell = firstCell;
        this.lastCell = lastCell;

    }

    public Cell getFirstCell() {
        return firstCell;
    }

    public Cell getLastCell() {
        return lastCell;
    }

    /**
     * transmit piece to lastCell
     */

    public void transmit(Piece piece) {
        if (this.lastCell.canEnter(piece)) {
            piece.moveTo(lastCell);
        }
        piece.getPlayer().applyOnScore(-3);
        System.out.println(piece.getPlayer().getName() + "'s Piece Got Stinged By a Normal Snake");
    }
    public String getType(){
        return "NORMAL";
    }
}
