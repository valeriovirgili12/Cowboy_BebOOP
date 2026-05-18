package it.unicam.cs.mpgc.rpg126421.model.character.crew;

import it.unicam.cs.mpgc.rpg126421.model.shared.CrewClass;

/**
 * Nyx — freelancer legata alla Helix Corporation.
 * Membro opzionale della crew, complementare alla classe del capitano.
 * La sua lealtà dipende dalle scelte del giocatore nell'episodio 2.
 *
 * Se il capitano è Gunslinger → Nyx è Hacker (e viceversa).
 */
public class Nyx extends CrewMember {

    private boolean loyal;

    public Nyx(CrewClass crewClass) {
        super("Nyx", crewClass);
        this.loyal = true;
    }

    public boolean isLoyal()         { return loyal; }
    public void setLoyal(boolean loyal) { this.loyal = loyal; }

    /**
     * Contributo in missione — varia in base alla classe assegnata.
     */
    @Override
    public String contributeToMission() {
        return switch (getCrewClass()) {
            case HACKER    -> "Nyx neutralizza i sistemi Helix dall'interno [TECH: "
                    + getCrewClass().getTech() + "]";
            case GUNSLINGER -> "Nyx copre l'avanzata con fuoco di precisione [STR: "
                    + getCrewClass().getStrength() + "]";
        };
    }

    @Override
    public String toString() {
        return "Nyx{class=" + getCrewClass() + ", loyal=" + loyal +
                ", trust=" + getTrust() + "}";
    }
}