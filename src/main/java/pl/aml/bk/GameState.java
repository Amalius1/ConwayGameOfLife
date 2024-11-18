package pl.aml.bk;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameState {

    private Cell[][] cells;
    private final int boardSize;
    private Integer generation = 0;
    private final Map<Integer, Cell[][]> history = new HashMap<>();

    public GameState(int boardSize) {
        this.boardSize = boardSize;
        this.cells = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void addNextGeneration(Cell[][] nextGen) {
        history.put(generation, nextGen);
        this.generation += 1;
        this.cells = nextGen;
    }

}
