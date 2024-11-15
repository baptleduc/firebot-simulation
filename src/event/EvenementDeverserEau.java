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
     * Constructeur de l'événement de déversement d'eau.
     *
     * @param robot            le robot qui effectue le déversement.
     * @param incendie         l'incendie cible de l'intervention.
     * @param quantiteEauDeversee la quantité d'eau que le robot va déverser.
     * @param tempsIntervention le temps nécessaire pour déverser l'eau.
     * @param date             la date d'exécution de l'événement.
     */
    public EvenementDeverserEau(Robot robot, Incendie incendie, int quantiteEauDeversee, long tempsIntervention, long date){
        super(date);
        this.robot = robot;
        this.quantiteEauDeversee = quantiteEauDeversee;
        this.incendie = incendie;
        this.tempsIntervention = tempsIntervention;

    }

    /**
     * Crée une copie de cet événement.
     *
     * @return une nouvelle instance d'EvenementDeverserEau avec les mêmes paramètres.
     */
    @Override
    public Evenement clone(){
        return new EvenementDeverserEau(robot, incendie, quantiteEauDeversee, tempsIntervention, super.getDate());
    }

     /**
     * Exécute l'événement en déversant l'eau sur l'incendie et en mettant à jour l'état du robot.
     * @throws NoSuchElementException si l'incendie ou le robot est invalide.
     */
    @Override
    public void execute() throws NoSuchElementException{
        if (tempsIntervention > 0){
            assert(robot.getEtatCourant() == EtatRobot.EN_DEVERSAGE);
        }
        robot.deverserEau(this.quantiteEauDeversee, this.incendie);
        robot.setEtatCourant(EtatRobot.DISPONIBLE); // Le robot re-devient disponible à la fin de son intervention
    }
}
