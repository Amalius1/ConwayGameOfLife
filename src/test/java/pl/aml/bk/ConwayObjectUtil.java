package pl.aml.bk;


import lombok.experimental.UtilityClass;

@UtilityClass
public class ConwayObjectUtil {

    public Cell[][] getRockObject() {
        int size = 9;
        Cell[][] cells = constructBoard(size);

        cells[3][3].setAlive(true);
        cells[3][4].setAlive(true);
        cells[4][3].setAlive(true);
        cells[4][4].setAlive(true);

        return cells;
    }

    public Cell[][] getFlipperObject() {
        int size = 9;
        Cell[][] cells = constructBoard(size);

        cells[3][3].setAlive(true);
        cells[3][4].setAlive(true);
        cells[3][5].setAlive(true);

        return cells;
    }

    private static Cell[][] constructBoard(int size) {
        Cell[][] cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
        return cells;
    }

}
