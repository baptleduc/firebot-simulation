package event;

import model.robot.*;

import java.util.NoSuchElementException;

public class EvenementChangementEtat extends Evenement {

    private Robot robot;
    private EtatRobot etat;

    /**
     * Constructeur de la classe EvenementChangementEtat.
     * 
     * @param robot   le robot associé
     * @param newEtat le nouvel état du robot
     * @param date    la date de l'événement
     */
    public EvenementChangementEtat(Robot robot, EtatRobot newEtat, long date) {
        super(date);
        this.etat = newEtat;
        this.robot = robot;

    }

    @Override
    public Evenement clone() {
        return new EvenementChangementEtat(robot, etat, super.getDate());
    }

    @Override
    public void execute() throws NoSuchElementException {
        robot.setEtatCourant(etat);
    }
}
