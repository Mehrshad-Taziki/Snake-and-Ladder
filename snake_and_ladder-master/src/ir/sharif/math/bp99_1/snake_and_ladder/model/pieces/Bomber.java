package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Bomber extends Piece {
    public Bomber(Player player, Color color) {
        super(player, color);
    }

    public void usePower() {
        this.setHasPower(false);
        int X = this.getCurrentCell().getX();
        int Y = this.getCurrentCell().getY();
        this.getCurrentCell().setColor(Color.BLACK);
        this.kill();
        this.setSelected(false);
        this.getPlayer().setSelectedPiece(null);
        getCell(X, Y).setPrize(null);
        if (getCell(X + 1, Y) != null) {
            if (getCell(X + 1, Y).getPrize() != null) {
                getCell(X + 1, Y).getPrize().setDestroyed(true);
                getCell(X + 1, Y).setPrize(null);
            }
            if (getCell(X + 1, Y).getPiece() != null) {
                getCell(X + 1, Y).getPiece().kill();
            }
        }
        if (getCell(X - 1, Y) != null) {
            if (getCell(X - 1, Y).getPrize() != null) {
                getCell(X - 1, Y).getPrize().setDestroyed(true);
                getCell(X - 1, Y).setPrize(null);
            }
            if (getCell(X - 1, Y).getPiece() != null) {
                getCell(X - 1, Y).getPiece().kill();
            }
        }
        if (getCell(X, Y + 1) != null) {
            if (getCell(X, Y + 1).getPrize() != null) {
                getCell(X, Y + 1).getPrize().setDestroyed(true);
                getCell(X, Y + 1).setPrize(null);
            }
            if (getCell(X, Y + 1).getPiece() != null) {
                getCell(X, Y + 1).getPiece().kill();
            }

        }
        if (getCell(X + 1, Y + 1) != null) {
            if (getCell(X + 1, Y + 1).getPrize() != null) {
                getCell(X + 1, Y + 1).getPrize().setDestroyed(true);
                getCell(X + 1, Y + 1).setPrize(null);
            }
            if (getCell(X + 1, Y + 1).getPiece() != null) {
                getCell(X + 1, Y + 1).getPiece().kill();
            }
        }
        if (getCell(X - 1, Y + 1) != null) {
            if (getCell(X - 1, Y + 1).getPrize() != null) {
                getCell(X - 1, Y + 1).getPrize().setDestroyed(true);
                getCell(X - 1, Y + 1).setPrize(null);
            }
            if (getCell(X - 1, Y + 1).getPiece() != null) {
                getCell(X - 1, Y + 1).getPiece().kill();
            }
        }
        if (getCell(X, Y - 1) != null) {
            if (getCell(X, Y - 1).getPrize() != null) {
                getCell(X, Y - 1).getPrize().setDestroyed(true);
                getCell(X, Y - 1).setPrize(null);
            }
            if (getCell(X, Y - 1).getPiece() != null) {
                getCell(X, Y - 1).getPiece().kill();
            }
        }
        if (getCell(X + 1, Y - 1) != null) {
            if (getCell(X + 1, Y - 1).getPrize() != null) {
                getCell(X + 1, Y - 1).getPrize().setDestroyed(true);
                getCell(X + 1, Y - 1).setPrize(null);
            }
            if (getCell(X + 1, Y - 1).getPiece() != null) {
                getCell(X + 1, Y - 1).getPiece().kill();
            }
        }
        if (getCell(X - 1, Y - 1) != null) {
            if (getCell(X - 1, Y - 1).getPrize() != null) {
                getCell(X - 1, Y - 1).getPrize().setDestroyed(true);
                getCell(X - 1, Y - 1).setPrize(null);
            }
            if (getCell(X - 1, Y - 1).getPiece() != null) {
                getCell(X - 1, Y - 1).getPiece().kill();
            }
        }
    }

    @Override
    public boolean canUsePower() {
        if (this.isAlive()) {
            return true;
        }
        return false;
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

    public Cell getCell(int x, int y) {
        for (Cell helper :
                Cell.getCells()) {
            if (helper.getX() == x && helper.getY() == y) {
                return helper;
            }
        }
        return null;
    }
}
