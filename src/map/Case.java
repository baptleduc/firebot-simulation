package map;
public class Case {
    private int ligne;
    private int colonne;
    private NatureTerrain nature;

    public Case(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
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
        return String.format("Case [ligne=%d, colonne=%d]", ligne, colonne);
    }
}
