package it.unicam.cs.mpgc.rpg126421.model.character.crew;

import it.unicam.cs.mpgc.rpg126421.model.shared.CharacterClass;

/**
 * Membro della crew specializzato in tecnologia e infiltrazione digitale.
 * Il suo contributo alle missioni bypassa i sistemi di sicurezza.
 */
public class Hacker extends CrewMember {

    public Hacker(String name) {
        super(name, CharacterClass.HACKER);
    }

    /**
     * In missione: disabilita sistemi di sicurezza o recupera informazioni.
     * Il bonus dipende dalla stat tech della sua classe.
     */
    @Override
    public String contributeToMission() {
        int bonus = getCharacterClass().getTech();
        return getName() + " neutralizza i sistemi di sicurezza [TECH: " + bonus + "]";
    }
}