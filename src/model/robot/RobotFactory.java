package model.robot;

import model.map.*;
import java.util.NoSuchElementException;

/**
 * Classe responsable de la création d'instances de différents types de robots.
 * (Factory Pattern)
 * La classe fournit une méthode statique pour obtenir un robot en fonction du
 * type spécifié,
 * de la case courante, de la carte et de la vitesse.
 */
public class RobotFactory {

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
