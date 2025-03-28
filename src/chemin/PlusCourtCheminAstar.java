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

    /**
     * Constructeur de la classe NoeudAstar
     * 
     * @param caseNoeud       la case du noeud
     * @param cout            le cout du noeud
     * @param heuristique     l'heuristique du noeud
     * @param noeudPrecedente le noeud précédent
     */
    public NoeudAstar(Case caseNoeud, double cout, double heuristique, NoeudAstar noeudPrecedente) {
        this.caseNoeud = caseNoeud;
        this.cout = cout;
        this.heuristique = heuristique;
        this.noeudPrecedent = noeudPrecedente;
    }

    @Override
    public int compareTo(NoeudAstar Noeud2) {
        double h1;
        double h2;
        h1 = this.heuristique;
        h2 = Noeud2.heuristique;
        if (h1 < h2) {
            return -1;
        }
        if (h1 > h2) {
            return 1;
        }
        return 0;
    }

}

public class PlusCourtCheminAstar implements PlusCourtChemin {

    private Carte carte;

    /**
     * Constructeur de la classe PlusCourtCheminAstar
     * 
     * @param carte la carte associée
     */
    public PlusCourtCheminAstar(Carte carte) {
        this.carte = carte;
    }

    /**
     * Calcule la distance entre deux cases
     * 
     * @param case1 la première case
     * @param case2 la deuxième case
     * @return la distance entre les deux cases
     */
    private static double calculerDistance(Case case1, Case case2) {
        return Math.abs(case1.getLigne() - case2.getLigne()) + Math.abs(case1.getColonne() - case2.getColonne());
    }

    /**
     * Calcule le temps de déplacement entre deux cases pour un robot donné.
     * une erreur est levée si le robot ne peut pas effectuer le déplacement.
     */
    public double tempsDeplacement(Robot robot, Case caseDepart, Case caseArrivee) {
        NoeudAstar ndAstar = executeAstar(robot, caseDepart, caseArrivee);
        if (ndAstar == null) {
            throw new IllegalArgumentException("Le robot ne peut pas effectuer ce déplacement");
        }
        return ndAstar.cout;
    }

    /**
     * Crée un chemin entre deux cases pour un robot donné.
     * 
     * @param robot       le robot associé
     * @param caseDepart  la case de départ
     * @param caseArrivee la case d'arrivée
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
     * Execute l'algorithme A* pour trouver le plus court chemin entre deux cases
     * 
     * @param robot       le robot associé
     * @param caseDepart  la case de départ
     * @param caseArrivee la case d'arrivée
     * @return le noeud d'arrivée
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
        return null;
    }
}