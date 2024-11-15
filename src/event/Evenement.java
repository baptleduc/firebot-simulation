package event;

public abstract class Evenement implements Comparable<Evenement> {
    private long date;

    /**
     * Constructeur de la classe Evenement.
     * 
     * @param date la date de l'événement.
     */
    public Evenement(long date) {
        this.date = date;
    }

    public long getDate() {
        return this.date;
    }

    /**
     * Crée et retourne une copie de cet objet {@code Evenement}.
     *
     * @return une copie de cet {@code Evenement}.
     */
    public abstract Evenement clone();

    @Override
    public int compareTo(Evenement e) {
        return Long.compare(this.date, e.date);
    }

    /**
     * Exécute l'événement.
     */
    public abstract void execute();
}
