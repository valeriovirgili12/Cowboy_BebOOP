package it.unicam.cs.mpgc.rpg126421.model.episode;

import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.CrewClassRequirement;
import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.FlagRequirement;
import it.unicam.cs.mpgc.rpg126421.model.episode.requirement.WoolongRequirement;
import it.unicam.cs.mpgc.rpg126421.model.session.GameSession;
import it.unicam.cs.mpgc.rpg126421.model.shared.CaptainClass;
import it.unicam.cs.mpgc.rpg126421.model.shared.CrewClass;

import java.util.List;

/**
 * Episodio 1 — "Cheap Cigarettes"
 * La Blue Mantis è a corto di carburante vicino a Io.
 * Una taglia su Lena Morrow apre una storia più grande.
 */
public class EpisodeOne extends Episode {

    public EpisodeOne() {
        super("ep1", "Cheap Cigarettes", buildScenes());
    }

    private static List<Scene> buildScenes() {

        // ── Scena 1 — La taglia ──────────────────────────────────────────────
        Choice findLena = new Choice.Builder(
                "Traccia il segnale — Lena si nasconde nel settore merci",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(0)
                        .flag("ep1_found_lena", "true")
                        .narrative("Il segnale porta a un vecchio magazzino pressurizzato. " +
                                "Lena Morrow è lì, con una pistola in mano e la paura negli occhi.")
                        .build()
        ).logEntry("Hai rintracciato Lena Morrow nel settore merci.")
                .build();

        Scene scene1 = new Scene(
                "ep1_s1",
                "ISSP — AVVISO TAGLIA\n\n" +
                        "Soggetto: Lena Morrow\n" +
                        "Taglia: ₩ 8.000\n" +
                        "Accusa: furto di dati classificati\n\n" +
                        "La Blue Mantis è in orbita bassa su Io. " +
                        "Il carburante basta per un altro salto, forse due. " +
                        "Marcus riconosce il cognome ma non dice nulla — ancora.",
                List.of(findLena)
        );

        // ── Scena 2 — L'incontro con Lena ────────────────────────────────────
        Choice listenToLena = new Choice.Builder(
                "Ascoltala — cosa ha rubato esattamente?",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(5)
                        .trust("marcus", -10)
                        .flag("ep1_listened_lena", "true")
                        .narrative("Lena parla veloce. Ha sottratto un archivio dalla Helix Corporation — " +
                                "dati che provano come l'azienda manipoli le taglie ISSP. " +
                                "Marcus ascolta in silenzio. La sua mascella si irrigidisce.")
                        .build()
        ).logEntry("Hai ascoltato la storia di Lena.")
                .build();

        Choice grabLena = new Choice.Builder(
                "[GUNSLINGER] Disarmala subito — le chiacchiere costano tempo",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(-10)
                        .trust("marcus", 5)
                        .flag("ep1_listened_lena", "false")
                        .narrative("La disarmi prima che finisca la frase. " +
                                "Professionale. Freddo. Marcus annuisce appena.")
                        .build()
        ).requires(new CrewClassRequirement(CaptainClass.GUNSLINGER))
                .logEntry("Hai neutralizzato Lena senza ascoltarla.")
                .build();

        Scene scene2 = new Scene(
                "ep1_s2",
                "Lena abbassa lentamente la pistola.\n\n" +
                        "\"Non sono una criminale. Helix mi ha messa sulla lista " +
                        "perché so troppo. Se mi consegnate all'ISSP sono morta " +
                        "prima dell'interrogatorio.\"\n\n" +
                        "Marcus si è fermato sulla soglia. Non interviene.",
                List.of(listenToLena, grabLena)
        );

        // ── Scena 3 — La scelta su Lena (A TEMPO) ───────────────────────────
        Choice spareLena = new Choice.Builder(
                "Lasciala andare — non vale i problemi",
                new Outcome.Builder()
                        .woolong(0)
                        .morale(20)
                        .trust("marcus", 15)
                        .flag("sparedLena", "true")
                        .narrative("Lena sparisce nel corridoio. " +
                                "Hai perso la taglia, ma hai guadagnato qualcos'altro. " +
                                "Marcus ti guarda diversamente.")
                        .build()
        ).logEntry("Hai lasciato andare Lena Morrow.")
                .keyChoice()
                .build();

        Choice deliverLena = new Choice.Builder(
                "Consegnala all'ISSP — una taglia è una taglia",
                new Outcome.Builder()
                        .woolong(8000)
                        .morale(-15)
                        .trust("marcus", -20)
                        .flag("sparedLena", "false")
                        .narrative("Lena viene caricata sul trasporto ISSP. " +
                                "Otto mila woolong sul conto. " +
                                "Marcus non parla per il resto della giornata.")
                        .build()
        ).logEntry("Hai consegnato Lena Morrow all'ISSP.")
                .timeout(30) // ← scatta se il giocatore non decide
                .keyChoice()
                .build();

        Choice sellInfo = new Choice.Builder(
                "[HACKER] Copia i suoi dati prima di consegnarla",
                new Outcome.Builder()
                        .woolong(8000)
                        .morale(-5)
                        .trust("marcus", -10)
                        .flag("sparedLena", "false")
                        .flag("ep1_copied_data", "true")
                        .narrative("I dati sono tuoi. Lena viene consegnata. " +
                                "Non è pulito, ma potrebbe valere qualcosa.")
                        .build()
        ).requires(new CrewClassRequirement(CaptainClass.HACKER))
                .logEntry("Hai copiato i dati di Lena prima di consegnarla.")
                .keyChoice()
                .build();

        Scene scene3 = new Scene(
                "ep1_s3",
                "La polizia spaziale sta convergendo sul settore.\n\n" +
                        "Hai pochi secondi per decidere cosa fare con Lena Morrow.\n\n" +
                        "Marcus: \"Qualunque cosa tu faccia, falla adesso.\"",
                List.of(spareLena, deliverLena, sellInfo)
        );


        return List.of(scene1, scene2, scene3);
    }

    @Override
    public void initialize(GameSession session) {
        // Logica contestuale futura:
        // es. se Marcus trust è già bassa, modificare testo scena 2
    }
}