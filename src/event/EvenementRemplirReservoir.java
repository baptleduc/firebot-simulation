package event;

import model.robot.*;

/**
 * Représente un événement où un robot remplit son réservoir.
 * Cet événement met à jour l'état du robot et effectue l'opération de remplissage à une date spécifiée.
 */
public class EvenementRemplirReservoir extends Evenement {

    private Robot robot;
    private long tempsRemplissage;

    /**
     * Constructeur de la classe EvenementRemplirReservoir.
     * 
     * @param robot            le robot associé
     * @param tempsRemplissage le temps de remplissage
     * @param date             la date de l'événement
     */
    public EvenementRemplirReservoir(Robot robot, long tempsRemplissage, long date) {
        super(date);
        this.robot = robot;
        this.tempsRemplissage = tempsRemplissage;

    }

    @Override
    public Evenement clone() {
        return new EvenementRemplirReservoir(robot, tempsRemplissage, super.getDate());
    }

    @Override
    public void execute() {
        if (tempsRemplissage > 0) {
            assert (robot.getEtatCourant() == EtatRobot.EN_REMPLISSAGE);
        }

        this.robot.remplirReservoir();
        robot.setEtatCourant(EtatRobot.EN_REMPLISSAGE); // Le robot re-devient disponible à la fin de son remplissage
    }
}
