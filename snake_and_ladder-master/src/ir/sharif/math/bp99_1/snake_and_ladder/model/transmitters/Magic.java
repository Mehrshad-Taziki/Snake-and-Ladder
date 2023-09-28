package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Magic extends Transmitter{
    public Magic(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }
    public void transmit(Piece piece) {
        if (this.lastCell.canEnter(piece)) {
            piece.moveTo(lastCell);
        }
        piece.setHasPower(true);
        piece.getPlayer().applyOnScore(3);
        System.out.println(piece.getPlayer().getName() + "'s Piece Got Stinged By a Magical Snake");

    }
    public String getType(){
        return "MAGIC";
    }
}
