package event;

import model.robot.*;

import java.util.NoSuchElementException;

/**
 * Représente un événement de changement d'état pour un robot dans la simulation.
 * Cet événement modifie l'état courant d'un robot à une date spécifiée.
 */
public class EvenementChangementEtat extends Evenement {
    
    private Robot robot;
    private EtatRobot etat;

    /**
     * Constructeur de l'événement de changement d'état.
     *
     * @param robot   le robot dont l'état doit être modifié.
     * @param newEtat le nouvel état à appliquer au robot.
     * @param date    la date de l'exécution de l'événement.
     */
    public EvenementChangementEtat(Robot robot, EtatRobot newEtat, long date){
        super(date);
        this.etat= newEtat;
        this.robot = robot;

    }

    /**
     * Crée une copie de cet événement.
     *
     * @return une nouvelle instance d'EvenementChangementEtat avec les mêmes paramètres.
     */
    @Override
    public Evenement clone(){
        return new EvenementChangementEtat(robot, etat, super.getDate());
    }

    /**
     * Exécute l'événement en modifiant l'état courant du robot.
     *
     * @throws NoSuchElementException si le robot ou l'état est invalide.
     */
    @Override
    public void execute() throws NoSuchElementException{
        robot.setEtatCourant(etat);
    }
}
