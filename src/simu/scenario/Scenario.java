package simu.scenario;

import event.Evenement;
import event.EvenementDeplacement;
import event.EvenementErreur;
import model.DonneesSimulation;
import model.map.Carte;
import model.map.Case;
import model.map.Direction;
import model.robot.Robot;
import simu.Simulateur;

public abstract class Scenario {
    
    public abstract void createEvenements(Simulateur simulateur, DonneesSimulation model);


    protected Case deplacerRobot(Simulateur simulateur, Robot robot, Carte carte, Case currentCase, long date, Direction direction) {
        Evenement evenement;
        Case nextCase;
        try {
            nextCase = carte.getVoisin(currentCase, direction);
            evenement = new EvenementDeplacement(robot, nextCase, date);
        } catch (IllegalArgumentException e) {
            evenement = new EvenementErreur(e.getMessage(), date);
            nextCase = currentCase;
        }
        simulateur.ajouteEvenement(evenement);
        return nextCase;
    }

}
