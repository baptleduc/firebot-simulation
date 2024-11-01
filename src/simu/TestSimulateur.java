package simu;

import java.awt.Color;
import gui.GUISimulator;
import io.LecteurDonnees;
import model.DonneesSimulation;
import simu.scenario.Scenario;
import simu.scenario.Scenario0;
import simu.scenario.Scenario1;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.zip.DataFormatException;

public class TestSimulateur {

    public static Scenario createScenario(String numScenario) throws IllegalArgumentException {
        switch (numScenario) {
            case "0":
                System.out.println("Scenario 0 choisi");
                return new Scenario0();
            case "1":
                System.out.println("Scenario 1 choisi");
                return new Scenario1();

            default:
                throw new IllegalArgumentException(String.format("Scenario numero : %s not found", numScenario));
        }
    }
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier> <numScenario>");
            System.exit(1);
        }

        Scenario scenario = null;
        if (args.length == 2){
            try{
              scenario = createScenario(args[1]);  
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                System.exit(1);
            }
            
        }
        
        // Creation du model
        DonneesSimulation donneesSimulation = null;
        try {
            donneesSimulation = LecteurDonnees.creeDonnees(args[0]);
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            // crée le simulateur, en l'associant à la fenêtre graphique précédente
            Simulateur simulateur = new Simulateur(gui, donneesSimulation);
            
            if (scenario != null){
                scenario.createEvenements(simulateur, donneesSimulation);
            }

            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
            System.exit(1);
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
            System.exit(1);
        }  

    }

}
