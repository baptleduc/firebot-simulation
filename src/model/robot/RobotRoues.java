package model.robot;

import model.map.*;

public class RobotRoues extends RobotTerrestre {
    // Constantes
    private static final int CAPACITE_RESERVOIR = 5000;
    private static final int NIVEAU_EAU = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
    public static final double VITESSE_DEFAUT = 80;
    private static final double VITESSE_MAX = Double.MAX_VALUE;
    private static final int TEMPS_REMPLISSAGE = 5;
    private static final int INTER_UNITAIRE = 6000 / 5; //Intervention unitaire : 6000/5 litres en 1 min

    public RobotRoues(Case position, Carte carte, double vitesse) {
        super(position, NIVEAU_EAU, CAPACITE_RESERVOIR, vitesse, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    @Override
    public double getVitesse(NatureTerrain terrain) {
        return this.vitesse;
    }
    @Override
    public String getImagePath()
    {
        return "./images/robot/robot_roues.png";
    }

    /**
     * Vérifie si une position donnée est valide sur la carte avant la création d'un robot.
     * Cette méthode est statique, car elle est appelée une factory method, dans un contexte où une instance de 
     * RobotRoues n'existe pas encore, pour la validation initiale de la position.
     * @param position la case à vérifier.
     * @param carte    la carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas ou si elle est de
     *                                  type eau ou roche.
     */
    private static void checkPositionStatic(Case position, Carte carte) {
        if (!(carte.caseExiste(position))) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'existe pas sur la carte.", position));
        }

        NatureTerrain terrain = position.getNature();
        if (terrain != NatureTerrain.TERRAIN_LIBRE && terrain != NatureTerrain.HABITAT) {
            throw new IllegalArgumentException(
                    "Le Robot à roues ne peut aller que sur du terrain libre ou de l'habitat !");
        }
    }


    /**
     * Implémentation de la méthode abstraite `checkPosition` définie dans la classe `Robot`.
     * Cette méthode vérifie si une position est valide pour un RobotRoues en appelant 
     * `checkPositionStatic`, qui gère la logique de validation spécifique.
     *
     * @param position La case à vérifier.
     * @param carte    La carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas ou est de type EAU ou ROCHE.
     */
    @Override
    public void checkPosition(Case position, Carte carte){
        RobotRoues.checkPositionStatic(position, carte);
    }


    /**
     * FACTORY METHOD
     * Crée un robot roues avec les paramètres spécifiés.
     *
     * @param caseCourante la case sur laquelle le robot sur roues doit être
     *                     positionné.
     * @param carte        la carte dans laquelle le robot sur roues évolue.
     * @param vitesseLue   la vitesse initiale du robot sur roues (peut être -1 pour
     *                     utiliser la vitesse par défaut).
     * @return une instance de {@link RobotRoues}.
     */
    public static RobotRoues createRobotRoues(Case caseCourante, Carte carte, double vitesseLue) {
        RobotRoues.checkPositionStatic(caseCourante, carte);
        double vitesse = RobotRoues.VITESSE_DEFAUT;
        if (vitesseLue != -1) {
            vitesse = vitesseLue;
        }

        return new RobotRoues(caseCourante, carte, vitesse);
    }
}
