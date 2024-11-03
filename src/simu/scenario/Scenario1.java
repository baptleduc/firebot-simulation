package simu.scenario;


import model.DonneesSimulation;
import model.map.Direction;
import model.map.Incendie;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario1 implements Scenario {

    public void createEvenements(Simulateur simulateur, DonneesSimulation model) {
        Robot robot = model.getRobots().get(1); // Choisir le deuxième robot
    
        // Déplacer le robot vers le NORD pour atteindre la case (5, 5)
        robot.createEvenementsDeplacement(simulateur, Direction.NORD);
    
        // Gérer l'incendie sur la case actuelle
        Incendie incendie = model.getIncendies().get(robot.getPositionApresEvenements());
        robot.createEvenementsInterventionIncendie(simulateur, incendie);
    
        // Déplacer le robot vers l'OUEST deux fois
        robot.createEvenementsDeplacement(simulateur, Direction.OUEST);
        robot.createEvenementsDeplacement(simulateur, Direction.OUEST);

        
        // Remplir Reservoir 
        robot.createEvenementsRemplirReservoir(simulateur);;

        // Déplacer le robot vers l'EST deux fois
        robot.createEvenementsDeplacement(simulateur, Direction.EST);
        robot.createEvenementsDeplacement(simulateur, Direction.EST);
        

        // Gérer l'incendie sur la case actuelle
        incendie = model.getIncendies().get(robot.getPositionApresEvenements());
        robot.createEvenementsInterventionIncendie(simulateur, incendie);
    }
}
