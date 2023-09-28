package ir.sharif.math.bp99_1.snake_and_ladder.graphic.Listeners;

import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.graphic.models.GraphicalCell;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class BoardMouseListener implements DummyListener {
    private final GraphicalAgent graphicalAgent;

    public BoardMouseListener(GraphicalAgent graphicalAgent) {
        this.graphicalAgent = graphicalAgent;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int xa = e.getX();
        int ya = e.getY();
        try {
            graphicalAgent.clickRequest((ya / GraphicalCell.CELL_SIZE) + 1, (xa / GraphicalCell.CELL_SIZE) + 1);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
