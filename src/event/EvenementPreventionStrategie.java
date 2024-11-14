package event;

import model.robot.Robot;
import strategie.FinInterventionAction;

public class EvenementPreventionStrategie extends Evenement {

    private Robot robot;
    private FinInterventionAction action;

    public EvenementPreventionStrategie(Robot robot, long date, FinInterventionAction action){
        super(date);
        this.robot = robot;
        this.action = action;
    }

    @Override
    public void execute(){
        this.action.finIntervention(robot);
    }

}
