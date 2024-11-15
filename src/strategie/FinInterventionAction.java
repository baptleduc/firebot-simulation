package strategie;

import model.robot.Robot;

@FunctionalInterface
public interface FinInterventionAction {
    /**
     * Méthode qui permet de définir l'action à effectuer lorsqu'un robot a fini son intervention.
     * @param robot
     */
    void finIntervention(Robot robot);
}
