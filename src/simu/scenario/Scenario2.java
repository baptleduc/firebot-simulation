package simu.scenario;

import chemin.PlusCourtCheminAstar;
import model.DonneesSimulation;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario2 implements Scenario {
    @Override
    public void createEvenements(Simulateur simulateur, DonneesSimulation model){
            Robot robot = model.getRobots().get(2); 

            PlusCourtCheminAstar algo = new PlusCourtCheminAstar(model.getCarte());
            
            robot.deplacementPlusCourtChemin(simulateur, model.getCarte().getCase(7, 0),algo);
    }
}
