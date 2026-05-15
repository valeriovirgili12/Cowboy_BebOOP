package it.unicam.cs.mpgc.rpg126421.model.character;

import it.unicam.cs.mpgc.rpg126421.model.shared.CaptainClass;
import it.unicam.cs.mpgc.rpg126421.model.shared.CharacterRole;

/**
 * Classe astratta base per tutti i personaggi del gioco.
 * Definisce nome e classe — comuni a Captain e CrewMember.
 */
public abstract class Character {

    private final String name;
    private final CharacterRole characterRole;

    protected Character(String name, CharacterRole characterRole) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be blank");
        this.name           = name;
        this.characterRole = characterRole;
    }

    public String getName()                   { return name; }
    public CharacterRole getCharacterRole() { return characterRole; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character other)) return false;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{name='" + name + "', class=" + characterRole + "}";
    }
}
