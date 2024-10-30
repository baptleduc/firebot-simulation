package model.map;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public enum NatureTerrain {
    EAU,
    FORET,
    ROCHE,
    TERRAIN_LIBRE,
    HABITAT;

    // Map pour stocker les couleurs associées à chaque type de terrain
    private static final Map<NatureTerrain, Color> drawTerrainColors = new HashMap<>();

    // Bloc d'initialisation statique pour remplir la map avec les couleurs associées
    static {
        drawTerrainColors.put(EAU, Color.BLUE);
        drawTerrainColors.put(FORET, Color.GREEN);
        drawTerrainColors.put(ROCHE, Color.DARK_GRAY);
        drawTerrainColors.put(TERRAIN_LIBRE, Color.LIGHT_GRAY);
        drawTerrainColors.put(HABITAT, new Color(150, 75, 0)); // Marron
    }

    /**
     * Retourne la couleur associée au type de terrain.
     *
     * @return La couleur associée au type de terrain
     * @throws IllegalArgumentException si le type de terrain est inconnu
     */
    public Color getDrawColor() {
        Color color = drawTerrainColors.get(this);
        if (color == null) {
            throw new IllegalArgumentException("Terrain inconnu : " + this);
        }
        return color;
    }
}
