package simu.scenario;


import model.DonneesSimulation;
import simu.Simulateur;

/**
 * Interface représentant un scénario de simulation.
 */
public interface Scenario {
    /**
     * Crée les événements nécessaires pour le scénario spécifié.
     * 
     * @param simulateur L'instance de simulateur responsable de la gestion des événements.
     * @param model Les données de simulation sur lesquelles les événements seront appliqués.
     */
    public abstract void createEvenements(Simulateur simulateur, DonneesSimulation model);

    
}
