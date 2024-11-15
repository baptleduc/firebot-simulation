package event;

/**
 * Représente un événement d'erreur, qui affiche un message d'erreur à une date spécifiée.
 * Cet événement permet de signaler une erreur dans la simulation ou l'exécution du programme.
 */
public class EvenementErreur extends Evenement {
    
    private String msgErreur; // Le message d'erreur à afficher.

    /**
     * Constructeur de l'événement d'erreur.
     *
     * @param msgErreur le message d'erreur à afficher.
     * @param date      la date d'exécution de l'événement.
     */
    public EvenementErreur(String msgErreur, long date){
        super(date);
        this.msgErreur = msgErreur;

    }

    /**
     * Crée une copie de cet événement d'erreur.
     *
     * @return une nouvelle instance d'EvenementErreur avec le même message et la même date.
     */
    @Override
    public Evenement clone(){
        return new EvenementErreur(msgErreur, super.getDate());
    }
    
    /**
     * Exécute l'événement en affichant le message d'erreur sur la sortie d'erreur standard.
     */
    @Override
    public void execute(){
        System.err.println(this.msgErreur);
    }
}
