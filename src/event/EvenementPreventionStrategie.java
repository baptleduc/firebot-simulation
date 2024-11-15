package event;

import model.robot.Robot;
import strategie.FinInterventionAction;

public class EvenementPreventionStrategie extends Evenement {

    private Robot robot;
    private FinInterventionAction action;

    /**
     * Constructeur de la classe EvenementPreventionStrategie.
     * 
     * @param robot  le robot associé
     * @param date   la date de l'événement
     * @param action l'action de fin d'intervention
     */
    public EvenementPreventionStrategie(Robot robot, long date, FinInterventionAction action) {
        super(date);
        this.robot = robot;
        this.action = action;
    }

    @Override
    public Evenement clone() {
        return new EvenementPreventionStrategie(robot, super.getDate(), action);
    }

    @Override
    public void execute() {
        this.action.finIntervention(robot);
    }

}
