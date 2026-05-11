package it.unicam.cs.mpgc.rpg126421.model.character.crew;

import it.unicam.cs.mpgc.rpg126421.model.character.Character;
import it.unicam.cs.mpgc.rpg126421.model.shared.CharacterClass;

/**
 * Classe astratta per tutti i membri reclutabili della crew.
 * Ogni membro ha un livello di fiducia verso il capitano
 * e contribuisce alle missioni in modo unico.
 */
public abstract class CrewMember extends Character {

    private static final int MAX_TRUST     = 100;
    private static final int MIN_TRUST     = 0;
    private static final int STARTING_TRUST = 50;

    private int trust;

    protected CrewMember(String name, CharacterClass characterClass) {
        super(name, characterClass);
        this.trust = STARTING_TRUST;
    }

    public int getTrust() { return trust; }

    public void changeTrust(int delta) {
        this.trust = Math.clamp(this.trust + delta, MIN_TRUST, MAX_TRUST);
    }

    /**
     * Contributo unico alla missione — implementato da ogni sottoclasse.
     * Restituisce una stringa descrittiva dell'azione compiuta.
     */
    public abstract String contributeToMission();

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{name='" + getName() + "', class=" + getCharacterClass() +
                ", trust=" + trust + "}";
    }
}