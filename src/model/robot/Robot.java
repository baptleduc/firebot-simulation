package model.robot;

import java.awt.Color;
import model.map.Case;
import model.map.Incendie;
import model.map.NatureTerrain;
import model.map.Carte;

public abstract class Robot {

    private int niveauEau; // en litres
    private int capaciteReservoir; // en litres
    private int tempsRemplissage; // en minutes
    private int interUnitaire; // Nombre de litre qu'il peut deverser en 1 min
    private double vitesseMax;
    private final Color DRAW_COLOR = Color.MAGENTA;
    private Case position;
    private EtatRobot etatCourant;

    protected Carte carte;
    protected double vitesse; // en km/min
    

    public Robot(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax,
            int tempsRemplissage, Carte carte, int interUnitaire) {
        this.position = position;
        this.carte = carte;
        this.capaciteReservoir = capaciteReservoir;
        this.vitesseMax = vitesseMax;
        this.tempsRemplissage = tempsRemplissage;
        this.interUnitaire = interUnitaire;

        checkVitesse(vitesse, vitesseMax);
        this.vitesse = vitesse;

        checkNiveauEau(niveauEau, capaciteReservoir);
        this.niveauEau = niveauEau;

        this.etatCourant = EtatRobot.DISPONIBLE; // On suppose qu'à l'initialisation un robot est immédiatement disponible
    }

    public Case getPosition() {
        return this.position;
    }

    public int getNiveauEau() {
        return this.niveauEau;
    }

    public Color getDrawColor() {
        return this.DRAW_COLOR;
    }

    public int getCapaciteReservoir(){
        return this.capaciteReservoir;
    }

    public int getTempsRemplissage(){
        return this.tempsRemplissage;
    }

    public boolean estOccupe() {
        return this.etatCourant == EtatRobot.EN_REMPLISSAGE || this.etatCourant == EtatRobot.EN_DEVERSAGE || this.etatCourant == EtatRobot.EN_DEPLACEMENT;
    }

    public int getInterUnitaire(){
        return this.interUnitaire;
    }

    public EtatRobot getEtatCourant(){
        return this.etatCourant;
    }

    public void setEtatCourant(EtatRobot newEtat){
        this.etatCourant = newEtat;
    }
    

    private static void checkNiveauEau(int niveauEau, int capaciteReservoir) {
        if (niveauEau < 0) {
            throw new IllegalArgumentException("Le niveau d'eau ne peut pas être négatif.");
        }
        if (niveauEau > capaciteReservoir) {
            throw new IllegalArgumentException(String.format(
                    "Le niveau d'eau ne peut pas dépasser la capacité du réservoir de %d litres.", capaciteReservoir));
        }
    }

    public void setNiveauEau(int niveauEau) {
        checkNiveauEau(niveauEau, this.capaciteReservoir);
        this.niveauEau = niveauEau;
    }

    /**
     * Déverse une quantité d'eau du réservoir.
     *
     * @param vol la quantité d'eau à déverser en litres.
     * @throws IllegalArgumentException si la quantité demandée est supérieure au
     *                                  niveau actuel d'eau.
     */
    public void deverserEau(int quantite, Incendie incendie) {
        // Vérifiez que le robot est sur la même case que l'incendie
        if (!this.position.equals(incendie.getPosition())) {
            System.err.println(this.position);
            System.err.println(incendie.getPosition());
            throw new IllegalArgumentException("Le robot n'est pas sur la case de l'incendie.");
        }

        if (quantite <= this.niveauEau) {
            incendie.eteindre(quantite);
            this.niveauEau -= quantite; 
        } else {
            throw new IllegalArgumentException("Pas assez d'eau dans le réservoir pour déverser cette quantité.");
        }
    }

    private static void checkVitesse(double vitesse, double vitesseMax) {
        if (vitesse > vitesseMax) {
            throw new IllegalArgumentException(
                    String.format("La nouvelle vitesse doit être inférieure ou égale à la vitesse max qui est : %d",
                            vitesseMax));
        }
    }

    public void setVitesse(double newVitesse) {
        checkVitesse(newVitesse, this.vitesseMax);
        this.vitesse = newVitesse;
    }

    /**
     * Calcule le temps nécessaire (en min) pour se déplacer d'une case à une autre, en
     * fonction
     * des vitesses spécifiques des terrains de départ et d'arrivée.
     *
     * @param caseDepart  la case de départ du déplacement.
     * @param caseArrivee la case de destination du déplacement.
     * @return le temps de déplacement en heures.
     */
    public double calculerTempsDeplacementMinute(Case caseDepart, Case caseArrivee) {
        double vitesseDepart = getVitesse(caseDepart.getNature());
        double vitesseArrivee = getVitesse(caseArrivee.getNature());

        double vitesseMoyenne = (vitesseDepart + vitesseArrivee) / 2;
        int tailleCasesKiloMetres = this.carte.getTailleCases() / 1000;

        return tailleCasesKiloMetres * 60 / vitesseMoyenne;
    }

    public void setPosition(Case newPosition){
        checkPosition(newPosition, this.carte);
        if (!carte.estVoisin(this.position, newPosition)) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'est pas voisine de la position actuelle : %s", newPosition,
                            this.position));
        }
        this.position = newPosition;
    }

    public abstract double getVitesse(NatureTerrain terrain);

    protected abstract void checkPosition(Case position, Carte carte);

    public abstract void remplirReservoir();

    @Override
    public String toString() {
        String output = String.format("Robot(%d, %d)", this.position.getLigne(), this.position.getColonne());
        return output;
    }
}
