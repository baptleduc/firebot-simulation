package strategie;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import model.map.Carte;
import model.map.Case;
import model.map.Incendie;
import model.robot.Robot;
import simu.Simulateur;

public class StrategieElementaire extends Strategie {

    /**
     * Constructeur de la classe StrategieElementaire
     * 
     * @param simulateur le simulateur associé
     * @param carte      la carte associée
     * @param incendies  les incendies à éteindre
     * @param robots     les robots disponibles
     * @param pointsEau  les points d'eau disponibles
     */
    public StrategieElementaire(Simulateur simulateur, Carte carte, Map<Case, Incendie> incendies, List<Robot> robots,
            List<Case> pointsEau) {
                Map<Case, Incendie> incendiesCopy = new HashMap<>();
                for (Incendie incendie : incendies.values()) {
                    incendiesCopy.put(incendie.getPosition(), incendie);
                }
        super(simulateur, carte, incendiesCopy, robots, pointsEau);
    }

    /**
     * Traite le cas où l'incendie affecté à un robot est éteint
     * @param robot le robot associé
     */
    private void traiterIncendieEteint(Robot robot) {
        this.incendies.remove(this.affectationRobots.get(robot).getPosition());
        super.desaffecterRobot(robot);

        Case caseIncendie = super.obtenirCaseLaPlusProche(robot, robot.getPosition(),
                new ArrayList<>(this.incendies.keySet()));
        if (caseIncendie == null) {
            return;
        }
        Incendie incendie = this.incendies.get(caseIncendie);
        super.affecterRobot(robot, incendie);
        super.ordonnerInterventionIncendie(robot, incendie);
    }

    @Override
    protected void affectationsInitiales() {
        List<Robot> copieRobots = new ArrayList<>(this.robots);
        for (Incendie incendie : this.incendies.values()) {
            for (Robot robot : copieRobots) {
                if (robot.estOccupe()) {
                    continue;
                }
                try {
                    this.algoPlusCourtChemin.tempsDeplacement(robot, incendie.getPosition(), robot.getPosition());
                } catch (Exception e) {
                    continue;
                }
                super.affecterRobot(robot, incendie);
                copieRobots.remove(robot);
                break;
            }
        }
    }

    @Override
    public void finEvenementsAction(Robot robot) {
        if (robot.getNiveauEau() == 0) {
            super.desaffecterRobot(robot);
            return;
        }
        this.traiterIncendieEteint(robot);
    }

}
