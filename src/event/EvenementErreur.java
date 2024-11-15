package event;

/**
 * Représente un événement d'erreur, qui affiche un message d'erreur à une date spécifiée.
 * Cet événement permet de signaler une erreur dans la simulation ou l'exécution du programme.
 */
public class EvenementErreur extends Evenement {

    private String msgErreur;

    /**
     * Constructeur de la classe EvenementErreur.
     * 
     * @param msgErreur le message d'erreur
     * @param date      la date de l'événement
     */
    public EvenementErreur(String msgErreur, long date) {
        super(date);
        this.msgErreur = msgErreur;

    }

    @Override
    public Evenement clone() {
        return new EvenementErreur(msgErreur, super.getDate());
    }

    @Override
    public void execute() {
        System.err.println(this.msgErreur);
    }
}
