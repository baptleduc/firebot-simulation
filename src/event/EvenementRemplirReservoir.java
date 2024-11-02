package event;

import model.robot.*;

public class EvenementRemplirReservoir extends Evenement {
    
    private Robot robot;

    public EvenementRemplirReservoir(Robot robot, long date){
        super(date);
        this.robot = robot;

    }
    public void execute(){
        this.robot.remplirReservoir();
    }
}
