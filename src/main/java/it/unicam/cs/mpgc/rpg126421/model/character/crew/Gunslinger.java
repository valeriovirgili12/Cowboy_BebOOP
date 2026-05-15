package it.unicam.cs.mpgc.rpg126421.model.character.crew;

import it.unicam.cs.mpgc.rpg126421.model.shared.CaptainClass;
import it.unicam.cs.mpgc.rpg126421.model.shared.CrewClass;

/**
 * Membro della crew specializzato nel combattimento.
 * Il suo contributo alle missioni è diretto e aggressivo.
 */
public class Gunslinger extends CrewMember {

    public Gunslinger(String name) {
        super(name, CrewClass.GUNSLINGER);
    }

    /**
     * In missione: copre la ritirata o apre un varco a forza.
     * Il bonus dipende dalla stat strength della sua classe.
     */
    @Override
    public String contributeToMission() {
        int bonus = getCharacterRole().getStrength();
        return getName() + " copre il gruppo con fuoco di copertura [STR: " + bonus + "]";
    }
}