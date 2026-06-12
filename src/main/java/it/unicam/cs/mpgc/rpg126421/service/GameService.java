package it.unicam.cs.mpgc.rpg126421.service;

import it.unicam.cs.mpgc.rpg126421.model.episode.Choice;
import it.unicam.cs.mpgc.rpg126421.model.episode.Episode;
import it.unicam.cs.mpgc.rpg126421.model.episode.Outcome;
import it.unicam.cs.mpgc.rpg126421.model.episode.Scene;
import it.unicam.cs.mpgc.rpg126421.model.market.Item;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.List;
import java.util.Optional;

/**
 * Orchestratore principale dei service di gioco.
 * Unico punto di accesso per i controller.
 * Non contiene logica diretta — delega ai service specializzati.
 */
public class GameService {

    private final GameSession session;
    private final OutcomeService outcomeService;
    private final FinanceService financeService;
    private final CrewService crewService;
    private final EpisodeService episodeService;

    public GameService(GameSession session) {
        if (session == null) throw new IllegalArgumentException("Session cannot be null");
        this.session        = session;
        this.outcomeService = new OutcomeService(session);
        this.financeService = new FinanceService(session);
        this.crewService    = new CrewService(session);
        this.episodeService = new EpisodeService(session, outcomeService, crewService);
    }

    // ── Sessione ─────────────────────────────────────────────────────────────

    public GameSession getSession() { return session; }

    // ── Episodi ──────────────────────────────────────────────────────────────

    public Optional<Episode> startNextEpisode() {
        return episodeService.startNextEpisode();
    }

    public boolean tryCompleteCurrentEpisode() {
        return episodeService.tryCompleteCurrentEpisode();
    }

    public Optional<Scene> getCurrentScene() {
        return episodeService.getCurrentScene();
    }

    public List<Choice> getAvailableChoices(Scene scene) {
        return episodeService.getAvailableChoices(scene);
    }

    public Outcome resolveScene(Scene scene, Choice choice) { return episodeService.resolveScene(scene, choice); }



    // ── Finance ──────────────────────────────────────────────────────────────

    public boolean applyFixedCosts(int amount) {
        return financeService.applyFixedCosts(amount);
    }

    public boolean buyItem(Item item) {
        boolean paid = financeService.buyItem(item);
        if (paid) {
            switch (item) {
                case RAVEN -> session.getWorldState().setFlag("pistol", "true");
                case HELIX_ARCHIVE -> session.getWorldState().setFlag("marketArchive", "true");
            }
        }
        return paid;
    }

    // ── Stato di gioco ───────────────────────────────────────────────────────

    public boolean isGameOver() {
        return episodeService.isGameOver();
    }
}