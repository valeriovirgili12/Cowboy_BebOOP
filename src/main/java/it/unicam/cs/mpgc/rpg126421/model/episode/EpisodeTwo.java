package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.FlagRequirement;
import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.WoolongRequirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.List;

/**
 * Episodio 2 — titolo e trama da definire.
 * I flag dell'episodio 1 iniziano a pesare sulle scelte disponibili.
 */
public class EpisodeTwo extends Episode {

    public EpisodeTwo() {
        super("ep2", "Episodio 2", buildScenes());
    }

    private static List<Scene> buildScenes() {

        // ── Scena 1 ──────────────────────────────────────────────────────────
        Choice choiceA1 = new Choice.Builder(
                "[A] Paga l'informatore — informazioni sicure",
                new Outcome.Builder()
                        .woolong(-200)
                        .morale(5)
                        .flag("ep2_info", "bought")
                        .narrative("Le informazioni sono accurate. Hai speso bene.")
                        .build()
        ).requires(new WoolongRequirement(200))
                .build();

        Choice choiceB1 = new Choice.Builder(
                "[B] Intimidisci l'informatore — gratis ma rischioso",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(-10)
                        .flag("ep2_info", "forced")
                        .narrative("Parla, ma non sai quanto fidarti. Rischio calcolato.")
                        .build()
        ).build();

        Scene scene1 = new Scene(
                "ep2_s1",
                "Hai bisogno di informazioni sul prossimo bersaglio. " +
                        "Un informatore di fiducia ha quello che ti serve, ma vuole essere pagato.",
                List.of(choiceA1, choiceB1)
        );

        // ── Scena 2 (scelta chiave) ───────────────────────────────────────────
        Choice choiceA2 = new Choice.Builder(
                "[A] Condividi la taglia con chi ti ha aiutato",
                new Outcome.Builder()
                        .woolong(-150)
                        .morale(20)
                        .flag("ep2_final", "honest")
                        .narrative("Mantieni la parola. La tua reputazione vale più dei woolong.")
                        .build()
        ).keyChoice().build();

        Choice choiceB2 = new Choice.Builder(
                "[B] Tieniti tutto — erano solo affari",
                new Outcome.Builder()
                        .woolong(300)
                        .morale(-20)
                        .flag("ep2_final", "opportunist")
                        .narrative("Soldi in tasca, coscienza sporca. " +
                                "Ma la Bebop non si paga da sola.")
                        .build()
        ).keyChoice().build();

        Choice choiceBonus = new Choice.Builder(
                "[BONUS] Chiedi un favore invece dei soldi — connessione futura",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(10)
                        .flag("ep2_final", "strategic")
                        .narrative("Un favore vale più dei woolong. Lo scoprirai nell'episodio finale.")
                        .build()
        ).requires(new FlagRequirement("ep1_final", "honest"))
                .keyChoice()
                .build();

        Scene scene2 = new Scene(
                "ep2_s2",
                "La taglia è presa. Ma chi ti ha aiutato aspetta la sua parte. " +
                        "Come la gestisci?",
                List.of(choiceA2, choiceB2, choiceBonus)
        );

        return List.of(scene1, scene2);
    }

    @Override
    public void initialize(GameSession session) {
        // in futuro: modificare testo scene in base ai flag ep1
    }
}