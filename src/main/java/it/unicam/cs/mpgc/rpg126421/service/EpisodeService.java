package it.unicam.cs.mpgc.rpg126421.service;

import it.unicam.cs.mpgc.rpg126421.model.episode.Choice;
import it.unicam.cs.mpgc.rpg126421.model.episode.Episode;
import it.unicam.cs.mpgc.rpg126421.model.episode.Outcome;
import it.unicam.cs.mpgc.rpg126421.model.episode.Scene;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.List;
import java.util.Optional;

/**
 * Gestisce l'avanzamento degli episodi e delle scene.
 * Responsabilità unica: logica di progressione narrativa.
 */
public class EpisodeService {

    private final GameSession session;
    private final OutcomeService outcomeService;
    private final CrewService crewService;
    private boolean gameOver = false;

    public EpisodeService(GameSession session,
                          OutcomeService outcomeService,
                          CrewService crewService) {
        this.session        = session;
        this.outcomeService = outcomeService;
        this.crewService    = crewService;
    }

    /**
     * Avvia il prossimo episodio non ancora iniziato.
     */
    public Optional<Episode> startNextEpisode() {
        return session.getEpisodes().stream()
                .filter(Episode::isNotStarted)
                .findFirst()
                .map(e -> {
                    e.initialize(session);
                    e.start();
                    return e;
                });
    }

    /**
     * Completa l'episodio corrente se tutte le scene sono completate.
     */
    public boolean tryCompleteCurrentEpisode() {
        return session.getCurrentEpisode()
                .filter(e -> e.getScenes().stream()
                        .allMatch(it.unicam.cs.mpgc.rpg126421.model.episode.Scene::isCompleted))
                .map(e -> {
                    e.complete();
                    return true;
                })
                .orElse(false);
    }

    /**
     * Restituisce la scena corrente dell'episodio attivo.
     */
    public Optional<Scene> getCurrentScene() {
        return session.getCurrentEpisode()
                .flatMap(Episode::getNextScene);
    }

    /**
     * Restituisce le scelte disponibili nella scena corrente.
     */
    public List<Choice> getAvailableChoices(Scene scene) {
        return scene.getAvailableChoices(session);
    }

    /**
     * Risolve una scena applicando la scelta del giocatore.
     * Gestisce successo e fallimento, aggiorna log e crew.
     */
    public void resolveScene(Scene scene, Choice choice) {
        boolean failed = choice.willFail(session);

        Outcome outcome = failed && choice.getOutcome().hasFailureOutcome()
                ? choice.getOutcome().getFailureOutcome()
                : choice.getOutcome();

        boolean causedGameOver = outcomeService.apply(outcome);
        if (causedGameOver) gameOver = true;

        // reclutamento Nyx automatico dopo flag
        if ("true".equals(session.getWorldState().getFlag("recruitedNyx"))
                && crewService.getNyx().isEmpty()) {
            crewService.recruitNyx();
        }

        scene.complete();
        session.getNarrativeLog().add(choice.getLogEntry());
    }

    public boolean isGameOver() { return gameOver; }
}