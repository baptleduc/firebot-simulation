package model.robot;

import java.util.NoSuchElementException;

import model.map.*;

public abstract class RobotTerrestre extends Robot {

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

}
