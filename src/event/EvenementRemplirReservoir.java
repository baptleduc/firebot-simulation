package event;

import model.robot.*;

public class EvenementRemplirReservoir extends Evenement {
    
    private Robot robot;

    public EvenementRemplirReservoir(Robot robot, long date){
        super(date);
        this.robot = robot;

    }

    @Override
    public Evenement clone(){
        return new EvenementRemplirReservoir(robot, super.getDate());
    }
    
    @Override
    public void execute(){
        assert(robot.getEtatCourant() == EtatRobot.EN_REMPLISSAGE);
        this.robot.remplirReservoir();
        robot.setEtatCourant(EtatRobot.EN_REMPLISSAGE); // Le robot re-devient disponible à la fin de son remplissage
    }
}
