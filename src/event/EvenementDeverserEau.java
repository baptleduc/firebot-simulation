package event;

import model.robot.*;

import java.util.NoSuchElementException;

import model.map.Incendie;

public class EvenementDeverserEau extends Evenement {
    
    private Robot robot;
    private Incendie incendie;
    private int quantiteEauDeversee;

    public EvenementDeverserEau(Robot robot, Incendie incendie, int quantiteEauDeversee, long date){
        super(date);
        this.robot = robot;
        this.quantiteEauDeversee = quantiteEauDeversee;
        this.incendie = incendie;

    }

    @Override
    public Evenement clone(){
        return new EvenementDeverserEau(robot, incendie, quantiteEauDeversee, super.getDate());
    }

    @Override
    public void execute() throws NoSuchElementException{
        // assert(robot.getEtatCourant() == EtatRobot.EN_DEVERSAGE);
        robot.deverserEau(this.quantiteEauDeversee, this.incendie);
        robot.setEtatCourant(EtatRobot.DISPONIBLE); // Le robot re-devient disponible à la fin de son intervention
    }
}
