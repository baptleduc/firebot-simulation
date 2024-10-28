package map;

public class Carte {
    private final int nbLignes;
    private final int nbColonnes;
    private int tailleCases;
    private Case[][] cases;

    public Carte(int nbLignes, int nbColonnes, int tailleCases){
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCases = tailleCases;
        this.cases= new Case[nbLignes][nbColonnes];
        for(int i = 0; i < nbLignes; i++){
            for(int j=0; j < nbColonnes; j++){
                this.cases[i][j] = new Case(i, j);
            }
        }
    }

    public int getNbLignes(){
        return this.nbLignes; 
    }
    
    public int getNbColonnes(){
        return this.nbColonnes;
    }

    public int getTailleCases(){
        return this.tailleCases;

    }

    public Case getCase(int lig, int col){
        if (!estIndiceValide(lig, col)) {
            throw new IllegalArgumentException("Indices de case invalides : " + lig + ", " + col);
        }
        return this.cases[lig][col];
    }

    public Case getVoisin(Case src, Direction dir){
        int lig = src.getLigne();
        int col = src.getColonne();
        switch (dir) {
            case NORD -> lig --;
            case SUD -> lig ++;
            case EST -> col ++;
            case OUEST -> col --;
            default -> throw new IllegalArgumentException("Direction inconnue : " + dir);
        }

        return this.getCase(lig, col);
    }

    public boolean voisinExiste(Case src, Direction dir) {
        int lig = src.getLigne();
        int col = src.getColonne();

        switch (dir) {
            case NORD -> lig--;
            case SUD -> lig++;
            case EST -> col++;
            case OUEST -> col--;
        }
        return estIndiceValide(lig, col);
    }
    
    public boolean caseExiste(Case src){
        int lig = src.getLigne();
        int col = src.getColonne();

        return estIndiceValide(lig, col);
    }

    public boolean estVoisin(Case src, Case dest) {
        int srcLigne = src.getLigne();
        int destLigne = dest.getColonne();

        int srcColonne = src.getColonne();
        int destColonne = dest.getColonne();

        boolean estVoisin = (srcLigne == destLigne && Math.abs(srcColonne - destColonne) == 1) || // Voisins à l'est ou à l'ouest
        (srcColonne == destColonne && Math.abs(srcLigne - destLigne) == 1); // Voisins au nord ou au sud

        return estVoisin;    
    }

    public boolean estIndiceValide(int lig, int col){
        return lig >= 0 && lig < nbLignes && col >= 0 && col < nbColonnes;
    }
    


    


}
