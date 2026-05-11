package it.unicam.cs.mpgc.rpg126421.model.episode.requirement;

import it.unicam.cs.mpgc.rpg126421.model.episode.Requirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CharacterClass;

/**
 * Soddisfatta se nella crew c'è almeno un membro della classe richiesta.
 */
public class CrewClassRequirement implements Requirement {

    private final CharacterClass required;

    public CrewClassRequirement(CharacterClass required) {
        this.required = required;
    }

    @Override
    public boolean isMet(GameSession session) {
        return session.getCrew().stream()
                .anyMatch(m -> m.getCharacterClass() == required);
    }

    @Override
    public String getHint() {
        return "Serve un " + required.getDisplayName() + " nella crew";
    }
}