package model.robot;
import model.map.*;

public class RobotRoues extends RobotTerrestre {
    // Constantes
    private static final int CAPACITE_RESERVOIR = 5000;
    private static final int NIVEAU_EAU = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
    public static final double VITESSE_DEFAUT = 80;
    private static final double VITESSE_MAX = Double.MAX_VALUE;
    private static final int TEMPS_REMPLISSAGE = 10;
    private static final int INTER_UNITAIRE = 100/5;


    public RobotRoues(Case position, Carte carte, double vitesse){
        super(position, NIVEAU_EAU, CAPACITE_RESERVOIR, vitesse, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    public double getVitesse(NatureTerrain terrain){
        return this.vitesse;
    }

    /**
     * Vérifie si la position donnée est valide sur la carte.
     *
     * @param position la case à vérifier.
     * @param carte    la carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas ou si elle est de type eau ou roche.
     */
    public static void checkPosition(Case position, Carte carte){
        if (!(carte.caseExiste(position))) {
            throw new IllegalArgumentException(
                String.format("La case : %s n'existe pas sur la carte.", position));
        }
    
        NatureTerrain terrain = position.getNature();
        if (terrain != NatureTerrain.TERRAIN_LIBRE && terrain != NatureTerrain.HABITAT) {
            throw new IllegalArgumentException("Le Robot à roues ne peut aller que sur du terrain libre ou de l'habitat !");
        }
    }

    public void setPosition(Case newPosition) {
        checkPosition(newPosition, this.carte);
        if (!carte.estVoisin(this.position, newPosition)){
            throw new IllegalArgumentException(
                String.format("La case : %s n'est pas voisine de la position actuelle : %s", newPosition, this.position));
        } 
        this.position = newPosition;
    }
}
