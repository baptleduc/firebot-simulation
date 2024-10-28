package robot;

import map.Carte;
import map.Case;
import map.NatureTerrain;

public class Drone extends RobotAerien {
    // Constantes
    private static final int CAPACITE_RESERVOIR = 10000;
    private static final double  VITESSE_DEFAUT = 100;
    private static final double VITESSE_MAX = 150;
    private static final int TEMPS_REMPLISSAGE = 30;
    private static int INTER_UNITAIRE = CAPACITE_RESERVOIR / 30;

    public Drone (Case position, Carte carte, double vitesseLue){
        checkPosition(position, carte);
        double vitesse = VITESSE_DEFAUT;
        if (vitesseLue != 0 ){
            vitesse = vitesseLue;
        }
        int niveauEau = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
        super(position, niveauEau, CAPACITE_RESERVOIR, vitesse, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    private static void checkPosition(Case position, Carte carte){
        if (!(carte.caseExiste(position) && carte.estVoisin(position, position))) {
            throw new IllegalArgumentException(
                String.format("La case : %s n'est pas voisine ou n'existe pas.", position));
        }

    }

    public void setPosition(Case newPosition){
        checkPosition(newPosition, this.carte);
        this.position = newPosition;
    }

    public double getVitesse(NatureTerrain terrain){
        return this.vitesse;
    }
}
