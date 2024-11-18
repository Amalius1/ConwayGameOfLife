package pl.aml.bk;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class NeighbourhoodTest implements WithAssertions {

    @Test
    void whenInTopLeftCorner_shouldReturnThreeNeighbours() {
        Cell[][] cells = getBoard(3);
        int x = 0;
        int y = 0;
        Neighbourhood neighbourhood = new Neighbourhood(cells[x][y]);
        neighbourhood.construct(cells, x, y);

        List<Cell> neighbours = neighbourhood.getNeighbours();

        assertThat(neighbours.size()).isEqualTo(3);

    }

    @Test
    void whenInTopRightCorner_shouldReturnThreeNeighbours() {
        Cell[][] cells = getBoard(3);
        int x = 2;
        int y = 0;
        Neighbourhood neighbourhood = new Neighbourhood(cells[x][y]);
        neighbourhood.construct(cells, x, y);

        List<Cell> neighbours = neighbourhood.getNeighbours();

        assertThat(neighbours.size()).isEqualTo(3);

    }

    @Test
    void whenInBottomRightCorner_shouldReturnThreeNeighbours() {
        Cell[][] cells = getBoard(3);
        int x = 2;
        int y = 2;
        Neighbourhood neighbourhood = new Neighbourhood(cells[x][y]);
        neighbourhood.construct(cells, x, y);

        List<Cell> neighbours = neighbourhood.getNeighbours();

        assertThat(neighbours.size()).isEqualTo(3);

    }

    @Test
    void whenInBottomLeftCorner_shouldReturnThreeNeighbours() {
        Cell[][] cells = getBoard(3);
        int x = 0;
        int y = 2;
        Neighbourhood neighbourhood = new Neighbourhood(cells[x][y]);
        neighbourhood.construct(cells, x, y);

        List<Cell> neighbours = neighbourhood.getNeighbours();

        assertThat(neighbours.size()).isEqualTo(3);

    }

    @Test
    void whenInCenter_shouldReturnEightNeighbours() {
        Cell[][] cells = getBoard(3);
        int x = 1;
        int y = 1;
        Neighbourhood neighbourhood = new Neighbourhood(cells[x][y]);
        neighbourhood.construct(cells, x, y);

        List<Cell> neighbours = neighbourhood.getNeighbours();

        assertThat(neighbours.size()).isEqualTo(8);

    }

    @Test
    void whenInLeftColumn_shouldReturnFiveNeighbours() {
        Cell[][] cells = getBoard(3);
        int x = 0;
        int y = 1;
        Neighbourhood neighbourhood = new Neighbourhood(cells[x][y]);
        neighbourhood.construct(cells, x, y);

        List<Cell> neighbours = neighbourhood.getNeighbours();

        assertThat(neighbours.size()).isEqualTo(5);

    }

    @Test
    void whenInTopRow_shouldReturnFiveNeighbours() {
        Cell[][] cells = getBoard(3);
        int x = 1;
        int y = 0;
        Neighbourhood neighbourhood = new Neighbourhood(cells[x][y]);
        neighbourhood.construct(cells, x, y);

        List<Cell> neighbours = neighbourhood.getNeighbours();

        assertThat(neighbours.size()).isEqualTo(5);

    }

    @Test
    void whenInBottomRow_shouldReturnFiveNeighbours() {
        Cell[][] cells = getBoard(3);
        int x = 1;
        int y = 2;
        Neighbourhood neighbourhood = new Neighbourhood(cells[x][y]);
        neighbourhood.construct(cells, x, y);

        List<Cell> neighbours = neighbourhood.getNeighbours();

        assertThat(neighbours.size()).isEqualTo(5);

    }


    private Cell[][] getBoard(int size) {
        Cell[][] cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell();
            }
        }
        return cells;
    }


}
