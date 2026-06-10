package it.unicam.cs.mpgc.rpg126421.model.market;

/**
 * Oggetti acquistabili al mercato nero.
 * Ogni item ha un costo, una descrizione e un effetto narrativo.
 */
public enum Item {

    RAVEN(
            "Raven",
            800,
            "Pistola modificata — silenziosa, affidabile, letale.",
            "Sblocca l'opzione di eliminazione diretta nel finale. Può evitare o causare game over.",
            "/it/unicam/cs/mpgc/rpg126421/sprites/ui/raven.png"
    ),
    HELIX_ARCHIVE(
            "Helix Archive",
            1200,
            "Pacchetto dati rubato dalla Helix Corporation.",
            "Potrebbe contenere prove autentiche. O potrebbe essere esca. Non lo saprai finché non sarà troppo tardi.",
            "/it/unicam/cs/mpgc/rpg126421/sprites/ui/helix_archive.png"

    );

    private final String displayName;
    private final int cost;
    private final String description;
    private final String effect;
    private final String imagePath;

    Item(String displayName, int cost, String description, String effect, String imagePath) {
        this.displayName = displayName;
        this.cost        = cost;
        this.description = description;
        this.effect      = effect;
        this.imagePath = imagePath;
    }

    public String getDisplayName() { return displayName; }
    public int getCost()           { return cost; }
    public String getDescription() { return description; }
    public String getEffect()      { return effect; }
    public String getImagePath()   { return imagePath; }

    @Override
    public String toString() { return displayName; }
}