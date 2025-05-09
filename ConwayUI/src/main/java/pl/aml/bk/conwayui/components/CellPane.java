package pl.aml.bk.conwayui.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Random;

public class CellPane extends Pane {

    private final SimpleBooleanProperty state = new SimpleBooleanProperty(false);
    private static double cellSize = 18d;
    private int age = 0;
    private static final Random random = new Random();
    private static final int AGE_THRESHOLD = 5;

    public CellPane(boolean state) {
        this(state, 0);
    }

    public CellPane(boolean state, int age) {
        this.setBorder(Border.stroke(Paint.valueOf("gray")));
        this.setPrefWidth(cellSize);
        this.setPrefHeight(cellSize);
        this.age = age;

        this.state.addListener((obs, oldVal, newVal) -> {
            updateCellColor();
        });

        setOnMouseClicked(event -> toggle());
        this.state.set(state);
        updateCellColor(); // Ensure color is set initially
    }

    private void updateCellColor() {
        if (!state.get()) {
            // Dead cell is always white
            setBackground(Background.fill(Paint.valueOf("white")));
        } else if (age > AGE_THRESHOLD) {
            // Cells older than threshold get a random color
            Color randomColor = Color.rgb(
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
            );
            setBackground(Background.fill(randomColor));
        } else {
            // Young living cells are black
            setBackground(Background.fill(Paint.valueOf("black")));
        }
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        updateCellColor();
    }
}
