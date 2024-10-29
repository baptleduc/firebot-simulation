package io;
import map.*;
import robot.Robot;
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

}
