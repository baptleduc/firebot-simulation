package simu;

import java.awt.Color;
import gui.GUISimulator;
import io.LecteurDonnees;
import model.DonneesSimulation;
import simu.scenario.Scenario;
import simu.scenario.Scenario0;
import simu.scenario.Scenario1;
import simu.scenario.Scenario2;
import strategie.StrategieElementaire;
import strategie.StrategieEvoluee;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestSimulateur {

    public static Scenario createScenario(String numScenario, Simulateur simulateur, DonneesSimulation model) throws IllegalArgumentException {
        switch (numScenario) {
            case "0":
                System.out.println("Scenario 0 choisi");
                return new Scenario0(simulateur, model);
            case "1":
                System.out.println("Scenario 1 choisi");
                return new Scenario1(simulateur, model);
            case "2":
                System.out.println("Scenario 2 choisi");
                return new Scenario2(simulateur, model);

            default:
                throw new IllegalArgumentException(String.format("Scenario numero : %s not found", numScenario));
        }
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out
                    .println("Syntaxe: java TestLecteurDonnees <nomDeFichier> [-strategie] <numScenario/numStrategie>");
            System.exit(1);
        }



        // Creation du model
        DonneesSimulation model = null;
        try {
            model = LecteurDonnees.creeDonnees(args[0]);
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(800, 608, Color.BLACK);
            // crée le simulateur, en l'associant à la fenêtre graphique précédente
            Simulateur simulateur = new Simulateur(gui, model);
            Scenario scenario = null;
            if (args.length == 2) {
                try {
                    scenario = createScenario(args[1], simulateur, model);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            }
            else if (args.length == 3 && args[1].equals("-strategie")) {
                try {
                    if (args[2].toLowerCase().equals("elementaire")) {
    
                        scenario = new StrategieElementaire(simulateur, model);
                    } else if (args[2].toLowerCase().equals("evoluee")) {
                        scenario = new StrategieEvoluee(simulateur, model);
                    } else {
                        throw new IllegalArgumentException("Strategie inconnue");
                        
                    }
                    
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            }
            scenario.createEvenements();

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
            System.exit(1);
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
            System.exit(1);
        }

    }

}
