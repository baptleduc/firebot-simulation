package model.robot;

import model.map.*;
import java.util.NoSuchElementException;

/**
 * Classe responsable de la création d'instances de différents types de robots. (Factory Pattern)
 * La classe fournit une méthode statique pour obtenir un robot en fonction du type spécifié,
 * de la case courante, de la carte et de la vitesse.
 */
public class RobotFactory {

    public static Robot getRobot(String typeRobot, Case caseCourante, Carte carte, double vitesseLue){

        Robot newRobot;
        switch (typeRobot) {
                case "DRONE":
                    newRobot = createDrone(caseCourante, carte, vitesseLue);
                    break;
                    
                case "ROUES":
                    newRobot = createRobotRoues(caseCourante, carte, vitesseLue);
                    break;
                    
                case "PATTES":
                    assert (vitesseLue == -1);
                    newRobot = createRobotPattes(caseCourante, carte);
                    break; // Ajoutez break ici
                    
                case "CHENILLES":
                    newRobot = createRobotChenilles(caseCourante, carte, vitesseLue);
                    break;
                    
                default:
                    throw new NoSuchElementException("Type de robot non reconnu."); // Ajoutez des parenthèses pour le message
            }
        return newRobot;
    }

    /**
     * Crée un drone avec les paramètres spécifiés.
     *
     * @param caseCourante la case sur laquelle le drone doit être positionné.
     * @param carte        la carte dans laquelle le drone évolue.
     * @param vitesseLue   la vitesse initiale du drone (peut être -1 pour utiliser la vitesse par défaut).
     * @return une instance de {@link Drone}.
     */
    private static Drone createDrone(Case caseCourante, Carte carte, double vitesseLue){
        Drone.checkPosition(caseCourante, carte);
        double vitesse = Drone.VITESSE_DEFAUT;
        if (vitesseLue != -1 ){
            vitesse = vitesseLue;
        }

        return new Drone(caseCourante, carte, vitesse);
    }
    
    /**
     * Crée un robot roues avec les paramètres spécifiés.
     *
     * @param caseCourante la case sur laquelle le robot sur roues doit être positionné.
     * @param carte        la carte dans laquelle le robot sur roues évolue.
     * @param vitesseLue   la vitesse initiale du robot sur roues (peut être -1 pour utiliser la vitesse par défaut).
     * @return une instance de {@link RobotRoues}.
     */
    private static RobotRoues createRobotRoues(Case caseCourante, Carte carte, double vitesseLue){
        RobotRoues.checkPosition(caseCourante, carte);
        double vitesse = RobotRoues.VITESSE_DEFAUT;
        if (vitesseLue != -1){
            vitesse = vitesseLue;
        }

        return new RobotRoues(caseCourante, carte, vitesse);
    }

    /**
     * Crée un robot à pattes avec les paramètres spécifiés.
     *
     * @param caseCourante la case sur laquelle le robot à pattes doit être positionné.
     * @param carte        la carte dans laquelle le robot à pattes évolue.
     * @return une instance de {@link RobotPattes}.
     */
    private static RobotPattes createRobotPattes(Case caseCourante, Carte carte){
        RobotPattes.checkPosition(caseCourante, carte);
        return new RobotPattes(caseCourante, carte);
    }

    /**
     * Crée un robot à chenilles avec les paramètres spécifiés.
     *
     * @param caseCourante la case sur laquelle le robot à chenilles doit être positionné.
     * @param carte        la carte dans laquelle le robot à chenilles évolue.
     * @param vitesseLue   la vitesse initiale du robot à chenilles (peut être -1 pour utiliser la vitesse par défaut).
     * @return une instance de {@link RobotChenilles}.
     */
    private static RobotChenilles createRobotChenilles(Case caseCourante, Carte carte, double vitesseLue){
        RobotChenilles.checkPosition(caseCourante, carte);
        double vitesse = RobotChenilles.VITESSE_DEFAUT;
        if (vitesseLue != -1){
            vitesse = vitesseLue;
        }
        return new RobotChenilles(caseCourante, carte, vitesse);
    }
}
