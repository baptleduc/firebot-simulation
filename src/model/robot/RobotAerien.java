package model.robot;

import model.map.*;

public abstract class RobotAerien extends Robot {
    
    public RobotAerien(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax, int tempsRemplissage, Carte carte, int interUnitaire ){
        super(position, niveauEau, capaciteReservoir, vitesse, vitesseMax, tempsRemplissage, carte, interUnitaire);
        
    }
    
    
    public void remplirReservoir(){
        Case posCourante = getPosition();
        
        NatureTerrain terrain = posCourante.getNature();
        if (terrain == NatureTerrain.EAU){
            System.out.println("Le robot aerien se rempli");
        }
        else{
            System.out.println("Le robot aérien ne peut pas se remplir car il n'est pas sur une case d'eau !");
        }
    }
}
