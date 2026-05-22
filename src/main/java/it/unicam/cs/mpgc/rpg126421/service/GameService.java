package it.unicam.cs.mpgc.rpg126421.service;

import it.unicam.cs.mpgc.rpg126421.model.character.crew.CrewMember;
import it.unicam.cs.mpgc.rpg126421.model.character.crew.Nyx;
import it.unicam.cs.mpgc.rpg126421.model.episode.Choice;
import it.unicam.cs.mpgc.rpg126421.model.episode.Episode;
import it.unicam.cs.mpgc.rpg126421.model.episode.Outcome;
import it.unicam.cs.mpgc.rpg126421.model.episode.Scene;
import it.unicam.cs.mpgc.rpg126421.model.market.Item;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CrewClass;

import java.util.List;
import java.util.Optional;

/**
 * Logica di gioco principale.
 * Applica le conseguenze delle scelte e avanza lo stato della partita.
 * Non conosce la UI — parla solo con GameSession.
 */
public class GameService {

    private final GameSession session;
    private boolean gameOver = false;
    public GameService(GameSession session) {
        if (session == null) throw new IllegalArgumentException("Session cannot be null");
        this.session = session;
    }

    // ── Scelte ───────────────────────────────────────────────────────────────

    /**
     * Applica l'outcome di una scelta alla sessione corrente.
     * Aggiorna woolong, morale, trust dei crew member e flag di mondo.
     */
    public void applyChoice(Choice choice, Outcome outcome) {
        int woolongDelta = outcome.getWoolongDelta();
        if (woolongDelta >= 0) {
            if (woolongDelta > 0) session.getFinance().earn(woolongDelta);
        } else {
            session.getFinance().spend(-woolongDelta);
        }
        session.getCaptain().changeMorale(outcome.getMoraleDelta());
        outcome.getTrustDeltas().forEach((memberName, delta) -> {
            if (memberName.equalsIgnoreCase("marcus")) {
                changeMarcusTrust(delta);
            } else {
                session.getCrew().stream()
                        .filter(m -> m.getName().equals(memberName))
                        .findFirst()
                        .ifPresent(m -> m.changeTrust(delta));
            }
        });
        outcome.getFlagsToSet().forEach((key, value) ->
                session.getWorldState().setFlag(key, value)
        );
        if (outcome.causesGameOver()) gameOver = true;
    }

    // mantieni il vecchio per compatibilità
    public void applyChoice(Choice choice) {
        applyChoice(choice, choice.getOutcome());
    }

    // ── Scene ────────────────────────────────────────────────────────────────

    /**
     * Restituisce le scelte disponibili nella scena corrente.
     */
    public List<Choice> getAvailableChoices(Scene scene) {
        return scene.getAvailableChoices(session);
    }

    /**
     * Applica una scelta e segna la scena come completata.
     */
    public void resolveScene(Scene scene, Choice choice) {
        Outcome outcome;

        if (choice.willFail(session)) {
            // scelta tentata senza requisiti — usa failure outcome
            if (choice.getOutcome().hasFailureOutcome()) {
                outcome = choice.getOutcome().getFailureOutcome();
            } else {
                outcome = choice.getOutcome(); // fallback
            }
            applyChoice(choice, outcome);
            if (outcome.causesGameOver()) gameOver = true;
        } else {
            outcome = choice.getOutcome();
            applyChoice(choice, outcome);
        }

        scene.complete();
        session.getNarrativeLog().add(choice.getLogEntry());
    }

    // ── Episodi ──────────────────────────────────────────────────────────────

    /**
     * Avvia il prossimo episodio non ancora iniziato.
     * @return l'episodio avviato, o empty se tutti completati
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
     * @return true se l'episodio è stato completato
     */
    public boolean tryCompleteCurrentEpisode() {
        return session.getCurrentEpisode()
                .filter(e -> e.getScenes().stream().allMatch(Scene::isCompleted))
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
     * Scala i costi fissi tra episodi (carburante, cibo).
     * Se i woolong sono insufficienti, imposta game over.
     */
    public boolean applyFixedCosts(int amount) {
        boolean paid = session.getFinance().spend(amount);
        if (!paid) {
            gameOver = true;
        }
        return paid;
    }

    /**
     * Acquista un oggetto dal mercato nero.
     * @return false se woolong insufficienti
     */
    public boolean buyItem(Item item) {
        boolean paid = session.getFinance().spend(item.getCost());
        if (paid) {
            session.addItem(item);
        }
        return paid;
    }

    public boolean isGameOver() { return gameOver; }


    // ── Crew ─────────────────────────────────────────────────────────────────

    /**
     * Recluta un membro nella crew.
     * @return false se era già presente
     */
    public boolean recruitCrew(CrewMember member) {
        return session.recruitCrew(member);
    }

    /**
     * Recluta Nyx con la classe complementare al capitano.
     */
    public void recruitNyx() {
        CrewClass nyxClass = switch (session.getCaptain().getCaptainClass()) {
            case GUNSLINGER  -> CrewClass.HACKER;
            case HACKER      -> CrewClass.GUNSLINGER;
            case BOUNTY_HUNTER, MECHANIC -> CrewClass.HACKER; // default complementare
        };
        Nyx nyx = new Nyx(nyxClass);
        session.recruitCrew(nyx);
        session.getWorldState().setFlag("recruitedNyx", "true");
    }

    /**
     * Restituisce Nyx se presente nella crew, altrimenti empty.
     */
    public java.util.Optional<Nyx> getNyx() {
        return session.getCrew().stream()
                .filter(m -> m instanceof Nyx)
                .map(m -> (Nyx) m)
                .findFirst();
    }

    /**
     * Modifica la fiducia di Marcus e imposta il flag corrispondente.
     */
    public void changeMarcusTrust(int delta) {
        session.getMarcus().changeTrust(delta);
        String state = session.getMarcus().isTrustHigh() ? "high" : "low";
        session.getWorldState().setFlag("trustMarcusHigh",
                String.valueOf(session.getMarcus().isTrustHigh()));
    }


    public GameSession getSession() { return session; }
}