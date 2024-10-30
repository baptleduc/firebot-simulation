package model.map;
public class Case {
    private int ligne;
    private int colonne;
    private NatureTerrain nature;

    public Case(int ligne, int colonne, NatureTerrain natureTerrain){
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = natureTerrain;
    }

    public int getLigne(){
        return this.ligne;
    }

    public int getColonne(){
        return this.colonne;
    }
    
    public NatureTerrain getNature(){
        return this.nature;
    }

    @Override
    public String toString() {
        return String.format("Case (%d, %d) : nature %s\n", ligne, colonne, getNature().name());
    }
}
