package simu.scenario;

import model.DonneesSimulation;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario2 implements Scenario {

    public void createEvenements(Simulateur simulateur, DonneesSimulation model){
            Robot robot = model.getRobots().get(2); 
            
            robot.deplacementPlusCourtChemin(simulateur, model.getCarte().getCase(7, 0));
    }
}
