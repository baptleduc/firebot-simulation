package model.robot;
import model.map.*;

public abstract class RobotTerrestre extends Robot{
    
    public RobotTerrestre(Case position, int niveauEau, int capaciteReservoir, double vitesse, double vitesseMax, int tempsRemplissage, Carte carte, int interUnitaire ){
        super(position, niveauEau, capaciteReservoir, vitesse, vitesseMax, tempsRemplissage, carte, interUnitaire);
        
    }
    
    public void remplirReservoir(){
        Case posCourante = getPosition();
    }
    
}
