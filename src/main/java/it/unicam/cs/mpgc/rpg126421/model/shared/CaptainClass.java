package it.unicam.cs.mpgc.rpg126421.model.shared;

/**
 * Classi disponibili per il protagonista e i crew member.
 * Ogni classe ha stat di base e un perk descrittivo.
 */
public enum CaptainClass implements CharacterRole{

    BOUNTY_HUNTER  ("Bounty Hunter", 6, 5, 4, "Naso per le taglie: +10% woolong da ogni bounty"),
    HACKER         ("Hacker",        3, 8, 5, "Accesso negato: sblocca opzioni di dialogo tecniche"),
    GUNSLINGER     ("Gunslinger",    8, 3, 4, "Grilletto facile: +1 opzione offensiva per episodio"),
    MECHANIC       ("Mechanic",      4, 5, 7, "Duct tape & dreams: riduce costi di riparazione nave");

    private final String displayName;
    private final int strength;    // forza / combattimento
    private final int tech;        // tecnologia / hacking
    private final int resilience;  // resistenza / sopravvivenza
    private final String perk;

    CaptainClass(String displayName, int strength, int tech, int resilience, String perk) {
        this.displayName = displayName;
        this.strength    = strength;
        this.tech        = tech;
        this.resilience  = resilience;
        this.perk        = perk;
    }

    public String getDisplayName() { return displayName; }
    public int getStrength()       { return strength; }
    public int getTech()           { return tech; }
    public int getResilience()     { return resilience; }
    public String getPerk()        { return perk; }

    @Override
    public String toString() { return displayName; }
}