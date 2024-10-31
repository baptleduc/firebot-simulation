package model.map;

import java.awt.Color;

public class Incendie {
    private Case position;
    private int quantiteEau;

    private Color drawColor;

    public Incendie(Case position, int quantiteEau) {
        this.position = position;
        this.quantiteEau = quantiteEau;
    }

    public Case getPosition() {
        return this.position;
    }

    public Color getDrawColor() {
        return this.drawColor;
    }

    @Override
    public String toString() {
        String output = String.format("Incendie(%d, %d)", this.position.getLigne(), this.position.getColonne());
        return output;
    }

}
