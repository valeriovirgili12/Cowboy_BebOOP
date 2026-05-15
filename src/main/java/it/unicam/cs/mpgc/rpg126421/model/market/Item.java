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
            "Sblocca l'opzione di eliminazione diretta nel finale. Può evitare o causare game over."
    ),
    HELIX_ARCHIVE(
            "Helix Archive",
            1200,
            "Pacchetto dati rubato dalla Helix Corporation.",
            "Potrebbe contenere prove autentiche. O potrebbe essere esca. Non lo saprai finché non sarà troppo tardi."
    );

    private final String displayName;
    private final int cost;
    private final String description;
    private final String effect;

    Item(String displayName, int cost, String description, String effect) {
        this.displayName = displayName;
        this.cost        = cost;
        this.description = description;
        this.effect      = effect;
    }

    public String getDisplayName() { return displayName; }
    public int getCost()           { return cost; }
    public String getDescription() { return description; }
    public String getEffect()      { return effect; }

    @Override
    public String toString() { return displayName; }
}