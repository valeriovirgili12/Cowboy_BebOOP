package it.unicam.cs.mpgc.rpg126421.model.shared;

/**
 * Classi disponibili per i membri della crew e Nyx.
 * Separata da CaptainClass — ruoli diversi, gerarchia diversa.
 */
public enum CrewClass implements CharacterRole{

    GUNSLINGER("Gunslinger", 8, 3, 4, "Fuoco di copertura: +1 opzione offensiva per scena"),
    HACKER    ("Hacker",     3, 8, 5, "Accesso negato: sblocca opzioni tecniche e stealth");

    private final String displayName;
    private final int strength;
    private final int tech;
    private final int resilience;
    private final String perk;

    CrewClass(String displayName, int strength, int tech, int resilience, String perk) {
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