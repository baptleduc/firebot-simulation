package strategie;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

import model.map.Carte;
import model.map.Case;
import model.map.Incendie;
import model.robot.Robot;
import simu.Simulateur;

public class StrategieEvoluee extends Strategie {

    private Carte carte;

    /**
     * Constructeur de la classe StrategieEvoluee
     * 
     * @param simulateur le simulateur associé
     * @param carte      la carte associée
     * @param incendies  les incendies à éteindre
     * @param robots     les robots disponibles
     * @param pointsEau  les points d'eau disponibles
     */
    public StrategieEvoluee(Simulateur simulateur, Carte carte, Map<Case, Incendie> incendies, List<Robot> robots,
            List<Case> pointsEau) {
        super(simulateur, carte, incendies, robots, pointsEau);
        this.carte = carte;
    }

    /**
     * Calcule les points de remplissage accessibles par un robot
     * 
     * @param robot le robot associé
     * @return la liste des points de remplissage accessibles
     */
    private List<Case> obtenirPointsRemplissageAccessibles(Robot robot) {
        List<Case> pointsRemplissage = new ArrayList<>();
        for (Case c : this.pointsEau) {
            try {
                pointsRemplissage.add(robot.obtenirCaseRemplissageAssocié(c, algoPlusCourtChemin, carte));
            } catch (Exception e) {
                continue;
            }
        }
        return pointsRemplissage;
    }

    /**
     * Calcule le temps d'extinction d'un incendie par un robot, en prenant en
     * compte le temps de déplacement vers l'incendie, le temps de remplissage du
     * réservoir et le temps de déplacement vers un point d'eau
     * 
     * @param robot    le robot associé
     * @param incendie l'incendie associé
     * @return le temps d'extinction de l'incendie par le robot
     */
    private double obtenirTempsExtinctionIncendie(Robot robot, Incendie incendie) {
        double tempsDeplacementVersIncendie = this.algoPlusCourtChemin.tempsDeplacement(robot, robot.getPosition(),
                incendie.getPosition());
        double nombreDeRecharges = Math.floor(incendie.getQuantiteEau() / robot.getCapaciteReservoir());
        if (nombreDeRecharges == 0) {
            return tempsDeplacementVersIncendie;
        }

        List<Case> pointsRemplissage = this.obtenirPointsRemplissageAccessibles(robot);

        Case pointRemplissageAProximite = super.obtenirCaseLaPlusProche(robot, incendie.getPosition(),
                pointsRemplissage);

        double tempsDeplacementIncendieVersEau = this.algoPlusCourtChemin.tempsDeplacement(robot,
                incendie.getPosition(), pointRemplissageAProximite);

        return tempsDeplacementVersIncendie + tempsDeplacementIncendieVersEau * nombreDeRecharges * 2;
    }

    @Override
    protected void affectationsInitiales() {
        List<Robot> copieRobots = new ArrayList<>(this.robots); // Copie modifiable de la liste des robots
        while (copieRobots.size() > 0) {
            Double tempsMinToutRobot = Double.MAX_VALUE;
            Robot robotPlusRapide = null;
            Incendie incendiePlusRapide = null;

            // Utilisation d'un Iterator pour parcourir et modifier copieRobots en toute
            // sécurité
            Iterator<Robot> iterator = copieRobots.iterator();
            while (iterator.hasNext()) {
                Robot r = iterator.next();
                if (r.estOccupe()) { // Le robot est occupé, il ne sera donc pas affecté
                    iterator.remove(); // Suppression sécurisée avec l'Iterator
                    continue;
                }

                Incendie incendiePlusProche = null;
                Double tempsMin = Double.MAX_VALUE;

                for (Incendie i : this.incendies.values()) {
                    Double temps;
                    try {
                        temps = this.obtenirTempsExtinctionIncendie(r, i);
                    } catch (Exception e) {
                        continue; // Ignorer cet incendie en cas d'exception
                    }

                    if (temps < tempsMin) { // Recherche de l'incendie le plus proche
                        tempsMin = temps;
                        incendiePlusProche = i;
                    }
                }

                if (incendiePlusProche == null) { // Aucun incendie n'est atteignable par le robot
                    iterator.remove(); // Suppression sécurisée avec l'Iterator
                    continue;
                }

                // Recherche du robot le plus rapide pour atteindre un incendie
                if (tempsMin < tempsMinToutRobot) {
                    tempsMinToutRobot = tempsMin;
                    robotPlusRapide = r;
                    incendiePlusRapide = incendiePlusProche;
                }
            }

            if (robotPlusRapide == null) { // Aucun robot n'est affectable
                break;
            }

            // Affecter le robot le plus rapide à l'incendie le plus proche
            super.affecterRobot(robotPlusRapide, incendiePlusRapide);
            copieRobots.remove(robotPlusRapide); // Retirer le robot affecté de la liste copieRobots
        }
    }

    @Override
    public void finEvenementsAction(Robot robot) {
        if (robot.getNiveauEau() == 0) { // Le robot n'a plus d'eau, il doit se remplir
            List<Case> pointsRemplissage = this.obtenirPointsRemplissageAccessibles(robot);
            Case pointRemplissageAProximite = super.obtenirCaseLaPlusProche(robot, robot.getPosition(),
                    pointsRemplissage);
            robot.deplacementPlusCourtChemin(simulateur, pointRemplissageAProximite, algoPlusCourtChemin);
            robot.createEvenementsRemplirReservoir(simulateur);
            robot.createEvenementsPrevenirFinIntervention(simulateur, this::finRemplissageAction);
            return;
        }
        
        this.incendies.remove(robot.getPosition()); // L'incendie est éteint
        super.desaffecterRobot(robot);

        Case caseIncendie = super.obtenirCaseLaPlusProche(robot, robot.getPosition(),
                new ArrayList<>(this.incendies.keySet()));
        if (caseIncendie == null) { // Plus d'incendie à éteindre pour ce robot
            return;
        }
        Incendie incendie = this.incendies.get(caseIncendie);
        super.affecterRobot(robot, incendie);
        super.ordonnerInterventionIncendie(robot, incendie);
    }

    public void finRemplissageAction(Robot robot) {
        super.ordonnerInterventionIncendie(robot, this.affectationRobots.get(robot));
    }

}
