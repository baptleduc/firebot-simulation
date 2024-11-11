package strategie;

import model.robot.Robot;
import simu.Simulateur;
import chemin.PlusCourtCheminAstar;
import model.map.Carte;
import model.map.Incendie;
import model.map.Case;

public class ChefPompier {


    private Simulateur simulateur;
    private Carte carte;
    private Incendie[] incendies;
    private Robot[] robots;

    private PlusCourtCheminAstar algoPlusCourtChemin;

    public ChefPompier(Simulateur simulateur, Carte carte, Incendie[] incendies, Robot[] robots) {
        this.simulateur = simulateur;
        this.carte = carte;
        this.incendies = incendies;
        this.robots = robots;

        this.algoPlusCourtChemin = new PlusCourtCheminAstar(carte);
    }

    private int obtenirIndexRobotDisponible(int indexCourant) {
        for (int i = indexCourant; i < this.robots.length; i++) {
            if (!this.robots[i].estOccupe()) {
                return i;
            }
        }
        return -1;
    }

    private Robot obtenirRobotLePlusProche(Case caseDestination){
        Robot robotPlusProche = null;
        double tempsDeplacementMin = Integer.MAX_VALUE;
        for (Robot robot : this.robots) {
            double tempsDeplacement = this.algoPlusCourtChemin.tempsDeplacement(robot, caseDestination, robot.getPosition());
            if (!robot.estOccupe() && tempsDeplacementMin > tempsDeplacement) {
                tempsDeplacementMin = tempsDeplacement;
                robotPlusProche = robot;
            }
        }
        return robotPlusProche;
    }

    public void executeStrategieElementaire() {
        int robotIndex = 0;
        for (Incendie incendie : this.incendies) {
            robotIndex = obtenirIndexRobotDisponible(robotIndex);
            if (robotIndex == -1) {
                return;
            }
            this.robots[robotIndex].intervernirIncendie(simulateur, incendie, this.algoPlusCourtChemin);
        }
    }

    public void executeStrategieElementairePlus() {
        for (Incendie incendie : this.incendies) {
            Robot robot = obtenirRobotLePlusProche(incendie.getPosition());

            if (robot == null){
                continue;
            }

            robot.intervernirIncendie(simulateur, incendie, this.algoPlusCourtChemin);
        }
    }

}