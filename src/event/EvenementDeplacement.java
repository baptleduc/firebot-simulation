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
     * Constructeur de la classe EvenementDeplacement.
     * 
     * @param robot            le robot associé
     * @param destCase         la case de destination
     * @param tempsDeplacement le temps de déplacement
     * @param date             la date de l'événement
     */
    public EvenementDeplacement(Robot robot, Case destCase, long tempsDeplacement, long date) {
        super(date);
        this.robot = robot;
        this.destCase = destCase;
        this.tempsDeplacement = tempsDeplacement;

    }

    @Override
    public Evenement clone() {
        return new EvenementDeplacement(robot, destCase, tempsDeplacement, super.getDate());
    }

    @Override
    public void execute() {
        if (tempsDeplacement > 0) {
            assert (robot.getEtatCourant() == EtatRobot.EN_DEPLACEMENT);
        }
        robot.setPosition(destCase);
        robot.setEtatCourant(EtatRobot.DISPONIBLE); // Le robot re-devient disponible à la fin de son déplacement
    }
}
