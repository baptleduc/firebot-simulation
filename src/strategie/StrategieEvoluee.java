package strategie;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import model.DonneesSimulation;
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
    public StrategieEvoluee(Simulateur simulateur, DonneesSimulation model) {
        super(simulateur, model);
        this.carte = model.getCarte();
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
                pointsRemplissage.add(robot.obtenirCaseRemplissageAssocié(c, algoPlusCourtChemin, this.carte));
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
    private double obtenirTempsExtinctionIncendie(Robot robot, Incendie incendie) throws IllegalArgumentException{
        double tempsDeplacementVersIncendie = this.algoPlusCourtChemin.tempsDeplacement(robot, robot.getPosition(),
                incendie.getPosition());
        double nombreDeRecharges = Math.floor(incendie.getQuantiteEau() / robot.getCapaciteReservoir());
        if (nombreDeRecharges == 0) {
            return tempsDeplacementVersIncendie;
        }

        List<Case> pointsRemplissage = this.obtenirPointsRemplissageAccessibles(robot);
        if (pointsRemplissage.isEmpty()) {
            throw new IllegalArgumentException("Aucun point de remplissage accessible");
        }

        Case pointRemplissageAProximite = super.obtenirCaseLaPlusProche(robot, incendie.getPosition(),
                pointsRemplissage);

        double tempsDeplacementIncendieVersEau = this.algoPlusCourtChemin.tempsDeplacement(robot,
                incendie.getPosition(), pointRemplissageAProximite);

        return tempsDeplacementVersIncendie + tempsDeplacementIncendieVersEau * nombreDeRecharges * 2;
    }

    /**
     * Déplace le robot vers un point de remplissage et rempli son réservoir
     * 
     * @param robot le robot associé
     */
    private void deplacementEtRemplissementReservoir(Robot robot) {
        List<Case> pointsRemplissage = obtenirPointsRemplissageAccessibles(robot);
        if (pointsRemplissage.isEmpty()) {
            return;
        }
        Case pointRemplissageAProximite = super.obtenirCaseLaPlusProche(robot, robot.getPosition(), pointsRemplissage);
        robot.deplacementPlusCourtChemin(simulateur, pointRemplissageAProximite, algoPlusCourtChemin);
        robot.createEvenementsRemplirReservoir(simulateur);
        robot.createEvenementsPrevenirFinIntervention(simulateur, this::finRemplissementAction);
    }

    /**
     * Traite le cas où l'incendie affecté à un robot est éteint
     * 
     * @param robot le robot associé
     */
    private void traiterIncendieEteint(Robot robot) {
        this.incendies.remove(this.affectationRobots.get(robot).getPosition());
        super.desaffecterRobot(robot);

        Incendie incendie = this.trouverIncendieLePlusRapideAEteindre(robot);
        if (incendie == null) {
            return;
        }
        
        super.affecterRobot(robot, incendie);
        super.ordonnerInterventionIncendie(robot, incendie);
    }

    /**
     * Affecte le robot le plus rapide à éteindre un incendie sur ce dernier
     * @param robots la liste des robots disponibles
     */
    private void assignerRobotPlusRapide(List<Robot> robots) {
        Robot robotPlusRapide = null;
        Incendie incendiePlusRapide = null;
        Double tempsMinToutRobot = Double.MAX_VALUE;

        Iterator<Robot> iterator = robots.iterator();
        while (iterator.hasNext()) {
            Robot robot = iterator.next();
            if (robot.estOccupe()) {
                iterator.remove();
                continue;
            }

            Incendie incendieCible = trouverIncendieLePlusRapideAEteindre(robot);
            if (incendieCible == null) {
                iterator.remove();
                continue;
            }

            double temps;
            try {
                temps = obtenirTempsExtinctionIncendie(robot, incendieCible);
            } catch (Exception e) {
                iterator.remove();
                continue;
            }
            if (temps < tempsMinToutRobot) {
                tempsMinToutRobot = temps;
                robotPlusRapide = robot;
                incendiePlusRapide = incendieCible;
            }
        }

        if (robotPlusRapide != null) {
            super.affecterRobot(robotPlusRapide, incendiePlusRapide);
            robots.remove(robotPlusRapide);
        }
    }

    /**
     * Trouve l'incendie le plus rapide à éteindre pour un robot
     * @param robot le robot associé
     * @return l'incendie le plus rapide à éteindre, null si aucun incendie n'est accessible
     */
    private Incendie trouverIncendieLePlusRapideAEteindre(Robot robot) {
        Incendie incendiePlusProche = null;
        Double tempsMin = Double.MAX_VALUE;

        for (Incendie incendie : this.incendies.values()) {
            double temps;
            try {
                temps = obtenirTempsExtinctionIncendie(robot, incendie);
            } catch (Exception e) {
                continue;
            }

            if (temps < tempsMin) {
                tempsMin = temps;
                incendiePlusProche = incendie;
            }
        }
        return incendiePlusProche;
    }

    @Override
    protected void affectationsInitiales() {
        List<Robot> copieRobots = new ArrayList<>(this.robots); // Copie modifiable de la liste des robots
        while (!copieRobots.isEmpty()) {
            assignerRobotPlusRapide(copieRobots);
        }
    }

    @Override
    public void finEvenementsAction(Robot robot) {
        if (robot.getNiveauEau() == 0) {
            this.deplacementEtRemplissementReservoir(robot);
            return;
        }
        this.traiterIncendieEteint(robot);
    }

    public void finRemplissementAction(Robot robot) {
        super.ordonnerInterventionIncendie(robot, this.affectationRobots.get(robot));
    }

}
