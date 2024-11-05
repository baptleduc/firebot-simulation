package chemin;


import chemin.PlusCourtChemin;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.robot.Robot;
import simu.Simulateur;
import model.map.Carte;
import model.map.Case;
import model.map.Direction;

class NoeudAstar {

    public Case caseNoeud;
    public double cout;
    public double heuristique;
    public NoeudAstar noeudPrecedente;

    public NoeudAstar(Case caseNoeud, double cout, double heuristique, NoeudAstar noeudPrecedente) {
        this.caseNoeud = caseNoeud;
        this.cout = cout;
        this.heuristique = heuristique;
        this.noeudPrecedente = noeudPrecedente;
    }
    

}

class comparateur implements Comparator<NoeudAstar> {
    public int compare(NoeudAstar Noeud1, NoeudAstar Noeud2)
    {
        double h_1;
        double h_2;
        h_1 = Noeud1.heuristique ;
        h_2 = Noeud2.heuristique;
        if(h_1 < h_2)
        {
            return -1;
        }
        if(h_1 > h_2)
        {
            return 1;
        }
        return 0;
    }

}

public class PlusCourtCheminAstar extends PlusCourtChemin {


    private static double calculerDistance(Case case1, Case case2) {
        return Math.abs(case1.getLigne() - case2.getLigne()) + Math.abs(case1.getColonne() - case2.getColonne());
    }

    public static double tempsDeplacement(Robot robot, Carte carte, Case caseDepart, Case caseArrivee){
        return executeAstar(robot, carte, caseDepart, caseArrivee).cout;
    }

    public static List<Case> chemin(Robot robot, Carte carte, Case caseDepart, Case caseArrivee){
        NoeudAstar noeud = executeAstar(robot, carte, caseDepart, caseArrivee);
        
        // on eneleve le noeud de depart
        if(noeud == null){
            return null;
        }
        noeud = noeud.noeudPrecedente;

        List<Case> chemin = new ArrayList<Case>();
        while (noeud != null){
            chemin.add(noeud.caseNoeud);
            noeud = noeud.noeudPrecedente;
        }
        return chemin;
    }

    private static NoeudAstar executeAstar(Robot robot, Carte carte, Case caseDepart, Case caseArrivee){


        try{
            robot.checkPosition(caseArrivee, carte);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException("La case d'arrivée n'est pas valide");
        }

        robot.checkPosition(caseDepart, carte);


        PriorityQueue<NoeudAstar> queue = new PriorityQueue<NoeudAstar>(new comparateur());
        List<Case> casesVisitees = new ArrayList<Case>();
        
        NoeudAstar noeudDepart = new NoeudAstar( caseArrivee, 0, 0, null);


        
        queue.add(noeudDepart);
            
            while (!queue.isEmpty()){
                NoeudAstar noeudCourant = queue.poll();
                if (noeudCourant.caseNoeud.equals(caseDepart)){
                    
                    return noeudCourant;
                }
                else{
                    for (Direction dir : Direction.values()){
                        try{
                        if (carte.voisinExiste(noeudCourant.caseNoeud, dir) && !casesVisitees.contains(carte.getVoisin(noeudCourant.caseNoeud, dir))){
                            
                                Case voisin = carte.getVoisin(noeudCourant.caseNoeud, dir);
                                robot.checkPosition(voisin,carte);
                                
                                double cout = noeudCourant.cout + robot.calculerTempsDeplacementMinute(noeudCourant.caseNoeud, voisin);
                                double heuristique = cout + calculerDistance(noeudCourant.caseNoeud, caseDepart);
                                NoeudAstar noeudVoisin = new NoeudAstar(voisin, cout, heuristique, noeudCourant);

                                queue.add(noeudVoisin);
                                casesVisitees.add(voisin);
                            } 
                        }

                        catch (IllegalArgumentException e){
                            // On ne va pas sur cette case
                        }   
                    }
                }
            }
        // On n'a pas trouvé de chemin
        return null;
    }
}