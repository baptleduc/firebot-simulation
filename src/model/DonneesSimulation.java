package model;
import model.map.*;
import model.robot.Robot;
import java.util.ArrayList;
import java.util.List;

public class DonneesSimulation {
    private Carte carte;
    private List<Robot> robots = new ArrayList<Robot>();
    private List<Incendie> incendies = new ArrayList<Incendie>();
    
    public DonneesSimulation(){
    }


    public void setCarte(Carte carte){
        this.carte = carte;
    }

    public Carte getCarte(Carte carte){
        return this.carte;
    }
    
    public List<Incendie> getIncendies(){
        return this.incendies;
    }

    public List<Robot> getRobots(){
        return this.robots;
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder(); // mutable
        output.append("Carte :\n").append(this.carte.toString()).append("\n");

        output.append("Incendies : ").append(this.incendies.size()).append("\n");
        for (Incendie incendie : this.incendies){
            output.append(incendie.toString()).append("\n");
        }

        output.append("\nRobot : ").append(this.robots.size()).append("\n");
        for(Robot robot : this.robots){
            output.append(robot.toString()).append("\n");
        }
        return output.toString();
    } 

}
