package simu.scenario;

import event.Evenement;
import event.EvenementChangementEtat;
import event.EvenementDeplacement;
import event.EvenementDeverserEau;
import event.EvenementErreur;
import event.EvenementRemplirReservoir;
import model.DonneesSimulation;
import model.map.Carte;
import model.map.Case;
import model.map.Direction;
import model.map.Incendie;
import model.robot.EtatRobot;
import model.robot.Robot;
import simu.Simulateur;

public abstract class Scenario {
    
    public abstract void createEvenements(Simulateur simulateur, DonneesSimulation model);



    protected static Object[] deplacerRobot(Simulateur simulateur, Robot robot, Carte carte, Case currentCase, long dateDebutDeplacement, Direction direction) {
        Evenement evenement;
        Case nextCase;
        long dateFinDeplacement = dateDebutDeplacement;
        
        try {
            nextCase = carte.getVoisin(currentCase, direction);
            // Arrondit le temps de déplacement en minutes vers le haut pour garantir le temps nécessaire
            long tempsDeplacement = (long) Math.ceil(robot.calculerTempsDeplacementMinute(currentCase, nextCase));
            dateFinDeplacement = dateDebutDeplacement + tempsDeplacement;
            simulateur.ajouteEvenement(new EvenementChangementEtat(robot, EtatRobot.EN_DEPLACEMENT, dateDebutDeplacement));
            evenement = new EvenementDeplacement(robot, nextCase, dateFinDeplacement);
            simulateur.ajouteEvenement(evenement);
            
        } catch (IllegalArgumentException e) {
            evenement = new EvenementErreur(e.getMessage(), dateDebutDeplacement);
            nextCase = currentCase;
            simulateur.ajouteEvenement(evenement);
        }

        return new Object[] {nextCase, dateFinDeplacement};
    }

    protected static long intervenirIncendie(Simulateur simulateur, Robot robot, Incendie incendie, long dateDebutIntervention){
        int quantiteEauDeversee = Math.min(robot.getNiveauEau(), incendie.getQuantiteEau());
        long dateFinIntervention = dateDebutIntervention + quantiteEauDeversee / robot.getInterUnitaire();

        simulateur.ajouteEvenement(new EvenementChangementEtat(robot, EtatRobot.EN_DEVERSAGE, dateDebutIntervention));
        simulateur.ajouteEvenement(new EvenementDeverserEau(robot, incendie, quantiteEauDeversee, dateFinIntervention));
        simulateur.ajouteEvenement(new EvenementChangementEtat(robot, EtatRobot.DISPONIBLE, dateFinIntervention)); // Le robot a fini son intervention, il est re-devient disponible

        return dateFinIntervention;
    }

    protected static long remplirReservoir(Simulateur simulateur, Robot robot, long dateDebutRemplissage){
        long dateFinRemplissage = dateDebutRemplissage + robot.getTempsRemplissage();
        simulateur.ajouteEvenement(new EvenementChangementEtat(robot, EtatRobot.EN_REMPLISSAGE, dateDebutRemplissage));
        simulateur.ajouteEvenement(new EvenementRemplirReservoir(robot, dateFinRemplissage));
        simulateur.ajouteEvenement(new EvenementChangementEtat(robot, EtatRobot.DISPONIBLE, dateFinRemplissage));
        return dateFinRemplissage;
    }
}
