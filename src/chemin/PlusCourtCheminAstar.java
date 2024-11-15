package chemin;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

import model.robot.Robot;
import model.map.Carte;
import model.map.Case;
import model.map.Direction;

class NoeudAstar implements Comparable<NoeudAstar> {


    public Case caseNoeud;
    public double cout;
    public double heuristique;
    public NoeudAstar noeudPrecedent;

    public NoeudAstar(Case caseNoeud, double cout, double heuristique, NoeudAstar noeudPrecedente) {
        this.caseNoeud = caseNoeud;
        this.cout = cout;
        this.heuristique = heuristique;
        this.noeudPrecedent = noeudPrecedente;
    }

    @Override
    public int compareTo(NoeudAstar Noeud2) {
        double h_1;
        double h_2;
        h_1 = this.heuristique;
        h_2 = Noeud2.heuristique;
        if (h_1 < h_2) {
            return -1;
        }
        if (h_1 > h_2) {
            return 1;
        }
        return 0;
    }

}

/**
 * Classe PlusCourtCheminAstar permettant de calculer le plus court chemin en utilisant l'algorithme A*.
 */
public class PlusCourtCheminAstar implements PlusCourtChemin {

    private Carte carte;

    public PlusCourtCheminAstar(Carte carte) {

        this.carte = carte;

    }

    private static double calculerDistance(Case case1, Case case2) {
        return Math.abs(case1.getLigne() - case2.getLigne()) + Math.abs(case1.getColonne() - case2.getColonne());
    }

    /**
     * Calcule le temps de déplacement entre deux cases pour un robot donné.
     * une erreur est levée si le robot ne peut pas effectuer le déplacement.
     * 
     * @param robot
     * @param caseDepart
     * @param caseArrivee
     * @return le temps de déplacement en minutes.
     */
    public double tempsDeplacement(Robot robot, Case caseDepart, Case caseArrivee) {
        NoeudAstar ndAstar = executeAstar(robot, caseDepart, caseArrivee);
        if (ndAstar == null) {
            throw new IllegalArgumentException("Le robot ne peut pas effectuer ce déplacement");
        }
        return ndAstar.cout;
    }

    /**
     * Execute A* et reconstruit le chemin entre deux cases.
     * 
     * @param robot
     * @param caseDepart
     * @param caseArrivee
     */
    public List<Case> creeChemin(Robot robot, Case caseDepart, Case caseArrivee) {
        NoeudAstar noeud = executeAstar(robot, caseDepart, caseArrivee);

        // on enleve le noeud de depart
        if (noeud == null) {
            return null;
        }
        noeud = noeud.noeudPrecedent;

        List<Case> chemin = new ArrayList<Case>();
        while (noeud != null) {
            chemin.add(noeud.caseNoeud);
            noeud = noeud.noeudPrecedent;
        }
        return chemin;
    }

    /**
     * Execute l'algorithme A* pour retrouver le plus court chemin entre deux cases.
     * @param robot
     * @param caseDepart
     * @param caseArrivee
     * @return
     */
    private NoeudAstar executeAstar(Robot robot, Case caseDepart, Case caseArrivee) {

        try {
            robot.checkPosition(caseArrivee, this.carte);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La case d'arrivée n'est pas valide");
        }

        robot.checkPosition(caseDepart, this.carte);

        PriorityQueue<NoeudAstar> queue = new PriorityQueue<>();
        List<Case> casesVisitees = new ArrayList<Case>();

        NoeudAstar noeudDepart = new NoeudAstar(caseArrivee, 0, 0, null);

        queue.add(noeudDepart);

        while (!queue.isEmpty()) {
            NoeudAstar noeudCourant = queue.poll();
            if (noeudCourant.caseNoeud.equals(caseDepart)) {

                return noeudCourant;
            } else {
                for (Direction dir : Direction.values()) {
                    try {
                        if (this.carte.voisinExiste(noeudCourant.caseNoeud, dir)
                                && !casesVisitees.contains(this.carte.getVoisin(noeudCourant.caseNoeud, dir))) {

                            Case voisin = this.carte.getVoisin(noeudCourant.caseNoeud, dir);
                            robot.checkPosition(voisin, this.carte);

                            double cout = noeudCourant.cout
                                    + robot.calculerTempsDeplacementMinute(noeudCourant.caseNoeud, voisin);
                            double heuristique = cout + calculerDistance(noeudCourant.caseNoeud, caseDepart);
                            NoeudAstar noeudVoisin = new NoeudAstar(voisin, cout, heuristique, noeudCourant);

                            queue.add(noeudVoisin);
                            casesVisitees.add(voisin);
                        }
                    }

                    catch (IllegalArgumentException e) {
                        // On ne va pas sur cette case
                    }
                }
            }
        }
        // On n'a pas trouvé de chemin
        System.out.println("Le probleme est dans le parcours");
        return null;
    }
}