package pl.aml.bk.conwayui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * UI component for configuring the Conway Game of Life board.
 */
public class ConfigurationPane extends VBox {

    private final Slider boardSizeSlider;
    private final TextField boardSizeField;
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
        boardSizeSlider = new Slider(5, 100, 10);
        boardSizeSlider.setShowTickMarks(true);
        boardSizeSlider.setShowTickLabels(true);
        boardSizeSlider.setMajorTickUnit(10);
        boardSizeSlider.setMinorTickCount(9);
        boardSizeSlider.setSnapToTicks(true);

        // Create text field for direct input of board size
        boardSizeField = new TextField("10");
        boardSizeField.setPrefWidth(50);

        // Update text field when slider changes
        boardSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int value = newVal.intValue();
            boardSizeField.setText(String.valueOf(value));
        });

        // Update slider when text field changes (with validation)
        boardSizeField.textProperty().addListener((obs, oldVal, newVal) -> {
            try {
                if (!newVal.isEmpty()) {
                    int value = Integer.parseInt(newVal);
                    // Ensure value is within slider range
                    if (value >= 5 && value <= 100) {
                        boardSizeSlider.setValue(value);
                    }
                }
            } catch (NumberFormatException e) {
                // Restore previous valid value if input is not a number
                boardSizeField.setText(oldVal);
            }
        });

        boardSizeBox.getChildren().addAll(boardSizeTextLabel, boardSizeSlider, boardSizeField);

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
