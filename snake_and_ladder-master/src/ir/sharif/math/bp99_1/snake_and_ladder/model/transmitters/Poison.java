package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Poison extends Transmitter{
    public Poison(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }
    public void transmit(Piece piece) {
        if (this.lastCell.canEnter(piece)) {
            piece.moveTo(lastCell);
        }
        piece.getPlayer().applyOnScore(-3);
        piece.kill();
        System.out.println(piece.getPlayer().getName() + "'s Piece Got Stinged By a poisonous Snake");
    }
    public String getType(){
        return "POISON";
    }
}
