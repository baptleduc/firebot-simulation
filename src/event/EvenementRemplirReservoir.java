package event;

import model.robot.*;

public class EvenementRemplirReservoir extends Evenement {
    
    private Robot robot;
    private long tempsRemplissage;

    public EvenementRemplirReservoir(Robot robot, long tempsRemplissage, long date){
        super(date);
        this.robot = robot;
        this.tempsRemplissage = tempsRemplissage;

    }

    @Override
    public Evenement clone(){
        return new EvenementRemplirReservoir(robot, tempsRemplissage, super.getDate());
    }
    
    @Override
    public void execute(){
        if (tempsRemplissage > 0){
            assert(robot.getEtatCourant() == EtatRobot.EN_REMPLISSAGE);  
        }
        
        this.robot.remplirReservoir();
        robot.setEtatCourant(EtatRobot.EN_REMPLISSAGE); // Le robot re-devient disponible à la fin de son remplissage
    }
}
