package model.robot;

import model.map.*;

public class RobotPattes extends RobotTerrestre {

    // Constantes
    private static final int CAPACITE_RESERVOIR = Integer.MAX_VALUE;
    private static final int NIVEAU_EAU = CAPACITE_RESERVOIR; // Hypothèse : un robot est créé avec son reservoir rempli
    public static final double VITESSE_DEFAUT = 30;
    private static final double VITESSE_MAX = VITESSE_DEFAUT;
    private static final int TEMPS_REMPLISSAGE = 0;
    private static final int INTER_UNITAIRE = 10;

    public RobotPattes(Case position, Carte carte) {
        super(position, NIVEAU_EAU, CAPACITE_RESERVOIR, VITESSE_DEFAUT, VITESSE_MAX, TEMPS_REMPLISSAGE, carte,
                INTER_UNITAIRE);
    }

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
    void deverserEau(int vol) {
        System.out.println("Le robot à pattes utilise de la poudre pour éteindre l'incendie.");
    }

    /**
     * Vérifie si la position donnée est valide sur la carte.
     *
     * @param position la case à vérifier.
     * @param carte    la carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas ou si elle est de
     *                                  type eau ou roche.
     */
    public static void checkPosition(Case position, Carte carte) throws IllegalArgumentException {
        if (!(carte.caseExiste(position))) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'existe pas sur la carte.", position));
        }
        NatureTerrain terrain = position.getNature();
        if (terrain == NatureTerrain.EAU) {
            throw new IllegalArgumentException("RobotPattes ne peut pas se rendre sur une case eau !");
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
        RobotPattes.checkPosition(caseCourante, carte);
        return new RobotPattes(caseCourante, carte);
    }
}
