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
    private static int INTER_UNITAIRE = CAPACITE_RESERVOIR * 60 / 30; // Intervention unitaire : 6000000/30 litres en 1 min

    public Drone(Case position, Carte carte, double vitesse) {
        super(position, NIVEAU_EAU, CAPACITE_RESERVOIR, vitesse, VITESSE_MAX, TEMPS_REMPLISSAGE, carte, INTER_UNITAIRE);
    }

    /**
     * Vérifie si une position donnée est valide sur la carte avant la création d'un robot.
     * Cette méthode est statique, car elle est appelée une factory method, dans un contexte où une instance de 
     * Drone n'existe pas encore, pour la validation initiale de la position.
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

    }

    /**
     * Implémentation de la méthode abstraite `checkPosition` définie dans la classe `Robot`.
     * Cette méthode vérifie si une position est valide pour un Drone en appelant 
     * `checkPositionStatic`, qui gère la logique de validation spécifique.
     *
     * @param position La case à vérifier.
     * @param carte    La carte dans laquelle se trouve la case.
     * @throws IllegalArgumentException si la case n'existe pas ou est de type EAU ou ROCHE.
     */ 
    @Override
    public void checkPosition(Case position, Carte carte) throws IllegalArgumentException{
        Drone.checkPositionStatic(position, carte);
    }

    @Override
    public double getVitesse(NatureTerrain terrain) {
        return this.vitesse;
    }

    @Override
    public String getType()
    {
        return "Drone";
    }


    /**
     * FACTORY METHOD
     * Crée un drone avec les paramètres spécifiés.
     *
     * @param caseCourante la case sur laquelle le drone doit être positionné.
     * @param carte        la carte dans laquelle le drone évolue.
     * @param vitesseLue   la vitesse initiale du drone (peut être -1 pour utiliser
     *                     la vitesse par défaut).
     * @return une instance de {@link Drone}.
     */
    public static Drone createDrone(Case caseCourante, Carte carte, double vitesseLue) {
        Drone.checkPositionStatic(caseCourante, carte);
        double vitesse = Drone.VITESSE_DEFAUT;
        if (vitesseLue != -1) {
            vitesse = vitesseLue;
        }

        return new Drone(caseCourante, carte, vitesse);
    }
}
