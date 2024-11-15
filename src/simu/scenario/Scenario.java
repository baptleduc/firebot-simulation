package simu.scenario;

import model.DonneesSimulation;

public interface Scenario {
    
    public abstract void createEvenements();
    public abstract void setModel(DonneesSimulation model);

    
}
