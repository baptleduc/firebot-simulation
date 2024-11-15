package strategie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chemin.PlusCourtCheminAstar;
import model.map.Carte;
import model.map.Case;
import model.map.Incendie;
import model.robot.Robot;
import simu.Simulateur;

public abstract class Strategie {

    protected Simulateur simulateur;
    protected PlusCourtCheminAstar algoPlusCourtChemin;

    protected Map<Case, Incendie> incendies = new HashMap<>();
    protected Map<Robot, Incendie> affectationRobots = new HashMap<>();

    protected List<Robot> robots = new ArrayList<Robot>();
    protected List<Case> pointsEau = new ArrayList<Case>();

    /**
     * Constructeur de la classe abstraite Strategie
     * 
     * @param simulateur le simulateur associé
     * @param carte      la carte associée
     * @param incendies  les incendies à éteindre
     * @param robots     les robots disponibles
     * @param pointsEau  les points d'eau disponibles
     */
    public Strategie(Simulateur simulateur, Carte carte, Map<Case, Incendie> incendies, List<Robot> robots,
            List<Case> pointsEau) {

                for (Incendie incendie : incendies.values()) {
                    this.incendies.put(incendie.getPosition(), incendie);
                }
        this.simulateur = simulateur;
        this.algoPlusCourtChemin = new PlusCourtCheminAstar(carte);
        this.robots = robots;
        this.pointsEau = pointsEau;
    }

    /**
     * Retourne la case la plus proche de la case départ parmi les cases passées en
     * paramètre, le calcul de la distance se fait en fonction des caractéristiques
     * du robot
     * (méthode utilitaire pour les stratégies)
     * 
     * @param robot      le robot associé
     * @param caseDepart la case de départ
     * @param cases      les cases à tester
     * @return la case la plus proche du robot
     */
    protected Case obtenirCaseLaPlusProche(Robot robot, Case caseDepart, List<Case> cases) {
        Case caseLaPlusProche = null;
        double distanceMin = Double.MAX_VALUE;
        for (Case c : cases) {
            double tempsDeplacement;
            try {
                tempsDeplacement = this.algoPlusCourtChemin.tempsDeplacement(robot, caseDepart, c);
            } catch (Exception e) {
                continue;
            }
            if (distanceMin > tempsDeplacement) {
                distanceMin = tempsDeplacement;
                caseLaPlusProche = c;
            }
        }
        return caseLaPlusProche;
    }

    /**
     * Méthode permettant de donner les évenements d'intervention sur un incendie à
     * un robot
     * 
     * @param robot le robot qui doit intervenir
     */
    protected void ordonnerInterventionIncendie(Robot robot, Incendie incendie) {
        robot.deplacementPlusCourtChemin(this.simulateur, incendie.getPosition(), this.algoPlusCourtChemin);
        robot.createEvenementsInterventionIncendie(this.simulateur, incendie);
        robot.createEvenementsPrevenirFinIntervention(this.simulateur, this::finEvenementsAction);
    }

    /**
     * Méthode permettant d'ordonner toutes les interventions incendies associées
     * aux affectations
     */
    protected void ordonnerToutesLesInterventionsIncendies() {
        for (Robot robot : this.affectationRobots.keySet()) {
            Incendie incendie = this.affectationRobots.get(robot);
            this.ordonnerInterventionIncendie(robot, incendie);
        }
    }

    /**
     * Méthode permettant de desaffecter un robot d'un incendie
     * 
     * @param robot le robot à desaffecter
     */
    protected void desaffecterRobot(Robot robot) {
        this.affectationRobots.remove(robot);
    }

    /**
     * Méthode permettant d'affecter un robot à un incendie
     * 
     * @param robot    le robot à affecter
     * @param incendie l'incendie à affecter
     */
    protected void affecterRobot(Robot robot, Incendie incendie) {
        this.affectationRobots.put(robot, incendie);
    }

    /**
     * Méthode permettant d'effectuer les affectations initiales des robots aux
     * incendies
     */
    protected abstract void affectationsInitiales();

    /**
     * Méthode qui doit être appelée par l'évenement de fin d'intervention, permet
     * de donner un nouvel enchainement d'evenements au robot qui vient de finir ses
     * évenements
     * 
     * @param robot le robot qui vient de finir ses évenements
     */
    public abstract void finEvenementsAction(Robot robot);

    /**
     * Exécute la stratégie
     */
    public void executeStrategie() {
        this.affectationsInitiales();
        this.ordonnerToutesLesInterventionsIncendies();
    }

}
