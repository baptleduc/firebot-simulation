package model.robot;

import java.awt.Color;
import java.util.List;

import chemin.PlusCourtChemin;
import chemin.PlusCourtCheminAstar;
import event.EvenementChangementEtat;
import event.EvenementDeplacement;
import event.EvenementDeverserEau;
import event.EvenementRemplirReservoir;
import model.map.Case;
import model.map.Direction;
import model.map.Incendie;
import model.map.NatureTerrain;
import simu.Simulateur;
import model.map.Carte;

public abstract class Robot {

    private int niveauEau; // en litres
    private int capaciteReservoir; // en litres
    private int tempsRemplissage; // en minutes
    private int interUnitaire; // Nombre de litre qu'il peut deverser en 1 min
    private double vitesseMax;
    private final Color DRAW_COLOR = Color.MAGENTA; //TODO Remove
    private Case position;
    private EtatRobot etatCourant;

    private Case positionApresEvenements;       // Position prévue du robot après l'exécution de ses événements planifiés
    private long dateApresEvenements;           // Date prévue après l'exécution des événements planifiés pour le robot


    protected Carte carte;
    protected double vitesse; // en km/min
    

    public Robot(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax,
            int tempsRemplissage, Carte carte, int interUnitaire) {
        this.position = position;
        this.carte = carte;
        this.capaciteReservoir = capaciteReservoir;
        this.vitesseMax = vitesseMax;
        this.tempsRemplissage = tempsRemplissage;
        this.interUnitaire = interUnitaire;

        checkVitesse(vitesse, vitesseMax);
        this.vitesse = vitesse;

        checkNiveauEau(niveauEau, capaciteReservoir);
        this.niveauEau = niveauEau;
        
        this.etatCourant = EtatRobot.DISPONIBLE;

        this.dateApresEvenements = 0; 
        this.positionApresEvenements = this.position;
    }

    public Case getPosition() {
        return this.position;
    }

    public int getNiveauEau() {
        return this.niveauEau;
    }

    public Color getDrawColor() {
        return this.DRAW_COLOR;
    }

    public int getCapaciteReservoir(){
        return this.capaciteReservoir;
    }

    public int getTempsRemplissage(){
        return this.tempsRemplissage;
    }

    public boolean estOccupe() {
        return this.etatCourant == EtatRobot.EN_REMPLISSAGE || this.etatCourant == EtatRobot.EN_DEVERSAGE || this.etatCourant == EtatRobot.EN_DEPLACEMENT;
    }

    public int getInterUnitaire(){
        return this.interUnitaire;
    }

    public EtatRobot getEtatCourant(){
        return this.etatCourant;
    }


    public void setEtatCourant(EtatRobot newEtat){
        this.etatCourant = newEtat;
    }

    
    public Case getPositionApresEvenements(){
        return this.positionApresEvenements;
    }




    private static void checkNiveauEau(int niveauEau, int capaciteReservoir) {
        if (niveauEau < 0) {
            throw new IllegalArgumentException("Le niveau d'eau ne peut pas être négatif.");
        }
        if (niveauEau > capaciteReservoir) {
            throw new IllegalArgumentException(String.format(
                    "Le niveau d'eau ne peut pas dépasser la capacité du réservoir de %d litres.", capaciteReservoir));
        }
    }

    public void setNiveauEau(int niveauEau) {
        checkNiveauEau(niveauEau, this.capaciteReservoir);
        this.niveauEau = niveauEau;
    }

    /**
     * Déverse une quantité d'eau du réservoir.
     *
     * @param vol la quantité d'eau à déverser en litres.
     * @throws IllegalArgumentException si la quantité demandée est supérieure au
     *                                  niveau actuel d'eau.
     */
    public void deverserEau(int quantite, Incendie incendie) {
        // Vérifiez que le robot est sur la même case que l'incendie
        if (!this.position.equals(incendie.getPosition())) {
            System.err.println(this.position);
            System.err.println(incendie.getPosition());
            throw new IllegalArgumentException("Le robot n'est pas sur la case de l'incendie.");
        }

        if (quantite <= this.niveauEau) {
            incendie.eteindre(quantite);
            this.niveauEau -= quantite; 
        } else {
            throw new IllegalArgumentException("Pas assez d'eau dans le réservoir pour déverser cette quantité.");
        }
    }

    private static void checkVitesse(double vitesse, double vitesseMax) {
        if (vitesse > vitesseMax) {
            throw new IllegalArgumentException(
                    String.format("La nouvelle vitesse doit être inférieure ou égale à la vitesse max qui est : %d",
                            vitesseMax));
        }
    }

    public void setVitesse(double newVitesse) {
        checkVitesse(newVitesse, this.vitesseMax);
        this.vitesse = newVitesse;
    }

    /**
     * Calcule le temps nécessaire (en min) pour se déplacer d'une case à une autre, en
     * fonction
     * des vitesses spécifiques des terrains de départ et d'arrivée.
     *
     * @param caseDepart  la case de départ du déplacement.
     * @param caseArrivee la case de destination du déplacement.
     * @return le temps de déplacement en heures.
     */
    public double calculerTempsDeplacementMinute(Case caseDepart, Case caseArrivee) {
        double vitesseDepart = getVitesse(caseDepart.getNature());
        double vitesseArrivee = getVitesse(caseArrivee.getNature());

        double vitesseMoyenne = (vitesseDepart + vitesseArrivee) / 2;
        int tailleCasesKiloMetres = this.carte.getTailleCases() / 1000;

        return tailleCasesKiloMetres * 60 / vitesseMoyenne;
    }

