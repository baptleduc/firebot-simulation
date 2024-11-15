package simu.scenario;

import chemin.PlusCourtCheminAstar;
import model.DonneesSimulation;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario3 implements Scenario {

    @Override
    public void createEvenements(Simulateur simulateur, DonneesSimulation model) {
        Robot robot = model.getRobots().get(0); // Récupération du robot à chenilles

        // Création de l'algorithme de plus court chemin
        PlusCourtCheminAstar algo = new PlusCourtCheminAstar(model.getCarte());

        // Déplacement du robot vers le coin opposé
        robot.deplacementPlusCourtChemin(simulateur, model.getCarte().getCase(4, 7), algo);
    }
}
