package event;

import model.robot.*;
import model.map.Case;

public class EvenementDeplacement extends Evenement {
    
    private Robot robot;
    private Case destCase;
    private long tempsDeplacement;

    public EvenementDeplacement(Robot robot, Case destCase, long tempsDeplacement, long date){
        super(date);
        this.robot = robot;
        this.destCase = destCase;
        this.tempsDeplacement = tempsDeplacement;

    }

    @Override
    public Evenement clone(){
        return new EvenementDeplacement(robot, destCase, tempsDeplacement, super.getDate());
    }

    @Override
    public void execute(){
        if (tempsDeplacement > 0){
            assert(robot.getEtatCourant() == EtatRobot.EN_DEPLACEMENT);
        }
        
        System.out.println(destCase);
        robot.setPosition(destCase);
        robot.setEtatCourant(EtatRobot.DISPONIBLE); // Le robot re-devient disponible à la fin de son déplacement
    }
}
