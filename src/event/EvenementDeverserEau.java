package event;

import model.robot.*;
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
    public void execute(){
        robot.deverserEau(this.quantiteEauDeversee, this.incendie);
    }
}
