package it.unicam.cs.mpgc.rpg126421.model.episode;

import java.util.Collections;
import java.util.List;

/**
 * Classe astratta base per un episodio del gioco.
 * Ogni episodio ha un titolo, una sequenza di scene e un proprio stato.
 * Le sottoclassi concrete definiscono le scene e la logica narrativa.
 */
public abstract class Episode {

    private final String id;
    private final String title;
    private final List<Scene> scenes;
    private EpisodeStatus status;

    protected Episode(String id, String title, List<Scene> scenes) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Episode id cannot be blank");
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Episode title cannot be blank");
        if (scenes == null || scenes.isEmpty())
            throw new IllegalArgumentException("Episode must have at least one scene");
        this.id     = id;
        this.title  = title;
        this.scenes = Collections.unmodifiableList(scenes);
        this.status = EpisodeStatus.NOT_STARTED;
    }

    public String getId()          { return id; }
    public String getTitle()       { return title; }
    public List<Scene> getScenes() { return scenes; }
    public EpisodeStatus getStatus() { return status; }

    public void start()    { this.status = EpisodeStatus.IN_PROGRESS; }
    public void complete() { this.status = EpisodeStatus.COMPLETED; }

    public boolean isCompleted()  { return status == EpisodeStatus.COMPLETED; }
    public boolean isNotStarted() { return status == EpisodeStatus.NOT_STARTED; }

    /**
     * Restituisce la prossima scena non completata, se esiste.
     */
    public java.util.Optional<Scene> getNextScene() {
        return scenes.stream()
                .filter(s -> !s.isCompleted())
                .findFirst();
    }

    /**
     * Logica di inizializzazione specifica dell'episodio.
     * Le sottoclassi la usano per costruire scene e scelte.
     */
    public abstract void initialize(
            it.unicam.cs.mpgc.rpg126421.model.session.GameSession session);

    //metodo helper
    protected Scene findScene(String id) {
        return getScenes().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Scene not found: " + id));
    }
    @Override
    public String toString() {
        return "Episode{id='" + id + "', title='" + title + "', status=" + status + "}";
    }
}