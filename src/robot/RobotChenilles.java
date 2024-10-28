package robot;
import map.*;

public class RobotChenilles extends RobotTerrestre {

    // Constantes
    private static final int CAPACITE_RESERVOIR = 2000;
    private static final double VITESSE_DEFAUT = 60;
    private static final double VITESSE_MAX = 80;
    private static final int TEMPS_REMPLISSAGE = 5;
    private static final int INTER_UNITAIRE = 100/8;

    public RobotChenilles(Case position,  Carte carte, double vitesseLue){

        checkPosition(position, carte);

        double vitesse = VITESSE_DEFAUT;
        if (vitesseLue != 0){
            vitesse = vitesseLue;
        }
        int niveauEau = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
        super(position, niveauEau, CAPACITE_RESERVOIR, vitesse, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    public double getVitesse(NatureTerrain terrain){
        if (terrain == NatureTerrain.FORET){
            return this.vitesse / 2;
        }
        return this.vitesse;
    }

    @Override
    void deverserEau(int vol){
        System.out.println("Le robot à pattes utilise de la poudre pour éteindre l'incendie.");
    }

    private static void checkPosition(Case position, Carte carte){
        if (!(carte.caseExiste(position) && carte.estVoisin(position, position))) {
            throw new IllegalArgumentException(
                String.format("La case : %s n'est pas voisine ou n'existe pas.", position));
        }
        NatureTerrain terrain = position.getNature();
        if(terrain == NatureTerrain.EAU || terrain == NatureTerrain.ROCHE){
            throw new IllegalArgumentException("RobotChenille ne peut pas se rendre sur une case eau !");
        }
        
    }

    public void setPosition(Case newPosition){
        checkPosition(newPosition, this.carte);
        this.position = newPosition;
    }
}