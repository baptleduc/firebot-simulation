package simu;

import java.awt.Color;
import java.util.PriorityQueue;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import model.DonneesSimulation;
import event.Evenement;
import event.EvenementErreur;
import model.map.*;
import model.robot.*;

public class Simulateur implements Simulable {
    private GUISimulator gui;
    private DonneesSimulation model;
    private long dateSimulation;
    private PriorityQueue<Evenement> evenements = new PriorityQueue<>(); 
    private final int RATIO_BORDURE_X = 10;
    private final int RATIO_BORDURE_Y = 10;

    private int taillePixelCases;

    private int xMin = 60;
    private int yMin = 60;

    public Simulateur(GUISimulator gui, DonneesSimulation model) {
        this.dateSimulation = (long) 0;
        this.gui = gui;
        this.model = model;

        this.xMin = gui.getWidth() / RATIO_BORDURE_X;
        this.yMin = gui.getHeight() / RATIO_BORDURE_Y;

        // Calcul de la largeur et de la hauteur des cases, en fonction des bordures et
        // de la taille du GUI
        int widthCases = (gui.getWidth() - 2 * this.xMin) / this.model.getCarte().getNbColonnes();
        int heightCases = (gui.getHeight() - 2 * this.yMin) / this.model.getCarte().getNbLignes();
        this.taillePixelCases = Math.min(widthCases, heightCases);

        this.gui.setSimulable(this);
        draw();
    }

    public void ajouteEvenement(Evenement e){
        this.evenements.add(e);
    }
    
    private void incrementeDate(){
        this.dateSimulation ++;
    }

    private boolean simulationTerminee(){
        return this.evenements.isEmpty();
    }

    /**
     * Réinitialise l'affichage et dessine tous les éléments de la simulation : la
     * carte,
     * les incendies, et les robots.
     */
    private void draw() {
        this.gui.reset(); // Clear the window
        drawCarte();
        drawIncendies();
        drawRobots();
    }

    /**
     * Dessine la carte en divisant l'espace graphique en cases colorées
     * représentant les terrains.
     */
    private void drawCarte() {

        Carte carte = this.model.getCarte();

        int nbLignes = carte.getNbLignes();
        int nbColonnes = carte.getNbColonnes();

        

        for (int idx_lig = 0; idx_lig < nbLignes; idx_lig++) {
            for (int idx_col = 0; idx_col < nbColonnes; idx_col++) {
                // Calcul des positions top-left x et y de la case
                int x = calculateXPosition(idx_col);
                int y = calculateYPosition(idx_lig);
                Case c = carte.getCase(idx_lig, idx_col);
                drawRectangle(x, y, taillePixelCases, c.getDrawColor());
            }
        }
    }

    /**
     * Dessine une case spécifique avec les coordonnées et la couleur données.
     *
     * @param x           la coordonnée x de la case.
     * @param y           la coordonnée y de la case.
     * @param tailleCases la taille en pixels de la case.
     * @param color       la couleur de la case.
     */
    private void drawRectangle(int x, int y, int tailleCases, Color color) {
        gui.addGraphicalElement(new Rectangle(x, y, Color.black, color, tailleCases));
    }

    /**
     * Dessine les incendies en les plaçant sur leurs positions et en utilisant une
     * couleur spécifique.
     */
    private void drawIncendies() {
        int taillePixelIncendie = this.taillePixelCases * 8 / 10;
        for (Incendie incendie : this.model.getIncendies().values()) {
            if (incendie.getQuantiteEau() > 0){
                Case c = incendie.getPosition();
                int x = calculateXPosition(c.getColonne());
                int y = calculateYPosition(c.getLigne());
                drawRectangle(x, y, taillePixelIncendie, Color.ORANGE);
            }    
        }
    }

    /**
     * Dessine tous les robots à leur position actuelle.
     */
    private void drawRobots() {
        for (Robot robot : this.model.getRobots()) {
            drawRobot(robot);
        }
    }

