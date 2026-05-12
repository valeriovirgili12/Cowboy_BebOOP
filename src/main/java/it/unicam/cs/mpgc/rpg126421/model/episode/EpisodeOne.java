package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.CrewClassRequirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CharacterClass;

import java.util.List;

/**
 * Episodio 1 — titolo e trama da definire.
 * Introduce il giocatore al mondo, alla crew base e al sistema di taglie.
 */
public class EpisodeOne extends Episode {

    public EpisodeOne() {
        super("ep1", "Episodio 1", buildScenes());
    }

    private static List<Scene> buildScenes() {

        // ── Scena 1 ──────────────────────────────────────────────────────────
        Choice choiceA1 = new Choice.Builder(
                "[A] Approccio diplomatico — parla prima di sparare",
                new Outcome.Builder()
                        .morale(10)
                        .woolong(-50)
                        .flag("ep1_approach", "diplomatic")
                        .narrative("Riesci a risolvere la situazione senza spargimento di sangue. " +
                                "Ci hai rimesso qualche woolong, ma la tua reputazione sale.")
                        .build()
        ).build();

        Choice choiceB1 = new Choice.Builder(
                "[B] Approccio diretto — prendi la taglia e scappa",
                new Outcome.Builder()
                        .morale(-10)
                        .woolong(150)
                        .flag("ep1_approach", "aggressive")
                        .narrative("Lavoro fatto, tasca piena. Qualcuno si è fatto male, " +
                                "ma non era il tuo problema.")
                        .build()
        ).build();

        Scene scene1 = new Scene(
                "ep1_s1",
                "Una soffiata ti ha portato su una stazione orbitale fatiscente. " +
                        "Il bersaglio è in vista, ma non è solo.",
                List.of(choiceA1, choiceB1)
        );

        // ── Scena 2 (scelta chiave) ───────────────────────────────────────────
        Choice choiceA2 = new Choice.Builder(
                "[A] Lascia andare il complice — non vale la taglia",
                new Outcome.Builder()
                        .morale(15)
                        .woolong(-100)
                        .flag("ep1_final", "honest")
                        .narrative("Lo lasci andare. Non era il tipo da cacciare. " +
                                "Forse hai fatto la cosa giusta.")
                        .build()
        ).keyChoice().build();

        Choice choiceB2 = new Choice.Builder(
                "[B] Consegna anche il complice — doppia taglia",
                new Outcome.Builder()
                        .morale(-15)
                        .woolong(300)
                        .flag("ep1_final", "opportunist")
                        .narrative("Doppia taglia, doppio guadagno. " +
                                "La Bebop ha il carburante per un altro mese.")
                        .build()
        ).keyChoice().build();

        Choice choiceHacker = new Choice.Builder(
                "[TECH] Hacera i sistemi della stazione — via di fuga sicura",
                new Outcome.Builder()
                        .morale(5)
                        .woolong(200)
                        .flag("ep1_final", "clever")
                        .narrative("Con i sistemi in tuo controllo, gestisci tutto " +
                                "senza lasciare tracce. Lavoro pulito.")
                        .build()
        ).requires(new CrewClassRequirement(CharacterClass.HACKER))
                .keyChoice()
                .build();

        Scene scene2 = new Scene(
                "ep1_s2",
                "Il bersaglio è neutralizzato, ma il suo complice ti punta una pistola. " +
                        "Come chiudi questa faccenda?",
                List.of(choiceA2, choiceB2, choiceHacker)
        );

        return List.of(scene1, scene2);
    }

    @Override
    public void initialize(GameSession session) {
        // logica di inizializzazione contestuale — per ora vuota
        // qui in futuro potremo modificare scene in base ai flag esistenti
    }
}