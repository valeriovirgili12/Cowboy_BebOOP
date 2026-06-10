package it.unicam.cs.mpgc.rpg126421.model.shared;

/**
 * Classi disponibili per i membri della crew e Nyx.
 * Separata da CaptainClass — ruoli diversi, gerarchia diversa.
 */
public enum CrewClass implements CharacterRole {

    GUNSLINGER("Gunslinger", "Fuoco di copertura: +1 opzione offensiva per scena"),
    HACKER    ("Hacker",     "Accesso negato: sblocca opzioni tecniche e stealth");

    private final String displayName;
    private final String perk;

    CrewClass(String displayName, String perk) {
        this.displayName = displayName;
        this.perk        = perk;
    }

    @Override public String getDisplayName() { return displayName; }
    @Override public String getPerk()        { return perk; }
    @Override public String toString() { return displayName; }
}