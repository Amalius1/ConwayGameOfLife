package pl.aml.bk;

public class Main {
    public static void main(String[] args) {
        int size = 100;
        int generations = 10;
        Game game = new Game(size, new ConsolePresenter());
        game.randomizeBoard();
        for (int i = 0; i <= generations; i++) {
            game.printBoard();
            game.nextGeneration();
        }
    }
}