package event;

import model.robot.*;

import java.util.NoSuchElementException;

import model.map.Incendie;

public class EvenementDeverserEau extends Evenement {
    
    private Robot robot;
    private Incendie incendie;
    private int quantiteEauDeversee;
    private long tempsIntervention;

    public EvenementDeverserEau(Robot robot, Incendie incendie, int quantiteEauDeversee, long tempsIntervention, long date){
        super(date);
        this.robot = robot;
        this.quantiteEauDeversee = quantiteEauDeversee;
        this.incendie = incendie;
        this.tempsIntervention = tempsIntervention;

    }

    @Override
    public Evenement clone(){
        return new EvenementDeverserEau(robot, incendie, quantiteEauDeversee, tempsIntervention, super.getDate());
    }

    @Override
    public void execute() throws NoSuchElementException{
        if (tempsIntervention > 0){
            assert(robot.getEtatCourant() == EtatRobot.EN_DEVERSAGE);
        }
        robot.deverserEau(this.quantiteEauDeversee, this.incendie);
        robot.setEtatCourant(EtatRobot.DISPONIBLE); // Le robot re-devient disponible à la fin de son intervention
    }
}
