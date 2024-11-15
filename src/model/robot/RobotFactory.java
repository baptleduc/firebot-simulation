package model.robot;

import model.map.*;
import java.util.NoSuchElementException;

/**
 * Classe responsable de la création d'instances de différents types de robots.
 * (Factory Pattern)
 */
public class RobotFactory {

    /**
     * Permet d'obtenir une instance du type de robot souhaité.
     * 
     * @param typeRobot    le type de robot à créer
     * @param caseCourante la case sur laquelle le robot doit être positionné
     * @param carte        la carte dans laquelle le robot évolue
     * @param vitesseLue   la vitesse du robot
     * @return
     */
    public static Robot getRobot(String typeRobot, Case caseCourante, Carte carte, double vitesseLue) {

        Robot newRobot;
        switch (typeRobot) {
            case "DRONE":
                newRobot = Drone.createDrone(caseCourante, carte, vitesseLue);
                break;

            case "ROUES":
                newRobot = RobotRoues.createRobotRoues(caseCourante, carte, vitesseLue);
                break;

            case "PATTES":
                assert (vitesseLue == -1);
                newRobot = RobotPattes.createRobotPattes(caseCourante, carte);
                break; // Ajoutez break ici

            case "CHENILLES":
                newRobot = RobotChenilles.createRobotChenilles(caseCourante, carte, vitesseLue);
                break;

            default:
                throw new NoSuchElementException("Type de robot non reconnu."); // Ajoutez des parenthèses pour le
                                                                                // message
        }
        return newRobot;
    }

}
