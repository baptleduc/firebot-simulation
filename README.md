# Firefighting Robot Simulation

This Java application simulates a team of autonomous firefighting robots operating in a natural environment. It allows robots to move, interact with fires, and coordinate interventions to extinguish fires as quickly as possible. This project was developed as part of my Object-Oriented Programming course in my second year at Ensimag in the Information Systems Engineering course.

## Prerequisites
- Java Development Kit (JDK) version 11 or later.

## Installation
1. Download and extract the project archive or clone the repository.
2. Ensure that the `gui.jar` file is located in the project directory.

## Available Commands

### Compilation
To compile the necessary classes for the simulation and tests, run:
```sh
make all
```

### Running Tests
#### GUI Interface Test
```sh
make exeInvader
```
Runs `TestInvader` to verify graphical display using `gui.jar`.

#### Data Reading Test
```sh
make exeLecture
```
Runs `TestLecteurDonnees` to load and display map data (`carteSujet.map`).

### Running the Simulation
#### Basic Simulation
```sh
make Simu
```
Runs the basic simulation with the `carteSujet.map` map.

#### Simulation with Specific Scenarios
- **Scenario 0**
  ```sh
  make SimuScenario0
  ```
- **Scenario 1**
  ```sh
  make SimuScenario1
  ```
- **Scenario 2 (Shortest Path Test)**
  ```sh
  make SimuScenario2
  ```

### Simulation with Strategies
- **Basic strategy on the default map:**
  ```sh
  make SimuStrategieElementaire
  ```
- **Advanced strategy on the default map:**
  ```sh
  make SimuStrategieEvoluee
  ```
- **Advanced strategy on different maps:**
  ```sh
  make SimuStrategieEvolueeDesert
  make SimuStrategieEvolueeMushroom
  make SimuStrategieEvolueeSpiral
  ```
  Replace `Evoluee` with `Elementaire` to switch to the basic strategy.

## Project Structure
- **Source (`src/`)**: Contains all Java source files organized by package.
- **Binary (`bin/`)**: Stores compiled `.class` files.
- **Graphical Interface (`lib/gui.jar`)**: Archive containing interface-related classes.
- **Maps (`cartes/`)**: Contains map files for the simulation.
- **Images (`images/`)**: Stores images used for simulation display.
- **Doc (`doc/`)**: Contains all Javadoc files and pUML diagrams.
- **Livrable (`livrable/`)**: Contains the documentation in French, including the deliverable we needed to submit.

### Cleaning Compiled Files
To remove all compiled files from the project directory:
```sh
make clean
```
Deletes all `.class` files from the `bin/` directory.

## License
Academic project - Ensimag.
