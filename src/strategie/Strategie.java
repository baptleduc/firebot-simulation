package strategie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chemin.PlusCourtCheminAstar;
import model.DonneesSimulation;
import model.map.Carte;
import model.map.Case;
import model.map.Incendie;
import model.robot.Robot;
import simu.Simulateur;
import simu.scenario.Scenario;

public abstract class Strategie implements Scenario{

    protected Simulateur simulateur;
    protected PlusCourtCheminAstar algoPlusCourtChemin;

    protected DonneesSimulation model;
    protected Carte carte;
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
    public Strategie(Simulateur simulateur, DonneesSimulation model) {
        this.simulateur = simulateur;
        setModel(model);
    }

    @Override
    public void setModel(DonneesSimulation newModel){
        System.out.println("Changement de modèle");

        
        this.incendies.clear();
        affectationRobots.clear();

        this.model = newModel;


        for (Incendie incendie : newModel.getIncendiesParCase().values()) {
            this.incendies.put(incendie.getPosition(), incendie);
            System.out.println("Incendie ajouté à la stratégie : " + incendie.getPosition());
        }

        this.algoPlusCourtChemin = new PlusCourtCheminAstar(newModel.getCarte());
        this.carte = newModel.getCarte();
        this.robots = newModel.getRobots();
        this.pointsEau = newModel.getPointsEau();
        
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
        System.out.println("Intervention sur l'incendie " + incendie.getPosition());
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
        System.out.println("Affectation du robot " + robot + " à l'incendie " + incendie.getPosition());
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
    @Override
    public void createEvenements() {
        System.out.println("Création des événements de la stratégie");	
        this.affectationsInitiales();
        this.ordonnerToutesLesInterventionsIncendies();
    }

}
