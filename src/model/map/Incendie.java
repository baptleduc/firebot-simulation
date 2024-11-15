package model.map;

import java.awt.Color;

public class Incendie {
    private Case position;
    private int quantiteEau;
    private int quantiteInitiale;

    private Color drawColor;

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

    public void eteindre(int quantiteEau){
        this.quantiteEau -= quantiteEau;
    }

    public Incendie clone() {
        Incendie clone = new Incendie(this.position, this.quantiteEau);
        return clone;
    }

    public void reset(){
        this.quantiteEau = quantiteInitiale;
    }

    @Override
    public String toString() {
        String output = String.format("Incendie(%d, %d)", this.position.getLigne(), this.position.getColonne());
        return output;
    }

}
