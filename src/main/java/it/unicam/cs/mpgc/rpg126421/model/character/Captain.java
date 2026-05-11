package it.unicam.cs.mpgc.rpg126421.model.character;

import it.unicam.cs.mpgc.rpg126421.model.shared.CharacterClass;

/**
 * Il protagonista — il nostro Cowboy.
 * Ha morale variabile in base alle scelte narrative.
 */
public class Captain extends Character {

    private static final int MAX_MORALE  = 100;
    private static final int MIN_MORALE  = 0;
    private static final int STARTING_MORALE = 70;

    private int morale;

    public Captain(String name, CharacterClass characterClass) {
        super(name, characterClass);
        this.morale = STARTING_MORALE;
    }

    public int getMorale() { return morale; }

    public void changeMorale(int delta) {
        this.morale = Math.clamp(this.morale + delta, MIN_MORALE, MAX_MORALE);
    }

    @Override
    public String toString() {
        return "Captain{name='" + getName() + "', class=" + getCharacterClass() +
                ", morale=" + morale + "}";
    }
}