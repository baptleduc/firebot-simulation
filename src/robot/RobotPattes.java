package robot;
import map.*;

public class RobotPattes extends RobotTerrestre {

    // Constantes
    private static final int CAPACITE_RESERVOIR = Integer.MAX_VALUE;
    private static final double VITESSE_DEFAUT = 30;
    private static final double VITESSE_MAX = VITESSE_DEFAUT;
    private static final int TEMPS_REMPLISSAGE = 0;
    private static final int INTER_UNITAIRE = 10;

    public RobotPattes(Case position, Carte carte){
        checkPosition(position, carte);
        int niveauEau = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
        super(position, niveauEau, CAPACITE_RESERVOIR, VITESSE_DEFAUT, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    public double getVitesse(NatureTerrain terrain){
        if (terrain == NatureTerrain.ROCHE){
            return Math.min(this.vitesse,10);
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
        if(terrain == NatureTerrain.EAU){
            throw new IllegalArgumentException("RobotPattes ne peut pas se rendre sur une case eau !");
        }

    }

    public void setPosition(Case newPosition){
        checkPosition(newPosition, this.carte);
        this.position = newPosition;
    }
}
