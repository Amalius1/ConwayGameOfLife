package pl.aml.bk.conwayui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * UI component for configuring the Conway Game of Life board.
 */
public class ConfigurationPane extends VBox {

    private final Slider boardSizeSlider;
    private final Label boardSizeLabel;
    private final Button startButton;
    private final Button stopButton;
    private final Button stepButton;
    private final Button resetButton;
    private final Button randomizeButton;
    private final Button saveButton;
    private final Button loadButton;

    public ConfigurationPane() {
        setPadding(new Insets(10));
        setSpacing(10);

        // Board size configuration
        Label titleLabel = new Label("Conway's Game of Life");
        titleLabel.setFont(new Font(18));

        HBox boardSizeBox = new HBox(10);
        Label boardSizeTextLabel = new Label("Board Size:");
        boardSizeSlider = new Slider(5, 30, 10);
        boardSizeSlider.setShowTickMarks(true);
        boardSizeSlider.setShowTickLabels(true);
        boardSizeSlider.setMajorTickUnit(5);
        boardSizeSlider.setMinorTickCount(4);
        boardSizeSlider.setSnapToTicks(true);
        boardSizeLabel = new Label("10");
        boardSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int value = newVal.intValue();
            boardSizeLabel.setText(String.valueOf(value));
        });
        boardSizeBox.getChildren().addAll(boardSizeTextLabel, boardSizeSlider, boardSizeLabel);

        // Game control buttons
        HBox controlBox = new HBox(10);
        startButton = new Button("Start");
        stopButton = new Button("Stop");
        stepButton = new Button("Step");
        resetButton = new Button("Reset");
        randomizeButton = new Button("Randomize");
        saveButton = new Button("Save");
        loadButton = new Button("Load");

        stopButton.setDisable(true);

        controlBox.getChildren().addAll(startButton, stopButton, stepButton, resetButton, randomizeButton, saveButton, loadButton);

        getChildren().addAll(titleLabel, boardSizeBox, controlBox);
    }

    public int getBoardSize() {
        return (int) boardSizeSlider.getValue();
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public Button getStepButton() {
        return stepButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public Button getRandomizeButton() {
        return randomizeButton;
    }

    public Slider getBoardSizeSlider() {
        return boardSizeSlider;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }
}
