package model.robot;

import model.map.*;

public class RobotPattes extends RobotTerrestre {

    // Constantes
    private static final int CAPACITE_RESERVOIR = Integer.MAX_VALUE;
    private static final int NIVEAU_EAU = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
    public static final double VITESSE_DEFAUT = 30;
    private static final double VITESSE_MAX = VITESSE_DEFAUT;
    private static final int TEMPS_REMPLISSAGE = 0;
    private static final int INTER_UNITAIRE = 600; // Intervention unitaire : 600 litres en 1 min

    public RobotPattes(Case position, Carte carte) {
        super(position, NIVEAU_EAU, CAPACITE_RESERVOIR, VITESSE_DEFAUT, VITESSE_MAX, TEMPS_REMPLISSAGE, carte,
                INTER_UNITAIRE);
    }

    @Override
    public double getVitesse(NatureTerrain terrain) {
        if (terrain == NatureTerrain.ROCHE) {
            return Math.min(this.vitesse, 10);
        }

        return this.vitesse;
    }

    /**
     * Redéfinit la méthode de déversement pour le robot à pattes.
     * 
     * Ce robot a une réserve d'eau infinie et utilise de la poudre pour éteindre
     * les incendies, plutôt que de déverser de l'eau.
     *
     */
    @Override
    public void deverserEau(int vol, Incendie incendie) {
        System.out.println("Le robot à pattes utilise de la poudre pour éteindre l'incendie.");
    }

    /**
     * Vérifie si une position donnée est valide sur la carte avant la création d'un robot.
     * Cette méthode est statique, car elle est appelée une factory method, dans un contexte où une instance de 
     * RobotPattes n'existe pas encore, pour la validation initiale de la position.
     *
     * @param position La case à vérifier.
     * @param carte    La carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas sur la carte ou est de 
     *                                  type EAU ou ROCHE, rendant la position invalide.
     */
    private static void checkPositionStatic(Case position, Carte carte) throws IllegalArgumentException {
        if (!(carte.caseExiste(position))) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'existe pas sur la carte.", position));
        }
        NatureTerrain terrain = position.getNature();
        if (terrain == NatureTerrain.EAU) {
            throw new IllegalArgumentException("RobotPattes ne peut pas se rendre sur une case eau !");
        }
    }

    /**
     * Implémentation de la méthode abstraite `checkPosition` définie dans la classe `Robot`.
     * Cette méthode vérifie si une position est valide pour un RobotPattes en appelant 
     * `checkPositionStatic`, qui gère la logique de validation spécifique.
     *
     * @param position La case à vérifier.
     * @param carte    La carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas ou est de type EAU ou ROCHE.
     */
    @Override
    public void checkPosition(Case position, Carte carte){
        RobotPattes.checkPositionStatic(position, carte);
    }

    @Override
    public String getImagePath()
    {
        return "./images/robot/robot_pattes.png";
    }

    /**
     * FACTORY METHOD
     * Crée un robot à pattes avec les paramètres spécifiés.
     *
     * @param caseCourante la case sur laquelle le robot à pattes doit être
     *                     positionné.
     * @param carte        la carte dans laquelle le robot à pattes évolue.
     * @return une instance de {@link RobotPattes}.
     */
    public static RobotPattes createRobotPattes(Case caseCourante, Carte carte) {
        RobotPattes.checkPositionStatic(caseCourante, carte);
        return new RobotPattes(caseCourante, carte);
    }
}
