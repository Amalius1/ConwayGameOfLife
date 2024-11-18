package pl.aml.bk;

import pl.aml.bk.display.Presenter;

public class ConsolePresenter implements Presenter {


    private static final String DEAD_CELL = " ";
    private static final String ALIVE_CELL = "+";

    @Override
    public void display(GameState gameState) {
        System.out.println("Generation number: " + gameState.getGeneration());
        Cell[][] cells = gameState.getCells();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                System.out.print(cell.isAlive() ? ALIVE_CELL : DEAD_CELL);
            }
            System.out.println();
        }
        System.out.println("_".repeat(10));
    }
}
