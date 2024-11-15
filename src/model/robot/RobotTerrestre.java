package model.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import chemin.PlusCourtChemin;
import model.map.*;

/**
 * Classe RobotTerrestre, classe abstraite heritant de Robot.
 */
public abstract class RobotTerrestre extends Robot {

    /**
     * Constructeur de la classe RobotTerrestre
     * 
     * @param position          la position initiale du robot
     * @param niveauEau         le niveau d'eau initial du robot
     * @param capaciteReservoir la capacité du réservoir du robot
     * @param vitesse           la vitesse du robot
     * @param vitesseMax        la vitesse maximale du robot
     * @param tempsRemplissage  le temps de remplissage du réservoir
     * @param carte             la carte sur laquelle se déplace le robot
     * @param interUnitaire     le nombre de litres que le robot peut deverser en 1
     */
    public RobotTerrestre(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax,
            int tempsRemplissage, Carte carte, int interUnitaire) {
        super(position, niveauEau, capaciteReservoir, vitesse, vitesseMax, tempsRemplissage, carte, interUnitaire);

    }

    @Override
    public void remplirReservoir() throws NoSuchElementException {
        Case posCourante = getPosition();
        for (Direction direction : Direction.values()) {
            Case voisin = carte.getVoisin(posCourante, direction);
            if (voisin.getNature() == NatureTerrain.EAU) {
                System.out.println(String.format("La case voisine : %s est une case eau", voisin));
                System.out.println(String.format("Remplissage du robot pendant : %d min", this.getTempsRemplissage()));

                this.setNiveauEau(this.getCapaciteReservoir());
                return;
            }
        }
        throw new NoSuchElementException("Pas de case eau voisine");
    }

    @Override
    public Case obtenirCaseRemplissageAssocié(Case pointEau, PlusCourtChemin algo, Carte carte)
            throws IllegalArgumentException {
        List<Case> destinationsPossibles = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Case voisin;
            try {
                voisin = carte.getVoisin(pointEau, direction);
            } catch (Exception e) {
                continue;
            }
            if (voisin.getNature() == NatureTerrain.EAU) {
                continue;
            }
            destinationsPossibles.add(voisin);
        }
        double tempsMin = Double.MAX_VALUE;
        List<Case> cheminMin = null;
        for (Case destination : destinationsPossibles) {
            List<Case> chemin = algo.creeChemin(this, this.position, destination);
            if (chemin != null) {
                double temps = algo.tempsDeplacement(this, this.position, destination);
                if (temps < tempsMin) {
                    tempsMin = temps;
                    cheminMin = chemin;
                }
            }
        }
        if (cheminMin != null) {
            return cheminMin.get(cheminMin.size() - 1);
        }
        throw new IllegalArgumentException(
                "Le robot ne peut pas atteindre de point de remplissage associé à ce point d'eau.");
    }

}
