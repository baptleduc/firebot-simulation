package event;

import model.robot.*;

public class EvenementRemplirReservoir extends Evenement {
    
    private Robot robot;

    public EvenementRemplirReservoir(Robot robot, long date){
        super(date);
        this.robot = robot;

    }
    @Override
    public void execute(){
        assert(robot.getEtatCourant() == EtatRobot.EN_REMPLISSAGE);
        this.robot.remplirReservoir();
    }
}
