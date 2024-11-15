package event;

import model.robot.Robot;
import strategie.FinInterventionAction;

/**
 * Représente un événement de prévention de stratégie, qui exécute une action de fin d'intervention pour un robot.
 * Cet événement est déclenché à une date spécifiée et permet de finaliser l'intervention du robot.
 */
public class EvenementPreventionStrategie extends Evenement {

    private Robot robot;
    private FinInterventionAction action;

    /**
     * Constructeur de l'événement de prévention de stratégie.
     *
     * @param robot  le robot sur lequel l'action de fin d'intervention sera effectuée.
     * @param date   la date d'exécution de l'événement.
     * @param action l'action de fin d'intervention à exécuter.
     */
    public EvenementPreventionStrategie(Robot robot, long date, FinInterventionAction action){
        super(date);
        this.robot = robot;
        this.action = action;
    }
    
    /**
     * Crée une copie de cet événement de prévention de stratégie.
     *
     * @return une nouvelle instance d'EvenementPreventionStrategie avec les mêmes paramètres.
     */
    @Override
    public Evenement clone(){
        return new EvenementPreventionStrategie(robot, super.getDate(), action);
    }

    /**
     * Exécute l'action de fin d'intervention sur le robot.
     * Cette méthode appelle la méthode `finIntervention()` de l'action associée.
     */
    @Override
    public void execute(){
        this.action.finIntervention(robot);
    }

}
