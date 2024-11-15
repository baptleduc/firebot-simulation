package model.map;

/**
 * Classe représentant la carte composée d'une matrice de cases.
 */
public class Carte {
    private final int nbLignes;
    private final int nbColonnes;
    private int tailleCases;
    private Case[][] cases;

    /**
     * Constructeur de la classe Carte.
     * 
     * @param nbLignes    le nombre de lignes de la carte.
     * @param nbColonnes  le nombre de colonnes de la carte.
     * @param tailleCases la taille des cases de la carte.
     * @param cases       les cases de la carte.
     */
    public Carte(int nbLignes, int nbColonnes, int tailleCases, Case[][] cases) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCases = tailleCases;
        this.cases = cases;
    }

    public int getNbLignes() {
        return this.nbLignes;
    }

    public int getNbColonnes() {
        return this.nbColonnes;
    }

    public int getTailleCases() {
        return this.tailleCases;

    }

    /**
     * Retourne la case située aux indices donnés.
     * 
     * @param lig la ligne de la case.
     * @param col la colonne de la case.
     * @return la case située aux indices donnés.
     * @throws IllegalArgumentException
     */
    public Case getCase(int lig, int col) throws IllegalArgumentException {
        if (!estIndiceValide(lig, col)) {
            throw new IllegalArgumentException("Indices de case invalides : " + lig + ", " + col);
        }
        return this.cases[lig][col];
    }

    /**
     * Retourne la case voisine de la case source dans la direction donnée.
     * 
     * @param src la case source.
     * @param dir la direction du voisin.
     * @return la case voisine.
     * @throws IllegalArgumentException
     */
    public Case getVoisin(Case src, Direction dir) throws IllegalArgumentException {
        int lig = src.getLigne();
        int col = src.getColonne();
        switch (dir) {
            case NORD:
                lig--;
                break;
            case SUD:
                lig++;
                break;
            case EST:
                col++;
                break;
            case OUEST: 
                col--;
                break;
            default :
                throw new IllegalArgumentException("Direction inconnue : " + dir);
        }

        return this.getCase(lig, col);
    }

    /**
     * Vérifie si une case possède un voisin dans la direction spécifiée.
     *
     * @param src la case source.
     * @param dir la direction à vérifier.
     * @return true si un voisin existe dans la direction donnée, false sinon.
     */
    public boolean voisinExiste(Case src, Direction dir) {
        int lig = src.getLigne();
        int col = src.getColonne();

        switch (dir) {
            case NORD:
                lig--;
                break;
            case SUD:
                lig++;
                break;
            case EST:
                col++;
                break;
            case OUEST :
                col--;
                break;
        }
        return estIndiceValide(lig, col);
    }

    /**
     * Vérifie si la case donnée existe dans la carte.
     *
     * @param src la case à vérifier.
     * @return true si la case existe, false sinon.
     */
    public boolean caseExiste(Case src) {
        int lig = src.getLigne();
        int col = src.getColonne();

        return src == this.getCase(lig, col);
    }

    /**
     * Vérifie si deux cases sont voisines (adjacentes).
     *
     * @param src  la case source.
     * @param dest la case de destination.
     * @return true si les cases sont adjacentes, false sinon.
     */
    public boolean estVoisin(Case src, Case dest) {
        int srcLigne = src.getLigne();
        int srcColonne = src.getColonne();

        int destLigne = dest.getLigne();
        int destColonne = dest.getColonne();

        boolean estVoisin = (srcLigne == destLigne && Math.abs(srcColonne - destColonne) == 1) || // Voisins à l'est ou
                                                                                                  // à l'ouest
                (srcColonne == destColonne && Math.abs(srcLigne - destLigne) == 1); // Voisins au nord ou au sud

        return estVoisin;
    }

    /**
     * Vérifie si les indices fournis sont valides pour accéder aux cases de la
     * carte.
     *
     * @param lig l'indice de la ligne.
     * @param col l'indice de la colonne.
     * @return true si les indices sont dans les limites de la carte, false sinon.
     */
    public boolean estIndiceValide(int lig, int col) {
        return lig >= 0 && lig < nbLignes && col >= 0 && col < nbColonnes;
    }

    @Override
    public String toString() {
        String result = "Carte:\n";

        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < this.nbColonnes; j++) {
                result += cases[i][j].toString();
            }
        }

        return result;
    }

}
