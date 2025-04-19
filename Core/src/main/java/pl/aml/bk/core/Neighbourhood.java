package pl.aml.bk.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Neighbourhood {

    @Getter
    private List<Cell> neighbours;
    private final Cell currentCell;

    public void construct(Cell[][] cells, int x, int y) {
        neighbours = new ArrayList<>(8);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i >= 0 && x + i < cells.length && y + j >= 0 && y + j < cells[0].length) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    neighbours.add(cells[x + i][y + j]);
                }
            }
        }
    }

    public boolean calculateIfSurvives() {
        long countOfLiveNeighbours = neighbours.stream().filter(Cell::isAlive).count();
        return (currentCell.isAlive() && (countOfLiveNeighbours == 2 || countOfLiveNeighbours == 3))
                || !currentCell.isAlive() && countOfLiveNeighbours == 3;
    }

}
