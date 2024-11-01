package simu.scenario;

import event.*;
import model.DonneesSimulation;
import model.map.Case;
import model.map.Direction;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario0 implements Scenario {

    public void createEvenements(Simulateur simulateur, DonneesSimulation model){
            Robot robot = model.getRobots().get(0); // Choisi le premier robot du model : 'Drone'
            
            Case currentCase = robot.getPosition();
            Evenement event;
            for (long i = 2; i < 6; i++){
                try{
                    Case nextCase = model.getCarte().getVoisin(currentCase, Direction.NORD);
                    event = new EvenementDeplacement(robot, nextCase, i);
                    currentCase = nextCase;
                }
                catch(IllegalArgumentException e){
                    event = new EvenementErreur(e.getMessage(), i);
                }
                
                simulateur.ajouteEvenement(event);
                
            }
    }
}
