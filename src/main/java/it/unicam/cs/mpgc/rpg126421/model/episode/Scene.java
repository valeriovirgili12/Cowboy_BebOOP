package it.unicam.cs.mpgc.rpg126421.model.episode;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Una scena narrativa all'interno di un episodio.
 * Contiene il testo descrittivo e le scelte disponibili al giocatore.
 */
public class Scene {

    private final String id;
    private String narrativeText;
    private final List<Choice> choices;
    private boolean completed;
    private final String characterSprite; // null = nessun personaggio
    private final String backgroundSprite; // null = nessuno sfondo
    private final boolean finalScene;


    public Scene(String id, String narrativeText, List<Choice> choices) {
        this(id, narrativeText, choices, null, null);
    }

    // nuovo costruttore completo
    public Scene(String id, String narrativeText, List<Choice> choices,
                 String characterSprite, String backgroundSprite) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Scene id cannot be blank");
        if (narrativeText == null || narrativeText.isBlank())
            throw new IllegalArgumentException("Narrative text cannot be blank");
        if (choices == null || choices.isEmpty())
            throw new IllegalArgumentException("Scene must have at least one choice");
        this.id              = id;
        this.narrativeText   = narrativeText;
        this.choices         = Collections.unmodifiableList(choices);
        this.characterSprite = characterSprite;
        this.backgroundSprite = backgroundSprite;
        this.completed       = false;
        this.finalScene      = false;

    }
    // costruttore con flag finale
    public Scene(String id, String narrativeText, List<Choice> choices,
                 String characterSprite, String backgroundSprite,
                 boolean finalScene) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Scene id cannot be blank");
        if (narrativeText == null || narrativeText.isBlank())
            throw new IllegalArgumentException("Narrative text cannot be blank");
        if (choices == null || choices.isEmpty())
            throw new IllegalArgumentException("Scene must have at least one choice");
        this.id              = id;
        this.narrativeText   = narrativeText;
        this.choices         = Collections.unmodifiableList(choices);
        this.characterSprite = characterSprite;
        this.backgroundSprite = backgroundSprite;
        this.completed       = false;
        this.finalScene = finalScene;

    }

    public String getId()            { return id; }
    public String getNarrativeText() { return narrativeText; }
    public void setNarrativeText(String narrativeText) {
        if (narrativeText == null || narrativeText.isBlank())
            throw new IllegalArgumentException("Narrative text cannot be blank");
        this.narrativeText = narrativeText;
    }
    public List<Choice> getChoices() { return choices; }
    public boolean isCompleted()     { return completed; }

    public void complete() { this.completed = true; }
    public Optional<String> getCharacterSprite()  { return Optional.ofNullable(characterSprite); }
    public Optional<String> getBackgroundSprite() { return Optional.ofNullable(backgroundSprite); }
    /**
     * Restituisce solo le scelte disponibili nella sessione corrente.
     */
    public List<Choice> getAvailableChoices(
            it.unicam.cs.mpgc.rpg126421.model.session.GameSession session) {
        return choices.stream()
                .filter(c -> c.isAvailable(session))
                .toList();
    }

    public boolean isFinalScene() { return finalScene; }

    @Override
    public String toString() {
        return "Scene{id='" + id + "', completed=" + completed + "}";
    }
}