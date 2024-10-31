package model.robot;

import model.map.Carte;
import model.map.Case;
import model.map.NatureTerrain;

public class Drone extends RobotAerien {
    // Constantes
    private static final int CAPACITE_RESERVOIR = 10000;
    private static final int NIVEAU_EAU = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
    public static final double VITESSE_DEFAUT = 100;
    private static final double VITESSE_MAX = 150;
    private static final int TEMPS_REMPLISSAGE = 30;
    private static int INTER_UNITAIRE = CAPACITE_RESERVOIR / 30;

    public Drone(Case position, Carte carte, double vitesse) {
        super(position, NIVEAU_EAU, CAPACITE_RESERVOIR, vitesse, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    /**
     * Vérifie si la référence de la position fournie existe dans la carte.
     *
     * @param position la case à vérifier.
     * @param carte    la carte de référence.
     * @throws IllegalArgumentException si la case n'existe pas dans la carte.
     */
    public static void checkPosition(Case position, Carte carte) throws IllegalArgumentException {
        if (!(carte.caseExiste(position))) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'existe pas sur la carte.", position));
        }

    }

    public void setPosition(Case newPosition) {
        checkPosition(newPosition, this.carte);
        if (!carte.estVoisin(this.position, newPosition)) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'est pas voisine de la position actuelle : %s", newPosition,
                            this.position));
        }
        this.position = newPosition;
    }

    public double getVitesse(NatureTerrain terrain) {
        return this.vitesse;
    }
}
