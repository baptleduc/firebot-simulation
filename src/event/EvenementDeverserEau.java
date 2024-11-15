package event;

import model.robot.*;

import java.util.NoSuchElementException;

import model.map.Incendie;
/**
 * Représente un événement où un robot intervient pour déverser de l'eau sur un incendie.
 * Cet événement modifie l'état du robot et la quantité d'eau déversée sur l'incendie.
 */
public class EvenementDeverserEau extends Evenement {

    private Robot robot;
    private Incendie incendie;
    private int quantiteEauDeversee;
    private long tempsIntervention;

    /**
     * Constructeur de la classe EvenementDeverserEau.
     * 
     * @param robot               le robot associé
     * @param incendie            l'incendie associé
     * @param quantiteEauDeversee la quantité d'eau déversée
     * @param tempsIntervention   le temps d'intervention
     * @param date                la date de l'événement
     */
    public EvenementDeverserEau(Robot robot, Incendie incendie, int quantiteEauDeversee, long tempsIntervention,
            long date) {
        super(date);
        this.robot = robot;
        this.quantiteEauDeversee = quantiteEauDeversee;
        this.incendie = incendie;
        this.tempsIntervention = tempsIntervention;

    }

    @Override
    public Evenement clone() {
        return new EvenementDeverserEau(robot, incendie, quantiteEauDeversee, tempsIntervention, super.getDate());
    }

    @Override
    public void execute() throws NoSuchElementException {
        if (tempsIntervention > 0) {
            assert (robot.getEtatCourant() == EtatRobot.EN_DEVERSAGE);
        }
        robot.deverserEau(this.quantiteEauDeversee, this.incendie);
        robot.setEtatCourant(EtatRobot.DISPONIBLE); // Le robot re-devient disponible à la fin de son intervention
    }
}
