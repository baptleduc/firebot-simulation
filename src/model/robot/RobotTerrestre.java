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
     * Constructeur pour la classe RobotTerrestre.
     * 
     * @param position La position initiale du robot sur la carte.
     * @param niveauEau Le niveau initial d'eau du robot en litres.
     * @param capaciteReservoir La capacité maximale du réservoir en litres.
     * @param vitesse La vitesse initiale du robot en km/min.
     * @param vitesseMax La vitesse maximale du robot en km/min.
     * @param tempsRemplissage Le temps de remplissage du réservoir en minutes.
     * @param carte La carte sur laquelle le robot se déplace.
     * @param interUnitaire La quantité d'eau déversée par minute en litres.
     */
    public RobotTerrestre(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax,
            int tempsRemplissage, Carte carte, int interUnitaire) {
        super(position, niveauEau, capaciteReservoir, vitesse, vitesseMax, tempsRemplissage, carte, interUnitaire);

    }

    @Override
    public void remplirReservoir() throws NoSuchElementException{
        Case posCourante = getPosition();
        for (Direction direction : Direction.values()){
            Case voisin = carte.getVoisin(posCourante, direction);
            if (voisin.getNature() == NatureTerrain.EAU){
                System.out.println(String.format("La case voisine : %s est une case eau", voisin));
                System.out.println(String.format("Remplissage du robot pendant : %d min", this.getTempsRemplissage()));

                this.setNiveauEau(this.getCapaciteReservoir());
                return;
            }
        }
        throw new NoSuchElementException("Pas de case eau voisine");
    }

    @Override
    public Case obtenirCaseRemplissageAssocié(Case pointEau, PlusCourtChemin algo, Carte carte) throws IllegalArgumentException {
        List<Case> destinationsPossibles = new ArrayList<>();
        for (Direction direction : Direction.values()){
            Case voisin = carte.getVoisin(pointEau, direction);
            if (voisin.getNature() == NatureTerrain.EAU){
                continue;
            }
            destinationsPossibles.add(voisin);
        }
        for (Case destination : destinationsPossibles){
            List<Case> chemin = algo.creeChemin(this, this.position, destination);
            if(chemin != null){
                return destination;
            }
        }
        throw new IllegalArgumentException("Le robot ne peut pas atteindre de point de remplissage associé à ce point d'eau.");
    }

}
