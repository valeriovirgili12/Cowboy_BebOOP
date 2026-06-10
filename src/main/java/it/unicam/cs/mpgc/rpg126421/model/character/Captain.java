package it.unicam.cs.mpgc.rpg126421.model.character;

import it.unicam.cs.mpgc.rpg126421.model.shared.CaptainClass;

/**
 * Il protagonista — il nostro Cowboy.
 * Ha morale variabile in base alle scelte narrative.
 */
public class Captain extends Character<CaptainClass> {

    private static final int MAX_MORALE  = 100;
    private static final int MIN_MORALE  = 0;
    private static final int STARTING_MORALE = 70;

/*Puramente statistico, non influenza scelte e outcome, per ora*/
    private int morale;

    public Captain(String name, CaptainClass captainClass) {
        super(name, captainClass);
        this.morale = STARTING_MORALE;
    }

    public int getMorale() { return morale; }

    // getter specifico opzionale (utile nel controller)
    public CaptainClass getCaptainClass() { return (CaptainClass) getCharacterRole(); }

    public void changeMorale(int delta) {
        this.morale = Math.clamp(this.morale + delta, MIN_MORALE, MAX_MORALE);
    }

    @Override
    public String toString() {
        return "Captain{name='" + getName() + "', class=" + getCharacterRole() +
                ", morale=" + morale + "}";
    }
}