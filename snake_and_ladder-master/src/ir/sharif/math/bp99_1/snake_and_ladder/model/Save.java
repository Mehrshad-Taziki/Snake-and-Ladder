package ir.sharif.math.bp99_1.snake_and_ladder.model;

import java.io.File;
import java.io.FileNotFoundException;

public interface Save {
    public void save(String path) throws FileNotFoundException;
}
