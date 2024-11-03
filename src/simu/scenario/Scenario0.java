package simu.scenario;

import model.DonneesSimulation;
import model.map.Direction;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario0 implements Scenario {

    public void createEvenements(Simulateur simulateur, DonneesSimulation model){
            Robot robot = model.getRobots().get(0); // Choisi le premier robot du model : 'Drone'
            for (long i = 0; i < 4; i++){
                robot.createEvenementsDeplacement(simulateur, Direction.NORD);
            }
    }
}
