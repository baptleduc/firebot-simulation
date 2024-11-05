package event;

import model.robot.*;

import java.util.NoSuchElementException;


public class EvenementChangementEtat extends Evenement {
    
    private Robot robot;
    private EtatRobot etat;

    public EvenementChangementEtat(Robot robot, EtatRobot newEtat, long date){
        super(date);
        this.etat= newEtat;
        this.robot = robot;

    }

    @Override
    public void execute() throws NoSuchElementException{
        robot.setEtatCourant(etat);
    }
}
