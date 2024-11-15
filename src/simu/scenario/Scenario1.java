package simu.scenario;


import model.DonneesSimulation;
import model.map.Direction;
import model.map.Incendie;
import model.robot.Robot;
import simu.Simulateur;

public class Scenario1 implements Scenario {
    @Override
    public void createEvenements(Simulateur simulateur, DonneesSimulation model) {
        Robot robot = model.getRobots().get(1); // Choisir le deuxième robot
    
        // Déplacer le robot vers le NORD pour atteindre la case (5, 5)
        robot.deplacerDirection(simulateur, Direction.NORD);
    
        // Gérer l'incendie sur la case actuelle
        Incendie incendie = model.getIncendiesParCase().get(robot.getPositionApresEvenements());
        robot.createEvenementsInterventionIncendie(simulateur, incendie);
    
        // Déplacer le robot vers l'OUEST deux fois
        robot.deplacerDirection(simulateur, Direction.OUEST);
        robot.deplacerDirection(simulateur, Direction.OUEST);

        
        // Remplir Reservoir 
        robot.createEvenementsRemplirReservoir(simulateur);;

        // Déplacer le robot vers l'EST deux fois
        robot.deplacerDirection(simulateur, Direction.EST);
        robot.deplacerDirection(simulateur, Direction.EST);
        

        // Gérer l'incendie sur la case actuelle
        incendie = model.getIncendiesParCase().get(robot.getPositionApresEvenements());
        robot.createEvenementsInterventionIncendie(simulateur, incendie);
    }
}
