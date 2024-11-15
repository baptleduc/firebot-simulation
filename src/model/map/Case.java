package model.map;

import java.awt.Color;

/**
 * Classe Case définit par sa position (ligne, colonne) et sa nature.
 */
public class Case {
    private int ligne;
    private int colonne;
    private NatureTerrain nature;
    private Color drawColor;

    /**
     * Constructeur de la classe Case.
     * 
     * @param ligne         la ligne de la case.
     * @param colonne       la colonne de la case.
     * @param natureTerrain la nature de la case.
     */
    public Case(int ligne, int colonne, NatureTerrain natureTerrain) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = natureTerrain;
        this.drawColor = this.nature.getDrawColor();

    }

    public int getLigne() {
        return this.ligne;
    }

    public int getColonne() {
        return this.colonne;
    }

    public Color getDrawColor() {
        return this.drawColor;
    }

    public NatureTerrain getNature() {
        return this.nature;
    }

    @Override
    public String toString() {
        return String.format("Case (%d, %d) : nature %s\n", ligne, colonne, getNature().name());
    }
}
