package simu.scenario;

import model.DonneesSimulation;
import model.map.Direction;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario0 implements Scenario {

    private Simulateur simulateur;
    private DonneesSimulation model;

    public Scenario0(Simulateur simulateur, DonneesSimulation model){
        this.simulateur = simulateur;
        this.model = model;
    }
    public void createEvenements(){
            Robot robot = model.getRobots().get(0); // Choisi le premier robot du model : 'Drone'
            for (long i = 0; i < 4; i++){
                robot.deplacerDirection(simulateur, Direction.NORD);
            }
    }
}
