package pl.aml.bk.core;

import lombok.Getter;
import pl.aml.bk.core.display.Presenter;

import java.io.*;
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

    /**
     * Saves the current board state to a text file.
     * Uses '1' for alive cells and '0' for dead cells.
     *
     * @param file The file to save to
     * @throws IOException If an I/O error occurs
     */
    public void saveToFile(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write board size on the first line
            writer.write(String.valueOf(gameState.getBoardSize()));
            writer.newLine();

            // Write board state
            Cell[][] cells = gameState.getCells();
            for (int i = 0; i < gameState.getBoardSize(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < gameState.getBoardSize(); j++) {
                    line.append(cells[i][j].isAlive() ? '1' : '0');
                }
                writer.write(line.toString());
                writer.newLine();
            }
        }
    }

    /**
     * Loads a board state from a text file.
     * Expects '1' for alive cells and '0' for dead cells.
     *
     * @param file The file to load from
     * @throws IOException              If an I/O error occurs
     * @throws IllegalArgumentException If the file format is invalid or the board size doesn't match
     */
    public void loadFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Read board size
            String sizeStr = reader.readLine();
            if (sizeStr == null) {
                throw new IllegalArgumentException("Invalid file format: missing board size");
            }

            int fileSize;
            try {
                fileSize = Integer.parseInt(sizeStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid file format: board size must be a number");
            }

            if (fileSize != gameState.getBoardSize()) {
                throw new IllegalArgumentException("Board size in file (" + fileSize +
                        ") doesn't match current board size (" +
                        gameState.getBoardSize() + ")");
            }

            // Read board state
            Cell[][] newCells = new Cell[fileSize][fileSize];
            for (int i = 0; i < fileSize; i++) {
                String line = reader.readLine();
                if (line == null || line.length() != fileSize) {
                    throw new IllegalArgumentException("Invalid file format: line " + (i + 2) +
                            " is missing or has wrong length");
                }

                for (int j = 0; j < fileSize; j++) {
                    char c = line.charAt(j);
                    if (c != '0' && c != '1') {
                        throw new IllegalArgumentException("Invalid file format: line " + (i + 2) +
                                " contains invalid character '" + c +
                                "' (expected '0' or '1')");
                    }

                    Cell cell = new Cell();
                    cell.setAlive(c == '1');
                    newCells[i][j] = cell;
                }
            }

            // Set the new board state
            gameState.setCells(newCells);
        }
    }
}
