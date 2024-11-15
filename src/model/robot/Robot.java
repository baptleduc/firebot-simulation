package model.robot;

import java.awt.Color;
import java.util.List;

import chemin.PlusCourtChemin;
import event.EvenementChangementEtat;
import event.EvenementDeplacement;
import event.EvenementDeverserEau;
import event.EvenementPreventionStrategie;
import event.EvenementRemplirReservoir;
import model.map.Case;
import model.map.Direction;
import model.map.Incendie;
import model.map.NatureTerrain;
import simu.Simulateur;
import strategie.FinInterventionAction;
import model.map.Carte;

/*
 * Classe abstraite représentant un robot
 */
public abstract class Robot {

    private int niveauEau; // en litres
    private int capaciteReservoir; // en litres
    private int tempsRemplissage; // en minutes
    private int interUnitaire; // Nombre de litre qu'il peut deverser en 1 min
    private double vitesseMax;
    private final Color DRAW_COLOR = Color.MAGENTA;
    protected Case position;
    private Case positionInitiale;
    private EtatRobot etatCourant;

    private Case positionApresEvenements; // Position prévue du robot après l'exécution de ses événements planifiés
    private long dateApresEvenements; // Date prévue après l'exécution des événements planifiés pour le robot

    protected Carte carte;
    protected double vitesse; // en km/min

