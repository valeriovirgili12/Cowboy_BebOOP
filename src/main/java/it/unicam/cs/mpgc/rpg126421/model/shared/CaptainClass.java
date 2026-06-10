package it.unicam.cs.mpgc.rpg126421.model.shared;

/**
 * Classi disponibili per il protagonista e i crew member.
 * Ogni classe ha un perk descrittivo.
 */
public enum CaptainClass implements CharacterRole{

    BOUNTY_HUNTER  ("Bounty Hunter", 6, 5, 4, "Naso per le taglie: +10% woolong da ogni bounty"),
    HACKER         ("Hacker",        3, 8, 5, "Accesso negato: sblocca opzioni di dialogo tecniche"),
    GUNSLINGER     ("Gunslinger",    8, 3, 4, "Grilletto facile: +1 opzione offensiva per episodio"),
    MECHANIC       ("Mechanic",      4, 5, 7, "Duct tape & dreams: riduce costi di riparazione nave");

    private final String displayName;
    private final String perk;

    CaptainClass(String displayName, int strength, int tech, int resilience, String perk) {
        this.displayName = displayName;
        this.perk        = perk;
    }

    public String getDisplayName() { return displayName; }
    public String getPerk()        { return perk; }

    @Override
    public String toString() { return displayName; }
}