package event;

import model.robot.*;

import java.util.NoSuchElementException;

import model.map.Incendie;

public class EvenementChangementEtat extends Evenement {
    
    private Robot robot;
    private EtatRobot newEtat;

    public EvenementChangementEtat(Robot robot, Incendie incendie, EtatRobot newEtat, long date){
        super(date);
        this.newEtat = newEtat;
        this.robot = robot;

    }

    @Override
    public void execute() throws NoSuchElementException{
        robot.setEtatCourant(newEtat);
    }
}