    public void setPosition(Case newPosition){
        checkPosition(newPosition, this.carte);
        if (!carte.estVoisin(this.position, newPosition)) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'est pas voisine de la position actuelle : %s (setPosition)", newPosition,
                            this.position));
        }
        this.position = newPosition;
    }

    public abstract double getVitesse(NatureTerrain terrain);

    public abstract void checkPosition(Case position, Carte carte);

    public abstract void remplirReservoir();

    //recupere le chemin de l'image du robot
    public String getImagePath()
    {
        return "images/robot/robot_defaut.png";
    }

    @Override
    public String toString() {
        String output = String.format("Robot(%d, %d)", this.position.getLigne(), this.position.getColonne());
        return output;
    }

    //TODO: A supprimer
    /**
     * Crée les évenements nécessaires afin d'effectuer un déplacement élémentaire dans la direction donnée
     * @param simulateur
     * @param direction
     */
    public void createEvenementsDeplacement(Simulateur simulateur, Direction direction) {

        Case nouvellePosition = this.carte.getVoisin(this.positionApresEvenements, direction);
        // Arrondit le temps de déplacement en minutes vers le haut pour garantir le temps nécessaire
        long tempsDeplacement = (long) Math.ceil(this.calculerTempsDeplacementMinute(this.positionApresEvenements, nouvellePosition));

        simulateur.ajouteEvenement(new EvenementChangementEtat(this, EtatRobot.EN_DEPLACEMENT, this.dateApresEvenements)); // Le robot est en état "EN_DEPLACEMENT" entre la date this.dateApresEvenement et this.dateApresEvenement + tempsDeplacement

        this.positionApresEvenements = nouvellePosition;
        this.dateApresEvenements += tempsDeplacement;
    
        // Ajoute un événement pour effectuer le déplacement effectif à la date prévue
        simulateur.ajouteEvenement(new EvenementDeplacement(this, this.positionApresEvenements, this.dateApresEvenements));

        this.dateApresEvenements ++; // Met à jour la date pour le prochain évenement

    }

    /**
     * Crée les évenements nécessaires afin d'effectuer un déplacement élémentaire vers une case donnée (voisine)
     * @param simulateur
     * @param Case
     */
    public void createEvenementsDeplacement(Simulateur simulateur, Case nouvellePosition) {

        // Vérifie que la case d'arrivée est bien voisine de la position actuelle
        if (!carte.estVoisin(this.positionApresEvenements, nouvellePosition)) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'est pas voisine de la position actuelle : %s (createEvenementsDeplacement)", nouvellePosition,
                            this.positionApresEvenements));
        }
        // Verifie que la case d'arrivée est accessible
        this.checkPosition(nouvellePosition, this.carte);

        // Arrondit le temps de déplacement en minutes vers le haut pour garantir le temps nécessaire
        long tempsDeplacement = (long) Math.ceil(this.calculerTempsDeplacementMinute(this.positionApresEvenements, nouvellePosition));

        simulateur.ajouteEvenement(new EvenementChangementEtat(this, EtatRobot.EN_DEPLACEMENT, this.dateApresEvenements)); // Le robot est en état "EN_DEPLACEMENT" entre la date this.dateApresEvenement et this.dateApresEvenement + tempsDeplacement

        this.positionApresEvenements = nouvellePosition;
        this.dateApresEvenements += tempsDeplacement;
    
        // Ajoute un événement pour effectuer le déplacement effectif à la date prévue
        simulateur.ajouteEvenement(new EvenementDeplacement(this, this.positionApresEvenements, this.dateApresEvenements));

        this.dateApresEvenements ++; // Met à jour la date pour le prochain évenement

    }

    /*
     * Crée les évenements nécessaires afin d'effectuer un déplacement vers une case donnée en utilisant l'algorithme A*
     * @param simulateur
     * @param destination
     */
    public void deplacementPlusCourtChemin( Simulateur simulateur, Case destination, PlusCourtChemin algo) {
        
        List<Case> chemin = algo.creeChemin(this, this.position, destination);
        if(chemin == null){
            throw new IllegalArgumentException("Le robot ne peut pas atteindre la destination.");
        }
        for (Case caseDestination : chemin) {
            // print("Case destination : " + caseDestination);
            //System.out.println("Case destination : " + caseDestination);
            this.createEvenementsDeplacement(simulateur, caseDestination);
        }
    }

   

    public void createEvenementsInterventionIncendie(Simulateur simulateur, Incendie incendie){

        int quantiteEauDeversee = Math.min(this.getNiveauEau(), incendie.getQuantiteEau());
        long tempsIntervention =  quantiteEauDeversee / this.getInterUnitaire();

        simulateur.ajouteEvenement(new EvenementChangementEtat(this, EtatRobot.EN_DEVERSAGE, this.dateApresEvenements));// Le robot est en état "EN_DEVERSAGE" entre la date this.dateApresEvenement et this.dateApresEvenement + tempsIntervention

        this.dateApresEvenements += tempsIntervention;
        simulateur.ajouteEvenement(new EvenementDeverserEau(this, incendie, quantiteEauDeversee, this.dateApresEvenements));

        this.dateApresEvenements ++; // Met à jour la date pour le prochain évenement

    }


    public void createEvenementsRemplirReservoir(Simulateur simulateur){
        long tempsRemplissage = this.getTempsRemplissage();

        simulateur.ajouteEvenement(new EvenementChangementEtat(this, EtatRobot.EN_REMPLISSAGE, this.dateApresEvenements)); // Le robot est en état "EN_REMPLISSAGE" entre la date this.dateApresEvenement et this.dateApresEvenement + tempsRemplissage

        this.dateApresEvenements += tempsRemplissage;
        simulateur.ajouteEvenement(new EvenementRemplirReservoir(this, this.dateApresEvenements));

        this.dateApresEvenements ++; // Met à jour la date pour le prochain évenement

    }
}
