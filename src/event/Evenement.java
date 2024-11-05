package event;

public abstract class Evenement implements Comparable<Evenement>{
    private long date;

    public Evenement(long date){
        this.date = date;
    }

    public long getDate(){
        return this.date;
    }

    @Override
    public int compareTo(Evenement e){
        return Long.compare(this.date, e.date);
    }
    public abstract void execute();
}
