package it.unicam.cs.mpgc.rpg126421.model.episode;

import java.util.Collections;
import java.util.List;

/**
 * Una scena narrativa all'interno di un episodio.
 * Contiene il testo descrittivo e le scelte disponibili al giocatore.
 */
public class Scene {

    private final String id;
    private final String narrativeText;
    private final List<Choice> choices;
    private boolean completed;

    public Scene(String id, String narrativeText, List<Choice> choices) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Scene id cannot be blank");
        if (narrativeText == null || narrativeText.isBlank())
            throw new IllegalArgumentException("Narrative text cannot be blank");
        if (choices == null || choices.isEmpty())
            throw new IllegalArgumentException("Scene must have at least one choice");
        this.id            = id;
        this.narrativeText = narrativeText;
        this.choices       = Collections.unmodifiableList(choices);
        this.completed     = false;
    }

    public String getId()            { return id; }
    public String getNarrativeText() { return narrativeText; }
    public List<Choice> getChoices() { return choices; }
    public boolean isCompleted()     { return completed; }

    public void complete() { this.completed = true; }

    /**
     * Restituisce solo le scelte disponibili nella sessione corrente.
     */
    public List<Choice> getAvailableChoices(
            it.unicam.cs.mpgc.rpg126421.model.session.GameSession session) {
        return choices.stream()
                .filter(c -> c.isAvailable(session))
                .toList();
    }

    @Override
    public String toString() {
        return "Scene{id='" + id + "', completed=" + completed + "}";
    }
}