package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board implements Save {
    private List<Cell> cells;
    private List<Transmitter> transmitters;
    private List<Wall> walls;
    private List<Prize> prizes;
    private Map<Cell, Integer> startingCells;

    public Board() {
        cells = new LinkedList<>();
        transmitters = new LinkedList<>();
        walls = new LinkedList<>();
        startingCells = new HashMap<>();
    }

    public void setPrizes(List<Prize> prizes) { this.prizes = prizes; }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void setTransmitters(List<Transmitter> transmitters) {
        this.transmitters = transmitters;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public void setStartingCells(Map<Cell, Integer> startingCells) {
        this.startingCells = startingCells;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }


    public Map<Cell, Integer> getStartingCells() {
        return startingCells;
    }

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }


    /**
     * give x,y , return a cell with that coordinates
     * return null if not exist.
     */
    //x = satr y = soton
    public Cell getCell(int x, int y) {
        for (Cell helper :
                cells) {
            if (helper.getX() == x && helper.getY() == y) {
                return helper;
            }
        }
        return null;
    }

    public void FillAdjacentCells() {
        for (Cell cell :
                this.cells) {
            int X = cell.getX();
            int Y = cell.getY();
            if (this.getCell(X - 1, Y) != null) {
                cell.getAdjacentCells().add(this.getCell(X - 1, Y));
            }
            if (this.getCell(X, Y - 1) != null) {
                cell.getAdjacentCells().add(this.getCell(X, Y - 1));
            }
            if (this.getCell(X + 1, Y) != null) {
                cell.getAdjacentCells().add(this.getCell(X + 1, Y));
            }
            if (this.getCell(X, Y + 1) != null) {
                cell.getAdjacentCells().add(this.getCell(X, Y + 1));
            }
        }
    }

    public void FillAdjacentOpenCells() {
        for (Cell cell :
                this.cells) {
            int X = cell.getX();
            int Y = cell.getY();
            if (this.getCell(X - 1, Y) != null && !this.HasWall(cell, this.getCell(X - 1, Y))) {
                cell.getAdjacentOpenCells().add(this.getCell(X - 1, Y));
            }
            if (this.getCell(X, Y - 1) != null && !this.HasWall(cell, this.getCell(X, Y - 1))) {
                cell.getAdjacentOpenCells().add(this.getCell(X, Y - 1));
            }
            if (this.getCell(X + 1, Y) != null && !this.HasWall(cell, this.getCell(X + 1, Y))) {
                cell.getAdjacentOpenCells().add(this.getCell(X + 1, Y));
            }
            if (this.getCell(X, Y + 1) != null && !this.HasWall(cell, this.getCell(X, Y + 1))) {
                cell.getAdjacentOpenCells().add(this.getCell(X, Y + 1));
            }
        }
    }

    public boolean HasWall(Cell firstCell, Cell secondCell) {
        for (Wall wall :
                this.walls) {
            if (wall.getCell1() == firstCell && wall.getCell2() == secondCell) {
                return true;
            } else if (wall.getCell1() == secondCell && wall.getCell2() == firstCell) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(String path) throws FileNotFoundException {
        File gameBoard = new File(path);
        PrintStream printStream = new PrintStream(gameBoard);
        printStream.println("CELLS[ 7 16 ]:");
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 16; j++) {
                printStream.print(this.getCell(i, j).getColor().toString() + " " );
            }
            printStream.println();
        }
        printStream.println();
        printStream.println("STARTING_CELLS[ 8 ]:\n" +
                "1 1 1\n" +
                "3 1 1\n" +
                "5 1 1\n" +
                "7 1 1\n" +
                "1 16 2\n" +
                "3 16 2\n" +
                "5 16 2\n" +
                "7 16 2\n");
        printStream.println();
        printStream.println("WALLS[ " + this.getWalls().size() + " ]:");
        for (Wall wall :
                this.getWalls()) {
            int X1 = wall.getCell1().getX();
            int Y1 = wall.getCell1().getY();
            int X2 = wall.getCell2().getX();
            int Y2 = wall.getCell2().getY();
            printStream.println(X1 + " " + Y1 + " " + X2 + " " + Y2);
        }
        printStream.println();
        printStream.println("TRANSMITTERS[ " + this.getTransmitters().size() + " ]:");
        for (Transmitter transmitter :
                this.getTransmitters()) {
            int X1 = transmitter.getFirstCell().getX();
            int Y1 = transmitter.getFirstCell().getY();
            int X2 = transmitter.getLastCell().getX();
            int Y2 = transmitter.getLastCell().getY();
            printStream.println(X1 + " " + Y1 + " " + X2 + " " + Y2 + " " + transmitter.getType());
        }
        printStream.println();
        printStream.println("PRIZES[ " + this.getPrizes().size() + " ]:");
        for (Prize prize :
                this.getPrizes()) {
            int X = prize.getCell().getX();
            int Y = prize.getCell().getY();
            int Point = prize.getPoint();
            int Chance = prize.getChance();
            int DiceNum = prize.getDiceNumber();
            boolean pickedUp = prize.isPickedUp();
            boolean destroyed = prize.isDestroyed();
            printStream.println(X + " " + Y + " " + Point + " " + Chance + " " + DiceNum + " " + pickedUp + " " + destroyed);
        }
        printStream.println();
        printStream.flush();
        printStream.close();
    }


}
