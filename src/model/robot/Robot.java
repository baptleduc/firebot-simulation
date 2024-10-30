package model.robot;

import java.awt.Color;
import model.map.Case;
import model.map.NatureTerrain;
import model.map.Carte;

public abstract class  Robot {
    
    private int niveauEau; // en litres
    private int capaciteReservoir; // en litres
    private int tempsRemplissage; // en minutes
    private int interUnitaire; // Nombre de litre qu'il peut deverser en 1 s
    private double vitesseMax;
    private final Color DRAW_COLOR = Color.MAGENTA;

    protected Carte carte;
    protected Case position;
    protected double vitesse; // en km/h


    public Robot(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax, int tempsRemplissage, Carte carte, int interUnitaire){
        this.position = position;
        this.carte = carte;
        this.capaciteReservoir = capaciteReservoir;
        this.vitesseMax = vitesseMax;
        this.tempsRemplissage = tempsRemplissage;


        checkVitesse(vitesse, vitesseMax);
        this.vitesse = vitesse;

        checkNiveauEau(niveauEau, capaciteReservoir);
        this.niveauEau = niveauEau;
    }

    public Case getPosition(){
        return this.position;
    }

    public int getNiveauEau(){
        return this.niveauEau;
    }

    public Color getDrawColor(){
        return this.DRAW_COLOR;
    }

    private static void checkNiveauEau(int niveauEau, int capaciteReservoir){
        if (niveauEau < 0) {
            throw new IllegalArgumentException("Le niveau d'eau ne peut pas être négatif.");
        }
        if (niveauEau > capaciteReservoir) {
            throw new IllegalArgumentException(String.format("Le niveau d'eau ne peut pas dépasser la capacité du réservoir de %d litres.", capaciteReservoir));
        }
    }   

    void setNiveauEau(int niveauEau) {
        checkNiveauEau(niveauEau, this.capaciteReservoir);
        this.niveauEau = niveauEau;
    }

    void deverserEau(int vol){
        if (vol < this.niveauEau){
           this.niveauEau -= vol; // Update reservoirEau 
        }
        else{
            throw new IllegalArgumentException("Le robot n'a pas assez d'eau dans son reservoir");
        }
    }

    private static void checkVitesse(double vitesse, double vitesseMax){
        if(vitesse > vitesseMax){
            throw new IllegalArgumentException(
                String.format("La nouvelle vitesse doit être inférieure ou égale à la vitesse max qui est : %d", vitesseMax)
            );
        }
    }

    void setVitesse(double newVitesse){
        checkVitesse(newVitesse, this.vitesseMax);
        this.vitesse = newVitesse;
    }

    public double calculerTempsDeplacement(Case caseDepart, Case caseArrivee) {
        double vitesseDepart = getVitesse(caseDepart.getNature());
        double vitesseArrivee = getVitesse(caseArrivee.getNature());

        double vitesseMoyenne = (vitesseDepart + vitesseArrivee) / 2;

        return this.carte.getTailleCases() / vitesseMoyenne;
    }

    public abstract void setPosition(Case newPosCase);
    public abstract double getVitesse(NatureTerrain terrain);
    
    public abstract void remplirReservoir();


    @Override
    public String toString(){
        String output = String.format("Robot(%d, %d)",this.position.getLigne(), this.position.getColonne());
        return output;
    }
}
