package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.CrewClassRequirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CaptainClass;

import java.util.List;

/**
 * Episodio 1.
 * Introduce il giocatore al mondo, alla crew base e al sistema di taglie.
 */
public class EpisodeOne extends Episode {

    public EpisodeOne() {
        super("ep1", "Cheap Cigarettes", buildScenes());
    }

    private static List<Scene> buildScenes() {

        // ── Scena 1 ──────────────────────────────────────────────────────────
        Choice choiceA1 = new Choice.Builder(
                "[A] Prova a parlarci",
                new Outcome.Builder()
                        .morale(10)
                        .woolong(-50)
                        .flag("ep1_approach", "diplomatic")
                        .narrative("Tieni le mani lontane dalla pistola e lasci parlare il silenzio. " +
                                "Costa qualche woolong, ma almeno nessuno finisce steso sul pavimento.")
                        .build()
        ).build();

        Choice choiceB1 = new Choice.Builder(
                "[B] Prendi la taglia e vattene",
                new Outcome.Builder()
                        .morale(-10)
                        .woolong(150)
                        .flag("ep1_approach", "aggressive")
                        .narrative("Lavoro veloce. Rumoroso, ma veloce. " +
                                "Marcus non dice niente mentre contate i soldi.")
                        .build()
        ).build();

        Scene scene1 = new Scene(
                "ep1_s1",
                "Io puzza di carburante vecchio e sigarette economiche. " +
                        "La Blue Mantis attracca a una stazione che sembra tenuta insieme dalla ruggine. " +
                        "La taglia è semplice sulla carta. Di solito è lì che iniziano i problemi.",
                List.of(choiceA1, choiceB1)
        );

        // ── Scena 2 (scelta chiave) ───────────────────────────────────────────
        Choice choiceA2 = new Choice.Builder(
                "[A] Lascia andare il complice",
                new Outcome.Builder()
                        .morale(15)
                        .woolong(-100)
                        .flag("ep1_final", "honest")
                        .narrative("Abbassi l'arma per primo. " +
                                "Il ragazzo scappa senza voltarsi. Marcus si accende una sigaretta e sospira piano.")
                        .build()
        ).keyChoice().build();

        Choice choiceB2 = new Choice.Builder(
                "[B] Consegna anche il complice",
                new Outcome.Builder()
                        .morale(-15)
                        .woolong(300)
                        .flag("ep1_final", "opportunist")
                        .narrative("Due taglie pagano meglio di una. " +
                                "La stazione sparisce dietro di voi mentre la Blue Mantis riparte con il pieno quasi fatto.")
                        .build()
        ).keyChoice().build();

        Choice choiceHacker = new Choice.Builder(
                "[TECH] Blocca i sistemi della stazione",
                new Outcome.Builder()
                        .morale(5)
                        .woolong(200)
                        .flag("ep1_final", "clever")
                        .narrative("Le telecamere si spengono una dopo l'altra. " +
                                "Nessun allarme. Nessuna sparatoria. Solo il ronzio dei server e il motore della nave che si riaccende.")
                        .build()
        ).requires(new CrewClassRequirement(CaptainClass.HACKER))
                .keyChoice()
                .build();

        Scene scene2 = new Scene(
                "ep1_s2",
                "Il bersaglio è a terra. " +
                        "Il complice trema con una pistola puntata verso di te, troppo spaventato per sparare davvero. " +
                        "Marcus lo guarda per un secondo di troppo.",
                List.of(choiceA2, choiceB2, choiceHacker)
        );

        return List.of(scene1, scene2);
    }

    @Override
    public void initialize(GameSession session) {
        // logica di inizializzazione contestuale
    }
}