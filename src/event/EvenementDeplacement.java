package event;

import model.robot.*;
import model.map.Case;

/**
 * Représente un événement de déplacement d'un robot vers une case cible.
 * Cet événement met à jour la position du robot et son état à une date donnée.
 */
public class EvenementDeplacement extends Evenement {
    
    private Robot robot;
    private Case destCase;
    private long tempsDeplacement;

    /**
     * Constructeur de l'événement de déplacement.
     *
     * @param robot            le robot qui effectue le déplacement.
     * @param destCase         la case cible du déplacement.
     * @param tempsDeplacement le temps nécessaire pour accomplir le déplacement.
     * @param date             la date d'exécution de l'événement.
     */
    public EvenementDeplacement(Robot robot, Case destCase, long tempsDeplacement, long date){
        super(date);
        this.robot = robot;
        this.destCase = destCase;
        this.tempsDeplacement = tempsDeplacement;

    }

    /**
     * Crée une copie de cet événement.
     *
     * @return une nouvelle instance d'EvenementDeplacement avec les mêmes paramètres.
     */
    @Override
    public Evenement clone(){
        return new EvenementDeplacement(robot, destCase, tempsDeplacement, super.getDate());
    }
    
    /**
     * Exécute l'événement en déplaçant le robot vers la case cible et en changeant son état.
     */
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
