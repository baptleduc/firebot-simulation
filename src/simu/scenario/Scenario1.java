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
    
        // Déplacer le robot vers le NORD jusqu'à atteindre la case (5, 5)
        while (!(currentCase.getLigne() == 5 && currentCase.getColonne() == 5)) {
            currentCase = deplacerRobot(simulateur, robot, carte, currentCase,  ++date, Direction.NORD);
        }
    
        // Gérer l'incendie sur la case actuelle
        Incendie incendie = model.getIncendies().get(currentCase);
        int quantiteEauDeversee = Math.min(robot.getNiveauEau(), incendie.getQuantiteEau());
        simulateur.ajouteEvenement(new EvenementDeverserEau(robot, incendie, quantiteEauDeversee, ++ date));
    
        // Déplacer le robot vers l'OUEST deux fois
        currentCase = deplacerRobot(simulateur, robot, carte, currentCase, ++date, Direction.OUEST);
        currentCase = deplacerRobot(simulateur, robot, carte, currentCase, ++date, Direction.OUEST);


        // Remplir Reservoir 


        // Déplacer le robot vers l'EST deux fois
        currentCase = deplacerRobot(simulateur, robot, carte, currentCase, ++date, Direction.EST);
        currentCase = deplacerRobot(simulateur, robot, carte, currentCase, ++date, Direction.EST);
    }
    
    
    
    
    
}
