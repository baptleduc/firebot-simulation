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


    protected static Case deplacerRobot(Simulateur simulateur, Robot robot, Carte carte, Case currentCase, long date, Direction direction) {
        Evenement evenement;
        Case nextCase;
        try {
            nextCase = carte.getVoisin(currentCase, direction);
            evenement = new EvenementDeplacement(robot, nextCase, date);
        } catch (IllegalArgumentException e) {
            evenement = new EvenementErreur(e.getMessage(), date);
            nextCase = currentCase;
        }
        simulateur.ajouteEvenement(evenement);
        return nextCase;
    }

    protected static long intervenirIncendie(Simulateur simulateur, Robot robot, Incendie incendie, long dateDebutIntervention){
        int quantiteEauDeversee = Math.min(robot.getNiveauEau(), incendie.getQuantiteEau());
        long dateFinIntervention = dateDebutIntervention + quantiteEauDeversee / robot.getInterUnitaire();

        simulateur.ajouteEvenement(new EvenementChangementEtat(robot, EtatRobot.EN_DEVERSAGE, dateDebutIntervention));
        simulateur.ajouteEvenement(new EvenementDeverserEau(robot, incendie, quantiteEauDeversee, dateFinIntervention));
        simulateur.ajouteEvenement(new EvenementChangementEtat(robot, EtatRobot.EN_DEVERSAGE, dateFinIntervention)); // Le robot a fini son intervention, il est re-devient disponible

        return dateFinIntervention;
    }

    protected static long remplirReservoir(Simulateur simulateur, Robot robot, long dateDebutRemplissage){
        long dateFinRemplissage = dateDebutRemplissage + robot.getTempsRemplissage();
        simulateur.ajouteEvenement(new EvenementChangementEtat(robot, EtatRobot.EN_REMPLISSAGE, dateDebutRemplissage));
        simulateur.ajouteEvenement(new EvenementRemplirReservoir(robot, dateFinRemplissage));
        return dateFinRemplissage;
    }
}
