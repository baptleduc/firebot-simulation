package simu;

import java.awt.Color;
import java.util.PriorityQueue;
import java.util.Random;

import gui.GUISimulator;
import gui.ImageElement;
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
    
    /**
     * Incrémente de 1 minute la date courante
     */
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


        //Random pour afficher la diversité dans les cases d'herbe et maison
        //On fixe la seed pour avoir la même carte à chaque fois
        Random random = new Random(1111);

        

        for (int idx_lig = 0; idx_lig < nbLignes; idx_lig++) {
            for (int idx_col = 0; idx_col < nbColonnes; idx_col++) {
                // Calcul des positions top-left x et y de la case
                int x = calculateXPosition(idx_col);
                int y = calculateYPosition(idx_lig);
                Case c = carte.getCase(idx_lig, idx_col);

                drawCase(x, y,random,c,carte);
            }
        }
    }

    
   
    /**
     * Dessine les cases qui prennent en compte les bords en fonction de la nature de la case et de ses voisins.
     * 
     * @param c
     * @param sprites
     * @param carte
     * @param coin
     * @return
     */
    private String[] drawElementBord(Case c, String[] sprites, Carte carte, boolean coin)
    {

        int lig = c.getLigne();
        int col = c.getColonne();

        // Sprites par défaut
        String s1 = sprites[0];
        String s2 = sprites[0];
        String s3 = sprites[0];
        String s4 = sprites[0];
    
        // Voisins
        boolean left = col > 0 && carte.getCase(lig, col - 1).getNature() == c.getNature();
        boolean right = col < carte.getNbColonnes() - 1 && carte.getCase(lig, col + 1).getNature() == c.getNature();
        boolean top = lig > 0 &&  carte.getCase(lig - 1, col).getNature() == c.getNature();
        boolean bottom = lig <  carte.getNbLignes() - 1 && carte.getCase(lig + 1, col).getNature() == c.getNature();
    
        if (top && left && !right && !bottom) {
            s1 = sprites[0];
            s2 = sprites[2]; 
            s3 = sprites[4];
            s4 = sprites[8];
        } else if (top && right && !left && !bottom) {
            s1 = sprites[1]; 
            s2 = sprites[0];
            s3 = sprites[7]; 
            s4 = sprites[4];
        } else if (bottom && left && !right && !top) {
            s1 = sprites[3];
            s2 = sprites[6];
            s3 = sprites[0];
            s4 = sprites[2];
        } else if (bottom && right && !left && !top) {
            s1 = sprites[5];
            s2 = sprites[3];
            s3 = sprites[1];
            s4 = sprites[0];
        } else if (top && bottom && !left && !right) {
            s1 = s3 = sprites[1]; 
            s2 = s4 = sprites[2]; 
        } else if (left && right && !top && !bottom) {
            s1 = s2 = sprites[3]; 
            s3 = s4 = sprites[4]; 
        } else if (top && bottom && left && right) {
            s1 = s2 = s3 = s4 = sprites[0];
        } else if (!top && !bottom && !left && !right) {
            s1 = sprites[5]; // Haut gauche
            s2 = sprites[6]; // Haut droite
            s3 = sprites[7]; // Bas gauche
            s4 = sprites[8]; // Bas droite
        
        // Cas où seul le haut est connecté
        } else if (top && !bottom && !left && !right) {
            s1 = sprites[1]; // Gauche
            s2 = sprites[2]; // Droite
            s3 = sprites[7]; // Bas gauche
            s4 = sprites[8]; // Bas droite
        
        // Cas où seul le bas est connecté
        } else if (bottom && !top && !left && !right) {
            s1 = sprites[5]; // Haut gauche
            s2 = sprites[6]; // Haut droite
            s3 = sprites[1]; // Gauche
            s4 = sprites[2]; // Droite
        
        // Cas où seule la gauche est connectée
        } else if (left && !right && !top && !bottom) {
            s1 = sprites[3]; // Haut
            s2 = sprites[6]; // Haut droite
            s3 = sprites[4]; // Bas
            s4 = sprites[8]; // Bas droite
        
        // Cas où seule la droite est connectée
        } else if (right && !left && !top && !bottom) {
            s1 = sprites[5]; // Haut gauche
            s2 = sprites[3]; // Haut
            s3 = sprites[7]; // Bas gauche
            s4 = sprites[4]; // Bas
        } if (top && left && bottom && !right) {
            s1 = sprites[0]; // Milieu
            s2 = sprites[2]; // Droite
            s3 = sprites[0]; // Milieu
            s4 = sprites[2]; // Droite
        
        // Cas où le haut, la droite et le bas sont connectés, mais pas la gauche
        } else if (top && right && bottom && !left) {
            s1 = sprites[1]; // Gauche
            s2 = sprites[0]; // Milieu
            s3 = sprites[1]; // Bas gauche
            s4 = sprites[0]; // Bas
        
        // Cas où le haut, la gauche et la droite sont connectés, mais pas le bas
        } else if (top && left && right && !bottom) {
            s1 = sprites[0]; // Haut
            s2 = sprites[0]; // Milieu
            s3 = sprites[4]; // Bas
            s4 = sprites[4]; // Bas droite
        
        } else if (bottom && left && right && !top) {
            s1 = sprites[3]; // Haut gauche
            s2 = sprites[3]; // Haut
            s3 = sprites[0]; // Gauche
            s4 = sprites[0]; // Milieu
        }

        // Cas des coins suivant la nature des cases en diagonale
        if(coin){
            
            if(s1 == sprites[0] && lig > 0 && col > 0 && carte.getCase(lig - 1, col - 1).getNature() != c.getNature()){
                s1 = sprites[12];
            }
           
                if(s2 == sprites[0] && lig > 0 && col < carte.getNbColonnes() - 1 && carte.getCase(lig - 1, col + 1).getNature() != c.getNature()){
                s2 = sprites[11];
            }
            if(s3 == sprites[0] && lig < carte.getNbLignes() - 1 && col > 0 && carte.getCase(lig + 1, col - 1).getNature() != c.getNature()){
                s3 = sprites[10];
            }
            if(s4 == sprites[0] && lig < carte.getNbLignes() - 1 && col < carte.getNbColonnes() - 1 && carte.getCase(lig + 1, col + 1).getNature() != c.getNature()){
                s4 = sprites[9];
            }
        }
        return new String[]{s1, s2, s3, s4};
    }

     /**
     * Dessine la case à une position donnée.
     * 
     * @param x_sprite
     * @param y_sprite
     * @param random
     * @param case
     * @param carte
     */
    private void drawCase(int xCase, int yCase, Random random, Case c, Carte carte) { 
        


        int adjustedX = xCase- taillePixelCases / 2;
        int adjustedY = yCase - taillePixelCases / 2;
        
        int halfSize = taillePixelCases / 2;
        

        // Images par défaut, une case est consituée de 4 images
        String s1 = "./images/herbe/herbe_1.png";
        String s2 = "./images/herbe/herbe_1.png";
        String s3 = "./images/herbe/herbe_1.png";
        String s4 = "./images/herbe/herbe_1.png";

        if(c.getNature() == NatureTerrain.TERRAIN_LIBRE )
        {

            String[] sprites_herbe = {
            "./images/herbe/herbe_1.png",
            "./images/herbe/herbe_2.png",
            "./images/herbe/herbe_1.png",
            "./images/herbe/herbe_3.png",
            "./images/herbe/herbe_1.png",
            "./images/herbe/herbe_2.png",
            "./images/herbe/herbe_1.png",
            "./images/herbe/herbe_3.png",
            "./images/herbe/herbe_1.png",
            "./images/herbe/herbe_2.png",
            "./images/herbe/herbe_3.png",
            "./images/herbe/herbe_2.png",
            "./images/herbe/herbe_2.png",
            "./images/herbe/herbe_1.png",
            "./images/herbe/herbe_2.png",
            "./images/herbe/herbe_1.png",
            "./images/herbe/herbe_3.png",
            "./images/herbe/herbe_1.png",
            "./images/herbe/herbe_2.png",
            "./images/herbe/herbe_3.png",
            "./images/herbe/herbe_6.png",
            "./images/herbe/herbe_7.png",
            "./images/herbe/herbe_8.png",
            "./images/herbe/herbe_9.png",
            "./images/herbe/herbe_10.png",
            "./images/herbe/herbe_11.png",
            "./images/herbe/herbe_12.png",

        };

        
            s1 = sprites_herbe[random.nextInt(sprites_herbe.length-1)];
            s2 = sprites_herbe[random.nextInt(sprites_herbe.length-1)];
            s3 = sprites_herbe[random.nextInt(sprites_herbe.length-1)];
            s4 = sprites_herbe[random.nextInt(sprites_herbe.length-1)];
        }
        else if(c.getNature() == NatureTerrain.HABITAT)
        {
            String[] sprites_maison = 
            {
                "./images/maison/maison_1.png",
                "./images/maison/maison_2.png",
                "./images/maison/maison_3.png",
                "./images/maison/maison_4.png",
                "./images/maison/maison_5.png",
                "./images/maison/maison_6.png",
                "./images/maison/maison_7.png",
                "./images/maison/maison_8.png",
                "./images/maison/maison_9.png"
            };


            String[] sprites_herbe = {
                "./images/herbe/herbe_1.png",
                "./images/herbe/herbe_2.png",
                "./images/herbe/herbe_1.png",
                "./images/herbe/herbe_3.png",
                "./images/herbe/herbe_1.png",
                "./images/herbe/herbe_2.png",
                "./images/herbe/herbe_1.png",
                "./images/herbe/herbe_3.png",
                "./images/herbe/herbe_1.png",
                "./images/herbe/herbe_2.png",
                "./images/herbe/herbe_3.png",
                "./images/herbe/herbe_2.png",
                "./images/herbe/herbe_2.png",
                "./images/herbe/herbe_1.png",
                "./images/herbe/herbe_2.png",
                "./images/herbe/herbe_1.png",
                "./images/herbe/herbe_3.png",
                "./images/herbe/herbe_1.png",
                "./images/herbe/herbe_2.png",
                "./images/herbe/herbe_3.png",
                "./images/herbe/herbe_6.png",
                "./images/herbe/herbe_7.png",
                "./images/herbe/herbe_8.png",
                "./images/herbe/herbe_9.png",
                "./images/herbe/herbe_10.png",
                "./images/herbe/herbe_11.png",
                "./images/herbe/herbe_12.png",
    
            };
    
            
                s1 = sprites_herbe[random.nextInt(sprites_herbe.length-1)];
                s2 = sprites_herbe[random.nextInt(sprites_herbe.length-1)];
                s3 = sprites_herbe[random.nextInt(sprites_herbe.length-1)];
                s4 = sprites_herbe[random.nextInt(sprites_herbe.length-1)];



            gui.addGraphicalElement(new ImageElement(adjustedX, adjustedY, s1, halfSize, halfSize, null));
        
            gui.addGraphicalElement(new ImageElement(adjustedX + halfSize, adjustedY, s2, halfSize, halfSize, null));
        
            gui.addGraphicalElement(new ImageElement(adjustedX, adjustedY + halfSize, s3, halfSize, halfSize, null));
        
            gui.addGraphicalElement(new ImageElement(adjustedX + halfSize, adjustedY + halfSize, s4, halfSize, halfSize, null));

            random.nextInt(10);
            s1 = sprites_maison[random.nextInt(sprites_maison.length-1)];
            s2 = sprites_maison[random.nextInt(sprites_maison.length-1)];
            s3 = sprites_maison[random.nextInt(sprites_maison.length-1)];
            s4 = sprites_maison[random.nextInt(sprites_maison.length-1)];
        }
        else if(c.getNature() == NatureTerrain.EAU){

            String[] sprites_eau = {
                "./images/eau/eau_milieu.png",        // 0
                "./images/eau/eau_gauche.png",        // 1
                "./images/eau/eau_droite.png",        // 2
                "./images/eau/eau_haut.png",          // 3
                "./images/eau/eau_bas.png",           // 4
                "./images/eau/eau_haut_gauche.png",   // 5
                "./images/eau/eau_haut_droite.png",   // 6
                "./images/eau/eau_bas_gauche.png",    // 7
                "./images/eau/eau_bas_droite.png",     // 8
                "./images/eau/coin_bas_droite.png",    // 9
                "./images/eau/coin_bas_gauche.png",     // 10
                "./images/eau/coin_haut_droite.png",    // 11
                "./images/eau/coin_haut_gauche.png",    // 12
            };

            String[] res = drawElementBord(c, sprites_eau, carte, true);
            s1 = res[0];
            s2 = res[1];
            s3 = res[2];
            s4 = res[3];
        
        }
        else if(c.getNature() == NatureTerrain.FORET){
            String[] sprites_foret = {
                "./images/foret/foret_milieu.png",
                "./images/foret/foret_gauche.png",
                "./images/foret/foret_droite.png",
                "./images/foret/foret_haut.png",
                "./images/foret/foret_bas.png",
                "./images/foret/foret_haut_gauche.png",
                "./images/foret/foret_haut_droite.png",
                "./images/foret/foret_bas_gauche.png",
                "./images/foret/foret_bas_droite.png",

            };
            String[] res = drawElementBord(c, sprites_foret, carte, false);
            s1 = res[0];
            s2 = res[1];
            s3 = res[2];
            s4 = res[3];

        }
        else if(c.getNature() == NatureTerrain.ROCHE){
            String[] sprites_roche = {
                "./images/herbe/herbe_1.png",
                "./images/roche/roche_gauche.png",
                "./images/roche/roche_droite.png",
                "./images/roche/roche_haut.png",
                "./images/roche/roche_bas.png",
                "./images/roche/roche_haut_gauche.png",
                "./images/roche/roche_haut_droite.png",
                "./images/roche/roche_bas_gauche.png",
                "./images/roche/roche_bas_droite.png",
                "./images/roche/roche_coin_bas_droite.png",
                "./images/roche/roche_coin_bas_gauche.png",
                "./images/roche/roche_coin_haut_droite.png",
                "./images/roche/roche_coin_haut_gauche.png",

            };
            String[] res = drawElementBord(c, sprites_roche, carte, true);
            s1 = res[0];
            s2 = res[1];
            s3 = res[2];
            s4 = res[3];
        }
        else
        {
            s1 = "./images/herbe/herbe_1.png";
            s2 = "./images/herbe/herbe_1.png";
            s3 = "./images/herbe/herbe_1.png";
            s4 = "./images/herbe/herbe_1.png";
        }
        
        
        gui.addGraphicalElement(new ImageElement(adjustedX, adjustedY, s1, halfSize, halfSize, null));
        
        gui.addGraphicalElement(new ImageElement(adjustedX + halfSize, adjustedY, s2, halfSize, halfSize, null));
        
        gui.addGraphicalElement(new ImageElement(adjustedX, adjustedY + halfSize, s3, halfSize, halfSize, null));
        
        gui.addGraphicalElement(new ImageElement(adjustedX + halfSize, adjustedY + halfSize, s4, halfSize, halfSize, null));
        
       
    }


    /**
     * Dessine les incendies en les plaçant sur leurs positions.
     */
    private void drawIncendies() {
        int taillePixelIncendie = this.taillePixelCases * 8 / 10;
        for (Incendie incendie : this.model.getIncendies().values()) {
            if (incendie.getQuantiteEau() > 0){
                Case c = incendie.getPosition();
                int x = calculateXPosition(c.getColonne());
                int y = calculateYPosition(c.getLigne());
                String sprite = "./images/feu/feu.png";
                int halfSize = taillePixelIncendie / 2;
                int adjustedX = x - halfSize;
                int adjustedY = y - halfSize;
                gui.addGraphicalElement(new ImageElement(adjustedX, adjustedY, sprite, taillePixelIncendie, taillePixelIncendie, null));

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

        int x = calculateXPosition(position.getColonne());
        int y = calculateYPosition(position.getLigne());

        String sprite = robot.getImagePath();
        
        int halfSize = taillePixelCases / 2;
        int adjustedX = x - halfSize;
        int adjustedY = y - halfSize;
        gui.addGraphicalElement(new ImageElement(adjustedX, adjustedY, sprite, taillePixelCases, taillePixelCases, null));
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
     * NOT USED
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



    /**
     * NOT USED
     * Utilise des rectangles pour dessiner une case
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
     * NOT USED
     * Dessine un robot spécifique en fonction de sa position et de sa couleur.
     * Utilise des rectangles pour dessiner le robot.
     * @param robot l'instance du robot à dessiner.
     */
    private void drawRobotPixel(Robot robot) {
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
     * NOT USED
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

    

    @Override
    public void next() { // Hypothèse : un appel à next = 1min
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
