package simu.scenario;

import chemin.PlusCourtCheminAstar;
import model.DonneesSimulation;
import model.robot.Robot;
import simu.Simulateur;

/**
 * Scénario 2 : Déplacer le robot vers la case (7, 0) en utilisant l'algorithme A*.
 */
public class Scenario2 implements Scenario {

    private Simulateur simulateur;
    private DonneesSimulation model;

    public Scenario2(Simulateur simulateur, DonneesSimulation model){
        this.simulateur = simulateur;
        this.model = model;
    }

    public void createEvenements(){
            Robot robot = model.getRobots().get(2); 

            PlusCourtCheminAstar algo = new PlusCourtCheminAstar(model.getCarte());
            
            robot.deplacementPlusCourtChemin(simulateur, model.getCarte().getCase(7, 0),algo);
    }
}
