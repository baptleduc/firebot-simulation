package simu.scenario;

import model.DonneesSimulation;
import simu.Simulateur;

public interface Scenario {
    
    void createEvenements(Simulateur simulateur, DonneesSimulation model);
}
