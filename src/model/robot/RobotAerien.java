package model.robot;

import chemin.PlusCourtChemin;
import model.map.*;

public abstract class RobotAerien extends Robot {

    /**
     * Constructeur de la classe RobotAerien
     * 
     * @param position          la position initiale du robot
     * @param niveauEau         le niveau d'eau initial du robot
     * @param capaciteReservoir la capacité du réservoir du robot
     * @param vitesse           la vitesse du robot
     * @param vitesseMax        la vitesse maximale du robot
     * @param tempsRemplissage  le temps de remplissage du réservoir du robot
     * @param carte             la carte associée
     * @param interUnitaire     le nombre de litres que le robot peut deverser en 1
     */
    public RobotAerien(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax,
            int tempsRemplissage, Carte carte, int interUnitaire) {
        super(position, niveauEau, capaciteReservoir, vitesse, vitesseMax, tempsRemplissage, carte, interUnitaire);

    }

    /**
     * Remplit le réservoir d'eau du robot aérien. Ce remplissage est possible
     * uniquement si
     * le robot se trouve sur une case de type {@link NatureTerrain#EAU}.
     */
    @Override
    public void remplirReservoir() {
        Case posCourante = getPosition();

        NatureTerrain terrain = posCourante.getNature();
        if (terrain == NatureTerrain.EAU) {
            this.setNiveauEau(this.getCapaciteReservoir());
        } else {
            System.out.println("Le robot aérien ne peut pas se remplir car il n'est pas sur une case d'eau !");
        }
    }

    @Override
    public Case obtenirCaseRemplissageAssocié(Case pointEau, PlusCourtChemin algo, Carte carte)
            throws IllegalArgumentException {
        return pointEau;
    }
}
