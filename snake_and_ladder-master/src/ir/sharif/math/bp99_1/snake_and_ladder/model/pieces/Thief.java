package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

import javax.print.attribute.standard.NumberUp;

public class Thief extends Piece {
    public Thief(Player player, Color color) {
        super(player, color);
    }

    @Override
    public void usePower() {
        if ((this.getPrize()) != null) {
            if (this.currentCell.getPrize() != null) {
                this.currentCell.getPrize().setDestroyed(true);
            }
            this.getCurrentCell().setPrize(this.getPrize());
            this.getPrize().setCell(this.getCurrentCell());
            this.getPrize().setPickedUp(false);
            this.setPrize(null);
            this.currentCell.getPrize().setCell(this.currentCell);
            this.setUsedPower(true);
            this.setSelected(false);
            this.getPlayer().setSelectedPiece(null);
        } else if (this.getCurrentCell().getPrize() != null) {
            this.setPrize(this.getCurrentCell().getPrize());
            this.getCurrentCell().setPrize(null);
            this.getPrize().setPickedUp(true);
            this.setUsedPower(true);
            this.setSelected(false);
            this.getPlayer().setSelectedPiece(null);
        }

    }

    @Override
    public boolean canUsePower() {
        if (this.isAlive()) {
            if (this.prize != null) {
                if (!this.isUsedPower()) {
                    return true;
                }
            }
            if (this.getCurrentCell().getPrize() != null) {
                if (!this.isUsedPower()) {
                    return true;
                }
            }
        }
        return false;
    }

    Prize prize = null;

    boolean usedPower = false;

    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public boolean isUsedPower() {
        return usedPower;
    }

    public void setUsedPower(boolean usedPower) {
        this.usedPower = usedPower;
    }

    @Override
    public boolean isValidMove(Cell destination, int diceNumber) {
        if (isAlive()) {
            if (!this.isUsedPower()) {
                if (destination.getX() == this.currentCell.getX()) {
                    if (Math.abs(destination.getY() - this.currentCell.getY()) == diceNumber) {
                        if (destination.getPiece() == null) {
                            return true;
                        }
                    }
                }
                if (destination.getY() == this.currentCell.getY()) {
                    if (Math.abs(destination.getX() - this.currentCell.getX()) == diceNumber) {
                        if (destination.getPiece() == null) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void moveTo(Cell destination) {
        this.currentCell.setPiece(null);
        destination.setPiece(this);
        this.currentCell = destination;
        if (destination.hasPrize() && !destination.getPrize().isPickedUp() && !destination.getPrize().isDestroyed()) {
            if (this.prize == null) {
                this.player.usePrize(destination.getPrize());
            }
        }
        if (this.getColor() == destination.getColor()) {
            this.player.applyOnScore(4);
        }
        if (destination.getTransmitter() != null) {
            this.player.applyOnScore(-3);
            destination.getTransmitter().transmit(this);
            if (this.getPrize() != null) {
                this.getPrize().setDestroyed(true);
                this.getPrize().setPickedUp(false);
                this.setPrize(null);
            }
        }
    }

}
