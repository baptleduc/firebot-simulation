package model.map;

import java.awt.Color;

/**
 * Représente un incendie situé à une position spécifique sur la carte,
 * avec une certaine quantité d'eau à éteindre.
 */
public class Incendie {
    private Case position;
    private int quantiteEau;
    private int quantiteInitiale;

    private Color drawColor;

    /**
     * Constructeur de la classe Incendie.
     * 
     * @param position    la position de l'incendie.
     * @param quantiteEau la quantité d'eau nécessaire pour éteindre l'incendie.
     */
    public Incendie(Case position, int quantiteEau) {
        this.position = position;
        this.quantiteEau = quantiteEau;
        this.quantiteInitiale = quantiteEau;
    }

    public Case getPosition() {
        return this.position;
    }

    public Color getDrawColor() {
        return this.drawColor;
    }

    public int getQuantiteEau() {
        return this.quantiteEau;
    }

    /**
     * Eteint l'incendie en diminuant la quantité d'eau nécessaire pour l'éteindre.
     * 
     * @param quantiteEau la quantité d'eau utilisée
     */
    public void eteindre(int quantiteEau) {
        this.quantiteEau -= quantiteEau;
    }

    /**
     * Clone l'incendie.
     */
    public Incendie clone() {
        Incendie clone = new Incendie(this.position, this.quantiteEau);
        return clone;
    }

    /**
     * Réinitialise la quantité d'eau nécessaire pour éteindre l'incendie.
     */
    public void reset() {
        this.quantiteEau = quantiteInitiale;
    }

    @Override
    public String toString() {
        String output = String.format("Incendie(%d, %d)", this.position.getLigne(), this.position.getColonne());
        return output;
    }

}
