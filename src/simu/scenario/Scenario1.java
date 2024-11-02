package simu.scenario;


import event.*;
import model.DonneesSimulation;
import model.map.Carte;
import model.map.Case;
import model.map.Direction;
import model.map.Incendie;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario1 extends Scenario {

    public void createEvenements(Simulateur simulateur, DonneesSimulation model) {
        Robot robot = model.getRobots().get(1); // Choisir le deuxième robot
        Carte carte = model.getCarte();
        Case currentCase = robot.getPosition();
        long date = 0;
        Object[] returnObject;
    
        // Déplacer le robot vers le NORD jusqu'à atteindre la case (5, 5)
        while (!(currentCase.getLigne() == 5 && currentCase.getColonne() == 5)) {
            returnObject = deplacerRobot(simulateur, robot, carte, currentCase,  ++date, Direction.NORD);
            currentCase =  (Case) returnObject[0];
            date = (long) returnObject[1];
        }
    
        // Gérer l'incendie sur la case actuelle
        Incendie incendie = model.getIncendies().get(currentCase);
        date = intervenirIncendie(simulateur, robot, incendie, ++date);
        
    
        // Déplacer le robot vers l'OUEST deux fois
        for(int i = 0; i < 2; i++){
            returnObject = deplacerRobot(simulateur, robot, carte, currentCase,  ++date, Direction.OUEST);
            currentCase =  (Case) returnObject[0];
            date = (long) returnObject[1]; 
        }
        
        // Remplir Reservoir 
        date = remplirReservoir(simulateur, robot, date);

        // Déplacer le robot vers l'EST deux fois
        for (int i = 0; i < 2; i++){
            returnObject = deplacerRobot(simulateur, robot, carte, currentCase,  ++date, Direction.EST);
            currentCase =  (Case) returnObject[0];
            date = (long) returnObject[1];
        }
        

        // Gérer l'incendie sur la case actuelle
        incendie = model.getIncendies().get(currentCase);
        date = intervenirIncendie(simulateur, robot, incendie, ++ date);
    }
}
