package chemin;

import model.robot.Robot;

import java.util.List;

import model.map.Carte;
import model.map.Case;

public interface PlusCourtChemin {

    public abstract double tempsDeplacement(Robot robot, Case caseDepart, Case caseArrivee);

    public abstract List<Case> creeChemin(Robot robot, Case caseDepart, Case caseArrivee);
}

