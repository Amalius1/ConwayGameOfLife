package pl.aml.bk;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

class GameTest implements WithAssertions {


    @Test
    void rockObjectSurvives() {
        Game game = new Game(9, new ConsolePresenter());

        Cell[][] rockObject = ConwayObjectUtil.getRockObject();
        game.setBoardManually(rockObject);
        game.nextGeneration();
        game.nextGeneration();

        Cell[][] generation1 = game.getGameState().getHistory().get(1);
        assertThat(generation1[3][3].isAlive()).isTrue();
        assertThat(generation1[4][3].isAlive()).isTrue();
        assertThat(generation1[3][4].isAlive()).isTrue();
        assertThat(generation1[4][4].isAlive()).isTrue();
    }


    @Test
    void flipperObjectSurvives() {
        Game game = new Game(9, new ConsolePresenter());

        Cell[][] flipperObject = ConwayObjectUtil.getFlipperObject();
        game.setBoardManually(flipperObject);
        game.nextGeneration();
        game.nextGeneration();
        game.nextGeneration();

        Cell[][] generation1 = game.getGameState().getHistory().get(1);
        assertThat(generation1[3][3].isAlive()).isTrue();
        assertThat(generation1[3][4].isAlive()).isTrue();
        assertThat(generation1[3][5].isAlive()).isTrue();
    }


}
