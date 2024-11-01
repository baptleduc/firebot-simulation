package simu.scenario;

import event.*;
import model.DonneesSimulation;
import model.map.Carte;
import model.map.Case;
import model.map.Direction;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario0 extends Scenario {

    public void createEvenements(Simulateur simulateur, DonneesSimulation model){
            Robot robot = model.getRobots().get(0); // Choisi le premier robot du model : 'Drone'
            Carte carte = model.getCarte();
            Case currentCase = robot.getPosition();
            for (long i = 1; i < 4; i++){
                currentCase = deplacerRobot(simulateur, robot, carte, currentCase, i, Direction.NORD);
            }
    }
}
