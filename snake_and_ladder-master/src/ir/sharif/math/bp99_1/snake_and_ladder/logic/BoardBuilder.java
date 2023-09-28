package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Wall;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Magic;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Poison;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Worm;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.*;


public class BoardBuilder {
    public Scanner boardCreator;

    public BoardBuilder(Scanner boardCreator) {
        this.boardCreator = boardCreator;
    }

    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */
    public Board build() {
        Board base = new Board();
        this.boardCreator.next();
        int x = this.boardCreator.nextInt();
        int y = this.boardCreator.nextInt();
        this.boardCreator.next();
        LinkedList<Cell> cells = new LinkedList<>();
        for (int i = 1; i <= x; i++) {
            for (int j = 1; j <= y; j++) {
                Cell helper = new Cell(Color.valueOf(this.boardCreator.next()), i, j);
                cells.add(helper);
            }
        }
        base.setCells(cells);
        this.boardCreator.next();
        int startingCellsCount = this.boardCreator.nextInt();
        this.boardCreator.next();
        Map<Cell, Integer> StartingCells = new HashMap<>();
        for (int i = 0; i < startingCellsCount; i++) {
            int CellX = this.boardCreator.nextInt();
            int CellY = this.boardCreator.nextInt();
            int PlayersCell = this.boardCreator.nextInt();
            StartingCells.put(base.getCell(CellX, CellY), PlayersCell);
        }
        base.setStartingCells(StartingCells);
        this.boardCreator.next();
        int BoardWalls = this.boardCreator.nextInt();
        this.boardCreator.next();
        LinkedList<Wall> Walls = new LinkedList<>();
        for (int i = 0; i < BoardWalls; i++) {
            int firstX = this.boardCreator.nextInt();
            int firstY = this.boardCreator.nextInt();
            int secondX = this.boardCreator.nextInt();
            int secondY = this.boardCreator.nextInt();
            Wall helper = new Wall(base.getCell(firstX, firstY), base.getCell(secondX, secondY));
            Walls.add(helper);
        }
        base.setWalls(Walls);
        this.boardCreator.next();
        int TransmitterCount = this.boardCreator.nextInt();
        this.boardCreator.next();
        LinkedList<Transmitter> Transmitters = new LinkedList<>();
        for (int i = 0; i < TransmitterCount; i++) {
            int firstX = this.boardCreator.nextInt();
            int firstY = this.boardCreator.nextInt();
            int secondX = this.boardCreator.nextInt();
            int secondY = this.boardCreator.nextInt();
            String type = this.boardCreator.next();
            if (type.equals("NORMAL")) {
                Transmitter helper = new Transmitter(base.getCell(firstX, firstY), base.getCell(secondX, secondY));
                Transmitters.add(helper);
                base.getCell(firstX, firstY).setTransmitter(helper);
            }
            if (type.equals("MAGIC")) {
                Magic helper = new Magic(base.getCell(firstX, firstY), base.getCell(secondX, secondY));
                Transmitters.add(helper);
                base.getCell(firstX, firstY).setTransmitter(helper);
            }
            if (type.equals("WORM")) {
                Worm helper = new Worm(base.getCell(firstX, firstY), base.getCell(secondX, secondY));
                Transmitters.add(helper);
                base.getCell(firstX, firstY).setTransmitter(helper);
            }
            if (type.equals("POISON")) {
                Poison helper = new Poison(base.getCell(firstX, firstY), base.getCell(secondX, secondY));
                Transmitters.add(helper);
                base.getCell(firstX, firstY).setTransmitter(helper);
            }

        }
        base.setTransmitters(Transmitters);
        this.boardCreator.next();
        int PrizeCount = this.boardCreator.nextInt();
        this.boardCreator.next();
        ArrayList<Prize> prize = new ArrayList<>();
        for (int i = 0; i < PrizeCount; i++) {
            int X = this.boardCreator.nextInt();
            int Y = this.boardCreator.nextInt();
            int Point = this.boardCreator.nextInt();
            int Chance = this.boardCreator.nextInt();
            int DiceNum = this.boardCreator.nextInt();
            boolean pickedUp = this.boardCreator.nextBoolean();
            boolean destroyed = this.boardCreator.nextBoolean();
            Prize helper = new Prize(base.getCell(X, Y), Point, Chance, DiceNum, pickedUp, destroyed);
            prize.add(helper);
            if (!pickedUp && !destroyed) {
                base.getCell(X, Y).setPrize(helper);
            }
        }
        Cell.cells.addAll(base.getCells());
        base.setPrizes(prize);
        this.boardCreator.close();
        base.FillAdjacentCells();
        base.FillAdjacentOpenCells();
        return base;
    }


}