    /**
     * Dessine un robot spécifique en fonction de sa position et de sa couleur.
     *
     * @param robot l'instance du robot à dessiner.
     */
    private void drawRobot(Robot robot) {
        Case position = robot.getPosition();
        Color robotColor = robot.getDrawColor();
        int tailleElement = taillePixelCases / 10;

        // Get Top Left x and y
        int x = calculateXPosition(position.getColonne());
        int y = calculateYPosition(position.getLigne());

        // Liste des coordonnées de chaque sous-rectangle pour le robot
        int[][] coordinates = getRobotCoordinates();

        // Ajout des rectangles au gui avec centrage
        drawRobotRectangles(coordinates, x, y, robotColor, tailleElement);
    }

    /**
     * Calcule la position x (en pixels) de départ d'une case.
     *
     * @param colonne l'indice de la colonne de la case.
     * @return la position x en pixels.
     */
    private int calculateXPosition(int colonne) {
        return colonne * this.taillePixelCases + this.xMin;
    }

    /**
     * Calcule la position y (en pixels) de départ d'une case.
     *
     * @param ligne l'indice de la ligne de la case.
     * @return la position y en pixels.
     */
    private int calculateYPosition(int ligne) {
        return ligne * this.taillePixelCases + this.yMin;
    }

    /**
     * Renvoie les coordonnées des rectangles composant la forme du robot.
     *
     * @return un tableau 2D contenant les coordonnées relatives du robot.
     */
    private int[][] getRobotCoordinates() {
        return new int[][] {
                { 30, 0 }, { 40, 0 }, { 50, 0 },
                { 20, 10 }, { 30, 10 }, { 40, 10 }, { 50, 10 }, { 60, 10 },
                { 10, 20 }, { 20, 20 }, { 30, 20 }, { 40, 20 }, { 50, 20 }, { 60, 20 }, { 70, 20 },
                { 0, 30 }, { 10, 30 }, { 40, 30 }, { 70, 30 }, { 80, 30 },
                { 0, 40 }, { 10, 40 }, { 40, 40 }, { 70, 40 }, { 80, 40 },
                { 0, 50 }, { 10, 50 }, { 20, 50 }, { 30, 50 }, { 40, 50 }, { 50, 50 }, { 60, 50 }, { 70, 50 },
                { 80, 50 },
                { 20, 60 }, { 30, 60 }, { 40, 60 }, { 50, 60 }, { 60, 60 },
                { 10, 70 }, { 20, 70 }, { 30, 70 }, { 40, 70 }, { 50, 70 }, { 60, 70 }, { 70, 70 },
                { 10, 80 }, { 20, 80 }, { 30, 80 }, { 40, 80 }, { 50, 80 }, { 60, 80 }, { 70, 80 },
                { 0, 90 }, { 20, 90 }, { 60, 90 }, { 80, 90 }
        };
    }

    /**
     * Dessine chaque partie d'un robot sous forme de petits rectangles.
     *
     * @param coordinates   coordonnées des sous-rectangles représentant le robot.
     * @param x             la coordonnée x de base du robot.
     * @param y             la coordonnée y de base du robot.
     * @param robotColor    la couleur du robot.
     * @param tailleElement la taille d'un sous-rectangle.
     */
    private void drawRobotRectangles(int[][] coordinates, int x, int y, Color robotColor, int tailleElement) {
        for (int[] coord : coordinates) {
            int coordX = x + (coord[0] - 40) * tailleElement / 10; // Centrer par rapport à la largeur total = 80
            int coordY = y + (coord[1] - 45) * tailleElement / 10; // Centrer par rapport à hauteur total = 90
            Rectangle rect = new Rectangle(coordX, coordY, robotColor, robotColor, tailleElement);
            gui.addGraphicalElement(rect);
        }
    }

    @Override
    public void next() {
        incrementeDate();
        while (!simulationTerminee() && evenements.peek().getDate() <= dateSimulation) {
            Evenement event = evenements.poll();
            try {
                event.execute();   
            } catch (Exception e) {
                Evenement evenementErreur = new EvenementErreur(e.getMessage(), dateSimulation);
                evenementErreur.execute();
            }
        }
        draw();

        
    }

    @Override
    public void restart() {
    }

}
