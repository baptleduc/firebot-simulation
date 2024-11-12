package model.robot;

import model.map.*;

public class RobotChenilles extends RobotTerrestre {

    // Constantes
    private static final int CAPACITE_RESERVOIR = 2000;
    private static final int NIVEAU_EAU = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
    public static final double VITESSE_DEFAUT = 60;
    private static final double VITESSE_MAX = 80;
    private static final int TEMPS_REMPLISSAGE = 5;
    private static final int INTER_UNITAIRE = 6000 / 8; // Intervention unitaire : 6000/8 litres en 1min

    public RobotChenilles(Case position, Carte carte, double vitesse) {
        super(position, NIVEAU_EAU, CAPACITE_RESERVOIR, vitesse, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    @Override
    public double getVitesse(NatureTerrain terrain) {
        if (terrain == NatureTerrain.FORET) {
            return this.vitesse / 2;
        }
        return this.vitesse;
    }

    @Override
    public String getImagePath()
    {
        return "./images/robot/robot_chenille.png";
    }

    /**
     * Vérifie si une position donnée est valide sur la carte avant la création d'un robot.
     * Cette méthode est statique, car elle est appelée une factory method, dans un contexte où une instance de 
     * RobotChenilles n'existe pas encore, pour la validation initiale de la position.
     * @param position la case à vérifier.
     * @param carte    la carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas ou si elle est de
     *                                  type eau ou roche.
     */
    private static void checkPositionStatic(Case position, Carte carte) throws IllegalArgumentException {
        if (!(carte.caseExiste(position))) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'existe pas sur la carte.", position));
        }
        NatureTerrain terrain = position.getNature();
        if (terrain == NatureTerrain.EAU || terrain == NatureTerrain.ROCHE) {
            throw new IllegalArgumentException("RobotChenille ne peut pas se rendre sur une case eau ou roche!");
        }

    }

    /**
     * Implémentation de la méthode abstraite `checkPosition` définie dans la classe `Robot`.
     * Cette méthode vérifie si une position est valide pour un RobotChenilles en appelant 
     * `checkPositionStatic`, qui gère la logique de validation spécifique.
     *
     * @param position La case à vérifier.
     * @param carte    La carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas ou est de type EAU ou ROCHE.
     */
    @Override
    public void checkPosition(Case position, Carte carte) throws IllegalArgumentException{
        RobotChenilles.checkPositionStatic(position, carte);
    }

    /**
     * FACTORY METHOD
     * Crée un robot à chenilles avec les paramètres spécifiés. 
     *
     * @param caseCourante la case sur laquelle le robot à chenilles doit être
     *                     positionné.
     * @param carte        la carte dans laquelle le robot à chenilles évolue.
     * @param vitesseLue   la vitesse initiale du robot à chenilles (peut être -1
     *                     pour utiliser la vitesse par défaut).
     * @return une instance de {@link RobotChenilles}.
     */
    public static RobotChenilles createRobotChenilles(Case caseCourante, Carte carte, double vitesseLue) {
        RobotChenilles.checkPositionStatic(caseCourante, carte);
        double vitesse = RobotChenilles.VITESSE_DEFAUT;
        if (vitesseLue != -1) {
            vitesse = vitesseLue;
        }
        return new RobotChenilles(caseCourante, carte, vitesse);
    }
}