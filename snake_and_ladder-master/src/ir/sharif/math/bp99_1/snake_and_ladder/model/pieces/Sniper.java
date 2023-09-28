package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece {
    public Sniper(Player player, Color color) {
        super(player, color);
    }

    public void usePower() {
        for (Cell cell : this.getCurrentCell().getAdjacentCells()) {
            if (cell.getPiece() != null) {
                if (cell.getPiece().isAlive() && cell.getPiece().getColor() != Color.GREEN && cell.getPiece().getPlayer() != this.getPlayer()) {
                    cell.getPiece().kill();
                }
            }
        }
        this.setHasPower(false);
        System.out.println("SNIPER'S POWER TURNED OFF");

    }

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
            this.player.applyOnScore(-3);
            destination.getTransmitter().transmit(this);
        }
    }

    @Override
    public boolean canUsePower() {
        if (this.alive) {
            if (this.hasPower) {
                for (Cell cell : this.getCurrentCell().getAdjacentCells()) {
                    if (cell.getPiece() != null) {
                        if (cell.getPiece().isAlive() && cell.getPiece().getColor() != Color.GREEN && cell.getPiece().getPlayer() != this.getPlayer()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
