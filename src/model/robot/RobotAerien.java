package model.robot;

import chemin.PlusCourtChemin;
import model.map.*;


/**
 * Classe RobotAerien, classe abstraite heritant de Robot.
 * 
 */
 
public abstract class RobotAerien extends Robot {

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
    public Case obtenirCaseRemplissageAssocié(Case pointEau, PlusCourtChemin algo, Carte carte) throws IllegalArgumentException {
        return pointEau;
    }
}
