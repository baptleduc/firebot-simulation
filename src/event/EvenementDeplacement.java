package event;

import model.robot.*;
import model.map.Case;

public class EvenementDeplacement extends Evenement {
    
    private Robot robot;
    private Case destCase;

    public EvenementDeplacement(Robot robot, Case destCase, long date){
        super(date);
        this.robot = robot;
        this.destCase = destCase;

    }
    public void execute(){
        robot.setPosition(destCase);
    }
}
