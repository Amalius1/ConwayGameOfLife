package pl.aml.bk.conwayui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.aml.bk.conwayui.components.ConfigurationPane;
import pl.aml.bk.conwayui.components.JavaFXPresenter;
import pl.aml.bk.core.Game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.clamp;

public class ConwayUIApplication extends Application {

    private Game game;
    private JavaFXPresenter presenter;
    private GridPane gridPane;
    private Label generationLabel;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Create configuration pane
        var configPane = new ConfigurationPane();
        root.setTop(configPane);

        // Create grid pane for the game board
        gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        root.setCenter(gridPane);

        // Create generation counter
        HBox statusBar = new HBox(10);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPadding(new Insets(10));
        generationLabel = new Label("Generation: 0");
        generationLabel.setFont(new Font(14));
        statusBar.getChildren().add(generationLabel);
        root.setBottom(statusBar);

        // Initialize game with default size
        initializeGame(configPane.getBoardSize());

        // Set up animation timeline for game simulation
        var timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            game.nextGeneration();
            presenter.display(game.getGameState());
            updateGenerationLabel();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        // Set up button actions
        configPane.getStartButton().setOnAction(e -> {
            timeline.play();
            configPane.getStartButton().setDisable(true);
            configPane.getStopButton().setDisable(false);
            configPane.getBoardSizeSlider().setDisable(true);
        });

        configPane.getStopButton().setOnAction(e -> {
            timeline.stop();
            configPane.getStartButton().setDisable(false);
            configPane.getStopButton().setDisable(true);
            configPane.getBoardSizeSlider().setDisable(false);
        });

        configPane.getStepButton().setOnAction(e -> {
            game.nextGeneration();
            presenter.display(game.getGameState());
            updateGenerationLabel();
        });

        configPane.getResetButton().setOnAction(e -> {
            timeline.stop();
            configPane.getStartButton().setDisable(false);
            configPane.getStopButton().setDisable(true);
            configPane.getBoardSizeSlider().setDisable(false);
            initializeGame(configPane.getBoardSize());
        });

        configPane.getRandomizeButton().setOnAction(e -> {
            game.randomizeBoard();
            presenter.display(game.getGameState());
        });

        configPane.getSaveButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Board State");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    game.saveToFile(file);
                    showAlert(AlertType.INFORMATION, "Save Successful",
                            "The board state was saved successfully to " + file.getName());
                } catch (IOException ex) {
                    showAlert(AlertType.ERROR, "Save Error",
                            "An error occurred while saving the board state: " + ex.getMessage());
                }
            }
        });

        configPane.getLoadButton().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Board State");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    // First read the board size from the file
                    int fileSize = readBoardSizeFromFile(file);

                    // If the board size is different, reinitialize the game with the new size
                    if (game.getGameState().getBoardSize() != fileSize) {
                        initializeGame(fileSize);
                        // Update the slider to match the new board size
                        configPane.getBoardSizeSlider().setValue(fileSize);
                    }

                    // Now load the file content
                    game.loadFromFile(file);
                    presenter.display(game.getGameState());
                    updateGenerationLabel();
                    showAlert(AlertType.INFORMATION, "Load Successful",
                            "The board state was loaded successfully from " + file.getName());
                } catch (IOException ex) {
                    showAlert(AlertType.ERROR, "Load Error",
                            "An error occurred while loading the board state: " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    showAlert(AlertType.ERROR, "Invalid File Format", ex.getMessage());
                }
            }
        });

        configPane.getBoardSizeSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!timeline.getStatus().equals(Animation.Status.RUNNING)) {
                initializeGame(newVal.intValue());
            }
        });

        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.setTitle("Conway's Game of Life");
        stage.setResizable(true);
        stage.show();
    }

    private void initializeGame(int size) {
        // Clear the grid
        gridPane.getChildren().clear();

        // Adjust cell size based on board size to fit the window
        // For larger boards (>50), use smaller cells to fit the screen
        double availableSpace = 600; // Default available space
        double cellSize;

        if (size > 50) {
            // For very large boards, scale down more aggressively
            cellSize = clamp(availableSpace / size, 4, 10);
        } else {
            cellSize = clamp(availableSpace / size, 4, 18);
        }

        // Update CellPane size before displaying
        pl.aml.bk.conwayui.components.CellPane.setCellSize(cellSize);

        // Create the presenter and game
        presenter = new JavaFXPresenter(gridPane);
        game = new Game(size, presenter);

        // Display the game state
        presenter.display(game.getGameState());

        // Reset generation label
        updateGenerationLabel();
    }

    /**
     * Updates the generation label with the current generation number.
     */
    private void updateGenerationLabel() {
        if (game != null && generationLabel != null) {
            generationLabel.setText("Generation: " + game.getGameState().getGeneration());
        }
    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param type    The type of alert (INFORMATION, WARNING, ERROR)
     * @param title   The title of the alert
     * @param message The message to display
     */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Reads the board size from the first line of a Conway's Game of Life save file.
     *
     * @param file The file to read from
     * @return The board size as an integer
     * @throws IOException              If an I/O error occurs
     * @throws IllegalArgumentException If the file format is invalid
     */
    private int readBoardSizeFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Read board size from the first line
            String sizeStr = reader.readLine();
            if (sizeStr == null) {
                throw new IllegalArgumentException("Invalid file format: missing board size");
            }

            try {
                return Integer.parseInt(sizeStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid file format: board size must be a number");
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
