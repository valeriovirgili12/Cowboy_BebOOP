package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.FlagRequirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;

import java.util.List;

/**
 * Episodio 3 — la resa dei conti.
 * I flag accumulati determinano quale finale è disponibile.
 * Finale A (onesto) o Finale B (opportunista).
 */
public class EpisodeThree extends Episode {

    public EpisodeThree() {
        super("ep3", "Episodio 3", buildScenes());
    }

    private static List<Scene> buildScenes() {

        // ── Scena 1 ──────────────────────────────────────────────────────────
        Choice choiceA1 = new Choice.Builder(
                "[A] Affronta il problema di petto",
                new Outcome.Builder()
                        .morale(10)
                        .woolong(-100)
                        .flag("ep3_approach", "direct")
                        .narrative("Nessun gioco. Vai dritto al punto.")
                        .build()
        ).build();

        Choice choiceB1 = new Choice.Builder(
                "[B] Aspetta e osserva — informazioni prima",
                new Outcome.Builder()
                        .morale(5)
                        .woolong(0)
                        .flag("ep3_approach", "cautious")
                        .narrative("Pazienza. Conosci il tuo nemico prima di muoverti.")
                        .build()
        ).build();

        Scene scene1 = new Scene(
                "ep3_s1",
                "Il bersaglio finale è il più pericoloso che tu abbia mai inseguito. " +
                        "Come ti prepari?",
                List.of(choiceA1, choiceB1)
        );

        // ── Finale A — onesto ─────────────────────────────────────────────────
        Choice finaleA = new Choice.Builder(
                "[FINALE A] Fai la cosa giusta — anche se costa",
                new Outcome.Builder()
                        .morale(30)
                        .woolong(-200)
                        .flag("finale", "honest")
                        .narrative("Hai scelto la strada difficile. La crew ti guarda con rispetto. " +
                                "I woolong scarseggiano, ma dormi bene la notte. " +
                                "See you, Space Cowboy...")
                        .build()
        ).requires(new FlagRequirement("ep1_final", "honest"))
                .keyChoice()
                .build();

        // ── Finale B — opportunista ───────────────────────────────────────────
        Choice finaleB = new Choice.Builder(
                "[FINALE B] Massimizza il guadagno — sempre",
                new Outcome.Builder()
                        .morale(-20)
                        .woolong(500)
                        .flag("finale", "opportunist")
                        .narrative("La Bebop è piena di carburante e il frigo è carico. " +
                                "La coscienza? Un lusso che non puoi permetterti. " +
                                "See you, Space Cowboy...")
                        .build()
        ).keyChoice()
                .build();

        Scene scene2 = new Scene(
                "ep3_s2",
                "Il momento della verità. Tutto quello che hai fatto finora " +
                        "ti ha portato qui. Come vuoi che vada a finire?",
                List.of(finaleA, finaleB)
        );

        return List.of(scene1, scene2);
    }

    @Override
    public void initialize(GameSession session) {
        // in futuro: testo finale personalizzato in base ai flag accumulati
    }
}