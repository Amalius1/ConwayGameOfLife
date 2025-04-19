package pl.aml.bk.conwayui.components;

import javafx.scene.layout.GridPane;
import pl.aml.bk.core.Cell;
import pl.aml.bk.core.GameState;
import pl.aml.bk.core.display.Presenter;

/**
 * JavaFX implementation of the Presenter interface.
 * Displays the game state in a JavaFX GridPane.
 */
public class JavaFXPresenter implements Presenter {

    private final GridPane gridPane;
    private GameState gameState;

    public JavaFXPresenter(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    @Override
    public void display(GameState gameState) {
        this.gameState = gameState;
        gridPane.getChildren().clear();

        Cell[][] cells = gameState.getCells();
        int boardSize = gameState.getBoardSize();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                final int row = i;
                final int col = j;
                CellPane cellPane = new CellPane(cells[i][j].isAlive());

                // Update the game state when the cell is clicked
                cellPane.setOnMouseClicked(event -> {
                    cellPane.toggle();
                    if (gameState != null && gameState.getCells() != null) {
                        gameState.getCells()[row][col].setAlive(cellPane.getState());
                    }
                });

                gridPane.add(cellPane, i, j);
            }
        }
    }

    /**
     * Updates the UI to reflect the current game state.
     * This method can be called when the game state changes outside the UI.
     */
    public void updateUI() {
        if (gameState != null) {
            display(gameState);
        }
    }
}
