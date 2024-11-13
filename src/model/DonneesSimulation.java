package model;

import model.map.*;
import model.robot.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonneesSimulation {
    private Carte carte;
    private List<Robot> robots = new ArrayList<Robot>();
    private Map<Case, Incendie> incendiesParCase = new HashMap<>();
    private List<Incendie> incendies = new ArrayList<Incendie>();
    

    public DonneesSimulation() {
        return;
    }

    /*
     * Clone l'ojet DonneesSimulation
     * @return DonneesSimulation 
     */
    public DonneesSimulation clone()
    {
        DonneesSimulation clone = new DonneesSimulation();
        clone.carte = this.getCarte();

        
        
        for (Incendie incendie : this.getIncendies()) {
            clone.incendies.add(incendie.clone());
        }
        for (Robot robot : this.getRobots()) {
            clone.robots.add(robot.clone());
        }

        
        return clone;
        
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public void setIncendies(List<Incendie> incendies) {
        this.incendies = incendies;
        for (Incendie incendie : incendies) {
            this.incendiesParCase.put(incendie.getPosition(), incendie);
        }
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    public Carte getCarte() {
        return this.carte;
    }

    public Map<Case, Incendie> getIncendiesParCase() {
        return this.incendiesParCase;
    }

    public List<Incendie> getIncendies() {
        return this.incendies;
    }

    public List<Robot> getRobots() {
        return this.robots;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(); // mutable
        output.append("Carte :\n").append(this.carte.toString()).append("\n");

        output.append("Incendies : ").append(this.incendies.size()).append("\n");
        for (Incendie incendie : this.incendies) {
            output.append(incendie.toString()).append("\n");
        }

        output.append("\nRobot : ").append(this.robots.size()).append("\n");
        for (Robot robot : this.robots) {
            output.append(robot.toString()).append("\n");
        }
        return output.toString();
    }

}
