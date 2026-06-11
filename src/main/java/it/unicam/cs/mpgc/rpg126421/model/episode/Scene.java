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
    private final String characterSprite;
    private final String backgroundSprite;
    private final boolean finalScene;

    // ── Costruttore privato ───────────────────────────────────────────────────

    private Scene(String id, String narrativeText, List<Choice> choices,
                  String characterSprite, String backgroundSprite, boolean finalScene) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Scene id cannot be blank");

        if (choices == null || choices.isEmpty())
            throw new IllegalArgumentException("Scene must have at least one choice");
        this.id               = id;
        this.narrativeText    = narrativeText;
        this.choices          = Collections.unmodifiableList(choices);
        this.characterSprite  = characterSprite;
        this.backgroundSprite = backgroundSprite;
        this.completed        = false;
        this.finalScene       = finalScene;
    }

    // ── Factory methods ───────────────────────────────────────────────────────

    public static Scene of(String id, String narrativeText, List<Choice> choices) {
        return new Scene(id, narrativeText, choices, null, null, false);
    }

    public static Scene withSprites(String id, String narrativeText, List<Choice> choices,
                                    String characterSprite, String backgroundSprite) {
        return new Scene(id, narrativeText, choices, characterSprite, backgroundSprite, false);
    }

    public static Scene finalScene(String id, String narrativeText, List<Choice> choices,
                                   String characterSprite, String backgroundSprite) {
        return new Scene(id, narrativeText, choices, characterSprite, backgroundSprite, true);
    }

    public static Scene intro(String id, String narrativeText,
                              String characterSprite, String backgroundSprite) {
        Choice continua = new Choice.Builder(
                "[ CONTINUA ]",
                new Outcome.Builder().build()
        ).build();
        return new Scene(id, narrativeText, List.of(continua), characterSprite, backgroundSprite, false);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getId()            { return id; }
    public String getNarrativeText() { return narrativeText; }
    public List<Choice> getChoices() { return choices; }
    public boolean isCompleted()     { return completed; }
    public boolean isFinalScene()    { return finalScene; }

    public Optional<String> getCharacterSprite()  { return Optional.ofNullable(characterSprite); }
    public Optional<String> getBackgroundSprite() { return Optional.ofNullable(backgroundSprite); }

    public void complete() { this.completed = true; }

    public void setNarrativeText(String narrativeText) {
        if (narrativeText == null || narrativeText.isBlank())
            throw new IllegalArgumentException("Narrative text cannot be blank");
        this.narrativeText = narrativeText;
    }

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scene scene)) return false;
        return id != null && id.equals(scene.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}