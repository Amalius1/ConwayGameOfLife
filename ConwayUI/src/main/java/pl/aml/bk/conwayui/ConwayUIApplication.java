package pl.aml.bk.conwayui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.aml.bk.conwayui.components.ConfigurationPane;
import pl.aml.bk.conwayui.components.JavaFXPresenter;
import pl.aml.bk.core.Game;

public class ConwayUIApplication extends Application {

    private Game game;
    private JavaFXPresenter presenter;
    private Timeline timeline;
    private GridPane gridPane;
    private ConfigurationPane configPane;
    private Label generationLabel;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Create configuration pane
        configPane = new ConfigurationPane();
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
        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
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

        configPane.getBoardSizeSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!timeline.getStatus().equals(Animation.Status.RUNNING)) {
                initializeGame(newVal.intValue());
            }
        });

        Scene scene = new Scene(root, 600, 700);
        stage.setScene(scene);
        stage.setTitle("Conway's Game of Life");
        stage.show();
    }

    private void initializeGame(int size) {
        // Clear the grid
        gridPane.getChildren().clear();

        // Adjust cell size based on board size to fit the window
        double cellSize = Math.min(500 / size, 18);

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

    public static void main(String[] args) {
        launch();
    }
}
