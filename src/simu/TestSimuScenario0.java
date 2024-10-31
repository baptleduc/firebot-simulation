package simu;

import java.awt.Color;
import gui.GUISimulator;
import io.LecteurDonnees;
import model.DonneesSimulation;
import event.*;
import model.robot.*;
import model.map.*;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestSimuScenario0 {


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        DonneesSimulation donneesSimulation;
        try {

            donneesSimulation = LecteurDonnees.creeDonnees(args[0]);
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

            // crée le simulateur, en l'associant à la fenêtre graphique précédente
            Simulateur simulateur = new Simulateur(gui, donneesSimulation);
            Robot robot = donneesSimulation.getRobots().get(0);
            
            Case currentCase = robot.getPosition();
            for (long i = 2; i < 6; i++){
                Case nextCase = donneesSimulation.getCarte().getVoisin(currentCase, Direction.NORD);
                Evenement event = new EvenementDeplacement(robot, nextCase, i);
                simulateur.ajouteEvenement(event);
                currentCase = nextCase;
            }

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }

    }
}