    /**
     * Constructeur de la classe Robot
     * 
     * @param position          la position initiale du robot
     * @param niveauEau         le niveau d'eau initial du robot
     * @param capaciteReservoir la capacité du réservoir du robot
     * @param vitesse           la vitesse du robot
     * @param vitesseMax        la vitesse maximale du robot
     * @param tempsRemplissage  le temps de remplissage du réservoir
     * @param carte             la carte sur laquelle se déplace le robot
     * @param interUnitaire     le nombre de litres que le robot peut deverser en 1
     *                          min
     */
    public Robot(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax,
            int tempsRemplissage, Carte carte, int interUnitaire) {
        this.position = position;
        this.positionInitiale = position;
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

    /**
     * Crée une copie du robot.
     */
    public abstract Robot clone();

    /**
     * Réinitialise le robot à son état initial.
     */
    public void reset() {
        this.niveauEau = this.capaciteReservoir;
        this.etatCourant = EtatRobot.DISPONIBLE;
        this.dateApresEvenements = 0;
        this.position = this.positionInitiale;
        this.positionApresEvenements = this.positionInitiale;
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

    public int getCapaciteReservoir() {
        return this.capaciteReservoir;
    }

    public int getTempsRemplissage() {
        return this.tempsRemplissage;
    }

    public boolean estOccupe() {
        return this.etatCourant == EtatRobot.EN_REMPLISSAGE || this.etatCourant == EtatRobot.EN_DEVERSAGE
                || this.etatCourant == EtatRobot.EN_DEPLACEMENT;
    }

    public int getInterUnitaire() {
        return this.interUnitaire;
    }

    public EtatRobot getEtatCourant() {
        return this.etatCourant;
    }

    public void setEtatCourant(EtatRobot newEtat) {
        this.etatCourant = newEtat;
    }

    public Case getPositionApresEvenements() {
        return this.positionApresEvenements;
    }

    /**
     * Vérifie que le niveau d'eau est valide.
     * @param niveauEau le niveau d'eau à vérifier.
     * @param capaciteReservoir la capacité du réservoir du robot.
     */
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

    /**
     * Vérifie que la nouvelle vitesse est valide.
     * @param vitesse la nouvelle vitesse à vérifier.
     * @param vitesseMax la vitesse maximale du robot.
     */
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
     * Calcule le temps nécessaire (en min) pour se déplacer d'une case à une autre,
     * en
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

    public void setPosition(Case newPosition) {
        checkPosition(newPosition, this.carte);
        if (!carte.estVoisin(this.position, newPosition)) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'est pas voisine de la position actuelle : %s (setPosition)",
                            newPosition,
                            this.position));
        }
        this.position = newPosition;
    }

    public abstract double getVitesse(NatureTerrain terrain);

    /**
     * Vérifie si une position est valide pour un robot.
     * @param position la case à vérifier.
     * @param carte la carte dans laquelle se trouve la case.
     */
    public abstract void checkPosition(Case position, Carte carte);

    /**
     * Remplit le réservoir du robot.
     */
    public abstract void remplirReservoir();

    /**
     * Retourne la case de remplissage associée à un point d'eau donné, c'est sur
     * cette case que le robot se déplacera pour se remplir
     * 
     * @param pointEau le point d'eau à associer
     * @param algo     l'algorithme de plus court chemin à utiliser
     * @return la case de remplissage associée
     */
    public abstract Case obtenirCaseRemplissageAssocié(Case pointEau, PlusCourtChemin algo, Carte carte)
            throws IllegalArgumentException;

    // recupere le chemin de l'image du robot
    public String getImagePath() {
        return "images/robot/robot_defaut.png";
    }

    @Override
    public String toString() {
        String output = String.format("Robot(%d, %d)", this.position.getLigne(), this.position.getColonne());
        return output;
    }

    /**
     * Effectue les étapes communes pour déplacer le robot et ajouter les événements
     * nécessaires.
     * 
     * @param simulateur       Le simulateur qui gère les événements.
     * @param nouvellePosition La case vers laquelle le robot se déplace.
     * @param tempsDeplacement Le temps nécessaire pour le déplacement.
     */
    private void effectuerDeplacement(Simulateur simulateur, Case nouvellePosition) {
        // Vérifie que la position est accessible
        this.checkPosition(nouvellePosition, this.carte);

        // Arrondit le temps de déplacement en minutes vers le haut pour garantir le
        // temps nécessaire
        long tempsDeplacement = (long) Math
                .ceil(this.calculerTempsDeplacementMinute(this.positionApresEvenements, nouvellePosition));

        if (tempsDeplacement > 0) {
            // Ajoute un événement pour changer l'état du robot (en déplacement)
            simulateur.ajouteEvenement(
                    new EvenementChangementEtat(this, EtatRobot.EN_DEPLACEMENT, this.dateApresEvenements));
        }

        // Met à jour la position et la date après le déplacement
        this.positionApresEvenements = nouvellePosition;
        this.dateApresEvenements += Math.max(tempsDeplacement, 1);

        // Ajoute un événement pour effectuer le déplacement effectif à la date prévue
        simulateur.ajouteEvenement(new EvenementDeplacement(this, this.positionApresEvenements, tempsDeplacement,
                this.dateApresEvenements));

        // Met à jour la date pour le prochain événement
        this.dateApresEvenements++;
    }

    /**
     * Crée les événements nécessaires pour déplacer le robot dans la direction
     * spécifiée.
     * 
     * @param simulateur Le simulateur qui gère les événements.
     * @param direction  La direction du déplacement.
     */
    public void deplacerDirection(Simulateur simulateur, Direction direction) {
        Case nouvellePosition = this.carte.getVoisin(this.positionApresEvenements, direction);
        this.effectuerDeplacement(simulateur, nouvellePosition);
    }

    /**
     * Crée les événements nécessaires pour déplacer le robot vers une case voisine
     * donnée.
     * 
     * @param simulateur       Le simulateur qui gère les événements.
     * @param nouvellePosition La case de destination.
     * @throws IllegalArgumentException Si la case n'est pas voisine.
     */
    public void deplacerVersCase(Simulateur simulateur, Case nouvellePosition) throws IllegalArgumentException {
        // Vérifie que la case d'arrivée est voisine de la position actuelle
        if (!carte.estVoisin(this.positionApresEvenements, nouvellePosition)) {
            throw new IllegalArgumentException(
                    String.format("La case : %s n'est pas voisine de la position actuelle : %s (deplacerVersCase)",
                            nouvellePosition,
                            this.positionApresEvenements));
        }

        effectuerDeplacement(simulateur, nouvellePosition);
    }

    /*
     * Crée les évenements nécessaires afin d'effectuer un déplacement vers une case
     * donnée en utilisant l'algorithme A*
     * 
     * @param simulateur
     * 
     * @param destination
     */
    public void deplacementPlusCourtChemin(Simulateur simulateur, Case destination, PlusCourtChemin algo) {

        List<Case> chemin = algo.creeChemin(this, this.position, destination);
        if (chemin == null) {
            throw new IllegalArgumentException("Le robot ne peut pas atteindre la destination.");
        }
        for (Case caseDestination : chemin) {
            // print("Case destination : " + caseDestination);
            // System.out.println("Case destination : " + caseDestination);
            this.deplacerVersCase(simulateur, caseDestination);
        }
    }

    /**
     * Crée les événements nécessaires pour intervenir sur un incendie.
     * @param simulateur le simulateur associé.
     * @param incendie l'incendie sur lequel intervenir.
     */
    public void createEvenementsInterventionIncendie(Simulateur simulateur, Incendie incendie) {

        int quantiteEauDeversee = Math.min(this.getNiveauEau(), incendie.getQuantiteEau());
        long tempsIntervention = quantiteEauDeversee / this.getInterUnitaire();

        if (tempsIntervention > 0) {
            // Le robot est en état "EN_DEVERSAGE" entre la date this.dateApresEvenement et
            // this.dateApresEvenement + tempsIntervention
            simulateur.ajouteEvenement(
                    new EvenementChangementEtat(this, EtatRobot.EN_DEVERSAGE, this.dateApresEvenements));
        }

        this.dateApresEvenements += tempsIntervention;
        simulateur.ajouteEvenement(new EvenementDeverserEau(this, incendie, quantiteEauDeversee, tempsIntervention,
                this.dateApresEvenements));

        this.dateApresEvenements++; // Met à jour la date pour le prochain évenement
    }

    /**
     * Crée les événements nécessaires pour remplir le réservoir du robot.
     * @param simulateur le simulateur associé.
     */
    public void createEvenementsRemplirReservoir(Simulateur simulateur) {
        long tempsRemplissage = this.getTempsRemplissage();

        if (tempsRemplissage > 0) {
            // Le robot est en état "EN_REMPLISSAGE" entre la date this.dateApresEvenement
            // et this.dateApresEvenement + tempsRemplissage
            simulateur.ajouteEvenement(
                    new EvenementChangementEtat(this, EtatRobot.EN_REMPLISSAGE, this.dateApresEvenements));
        }

        this.dateApresEvenements += tempsRemplissage;
        simulateur.ajouteEvenement(new EvenementRemplirReservoir(this, tempsRemplissage, this.dateApresEvenements));

        this.dateApresEvenements++; // Met à jour la date pour le prochain évenement
    }

    /**
     * Crée les événements nécessaires pour prévenir la fin d'une intervention.
     * @param simulateur le simulateur associé.
     * @param action l'action à effectuer à la fin de l'intervention.
     */
    public void createEvenementsPrevenirFinIntervention(Simulateur simulateur, FinInterventionAction action) {
        simulateur.ajouteEvenement(new EvenementPreventionStrategie(this, this.dateApresEvenements, action));
        this.dateApresEvenements++; // Met à jour la date pour le prochain évenement
    }

}
