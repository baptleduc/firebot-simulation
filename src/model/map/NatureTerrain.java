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

    static {
        drawTerrainColors.put(EAU, Color.BLUE);
        drawTerrainColors.put(FORET, Color.GREEN);
        drawTerrainColors.put(ROCHE, Color.DARK_GRAY);
        drawTerrainColors.put(TERRAIN_LIBRE, Color.LIGHT_GRAY);
        drawTerrainColors.put(HABITAT, new Color(150, 75, 0)); // Marron
    }

    public Color getDrawColor() throws IllegalArgumentException {
        Color color = drawTerrainColors.get(this);
        if (color == null) {
            throw new IllegalArgumentException("Terrain inconnu : " + this);
        }
        return color;
    }
}
