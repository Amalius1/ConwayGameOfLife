# Conway's Game of Life

A Java implementation of Conway's Game of Life with a JavaFX graphical user interface.

## Overview

Conway's Game of Life is a cellular automaton devised by mathematician John Conway in 1970. It's a zero-player game, meaning its evolution is determined by its initial state, with no further input from humans. The game takes place on a grid of cells, each of which can be either alive or dead. The state of each cell in the next generation is determined by a set of rules based on the number of live neighbors it has.

## Rules

1. Any live cell with fewer than two live neighbors dies (underpopulation)
2. Any live cell with two or three live neighbors lives on to the next generation
3. Any live cell with more than three live neighbors dies (overpopulation)
4. Any dead cell with exactly three live neighbors becomes a live cell (reproduction)

## Features

- Interactive grid display with adjustable size
- Controls to start, stop, and step through generations
- Randomize the board to create new patterns
- Reset the board to start over
- Save and load board states to/from files
- Generation counter to track the current generation

## Project Structure

The project is organized into two main modules:

### Core Module

Contains the core game logic:
- `Cell.java`: Represents a single cell in the game
- `Game.java`: Main game class that manages the game state and rules
- `GameState.java`: Manages the state of the game board and tracks generations
- `Neighbourhood.java`: Implements the rules for determining cell survival
- `Presenter.java`: Interface for displaying the game state

### ConwayUI Module

Contains the JavaFX user interface:
- `ConwayUIApplication.java`: Main application class with the UI layout and controls
- `CellPane.java`: Custom UI component for displaying a cell
- `ConfigurationPane.java`: UI component for game controls
- `JavaFXPresenter.java`: Implementation of the Presenter interface for JavaFX

## Requirements

- Java 21 or higher
- JavaFX 21 or higher
- Maven wrapper is included (no need to install Maven)

## Building and Running

To build the project using the Maven wrapper:

```bash
# On Windows
.\mvnw clean install

# On Linux/Mac
./mvnw clean install
```

To run the application:

```bash
# On Windows
.\mvnw -pl ConwayUI javafx:run

# On Linux/Mac
./mvnw -pl ConwayUI javafx:run
```

Alternatively, you can run the JAR file directly after building:

```bash
java -jar ConwayUI/target/ConwayUI-1.0-SNAPSHOT.jar
```

## Usage

1. Adjust the board size using the slider
2. Click "Randomize" to create a random initial state, or manually set up the board by clicking on cells
3. Click "Start" to begin the simulation
4. Use "Stop" to pause the simulation
5. Use "Step" to advance one generation at a time
6. Use "Reset" to clear the board and start over
7. Use "Save" and "Load" to save and load board states

## License

This project is open source and available under the MIT License.
