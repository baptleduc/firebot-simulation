package event;


public class EvenementErreur extends Evenement {
    
    private String msgErreur;

    public EvenementErreur(String msgErreur, long date){
        super(date);
        this.msgErreur = msgErreur;

    }
    public void execute(){
        System.err.println(this.msgErreur);
    }
}
