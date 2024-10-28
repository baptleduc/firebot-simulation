package robot;
import map.*;

public class RobotRoues extends RobotTerrestre {
    // Constantes
    private static int CAPACITE_RESERVOIR = 5000;
    private static double VITESSE_DEFAUT = 80;
    private static double VITESSE_MAX = Double.MAX_VALUE;
    private static int TEMPS_REMPLISSAGE = 10;
    private static int INTER_UNITAIRE = 100/5;


    public RobotRoues(Case position, Carte carte, double vitesseLue){
        checkPosition(position, carte);
        double vitesse = VITESSE_DEFAUT;

        if (vitesseLue != 0){
            vitesse = vitesseLue;
        }
        int niveauEau = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
        super(position, niveauEau, CAPACITE_RESERVOIR, vitesse, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    public double getVitesse(NatureTerrain terrain){
        return this.vitesse;
    }

    private static void checkPosition(Case position, Carte carte){
        if (!(carte.caseExiste(position) && carte.estVoisin(position, position))) {
            throw new IllegalArgumentException(
                String.format("La case : %s n'est pas voisine ou n'existe pas.", position));
        }
    
        NatureTerrain terrain = position.getNature();
        if (terrain != NatureTerrain.TERRAIN_LIBRE && terrain != NatureTerrain.HABITAT) {
            throw new IllegalArgumentException("Le Robot à roues ne peut aller que sur du terrain libre ou de l'habitat !");
        }
    }

    public void setPosition(Case newPosition) {
        checkPosition(newPosition, this.carte);
        this.position = newPosition;
    }
}
