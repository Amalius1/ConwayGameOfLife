package pl.aml.bk.conwayui.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class CellPane extends Pane {

    private final SimpleBooleanProperty state = new SimpleBooleanProperty(false);
    private static double cellSize = 18d;

    public CellPane(boolean state) {
        this.setBorder(Border.stroke(Paint.valueOf("gray")));
        this.setPrefWidth(cellSize);
        this.setPrefHeight(cellSize);
        this.state.addListener((obs, oldVal, newVal) -> {
            Paint stateColor = Boolean.TRUE.equals(newVal) ? Paint.valueOf("black") : Paint.valueOf("white");
            setBackground(Background.fill(stateColor));
        });
        setOnMouseClicked(event -> toggle());
        this.state.set(state);
    }

    public void toggle() {
        this.state.setValue(!this.state.getValue());
    }

    public boolean getState() {
        return state.get();
    }

    public void setState(boolean alive) {
        this.state.set(alive);
    }

    public SimpleBooleanProperty stateProperty() {
        return state;
    }

    /**
     * Sets the size of all cell panes.
     *
     * @param size The size in pixels
     */
    public static void setCellSize(double size) {
        cellSize = size;
    }

    /**
     * Gets the current cell size.
     *
     * @return The cell size in pixels
     */
    public static double getCellSize() {
        return cellSize;
    }
}
