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
     * @param position
     * @param quantiteEau
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

    public int getQuantiteEau(){
        return this.quantiteEau;
    }

    /**
     * Méthode qui permet d'éteindre un incendie en diminuant la quantité d'eau.
     * @param quantiteEau
     */
    public void eteindre(int quantiteEau){
        this.quantiteEau -= quantiteEau;
    }

    /**
     * Crée une copie profonde de l'incendie.
     *
     * @return Une nouvelle instance d'Incendie avec la même position et quantité d'eau.
     */
    public Incendie clone() {
        Incendie clone = new Incendie(this.position, this.quantiteEau);
        return clone;
    }

    /**
     * Méthode qui permet de réinitialiser la quantité d'eau à sa valeur initiale.
     * 
     */
    public void reset(){
        this.quantiteEau = quantiteInitiale;
    }

    @Override
    public String toString() {
        String output = String.format("Incendie(%d, %d)", this.position.getLigne(), this.position.getColonne());
        return output;
    }

}
