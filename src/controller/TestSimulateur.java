package controller;

import java.awt.Color;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import io.LecteurDonnees;
import model.DonneesSimulation;
import model.map.*;
import model.robot.*;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestSimulateur {
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

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
    }
}

class Simulateur implements Simulable {
    private GUISimulator gui;
    private DonneesSimulation model;

    private final int RATIO_BORDURE_X = 10;
    private final int RATIO_BORDURE_Y = 10;

    private int taillePixelCases;
    
    private int xMin = 60;
    private int yMin = 60;

    public Simulateur(GUISimulator gui, DonneesSimulation model){
        this.gui = gui;
        this.model = model;

        this.xMin = gui.getWidth() / RATIO_BORDURE_X;
        this.yMin = gui.getHeight() / RATIO_BORDURE_Y;

        this.gui.setSimulable(this);
        draw();
    }


    private void draw(){
        this.gui.reset(); // Clear the window
        drawCarte();
        drawIncendies();
    }


    private void drawCarte() {
        
        Carte carte = this.model.getCarte();

        int nbLignes = carte.getNbLignes();
        int nbColonnes = carte.getNbColonnes();
        
        // Calcul de la largeur et de la hauteur des cases, en fonction des bordures et de la taille du GUI
        int widthCases = (gui.getWidth() - 2 * this.xMin) / nbColonnes;
        int heightCases = (gui.getHeight() - 2 * this.yMin) / nbLignes;
        int taillePixelCases = Math.min(widthCases, heightCases);

        this.taillePixelCases = taillePixelCases;
        
    
        for (int idx_lig = 0; idx_lig < nbLignes; idx_lig++) {
            for (int idx_col = 0; idx_col < nbColonnes; idx_col++) {
                // Calcul des positions top-left x et y de la case
                int x = idx_col * taillePixelCases + this.xMin;
                int y = idx_lig * taillePixelCases + this.yMin;
                Case c = carte.getCase(idx_lig, idx_col);
                drawSquare(x, y, taillePixelCases, c.getDrawColor());
            }
        }
    }
    
    private void drawSquare(int x, int y, int tailleCases, Color color) {
        gui.addGraphicalElement(new Rectangle(x, y,  Color.black, color, tailleCases));
    }

    private void drawIncendies(){
        int taillePixelIncendie = this.taillePixelCases - 10;
        for(Incendie incendie : this.model.getIncendies()){
            Case c = incendie.getPosition();
            int x = c.getColonne() * taillePixelCases + this.xMin;
            int y = c.getLigne() * taillePixelCases + this.yMin;
            drawSquare(x, y , taillePixelIncendie, Color.ORANGE);
        }
    }



    @Override
    public void next(){

    }

    @Override
    public void restart(){

    }

}
