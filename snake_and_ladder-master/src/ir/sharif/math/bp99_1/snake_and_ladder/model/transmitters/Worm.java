package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import java.util.Random;

public class Worm extends Transmitter{
    public Worm(Cell firstCell, Cell lastCell) {
        super(firstCell, lastCell);
    }
    public void transmit(Piece piece) {
         Random rand = new Random();
         int number = rand.nextInt(Cell.getCells().size());

        if (Cell.getCells().get(number).canEnter(piece)) {
            piece.moveTo(Cell.getCells().get(number));
        }
        piece.getPlayer().applyOnScore(-3);
        System.out.println(piece.getPlayer().getName() + "'s Piece Got Stinged By a Worm");
    }
    public String getType(){
        return "WORM";
    }
}
