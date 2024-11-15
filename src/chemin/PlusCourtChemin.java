package chemin;

import model.robot.Robot;

import java.util.List;

import model.map.Case;

public interface PlusCourtChemin {

    /**
     * Calcule le temps de déplacement entre deux cases
     * 
     * @param robot       le robot associé
     * @param caseDepart  la case de départ
     * @param caseArrivee la case d'arrivée
     * @return
     */
    public abstract double tempsDeplacement(Robot robot, Case caseDepart, Case caseArrivee);

    public abstract List<Case> creeChemin(Robot robot, Case caseDepart, Case caseArrivee);
}
