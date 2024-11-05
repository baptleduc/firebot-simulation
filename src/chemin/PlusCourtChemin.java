package chemin;

import model.robot.Robot;
import simu.Simulateur;

import java.util.List;

import model.map.Carte;
import model.map.Case;

public abstract class PlusCourtChemin {

    

    public static double tempsDeplacement(Robot robot, Carte carte, Case caseDepart, Case caseArrivee){
        return 0;
    };

    public static List<Case> chemin(Robot robot, Carte carte, Case caseDepart, Case caseArrivee){
        return null;
    };
}

