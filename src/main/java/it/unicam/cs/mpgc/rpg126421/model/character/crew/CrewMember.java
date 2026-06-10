package it.unicam.cs.mpgc.rpg126421.model.character.crew;

import it.unicam.cs.mpgc.rpg126421.model.character.Character;
import it.unicam.cs.mpgc.rpg126421.model.shared.CrewClass;

/**
 * Classe astratta per tutti i membri reclutabili della crew.
 * Ogni membro ha un livello di fiducia verso il capitano
 * e contribuisce alle missioni in modo unico.
 *
 */
public abstract class CrewMember extends Character<CrewClass> {

    private static final int MAX_TRUST     = 100;
    private static final int MIN_TRUST     = 0;
    private static final int STARTING_TRUST = 50;

/*Il sistema di trust non è implementato per i primi tre episodi
  per quanto riguarda i CrewMember; lo utilizza solo Marcus.
  Lasciato per implementazioni future
 */
    private int trust;

    protected CrewMember(String name, CrewClass crewClass) {
        super(name, crewClass);
        this.trust = STARTING_TRUST;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{name='" + getName() + "', class=" + getCharacterRole() +
                ", trust=" + trust + "}";
    }
}