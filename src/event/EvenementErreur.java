package event;


public class EvenementErreur extends Evenement {
    
    private String msgErreur;

    public EvenementErreur(String msgErreur, long date){
        super(date);
        this.msgErreur = msgErreur;

    }

    @Override
    public Evenement clone(){
        return new EvenementErreur(msgErreur, super.getDate());
    }
    

    @Override
    public void execute(){
        System.err.println(this.msgErreur);
    }
}
