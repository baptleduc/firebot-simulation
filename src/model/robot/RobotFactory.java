package model.robot;

import model.map.*;
import java.util.NoSuchElementException;

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

    private static Drone createDrone(Case caseCourante, Carte carte, double vitesseLue){
        Drone.checkPosition(caseCourante, carte);
        double vitesse = Drone.VITESSE_DEFAUT;
        if (vitesseLue != -1 ){
            vitesse = vitesseLue;
        }

        return new Drone(caseCourante, carte, vitesse);
    }
    
    private static RobotRoues createRobotRoues(Case caseCourante, Carte carte, double vitesseLue){
        RobotRoues.checkPosition(caseCourante, carte);
        double vitesse = RobotRoues.VITESSE_DEFAUT;
        if (vitesseLue != -1){
            vitesse = vitesseLue;
        }

        return new RobotRoues(caseCourante, carte, vitesse);
    }

    private static RobotPattes createRobotPattes(Case caseCourante, Carte carte){
        RobotPattes.checkPosition(caseCourante, carte);
        return new RobotPattes(caseCourante, carte);
    }

    private static RobotChenilles createRobotChenilles(Case caseCourante, Carte carte, double vitesseLue){
        RobotChenilles.checkPosition(caseCourante, carte);
        double vitesse = RobotChenilles.VITESSE_DEFAUT;
        if (vitesseLue != -1){
            vitesse = vitesseLue;
        }
        return new RobotChenilles(caseCourante, carte, vitesse);
    }
}
