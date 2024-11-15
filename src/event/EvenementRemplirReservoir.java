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
     * Constructeur de l'événement de remplissage du réservoir.
     *
     * @param robot           le robot effectuant le remplissage.
     * @param tempsRemplissage le temps nécessaire pour remplir le réservoir.
     * @param date            la date d'exécution de l'événement.
     */
    public EvenementRemplirReservoir(Robot robot, long tempsRemplissage, long date){
        super(date);
        this.robot = robot;
        this.tempsRemplissage = tempsRemplissage;

    }

    /**
     * Crée une copie de cet événement de remplissage du réservoir.
     *
     * @return une nouvelle instance d'EvenementRemplirReservoir avec les mêmes paramètres.
     */
    @Override
    public Evenement clone(){
        return new EvenementRemplirReservoir(robot, tempsRemplissage, super.getDate());
    }
    
    /**
     * Exécute l'événement en remplissant le réservoir du robot et en mettant à jour son état.
     * Si le temps de remplissage est supérieur à zéro, une vérification est effectuée pour s'assurer
     * que le robot est en état de remplissage.
     */
    @Override
    public void execute(){
        if (tempsRemplissage > 0){
            assert(robot.getEtatCourant() == EtatRobot.EN_REMPLISSAGE);  
        }
        
        this.robot.remplirReservoir();
        robot.setEtatCourant(EtatRobot.EN_REMPLISSAGE); // Le robot re-devient disponible à la fin de son remplissage
    }
}
