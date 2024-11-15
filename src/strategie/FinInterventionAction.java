package strategie;

import model.robot.Robot;

@FunctionalInterface
public interface FinInterventionAction {
    void finIntervention(Robot robot);
}
