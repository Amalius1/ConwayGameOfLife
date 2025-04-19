package pl.aml.bk.core;

import lombok.Getter;
import pl.aml.bk.core.display.Presenter;

import java.util.Random;

public class Game {

    private final Random random = new Random();
    private final Presenter presenter;

    @Getter
    private final GameState gameState;

    public Game(int size, Presenter presenter) {
        this.gameState = new GameState(size);
        this.presenter = presenter;
    }

    public void printBoard() {
        presenter.display(gameState);
    }

    public void nextGeneration() {
        final int boardSize = gameState.getBoardSize();
        Cell[][] nextGeneration = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Neighbourhood neighbours = getNeighbours(i, j);
                Cell cell = new Cell();
                cell.setAlive(neighbours.calculateIfSurvives());
                nextGeneration[i][j] = cell;
            }
        }
        gameState.addNextGeneration(nextGeneration);
    }

    private Neighbourhood getNeighbours(int x, int y) {
        Neighbourhood neighbours = new Neighbourhood(gameState.getCells()[x][y]);
        neighbours.construct(gameState.getCells(), x, y);
        return neighbours;
    }

    public void randomizeBoard() {
        for (Cell[] row :
                gameState.getCells()) {
            for (Cell cell :
                    row) {
                cell.setAlive(random.nextBoolean());
            }
        }
    }

    public void setBoardManually(Cell[][] cells) {
        if (cells.length != this.gameState.getBoardSize()) {
            throw new IllegalArgumentException("Board must be of the same size!");
        }
        this.gameState.setCells(cells);
    }

}
